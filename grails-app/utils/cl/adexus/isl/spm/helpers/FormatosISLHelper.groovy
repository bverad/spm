package cl.adexus.isl.spm.helpers

import java.beans.StaticFieldsPersistenceDelegate;
import java.util.Date;

import cl.adexus.helpers.FormatosHelper
import cl.adexus.isl.spm.DetalleFactura
import cl.adexus.isl.spm.DetalleGastosReembolso
import cl.adexus.isl.spm.DIAT;
import cl.adexus.isl.spm.DIEP;
import cl.adexus.isl.spm.PersonaNatural
import cl.adexus.isl.spm.Prestador
import cl.adexus.isl.spm.Reembolso


class FormatosISLHelper extends FormatosHelper {


	def nombreCompleto(PersonaNatural p){
		def n = ""
		if(!p) return n
		 
		if(p.nombre){
			n+=p.nombre
		}
		if(p.apellidoPaterno){
			n+=' '+p.apellidoPaterno
		}
		if(p.apellidoMaterno){
			n+=' '+p.apellidoMaterno
		}

		return n
	}

	def static nombreCompletoStatic(PersonaNatural p){
		def n = ""
		if(!p) return n
		 
		if(p.nombre){
			n+=p.nombre
		}
		if(p.apellidoPaterno){
			n+=' '+p.apellidoPaterno
		}
		if(p.apellidoMaterno){
			n+=' '+p.apellidoMaterno
		}

		return n
	}
	
	def static rutPrestadorStatic(Prestador p){
		if(p.personaJuridica!=null){
			return runFormatStatic(p.personaJuridica.rut)
		}
		if(p.personaNatural!=null){
			return runFormatStatic(p.personaNatural.run)
		}
		
	}
	
	def static nombrePrestadorStatic(Prestador p){
		if(p.personaJuridica!=null){
			return p.personaJuridica.razonSocial;
		}
		if(p.personaNatural!=null){
			return nombreCompletoStatic(p.personaNatural)
		}
		
	}
	
	def periodoSGA(Date fecha){
		if(!fecha) return ''
		def sdf = new java.text.SimpleDateFormat("yyyyMM")
		return Integer.parseInt(sdf.format(fecha))
	}
	
	def direccionCompletaTrabajador(DIAT diat) {		
		if (!diat) return ''
		def direccion = ""
		if (diat?.direccionTrabajadorTipoCalle?.descripcion)
			direccion += diat?.direccionTrabajadorTipoCalle?.descripcion
		if (diat?.direccionTrabajadorNombreCalle)
			direccion += " " + diat?.direccionTrabajadorNombreCalle
		if (diat?.direccionTrabajadorNumero)
			direccion += " " + diat?.direccionTrabajadorNumero
		return direccion
	}
	
	def direccionCompletaEmpleador(DIAT diat) {
		if (!diat) return ''
		def direccion = ""
		if (diat.direccionEmpleadorTipoCalle?.descripcion)
			direccion += diat.direccionEmpleadorTipoCalle?.descripcion
		if (diat.direccionEmpleadorNombreCalle)
			direccion += " " + diat.direccionEmpleadorNombreCalle 
		if (diat.direccionEmpleadorNumero)
			direccion += " " + diat.direccionEmpleadorNumero
		return direccion
	}

	def direccionCompletaTrabajador(DIEP diep) {
		if (!diep) return ''
		def direccion = ""
		if (diep.direccionTrabajadorTipoCalle?.descripcion)
			direccion += diep.direccionTrabajadorTipoCalle?.descripcion
		if (diep.direccionTrabajadorNombreCalle)
			direccion += " " + diep.direccionTrabajadorNombreCalle
		if (diep.direccionTrabajadorNumero)
			direccion += " " + diep.direccionTrabajadorNumero
		return direccion
		
	}
	
	def direccionCompletaEmpleador(DIEP diep) {
		if (!diep) return ''
		def direccion = ""
		if (diep.direccionEmpleadorTipoCalle?.descripcion)
			direccion += diep.direccionEmpleadorTipoCalle?.descripcion
		if (diep.direccionEmpleadorNombreCalle)
			direccion += " " + diep.direccionEmpleadorNombreCalle
		if (diep.direccionEmpleadorNumero)
			direccion += " " + diep.direccionEmpleadorNumero
		return direccion
	}

	def static direccionCompletaTrabajadorStatic(DIAT diat) {
		if (!diat) return ''
		def direccion = ""
		if (diat?.direccionTrabajadorTipoCalle?.descripcion)
			direccion += diat?.direccionTrabajadorTipoCalle?.descripcion
		if (diat?.direccionTrabajadorNombreCalle)
			direccion += " " + diat?.direccionTrabajadorNombreCalle
		if (diat?.direccionTrabajadorNumero)
			direccion += " " + diat?.direccionTrabajadorNumero
		return direccion
	}

	def static direccionCompletaTrabajadorStatic(DIEP diep) {
		if (!diep) return ''
		def direccion = ""
		if (diep.direccionTrabajadorTipoCalle?.descripcion)
			direccion += diep.direccionTrabajadorTipoCalle?.descripcion
		if (diep.direccionTrabajadorNombreCalle)
			direccion += " " + diep.direccionTrabajadorNombreCalle
		if (diep.direccionTrabajadorNumero)
			direccion += " " + diep.direccionTrabajadorNumero
		return direccion
	}

	def static direccionCompletaEmpleadorStatic(DIAT diat) {
		if (!diat) return ''
		def direccion = ""
		if (diat.direccionEmpleadorTipoCalle?.descripcion)
			direccion += diat.direccionEmpleadorTipoCalle?.descripcion
		if (diat.direccionEmpleadorNombreCalle)
			direccion += " " + diat.direccionEmpleadorNombreCalle
		if (diat.direccionEmpleadorNumero)
			direccion += " " + diat.direccionEmpleadorNumero
		return direccion
	}

	def static direccionCompletaEmpleadorStatic(DIEP diep) {
		if (!diep) return ''
		def direccion = ""
		if (diep.direccionEmpleadorTipoCalle?.descripcion)
			direccion += diep.direccionEmpleadorTipoCalle?.descripcion
		if (diep.direccionEmpleadorNombreCalle)
			direccion += " " + diep.direccionEmpleadorNombreCalle
		if (diep.direccionEmpleadorNumero)
			direccion += " " + diep.direccionEmpleadorNumero
		return direccion
	}
	
	def static getNivelComplejidadStr(nivelComplejidad) {
		def nivelComplejidadStr
		switch (nivelComplejidad) {
			case 0:
				nivelComplejidadStr = "Leve"; break
			case 1:
				nivelComplejidadStr = "Menos Grave"; break
			case 2:
				nivelComplejidadStr = "Grave"; break
			case 3:
				nivelComplejidadStr = "Muy Grave"; break
			default:
				nivelComplejidadStr = "No se encuentra complejidad"; break
		}
		return nivelComplejidadStr
	}

	def static montoReemAprobStatic(reembolso) {
		def monto = 0
		def detalles = DetalleGastosReembolso.findAllByReembolso(reembolso)
		for (def detalle in detalles)
		{
			monto += detalle.valorAprobado
		}
		def df = new java.text.DecimalFormat("\$###,###,###,###")
		return df.format(monto)
	}

	def static montoFactEnvPagoStatic(factura) {
		def monto = 0
		def detalles = DetalleFactura.findAllByFactura(factura)
		for (def detalle in detalles)
		{
			monto += detalle.valorCuentaMedica
		}
		def df = new java.text.DecimalFormat("\$###,###,###,###")
		return df.format(monto)
	}
}
