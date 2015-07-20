package cl.adexus.isl.spm

import grails.converters.JSON
import org.apache.shiro.SecurityUtils
import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;
import javax.servlet.ServletOutputStream
import cl.adexus.isl.spm.enums.RetornoWSEnum


class CalOrigenATController {

	def CalOrigenService
	def DiagnosticoService
	def DocumentacionService
	def UsuarioService
	def SUSESOService
	def pass='1234'

	def index() {
		redirect(action: 'dp01')
	}

	//Revisa Califica y Codifica (Nivel 1)
	def dp01() {
		log.info("Ejecutando accion dp01")
		log.info("Datos recibidos : $params")

		log.info("Verificando si flash.model existe")
		if (flash.model) {
			log.debug("Tiene datos comienza a setear parametros")
			params.siniestroId = flash.model.siniestroId
			params.backTo = flash.model.backTo
			params.backToController = flash.model.backToController
			params.taskId = flash.model.taskId
			log.debug("Seteo de datos concluido")
		}

		log.info "flash.model.siniestroId ->"+flash?.model?.siniestroId
		log.info "params.model.siniestroId ->"+params?.model?.siniestroId

		def siniestroId = params.siniestroId
		def taskId = params.taskId
		def siniestro = Siniestro.findById(siniestroId)
		def reca = RECA.findBySiniestro(siniestro)

		//Carga los combo
		def origen = OrigenSiniestro.listOrderByCodigo()
		def forma = CodigoForma.listOrderByCodigo()
		def agente = CodigoAgenteAccidente.listOrderByCodigo()
		def grupoIntencionalidad = GrupoIntencionalidad.listOrderByCodigo()
		def intencion = CodigoIntencionalidad.listOrderByCodigo()
		def modoT = CodigoModoTransporte.listOrderByCodigo()
		def papelLes = CodigoPapelLesionado.listOrderByCodigo()
		def contraparte = CodigoContraparte.listOrderByCodigo()
		def tipoEvento = TipoEvento.listOrderByCodigo()
		def diagnosticos = Diagnostico.findAllBySiniestro(siniestro)
		def prestador

		//Combo calificacion
		def eventoS = TipoEventoSiniestro.listOrderByCodigo()
		def altaInmediata = [['codigo':'1', 'descripcion':'Sí'],['codigo':'2', 'descripcion':'No'],['codigo':'3', 'descripcion':'No Aplica']]
		def calificacion = TipoCalificacion.listOrderByCodigo()

		//buscar toda la documentacion adicional, en todas las DIAT asociadas al siniestro
		def docsDIAT = DocumentacionAdicional.executeQuery(
				"SELECT	d " +
				"FROM  	DocumentacionAdicional as d, DIAT as diat " +
				"WHERE 	diat.siniestro.id = ? " +
				"AND	d.denunciaAT.id = diat.id ", [siniestro.id]);

		//Prestador
		def opxx = siniestro.opa? siniestro?.opa : siniestro?.opaep
		if (opxx){
			prestador = FormatosISLHelper.nombrePrestadorStatic(opxx?.centroAtencion?.prestador)
		}

		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro.creadoEl)

		//Región del usuario
		def dixx = siniestro.esEnfermedadProfesional == true? siniestro?.diepOA : siniestro?.diatOA
		def region = UsuarioService.getRegion(dixx?.creadoPor)

		//Model
		def model = [
			taskId:			taskId,
			siniestroId:  	siniestroId,
			siniestro:  	siniestro,
			prestador:		prestador,
			docsDIAT:		docsDIAT,
			diasR:			diasR,
			origenSiniestro:origen,
			forma:			forma,
			agente:			agente,
			grupoIntencionalidad: grupoIntencionalidad,
			intencion:		intencion,
			modoT:			modoT,
			papelLes:		papelLes,
			contraparte:	contraparte,
			tipoEvento: 	tipoEvento,
			diagnosticos:	diagnosticos,
			altaInmediata:	altaInmediata,
			eventoS:		eventoS,
			calificacion:	calificacion,
			region:			region
		]

