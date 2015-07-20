package cl.adexus.isl.spm

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;
import cl.adexus.helpers.FormatosHelper;

class BISRevisionService {
	
	def JBPMService
	def uploadService
	def mailService
	def grailsApplication
	def UsuarioService
	
	def otp77bisPagoProcessName = 'cl.isl.spm.otp.otp-77bispago'
	
	/**
	 * Inicia el proceso 77bis Pago
	 * @param user
	 * @param pass
	 * @param siniestroId
	 * @return
	 */
	def start77bisRevision(user,pass,bisId){
		// Invocamos al BPM
		def bpmParams = ['bisId': bisId.toString(), user:user]
		def r = JBPMService.processComplete(user, pass, otp77bisPagoProcessName, bpmParams)
		log.info(otp77bisPagoProcessName+"::processComplete result:"+r)
		return r
	}
	
	//************************************ ****************** ************************************
		
	 /**
	  * Guarda datos del formulario y redirecciona
	  * @param params
	  * @return
	  */
	 def postDp01(params) {
		 
		log.info "POSTDP01-77BIS ::REVISION:: PARAMETROS -> "+params
		def bis = Bis.findById(params?.bis_id)
		def rechazo
		def error = 0
		def next
		 
		if (params?.comentarios_seguimiento)
			bis.comentariosSeguimiento = params.comentarios_seguimiento
		 
		if (params?.montoAprobado){
			if (params?.montoAprobado.toLong() > bis.montoSolicitado){
				error = 1
			}else{
				bis.montoAprobado = params.montoAprobado.toLong()
			}
		}
			
		//Guardamos la fecha de la revision
		bis.fechaRevision = new Date()
		
		//Vemos si el monto aprobado es mayor a 0, si es asi, esta parcial o totalmente aprobado
		if (bis.montoAprobado > 0)
			rechazo = false
		else
			rechazo = true
				
		//validamos y guardamos, la costumbre
		if (!bis.validate() || error == 1){
			next = [next: [action: 'dp01'], model: [bisId: bis.id, comentario: params?.comentarios_seguimiento, montoAprobado: params?.montoAprobado, taskId: params?.taskId], error: error]
		}else{
			bis.save(flush: true)
			next = [next: [controller:'nav', action: 'area2'], rechazo: rechazo, taskId: params?.taskId, error: error]
		}
			 
		return next
	 }
	 
	 def completeDp01(user, pass, taskId, data){
		 // Invocamos al BPM
		 def dataToBpm=[taskRechazoTotal: data.toString(), taskUser_:user]
		 def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		 return 'OK'
	 }
	 
	 /**
	  * Guarda e informa del rechazo total
	  * @param params
	  * @return
	  */
	 def postDp02(params) {
		 
		log.info "POSTDP02 ::INFORMAR RECHAZO TOTAL:: PARAMETROS -> "+params
		def bis = Bis.findById(params?.bisId)
		def next
		 
		if (params?.ordinario)
			bis.ordinario = params.ordinario
			
		if (params?.encargadoCobranza)
			bis.encargadoCobranza = params.encargadoCobranza
			
		if (params?.entidadCobradora)
			bis.entidadCobradora = params.entidadCobradora
		
		if (params?.direccionEntidad)
			bis.direccionEntidad = params.direccionEntidad
			
		if (params?.comentariosRevision)
			bis.comentariosRevision = params.comentariosRevision
		
		bis.fechaRechazo = new Date()
			
		//validamos y guardamos, la costumbre
		if (!bis.validate()){
			next = [next: [action: 'dp02'], model: [bis: bis]]
		}else{
			bis.save(flush: true)
			next = [next: [controller:'nav', action: 'area2'], taskId: params?.taskId, bis: bis]
		}
			 
		return next
	 }
	 
	 def completeDp02(user, pass, taskId, data){
		 // Invocamos al BPM
		 def dataToBpm=[success : data? 'true':'false', taskUser_:user]
		 def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		 //enviar correo
		 if (r){
			 notificarRechazo(data)
		 }
		 return 'OK'
	 }
	 
