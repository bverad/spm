package cl.adexus.isl.spm

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;
import cl.adexus.helpers.FormatosHelper;
import org.apache.shiro.SecurityUtils



class CalOrigenService {

	def JBPMService
	def uploadService
	def SiniestroService
	def SUSESOService
	def UsuarioService
	def BISRevisionService

	def bpmPass = '1234'

	def calificacionOrigenATProcessName = 'cl.isl.spm.calor.calor-calificacion'
	def calificacionOrigenEPProcessName = 'cl.isl.spm.calor.calor-calificacion-ep'	
	//.....CALIFICACION ORIGEN AT .........

	def startCalificacionOrigenAT(user,pass,siniestroId){
		log.info("Ejecutando metodo startCalificacionOrigenAT")
		// Invocamos al BPM
		def bpmParams = ['siniestroId': siniestroId.toString(), user:user]
		def r = JBPMService.processComplete(user, pass, calificacionOrigenATProcessName, bpmParams)
		log.info(calificacionOrigenATProcessName+"::processComplete result:"+r)
		log.info("Ejecucion de metodo startCalificacionOrigenAT finalizada")
		return r
	}

	//Guarda la calificación y codificación //TODO:
	def postDp01At(params){
		log.info("Ejecutando metodo postDp01")
		log.info("Datos recibidos : $params")

		// Obtiene el siniestro y reca
		def siniestro = Siniestro.get(params.siniestroId)
		def reca = RECA.findBySiniestro(siniestro)
		def data = [:]

		// Verifica si existe la reca, sino, la crea.
		log.info("Verificando si RECA existe...")
		if (!reca){
			log.debug("RECA no existe, creando")
			reca = new RECA()
			reca.siniestro = siniestro
			reca.save()
			log.debug("RECA creada con exito")
		}

		def codigo_evento = reca?.calificacion?.eventoSiniestro?.codigo
		log.info "Evento :  ->$codigo_evento"

		// Guarda la calificación
		// Si viene de guardar o actualizar un diagnostico o solicitud de antecedentes
		if (params?.action == 'dp05' || params?.action == 'dp06' || params?.action == 'SolicitarAntecedentes'){
			(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
			(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
		}

		// Si vamos a calificar vemos si existe(nueva) o si estaba ya en la RECA
		if (params?.califica){
			if (reca.calificacion && params?.califica){
				if (reca.calificacion != TipoCalificacion.findByCodigo(params.califica)){
					(params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
					(params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
				}
			}else{
				(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
				(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
			}
		}else if(params?.origen != "0"){
			if (!reca.calificacion || (params?.origen && params?.tipoEvento)){
				(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
				(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
			}
		}else{
			data.status = 3
		}

		// Guarda la codificación solo si el tipo de evento es laboral
		if ((params?.tipoEvento && (params?.tipoEvento == "1" || params?.tipoEvento == "2")) || (codigo_evento =="1" || codigo_evento =="2")){
			// Comprueba que no entren datos nulos
			(params?.cod_forma != "0") 			? (reca.forma = CodigoForma.findByCodigo(params.cod_forma)) 						: (data.status = 4)
			(params?.cod_agente != "0")			? (reca.agenteAccidente = CodigoAgenteAccidente.findByCodigo(params.cod_agente))	: (data.status = 4)
			(params?.cod_intencion != "0") 		? (reca.intencionalidad = CodigoIntencionalidad.findByCodigo(params.cod_intencion)) : (data.status = 4)
			/*(params?.cod_modoTransporte != "0") ? (reca.transporte = CodigoModoTransporte.findByCodigo(params.cod_modoTransporte)) 	: (data.status = 4)
			(params?.cod_papelLesionado != "0") ? (reca.lesionado = CodigoPapelLesionado.findByCodigo(params.cod_papelLesionado)) 	: (data.status = 4)
			(params?.cod_contraparte != "0")  	? (reca.contraparte = CodigoContraparte.findByCodigo(params.cod_contraparte)) 		: (data.status = 4)
			(params?.cod_tipoEvento != "0")  	? (reca.evento = TipoEvento.findByCodigo(params.cod_tipoEvento)) 					: (data.status = 4)*/
		}

		if (params?.tipoEvento == "3" && params['resolucion']=='Calificar'){
			// Setea la calificacion a null, porque no es necesaria por el tipo de evento
			reca.forma = null
			reca.agenteAccidente = null
			reca.intencionalidad = null
			/*reca.transporte = null
			reca.lesionado = null
			reca.contraparte = null
			reca.evento = null*/
		}

		// Cuenta diagnosticos, verifica que pueda calificar
		if (params.origen == "1"){
			(Diagnostico.countBySiniestroAndEsLaboral(siniestro, true) > 0 ) ? (data.esLaboral = true) : (data.status = 1)
		}else if ((params.origen == "2") || (params.origen == "3")){
			((Diagnostico.countBySiniestroAndEsLaboral(siniestro, false) > 0) && (Diagnostico.countBySiniestroAndEsLaboral(siniestro, true) < 1)) ? (data.esLaboral = false) : (data.status = 2)
		}

		// Guarda, y envia de vuelta los datos para el bpm
		if(params['resolucion']=='Calificar' && data.status == null){
			// Se crea un seguimiento a siniestro sin seguimiento
			def sdaat = SDAAT.findBySiniestro(siniestro)
			def tNivelComplejidad

			//recatamos la complejidad calculada
			if (!sdaat){tNivelComplejidad = 0}
			else{tNivelComplejidad = (sdaat.cuestionarioComplejidad.complejidadCalculada).toInteger()}
			log.info "Complejidad calculada ->"+tNivelComplejidad

			//Evaluo la complejidad y creo el seguimiento de ser necesario
			if (tNivelComplejidad == 0){
				log.info "Creando seguimiento dummy para el siniestro ->"+siniestro.id
				def r = crearSeguimientoCuestionario(siniestro)
				if (r.error) {
					data.status = 5
				}
			}

			// Guardo la fecha de calificacion de la reca, y puedeCalificar para el BPM
			reca.fechaCalificacion = FechaHoraHelper.hace10minutos();
			data.puedeCalificar = true

			//Vemos si es 77bis
			def bis=Bis.findBySiniestro(siniestro);
			log.debug("BIS?:"+bis)
			if(bis){
				//Inicia la revision
				def user = UsuarioService.getUsuario(bis.creadoPor);
				log.debug("usuario:"+user.username+" iniciando revision de 77bis: "+bis)
				def b = BISRevisionService.start77bisRevision(user.username, bpmPass, bis.id)
			}

		} else {
			// Set puedeCalificar en false, para que pase al nivel 2
			data.puedeCalificar = false
		}

		// Guardo y retorno la data
		reca.save(flush: true)
		//guarda id de reca para rescatarla y recuperar xmlRecibido para tomar el estado y generar mensajeria.
		data.recaId = reca.id

		return data;

	}

	def crearSeguimientoCuestionario(siniestro) {
		log.info("Ejecutando metodo crearSeguimientoCuestionario")
		log.info("Siniestro : $siniestro")
		def seguimiento						= new Seguimiento()
		// Si es sin seguimiento validar si ya se creo la actividad de seguimiento
		seguimiento.siniestro				= siniestro
		seguimiento.usuario					= SecurityUtils.subject?.principal
		seguimiento.resumen					= "Siniestro con cuestionario de complejidad igual a cero"
		seguimiento.observaciones			= "Siniestro con cuestionario de complejidad igual a cero"
		seguimiento.nivel					= 0
		seguimiento.fechaIngreso 			= new Date()
		seguimiento.nivelComplejidadIngreso	= seguimiento.nivel
		seguimiento.fechaCambioNivel		= seguimiento.fechaIngreso
		seguimiento.fechaAlta				= seguimiento.fechaIngreso
		if (!seguimiento.validate()) {
			log.info "Error al guardar el seguimiento"
			return [error: true, seguimiento: seguimiento]
		}
		seguimiento.save()
		def tipoActividad = TipoActividadSeguimiento.findByCodigo('9')
		def actSeg = new ActividadSeguimiento(	[ seguimiento		: seguimiento
			, tipoActividad		: tipoActividad
			, fechaActividad	: seguimiento.fechaIngreso
			, resumen			: seguimiento.resumen
			, comentario		: seguimiento.observaciones]);
		if (!actSeg.validate()) {
			log.info "Error al guardar la actividad seguimiento"
			return [error: true, seguimiento: seguimiento, actSeg: actSeg]
		}
		actSeg.save();
		return [error: false]
	}

	//termina la tarea
	def completeDp01At(user, pass, taskId, data){
		log.info("Ejecutando metodo completeDp01At")
		log.info("params: user: $user, pass: $pass, taskId: $taskId, data: $data")
		// Invocamos al BPM
		def dataToBpm=[taskPuedeCalificar: (data.puedeCalificar).toString(),
			taskEsLaboral: (data.esLaboral).toString(), taskUser_:user]

		log.info("taskId:"+taskId)
		log.info("dataToBpm:"+dataToBpm)

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'

	}

	//Guarda la calificacion y codificacion nivel 2 //TODO
	def postDp02At(params){
		log.info("Ejecutando metodo postDp02At")
		log.info("Datos recibidos : $params")

		//obtiene el siniestro y reca
		def siniestro = Siniestro.get(params.siniestroId)
		def reca = RECA.findBySiniestro(siniestro)
		def data = [:]

		//verifica si existe la reca, sino, la crea.
		if (!reca){
			reca = new RECA()
			reca.siniestro = siniestro
			reca.save()
		}

		def codigo_evento = reca?.calificacion?.eventoSiniestro?.codigo
		log.info "EVENTO ->"+codigo_evento

		// Guarda la calificación
		// Si viene de guardar o actualizar un diagnostico o solicitud de antecedentes
		if (params?.action == 'dp05' || params?.action == 'dp06' || params?.action == 'SolicitarAntecedentes'){
			(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
			(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
		}

		// Si vamos a calificar vemos si existe(nueva) o si estaba ya en la RECA
		if (params?.califica){
			if (reca.calificacion && params?.califica){
				if (reca.calificacion != TipoCalificacion.findByCodigo(params.califica)){
					(params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
					(params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
				}
			}else{
				(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
				(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
			}
		}else if(params?.origen != "0"){
			if (!reca.calificacion || (params?.origen && params?.tipoEvento)){
				(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
				(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
			}
		}else{
			data.status = 3
		}

		// Guarda la codificación solo si el tipo de evento es laboral
		if ((params?.tipoEvento && (params?.tipoEvento == "1" || params?.tipoEvento == "2")) || (codigo_evento =="1" || codigo_evento =="2")){
			// Comprueba que no entren datos nulos
			(params?.cod_forma != "0") 			? (reca.forma = CodigoForma.findByCodigo(params.cod_forma)) 						: (data.status = 4)
			(params?.cod_agente != "0")			? (reca.agenteAccidente = CodigoAgenteAccidente.findByCodigo(params.cod_agente))	: (data.status = 4)
			(params?.cod_intencion != "0") 		? (reca.intencionalidad = CodigoIntencionalidad.findByCodigo(params.cod_intencion)) : (data.status = 4)
			(params?.cod_modoTransporte != "0") ? (reca.transporte = CodigoModoTransporte.findByCodigo(params.cod_modoTransporte)) 	: (data.status = 4)
			(params?.cod_papelLesionado != "0") ? (reca.lesionado = CodigoPapelLesionado.findByCodigo(params.cod_papelLesionado)) 	: (data.status = 4)
			(params?.cod_contraparte != "0")  	? (reca.contraparte = CodigoContraparte.findByCodigo(params.cod_contraparte)) 		: (data.status = 4)
			(params?.cod_tipoEvento != "0")  	? (reca.evento = TipoEvento.findByCodigo(params.cod_tipoEvento)) 					: (data.status = 4)
		}

		if (params?.tipoEvento == "3" && params['resolucion']=='Calificar'){
			//setea la calificacion a null, porque no es necesaria por el tipo de evento
			reca.forma = null
			reca.agenteAccidente = null
			reca.intencionalidad = null
			reca.transporte = null
			reca.lesionado = null
			reca.contraparte = null
			reca.evento = null
		}

		//Guarda la codificacion de agente
		if (params?.tipoEvento == "3" || reca?.eventoSiniestro?.codigo == "3"){
			// Guarda los datos de agente e indicación
			(params.indicacion != "")? (reca.indicacion = params.indicacion) : (data.status = 6)
			if (reca.codificacionAgente && params.cod_agente_6){
				if (reca.codificacionAgente != Agente.findByCodigo(params.cod_agente_6)){
					(params.cod_agente_6 != "0") ? (reca.codificacionAgente = Agente.findByCodigo(params.cod_agente_6)) : (data.status = 5)
				}
			}else if (params.cod_agente_6){
				(params.cod_agente_6 && params.cod_agente_6 != "0") ? (reca.codificacionAgente = Agente.findByCodigo(params.cod_agente_6)) : (data.status = 5)
			}else if(params.cod_agente_1 == "0" || !reca.codificacionAgente){
				log.info "NO EXISTE codificacion de Agentes"
				data.status = 5
			}
		}

		// Cuenta diagnosticos, verifica que pueda calificar
		if (params.origen == "1"){
			(Diagnostico.countBySiniestroAndEsLaboral(siniestro, true) > 0 ) ? (data.esLaboral = true) : (data.status = 1)
		}else if ((params.origen == "2") || (params.origen == "3")){
			((Diagnostico.countBySiniestroAndEsLaboral(siniestro, false) > 0) && (Diagnostico.countBySiniestroAndEsLaboral(siniestro, true) < 1)) ? (data.esLaboral = false) : (data.status = 2)
		}

		// Guarda, y envia de vuelta los datos para el bpm
		if(params['resolucion']=='Calificar' && data.status == null){
			// Guardo la fecha de calificacion de la reca, y puedeCalificar para el BPM
			reca.fechaCalificacion = FechaHoraHelper.hace10minutos();
			data.puedeCalificar = 'true'
			//Vemos si es 77bis
			def bis=Bis.findBySiniestro(siniestro);
			log.debug("BIS?:"+bis)
			if(bis){
				//Inicia la revision
				def user = UsuarioService.getUsuario(bis.creadoPor);
				log.debug("usuario:"+user.username+" iniciando revision de 77bis: "+bis)
				def b = BISRevisionService.start77bisRevision(user.username, bpmPass, bis.id)
			}
		}else{
			data.puedeCalificar = 'false'
		}

		// Guardo y retorno la data
		reca.save(flush: true)
		return data;

	}

	//termina la tarea nivel 2
	def completeDp02At(user, pass, taskId, data){
		log.info("Ejecutando metodo completeDp02At")
		log.info("params: user: $user, pass: $pass, taskId: $taskId, data: $data")
		// Invocamos al BPM
		def dataToBpm=[success: data.puedeCalificar, taskUser_:user]

		log.info("taskId:"+taskId)
		log.info("dataToBpm:"+dataToBpm)

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'

	}

	//Direcciona a solicitar mas antecedentes, SolAnteAdic/dp01
	def SolicitarAntecedentes(params){
		log.info("Ejecutando metodo SolicitarAntecedentes")
		log.info("params: $params")
		def model = [	siniestroId:		params.siniestroId,
			backTo:				params.backTo,
			backToController:	params.backToController,
			taskId:				params.taskId,
			recaOrigen:			params?.recaOrigen,
			antecedentesOrigen: params?.antecedentesOrigen
		]
		model += params
		return ([next: [action: 'dp01', controller: 'SolAnteAdic'], model: model])
	}

	//Direcciona a ver solicitudes de antecedentes, SolAnteAdic/dp03
	def verSolicitud(params){
		log.info("Ejecutando metodo verSolicitud")
		log.info("params: $params")
		log.info("Redireccion a ver solicitud de antecedentes, antecedente ID: " + params.antecedenteId)
		def model = [	siniestroId:		params.siniestroId,
			antecedenteId:		params.antecedenteId,
			backTo:				params.backTo,
			backToController:	params.backToController,
			taskId:				params.taskId,
			recaOrigen:			params?.recaOrigen,
			antecedentesOrigen: params?.antecedentesOrigen
		]
		//model += params
		return ([next: [action: 'dp03', controller: 'SolAnteAdic'], model: model])
	}

	//Guarda la nueva complejidad
	def postDp03At(params){
		log.info("Ejecutando metodo postDp03At")
		log.info("params: $params")

		//Obtiene el siniestro
		def siniestro = Siniestro.get(params.siniestroId)
		def nivelComplejidad
		def data=[:]

		//Guarda la calificación
		log.info("Evaluando complejidad : ${params?.eval_complejidad}")
		if (params?.eval_complejidad){
			nivelComplejidad = (params.eval_complejidad).toInteger()
			siniestro.nivelComplejidad = nivelComplejidad
			// Simular ingreso de seguimiento
			if (siniestro.nivelComplejidad == 0 && (params['resolucion']=='Asignar')) {
				def odaList = ODA.findAllBySiniestroAndCesadaIsNull(siniestro)
				if(odaList)
					return false
				
				def r = crearSeguimiento(siniestro)
				if (r.error) {
					log.info "No se pudo crear simulación de seguimiento en AT"
					return r.error
				}
			}
			def r = params['resolucion']=='Asignar' ? (siniestro.fechaComplejidad = new Date()) : null
		}

		//Guarda, si no pasa se va a la shit
		if (!siniestro.save(flush: true)){
			log.info("Registrando error : hubo un error al almacenar siniestro")
			return false
			
		}

		data.nivelComplejidad = nivelComplejidad;

		return data;

	}

	//Termina la tarea at
	def completeDp03At(user, pass, taskId, data){
		// Invocamos al BPM
		log.info("Ejecutando completeDp03At")
		log.info("params: user: $user, pass: $pass, taskId: $taskId, data: $data")

		def dataToBpm=[taskNivelComplejidad: (data.nivelComplejidad).toString(), taskUser_:user]

		log.info("dataToBpm: $dataToBpm")

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'

	}

	def postDp04At(params){
		log.info("Ejecutando postDp04At")
		log.info("params: $params")

		//obtiene el siniestro
		def siniestro = Siniestro.findById(params.siniestroId)
		def diatOA = siniestro.diatOA
		def reca = RECA.findBySiniestro(siniestro)

		//creo la diepOA
		def diepOA = siniestro.diepOA
		if (!diepOA) { diepOA = new DIEP() }

		//traspaso todos los datos que ya se validaron en DIATOA y que estan presenten tambien en DIEPOA
		//ZONA A
		diepOA.fechaEmision						=	FechaHoraHelper.hace10minutos();
		diepOA.empleador						=	diatOA?.empleador
		diepOA.ciiuEmpleador					=	diatOA?.ciiuEmpleador
		diepOA.ciiuPrincipal					=	diatOA?.ciiuPrincipal
		diepOA.direccionEmpleadorTipoCalle		=	diatOA?.direccionEmpleadorTipoCalle
		diepOA.direccionEmpleadorNombreCalle	=	diatOA?.direccionEmpleadorNombreCalle
		diepOA.direccionEmpleadorNumero			=	diatOA?.direccionEmpleadorNumero
		diepOA.direccionEmpleadorRestoDireccion	=	diatOA?.direccionEmpleadorRestoDireccion
		diepOA.direccionEmpleadorComuna			=	diatOA?.direccionEmpleadorComuna
		diepOA.telefonoEmpleador				=	diatOA?.telefonoEmpleador
		diepOA.nTrabajadoresHombre				=	diatOA?.nTrabajadoresHombre
		diepOA.nTrabajadoresMujer				=	diatOA?.nTrabajadoresMujer
		diepOA.propiedadEmpresa					=	diatOA?.propiedadEmpresa
		diepOA.tipoEmpresa						=	diatOA?.tipoEmpresa
		//ZONA B
		diepOA.trabajador						=	diatOA?.trabajador
		diepOA.nacionalidadTrabajador			=	diatOA?.nacionalidadTrabajador
		diepOA.direccionTrabajadorTipoCalle		=	diatOA?.direccionTrabajadorTipoCalle
		diepOA.direccionTrabajadorNombreCalle	=	diatOA?.direccionTrabajadorNombreCalle
		diepOA.direccionTrabajadorNumero		=	diatOA?.direccionTrabajadorNumero
		diepOA.direccionTrabajadorRestoDireccion=	diatOA?.direccionTrabajadorRestoDireccion
		diepOA.direccionTrabajadorComuna		=	diatOA?.direccionTrabajadorComuna
		diepOA.telefonoTrabajador				=	diatOA?.telefonoTrabajador
		diepOA.etnia							=	diatOA?.etnia
		diepOA.otroPueblo						=	diatOA?.otroPueblo
		diepOA.profesionTrabajador				=	diatOA?.profesionTrabajador
		diepOA.fechaIngresoEmpresa				=	diatOA?.fechaIngresoEmpresa
		diepOA.duracionContrato					=	diatOA?.duracionContrato
		diepOA.tipoRemuneracion					=	diatOA?.tipoRemuneracion
		diepOA.categoriaOcupacion				=	diatOA?.categoriaOcupacion
		//ZONA C
		diepOA.sintoma							=	params.sintoma
		diepOA.parteCuerpo 						=	params.parteCuerpo
		diepOA.descripcionTrabajo 				=	params.descripcionTrabajo
		diepOA.puestoTrabajo 					=	params.puestoTrabajo
		diepOA.agenteSospechoso 				= 	params.agenteSospechoso
		diepOA.fechaSintoma						=	FechaHoraHelper.stringToDate(params.fechaSintoma)

		if (FormatosHelper.fechaCortaStatic(params.fechaAgente) <= FormatosHelper.fechaCortaStatic(FechaHoraHelper.stringToDate(params.fechaSintoma)))
			diepOA.fechaAgente					=	params.fechaAgente
		else diepOA.fechaAgente					=	null

		diepOA.fechaAgente						=	params.fechaAgente
		diepOA.esAntecedenteCompanero 			= 	FormatosHelper.booleanString(params.esAntecedenteCompanero)
		diepOA.esAntecedentePrevio 				= 	FormatosHelper.booleanString(params.esAntecedentePrevio)
		//ZONA F
		diepOA.denunciante						=	diatOA?.denunciante
		diepOA.calificacionDenunciante			=	diatOA?.calificacionDenunciante
		diepOA.telefonoDenunciante				=	diatOA?.telefonoDenunciante
		diepOA.codigoActividadEmpresa			=	diatOA?.codigoActividadEmpresa

		//Asocia al siniestro
		diepOA.siniestro = siniestro

		if (!diepOA.validate()){
			def next = [next: [action: 'dp04', controller: 'calOrigenAT'],error: 1, model: [siniestroId: params?.siniestroId, taskId: params?.taskId, diep: diepOA]]
			return next
		}
		//Se actualiza el siniestro con los datos oficiales de la DIEP OA
		diepOA.save()
		siniestro.diepOA=diepOA

		//Guarda el siniestro
		if (!siniestro.save(flush: true)){
			return false;
		}
		//Datos BPM
		def data=[:]
		data.siniestroId = params.siniestroId

		return data;
	}

	def completeDp04At(user, pass, taskId, data){
		log.info("Ejecutando completeDp04At")
		log.info("params: user: $user, pass: $pass, taskId: $taskId, data: $data")
		// Invocamos al BPM
		def dataToBpm=[taskSiniestroId: (data.siniestroId).toString(), taskUser_:user]

		log.info("taskId:"+taskId)
		log.info("dataToBpm:"+dataToBpm)

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'
	}

	//////////////////........CALIFICACION ORIGEN EP .........//////////////////



	def startCalificacionOrigenEP(user,pass,siniestroId){
		log.info("Ejecutando startCalificacionOrigenEP")
		log.info("params: user: $user, pass: $pass, siniestroId: $siniestroId")
		// Invocamos al BPM
		def bpmParams = ['siniestroId': siniestroId.toString(), user:user]
		def r = JBPMService.processComplete(user, pass, calificacionOrigenEPProcessName, bpmParams)
		log.info(calificacionOrigenEPProcessName+"::processComplete result:"+r)

		return r
	}

	//Guarda la calificación y codificación  //TODO:
	def postDp01Ep(params){
		log.info("Ejecutando postDp01Ep")
		log.info("params: $params")
		//Obtiene el siniestro y reca
		def siniestro = Siniestro.get(params.siniestroId)
		def reca = RECA.findBySiniestro(siniestro)
		def data = [:]

		//verifica si existe la reca, sino, la crea.
		if (!reca){
			reca = new RECA()
			reca.siniestro = siniestro
			reca.save()
		}

		// Guarda la calificación
		// Si viene de guardar o actualizar un diagnostico o solicitud de antecedentes
		if (params?.action == 'dp04' || params?.action == 'dp05' || params?.action == 'SolicitarAntecedentes'){
			(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
			(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
		}

		// Si vamos a calificar vemos si existe(nueva) o si estaba ya en la RECA
		if (params?.califica){
			if (reca.calificacion && params?.califica){
				if (reca.calificacion != TipoCalificacion.findByCodigo(params.califica)){
					(params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
					(params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
				}
			}else{
				(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
				(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
			}
		}else if(params?.origen != "0"){
			if (!reca.calificacion || (params?.origen && params?.tipoEvento)){
				(params.califica && params?.califica != "0") 		? (reca.calificacion = TipoCalificacion.findByCodigo(params.califica)) 			: (data.status = 3)
				(params.tipoEvento && params?.tipoEvento != "0") 	? (reca.eventoSiniestro = TipoEventoSiniestro.findByCodigo(params.tipoEvento)) 	: (data.status = 3)
			}
		}else{
			data.status = 3
		}

		// Guarda la codificación solo si el tipo de evento es laboral
		if (params?.tipoEvento == "1" || params?.tipoEvento == "2"){
			// Comprueba que no entren datos nulos
			(params?.cod_forma != "0") 			? (reca.forma = CodigoForma.findByCodigo(params.cod_forma)) 						: (data.status = 4)
			(params?.cod_agente != "0")			? (reca.agenteAccidente = CodigoAgenteAccidente.findByCodigo(params.cod_agente))	: (data.status = 4)
			(params?.cod_intencion != "0") 		? (reca.intencionalidad = CodigoIntencionalidad.findByCodigo(params.cod_intencion)) : (data.status = 4)
			(params?.cod_modoTransporte != "0") ? (reca.transporte = CodigoModoTransporte.findByCodigo(params.cod_modoTransporte)) 	: (data.status = 4)
			(params?.cod_papelLesionado != "0") ? (reca.lesionado = CodigoPapelLesionado.findByCodigo(params.cod_papelLesionado)) 	: (data.status = 4)
			(params?.cod_contraparte != "0")  	? (reca.contraparte = CodigoContraparte.findByCodigo(params.cod_contraparte)) 		: (data.status = 4)
			(params?.cod_tipoEvento != "0")  	? (reca.evento = TipoEvento.findByCodigo(params.cod_tipoEvento)) 					: (data.status = 4)
		}

		if (params?.tipoEvento == "3" && params['resolucion']=='Calificar'){
			// Setea la calificacion a null, porque no es necesaria por el tipo de evento
			reca.forma = null
			reca.agenteAccidente = null
			reca.intencionalidad = null
			reca.transporte = null
			reca.lesionado = null
			reca.contraparte = null
			reca.evento = null
		}

		//Guarda la codificacion de agente
		if (params?.tipoEvento == "3" || reca?.eventoSiniestro?.codigo == "3"){
			// Guarda los datos de agente e indicación
			(params.indicacion != "")? (reca.indicacion = params.indicacion) : (data.status = 6)
			if (reca.codificacionAgente && params.cod_agente_6){
				if (reca.codificacionAgente != Agente.findByCodigo(params.cod_agente_6)){
					(params.cod_agente_6 != "0") ? (reca.codificacionAgente = Agente.findByCodigo(params.cod_agente_6)) : (data.status = 5)
				}
			}else if (params.cod_agente_6){
				(params.cod_agente_6 && params.cod_agente_6 != "0") ? (reca.codificacionAgente = Agente.findByCodigo(params.cod_agente_6)) : (data.status = 5)
			}else if(!params.cod_agente_1 && !reca.codificacionAgente){
				data.status = 5
			}else{
				data.status = 5
			}
		}

		// Cuenta diagnosticos, verifica que pueda calificar
		if (params.origen == "1"){
			(Diagnostico.countBySiniestroAndEsLaboral(siniestro, true) > 0 ) ? (data.esLaboral = true) : (data.status = 1)
		}else if ((params.origen == "2") || (params.origen == "3")){
			((Diagnostico.countBySiniestroAndEsLaboral(siniestro, false) > 0) && (Diagnostico.countBySiniestroAndEsLaboral(siniestro, true) < 1)) ? (data.esLaboral = false) : (data.status = 2)
		}

		// Guarda, y envia de vuelta los datos para el bpm
		if(params['resolucion']=='Calificar' && data.status == null){
			// Guardo la fecha de calificacion de la reca, y puedeCalificar para el BPM
			reca.fechaCalificacion = FechaHoraHelper.hace10minutos();
			data.puedeCalificar = 'true'
			//Vemos si es 77bis
			def bis=Bis.findBySiniestro(siniestro);
			log.debug("BIS?:"+bis)
			if(bis){
				//Inicia la revision
				def user = UsuarioService.getUsuario(bis.creadoPor);
				log.debug("usuario:"+user.username+" iniciando revision de 77bis: "+bis)
				def b = BISRevisionService.start77bisRevision(user.username, bpmPass, bis.id)
			}
		}else{
			data.puedeCalificar = 'false'
		}

		// Guardo y retorno la data
		reca.save(flush: true)
		return data;

	}

	//TERMINA TAREA
	def completeDp01Ep(user, pass, taskId, data){
		log.info("Ejecutando completeDp01Ep")
		log.info("params: user: $user, pass: $pass, taskId: $taskId, data: $data")
		// Invocamos al BPM
		def dataToBpm=[taskPuedeCalificar: data.puedeCalificar, taskUser_:user]

		log.info("dataToBpm: $dataToBpm")

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'

	}

	//Guarda la nueva complejidad
	def postDp02Ep(params){
		log.info("Ejecutando postDp02Ep")
		log.info("params: $params")
		

		//obtiene el siniestro
		def siniestro = Siniestro.get(params.siniestroId)
		def nivelComplejidad
		def data=[:]

		//Guarda la calificación
		if (params?.eval_complejidad){
			nivelComplejidad = (params.eval_complejidad).toInteger()
			siniestro.nivelComplejidad = nivelComplejidad
			
			// Simular ingreso se seguimiento
			if (siniestro.nivelComplejidad == 0 && (params['resolucion']=='Asignar')) {
				def odaList = ODA.findAllBySiniestroAndCesadaIsNull(siniestro)
				if(odaList)
					return false
				
				def r = crearSeguimiento(siniestro)
				if (r.error) {
					log.info "No se pudo crear simulación de seguimiento en EP"
					return false
				}
			}
			params['resolucion']=='Asignar' ? (siniestro.fechaComplejidad = new Date()) : null
		}

		//Guarda, si no pasa se va a la shit
		if (!siniestro.save(flush: true)){
			return false;
		}

		log.info "Resolución ->"+params['resolucion']
		log.info "Nivel Complejidad ->"+nivelComplejidad

		data.nivelComplejidad = nivelComplejidad;

		return data;

	}

	//Termina la tarea
	def completeDp02Ep(user, pass, taskId, data){
		log.info("Ejecutando completeDp02Ep")
		log.info("params: user: $user, pass: $pass, taskId: $taskId, data: $data")
		// Invocamos al BPM
		def dataToBpm=[taskNivelComplejidad: (data.nivelComplejidad).toString(), taskUser_:user]

		
		log.info("dataToBpm: $dataToBpm")

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'

	}

	//ingresa seccion C para AT

	def postDp03Ep(params){
		log.info("Ejecutando postDp03Ep")
		log.info("params: $params")
		log.info("Siniestro ID : ${params.siniestroId}")

		//obtiene el siniestro
		def siniestro = Siniestro.findById(params.siniestroId)
		def diepOA = siniestro.diepOA
		def reca = RECA.findBySiniestro(siniestro)

		//creo la diatOA
		def diatOA = siniestro.diatOA
		if (!diatOA) { diatOA = new DIAT() }
		log.info("DIAT NUMERO: ${diatOA.id}")

		//Busca y guarda
		def direccionAccidenteComuna 		= Comuna.findByCodigo(params["direccionAccidenteComuna"])

		//traspaso todos los datos que ya se validaron en DIATOA y que estan presenten tambien en DIEPOA
		//ZONA A
		diatOA.fechaEmision						=	FechaHoraHelper.hace10minutos();
		diatOA.empleador						=	diepOA.empleador
		diatOA.ciiuEmpleador					=	diepOA.ciiuEmpleador
		diatOA.ciiuPrincipal					=	diepOA.ciiuPrincipal
		diatOA.direccionEmpleadorTipoCalle		=	diepOA.direccionEmpleadorTipoCalle
		diatOA.direccionEmpleadorNombreCalle	=	diepOA.direccionEmpleadorNombreCalle
		diatOA.direccionEmpleadorNumero			=	diepOA.direccionEmpleadorNumero
		diatOA.direccionEmpleadorRestoDireccion	=	diepOA.direccionEmpleadorRestoDireccion
		diatOA.direccionEmpleadorComuna			=	diepOA.direccionEmpleadorComuna
		diatOA.telefonoEmpleador				=	diepOA.telefonoEmpleador
		diatOA.nTrabajadoresHombre				=	diepOA.nTrabajadoresHombre
		diatOA.nTrabajadoresMujer				=	diepOA.nTrabajadoresMujer
		diatOA.propiedadEmpresa					=	diepOA.propiedadEmpresa
		diatOA.tipoEmpresa						=	diepOA.tipoEmpresa
		//ZONA B
		diatOA.trabajador						=	diepOA.trabajador
		diatOA.nacionalidadTrabajador			=	diepOA.nacionalidadTrabajador
		diatOA.direccionTrabajadorTipoCalle		=	diepOA.direccionTrabajadorTipoCalle
		diatOA.direccionTrabajadorNombreCalle	=	diepOA.direccionTrabajadorNombreCalle
		diatOA.direccionTrabajadorNumero		=	diepOA.direccionTrabajadorNumero
		diatOA.direccionTrabajadorRestoDireccion=	diepOA.direccionTrabajadorRestoDireccion
		diatOA.direccionTrabajadorComuna		=	diepOA.direccionTrabajadorComuna
		diatOA.telefonoTrabajador				=	diepOA.telefonoTrabajador
		diatOA.etnia							=	diepOA.etnia
		diatOA.otroPueblo						=	diepOA.otroPueblo
		diatOA.profesionTrabajador				=	diepOA.profesionTrabajador
		diatOA.fechaIngresoEmpresa				=	diepOA.fechaIngresoEmpresa
		diatOA.duracionContrato					=	diepOA.duracionContrato
		diatOA.tipoRemuneracion					=	diepOA.tipoRemuneracion
		diatOA.categoriaOcupacion				=	diepOA.categoriaOcupacion
		//ZONA C

		Date fechaAccidente=FechaHoraHelper.horaToDate(params.fechaAccidente_hora, siniestro?.fecha)

		Date horaIngreso						=	FechaHoraHelper.horaToDate(params.horaIngreso)
		Date horaSalida							=	FechaHoraHelper.horaToDate(params.horaSalida)

		diatOA.fechaAccidente					=	fechaAccidente
		diatOA.horaIngreso						=	horaIngreso
		diatOA.horaSalida						=	horaSalida
		diatOA.direccionAccidenteNombreCalle	=	params.direccionAccidenteNombreCalle
		diatOA.direccionAccidenteComuna			=   Comuna.findByCodigo(params["direccionAccidenteComuna"])
		diatOA.que								=	params.que
		diatOA.como								=	params.como
		diatOA.lugarAccidente					=	params.lugarAccidente
		diatOA.trabajoHabitualCual				=	params.trabajoHabitualCual
		diatOA.esTrabajoHabitual				=	Boolean.valueOf(params.esTrabajoHabitual)
		diatOA.gravedad 						= 	CriterioGravedad.findByCodigo(params.gravedad)
		diatOA.esAccidenteTrayecto				=	params.esAccidenteTrayecto.equals('2') ? true : false
		diatOA.tipoAccidenteTrayecto 			=	TipoAccidenteTrayecto.findByCodigo(params["tipoAccidenteTrayecto"])
		diatOA.medioPrueba 						= 	TipoMedioPruebaAccidente.findByCodigo(params["medioPrueba"])
		diatOA.detallePrueba					=	params.detallePrueba

		//ZONA F
		diatOA.denunciante						=	diepOA.denunciante
		diatOA.calificacionDenunciante			=	diepOA.calificacionDenunciante
		diatOA.telefonoDenunciante				=	diepOA.telefonoDenunciante
		diatOA.codigoActividadEmpresa			=	diepOA.codigoActividadEmpresa

		//Asocia al siniestro
		diatOA.siniestro = siniestro
		diatOA.save()
		//Se actualiza el siniestro con los datos oficiales de la DIEP OA
		siniestro.diatOA=diatOA
		//Guarda el siniestro
		if (!siniestro.save(flush: true)){
			return false;
		}
		//Datos BPM
		def data=[:]
		data.siniestroId = params.siniestroId

		return data;
	}

	def completeDp03Ep(user, pass, taskId, data){
		log.info("Ejecutando completeDp03Ep")
		log.info("params: user: $user, pass: $pass, taskId: $taskId, data: $data")
		// Invocamos al BPM
		
		def dataToBpm=[taskSiniestroId: (data.siniestroId).toString(), taskUser_:user]
		log.info("dataToBpm: ${dataToBpm}")

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'
	}


	//Rescata datos para el js en dp01 y dp05/06
	def getCalificacion(def codigos){
		log.info("Ejecutando metodo getCalificacion")
		log.info("codigos : $codigos")
		def calificaciones = TipoCalificacion.findAllByCodigoInList(codigos)
		return calificaciones
	}

	def getEvento(def codigos){
		log.info("Ejecutando metodo getEvento")
		log.info("codigos : $codigos")
		def eventos = TipoEventoSiniestro.findAllByCodigoInList(codigos)
		return eventos
	}

	def getCIE10(String codigos){
		log.info("Ejecutando metodo getCIE10")
		log.info("codigos : $codigos")
		def cie10 = CIE10.findByCodigo(codigos)
		return cie10
	}

	//Combos codificación de agentes

	def getCombo(def codigo){
		log.info("Ejecutando metodo getCombo")
		log.info("codigo : $codigo")
		def selector = (codigo).length()
		switch (selector) {
			case 1:
				def agente = AgenteC1.findByCodigo(codigo)
				def combo = AgenteC2.findAllByAgente1(agente)
				return combo
				break
			case 2:
				def agente = AgenteC2.findByCodigo(codigo)
				def combo = AgenteC3.findAllByAgente2(agente)
				return combo
				break
			case 4:
				def agente = AgenteC3.findByCodigo(codigo)
				def combo = AgenteC4.findAllByAgente3(agente)
				return combo
				break
			case 6:
				def agente = AgenteC4.findByCodigo(codigo)
				def combo = AgenteC5.findAllByAgente4(agente)
				return combo
				break
			case 8:
				def agente = AgenteC5.findByCodigo(codigo)
				def combo = Agente.findAllByAgente5(agente)
				return combo
				break
			default:
				break
		}

	}

	/**
	 * Simula un ingreso de un seguimiento
	 * @param siniestro
	 * @return
	 */
	def crearSeguimiento(siniestro) {
		log.info("Ejecutando metodo crearSeguimiento")
		log.info("siniestro : $siniestro")
		def seguimiento						= new Seguimiento()
		// Si es sin seguimiento validar si ya se creo la actividad de seguimiento
		seguimiento.siniestro				= siniestro
		seguimiento.usuario					= SecurityUtils.subject?.principal
		seguimiento.resumen					= "Siniestro con evaluación de complejidad igual a cero"
		seguimiento.observaciones			= "Siniestro con evaluación de complejidad igual a cero"
		seguimiento.nivel					= 0
		seguimiento.fechaIngreso 			= new Date()
		seguimiento.nivelComplejidadIngreso	= seguimiento.nivel
		seguimiento.fechaCambioNivel		= seguimiento.fechaIngreso
		seguimiento.fechaAlta				= seguimiento.fechaIngreso
		if (!seguimiento.validate()) {
			log.info "Error al guardar el seguimiento"
			return [error: true, seguimiento: seguimiento]
		}
		seguimiento.save()
		def tipoActividad = TipoActividadSeguimiento.findByCodigo('9')
		def actSeg = new ActividadSeguimiento(	[ seguimiento		: seguimiento
			, tipoActividad		: tipoActividad
			, fechaActividad	: seguimiento.fechaIngreso
			, resumen			: seguimiento.resumen
			, comentario		: seguimiento.observaciones]);
		if (!actSeg.validate()) {
			log.info "Error al guardar la actividad seguimiento"
			return [error: true, seguimiento: seguimiento, actSeg: actSeg]
		}
		log.info "Guardando la actividad seguimiento"
		actSeg.save(flush: true);
		return [error: false]
	}

	
	/**
	 * Retorna el codigo de la intencionalidad segun un grupo dado
	 * @param grupo codigo del grupo
	 * @return lista con los valores asociados a intencionalidad segun CIE10
	 * @author bvera
	 * @since 1.0.9.8
	 */
	def getIntencionalidadList(grupo){
		if(!grupo || grupo.equals("")){
			log.info ("El grupo ingresado no existe")
			return false
		}
		
		def grupoIntencionalidad = GrupoIntencionalidad.findByCodigo(grupo)
		def intencionalidadList = CodigoIntencionalidad.findAllByGrupo(grupoIntencionalidad)
		return intencionalidadList
	}
}
