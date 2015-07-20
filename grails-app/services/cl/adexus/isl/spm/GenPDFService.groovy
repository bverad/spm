package cl.adexus.isl.spm

import cl.adexus.helpers.FormatosHelper
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.isl.spm.domain.ALLAPDF
import cl.adexus.isl.spm.domain.ALMEPDF
import cl.adexus.isl.spm.domain.Constantes
import cl.adexus.isl.spm.domain.ODA_AHIPDF
import cl.adexus.isl.spm.domain.ODA_EAVDPDF
import cl.adexus.isl.spm.domain.RELAPDF
import cl.adexus.isl.spm.domain.SolicitudReembolsoPDF;

class GenPDFService {
	def PDFService
	def mailService
	def grailsApplication
	def UsuarioService
	
	def genSolReembolsoPDF(Reembolso reembolso)
	{
		def tipoPDF                       = Constantes.TIPOS_PDF_SOL_REEMBOLSO
		def SolicitudReembolsoPDF dataPDF = new SolicitudReembolsoPDF()
		def siniestro                     = reembolso.siniestro
		def relacionDelSolicitante
		if (reembolso.solicitanteTipo == 'Otro') {
			relacionDelSolicitante = reembolso.solicitanteRelacion
		} else {
			relacionDelSolicitante = reembolso.solicitanteTipo
		}
		
		// Mapear los datos del siniestro y el pago a dataPDF
		dataPDF.setIdSiniestro(siniestro.id.toString())
		
		def tipoSiniestro
		if (siniestro?.esEnfermedadProfesional == true) {
			tipoSiniestro = "Enfermedad Profesional"
		} else {
			def diat = siniestro?.diatOA
			if (diat?.esAccidenteTrayecto == true) {
				tipoSiniestro = "Accidente de Trayecto"
			} else {
				tipoSiniestro = "Accidente del Trabajo"
			}
		}
		dataPDF.setDescTipoSiniestro(tipoSiniestro)
		
		if (reembolso.trasladoPaciente)
		{
			dataPDF.setEsTraslado("X")
		}
		if (reembolso.medicamentos)
		{
			dataPDF.setEsMedicamento("X")
		}
		if (reembolso.hospitalizacion)
		{
			dataPDF.setEsHospitalizacion("X")
		}
		if (reembolso.alojamiento)
		{
			dataPDF.setEsAlojamiento("X")
		}
		dataPDF.setNombreTrab(FormatosISLHelper.nombreCompletoStatic(reembolso.trabajador))
		dataPDF.setRunTrab(FormatosHelper.runFormatStatic(reembolso.trabajador.run))
		dataPDF.setDirTrab(reembolso.trabajadorDireccion)
		dataPDF.setComunaTrab(reembolso.trabajadorComuna.descripcion)
		dataPDF.setTelefonoTrab(reembolso.trabajadorTelefonoFijo)
		dataPDF.setCelularTrab(reembolso.trabajadorCelular)
		dataPDF.setEmailTrab(reembolso.trabajadorEmail)
		dataPDF.setDescTipoSolicitante(reembolso.solicitanteTipo)
		dataPDF.setNombreSolicitante(FormatosISLHelper.nombreCompletoStatic(reembolso.solicitante))
		dataPDF.setRutSolicitante(FormatosHelper.runFormatStatic(reembolso.solicitante.run))
		dataPDF.setRelacionSolicitante(relacionDelSolicitante)
		dataPDF.setMontoSolicitado(reembolso.montoSolicitado.toString())
		dataPDF.setRutPago(FormatosHelper.runFormatStatic(reembolso.cobrador.run))
		dataPDF.setNombreCobrador(FormatosISLHelper.nombreCompletoStatic(reembolso.cobrador))

		if (reembolso.tipoPagoDeposito)
		{
			dataPDF.setDescTipoPago("Depósito")
			dataPDF.setDescTipoCuenta(reembolso.tipoCuenta.descripcion)
			dataPDF.setNumCuenta(reembolso.numero)
			dataPDF.setBanco(reembolso.banco.descripcion)
		}
		else if (reembolso.tipoPagoPresencial)
		{
			dataPDF.setDescTipoPago("Pago Presencial Banco Estado")
		}

		dataPDF.setObservaciones(reembolso.observaciones)
		dataPDF.setFechaRecepcion(FormatosISLHelper.fechaCortaStatic(new Date()))
		ByteArrayOutputStream pdf = PDFService.doPdf(dataPDF, tipoPDF)
		
		return pdf
	}

