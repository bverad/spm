package cl.adexus.isl.spm

import java.util.Map
import cl.adexus.helpers.FormatosHelper
import cl.adexus.isl.spm.helpers.FormatosISLHelper

/**
 * Servicios REST de Facturas (para ser usados por el BPM)
 *
 */
class FACTRestService {
	def mailService
	def grailsApplication


	public def sendMailFacturaObj(def nombre, def mail, def fid, Date fecha, def errores, def detalles) {
		mailService.sendMail {
			int counter = 1
			String causas = "<br><table>"
			for (def error in errores)
			{
				causas += "<tr><td>" + counter + "</td><td>" + error.mensaje + "</td></tr>"
				counter++
			}
			for (def detalle in detalles)
			{
				def cm = CuentaMedica.findById(detalle.idCuentaMedica)
				def erroresDF = ErrorDetalleFactura.findAllByDetalleFactura(detalle)
				for (def error in erroresDF)
				{
					causas += "<tr><td>" + counter + "</td><td>Cuenta Médica " + cm.folioCuenta + ": " +
							error.mensaje + "</td></tr>"
					counter++
				}
			}

			causas += "</table>"

			to mail
			bcc grailsApplication.config.bccMail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Factura con cuentas erroneas"
			html "Estimado " + nombre + ":<br><br>Lamentamos informarle que su factura con número " + fid +
					" y fecha de revisión " + FormatosHelper.fechaCortaStatic(fecha) +
					//	" la cual contiene los siguientes ítemes: " + tabla +
					" presenta los siguientes errores: " + causas +
					"<br>Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}

	public def sendMailRechFactura(def nombre, def mail, def fid, Date fecha, def tabla, def comentario) {

		mailService.sendMail {
			to mail
			bcc grailsApplication.config.bccMail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Factura rechazada"
			html "Estimado " + nombre + ":<br><br>Lamentamos informarle que su factura con número " + fid +
					" y fecha de revisión " + FormatosHelper.fechaCortaStatic(fecha) + " ha sido rechazada según lo que se detalla a continuación: " +
					tabla + "<br><br>Factura rechazada por decisión del Analista.<br><br>Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}


	public def sendMailRechFacturaAnt(def nombre, def mail, def fid, Date fecha, def tabla, def comentario) {
		mailService.sendMail {
			to mail
			bcc grailsApplication.config.bccMail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Factura rechazada por falta de documento rectificatorio"
			html "Estimado " + nombre + ":<br><br>Lamentamos informarle que su factura con número " + fid +
					" con fecha de revisión " + FormatosHelper.fechaCortaStatic(fecha) + " ha sido rechazada, puesto que" +
					" han transcurrido más de 30 días desde que fue revisada y solicitada la re-factura/nota de crédito; por diferencias arancelarias y/o pertinencia médica." +
					"<br><br>El detalle de la factura original es el siguiente:" + FormatosHelper.blankStatic(comentario) +
					tabla + "<br><br>Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}

	public def sendMailRechDigitador(def nombre, def mail, def fid, Date fecha, def tabla) {
		mailService.sendMail {
			to mail
			bcc grailsApplication.config.bccMail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Factura rechazada por razones administrativas"
			html "Estimado " + nombre + ":<br><br>Lamentamos informarle que su factura con número " + fid +
					" con fecha de emisión " + FormatosHelper.fechaCortaStatic(fecha) + " ha sido rechazada por razones administrativas " +
					"como se detalla a continuación:" + tabla + "<br><br>Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}

	/**
	 * Validaciones de encabezado de factura
	 */
	def r01 (Map params) {
		log.info("Llamada desde el BPM ---> r01 (Hay errores de encabezado FACT?)::"+params)
		// buscar factura
		def factura = Factura.findById(Long.parseLong(params.fid.toString()))
		log.info("factura: ${factura?.id}")
		def contError = 0
		def aprobado = false
		if (factura) {
			def detalleFactura = DetalleFactura.findAllByFactura(factura)
			detalleFactura.each { detalle ->
				def cuentaMedica = CuentaMedica.findById(detalle.idCuentaMedica)
				if (!cuentaMedica) {
					contError++
					log.info "No existe la cuenta medica ${cuentaMedica.folioCuenta}"
					def error = new ErrorDetalleFactura([fecha: new Date(), mensaje: "No existe la cuenta medica ${cuentaMedica.folioCuentaa}", detalleFactura: detalle])
					error.save()
				} else if (factura?.prestador?.id != cuentaMedica?.centroSalud?.prestador?.id) {
					contError++
					log.info "El prestador de la cuenta medica ${cuentaMedica.folioCuenta} es distinto"
					def error = new ErrorDetalleFactura([fecha: new Date(), mensaje: "El prestador de la cuenta medica ${cuentaMedica.folioCuenta} es distinto", detalleFactura: detalle])
					error.save()
				} else if (!cuentaMedica?.esAprobada) {
					contError++
					log.info "La cuenta medica ${cuentaMedica.folioCuenta} no está aprobada"
					def error = new ErrorDetalleFactura([fecha: new Date(), mensaje: "La cuenta medica ${cuentaMedica.folioCuenta} no está aprobada", detalleFactura: detalle])
					error.save()
				}
			}

			log.info("contError: ${contError}")
			log.info("detalleFactura.size(): ${detalleFactura.size()}")
			aprobado = (detalleFactura.size() > contError)

			log.info "aprobado: ${aprobado}"
			return [aprobado: aprobado]
		} else {
			log.info "No existe la factura ${params.fid}"
		}
		log.info "aprobado: ${aprobado}"
		return [aprobado: aprobado]
	}

	/**
	 * Informar al prestador rechazo de factura
	 * 
	 * @param params
	 * @return
	 */
	def r03 (Map params){
		log.info("Llamada desde el BPM ---> r03 (Informar al prestador rechazo de FACT?)::"+params)
		def factura = Factura.findById(Long.parseLong(params.fid.toString()))
		def prestador = factura.prestador
		def detalles = DetalleFactura.findAllByFactura(factura)
		def nombre = FormatosISLHelper.nombrePrestadorStatic(prestador)
		//		def errores = cuentaMedica.errores
		sendMailFacturaObj(nombre.toString(), prestador.email.toString(),
				factura.folio.toString(), new Date(), ErrorFactura.findAllByFactura(factura),
				detalles)//, obtenerTablaDetalles(detalles, false, null))

		return [success: true]
	}

	/**
	 * Validar que los montos de la cuentas de la factura
	 * correspondan a los montos de las cuentas finalmente
	 * 
	 * @param params
	 * @return aprobado si es que no hay ninguna diferencia con ninguna cuenta
	 */
	def r04 (Map params){
		log.info("Llamada desde el BPM ---> r04 ( Validar que los montos de la cuentas de FACT?)::"+params)
		def aprobado = false
		def factura = Factura.findById(Long.parseLong(params.fid.toString()))
		log.info "factura: ${factura}"
		def totalMontoFactura	= 0
		def totalMontoCuentasAprobado	= 0
		if (factura) {
			def detalleFactura = DetalleFactura.findAllByFactura(factura)
			detalleFactura.each { detalleFact ->
				totalMontoFactura += detalleFact.valorCuentaMedica
				def cuentaMedica = CuentaMedica.findById(detalleFact.idCuentaMedica)
				totalMontoCuentasAprobado += cuentaMedica.valorCuentaAprobado;
			}
		}
		aprobado = (totalMontoFactura == totalMontoCuentasAprobado)
		log.info "aprobado: ${aprobado}"
		return [aprobado: aprobado]
	}

	/**
	 * Informar al prestador rechazo de factura, porque no dio ni para NC
	 *
	 * @param params
	 * @return
	 */
	def r05 (Map params){
		log.info("Llamada desde el BPM ---> r05 (Informar al prestador rechazo de FACT)::"+params)

		def factura = Factura.findById(Long.parseLong(params.fid.toString()))
		def prestador = factura.prestador
		def detalles = factura.detalleFactura
		def comentario = factura.comentarioIngreso
		def nombre = FormatosISLHelper.nombrePrestadorStatic(prestador)
		//		def errores = cuentaMedica.errores
		sendMailRechFactura(nombre.toString(), prestador.email.toString(),
				factura.folio.toString(), new Date(),
				obtenerTablaDetalles(DetalleFactura.findAllByFactura(factura), true, null), comentario)

		return [success: true]
	}


	/**
	 * Informar al prestador rechazo de factura, porque no llego nunca la NC o porque no le gust�
	 *
	 * @param params
	 * @return
	 */
	def r06 (Map params){
		log.info("Llamada desde el BPM ---> r06 (Informar al prestador rechazo de FACT (nunca llego NC/ND)?) " +
				"o rechazo del digitador::"+params)

		def factura = Factura.findById(Long.parseLong(params.fid.toString()))
		def prestador = factura.prestador
		def nombre = FormatosISLHelper.nombrePrestadorStatic(prestador)
		def detalles = factura.detalleFactura
		def comentario = factura.comentarioIngreso
		//		def errores = cuentaMedica.errores
		def llegoDocRectif = true
		
		log.info("Verificando status de factura")
		if (factura.status == "ndc"){
			log.debug("status : ndc")
			def notaCred = NotaCredito.findByFacturaOrigen(factura)
			if (notaCred == null)
				llegoDocRectif = false
		}else if (factura.status == "fct"){
			log.debug("status : fct")
			def reFactura = Factura.findByFacturaOrigen(factura)
			if (reFactura == null)
				llegoDocRectif = false
		}
		//meter la condicion de si tiene NC asociadas
		if (!llegoDocRectif){
			log.debug("Notificando rechazo de factura ant")
			sendMailRechFacturaAnt(nombre.toString(), prestador.email.toString(),
					factura.folio.toString(), new Date(),
					obtenerTablaDetalles(DetalleFactura.findAllByFactura(factura),true, null), comentario)
		}else{
			log.debug("Notificando rechazo de factura digitador")
			sendMailRechDigitador(nombre.toString(), prestador.email.toString(),
					factura.folio.toString(), new Date(),
					obtenerTablaDetalles(DetalleFactura.findAllByFactura(factura), false, factura))
		}
		return [success: true]
	}


	/**
	 * Enviar la factura a pago
	 * 
	 * @param params
	 * @return success si es que se pudo enviar
	 */
	def r07 (Map params){
		log.info("Llamada desde el BPM ---> r07 (Enviar Factura a Pago) ::"+params)

		log.info "PARAMS:"+params
		log.info "params.fid:"+params.fid
		def factura = Factura.findById(Long.parseLong(params.fid.toString()))
		factura.fechaEnvioPago=new Date();
		def success=(factura.save())

		return [success: success]
	}

	/**
	 * Guardar informacion de pago
	 * @param params
	 * @return
	 */
	def r09 (Map params){
		log.info("BPM CallBack r09")

		log.info "PARAMS:"+params
		log.info "params.fid:"+params.fid

		return [success: true]
	}

	def obtenerTablaDetalles (def detalles, def tieneLinea, def facturaOrigen)
	{
		String tabla = "<br><br><table border =\"1\"><tr><td></td><td>Cuenta Médica No</td><td>Facturado \$</td><td>" +
				"Cuenta Médica \$</td><td>"
		def detNotaCred
		def detReFact
		def status
		if (facturaOrigen == null)
		{
			tabla += "Comentario ISL"
		}
		else
		{
			status = facturaOrigen.status
			if ( status == "fct")
			{
				tabla += "Re-Factura \$"
				def reFactura = Factura.findByFacturaOrigen(facturaOrigen)
				detReFact = DetalleFactura.findAllByFactura(reFactura)
			}
			else if ( status == "ndc")
			{
				tabla += "Nota de Crédito \$"
				def notCred = NotaCredito.findByFacturaOrigen(facturaOrigen)
				detNotaCred = DetalleNotaCredito.findAllByNotaCredito(notCred)
			}
		}
		tabla += "</td></tr>"
		int counter = 1
		long totFact = 0
		long totCM = 0
		for (def detalleFactura in detalles)
		{

			def cm = CuentaMedica.findById(detalleFactura.idCuentaMedica)
			totFact += detalleFactura.valorCuentaMedica
			totCM += cm.valorCuentaAprobado
			tabla = tabla + "<tr><td>"+ counter + "</td><td>" + cm.folioCuenta.toString() + "</td><td>" +
					detalleFactura.valorCuentaMedica.toString() + "</td><td>" + cm.valorCuentaAprobado.toString() +
					"</td><td>"
			if (facturaOrigen == null)
			{
				tabla += FormatosHelper.blankStatic(detalleFactura.comentarioIngreso)
			}
			else
			{
				if ( status == "fct")
				{
					//reviso todos los ítemes en la refactura
					for (def detalleReFactura in detReFact)
					{
						//si tienen el mismo id
						if (detalleFactura.idCuentaMedica == detalleReFactura.idCuentaMedica)
						{
							tabla += detalleReFactura.valorCuentaMedica.toString()
						}
					}
				}
				else if ( status == "ndc")
				{
					//reviso todos los ítemes en la refactura
					for (def detalleNC in detNotaCred)
					{
						//si tienen el mismo id
						if (detalleFactura.idCuentaMedica == detalleNC.idCuentaMedica)
						{
							tabla += detalleNC.valorCuentaMedica.toString()
						}
					}
				}
			}
			tabla += "</td></tr>"
			counter++
		}

		def diferenciaSuma       = totCM - totFact
		tabla = tabla + "</table><br>Total Factura \t\t\$" + totFact.toString() + "<br>Total Cuentas Médicas \t\$" + totCM.toString() +
				"<br>Diferencia \t\$" + diferenciaSuma.toString()
		def objSol = ""
		def difPos = ""
		if (diferenciaSuma < 0)
		{
			objSol = "Nota de Crédito"
			difPos = ((-1) * diferenciaSuma).toString()
		}
		else
		{
			objSol = "Factura"
			difPos = diferenciaSuma.toString()
		}
		if (tieneLinea)
		{
			tabla = tabla + "<br><br>" + objSol +": \$" + difPos
		}
		//	log.info(tabla)
		return tabla
	}


}
