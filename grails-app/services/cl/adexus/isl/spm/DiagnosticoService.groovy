package cl.adexus.isl.spm

import java.util.Date;

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;
import cl.adexus.helpers.FormatosHelper

class DiagnosticoService {

	def JBPMService
	def uploadService
	def SiniestroService

	//def calificacionOrigenATProcessName = 'cl.isl.spm.calor.calor-calificacion'
	//def calificacionOrigenEPProcessName = 'cl.isl.spm.calor.calor-calificacion-ep'

	def addDiagnostico(params){
		log.info 'PARAMETROS ADD DIAGNOSTICOS::'+params

		def siniestro = Siniestro.findById(params?.siniestroId)
		def diagnostico = new Diagnostico()
		def error = 0
		def next

		diagnostico.siniestro = siniestro

		if (params?.esLaboral && params?.esLaboral == "1")
			diagnostico.esLaboral = true
		else
			diagnostico.esLaboral = false

		if (params?.diagnostico){
			diagnostico.diagnostico = params?.diagnostico
		}
		if (params?.parte){
			diagnostico.parte = CodigoUbicacionLesion.findByCodigo(params?.parte)
		}
		if (params?.lateralidad){
			diagnostico.lateralidad = TipoLateralidad.findByCodigo(params?.lateralidad)
		}
		if (params?.origenDiagnostico){
			diagnostico.origen = OrigenDiagnostico.findByCodigo(params?.origenDiagnostico)
		}
		if (params?.cie10){
			diagnostico.cie10 = CIE10.findByCodigo((params?.cie10).toUpperCase())
		}
		if(params?.fechaDiagnostico){
			if (params.backToController != 'calOrigenEP'){
				if (params?.fechaDiagnostico >= siniestro.fecha.clearTime()){
					diagnostico.fechaDiagnostico = params?.fechaDiagnostico
				}else{
					diagnostico.fechaDiagnostico = params?.fechaDiagnostico
					error = 1
				}
			}else
				diagnostico.fechaDiagnostico = params?.fechaDiagnostico
		}

		if (!diagnostico.validate() || error > 0){
			if (params?.backToController == 'calOrigenEP' || params?.backToController == 'informeOPA'){
				next = [next: [action: 'dp04'], error: error, model: [siniestroId: params?.siniestroId, taskId: params?.taskId, backTo: params?.backTo, backToController: params?.backToController, diagnosticoId: params?.diagnosticoId, diagnostico: diagnostico, volverSeguimiento: params?.volverSeguimiento]]
			} else {
				next = [next: [action: 'dp05'], error: error, model: [siniestroId: params?.siniestroId, taskId: params?.taskId, backTo: params?.backTo, backToController: params?.backToController, diagnosticoId: params?.diagnosticoId, diagnostico: diagnostico, volverSeguimiento: params?.volverSeguimiento]]
			}
		} else {
			//guarda el diagnostico
			if (params?.volverSeguimiento) {
				next = ['next': [controller: 'seguimiento', action: 'dp04'], model: ['siniestroId': params?.siniestroId]]
				diagnostico.desdeSeguimiento = true
			} else {
				next = [next: [action: params.backTo, controller: params.backToController], error: error, model: [siniestroId: params?.siniestroId, taskId: params?.taskId, diagnostico: diagnostico]]
			}
			diagnostico.save(flush: true)
		}
		return next
	}

