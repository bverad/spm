package cl.adexus.isl.spm

import org.apache.shiro.SecurityUtils

import grails.converters.JSON
import grails.util.GrailsUtil;
import cl.adexus.isl.spm.helpers.TaskHelper
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH


class INBOXController {

	def JBPMService
	def TaskDataService
	def uploadService
	def SGAService
	def SUSESOService
	

	def bpmPassword='1234'
	def index () {
		log.debug("Entorno actual : " + GrailsUtil.environment)
		log.debug("grails.dbconsole.enabled : ${CH.config.grails.dbconsole.enabled}")
		log.debug("grails.logging.jul.usebridge : ${CH.config.grails.logging.jul.usebridge}")

		redirect (action: 'dp01')
	}

	def dp01 () {
		log.debug("Ejecutando dp01")

		def user = SecurityUtils.subject?.principal
		def bpmUsername = user
		def taskData=[:]
		def sucursalData = [:]
		def personalTasks = []
		def groupTasks = []

		
		try{
			log.info("Listando tareas personales para : ${bpmUsername}")
			log.info("Los datos de autenticacion son los siguientes: user: $bpmUsername, pass: $bpmPassword")
			
			log.info("Los datos de uploadService son los siguientes:")
			log.info("--> user : ${uploadService.user}")
			log.info("--> pass : ${uploadService.password}")
			log.info("--> atomPubUrl : ${uploadService.atomPubUrl}")
			log.info("--> repositoryId : ${uploadService.repositoryId}")
			log.info("--> spmPath: ${uploadService.spmPath}")
			log.info("Los datos de SGAService son los siguientes:")
			log.info("-->SGAService.class : ${SGAService.getClass().getName()}")
			if(!SGAService.getClass().getName().equals("cl.adexus.isl.spm.SGADummyService"))
				log.info("--> ${SGAService?.wsdl}")
			
			log.info("Los datos de SUSESOService son los siguientes:")
			log.info("-->SUSESOService.class : ${SUSESOService.getClass().getName()}")
			if(!SUSESOService.getClass().getName().equals("cl.adexus.isl.spm.SUSESODummyService")){
				log.info("--> user: ${SUSESOService?.username}")
				log.info("--> pass: ${SUSESOService?.password}")
				log.info("--> wsIngreso: ${SUSESOService?.WSIngreso}")
				log.info("--> wsToken: ${SUSESOService?.WSToken}")
				log.info("--> wsAnulacion: ${SUSESOService?.WSAnulacion}")
			}

			//log.info("Ubicacion BRMS : host:${JBPMService.jbpmHost}, port:${JBPMService.jbpmPort}")
			personalTasks = JBPMService.tasks(bpmUsername,bpmPassword,bpmUsername)
			log.info("Verificando si usuario : $bpmUsername tiene tareas personales asignadas")
			if(personalTasks){
				personalTasks.each(){
					log.debug("User:"+bpmUsername+" task personal:"+it)
					def dataset=JBPMService.dataset(bpmUsername,bpmPassword,it.processInstanceId);
					taskData[it.id]=TaskDataService.taskData(it.processId,it.name,dataset);
				}
			}else
				log.info("usuario $bpmUsername no tiene tareas personales")
			log.info("Listado de tareas personales finalizado")


			log.info("Listando tareas grupales para : $bpmUsername")
			groupTasks=[]
			def allGroupTasks = JBPMService.taskParticipation(bpmUsername,bpmPassword,bpmUsername)
			log.info("Verificando tareas grupales")
			if(allGroupTasks){
				log.info("Existen tareas grupales")
				allGroupTasks.each() {
					log.info("User:"+bpmUsername+" task grupal: ${it.processId}")
					if(it.currentState != 'ASSIGNED'){ //Solo interesan las que no estan asignadas
						def dataset=JBPMService.dataset(bpmUsername,bpmPassword,it.processInstanceId);
						def usuario = ShiroUser.findByUsername(dataset.user)
						//si la sucursal no corresponde a nivel central
						def sucursal = usuario?.sucursal?.name
						def region = usuario?.agencia?.region?.name
						
						if(!usuario?.agencia){
							sucursal = usuario?.unidadOrganizativa?.name //si corresponde a nivel central
							region = usuario?.unidadOrganizativa?.region?.name
						}
								
						sucursalData[it.id] = [region : region, sucursal: sucursal, usuario: usuario?.username]
						taskData[it.id]=TaskDataService.taskData(it.processId,it.name,dataset);

						//Vemos si es valido por geolocalizacion
						def validoParaUsuario=TaskDataService.validoParaUsuario(taskData[it.id],user);

						if(validoParaUsuario){
							groupTasks.add(it)
						}
					}
				}
				
				log.debug("Sucursal data : $sucursalData")
			}else{
				log.info("No existen tareas grupales")
			}
			log.info("Listado de tareas grupales finalizado")

		}catch(e){
			//Se limpian los campos en el caso de que el error surja posterior a su seteo
			taskData=[:]
			personalTasks = []
			groupTasks = []
			log.error("Error no controlado: ${e.message}")
			e.printStackTrace()
			//generando mensaje de error
			flash.message = "cl.adexus.isl.spm.inbox.display.fail"
			flash.args = []
			flash.default = "Error no controlado: ${e.message}"
		}


		render(view: 'dp01', model: [personalTasks: personalTasks, groupTasks: groupTasks, taskData: taskData, sucursalData:sucursalData])
	}

	def claimTask(){
		def user=SecurityUtils.subject?.principal
		def bpmUsername = user
		def id = params.id
		log.debug("claimTask :"+ id);
		//Busca primero si ya no esta asignada
		def asignadaDeAntes=true;

		def allGroupTasks = JBPMService.taskParticipation(bpmUsername,bpmPassword,bpmUsername)
		allGroupTasks.each() {
			log.debug("User:"+bpmUsername+" task grupal:"+it)
			if(it.id.toString()==id && it.currentState != 'ASSIGNED'){ //Si no esta asignada a mi quizas
				log.debug("Encontre para asignar!!!")
				def bpmTask= JBPMService.assign(bpmUsername,bpmPassword,id,bpmUsername)
				asignadaDeAntes=false;
			}
		};
		if(asignadaDeAntes==true){
			log.debug("Ya estaba asignada!");
			flash.put("message", "La tarea ya estaba asignada.");
		}
		redirect (action: 'dp01')
	}

	def resolveTask () {
		def user=SecurityUtils.subject?.principal
		def bpmUsername = user

		def id = params.id
		//TODO: Talvez debiera guardarlo en session y leerlo de ahi? (ajunge)
		def personalTasks = JBPMService.tasks(bpmUsername,bpmPassword,bpmUsername)
		def bpmTask;
		personalTasks.each() {
			if(it.id.toString()==id){
				bpmTask=it;
			}
		};
		log.info "bpmTask(${id}):"+bpmTask

		//Traer el dataset
		def processInstanceId=bpmTask.processInstanceId;
		log.info "processInstanceId:"+processInstanceId
		def dataset=JBPMService.dataset(bpmUsername,bpmPassword,processInstanceId)

		def toParams=dataset
		toParams['taskId']=bpmTask.id
		toParams['processInstanceId']=bpmTask.processInstanceId
		log.info "toParams:"+toParams


		//Formulario a mostrar
		log.info 'bpmTask.name: ' + bpmTask.processId+"."+bpmTask.name
		def to=TaskHelper.taskForm(bpmTask.processId+"."+bpmTask.name)
		to['params']=toParams

		log.info to
		redirect (to)
	}

}
