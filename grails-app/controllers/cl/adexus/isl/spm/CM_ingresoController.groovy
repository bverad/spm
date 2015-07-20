package cl.adexus.isl.spm

import grails.converters.JSON
import org.apache.shiro.SecurityUtils

class CM_ingresoController {

	def CMService
	def pass='1234'
	
	def index() {
		redirect(action: 'dp01')
	}

	/**
	 * Ingreso Datos Encabezado Cuenta Medica
	 */
	def dp01() {
		log.info("pasando por dp01")
		// Si llegamos por primera vez a la pagina
		def tipoCuenta = TipoCuentaMedica.list()
		def formatoOrigen = FormatoOrigen.list()

		def model = [tipoCuenta:    tipoCuenta,
				     formatoOrigen: formatoOrigen]
		if(params.model){
			model + params.model
		}else{
			model
		}
	}

	/**
	 * (dp01) Obtiene los datos del prestador a partir de su rut
	 */
	def datosPrestadorJSON () {
		def rutPrestador = params.rutPrestador
		def datos = CMService.getDatosPrestador(rutPrestador)
		JSON.use("deep"){ render datos as JSON }
	}
	
	/**
	 * (dp01) Obtiene los datos del trabajador a partir de su run
	 */
	def datosTrabajadorJSON () {
		def runTrabajador = params.runTrabajador
		def datos = CMService.getDatosTrabajador(runTrabajador)
		JSON.use("deep") { render datos as JSON }
	}
	
	/**
	 * (dp02) Obtiene los datos de siniestro
	 */
	def datosSiniestroJSON () {
		def idSiniestro = params.idSiniestro
		def datos = SiniestroService.cu02(idSiniestro)
		JSON.use("deep") { render datos as JSON	}
		
	}
	
	/**
	 * (dp01) Instancia un nuevo proceso BPM
	 *        Envia los datos para completar la primera tarea 
	 */
	def postDp01 () {
		log.info("pasando por postDp01")
		def user = SecurityUtils.subject?.principal
		
		//Guarda la CM
		def response = CMService.postDp01(params)
		if (response.cid) {
			// Valida la CM
			def rBpm = CMService.completeDp01(user, pass, response.cid)
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])			
		} else {
			// Repite el llenado en dp01
			params.put('model', response.r.get('model'))
			forward(action: response.r.get('next').action, params: params)
		}
	}

	/**
	 * Formulario Ingreso cuenta en papel
	 */
	def dp02 () {
		def cuentaMedicaId = params.cuentaMedicaId
		def taskId=params.taskId
		log.info("dp02_params:"+params)
		
		if (cuentaMedicaId != null) {
			def cuentaMedica    = CuentaMedica.findById(cuentaMedicaId)			
			def trabajador      = cuentaMedica.trabajador
			def centroSalud     = cuentaMedica.centroSalud
			def prestador       = centroSalud.prestador

			def detallesCuenta  = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)
			def sumaValores     = 0
			detallesCuenta.each {
				sumaValores += it.valorTotal
			}
			def cm              = ['sumaValores': sumaValores]			
			
			def model = [taskId:		  taskId,
						 cuentaMedica:    cuentaMedica,
                         trabajador:      trabajador,
                         centroSalud:     centroSalud,
                         prestador:       prestador,
                         detallesCuenta:  detallesCuenta,
						 cm:              cm]
			
			if (params.get("model")) {
				// Si venimos de una redireccion por error
				model += params.get("model")
			}
			render(view: 'dp02', model: model)			
		} else {
			// Rendereamos en blanco. Aunque yo tiraria un error o una redireccion
			render(view: 'dp02')
		}
	}
	
	/**
	 * Boton Agregar de dp02
	 * Agrega un detalle a la cuenta medica X
	 */
	def agregaDetalleCuentaMedica () {
		def r = CMService.agregaDetalleCuentaMedica(params);
		def cuentaMedicaId = params.cuentaMedicaId
		def taskId = params.taskId
		if (r == 'OK') {
			// Redirigimos a dp02
			// Por ahora meteremos el folio_cuenta como parametro
			redirect(action: 'dp02', params: [cuentaMedicaId : cuentaMedicaId, taskId: taskId])
		} else {
			// Va a dp02, pero con error
			params.put('model', r.get('model'))
			params.put('cuentaMedicaId', cuentaMedicaId)
			params.put('taskId', taskId)
			forward(action: 'dp02', params: params)
		}
	}
	
	/**
	 * Link elimina detalle de cuenta medica
	 */
	def eliminaDetalleCuentaMedica () {
		def cuentaMedicaId  = params.id.split("_")[0]
		def detalleCuentaId = params.id.split("_")[1]
		def taskId = params.id.split("_")[2]
		
		def r = CMService.eliminaDetalleCuentaMedica(detalleCuentaId)
		
		if (r == "OK") {
			flash.mensajes = "El detalle se ha borrado exitosamente"
		} else {
			flash.mensajes = "Hubo un error tratando de borrar el detalle"
		}
		redirect (action: 'dp02', params: [cuentaMedicaId : cuentaMedicaId, taskId: taskId])
	}	

	/**
	 * (dp02) Boton Siguiente
	 */
	def postDp02 () {
		log.info("Ejecutando action postDp02 ")
		log.info("Datos recibidos $params")
		def user = SecurityUtils.subject?.principal
		def r = CMService.postDp02(user, pass, params.taskId, params.cuentaMedicaId)
		
		if (r != 'OK') {
			// Devolvemos el error
			flash.mensajes = "La Cuenta Médica debe llevar por lo menos un detalle"
			redirect (action: 'dp02', params: [cuentaMedicaId : params.cuentaMedicaId, taskId: params.taskId])
		} else {		
			// Lo mandamos al INBOX
			log.info("Redirigiendo a bandeja de entrada")
			redirect ([controller: 'nav', action: 'inbox'])
		}
	}
	
	/**
	 * Formulario carga archivo detalle cuenta
	 */
	def dp03 () {
		def cuentaMedicaId = params.cuentaMedicaId
		if (cuentaMedicaId != null) {
			def cuentaMedica    = CuentaMedica.findById(cuentaMedicaId)
			def trabajador      = cuentaMedica.trabajador
			def centroSalud     = cuentaMedica.centroSalud
			def prestador       = centroSalud.prestador

			def model=[	cuentaMedica:    cuentaMedica,
						trabajador:      trabajador,
						centroSalud:     centroSalud,
						prestador:       prestador]
			
			log.info 'model b: ' + params.get('model')
			if(params.get('model')){
				model + params.get('model')
			}else{
				model
			}
		}
	}

	/**
	 * Validacion formato del archivo [dp03] y guardamos la cuenta
	 * Pasamos a revision
	 */
	def postDp03 () {
		def user = SecurityUtils.subject?.principal
		// Obtenemos el archivo y lo guardamos
		def file = request.getFile('detalleCuentaFile')
		def r = CMService.postDp03(user, pass, params, file)
		
		def next;
		if(!r.status){
			params.put('model', ['cuentaMedica': r.model.cuentaMedica])
			params.put('cuentaMedicaId', params.cuentaMedicaId);
			next=[action: 'dp03', params: params];
		}else{
			next=[controller: 'nav', action: 'inbox']
		}
		forward(next)
	}
}
