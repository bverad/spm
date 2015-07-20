
package cl.adexus.isl.spm

import java.util.logging.Logger;

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper;
import cl.adexus.helpers.FormatosHelper

class CMService {
	def JBPMService
	def uploadService
	def SiniestroService

	def cuentaMedicaIngresoProcessName = 'cl.isl.spm.cm.ingreso'

	/**
	 * (dp01) JSON
	 */
	def getDatosPrestador (String rutPrestador) {
		log.info("Ejecutando getDatosPrestador")
		log.debug("Datos recibidos $rutPrestador")
		rutPrestador = rutPrestador == null ? "" : rutPrestador.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		def respuesta = [:]
		def centrosSalud

		// Veamos si con el rut tenemos una persona juridica
		def persona = PersonaJuridica.findByRut(rutPrestador)
		// Si nos fue mal, nos vamos
		if (persona == null){
			def personaNatural = PersonaNatural.findByRun(rutPrestador)
			respuesta.razon_social = FormatosISLHelper.nombreCompletoStatic(personaNatural)
			def prestador    = Prestador.findByPersonaNatural(personaNatural)
			centrosSalud = CentroSalud.findAllByPrestador(prestador)
		}else{
			respuesta.razon_social = persona.razonSocial
			def prestador    = Prestador.findByPersonaJuridica(persona)
			centrosSalud = CentroSalud.findAllByPrestador(prestador)
			// Deberiamos tener centros de salud para esta persona juridica
		}

		respuesta.centrosSalud = []
		centrosSalud.each () {
			respuesta.centrosSalud.add(["codigo" : it.id, "descripcion" : it.nombre])
		}

		return respuesta
	}

