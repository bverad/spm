package cl.adexus.isl.spm

import grails.converters.JSON
import org.apache.jasper.compiler.Node.ParamsAction;
import org.apache.shiro.SecurityUtils
import javax.servlet.ServletOutputStream
import cl.adexus.helpers.FechaHoraHelper;


class SolAnteAdicController {

	def DocumentacionService
	def CalOrigenService
	def SolAnteAdicService
	def pass='1234'
	
	
	def index() {
		redirect(action: 'dp01')
	}
	
	/**
	 * DP01 :: ingresar SOLICITUD DE ANTECEDENTES
	 * 
	 */
	def dp01(){
		log.info("Ejecutando action dp01")
		log.info("Datos recibidos : $params")
		
		if (flash.model) { 
			params.siniestroId = flash.model.siniestroId
			params.backTo = flash.model.backTo
			params.backToController = flash.model.backToController
			params.volverSeguimiento = flash.model.volverSeguimiento
		}
		
		//Busca los tipos de antecedentes especificos
		def tipoAntecedentes
		log.info "Valor volverSeguimiento : ${params?.volverSeguimiento}"
		if (params?.volverSeguimiento)
			tipoAntecedentes = TipoAntecedente.findAllByCodigoInList(['3'])
		else
			tipoAntecedentes = TipoAntecedente.findAllByCodigoInList(['1', '2'])
		
		//Datos para la vista
		def siniestro   = Siniestro.findById(params.siniestroId)	
		def regionResponsable = Region.listOrderByDescripcion()
		def anteAdicionales = AntecedenteAdicional.findAllBySiniestro(siniestro)
		
		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro?.creadoEl)
		
		//Model
		def model = [
			siniestro:  		siniestro,
			diasR:				diasR,
			solicitud:			params?.solicitud,
			tipoAntecedentes:	tipoAntecedentes,
			regionResponsable:	regionResponsable,
			tResp:				params?.regionResponsable,
			tAnte:				params?.tipoAntecedente,
			anteAdicionales:	anteAdicionales,
			recaOrigen:			params?.recaOrigen,
			origen:				params?.origen,
			volverSeguimiento: params.volverSeguimiento,
			volverHistorial: params?.volverHistorial,
			verDetalle: params.verDetalle,
			origen: params.origen,
			cesarODA: params.cesarODA,
		]
		
		if (flash.get("model")) {
			// Si venimos de una redireccion por error
			model += flash.get("model")
		}
		
		log.info("model : $model")
		
