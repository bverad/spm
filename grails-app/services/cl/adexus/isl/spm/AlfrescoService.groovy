package cl.adexus.isl.spm

import org.apache.chemistry.opencmis.commons.enums.BindingType
import org.apache.chemistry.opencmis.client.api.Folder
import org.apache.chemistry.opencmis.client.api.CmisObject
import org.apache.chemistry.opencmis.client.api.ItemIterable
import org.apache.chemistry.opencmis.client.api.Document
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.commons.data.ContentStream
import org.apache.chemistry.opencmis.commons.PropertyIds
import org.apache.chemistry.opencmis.commons.enums.VersioningState
import org.apache.chemistry.opencmis.commons.SessionParameter
import org.apache.chemistry.opencmis.client.api.CmisObjectProperties
import org.apache.chemistry.opencmis.client.api.Property
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl
import org.apache.chemistry.opencmis.client.api.Session
import org.apache.chemistry.opencmis.client.api.SessionFactory
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException
import org.codehaus.groovy.grails.web.context.ServletContextHolder

class AlfrescoService {

	def user
	def password
	def atomPubUrl
	def repositoryId
	def spmPath
	
	AlfrescoService(){
		log.info "Starting AlfrescoService"
	}

	def getSession(){
		def alfrescoParams = [
			(SessionParameter.USER): user,
			(SessionParameter.PASSWORD): password,
			(SessionParameter.ATOMPUB_URL): atomPubUrl,
			(SessionParameter.BINDING_TYPE): BindingType.ATOMPUB.value(),
			(SessionParameter.REPOSITORY_ID): repositoryId,
			(SessionParameter.LOCALE_ISO3166_COUNTRY): "cl",
			(SessionParameter.LOCALE_ISO639_LANGUAGE): "es",
			(SessionParameter.LOCALE_VARIANT): ""]
		log.info "AlfrescoSession Params:"+alfrescoParams
		return SessionFactoryImpl.newInstance().createSession(new HashMap<String, String>(alfrescoParams));

	}

	def doUpload(org.springframework.web.multipart.commons.CommonsMultipartFile data) {
		return doUpload(data, null, null, "application/pdf")
	}

	def doUpload(org.springframework.web.multipart.commons.CommonsMultipartFile data, run, idSiniestro) {
		return doUpload(data, run, idSiniestro, "application/pdf")
	}

	def doUpload(org.springframework.web.multipart.commons.CommonsMultipartFile data, run) {
		return doUpload(data, run, null, "application/pdf")
	}
	
	def doUploadCSV(org.springframework.web.multipart.commons.CommonsMultipartFile data, run) {
		return doUpload(data, run, null, null)
	}
	
	def doUpload(org.springframework.web.multipart.commons.CommonsMultipartFile data, run, idSiniestro, tipoArchivo) {
		def statusResult=[:]

		if (data == null || data.empty) {
			statusResult=[status: -1, mensaje: "La data está vacía"]
			return statusResult
		}
		
		try {
			if (tipoArchivo) {
				// TODO Para validar cada extensión se debe agregar un nuevo if con el content type
				def extension = ""
				extension = "application/pdf".equals(tipoArchivo) ? "PDF" : ""
				log.info 'data.getContentType(): ' + data.getContentType()
				if (!data.getContentType().equalsIgnoreCase(tipoArchivo)) {
					statusResult=[status: -1, mensaje: "Sólo se permiten " + extension + "."]
					return statusResult
				}
			}
		
			// Genera nombre archivo
			String nombreFile = System.nanoTime().toString() + ".pdf"
			
			//Crea las propiedades
			Map<String, Object> properties = new HashMap<String, Object>()
			properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document")
			properties.put(PropertyIds.NAME,nombreFile)
			
			//Session alfresco
			def session = getSession()
			
			// Directorio para almacenar
			def folder
			def realPath
			// Busca la raiz y la crea si no existe
			try{
				folder= (Folder) session.getObjectByPath(spmPath)
			} catch (CmisObjectNotFoundException confe) {
				// Crea directorio si no existe
				folder= (Folder) session.getObjectByPath("/")
				Map<String, Object> propertiesF = new HashMap<String, Object>()
				propertiesF.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder")
				propertiesF.put(PropertyIds.NAME, spmPath.replaceAll("/",""))
				def of=folder.createFolder(propertiesF)
				folder = session.getObject(of)
			}
			if (run) {
				realPath = spmPath + run + "/"
				try{
					folder= (Folder) session.getObjectByPath(realPath)
				} catch (CmisObjectNotFoundException confe) {
					// Crea directorio si no existe
					folder = (Folder) session.getObjectByPath(spmPath)
					Map<String, Object> propertiesF = new HashMap<String, Object>()
					propertiesF.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder")
					propertiesF.put(PropertyIds.NAME, run)
					def of=folder.createFolder(propertiesF)
					folder = session.getObject(of)
				}
			}
			if (idSiniestro) {
				realPath = realPath + idSiniestro + "/"
				try{
					folder= (Folder) session.getObjectByPath(realPath)
				} catch (CmisObjectNotFoundException confe) {
					// Crea directorio si no existe
					folder = (Folder) session.getObjectByPath(spmPath + run)
					Map<String, Object> propertiesF = new HashMap<String, Object>()
					propertiesF.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder")
					propertiesF.put(PropertyIds.NAME, idSiniestro + "")
					def of=folder.createFolder(propertiesF)
					folder = session.getObject(of)
				}
			}
			log.info 'realPath: ' + realPath
			
			//Sube el archivo
			def buf = data.getBytes()
			ByteArrayInputStream input = new ByteArrayInputStream(buf)
			ContentStream contentStream = session.getObjectFactory().createContentStream(nombreFile,
				buf.length, data.contentType, input)
			def o=folder.createDocument(properties, contentStream, VersioningState.MAJOR)
			log.info "Saved file: ${realPath}:${nombreFile}"
			log.info "Alfresco ID: "+o.getId()
			def nom = nombreFile - ".pdf"
			statusResult=[status: 0, mensaje: o.getId()]
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
			//Session alfresco
			def session = getSession();
			//Buscamos el archivo
			def doc = (Document) session.getObject(fileId)
			// Borra la actual version y todas las anteriores
	        doc.deleteAllVersions();
			statusResult=[status: 0, mensaje: "OK"]
		} catch (Exception e) {
			e.printStackTrace()
			statusResult=[status: -1, mensaje: e.toString()]
		}
		return statusResult
	}
	

	def doGetDocumentoAdicional(String fileId) {
		def statusResult = [:]

		try {
			//Session alfresco
			def session = getSession();
			//Buscamos el archivo
			def doc = (Document) session.getObject(fileId)			

			def outputDoc=[:]
			outputDoc['contentLength']=(int)doc.getContentStreamLength();
			outputDoc['name']=doc.getName()
			outputDoc['inputStream']=doc.getContentStream().getStream()
			outputDoc['mimeType']=doc.getContentStreamMimeType()
			log.info "mimeType:"+outputDoc.mimeType
			statusResult=[status: 0, doc: outputDoc]
			return statusResult
		} catch (Exception e) {
			e.printStackTrace()
			statusResult=[status: -1, mensaje: e.toString()]
		}
		return statusResult
	}
}