	/**
	 * (dp01) JSON
	 */
	def getDatosTrabajador (String runTrabajador) {
		log.info("Ejecutando getDatosTrabajador")
		log.debug("Datos recibidos $runTrabajador")
		def respuesta = [:]
		runTrabajador = runTrabajador == null ? "" : runTrabajador.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		def trabajador = PersonaNatural.findByRun(runTrabajador)
		respuesta.nombre = FormatosISLHelper.nombreCompletoStatic(trabajador)
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
		log.info("Ejecutando postDp01")
		log.debug("Datos recibidos :  $params")
		log.debug("rutPrestador : ${params.rutPrestador}")
		params.esAprobada = false
		params.centroSalud   = CentroSalud.findById(params.centroSalud)
		params.tipoCuenta    = TipoCuentaMedica.findByCodigo(params.tipoCuenta)
		params.formatoOrigen = FormatoOrigen.findByCodigo(params.formatoOrigen)
		params.runTrabajador = params.runTrabajador == null ? "" : params.runTrabajador.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		params.rutPrestador = params.rutPrestador == null ? "" : params.rutPrestador.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		params.trabajador    = PersonaNatural.findByRun(params.runTrabajador)
		def listaDocumentos = params?.listaOXA.split(":")
		log.debug("Valor listaDocumentos : $listaDocumentos")
		// Revisemos que hayan puesto al menos una OXA
		if(listaDocumentos ==  []){
			def r = rechazaFormulario(params, "OxA", "Debe introducir al menos una OPA/ODA/OPAEP")
			return ['r' : r, 'cid' : null]
		}

		// Debe existir el prestador
		if (params.nombrePrestador == '') {
			def r = rechazaFormulario(params, "nombrePrestador", "Debe existir prestador para esta cuenta medica")
			return ['r' : r, 'cid' : null]
		}
		// Debe existir el trabajador
		if (params.nombreTrabajador == '') {
			def r = rechazaFormulario(params, "nombreTrabajador", "Debe existir trabajador para esta cuenta medica")
			return ['r' : r, 'cid' : null]
		}
		if(params.fechaDesde>params.fechaHasta){
			def r = rechazaFormulario(params, "fechaDesde", "La fecha desde debe ser menor a la fecha hasta")
			return ['r' : r, 'cid' : null]
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date manyanaAPrimeraHora= cal.getTime();

		log.info("manyanaAPrimeraHora:"+manyanaAPrimeraHora);
		log.info("params.fechaHasta:"+params.fechaHasta);
		log.info("params.fechaEmision:"+params.fechaEmision);

		if(params.fechaHasta>=manyanaAPrimeraHora){
			def r = rechazaFormulario(params, "fechaHasta", "La fecha hasta debe ser menor a la fecha de hoy")
			return ['r' : r, 'cid' : null]
		}


		if(params.fechaEmision>=manyanaAPrimeraHora){
			def r = rechazaFormulario(params, "fechaEmision", "La fecha emision debe ser menor a la fecha de hoy")
			return ['r' : r, 'cid' : null]
		}


		log.info("Validando llave trabajador, prestador y documento (opa y oda)")
		def results = []
		listaDocumentos.each{documento->
			def array = documento.split("_")
			def tipoDocumento = array[0]
			def idDocumento = array[1]
			if(tipoDocumento.equals("OPA")){
				def opa = OPA.get(idDocumento)
				results = validaExistenciaYllaveDocumento(opa, tipoDocumento, params, results)
			}else if(tipoDocumento.equals("OPAEP")){
				def opaep = OPAEP.get(idDocumento)
				results = validaExistenciaYllaveDocumento(opaep, tipoDocumento, params, results)
			}else{
				def oda = ODA.get(idDocumento)
				results = validaExistenciaYllaveDocumento(oda, tipoDocumento, params, results)
			}
		}

		//itera sobre los resultados segun los documentos ingresados
		if(results != []){
			def mensaje = ""
			results.each{result->
				if(!result.existencia){
					mensaje += "La ${result.descripcionDocumento} indicada no existe. "
				}		
				if(!result.llave){
					mensaje += "La llave empleador, trabajador y ${result.descripcionDocumento} no corresponde. "
				}
			}
			def r = rechazaFormulario(params, "documento", mensaje)
			return ['r' : r, 'cid' : null]
		}

		// Validar que no exista una cuenta medica ya aprobada con el folio y prestador indicado indicado
		def cuentaMedica = CuentaMedica.findByFolioCuentaAndCentroSalud(params.folioCuenta, params.centroSalud)
		if (cuentaMedica) {
			def r = rechazaFormulario(params, "folioCuenta", "Ya existe una cuenta medica ingresada para el folio [${params.folioCuenta}]")
			return ['r' : r, 'cid' : null]
		}

		// Ahora si, grabemos (con cuatica, para que le BPM la pueda ver)
		cuentaMedica = new CuentaMedica(params)
		// Se setea hora a 23:59 para validar con prestaciones
		cal.setTime(cuentaMedica.fechaHasta)
		cal.set(Calendar.HOUR_OF_DAY, 23)
		cal.set(Calendar.MINUTE, 59)
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cuentaMedica.fechaHasta = cal.getTime()
		log.info "cuentaMedica: ${cuentaMedica.fechaHasta}"

		if(!cuentaMedica.validate()) {
			def p = [ 'rutPrestador':    params.rutPrestador]
			def t = [ 'runTrabajador':   params.runTrabajador]
			return ([r : ['next' : [action: 'dp01'],
					'model': ['cuentaMedica':  cuentaMedica,
						'p':             p,
						't':             t]
				]
			])
		} else {
			listaDocumentos.each() {
				def tipoOXA = it.split("_")[0]
				def id      = it.split("_")[1]
				log.info 'Procesando ' + tipoOXA + ', id: ' + id
				def oxa, msj
				switch (tipoOXA) {
					case "OPA":
							cuentaMedica.addToOpas(Integer.parseInt(id)); break;
					case "OPAEP":
							cuentaMedica.addToOpaeps(Integer.parseInt(id)); break;
					case "ODA":
							cuentaMedica.addToOdas(Integer.parseInt(id)); break;
				}
			}
			if (cuentaMedica.save()) {
				log.info("cuentaMedica saved. id:" + cuentaMedica.id)
				return ['r' : null, 'cid' : cuentaMedica.id]
			} else {
				log.error("cuentaMedica not saved.")
				return ['r' : null, 'cid' : null]
			}
		}
	}

	/*
	 * Valida si el documento ingresado (opa, opaep, oda) existe y que la combinacion de prestador, trabajador y documento corresponden
	 * @params <b>documento : </b> corresponde al documento en contexto
	 * @params <b>descripcionDocumento : </b> descripcion del documento en contexto
	 * @author bvera
	 * @since 1.0.3
	 * 
	 * */
	private def validaExistenciaYllaveDocumento(documento, descripcionDocumento, params, results){
		def llave = true
		def existencia = true
		def personaJuridica = PersonaJuridica.findByRut(params?.rutPrestador)
		def trabajador = PersonaNatural.findByRun(params?.runTrabajador)
		def prestador = Prestador.findByPersonaJuridica(personaJuridica)
		if(documento){
			if(!documento.centroAtencion.prestador.equals(prestador) || !documento.siniestro.trabajador.equals(trabajador)){
				llave = false
			}
		}else{
			existencia = false
		}
		//retorna resultado
		if(llave == false || existencia == false)
			results << [llave:llave, existencia:existencia, descripcionDocumento: descripcionDocumento]
		return results
	}

	/**
	 * (dp01) Helper para rechazar el formulario por A, B o C motivo, recargandose.
	 */
	private def rechazaFormulario(params, typeError, errorMessage) {
		log.info("Ejecutando rechazaFormulario")
		log.debug("Datos recibidos $params")
		log.debug("typeError : $typeError")
		log.debug("errorMessage : $errorMessage")
		def cuentaMedica = new CuentaMedica(params)
		cuentaMedica.errors.reject(typeError, errorMessage)
		// Tenemos que hacer algunas cosas para permitir
		// a la pagina recuperar los datos
		def p = [ 'rutPrestador':    params.rutPrestador, 'listaOXA': params.listaOXA]
		def t = [ 'runTrabajador':   params.runTrabajador]

		return (['next': [action: 'dp01'], model: ['cuentaMedica':  cuentaMedica,
				'p':             p,
				't':				t]])
	}

	def completeDp01(user,pass,cid){
		log.info("Ejecutando completeDp01")
		log.debug("user : $user")
		log.debug("pass : $pass")
		log.debug("cuenta medica id : $cid")
		// Invocamos al BPM
		def bpmParams = ['cuentaMedicaId': cid.toString()]
		def r = JBPMService.processComplete(user, pass, cuentaMedicaIngresoProcessName, bpmParams)
		log.info(cuentaMedicaIngresoProcessName+"::processComplete result:"+r)
		return r
	}

	/**
	 * (dp01) Helper para obtener el nombre de una OPA / OPAEP / ODA
	 */
	private def getOxaName(oxa) {
		log.info("Ejecutando getOxaName")
		log.debug("oxa : ${oxa.id}")
		if (oxa instanceof OPA)
			return 'OPA N° ' + oxa.id
		else if (oxa instanceof OPAEP)
			return 'OPAEP N° ' + oxa.id
		else if (oxa instanceof ODA)
			return 'ODA N° ' + oxa.id
	}

	/**
	 * (dp02) Agrega un detalle de cuenta medica
	 */
	def agregaDetalleCuentaMedica (params) {
		log.info("Ejecutando agregaDetalleCuentaMedica")
		log.debug("datos recibidos : $params")
		params.cuentaMedica = CuentaMedica.findById(params.cuentaMedicaId)
		def detalleCM = new DetalleCuentaMedica(params)

		if (!params.fecha) {
			detalleCM.errors.rejectValue("fecha", "Falta fecha")
			return ([model: ['detalleCM' : detalleCM]])
		}

		// Tratar de meter la hora en la fecha
		try {
			detalleCM.fecha = FechaHoraHelper.horaToDate(params.hora, params.fecha)
		} catch (Exception e) {
			detalleCM.errors.rejectValue("fecha", "hora.mala", e.toString())
			return ([model: ['detalleCM' : detalleCM]])
		}

		// Verificar que la fecha sea menor a la fecha/hora de "ahora ya".
		if (detalleCM.fecha > new Date()) {
			detalleCM.errors.rejectValue("fecha", "Fecha / hora mayor a la actual")
			return ([model: ['detalleCM' : detalleCM]])
		}

		// Debe haber uno de estos dos: codigo o glosa
		if (params.codigo  == "" && params.glosa == "") {
			detalleCM.errors.rejectValue("codigo", "Debe estar definido o bien código, o bien glosa")
			return ([model: ['detalleCM' : detalleCM]])
		}

		// Si alguno de estos no existe, devuelve error
		if (!params.valorUnitario) {
			detalleCM.errors.rejectValue("valorUnitario", "No hay valor unitario")
			return ([model: ['detalleCM' : detalleCM]])
		}

		if (!params.cantidad) {
			detalleCM.errors.rejectValue("cantidad", "Falta cantidad")
			return ([model: ['detalleCM' : detalleCM]])
		}

		if (!params.valorTotal) {
			detalleCM.errors.rejectValue("valorTotal", "No hay valor total")
			return ([model: ['detalleCM' : detalleCM]])
		}

		if (!detalleCM.validate()){
			return ([model: ['detalleCM' : detalleCM]])
		} else {
			detalleCM.save()
			return "OK"
		}
	}

	/**
	 * (dp02) Elimina un detalle de cuenta medica
	 */
	def eliminaDetalleCuentaMedica (id) {
		log.info("Ejecutando eliminaDetalleCuentaMedica")
		log.debug("id : $id")
		def detalleCuentaMedica = DetalleCuentaMedica.findById(id);
		detalleCuentaMedica.delete(flush: true)
		return 'OK'
	}

	/**
	 * (dp02) Boton Aceptar
	 */
	def postDp02 (user, pass, taskId, cuentaMedicaId) {
		log.info("Ejecutando postDp02")
		log.debug("user:" + user)
		log.debug("pass:" + pass)
		log.debug("taskId:" + taskId)
		log.debug("cuentaMedicaId:" + cuentaMedicaId)

		// Debemos revisar que haya al menos una cuenta medica introducida
		def cuentaMedica = CuentaMedica.findById(cuentaMedicaId)
		def detalles     = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)

		if (detalles.size == 0) {
			log.info "Cuenta Medica ${cuentaMedicaId} no lleva detalle"
			return "ERROR"
		} else {
			log.info("Ejecutando taskComplete")
			def r = JBPMService.taskComplete(user, pass, taskId, [:])
			return "OK"
		}
	}

