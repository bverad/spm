package cl.adexus.isl.spm

import java.util.Map;
import java.util.logging.Logger;

import cl.adexus.helpers.FormatosHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;


class FACTService {
	def JBPMService
	def uploadService
	def mailService
	
	def facturaIngresoProcessName = 'cl.isl.spm.cm.fact-ingreso'
	
	/**
	 * (dp01) JSON
	 */
	def getDatosPrestador (String rutPrestador) {
		def respuesta = [:]
		rutPrestador = rutPrestador == null ? "" : rutPrestador.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		// Veamos si con el rut tenemos una persona juridica
		def persona = PersonaJuridica.findByRut(rutPrestador)
		// Si nos fue mal, nos vamos
		if (persona == null) {
			def personaNatural = PersonaNatural.findByRun(rutPrestador)
			respuesta.razon_social = FormatosISLHelper.nombreCompletoStatic(personaNatural)
			def prestador    = Prestador.findByPersonaNatural(personaNatural)
			respuesta.direccion = prestador.direccion
			respuesta.telefono = prestador.telefono
			respuesta.email = prestador.email
			//return respuesta
		} else {
			respuesta.razon_social = persona.razonSocial
			def prestador    = Prestador.findByPersonaJuridica(persona)
			respuesta.direccion = prestador.direccion
			respuesta.telefono = prestador.telefono
			respuesta.email = prestador.email
		}
		return respuesta
	}


