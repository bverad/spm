package cl.adexus.isl.spm

class OTPIngresoService {
	def documentacionService
	def GenPDFService
	
	def buscarInfoTrabajador (runTrabajador) {
		log.info("Ejecutando metodo buscarInfoTrabajador")
		log.info("rut trabajador : $runTrabajador")
		// Primero vamos a validar el run
		def persona = new PersonaNatural()
		persona.run = runTrabajador.replaceAll("\\.|-", "")
		persona.nombre = "AAA"
		persona.apellidoPaterno = "BBB"
		persona.apellidoMaterno = "CCC"
		if (!persona.validate()) {
			return [status : 'NOK']		
		}			
		
		def next
		
		runTrabajador = ((String)runTrabajador).replaceAll(/\.|\-/, "").toUpperCase().trim()
		
		if (!runTrabajador) {
			next="dp01"
			return ['next': [action: next]]
		}
		
		// 20140207. HJ.
		//              Cambia la condición de buscar que un trabajador:
		//				a) Esté en un siniestro y que este siniestro,
		//              b) Esté en una RECA (REsolución de CAlificación)
		def trabajador   = PersonaNatural.findByRun(runTrabajador)
		def siniestros   = Siniestro.findAllByTrabajador(trabajador)
		def siniestrosOK = []// O sea, los que estén RECA
		def reca
		siniestros.each {
			reca = RECA.findBySiniestro(it)
			if (reca != null && reca?.xmlRecibido){
				siniestrosOK.add(it)
			}
		}
				
		//log.info("empresas.size: " + empresas.size())
		if( siniestrosOK.size() != 0){
			next="dp03"
		} else {
			next="dp02"
		}
		
		def r = [status: 'OK', next: [action: next], model: ['runTrabajador' : runTrabajador, 'siniestrosOK' : siniestrosOK ]]
		
		return (r)
	}

	def buscarSiniestro (siniestroId) {
		def model=[:]
		
		if (siniestroId == null)
			return null
		
		// Busca si viene un id de Siniestro
		Siniestro s = Siniestro.get(siniestroId)
			
		if (s) {
			// Busca Denuncias
			def diats=DIAT.findAllBySiniestro(s)
			def dieps=DIEP.findAllBySiniestro(s)

			// Busca ODA
			def odas=ODA.findAllBySiniestro(s)
			
			//OPA
			if (s.opa!=null && !s.opa.isAttached()) {
				s.opa.attach()
			}
			log.info("centro:"+s.opa?.centroAtencion?.prestador?.personaJuridica?.razonSocial) //Pal Lazy
			
			//OPAEP
			if (s.opaep!=null && !s.opaep.isAttached()) {
				s.opaep.attach()
			}
			log.info("centro:"+s.opaep?.centroAtencion?.prestador?.personaJuridica?.razonSocial) //Pal Lazy
			
			//Documentacion Adicional
			def docsDIAT = DocumentacionAdicional.executeQuery(
					"SELECT	d " +
					"FROM  	DocumentacionAdicional as d, DIAT as diat " +
					"WHERE 	diat.siniestro.id = ?" +
					"AND	d.denunciaAT.id = diat.id", [s.id]
				);
			def docsDIEP = DocumentacionAdicional.executeQuery(
				"SELECT	d " +
				"FROM  	DocumentacionAdicional as d, DIEP as diep " +
				"WHERE 	diep.siniestro.id = ?" +
				"AND	d.denunciaEP.id = diep.id", [s.id]
				);
			
			model = [ 'diats'     : diats,
                      'dieps'     : dieps,
                      'odas'      : odas,
                      'docsDIAT'  : docsDIAT,
                      'docsDIEP'  : docsDIEP,
                      'siniestro' : s]
		}
			
		return ([ model: model ])
	}

