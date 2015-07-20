
import grails.util.Environment
import cl.adexus.isl.spm.FileSystemService
import cl.adexus.isl.spm.AlfrescoService
import cl.adexus.isl.spm.SGADummyService
import cl.adexus.isl.spm.SGAWebService
import cl.adexus.isl.spm.SUSESODummyService
import cl.adexus.isl.spm.SUSESOWebService
import org.apache.shiro.authc.credential.Sha1CredentialsMatcher

import cl.adexus.isl.spm.JBPMRestService
import cl.adexus.isl.spm.CMRestService


//Custom environment
def isCustomEnvironment= true
def customEnvironment= grails.util.Environment.current.name

//atom definitions
def userAtom//="spm"
def passwordAtom//="ALVEPvhh"
def atomPubUrl//="http://172.16.6.113:8080/alfresco/cmisatom"
def repositoryId//="b08bed31-d80b-4b96-9ee6-8dc1223c59f8"
def spmPath//="/spm/"
//SGA
def wsdl//="http://172.16.6.51:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"

//SUSESO
def	userSUSESO//="isl"
def passwordSUSESO//="345612"
def wsIngreso//="http://siatepqa.suseso.cl:8888/Siatep/WSIngreso"
def wsToken//="http://siatepqa.suseso.cl:8888/Siatep/WSToken"
def wsAnulacion//="http://siatepqa.suseso.cl:8888/Siatep/WSAnulacion"

//JBPM
def jbpmHost//="172.16.6.114"
def jbpmPort//="8080"

switch(Environment.current) {
	case Environment.DEVELOPMENT:
		log.info("Resources environment : development")
	//atom upload service
		userAtom="spm"
		passwordAtom="ALVEPvhh"
		atomPubUrl="http://172.16.6.113:8080/alfresco/cmisatom"
		repositoryId="b08bed31-d80b-4b96-9ee6-8dc1223c59f8"
		spmPath="/spm/"

	//SGA
		wsdl="http://172.16.6.51:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"

	//SUSESO
		userSUSESO="isl"
		password="345612"
		wsIngreso="http://siatepqa.suseso.cl:8888/Siatep/WSIngreso"
		wsToken="http://siatepqa.suseso.cl:8888/Siatep/WSToken"
		wsAnulacion="http://siatepqa.suseso.cl:8888/Siatep/WSAnulacion"

	//JBPM
		jbpmHost="172.16.6.114"
		jbpmPort="8080"
		break

	case Environment.TEST:
		log.info("Resources environment : test")
	//atom upload service
		userAtom="spm34"
		passwordAtom="u93ks_"
		atomPubUrl="http://172.16.6.128:8080/alfresco/s/cmis"
		repositoryId="8c4769cb-5aa9-441d-8290-b73668505419"
		spmPath="/spm52/"

	//SGA
		wsdl="http://172.16.6.52:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"

	//SUSESO
		userSUSESO="isl"
		passwordSUSESO="345612"
		wsIngreso="http://siatepqa.suseso.cl:8888/Siatep/WSIngreso"
		wsToken="http://siatepqa.suseso.cl:8888/Siatep/WSToken"
		wsAnulacion="http://siatepqa.suseso.cl:8888/Siatep/WSAnulacion"

	//JBPM
		jbpmHost="172.16.6.129"
		jbpmPort="8080"
		break

	case Environment.PRODUCTION:
	//production
		log.info("Resources environment : production")
		isCustomEnvironment = false
		
		userAtom="spm"
		passwordAtom="mVvCfeLe"
		atomPubUrl="http://172.16.6.146:8080/alfresco/cmisatom"
		repositoryId="a2115904-482e-4ced-9b61-7f84e5534b8f"
		spmPath="/spm/"

	//SGA
		wsdl="http://172.16.6.36:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"

	//SUSESO
		userSUSESO="isl"
		passwordSUSESO="345612"
		wsIngreso="http://siatep.suseso.cl:8888/Siatep/WSIngreso"
		wsToken="http://siatep.suseso.cl:8888/Siatep/WSToken"
		wsAnulacion="http://siatep.suseso.cl:8888/Siatep/WSAnulacion"

	//JBPM
		jbpmHost="172.16.6.149"
		jbpmPort="8080"
		break
}

