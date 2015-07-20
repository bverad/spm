package cl.adexus.isl.spm

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;
import cl.adexus.helpers.FormatosHelper;

import java.text.SimpleDateFormat
import java.util.logging.Logger;


class BISIngresoService {

	def JBPMService
	def uploadService
	def mailService
	def grailsApplication
	def UsuarioService


	def otp77bisProceso2ProcessName = 'cl.isl.spm.otp.otp-77bisingreso'

	//************************************** 77 bis-ingreso **************************************

	/**
	 * Inicia el proceso 2 para 77 bis, regularización
	 * @param user
	 * @param pass
	 * @param siniestroId
	 * @return
	 */
	def start77bisRegularizacion(user,pass,bisId){
		// Invocamos al BPM
		def bpmParams = ['bisId': bisId.toString(), user:user]
		def r = JBPMService.processComplete(user, pass, otp77bisProceso2ProcessName, bpmParams)
		log.info(otp77bisProceso2ProcessName+"::processComplete result:"+r)
		return r
	}

	/**
	 * Termina el proceso de 77bis regularización
	 * @param user
	 * @param pass
	 * @param taskId
	 * @return
	 */
	def rechazo(user, pass, taskId){
		// Invocamos al BPM
		def dataToBpm=[taskRechazo: 'true', taskUser_:user]
		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'
	}

	/**
	 * Termina el proceso 77bis notificar al solicitante
	 * @param user
	 * @param pass
	 * @param taskId
	 * @param data
	 * @return
	 */
	def complete77bis(user, pass, taskId){
		// Invocamos al BPM
		def dataToBpm=[success: 'true', taskUser_:user]
		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'
	}

	//************************************ ****************** ************************************

	/**
	 * Guarda el formulario 77bis-ingreso, validando rut/run y buscando los siniestros asociados
	 * @param params
	 * @return
	 */
	def postDp01(params) {
		log.info "Ejecutando metodo postDp01"
		log.info "Datos recibidos : $params"

		def bis = new Bis()
		def next

		//Arreglo RUT
		params?.rut_emisor 		= params.rut_emisor ? ((String)params.rut_emisor).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim() : params.rut_emisor
		params?.run_trabajador 	= params.run_trabajador ? ((String)params.run_trabajador).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim() : params.run_trabajador
		params?.rut_empleador 	= params.rut_empleador ? ((String)params.rut_empleador).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim() : params.rut_empleador

		//Compruebo y guardo
		if (params?.rut_emisor){
			def emisor = PersonaJuridica.findByRut(params?.rut_emisor)
			if (emisor){
				if (emisor.razonSocial.toUpperCase() != params?.nombre_emisor.toUpperCase()){
					emisor.razonSocial = params?.nombre_emisor
					//emisor.nombreFantasia = params?.nombre_emisor
					emisor.save(flush: true)
				}
				bis.emisor = emisor
			}else{
				emisor = new PersonaJuridica()
				emisor.rut = params?.rut_emisor
				emisor.razonSocial = params?.nombre_emisor
				if (emisor.validate()){
					emisor.save(flush: true)
					bis.emisor = emisor
				}
			}
		}

		//Valido con Trabajador los rut, lo copie de OTP_ingreso jojo
		if(params?.run_trabajador)
			bis.runTrabajador = params?.run_trabajador

		if(params?.rut_empleador)
			bis.rutEmpleador = params?.rut_empleador

		//Fechas de siniestro y recepción
		if(params?.fecha_recepcion)
			bis.fechaRecepcion = params?.fecha_recepcion

		if(params?.fecha_siniestro)
			bis.fechaSiniestro = params?.fecha_siniestro

		//Tipo siniestro y monto solicitado
		if(params?.tipoSiniestro)
			bis.tipoSiniestro = TipoEventoSiniestro.findByCodigo(params?.tipoSiniestro)

		if(params?.montoSolicitado)
			bis.montoSolicitado = params?.montoSolicitado.toLong()


		def sdf = new SimpleDateFormat("dd/MM/yyyy")
		def fechaRecepcion = params?.fecha_recepcion
		def fechaSiniestro = params?.fecha_siniestro
		log.info "Validando fecha de siniestro y fecha de recepcion"
		if(fechaSiniestro > fechaRecepcion){
			log.info "fecha de siniestro mayor a fecha de recepcion de carta"
			bis.errors.reject("La fecha de recepcion de la carta [${sdf.format(fechaRecepcion)}] no debe ser inferior a la fecha del siniestro [${sdf.format(fechaSiniestro)}] ")
			if(bis.hasErrors()){
				bis.errors.each{error->
					log.info "-->error : $error"
					next = [next: [action: 'dp01'], model: [bis: bis, rut_emisor: params?.rut_emisor, nombre_emisor: params?.nombre_emisor ]]
					return next
				}
			}
		}


		log.info "Validacion de fecha de siniestro y fecha de recepcion de carta finalizada : ${bis.hasErrors()}"
		//Validate, creo que deberia validar si no hay otro ingreso 77bis con los mismos datos
		if(bis.hasErrors()){
			log.info "Tiene errores"
		}else if(!bis.validate()){
			next = [next: [action: 'dp01'], model: [bis: bis, rut_emisor: params?.rut_emisor, nombre_emisor: params?.nombre_emisor ]]
		}else{
			bis.save(flush: true)
			next = [next: [action: 'dp01da'], model: [bis_id: bis.id]]
		}

		return next

	}