	def cu04 (params, guardar) {
		def siniestro = Siniestro.findById(params.siniestroId)
		def reembolso = new Reembolso()
		
		// I. Identificación del Beneficio
		def idBeneficio = [:]
				
		if (params.idBeneficio) {
			if (!params.idBeneficio.getClass().isArray()) {
				// Convertimos a array antes de procesar
				params.idBeneficio = [params.idBeneficio]
			}			
			idBeneficio.traslado        = params.idBeneficio.findIndexOf { it ==~ /traslado/ } != -1
			idBeneficio.medicamentos    = params.idBeneficio.findIndexOf { it ==~ /medicamentos/ } != -1
			idBeneficio.hospitalizacion = params.idBeneficio.findIndexOf { it ==~ /hospitalizacion/ } != -1
			idBeneficio.alojamiento     = params.idBeneficio.findIndexOf { it ==~ /alojamiento/ } != -1			
		} else {
			// Si ninguno de los beneficios fue chequeado, error!
			reembolso.errors.reject("reembolso", "Debe haber al menos un Beneficio Identificado (Item I)")
		}
		// Esta pasada de datos es para devolver si hay error. Por eso es importante.
		// Ahora veamos que validaciones se solicitan (TODO), por ejemplo, que haya al menos una marcada, etc.
		// Pasemos estos datos a los booleanos de nuestro nuevo reembolso.
		reembolso.siniestro        = siniestro
		reembolso.trasladoPaciente = idBeneficio.traslado != null ? idBeneficio.traslado : false
		reembolso.medicamentos     = idBeneficio.medicamentos != null ? idBeneficio.medicamentos : false
		reembolso.hospitalizacion  = idBeneficio.hospitalizacion != null ? idBeneficio.hospitalizacion : false
		reembolso.alojamiento      = idBeneficio.alojamiento != null ? idBeneficio.alojamiento : false
		
		// II. Identificación del Trabajador
		// Paso de datos a reembolso
		reembolso.trabajador             = PersonaNatural.findByRun(params.trabajadorRun)
		reembolso.trabajadorDireccion    = params.direccion
		reembolso.trabajadorComuna       = Comuna.findByCodigo(params.comuna)
		reembolso.trabajadorTelefonoFijo = params.telefonoFijo
		reembolso.trabajadorCelular      = params.celular
		reembolso.trabajadorEmail        = params.correoElectronico
		
		// Si el celular o el correo electronico no vienen vacíos, debemos validarlos
		if (params.celular != null && params.celular != "") {
			if (!(params.celular ==~ /\d+/)) reembolso.errors.reject("reembolso", "Celular Inválido (Item II)")
		}
		
		if (params.correoElectronico != null && params.correoElectronico != "") {
			if (!(params.correoElectronico ==~ /[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})/))
				reembolso.errors.reject("reembolso", "Correo Electrónico Inválido (Item II)")
		}
		
		// La siguiente pasada de datos es por si hay un error, para que se lo podamos
		// devolver al controller y renderear
		def otrosDatosTrabajador = [
			'direccion'     : params.direccion,
			'comuna'        : params.comuna,
			'telefonoFijo'  : params.telefonoFijo,
			'celular'       : params.celular,
			'email'         : params.correoElectronico
		]
		
		// III. Identificación del Solicitante
		// Validaciones
		// Al menos un tipo de solicitante
		if (params["clicker-solicitante"] == null) {
			reembolso.errors.reject("reembolso", "Debe elegir al menos un Tipo de Solicitante (Item III)")
		}	
		// RUN, Nombre, Apellidos Paterno y Materno
		if (params.solicitanteRut == null             || params.solicitanteRut == "" ||
		    params.solicitanteNombres == null         || params.solicitanteNombres == "" ||
			params.solicitanteApellidoPaterno == null || params.solicitanteApellidoPaterno == "" ||
			params.solicitanteApellidoMaterno == null || params.solicitanteApellidoMaterno == "") {
			reembolso.errors.reject("reembolso", "Debe completar los datos del solicitante (Item III)")
		}			
				
		// Si user marcó `Otro`, debe llenar `relación` con algo
		if (params["clicker-solicitante"] == "Otro" && (params.solicitanteRelacion == null || params.solicitanteRelacion == "")) {
			reembolso.errors.reject("reembolso", "Debe indicar relación del solicitante (Item III)")
		}		
		
