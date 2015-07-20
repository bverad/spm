package cl.adexus.isl.spm

import cl.adexus.isl.spm.SistemaSalud

class SDAAT_diatController {

	def documentacionService
	def SDAATService
	def DenunciaService

	def index() {
		def sdaat = SDAAT.findById(session['sdaat'])
		def model = [ 'sdaat': sdaat ]
	}

	/**
	 * Formulario
	 * - Calificacion Denunciante
	 * - RUN Denunciante
	 * [Siguiente] --> r01
	 */
	def dp01(){
		//Identificacion del denunciante
		def sdaat = SDAAT.findById(session['sdaat'])

		def calificaciones = CalificacionDenunciante.executeQuery("select cd from CalificacionDenunciante cd where cd.id <> ?",["7"])//CalificacionDenunciante.list()
		def model=[ calificaciones: calificaciones, sdaat: sdaat ]
		if(params.get('model')){
			model += params.get('model')
		}

		model
	}

	/**
	 * Busca si hay denuncia previa para ese denunciante
	 * 
	 * Si no hay --> dp02
	 * Si hay --> dp05
	 */
	def r01(){
		// Llamamos al servicio
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.diatR01(params, sdaat)
		params.put('model', r.get('model'))
		forward action: r.next.action, params: params
	}

	/**
	 * DIAT Sección A y B
	 * 
	 * [Siguiente] --> cu02
	 */
	def dp02(){
		log.info("Ejecutando action dp02")
		log.info("Datos recibidos $params")
		// DIAT
		def sdaat = SDAAT.findById(session['sdaat'])

		def comunas=Comuna.listOrderByDescripcion()
		def tipoCalles=TipoCalle.listOrderByCodigo()
		def tipoPropiedades=TipoPropiedadEmpresa.listOrderByCodigo()
		def tipoEmpresas=TipoEmpresa.listOrderByCodigo()
		def naciones = Nacion.listOrderByDescripcion()
		def etnias = Etnia.listOrderByCodigo()
		def tipoDuracionContratos = TipoDuracionContrato.listOrderByCodigo()
		def tipoRemuneraciones = TipoRemuneracion.listOrderByCodigo()
		def categoriaOcupaciones = CategoriaOcupacion.listOrderByCodigo()
		def tipoActividadEconomica = TipoActividadEconomica.listOrderByDescripcion()
		

		// Vemos si podemos prellenar con algo
		def diat = sdaat?.diat?:params?.model?.diat
		if (!diat) {
			diat = new DIAT()
			diat.setTrabajador(sdaat?.trabajador)
			diat.setEmpleador(sdaat?.empleador)
			diat.ciiuEmpleador = TipoActividadEconomica.findByCodigo(sdaat?.codigoActividadEmpresa)
		}
		if (!diat.nacionalidadTrabajador)
			diat.nacionalidadTrabajador=Nacion.findByCodigo('152') //Chileno por defecto
		// Se evaluan requeridos en funci�n del tipo denunciante
		def tipoDenunciante = sdaat?.tipoDenunciante
		def esEmpleador = tipoDenunciante.codigo == '1'
		def attrsReq = [  'ciiuEmpleador'					: esEmpleador
			, 'direccionEmpleadorTipoCalle'		: esEmpleador
			, 'direccionEmpleadorNombreCalle'	: esEmpleador
			, 'direccionEmpleadorNumero'		: esEmpleador
			, 'direccionEmpleadorComuna'		: esEmpleador
			, 'nTrabajadoresHombre'				: esEmpleador
			, 'nTrabajadoresMujer'				: esEmpleador
			, 'propiedadEmpresa'				: esEmpleador
			, 'tipoEmpresa'						: esEmpleador
			, 'direccionTrabajadorTipoCalle'	: esEmpleador
			, 'direccionTrabajadorNombreCalle'	: esEmpleador
			, 'direccionTrabajadorNumero'		: esEmpleador
			, 'direccionTrabajadorComuna'		: esEmpleador
			, 'profesionTrabajador'				: esEmpleador
			, 'duracionContrato'				: esEmpleador
			, 'tipoRemuneracion'				: esEmpleador
			, 'categoriaOcupacion'				: esEmpleador
			, 'fechaIngresoEmpresa'				: esEmpleador]
		def model=[   comunas					: comunas
			, tipoCalles				: tipoCalles
			, tipoPropiedades			: tipoPropiedades
			, tipoEmpresas				: tipoEmpresas
			, naciones					: naciones
			, etnias					: etnias
			, tipoDuracionContratos		: tipoDuracionContratos
			, tipoRemuneraciones		: tipoRemuneraciones
			, categoriaOcupaciones		: categoriaOcupaciones
			, diat						: diat
			, attrsReq					: attrsReq
			, tipoActividadEconomica	: tipoActividadEconomica
			, sistemaSalud				: SistemaSalud.list()
			, sdaat                     : sdaat]

		if (params.get('model')) {
			model += params.get('model')
		}
		
		def trabajador = params?.model?.trabajador
		def trabajadorErrors = params?.model?.trabajadorErrors
		if(trabajador && trabajadorErrors){
			trabajador.errors.reject(trabajadorErrors)
		}
		
		def denunciante = params?.model?.denunciante
		def denuncianteErrors = params?.model?.denuncianteErrors
		if(denunciante && denuncianteErrors){
			denunciante.errors.reject(denuncianteErrors)
		}
		
		def d = params?.model?.diat 
		def errores = params?.model?.errores
		log.info("errores diat : $errores")
		if(d && errores){
			d.errors = errores
		}
		
		
		def diatOA = params?.model?.diatOA
		def diatOAErrors = params?.model?.diatOAErrors
		if(diatOA && diatOAErrors){
			diatOA.errors.reject(diatOAErrors)
		}
		
		def siniestro = params?.model?.siniestro
		def siniestroErrors = params?.model?.siniestro
		if(siniestro && siniestroErrors){
			siniestro.errors.reject(siniestroErrors)
		}
		
		model
	}

