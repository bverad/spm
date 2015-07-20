package cl.adexus.isl.spm

import grails.converters.JSON
import org.apache.shiro.SecurityUtils
import cl.adexus.isl.spm.helpers.FormatosISLHelper

class FACT_ingresoController {

	def CMService
	def FACTService
	def pass='1234'

	def index () { redirect(action: 'dp01') }

	/**
	 * Ingreso Datos Factura
	 */

	def dp01 () {
		log.info("Ejecutando accion dp01")
		if (params.get("model")) {
			log.info("Seteando valor de model por :" +  params.get("model"))
			// Si venimos de una redireccion por error
			def model = params.get("model")
		}
	}

	/**
	 * (dp01) Obtiene los datos del prestador a partir de su rut
	 */
	def datosPrestadorJSON () {
		def rutPrestador = params.rutPrestador
		def datos = FACTService.getDatosPrestador(rutPrestador)
		JSON.use("deep"){ render datos as JSON }
	}


	/**
	 * (dp01) Instancia un nuevo proceso BPM
	 *        Envia los datos para completar la primera tarea
	 */
	def postDp01 () {
		log.info("Ejecutando postDp01")
		log.debug("Datos recibidos : $params")
		def user = SecurityUtils.subject?.principal
		//limpiando mensajes
		flash.default = null
		
		//Guarda la Factura
		def response = FACTService.postDp01(params)
		if (response.fid) {
			log.info("Id de factura generado completando tarea en bpm")
			def rBpm = FACTService.completeDp01(user, pass, response.fid)
			log.info("Tarea finalizada")
			log.info("Generando mensajeria : La Factura N° ${response.folio} asociada al prestador ${params?.rutPrestador} ha sido enviada a pago exitosamente")
			// Lo mandamos al INBOX
			flash.message = "cl.adexus.isl.spm.fact.pago.sucess"
			flash.args = []
			flash.default = "La Factura N° ${response.folio} asociada al prestador ${params?.rutPrestador} ha sido enviada a pago exitosamente"
			log.info("Redirigiendo a accion dp01")
			render(view: 'dp01', model: params)
			
		} else {
			// Repite el llenado en dp01
			log.info("Id de factura incorrecto")
			log.debug("Seteando variable model en params con el siguiente valor : " + response.r.get('next').action)
			params.put('model', response.r.get('model'))
			forward (action: response.r.get('next').action, params: params)
		}
	}


	/**
	 * Decide Solicitar Nota Credito Factura
	 */
	def dp02 () {
		log.info("Ejecutando accion dp02")
		log.info("dp02_params:" + params)

		def facturaId = params.facturaId
		def taskId    = params.taskId
		def cms       = []

		if (facturaId != null) {
			def factura  		= Factura.findById(facturaId)
			def runPrestador	= FormatosISLHelper.rutPrestadorStatic(factura.prestador)
			def nombrePrestador	= FormatosISLHelper.nombrePrestadorStatic(factura.prestador)

			def detalles = DetalleFactura.findAllByFactura(factura)

			def totalFactura         = 0
			def sumaCMS              = 0
			def diferenciaSuma
			def diferenciaPorcentaje

			detalles.each {
				// ¿Cuanto facturamos por la cuenta medica de este id?
				def dcm = CuentaMedica.findById(it.idCuentaMedica)
				log.info("CM Aprobado:"+dcm?.valorCuentaAprobado)
				log.info("FACT Detalle CM:"+it.valorCuentaMedica)
				cms.add(['id'                  : dcm.folioCuenta,
					'facturadoPesos'      : it.valorCuentaMedica,
					'valorCuentaAprobado' : dcm?.valorCuentaAprobado,
					'diferencia'          : dcm?.valorCuentaAprobado - it.valorCuentaMedica
				])

				totalFactura = totalFactura + it.valorCuentaMedica
				sumaCMS      = sumaCMS      + dcm?.valorCuentaAprobado
			}

			diferenciaSuma       = sumaCMS - totalFactura
			diferenciaPorcentaje = ( diferenciaSuma / sumaCMS ) * 100

			def agregados = [
				totalFactura         : totalFactura,
				sumaCMS              : sumaCMS,
				diferenciaSuma       : diferenciaSuma,
				diferenciaPorcentaje : diferenciaPorcentaje
			]


			def model = [taskId        : taskId,
				factura       : factura,
				runPrestador  : runPrestador,
				nombrePrestador: nombrePrestador,
				cms           : cms,
				agregados     : agregados]

			if (params.get("model")) {
				// Si venimos de una redireccion por error
				model += params.get("model")
			}
			render(view: 'dp02', model: model)
		} else {
			// Rendereamos en blanco. Aunque yo tiraria un error o una redireccion
			render(view: 'dp02')
		}
		log.info("Ejecucion de accion dp02 concluida")
	}