	def guardarDictamen(params){
		log.info "Ejecutando metodo guardaDictamen"
		log.info "Datos recibidos : $params"

		def bis = Bis.findById(params?.bis_id)
		def ok = true

		//Dictamen, archivos adicionales tipo de siniestro y monto
		if(params?.dictamen[1]?.toBoolean() == true || bis?.dictamen == true){
			bis.dictamen = true
			bis.numeroDictamen = params?.numeroDictamen
			params?.fechaDictamen? (bis.fechaDictamen = params?.fechaDictamen) : (bis.fechaDictamen = new Date())
		}else{
			bis.dictamen = false
			bis.numeroDictamen = null
			bis.fechaDictamen = null
		}

		if (!bis.save(flush: true)){
			ok = false
		}

		return ok
	}

	def postDp01da(params){
		log.info "No hay mas documentacion adicional, rediccional al flujo habitual"

		def bis = Bis.findById(params?.bis_id)
		def next

		//Buscamos los siniestros asociados
		def siniestros = buscaSiniestros(bis)

		if (siniestros.size()>0){
			next = [next: [action: 'dp02'], model: [bis_id: bis.id]]
		}else{
			next = [next: [action: 'dp03'], model: [bis_id: bis.id]]
		}

		log.info "Redireccionando a ->"+next
		return next
	}

	def postDp04(params){
		log.info "Post dp04, parametros ->"+params

		def bis = Bis.findById(params?.bisId)
		def next

		//Guarda los datos
		if (params?.ordinario)
			bis.ordinario = params?.ordinario

		if (params?.encargadoCobranza)
			bis.encargadoCobranza = params?.encargadoCobranza

		if (params?.entidadCobradora)
			bis.entidadCobradora = params?.entidadCobradora

		if (params?.direccionEntidad)
			bis.direccionEntidad = params?.direccionEntidad

		if (params?.comentariosRechazo)
			bis.comentariosRechazo = params?.comentariosRechazo

		//validamos y retornamos
		if (!bis.validate()){
			next = [next: [action: 'dp04'], model: [bis: bis]]
		}else{
			next = [next: [action: 'inbox', controller: 'nav'], bis: bis]
		}

		return next
	}

