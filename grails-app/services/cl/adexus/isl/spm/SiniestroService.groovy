package cl.adexus.isl.spm

import cl.adexus.helpers.DataSourceHelper
import cl.adexus.helpers.FormatosHelper
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.isl.spm.domain.*
import groovy.sql.Sql


class SiniestroService {


	def dataSource
	def SeguimientoService
	def BISIngresoService

	/**
	 * Determina los siniestros del trabajador
	 *
	 * @params run
	 */
	def r01(params) {
		// Limpiamos el run
		params['run'] = ((String)params['run']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		params['rutEmpresa'] = ((String)params['rutEmpresa']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		
		def run = params['run']
		def rutEmpresa = params['rutEmpresa']  
		
		//verifica si los campos fueron completados
		if(run.equals("") && rutEmpresa.equals("")){
			def t =  new PersonaNatural()
			t.errors.reject("cl.adexus.personaNatural.busqueda.fail",
				["empresa", "persona"] as Object[],
				"[Debe existir al menos un rut de [{0}] o [{1}] para efectuar una busqueda]")
			return (['result': false,  model: ['t': t]])
			
		}
		
		//si run viene con valor se verifica si existe en el registro
		if(!run.equals("")){
			def trabajador = PersonaNatural.findByRun(run)	
			if (!trabajador) {
				def t =  new PersonaNatural()
				t.run = run
				t.errors.reject("cl.adexus.personaNatural.noExists",
					[run] as Object[],
					"[El trabajador [{0}] no existe]")
				return (['result': false,  model: ['t': t]])
			}
		}
		
		//si rutEmpresa viene con valor se verifica si existe en el registro
		if(!rutEmpresa.equals("")){
			def empleador = PersonaJuridica.findByRut(rutEmpresa)
			log.info("empleador : $empleador")
			if(!empleador){
				def e =  new PersonaJuridica()
				e.rut = rutEmpresa
				e.errors.reject("cl.adexus.personaJuridica.noExists",
					[rutEmpresa] as Object[],
					"[El empleador [{0}] no existe]")
				return (['result': false,  model: ['e': e]])
			}
		}
		// Busca si existe el trabajador (run)
		return ['result': true]

	}

	def preDp02(run, rutEmpresa) {
		log.info("Ejecutando metodo preDp02")
		log.info("Datos recibidos : $run , $rutEmpresa")
		def model=[:]
		run = ((String)run).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		rutEmpresa = ((String)rutEmpresa).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

		def siniestroList = []
		def trabajador
		def empleador
		if(run && rutEmpresa){
			log.info("rut trabajador y empleador presentes en el filtro de siniestros")
			trabajador = PersonaNatural.findByRun(run)
			empleador = PersonaJuridica.findByRut(rutEmpresa)
			model['trabajador']= trabajador
			model['empleador']= empleador
			siniestroList = Siniestro.findAllByTrabajadorAndEmpleador(trabajador, empleador)
		}else if(run && !rutEmpresa){
			log.info("solo run presente en el filtro")
			trabajador = PersonaNatural.findByRun(run)
			model['trabajador']= trabajador
			siniestroList = Siniestro.findAllByTrabajador(trabajador)
		}else if(!run && rutEmpresa){
			log.info("solo rut presente en el filtro")
			empleador = PersonaJuridica.findByRut(rutEmpresa)
			model['empleador']= empleador
			siniestroList = Siniestro.findAllByEmpleador(empleador)
		}
		
		model['siniestros'] = siniestroList
		
	
		return model
	}

	def preDp03(m) {
		return m
	}

	/**
	 * Busca los datos del siniestro
	 *
	 * @params id (de siniestro)
	 */
	def cu02(params){
		def model=[:]
		// Busca si viene un id de Siniestro
		if (params['id']) {
			Siniestro s = Siniestro.get(params['id'])

			if (s) {
				// Busca Denuncias
				def diats=DIAT.findAllBySiniestro(s)
				def dieps=DIEP.findAllBySiniestro(s)

				// Busca ODA
				def odas=ODA.findAllBySiniestro(s)

				//OPA
				if (s.opa!=null && !s.opa.isAttached()) {
					s.opa.attach()
				}
				log.info("OPA"+s.opa?.centroAtencion?.prestador?.personaJuridica?.razonSocial) //Pal Lazy

				//OPAEP
				if (s.opaep!=null && !s.opaep.isAttached()) {
					s.opaep.attach()
				}
				log.info("OPAEP:"+s.opaep?.centroAtencion?.prestador?.personaJuridica?.razonSocial) //Pal Lazy

				// Busca CMs
				long sumCMMontosCobr = 0
				long sumCMMontosAprob = 0
				def sql = new Sql(dataSource)
				def sqlQueryCM =	"SELECT DISTINCT cm.id, cm.valor_cuenta, cm.valor_cuenta_aprobado " +
						" FROM cuenta_medica as cm, siniestro, cuenta_medica_opas as cmo, opa " +
						" WHERE siniestro.id = :id_siniestro " +
						" AND siniestro.opa_id = opa.id " +
						" AND cmo.cuenta_medica_id = cm.id  " +
						" AND cmo.opas_integer = opa.id  " +
						" UNION " +
						" SELECT DISTINCT cm.id, cm.valor_cuenta, cm.valor_cuenta_aprobado " +
						" FROM cuenta_medica as cm, cuenta_medica_odas as cmo, oda " +
						" WHERE oda.siniestro_id =  :id_siniestro " +
						" AND cmo.cuenta_medica_id = cm.id  " +
						" AND cmo.odas_integer = oda.id  " +
						" UNION " +
						" SELECT DISTINCT cm.id, cm.valor_cuenta, cm.valor_cuenta_aprobado " +
						" FROM cuenta_medica as cm, siniestro, cuenta_medica_opaeps as cmo, opaep " +
						" WHERE siniestro.id =  :id_siniestro " +
						" AND siniestro.opaep_id = opaep.id " +
						" AND cmo.cuenta_medica_id = cm.id  " +
						" AND cmo.opaeps_integer = opaep.id  "
				def sqlQuery = sqlQueryCM + " ORDER BY ID ASC  "
				def queryParams = [id_siniestro: s.id]
				def rows= sql.rows(sqlQuery, queryParams)
				log.info "Obtener CM: rows [${rows?.size()}]"
				def cuentasMedicas
				rows.each { row ->
					if (!cuentasMedicas) { cuentasMedicas = new ArrayList() }
					cuentasMedicas.add(CuentaMedica.get(row.id))
					sumCMMontosCobr += row.valor_cuenta
					sumCMMontosAprob += row.valor_cuenta_aprobado
				}


				// Busca Facturas
				long sumFactMontoAprob = 0

				def sqlQueryFact = "SELECT	distinct fact.id " +
						" FROM factura as fact, detalle_factura as df, (" + sqlQueryCM + ") as cm " +
						" WHERE fact.id = df.factura_id  " +
						" AND df.id_cuenta_medica = cm.id " +
						" ORDER BY ID ASC"

				def rowsFact		= sql.rows(sqlQueryFact, queryParams)
				log.info "Obtener Fact: rows [${rowsFact?.size()}]"
				def facturas
				rowsFact.each { row ->
					if (!facturas) { facturas = new ArrayList() }
					def factura = Factura.get(row.id)
					facturas.add(factura)
					if (factura.fechaEnvioPago!= null)
					{
						def detFacts = DetalleFactura.findAllByFactura(factura)
						for (def df in detFacts)
						{
							sumFactMontoAprob += df.valorCuentaMedica
						}
					}
				}

				long sumReemMontosSolic = 0
				long sumReemMontosAprob = 0
				// Busca Reembolso
				def reembolsos=Reembolso.findAllBySiniestro(s)
				for (def reembolso in reembolsos)
				{
					if (reembolso.solicitante!=null && !reembolso.solicitante.isAttached()) {
						reembolso.solicitante.attach()
					}
					sumReemMontosSolic += reembolso.montoSolicitado
					if (reembolso.fechaAprobacion!=null)
					{
						def detalles = DetalleGastosReembolso.findAllByReembolso(reembolso)
						for (def detalle in detalles)
						{
							sumReemMontosAprob += detalle.valorAprobado
						}
					}
				}

				// Busca RECA
				def reca=RECA.findBySiniestroAndXmlEnviadoIsNotNull(s)
				if (reca!=null && reca.calificacion!=null && !reca.calificacion.isAttached()) {
					reca.calificacion.attach()
				}

				// Busca RELAs
				def relas=Rela.findAllBySiniestro(s)

				// Busca ALLAs
				def allas=Alla.findAllBySiniestro(s)

				// Busca ALMEs
				def almes=Alme.findAllBySiniestro(s)

				//Documentacion Adicional
				def docsDIAT = DocumentacionAdicional.executeQuery(
						"SELECT	d " +
						"FROM  	DocumentacionAdicional as d, DIAT as diat " +
						"WHERE 	diat.siniestro.id = ?" +
						"AND	d.denunciaAT.id = diat.id", [s.id]
						);
				def docsDIEP = DocumentacionAdicional.executeQuery(
						"SELECT	d " +
						"FROM  	DocumentacionAdicional as d, DIEP as diep " +
						"WHERE 	diep.siniestro.id = ?" +
						"AND	d.denunciaEP.id = diep.id", [s.id]
						);


				model = [ 'diats': diats,
					'dieps': dieps,
					'odas': odas,
					'cuentasMedicas': cuentasMedicas,
					'facturas': facturas,
					'reembolsos': reembolsos,
					'sumReemMontosSolic': sumReemMontosSolic,
					'sumReemMontosAprob': sumReemMontosAprob,
					'sumCMMontosCobr': sumCMMontosCobr,
					'sumCMMontosAprob': sumCMMontosAprob,
					'sumFactMontoAprob': sumFactMontoAprob,
					'reca': reca,
					'relas': relas,
					'allas': allas,
					'almes': almes,
					'docsDIAT': docsDIAT,
					'docsDIEP': docsDIEP,
					'siniestro': s]
			}
		}
		if (params?.siniestroControllerHome != null){
			model.put('siniestroControllerHome', params?.siniestroControllerHome)
		}
		return (['next': [action:'dp03'], model: model])
	}

	def volver(params){
		def next
		if (params?.siniestroControllerHome?.equals("BIS_Ingreso"))
		{
			log.info "Volviendo a 77 bis ->"+params
			def bis = Bis.findById(params?.bisId)
			def siniestros = BISIngresoService.buscaSiniestros(bis)

			//Rescatamos los siniestros again
			next = [next: [controller: 'BIS_ingreso', action: 'dp02'], model: [bis_id: bis.id]]
		}
		if (params?.siniestroControllerHome?.equals("OTP_ingreso"))
		{
			log.info "Volviendo a OTP_ingreso ->"+params
			//Rescatamos los siniestros again
			next = [next: [controller: 'OTP_ingreso', action: 'dp03'], model: [runTrabajador: params?.runTrabajador]]
		}
		return next
	}

	//Accesos a DATOS

	//Busca siniestro previo
	def findSiniestrosPrevios(fechaSiniestro,empleador,trabajador){
		def formatDateTimeFunction = (new DataSourceHelper()).formatDatetimeFunction();
		def siniestrosPrevios = Siniestro.executeQuery(
				"select s "+
				"  from Siniestro as s "+
				" where "+formatDateTimeFunction+"(s.fecha, 'dd-MM-yyyy')=?"+
				"   and s.empleador=?"+
				"   and s.trabajador=?",[new java.text.SimpleDateFormat("dd-MM-yyyy").format(fechaSiniestro),
					empleador,trabajador])

		return siniestrosPrevios
	}

	// Busca siniestro a partir de la cuenta medica
	def findSiniestroByCuentaMedica(cuentaMedica) {
		for (String it : cuentaMedica.opas) {
			def opa = OPA.findById(it)
			if (opa) { return opa.siniestro }
		}
		for (String it : cuentaMedica.opaeps) {
			def opaep = OPAEP.findById(it)
			if (opaep) { return opaep.siniestro }
		}
		for (String it : cuentaMedica.odas) {
			def oda = ODA.findById(it)
			if (oda) { return oda.siniestro }
		}
	}


	def genALLAPDF(params){
		def alla = Alla.findById(params.allaId)
		try {
			def allaPDF = SeguimientoService.genALLAPDF(alla)
			return(allaPDF)
		}
		catch(Exception e)
		{
			alla.errors.rejectvalue('alla', 'genPDF', e.toString())
			return ([model: ['alla': alla]])
		}
	}

	def genALMEPDF(params){
		def alme = Alme.findById(params.almeId)
		try {
			def almePDF = SeguimientoService.genALMEPDF(alme)
			return(almePDF)
		}
		catch(Exception e)
		{
			alme.errors.rejectvalue('alme', 'genPDF', e.toString())
			return ([model: ['alme': alme]])
		}
	}


	def genRELAPDF(params){
		def rela = Rela.findById(params.relaId)

		try {
			def relaPDF = SeguimientoService.genRELAPDF(rela)
			return(relaPDF)
		}
		catch(Exception e)
		{
			rela.errors.rejectvalue('rela', 'genPDF', e.toString())
			return ([model: ['rela': rela]])
		}
	}


}