		// Paso de datos a reembolso
		reembolso.solicitanteTipo          = params["clicker-solicitante"]
		reembolso.solicitanteRelacion      = params.solicitanteRelacion
		// Vamos a revisar si este solicitante existe en nuestra BD como persona natural
		def solicitantePersona
		log.info "params.solicitanteRut Service: ${params.solicitanteRut}"
		if (params.solicitanteRut) {
			log.info "Dentro de IF: ${params.solicitanteRut}"
			solicitantePersona             = PersonaNatural.findByRun(params.solicitanteRut.replaceAll("\\.|-", ""))
			// Si no existe lo creamos y tomamos los datos que nos mandaron
			if (solicitantePersona == null) {
				solicitantePersona = new PersonaNatural()
				solicitantePersona.run             = params.solicitanteRut.replaceAll("\\.|-", "")
				solicitantePersona.nombre          = params.solicitanteNombres
				solicitantePersona.apellidoPaterno = params.solicitanteApellidoPaterno
				solicitantePersona.apellidoMaterno = params.solicitanteApellidoMaterno
				// Antes de grabar a la persona, hacemos una validación
				if (!solicitantePersona.validate()) {
					reembolso.errors.reject("reembolso", "Existen errores en la validación del solicitante (Item III)")
				} else {
					solicitantePersona.save(flush: true)
				}
			}
			reembolso.solicitante = solicitantePersona
		}
		// Paso de datos por si hay error
		def solicitante = [:]
		solicitante.tipo        = params["clicker-solicitante"]
		def solicitanteRelacion = params.solicitanteRelacion

		// IV. Opción de Pago
		// (TODO) Validaciones
		// Paso de datos a reembolso
		def montoSolicitado = (params.montoSolicitado != null && params.montoSolicitado != "")?
			Long.parseLong(params.montoSolicitado.replaceAll(/\.|\,/, "")) : 0
		reembolso.montoSolicitado    = montoSolicitado
		reembolso.tipoPagoDeposito   = params.tipoPago == "deposito" ? true : false
		reembolso.tipoPagoPresencial = params.tipoPago == "pagoPresencial" ? true : false
		
		// Necesitamos validar que en el caso que tengamos tipoPago == "deposito", entonces
		// el usuario debe entregar datos del numero de cuenta, tipo y banco
		if (reembolso.tipoPagoDeposito == true) {
			// Pasamos los valores
			reembolso.tipoCuenta = TipoCuenta.findByCodigo(params.tipoCuenta)
			reembolso.numero 	 = params.cuentaNumero.trim()
			reembolso.banco      = Banco.findByCodigo(params.cuentaBanco)
			
			if (reembolso.tipoCuenta == null) {
				reembolso.errors.reject("reembolso", "Debe ingresar el TIPO de Cuenta")
			}
			
			if (reembolso.numero == null || reembolso.numero.trim() == "") {
				reembolso.errors.reject("reembolso", "Debe ingresar NÚMERO de Cuenta")
			}
			
			if (reembolso.banco == null) {
				reembolso.errors.reject("reembolso", "Debe ingresar el BANCO de la Cuenta")
			}
		}
		
		def odpPersona
		// Validemos si me mandaron rut de solicitante
		log.info "params.cobradorRut Service: ${params.cobradorRut}"
		if (params.cobradorRut.replaceAll("\\.|-", "") == "") {
			reembolso.errors.reject("reembolso", "Debe haber rut de solicitante ingresado! (Item IV)")
		} else {
			// Vamos a revisar si este solicitante existe en nuestra BD como persona natural
			odpPersona = PersonaNatural.findByRun(params.cobradorRut.replaceAll("\\.|-", ""))
			// Si no existe lo creamos y tomamos los datos que nos mandaron
			if (odpPersona == null) {
				odpPersona = new PersonaNatural()
				odpPersona.run             = params.cobradorRut.replaceAll("\\.|-", "")
				odpPersona.nombre          = params.cobradorNombres
				odpPersona.apellidoPaterno = params.cobradorApellidoPaterno
				odpPersona.apellidoMaterno = params.cobradorApellidoMaterno
				// Antes de grabar a la persona, hacemos una validación
				if (!odpPersona.validate()) {
					reembolso.errors.reject("reembolso", "Existen errores en la validación del cobrador (Item IV)")
				} else {
					odpPersona.save(flush: true)
				}
			}
			reembolso.cobrador = odpPersona
		}
		