	/**
	 * 
	 */
	def postDp03(user, pass, params, org.springframework.web.multipart.commons.CommonsMultipartFile file) {
		log.info("Ejecutando postDp03")
		log.debug("Datos recibidos : $params")
		log.debug("user : $user")
		log.debug("pass : $pass")
		def cuentaMedica = CuentaMedica.findById(params.cuentaMedicaId)
		// Identificar si viene el archivo DOH!
		if (!file) {
			cuentaMedica.errors.reject('cl.adexus.isl.spm.CuentaMedica.detalleCuentaFile.nullable')
			return [status: false, model: [cuentaMedica: cuentaMedica]]
		}
		// Subir archivo
		def result = uploadService.doUploadCSV(file, cuentaMedica?.trabajador?.run)
		def archivoSeleccionado
		if (result.status == 0) {
			archivoSeleccionado = result.mensaje
		} else {
			cuentaMedica.errors.reject(result.mensaje)
			return [status: false, model: [cuentaMedica: cuentaMedica]]
		}
		// Guardar identificador del archivo
		cuentaMedica.idArchivo = archivoSeleccionado
		cuentaMedica.save()
		// Enviar al BPM
		def bpmParams = ['cuentaMedicaId': cuentaMedica.id.toString()]
		def r = JBPMService.taskComplete(user, pass, params.taskId, bpmParams)
		return [status: true, model: [cuentaMedica: cuentaMedica]]
	}

