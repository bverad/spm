package cl.adexus.isl.spm

import grails.converters.JSON
import org.apache.shiro.SecurityUtils
import javax.servlet.ServletOutputStream
import org.apache.jasper.compiler.Node.ParamsAction
import cl.adexus.helpers.FormatosHelper
import cl.adexus.helpers.FechaHoraHelper

class BIS_revisionController {
	
	def BISRevisionService
	def DocumentacionService
	
	def pass = '1234'
	def user = SecurityUtils.subject?.principal
	
	
	def index() { redirect ([action: 'dp01'])}
	
	/**
	 * Analisis de solicitud
	 * @return
	 */
	def dp01(){
		log.info "77BIS revisión - DP01 [Analisis de solicitud] :: Parametros "+params
		def comentario
		def montoAprobado
		
		if (params.model) {
			params.bisId = params.model.bisId
			params.taskId = params.model.taskId
			montoAprobado = params.model.montoAprobado
			comentario = params.model.comentario
		}
		
		//Para la vista
		def bisId = params?.bisId
		def bis = Bis.findById(bisId)
		def model
		
		//Buscar toda la documentacion adicional
		def docs = DocumentacionAdicional.executeQuery(
						"SELECT	d " +
						"FROM  	DocumentacionAdicional as d, Bis as bis " +
						"WHERE 	bis.id = ? " +
						"AND	d.documentacion.id = bis.id ", [bis.id]);
		
		//Buscar toda la documentacion adicional, en todas las DIAT asociadas al siniestro
		def dictamen = DocumentacionAdicional.executeQuery(
						"SELECT	d " +
						"FROM  	DocumentacionAdicional as d, Bis as bis " +
						"WHERE 	bis.id = ? " +
						"AND	d.dictamen.id = bis.id ", [bis.id]);
					
		//Model
		model = [	bis: 			bis,
					taskId:			params?.taskId,
					dictamen:		dictamen[0]?.id,
					docs:			docs,
					montoAprobado:	montoAprobado,
					comentario:		comentario	]
		
		//Si venimos de una redireccion por error
		if (params.get("model")) {			
			model += params.get("model")
		}
		
		render(view: 'dp01', model: model)
		
	}
	
	/**
	 * Procesa los datos, guarda y decide donde ira a continuación, dependiendo del monto aprobado
	 * @return
	 */
	def postDp01(){
		log.info "77BIS revisión - POSTDP01 :: Parametros "+params
		def r = BISRevisionService.postDp01(params)
		
		//Llamada al BPM
		if (r.error == 0){		
			log.info "resultado postDP01-> "+r	
			def b = BISRevisionService.completeDp01(user, pass,r.taskId ,r.rechazo)
			flash.mensajes = null
		}else{
			flash.mensajes = 'El monto aprobado NO puede mayor al monto solicitado!'
		}
				
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}
	
	/**
	 * Informar al solicitante el rechazo total (monto aprobado = 0)
	 * @return
	 */
	def dp02(){
		log.info "77BIS revisión - DP02 [Informar al solicitante el rechazo total] :: Parametros "+params
		//Para la vista
		def bis = Bis.findById(params.bisId)
		
		//Model
		def model = [
				bis:	bis
		]

		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}
		render(view: 'dp02', model: model)
		
	}
	
	/**
	 * Procesa los datos, guarda e imprime, termina la tarea si imprime?
	 * @return
	 */
	def postDp02(){
		log.info "77BIS revisión - POSTDP02 :: Parametros "+params
		def r = BISRevisionService.postDp02(params)
		
		if(params?._action_postDp02 != "Guardar"){
			//Llamada al BPM
			log.info "Terminar la tarea n°"+params?.taskId+" de 77Bis"
			//TODO: implementar la impresion del pdf y el envio de correo
			def b = BISRevisionService.completeDp02(user, pass, params?.taskId ,r.bis)
		}
		
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}
	
	/**
	 * Informar al solicitante (monto mayor a 0)
	 * @return
	 */
	def dp03(){
		log.info "77BIS revisión - DP03 [Informar al solicitante] :: Parametros "+params
		//Para la vista
		def bis = Bis.findById(params.bisId)
		
		//Model
		def model = [
				bis:	bis
		]

		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}
		render(view: 'dp03', model: model)
		
	}
	
	/**
	 * Informar al solicitante el analisis de pago
	 * @return
	 */
	def postDp03(){
		log.info "77BIS revisión - POSTDP03 :: Parametros "+params
		def r = BISRevisionService.postDp03(params)
		
		if(params?._action_postDp02 != "Guardar"){
			//Llamada al BPM
			log.info "Terminar la tarea n°"+params?.taskId+" de 77Bis"
			//TODO: implementar la impresion del pdf y el envio de correo
			def b = BISRevisionService.completeDp03(user, pass,r.taskId ,r.bis)
		}
		
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}
	
	/**
	 * Plantilla de cálculo de interés y Generar informe de pago
	 * @return
	 */
	def dp04(){
		log.info "77BIS revisión - DP04 [Plantilla de cálculo de interés]:: Parametros "+params
		
		//Para la vista
		def bis = Bis.findById(params?.bisId)	
		def dias = FechaHoraHelper.diffDates(bis?.fechaRecepcion)
		log.info "DIFERENCIA DE DIAS ->"+dias

		//Buscar toda la documentacion adicional
		def docs = DocumentacionAdicional.executeQuery(
						"SELECT	d " +
						"FROM  	DocumentacionAdicional as d, Bis as bis " +
						"WHERE 	bis.id = ? " +
						"AND	d.documentacion.id = bis.id ", [bis.id]);
					
		//Buscar toda la documentacion adicional
		def dictamen = DocumentacionAdicional.executeQuery(
						"SELECT	d " +
						"FROM  	DocumentacionAdicional as d, Bis as bis " +
						"WHERE 	bis.id = ? " +
						"AND	d.dictamen.id = bis.id ", [bis.id]);
					
		//Model
		def model = [
						bis: 		bis,
						dias:		dias,
						docs:		docs,
						dictamen:	dictamen
					]
					
		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}
		render(view: 'dp04', model: model)
		
	}
	
	/**
	 * Informar al solicitante el analisis de pago
	 * @return
	 */
	def postDp04(){
		log.info "77BIS revisión - POSTDP04 :: Parametros "+params
		def r = BISRevisionService.postDp04(params)
		
		if (r.taskId){
			//Llamada al BPM
			def b = BISRevisionService.completeDp04(user, pass,r.taskId ,r.bis)
		}
		
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}
	
	def viewDoc(){
		log.info 'SE RESCATA EL DOC ID ->'+params.docId
		
		def r = DocumentacionService.view(params.docId)
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

	
}