	/**
	 * Procesa Seccion A y B de DIAT
	 * Todo OK --> dp02_2
	 * Algo malo --> dp02
	 */
	def cu02(){
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.diatCU02(params, sdaat)
		params.put('model', r.get('model'))
		forward action: r.next.action, params: params
	}

	/**
	 * DIAT Seccion C y D
	 *
	 * [Siguiente] --> cu02_2
	 *
	 * @return
	 */
	def dp02_2(){
		def sdaat = SDAAT.findById(session['sdaat'])
		log.info("dp02_2: sdaat: " + sdaat)

		def comunas = Comuna.listOrderByDescripcion()
		def criterioGravedades = CriterioGravedad.listOrderByCodigo()
		def tiposAccidenteTrayecto = TipoAccidenteTrayecto.list()
		def tipoMedioPruebaAccidentes = TipoMedioPruebaAccidente.list()

		//Vemos si podemos prellenar con algo
		def diat = sdaat.diat
		if (!diat?.como)
			diat.setComo(sdaat?.relato)
		if (!diat?.denunciante)
			sdaat.denunciante.refresh()
		diat.setDenunciante(sdaat?.denunciante)
		if (!diat?.calificacionDenunciante)
			diat.setCalificacionDenunciante(sdaat?.tipoDenunciante)
		if (!diat.fechaAccidente) {
			if(sdaat?.cuestionarioOrigenTrayecto?.pregunta1)
				diat.fechaAccidente = sdaat?.cuestionarioOrigenTrayecto?.pregunta1
			else
				diat.fechaAccidente = sdaat?.fechaSiniestro
		}
		/* No estoy seguro si se debe sacar este codigo o corregir 2013-10-01 :: SVERA
		 if(session?.sdaat?.cuestionarioOrigenTrayecto?.pregunta4 && !diat?.horaSalida){
		 diat.horaSalida=session?.sdaat?.cuestionarioOrigenTrayecto?.pregunta4
		 }*/

		def diatPrevias = DenunciaService.diatPrevias(sdaat.fechaSiniestro, sdaat.empleador, sdaat.trabajador);
		if (diatPrevias.size > 0) {
			diat.esAccidenteTrayecto = diatPrevias[0].esAccidenteTrayecto
		} else {
			if (!diat?.esAccidenteTrayecto) {
				diat.esAccidenteTrayecto = sdaat?.esAccidenteTrayecto
			}
		}
		if (diat?.esAccidenteTrayecto && !diat?.tipoAccidenteTrayecto)
			diat.tipoAccidenteTrayecto = sdaat?.cuestionarioOrigenTrayecto?.tipoAccidenteTrayecto

		def tipoDenunciante = sdaat?.tipoDenunciante
		def esEmpleador = tipoDenunciante.codigo == '1'
		def attrsReq = [  'horaIngreso'			: esEmpleador
			, 'horaSalida'			: esEmpleador
			, 'que'					: esEmpleador
			, 'lugarAccidente'		: esEmpleador
			, 'trabajoHabitualCual' : esEmpleador
			, 'esTrabajoHabitual'	: esEmpleador
			, 'gravedad'			: esEmpleador
			, 'esAccidenteTrayecto'	: esEmpleador
			, 'medioPrueba'			: esEmpleador
			, 'detallePrueba'		: esEmpleador]
		def model=[comunas						: comunas
			, criterioGravedades			: criterioGravedades
			, tiposAccidenteTrayecto		: tiposAccidenteTrayecto
			, tipoMedioPruebaAccidentes	: tipoMedioPruebaAccidentes
			, diat							: diat
			, attrsReq						: attrsReq
			, sdaat                        : sdaat]

		if(params.get('model')){
			model += params.get('model')
		}
		if(!model.diat?.esAccidenteTrayecto && diat?.esAccidenteTrayecto)
			model.diat.esAccidenteTrayecto = diat?.esAccidenteTrayecto


		if(model.diat?.tipoAccidenteTrayecto == null && diat?.tipoAccidenteTrayecto != null)
			model.diat.tipoAccidenteTrayecto = diat?.tipoAccidenteTrayecto
		model

	}