	/***********************************************************************
	 * REVISION
	 ***********************************************************************/

	/**
	 * DP01
	 */
	def revisionPostDp01(params){
		log.info("Ejecutando revisionPostDp")
		log.debug("Datos recibidos : $params")
		// TODO: Guardar el analisis y las consultas medicas y de convenio
		def cuentaMedica = CuentaMedica.findById(params.cuentaMedicaId)
		def detalles     = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)
		// Definir true si es que hay alguna consulta medica
		def hayConsultaMedica=false
		// Definir true si es que hay alguna consulta convenio
		def hayConsultaConvenio=false

		def cantidadFinal
		def descuentoFinal

		detalles.each {
			if (it.consultaMedica != null)   hayConsultaMedica = true
			if (it.consultaConvenio != null) hayConsultaConvenio = true

			cantidadFinal  = params['cantidadFinal_' + it.id].replaceAll(/\./, "").replaceAll(/\,/, "")
			descuentoFinal = params['descuentoFinal_' + it.id].replaceAll(/\./, "").replaceAll(/\,/, "")

			if (cantidadFinal == "")  cantidadFinal  = "0"
			if (descuentoFinal == "") descuentoFinal = "0"

			it.cantidadFinal         = Long.parseLong(cantidadFinal)

			def valorUnitarioPactado	= it.valorUnitarioPactado ? it.valorUnitarioPactado : 0
			it.valorUnitarioFinal    = it.valorUnitario > valorUnitarioPactado  ? valorUnitarioPactado : it.valorUnitario //Issue #908

			it.descuentoFinal        = Long.parseLong(descuentoFinal)
			it.recargoUnitarioFinal  = it.recargoUnitarioPactado == null ? 0 : it.recargoUnitarioPactado
			it.valorTotalFinal       = it.cantidadFinal * (it.valorUnitarioFinal - it.descuentoFinal + it.recargoUnitarioFinal);

			it.save()
		}

