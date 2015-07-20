package cl.adexus.isl.spm

import cl.adexus.isl.spm.helpers.FormatosISLHelper
import grails.converters.JSON
import javax.servlet.ServletOutputStream
import cl.adexus.isl.spm.enums.TipoCalificacionEnum

class OTP_ingresoController {
	
	def OTPIngresoService
	def DenunciaService
	def documentacionService

    def index () { redirect(action: 'dp01') }
	
	def dp01 () {
		// nothing to do here
	}

	def r01 () {
		def runTrabajador
		
		log.info("*** r01 ***")
		
		if (params?.runTrabajador == null)
			forward(action: "dp01")
			
		def r = OTPIngresoService.buscarInfoTrabajador(params.runTrabajador)
		
		if (r.status == 'OK') {
			params.put('model', r.model)
			flash.mensajes = null			
			def next = r.next
			next.params = params
			forward (next)
		} else {
			flash.mensajes = "RUN Inválido"			
			render (view : 'dp01')
		}
	}	
	
	def dp02 () {		
		def model
		def runTrabajador
		
		if(params.get('model')) {
			
			if (params.get('model').runTrabajador) {
				runTrabajador = params.get('model').runTrabajador
				model = [ 'runTrabajador': runTrabajador ]
			}
		}
		
		model
	}
		
	def dp03 () {		
		def model
		def trabajador
		def runTrabajador
		def siniestros
		
		log.info("*** dp03 ***")
		
		if(params.get('model')) {
			if (params.get('model').runTrabajador) {
				runTrabajador = params.get('model').runTrabajador
			}			
		}else if(flash.get('model')) {
			if (flash.get('model').runTrabajador) {
				runTrabajador = flash.get('model').runTrabajador
			}
		} else {
			// Podemos ver si se envi� como par�metro
			runTrabajador = params?.runTrabajador
		}

		// Fallback
		if (runTrabajador == null) return redirect (action: "dp01")
			
		log.info("buscando informaci�n del rut: " + runTrabajador)
		trabajador = PersonaNatural.findByRun(runTrabajador)

		def r = OTPIngresoService.buscarInfoTrabajador(runTrabajador)
		siniestros = r?.model?.siniestrosOK
		log.info("siniestros: " + siniestros.size())
		
		// Ordenamos los siniestros, buscamos el último, sacamos su DIAT (o DIEP)
		// y de ahí extraemos datos del trabajador
		def idUltimoFecha = new Date(0)
		def idUltimo
		siniestros.each {
			if (it.fecha > idUltimoFecha) {
				idUltimoFecha = it.fecha
				idUltimo      = it.id
			}
		}	
		def ultimoSiniestro = Siniestro.findById(idUltimo);
		/*deprecado por issue #1035 
		 * def dixx            = DIAT.findBySiniestro(ultimoSiniestro) // dixx pq es diat o diep
		if (dixx == null) {
			dixx            = DIEP.findBySiniestro(ultimoSiniestro)
		}*/
		def dixx = ultimoSiniestro.diatOA
		if (ultimoSiniestro?.esEnfermedadProfesional == true) {
			dixx = ultimoSiniestro?.diepOA
		}
		
		// (La función está justo abajo de esta...)
		def direccion = auxiliarDatosTrabajador(dixx?.direccionTrabajadorTipoCalle?.descripcion) + ' ' +
                        auxiliarDatosTrabajador(dixx?.direccionTrabajadorNombreCalle)            + ' ' + 
                        auxiliarDatosTrabajador(dixx?.direccionTrabajadorNumero)                 + ' ' +
                        auxiliarDatosTrabajador(dixx?.direccionTrabajadorRestoDireccion)
		
		def datosTrabajador = [
			direccion    : direccion,
			comuna       : auxiliarDatosTrabajador(dixx?.direccionTrabajadorComuna?.descripcion),
			telefono     : auxiliarDatosTrabajador(dixx?.telefonoTrabajador),
			nacionalidad : auxiliarDatosTrabajador(dixx?.nacionalidadTrabajador?.descripcion)	
		]		
	
		// Iteramos por siniestro para generar el "tipo de siniestro"
		def diat
		def reca
		def s = []
		
		siniestros.each {			
			//corresponde!
			def siniestro = [:]
			
			// Agregamos las propiedades del siniestro que vamos a utilizar
			siniestro.id     = it.id
			siniestro.fecha  = it.fecha
			siniestro.relato = it.relato
			
			// Tipo de Siniestro?
			if (it?.esEnfermedadProfesional == true) {
				siniestro.tipoSiniestro = "Enfermedad Profesional"
			} else {
				diat = DIAT.findBySiniestro(it)
				if (diat?.esAccidenteTrayecto == true) {
					siniestro.tipoSiniestro = "Accidente de Trayecto"
				} else {
					siniestro.tipoSiniestro = "Accidente del Trabajo"
				}
			}
			
			// Calificación del Siniestro?
			reca = RECA.findBySiniestro(it)
			
			
			// TODO
			// Este `if null` es entero flaite. Pero reca no está lista aún.
			if (reca != null) {
				siniestro.calificacion = reca.calificacion
			}
			
			// Se va este `siniestro` a nuestro array `s`
			s.push(siniestro)			
		}
		
		model = [ trabajador      : trabajador, 
				  datosTrabajador : datosTrabajador,
				  siniestros      : s ]
		
		model
	}
	