	//Guarda diagnosticos NP TODO: falta confirmar el guardado final
	def saveDiagnosticoNP(params){
		log.info 'PARAMETROS ADD DIAGNOSTICOS::'+params

		def siniestro = Siniestro.findById(params?.siniestroId)
		def diagnostico = new Diagnostico()
		def diagnosticoNP = [:]
		def next

		diagnostico.siniestro = siniestro

		if (params?.esLaboral && params?.esLaboral == "1"){
			diagnostico.esLaboral = true
			diagnosticoNP?.esLaboral = true
		}else{
			diagnostico.esLaboral = false
			diagnosticoNP?.esLaboral = false
		}

		if (params?.diagnostico){
			diagnostico.diagnostico = params?.diagnostico
			diagnosticoNP.diagnostico = params?.diagnostico
		}

		if (params?.parte){
			diagnostico.parte = CodigoUbicacionLesion.findByCodigo(params?.parte)
			def parte = ['codigo':diagnostico?.parte?.codigo, 'descripcion':diagnostico?.parte?.descripcion]
			diagnosticoNP.parte = parte
		}

		if (params?.lateralidad){
			diagnostico.lateralidad = TipoLateralidad.findByCodigo(params?.lateralidad)
			def lateralidad = ['codigo':diagnostico?.lateralidad?.codigo, 'descripcion':diagnostico?.lateralidad?.descripcion]
			diagnosticoNP.lateralidad = lateralidad
		}

		if (params?.origen){
			diagnostico.origen = OrigenDiagnostico.findByCodigo(params?.origen)
			def origen = ['codigo':diagnostico?.origen?.codigo, 'descripcion':diagnostico?.origen?.descripcion]
			diagnosticoNP.origen = origen
		}

		if (params?.cie10){
			diagnostico.cie10 = CIE10.findByCodigo((params?.cie10).toUpperCase())
			def cie10 = ['codigo':diagnostico?.cie10?.codigo, 'descripcion':diagnostico?.cie10?.descripcion]
			diagnosticoNP.cie10 = cie10
		}

		if(params?.fechaDiagnostico){
			diagnostico.fechaDiagnostico = params?.fechaDiagnostico
			diagnosticoNP.fechaDiagnostico = diagnostico?.fechaDiagnostico
		}

		if (!diagnostico.validate()){
			def model = [siniestroId: params?.siniestroId, backTo: params?.backTo, backToController: params?.backToController, diagnosticoId: params?.diagnosticoId, diagnostico: diagnostico]
			next = [next: [action: 'dp04'], 'model': model]
		}else{
			//Envia el diagnostico
			def model = [siniestroId: params?.siniestroId, backTo: params?.backTo, backToController: params?.backToController, diagnostico: diagnosticoNP]
			next = [next: [action: 'dp03'], 'model': model]
		}

		return next
	}

	//Update de diagnostico super choro
	def updateDiagnostico(params){
		log.info("Ejecutando metodo updateDiagnostico")
		log.info("Datos recibidos : $params")

		def siniestro = Siniestro.findById(params?.siniestroId)
		def diagnostico = Diagnostico.findById(params?.diagnosticoId)
		def error = 0
		def next

		if (params?.esLaboral && params?.esLaboral == "1")
			diagnostico.esLaboral = true
		else
			diagnostico.esLaboral = false
		if (diagnostico.origen.codigo != '2'){
			if (params?.diagnostico){
				diagnostico.diagnostico = params?.diagnostico
			}
			if (params?.parte){
				diagnostico.parte = CodigoUbicacionLesion.findByCodigo(params?.parte)
			}
			if (params?.lateralidad){
				diagnostico.lateralidad = TipoLateralidad.findByCodigo(params?.lateralidad)
			}
			if (params?.origen){
				diagnostico.origen = OrigenDiagnostico.findByCodigo(params?.origenDiagnostico)
			}
			if (params?.cie10){
				diagnostico.cie10 = CIE10.findByCodigo(params?.cie10)
			}
			if(params?.fechaDiagnostico){
				if (params.backToController != 'calOrigenEP'){
					if (params?.fechaDiagnostico >= siniestro.fecha.clearTime()){
						diagnostico.fechaDiagnostico = params?.fechaDiagnostico
					}else{
						diagnostico.fechaDiagnostico = params?.fechaDiagnostico
						error = 1
					}
				}else
					diagnostico.fechaDiagnostico = params?.fechaDiagnostico
			}
		}
		if (!diagnostico.validate() || error > 0){
			if (params?.backToController == 'calOrigenEP' || params?.backToController == 'informeOPA'){
				next = [next: [action: 'dp05'], error: error, model: [siniestroId: params?.siniestroId, taskId: params?.taskId, backTo: params?.backTo, backToController: params?.backToController, diagnosticoId: params?.diagnosticoId, diagnostico: diagnostico, volverSeguimiento: params?.volverSeguimiento, recaOrigen:params?.recaOrigen]]
			} else {
				log.info("redireccionando en el caso que corresponda a una enfermedad profesional")
				next = [next: [action: 'dp06'], error: error, model: [siniestroId: params?.siniestroId, taskId: params?.taskId, backTo: params?.backTo, backToController: params?.backToController, diagnosticoId: params?.diagnosticoId, diagnostico: diagnostico, volverSeguimiento: params?.volverSeguimiento, recaOrigen:params?.recaOrigen]]
			}
		} else {
			if (params?.volverSeguimiento) {
				next = ['next': [controller: 'seguimiento', action: 'dp04'], model: ['siniestroId': params?.siniestroId, recaOrigen:params?.recaOrigen]]
				diagnostico.desdeSeguimiento = true
			} else {
				next = [next: [action: params.backTo, controller: params.backToController], error: error, model: [siniestroId: params?.siniestroId, taskId: params?.taskId, diagnostico: diagnostico, recaOrigen:params?.recaOrigen]]
			}
			//guarda el diagnostico
			diagnostico.save(flush: true)
		}
		return next
	}