		def ret=[cid: params.cuentaMedicaId, hayConsultaMedica: hayConsultaMedica, hayConsultaConvenio: hayConsultaConvenio]
		log.debug("ret : $ret")
		return ret
	}

	def completeRevisionDp01(user,pass,taskId,data){
		log.info("Ejecutando completeRevisionDp01")
		log.debug("user : $user")
		log.debug("pass : $pass")
		log.debug("taskId : $taskId")
		log.debug("data : $data")
		// Invocamos al BPM
		def dataToBpm=[taskAprobadaSolicitudMedica: (!data.hayConsultaMedica).toString(),
			taskAprobadaSolicitudConvenio: (!data.hayConsultaConvenio).toString()]

		log.info("taskId:"+taskId)
		log.info("dataToBpm:"+dataToBpm)

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		return 'OK'
	}

	/**
	 * DP02
	 */
	def revisionPostDp02 (params) {
		log.info("Ejecutando revisionPostDp02")
		log.debug("Datos recibidos : $params")
		def cuentaMedica = CuentaMedica.findById(params.cuentaMedicaId)
		def detalles     = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)

		def hayConsultaSinResponder = false

		detalles.each {
			if ( it.consultaMedica != null &&
			(it.respuestaMedicaSugiereAprobar == null &&
			it.respuestaMedicaModificarCantidad == null ) ) hayConsultaSinResponder = true
			it.save()
		}

		def status = (hayConsultaSinResponder == true)? "FAIL" : "OK";

		return [ "status" : status ]
	}

	def completeRevisionDp02 (user, pass, taskId) {
		log.info("Ejecutando completeRevisionDp01")
		log.debug("user : $user")
		log.debug("pass : $pass")
		log.debug("taskId:  $taskId")
		def r = JBPMService.taskComplete(user, pass, taskId, [:])
		return "OK"
	}

	/**
	 * DP03
	 */

	def revisionPostDp03 (params) {
		log.info("Ejecutando revisionPostDp03")
		log.debug("Datos recibidos : $params")
		def cuentaMedica = CuentaMedica.findById(params.cuentaMedicaId)
		def detalles     = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)

		def hayConsultaSinResponder = false

		detalles.each {
			if ( it.consultaConvenio != null &&
			(it.respuestaConvenioSugiereAprobar == null &&
			it.respuestaConvenioModificarMonto == null ) ) hayConsultaSinResponder = true

			it.save()
		}

		def status = (hayConsultaSinResponder == true)? "FAIL" : "OK";

		return [ "status" : status ]
	}

	def completeRevisionDp03 (user, pass, taskId) {
		log.info("Ejecutando completeRevisionDp03")
		log.debug("user : $user")
		log.debug("pass : $pass")
		log.debug("taskId: $taskId")
		def r = JBPMService.taskComplete(user, pass, taskId, [:])
		return 'OK'
	}



	/**
	 * DP04
	 */
	def revisionPostDp04(params){
		log.info("Ejecutando revisionPostDp04")
		log.debug("params : $params")
		def valorCuentaAprobado	= 0;
		def cuentaMedica 		= CuentaMedica.findById(params.cuentaMedicaId)
		def detalles     		= DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)
		def cantidadFinal
		def valorUnitarioFinal
		def descuentoFinal
		def recargoUnitarioFinal

		detalles.each {
			cantidadFinal        = params['cantidadFinal_' + it.id].replaceAll(/\./, "").replaceAll(/\,/, "")
			valorUnitarioFinal   = params['valorUnitarioFinal_' + it.id].replaceAll(/\./, "").replaceAll(/\,/, "")
			descuentoFinal       = params['descuentoFinal_' + it.id].replaceAll(/\./, "").replaceAll(/\,/, "")
			recargoUnitarioFinal = params['recargoUnitarioFinal_' + it.id].replaceAll(/\./, "").replaceAll(/\,/, "")

			if (cantidadFinal == "")        cantidadFinal        = "0"
			if (valorUnitarioFinal == "")   valorUnitarioFinal   = "0"
			if (descuentoFinal == "")       descuentoFinal       = "0"
			if (recargoUnitarioFinal == "") recargoUnitarioFinal = "0"

			it.cantidadFinal    	= Long.parseLong(cantidadFinal)
			it.valorUnitarioFinal   = Long.parseLong(valorUnitarioFinal)
			it.descuentoFinal       = Long.parseLong(descuentoFinal)
			it.recargoUnitarioFinal = Long.parseLong(recargoUnitarioFinal)
			it.valorTotalFinal      = it.cantidadFinal * (it.valorUnitarioFinal - it.descuentoFinal + it.recargoUnitarioFinal);

			valorCuentaAprobado		+= it.valorTotalFinal;
			it.save()
		}

		log.info "params.esAprobada			: ${params.esAprobada}"
		log.info "valorCuentaAprobado		: ${valorCuentaAprobado}"

		cuentaMedica.esAprobada = params.esAprobada
		if (!cuentaMedica.esAprobada) {
			log.info "La cuenta medica se ha rechazado"
			cuentaMedica.valorCuentaAprobado = 0
		} else {
			cuentaMedica.esAprobada = valorCuentaAprobado > 0
			cuentaMedica.valorCuentaAprobado = valorCuentaAprobado
		}
		cuentaMedica.fechaAceptacion = new Date()
		log.info "Fecha de aprobacion		: ${cuentaMedica.fechaAceptacion}"
		log.info "Cuenta medica aprobada	: ${cuentaMedica.esAprobada}"
		log.info "Valor cuenta medica	 	: ${cuentaMedica.valorCuentaAprobado}"
		if (!cuentaMedica.save()) {
			log.info 'Hay error al guardar cuentaMedica...'
		}
		def ret=[cid: params.cuentaMedicaId]
		return ret
	}

	def completeRevisionDp04(user,pass,taskId,data){
		log.info("Ejecutando completeRevisionDp04")
		log.debug("user : $user")
		log.debug("pass : $pass")
		log.debug("taskId : $taskId")
		log.debug("data: $data")
		// Invocamos al BPM
		def dataToBpm=[:]

		log.info("taskId:"+taskId)
		log.info("dataToBpm:"+dataToBpm)

		def r = JBPMService.taskComplete(user, pass, taskId, dataToBpm)
		
		
		return 'OK'
	}

	/*******************************************************************
	 * 
	 *  FUNCIONES AUXILIARES
	 */	

	/**
	 * Este es el JSON del dp01 de revision
	 * y el JSON del dp02 y dp03 en consulta
	 */
	def guardaConsulta (params) {
		log.info("Ejecutando guardaConsulta")
		log.debug("params : $params")
		def idDetalle     = params.id
		def tipoConsulta  = params.tipo
		def detalleCuenta = DetalleCuentaMedica.findById(idDetalle)

		// La existencia de la variable adecuada indica el
		// uso de esta funcion
		if (params.texto != null) {
			def textoConsulta = params.texto
			if (tipoConsulta == "M") {
				detalleCuenta.consultaMedica = textoConsulta
			} else {
				detalleCuenta.consultaConvenio = textoConsulta
			}
		}

		def respuestaTexto = (params.respuestaTexto != null && params.respuestaTexto != '') ? params.respuestaTexto : null
		if (tipoConsulta == "M") {
			detalleCuenta.respuestaMedicaTexto = respuestaTexto
		} else {
			detalleCuenta.respuestaConvenioTexto = respuestaTexto
		}

		def respuestaSugiereAprobar
		switch (params.respuestaSugiereAprobar) {
			case 'true':
				respuestaSugiereAprobar = true
				break;
			case 'false':
				respuestaSugiereAprobar = false
				break;
			default:
				respuestaSugiereAprobar = null
				break;
		}

		if (tipoConsulta == "M") {
			detalleCuenta.respuestaMedicaSugiereAprobar = respuestaSugiereAprobar
		} else {
			detalleCuenta.respuestaConvenioSugiereAprobar = respuestaSugiereAprobar
		}

		if (respuestaSugiereAprobar == null) {
			if (params.respuestaModificarCantidad != null && params.respuestaModificarCantidad != '') {
				params.respuestaModificarCantidad = params.respuestaModificarCantidad.replaceAll("\\.", "")
				def respuestaModificarCantidad = Long.parseLong(params.respuestaModificarCantidad)
				if (tipoConsulta == "M") {
					detalleCuenta.respuestaMedicaModificarCantidad = respuestaModificarCantidad
				} else {
					detalleCuenta.respuestaConvenioModificarMonto = respuestaModificarCantidad
				}
			}
		} else {
			if (tipoConsulta == "M") {
				detalleCuenta.respuestaMedicaModificarCantidad = null
			} else {
				detalleCuenta.respuestaConvenioModificarMonto = null
			}
		}



		detalleCuenta.save()
		return null
	}

	def obtenDatosConsulta (params) {
		log.info("Ejecutando obtenDatosConsulta")
		log.debug("params : $params")
		def idDetalle     = params.id
		def tipoConsulta  = params.tipo

		def detalleCuenta = DetalleCuentaMedica.findById(idDetalle)
		if (tipoConsulta == "M") {
			return [ 'id__tipo'                    : params.id + "__" + params.tipo,
				'texto'                      : detalleCuenta.consultaMedica,
				'respuestaTexto'             : detalleCuenta.respuestaMedicaTexto,
				'respuestaSugiereAprobar'    : detalleCuenta.respuestaMedicaSugiereAprobar,
				'respuestaModificarCantidad' : detalleCuenta.respuestaMedicaModificarCantidad
			]
		} else {
			return [ 'id__tipo'                    : params.id + "__" + params.tipo,
				'texto'                      : detalleCuenta.consultaConvenio,
				'respuestaTexto'             : detalleCuenta.respuestaConvenioTexto,
				'respuestaSugiereAprobar'    : detalleCuenta.respuestaConvenioSugiereAprobar,
				'respuestaModificarMonto'    : detalleCuenta.respuestaConvenioModificarMonto ]
		}
	}

	def obtenerHistorialCuentasMedicas(trabajador) {
		log.info("Ejecutando obtenerHistorialCuentasMedicas")
		log.debug("trabajador : $trabajador")
		def cmTrabajador = CuentaMedica.findAllByTrabajadorAndEsAprobada(trabajador, true)
		def cuentasHist = new ArrayList()
		cmTrabajador.each {
			log.info "Buscar siniestro y detalle factura de cuenta medica: ${it.folioCuenta}"
			def siniestro 	= SiniestroService.findSiniestroByCuentaMedica(it)
			log.info "Siniestro encontrado: ${siniestro?.id}"
			def detFactura	= DetalleFactura.findByIdCuentaMedica(it.id)

			cuentasHist += [idCuentaMedica		: it.folioCuenta
				, idSiniestro			: siniestro?.id
				, fechaAceptacion		: it.fechaAceptacion
				, fechaPago			: detFactura?.factura?.fechaEnvioPago
				, valorCuentaMedica	: it.valorCuentaAprobado]
		}
		return cuentasHist
	}
}