	/**
	 * (dp01) Boton Aceptar
	 *
	 * Armar y guardar un objeto cuenta medica.
	 * Ejectura r01
	 *
	 */
	def postDp01 (params) {
		// Revisemos que hayan puesto al menos una CM
		log.info("Ejecutando postDp01")
		log.info("Datos recibidos : $params")
		def cms = params.listaCM.split(":")
		if (cms[0] == '') {
			def r = rechazaFormulario(params, "cuentas", "Debe existir al menos una cuenta médica")
			return ['r' : r, 'cid' : null]
		}

		params.rutPrestador 	= params.rutPrestador == null ? "" : params.rutPrestador.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		def personaJuridica 	= PersonaJuridica.findByRut(params.rutPrestador)
		def prestadorObj		= Prestador.findByPersonaJuridica(personaJuridica)
		if (!prestadorObj) {
			def personaNatural 	= PersonaNatural.findByRun(params.rutPrestador)
			prestadorObj 			= Prestador.findByPersonaNatural(personaNatural)
		}
		params.prestador    	= prestadorObj

		// Ahora si, grabemos
		def factura = new Factura(params)

		// Si encontramos una factura con el mismo folio y prestador, se va para afuera
		def otraFactura = Factura.findByFolioAndPrestador(factura.folio, params.prestador)
		
		
		//verificando si existen cuentas medicas rechazadas
		log.info("Comprobando cuentas medicas rechazadas : ${cms}")
		def cmsList = []
		cms.each{cm->
			def values = cm.split("_")
			def result = values[0]
			cmsList << result
		}
		def cuentaMedicaList = CuentaMedica.findAllByEsAprobadaAndFolioCuentaInList(false, cmsList)
		log.info("CuentaMedicaList : $cuentaMedicaList")
		if(cuentaMedicaList){
			log.info("Las siguientes cuentas medicas han sido rechazadas : ${cuentaMedicaList.folioCuenta}")
			factura.errors.reject("prestador", "No se puede generar factura. Existen las siguientes cuentas medicas rechazadas ${cuentaMedicaList.folioCuenta} ")
			log.info("Redirigiendo a accion dp01")
			def p = ['rutPrestador': params.rutPrestador]
			return ([r : ['next' : [action: 'dp01'], 'model': ['factura': factura,'p': p, 'listaCM'	: params.listaCM],'fid': null]])
		}
		
		log.info("Comprobando existencia de la factura")
		if (otraFactura != null) {
			// Encontramos una factura con el mismo folio y prestador...
			// 1. status debe ser null y  2. fechaEnvioPago debe ser null
			if (otraFactura.facturaOrigen != null || otraFactura.status != null || otraFactura.fechaEnvioPago != null) {
				log.debug("La factura ${factura.folio} con prestador ${params.rutPrestador} ya está ingresada.")
				factura.errors.reject("prestador", "La factura ${factura.folio} con prestador ${params.rutPrestador} ya está ingresada")
				log.info("Redirigiendo a accion dp01")
				
				p = [ 'rutPrestador': params.rutPrestador]
				return ([r : ['next' : [action: 'dp01'],
							  'model': ['factura'	: factura,
										'p'			: p,
										'listaCM'	: params.listaCM
									   ],
							  'fid': null
							 ]
					   ])
			}
		}		
		
		if(!factura.validate()) {
			log.debug("Error en validacion de campos de factura")
			log.info("Redirigiendo a accion dp01")
			def p = [ 'rutPrestador': params.rutPrestador]
			return ([ r : ['next' : [action: 'dp01'],
						  'model': ['factura'	: factura,
									'p'			: p,
									'listaCM'	: params.listaCM
							       ]
						 ]
				   ])
		}
		
		log.info("Guardando factura")
		if (factura.save()) {
			log.info("Factura con folio ${factura?.folio} guardada con exito")
			def hayErroresEnDetalleFlag = false

			cms.each {
				log.info "Guardando detalle de factura ${factura.folio}"
				def folioCuenta	= it.split("_")[0]
				def valorCuenta = it.split("_")[1]

				// Debemos validar el folio con el prestador y asi obtener el iD
				def cuentaMedica = CuentaMedica.findByFolioCuenta(folioCuenta)
				if (!cuentaMedica) {
					log.info "La cuenta medica ${folioCuenta} no existe"
					def error = new ErrorFactura([fecha: new Date(), mensaje: "No existe la cuenta medica ${folioCuenta}", factura: factura])
					error.save(flush: true)
				} else if (!cuentaMedica.esAprobada) {
					log.info "La cuenta medica ${folioCuenta} no está aprobada"
					def error = new ErrorFactura([fecha: new Date(), mensaje: "La cuenta medica ${folioCuenta} no está aprobada", factura: factura])
					error.save(flush: true)
				} else {
					def centroSalud  = cuentaMedica?.centroSalud
					def prestador    = centroSalud?.prestador

					if (prestador != params.prestador) {
						// Si el prestador de esta cuenta medica no coincide con
						// el prestador entregado en la factura
						log.info "Prestador de cuenta medica ${folioCuenta} no coincide con prestador de factura"
						hayErroresEnDetalleFlag = true
						factura.errors.reject("prestador", "Prestador de cuenta medica ${folioCuenta} no coincide con prestador de factura")
					} else {
						// Debemos chequear además que esa cuenta médica no pertenezca a una factura enviada a pago
						def otrosDetalles = DetalleFactura.findAllByIdCuentaMedica(cuentaMedica.id);
						def facturaEnviadaAPagoFlag = false
						
						if (otrosDetalles.size() != 0) {
							otrosDetalles.each {
								if (it.factura.fechaEnvioPago != null) {
									log.info("Cuenta Medica id: ${cuentaMedica.id} figura enviada a pago en Factura ${factura.id}")
									facturaEnviadaAPagoFlag = true;
								}
							}		
						}
						
						if (facturaEnviadaAPagoFlag == true) {
								log.info "Error al validar el detalle de la Factura, la Cuenta Médica ya fue enviada a pago"
								hayErroresEnDetalleFlag = true
								factura.errors.reject("validacion", "Error al validar el detalle de la Factura, la Cuenta Médica ya fue enviada a pago")
						} else {
							def detFactura 	= new DetalleFactura([factura: factura, idCuentaMedica: cuentaMedica.id, valorCuentaMedica: valorCuenta])
							if (!detFactura.validate()) {
								log.info "Hay error al validar detalle de cuenta medica ${folioCuenta}"
								hayErroresEnDetalleFlag = true
								factura.errors.reject("validacion", "Hay error al validar detalle de cuenta medica ${folioCuenta}")
							}
							if (!detFactura.save()) {
								log.info "Hay error al guardar detalle de cuenta medica ${folioCuenta}"
							}						
						}
					}
				}
				log.info("Se ha guardado la Factura ${factura.id}")
			}
			log.info("hayErroresEnDetalleFlag: ${hayErroresEnDetalleFlag}")
			if (!hayErroresEnDetalleFlag) {
				log.info("factura id: ${factura.id}")
				return [ 'fid' : factura.id, folio: factura.folio ]
			} else {
				log.info("Existen errores en el detalle de las cuentas, redirigiendo a dp01")
				def p = [ 'rutPrestador': params.rutPrestador]
				return ([r : ['next' : [action: 'dp01'],
							  'model': ['factura'	: factura,
										'p'			: p,
										'listaCM'	: params.listaCM
									   ],
							  'fid': null
							 ]
					   ])
			}
		} else {
			log.info("Ocurrio un error al almacenar factura, redirigiendo a accion dp01.")
			def p = [ 'rutPrestador': params.rutPrestador]
			return ([r : ['next' : [action: 'dp01'],
						  'model': ['factura'	: factura,
									'p'			: p,
									'listaCM'	: params.listaCM
							       ],
						  'fid': null
						 ]
				   ])
		}
	}