	// Auxiliar, para llenar la direccion
	def auxiliarDatosTrabajador (val) {
		if (val != null && val != '') {
			return val.toString()
		} else {
			return ''
		}
	}
		
	def cu03() {
		log.info("Ejecutando action cu03")
		log.info("Datos recibidos : $params")
		if (params?._action_cu03 == "Terminar") {
			def model = [runTrabajador : params?.runTrabajador]

			
			params.put('model', model)
			def next = [action: "dp02"]
			next.params = params
			forward (next)
		} else {
			def model
			def siniestroId = params?.siniestroId
			
			if (siniestroId == null || siniestroId == "") {
				flash.mensajes = "Debe seleccionar un siniestro para continuar"
				model = [ 'runTrabajador': params?.runTrabajador ]

				params.put('model', model)
				def next = [action: "dp03"]
				next.params = params
				forward (next)
			}
			
			def siniestro = Siniestro.get(params?.siniestroId)
			def reca = RECA.findBySiniestro(siniestro)
			log.info("RECA : ${reca?.id} ,  ${reca?.calificacion?.descripcion}")
			if(reca?.calificacion?.codigo?.equals(TipoCalificacionEnum.CODE_06.getCodigo()) ||
				reca?.calificacion?.codigo?.equals(TipoCalificacionEnum.CODE_07.getCodigo())){
				flash.mensajes = "No es posible realizar reembolsos para siniestros calificados como : ${reca?.calificacion?.descripcion}"
				model = [ 'runTrabajador': params?.runTrabajador ]

				params.put('model', model)
				def next = [action: "dp03"]
				next.params = params
				forward (next)
			}
			
			
			
			log.info("*** cu03 ***")
			
			log.info("siniestroId : " + siniestroId)
			
			model = [ siniestroId: siniestroId ]
			params.put('model', model)
			def next = [action: "dp04"]
			next.params = params
			forward (next)
		}
	}	
	