	/**
	 * Botones siguiente de dp02
	 *
	 */
	def postDp02(){
		log.info("Ejecutando accion postDp02")
		log.info("Datos recibidos : $params")

		def user = SecurityUtils.subject?.principal

		//Guarda la Revision
		def data      = FACTService.postDp02(params);
		def facturaId = params.facturaId
		def taskId    = params.taskId
		//Si no es un rechazo manda el correo
		if (data.solicitaAlgo) {
			FACTService.enviarSolNotCred(data.facturaId)
		}

		def r = FACTService.completeDp02(user, pass, taskId, data);
		if (r == 'OK') {
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])
		} else {
			// Va a dp02, pero con error
			// Repite el llenado en dp02
			params.put('model', response.r.get('model'))
			forward (action: response.r.get('next').action, params: params)
		}
		log.info("Ejecucion de accion postDp02 concluida")
		
	}

	/*
	 * AUXILIAR
	 *          Guarda comentario hecho a una factura
	 */
	def guardaComentarioFacturaJSON () {
		def facturaId  = params.facturaId
		def comentario = params.comentario
		def r = FACTService.guardaComentarioFactura(facturaId, comentario)
		JSON.use("deep"){ render r as JSON }
	}

	/*
	 * AUXILIAR
	 *          Guarda comentario hecho a una factura
	 */
	def guardaDetallesComentariosFacturaJSON () {
		log.info("Ejecutando accion guardaDetallesComentariosFacturaJSON")
		log.info("Datos recibidos : $params")
		def comentarios = params.comentarios
		def r = FACTService.guardaDetallesComentariosFactura(comentarios)
		JSON.use("deep"){ render r as JSON }
	}
	
	/**
	 * Retorna si la cuenta medica ingresada existe
	 * @return
	 */
	def existeCuentaMedicaJSON(){
		log.info("Ejecutando metodo existeCuentaMedicaJSON")
		log.info("Datos recibidos : $params")
		def result = [:]
		def existCuentaMedica = false
		def cuentaMedica = CuentaMedica.findByFolioCuenta(params?.id)
		
		log.info("Verificando existencia de cuenta medica ${params?.id}")
		if(cuentaMedica){
			log.debug("Cuenta medica existe : $cuentaMedica")
			existCuentaMedica = true
		}else
			log.debug("Cuenta medica ${params?.id} no existe")

		result = [existCuentaMedica : existCuentaMedica]
		log.info("Resultado : $result")
		
		JSON.use("deep"){ render result as JSON }
	}




	/**
	 * Revisar nota de credito
	 */
	def dp03 () {
		log.info("Ejecutando accion dp03")
		log.info("Datos recibidos : $params")

		def facturaId = params.facturaId
		def taskId    = params.taskId
		def cms       = []

		if (facturaId != null) {
			def factura  = Factura.findById(facturaId)
			def detalles = DetalleFactura.findAllByFactura(factura)

			def tipoDocumento = factura.status == 'ndc' ? 'Nota de Crédito' : 'Factura'

			def totalFactura         = 0
			def sumaCMS              = 0
			def diferenciaSuma
			def diferenciaPorcentaje

			detalles.each {
				// ¿Cuanto facturamos por la cuenta medica de este id?
				def dcm = CuentaMedica.findById(it.idCuentaMedica)

				cms.add(['folioCuenta'       : dcm?.folioCuenta,
					'facturadoPesos'    : it.valorCuentaMedica,
					'cuentaMedicaPesos' : dcm?.valorCuentaAprobado,
					'idDetalleFactura'  : it.id
				])

				totalFactura = totalFactura + it.valorCuentaMedica
				sumaCMS      = sumaCMS      + dcm?.valorCuentaAprobado
			}

			diferenciaSuma       = sumaCMS - totalFactura
			diferenciaPorcentaje = ( diferenciaSuma / sumaCMS ) * 100

			def agregados = [
				totalFactura         : totalFactura,
				sumaCMS              : sumaCMS,
				diferenciaSuma       : diferenciaSuma,
				diferenciaPorcentaje : diferenciaPorcentaje
			]

			def model = [taskId          : taskId,
				factura         : factura,
				cms             : cms,
				agregados       : agregados,
				tipoDocumento   : tipoDocumento,
				nombrePrestador : FormatosISLHelper.nombrePrestadorStatic(factura?.prestador),
				rutPrestador 	 : FormatosISLHelper.rutPrestadorStatic(factura?.prestador)]

			if (params.get("model")) {
				// Si venimos de una redireccion por error
				model += params.get("model")
			}
			render(view: 'dp03', model: model)
		} else {
			// Rendereamos en blanco. Aunque yo tiraria un error o una redireccion
			render(view: 'dp03')
		}
		log.info("Ejecutando de accion dp03 concluida")
	}

	/**
	 * Aceptar Nota de Credito (o Factura)
	 */
	def cu03a () {
		params['resolucion'] = 'Aceptada'
		forward action: "postDp03", params: params
	}

	/**
	 * Rechazar Nota de Credito (o Factura)
	 */
	def cu03r () {
		params['resolucion'] = 'Rechazada'
		forward action: "postDp03", params: params
	}

	def postDp03(){
		log.info("Ejecutando accion postDp03")
		log.info("Datos recibidos : $params")

		def user = SecurityUtils.subject?.principal

		// Guarda la NC
		def data      = FACTService.postDp03(params);
		def facturaId = params.facturaId
		def taskId    = params.taskId

		if (data.result != 'ok') {
			// Va a dp03, pero con error
			// Repite el llenado en dp3

			// (TODO) Traer todos los datos a la vista de error.

			return redirect ([action: 'dp03', params: [facturaId: facturaId, taskId: taskId]])
		}

		def r = FACTService.completeDp03(user, pass, taskId, data);
		if (r == 'OK') {
			// Lo mandamos al INBOX
			redirect ([controller: 'nav', action: 'inbox'])
		} else {

		}
		
		log.info("Finalizando accion postDp03")
	}

}