	/**
	 * (dp01) Helper para rechazar el formulario por A, B o C motivo, recargandose.
	 */
	private def rechazaFormulario(params, typeError, errorMessage) {
		def factura = new Factura(params)
		factura.errors.reject(typeError, errorMessage)
		// Tenemos que hacer algunas cosas para permitir
		// a la pagina recuperar los datos
		def p = [ 'rutPrestador': params.rutPrestador]
		return (['next' : [action: 'dp01'],
					  'model': ['factura':  factura,
								'p':             p
								]
				])
	}

	def completeDp01(user,pass,fid){
		// Invocamos al BPM
		def bpmParams = ['facturaId': fid.toString(), user:user]
		def r = JBPMService.processComplete(user, pass, facturaIngresoProcessName, bpmParams)
		log.info(facturaIngresoProcessName+"::processComplete result:"+r)
		return r
	}

	def postDp02(params){
		// Guardar resolucion y los datos
		def factura = Factura.findById(params.facturaId);
		if (params.resolucion == 'Solicitar NC') {
			factura.status = 'ndc'
		} else if (params.resolucion == 'Solicitar Factura' ) {
			factura.status = 'fct'
		} else {
			// Rechazada
			factura.status = 'nok'
		}

		factura.save(flush : true)

		def solicitaAlgo = true;
		if(params.resolucion == 'Rechazada'){
			solicitaAlgo = false;
		}
		
		def ret = [facturaId: params.facturaId, solicitaAlgo: solicitaAlgo]
		return ret
	}
	
	def completeDp02(user,pass,taskId,data){
		// Invocamos al BPM
		def dataToBpm=[taskSolicitaNCND: data.solicitaAlgo.toString(), taskUser_:user]
		
		log.info("taskId:"+taskId)
		log.info("dataToBpm:"+dataToBpm)
		
		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'
	}

	/**
	 * (dp01 ingreso) JSON
	 * 
	 * Guarda los comentarios de factura que mande el usuario en el modal
	 * Sirve para la Nota de Credito y para la Factura
	 */
	def guardaComentarioFactura (String facturaId, String comentario) {
		def respuesta = [:]
		def factura = Factura.findById(facturaId);
		
		/*factura.comentarioIngreso = comentario;
		factura.save(flush: true)*/
		
		return respuesta
	}
	
