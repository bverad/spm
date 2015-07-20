package cl.adexus.isl.spm

import java.util.logging.Logger;

import cl.adexus.isl.spm.domain.DIATSuseso
import cl.adexus.isl.spm.domain.DIEPSuseso
import cl.adexus.isl.spm.domain.RECAATSuseso
import cl.adexus.isl.spm.domain.RECAEPSuseso
import cl.adexus.isl.spm.domain.RELASuseso
import cl.adexus.isl.spm.domain.ALLASuseso
import cl.adexus.isl.spm.domain.ALMESuseso
import cl.adexus.isl.suseso.clients.SusesoWSClients
import cl.adexus.isl.suseso.xmldsig.SusesoSignature
import cl.adexus.isl.suseso.xmlenc.SusesoEncrypt


class SUSESOWebService {

	def String username;
	def String password;
	def String WSIngreso;
	def String WSToken;
	def String WSAnulacion;

	def enviarDIAT(DIAT diat){
		enviarDIAT(diat,false)
	}
	
	def enviarDIAT(DIAT diat,boolean async){
		//Obtiene la DIAT en XML Firmado
		log.info("Ejecutando metodo DIAT")
		def xmlDataSigned=getXmlDIAT(diat)
		diat.xmlEnviado=xmlDataSigned
		diat.save(flush: true);
		
		log.info("verificando si se ejecutara el envio de DIAT de manera asincrona")
		if(async){
			log.debug("el envio sera asincrono")
			//Ejecutamos la llamada a la suseso Asincronamente (fire-forget)
			runAsync{
				doSendDIAT(diat)
			}
		}else{
			log.debug("el envio no sera asincrono")
			doSendDIAT(diat)
		}
	}

	def doSendDIAT(DIAT diat){
		log.info("Ejecutando doSendDIAT")
		def localDiat=DIAT.get(diat.id)
		def tipoDoc=getTipoDoc("DIAT",localDiat.calificacionDenunciante.codigo);
		def respuestaIngresoDocumento=enviarSuseso(tipoDoc,localDiat.xmlEnviado,localDiat.siniestro.cun)
		log.debug("Verificando respuesta de ingreso del documento")
		if(respuestaIngresoDocumento){
			localDiat.xmlRecibido=respuestaIngresoDocumento
			localDiat.save(flush: true);
			def cun=getCUN(respuestaIngresoDocumento)
			if(cun){
				localDiat.siniestro.merge()
				localDiat.siniestro.cun=cun
				localDiat.siniestro.save(flush: true);
			}
		}
		log.debug("la respuesta al ingreso es $respuestaIngresoDocumento")
		log.info("verificacion de respuesta de ingreso del documento completada")
	}

	def enviarDIEP(DIEP diep){
		enviarDIEP(diep,false)
	}
	
	def enviarDIEP(DIEP diep,boolean async){
		log.info("Ejecutando metodo DIEP")
		//Obtiene la DIEP en XML Firmado
		def xmlDataSigned=getXmlDIEP(diep)
		diep.xmlEnviado=xmlDataSigned
		diep.save(flush: true);

		log.info("verificando si se ejecutara el envio de DIEP de manera asincrona")
		if(async){
			log.debug("el envio sera asincrono")
			//Ejecutamos la llamada a la suseso Asyncronamente (fire-forget)
			runAsync{
				doSendDIEP(diep)
			}
		}else{
			log.debug("el envio no sera asincrono")
			doSendDIEP(diep)
		}
	}
	