		if(reca) model += [reca: reca]

		if (flash.get("model")) {
			// Si venimos de una redireccion por error
			model += flash.get("model")
		}

		log.info("Finalizando ejecucion de accion dp01")
		render(view: 'dp01', model: model)
	}

	//dp01 Posponer
	def cu01p(){
		params['resolucion']='Posponer'
		forward action: "postDp01", params: params
	}

	//dp01 Derivar
	def cu01d(){
		params['resolucion']='Derivar'
		forward action: "postDp01", params: params
	}

	//dp01 Calificar
	/**
	 * 
	 * @return
	 */
	def cu01c(){
		log.info("Ejecutando accion cu01c")
		log.debug("Datos recibidos : $params")
		log.debug("Seteando resolucion con la accion calificar")
		params['resolucion']='Calificar'
		log.debug("Seteo de resolucion completo")
		log.info("Ejecucion de accion cu01c finalizada")
		forward action: "postDp01", params: params
	}

	def postDp01() {
		log.info("Ejecutando accion postDp01")
		log.info("Datos recibidos : $params")

		def mensaje = ""
		def retorno = ""

		//Guarda los datos en la bd
		log.info("Ejecutando CalOrigenService.postDp01At")
		def data=CalOrigenService.postDp01At(params)
		log.info("Ejecucion de CalOrigenService.postDp01At")

		log.info("Verificando si la respuesta del servicio contiene datos")
		if(data){
			log.debug("Los datos son siguientes: $data")
			def user = SecurityUtils.subject?.principal
			def taskId=params.taskId

			//Llama al bpm para realizar la calificación
			log.debug("Ejecutando tarea para calificacion de origen")
			if(params['resolucion']=='Derivar' || (params['resolucion']=='Calificar' && data.status == null)){
				def r2 = CalOrigenService.completeDp01At(user, pass, taskId, data);
				log.info "Resultado de la operación de ${params.resolucion} ::completeDp01At:: -> $r2"
			}

			log.debug("Retornando RECA con la que posteriormente se tomara el XML recibido ${data.recaId}")
			def reca = RECA.get(data.recaId)

			//Si deriva salta los errores
			if(params['resolucion']=='Calificar'){
				log.info("La resolucion es de tipo calificacion, su status corresponde a ${data.status}")
				//Tratamos el error
				switch (data.status) {
					case 1:
						flash.mensajes = 'El siniestro puede ser calificado como laboral sólo si tiene al menos un diagnóstico de tipo laboral'
						redirect ([action: 'dp01', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 2:
						flash.mensajes = 'El siniestro puede ser calificado como común o especial sólo si todos los diagnósticos son distintos de laboral'
						redirect ([action: 'dp01', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 3:
						flash.mensajes = 'Debe seleccionar la calificación del siniestro'
						redirect ([action: 'dp01', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 4:
						flash.mensajes = 'Debe seleccionar toda la codificación del accidente'
						redirect ([action: 'dp01', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 5:
						flash.mensajes = 'Ocurrió un error al crear el seguimiento para el siniestro'
						redirect ([action: 'dp01', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					default:
						if(reca){
							log.info("Reca existe y posee la siguiente salida : ${reca} ")
							if(reca?.xmlRecibido){
								log.debug("Respuesta SUSESO : ${reca?.xmlRecibido}")
								retorno = SUSESOService.getRetorno(reca?.xmlRecibido)
								if(retorno != ""){
									log.info("SUSESO service retorno el valor : $retorno")
									flash.mensajes = "valor de retorno $retorno : ${RetornoWSEnum.findByKey(retorno.text()).getDescripcion()}"
									log.info("Obtencion de valor de retorno desde xml recibido desde SUSESO finalizada")
								}else{
									flash.mensajes = "No se retornaron valores"
								}
							}else
								flash.mensajes = "Aun no se ha recibido la respuesta de XML"
						}else
							flash.mensajes = "Error: no fue posible retornar los valores de respuesta desde SUSESO"

						log.info("Ejecucion de postDp01 terminada")
						redirect ([controller: 'nav', action: 'inbox'])
						break
				}
			}else{
				log.info("La resolucion es de tipo derivacion")
				if(reca){
					log.info("Reca existe y posee la siguiente salida : ${reca} ")
					if(reca?.xmlRecibido){
						log.debug("Respuesta SUSESO : ${reca?.xmlRecibido}")
						retorno = SUSESOService.getRetorno(reca?.xmlRecibido)
						if(retorno != ""){
							log.info("SUSESO service retorno el valor : $retorno")
							mensaje = "valor de retorno $retorno : ${RetornoWSEnum.findByKey(retorno.text()).getDescripcion()}"
							log.info("Obtencion de valor de retorno desde xml recibido desde SUSESO finalizada")
						}else{
							mensaje = "No se retornaron valores"
						}
					}else
						mensaje = "Aun no se ha recibido la respuesta de XML"
				}else
					mensaje = "Error: no fue posible retornar los valores de respuesta desde SUSESO"

				log.debug(mensaje)
				flash.message = "cl.adexus.isl.spm.reca.susesows.mensaje"
				flash.args = []
				flash.default = mensaje
				log.info("Ejecucion de postDp01 terminada")
				redirect ([controller: 'nav', action: 'inbox'])
			}
		}
	}

	//Revisa Califica y Codifica (Nivel 2)
	def dp02() {
		log.info("dp02::params:"+params)
		flash.mensajes = null
		if (flash.model) {
			params.siniestroId = flash.model.siniestroId
			params.backTo = flash.model.backTo
			params.backToController = flash.model.backToController
			params.taskId = flash.model.taskId
		}

		def siniestroId = params.siniestroId
		def taskId=params.taskId
		def siniestro    = Siniestro.findById(siniestroId)
		def reca = RECA.findBySiniestro(siniestro)
		def anteAdicionales = AntecedenteAdicional.findAllBySiniestro(siniestro)

		//buscar toda la documentacion adicional, en todas las DIAT asociadas al siniestro
		def docsDIAT = DocumentacionAdicional.executeQuery(
				"SELECT	d " +
				"FROM  	DocumentacionAdicional as d, DIAT as diat " +
				"WHERE 	diat.siniestro.id = ? " +
				"AND	d.denunciaAT.id = diat.id ", [siniestro.id]);

		//Carga los combo
		def origen = OrigenSiniestro.listOrderByCodigo()
		def forma = CodigoForma.listOrderByCodigo()
		def agente = CodigoAgenteAccidente.listOrderByCodigo()
		def intencion = CodigoIntencionalidad.listOrderByCodigo()
		def modoT = CodigoModoTransporte.listOrderByCodigo()
		def papelLes = CodigoPapelLesionado.listOrderByCodigo()
		def contraparte = CodigoContraparte.listOrderByCodigo()
		def tipoEvento = TipoEvento.listOrderByCodigo()
		def agenteCombo1 = AgenteC1.listOrderByCodigo()
		def diagnosticos = Diagnostico.findAllBySiniestro(siniestro)
		def prestador

		//Combo calificacion
		def eventoS = TipoEventoSiniestro.listOrderByCodigo()
		def altaInmediata = [['codigo':'1', 'descripcion':'Sí'],['codigo':'2', 'descripcion':'No'],['codigo':'3', 'descripcion':'No Aplica']]
		def calificacion = TipoCalificacion.listOrderByCodigo()

		//Prestador
		def opxx = siniestro.opa? siniestro?.opa : siniestro?.opaep
		if (opxx){
			prestador = FormatosISLHelper.nombrePrestadorStatic(opxx?.centroAtencion?.prestador)
		}

		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro.creadoEl)

		//Región del usuario
		def dixx = siniestro.esEnfermedadProfesional == true? siniestro?.diepOA : siniestro?.diatOA
		def region = UsuarioService.getRegion(dixx?.creadoPor)

		//Model
		def model = [
			taskId:				taskId,
			siniestroId:  		siniestroId,
			siniestro:  		siniestro,
			prestador:			prestador,
			anteAdicionales:	anteAdicionales,
			docsDIAT:			docsDIAT,
			diasR:				diasR,
			origenSiniestro:    origen,
			forma:				forma,
			agente:				agente,
			intencion:			intencion,
			modoT:				modoT,
			papelLes:			papelLes,
			contraparte:		contraparte,
			tipoEvento: 		tipoEvento,
			diagnosticos:		diagnosticos,
			agenteCombo1:		agenteCombo1,
			altaInmediata:		altaInmediata,
			eventoS:			eventoS,
			calificacion:		calificacion,
			region:				region
		]

		if(reca){
			if (reca.codificacionAgente){
				def cod6 = Agente.findByCodigo(reca.codificacionAgente.codigo)
				def cod5 = AgenteC5.findByCodigo(cod6.agente5.codigo)
				def cod4 = AgenteC4.findByCodigo(cod5.agente4.codigo)
				def cod3 = AgenteC3.findByCodigo(cod4.agente3.codigo)
				def cod2 = AgenteC2.findByCodigo(cod3.agente2.codigo)
				def cod1 = AgenteC1.findByCodigo(cod2.agente1.codigo)
				model += [	cod6: cod6,
					cod5: cod5,
					cod4: cod4,
					cod3: cod3,
					cod2: cod2,
					cod1: cod1	]
			}
			model += [reca: reca]
		}

		if (flash.get("model")) {
			// Si venimos de una redireccion por error
			model += flash.get("model")
		}

		render(view: 'dp02', model: model)
	}

	//dp02 Posponer
	def cu02p(){
		params['resolucion']='Posponer'
		forward action: "postDp02", params: params
	}

	//dp02 Calificar
	def cu02c(){
		params['resolucion']='Calificar'
		forward action: "postDp02", params: params
	}

	//dp01 SolAnteAdic :: Solicitud de antecedentes adicionales
	def SolicitarAntecedentes(){
		log.info "SolicitarAntecedentes. Params: ${params}"

		//Guardamos el formulario anterior
		if (params?.backTo == 'dp01')
			def data = CalOrigenService.postDp01At(params)
		else if(params?.backTo == 'dp02')
			def data = CalOrigenService.postDp02At(params)

		//Guardar antecedentes adicionales, bpm
		def r = CalOrigenService.SolicitarAntecedentes(params)

		flash.put('model', r.model)
		redirect r.next
	}

	//dp03 SolAnteAdic :: Ver Solicitud de antecedentes adicionales
	def to_solicitud(){
		log.info "Ver Solicitud de Antecedentes. Params: ${params}"
		def r = CalOrigenService.verSolicitud(params)
		flash.put('model', r.model)
		redirect r.next
	}

	def postDp02() {
		log.info("postDp02:"+params)
		//Guarda los datos en la bd
		def data=CalOrigenService.postDp02At(params)
		if(data){
			def user = SecurityUtils.subject?.principal
			def taskId=params.taskId

			//Llama al bpm para realizar la calificación
			if(params['resolucion']!='Posponer' && data.status == null){
				def r2 = CalOrigenService.completeDp02At(user, pass, taskId, data);
			}

			//Si no tiene errores, pasa directo al inbox
			if(params['resolucion']=='Calificar'){
				//Tratamos el error
				switch (data.status) {
					case 1:
						flash.mensajes = 'El siniestro puede ser calificado como laboral sólo si tiene al menos un diagnóstico de tipo laboral'
						redirect ([action: 'dp02', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 2:
						flash.mensajes = 'El siniestro puede ser calificado como común o especial sólo si todos los diagnósticos son distintos de laboral'
						redirect ([action: 'dp02', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 3:
						flash.mensajes = 'Debe seleccionar la calificación del siniestro'
						redirect ([action: 'dp02', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 4:
						flash.mensajes = 'Debe seleccionar toda la codificación del accidente'
						redirect ([action: 'dp02', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 5:
						flash.mensajes = 'Debe seleccionar toda la codificación de agentes'
						redirect ([action: 'dp02', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					case 6:
						flash.mensajes = 'Debe ingresar la indicación médica para este siniestro'
						redirect ([action: 'dp02', params:['siniestroId': params.siniestroId, 'taskId': params.taskId]])
						break
					default:
						redirect ([controller: 'nav', action: 'inbox'])
						break
				}
			}else{
				redirect ([controller: 'nav', action: 'inbox'])
			}
		}
	}

	//Evaluar Complejidad
	def dp03() {
		log.info("Ejecutando action dp03")
		log.info("Datos recibidos : $params")

		def siniestroId = params.siniestroId
		def taskId = params.taskId
		def siniestro = Siniestro.findById(siniestroId)
		def diagnosticos = Diagnostico.findAllBySiniestro(siniestro)
		def informeOpa = InformeOPA.findBySiniestro(siniestro)
		def prestador

		//buscar toda la documentacion adicional, en todas las DIAT asociadas al siniestro
		def docsDIAT = DocumentacionAdicional.executeQuery(
				"SELECT	d " +
				"FROM  	DocumentacionAdicional as d, DIAT as diat " +
				"WHERE 	diat.siniestro.id = ? " +
				"AND	d.denunciaAT.id = diat.id ", [siniestro.id]);

		//Datos evaluación de complejidad
		def e_complejidad = [['codigo':0, 'descripcion':'Pacientes con lesiones leves'],
			['codigo':1, 'descripcion':'Pacientes con Lesiones menos graves'],
			['codigo':2, 'descripcion':'Pacientes con lesiones graves'],
			['codigo':3, 'descripcion':'Pacientes con lesiones muy graves']]

		//Prestador
		def opxx = siniestro.opa? siniestro.opa : siniestro.opaep
		if (opxx){
			prestador = FormatosISLHelper.nombrePrestadorStatic(opxx?.centroAtencion?.prestador)
		}

		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro.creadoEl)

		//Model
		def errores = []
		def odaList = ODA.findAllBySiniestroAndCesadaIsNull(siniestro)
		log.info("ODAs existentes : $odaList")
		
		if(odaList && params?.postDp03){
			log.info("Registrando error : existen odas activas por ende no se puede cerrar el caso")
			def oda = new ODA()
			oda.errors.reject("No se puede asignar complejidad leve a un caso que contenga ODAs activas ${odaList.id}")
			errores = oda.errors
		}
		

		
		def model = [
			taskId:			taskId,
			siniestro:  	siniestro,
			docsDIAT:		docsDIAT,
			diagnosticos:	diagnosticos,
			prestador:		prestador,
			informeOpa:		informeOpa,
			e_complejidad:	e_complejidad,
			diasR:			diasR,
			errores:		errores
		]

		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}

		/*if(params?.errores){
			flash.mensajes = params.errores
		}*/
		
		render(view: 'dp03', model: model)
	}

	//dp02 Posponer
	def cu03p(){
		params['resolucion']='Posponer'
		forward action: "postDp03", params: params
	}

	//dp02 Calificar
	def cu03a(){
		params['resolucion']='Asignar'
		forward action: "postDp03", params: params
	}

	//complejidad 0 --> FIN PROCESO | complejidad 1,2,3 --> SEGUIMIENTO
	def postDp03() {
		log.info("postDp03:"+params)
		//Guarda los datos en la bd
		def data = CalOrigenService.postDp03At(params)
		if(data){
			log.info("Completando tarea en bpmn para asignacion de complejidad")
			def user = SecurityUtils.subject?.principal
			def taskId=params.taskId
			if(params['resolucion']!='Posponer'){
				def r2 = CalOrigenService.completeDp03At(user, pass, taskId, data);
			}
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])
		}else
			redirect(action:"dp03", params:[siniestroId:params.siniestroId, taskId:params.taskId, postDp03:"true"])
	}

	//Ingresar Seccion C para EP
	def dp04() {
		log.info("dp04::params:"+params)

		if (flash.model) {
			params.siniestroId = flash.model.siniestroId
			params.taskId	   = flash.model.taskId
		}

		def siniestroId = params.siniestroId
		def taskId = params.taskId
		def siniestro = Siniestro.findById(siniestroId)
		def diep = siniestro.diepOA

		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro.creadoEl)

		//Model
		def model = [
			taskId:		taskId,
			siniestro:  siniestro,
			diasR:		diasR
		]

		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}

		if(diep) model += [diep: diep]

		render(view: 'dp04', model: model)
	}

	//dp02 Posponer
	def cu04p(){
		params['resolucion']='Posponer'
		forward action: "postDp04", params: params
	}

	//dp02 Calificar
	def cu04r(){
		params['resolucion']='Regularizar'
		forward action: "postDp04", params: params
	}

	//Guarda la seccion C para EP
	def postDp04() {
		log.info("postDp04:"+params)
		//Guarda los datos en la bd
		def data=CalOrigenService.postDp04At(params)
		if(data.error != 1){
			def user = SecurityUtils.subject?.principal
			def taskId=params.taskId
			if(params['resolucion']!='Posponer'){
				def r2 = CalOrigenService.completeDp04At(user, pass, taskId, data);
			}
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])
		}else{
			flash.put('model', data.model)
			redirect data.next
		}
	}

	//Crear Diagnosticos
	def dp05() {
		log.info("Ejecutando action dp05")
		log.info("Datos recibidos : $params")

		//Guardamos el formulario anterior
		if (params?.backTo == 'dp01')
			def data = CalOrigenService.postDp01At(params)
		else if(params?.backTo == 'dp02')
			def data = CalOrigenService.postDp02At(params)

		if (params.model) {
			params.siniestroId = params.model.siniestroId
			params.backTo = params.model.backTo
			params.backToController = params.model.backToController
			params.taskId = params.model.taskId
		}

		def siniestroId = params.siniestroId
		def siniestro    = Siniestro.findById(siniestroId)
		def taskId = params.taskId

		//LLenado combos
		def parte = CodigoUbicacionLesion.listOrderByDescripcion()
		def lateralidad = TipoLateralidad.listOrderByCodigo()
		def origenDiag = OrigenDiagnostico.findAllByCodigoInList(['1','3','4'])

		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro.creadoEl)

		//Model
		def model = [
			taskId:			taskId,
			siniestro:  	siniestro,
			diasR:			diasR,
			parte:			parte,
			lateralidad:	lateralidad,
			origenDiag:		origenDiag,
			volverSeguimiento: params?.volverSeguimiento,
			volverHistorial: params?.volverHistorial,
			verDetalle: params.verDetalle,
			origen: params.origen,
			cesarODA: params.cesarODA,
			recaOrigen: params?.recaOrigen
		]

		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}

		render(view: 'dp05', model: model)
	}

	//vuelve a flujo anterior
	def go_back(){
		log.info("Ejecutando action go_back")
		log.info("Datos recibidos : $params")
		def r = DiagnosticoService.goBack(params)
		params.put('model', r.model)
		
		def next = r.next
		next.params = params
		log.info("controller: ${params?.backToController}, action: ${params?.backTo}")
		forward next
	}

	//Editar Diagnosticos
	def dp06() {
		log.info("dp06::params:"+params)

		//Guardamos el formulario anterior
		if (params?.backTo == 'dp01')
			def data = CalOrigenService.postDp01At(params)
		else if(params?.backTo == 'dp02')
			def data = CalOrigenService.postDp02At(params)

		if (params.model) {
			params.siniestroId = params.model.siniestroId
			params.backTo = params.model.backTo
			params.backToController = params.model.backToController
			params.taskId = params.model.taskId
			params.diagnosticoId = params.model.diagnosticoId
		}

		def siniestroId = params.siniestroId
		def siniestro = Siniestro.findById(siniestroId)
		def diagnosticoId = params.diagnosticoId
		def diagnostico = Diagnostico.findById(diagnosticoId)
		def informeOpa

		//Calculo dias restantes
		def diasR =  15 - FechaHoraHelper.diffDates(siniestro.creadoEl)

		//LLenado combos
		def parte = CodigoUbicacionLesion.listOrderByDescripcion()
		def lateralidad = TipoLateralidad.listOrderByCodigo()
		def origenDiag

		//Viene de informe OPA?
		if (diagnostico.origen.codigo == '2'){
			origenDiag = OrigenDiagnostico.findAllByCodigoInList(['2'])
			informeOpa = true
		}else{
			origenDiag = OrigenDiagnostico.findAllByCodigoInList(['1','3','4'])
			informeOpa = false
		}

		//Model
		def model = [
			siniestro:  	siniestro,
			diasR:			diasR,
			parte:			parte,
			lateralidad:	lateralidad,
			origenDiag:		origenDiag,
			diagnostico:	diagnostico,
			informeOpa:		informeOpa, 
			volverSeguimiento: params?.volverSeguimiento, 
			volverHistorial: params?.volverHistorial, 
			verDetalle: params.verDetalle, 
			origen: params.origen, 
			cesarODA: params.cesarODA,
			recaOrigen: params?.recaOrigen
		]

		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}

		render(view: 'dp06', model: model)
	}

	//llama al service para guardar el diagnostico
	def save_dg(){
		def r = DiagnosticoService.addDiagnostico(params)
		if (r.error > 0) {
			flash.mensajes = 'La Fecha del diagnóstico debe ser mayor a la fecha del siniestro'
		}else{
			flash.mensajes = null
		}
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward next
	}

	//llama al service para actualizar un diagnostico
	def update_dg(){
		def r = DiagnosticoService.updateDiagnostico(params)
		if (r.error > 0) {
			flash.mensajes = 'La Fecha del diagnóstico debe mayor a la fecha del siniestro'
		}else{
			flash.mensajes = null
		}
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward next
	}

	//Elimina un diagnostico
	def delete_cs() {
		def r = DiagnosticoService.deleteDiagnostico(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward next
	}

	/**
	 * Muestra un documentoAdicional
	 * @return
	 */
	def viewDoc(){
		log.info 'SE RESCATA EL DOC ID ->'+params.docId

		def r = DocumentacionService.view(params.docId)
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

	/*
	 * 	Funciones AJAX
	 *  Calificación & Codificación
	 *  
	 */

	/**
	 * Obtiene calificaciones
	 * @return
	 */
	def datosCalificacionJSON () {
		List<String> calificacionList = (params.codigos).split(',') as List<String>
		def datosCalificacion = CalOrigenService.getCalificacion(calificacionList)
		JSON.use("deep") { render datosCalificacion as JSON }
	}

	/**
	 * Obtiene eventos
	 * @return
	 */
	def datosEventosJSON () {
		List<String> eventoList = (params.codigos).split(',') as List<String>
		def datosEvento = CalOrigenService.getEvento(eventoList)
		JSON.use("deep") { render datosEvento as JSON }
	}

	/**
	 * Obtiene enfermedades CIE-10
	 * @return
	 */
	def datosCIE10JSON () {
		String cie = params.codigos.toString().toUpperCase();
		log.info "Código CIE-10: "+cie
		def datosCIE10 = CalOrigenService.getCIE10(cie)
		JSON.use("deep") { render datosCIE10 as JSON }
	}

	/**
	 * Obtiene calificaciones
	 * @return
	 */
	def comboAgentesJSON () {
		def datosCombo = CalOrigenService.getCombo(params.codigo)
		JSON.use("deep") { render datosCombo as JSON }
	}
	
	 /** Retorna el codigo de la intencionalidad segun un grupo dado
	 * @param grupo codigo del grupo
	 * @return lista con los valores asociados a intencionalidad segun CIE10
	 * @author bvera
	 * @since 1.0.9.8
	 */
	def getIntencionalidadListJSON(){
		log.info("Ejecutando action getIntencionalidadListJSON ")
		log.info ("Grupo : ${params?.codigo}")
		def intencionalidadList = CalOrigenService.getIntencionalidadList(params.codigo.toInteger())
		log.info("IntencionalidadList : $intencionalidadList")
		JSON.use("deep") { render intencionalidadList as JSON }
	}

}