	def dp04 () {		
		def siniestroId = params?.siniestroId
		def tipoSiniestro
		def model = [:]
		
		log.info("*** dp04 ***")
		
		if(params.get('model')) {
			if (params.get('model').siniestroId) {
				siniestroId = params.get('model').siniestroId
			}		
		}
		
		// Un fallback
		if (siniestroId == null) return redirect(action: "dp01")
		
		log.info("siniestroId : " + siniestroId)
		def siniestro = Siniestro.findById(siniestroId)
		
		// I. IDENTIFICACION DEL BENEFICIO		
		if (siniestro?.esEnfermedadProfesional == true) {
			tipoSiniestro = "Enfermedad Profesional"
		} else {
			def diat = DIAT.findBySiniestro(siniestro)
			if (diat?.esAccidenteTrayecto == true) {
				tipoSiniestro = "Accidente de Trayecto"
			} else {
				tipoSiniestro = "Accidente del Trabajo"
			}
		}
		
		// II. IDENTIFICACION DEL TRABAJADOR		
		def trabajadorNombreCompleto = FormatosISLHelper.nombreCompletoStatic(siniestro.trabajador)
		
		// Buscamos que no sea, un error. Si lo es, entonces los otrosDatosTrabajador vienen
		// de antes
		def otrosDatosTrabajador = [:]
		
		if (params.get('model') && params.get('model').otrosDatosTrabajador) {
			otrosDatosTrabajador = params.get('model').otrosDatosTrabajador
		} else {
			// Saquemos la dirección, comuna y teléfono fijo de los datos del ultimo siniestro
			def dixx            = DIAT.findBySiniestro(siniestro) // dixx pq es diat o diep
			if (dixx == null) {
				dixx            = DIEP.findBySiniestro(siniestro)
			}
			// (La función está más arriba...)
			otrosDatosTrabajador.direccion     = auxiliarDatosTrabajador(dixx.direccionTrabajadorTipoCalle?.descripcion) + ' ' +
											     auxiliarDatosTrabajador(dixx.direccionTrabajadorNombreCalle)            + ' ' +
											     auxiliarDatosTrabajador(dixx.direccionTrabajadorNumero)                 + ' ' +
											     auxiliarDatosTrabajador(dixx.direccionTrabajadorRestoDireccion)
											 
			otrosDatosTrabajador.comuna        = Comuna.findByDescripcion(dixx.direccionTrabajadorComuna?.descripcion)?.codigo
			otrosDatosTrabajador.telefonoFijo  = auxiliarDatosTrabajador(dixx.telefonoTrabajador)
		}
		
		// Lista de las comunas para el combobox
		def comunas = Comuna.listOrderByDescripcion()

		// III. IDENTIFICACION DEL SOLICITANTE		
		// Buscamos que no sea un error. Si lo es, entonces el solicitante viene de antes
		def solicitante = [:]		
		if (params.get('model') && params.get('model').solicitante) {
			solicitante = params.get('model').solicitante
		} // No hay `else`. El usuario tiene que llenar acá..
		
		def solicitantePersonaRelacion
		if (params.get('model') && params.get('model').solicitanteRelacion) {
			solicitantePersonaRelacion = params.get('model').solicitanteRelacion
		} // No hay `else`. El usuario tiene que llenar acá..
	
		def solicitantePersona = [:]
		if (params.get('model') && params.get('model').solicitantePersona) {
			solicitantePersona = params.get('model').solicitantePersona
		} // No hay `else`. El usuario tiene que llenar acá..
		
		// IV. OPCION DE PAGO
		// Buscamos que no sea un error. Si lo es, entonces el solicitante viene de antes
		def opcionDePago = [:]
		if (params.get('model') && params.get('model').opcionDePago) {
			opcionDePago = params.get('model').opcionDePago
		} // No hay `else`. El usuario tiene que llenar acá..
		
		def odpPersona = [:]
		if (params.get('model') && params.get('model').odpPersona) {
			odpPersona = params.get('model').odpPersona
		} // No hay `else`. El usuario tiene que llenar acá..
		
		// Lista de los tipos de cuentas para el combobox
		def tipoCuentas = TipoCuenta.listOrderByDescripcion()
		
		// Lista de los bancos para el combobox
		def bancos = Banco.listOrderByDescripcion()		

		// V. OBSERVACIONES
		def observaciones
		if (params.get('model') && params.get('model').observaciones) {
			observaciones = params.get('model').observaciones
		} // No hay `else`. El usuario tiene que llenar acá..
		
		// Este objeto acarrería errores si es que viene de vuelta
		def reembolso
		if (params.get('model') && params.get('model').reembolso) {
			reembolso = params.get('model').reembolso
		}	
		
		model = [
			siniestroId				   : siniestroId,
			siniestro                  : siniestro,
			tipoSiniestro              : tipoSiniestro,
			trabajadorNombreCompleto   : trabajadorNombreCompleto,
			otrosDatosTrabajador       : otrosDatosTrabajador,
			comunas                    : comunas,
			solicitante                : solicitante,
			solicitantePersona         : solicitantePersona,
			solicitantePersonaRelacion : solicitantePersonaRelacion,
			opcionDePago               : opcionDePago,
			odpPersona				   : odpPersona,
			tipoCuentas                : tipoCuentas,
			bancos                     : bancos,
			observaciones              : observaciones
		]
		
		if(params.get('model')) {
			// Así agrega cosas de la redirección por error
			model += params.get("model")
		}
		
		model
	}
	