	def doSendDIEP(DIEP diep){
		log.info("Ejecutando doSendDIEP")
		def localDiep=DIEP.get(diep.id)
		def tipoDoc=getTipoDoc("DIEP",localDiep.calificacionDenunciante.codigo);
		def respuestaIngresoDocumento=enviarSuseso(tipoDoc,localDiep.xmlEnviado,localDiep.siniestro.cun)
		log.debug("Verificando respuesta de ingreso del documento")
		if(respuestaIngresoDocumento){
			localDiep.xmlRecibido=respuestaIngresoDocumento
			localDiep.save(flush: true);
			def cun=getCUN(respuestaIngresoDocumento)
			if(cun){
				localDiep.siniestro.merge()
				localDiep.siniestro.cun=cun
				localDiep.siniestro.save(flush: true);
			}
		}
		log.debug("La respuesta al ingreso es $respuestaIngresoDocumento")
		log.info("Verificacion de respuesta de ingreso del documento completada")
	}
	//TODO: reca, arreglar el copy-paste from hell
	def enviarRECAAT(RECA reca){
		enviarRECAAT(reca,false)
	}
	
	def enviarRECAAT(RECA reca,boolean async){
		log.info("Ejecutando enviarRECAAT")
		//Obtiene la RECA en XML Firmado
		def xmlDataSigned=getXmlRECAAT(reca)
		reca.xmlEnviado=xmlDataSigned
		reca.save(flush: true);

		log.info("Verificando si se ejecutara el envio de RECAAT de manera asincrona")
		if(async){
			//Ejecutamos la llamada a la suseso Asyncronamente (fire-forget)
			log.debug("El envio sera asincrono")
			runAsync{
				doSendRECAAT(reca)
			}
		}else{
			log.debug("El envio sera sincrono")
			doSendRECAAT(reca)
		}
	}
	
	def doSendRECAAT(RECA reca){
		log.info("Ejecutando doSendRECAAT")
		try{
			def localReca=RECA.get(reca.id)
			def tipoDoc=getTipoDoc("RECA",null);
			def respuestaIngresoDocumento=enviarSuseso(tipoDoc,localReca.xmlEnviado,localReca.siniestro.cun)
			log.info("Verificando respuesta de ingreso del documento")
			if(respuestaIngresoDocumento){
				localReca.xmlRecibido=respuestaIngresoDocumento
				localReca.save(flush: true);
				def cun=getCUN(respuestaIngresoDocumento)
				if(cun){
					localReca.siniestro.merge()
					localReca.siniestro.cun=cun
					localReca.siniestro.save(flush: true);
				}
			}
			log.info("La reca en contexto es : ${reca.id}")
			log.info("La respuesta al ingreso es $respuestaIngresoDocumento")
			log.info("Verificacion de respuesta de ingreso del documento completada")
		}catch(Exception e){
			log.info("Error no controlado : ${e.message}")
		}
	}
	
	/*
	 * RELA
	 */
	def enviarRELA(Rela rela) {
		log.info("Ejecutando enviarRELA")
		def xmlDataSigned = getXmlRELA(rela)
		def async = true
		
		rela.xmlEnviado = xmlDataSigned
		rela.save(flush: true);
		
		log.info("Verificando si el envio sera asincrono")
		if(async){
			//Ejecutamos la llamada a la suseso Asyncronamente (fire-forget)
			log.debug("El envio sera asicrono")
			runAsync{
				doSendRELA(rela)
			}
		}else{
			log.debug("El envio no sera asincrono")
			doSendRELA(rela)
		}
	}
	
	def getXmlRELA(Rela rela){
		log.info("Ejecutando getXmlRELA")
		def relaSuseso = new RELASuseso();
		def xmlData = relaSuseso.getXmlDocument(rela)
		def xmlEncData = encrypt(xmlData)
		return sign(xmlEncData)
	}
	
	def doSendRELA(Rela rela){
		log.info("Ejecutando doSendRELA")
		try{
			def localRela = Rela.get(rela.id)
			def tipoDoc = getTipoDoc("RELA", null);
			
			def respuestaIngresoDocumento=enviarSuseso(tipoDoc,localRela?.xmlEnviado,localRela?.siniestro?.cun)
			
			log.info("Verificando la respuesta de ingreso del documento")
			if(respuestaIngresoDocumento){
				localRela.xmlRecibido = respuestaIngresoDocumento
				localRela.save(flush: true);
				def cun = getCUN(respuestaIngresoDocumento)
				if(cun){
					localRela.siniestro.merge()
					localRela.siniestro.cun=cun
					localRela.siniestro.save(flush: true);
				}
			}
			log.debug("La respuesta al ingreso es $respuestaIngresoDocumento")
			log.info("Verificacion de respuesta de ingreso del documento completada")
		}catch(Exception e){
			log.error("doSendRELA:"+e.getMessage().toString());
		}
	}