	/**
	 * (dp01 ingreso) JSON
	 *
	 * Guarda los comentarios de factura que mande el usuario en el modal
	 * Sirve para la Nota de Credito y para la Factura
	 */
	def guardaDetallesComentariosFactura (String comentarios) {
		log.info("Ejecutando metodo guardaDetallesComentariosFactura ")
		log.info("Comentarios : $comentarios")
		def respuesta = true
		def cm
		def detalle
		
		if(!comentarios.equals("")){
			def comentariosList = comentarios.split(",");
			for (int i = 0; i < comentariosList.length; i = i + 2) {
				cm      = CuentaMedica.findByFolioCuenta(comentariosList[i])
				detalle = DetalleFactura.findByIdCuentaMedica(cm.id)
				detalle.comentarioIngreso = comentariosList[i + 1]
				detalle.save(flush: true)
			}
		}else{
			respuesta = false
		}

		
		
		return [respuesta:respuesta]
	}	
	
	def postDp03 (params) {
		log.info("Ejecutando metodo postDp03")
		log.info("Datos recibidos : $params")
		def documentoAprobado
		def rutPrestador = params?.rutPrestador.replace(".","").replace("-","")
		if (params.resolucion == 'Rechazada') {
			log.debug("Iniciando rechazo de factura")
			documentoAprobado = false
			// Guardar el _nok_ en la factura.
			def factura = Factura.findById(Long.parseLong(params.facturaId))
			factura.status = 'nok';
			factura.save(flush: true)
			log.debug("Rechazo de factura finalizado")
		} else {
			
			documentoAprobado = true
			if (params.tipoDocumento == 'Factura') {
				log.debug("Iniciando aprobacion de factura")
				log.debug("Rut prestador : ${rutPrestador}")
				def persona = PersonaJuridica.findByRut(rutPrestador)
				log.debug("Valor persona juridica : $persona")
				def prestador
				if (persona == null) {
					log.debug("No existe persona juridica, buscando persona natural")
					def personaNatural = PersonaNatural.findByRun(rutPrestador)
					log.debug("Valor persona natural : $personaNatural")
					prestador          = Prestador.findByPersonaNatural(personaNatural)
					log.debug("Valor prestador : $prestador")
				} else {
					prestador          = Prestador.findByPersonaJuridica(persona)
					log.debug("Valor de prestador en base a persona juridica : $prestador")
				}
				def facturaOrigen      = Factura.findById(Long.parseLong(params.facturaId))
				log.debug("Creando factura complementaria")
				def factura = new Factura([
					folio         : params.notaCreditoNumero,
					prestador     : prestador,
					facturaOrigen : facturaOrigen
				])
				
				if (factura.save(flush: true) ) {
					log.debug("Factura creada con exito, almacenando detalle")
					def detalles = DetalleFactura.findAllByFactura(facturaOrigen)
					def nuevoDetalle
					def valorDetalle
					detalles.each {
						valorDetalle = params['detalle-fct-ndc-' + it["id"]] == '' ?
										0 : params['detalle-fct-ndc-' + it["id"]]

						nuevoDetalle = new DetalleFactura([
							idCuentaMedica    : it.idCuentaMedica,
							valorCuentaMedica : valorDetalle,
							factura           : factura
						])
						
						nuevoDetalle.save(flush: true)
					}
					log.debug("Detalle de factura creado con exito")
				}
				log.debug("Aprobacion de factura concluida")
			} else if (params.tipoDocumento == 'Nota de Crédito') {
				log.debug("Procesando nota de credito")
				log.debug("RUT prestador : ${rutPrestador}")
				def persona = PersonaJuridica.findByRut(rutPrestador)
				log.debug("Valor persona juridica  : $persona")
				def prestador
				if (persona == null) {
					log.debug("No existe persona juridica, buscando rut como persona natural")
					def personaNatural = PersonaNatural.findByRun(rutPrestador)
					log.debug("Valor persona natural : $personaNatural")
					prestador          = Prestador.findByPersonaNatural(personaNatural)
					log.debug("Valor de prestador en base a persona natural : $prestador")	
				} else {
					prestador          = Prestador.findByPersonaJuridica(persona)
					log.debug("Valor de prestador en base a persona juridica : $prestador")
				}
				def facturaOrigen      = Factura.findById(Long.parseLong(params.facturaId))
				
				log.debug("Creando nota de credito")
				def ndc = new NotaCredito([
					folio         : params.notaCreditoNumero,
					prestador     : prestador,
					facturaOrigen : facturaOrigen
				])
				
				if (ndc.save(flush: true) ) {
					log.debug("Nota de credito almacenada con exito, asignando detalle")
					def detalles = DetalleFactura.findAllByFactura(facturaOrigen)
					def nuevoDetalle
					def valorDetalle
					detalles.each {
						valorDetalle = params['detalle-fct-ndc-' + it["id"]] == '' ?
										0 : params['detalle-fct-ndc-' + it["id"]]
									
						nuevoDetalle = new DetalleNotaCredito([
							idCuentaMedica    : it.idCuentaMedica,
							valorCuentaMedica : valorDetalle,
							notaCredito       : ndc
						])
						
						nuevoDetalle.save(flush: true)
					}
					log.debug("Detalle de nota de credito creado con exito")
				}
				log.debug("Procesamiento nota de credito concluido")
			}
		}

		def ret = [result: 'ok', facturaId: params.facturaId, aprobada: documentoAprobado]
		log.info("Finalizando ejecucion metodo postDp03")
		return ret
	}

