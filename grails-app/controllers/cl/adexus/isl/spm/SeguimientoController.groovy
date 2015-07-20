package cl.adexus.isl.spm

import grails.converters.JSON

import javax.servlet.ServletOutputStream

import cl.adexus.helpers.FlashHelper;

class SeguimientoController {

	def SeguimientoService
	def documentacionService

	def index() { redirect ([action: 'dp01']) }

	//Listar Casos para Ingreso
	def dp01(){
		log.debug("dp01. Params:"+params)
		def model = [:]
		if(params.siniestroId != null || params.siniestroId != "" || params.nivelComplejidad != null || params.nivelComplejidad != ""){
			def r = SeguimientoService.dp01(params)
			model += r.model
		}
		def nivelComplejidadList =	[ /*[codigo: "0", descripcion : "Leve"],*/
			[codigo: "1", descripcion : "Menos Grave"]
			, [codigo: "2", descripcion : "Grave"]
			, [codigo: "3", descripcion : "Muy Grave"]]
		model += [nivelComplejidadList: nivelComplejidadList]
		model
	}


	//Realizar Ingreso
	def dp02(){
		log.info("Ejecutando accion dp02 ")
		log.info("Datos recibidos : $params")
		//determina de donde proviene la variable de siniestro
		def errores = []
		
		//validando que el ingreso no se haya creado previamente
		if(params.id){ 
			//verifica si ya se ingreso un registro de seguimiento de tipo ingreso
			log.debug("ID se siniestro init : ${params?.id}")
			def r = SeguimientoService.dp01(params)
			def siniestro = Siniestro.get(params?.id)
			def ingresosList = SeguimientoService.existeIngreso(siniestro)
			
			log.info ("Valor action terminar : ${params?._action_cu02t}")
			//realiza validacion antes de terminar o de ingresar
			def origin = params?._action_cu02t?:params?._action_cu02i
			log.info("Valor origin : $origin")
			if(!origin){
				log.info("La accion no corresponde a terminar, determinando si el registro cuenta con una actividad de seguimiento de tipo ingreso : $ingresosList")
				if(ingresosList){
					log.debug("Ya existe ingreso se seguimiento, redireccionando a accion dp01")
					siniestro = new Siniestro()
					siniestro.errors.reject("relato", "Ya existe un ingreso para el siniestro ${params?.id}")
					errores = siniestro.errors
					log.debug("Lista de errores value : $errores")
					render (view: 'dp01', model:[errores:errores, siniestros:r.model.siniestros,
								volverSeguimiento: params.volverSeguimiento,
								volverHistorial: params?.volverHistorial,
								verDetalle: params.verDetalle,
								origen: params.origen,
								cesarODA: params.cesarODA])
					return
				}
			}else{
				if(params?._action_cu02t){
					siniestro = new Siniestro()
					siniestro.errors.reject("relato", "El seguimiento asociado al siniestro ${params?.id} fue creado con exito")
					errores = siniestro.errors
				}
				
			}

		}else{
			log.info("Debe seleccionar algun siniestro para seguimiento")
			def r = SeguimientoService.dp01(params)
			def siniestro = new Siniestro()
			siniestro.errors.reject("relato", "Debe ingresar algun siniestro para seguimiento")
			errores = siniestro.errors
			log.debug("Lista de errores value : $errores")
			render (view: 'dp01', model:[errores:errores, siniestros:r.model.siniestros,
						volverSeguimiento: params.volverSeguimiento,
						volverHistorial: params?.volverHistorial,
						verDetalle: params.verDetalle,
						origen: params.origen,
						cesarODA: params.cesarODA,
						siniestroId: params.siniestroId,
						nivelComplejidad: params.nivelComplejidad,
						tipoSiniestro: params.tipoSiniestro])
			return
		}
	
		if (params.model) {
			if (params.model.siniestroId){
				params.id = params.model.siniestroId
				log.debug("Parametro corresponde a siniestroId : ${params?.id}")
			}else{
				params.id = params.model.siniestro.id
				log.debug("Parametro corresponde a siniestro.id : ${params?.id}")
			}
		}
		
		//ejecutando servicios
		def r = SeguimientoService.dp02(params)
		def model = r.model
		if (params.model) {
			// Recuperar documentos
			model += [adjuntos: DocumentacionAdicional.findAllByActividadSeguimiento(params.model.actividadSeguimiento)]
			model += params.model
		}

		model += [errores:errores, siniestros:r.model.siniestros,
						volverSeguimiento: params.volverSeguimiento,
						volverHistorial: params?.volverHistorial,
						verDetalle: params.verDetalle,
						origen: params.origen,
						cesarODA: params.cesarODA,
						siniestroId: params.siniestroId,
						nivelComplejidad: params.nivelComplejidad,
						tipoSiniestro: params.tipoSiniestro]
		
		model
	}

