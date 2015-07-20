package cl.adexus.isl.spm

import org.codehaus.groovy.grails.web.context.ServletContextHolder

class FileSystemService {

	def uploadDir
	
	FileSystemService(){
		log.info "Starting FileSystemService."
	}
	
	def doUpload(org.springframework.web.multipart.commons.CommonsMultipartFile data, rut) {
		doUpload(data, rut, null, "application/pdf")
	}
	
	def doUploadCSV(org.springframework.web.multipart.commons.CommonsMultipartFile data, run) {
		return doUpload(data, run, null, "application/vnd.ms-excel")
	}
	
	def doUpload(org.springframework.web.multipart.commons.CommonsMultipartFile data, rut, idSiniestro, tipoArchivo) {
		def statusResult=[:]

		if (data == null || data.empty) {
			statusResult=[status: -1, mensaje: "La data está vacía"]
			return statusResult
		}

		try {
			if (!data.getContentType().equalsIgnoreCase("application/pdf")){
				statusResult=[status: -1, mensaje: "Sólo se permiten PDF."]
				return statusResult
			}

			// Genera nombre archivo
			String nombreFile = System.nanoTime().toString() + ".pdf"

			// Directorio para almacenar
			def servletContext = ServletContextHolder.servletContext
			def storagePath = servletContext.getRealPath(uploadDir)

			// Crea directorio si no existe
			def storagePathDirectory = new File(storagePath)
			if (!storagePathDirectory.exists()) {
				print "CREATING DIRECTORY ${storagePath}: "
				if (storagePathDirectory.mkdirs()) {
					log.info "SUCCESS"
				} else {
					log.info "FAILED"
				}
			}

			// Sube el archivo
			data.transferTo(new File("${storagePath}/${nombreFile}"))
			log.info "Saved file: ${storagePath}/${nombreFile}"
			def nom = nombreFile - ".pdf"
			statusResult=[status: 0, mensaje: nom]
			return statusResult

		} catch (Exception e) {
			e.printStackTrace()
			statusResult=[status: -1, mensaje: e.toString()]
			return statusResult;
		}
	}

	def doDeleteDocumentoAdicional(String fileId) {
		def statusResult = [:]

		try {
			def servletContext = ServletContextHolder.servletContext
			def storagePath = servletContext.getRealPath(uploadDir)
			def filePath = storagePath + "/" + fileId + ".pdf"
			File file = new File(filePath)
			if (file.delete()) {
				statusResult=[status: 0, mensaje: "OK"]
			} else {
				statusResult=[status: 1, mensaje: "Delete failed"]
			}

		} catch (Exception e) {
			e.printStackTrace()
			statusResult=[status: -1, mensaje: e.toString()]
		}

		return statusResult
	}

	
	def doGetDocumentoAdicional(String fileId) {
		def statusResult = [:]

		try {
			def servletContext = ServletContextHolder.servletContext
			def storagePath = servletContext.getRealPath(uploadDir)
			def filePath = storagePath + "/" + fileId + ".pdf"
			File file = new File(filePath)

			def outputDoc=[:]
			outputDoc['contentLength']=file.length()
			outputDoc['name']=fileId+".pdf"
			outputDoc['inputStream']=new FileInputStream(filePath);
			outputDoc['mimeType']="application/pdf"
			
			statusResult=[status: 0, doc: outputDoc]
			return statusResult

		} catch (Exception e) {
			e.printStackTrace()
			statusResult=[status: -1, mensaje: e.toString()]
		}

		return statusResult
	}
}