	def completeDp03(user,pass,taskId,data){
		// Invocamos al BPM
		def dataToBpm=[taskCreditoAprobado: data.aprobada.toString(), taskUser_:user]
		
		log.info("taskId:"+taskId)
		log.info("dataToBpm:" + dataToBpm)
		
		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'
	}
	
	/**
	 * Solicitar Nota de Crédito o Factura
	 *
	 * @param params
	 * @return
	 */
	def enviarSolNotCred (def facturaId){
		log.info("Solicitar al prestador NC/ND)?)::"+facturaId)
		
		def factura = Factura.findById(Long.parseLong(facturaId.toString()))
		def prestador = factura.prestador
		def nombre = FormatosISLHelper.nombrePrestadorStatic(prestador)
		def detalles = factura.detalleFactura
		def comentario = factura.comentarioIngreso	
		String tabla = "<br><br><table border =\"1\"><tr><td></td><td>Cuenta Médica No</td><td>Facturado \$</td><td>" + 
			"Cuenta Médica \$     </td><td>Comentario ISL</td></tr>"
		int counter = 1
		long totFact = 0
		long totCM = 0
		for (def detalleFactura in detalles)
		{

			def cm = CuentaMedica.findById(detalleFactura.idCuentaMedica)
			totFact += detalleFactura.valorCuentaMedica
			totCM += cm.valorCuentaAprobado
			tabla = tabla +"<tr><td>"+ counter + "</td><td>" + cm.folioCuenta.toString() + "</td><td>" +
				detalleFactura.valorCuentaMedica.toString() + "</td><td>" + cm.valorCuentaAprobado.toString() + 
				"</td><td>" + FormatosHelper.blankStatic(detalleFactura.comentarioIngreso) + "</td></tr>"
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
		tabla = tabla + "<br><br>" + objSol + " a Emitir: \$" + difPos
		mailService.sendMail {
			to prestador.email.toString()
		  from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
		  subject "Solicitud de emisión de nueva " + objSol + " para regularizar factura incorrecta"
			html "Estimado " + nombre.toString() + ":<br><br>Lamentamos informarle que su factura con número " + factura.folio.toString() +
				" y fecha de revisión " + FormatosHelper.fechaCortaStatic(new Date()) + " ha sido objetada " + //Issue #584 por " + comentario +
				", por lo que solicitamos generar $objSol de acuerdo al siguiente detalle: " + tabla +
				"<br><br>por lo que se pide regularizar la situación lo antes posible emitiendo una " + objSol + " adicional, " + 
				"puesto que después de 30 días la factura se rechazará automáticamente.<br><br>Atte.,<br><br>Instituto de Seguridad Laboral"
		  }
		
		return [success: true]
	}

}