	 /**
	  * Guarda e informa el analisis de pago
	  * @param params
	  * @return
	  */
	 def postDp03(params) {
		 
		log.info "POSTDP03 ::INFORMAR ANALISIS DE PAGO:: PARAMETROS -> "+params
		def bis = Bis.findById(params?.bisId)
		def next
		 
		if (params?.ordenNumero)
			bis.ordinario = params.ordenNumero
			
		if (params?.encargadoCobranza)
			bis.encargadoCobranza = params.encargadoCobranza
			
		if (params?.entidadCobradora)
			bis.entidadCobradora = params.entidadCobradora
		
		if (params?.direccionEntidad)
			bis.direccionEntidad = params.direccionEntidad
			
		if (params?.comentariosRevision)
			bis.comentariosRevision = params.comentariosRevision
			
		//validamos y guardamos, la costumbre
		if (!bis.validate()){
			next = [next: [action: 'dp03'], model: [bis: bis]]
		}else{
			bis.save(flush: true)
			next = [next: [controller:'nav', action: 'area2'], taskId: params?.taskId, bis: bis]
		}
			 
		return next
	 }
	 
	 def completeDp03(user, pass, taskId, data){
		 // Invocamos al BPM
		 def dataToBpm=[taskOk: data? 'true' : 'false', taskUser_:user]
		 def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		 //enviar correo
		 if (r){
			 notificar(data)
		 }
		 return 'OK'
	 }
	 
	 /**
	  * Guarda los calculos de interez de la solicitud de 77bis > a 10 dï¿½as
	  * @param params
	  * @return
	  */
	 def postDp04(params) {
		 
		log.info "POSTDP04 :: GUARDAR INFO CALCULO INTERES/ANALISIS DE PAGO:: PARAMETROS -> "+params
		def bis = Bis.findById(params?.bisId)
		def error
		def next
		
		if (params?.ufPago)
			bis.ufPago = params.ufPago.toLong()
		
		if (params?.tasaInteres)
			bis.tasaInteres = params.tasaInteres.toDouble()
					
		if (params?.revisadoPor)
			bis.revisadoPor = params.revisadoPor
			
		if (params?.interesTotal)
			bis.totalInteres = params.interesTotal.toLong()
		else
			bis.totalInteres = 0
			
		if (params?.reembolsoTotal)
			bis.totalReembolso = params.reembolsoTotal.toLong()
		else
			bis.totalReembolso = bis.montoAprobado
		
		//Fecha de pago hoy!!
		if (params?.fechaPago)
			bis.fechaPago = new Date().parse("dd-MM-yyyy", params.fechaPago)
		else
			bis.fechaPago=new Date();
		
		//validamos y guardamos, la costumbre
		if (!bis.validate() && error == 0){
			next = [next: [action: 'dp04'], model: [bis: bis]]
		}else{
			bis.save(flush: true)
			next = [next: [controller:'nav', action: 'area2'], taskId: params?.taskId, bis: bis]
		}
			 
		return next
	 }
	 
	 def completeDp04(user, pass, taskId, data){
		 // Invocamos al BPM
		 def dataToBpm=[success: data? 'true' : 'false', taskUser_:user]
		 def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		 return 'OK'
	 }
	 
	 def notificar(def bis) {
		 def usuario       = UsuarioService.getUsuario(bis.creadoPor)
		 //El receptor del correo electronico
		 def mail = ""
		 if (grailsApplication.config.correoFijo == 1)
			 mail = grailsApplication.config.correo
		 else
			 mail = usuario.correoElectronico
		 
		 //Arma el super mail de notificación
		 mailService.sendMail {
			to 		mail
			from 	"Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Notificación Analisis 77bis"
			html 	"<table><tr><td>"/*<img src='cid:logo' />*/ + "</td><td style=\"text-align:right;vertical-align:text-top;\">ORD.N° "+ bis.ordinario +
					"<br>MAT: Devolución administrativa o de pertenencia médica, solicitud de reembolso por aplicación 77 BIS<br>" +
					"<b>Santiago, " + FormatosISLHelper.fechaHoraStatic(new Date()) + "</b></td></tr><tr><td colspan=2> " +
					"<b>DE: JESSICA NAVARRETE FUENTES<br>" +
					"JEFE DE UNIDAD DE REVISIÓN DE CUENTAS MÉDICAS<br>" +
					"INSTITUTO DE SEGURIDAD LABORAL<br><br>" +
					"A: " + bis.encargadoCobranza + "<br>" +
					"ENCARGADO DE COBRANZAS <br>" +
					"ISAPRE " + bis.entidadCobradora + ", " +bis.direccionEntidad + "</td></tr><tr><tdcolspan=2><br></td><tr>" +
					"<tr><td>Folio C.C " + bis.id + "</td><td>Fecha de Revisión " + FormatosISLHelper.fechaHoraStatic(bis.fechaRevision) + "</td></tr>" +
					"<tr><td colspan=2>Mediante la presente, informo a usted que su solicitud de 77 bis fue analizada y precesada según lo que se indica:</td></tr>" +
					"<tr><td><br><br>Monto Solicitado: "+ FormatosHelper.montosStatic(bis.montoSolicitado) +"<br>Monto Aprobado: "+ FormatosHelper.montosStatic(bis.montoAprobado) +"</td><td> Interés: " + FormatosHelper.montosStatic(bis.totalInteres) + "</td></tr>" +
					"<tr><td>Total a Reembolsar: "+ FormatosHelper.montosStatic(bis.totalReembolso) +"</td></tr><tr><tdcolspan=2><br></td><tr>" +
					"<tr><td colspan=2> Comentarios Revisión de Cuentas </td></tr>" +
					"<tr><td colspan=2> " + bis.comentariosRevision + "</td></tr><tr><tdcolspan=2><br></td><tr>" +
					"<tr><td style=\";vertical-align:text-top;\">Sin otro particular, le saluda atentamente</td><td style=\"text-align: right;\">" +
					"<b>JESSICA NAVARRETE FUENTES<br>JEFE DE UNIDAD DE REVISIÓN DE CUENTAS MÉDICAS<br>" +
					"INSTITUTO DE SEGURIDAD LABORAL</b></td></tr></table>"
		 }
	 }
	 
