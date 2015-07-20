package cl.adexus.isl.spm

import org.apache.shiro.SecurityUtils;

import cl.adexus.helpers.DataSourceHelper
import cl.adexus.isl.spm.domain.Constantes;
import cl.adexus.isl.spm.domain.OpaPDF;
import cl.adexus.isl.spm.domain.OpaepPDF;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;

class DenunciaService {
	def PDFService
	def SUSESOService
	
	def diatPrevias(fechaSiniestro,empleador,trabajador) {
		return diatPrevias(fechaSiniestro,empleador,trabajador,null)
	}
	
	def diatPrevias(fechaSiniestro,empleador,trabajador,tipoDenunciante) {
		denunciasPrevias("DIAT",fechaSiniestro,empleador,trabajador,tipoDenunciante)
    }
	
	def diepPrevias(siniestro,empleador,trabajador) {
		return diepPrevias(siniestro,empleador,trabajador,null)
	}
	
	def diepPrevias(siniestro,empleador,trabajador,tipoDenunciante) {
		denunciasPreviasSiniestro(siniestro,empleador,trabajador,tipoDenunciante)
	}
	
	def denunciasPrevias(tipo,fechaSiniestro,empleador,trabajador,tipoDenunciante){
		def formatDateTimeFunction = (new DataSourceHelper()).formatDatetimeFunction();
		
		def denunciasPrevias;
		
		if(tipoDenunciante){
			denunciasPrevias = DIAT.executeQuery(
				"select d "+
				"  from "+tipo+" as d, Siniestro as s "+
				" where d.siniestro=s.id "+
				"   and "+formatDateTimeFunction+"(s.fecha, 'dd-MM-yyyy')=?"+
				"   and s.empleador=?"+
				"   and s.trabajador=?"+
				"   and d.calificacionDenunciante=?",[new java.text.SimpleDateFormat("dd-MM-yyyy").format(fechaSiniestro),empleador,trabajador,tipoDenunciante])
		}else{
			denunciasPrevias = DIAT.executeQuery(
				"select d "+
				"  from "+tipo+" as d, Siniestro as s "+
				" where d.siniestro=s.id "+
				"   and "+formatDateTimeFunction+"(s.fecha, 'dd-MM-yyyy')=?"+
				"   and s.empleador=?"+
				"   and s.trabajador=?",[new java.text.SimpleDateFormat("dd-MM-yyyy").format(fechaSiniestro),empleador,trabajador])
		}
		return denunciasPrevias
    }

	def denunciasPreviasSiniestro(siniestro,empleador,trabajador,tipoDenunciante){
		def formatDateTimeFunction = (new DataSourceHelper()).formatDatetimeFunction();
		
		def denunciasPrevias;
		
		if(tipoDenunciante){
			denunciasPrevias = DIEP.executeQuery(
				"select d "+
				"  from DIEP as d, Siniestro as s "+
				" where d.siniestro=? "+
				"   and s.empleador=?"+
				"   and s.trabajador=?"+
				"   and d.calificacionDenunciante=?",[siniestro,empleador,trabajador,tipoDenunciante])
		}
		else
		{
			denunciasPrevias = DIEP.executeQuery(
				"select d "+
				"  from DIEP as d, Siniestro as s "+
				" where d.siniestro=? "+
				"   and s.empleador=?"+
				"   and s.trabajador=?",[siniestro,empleador,trabajador])
		}
		return denunciasPrevias
	}

		
	def getDenuncia(params) {
		// Esta variable especifica un cambio en la ubicación del "volver"
		// por default
		def volver = params.volver
		if ("DIAT".equals(params.tipo)) {
			def diat = DIAT.findById(params.id)
			return (['next':[action: 'diatView'],  model: ['diat': diat, 'volver' : volver]])
		} else {
			def diep = DIEP.findById(params.id)
			return (['next':[action: 'diepView'],  model: ['diep': diep, 'volver' : volver]])
		}
	}
	