	def genAvisoReembolsoCorreo(Reembolso reembolso)
	{
		def detalles      = DetalleGastosReembolso.findAllByReembolso(reembolso)
		def usuario       = UsuarioService.getUsuario(reembolso.creadoPor)
		def nombreUsuario = FormatosHelper.nombreUsuarioStatic(usuario)
		// TODO: correo mio!, cambiar y tal (mismo chiste que en CalOrigenRestService)
		// def mail = usuario.correoElectronico
		def mail = ""
		if (grailsApplication.config.correoFijo == 1){
			mail = grailsApplication.config.correo
		}
		else{
			mail = usuario.correoElectronico
		}
		def siniestro     = reembolso.siniestro
		def tipoSiniestro
		if (siniestro?.esEnfermedadProfesional == true) {
			tipoSiniestro = "Enfermedad Profesional"
		} else {
			def diat = siniestro?.diatOA
			if (diat?.esAccidenteTrayecto == true) {
				tipoSiniestro = "Accidente de Trayecto"
			} else {
				tipoSiniestro = "Accidente del Trabajo"
			}
		}
		def relacionDelSolicitante
		if (reembolso.solicitanteTipo == 'Otro') {
			relacionDelSolicitante = reembolso.solicitanteRelacion
		} else {
			relacionDelSolicitante = reembolso.solicitanteTipo
		}
		
		mailService.sendMail {
			long valAprob = 0
			int counter = 1
			String tabla = "<br><br><table border =\"1\"><tr><th></th><th>Fecha</th><th>Num. B/F</th><th>" + 
			"Concepto</th><th>Rut Proovedor</th><th>Nombre o R. S.</th><th>V. Doc</th>" +
			"<th>V. Sol</th><th>V. Aprob</th><th>Comentario</th></tr>"
			for (def detalle in detalles)
			{
				tabla += "<tr><td>" + counter + "</td><td>" + FormatosISLHelper.fechaCortaStatic(detalle.fechaGasto) + 
				"</td><td>" + detalle.numero.toString() + "</td><td>" +
				detalle.concepto.descripcion + "</td><td>"
				
				if( detalle.tipoProveedorPersonaJuridica)
				{
					tabla +=  FormatosHelper.runFormatStatic(detalle.proveedorJuridico.rut) + "</td><td>" +
					 detalle.proveedorJuridico.razonSocial + "</td><td>"
				}
				else if(detalle.tipoProveedorPersonaNatural)
				{
					tabla +=  FormatosHelper.runFormatStatic(detalle.proveedorNatural.run) + "</td><td>" +
					 FormatosISLHelper.nombreCompletoStatic(detalle.proveedorNatural) + "</td><td>"
				}
				tabla +=  detalle.valorDocumento.toString()  + "</td><td>" + detalle.valorSolicitado.toString()  + 
				"</td><td>" + detalle.valorAprobado.toString() + "</td><td>" + 
				FormatosISLHelper.blankStatic(detalle.comentario) + "</td></tr>"
				counter++
				valAprob += detalle.valorAprobado
			}

			tabla += "</table>"
		//	multipart true
			to mail
		    from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
		//    inline "logo", "image/jpeg", new File(grailsApplication.config.logo)
			subject "Resultado solicitud reembolso"
			html "<table><tr><td>"/*<img src='cid:logo' />*/ + "</td><td style=\"text-align:right;vertical-align:text-top;\">MAT:Informar Reembolso de Gastos Médicos<br>" +
					"<b>Santiago, " + FormatosISLHelper.fechaHoraStatic(new Date()) + "</b></td></tr><tr><td colspan=2> " +
					"<b>DE: LIZZY VIDAL NEIRA<br>" +
					"JEFE DE UNIDAD DE REVISIÓN DE CUENTAS MÉDICAS<br>" +
					"INSTITUTO DE SEGURIDAD LABORAL<br><br>" +
					"A: " + nombreUsuario + "<br><br>" +
					"Datos Trabajador</b><br>" +
					"<table><tr><td>Nombre Trabajador:</td><td>" + FormatosISLHelper.nombreCompletoStatic(reembolso.trabajador) + 
					"</td></tr><tr><td>RUN Trabajador:</td><td>" + FormatosHelper.runFormatStatic(reembolso.trabajador.run) + "<br>" +
					"</td></tr><tr><td>Número Siniestro:</td><td>" + siniestro.id.toString() + 
					"</td></tr><tr><td>Tipo de Siniestro:</td><td>" + tipoSiniestro + "</td></tr></table><br><br>" +
					"<b>Datos Solicitante</b><br>" +
					"<table><tr><td>Nombre Solicitante:</td><td>" + FormatosISLHelper.nombreCompletoStatic(reembolso.solicitante) + 
					"</td></tr><tr><td>RUN Solicitante:</td><td>" + FormatosHelper.runFormatStatic(reembolso.solicitante.run) + "<br>" +
					"</td></tr><tr><td>Relación con Trabajador:</td><td>" + relacionDelSolicitante + "</td></tr></table><br><br>" +
					"Mediante la presente, informo a usted que su Solicitud de Reembolso de Gastos Médicos " + 
					"fue analizada y procesada según lo siguiente:<br><br>" +
					"<b>Monto Solicitado: " + FormatosHelper.montosStatic(reembolso.montoSolicitado) + "</b>" + tabla + "<br>" +
					"<b>Monto a Reembolsar: " + FormatosHelper.montosStatic(valAprob) + "</b><br><br>" + 
					"</td></tr><tr><td style=\";vertical-align:text-top;\">Sin otro particular, le saluda atentamente</td><td style=\"text-align: right;\">" +
					"<b>LIZZY VIDAL NEIRA<br>JEFE DE UNIDAD DE REVISIÓN DE CUENTAS MÉDICAS<br>" +
					"INSTITUTO DE SEGURIDAD LABORAL</b></td></tr></table>"
		  }
	}
	
}