	//Update de diagnostico NP TODO: completar, esta enviando objetos
	def updateDiagnosticoNP(params){

		log.info("Actualizar diagnosticos NP para informe OPA");

		def siniestro = Siniestro.findById(params?.siniestroId)
		def diagnostico = new Diagnostico()
		def diagnosticoNP = [:]
		def error = 0
		def next

		diagnostico.siniestro = siniestro

		if (params?.esLaboral && params?.esLaboral == "1"){
			diagnostico.esLaboral = true
			diagnosticoNP?.esLaboral = true
		}else{
			diagnostico.esLaboral = false
			diagnosticoNP?.esLaboral = false
		}

		if (params?.diagnostico){
			diagnostico.diagnostico = params?.diagnostico
			diagnosticoNP.diagnostico = params?.diagnostico
		}

		if (params?.parte){
			diagnostico.parte = CodigoUbicacionLesion.findByCodigo(params?.parte)
			def parte = ['codigo':diagnostico?.parte?.codigo, 'descripcion':diagnostico?.parte?.descripcion]
			diagnosticoNP.parte = parte
		}

		if (params?.lateralidad){
			diagnostico.lateralidad = TipoLateralidad.findByCodigo(params?.lateralidad)
			def lateralidad = ['codigo':diagnostico?.lateralidad?.codigo, 'descripcion':diagnostico?.lateralidad?.descripcion]
			diagnosticoNP.lateralidad = lateralidad
		}

		if (params?.origen){
			diagnostico.origen = OrigenDiagnostico.findByCodigo(params?.origen)
			def origen = ['codigo':diagnostico?.origen?.codigo, 'descripcion':diagnostico?.origen?.descripcion]
			diagnosticoNP.origen = origen
		}

		if (params?.cie10){
			diagnostico.cie10 = CIE10.findByCodigo(params?.cie10)
			def cie10 = ['codigo':diagnostico?.cie10?.codigo, 'descripcion':diagnostico?.cie10?.descripcion]
			diagnosticoNP.cie10 = cie10
		}

		if(params?.fechaDiagnostico){
			diagnostico.fechaDiagnostico = params?.fechaDiagnostico
			diagnosticoNP.fechaDiagnostico = diagnostico?.fechaDiagnostico
		}

		if (!diagnostico.validate()){
			def model = [siniestroId: params?.siniestroId, backTo: params?.backTo, backToController: params?.backToController, numeroDiagnostico: params?.numeroDiagnostico, diagnostico: diagnostico]
			next = [next: [action: 'dp05'], 'model': model]
		}else{
			//Envia el diagnostico
			def model = [siniestroId: params?.siniestroId, backTo: params?.backTo, backToController: params?.backToController, updateDiagnostico: params?.numeroDiagnostico, diagnostico: diagnosticoNP]
			next = [next: [action: 'dp03'], 'model': model]
		}

		return next
	}

