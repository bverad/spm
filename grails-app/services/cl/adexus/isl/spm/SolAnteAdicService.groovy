package cl.adexus.isl.spm

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;
import cl.adexus.helpers.FormatosHelper;


class SolAnteAdicService {

	def SiniestroService
	def JBPMService

	def solicitudAntecedentesAdicionalesProcessName = 'cl.isl.spm.calor.calor-solantedic'

	//guarda los datos de la solicitud de antecedentes
	def postDp01(params){
		log.info("Ejecutando metodo postDp01")
		log.info("Datos recibidos : $params")

		def siniestro = Siniestro.findById(params.siniestroId)
		def antecedentes = AntecedenteAdicional.findById(params.antecedentesId)
		def error = 0
		def next

		if (!antecedentes){
			antecedentes = new AntecedenteAdicional()
			antecedentes.siniestro = siniestro
			antecedentes.estado = false
			antecedentes.fechaSolicitud = new Date()
		}

		(params?.tipoAntecedente != '0') 	? (antecedentes.tipoAntecedente = TipoAntecedente.findByCodigo(params.tipoAntecedente)) : (error = 1)
		(params?.regionResponsable != '0') 	? (antecedentes.regionResponsable = Region.findByCodigo(params.regionResponsable)) : (error = 2)
		antecedentes.solicitud = params.solicitud

		//Validación del formulario
		if(!antecedentes.validate() || error > 0){
			log.info("Errores de validacion al ingresar antecedentes")
			next = [next: [action: 'dp01', controller: 'SolAnteAdic'],error: error,
				model:[siniestroId: params?.siniestroId,
					taskId: params?.taskId,
					backTo: params?.backTo,
					backToController: params?.backToController,
					solicitud: params?.solicitud,
					volverSeguimiento: params.volverSeguimiento,
					volverHistorial: params?.volverHistorial,
					verDetalle: params.verDetalle,
					origen: params.origen,
					cesarODA: params.cesarODA,
					recaOrigen: params?.recaOrigen],
				antecedentes: antecedentes ]
		}else{
			log.info("Almacenando antecedentes")
			antecedentes.save(flush:true)
			//Guardamos la informacion para el bpm
			def data = [responsable: antecedentes.tipoAntecedente.toBpm, solicitudId: antecedentes.id ]
			//redireccionando segun corresponda (desde seguimiento, y desde calificacion)
			def action = params?.backTo
			def controller = params?.backToController
			if(params?.origen && !params?.origen.equals("")){
				controller = "seguimiento"
				switch(params.origen){
					case "dp03":
						action = "dp04"
						break
					case "dp11":
						action = "dp04"
						break
					case "0" :
						log.info("Seteando reca origen")
						controller = params?.recaOrigen
					//controla que retorno venga desde edición de antecedentes desde la creación
						if(params?.volverAntecedentes && !params?.volverAntecedentes("")){
							controller = "solAnteAdic"
							action = "dp01"
						}
						break
					case "1" :
						log.info("Seteando reca origen")
						controller = params?.recaOrigen
						if(params?.volverAntecedentes && !params?.volverAntecedentes.equals("")){
							controller = "solAnteAdic"
							action = "dp01"
						}
						break
				}
			}else{
				controller = params?.recaOrigen
				//controla que retorno venga desde edición de antecedentes desde la creación
				if(params?.volverAntecedentes && !params?.volverAntecedentes("")){
					controller = "solAnteAdic"
					action = "dp01"
				}
			}

			log.info("redirigiendo a controller : $controller accion : $action")
			next = [next: [action: action, controller: controller], error: error,
				model:[siniestroId: params?.siniestroId,
					taskId: params?.taskId,
					backTo: params?.backTo,
					backToController: params?.backToController,
					solicitud: params?.solicitud,
					volverSeguimiento: params.volverSeguimiento,
					volverHistorial: params?.volverHistorial,
					verDetalle: params.verDetalle,
					origen: params.origen,
					cesarODA: params.cesarODA,
					recaOrigen: params?.recaOrigen],
				data: data]
		}

		return next
	}

