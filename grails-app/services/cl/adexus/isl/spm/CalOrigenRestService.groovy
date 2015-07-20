package cl.adexus.isl.spm

import cl.adexus.helpers.FormatosHelper
import cl.adexus.isl.spm.domain.Constantes
import cl.adexus.isl.spm.domain.RECAPDF
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.isl.spm.enums.RetornoWSEnum


/**
 * Servicios REST de Calificacion de Origen (para ser usados por el BPM)
 *
 */
class CalOrigenRestService {

	def mailService
	def UsuarioService
	def SUSESOService
	def PDFService
	def grailsApplication

	/**
	 * 
	 * @param nombre_usuario
	 * @param mail
	 * @param numero_siniestro
	 * @param calificacion
	 * @param nombreArchivo
	 * @param archivo
	 * @return
	 */
	public def sendMailRECA(def nombre_usuario, def mail, def numero_siniestro, def calificacion,
			def nombreArchivo, def archivo) {

		mailService.sendMail {
			multipart true
			to mail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			//bcc grailsApplication.config.bccMail
			subject "Resolución de Calificación para el siniestro " + numero_siniestro +"."
			//dependiendo del tipo de objeto que se genere se debe reemplazar el metodo para obtener los bytes
			attach nombreArchivo, "application/pdf", archivo
			html "Estimado " + nombre_usuario + ":<br><br>" +
					"Se ha calificado el siniestro " + numero_siniestro + " como '" + calificacion + "'.<br><br>" +
					"Se adjunta la resolución de calificación del siniestro para los tramites que se estime conveniente.<br><br>" +

					"Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}

	/**
	 * 
	 * @param usuario
	 * @param mail
	 * @param siniestro
	 * @param codigoRetorno
	 * @param mensajeRetorno
	 * @return
	 */
	public def sendMailErrorRECA(usuario, mail, siniestro, codigoRetorno, mensajeRetorno){
		mailService.sendMail {
			multipart true
			to mail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			//bcc grailsApplication.config.bccMail
			subject "Resolución de Calificación para el siniestro ${siniestro.id}."
			html "Estimado $usuario :<br><br>" +
					"No se genero correctamente la RECA. Al ser enviada a SUSESO retorno el error que tiene por codigo $codigoRetorno.<br><br>" +
					"El mensaje de error asociado a dicho codigo es el siguiente $mensajeRetorno.<br><br>" +

					"Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}

	/**
	 * Obtener Complejidad cuestionario
	 */
	def r01at (Map params) {
		log.info("Llamada desde el BPM ---> r01 (Obtener Complejidad cuestionario)::"+params)

		def siniestro = Siniestro.findById(params.sid)
		def sdaat = SDAAT.findBySiniestro(siniestro)
		def tNivelComplejidad

		if (!sdaat){tNivelComplejidad = 0}
		else{tNivelComplejidad = (sdaat.cuestionarioComplejidad.complejidadCalculada).toInteger()}

		//setea la complejidad
		if (tNivelComplejidad == 0){
			siniestro.nivelComplejidad = tNivelComplejidad
			siniestro.save(flush: true)
		}
		log.info("COMPLEJIDAD RECUPERADA --> "+tNivelComplejidad)

		return [tNivelComplejidad:tNivelComplejidad]
	}


	/**
	 * Enviar RECA
	 */
	def r02at (Map params) {
		log.info("ejecutando r02at")
		log.info("Llamada desde el BPM ---> r02at (Enviar RECA)::"+params)
		return r02(params)
	}

	def r02ep (Map params) {
		log.info("Llamada desde el BPM ---> r02ep (Enviar RECA)::"+params)
		return r02(params)
	}

	def r02 (Map params) {
		log.info("Llamada desde el BPM ---> r02 (Enviar RECA)::"+params)
		def siniestro = Siniestro.findById(params.sid)
		log.info("siniestro : $siniestro")

		log.info("Validando si el siniestro es nulo")
		if(siniestro==null){
			log.info("siniestro no existe, retornando sucess: false")
			return [success:false]
		}else{
			log.info("siniestro existe")
		}
		log.info("Verificacion de existencia de siniestro finalizada")

		log.info("Buscando RECA por siniestro")
		def reca = RECA.findBySiniestro(siniestro)

		log.info("Verificando existencia de RECA")
		if(reca==null){
			log.info("La RECA es nula, retornando sucess:false")
			return [success:false]
		}else
			log.info("La RECA existe")
		log.info("Verificacion de existencia de RECA finalizada")

		// Envia a SUSESO
		log.info("Verificando si el siniestro corresponde a una enfermedad profesional")
		if (siniestro.esEnfermedadProfesional){
			log.info("es enfermedad profesional")
			SUSESOService.enviarRECAEP(reca)
			log.info("reca enviada")
		}else if(!siniestro.esEnfermedadProfesional){
			log.info("es accidente del trabajo")
			SUSESOService.enviarRECAAT(reca)
			log.info("reca enviada")
		}
		log.info("Verificacion de si el siniestro corresponde a una enfermedad profesional terminado")


		return [success:true] //Revisar que hay que devolver
	}

	/**
	 * Informar al usuario que genero la denuncia
	 */
	def r03at (Map params) {
		log.info("Llamada desde el BPM ---> r03at (Informar al usuario que genero la denuncia)::"+params)
		return r03(params)
	}

	def r03ep (Map params) {
		log.info("Llamada desde el BPM ---> r03ep (Informar al usuario que genero la denuncia)::"+params)
		return r03(params)
	}

	def r03 (Map params) {
		log.info("Llamada desde el BPM ---> r03 (Informar al usuario que genero la denuncia)::"+params)

		def siniestro = Siniestro.findById(params.sid)
		def usuario
		if(siniestro.diatOA!=null){
			usuario=UsuarioService.getUsuario(siniestro.diatOA.creadoPor)
		}
		if(siniestro.diepOA!=null){
			usuario=UsuarioService.getUsuario(siniestro.diepOA.creadoPor)
		}
		if(usuario!=null){

			def reca = RECA.findBySiniestro(siniestro)
			def fullName = usuario.nombres+" "+usuario.apellidoPaterno+" "+usuario.apellidoMaterno
			//TODO: correo mio!, cambiar y tal
			def email = ""
			if (grailsApplication.config.correoFijo == 1){
				email = grailsApplication.config.correo
			}
			else{
				email = usuario.correoElectronico
			}
			def nombreArchivo = "RECA_" + siniestro.id +".pdf"


			//verifica si la reca se envio correctamente, de lo contrario envia mail notificando el error
			def resultReca = reca?.xmlRecibido ? true : false
			log.info("Verificando si RECA retorno los datos correctamente se recibio xml : ${resultReca}")

			def retorno = SUSESOService.getRetorno(reca.xmlRecibido)
			def codigoRetorno = retorno.text()
			log.info("El codigo de retorno corresponde a : $codigoRetorno")
			if(codigoRetorno.length() > 3){
				log.info ("-22 retorna mensaje completo, cortando cadena")
				codigoRetorno = codigoRetorno.substring(0,3)
			}
			 
			log.info("Valor retorno editado : $codigoRetorno")

			if(!codigoRetorno.equals(RetornoWSEnum.CODE_40.getCodigo())){
				log.info("Enviando notificacion respecto a que RECA devolvio un codigo erroneo : $codigoRetorno")
				def mensajeRetorno = RetornoWSEnum.findByKey(codigoRetorno).getDescripcion()
				
				sendMailRECA(fullName
					, email
					, siniestro.id
					, reca.calificacion.descripcion
					, nombreArchivo
					, genRECAPDF(siniestro, reca))
				
				//futura implementacion para informar a soporte respecto a que no se efectuo correctamente el ingreso
				/*sendMailErrorRECA(fullName
						, email
						, siniestro
						, retorno
						, mensajeRetorno)*/
				return [sucess: true]
			}else{
				log.info("Enviando RECA")
				sendMailRECA(fullName
						, email
						, siniestro.id
						, reca.calificacion.descripcion
						, nombreArchivo
						, genRECAPDF(siniestro, reca))
				return [sucess: true]
			}


		}else{
			return [sucess: false]
		}
	}

	/**
	 * Regulariza Denuncia OA
	 */
	def r04at (Map params) {
		log.info("Llamada desde el BPM ---> r04at (Regulariza Denuncia OA)::"+params)
		return r04(params)
	}

	def r04ep (Map params) {
		log.info("Llamada desde el BPM ---> r04ep (Regulariza Denuncia OA)::"+params)
		return r04(params)
	}

	def r04 (Map params) {
		log.info("Llamada desde el BPM ---> r04 (Regulariza Denuncia OA)::"+params)
		def siniestro = Siniestro.findById(params.sid)

		//cambia el tipo de evento del siniestro, deberia bastar para identificar la diep o diat OA correspondiente
		if (siniestro.esEnfermedadProfesional){siniestro.esEnfermedadProfesional = false}
		else {siniestro.esEnfermedadProfesional = true}

		if (siniestro.save(flush:true)){
			//Envio a la SUSESO para generar le xml correspondiente
			if (siniestro.esEnfermedadProfesional){
				SUSESOService.enviarDIEP(siniestro.diepOA)
			}else if(!siniestro.esEnfermedadProfesional){
				SUSESOService.enviarDIAT(siniestro.diatOA)
			}
		}
		return [success:true]
	}

	/**
	 * Retorna esLaboral y cambioTipoSiniestro
	 */

	def r05at (Map params) {
		log.info("Llamada desde el BPM ---> r05at (Retorna esLaboral y cambioTipoSiniestro)::"+params)
		return r05(params)
	}

	def r05ep (Map params){
		log.info("Llamada desde el BPM ---> r05ep (Retorna esLaboral y cambioTipoSiniestro)::"+params)
		return r05(params)
	}


	def r05 (Map params){
		log.info("Llamada desde el BPM ---> r05 (Retorna esLaboral y cambioTipoSiniestro)::"+params)
		def siniestro = Siniestro.findById(params.sid)
		def reca = RECA.findBySiniestro(siniestro)
		def cambioTipoSiniestro
		def esLaboral = false

		if ((reca.calificacion.eventoSiniestro.codigo == '3'? reca.calificacion.eventoSiniestro.codigo : 'trabajo') != (siniestro.esEnfermedadProfesional? '3' : 'trabajo')){
			cambioTipoSiniestro = true
		}else{
			cambioTipoSiniestro = false
		}
		if (reca.calificacion.origen.codigo == '1'){esLaboral = true}

		log.info("esLaboral--> "+esLaboral)
		log.info("cambioTipoSiniestro--> "+cambioTipoSiniestro)

		return [esLaboral: esLaboral.toString(), cambioTipoSiniestro: cambioTipoSiniestro.toString()]
	}


	//Privados

	def genRECAPDF(def siniestro, def reca){
		def tipoPDF = Constantes.TIPOS_PDF_RECA
		def RECAPDF dataPDF = new RECAPDF()

		//Mapear los datos del siniestro y la reca a dataPDF
		dataPDF.setIdReca(reca.id.toString())
		dataPDF.setFechaResol(FormatosHelper.fechaCortaStatic(reca.fechaCalificacion))
		dataPDF.setCun(siniestro.cun)
		dataPDF.setNombreTrab(FormatosISLHelper.nombreCompletoStatic(siniestro.trabajador))
		dataPDF.setRunTrab(FormatosHelper.runFormatStatic(siniestro.trabajador.run))
		dataPDF.setFechaAccidente(FormatosHelper.fechaCortaStatic(siniestro.fecha))

		if (siniestro.esEnfermedadProfesional)
		{
			def diepOA = siniestro.diepOA
			dataPDF.setDirTrab(
					FormatosISLHelper.direccionCompletaTrabajadorStatic(diepOA))
			dataPDF.setComunaTrab(diepOA.direccionTrabajadorComuna.descripcion)
			dataPDF.setTelefonoTrab(FormatosHelper.blankNumStatic(diepOA.telefonoTrabajador))
		}
		else
		{
			def diatOA = siniestro.diatOA

			dataPDF.setDirTrab(
					FormatosISLHelper.direccionCompletaTrabajadorStatic(diatOA))
			dataPDF.setComunaTrab(diatOA.direccionTrabajadorComuna.descripcion)
			dataPDF.setTelefonoTrab(FormatosHelper.blankNumStatic(diatOA.telefonoTrabajador))
		}
		dataPDF.setNombreEmpl(siniestro.empleador.razonSocial)
		dataPDF.setRunEmpl(FormatosHelper.runFormatStatic(siniestro.empleador.rut))
		dataPDF.setCodCalif(reca.calificacion.codigo)
		dataPDF.setIndicacion(reca.indicacion)
		dataPDF.setOrgAdmin("Instituto de Seguridad Laboral")

		ByteArrayOutputStream pdf = PDFService.doPdf(dataPDF, tipoPDF)
		return(pdf.toByteArray());
	}
}
