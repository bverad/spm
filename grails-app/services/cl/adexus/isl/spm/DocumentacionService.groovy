package cl.adexus.isl.spm

class DocumentacionService {

	def uploadService //Inyectado en conf/spring/resources.groovy segun Enviroment

	def uploadSinSesion( Map map,
			String descripcion,
			String tipoDenuncia,
			org.springframework.web.multipart.commons.CommonsMultipartFile f) {
		def run
		def idSiniestro

		def sdaat = SDAAT.findById(map.getAt("sdaat"))
		def sdaep = SDAEP.findById(map.getAt("sdaep"))

		if (tipoDenuncia == 'DIAT') {
			run = map.getAt("sdaat")?.trabajador?.run
			idSiniestro = map.getAt("sdaat")?.siniestro?.id
		} else if (tipoDenuncia == 'DIEP') {
			run = map.getAt("sdaep")?.trabajador?.run
			idSiniestro = map.getAt("sdaep")?.siniestro?.id
		} else if (tipoDenuncia == "SAA"){
			run = map.getAt("siniestro").trabajador?.run
			idSiniestro = map.getAt("siniestro").id
		} else if (tipoDenuncia == "REEM") {
			run         = map.getAt("run")
			idSiniestro = map.getAt("siniestroId")
		} else if (tipoDenuncia == "REEMDET"){
			run         = map.getAt("run")
			idSiniestro = map.getAt("siniestroId")
		} else if (tipoDenuncia == "SEGU"){
			run = map.getAt("seguimiento")?.siniestro?.trabajador?.run
			idSiniestro = map.getAt("seguimiento")?.siniestro?.id
		} else if (tipoDenuncia == "ACSE"){
			run = map.getAt("actividadSeguimiento").seguimiento?.siniestro?.trabajador?.run
			idSiniestro = map.getAt("actividadSeguimiento").seguimiento?.siniestro?.id
		} else if (tipoDenuncia == "REIN"){
			run = map.getAt("reingreso")?.siniestro?.trabajador?.run
			idSiniestro = map.getAt("reingreso")?.siniestro?.id
		} else if (tipoDenuncia == "77DAC" || tipoDenuncia == "77DIC"){
			run = null
			idSiniestro = null
		}


		def result = uploadService.doUpload(f, run, idSiniestro)

		if (result.status == 0) {
			def documentacion = new DocumentacionAdicional()
			documentacion.setFileId(result.mensaje)
			documentacion.setDescripcion(descripcion)

			if (tipoDenuncia == 'DIAT') {
				documentacion.setSolicitudAT(map.getAt("sdaat"))
				documentacion.setDenunciaAT(map.getAt("sdaat")?.diat)
			} else if (tipoDenuncia == 'DIEP') {
				documentacion.setSolicitudEP(map.getAt("sdaep"))
				documentacion.setDenunciaEP(map.getAt("sdaep")?.diep)
				log.info("upload: setDenunciaEP: " + map.getAt("sdaep")?.diep)
			} else if (tipoDenuncia == "SAA"){
				documentacion.setAntecedente(map.getAt("antecedente"))
			} else if (tipoDenuncia == "REEM") {
				documentacion.setReembolso(map.getAt("reembolso"))
			} else if (tipoDenuncia == "REEMDET"){
				documentacion.setDetalleReembolso(map.getAt("detalleReembolso"))
			} else if (tipoDenuncia == "SEGU"){
				documentacion.setSeguimiento(map.getAt("actividadSeguimiento")?.seguimiento)
			} else if (tipoDenuncia == "ACSE"){
				def actividadSeguimiento = map.getAt("actividadSeguimiento")
				//se modifica ya que no es posible almacenar un elemento que dependa de otra entidad y no halla sido almacenada previamente
				actividadSeguimiento.addToDocumentacion(documentacion)
				return [status: 0, mensaje: 'OK']

			} else if (tipoDenuncia == "REIN"){
				documentacion.setReingreso(map.getAt("reingreso"))
			} else if (tipoDenuncia == "77DAC"){
				documentacion.setDocumentacion(map.getAt("bis"))
			} else if (tipoDenuncia == "77DIC"){
				documentacion.setDictamen(map.getAt("bis"))
			}


			if(!documentacion.validate()){
				// Hay algo malo con los datos y no se puede crear la documentacion
				return [ status: -1, mensaje: documentacion.errors]
			}

			if (!documentacion.save()) {
				log.info("Error catastrofico, imperdonable e irrepetible")
				return [status: 1, mensaje: "TOPITO"]
			}
			return [status: 0, mensaje: 'OK', documentacionId: documentacion.id]
		} else {
			// Error
			return [status: 1, mensaje: result.mensaje]
		}
	}