	// Valida el dp04.
	// Si le va bien manda a imprimir un pdf y levanta un modal
	// para que el usuario grabe
	def cu04() {
		def model
		def siniestroId = params?.siniestroId
		
		log.info "params.solicitanteRut: ${params.solicitanteRut}"
		def r = OTPIngresoService.cu04(params, false)
		
		if (r.status == 'OK') {
			// Imprimimos el pdf
			def b = OTPIngresoService.imprimeSolicitudReembolso(r.get('model').reembolso)
			if(b != null){
				response.setContentType("application/pdf")
				response.setHeader("Content-disposition", "attachment; filename=Solicitud_Reembolso.pdf")
				response.setContentLength(b.length)
				response.getOutputStream().write(b)
			} else {
				//No se genera el PDF
			}
		} else {
			// Tomamos los params y reescribimos dp04.
			// Para que salgan los errores
			params.put('model', r.model)
			def next = r.next
			next.params = params
			forward (next)
		}
	}
	
	// Esta es la función que definitivamente guarda el reembolso
	// Hace las mismas validaciones que cu04.
	// La diferencia es que no manda a imprimir
	def guardarReembolso () {
		def model
		def siniestroId = params?.siniestroId
		
		def r = OTPIngresoService.cu04(params, true)

		if (r.status == 'OK') {
			params.put('model', r.model)
			def next = [action: "dp06"]
			next.params = params
			forward (next)
		} else {
			// Tomamos los params y reescribimos dp04.
			// Para que salgan los errores
			params.put('model', r.model)
			def next = r.next
			next.params = params
			forward (next)
		}
	}	
	