	/*
	 * ALME
	 */
	def enviarALME(Alme alme) {
		log.info("Ejecutando enviar ALME")
		def xmlDataSigned = getXmlALME(alme)
		def async = true
		
		alme.xmlEnviado = xmlDataSigned
		alme.save(flush: true);
		
		log.info("Verificando si el envio del documento sera asincrono")
		if(async){
			//Ejecutamos la llamada a la suseso Asyncronamente (fire-forget)
			log.debug("El envio del documento sera asincrono")
			runAsync{
				doSendALME(alme)
			}
		}else{
			log.debug("El envio del documento no sera sera asincrono")
			doSendALME(alme)
		}
	}
	
	def getXmlALME(Alme alme){
		log.info("Ejecutando getXmlALME")
		def almeSuseso = new ALMESuseso();
		def xmlData = almeSuseso.getXmlDocument(alme)
		def xmlEncData = encrypt(xmlData)
		return sign(xmlEncData)
	}
	
	def doSendALME(Alme alme){
		log.info("Ejecutando doSendALME")
		try{
			def localAlme = Alme.get(alme.id)
			def tipoDoc = getTipoDoc("ALME", null);
			def respuestaIngresoDocumento=enviarSuseso(tipoDoc,localAlme?.xmlEnviado,localAlme?.siniestro?.cun)
			
			log.info("Verificando la respuesta del ingreso del documento")
			if(respuestaIngresoDocumento){
				localAlme.xmlRecibido = respuestaIngresoDocumento
				localAlme.save(flush: true);
				def cun = getCUN(respuestaIngresoDocumento)
				if(cun){
					localAlme.siniestro.merge()
					localAlme.siniestro.cun=cun
					localAlme.siniestro.save(flush: true);
				}
			}
			
			log.debug("La respuesta al ingreso es $respuestaIngresoDocumento")
			log.info("Verificacion de respuesta de ingreso del documento completada")
		}catch(Exception e){
			log.error("doSendALME:"+e.getMessage().toString());
		}
	}

	/*
	 * ALLA
	 */
	def enviarALLA(Alla alla) {
		log.info("Ejecutando enviarALLA")
		def xmlDataSigned = getXmlALLA(alla)
		def async = true
		
		alla.xmlEnviado = xmlDataSigned
		alla.save(flush: true);
		
		log.info("Verificando si el envio de ALLA sera asincrono")
		if(async){
			log.debug("El envio de ALLA sera asincrono")
			//Ejecutamos la llamada a la suseso Asyncronamente (fire-forget)
			runAsync{
				doSendALLA(alla)
			}
		}else{
			log.debug("El envio de ALLA no sera asincrono")
			doSendALLA(alla)
		}
	}
	
	def getXmlALLA(Alla alla){
		log.info("Ejecutando getXmlALLA : $alla")
		
		def allaSuseso = new ALLASuseso();
		def xmlData = allaSuseso.getXmlDocument(alla)		
		def xmlEncData = encrypt(xmlData)		
		return sign(xmlEncData)
	}
	
