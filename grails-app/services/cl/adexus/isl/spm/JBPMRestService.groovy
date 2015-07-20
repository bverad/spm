package cl.adexus.isl.spm


import com.cuyum.jbpm.client.BRMSClient;
import com.cuyum.jbpm.client.BRMSClientImpl;
import com.cuyum.jbpm.client.artifacts.responses.GETParticipationsTasksResponse;
import com.cuyum.jbpm.client.artifacts.responses.POSTCreateInstanceResponse;
import com.cuyum.jbpm.client.artifacts.responses.GETAssignedTasksResponse;
import com.cuyum.jbpm.client.artifacts.responses.GETParticipationsTasksResponse
import com.cuyum.jbpm.client.artifacts.responses.POSTClaimTaskResponse
import com.cuyum.jbpm.client.artifacts.responses.GETDatasetInstanceResponse
import com.cuyum.jbpm.client.artifacts.responses.POSTUpdateTaskResponse

import grails.converters.JSON
import grails.converters.XML
import java.util.concurrent.ConcurrentHashMap
import grails.util.Environment


class JBPMRestService {

	//Este parametro debe ser "inyectado"
	def jbpmHost
	def jbpmPort

	//Definicion de verbos REST en: baseURL+"/rs/server/resources/jbpm"
	//Devel: http://172.16.6.114:8080/business-central-server/rs/server/resources/jbpm


	/**
	 * Lista de tareas personales del username
	 * @param username
	 * @param password
	 * @param idRef usuario al cual se le buscan las tareas
	 * @return
	 */
	def tasks(username,password,idRef){
		GETAssignedTasksResponse assignedTasks = _getLoggedClient(username,password).getAssignedTasks(idRef);
		return assignedTasks.tasks
	}

	/**
	 * Obtiene la lista de tareas grupales
	 * @param username
	 * @param password
	 * @param idRef usuario al cual se le buscan las tareas
	 * @return
	 */
	def taskParticipation(username,password,idRef){
		GETParticipationsTasksResponse participationTasks = _getLoggedClient(username,password).getParticipationsTasks(idRef);
		return participationTasks.tasks
	}

	/**
	 * Asigna una tarea a un usuario
	 * @param username
	 * @param password
	 * @param taskId id de tarea
	 * @param ifRef usuario al que se le asigna la tarea
	 * @return
	 */
	def assign(username,password,taskId,ifRef){
		POSTClaimTaskResponse response = _getLoggedClient(username,password).claimTask(taskId,ifRef);
		return response.status
	}

	/**
	 * Completa un proceso
	 * @param username
	 * @param password
	 * @param processId identificador de proceso (segun lo que sale en definitions) ej: islf1.diatoa1
	 * @param postData Datos para enviar 
	 * @return
	 */
	def processComplete(username,password,processId,Map postData){
		POSTCreateInstanceResponse instanceResponse = _getLoggedClient(username,password).createInstance(processId, postData);
		return instanceResponse.status
	}

	/**
	 * Trae los datos de la instancia de proceso
	 * 
	 * @param username
	 * @param password
	 * @param id de instancia de proceso
	 * @return Map con los datos
	 */
	def dataset(username,password,id){
		GETDatasetInstanceResponse d = _getLoggedClient(username,password).getDatasetInstance(id);
		return d.dataset
	}

	/**
	 * Completa una tarea
	 * @param username
	 * @param password
	 * @param processId identificador de proceso (segun lo que sale en definitions) ej: islf1.diatoa1
	 * @param postData Datos para enviar
	 * @return
	 */
	def taskComplete(username,password,taskId,Map postData){
		POSTUpdateTaskResponse instanceResponse = _getLoggedClient(username,password).updateTask(taskId, postData);
		return instanceResponse.status
	}

	//-----

	ConcurrentHashMap clientCache = [:]

	def _getLoggedClient(username,password){
		log.info("********** Ejecutando getLoggedClient ********** ")
		BRMSClient client;

		try{
			//Independiente del password que use el usuario, se buscar el hash que tiene en la base de usuarios.
			def nombre = username.split("@")
			def usuario=ShiroUser.findByUsername(nombre[0])
			password =  usuario.passwordHash

			//if(clientCache.containsKey(username)){
			//	log.info("_getLoggedClient:: reciclando cliente para "+username)
			//	client=clientCache.get(username)
			//}else{
			log.info("_getLoggedClient:: nuevo cliente para "+username+ " pass:"+password)
			log.info("Entorno actual : ${Environment.current} ")
			log.info("Datos de autenticacion : host : $jbpmHost , port: $jbpmPort")
			client=new BRMSClientImpl(jbpmHost, jbpmPort);
			clientCache.put(username,client);
			//}
			//if(!client.isLogged()){
			client.login(username, password);
			//}
		}catch(e){
			log.error("Error no controlado : ${e.message}")
			e.printStackTrace()
		}

		return client;
	}


}