		render(view: 'dp01', model: model)
	}
	
	/**
	 * PostDp01 :: envia solicitud de antecedentes
	 *
	 */
	def postDp01(){
		log.info("postDp01:"+params)
		
		def siniestroId = params.siniestroId
		def taskId = params.taskId
		def d = SolAnteAdicService.postDp01(params)
		
		if(d.error == 0){
			//inicia la tarea
			def user = SecurityUtils.subject?.principal
			def r1 = SolAnteAdicService.startsolicitudAntecedentesAdicionales(user, pass, d.data);	
			// Lo mandamos de vuelta 
			flash.put('model', d.model)
			redirect (action:d.next.action, controller:d.next.controller, params:d.model)
		}else{
			switch (d.error) {
				case 1:
					flash.mensajes = 'Debe escojer un tipo de antecendente'
					params.put('model', d.model)
					forward (action: d.get('next').action, params: params)
				break
				case 2:
					flash.mensajes = 'Debe elegir la región del responsable'
					params.put('model', d.model)
					forward (action: d.get('next').action, params: params)
				break
				case 3:
					flash.mensajes = 'Debe ingresar su solicitud'
					params.put('model', d.model)
					forward (action: d.get('next').action, params: params)
				break
			}		
		}
	}
	
	/**
	 * DP02 ::  ingresar respuesta PREVENCIÓN/SEGUIMIENTO/NIVEL2
	 *
	 */
	def dp02(){
		log.info("dp02::params:"+params)
		
		if (flash.model) {
			params.siniestroId = flash.model.siniestroId
			params.backTo = flash.model.backTo
			params.backToController = flash.model.backToController
			params.taskId = flash.model.taskId
		}
		
		def taskId = params.taskId
		def antecedentes = AntecedenteAdicional.findById(params.solicitudId)
		def siniestro    = Siniestro.findById(antecedentes?.siniestro?.id)
		def anteAdicionales = AntecedenteAdicional.findAllBySiniestro(siniestro)
		
		def archivosAdjuntos = DocumentacionAdicional.executeQuery(
			"SELECT d " +
			"FROM  	DocumentacionAdicional as d, AntecedenteAdicional as antecedente " +
			"WHERE 	antecedente.id = ? " +
			"AND	d.antecedente.id = antecedente.id", [antecedentes?.id]
		);

		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro.creadoEl)
		
		//Model
		def model = [
			taskId:				taskId,
			siniestro:  		siniestro,
			diasR:				diasR,
			anteAdicionales:	anteAdicionales,
			antecedentes:		antecedentes,
			archivosAdjuntos:	archivosAdjuntos
		]
		
		if (flash.get("model")) {
			// Si venimos de una redireccion por error
			model += flash.get("model")
		}
		
		render(view: 'dp02', model: model)
	}
	
	/**
	 * PostDp02 :: envia respuesta a la solicitud de antecedentes
	 *
	 */
	def postDp02(){
		log.info("postDp02:"+params)
		//Obtiene el siniestroId
		def siniestroId = params.siniestroId
		def taskId = params.taskId
		//Guarda los datos en la bd
		def data=SolAnteAdicService.postDp02(params)
		if(data){
			//inicia la tarea
			def user = SecurityUtils.subject?.principal
			def r2 = SolAnteAdicService.completeDp02(user, pass, taskId, data);	
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])
		}
	}
	
	//redirecciona al inbox
	def dp02_back(){
		redirect ([controller: 'nav', action: 'inbox'])
	}
	
	/**
	 * DP03 :: REVISAR SOLICITUD DE ANTECEDENTES
	 *
	 */
	def dp03(){
		log.info("dp03::params:"+params)
		
		if (flash.model) {
			params.siniestroId 		= flash.model.siniestroId
			params.antecedenteId 	= flash.model.antecedenteId
			params.backTo 			= flash.model.backTo
			params.backToController = flash.model.backToController
			params.taskId 			= flash.model.taskId
		}
		
		def taskId 		 = params.taskId
		def antecedentes = AntecedenteAdicional.findById(params.antecedenteId)
		def siniestro    = Siniestro.findById(antecedentes?.siniestro?.id)
		def tipoAntecedentes = TipoAntecedente.listOrderByCodigo()
		def regionResponsable = Region.listOrderByCodigo()
		
		def archivosAdjuntos = DocumentacionAdicional.executeQuery(
						"SELECT d " +
						"FROM  	DocumentacionAdicional as d, AntecedenteAdicional as antecedente " +
						"WHERE 	antecedente.id = ? " +
						"AND	d.antecedente.id = antecedente.id ", [antecedentes.id]);

		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro.creadoEl)

		def volverAntecedentes = (params?.antecedentesOrigen && !params?.antecedentesOrigen.equals("")) ? "" :"dp01" 
		log.info "Valor volver antecedentes : $volverAntecedentes"
		//Model
		def model = [
			taskId:				taskId,
			siniestro:  		siniestro,
			diasR:				diasR,
			tipoAntecedentes:	tipoAntecedentes,
			regionResponsable:	regionResponsable,
			antecedentes:		antecedentes,
			archivosAdjuntos:	archivosAdjuntos,
			volverSeguimiento: params.volverSeguimiento,
			volverHistorial: params?.volverHistorial,
			verDetalle: params.verDetalle,
			origen: params.origen,
			cesarODA: params.cesarODA,
			recaOrigen: params?.recaOrigen,
			volverAntecedentes: (params?.antecedentesOrigen && !params?.antecedentesOrigen.equals("")) ? "" :"dp01"
		]
		
		if (flash.get("model")) {
			// Si venimos de una redireccion por error
			model += flash.get("model")
		}
		
		render(view: 'dp03', model: model)
	}
	
	//vuelve a flujo anterior
	def go_back(){
		log.info "Ejecutando action go_back"
		log.info "Datos recibidos : $params"
		def r = SolAnteAdicService.goBack(params)
		flash.put('model', r.model)
		forward (action: r.next.action, controller:r.next.controller, params:r.model)
	}

	//Upload de archivos adjuntos
	def adjuntarArchivoAdicional() {
		
		def siniestro    = Siniestro.findById(params.siniestroId)	
		def antecedente = AntecedenteAdicional.findById(params.solicitudId)	
		def f = request.getFile('archivoAdjunto')
		def descripcion = params?.descripcion
		
		//descripcion del archivo, por defecto 
		if (descripcion == "" || descripcion == null){
			descripcion = "AntecedenteAdicional "+params.solicitudId + params.siniestroId
		}
		
		//paso las variables a la session, deberia haber una mejor manera
		//session['siniestro'] = siniestro
		//session['antecedente'] = antecedente
		def map = ['siniestro': siniestro, 'antecedente': antecedente]
		def result = documentacionService.uploadSinSesion(map, descripcion, 'SAA', f)
		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha subido exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
		}
		
		if (flash.get("model")) {
			// Si venimos de una redireccion por error
			model += flash.get("model")
		}
		
		redirect(action: "dp02",params: [respuesta: params.respuesta, solicitudId: params.solicitudId, antecedentes:antecedente, taskId: params.taskId])
	}
	
	//Descarga el documento
	def viewAntecedente(){
		
		def r = DocumentacionService.view(params.antecedenteId)
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
	
	//Elimina el documento
	def deleteAntecedente(){ 
		
		def antecedenteId = params.antecedenteId		
		def result = DocumentacionService.delete(antecedenteId)
		
		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha eliminado exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la eliminación del archivo: ' + result.mensaje
		}
		
		redirect(action: "dp02",params: [respuesta: params.respuesta, solicitudId: params.solicitudId, taskId: params.taskId])
	}
	
}