	 def notificarRechazo(def bis) {
		 def usuario       = UsuarioService.getUsuario(bis.creadoPor)
		 //El receptor del correo electronico
		 def mail = ""
		 if (grailsApplication.config.correoFijo == 1)
			 mail = grailsApplication.config.correo
		 else
			 mail = usuario.correoElectronico
		 
		 //Arma el super mail de notificación
		 mailService.sendMail {
			to 		mail
			from 	"Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Notificación Analisis 77bis"
			html 	"<table><tr><td>"/*<img src='cid:logo' />*/ + "</td><td style=\"text-align:right;vertical-align:text-top;\">ORD.N° "+ bis.ordinario +
					"<br>MAT: Devolución administrativa o de pertenencia médica, solicitud de reembolso por aplicación 77 BIS<br>" +
					"<b>Santiago, " + FormatosISLHelper.fechaHoraStatic(new Date()) + "</b></td></tr><tr><td colspan=2> " +
					"<b>DE: JESSICA NAVARRETE FUENTES<br>" +
					"JEFE DE UNIDAD DE REVISIÓN DE CUENTAS MÉDICAS<br>" +
					"INSTITUTO DE SEGURIDAD LABORAL<br><br>" +
					"A: " + bis.encargadoCobranza + "<br>" +
					"ENCARGADO DE COBRANZAS <br>" +
					"ISAPRE " + bis.entidadCobradora + ", " +bis.direccionEntidad + "</td></tr><tr><td colspan=2><br></td><tr>" +
					"<tr><td>Folio C.C " + bis.id + "</td><td>Fecha de Revisión " + FormatosISLHelper.fechaHoraStatic(bis.fechaRevision) + "</td></tr><tr><td colspan=2><br></td><tr>" +
					"<tr><td colspan=2>Mediante la presente, informo a usted el rechazo total de su solicitud de 77 bis por el mismo motivo que se indica: </td></tr>" +
					"<tr><td colspan=2><h4>Comentarios Seguimiento</h4></td></tr>" +
					"<tr><td colspan=2> " + bis.comentariosSeguimiento + "</td></tr><tr><td colspan=2><br></td><tr>" +
					"<tr><td>La cual fue efectuada por un monto de: "+ FormatosHelper.montosStatic(bis.montoSolicitado) +"</td></tr><tr><td colspan=2><br></td><tr>" +
					"<tr><td colspan=2><h4>Comentarios Revisión de Cuentas</h4></td></tr>" +
					"<tr><td colspan=2> " + bis.comentariosRevision + "</td></tr><tr><tdcolspan=2><br></td><tr>" +
					"<tr><td style=\";vertical-align:text-top;\">Sin otro particular, le saluda atentamente</td><td style=\"text-align: right;\">" +
					"<b>JESSICA NAVARRETE FUENTES<br>JEFE DE UNIDAD DE REVISIÓN DE CUENTAS MÉDICAS<br>" +
					"INSTITUTO DE SEGURIDAD LABORAL</b></td></tr></table>"
		 }
	 }
	 
}