	def doSendALLA(Alla alla){
		log.info("Ejecutando doSendALLA")
		try{
			def localAlla = Alla.get(alla.id)
			def tipoDoc = getTipoDoc("ALLA", null);
			
			def respuestaIngresoDocumento=enviarSuseso(tipoDoc,localAlla?.xmlEnviado,localAlla?.siniestro?.cun)
			
			log.info("Verificando la respuesta de ingreso de ALLA")
			if(respuestaIngresoDocumento){
				localAlla.xmlRecibido = respuestaIngresoDocumento
				localAlla.save(flush: true);
				def cun = getCUN(respuestaIngresoDocumento)
				if(cun){
					localAlla.siniestro.merge()
					localAlla.siniestro.cun=cun
					localAlla.siniestro.save(flush: true);
				}
			}
			log.debug("La respuesta al ingreso es $respuestaIngresoDocumento")
			log.info("Verificacion de respuesta de ingreso del documento completada")
		}catch(Exception e){
			log.error("doSendALME:"+e.getMessage().toString());
		}
	}

	/*
	 * RECA	
	 */
	def enviarRECAEP(RECA reca){
		enviarRECAEP(reca,false)
	}
	
	def enviarRECAEP(RECA reca,boolean async){
		log.info("Ejecutando enviarRECAEP")
		//Obtiene la RECA en XML Firmado
		def xmlDataSigned=getXmlRECAEP(reca)
		reca.xmlEnviado=xmlDataSigned
		reca.save(flush: true);
		log.info("Verificando si el envio de RECAEP sera asincrono")
		if(async){
			log.info("Envio de RECAEP sera asincrono")
			//Ejecutamos la llamada a la suseso Asyncronamente (fire-forget)
			runAsync{
				doSendRECAEP(reca)
			}
		}else{
			log.info("Envio de RECAEP no sera asincrono")
			doSendRECAEP(reca)
		}
	}
	
	def doSendRECAEP(RECA reca){
		log.info("Ejecutando doSendRECAEP")
		try{
			def localReca=RECA.get(reca.id)
			def tipoDoc=getTipoDoc("RECA",null);
			def respuestaIngresoDocumento=enviarSuseso(tipoDoc,localReca.xmlEnviado,localReca.siniestro.cun)
			
			log.info("Verificando la respuesta de ingreso de RECAEP")
			if(respuestaIngresoDocumento){
				localReca.xmlRecibido=respuestaIngresoDocumento
				localReca.save(flush: true);
				def cun=getCUN(respuestaIngresoDocumento)
				if(cun){
					localReca.siniestro.merge()
					localReca.siniestro.cun=cun
					localReca.siniestro.save(flush: true);
				}
			}
			log.debug("La respuesta al ingreso es $respuestaIngresoDocumento")
			log.info("Verificacion de respuesta de ingreso del documento completada")
		}catch(Exception e){
			log.error("doSendRECAEP:"+e.getMessage().toString());
		}
	}
	
	def getXmlDIAT(DIAT diat){
		log.info("Ejecutando getXmlDIAT")
		// transforma la estructura DIAT al xml requerido por suseso
		def diatSuseso = new DIATSuseso();
		def xmlData = diatSuseso.getXmlDocument(diat)
		return sign(xmlData)
	}
	
	def getXmlDIEP(DIEP diep){
		log.info("Ejecutando getXmlDIEP")
		// transforma la estructura DIEP al xml requerido por suseso
		def diepSuseso = new DIEPSuseso();
		def xmlData = diepSuseso.getXmlDocument(diep)
		return sign(xmlData)
	}
	
	//RECA-AT
	def getXmlRECAAT(RECA reca){
		log.info("Ejecutando getXmlRECAAT")
		// transforma la estructura RECA AT al xml requerido por suseso
		def recaSuseso = new RECAATSuseso();
		def xmlData = recaSuseso.getXmlDocument(reca)
		def xmlEncData = encrypt(xmlData)
		return sign(xmlEncData)
	}
	