	def buscaSiniestros(bis){

		log.info ("Busca los siniestros asociados al trabajador y empleador entregados:")
		log.info ("trabajador: "+bis.runTrabajador+"/ Empleador: "+bis.rutEmpleador)

		def trabajador = PersonaNatural.findByRun(bis.runTrabajador)
		def empleador = PersonaJuridica.findByRut(bis.rutEmpleador)
		def siniestro = []
		def diat
		def reca

		//Busca los siniestros que tengan trabajador y empleador especificados
		def siniestros = Siniestro.findAllByTrabajadorAndEmpleador(trabajador,empleador)
		def siniestroList = []
		if (siniestros){
			log.info ("Siniestros encontrados: "+siniestros.size())
			siniestros.each {

				def helpSiniestro = [:]

				helpSiniestro.id 			= it.id
				helpSiniestro.fecha 		= it.fecha
				helpSiniestro.relato 		= it.relato

				if (it.esEnfermedadProfesional == true)
					helpSiniestro.tipoSiniestro = "Enfermedad Profesional"
				else{
					diat = DIAT.findBySiniestro(it)
					if (diat?.esAccidenteTrayecto == true)
						helpSiniestro.tipoSiniestro = "Accidente de Trayecto"
					else
						helpSiniestro.tipoSiniestro = "Accidente del Trabajo"
				}
				//Veo si tiene una reca asociada
				reca = RECA.findBySiniestroAndXmlRecibidoIsNotNull(it)
				if (reca){
					helpSiniestro.calificacion = reca?.calificacion?.descripcion
					siniestro.add(helpSiniestro)
					log.info "SINIESTRO ID "+siniestro.id
				}else{
					helpSiniestro.calificacion = 'No Calificado'
				}
			}
		}
		return siniestro

	}

	def buscaInfoTrabajador(runTrabajador){
		log.info ("Buscando información asociada al trabajador: "+runTrabajador)

		def trabajador = PersonaNatural.findByRun(runTrabajador)
		def siniestro
		def infoTrabajador = [:]
		def direccion
		def dixx

		def siniestros = Siniestro.findAll(sort:"creadoEl") {
			trabajador == trabajador
		}
		//Tomo el ultimo siniestro
		siniestros.each {siniestro = it}
		log.info "ID SINIESTRO RECUPERADO "+siniestro.id

		if (siniestro.esEnfermedadProfesional){
			dixx = DIEP.findBySiniestro(siniestro)
			log.info "Encontrada DIEP "+dixx?.id
		}else{
			dixx = DIAT.findBySiniestro(siniestro)
			log.info "Encontrada DIAT "+dixx?.id
		}

		infoTrabajador = [	direccion : FormatosISLHelper.direccionCompletaTrabajadorStatic(dixx),
			comuna: dixx?.direccionTrabajadorComuna? dixx?.direccionTrabajadorComuna?.descripcion : '',
			telefono: dixx?.telefonoTrabajador? dixx?.telefonoTrabajador : '',
			nacionalidad: dixx?.nacionalidadTrabajador? dixx?.nacionalidadTrabajador?.descripcion : '']

		return infoTrabajador

	}

	def verificarCalificacion(params){
		log.info ("Verificando calificación de origen para el siniestro número: "+params?.siniestroId + " en bis:"+params?.bisId)

		def siniestro = Siniestro.findById(params?.siniestroId)
		def reca = RECA.findBySiniestro(siniestro)
		def bis = Bis.findById(params?.bisId)
		def next

		bis.siniestro = siniestro
		bis.save(flush: true)

		if (reca){
			//Verificar común o laboral
			log.info "La calificación de origen del siniestro n°"+siniestro.id+" es "+reca.calificacion.origen.descripcion+"."
			if (reca.calificacion.origen.codigo == '1'){
				//retornamos
				next = ['laboral': true,'next': [controller: 'nav', action: 'index']]
			}else{
				log.info "Siniestro n°"+siniestro.id+" fue calificado como común, solicitud n°"+bis.id
				next = ['laboral': false, 'next': [controller: 'BIS_ingreso', action: 'dp04'], model: ['bis_id': params?.bisId, calificacion: reca.calificacion]]
			}
		}else{
			//No esta calificado. Debiera quedar a la espera
			def mensaje = "El caso seleccionado no está Calificado"
			log.info mensaje+", Siniestro n°"+siniestro.id
			next = ['laboral': false, 'next': [action: 'dp02'], mensaje: mensaje, model: [bis_id: params.bisId]]
		}

		return next
	}

