package cl.adexus.isl.spm

import java.util.Date;
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.helpers.FechaHoraHelper
import cl.adexus.helpers.FormatosHelper

class InformeOPAService {
	
	//PostDP03, guardar informe OPA
	def guardaInforme(params, def diagnosticos){
			
		log.info "Creación Informe OPA :: params -> "+params
		log.info diagnosticos.size()+ "Diagnosticos ->"+diagnosticos+" de tipo ->"+diagnosticos.getClass()
		
		def siniestro = Siniestro.findById(params?.siniestroId)
		def opaxx = siniestro.opa ? siniestro.opa : siniestro.opaep
		def informe = new InformeOPA()
		def error = 0
		def next
		Date fecha
				
		//arreglo RUN
		params?.medicoRun = params.medicoRun ? ((String)params.medicoRun).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim() : params.medicoRun
		
		//Valida y guarda los datos en informeOPA
		if (siniestro){
			informe.siniestro = siniestro
		}
		
		if (params?.fechaAtencion && params?.horaAtencion){			
			
			//Junto la fecha de atención con la hora de la atención
			fecha = FechaHoraHelper.horaToDate(params.horaAtencion, params.fechaAtencion)
			Date inicioVigencia = opaxx.inicioVigencia
			Date terminoVigencia = inicioVigencia + opaxx.duracionDias

			//Compruebo que la fecha de atención este dentro del rango de la opa/opaep
			informe.fechaAtencion = fecha
			if (fecha < inicioVigencia || fecha > terminoVigencia)
				error = 4
			
			//Compruebo que la fecha no sea superior a la fecha actual
			if (fecha > new Date())
				error = 5
			
		}
		
		if(params?.altaMedica){
			informe.altaMedica= Boolean.valueOf(params?.altaMedica)
		}
		
		if(params.fechaProximoControl){
			//Comprueba que la fecha del próximo control sea posterior a la fecha de atención
			if (params.fechaAtencion <= params.fechaProximoControl){
				if (informe.altaMedica == false){
					informe.fechaProximoControl = params?.fechaProximoControl
				}
			}else{
				error = 1
			}
		}
		
		if(params?.reposoLaboral){
			if (informe.altaMedica == false){
				informe.reposoLaboral = Boolean.valueOf(params?.reposoLaboral)
			}
		}
		
		if(params?.comentarioAtencion){
			informe.comentarioAtencion=params?.comentarioAtencion
		}
		
		if(params?.medicoRun){
			def medico = PersonaNatural.findByRun(params?.medicoRun)
			
			if (!medico){
				medico = new PersonaNatural()
				medico.run = params?.medicoRun
				medico.nombre = params?.medicoNombre
				medico.apellidoPaterno = params?.medicoPaterno
				medico.apellidoMaterno = params?.medicoMaterno
				if (medico.validate())
					medico.save()
				else
					error = 3
			}
			informe.medico = medico
			informe.paciente = siniestro.trabajador
			informe.estado = true
		}
		
		//Si no hay diagnosticos, el flag error en 2
		if (diagnosticos.size()<1){
			error = 2
		}
			
		if (!informe.validate() || error != 0){
			next = [next: [action: 'dp03'], error: error, model: ['informeOpa': informe, 'fecha': fecha,'fechaProximoControl': params?.fechaProximoControl,'siniestroId': siniestro.id]]
		}else{
			//Guarda el diagnóstico
			informe.save(flush: true)
			next = [next: [action: 'index'], error: error]
		}
		
		return next
		
	}
	
	def informeEnEspera(params){
		log.info "Guardando Datos de informe OPA :: EN ESPERA ::"
		
		def informe = [:]
		def fechaAtencion  = new Date().clearTime()
		def fechaProximoControl  = new Date().clearTime()
		
		//Arreglo RUN
		params?.medicoRun = params.medicoRun ? ((String)params.medicoRun).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim() : params.medicoRun
		
		//Fecha de atención
		if (params?.fechaAtencion)
			fechaAtencion.set(year: params?.fechaAtencion_year.toInteger(), month: params?.fechaAtencion_month.toInteger()-1, date: params?.fechaAtencion_day.toInteger())
		if (params?.horaAtencion)
			fechaAtencion = FechaHoraHelper.horaToDate(params.horaAtencion, fechaAtencion)
			
		informe.fechaAtencion = fechaAtencion? fechaAtencion : null	
		
		//Alta Médica y reposos (boolean & radio)
		if (params?.altaMedica != null)
			informe.altaMedica =  Boolean.valueOf(params?.altaMedica)
			
		if (params?.reposoLaboral != null && informe.altaMedica == false)
			informe.reposoLaboral =  Boolean.valueOf(params?.reposoLaboral)
		
		//Fecha de atención
		if (params?.fechaProximoControl)
			fechaProximoControl.set(year: params?.fechaProximoControl_year?.toInteger(), month: params?.fechaProximoControl_month.toInteger()-1, date: params?.fechaProximoControl_day.toInteger())
	
		informe.fechaProximoControl = (fechaProximoControl && informe.altaMedica == false)? fechaProximoControl : null
		
		//Comentarios de atencion
		informe.comentarioAtencion = params?.comentarioAtencion
		
		def medico = [:]
			medico.run = params?.medicoRun? params?.medicoRun : null
			medico.nombre = params?.medicoNombre? params?.medicoNombre : null
			medico.apellidoPaterno = params?.medicoPaterno? params?.medicoPaterno : null
			medico.apellidoMaterno = params?.medicoMaterno? params?.medicoMaterno : null
			
		informe.medico = medico
		
		return informe
		
	}
			
	
	
		
}
	
	