	def getOP(params) {
		def FormatosISLHelper f=new FormatosISLHelper()
		byte[] b
		if ("OPA".equals(params.tipo)) {
			def tipoPDF = Constantes.TIPOS_PDF_OPA
			def opa = OPA.findById(params.id)
			if (!opa.siniestro.isAttached()) {
				opa.siniestro.attach()
			}
			OpaPDF opaPDF = new OpaPDF()

			def nombrePrestador = f.nombrePrestadorStatic(opa?.centroAtencion?.prestador)
			
			opaPDF.setRazonSocialDelPrestador(nombrePrestador)
			opaPDF.setPrestador(nombrePrestador)
			
			opaPDF.setIdDocumento(opa?.id?.toString())
			opaPDF.setFechaCreacion(f.fechaCorta(opa?.fechaCreacion))
			opaPDF.setInicioVigencia(f.fechaCorta(opa?.inicioVigencia))
			opaPDF.setDuracion(opa?.duracionDias + '')
			opaPDF.setTerminoVigencia(f.fechaCorta(opa?.inicioVigencia + opa?.duracionDias))
			opaPDF.setCentroAtencion(opa?.centroAtencion?.nombre)
			
			opaPDF.setDireccionCentroAtencion(opa?.centroAtencion?.direccion)
	
			if (opa?.siniestro?.fecha)
				opaPDF.setSiniestroFecha(f.fechaCorta((opa?.siniestro?.fecha)))
			opaPDF.setSiniestroRelato(opa?.siniestro?.relato)
					
			opaPDF.setTrabajadorRun(f.run(opa?.siniestro?.trabajador?.run))
			opaPDF.setTrabajadorNombresApellidos(f.nombreCompleto(opa?.siniestro?.trabajador))
			log.info(opa?.direccionTrabajador)
			if(opa?.direccionTrabajador?.trim()!='')
				opaPDF.setTrabajadorDireccion(opa?.direccionTrabajador)
			if(opa?.comunaTrabajador!=null)
				opaPDF.setTrabajadorComuna(opa?.comunaTrabajador?.descripcion)
			if(opa?.telefonoTrabajador!=null)
				opaPDF.setTrabajadorTelefono(opa?.telefonoTrabajador.toString())
			opaPDF.setTrabajadorSexo(opa?.siniestro?.trabajador?.sexo == 'M' ? 'Masculino' : 'Femenino');
			opaPDF.setTrabajadorFechaNacimiento(f.fechaCorta(opa?.siniestro?.trabajador?.fechaNacimiento));
			opaPDF.setTrabajadorEmail("")
			
			opaPDF.setEmpleadorRut(f.run(opa?.siniestro?.empleador?.rut))
			opaPDF.setEmpleadorRazonSocial(opa?.siniestro?.empleador?.razonSocial)
			
			opaPDF.setEmitidoPor(opa?.usuarioEmisor)
			opaPDF.setImpresoPor(SecurityUtils.subject?.principal)
			opaPDF.setFechaHoraImpresion(f.fechaCompleta(new Date()))
			
			ByteArrayOutputStream pdf = PDFService.doPdf(opaPDF, tipoPDF)
			b = pdf.toByteArray();		
		} else {
			def opaep = OPAEP.findById(params.id)
			if (!opaep.siniestro.isAttached()) {
				opaep.siniestro.attach()
			}
			def tipoPDF = Constantes.TIPOS_PDF_OPAEP
			// Se hace mapping de los datos a mostrar en el archivo
			OpaepPDF opaepPDF = new OpaepPDF()
			
			opaepPDF.setTipoPatologia(opaep?.siniestro?.tipoPatologia?.descripcion)

			def nombrePrestador = f.nombrePrestadorStatic(opaep?.centroAtencion?.prestador)

			opaepPDF.setRazonSocialDelPrestador(nombrePrestador)
			opaepPDF.setPrestador(nombrePrestador)
			
			opaepPDF.setIdDocumento(opaep?.id?.toString())
			opaepPDF.setFechaCreacion(f.fechaCorta(opaep?.fechaCreacion))
			opaepPDF.setInicioVigencia(f.fechaCorta(opaep?.inicioVigencia))
			opaepPDF.setDuracion(opaep?.duracionDias + '')
			opaepPDF.setTerminoVigencia(f.fechaCorta(opaep?.inicioVigencia + opaep?.duracionDias))
			opaepPDF.setCentroAtencion(opaep?.centroAtencion?.nombre)
			opaepPDF.setDireccionCentroAtencion(opaep?.centroAtencion?.direccion)
			
			opaepPDF.setRelato(opaep?.siniestro?.relato)
	
			opaepPDF.setTrabajadorRun(f.run(opaep?.siniestro?.trabajador.run))
			opaepPDF.setTrabajadorNombresApellidos(f.nombreCompleto(opaep?.siniestro?.trabajador))
			if(opaep?.direccionTrabajador?.trim()!='')
				opaepPDF.setTrabajadorDireccion(opaep?.direccionTrabajador)
			if(opaep?.comunaTrabajador!=null)
				opaepPDF.setTrabajadorComuna(opaep?.comunaTrabajador?.descripcion)
			if(opaep?.telefonoTrabajador!=null)
				opaepPDF.setTrabajadorTelefono(opaep?.telefonoTrabajador.toString())
			opaepPDF.setTrabajadorSexo(opaep?.siniestro?.trabajador?.sexo == 'M' ? 'Masculino' : 'Femenino')
			opaepPDF.setTrabajadorFechaNacimiento(f.fechaCorta(opaep?.siniestro?.trabajador?.fechaNacimiento))
			opaepPDF.setTrabajadorEmail("")
			
			opaepPDF.setEmpleadorRut(f.run(opaep?.siniestro?.empleador?.rut))
			opaepPDF.setEmpleadorRazonSocial(opaep?.siniestro?.empleador?.razonSocial)
			
			def username=SecurityUtils.subject?.principal
			opaepPDF.setEmitidoPor(opaep?.usuarioEmisor)
			opaepPDF.setImpresoPor(username)
			opaepPDF.setFechaHoraImpresion(f.fechaCompleta(new Date()))
			
			ByteArrayOutputStream pdf = PDFService.doPdf(opaepPDF, tipoPDF)
			b = pdf.toByteArray()
		}
		return b;
	}
	