	/**
	 * Obtiene los datos de un emisor, si es que existe
	 * @param rutEmisor
	 * @return
	 */
	def getEmisor(String rutEmisor){
		log.info "Buscando datos para el RUT ->"+rutEmisor
		def emisor = PersonaJuridica.findByRut(rutEmisor)
		log.info "Emisor ->"+emisor?.razonSocial? emisor?.razonSocial : emisor?.nombreFantasia
		return emisor
	}

	/**
	 * Notifica al solicitante el rechazo por motivos tecnicos, trabajador no afiliado o siniestro calificado como común
	 * @param bis
	 * @param motivoRechazo
	 * @return
	 */
	def notificarRechazo(def bis) {
		def usuario       = UsuarioService.getUsuario(bis.creadoPor)
		def resolucion
		//El receptor del correo electronico
		def mail = ""
		if (grailsApplication.config.correoFijo == 1)
			mail = grailsApplication.config.correo
		else
			mail = usuario.correoElectronico

		//Arreglamos el motivo del rechazo
		if (bis.motivoRechazo == 'Origen es Común')
			resolucion = 'El caso se encuentra calificado por el ISL como origen Común'

		if (bis.motivoRechazo == 'Trabajador no Afiliado a ISL')
			resolucion = 'El trabajador a la fecha del accidente y/o enfermedad pertenecía a otra mutualidad'

		if (bis.motivoRechazo == 'Trabajador es Obrero')
			resolucion = 'El trabajador tiene calidad de obrero, por lo cual la consulta debe ser enviada al servicio de salud respectivo'

		//Arma el super mail de notificación
		mailService.sendMail {
			to 		mail
			from 	"Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Notificación Analisis 77bis"
			html 	"<table><tr><td>"/*<img src='cid:logo' />*/ + "</td><td style=\"text-align:right;vertical-align:text-top;\">ORD.N° "+ bis.ordinario +
					"<br>MAT: Devolución administrativa o de pertenencia médica, solicitud de reembolso por aplicación 77 BIS<br>" +
					"<b>Santiago, " + FormatosISLHelper.fechaHoraStatic(new Date()) + "</b></td></tr><tr><td colspan=2> " +
					"<b>DE: LIZZY VIDAL NEIRA<br>" +
					"JEFE DE UNIDAD DE REVISIÓN DE CUENTAS MÉDICAS<br>" +
					"INSTITUTO DE SEGURIDAD LABORAL<br><br>" +
					"A: " + bis.encargadoCobranza + "<br>" +
					"ENCARGADO DE COBRANZAS <br>" +
					"ISAPRE " + bis.entidadCobradora + ", " +bis.direccionEntidad + "</td></tr><tr><td colspan=2><br></td><tr>" +
					"<tr><td>Folio C.C " + bis.id + "</td><td>Fecha de Revisión " + FormatosISLHelper.fechaHoraStatic(bis.fechaRevision) + "</td></tr><tr><td colspan=2><br></td><tr>" +
					"<tr><td colspan=2>Mediante la presente, informo a usted el rechazo de su solicitud de 77 bis por el mismo motivo que se indica: </td></tr>" +
					"<tr><td colspan=2>  - " + resolucion + "</td></tr><tr><td colspan=2><br></td><tr>" +
					"<tr><td>La cual fue efectuada por un monto de: "+ FormatosHelper.montosStatic(bis.montoSolicitado) +"</td></tr><tr><td colspan=2><br></td><tr>" +
					"<tr><td colspan=2><h4>Comentarios</h4></td></tr>" +
					"<tr><td colspan=2> " + bis.comentariosRechazo + "</td></tr><tr><tdcolspan=2><br></td><tr>" +
					"<tr><td style=\";vertical-align:text-top;\">Sin otro particular, le saluda atentamente</td><td style=\"text-align: right;\">" +
					"<b>LIZZY VIDAL NEIRA<br>JEFE DE UNIDAD DE REVISIÓN DE CUENTAS MÉDICAS<br>" +
					"INSTITUTO DE SEGURIDAD LABORAL</b></td></tr></table>"
		}
	}



}