	def dp06 () {
		def reembolsoId = params?.reembolsoId
		if(params.get('model')) {
			if (params.get('model').reembolsoId) {
				reembolsoId = params.get('model').reembolsoId
			}
		}
		// fallback
		if (reembolsoId == null) return redirect(action: "dp01")

		log.info("*** dp06 ***")
		log.info("reembolsoId : " + reembolsoId)
		
		def reembolso = Reembolso.findById(reembolsoId)
		
		// Otro fallback
		if (reembolso == null) return redirect(action: "dp01")
		
		// Saquemos los detalles anteriores
		def dts          = DetalleGastosReembolso.findAllByReembolso(reembolso)		
		def detalles     = []
		def rutProveedor
		def nombreProveedor
		def sumaGastos   = 0
		
		dts.each {
			if (it.tipoProveedorPersonaJuridica) {
				rutProveedor    = it.proveedorJuridico.rut
				nombreProveedor = it.proveedorJuridico.razonSocial
			} else {
				rutProveedor    = it.proveedorNatural.run
				nombreProveedor = it.proveedorNatural.nombre + " " +
				                  it.proveedorNatural.apellidoPaterno + " " +
								  it.proveedorNatural.apellidoMaterno
			}
					
			detalles.add([
				'id'              : it.id,
				'fechaGasto'      : it.fechaGasto,
				'numero'          : it.numero,
				'concepto'        : it.concepto.descripcion,
				'rutProveedor'    : rutProveedor,
				'nombreProveedor' : nombreProveedor,
				'valorDocumento'  : it.valorDocumento,
				'valorSolicitado' : it.valorSolicitado
			])
			
			sumaGastos += it.valorSolicitado
		}
		
		def trabajadorNombreCompleto
		if (reembolso?.siniestro?.trabajador != null) {
			trabajadorNombreCompleto = reembolso.siniestro.trabajador.nombre + " " +
									   reembolso.siniestro.trabajador.apellidoPaterno + " " +
									   reembolso.siniestro.trabajador.apellidoMaterno
		}
		
		def tipoSiniestro
		if (reembolso?.siniestro?.esEnfermedadProfesional == true) {
			tipoSiniestro = "Enfermedad Profesional"
		} else {
			def diat = reembolso?.siniestro != null ? DIAT.findBySiniestro(reembolso.siniestro) : null
			if (diat?.esAccidenteTrayecto == true) {
				tipoSiniestro = "Accidente de Trayecto"
			} else {
				tipoSiniestro = "Accidente del Trabajo"
			}
		}
		
		def solicitanteNombreCompleto
		if (reembolso?.solicitante != null) {
			solicitanteNombreCompleto = reembolso.solicitante.nombre + " " +
									    reembolso.solicitante.apellidoPaterno + " " +
									    reembolso.solicitante.apellidoMaterno
		}
		
		// Conceptos de Detalle Gasto Reembolso
		def conceptos = TipoConceptoReembolso.listOrderByCodigo()
		
		// Hay detalle devuelto?
		def detalleReembolso
		if(params.get('model')) {
			if (params.get('model').detalleReembolso) {
				detalleReembolso = params.get('model').detalleReembolso
			}
		}
		
		def personaJuridica = [:]
		if (params.get('model') && params.get('model').personaJuridica) {
			personaJuridica = params.get('model').personaJuridica
		} // No hay `else`. El usuario tiene que llenar acá..
		
		def personaNatural = [:]
		if (params.get('model') && params.get('model').personaNatural) {
			personaNatural = params.get('model').personaNatural
		} // No hay `else`. El usuario tiene que llenar acá..
		
		def relacionDelSolicitante
		if (reembolso.solicitanteTipo == 'Otro') {
			relacionDelSolicitante = reembolso.solicitanteRelacion
		} else {
			relacionDelSolicitante = reembolso.solicitanteTipo
		}
		
		// Debemos averiguar si hay un archivo escaneado
		def documento = DocumentacionAdicional.findByReembolso(reembolso)
				
		def model = [ reembolso                 : reembolso,
					  detalles                  : detalles,
					  detalleReembolso          : detalleReembolso,
					  sumaGastos                : sumaGastos,
			          trabajadorNombreCompleto  : trabajadorNombreCompleto,
					  tipoSiniestro             : tipoSiniestro,
					  solicitanteNombreCompleto : solicitanteNombreCompleto,
					  relacionDelSolicitante    : relacionDelSolicitante,
					  conceptos                 : conceptos,
					  personaJuridica           : personaJuridica,
					  personaNatural            : personaNatural,
					  documento                 : documento
		]
	}
	
	def uploadArchivoReembolsoEscaneado () {
		def reembolsoId = params?.reembolsoId
		def reembolso   = Reembolso.findById(reembolsoId)
		def model       = [ reembolsoId: reembolsoId ]
		def documento
		
		def f =  request.getFile('fileFormularioFirmado')
		
//		session['reembolso']        = reembolso
//		session['run']              = reembolso.trabajador.run
//		session['siniestroId']      = reembolso.siniestro.id
		def map = ['reembolso': reembolso, 'run': reembolso.trabajador.run, 'siniestroId': reembolso.siniestro.id]
		
		// Busquemos si había un documento para este reembolso a priori.
		// Si lo hay, lo borramos antes de subir
		documento = DocumentacionAdicional.findByReembolso(reembolso)
		if (documento != null) {
			documentacionService.delete(Long.toString(documento.id))
		}
		
		// Subimos
		//def result = documentacionService.upload(session, 'Reembolso firmado', 'REEM', f)
		def result = documentacionService.uploadSinSesion(map, 'Reembolso firmado', 'REEM', f)

		
		if (result.status == 0) {
			// Necesitamos la id del documento
			documento = DocumentacionAdicional.findByReembolso(reembolso)
			model += [documento : documento.id]
		} else {
			flash.mensajes = 'Debe adjuntar un archivo para subir!'
		}
		params.put('model', model)
		def next = [action: "dp06"]
		next.params = params
		forward (next)
	}
	
	def deleteArchivoReembolsoEscaneado() {
		def id			= params.id
		def r			= documentacionService.delete(id)
		def reembolsoId = params.reembolsoId
		def model       = [ reembolsoId: reembolsoId ]
		params.put('model', r.model)
		def next = [action: "dp06"]
		next.params = params
		forward (next)
	}
	