if(customEnvironment == "desarrollo_isl"){
	println("| Resources environment : desarrollo_isl")
	//desarrollo_isl
	//atom upload service
	userAtom="spm"
	passwordAtom="ALVEPvhh"
	atomPubUrl="http://172.16.6.113:8080/alfresco/cmisatom"
	repositoryId="b08bed31-d80b-4b96-9ee6-8dc1223c59f8"
	spmPath="/spm/"

	//SGA
	wsdl="http://172.16.6.51:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"

	//SUSESO
	userSUSESO="isl"
	passwordSUSESO="345612"
	wsIngreso="http://siatepqa.suseso.cl:8888/Siatep/WSIngreso"
	wsToken="http://siatepqa.suseso.cl:8888/Siatep/WSToken"
	wsAnulacion="http://siatepqa.suseso.cl:8888/Siatep/WSAnulacion"
	
	//JBPM
	jbpmHost="172.16.6.114"
	jbpmPort="8080"
}else if(customEnvironment == "test_isl"){
	println(" | Resources environment : test_isl")
	//atom upload service
	userAtom="spm34"
	passwordAtom="u93ks_"
	atomPubUrl="http://172.16.6.128:8080/alfresco/s/cmis"
	repositoryId="8c4769cb-5aa9-441d-8290-b73668505419"
	spmPath="/spm52/"

	//SGA
	wsdl="http://172.16.6.52:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"

	//SUSESO
	userSUSESO="isl"
	passwordSUSESO="345612"
	wsIngreso="http://siatepqa.suseso.cl:8888/Siatep/WSIngreso"
	wsToken="http://siatepqa.suseso.cl:8888/Siatep/WSToken"
	wsAnulacion="http://siatepqa.suseso.cl:8888/Siatep/WSAnulacion"

	//JBPM
	jbpmHost="172.16.6.129"
	jbpmPort="8080"
}