		// Paso de datos por si hay error
		def opcionDePago = [:]
		opcionDePago.montoSolicitado = params.montoSolicitado
		opcionDePago.tipoPago        = params.tipoPago
		opcionDePago.tipoCuenta      = params.tipoCuenta
		opcionDePago.cuentaNumero    = params.cuentaNumero
		opcionDePago.cuentaBanco     = params.cuentaBanco
		
		// V. Observaciones
		// (TODO) Validaciones
		// Paso de datos a reembolso
		reembolso.observaciones = params.observaciones
		// Paso de datos por si hay error
		def observaciones = params.observaciones
		
		// Validar!
		if (reembolso.errors.hasErrors() || !reembolso.validate()) {
			// Nos fue mal :(
			return  (['next': [action: 'dp04'], model: ['reembolso'            : reembolso,
														'siniestro'            : siniestro,
														'siniestroId'          : params.siniestroId,
														'idBeneficio'          : idBeneficio,
														'otrosDatosTrabajador' : otrosDatosTrabajador,
														'solicitante'          : solicitante,
														'solicitantePersona'   : solicitantePersona,
														'solicitanteRelacion'  : solicitanteRelacion,
														'opcionDePago'		   : opcionDePago,
														'odpPersona'           : odpPersona,
														'observaciones'        : observaciones
			]])
		} else {
			// Estamos validados
			// Grabamos o devolvemos no más?
			if (guardar == true) {
				if (reembolso.save()) {
					return (['status' : 'OK', model: [ 'reembolsoId' : reembolso.id ]])
				}
			} else {
				// Solo devolvemos validado
				return (['status' : 'OK', model: [ 'reembolso'   : reembolso,
                                                   'siniestroId' : params.siniestroId ]])
			}
		}
	}
	
	def imprimeSolicitudReembolso (reembolso) {
		byte[] b
		ByteArrayOutputStream pdf = genPDFService.genSolReembolsoPDF(reembolso)
		b = pdf.toByteArray()
		return b
	}

	def cu07 (params) {
		def reembolso             = Reembolso.findById(params.reembolsoId)
		def detalles              = DetalleGastosReembolso.findAllByReembolso(reembolso)
		def valorAprobado
		def sumaValoresAprobados  = 0
		def comentario
		def hasValidationError    = false     
		
		detalles.each {
			valorAprobado = params['valorAprobado_' + it.id].replaceAll(/[^0-9]+/, "")
			comentario    = params['comentario_' + it.id]
			
			if (valorAprobado == "") valorAprobado = null
			if (comentario == "") comentario = null
		
			// Validación
			if (it.valorSolicitado < Long.parseLong(valorAprobado)) hasValidationError = true
				
			it.valorAprobado = Long.parseLong(valorAprobado)
			it.comentario    = comentario			
			it.save(flush : true)
			
			// La suma?
			sumaValoresAprobados += it.valorAprobado
		}
		
		// Si encontré un error, el usuario debe corregirlo
		if (hasValidationError) {
			return ([status      : 'Error',
				     mensaje     : 'Valor Aprobado debe ser menor o igual al Valor Solicitado',
					 reembolsoId : params.reembolsoId
			])
		}		
		
		// Ya. El tema que aquí importa es cuando apruebo o rechazo.
		// Eso ve que haya mail y redirección
		if (sumaValoresAprobados == 0) {
			reembolso.fechaRechazo = new Date()
			return ([status : 'Rechazo'])
		} else {
			reembolso.fechaAprobacion = new Date()
			// Enviamos el mail
			genPDFService.genAvisoReembolsoCorreo(reembolso)
			return ([status : 'Aprueba'])
		}
	}
	
	def getDatosPersonaJuridica (String rut) {
		rut = rut == null ? "" : rut.replaceAll(/\.|\-/, "").toUpperCase().trim()
		def respuesta = [:]
		def persona = PersonaJuridica.findByRut(rut)
		if (persona != null){
			respuesta.razon_social = persona.razonSocial
		}
		
		return respuesta
	}
	
	def getDatosPersonaNatural (String run) {
		run = run == null ? "" : run.replaceAll(/\.|\-/, "").toUpperCase().trim()
		def respuesta = [:]
		def persona = PersonaNatural.findByRun(run)
		if (persona != null){
			respuesta.nombre          = persona.nombre
			respuesta.apellidoPaterno = persona.apellidoPaterno
			respuesta.apellidoMaterno = persona.apellidoMaterno
		}
		
		return respuesta
	}
	
	def agregaDetalleReembolso (session, params, f) {
		def reembolsoId = params?.reembolsoId
		def reembolso   = Reembolso.findById(reembolsoId)
		
		def detalleReembolso          = new DetalleGastosReembolso()
		// Validemos que la fecha sea menor a ahora
		log.info "params.detalleFechaGasto: ${params.detalleFechaGasto}"
		if (params.detalleFechaGasto > new Date()) {
			detalleReembolso.errors.reject("detalleReembolso", "Fecha del detalle de Gasto debe ser menor o igual a la actual")
		}
		// Validemos que la fecha sea mayor o igual a la del siniestro
		def hoy = Calendar.getInstance()
		def tsFechaGasto = Calendar.getInstance()
		tsFechaGasto.setTime(params.detalleFechaGasto)
		tsFechaGasto.set(Calendar.HOUR_OF_DAY, hoy.get(Calendar.HOUR_OF_DAY))
		tsFechaGasto.set(Calendar.MINUTE, hoy.get(Calendar.MINUTE))
		tsFechaGasto.set(Calendar.SECOND, hoy.get(Calendar.SECOND))
		tsFechaGasto = tsFechaGasto.getTime()
		def tsSiniestro = reembolso?.siniestro?.fecha
		log.info "${tsFechaGasto} - ${tsSiniestro}"
		if (tsFechaGasto <= tsSiniestro) {
			detalleReembolso.errors.reject("detalleReembolso", "Fecha del detalle de Gasto debe ser mayor o igual a la del siniestro")
		}
		// Validemos que el valor solicitado sea menor o igual al del documento
		int solicitado = Long.parseLong(params.detalleValorSolicitado)
		int documento = Long.parseLong(params.detalleValorDocumento)
		if (solicitado > documento) {
			detalleReembolso.errors.reject("detalleReembolso", "Valor Solicitado debe ser menor o igual a Valor Documento")
		}
		def detGastos = DetalleGastosReembolso.findAllByReembolso(reembolso)
		def sumaGastos = 0
		for (def dgReembolso in detGastos)
		{
			sumaGastos += dgReembolso.valorSolicitado
		}
		// Validemos que la suma de los montos solicitados sea menor a lo especificado en el reembolso
		if (Long.parseLong(params.detalleValorSolicitado) + sumaGastos > reembolso.montoSolicitado) {
			detalleReembolso.errors.reject("detalleReembolso", "Suma de Gastos debe ser menor o igual a Monto Solicitado")
		}
		detalleReembolso.fechaGasto  = params.detalleFechaGasto
		detalleReembolso.numero      = params.detalleNumeroBoletaFactura
		detalleReembolso.concepto    = TipoConceptoReembolso.findByCodigo(params.detalleConcepto)
		detalleReembolso.reembolso   = reembolso

		def personaJuridica = [:]
		def personaNatural  = [:]

		// Enjuague
		params.proveedorJuridicoRut = params.proveedorJuridicoRut.replaceAll(/\.|\-/, "").toUpperCase().trim()
		params.proveedorNaturalRut  = params.proveedorNaturalRut.replaceAll(/\.|\-/, "").toUpperCase().trim()		
		
		if (params.tipoProveedor == 'juridico') {
			detalleReembolso.tipoProveedorPersonaJuridica = true
			detalleReembolso.tipoProveedorPersonaNatural  = false
			
			personaJuridica = PersonaJuridica.findByRut(params.proveedorJuridicoRut)
			if (personaJuridica == null) {
				personaJuridica             = new PersonaJuridica()
				personaJuridica.rut         = params.proveedorJuridicoRut
				personaJuridica.razonSocial = params.proveedorJuridicoRazonSocial
				if (personaJuridica.validate()) {
					personaJuridica.save(flush : true)
				}
			}
			
			detalleReembolso.proveedorJuridico = personaJuridica
			
		} else if (params.tipoProveedor == 'natural') {
			detalleReembolso.tipoProveedorPersonaJuridica = false
			detalleReembolso.tipoProveedorPersonaNatural  = true
			
			personaNatural = PersonaNatural.findByRun(params.proveedorNaturalRut)
			
			if (personaNatural == null) {
				personaNatural                 = new PersonaNatural()
				personaNatural.run             = params.proveedorNaturalRut
				personaNatural.nombre          = params.proveedorNaturalNombres
				personaNatural.apellidoPaterno = params.proveedorNaturalApellidoPaterno
				personaNatural.apellidoMaterno = params.proveedorNaturalApellidoMaterno
				if (personaNatural.validate()) {
					personaNatural.save(flush : true)
				}
			}
			
			detalleReembolso.proveedorNatural = personaNatural
		}
		
		detalleReembolso.valorDocumento  = (params.detalleValorDocumento != null && params.detalleValorDocumento != "")? Long.parseLong(params.detalleValorDocumento.replaceAll(/\.|\,/, "")) : 0
		detalleReembolso.valorSolicitado = (params.detalleValorSolicitado != null && params.detalleValorSolicitado != "")? Long.parseLong(params.detalleValorSolicitado.replaceAll(/\.|\,/, "")) : 0
		
		// Preparamos el modelo (para devolver en caso de)
		def model = [ 'reembolso'        : reembolso,
			          'reembolsoId'      : reembolsoId,
			          'detalleReembolso' : detalleReembolso,
			          'personaJuridica'  : personaJuridica,
			          'personaNatural'   : personaNatural,
			          'detalleArchivo'   : params?.detalleArchivoAdjunto
		]
		
		if (personaNatural.errors?.hasErrors() || 
			personaJuridica.errors?.hasErrors() ||
			detalleReembolso.errors.hasErrors() || 
			!detalleReembolso.validate()) {
			log.info "Hay errores!"
			return (['result' : 'NOK', model : model])
		} else {		
			if (!detalleReembolso.save(flush : true)) {
				return (['result' : 'NOK', model : model])
			} else {
				// Aquí es donde la cosa se pone interesante. Nosotros dejamos la subida del documento
				// para este paso, de manera de poder contar con el id del detalle del reembolso
				// si por alguna razón a mi me va mal sacando esta subida, yo _le_ elimino el detalleReembolso
				// recien hecho y _le_ devuelvo un error.
			//	session['detalleReembolso'] = detalleReembolso
			//	session['run']              = reembolso.trabajador.run
			//	session['siniestroId']      = reembolso.siniestro.id
				def map = ['detalleReembolso': detalleReembolso, 'run': reembolso.trabajador.run, 'siniestroId': reembolso.siniestro.id]
				def result = documentacionService.uploadSinSesion(map, 'Detalle Reembolso', 'REEMDET', f)
				
				if (result.status == 0) {
					// Nos fue bien y devolvemos un OK, ya que tenemos un archivo asociado
					model.remove('detalleReembolso')
					model.remove('personaJuridica')
					model.remove('personaJuridica')
					return (['result' : 'OK', model : model])
				} else {
					// Debemos informar del error al usuario (y borrar el detalle que hicimos)
					detalleReembolso.delete(flush : true)
					detalleReembolso.errors.reject("detalleReembolso", "Hubo un error con la grabación del archivo: ${result.mensaje}")
					return (['result' : 'NOK', model : model])
				}
			}
		}
	}

	def eliminaDetalleReembolso (detalleId) {
		def detalle   = DetalleGastosReembolso.findById(detalleId)
		// Busca el documento adjunto que le compete y orderna borrarlo
		def documento = DocumentacionAdicional.findByDetalleReembolso(detalle)
		def r         = documentacionService.delete(Long.toString(documento.id))
		
		if (r.status == 0) {
			// Bien? Eliminemos el item en la BD además
			detalle.delete(flush : true)
			return "OK"
		} else {
			return "NOK"
		}
	}
}
