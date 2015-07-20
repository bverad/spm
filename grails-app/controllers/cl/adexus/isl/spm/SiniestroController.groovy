package cl.adexus.isl.spm

import javax.servlet.ServletOutputStream

class SiniestroController {

	def siniestroService
	def DocumentacionService
	def DenunciaService
	def SeguimientoService
	def OTPIngresoService
	def CalOrigenRestService
	
	def index() {
		redirect(action: 'dp01')
	}

	/**
	 * Formulario
	 * - RUN Trabajador
	 * - [Siguiente] -> r01()
	 */
	def dp01(){
		def model
		if(params.get('model')){
			model = params.get('model')
		}
		model

	}

	/**
	 * Llamado por dp01
	 *
	 * Busca los siniestros del trabajador
	 *
	 * @params run
	 */
	def r01(){
		log.info("Ejecutando action r01")
		log.info("Datos recibidos : $params")
		// Llamamos al servicio
		def r = siniestroService.r01(params)
		if(r.get('result')){
			params.put('trabajador_run', params['run'])
			forward ([action:'dp02', params: params])   
		}else{
			params.put('model', r.get('model'))
			forward ([action:'dp01', params: params])
		}
	}

	/**
	 * Formulario
	 * 	- Lista de Siniestros previos para elegir
	 *  - [Siguiente] -> cu02()
	 */
	def dp02() {
		log.info("Ejecutando action dp02")
		log.info("Datos recibidos : $params")
		def run = params.get('trabajador_run')
		def rutEmpresa = params?.rutEmpresa
		return siniestroService.preDp02(run, rutEmpresa)
	}

	/**
	 * Llamado por dp02
	 *
	 * Busca los datos del siniestro
	 *
	 * @params id (de siniestro)
	 */
	def cu02(){
		log.info "parametros en cu02-> "+params
		// Llamamos al servicio
		params.put('siniestroControllerHome', session['siniestroControllerHome'])
		def r = siniestroService.cu02(params)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}

	/**
	 * Datos del Siniestro
	 */
	def dp03() {
		def m=params.get('model')
		return siniestroService.preDp03(m)
	}
	
	/**
	 * Para volver a 77bis
	 * @return
	 */
	def volver(){
		log.info "Volviendo a 77bis o a OTP_Ingreso-> " + params	
		params.put('bisId', session['bisId'])
		def r = siniestroService.volver(params)
		session.removeAttribute('siniestroControllerHome')
		flash.put('model', r.get('model'))
		redirect (r.next)
	}
	
	/**
	 * Muestra un documentoAdicional
	 * @return
	 */
	def viewDoc(){
		def r=DocumentacionService.view(params.id);
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
	
	//Imprimir ODA
	def genPdfODA(){ //Genera el PDF del informe de la ODA
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
	
	//Imprimir Sol Reembolso
	def genPdfReembolso(){ //Genera el PDF de la Solicitud de Reembolso
		log.info("Imprimir PDF Sol Reembolso. Params:"+params)
		def reembolso = Reembolso.findById(params.reembolsoId)
		// Imprimimos el pdf
		def b = OTPIngresoService.imprimeSolicitudReembolso(reembolso)
		if(b != null){
		response.setContentType("application/pdf")
		response.setHeader("Content-disposition", "attachment; filename=Solicitud_Reembolso_" +
				params.reembolsoId.toString() + ".pdf")
		response.setContentLength(b.length)
		response.getOutputStream().write(b)
		} else {
			//No se genera el PDF
		}
	}
	
	//Imprimir RECA
	def genPdfReca(){ //Genera el PDF de la RECA
		log.info("Imprimir PDF RECA. Params:"+params)
		def siniestro = Siniestro.findById(params.siniestroId)
		def reca = RECA.findById(params.recaId)
		def b = CalOrigenRestService.genRECAPDF(siniestro, reca)

		if(b!=null){
			response.setContentType("application/pdf")
			response.setHeader("Content-disposition", "attachment; filename=RECA_" +
				params.recaId.toString() + ".pdf")
			response.setContentLength(b.length)
			response.getOutputStream().write(b)
		} else {
			//No se genero el PDF
		}
	}

	//Imprimir RELA
	def genPdfRela(){ //Genera el PDF de la RELA
		log.info("Imprimir PDF RELA. Params:"+params)
		def b = siniestroService.genRELAPDF(params)

		if(b!=null){
			response.setContentType("application/pdf")
			response.setHeader("Content-disposition", "attachment; filename=RELA_" +
				params.relaId.toString() + ".pdf")
			response.setContentLength(b.length)
			response.getOutputStream().write(b)
		} else {
			//No se genero el PDF
		}
	}
	
	//Imprimir ALLA
	def genPdfAlla(){ //Genera el PDF de la ALLA
		log.info("Imprimir PDF ALLA. Params:"+params)
		def b = siniestroService.genALLAPDF(params)

		if(b!=null){
			response.setContentType("application/pdf")
			response.setHeader("Content-disposition", "attachment; filename=ALLA_" +
				params.allaId.toString() + ".pdf")
			response.setContentLength(b.length)
			response.getOutputStream().write(b)
		} else {
			//No se genero el PDF
		}
	}
	
	//Imprimir ALME
	def genPdfAlme(){ //Genera el PDF de la ALME
		log.info("Imprimir PDF ALME. Params:"+params)
		def b = siniestroService.genALMEPDF(params)

		if(b!=null){
			response.setContentType("application/pdf")
			response.setHeader("Content-disposition", "attachment; filename=ALME_" +
				params.almeId.toString() + ".pdf")
			response.setContentLength(b.length)
			response.getOutputStream().write(b)
		} else {
			//No se genero el PDF
		}
	}
}
