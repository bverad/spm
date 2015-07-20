package cl.adexus.isl.spm

import javax.servlet.ServletOutputStream

class ReingresoController {

	def ReingresoService
	def documentacionService
	
    def index() { }
	
	/**
	 * Ingreso de RUN Trabajador
	 * [Siguiente] cu01
	 * @return
	 */
	def dp01() {
		log.info "dp01 - model: ${flash.model}"
		def model = params.model
		model
	}
	
	def cu01() {
		log.info "cu01 - params: ${params}"
		// Buscar Trabajador
		def r = ReingresoService.cu01(params)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}

	/**
	 * Selección de Siniestro
	 * [Terminar] cu02t
	 * [Siguiente] cu02s
	 * @return
	 */
	def dp02() {
		log.info "dp02 - model: ${flash.model} - params: ${params}"
		def r = ReingresoService.dp02(params)
		def model = r.model
		if (params.model) {
			model += params.model
		}
		model
	}

	def cu02t() {
		log.info "cu02t - params: ${params}"
		redirect controller: 'nav', action: 'area4'
	}

	def cu02s() {
		log.info "cu02s - params: ${params}"
		def r = ReingresoService.cu02s(params)
		params.put('model', r.get('model'))
		log.info "cu02s r.next: ${r.next}"
		def next = r.next
		log.info "cu02s asdasd params: ${params} - next.params: ${next.params}"
		if (next.params)
			next.params += params
		else
			next.params = params
		forward (next)
	}

	/**
	 * Solicitud de Re-Ingreso
	 * [Upload] cu03u
	 * [Eliminar] cu03d
	 * [Siguiente] cu03s
	 * @return
	 */
	def dp03() {
		log.info "dp03 - model: ${flash.model} - params: ${params}"
		params.model
	}

	def cu03u() {
		log.info "cu03u - params: ${params}"
		def reingreso = Reingreso.get(params.reingresoId)
		def siniestro = Siniestro.get(params.siniestroId)
//		session.reingreso = reingreso
		def map = ['reingreso': reingreso]
		def f = request.getFile('file')
		def result = documentacionService.uploadSinSesion(map, f.fileItem.name, 'REIN', f)
		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha subido exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
		}
		def adjuntos = DocumentacionAdicional.findAllByReingreso(reingreso)
		params.put('model', [reingreso: reingreso, siniestro: siniestro, adjuntos: adjuntos])
		forward (action: "dp03", params: params)
	}

	def cu03d() {
		log.info "cu03d - params: ${params}"
		def result = documentacionService.delete(params.docAdicionalId)
		def reingreso = Reingreso.get(params.reingresoId)
		def siniestro = Siniestro.get(params.siniestroId)
		def adjuntos = DocumentacionAdicional.findAllByReingreso(reingreso)
		params.put('model', [reingreso: reingreso, siniestro: siniestro, adjuntos: adjuntos])
		flash.mensajes = "El archivo se ha eliminado exitosamente"
		forward action: 'dp03', params: params
	}

	def cu03s() {
		log.info "cu03s - params: ${params}"
		params['resolucion'] = 'Siguiente'
		forward action: "postDp03", params: params
	}

	def cu03f() {
		log.info "cu03f - params: ${params}"
		params['resolucion'] = 'Finalizar'
		forward action: "postDp03", params: params
	}
	
	def postDp03() {
		log.info "postDp03 - params: ${params}"
		def r = ReingresoService.postDp03(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		flash.mensajes = null
		// Lo mandamos al inicio si pulsa terminar o posponer de lo contrario lo dejamos en la misma pantalla
		forward (next)
	}

	def dp04() {
		log.info "dp04 - params: ${params}"
		def r = ReingresoService.dp04(params)
		def model = r.model
		if (params.model) {
			model += params.model
		}
		model
	}

	def cu04s() {
		log.info "cu04s - params: ${params}"
		def r = ReingresoService.cu04s(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward (next)
	}
	
	def dp05() {
		log.info "dp05 - model: ${flash.model} - params: ${params}"
		def nivel = [[codigo: "1", descripcion : "Nivel 1"]
					,[codigo: "2", descripcion : "Nivel 2"]
					,[codigo: "3", descripcion : "Nivel 3"]
					,[codigo: "4", descripcion : "Nivel 4"]]
		
		def model = params.model
		def reingreso = model.reingreso
		if (reingreso!=null)
		{
			def adjuntos = DocumentacionAdicional.findAllByReingreso(reingreso)
			model.putAt("adjuntos", adjuntos)
		}		
		if (!params?.model?.nivel)
			model.putAt("nivel", nivel)
		
		model 
		
	}

	def cu05s() {
		log.info "cu05s - params: ${params}"
		def r = ReingresoService.cu05s(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		// Lo mandamos al inicio si pulsa terminar o posponer de lo contrario lo dejamos en la misma pantalla
		forward (next)
	}

	def cu05v() {
		log.info "cu05v - params: ${params}"
		def r	= documentacionService.view(params.docAdicionalId);
		def doc = r.doc
		
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
