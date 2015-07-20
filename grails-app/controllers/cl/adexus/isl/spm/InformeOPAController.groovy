package cl.adexus.isl.spm

import cl.adexus.helpers.FormatosHelper
import cl.adexus.helpers.FechaHoraHelper
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import grails.converters.JSON

import org.apache.shiro.SecurityUtils

import groovy.time.TimeCategory
import groovy.time.TimeDuration
import groovy.sql.Sql


import javax.servlet.ServletOutputStream


class InformeOPAController {
	def dataSource
	def DiagnosticoService
	def informeOPAService
	def CalOrigenService
	def pass='1234'

	def index() {
		redirect(action: "dp01")
		return
	}
	
	//Busca y despliega
	def dp01() {
		
		log.info "PARAMETROS DP01 INFORME OPA -> "+params
		session['diagnosticos'] = []
		session['informeOpa'] = null
			
		params.runPaciente 		= params.runPaciente ? ((String)params.runPaciente).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim() : params.runPaciente
		
		//Entrada de datos
		def nSiniestro			= "${params.nSiniestro == null ? '' : params.nSiniestro}"
		def prestador			= "${(params.prestador == null || params.prestador == '0')? '' : params.prestador}"
		def runPaciente			= "${params.runPaciente == null ? '' : params.runPaciente}"
		def centroAtencion		= "${(params.centroAtencion == null || params.centroAtencion == '0')? '' : params.centroAtencion}"
		def informeEstado		= "${params.informeEstado == null ? '' : params.informeEstado}"

		//Super query!
		def query = " SELECT sine " +
					" FROM	Siniestro sine " +
					" WHERE	(sine.opa IS NOT NULL OR sine.opaep IS NOT NULL) "
		if (runPaciente !='')
			query += " AND sine.trabajador.run = '${runPaciente}' "

		if (centroAtencion !='')
			query += " AND ((sine.id IN (select sine1.id from Siniestro sine1 where sine1.opa.centroAtencion.id = '${centroAtencion}'))" +
					 " OR (sine.id IN (select sine2.id from Siniestro sine2 where sine.opaep.centroAtencion.id = '${centroAtencion}' ))) "
			
		if (prestador !='')
			query += " AND ((sine.id IN (select sine1.id from Siniestro sine1 where sine1.opa.centroAtencion.prestador.id = '${prestador}'))" +
					 " OR (sine.id IN (select sine2.id from Siniestro sine2 where sine.opaep.centroAtencion.prestador.id = '${prestador}' ))) "
	
		if (nSiniestro !='')
			query += " AND sine.id = ${nSiniestro} "
			   
		if(informeEstado == '1')
			query += " AND sine.id = (SELECT informe.siniestro.id FROM InformeOPA informe WHERE sine.id = informe.siniestro.id) "
		else if (informeEstado == '2')
			query += " AND sine.id NOT IN (SELECT informe.siniestro.id FROM InformeOPA informe) "
		
		println query
		
		def listaInformes	= Siniestro.executeQuery(query)
		def informesOPA = []
		def existe
		
		listaInformes.each{
			existe = InformeOPA.findBySiniestro(Siniestro.get(it.id)) ? true : false
				informesOPA.add( 	id							: it.id,
									trabajador					: it.trabajador,
									esEnfermedadProfesional		: it.esEnfermedadProfesional,
									opa							: it.opa,
									opaep						: it.opaep,
									estado						: existe)	
		}
		
		//Combo estado
		def estado = [['codigo': 2, 'descripcion':'Pendiente'],['codigo': 1, 'descripcion':'Ingresado']]
		
		//Carga combo centro de salud
		def cS = CentroSalud.listOrderByNombre()
		def centroS = []
		cS.each {
			centroS.add([
				'codigo'        : it.id,
				'descripcion'	: it.nombre
				])
		}
		
		//Query Prestador
		def prestadorList	= new ArrayList()
		def sql = new Sql(dataSource)
		def sqlQuery =  " SELECT pres.id " +
						" FROM	Prestador pres, Persona_Natural pena " +
						" WHERE	pres.PERSONA_NATURAL_ID	= pena.RUN " +
						" UNION " +
						" SELECT pres.id " +
						" FROM	Prestador pres, Persona_Juridica peju " +
						" WHERE	pres.PERSONA_JURIDICA_ID = peju.RUT "
						
		def rows		= sql.rows(sqlQuery)
		rows.each { row ->
			prestadorList.add(Prestador.get(row.id))
		
		}
		
		//Persona Jurídica o Natural
		def pre = []
		prestadorList.each{
			if(it.esPersonaJuridica){
				pre.add([
					'codigo' 		: it.id,
					'descripcion' 	: it.personaJuridica.razonSocial
				])
			}else{
				pre.add([
					'codigo' 		: it.id,
					'descripcion' 	: it.personaNatural.nombre+' '+it.personaNatural.apellidoPaterno+' '+it.personaNatural.apellidoMaterno
				])
			}
		}
		
		def model = [ informesOPA		: informesOPA
					, Cprestador		: params?.prestador
					, runPaciente		: params?.runPaciente
					, nSiniestro		: params?.nSiniestro
					, cAtencion			: params?.centroAtencion
					, informeEstado		: params?.informeEstado
					, estado			: estado
					, centroS			: centroS
					, pre 				: pre
			]
		
		//Modelo
		model

	}
	
