package cl.adexus.isl.spm

import grails.converters.JSON
import org.apache.shiro.SecurityUtils
import javax.servlet.ServletOutputStream
import org.apache.jasper.compiler.Node.ParamsAction
import cl.adexus.helpers.FormatosHelper
import java.text.SimpleDateFormat


class BIS_ingresoController {

	def BISIngresoService
	def BISRevisionService
	def DocumentacionService

	def pass = '1234'
	def user = SecurityUtils.subject?.principal


	def index() { redirect ([action: 'dp01'])}

	/**
	 * Ingresar 77bis
	 * @return
	 */
	def dp01(){
		log.info "Ejecutando action dp01"
		log.info "Datos recibidos : $params"
		//Para la vista
		def tipoSiniestro = TipoEventoSiniestro.findAllByCodigoInList(['1','2','3'])
		def model = [tipoSiniestro:	tipoSiniestro]

		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}
		render(view: 'dp01', model: model)

	}

	def postDp01() {
		log.info "77BIS - postDp01 :: Parametros: ${params}"

		//Guardamos el formulario
		def r = BISIngresoService.postDp01(params)

		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}

	def dp01da(){
		log.info "77BIS - DP01DA Adjuntar Documentación Adicional :: Parametros"+params
		def model
		def bis

		//Recuperamos el bis
		if (params.get('model')){
			//Si viene de una redireccion
			bis = Bis.findById(params.get('model').bis_id)
			log.info "El bis que recuperamos :: redireccion ->"+bis
		}else{
			//Si viene desde el upload
			bis = Bis.findById(params?.bis_id)
			log.info "El bis que recuperamos :: upload ->"+bis
		}

		//Buscar toda la documentacion adicional
		def docs = DocumentacionAdicional.executeQuery(
				"SELECT	d " +
				"FROM  	DocumentacionAdicional as d, Bis as bis " +
				"WHERE 	bis.id = ? " +
				"AND	d.documentacion.id = bis.id ", [bis.id]);

		//Buscar toda la documentacion adicional
		def dictamen = DocumentacionAdicional.executeQuery(
				"SELECT	d " +
				"FROM  	DocumentacionAdicional as d, Bis as bis " +
				"WHERE 	bis.id = ? " +
				"AND	d.dictamen.id = bis.id ", [bis.id]);

		log.info "Errores en bis : "
		bis.errors.each{error->
			log.info "--> error : $error"
		}

		//Docs adicionales al model
		def fechaSiniestro = params?.fecha_siniestro
		if(params?.action.equals("index")){	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
			fechaSiniestro = sdf.format(params?.fecha_siniestro)
		}

		log.info("Valor fecha de siniestro en dp01da : ${fechaSiniestro}")
		def errores = []
		errores = bis.errors
		flash.mensajes = null
		model = [	docs: 		docs,
			dictamen:	dictamen,
			bis:		bis,
			fecha_siniestro:fechaSiniestro,
			errores:errores]


		render(view: 'dp01da', model: model)
	}

	def buscarSiniestros(){
		log.info "Ejecuntando action buscarSiniestros"
		log.info "Datos recibidos : $params"

		//Set null al mensaje para que no persista en la siguiente pantalla
		flash.mensajes = null

		log.info "Inicializando validacion de fecha de dictamen"
		def fechaDictamen = params?.fechaDictamen
		if(fechaDictamen > new Date()){
			log.info "Fecha dictamen superior a la actual"
			def b = new Bis()
			b.fechaDictamen = fechaDictamen
			if(b.hasErrors()){
				log.info "Fecha dictamen con error"
				b.errors.each{error->
					log.info "-->error"
				}
			}
			params.b = b
			render(view:"dp01da", model:params)
			return
		}
		log.info "Finalizacion validacion de fecha de dictamen"
		def fechaSiniestro = params?.fechaSiniestro?:params?.fecha_siniestro 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
		fechaSiniestro = sdf.parse(fechaSiniestro)

		log.info "Inicializando validacion entre fecha de dictamen y fecha de siniestro"
		if((fechaDictamen < fechaSiniestro) && fechaDictamen){
			log.info "fecha de dictamen inferior a fecha de siniestro"
			def b = new Bis()
			b.errors.reject("La fecha de dictamen [$fechaDictamen] no debe ser menor a la fecha del siniestro [$fechaSiniestro]")
			if(b.hasErrors()){
				b.errors.each{error->
					log.info "-->error"
				}
			}
			params.b = b
			render(view:"dp01da", model:params)
			return
		}

		//Guardamos datos de dictamen
		def data = BISIngresoService.guardarDictamen(params)

		//Buscamos los siniestros
		def r = BISIngresoService.postDp01da(params)

		flash.put('model', r.get('model'))
		redirect (r.next)
	}

	/**
	 * Elegir siniestro <Todos asociados por empleado y trabajador>
	 * @return
	 */
	def dp02(){
		log.info "77BIS - DP02 :: Parametros "+params

		def bis_id

		if (flash.get('model')){
			bis_id = flash.get('model').bis_id
		}else if (params.get('model')){
			bis_id = params.get('model').bis_id
		}

		def bis = Bis.findById(bis_id)
		def trabajador = PersonaNatural.findByRun(bis.runTrabajador)
		def datosTrabajador = BISIngresoService.buscaInfoTrabajador(bis.runTrabajador)
		def siniestros = BISIngresoService.buscaSiniestros(bis)

		def model = [ 	trabajador      : trabajador,
			datosTrabajador : datosTrabajador,
			siniestros      : siniestros,
			bis_id			: bis_id]

		render(view: 'dp02', model: model)
	}

	/**
	 * Accion TERMINAR en dp02
	 * @return
	 */
	def cu02(){
		log.info "El siniestro no se encontro, o no esta calificado :: Parametros "+params
		redirect ([controller: 'nav', action: 'index'])
	}

	/**
	 * PostDP02, verificación calificación de origen
	 * @return
	 */
	def postDp02(){
		log.info "PostDp02 parametros-> "+params
		def r = BISIngresoService.verificarCalificacion(params)
		log.debug("r:"+r)
		if (r.laboral){
			def b = BISRevisionService.start77bisRevision(user, pass, params?.bisId)
		}

		//Mensaje de caso no calificado
		if (r.mensaje){
			flash.mensajes = r.mensaje
		}else{
			flash.mensajes = null
		}

		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}

	/**
	 * Enviar a regularizar
	 * @return
	 */
	def dp03(){

		log.info "DP03 :: Parametros "+params

		//Para la vista
		def bis_id
		def tipoSiniestro = TipoEventoSiniestro.findAllByCodigoInList(['1','2','3'])

		//Rescatamos el ID de 77bis
		if (flash.get('model')){
			bis_id = flash.get('model').bis_id
		}else if (params.get('model')){
			bis_id = params.get('model').bis_id
		}

		def bis = Bis.findById(bis_id)

		//Buscar toda la documentacion adicional
		def docs = DocumentacionAdicional.executeQuery(
				"SELECT	d " +
				"FROM  	DocumentacionAdicional as d, Bis as bis " +
				"WHERE 	bis.id = ? " +
				"AND	d.documentacion.id = bis.id ", [bis.id]);

		//Buscar toda la documentacion adicional
		def dictamen = DocumentacionAdicional.executeQuery(
				"SELECT	d " +
				"FROM  	DocumentacionAdicional as d, Bis as bis " +
				"WHERE 	bis.id = ? " +
				"AND	d.dictamen.id = bis.id ", [bis.id]);
		def dictamenId = null
		if(dictamen)
			dictamenId = dictamen[0].id

		log.info("Valor dictamen : $dictamenId")
		//Armamos el modelo
		def model = [	tipoSiniestro:	tipoSiniestro,
			docs: 			docs,
			dictamen:		dictamenId,
			bis:			bis				]

		// Si venimos de una redireccion por error
		if (flash.get("model")) {
			model += flash.get("model")
			flash.mensajes = null
		}

		render(view: 'dp03', model: model)

	}

	def EnvioRegularizar(){
		log.info "Enviando a regularizar, parametros ->"+params
		def r = BISIngresoService.start77bisRegularizacion(user, pass, params?.bisId)
		// Lo mandamos al INBOX
		redirect ([controller: 'nav', action: 'area2'])
	}

	def regularizar(){

		def bis = Bis.findById(params?.bisId)
		def next

		//Session, supongo que cambiará
		bis.taskId = params?.taskId
		session['sdaat']=null

		//Arreglo fecha para que concuerde
		def fechaSiniestro = new Date().clearTime()
		fechaSiniestro.set(year: 1900+(bis.fechaSiniestro).year.toInteger(), month: (bis.fechaSiniestro).month.toInteger(), date: (bis.fechaSiniestro).day.toInteger())
		//fechaSiniestro = FormatosHelper.fechaCortaStatic(fechaSiniestro)

		if (bis.siniestro){
			def reca = RECA.findBySiniestro(bis.siniestro)
			if (reca){
				//Cerramos la tarea e iniciamos revision
				def r = BISIngresoService.complete77bis(user, pass, params?.taskId)
				params['bis'] = bis.id
				next = [action: 'bisPago', params: params]
			}else{
				next = [controller: 'nav', action: 'inbox']
			}
		}else{
			if (bis){
				if (bis.tipoSiniestro.codigo == '3'){
					log.info "Redireccionando SDAEP ->"+params
					params['run'] = bis.runTrabajador
					next = [controller: 'SDAEP_previo', action: 'r01', params: params]
				}else{
					log.info "Redireccionando a SDAAT ->"+params
					params['run'] = bis.runTrabajador
					params['fechaSiniestro'] = fechaSiniestro
					params['fechaSiniestro_year'] = 1900+(bis.fechaSiniestro).year.toInteger()
					params['fechaSiniestro_month'] = (bis.fechaSiniestro).month.toInteger()
					params['fechaSiniestro_day'] = (bis.fechaSiniestro).day.toInteger()
					next = [controller: 'SDAAT_ident', action: 'r01', params: params]
				}
			}
		}
		bis.save(flush: true)
		redirect (next)
	}

	def bisPago(){
		log.info "Enviando a revision, parametros ->"+params
		def r = BISRevisionService.start77bisRevision(user, pass, params?.bis)
		//Lo mandamos al INBOX
		redirect ([controller: 'nav', action: 'inbox'])
	}

	/**
	 * Generar Carta de Rechazo
	 * @return
	 */
	def dp04(){
		log.info "DP04 :: Parametros "+params

		def resolucion
		def calificacion
		def taskId = params?.taskId

		//recuperamos el Id de 77bis y la calificación
		if (params.model) {
			params?.bisId = params?.model?.bis_id
			calificacion = params?.model?.calificacion
		}

		//Buscamos el 77bis
		def bis = Bis.findById(params?.bisId)

		//Mensaje de la calificación
		if (bis.motivoRechazo == 'Origen es Común' || (calificacion && calificacion.origen.codigo != 1)){
			resolucion = 'El caso se encuentra calificado por el ISL como origen Común'
			if (!bis.motivoRechazo){
				bis.motivoRechazo = 'Origen es Común'
				bis.save(flush: true)
			}
		}

		if (bis.motivoRechazo == 'Trabajador no Afiliado a ISL')
			resolucion = 'El trabajador a la fecha del accidente y/o enfermedad pertenecía a otra mutualidad'

		if (bis.motivoRechazo == 'Trabajador es Obrero')
			resolucion = 'El trabajador tiene calidad de obrero, por lo cual la consulta debe ser enviada al servicio de salud respectivo'

		//Model
		def model = [	bis				: bis,
			resolucion		: resolucion,
			taskId			: taskId	]

		render(view: 'dp04', model: model)

	}

	/**
	 * Guarda el formulario para la creación del pdf (termina la tarea JBPM si imprime)
	 * @return
	 */
	def completeDp04(){
		log.info "Parametros complete dp04 -> "+params

		//Guarda los datos en el dominio Bis
		def r = BISIngresoService.postDp04(params)
		if (r){
			//Si viene desde una regularizacion de 77bis
			if (params?.taskId){
				def b = BISIngresoService.complete77bis(user, pass, params?.taskId)
			}
			BISIngresoService.notificarRechazo(r.bis)
		}
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}

	/**
	 * Obtiene enfermedades CIE-10
	 * @return
	 */
	def getEmisorJSON () {
		String rutEmisor = params.rutEmisor
		def datosEmisor = BISIngresoService.getEmisor(rutEmisor)
		JSON.use("deep") { render datosEmisor as JSON }
	}

	//Upload de archivos adjuntos
	def adjuntarArchivoAdicional() {
		log.info "Adjuntando Archivos ->"+params

		def bis = Bis.findById(params?.bis_id)
		def f = request.getFile('archivoAdjunto')
		def date = new Date().format('ddMMyyyyHmmss')
		def descripcion

		//descripcion del archivo, por defecto este:
		if (params?.descripcion_aadicional){
			descripcion = "77bis_adicional_"+params?.descripcion_aadicional
		}else{
			descripcion = "77bis_adicional_"+date
		}

		log.info "descripcion: "+descripcion
		log.info "bis: "+bis

		def map = ['bis': bis]
		def result = DocumentacionService.uploadSinSesion(map, descripcion, '77DAC', f)
		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha subido exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
		}

		forward (action: "dp01da", params:[bis_id: bis.id])
	}
	
	/**
	 * 
	 * @return
	 */
	def guardaDictamen(){
		log.info "Ejecutando action guardaDictamen"
		log.info "Datos recibidos : $params"
		
		log.info "Dictamen value : ${params?.dictamen}"

		def bis = Bis.findById(params?.bis_id)
		def f = request.getFile('DictamenAdjunto')
	
		//descripcion del archivo, por defecto este:
		def descripcion = "77bis_dictamen"
		def fechaDictamen = params?.fechaDictamen
		def numeroDictamen = params?.numeroDictamen
		def dictamen = params?.dictamen
	
		bis.numeroDictamen = numeroDictamen
		bis.fechaDictamen = fechaDictamen
		bis.dictamen = dictamen
		//verificando campos vacios
		log.info("Validando numero de dictamen")
		if(!numeroDictamen){
			log.info "numero de dictamen vacio"
			bis.errors.reject("El valor para el [numero de dictamen] no puede ser vacio")
			if(bis.hasErrors()){
				bis.errors.each{error->
					log.info "-->error : $error"
				}
			}else
				log.info("Numero de dictamen sin errores")


			params.b = bis
			params.bis_id = bis.id
			forward(action:"dp01da", model:params)
			return
		}
		log.info("Validacion numero de dictamen finalizada")
		
		log.info("Validando fecha dictamen")
		if(!fechaDictamen){
			log.info "Fecha dictamen vacia"
			bis.errors.reject("El valor para la [fecha de dictamen] no puede ser vacio")
			if(bis.hasErrors()){
				bis.errors.each{error->
					log.info "-->error : $error"
				}
			}else
				log.info("Fecha dictamen sin errores")

			params.b = bis
			params.bis_id = bis.id
			forward(action:"dp01da", model:params)
			return
		}
		log.info("Validacion fecha dictamen finalizada")
		
		log.info("Validando archivo adjunto")
		if(f.empty){
			log.info "Archivo adjunto dictamen vacio"
			bis.errors.reject("Debe adjuntar un archivo antes de confirmar la accion")
			if(bis.hasErrors()){
				bis.errors.each{error->
					log.info "-->error : $error"
				}
			}else
				log.info("Archivo adjunto dictamen sin errores")

			params.b = bis
			params.bis_id = bis.id
			forward(action:"dp01da", model:params)
			return
		}
		log.info("Validacion archivo adjunto dictamen finalizada")
		
		log.info "Validando fecha de dictamen $fechaDictamen"
		if(!bis.validate()){
			log.info "Registrando error para fecha de dictamen"
			bis.errors.each{error->
				log.info "-->error : $error"
			}
			params.bis = bis
			params.bis_id = bis.id
			forward(action:"dp01da", model:params)
			return

		}
		log.info "Validacion de fecha de dictamen finalizada"

		log.info "Verificando conversion de fecha de siniestro ${params?.fecha_siniestro}"
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
		def fechaSiniestro = sdf.parse(params?.fecha_siniestro)
		log.info "Verificacion de conversion de fecha de siniestro finalizada."


		log.info "Inicializando validacion entre fecha de dictamen y fecha de siniestro [$fechaSiniestro]"
		if(fechaDictamen < fechaSiniestro){
			log.info "fecha de dictamen inferior a fecha de siniestro"
			bis.errors.reject("La fecha de dictamen [${sdf.format(fechaDictamen)}] no debe ser menor a la fecha del siniestro [${sdf.format(fechaSiniestro)}]")
			if(bis.hasErrors()){
				bis.errors.each{error->
					log.info "-->error : $error"
				}
			}

			params.b = bis
			params.bis_id = bis.id
			forward(action:"dp01da", model:params)
			return
		}

		//Guardamos datos de dictamen
		def data = BISIngresoService.guardarDictamen(params)


		def map = ['bis': bis]
		def result = DocumentacionService.uploadSinSesion(map, descripcion, '77DIC', f)
		if (result.status == 0) {
			flash.mensajes = 'El Dictamen se ha subido exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la subida del Dictamen: ' + result.mensaje
		}

		forward (action: "dp01da", params:[bis_id: bis.id])
	}

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

	def deleteDoc(){
		def docId = params.docId
		def result = DocumentacionService.delete(docId)

		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha eliminado exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la eliminación del archivo: ' + result.mensaje
		}

		forward (action: "dp01da", params:[bis_id: params?.bis_id])
	}

	def verSiniestro(){
		log.info("ver Siniestro: "+params)
		flash.mensajes = null //Set null el mensaje por si venia de un error
		session['siniestroControllerHome'] = "BIS_Ingreso"
		session['bisId'] =  params?.bisId
		forward controller: "siniestro", action: "cu02" , params: [id: params.siniestroId]
	}
}