	//RECA-EP
	def getXmlRECAEP(RECA reca){
		log.info("Ejecutando getXmlRECAEP")
		// transforma la estructura RECA EP al xml requerido por suseso
		def recaSuseso = new RECAEPSuseso();
		def xmlData = recaSuseso.getXmlDocument(reca)
		def xmlEncData = encrypt(xmlData)
		return sign(xmlEncData)
	}
	def sign(xmlData){
		log.info("Ejecutando sign")
		// datos para firmar
		def susesoSignature = new SusesoSignature(
				this.class.classLoader.getResourceAsStream('certificados.jks'), "password", "isl-p12", "")

		// firma el xml usando el keystore indicado
		def String xmlDataSigned = susesoSignature.signXml(xmlData).toString("UTF-8")
		return xmlDataSigned
	}
	
	def encrypt(xmlData){
		log.info("Ejecutando encrypt")
		// datos para firmar
		def susesoEncrypt = new SusesoEncrypt(this.class.classLoader.getResourceAsStream('suseso_pub.pem'))

		// encrypta el xml usando la llave indicada
		def String xmlDataEncrypted = susesoEncrypt.encryptXml(xmlData).toString("UTF-8")
		return xmlDataEncrypted
		
	}
	
	def getTipoDoc(tipoDocumento,calificacionDenuncianteCodigo){
		log.info("Ejecutando getTipoDoc")
		def tipoDoc;
		log.info("El tipo de documento corresponde a una : $tipoDocumento")
		if(tipoDocumento=="DIAT"){
			switch (calificacionDenuncianteCodigo){
				case ('1'): tipoDoc="3"; break; //'Empleador' -> "EM"
				case ('7'): tipoDoc="1"; break; //'Organismo administrador' -> "OA"
				default : tipoDoc="5"; break; //todos los demas -> "OT"
			}
			
			return tipoDoc;
		} 
		
		if (tipoDocumento=="DIEP"){
			switch (calificacionDenuncianteCodigo){
				case ('1'): tipoDoc="4"; break; //'Empleador' -> "EM"
				case ('7'): tipoDoc="2"; break; //'Organismo administrador' -> "OA"
				default : tipoDoc="6"; break; //todos los demas -> "OT"
			}
			
			return tipoDoc;
		} 
		
		if (tipoDocumento=="RECA"){
			return "7";
		}
		
		if (tipoDocumento == "RELA") {
			return "8";
		}
		
		if (tipoDocumento == "ALLA") {
			return "9";
		}
		
		if (tipoDocumento == "ALME") {
			return "10";
		}
		
		return tipoDoc;
	}

	def enviarSuseso(tipoDoc,xmlDataSigned,cun){
		log.info("Ejecutando enviarSuseso")
		// clientes de los servicios web
		def wsClients = new SusesoWSClients(username:username,
											password:password,
											WSIngreso:WSIngreso,
											WSToken:WSToken,
											WSAnulacion:WSAnulacion
											)

		try{
			// pide un token
			def token = wsClients.wsTokenClient()
			log.info "enviarSuseso::token :"+token
			
			//Ingresa el documento
			log.info "enviarSuseso::tipoDoc :"+tipoDoc
			log.info "enviarSuseso::cun :"+ cun
			log.info "enviarSuseso::xmlDataSigned :"+xmlDataSigned
			def respuestaIngresoDocumento = wsClients.wsIngresoServiceClient(token, tipoDoc, xmlDataSigned, cun)
			log.info "enviarSuseso::retorno :"+getRetorno(respuestaIngresoDocumento)
			return respuestaIngresoDocumento
		}catch(Exception e){
			log.info("error no controlado: ${e.message}")
			e.printStackTrace()
			return null
		}
	}
	
	def getRetorno(respuestaIngresoDocumento){
		log.info("Ejecutando getRetorno")
		def rootNode = new XmlSlurper().parseText(respuestaIngresoDocumento)
		return rootNode.salida.retorno
	}
	
	def getCUN(respuestaIngresoDocumento){
		log.info("Ejecutando getCUN")
		def rootNode = new XmlSlurper().parseText(respuestaIngresoDocumento)
		if(rootNode.salida.cun !=''){
			return rootNode.salida.cun
		}else{
			return null
		}
	}
	
	
}