	def cu06() {
		def model = [:]
		def reembolsoId = params?.reembolsoId
		def reembolso   = Reembolso.findById(reembolsoId)
		def action
		
		// fallback
		if (reembolso == null) return redirect (action : 'dp01')
		
		log.info("*** cu06 ***")
		log.info("reembolsoId : " + reembolsoId)
		
		// Debemos averiguar si hay un archivo escaneado
		// No puede avanzar si no lo tiene
		def documento = DocumentacionAdicional.findByReembolso(reembolso)
		if (documento == null) {
			flash.mensajes = "Debe subir el archivo escaneado para poder continuar"
			action        = 'dp06'
		} else {
			action        = 'outIngreso' 
		}
				
		model = [ reembolsoId: reembolsoId ]
		params.put('model', model)
		def next = [action: action]
		next.params = params
		forward (next)
	}
	
	def datosPrestadorJuridicoJSON () {
		def rutPrestador = params.rutPrestador
		def datos = OTPIngresoService.getDatosPersonaJuridica(rutPrestador)
		JSON.use("deep"){ render datos as JSON }
	}
	
	def datosPrestadorNaturalJSON () {
		def rutPrestador = params.rutPrestador
		def datos = OTPIngresoService.getDatosPersonaNatural(rutPrestador)
		JSON.use("deep"){ render datos as JSON }
	}
	
	def agregaDetalleReembolso () {
		def reembolsoId = params?.reembolsoId
		def f           = request.getFile('detalleArchivoAdjunto')
		// Llamamos a la función que hace el trabajo
		def r = OTPIngresoService.agregaDetalleReembolso(session, params, f)
		params.put('model', r.model)
		def next = [action: "dp06"]
		next.params = params
		forward (next)
	}
	
	def eliminaDetalleReembolso () {
		def detalleId = params.id
		def detalle   = DetalleGastosReembolso.findById(detalleId)
		
		def r = OTPIngresoService.eliminaDetalleReembolso(detalleId)
		
		if (r == "OK") {
			flash.mensajes = "El detalle se ha borrado exitosamente"
		} else {
			flash.mensajes = "Hubo un error tratando de borrar el detalle"
		}
		forward action: "dp06", params: [reembolsoId : detalle.reembolso.id]
	}
   
	
	def outIngreso(){
		redirect (controller: 'nav', action: 'area6')
	}
	
	/** hasta aca **/
	
	def listDp07(){
		def reembolsos = Reembolso.findAllByFechaAprobacionIsNullAndFechaRechazoIsNull()
		 return ['reembolsos': reembolsos];
	}
	