	def enviarDIATaSUSESO(diatId){
		def ret=[:]
		log.debug "enviarDIATaSUSESO::diatId :"+diatId
		def diat=DIAT.get(diatId)
		ret.diat=diat
		if(!diat){
			ret.error="no existe DIAT con diatId:"+diatId
			log.error ret.error
		}else if(diat.xmlRecibido!=null){
			ret.error="DIAT ya enviada. xmlRecibido!=null"
			log.error ret.error
		}else{
			if(diat.xmlEnviado==null){
				log.debug "Armando XML y enviando."
				SUSESOService.enviarDIAT(diat,false)
			}else{
				log.debug "XML armado. Enviando XML"
				SUSESOService.doSendDIAT(diat)
			}
		}
		return ret
		
	}
	
	def enviarDIEPaSUSESO(diepId){
		def ret=[:]
		log.debug "enviarDIEPaSUSESO::diepId :"+diepId
		def diep=DIEP.get(diepId)
		ret.diep=diep
		if(!diep){
			ret.error="no existe DIEP con diepId:"+diepId
			log.error ret.error
		}else if(diep.xmlRecibido!=null){
			ret.error="DIEP ya enviada. xmlRecibido!=null"
			log.error ret.error
		}else{
			if(diep.xmlEnviado==null){
				log.debug "Armando XML y enviando."
				SUSESOService.enviarDIEP(diep,false)
			}else{
				log.debug "XML armado. Enviando XML"
				SUSESOService.doSendDIEP(diep)
			}
		}
		return ret
		
	}
}