	//inicia el proceso
	def startsolicitudAntecedentesAdicionales(user,pass,data){
		log.info("Ejecutando metodo startsolicitudAntecedentesAdicionales")
		log.info("params : user: $user, pass: $pass, data: $data")
		def solicitudId=data.solicitudId
		def responsable=data.responsable

		// Invocamos al BPM
		def bpmParams = ['solicitudId': solicitudId.toString(), 'responsable': responsable, user:user]
		def r = JBPMService.processComplete(user, pass, solicitudAntecedentesAdicionalesProcessName, bpmParams)
		log.info(solicitudAntecedentesAdicionalesProcessName+"::processComplete result:"+r)
		return r
	}

	//termina la tarea
	def completeDp01(user, pass, taskId, data){
		// Invocamos al BPM
		def dataToBpm=[taskResponsable: (data.responsable).toString(),
			taskSolicituId: (data.solicitudId).toString(), taskUser_:user]

		log.info("taskId:"+taskId)
		log.info("dataToBpm:"+dataToBpm)

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'
	}

	//guarda los datos de la respuesta a la solicitud de antecedentes
	def postDp02(params){

		log.info "POSTDP02 SOLANTEADIC PARAMETROS -> "+params
		def siniestro = Siniestro.findById(params.siniestroId)
		def antecedentes = AntecedenteAdicional.findById(params.solicitudId)
		def data=[:]

		if (params?.respuesta){
			antecedentes.respuesta = params?.respuesta
			antecedentes.estado = true
			antecedentes.fechaRespuesta = new Date()
		}
		if (antecedentes.save(flush: true)){
			log.info "Antecedente::Respuesta -> "+params?.respuesta
			data=[respuesta:true]
		}

		return data

	}

	//termina la solicitud
	def completeDp02(user, pass, taskId, data){
		// Invocamos al BPM
		def dataToBpm=[taskRespuesta: (data.respuesta).toString(), taskUser_:user]

		log.info("taskId:"+taskId)
		log.info("dataToBpm:"+dataToBpm)

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'

	}

	def goBack(params){
		log.info("Ejecutando metodo goBack")
		log.info("Datos recibidos : $params")
		def model = [siniestroId:params.siniestroId,
			volverSeguimiento: params.volverSeguimiento,
			volverHistorial: params?.volverHistorial,
			verDetalle: params.verDetalle,
			origen: params.origen,
			cesarODA: params.cesarODA]

		params?.taskId? model+=[taskId: params?.taskId] : null
		//si existe un origen valido redirecciona segun corresponda
		def action = params?.backTo
		def controller = params?.backToController
		if(params?.origen && !params?.origen.equals("")){
			controller = "seguimiento"
			log.info "Campo origen viene con valores seteando controller : $controller"
			switch(params.origen){
				case "dp03":
					log.info "case dp03"
					action = "dp04"
					break
				case "dp11":
					action = "dp04"
					break
				case "0" :
					log.info("Seteando reca origen")
					controller = params?.recaOrigen
					if(params?.volverAntecedentes && !params?.volverAntecedentes.equals("")){
						controller = "solAnteAdic"
						action = "dp01"
					}
					break
				case "1" :
					log.info("Seteando reca origen")
					controller = params?.recaOrigen
					if(params?.volverAntecedentes && !params?.volverAntecedentes.equals("")){
						controller = "solAnteAdic"
						action = "dp01"
					}
					break
			}
		}else{
			log.info("go back desde reca")
			controller = params?.recaOrigen
			if(params?.volverAntecedentes && !params?.volverAntecedentes.equals("") && !action.equals("dp02")){
				controller = "solAnteAdic"
				action = "dp01"
			}
		}

		model += params
		log.info("goback redirigiendo controller: ${controller} - accion : $action")

		return ([next: [action: action, controller: controller], model: model])
	}

}