beans = {
	uploadService(user:userAtom, AlfrescoService, password:passwordAtom,atomPubUrl:atomPubUrl,repositoryId:repositoryId,spmPath:spmPath)

	//si el entorno corresponde a desarrollo o desarrollo_isl utiliza servicios dummy
	if(customEnvironment == "desarrollo_isl" || Environment.current == Environment.DEVELOPMENT){
		println("| Definiendo bean para : desarrollo ISL y desarrollo")
		SGAService(SGADummyService) //Enviado por gmanzur en email del 22-10-2013
		
		//SUSESOService(SUSESODummyService)
		SUSESOService(username:userSUSESO,SUSESOWebService,password:passwordSUSESO,WSIngreso:wsIngreso,WSToken:wsToken,WSAnulacion:wsAnulacion)
		
	}else{
		SGAService(wsdl:wsdl,SGAWebService) //Enviado por gmanzur en email del 22-10-2013
	
		SUSESOService(username:userSUSESO,SUSESOWebService,password:passwordSUSESO,WSIngreso:wsIngreso,WSToken:wsToken,WSAnulacion:wsAnulacion)
	}

	JBPMService(jbpmHost: jbpmHost, JBPMRestService, jbpmPort: jbpmPort)

	CredentialsMatcher(Sha1CredentialsMatcher) {
		storedCredentialsHexEncoded = true
	}




	//Inyeccion por Ambiente
/*Environment.executeForCurrentEnvironment {

		//Ambiente de desarrollo
		development {
			//atom upload service
			uploadService(AlfrescoService){ //Segun correo enviado por csaintpierre el 22-oct-2013
				user="spm" //master
				password="ALVEPvhh" //master
				//user="spm34" //rama treintaycuatro
				//password="u93ks_" //rama treintaycuatro
				atomPubUrl="http://172.16.6.113:8080/alfresco/cmisatom"
				repositoryId="b08bed31-d80b-4b96-9ee6-8dc1223c59f8" //se obtiene con http://172.16.6.113:8080/alfresco/service/cmis/index.html
				spmPath="/spm/"
			}
			SGAService(SGADummyService){
			}
			SUSESOService(SUSESODummyService){
			}
			JBPMService(JBPMRestService){
				//jbpmHost='172.16.6.114' //BPM Q.A
				//jbpmPort='8080'
				//momentaneamente se accedera a BRMS de qa
				jbpmHost='172.16.6.114' //BRMS VIP(129): Los nodos son: 172.16.6.124 y 172.16.6.125
				jbpmPort='8080'
			}
			CredentialsMatcher(Sha1CredentialsMatcher) {
				storedCredentialsHexEncoded = true
			}
		}
		//Personalizacion de ambiente local
		desarrollo_isl { //Desarrollo ISL (single JBOSS)
			uploadService(AlfrescoService){ //Segun correo enviado por csaintpierre el 22-oct-2013
				user="spm" //master
				password="ALVEPvhh" //master
				//user="spm34" //rama treintaycuatro
				//password="u93ks_" //rama treintaycuatro
				atomPubUrl="http://172.16.6.113:8080/alfresco/cmisatom"
				repositoryId="b08bed31-d80b-4b96-9ee6-8dc1223c59f8" //se obtiene con http://172.16.6.113:8080/alfresco/service/cmis/index.html
				spmPath="/spm/"
			}
			SGAService(SGAWebService){
				//wsdl="http://172.16.6.51:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"
				wsdl="http://172.16.6.52:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"
			}
			SUSESOService(SUSESOWebService){
				username="isl";
				password="345612";
				WSIngreso="http://siatepqa.suseso.cl:8888/Siatep/WSIngreso";
				WSToken="http://siatepqa.suseso.cl:8888/Siatep/WSToken"
				WSAnulacion="http://siatepqa.suseso.cl:8888/Siatep/WSAnulacion";
			}
			JBPMService(JBPMRestService){
				jbpmHost='172.16.6.114' //BPM desarrollo
				jbpmPort='8080'
			}
			CredentialsMatcher(Sha1CredentialsMatcher) {
				storedCredentialsHexEncoded = true
			}
		}
		//Ambiente Q.A
		test { //QA ISL (cluster JBOSS)
			//atom upload service
			//JBPM
			jbpmHost="172.16.6.129"
			jbpmPort="8080"
			uploadService(AlfrescoService){
				user="spm34" //rama treintaycuatro
				password="u93ks_" //rama treintaycuatro
				atomPubUrl="http://172.16.6.128:8080/alfresco/s/cmis"
				repositoryId="8c4769cb-5aa9-441d-8290-b73668505419" //Segun http://172.16.6.128:8080/alfresco/service/cmis/index.html
				spmPath="/spm52/"
			}
			SGAService(SGAWebService){
				wsdl="http://172.16.6.52:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"
			}
			SUSESOService(SUSESOWebService){
				username="isl";
				password="345612";
				WSIngreso="http://siatepqa.suseso.cl:8888/Siatep/WSIngreso";
				WSToken="http://siatepqa.suseso.cl:8888/Siatep/WSToken"
				WSAnulacion="http://siatepqa.suseso.cl:8888/Siatep/WSAnulacion";
			}
			JBPMService(JBPMRestService){
				jbpmHost='172.16.6.129' //BRMS VIP(129): Los nodos son: 172.16.6.124 y 172.16.6.125
				//jbpmHost='172.16.6.124' //directo a nodo
				jbpmPort='8080'
			}
			CredentialsMatcher(Sha1CredentialsMatcher) {
				storedCredentialsHexEncoded = true
			}
		}
		//Ambiente Q.A adaptado para testear comportamiento en ambiente de desarrollo
		test_isl {
			uploadService(AlfrescoService){
				user="spm34" //rama treintaycuatro
				password="u93ks_" //rama treintaycuatro
				atomPubUrl="http://172.16.6.128:8080/alfresco/s/cmis"
				repositoryId="8c4769cb-5aa9-441d-8290-b73668505419" //Segun http://172.16.6.128:8080/alfresco/service/cmis/index.html
				spmPath="/spm52/"
			}
			SGAService(SGAWebService){
				wsdl="http://172.16.6.52:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"
			}
			SUSESOService(SUSESOWebService){
				username="isl";
				password="345612";
				WSIngreso="http://siatepqa.suseso.cl:8888/Siatep/WSIngreso";
				WSToken="http://siatepqa.suseso.cl:8888/Siatep/WSToken"
				WSAnulacion="http://siatepqa.suseso.cl:8888/Siatep/WSAnulacion";
			}
			JBPMService(JBPMRestService){
				jbpmHost='172.16.6.129' //BRMS VIP(129): Los nodos son: 172.16.6.124 y 172.16.6.125
				jbpmPort='8080'
			}
			CredentialsMatcher(Sha1CredentialsMatcher) {
				storedCredentialsHexEncoded = true
			}
		}
		//Ambiente productivo
		production { //Produccion ISL (cluster JBOSS)
			uploadService(AlfrescoService){
				user="spm"
				password="mVvCfeLe"
				atomPubUrl="http://172.16.6.146:8080/alfresco/cmisatom"
				repositoryId="a2115904-482e-4ced-9b61-7f84e5534b8f"
				spmPath="/spm/"
			}
			SGAService(SGAWebService){ //Enviado por gmanzur en email del 22-10-2013
				wsdl="http://172.16.6.36:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl"
			}
			SUSESOService(SUSESOWebService){
				username="isl";
				password="345612";
				WSIngreso="http://siatep.suseso.cl:8888/SusesoSiatep/WSIngreso";
				WSToken="http://siatep.suseso.cl:8888/SusesoSiatep/WSToken"
				WSAnulacion="http://siatep.suseso.cl:8888/SusesoSiatep/WSAnulacion";
			}
			JBPMService(JBPMRestService){
				// BRMS Nodo 1: 172.16.6.137
				// BRMS Nodo 2: 172.16.6.138
				//jbpmHost='172.16.6.149'
				jbpmHost='172.16.6.149'
				jbpmPort='8080'
			}
			CredentialsMatcher(Sha1CredentialsMatcher) {
				storedCredentialsHexEncoded = true
			}
		}
	}*/


}