	def dp07 () {
		def reembolsoId = params?.reembolsoId
		if(params.get('model')) {
			if (params.get('model').reembolsoId) {
				reembolsoId = params.get('model').reembolsoId
			}
		}
		// fallback
		if (reembolsoId == null) return redirect(action: "dp01")
		
		log.info("*** dp06 ***")
		log.info("reembolsoId : " + reembolsoId)
		
		def reembolso = Reembolso.findById(reembolsoId)
		
		// Saquemos los detalles anteriores
		def dts          = DetalleGastosReembolso.findAllByReembolso(reembolso)
		def detalles     = []
		def rutProveedor
		def nombreProveedor
		def sumaGastos    = 0
		def sumaAprobados = 0
		
		
		dts.each {
			if (it.tipoProveedorPersonaJuridica) {
				rutProveedor    = it.proveedorJuridico.rut
				nombreProveedor = it.proveedorJuridico.razonSocial
			} else {
				rutProveedor    = it.proveedorNatural.run
				nombreProveedor = it.proveedorNatural.nombre + " " +
								  it.proveedorNatural.apellidoPaterno + " " +
								  it.proveedorNatural.apellidoMaterno
			}
					
			detalles.add([
				'id'              : it.id,
				'fechaGasto'      : it.fechaGasto,
				'numero'          : it.numero,
				'concepto'        : it.concepto.descripcion,
				'rutProveedor'    : rutProveedor,
				'nombreProveedor' : nombreProveedor,
				'valorDocumento'  : it.valorDocumento,
				'valorSolicitado' : it.valorSolicitado,
				'valorAprobado'   : it.valorAprobado,
				'comentario'      : it.comentario
			])
			
			sumaGastos    += it.valorSolicitado
			sumaAprobados += it.valorAprobado
		}
		
		def trabajadorNombreCompleto
		if (reembolso?.siniestro?.trabajador != null) {
			trabajadorNombreCompleto = reembolso.siniestro.trabajador.nombre + " " +
									   reembolso.siniestro.trabajador.apellidoPaterno + " " +
									   reembolso.siniestro.trabajador.apellidoMaterno
		}
		
		def tipoSiniestro
		if (reembolso?.siniestro?.esEnfermedadProfesional == true) {
			tipoSiniestro = "Enfermedad Profesional"
		} else {
			def diat = reembolso?.siniestro != null ? DIAT.findBySiniestro(reembolso.siniestro) : null
			if (diat?.esAccidenteTrayecto == true) {
				tipoSiniestro = "Accidente de Trayecto"
			} else {
				tipoSiniestro = "Accidente del Trabajo"
			}
		}
		
		def solicitanteNombreCompleto
		if (reembolso?.solicitante != null) {
			solicitanteNombreCompleto = reembolso.solicitante.nombre + " " +
										reembolso.solicitante.apellidoPaterno + " " +
										reembolso.solicitante.apellidoMaterno
		}
		
		def relacionDelSolicitante
		if (reembolso.solicitanteTipo == 'Otro') {
			relacionDelSolicitante = reembolso.solicitanteRelacion
		} else {
			relacionDelSolicitante = reembolso.solicitanteTipo
		}
		
		def model = [   reembolso                 : reembolso,
						detalles                  : detalles,
						trabajadorNombreCompleto  : trabajadorNombreCompleto,
						tipoSiniestro             : tipoSiniestro,
						solicitanteNombreCompleto : solicitanteNombreCompleto,
						relacionDelSolicitante    : relacionDelSolicitante,
						sumaGastos                : sumaGastos,
						sumaAprobados             : sumaAprobados
		]
	}

	def cu07 () {
		def r = OTPIngresoService.cu07(params)
		
		if (r.status == 'Error') {
			flash.mensajes = r.mensaje

			params.put('model', [reembolsoId : r.reembolsoId])
			def next = [action: 'dp07']
			next.params = params
			forward (next)

		} else {
			return redirect ([controller: 'nav', action: 'area6'])
		}
	}
	
	def verSiniestro() {
		session['siniestroControllerHome'] = "OTP_ingreso"
		log.info("ver Siniestro: "+params)
		forward controller: "siniestro", action: "cu02" , params: [id: params.siniestroId]
	}
	
	def verDetalleAdjunto () {
		// fallback
		if (params.detalleId == null) return null	
		def detalle   = DetalleGastosReembolso.findById(params.detalleId)		
		// otro fallback
		if (detalle == null) return null		
		def documento = DocumentacionAdicional.findByDetalleReembolso(detalle)		
		// otro fallback más!
		if (documento == null) return null		
		return descargaDocumento(Long.toString(documento.id))
	}	

	def verDocumentoAdjunto () {
		// fallback
		if (params.id == null) return null
		return descargaDocumento(params.id)
	}
	
	def descargaDocumento (String documentoId) {
		def r = documentacionService.view(documentoId)
		def doc = r.doc

		response.setContentLength((int)doc.contentLength)
		response.setHeader("Content-disposition", "attachment; filename=\"" + doc.name + "\"")
		
		ServletOutputStream outputStream = null
		DataInputStream docStream = null
		int length = 0
		outputStream = response.getOutputStream()
		docStream = new DataInputStream(doc.inputStream)
		byte[] bbuf = new byte[1024]
		while ((docStream != null) && ((length = docStream.read(bbuf)) != -1)) {
				outputStream.write(bbuf, 0, length)
		}
		outputStream?.flush()
		docStream?.close()
		outputStream?.close()
		
		// Debido a un bug de grails se asigna el content type despues de haber escrito al outputStream del response
		response.setContentType(doc.mimeType)
		
		null
	}
}