	def guardaDiagnosticosNP(def diagnosticoNP, def siniestroId){
		log.info "Guardando diagnostico NP ->"+diagnosticoNP

		def diagnostico = new Diagnostico()
		diagnostico.siniestro = Siniestro.findById(siniestroId)

		diagnostico.esLaboral = diagnosticoNP.esLaboral
		diagnostico.diagnostico = diagnosticoNP.diagnostico
		diagnostico.parte = CodigoUbicacionLesion.findByCodigo(diagnosticoNP.parte.codigo)
		diagnostico.lateralidad = TipoLateralidad.findByCodigo(diagnosticoNP.lateralidad.codigo)
		diagnostico.origen = OrigenDiagnostico.findByCodigo(diagnosticoNP.origen.codigo)
		diagnostico.cie10 = CIE10.findByCodigo((diagnosticoNP.cie10.codigo).toUpperCase())
		diagnostico.fechaDiagnostico = diagnosticoNP.fechaDiagnostico

		if (diagnostico.save(flush: true)){
			log.info "Guardado OK"
		}else{
			log.info "Guardado ERROR"
		}
	}

	def deleteDiagnostico(params){

		def diagnostico = Diagnostico.findById(params?.diagnosticoId)
		def siniestro = Siniestro.findById(params?.siniestroId)
		def next

		try {
			diagnostico.delete(flush:true)
			//next = ['next': [action: params.backTo, params: ['siniestroId': params?.siniestroId, 'taskId': params?.taskId]],  model: ['siniestro': siniestro]]
			next = [next: [action: params.backTo, model: [siniestroId: params?.siniestroId, taskId: params?.taskId]]]
		} catch (org.h2.jdbc.JdbcSQLException e) {

		}
		return next

	}

	def goBack(params){
		log.info "PARAMETROS VOLVER" + params
		def model = [siniestroId:params.siniestroId, taskId: params.taskId]
		//Mandamos el informe OPA si existe
		/*def controller = params?.backToController
		 if(params?.volverSeguimiento && !params?.volverSeguimiento.equals("")){
		 log.info("Setea volver a seguimiento")
		 params?.backTo = "dp04"
		 controller = "seguimiento"
		 }
		 if(params?.recaOrigen)
		 controller = params?.recaOrigen
		 params?.backTo = "dp01"
		 if (params?.informeOPA){
		 model+=[informeOpa:params?.informeOPA]
		 }*/


		//si existe un origen valido redirecciona segun corresponda
		def action = params?.backTo
		def controller = params?.backToController
		if(params?.origen && !params?.origen.equals("")){
			controller = "seguimiento"
			log.info "Campo origen viene con valores seteando controller : $controller"
			switch(params.origen){
				case "dp03":
					log.info "case dp03"
					action = "dp04"
					break
				case "dp11":
					action = "dp04"
					break
				case "0" :
					log.info("Seteando reca origen")
					controller = params?.recaOrigen
					if(params?.volverAntecedentes && !params?.volverAntecedentes.equals("")){
						controller = "solAnteAdic"
						action = "dp01"
					}
					break
				case "1" :
					log.info("Seteando reca origen")
					controller = params?.recaOrigen
					if(params?.volverAntecedentes && !params?.volverAntecedentes.equals("")){
						controller = "solAnteAdic"
						action = "dp01"
					}
					break
			}
		}else{
			log.info("go back desde reca")
			controller = params?.recaOrigen
			if(params?.volverAntecedentes && !params?.volverAntecedentes.equals("")){
				controller = "solAnteAdic"
				action = "dp01"
			}
		}

		if (params?.informeOPA){
			model+=[informeOpa:params?.informeOPA]
		}

		log.info "redirigiendo a : controller : $controller, action : $action"
		return ([next: [controller:controller, action: action], model: model])
	}

}
