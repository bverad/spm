package cl.adexus.isl.spm

import java.util.Date;

import cl.adexus.helpers.DataSourceHelper;
import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.helpers.FormatosHelper
import cl.adexus.isl.spm.helpers.FormatosISLHelper;

/**
 * Servicios REST de Cuentas Medicas (para ser usados por el BPM) 
 *
 */
class CMRestService {
	def uploadService
	def messageSource
	def PrestadorService
	def mailService

	//Revisar esto porque esta dando warns
	//static quizas?
	def static final FECHA_PRESTACION	= 0
	def static final HORA_PRESTACION	= 1
	def static final CODIGO_PRESTACION 	= 2
	def static final GLOSA_PRESTACION	= 3
	def static final CANT_PRESTACION 	= 4
	def static final VALOR_UNITARIO		= 5
	def static final DESCUENTO			= 6
	def static final RECARGO			= 7
	def static final VALOR_FINAL		= 8
	def static final FORMATO_FECHA_ORI  = "dd-MM-yy HH:mm:ss SSS"
	def static final FORMATO_FECHA_ALT  = "dd/MM/yyyy HH:mm:ss SSS"

	def user='admin'
	def pass='admin'

	def valorIva = 0.19

	public def sendMailRechCuenta(def nombre, def mail, def cid, def fecha, def errores) {
		mailService.sendMail {
			int counter = 1
			String causas = "<br><br><table>"
			for (def error in errores)
			{
				causas = causas + "<tr><td width:60px>" + counter + "</td><td>" + error.mensaje + "</td></tr><br>"
				counter++
			}
			causas = causas + "</table>"
			to mail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Cuenta Medica Rechazada"
			html "Estimado " + nombre + ":<br><br>Lamentamos informarle que su cuenta médica con número " + cid +
					" y fecha de emisión " + FormatosHelper.fechaCortaStatic(fecha) +
					" ha sido rechazada por las siguientes causas:" + causas + "<br>Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}

	public def sendMailRechFormato(def nombre, def mail, def cid, def fecha) {
		mailService.sendMail {
			to mail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Cuenta Medica No Reconocible"
			html "Estimado " + nombre + ":<br><br>Lamentamos informarle que su cuenta médica con número " + cid +
					" y fecha de emisión " + FormatosHelper.fechaCortaStatic(fecha) +
					" fue ingresada al sistema con un formato incorrecto.<br><br>Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}

	public def sendMailRech(def nombre, def mail, def cid, def fecha, def valor, def tabla) {
		log.info("Ejecutando sendMailRech ")
		log.info("nombre : $nombre")
		log.info("mail : $mail")
		log.info("cid : $cid")
		log.info("fecha : $fecha")
		log.info("valor : $valor")
		log.info("tabla : $tabla")

		mailService.sendMail {
			to mail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Cuenta Medica Rechazada"
			html "Estimado " + nombre + ":<br><br>Su cuenta médica con número " + cid + " y fecha de emisión " +
					FormatosHelper.fechaCortaStatic(fecha) + " ha sido rechazada. Se adjunta detalle de esta:" + tabla +
					"<br><br>Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}

	public def sendMailAprob(def nombre, def mail, def cid, def fecha, def valor, def tabla) {
		log.info("Ejecutando sendMailAprob ")
		log.info("nombre : $nombre")
		log.info("mail : $mail")
		log.info("cid : $cid")
		log.info("fecha : $fecha")
		log.info("valor : $valor")
		log.info("tabla : $tabla")
		mailService.sendMail {
			to mail
			from "Instituto de Seguridad Laboral <smtp@isl.gob.cl>"
			subject "Cuenta Medica Aceptada"
			html "Estimado " + nombre + ":<br><br>Su cuenta médica con número " + cid + " y fecha de emisión " +
					FormatosHelper.fechaCortaStatic(fecha) + " ha sido aceptada por un monto total de \$" + valor +
					", por lo que se requiere que la factura correspondiente sea enviada a las dependencias de ISL a la brevedad" +
					". Se adjunta detalle de esta:" + tabla + "<br><br>Atte.,<br><br>Instituto de Seguridad Laboral"
		}
	}
	/**
	 * Validaciones de encabezado de cuenta medica
	 */
	def r01 (Map params) {
		System.out.println("Llamada desde el BPM ---> r01 (Hay errores de encabezado CM?)::"+params)
		log.info("Llamada desde el BPM ---> r01 (Hay errores de encabezado CM?)::"+params)

		//mañana
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date manyanaAPrimeraHora= cal.getTime();

		// buscar cuenta medica
		def cm = CuentaMedica.findById(Long.parseLong(params.cid.toString()))
		def errores=false
		def cuentaEnPapel=false
		if (cm) {
			// Validar fechas de ingreso
			if (cm.fechaDesde > cm.fechaHasta) {
				errores = true
				log.info 'Fecha cobro desde debe ser menor que fecha cobro hasta'
				addErrorCuentaMedica('Fecha cobro desde debe ser menor que fecha cobro hasta', cm)
			}
			if (cm.fechaHasta > manyanaAPrimeraHora) {
				errores = true
				log.info 'Fecha cobro hasta debe ser menor que la fecha de hoy'
				addErrorCuentaMedica('Fecha cobro hasta debe ser menor que la fecha de hoy', cm)
			}
			if (cm.fechaEmision > manyanaAPrimeraHora) {
				errores = true
				log.info 'Fecha emisión debe ser menor que fecha de hoy'
				addErrorCuentaMedica('Fecha emisión debe ser menor que fecha de hoy', cm)
			}
			// Validar OXAs
			def idSinistroOPA = null
			cm.opas.each {
				log.info 'Procesando OPA: ' + it
				def oxa = OPA.findById(it)
				errores = validarOXA(cm, oxa, "OPA")
				if (!errores) {
					// Registrar ID de Siniestro
					idSinistroOPA = oxa.siniestro.id
				}
			}
			def idSinistroODA = null
			def siniestroOK = true
			cm.odas.each {
				log.info 'Procesando ODA: ' + it
				def oxa = ODA.findById(it)
				errores = validarOXA(cm, oxa, "ODA")
				if (!errores) {
					// Registrar ID de Siniestro
					if (!idSinistroODA) {
						idSinistroODA = oxa.siniestro.id
					}
					siniestroOK = idSinistroODA == oxa.siniestro.id
				}
			}
			def idSinistroOPAEP = null
			cm.opaeps.each {
				log.info 'Procesando OPAEP: ' + it
				def oxa = OPAEP.findById(it)
				errores = validarOXA(cm, oxa, "OPAEP")
				if (!errores) {
					// Registrar ID de Siniestro
					idSinistroOPAEP = oxa.siniestro.id
				}
			}
			// Validar ID's de Siniestro
			def validateSiniestro = validateSiniestro(idSinistroOPA, idSinistroOPAEP, idSinistroODA)
			log.info "Validando Siniestro: siniestroOK [${siniestroOK}] - validateSiniestro [${validateSiniestro}]"
			if (!(siniestroOK && validateSiniestro)) {
				errores = true
				log.info 'Las OPA u ODAs ingresadas, corresponden a más de un siniestro.'
				addErrorCuentaMedica('Las OPA u ODAs ingresadas, corresponden a más de un siniestro.', cm)
			}
			cuentaEnPapel = (cm.formatoOrigen.codigo == "PAPEL")
		}
		log.info 'cuentaEnPapel: ' + cuentaEnPapel
		log.info 'errores: ' + errores
		return [errores: errores, cuentaEnPapel: cuentaEnPapel]
	}

	def private validateSiniestro(idSinistroOPA, idSinistroOPAEP, idSinistroODA) {
		idSinistroOPA = !idSinistroOPA ? ( !idSinistroOPAEP ? ( !idSinistroODA ? 0 : idSinistroODA ) : idSinistroOPAEP ) : idSinistroOPA
		idSinistroOPAEP = !idSinistroOPAEP ? ( !idSinistroOPA ? ( !idSinistroODA ? 0 : idSinistroODA ) : idSinistroOPA ) : idSinistroOPAEP
		idSinistroODA = !idSinistroODA ? ( !idSinistroOPA ? ( !idSinistroOPAEP ? 0 : idSinistroOPAEP ) : idSinistroOPA ) : idSinistroODA
		return (idSinistroOPA == idSinistroODA) && (idSinistroOPAEP == idSinistroODA)
	}

	private def validarOXA(cm, oxa, tipo) {
		def errores = false
		if (oxa == null) {
			errores = true
			log.info "	La ${tipo} no existe"
			addErrorCuentaMedica("La ${tipo} no existe", cm)
		} else {
			// Validar Prestador
			if (oxa.centroAtencion.prestador != cm.centroSalud.prestador) {
				errores = true
				log.info "	(${tipo}:${oxa.id}) El prestador no corresponde al centro de atencion"
				addErrorCuentaMedica("(${tipo}:${oxa.id}) El prestador no corresponde al centro de atencion", cm)
			}
			// Validar Centro Salud
			if (oxa.centroAtencion != cm.centroSalud) {
				errores = true
				log.info "	(${tipo}:${oxa.id}) El Centro de Salud ingresado en el encabezado no corresponde al generado en la OPA/EP"
				addErrorCuentaMedica("(${tipo}:${oxa.id}) El Centro de Salud ingresado en el encabezado no corresponde al generado en la OPA/EP", cm)
			}

			// Validar Fechas inicioVigencia + duracionDias contemple las fechas de cobro desde y fecha de cobro hasta
			oxa.inicioVigencia = FechaHoraHelper.setTimeDate(oxa.inicioVigencia)
			if (!( ((oxa.inicioVigencia + oxa.duracionDias) >= cm.fechaDesde) &&
			(oxa.inicioVigencia <= cm.fechaHasta)) ) {
				errores = true
				log.info "	(${tipo}:${oxa.id}) El periodo de cobro no es correcto"
				addErrorCuentaMedica("(${tipo}:${oxa.id}) El periodo de cobro no es correcto", cm)
			}
			// Validar Trabajador
			if (oxa?.siniestro?.trabajador?.run != cm?.trabajador?.run) {
				errores = true
				log.info "	(${tipo}:${oxa.id}) El RUN del Trabajador digitado no corresponde al RUN del trabajador ingresado en la ${tipo}"
				addErrorCuentaMedica("(${tipo}:${oxa.id}) El RUN del Trabajador digitado no corresponde al RUN del trabajador ingresado en la ${tipo}", cm)
			}
		}
		return errores
	}

	/**
	 * 
	 * Informar al prestador de los errores de la cuenta medica 
	 * despues de esto termina el flujo
	 * 
	 */
	def r02(Map params){
		log.info("Llamada desde el BPM ---> r02 (Envia rechazo por validacion de CM)::"+params)
		CuentaMedica cuentaMedica = CuentaMedica.findById(params.cid.toString())
		def centroSalud = cuentaMedica.centroSalud
		def errores = cuentaMedica.errores
		sendMailRechCuenta(centroSalud.nombre.toString()
				, centroSalud.email.toString()
				, cuentaMedica.folioCuenta.toString()
				, cuentaMedica.fechaEmision
				, errores)
		cuentaMedica.esAprobada = false
		cuentaMedica.fechaAceptacion = new Date()
		cuentaMedica.save()
		def sucess=true
		log.info 'sucess: ' + sucess
		return [sucess: sucess]
	}

	/**
	 * Validar formato del archivo para cargar detalle de la cuenta medica
	 * @param params
	 * @return
	 */
	def r04(Map params){
		log.info("Llamada desde el BPM ---> r04 (Validacion formato archivo CM)::"+params)

		def cuentaMedica = CuentaMedica.findById(params.cid)
		def errors = 1
		if (cuentaMedica) {
			errors = 0
			log.info "Identificador del archivo: " + cuentaMedica.idArchivo
			// Obtenemos archivo del repositorio
			def file = uploadService.doGetDocumentoAdicional(cuentaMedica.idArchivo)
			if (file.status == 0) {
				// Validamos el archivo
				String[] lines = file.doc.inputStream.text.split('\n')
				List<String[]> rows = lines.collect {it.split(';')}
				def cont = 0
				rows.each {
					if (cont == 0) { cont++; return; }
					def fechaPrestacion
					def strFecha = it[FECHA_PRESTACION]
					def strHora = it[HORA_PRESTACION]
					try {
						fechaPrestacion = FechaHoraHelper.stringToDate(strFecha.trim() + ' ' + strHora.trim() + ":00 666", FORMATO_FECHA_ORI)
						log.info 'Fecha convertida: ' + fechaPrestacion.toString()
					} catch (Exception ex) {
						log.info 'No se pudo parsear fecha con formateo original. Se intenta con formateo alternativo.'
						try {
							fechaPrestacion = FechaHoraHelper.stringToDate(strFecha.trim() + ' ' + strHora.trim() + ":00 666", FORMATO_FECHA_ALT)
							log.info 'Fecha convertida: ' + fechaPrestacion.toString()
						} catch (Exception e) {
							log.info 'No se pudo parsear fecha: ' + e.getMessage()
						}
					}
					def detParams = [	fecha				: fechaPrestacion,		//Date
						codigo				: it[CODIGO_PRESTACION],
						glosa				: it[GLOSA_PRESTACION],
						cantidad			: it[CANT_PRESTACION],	//Integer
						valorUnitario		: it[VALOR_UNITARIO],	//Integer
						descuentoUnitario	: it[DESCUENTO],		//Integer
						recargoUnitario		: it[RECARGO],			//Integer
						cuentaMedica		: cuentaMedica]
					def dcm = new DetalleCuentaMedica(detParams)
					if (!dcm.validate()) {
						dcm.errors.allErrors.each {
							def mensaje = "Hay errores en la linea ${cont} del archivo: " + messageSource.getMessage(it, null)
							log.info mensaje
							addErrorCuentaMedica(mensaje, cuentaMedica)
						}
						errors++
					}
					cont++
				}
			}
		}
		def validacionFormato = errors == 0
		log.info 'validacionFormato: ' + validacionFormato
		return [validacionFormato: validacionFormato]
	}

	/**
	 * Si el formato del archivo se realiza la carga de los datos
	 * @param params
	 * @return
	 */
	def r05(Map params){
		log.info("Llamada desde el BPM ---> r05(Guarda detalle archivo CM en BD)::"+params)

		def cuentaMedica = CuentaMedica.findById(params.cid)
		List<DetalleCuentaMedica> detalleList = new ArrayList<DetalleCuentaMedica>()
		def errors = 1
		if (cuentaMedica) {
			errors = 0
			log.info "Identificador del archivo: " + cuentaMedica.idArchivo
			// Obtenemos archivo del repositorio
			def file = uploadService.doGetDocumentoAdicional(cuentaMedica.idArchivo)
			if (file.status == 0) {
				// Validamos el archivo
				String[] lines = file.doc.inputStream.text.split('\n')
				List<String[]> rows = lines.collect {it.split(';')}
				def mensaje = ''
				def cont = 0
				rows.each {
					if (cont == 0) { cont++; return; }
					def fechaPrestacion
					def strFecha = it[FECHA_PRESTACION]
					def strHora = it[HORA_PRESTACION]
					try {
						fechaPrestacion = FechaHoraHelper.stringToDate(strFecha.trim() + ' ' + strHora.trim() + ":00 666", FORMATO_FECHA_ORI)
						//	log.info 'Fecha convertida: ' + fechaPrestacion.toString()
					} catch (Exception ex) {
						log.info 'No se pudo parsear fecha con formateo original. Se intenta con formateo alternativo.'
						try {
							fechaPrestacion = FechaHoraHelper.stringToDate(strFecha.trim() + ' ' + strHora.trim() + ":00 666", FORMATO_FECHA_ALT)
							//		log.info 'Fecha convertida: ' + fechaPrestacion.toString()
						} catch (Exception e) {
							log.info 'No se pudo parsear fecha: ' + e.getMessage()
						}
					}
					def valorTotal = it[VALOR_FINAL].toLong()
					def detParams = [	fecha				: fechaPrestacion,		//Date
						codigo				: it[CODIGO_PRESTACION],
						glosa				: it[GLOSA_PRESTACION],
						cantidad			: it[CANT_PRESTACION],	//Integer
						valorUnitario		: it[VALOR_UNITARIO],	//Integer
						descuentoUnitario	: it[DESCUENTO],		//Integer
						recargoUnitario		: it[RECARGO],			//Integer
						valorTotal			: valorTotal,		//Integer
						cuentaMedica		: cuentaMedica]
					detalleList.add(new DetalleCuentaMedica(detParams))
				}
			}
		}
		// Si existe la cuenta se carga el archivo validado
		if (errors == 0) {
			detalleList.eachWithIndex { obj, i ->
				try {
					log.info "Guardando linea numero ${i}"
					/*log.info "Fecha:" + obj.fecha.toString()
					 log.info "Codigo:" + obj.codigo.toString()
					 log.info "Glosa:" + obj.glosa.toString()
					 log.info "Cantidad:" + obj.cantidad.toString()
					 log.info "V. Unit.:" + obj.valorUnitario.toString()
					 log.info "D. Unit.:" + obj.descuentoUnitario.toString()
					 log.info "R. Unit.:" + obj.recargoUnitario.toString()
					 log.info "V. Total:" + obj.valorTotal.toString()
					 log.info "CM:" + obj.cuentaMedica.toString()*/
					obj.save()
				} catch (Exception e) {
					// Si ocurrio un error en la persistencia del archivo se debe guardar en la BD
					errors++
					addErrorCuentaMedica(e.getMessage(), cuentaMedica)
				}

			}
		}
		def sucess = errors == 0
		log.info 'success: ' + sucess
		return [sucess: sucess]
	}

	/**
	 * Si el formato del archivo no es correcto se envia notificacion de correo al prestador
	 * @param params
	 * @return
	 */
	def r06(Map params){
		log.info("Llamada desde el BPM ---> r06(Envia rechazo por formato de archivo CM)::"+params)
		
		def cuentaMedica = CuentaMedica.findById(params.cid.toString())
		log.info("El valor de la cuenta medica es el siguiente : $cuentaMedica")
		if(cuentaMedica){
			def centroSalud = cuentaMedica.centroSalud
			sendMailRechFormato(centroSalud.nombre.toString(),
					centroSalud.email.toString(),
					cuentaMedica.folioCuenta.toString(),
					cuentaMedica.fechaEmision)
		}else
			log.info("No existe cuenta medica")

		def sucess=true

		log.info 'sucess: ' + sucess
		return [sucess: sucess]
	}


	/**
	 * Revision Arancelaria
	 * 
	 * @param params
	 * @return
	 */
	def rr01(Map params){
		log.info("Llamada desde el BPM ---> rr01(Revision Arancelaria CM)::"+params)
		def cuentaMedica = CuentaMedica.findById(params.cid)
		def detallesCuenta  = DetalleCuentaMedica.findAllByCuentaMedica(cuentaMedica)
		def errors = false
		detallesCuenta.each { detalle ->
			//Valida cada detalle de Cuenta
			def codigo = detalle.codigo
			def glosa = detalle.glosa
			log.info "Validando detalle ${codigo}"
			// WARNINGS
			// Código de prestación no viene
			log.info '	Código de prestación no viene: ' + !codigo
			if (!codigo)
				errors = addErrorDetalleCuentaMedica("La prestación ${codigo} no puede ser nulo", detalle)

			// Fecha y hora menor que hoy
			log.info '	Fecha y hora menor que hoy: ' + (detalle.fecha > new Date())
			if ( detalle.fecha > new Date() )
				errors = addErrorDetalleCuentaMedica("La prestación ${codigo} no puede ser mayor a hoy", detalle)

			// Fecha y hora este entre fecha de cobro del encabezado
			def fechaCobro = detalle.fecha < cuentaMedica.fechaDesde || detalle.fecha > cuentaMedica.fechaHasta
			log.info "	Validar fecha cobro - fechaCobro: ${fechaCobro}"
			if (fechaCobro)
				errors = addErrorDetalleCuentaMedica("La fecha de la prestación ${codigo} no está en el período de cobro [${FormatosISLHelper.fechaCortaStatic(cuentaMedica.fechaDesde)} - ${FormatosISLHelper.fechaCortaStatic(cuentaMedica.fechaHasta)}]", detalle)

			// Recargo horario es mayor que cero y el horario es habil
			def esHabil = FechaHoraHelper.isBetweenDayAndHours(detalle.fecha, Calendar.MONDAY, Calendar.FRIDAY, "08:00", "20:00")
			def hayRecargo = detalle.recargoUnitario > 0
			log.info '	Recargo horario es mayor que cero y el horario es habil: ' + (hayRecargo && esHabil)
			if (hayRecargo && esHabil)
				errors = addErrorDetalleCuentaMedica("La prestación ${codigo} tiene recargo y está en horario habil", detalle)

			// Valor total no es ( valor unitario - descto + recargo * cantidad )
			detalle.cantidad			= detalle.cantidad ? detalle.cantidad : 0
			detalle.valorUnitario		= detalle.valorUnitario ? detalle.valorUnitario : 0
			detalle.descuentoUnitario	= detalle.descuentoUnitario ? detalle.descuentoUnitario : 0
			detalle.recargoUnitario		= detalle.recargoUnitario ? detalle.recargoUnitario : 0
			def valorTotalCalc 			= detalle.cantidad * ( detalle.valorUnitario - detalle.descuentoUnitario + detalle.recargoUnitario)
			log.info '	Valor total no es ( valor unitario - descto + recargo * cantidad ): ' + (valorTotalCalc != detalle.valorTotal)
			if (valorTotalCalc != detalle.valorTotal)
				errors = addErrorDetalleCuentaMedica("La prestación ${codigo} presenta diferencias entre el VTN digitado y VTN Calculado", detalle)

			// Buscar OXA's de la cuenta medica
			//TODO: Falta validación de ODA
			def odas   = cuentaMedica.odas
			def opas   = cuentaMedica.opas
			def opaeps = cuentaMedica.opaeps
			errors = true
			opas.each {
				log.info "Validando OPA ID ${it}"
				def opa = OPA.findById(it)
				def fechaHasta = opa.inicioVigencia + opa.duracionDias
				def esValido = detalle.fecha < opa.inicioVigencia && detalle.fecha > fechaHasta
				log.info "	Validar fecha vigencia - esValido: ${esValido}"
				errors = false
				if (esValido)
					errors = addErrorDetalleCuentaMedica("La prestación ${codigo} no es valido para rango de fechas en la OPA " + opa.id, detalle)
			}
			errors = true
			opaeps.each {
				log.info "OPAEP ID ${it}"
				def opaep = OPAEP.findById(it)
				def fechaHasta = opaep.inicioVigencia + opaep.duracionDias
				def esValido = detalle.fecha < opaep.inicioVigencia && detalle.fecha > fechaHasta
				log.info "	Validando OPAEP " + opaep.id + " - esValido: ${esValido}"
				errors = false
				if (esValido)
					errors = addErrorDetalleCuentaMedica("La prestación ${codigo} no es valido para rango de fechas en la OPAEP " + opaep.id, detalle)
			}
			errors = true
			odas.each {
				log.info "ODA ID ${it}"
				def oda = ODA.findById(it)
				def fechaHasta = oda.inicioVigencia + oda.duracionDias
				def esValido = detalle.fecha < oda.inicioVigencia && detalle.fecha > fechaHasta
				log.info "	Validando ODA " + oda.id + " - esValido: ${esValido}"
				errors = false
				if (esValido)
					errors = addErrorDetalleCuentaMedica("La prestación ${codigo} no es valido para rango de fechas en la ODA " + oda.id, detalle)
			}

			log.info "Validar arancel en convenio centro salud [${cuentaMedica.centroSalud.id}], prestacion [${detalle.codigo}]"

			def arancelList = ArancelConvenio.executeQuery(
					"SELECT	DISTINCT arco, arba " +
					"FROM	CentroSaludEnConvenio cscv, ArancelConvenio arco, ArancelBase arba " +
					"WHERE	cscv.centroSalud.id = :cuentaMedicaId " +
					"AND	cscv.convenio.id = arco.convenio.id " +
					"AND	arco.codigoPrestacion = arba.codigo " +
					"AND 	((:fechaHoy >= arba.desde AND arba.hasta IS NULL) OR (:fechaHoy BETWEEN arba.desde AND arba.hasta)) " +
					"AND	((:fechaHoy >= arco.desde AND arco.hasta IS NULL) OR (:fechaHoy >= arco.desde AND :fechaHoy <= arco.hasta)) " +
					"AND	arba.codigo = :codigoPrestacion ",
					[cuentaMedicaId		: cuentaMedica.centroSalud.id
						, codigoPrestacion	: detalle.codigo
						, fechaHoy			: detalle.fecha])
			if (!arancelList) {
				log.info "La prestación ${codigo} no se encuentra en convenio [ArancelBase]"
				arancelList = ArancelConvenio.executeQuery(
						"SELECT	DISTINCT arco, paqu " +
						"FROM	CentroSaludEnConvenio cscv, ArancelConvenio arco, Paquete paqu " +
						"WHERE	cscv.centroSalud.id = :cuentaMedicaId " +
						"AND	cscv.convenio.id = arco.convenio.id " +
						"AND	arco.codigoPrestacion = paqu.codigo " +
						"AND 	((:fechaHoy >= paqu.desde AND paqu.hasta IS NULL) OR (:fechaHoy BETWEEN paqu.desde AND paqu.hasta)) " +
						"AND	((:fechaHoy >= arco.desde AND arco.hasta IS NULL) OR (:fechaHoy >= arco.desde AND :fechaHoy <= arco.hasta)) " +
						"AND	paqu.codigo = :codigoPrestacion ",
						[cuentaMedicaId		: cuentaMedica.centroSalud.id
							, codigoPrestacion	: detalle.codigo
							, fechaHoy			: detalle.fecha])
				if (!arancelList) {
					log.info "La prestación ${codigo} no se encuentra en convenio [Paquete]"
					// Error de prestación no existe en convenio
					errors = addErrorDetalleCuentaMedica("La prestación ${codigo} no se encuentra en convenio", detalle)
				}
			}
			if (arancelList) {
				log.info "Validar valores pactados"
				def arancelConvenio	= arancelList[0][0]
				def prestacion		= arancelList[0][1]
				def valorPactado = PrestadorService.getValorPactado(prestacion, arancelConvenio)
				// Validar valores
				def validacionValor = valorPactado != detalle.valorUnitario
				log.info "	Validando valor ingresado: ${validacionValor}"
				if (validacionValor)
					errors = addErrorDetalleCuentaMedica("La prestación ${codigo} tiene valor unitario distinto.", detalle)
				// Validar glosa
				if (detalle.glosa != null) {
					validacionValor = prestacion.glosa != detalle.glosa
					log.info "	Validando glosa: ${validacionValor}"
					if (validacionValor)
						errors = addErrorDetalleCuentaMedica("La prestación ${codigo} tiene glosa distinto. Glosa obtenida [${prestacion.glosa}]", detalle)
				}
				// Validar recargo
				log.info "	Validando recargo: ${!esHabil}"
				def valorRecargoPactado = 0
				if (!esHabil) {
					// Calcular recargo
					valorRecargoPactado = PrestadorService.getValorRecargo(prestacion, arancelConvenio)
					if (detalle.recargoUnitario != valorRecargoPactado)
						errors = addErrorDetalleCuentaMedica("La prestación ${codigo} tiene diferencias en el valor de recargo.", detalle)
				}

				detalle.valorUnitarioPactado = valorPactado
				detalle.recargoUnitarioPactado = valorRecargoPactado
				detalle.valorTotalPactado = detalle.cantidad * (detalle.valorUnitarioPactado - detalle.descuentoUnitario + detalle.recargoUnitarioPactado)
				detalle.glosaPactada = prestacion.glosa

				log.info 'Guardar Detalle con valores pactados'
				detalle.save()
			}
		}
		//En realidad al bpm se responde siempre true. ;)
		log.info 'aprobado:' + errors
		return [aprobado: errors]
	}

	def addErrorCuentaMedica(msj, cuentaMedica) {
		def errores = new ErrorCuentaMedica([fecha: new Date(), mensaje: msj, cuentaMedica: cuentaMedica])
		return errores.save()
	}

	def addErrorDetalleCuentaMedica(msj, detalleCuentaMedica) {
		def errores = new ErrorDetalleCuentaMedica([fecha: new Date(), mensaje: msj, detalleCuentaMedica: detalleCuentaMedica])
		return !(!errores.save())
	}

	/**
	 * Avisar al prestador de como quedo la CM
	 *
	 * @param params
	 * @return
	 */
	def rr02(Map params){
		log.info("Llamada desde el BPM ---> rr02(Informa al prestador CM final)::"+params)
		def cuentaMedica = CuentaMedica.findById(params.cid.toString())
		def centroSalud = cuentaMedica.centroSalud
		if (cuentaMedica.esAprobada){
			log.debug("Notificando cuenta medica aprobada")
			sendMailAprob(centroSalud.nombre.toString(),
					centroSalud.email.toString(),
					cuentaMedica.folioCuenta.toString(),
					cuentaMedica.fechaEmision,
					cuentaMedica.valorCuentaAprobado,
					obtenerTablaDetalles(cuentaMedica.detalleCuentaMedica))
		}else{
			log.debug("Notificando cuenta medica rechazada")
			sendMailRech(centroSalud.nombre.toString(),
					centroSalud.email.toString(),
					cuentaMedica.folioCuenta.toString(),
					cuentaMedica.fechaEmision,
					cuentaMedica.valorCuentaAprobado,
					obtenerTablaDetalles(cuentaMedica.detalleCuentaMedica))
		}
		def aprobado=true

		log.info 'aprobado:' + aprobado
		return [aprobado: aprobado]
	}

	def obtenerTablaDetalles (def detalles){

		String tabla = "<br><br><table border =\"1\"><tr><td></td><td>Fecha</td><td>Código</td><td>" +
				"Glosa</td><td>Cantidad</td><td>Valor</td><td>Descuento</td><td>Recargo</td><td>Total</td>" +
				"<td>Mensaje</td></tr>"
		int counter = 1
		long totFact = 0
		long totCM = 0
		for (def detalleCM in detalles)
		{
			def errores = ErrorDetalleCuentaMedica.findAllByDetalleCuentaMedica(detalleCM)
			int rowsp = 1
			if (errores != null && errores.size() > 0)
			{
				rowsp = errores.size()
			}
			tabla = tabla + "<tr><td rowspan=\"" + rowsp + "\">" + counter + "</td> " +
					"<td rowspan=\"" + rowsp + "\">" + FormatosHelper.fechaHoraStatic(detalleCM.fecha) + "</td>" +
					"<td rowspan=\"" + rowsp + "\">" + detalleCM.codigo + "</td>" +
					"<td rowspan=\"" + rowsp + "\">" + detalleCM.glosa + "</td>" +
					"<td rowspan=\"" + rowsp + "\">" + detalleCM.cantidadFinal.toString() + "</td>" +
					"<td rowspan=\"" + rowsp + "\">" + detalleCM.valorUnitarioFinal.toString() + "</td>" +
					"<td rowspan=\"" + rowsp + "\">" + detalleCM.descuentoFinal.toString() + "</td>" +
					"<td rowspan=\"" + rowsp + "\">" + detalleCM.recargoUnitarioFinal.toString() + "</td>" +
					"<td rowspan=\"" + rowsp + "\">" + detalleCM.valorTotalFinal.toString() + "</td><td>"
			int cont2 = 1
			for (def errorDCM in errores)
			{
				tabla = tabla + errorDCM.mensaje.toString()
				if (cont2 < rowsp)
				{
					tabla += "</td></tr><tr><td>"
					cont2++
				}
			}
			tabla = tabla + "</td></tr>"
			counter++
		}
		tabla = tabla +"</table>"
		//	log.info(tabla)
		return tabla
	}

}