	/**
	 * Procesa el archivo adjunto
	 */
	def uploadDocSeguimiento() {
		log.info "uploadDocActividadSeguimiento. Params: ${params}"
		def actividadSeguimiento = ActividadSeguimiento.get(params.actividadSeguimientoId)
		//session.actividadSeguimiento = actividadSeguimiento
		def map = ['actividadSeguimiento': actividadSeguimiento]
		def f = request.getFile('fileDenuncia')
		def result = documentacionService.uploadSinSesion(map, f.fileItem.name, 'ACSE', f)
		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha subido exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
		}
		params.put('model', [actividadSeguimiento: actividadSeguimiento, siniestroId: params.siniestroId, flag:params?.flag])
		forward action: "dp02", params: params
	}

	def postDp02Eliminar() {
		log.info "postDp05Eliminar. Params: ${params}"
		def result = documentacionService.delete(params.docAdicionalId)
		def actividadSeguimiento = ActividadSeguimiento.get(params.actividadSeguimientoId)
		params.put('model', [actividadSeguimiento: actividadSeguimiento, siniestroId: params.siniestroId])
		flash.mensajes = 'El archivo se ha eliminado exitosamente'
		forward action: "dp02", params: params
	}

	def cu02p(){
		params['resolucion'] = 'Posponer'
		forward action: "postDp02", params: params
	}

	def cu02i(){
		params['resolucion'] = 'Ingresar'
		forward action: "postDp02", params: params
	}

	def cu02t() {
		params['resolucion'] = 'Terminar'
		forward action: "postDp02", params: params
	}

	/**
	 * 
	 * @return
	 */
	def postDp02(){
		log.info("Ejecutando accion postDp02")
		log.info("Datos recibidos : $params")
		flash.mensajes = null
		flash.errores = null
		def errores = []
		if(params.siniestroId){
			//verifica si ya se ingreso un registro de seguimiento de tipo ingreso
			log.debug("ID se siniestro init : ${params?.siniestroId}")
			def r = SeguimientoService.dp01(params)
			def siniestro = Siniestro.get(params?.siniestroId)
			def ingresosList = SeguimientoService.existeIngreso(siniestro)
			log.info("Determinando si el registro cuenta con una actividad de seguimiento de tipo ingreso : $ingresosList")
			
			if(ingresosList && !params?.resolucion.equals("Terminar")){
				log.debug("Ya existe ingreso se seguimiento, redireccionando a accion dp01")
				siniestro = new Siniestro()
				siniestro.errors.reject("relato", "Ya existe un ingreso para el siniestro ${params?.siniestroId}")
				errores = siniestro.errors
				log.debug("Lista de errores value : $errores")
				render (view: 'dp01', model:[errores:errores, siniestros:r.model.siniestros])
				return
			}
		}

		log.info("Ejecutando service postDp02")
		def r = SeguimientoService.postDp02(params)
		
		params.put('model', r.model)
		// Lo mandamos al inicio si pulsa terminar o posponer de lo contrario lo dejamos en la misma pantalla
		def next = r.next
		next.params = params
		forward (next)
	}

	//Buscar Caso para Realizar Seguimiento
	def dp03(){
		log.info("dp03. Params: "+params)
		def model = [:]
		//if(params.siniestroId != null || params.run != null){
		def r = SeguimientoService.dp03(params)
		model += r.model
		//}
		if (params.model)
			model += params.model
		model
	}

	//Revisar Historial
	def dp04(){
		log.info("dp04. Params:"+params)
		flash.mensajes = null

		if (flash.model) {
			params.siniestroId = flash.model.siniestroId
		}else if (params.model) {
			params.siniestroId = params.model.siniestroId
		}

		def r = SeguimientoService.dp04(params)
		def model = r.model
		if (params.model)
			model += params.model
		model
	}

	def postDp04CambiarNivel() {
		log.info("postDp04CambiarNivel. Params:"+params)
		def r = SeguimientoService.postDp04CambiarNivel(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward (next)
	}

	def postDp04Comentario() {
		log.info("postDp04Comentario. Params:"+params)
		def r = SeguimientoService.postDp04Comentario(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward (next)
	}

	def cu04Alta() {
		log.info "cu04Alta. Params: ${params}"
		def r = SeguimientoService.cu04Alta(params)
		params.put('model', r.model)
		// Se quita action de alta porque genera loop
		//params.remove('_action_cu04Alta')
		def next = r.next
		next.params = params
		log.info "next: ${next}"
		forward (next)
	}

	def postDp04Antecedentes(){
		log.info "postDp04Antecedentes. Params: ${params}"
		def r = SeguimientoService.postDp04Antecedentes(params)
		flash.put('model', r.model)
		redirect (controller:r.next.controller, action:r.next.action, params:r.next.model)
	}

	def postDp04VerSolicitudes(){
		log.info "postDp04VerSolicitudes. Params: ${params}"
		def r = SeguimientoService.postDp04VerSolicitudes(params)
		flash.put('model', r.model)
		redirect (r.next)
	}

	//Registrar Actividad
	/**
	 * Despliega datos iniciales para registro de actividad
	 * @return
	 */
	def dp05(){
		log.info("Ejecutando accion dp05")
		log.info("Datos recibidos : $params")
		if (params.model) { params.siniestroId = params.model.siniestroId }
		def r = SeguimientoService.dp05(params)
		def model = r.model

		model
	}

	/**
	 * Almacena cambios realizados en registro de actividad
	 * @return
	 */
	def postDp05() {
		//comprobar que los archivos adjuntos fueron ingresados
		//verificar si la extension del archivo es valida
		log.info("Ejecutando accion postDp05")
		log.info("Datos recibidos $params")
		log.info("Inicializando mensajes y errores")
		flash.mensajes = null
		flash.errores = null

		def siniestro = Siniestro.get(params.siniestroId);
		def seguimiento = Seguimiento.findByFechaAltaIsNullAndFechaIngresoIsNotNullAndSiniestro(siniestro)
		def tipoActividad = TipoActividadSeguimiento.listOrderByDescripcion()

		def errores = []


		try{
			//obtiene validaciones de creacion de actividad seguimiento
			def result = SeguimientoService.postDp05(params)
			//comienza validaciones de archivos adjuntos
			def fileCount = 0
			def errors = false
			def fileDetail = []

			//se crea instancia de persona para control de errores
			def actividadSeguimiento = result.actividadSeguimiento
			def existsActividadSeguimiento = result.existsActividadSeguimiento
			if(params?.fileLength){
				fileCount = params?.fileLength.toInteger()
				for (int i = 1; i < fileCount; i++) {
					def file = request.getFile("file${i}")
					if(!file?.fileItem?.get()){
						actividadSeguimiento.errors.reject(
								'seguimiento.actividad.empty.file',
								["${i}"] as Object[],
								'Debe adjuntar archivo para registro numero [{0}]')
						errors = true
					}else{
						def index = file?.fileItem?.name.lastIndexOf(".")
						if (index == -1) {
							actividadSeguimiento.errors.reject(
									'seguimiento.actividad.without.extension',
									["${i}"] as Object[],
									'Archivo sin extension para registro numero [{0}]')
							errors = true
						} else {
							def fileName = file?.fileItem?.name
							def extension = fileName.substring(index + 1)
							if(!extension.equals("pdf")){
								actividadSeguimiento.errors.reject(
										'seguimiento.actividad.invalid.extension',
										["${i}", fileName] as Object[],
										'Extension invalida para registro numero [{0}], archivo : [{1}], solo se admite formato PDF')
								errors = true
							}
						}
					}
					//agregando detalle
					fileDetail << [field:"file${i}", errors:errors, name:file?.fileItem?.name]
				}
			}

			log.info("Verificando si se registraron errores")
			if(actividadSeguimiento.hasErrors()){
				log.info("Los errores encontrados son los siguientes:")
				errores = actividadSeguimiento.errors
				actividadSeguimiento.errors.each{error->
					log.info("--> $error")
				}
			}else{
				log.info("Procesando archivos cargados")
				fileDetail.each{f->
					def file = request.getFile(f.field)
					log.debug("Procesando archivo : ${file?.fileItem?.name}")
					def r = SeguimientoService.uploadDocActividadSeguimiento(actividadSeguimiento, file)
				}
				log.info("Procesamiento de archivos finalizado")

				log.info("Almacenando actividad de seguimiento")
				actividadSeguimiento.save()
				flash.mensajes = "La actividad ${actividadSeguimiento.id} fue ${existsActividadSeguimiento ? 'actualizada' : 'creada'} con exito"
				log.info("La actividad ${actividadSeguimiento.id} fue ${existsActividadSeguimiento ? 'actualizada' : 'creada'} con exito")
			}

			log.info("Finalizando accion postdp05")
			def model = [fileCount:fileCount
							,actividadSeguimiento:actividadSeguimiento
							, fileDetail:fileDetail
							, errores:errores
							, siniestro:siniestro
							, seguimiento:seguimiento
							, tipoActividad:tipoActividad
							, volverSeguimiento: params.volverSeguimiento
							, volverHistorial: params?.volverHistorial
							, verDetalle: params.verDetalle
							, origen: params.origen
							, cesarODA: params.cesarODA]
			render(view:"dp05", model:model)
		}catch(e){
			log.error("Error no controlado : ${e.message}")
			def actividadSeguimiento = new ActividadSeguimiento()
			actividadSeguimiento.errors.reject("seguimiento.error.nocontrolled",
					[] as Object[],
					"Surgio un error no controlado : ${e.message}")
			errores = actividadSeguimiento.errors
			e.printStackTrace()
			def model = [fileCount:0
				,actividadSeguimiento:actividadSeguimiento
				, fileDetail:[]
				, errores:errores
				, siniestro:siniestro
				, seguimiento:seguimiento
				, tipoActividad:tipoActividad
				, volverSeguimiento: params.volverSeguimiento
				, volverHistorial: params?.volverHistorial
				, verDetalle: params.verDetalle
				, origen: params.origen
				, cesarODA: params.cesarODA]
			
			render(view:"dp05", model:model)
		}
	}

	/**
	 * Elimina un archivo del registro y del repositorio alfresco asociado a la actividad de seguimiento en contexto
	 * @return
	 */
	def postDp05Eliminar() {
		log.info "postDp05Eliminar. Params: ${params}"
		log.debug("Eliminando archivo adjunto de repositorio")
		def result = documentacionService.delete(params.docAdicionalId)
		log.debug("Archivo adjunto de repositorio eliminado con exito")

		def actividadSeguimiento = ActividadSeguimiento.get(params.actividadSeguimientoId)

		params.put('model', [actividadSeguimiento: actividadSeguimiento, siniestroId: params.siniestroId])
		flash.mensajes = 'El archivo se ha eliminado exitosamente'
		forward action: 'dp05', params: params
	}

	/**
	 * 
	 * @return
	 */
	def viewDoc(){
		log.info "viewDoc. Params: ${params}"
		def r=documentacionService.view(params.docAdicionalId);
		def doc=r.doc

		response.setContentLength((int)doc.contentLength);
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

	//Revisar Actividad
	def dp06(){
		log.info("dp06. Params:"+params)
		def r = SeguimientoService.dp06(params)
		r.model
	}

	//Resumen Reposo y Alta
	def dp07(){
		log.info("dp07. Params:"+params)
		if (params.model) { params.siniestroId = params.model.siniestroId }
		def r = SeguimientoService.dp07(params)
		r.model
	}

	//Ver RELA
	def dp08(){
		log.info("dp08. Params:"+params)
		def r = SeguimientoService.dp08(params)
		r.model
	}

	def postDp08() {
		log.info("postDp08. Params:"+params)
		def r = SeguimientoService.postDp08(params)
		params.put('model', r.model)
		forward (action: r.next.action, params: params)
	}

	//Ver ALLA
	def dp09(){
		log.info("dp09. Params:"+params)
		def r = SeguimientoService.dp09(params)
		r.model
	}

	def postDp09() {
		log.info("postDp09. Params:"+params)
		def r = SeguimientoService.postDp09(params)
		params.put('model', r.model)
		forward (action: r.next.action, params: params)
	}

	//Ver ALME
	def dp10(){
		log.info("dp10. Params:"+params)
		def r = SeguimientoService.dp10(params)
		r.model
	}

	def postDp10() {
		log.info("postDp10. Params:"+params)
		def r = SeguimientoService.postDp10(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward (next)
	}

	//Buscar Caso para Revisar ODAs
	def dp11(){
		log.info("dp11. Params: "+params)
		//es necesario indicar el punto exacto de referencia a la siguiente pantalla porque utiliza javascript nativo
		def model = [:]
		//if(params.siniestroId != null || params.run != null || params.rut != null){
			def r = SeguimientoService.dp11(params)
			model += r.model
		//}
		model
	}

	//Revisar ODAs del Caso
	def dp12(){
		log.info("dp12. Params: " + params)

		if (params.model) {
			params.siniestroId = params?.model?.siniestroId
			params.volverSeguimiento = params?.model?.volverSeguimiento
		}
		def model = [:]
		def actionCrearODA = "dp13"
		if(!params.action.equals("index"))
			actionCrearODA = "../dp13"
		params.actionCrearODA = actionCrearODA		
		def r = SeguimientoService.dp12(params)
		model += r.model
		if (params.model) {
			model += params.model
		}
		model
	}

	def postDp12Nivel() {
		log.info("postDp12Nivel. Params:"+params)
		def r = SeguimientoService.postDp12CambiarNivel(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward (next)
	}

	def postDp12Cesar() {
		log.info("Ejecutando accion postDp12Cesar")
		log.info("Datos recibidos: $params")
		def r = SeguimientoService.postDp12Cesar(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward (next)
	}

	//Emitir ODA
	def dp13(){
		log.info("dp13. Params:"+params)
		if (params.model) {
			params.siniestroId = params.model.siniestroId
			params.volverSeguimiento = params.model.volverSeguimiento
		}

		def r = SeguimientoService.dp13(params)
		def model = r.model
		if (params.model)
			model += params.model
			
		model
	}

	def postDp13() {
		log.info("postDp13. Params:"+params)
		def r = SeguimientoService.postDp13(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward (next)
	}

	//Visualizar ODA
	def dp14(){
		log.info("dp14. Params:"+params)
		def r = SeguimientoService.dp14(params)
		def model = r.model
		if (params.model)
			model += params.model
		model
	}

	//Imprimir ODA
	def genPdf(){ //Genera el PDF del informe de la ODA
		log.info("Imprimir PDF ODA. Params:"+params)
		def b = SeguimientoService.odaGenPdf(params)

		if(b!=null){
			response.setContentType("application/pdf")
			response.setHeader("Content-disposition", "attachment; filename=ODA_" +
					params.odaId.toString() + ".pdf")
			response.setContentLength(b.length)
			response.getOutputStream().write(b)
		} else {
			//No se genero el PDF
		}
	}


	//Carga de Reposos y Altas
	def dp15(){
		log.info "Ejecutando action dp15"
		log.info "Datos recibidos : $params"
		def tipoDocumento = []
		def tipoDocumentoSeleccionado
		def errorMessage
		def listaRegistros
		def result
		def totalRegistros = 0

		log.info("*** dp15 ***")

		tipoDocumento[0] = [codigo: 'ALME', descripcion: 'ALME']
		tipoDocumento[1] = [codigo: 'ALLA', descripcion: 'ALLA']
		tipoDocumento[2] = [codigo: 'RELA', descripcion: 'RELA']

		if(params.get('model')) {
			if(params.get('model').error){
				log.info("dp15: error recibido")
				errorMessage = params.get('model').error
			}
			if(params.get('model').listaRegistros){
				log.info("dp15: lista de registros recibida")
				listaRegistros = params.get('model').listaRegistros
			}
			if(params.get('model').tipoDocumento){
				log.info("dp15: tipo documento recibida")
				tipoDocumentoSeleccionado = params.get('model').tipoDocumento
			}
			if(params.get('model').totalRegistros){
				log.info("dp15: total registros recibida")
				totalRegistros = params.get('model').totalRegistros
			}
		}

		def model = [ tipoDocumento: tipoDocumento, errorMessage: errorMessage, listaRegistros: listaRegistros,
			tipoDocumentoSeleccionado: tipoDocumentoSeleccionado, totalRegistros: totalRegistros												
												, volverSeguimiento: params.volverSeguimiento
												, volverHistorial: params?.volverHistorial
												, verDetalle: params.verDetalle
												, origen: params.origen
												, cesarODA: params.cesarODA
												, verDetalle: params.verDetalle ]

		model
	}

	def postDp15() {

		def tipoDocumentoSeleccionado
		def result = [:]
		def model
		def listaRegistros = []
		def totalRegistros = 0
		def criticalError = false

		log.info("*** postDp15 ***")

		tipoDocumentoSeleccionado = params?.tipoDocumento
		log.info("tipoDocumento: " + tipoDocumentoSeleccionado)

		result = SeguimientoService.validExcellDocument(request, params)

		if (!result["result"]) {
			log.info("Error obtenido: " + result["error"])
			model = [ 'error': result["error"]
					, volverSeguimiento: params.volverSeguimiento
					, volverHistorial: params?.volverHistorial
					, verDetalle: params.verDetalle
					, origen: params.origen
					, cesarODA: params.cesarODA
					, verDetalle: params.verDetalle]
			params.put('model', model)
			forward(action: "dp15")
			return
		}

		listaRegistros = SeguimientoService.getListaRegistrosDocumentosExcell(request, params)
		log.info("size result listaRegistros : " + listaRegistros.size())
		log.info("listaRegistros: " + listaRegistros)

		if (listaRegistros.size() == 1) {
			def rslt = listaRegistros[0].getAt("detalle")
			log.info("rslt: " + rslt)
			if (rslt != "") {
				criticalError = true
			}
		} else {
			for (def i = 0; i < listaRegistros.size(); i++) {
				if (listaRegistros[i].getAt("detalle") != "") {
					log.info "lista de registros sin detalle"
					criticalError = true
				}
			}
		}

		if (criticalError) {
			log.info("CRITICAL ERROR :: postDp15 :: totalRegistros: " + totalRegistros)
			model = [ 'listaRegistros': listaRegistros, 'tipoDocumento': tipoDocumentoSeleccionado, 'totalRegistros': totalRegistros					
					, volverSeguimiento: params.volverSeguimiento
					, volverHistorial: params?.volverHistorial
					, verDetalle: params.verDetalle
					, origen: params.origen
					, cesarODA: params.cesarODA
					, verDetalle: params.verDetalle ]
			params.put('model', model)

			forward(action: "dp15")
			return
		}

		if (tipoDocumentoSeleccionado == "ALME" && SeguimientoService.validaDatosAlme(listaRegistros)) {
			SeguimientoService.grabaDatosAlme(listaRegistros)
			totalRegistros = listaRegistros.size()
		}

		if (tipoDocumentoSeleccionado == "ALLA" && SeguimientoService.validaDatosAlla(listaRegistros)) {
			SeguimientoService.grabaDatosAlla(listaRegistros)
			totalRegistros = listaRegistros.size()
		}

		if (tipoDocumentoSeleccionado == "RELA" && SeguimientoService.validaDatosRela(listaRegistros)) {
			SeguimientoService.grabaDatosRela(listaRegistros)
			totalRegistros = listaRegistros.size()
		}

		log.info("postDp15: totalRegistros: " + totalRegistros)
		model = [ 'listaRegistros': listaRegistros, 'tipoDocumento': tipoDocumentoSeleccionado, 'totalRegistros': totalRegistros					
					, volverSeguimiento: params.volverSeguimiento
					, volverHistorial: params?.volverHistorial
					, verDetalle: params.verDetalle
					, origen: params.origen
					, cesarODA: params.cesarODA
					, verDetalle: params.verDetalle ]
		params.put('model', model)

		forward(action: "dp15", params: params)

	}

	/******** Funciones AJAX ********/
	def buscarPrestacionJSON () {
		log.info "buscarPrestacionJSON: ${params}"
		def datos
		if (params.centroSalud != "" && params.codigo != "")
			datos = SeguimientoService.buscarPrestacionJSON(params.centroSalud.toLong(), params.codigo)
		JSON.use("deep") { render datos as JSON }
	}

	def prestadorPorRegionJSON() {
		log.info("prestadorPorRegionJSON: ${params}")
		def prestadores = SeguimientoService.prestadorPorRegionJSON(params.regionId)
		JSON.use("deep") { render prestadores as JSON }

	}

	def centroSaludPorPrestadorJSON(){
		log.info("centroSaludPorPrestadorJSON: ${params}")
		def prestadorId = params.prestadorId.toLong()
		def regionId	= params.regionId
		def fechaHoy 	= new Date()
		def cs = SeguimientoService.centroSaludPorPrestadorJSON(prestadorId, regionId, fechaHoy)
		log.info("Centros de salud por prestador : $cs")
		JSON.use("deep"){ render cs as JSON }
	}

	def buscarPrestadorJSON() {
		log.info("buscarPrestadorJSON: " + params.prestadorId)
		def prestador = Prestador.get(params.prestadorId)
		JSON.use("deep") { render prestador as JSON }
	}
}
