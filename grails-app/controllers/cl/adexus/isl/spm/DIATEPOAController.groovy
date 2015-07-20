package cl.adexus.isl.spm

import javax.xml.ws.handler.LogicalHandler;

import grails.converters.JSON

import org.apache.shiro.SecurityUtils 

 class DIATEPOAController {


	def DIATEPOAService
	
    def index() { redirect ([action: 'r01'])}
	
	/**
	 * Prueba para test unitario
	 * 
	 * */
	def showMessage(){
		render "Hola mundo"
	}
	
	/**
	 * Prueba para test unitario
	 * 
	 */
	def redirectExample(){
		log.info "Redirigiendo a accion r01"
		redirect (action:'r01')	
	}
	
	/**
	 * Busca los siniestros que no tienen Denuncia OA
	 * 
	 */	
	def r01(){
		//si venimos de un error de denunciante
		if (params?.model?.error) flash.mensajes = params.model.error
		
		//usuario
		def username=SecurityUtils.subject?.principal
		//Llamamos al servicio
		def r = DIATEPOAService.r01(username)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}
	
	/**
	 * Formulario
	 * 	- Lista de Siniestros para elegir 
	 *  - [Siguiente] -> r02()
	 */
	def dp01(){
		params.get('model')
	}
	
	/**
	 * Arma la denuncia propuesta para el siniestro elegido
	 *
	 */
	def r02(){
		//Llamamos al servicio
		log.info("Ejecutando action r02")
		log.info("Datos recibidos : $params")
		def r = DIATEPOAService.r02(params)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		
		log.info("next.params : ${params?.model?.diatOAPropuesta}")
		
		forward (next)
	}
	
	/**
	 * Entrega las alternativas para una variable y en que tipo de DIAT/DIEP estaba
	 * Retorna un JSON
	 *
	 */
	def alternativasJson(){
		//Llamamos al servicio
		def r = DIATEPOAService.alternativas(params)
		for(int i = 0; i < r.size(); i++){
			if (r[i].nombre == 'horaIngreso' || r[i].nombre == 'fechaAccidente_hora' || r[i].nombre == 'horaSalida') {
				if (r[i].valor != null ){ 
					def dt = r[i].valor
					r[i].valor = cl.adexus.helpers.FormatosHelper.horaCortaStatic(new Date(dt.getTime()))
				}
			
			}

		  }
		render r as JSON
		
	} 
	
	/**
	 * Formulario
	 * DIEP OA propuesta
	 *  - [Siguiente] -> r03()
	 */
	def dp02ep(){
		log.info("Ejecutando action dp02ep")
		log.info("Datos recibidos : $params")
		def tiposAccidenteTrayecto=TipoAccidenteTrayecto.list()
		def tipoCalles=TipoCalle.list()
		def comunas=Comuna.listOrderByDescripcion()
		def tipoPropiedades=TipoPropiedadEmpresa.list()
		def tipoEmpresas=TipoEmpresa.list()
		def naciones = Nacion.list()
		def etnias = Etnia.list()
		def tipoDuracionContratos = TipoDuracionContrato.list()
		def tipoRemuneraciones = TipoRemuneracion.list()
		def categoriaOcupaciones = CategoriaOcupacion.list()
		def criterioGravedades=CriterioGravedad.list()
		def tipoMedioPruebaAccidentes=TipoMedioPruebaAccidente.list()
		def calificaciones=CalificacionDenunciante.list()
		def sexos=[[codigo:'F', descripcion:"Femenino"], [codigo:'M', descripcion:"Masculino"]]
		def esAntecedenteCompaneroList = [[codigo:true, descripcion:"Si"], [codigo:false, descripcion:"No"]]
		def esAntecedentePrevioList = [[codigo:true, descripcion:"Si"], [codigo:false, descripcion:"No"]]
		def tipoActividadEconomica = TipoActividadEconomica.listOrderByCodigo()
		def tipoAccidentes = [['codigo':'1','descripcion':'Trabajo'],['codigo':'2','descripcion':'Trayecto']]
		def trabajador = params.model?.trabajador
		def t = params.model?.t
		def denunciante = params.model?.denunciante
		def siniestro = params.model?.siniestro
		def diepOA = params.model?.diepOA
		def sistemaSalud = SistemaSalud.listOrderByCodigo()
		
		
		//Pasamos los datos al view
		def dixx=[:]
		dixx['empleador_rut'] = params.model.diepOAPropuesta?.empleador?.rut
		dixx['trabajador_run'] = params.model.diepOAPropuesta?.trabajador?.run
		log.info 'dixx: ' + dixx
		session['dixx']=dixx;
		def model=[comunas: comunas,
			tipoCalles: tipoCalles,
			tipoPropiedades: tipoPropiedades,
			tipoEmpresas: tipoEmpresas,
			naciones: naciones,
			etnias: etnias,
			tipoDuracionContratos: tipoDuracionContratos,
			tipoRemuneraciones: tipoRemuneraciones,
			categoriaOcupaciones: categoriaOcupaciones,
			tipoMedioPruebaAccidentes: tipoMedioPruebaAccidentes,
			calificaciones: calificaciones,
			sexos: sexos,
			criterioGravedades:criterioGravedades,
			tiposAccidenteTrayecto: tiposAccidenteTrayecto,
			esAntecedenteCompaneroList:esAntecedenteCompaneroList,
			esAntecedentePrevioList: esAntecedentePrevioList,
			tipoActividadEconomica: tipoActividadEconomica,
			trabajador:trabajador,
			t:t,
			denunciante:denunciante,
			siniestro:siniestro,
			diepOA:diepOA,
			sistemaSalud: sistemaSalud
			]
		if(params.get('model')){
			model + params.get('model')
		}else{
			model
		}
		
		
		
	}
	
	/**
	 * Formulario
	 * DIAT OA propuesta
	 *  - [Siguiente] -> r03()
	 */
	def dp02at(){
		log.info("Ejecutando accion dp02At")
		log.info("params : $params")
		// Combos y que el ancho de banda se apiade de nosotros.
		def tiposAccidenteTrayecto=TipoAccidenteTrayecto.list()
		def tipoCalles=TipoCalle.list()
		def comunas=Comuna.listOrderByDescripcion()
		def tipoPropiedades=TipoPropiedadEmpresa.list()
		def tipoEmpresas=TipoEmpresa.list()
		def naciones = Nacion.list()
		def etnias = Etnia.list()
		def tipoDuracionContratos = TipoDuracionContrato.list()
		def tipoRemuneraciones = TipoRemuneracion.list()
		def categoriaOcupaciones = CategoriaOcupacion.list()
		def criterioGravedades=CriterioGravedad.list()
		def tipoMedioPruebaAccidentes=TipoMedioPruebaAccidente.list()
		def calificaciones=CalificacionDenunciante.list()
		def sexos=[[codigo:'F', descripcion:"Femenino"], [codigo:'M', descripcion:"Masculino"]]
		def esTrabajoHabitualList = [[codigo:true, descripcion:"Si"], [codigo:false, descripcion:"No"]]
		def tipoActividadEconomica = TipoActividadEconomica.listOrderByCodigo()
		def tipoAccidentes = [['codigo':'1','descripcion':'Trabajo'],['codigo':'2','descripcion':'Trayecto']]
		def trabajador = params?.model?.trabajador
		def t = params?.model?.t
		def denunciante = params?.model?.denunciante
		def diatOA = params?.model?.diatOA
		def siniestro = params?.model?.siniestro
		def sistemaSalud = SistemaSalud.listOrderByCodigo()
		
		
		//Pasamos los datos al view
		def dixx=[:]
		dixx['empleador_rut'] = params.model.diatOAPropuesta?.empleador?.rut
		dixx['trabajador_run'] = params.model.diatOAPropuesta?.trabajador?.run
		log.info 'dixx: ' + dixx
		session['dixx']=dixx;
		def model=[comunas: comunas,
			tipoCalles: tipoCalles,
			tipoPropiedades: tipoPropiedades,
			tipoEmpresas: tipoEmpresas,
			naciones: naciones,
			etnias: etnias,
			tipoDuracionContratos: tipoDuracionContratos,
			tipoRemuneraciones: tipoRemuneraciones,
			categoriaOcupaciones: categoriaOcupaciones,
			tipoMedioPruebaAccidentes: tipoMedioPruebaAccidentes,
			calificaciones: calificaciones,
			sexos: sexos,
			tipoAccidentes: tipoAccidentes,
			criterioGravedades:criterioGravedades,
			tiposAccidenteTrayecto: tiposAccidenteTrayecto,
			esTrabajoHabitualList:esTrabajoHabitualList,
			tipoActividadEconomica: tipoActividadEconomica,
			diatOAPropuesta:params?.model?.diatOAPropuesta,
			trabajador:trabajador, t:t,
			denunciante:denunciante,
			diatOA:diatOA,
			siniestro:siniestro,
			sistemaSalud:sistemaSalud]
		if(params.get('model')){
			model + params.get('model')
		}else{
			model
		}
		
		
		render (view:"dp02at", model:model)
	}

	/**
	 * 
	 * @return
	 */
	def r03(){
		//Llamamos al servicio para guardar la DIAT OA
		def username=SecurityUtils.subject?.principal
		params.username=username
		def r = DIATEPOAService.r03(params, session['dixx'], true)
		params.put('model', r.get('model'))
		def next = r.next
		
		//momentaneamente se incluirá el mensaje por defecto
		flash.message = "cl.adexus.isl.spm.diatoaep.save.sucess"
		flash.args = []
		flash.default = next?.mensaje

		
		next.params = params
		
		forward (next)
	}
	
	def r03Temp() {
		//Llamamos al servicio para guardar la DIAT OA
		def username=SecurityUtils.subject?.principal
		params.username=username
		def r = DIATEPOAService.r03(params, session['dixx'], false)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}
}
