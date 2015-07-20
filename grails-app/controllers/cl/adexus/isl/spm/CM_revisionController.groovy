package cl.adexus.isl.spm

import grails.converters.JSON
import java.util.Date;
import org.apache.shiro.SecurityUtils;

class CM_revisionController {

	def CMService
	def pass='1234'
	
    def index() { redirect(action: 'dp01') }
	
	/*********************************************************
	 * Analisis Arancel y Pertenencia Medica (dp01)
	 * 
	 *********************************************************/	
	def dp01 (){
		log.info 'entro al dp01'
		log.debug("Datos recibidos : $params")
		def cuentaMedicaId = params.idCuentaMedica
		def taskId=params.taskId
		
		if (cuentaMedicaId != null) {
			def cuentaMedica    = CuentaMedica.findById(cuentaMedicaId)
			def trabajador      = cuentaMedica.trabajador
			
			def centroSalud     = cuentaMedica.centroSalud
			def prestador       = centroSalud.prestador
			
			def detallesCuenta  = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)

			def sumValorTotal				= 0
			def sumValorTotalPactado		= 0
			detallesCuenta.each {
				// Agregar valores finales
				def valorUnitarioPactado	= it.valorUnitarioPactado ? it.valorUnitarioPactado : 0
				
				it.valorUnitarioFinal		= it.valorUnitario > valorUnitarioPactado  ? valorUnitarioPactado : it.valorUnitario //Issue #908
				it.recargoUnitarioFinal		= it.recargoUnitarioPactado ? it.recargoUnitarioPactado : it.recargoUnitario
				
				sumValorTotal				+= it.valorTotal
				sumValorTotalPactado		+= valorUnitarioPactado

				it.cantidadFinal			= it.cantidad
				it.descuentoFinal			= it.descuentoUnitario ? it.descuentoUnitario : 0
				it.recargoUnitarioFinal		= it.recargoUnitarioPactado ? it.recargoUnitarioPactado : 0
				
				it.valorTotalFinal			= it.cantidadFinal * (it.valorUnitarioFinal - it.descuentoFinal + it.recargoUnitarioFinal)
			}
			def cm = [	'sumValorTotal': sumValorTotal,
						'sumValorTotalPactado': sumValorTotalPactado]
			
			// Necesitamos los warnings de los detalles de cuenta medica
			def warnings = [:]
			detallesCuenta.each {
				def errores = ErrorDetalleCuentaMedica.findAllByDetalleCuentaMedica(it)
				def mensajes = []
				errores.each {
					mensajes.push(it.mensaje)
				}
				warnings[it.id] = mensajes
			}
			
			def historialCuentas = CMService.obtenerHistorialCuentasMedicas(trabajador)
			def model = [cuentaMedica:    cuentaMedica,
                         trabajador:      trabajador,
                         centroSalud:     centroSalud,
                         prestador:       prestador,
                         detallesCuenta:  detallesCuenta,
						 cm:              cm,
						 taskId:		  taskId,
						 warnings:        warnings,
						 historialCuentas:historialCuentas
						 ]
			
			if (params.get("model")) {
				// Si venimos de una redireccion por error
				model += params.get("model")
			}
			
			render(view: 'dp01', model: model)			
		} else {
			// Rendereamos en blanco. Aunque yo tiraria un error o una redireccion
			render(view: 'dp01')
		}
	}

	/**
	 * Este es llamado por el modal del dp01 para guardar la consulta medica o de convenio
	 */	
	def guardaConsultaJSON () {		
		def r = CMService.guardaConsulta(params)		
		def respuesta = ["status" : "OK"]
		JSON.use("deep"){ render respuesta as JSON }
	}

	/**
	 * Este lo usamos por si queremos editar un detalle de consulta ya hecho.
	 */
	def obtenConsultaJSON () {
		def datos = CMService.obtenDatosConsulta(params)
		JSON.use("deep"){ render datos as JSON }
	}	
	
	/**
	 * Boton Siguiente de dp01
	 * 
	 */
	def postDp01 () {
		log.info("Ejecutando postDp01")
		log.debug("Datos recibidos : $params")
		def user = SecurityUtils.subject?.principal
		
		// Guarda la Revision
		def data           = CMService.revisionPostDp01(params)
		
		def cuentaMedicaId = params.cuentaMedicaId
		def taskId         = params.taskId
		def r              = CMService.completeRevisionDp01(user, pass, taskId, data)
		if (r == 'OK') {
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])
		} else {
			// Va a dp02, pero con error
			// Repite el llenado en dp01
			params.put('model', response.r.get('model'))
			forward (action: response.r.get('next').action, params: params)
		}
	}

	/*********************************************************
	 * Responde Solicitud Medica (dp02)
	 *
	 *********************************************************/
	def dp02 (){
		log.info 'entro al dp02'
		def cuentaMedicaId = params.idCuentaMedica
		def taskId=params.taskId
		
		if (cuentaMedicaId != null) {
			def cuentaMedica    = CuentaMedica.findById(cuentaMedicaId)
			def trabajador      = cuentaMedica.trabajador
			
			def centroSalud     = cuentaMedica.centroSalud
			def prestador       = centroSalud.prestador
			
			def detallesCuenta  = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)

			def sumValorTotal				= 0
			def sumValorTotalPactado		= 0
			detallesCuenta.each {
				// Agregar valores finales
				def valorTotalPactado		= it.valorTotalPactado ? it.valorTotalPactado : 0
				
				sumValorTotal				+= it.valorTotal
				sumValorTotalPactado		+= valorTotalPactado
				
				
			}
			def cm = [	'sumValorTotal': sumValorTotal,
						'sumValorTotalPactado': sumValorTotalPactado]
			
			// Necesitamos los warnings de los detalles de cuenta medica
			def warnings = [:]
			detallesCuenta.each {
				def errores = ErrorDetalleCuentaMedica.findAllByDetalleCuentaMedica(it)
				def mensajes = []
				errores.each {
					mensajes.push(it.mensaje)
				}
				warnings[it.id] = mensajes
			}
			
			def historialCuentas = CMService.obtenerHistorialCuentasMedicas(trabajador)
			def model = [cuentaMedica:    cuentaMedica,
						 trabajador:      trabajador,
						 centroSalud:     centroSalud,
						 prestador:       prestador,
						 detallesCuenta:  detallesCuenta,
						 cm:              cm,
						 taskId:		  taskId,
						 warnings:        warnings,
						 historialCuentas:historialCuentas
						 ]
			
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
	 * Este es llamado por el modal del dp02 (y dp03) para guardar la respuesta a
	 * la consulta medica o de convenio
	 */
	def guardaRespuestaJSON () {
		def r = CMService.guardaConsulta(params)
		def respuesta = ["status" : "OK"]
		JSON.use("deep"){ render respuesta as JSON }
	}
	
	/**
	 * Boton Siguiente del dp02
	 */
	def postDp02 () {
		def user = SecurityUtils.subject?.principal
		
		def data = CMService.revisionPostDp02(params)
		
		if (data.status == "OK") {
			def cuentaMedicaId = params.cuentaMedicaId
			def taskId         = params.taskId
			def r              = CMService.completeRevisionDp02(user, pass, taskId)			
		}
		
		redirect ([controller: 'nav', action: 'inbox'])
	}

	/*********************************************************
	 * Responde Convenios (dp03)
	 *
	 *********************************************************/
	def dp03 (){
		log.info 'entro al dp03'
		def cuentaMedicaId = params.idCuentaMedica
		def taskId=params.taskId
		
		if (cuentaMedicaId != null) {
			def cuentaMedica    = CuentaMedica.findById(cuentaMedicaId)
			def trabajador      = cuentaMedica.trabajador
			
			def centroSalud     = cuentaMedica.centroSalud
			def prestador       = centroSalud.prestador
			
			def detallesCuenta  = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)

			def sumValorTotal				= 0
			def sumValorTotalPactado		= 0
			detallesCuenta.each {
				// Agregar valores finales
				def valorTotalPactado		= it.valorTotalPactado ? it.valorTotalPactado : 0
				
				sumValorTotal				+= it.valorTotal
				sumValorTotalPactado		+= valorTotalPactado
			}
			def cm = [	'sumValorTotal': sumValorTotal,
						'sumValorTotalPactado': sumValorTotalPactado]
			
			// Necesitamos los warnings de los detalles de cuenta medica
			def warnings = [:]
			detallesCuenta.each {
				def errores = ErrorDetalleCuentaMedica.findAllByDetalleCuentaMedica(it)
				def mensajes = []
				errores.each {
					mensajes.push(it.mensaje)
				}
				warnings[it.id] = mensajes
			}
			
			def historialCuentas = CMService.obtenerHistorialCuentasMedicas(trabajador)
			def model = [cuentaMedica:    cuentaMedica,
						 trabajador:      trabajador,
						 centroSalud:     centroSalud,
						 prestador:       prestador,
						 detallesCuenta:  detallesCuenta,
						 cm:              cm,
						 taskId:		  taskId,
						 warnings:        warnings,
						 historialCuentas:historialCuentas
						 ]
			
			if (params.get("model")) {
				// Si venimos de una redireccion por error
				params += flash.get("model")
			}
			
			render(view: 'dp03', model: model)
		} else {
			// Rendereamos en blanco. Aunque yo tiraria un error o una redireccion
			render(view: 'dp03')
		}
	}	

	/**
	 * Boton Siguiente del dp03
	 */
	def postDp03 () {
		def user = SecurityUtils.subject?.principal
		
		def data = CMService.revisionPostDp03(params)

		if (data.status == "OK") {
			def cuentaMedicaId = params.cuentaMedicaId
			def taskId         = params.taskId
			def r              = CMService.completeRevisionDp03(user, pass, taskId)			
		}

		redirect ([controller: 'nav', action: 'inbox'])
	}
	
	/**********************************************************************************
	 * Definir Detalle Final Cuenta Medica
	 * 
	 *********************************************************************************/	
	def dp04 () {
		log.info 'entro al dp04'
		def cuentaMedicaId = params.idCuentaMedica
		def taskId=params.taskId
		
		if (cuentaMedicaId != null) {
			def cuentaMedica    = CuentaMedica.findById(cuentaMedicaId)
			def trabajador      = cuentaMedica.trabajador
			
			def centroSalud     = cuentaMedica.centroSalud
			def prestador       = centroSalud.prestador
			
			def detallesCuenta  = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)

			def sumValorTotal				= 0
			def sumValorTotalPactado		= 0
			def sumValorTotalFinal          = 0
			detallesCuenta.each {
				// Agregar valores finales
				def valorTotalPactado		= it.valorTotalPactado ? it.valorTotalPactado : 0				
				def valorUnitarioFinal		= it.valorUnitarioFinal ? it.valorUnitarioFinal : 0
				def descuentoFinal			= it.descuentoFinal ? it.descuentoFinal : 0
				def recargoUnitarioFinal	= it.recargoUnitarioFinal ? it.recargoUnitarioFinal : 0
				def valorTotalFinal			= it.valorTotalFinal ? it.valorTotalFinal : 0
				
				sumValorTotal				+= it.valorTotal
				sumValorTotalPactado		+= valorTotalPactado
			}
			def cm = [ 'sumValorTotal'		  : sumValorTotal,
					   'sumValorTotalPactado' : sumValorTotalPactado]
			
			// Necesitamos los warnings de los detalles de cuenta medica
			def warnings = [:]
			detallesCuenta.each {
				def errores = ErrorDetalleCuentaMedica.findAllByDetalleCuentaMedica(it)
				def mensajes = []
				errores.each {
					mensajes.push(it.mensaje)
				}
				warnings[it.id] = mensajes
			}
			
			def historialCuentas = CMService.obtenerHistorialCuentasMedicas(trabajador)
			
			def model = [cuentaMedica:    cuentaMedica,
						 trabajador:      trabajador,
						 centroSalud:     centroSalud,
						 prestador:       prestador,
						 detallesCuenta:  detallesCuenta,
						 cm:              cm,
						 taskId:		  taskId,
						 warnings:        warnings,
						 historialCuentas:historialCuentas]
			
			if (params.get("model")) {
				// Si venimos de una redireccion por error
				model += params.get("model")
			}
			
			render(view: 'dp04', model: model)
		} else {
			// Rendereamos en blanco. Aunque yo tiraria un error o una redireccion
			render(view: 'dp04')
		}
	}
	
	/*****************************************************************
	 * Botones siguiente de dp04
	 * 
	 *****************************************************************/
	def cu04a () {
		params['resolucion']='Aprobada'
		forward action: "postDp04", params: params
	}
	def cu04am () {
		params['resolucion']='Aprobada Con Modificaciones'
		forward action: "postDp04", params: params
	}
	def cu04r () {
		params['resolucion']='Rechazada'
		forward action: "postDp04", params: params
	}
	
	def postDp04(){
		def user = SecurityUtils.subject?.principal
		params.esAprobada = true
		
		log.info("R04:"+params)
		//Guarda la Revision
		def data = CMService.revisionPostDp04(params);
		def cuentaMedicaId=params.cuentaMedicaId
		def taskId=params.taskId
		def r = CMService.completeRevisionDp04(user, pass, taskId, data);
		if (r == 'OK') {
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])
		} else {
			// Va a dp04, pero con error
			// Repite el llenado en dp04
			params.put('model', response.r.get('model'))
			forward (action: response.r.get('next').action, params: params)
		}		
	}
	
	def rechazoDp04() {
		log.info "Se rechazo la cuenta medica"
		def user = SecurityUtils.subject?.principal
		params.esAprobada = false
		log.info("R04:"+params)
		//Guarda la Revision
		def data = CMService.revisionPostDp04(params);
		def cuentaMedicaId=params.cuentaMedicaId
		def taskId=params.taskId
		def r = CMService.completeRevisionDp04(user, pass, taskId, data);
		if (r == 'OK') {
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])
		} else {
			// Va a dp04, pero con error
			// Repite el llenado en dp04
			params.put('model', response.r.get('model'))
			forward (action: response.r.get('next').action, params: params)
		}

	}
}