	def upload( org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession session,
			String descripcion,
			String tipoDenuncia,
			org.springframework.web.multipart.commons.CommonsMultipartFile f) {
		def run
		def idSiniestro

		def sdaat = SDAAT.findById(session['sdaat'])
		def sdaep = SDAEP.findById(session['sdaep'])

		if (tipoDenuncia == 'DIAT') {
			run = sdaat?.trabajador?.run
			idSiniestro = sdaat?.siniestro?.id
		} else if (tipoDenuncia == 'DIEP') {
			run = sdaep?.trabajador?.run
			idSiniestro = sdaep?.siniestro?.id
		} else if (tipoDenuncia == "SAA"){
			run = siniestro?.trabajador?.run
			idSiniestro = siniestro?.id
		} else if (tipoDenuncia == "REEM") {
			run         = run
			idSiniestro = siniestroId
		} else if (tipoDenuncia == "REEMDET"){
			run         = run
			idSiniestro = siniestroId
		} else if (tipoDenuncia == "SEGU"){
			run = seguimiento?.siniestro?.trabajador?.run
			idSiniestro = seguimiento?.siniestro?.id
		} else if (tipoDenuncia == "ACSE"){
			run = actividadSeguimiento?.seguimiento?.siniestro?.trabajador?.run
			idSiniestro = actividadSeguimiento?.seguimiento?.siniestro?.id
		} else if (tipoDenuncia == "REIN"){
			run = reingreso?.siniestro?.trabajador?.run
			idSiniestro = reingreso?.siniestro?.id
		}
		def result = uploadService.doUpload(f, run, idSiniestro)

		if (result.status == 0) {
			def documentacion = new DocumentacionAdicional()
			documentacion.setFileId(result.mensaje)
			documentacion.setDescripcion(descripcion)

			if (tipoDenuncia == 'DIAT') {
				documentacion.setSolicitudAT(sdaat)
				documentacion.setDenunciaAT(sdaat?.diat)
			} else if (tipoDenuncia == 'DIEP') {
				documentacion.setSolicitudEP(sdaep)
				documentacion.setDenunciaEP(sdaep?.diep)
				log.info("upload: setDenunciaEP: " + sdaep?.diep)
			} else if (tipoDenuncia == "SAA"){
				documentacion.setAntecedente(antecedente)
			} else if (tipoDenuncia == "REEM") {
				documentacion.setReembolso(reembolso)
			} else if (tipoDenuncia == "REEMDET"){
				documentacion.setDetalleReembolso(detalleReembolso)
			} else if (tipoDenuncia == "SEGU"){
				documentacion.setSeguimiento(actividadSeguimiento?.seguimiento)
			} else if (tipoDenuncia == "ACSE"){
				documentacion.setActividadSeguimiento(actividadSeguimiento)
			} else if (tipoDenuncia == "REIN"){
				documentacion.setReingreso(reingreso)
			}

			if(!documentacion.validate()){
				// Hay algo malo con los datos y no se puede crear la documentacion
				return [ status: -1, mensaje: documentacion.errors]
			}

			if (!documentacion.save()) {
				log.info("Error catastrofico, imperdonable e irrepetible")
				return [status: 1, mensaje: "TOPITO"]
			}
			return [status: 0, mensaje: 'OK']
		} else {
			// Error
			return [status: 1, mensaje: result.mensaje]
		}
	}

	def delete(String id) {
		def docAdicional = DocumentacionAdicional.get(id.toLong())

		// Borra del sistema de archivos
		def result = uploadService.doDeleteDocumentoAdicional(docAdicional?.fileId)

		if (result.status == 0) {
			// Borra de la base de datos
			docAdicional.delete(flush: true)
			return [ status: 0, mensaje: 'OK']
		} else {
			// Error
			return [status: 1, mensaje: result.mensaje]
		}
	}

	def view(String id) {
		def docAdicional = DocumentacionAdicional.get(id)

		// Borra del sistema de archivos
		def result = uploadService.doGetDocumentoAdicional(docAdicional.fileId)
		return result;
	}
}