	//ver informe creado
	def dp02() {
		log.info "Parametros DP02 INFORME OPA ->"+params

		def siniestro    = Siniestro.findById(params.siniestroId)
		def informeOpa = InformeOPA.findBySiniestro(siniestro)
		def origen = OrigenDiagnostico.findByCodigo(2)
		def diagnosticos = Diagnostico.findAllBySiniestroAndOrigen(siniestro, origen)

		//Prestador
		def opx =  siniestro?.opaep? siniestro?.opaep : siniestro?.opa
		def prestadorOPA = FormatosISLHelper.nombrePrestadorStatic(opx?.centroAtencion?.prestador)
		
		//Centro atención
		def centroMedicoOPA = opx?.centroAtencion?.nombre
		
		//Model para la vista
		def model = [
			informeOpa: 		informeOpa,
			diagnosticos: 		diagnosticos,
			prestadorOPA:		prestadorOPA,
			centroMedicoOPA:	centroMedicoOPA
			]
		
		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}
		render(view: 'dp02', model: model)
		
	}
	
	//crea informe
	def dp03() {
		log.info "Parametros DP03 INFORME OPA ->"+params
		
		def siniestro = Siniestro.findById(params.siniestroId)	
		def diagnosticos = []
		def informeOpa
		
		if (params?.model?.diagnostico){
			//Verifica si vienen diagnosticos desde la actualizacion, si es asi, lo actualiza en la lista
			if (params?.model?.updateDiagnostico)
				session['diagnosticos'].set(params?.model?.updateDiagnostico.toInteger(), params?.model?.diagnostico)
			else
				session['diagnosticos'] += params?.model?.diagnostico
		}	
		
		//Guarda los diagnosticos de la session en una variable local
		for (i in session['diagnosticos']){
			diagnosticos.add(i)
		}	
		
		if (session['informeOpa']!= null){
			log.info "Informe OPA en espera ->"+session['informeOpa']
			informeOpa = session['informeOpa']
		}
		
		//Prestador
		def opx =  siniestro?.opaep? siniestro?.opaep : siniestro?.opa
		def prestadorOPA = FormatosISLHelper.nombrePrestadorStatic(opx?.centroAtencion?.prestador)
		
		//Centro atención
		def centroMedicoOPA = opx?.centroAtencion?.nombre
		
		def model = [	siniestro		: 	siniestro,
						diagnosticos	: 	diagnosticos,
						prestadorOPA	: 	prestadorOPA,
						centroMedicoOPA	:	centroMedicoOPA,
						informeOpa		:	informeOpa	]
		
		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}
		
		render(view: 'dp03', model: model)
		
	}

	//Post dp03, guarda el informe OPA
	def postDp03(){
		log.info "Guarda Informe OPA ->"+params
		
		//Recupero los datos de dp03
		def informe = informeOPAService.informeEnEspera(params)
		session['informeOpa'] = informe
		
		//Guardar el informe OPA
		def r = informeOPAService.guardaInforme(params, session['diagnosticos'])
		
		//Manejo de errores 
		switch (r.error) {
			case 1:
			flash.mensajes = "La fecha del próximo control debe ser posterior a la fecha de atención"
			break
			case 2:
			flash.mensajes = "El informe OPA debe tener al menos un diagnóstico asociado"
			break
			case 3:
			flash.mensajes = "El RUN del médico es inválido"
			break
			case 4:
			flash.mensajes = "La fecha de atención debe estar entre la fecha de inicio de vigencia y el término de la OPA/OPAEP"
			break
			case 5:
			flash.mensajes = "La fecha de atención debe ser menor a la fecha actual"
			break
		}
		if (r.error < 1){
			for (i in session['diagnosticos']){
				def d = DiagnosticoService.guardaDiagnosticosNP(i, params?.siniestroId)
			}
		}
		
		log.info "Redirigiendo a ->"+r.next
		params.put('model', r.get('model'))
		forward (action: r.get('next').action, params: params)
	}
		
	//Agregar diagnostico
	def dp04() {
		log.info("dp04::params:"+params)
		
		def siniestro    = Siniestro.findById(params.siniestroId)
								
		//Calculo dias restantes
		def diasR = 15- FechaHoraHelper.diffDates(siniestro?.creadoEl)
		
		//LLenado combos
		def parte = CodigoUbicacionLesion.listOrderByDescripcion()
		def lateralidad = TipoLateralidad.listOrderByCodigo()
		def origenDiag = OrigenDiagnostico.findByCodigo('2')
					
		//Model
		def model = [	siniestro:  siniestro,
						diasR:		diasR,
						parte:		parte,
						lateralidad:lateralidad,
						origenDiag:	origenDiag		]
		
		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}else{
			//Recupero los datos de dp03
			def informe = informeOPAService.informeEnEspera(params)
			session['informeOpa'] = informe
		}
		
		render(view: 'dp04', model: model)	
	}
	
	//Editar Diagnóstico
	def dp05() {	
		log.info("dp05::params:"+params)
		
		if (params.model) {
			params.siniestroId = params.model.siniestroId
			params.backTo = params.model.backTo
			params.backToController = params.model.backToController
			params.numeroDiagnostico = params.model.numeroDiagnostico
		}
			
		def siniestro    = Siniestro.findById(params?.siniestroId)
		def diagnostico = session['diagnosticos'].get(params?.numeroDiagnostico.toInteger())

		//Calculo dias restantes
		def diasR = 15- FechaHoraHelper.diffDates(siniestro?.creadoEl)
		
		//LLenado combos
		def parte = CodigoUbicacionLesion.listOrderByDescripcion()
		def lateralidad = TipoLateralidad.listOrderByCodigo()
		def origenDiag = OrigenDiagnostico.findByCodigo('2')
		
		//Model
		def model = [
			siniestro: 	 		siniestro,
			diasR:				diasR,
			parte:				parte,
			lateralidad: 		lateralidad,
			origenDiag:			origenDiag,
			diagnostico: 		diagnostico,
			numeroDiagnostico: 	params?.numeroDiagnostico
		]
		
		if (params.get("model")) {
			// Si venimos de una redireccion por error
			model += params.get("model")
		}else{
			//Recupero los datos de dp03
			def informe = informeOPAService.informeEnEspera(params)
			session['informeOpa'] = informe
		}
		
		render(view: 'dp05', model: model)	
	}
	
	//llama al service para guardar el diagnostico
	def save_dg(){
		def r = DiagnosticoService.saveDiagnosticoNP(params)
		if (r.error > 0) {
			 flash.mensajes = 'La Fecha del diagnóstico debe ser mayor a la fecha del siniestro'
		}else{
			flash.mensajes = null
			//session['diagnosticos'] = r.diagnostico
		}		
		params.put('model', r.get('model'))
		forward (action: r.get('next').action, params: params)
	}
	
	//llama al service para actualizar un diagnostico
	def update_dg(){
		def r = DiagnosticoService.updateDiagnosticoNP(params)
		if (r.error > 0) {
			flash.mensajes = 'La Fecha del diagnóstico debe mayor a la fecha del siniestro'
		}else{
			flash.mensajes = null
		}
		params.put('model', r.get('model'))
		forward (action: r.get('next').action, params: params)	
	}
	
	//vuelve a flujo anterior
	def go_back(){
		def r = DiagnosticoService.goBack(params)
		params.put('model', r.get('model'))
		forward (action: r.next.action, params:params)
	}
	
	/********************************************* AJAX *********************************************/
	
	def datosCIE10JSON () {
		String cie = params.codigos.toString().toUpperCase();
		log.info "Código CIE-10: "+cie
		def datosCIE10 = CalOrigenService.getCIE10(cie)
		JSON.use("deep") { render datosCIE10 as JSON }
	}
	
	def centroSaludPorPrestadorJSON(){
		
		def prestadorId = Long.parseLong(params.prestadorId)		
		def regionId=params.regionId
		
		//Llamamos al servicio
		def cs = CentroSalud.executeQuery(
				"SELECT DISTINCT cs " +
				"FROM	CentroSalud cs " +
				"WHERE	cs.prestador.id = :prestadorId " 
				, [ prestadorId	: prestadorId]); 
		JSON.use("deep"){ render cs as JSON }
	}
		
}