	/**
	 * Procesa Seccion C y D de DIAT
	 * Todo OK --> dp03
	 * Algo malo --> dp02_02
	 */
	def cu02_2(){
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.diatCU02_2(params, sdaat)
		params.put('model', r.get('model'))
		forward action: r.next.action, params: params
	}


	def cu02_2back_cu02() {
		def sdaat = SDAAT.findById(session['sdaat'])

		def r = SDAATService.diatCU02_2BackCU02(params, sdaat)
		params.put('model', r.get('model'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}

	/**
	 * Formulario
	 *
	 * [Generar PDF DIAT Borrador] --> genBorradorPdf()
	 * [Corregir DIAT] --> dp02()
	 * [Generar PDF DIAT Definitiva] --> genPdf()
	 * [Denunciante aprueba DIAT] --> dp04()
	 */
	def dp03(){
		def sdaat = SDAAT.findById(session['sdaat'])
		def model = [ 'sdaat': sdaat ]
	}

	/**
	 * Genera el borrador del DIAT en PDF
	 */
	def genBorradorPdf(){
		def sdaat = SDAAT.findById(session['sdaat'])

		def b = SDAATService.diatGenPdf(params, sdaat,true)
		if (b != null) {
			response.setContentType("application/pdf")
			response.setHeader("Content-disposition", "attachment; filename=diatBorrador.pdf")
			response.setContentLength(b.length)
			response.getOutputStream().write(b)
		} else {
			// No se generó el PDF
		}
	}

	/**
	 * Genera el DIAT en PDF
	 */
	def genPdf(){
		def sdaat = SDAAT.findById(session['sdaat'])

		def b = SDAATService.diatGenPdf(params, sdaat,false)
		if (b != null) {
			response.setContentType("application/pdf")
			response.setHeader("Content-disposition", "attachment; filename=diat.pdf")
			response.setContentLength(b.length)
			response.getOutputStream().write(b)
		} else {
			// No se generó el PDF
		}
	}

	/**
	 * Formulario
	 *  - fileDenuncia
	 * [Subir denuncia firmada] --> uploadDiatFirmada()
	 * [Siguiente] --> r04
	 */
	def dp04(){
		def sdaat = SDAAT.findById(session['sdaat'])

		def model = ['sdaat': sdaat ]
		if (params.get('model')?.denuncia != null)
		{
			model.put('denuncia', params.get('model')?.denuncia)
		}
		model
	}

	/**
	 * Procesa el archivo de DIAT firmado
	 */
	def uploadDiatFirmada() {
		def sdaat = SDAAT.findById(session['sdaat'])
		def f = request.getFile('fileDenuncia')
		def result = documentacionService.upload(session, 'DIAT firmada', 'DIAT', f)
		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha subido exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
		}
		render(view: 'dp04', model:[  sdaat: sdaat])

	}

	/**
	 * Procesa la diat, guardando la diat en el siniestro si es que no existe
	 * 
	 * --> dp05
	 */
	def r04(){
		log.info("Ejecutando accion r04")
		log.info("Datos recibidos : $params")

		log.info("Obteniendo sdaat desde sesion")
		def sdaat = SDAAT.findById(session['sdaat'])
		log.info("Sdaat obtenida")
		log.info("Obteniendo 77bis desde sesion")
		params['bisId'] = session['77bis']
		log.info("77bis obtenido")

		log.info("Ejecutando SDAATService.diatR04")
		def r = SDAATService.diatR04(params, sdaat)

		log.info("Verificando si hay datos seteado en el valor de params model")
		if(r.get('model')){
			log.debug("Existen datos")
			params.put('model', r.get('model'))
		}else
			log.debug("no existen datos")

		log.info("Verificando si accion redirecciona a dp05")
		def next = r.next
		if(next.action=='dp05'){
			log.info("Accion existe, redireccionando a ${next.action}")
			flash.message = "cl.adexus.isl.spm.sdaat.susesows.mensaje"
			flash.args = []
			flash.default = next?.mensaje
			redirect(action: next.action)
		}else{
			log.info("Redireccionando a ${next.action}")
			flash.mensajes = "Debes adjuntar DIAT en formato PDF para continuar"
			redirect (action: next.action)
		}
	}

	/**
	 * Formulario
	 * (doc adcional)
	 * [Agregar] -->dp06
	 * [Siguiente] --> cu05
	 */
	def dp05(){
		flash.mensajes = null
		log.info("Ejecutando accion dp05")
		log.debug("Recuperando variable de sesion para denuncia")
		def sdaat = SDAAT.findById(session['sdaat'])
		log.debug("El valor de la variable corresponde a : $sdaat")
		
		log.debug("Verificando si existe documentacion adicional")
		def docAdicionales = DocumentacionAdicional.findAllByDenunciaAT(sdaat?.diat)
		if(!docAdicionales){
			flash.mensajes = "Debe existir al menos una DIAT firmada en la documentacion adicional para continuar el flujo"
			render(view: 'dp06', model: [docAdicionales: docAdicionales, sdaat: sdaat])
		}else{
			def model = [ 'sdaat': sdaat ]
		}
		log.info("Accion dp05 finalizada")
		
		
		
		
		
	}

	/**
	 * Formulario
	 *  - descripcionDocumento
	 *  - fileAdicional
	 * [Subir documentaci�n adicional] --> uploadDocumentoAdicional()
	 * [Siguiente] --> dp05
	 */
	def dp06(){
		def sdaat = SDAAT.findById(session['sdaat'])

		def docAdicionales = DocumentacionAdicional.findAllByDenunciaAT(sdaat?.diat)
		render(view: 'dp06', model: [docAdicionales: docAdicionales, sdaat: sdaat])
	}

	/**
	 * Elimina el documento adicional indicado en link
	 */
	def deleteDocumentoAdicional() {
		def sdaat = SDAAT.findById(session['sdaat'])

		def result = documentacionService.delete(params.id)

		if (result.status == 0) {
			flash.mensajes = "El archivo se ha borrado exitosamente."
		} else {
			flash.mensajes = "Hubo un error tratando de borrar el archivo: " + result.mensaje
		}

		def docAdicionales = DocumentacionAdicional.findAllByDenunciaAT(sdaat?.diat)
		render(view: 'dp06', model: [docAdicionales: docAdicionales, sdaat: sdaat])
	}

	/**
	 * Procesa el archivo de documento adicional
	 */
	def uploadDocumentoAdicional() {
		def sdaat = SDAAT.findById(session['sdaat'])

		def descripcion = params.descripcionDocumento.trim()
		if (descripcion == "") {
			flash.mensajes = "Debe ingresar una descripci&oacute;n al archivo"
		} else {
			def f = request.getFile('fileAdicional')
			def result = documentacionService.upload(session, descripcion, 'DIAT', f)
			if (result.status == 0) {
				flash.mensajes = 'El archivo se ha subido exitosamente'
			} else {
				flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
			}
		}

		def docAdicionales = DocumentacionAdicional.findAllByDenunciaAT(sdaat?.diat)
		render(view: 'dp06', model: [docAdicionales: docAdicionales, sdaat: sdaat])
	}

	/**
	 * Redirecciona a la OPA
	 */
	def cu05(){
		forward ([controller: 'SDAAT_opa', action:'r01'])
	}
}
