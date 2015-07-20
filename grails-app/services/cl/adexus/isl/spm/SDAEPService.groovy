package cl.adexus.isl.spm

import java.util.Date;

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.isl.spm.domain.CuestionarioTrabajoPDF
import cl.adexus.isl.spm.domain.CuestionarioTrayectoPDF
import cl.adexus.isl.spm.domain.DietPDF
import cl.adexus.isl.spm.domain.OpaepPDF
import cl.adexus.isl.spm.domain.Constantes
import cl.adexus.isl.spm.enums.RetornoWSEnum

import org.apache.shiro.SecurityUtils

class SDAEPService {

	def SGAService
	def DroolsService
	def PDFService
	def SUSESOService
	def DenunciaService
	def BISIngresoService

	// ------------- COMUNES --------------
	def excepcionar(params, SDAEP sdaep, contexto){

		def excepcion = new Excepcion(params)
		excepcion.contexto=contexto
		excepcion.solicitudEP=sdaep
		if(!excepcion.validate()){
			//Hay algo malo con los datos y no se puede crear excepcion
			def deDondeVengo
			if(contexto=='ident') {deDondeVengo='dp03'}
			if(contexto=='clatrab') {deDondeVengo='dp02'}
			return (['next':['action':deDondeVengo],  model: ['excepcion': excepcion]])
		}
		excepcion.save()

		//TODO: Avisarle a alguien que hubo una excepcion?
		if(contexto=='ident') { return (['next':['controller': 'SDAEP_clatrab', 'action':'r01']]) }
		if(contexto=='clatrab') { return (['next':['action':'dp03']]) }
	}


	def terminar(params, SDAEP sdaep, salida){

		//Cerramos el SDAEP
		sdaep.finSolicitud=new Date()
		sdaep.salida=salida
		sdaep.save(flush:true)

		//Muere el sdaep?
		sdaep=null

		//Terminamos el proceso de regularizacion y vamos a notificar solicitante (BIS_ingreso/dp04)
		if (params?.bisId){
			def bis = Bis.findById(params?.bisId)
			bis.motivoRechazo = salida
			bis.fechaRechazo = new Date()
			if (bis.save(flush: true)){
				def user = SecurityUtils.subject?.principal
				def pass = '1234'
				def r = BISIngresoService.rechazo(user,pass,bis.taskId)
				log.info 'Result complete task id: '+bis.taskId+' -> '+r
			}
			return (['next':['controller':'nav', 'action':'inbox']])
		}

		return (['next':['controller':'nav', 'action':'area2']])
	}

	// ------------- PREVIO --------------
	/**
	 * 	Determina si existen siniestros de enfermedad profesional
	 *  asociados al trabajador. Si existen va a dp02 para elegir
	 *  el siniestro previo. Si no existe es nuevo y va a SDAEP_ident::dp01
	 *
	 *  @params run
	 */
	def previoR01(params) {
		//Limpiamos el run
		params['run']=((String)params['run']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

		//Busca si existe el trabajador (run)
		def trabajador=PersonaNatural.findByRun(params['run'])
		if(!trabajador){
			params.nombre = ' '
			params.apellidoPaterno = ' '
			trabajador=new PersonaNatural(params)
			if(!trabajador.validate()){
				//Hay algo malo en los datos y no se puede crear el trabajador
				return (['next': [action:'dp01'],  model: ['trabajador': trabajador]])
			}
			trabajador.save()
		}

		//Inicializa el SDAEP
		def sdaep = new SDAEP(params)
		sdaep.trabajador=trabajador
		sdaep.inicioSolicitud = new Date()
		def username=SecurityUtils.subject?.principal
		sdaep.usuario=username

		if(!sdaep.validate()){
			//Hay algo malo con los datos y no se puede crear el SDAEP
			return (['next': [action:'dp01'],  model: ['trabajador': trabajador, 'sdaep': sdaep]])
		}

		sdaep.save()

		//Busca siniestros previos
		def siniestros=Siniestro.findAllByTrabajadorAndEsEnfermedadProfesional(trabajador,true)
		if(siniestros){
			//Hay siniestros previos
			return (['next': [action:'dp02'], model: ['sdaep': sdaep, 'siniestros': siniestros ]])
		}else{
			//No Hay siniestros previos
			return (['next': [controller: 'SDAEP_ident', action: 'r01'], model: ['sdaep': sdaep?.id ]])
		}
	}

	/**
	 *  Guarda el relato. Si eligio un siniestro guarda
	 *  los datos en el sdaep y se va a SDAEP_diep::dp01,
	 *  si no eligio siniestro se va a SDAEP_ident::dp01
	 *
	 *  @params relato
	 *  @params id (de siniestro, en blanco si es nuevo)
	 */
	def previoCU02(params,SDAEP sdaep){
		//Guarda el relato
		if (sdaep.trabajador!=null && !sdaep.trabajador.isAttached()) {
			sdaep.trabajador.attach()
		}

		if (sdaep.empleador!=null && !sdaep.empleador.isAttached()) {
			sdaep.empleador.attach()
		}

		sdaep.relato=params['relato']

		//Busca si viene un id de Siniestro
		if(params['id']){
			Siniestro s=Siniestro.get(params['id'])
			if(s){
				sdaep.empleador=s.empleador
				sdaep.fechaSintomas=s.fecha
				sdaep.siniestro=s
				sdaep.save()
			}
		}

		if(sdaep.empleador){
			//Listo todo directo al diep
			return (['next': [controller:'SDAEP_diep', action:'dp01']])
		}else{
			//No Hay siniestros previos
			return (['next': [controller: 'SDAEP_ident', action: 'r01'], model: ['sdaep': sdaep ]])
		}
	}

	// ------------- IDENT --------------

	/**
	 * 	Determina los empleadores del trabajador.
	 *  Si existen va a dp01 para elegir. Si no existen
	 *  va a dp02 para ingresar el RUT
	 *
	 *  @params run
	 *
	 */
	def identR01(params, SDAEP sdaep) {
		log.info "parametros ident R01->"+params

		//Busca los datos en el WS del SGA
		def trabajador = sdaep.trabajador
		def empresas = SGAService.consultaTrabajador(trabajador.run,new Date())

		//Si retorna algun dato va a dp01 si no a dp02
		def next
		log.info("identR01: empresas: " + empresas.size())
		if( empresas.size() != 0 && !params?.bisId){
			next="dp01"
		}else{
			if (params?.bisId){
				next="r02"
			}else{
				next="dp02"
			}
		}

		//Retorna las empresas, la proxima pagina y el SDAAT inicializado
		log.info("identR01: next->" + next)
		def r=['next': [action: next], model: ['empresas': empresas ]]
		return (r)
	}

	/**
	 * Busca el rut de empleador. Si es ISL va a SDAEP_clatrab::r01
	 * Si no eligio rut o el RUT no es ISL va a dp02 para ingresar
	 * rut de empleador
	 *
	 *  @params rut (de empleador, blanco si es otro
	 */
	def identR02(params,SDAEP sdaep){
		//Limpiamos el rut
		params['rut']=((String)params['rut']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

		//Busca si existe el empleador (por rut)
		def empleador=PersonaJuridica.findByRut(params['rut'])
		if(!empleador){
			params.razonSocial = ' '
			empleador=new PersonaJuridica(params)
			if(!empleador.validate()){
				//Hay algo malo en los datos y no se puede crear el empleador
				empleador.rut = null
				return (['next': [action: 'dp02'],  model: ['empleador': empleador]])
			}
			empleador.save()
		}

		//Busca los datos en el el WS del SGA
		def empresa=SGAService.consultaEmpresa(empleador.rut,new Date())

		//Guarda los datos de la empresa en la solicitud
		sdaep.empleador=empleador
		sdaep.save()

		log.info 'empresa SGA: ' + empresa
		if(empresa != null){
			//Actualizamos los datos de la empresa (OJOPIOJO: Si el WS retorna otro nombre para ese rut se cambiara
			empleador.razonSocial=empresa['nombre']
			empleador.save()

			sdaep.codigoActividadEmpresa=empresa['codActEmp']

			//Revisamos si la empresa es ISL
			if(empresa['seguroLaboral'].equals('ISL')){
				//Estamos listos. Vamos a a
				return (['next':  [controller: 'SDAEP_clatrab', action: 'r01']])
			}
		}

		//Si llegamos aca, la empresa o no existe o no es ISL
		//Solo podemos ir al termino con excepcion
		return (['next':  [action: 'dp03']])
	}

	// ------------- CLATRAB --------------

	/**
	 * 	Determina si la empresa tiene solo empleados
	 *  Si es asi, derecho a la SDAEP_diep::dp01
	 *  Si no es asi hay que ir al cuestionario en dp01
	 *
	 */
	def clatrabR01(params,SDAEP sdaep){
		//Detecta si es empleado. CIU de la Empresa
		def esEmpleado=DroolsService.esEmpleado(sdaep.codigoActividadEmpresa)
		if(esEmpleado){
			// Si es empleado va al dp01 de diep
			return ([next: [action: 'dp03']])
		}else{
			// Si no es empleado va al dp01 (Cuestionario Obrero)
			// Busca los actividades  para el Cuestionario Obrero
			def acts=ActividadTrabajador.findAllByCodigoNotInList(["OE", "OO"])
			return ([next: [action: 'dp01'], model:['actividadesTrabajador':acts]])
		}
	}

	/**
	 * 	Procesa el cuestionario de Obrero
	 *  para determinar si es empleado.
	 *  Si es asi, a dp03 (Calificacion de Origen reducida)
	 *  Si no es asi a dp02 que termina o excepciona
	 *
	 */
	def clatrabR02(params,SDAEP sdaep){
		//Guarda los datos del cuestionario
		def cuestionario=sdaep.cuestionarioObrero
		if(!cuestionario){
			cuestionario=new CuestionarioObrero()
		}
		//Buscamos el codigo
		def actividadTrabajador = ActividadTrabajador.findByCodigo(params['codigo'])
		if(!actividadTrabajador){
			//Codigo de actividad no existe. Devuelta al cuestionario
			actividadTrabajador = new ActividadTrabajador()
			actividadTrabajador.errors.reject('cl.adexus.isl.spm.CuestionarioObrero.actividadTrabajador.selected')
			return ([next: [action:'dp01'], model: ['actividadTrabajador': actividadTrabajador]])
		}
		cuestionario.actividadTrabajador=actividadTrabajador

		//Vemos si es de tipo "OTRO"
		if(params['codigo']=='OO'){
			cuestionario.otro=params['otroObrero']
		}
		if(params['codigo']=='OE'){
			cuestionario.otro=params['otroEmpleado']
		}

		//validar antes de guardar y si no valida devolver al formulario
		if (!cuestionario.validate()) {
			//Hay algo malo en los datos y no se puede guardar el cuestionario
			return (['next': [action:'dp01'],  model: ['cuestionarioObrero': cuestionario]])
		}
		cuestionario.save(failOnError: true)
		//Lo agregamos a la solicitud
		sdaep.cuestionarioObrero=cuestionario
		sdaep.save(failOnError: true)

		//Vemos si es obrero
		def esObrero=(cuestionario.actividadTrabajador.codigo!='OE')
		if(esObrero){
			return ([next: [controller:'SDAEP_clatrab',action:'dp02']]) //Excepcion
		}else{
			return ([next: [controller: 'SDAEP_clatrab', action: 'dp03']]) //Calorigen encubierto
		}
	}

	/**
	 * Guarda Cuestionario Origen
	 *
	 *  @params
	 */
	def clatrabCU03(params,SDAEP sdaep){
		//Guarda los datos del cuestionario

		def cuestionario=sdaep.cuestionarioOrigen
		if(!cuestionario){
			cuestionario=new CuestionarioCalificacionOrigenEnfermedadProfesional(params)
		}

		//Buscamos el codigo
		def tipoEnfermedad = TipoEnfermedad.findByCodigo(params['codigo'])
		if(!tipoEnfermedad){
			//Codigo de tipo enfermedad no existe. Devuelta al cuestionario
			return ([next: [action:'dp03', model: ['cuestionarioOrigen': cuestionario]]])
		}
		cuestionario.tipoEnfermedad=tipoEnfermedad
		if(!cuestionario.validate()){
			return ([next: [action:'dp03', model: ['cuestionarioOrigen': cuestionario]]])
		}
		cuestionario.save(failOnError: true)

		//Lo agregamos a la solicitud
		sdaep.cuestionarioOrigen=cuestionario
		sdaep.save(failOnError: true)

		return ([next: [controller: 'SDAEP_diep', action:'dp01']])
	}

	// ------------ DIEP ----------

	def diepR01(params,SDAEP sdaep){
		if(params['tipoDenunciante']=='2'){ //Trabajador
			params['run']=sdaep.trabajador.run
		}

		//Limpiamos el run
		params['run']=((String)params['run']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

		sdaep.tipoDenunciante=CalificacionDenunciante.findByCodigo(params['tipoDenunciante'])

		//Busca si existe el denunciante (run)
		def denunciante=PersonaNatural.findByRun(params['run'])
		if(!denunciante){
			params.nombre = ""
			params.apellidoPaterno = ""
			denunciante=new PersonaNatural(params)
			if(!denunciante.validate()){
				//Hay algo malo en los datos y no se puede crear el denunciante
				if (sdaep.trabajador!=null && !sdaep.trabajador.isAttached()) {
					sdaep.trabajador.attach()
				}

				if (sdaep.empleador!=null && !sdaep.empleador.isAttached()) {
					sdaep.empleador.attach()
				}


				return ([next:  [action:'dp01'],  model: ['denunciante': denunciante, 'sdaep': sdaep]])
			}
			denunciante.save()
		}
		sdaep.denunciante=denunciante
		sdaep.save()

		//Busca denuncia previa para ese denunciante
		def diepPrevias = DenunciaService.diepPrevias(sdaep?.siniestro,sdaep.empleador,sdaep.trabajador,sdaep.tipoDenunciante);
		if(!diepPrevias){
			return ([next: [action:'dp02']])
		}else{
			sdaep.diep=diepPrevias[0]
			sdaep.diep.siniestro  //Solo para hacer el lazyLoading (supongo que hay una forma mas enchulada, pero tengo sue���o)
			return ([next: [action:'dp05']]) //Informacion adicional
		}
	}

	def diepCU02(params,SDAEP sdaep){
		log.info "Ejecutando metodo diepCU02"
		log.info "Datos recibidos : SDAEP : $sdaep, $params"

		params.empleador=PersonaJuridica.findByRut(params.empleador_rut)
		params.empleador.razonSocial=params.empleador_razonSocial

		params.direccionEmpleadorTipoCalle=TipoCalle.findByCodigo(params.direccionEmpleadorTipoCalle)
		params.direccionEmpleadorComuna=Comuna.findByCodigo(params.direccionEmpleadorComuna)
		params.propiedadEmpresa=TipoPropiedadEmpresa.findByCodigo(params.propiedadEmpresa)
		params.tipoEmpresa=TipoEmpresa.findByCodigo(params.tipoEmpresa)

		params.trabajador=PersonaNatural.findByRun(params.trabajador_run)
		params.trabajador.nombre=params.trabajador_nombre
		params.trabajador.apellidoPaterno=params.trabajador_apellidoPaterno
		params.trabajador.apellidoMaterno=params.trabajador_apellidoMaterno
		params.trabajador.sexo=params.trabajador_sexo

		if(sdaep.tipoDenunciante.codigo == '2'){ //Trabajador
			sdaep.denunciante.nombre=params.trabajador_nombre
			sdaep.denunciante.apellidoPaterno=params.trabajador_apellidoPaterno
			sdaep.denunciante.apellidoMaterno=params.trabajador_apellidoMaterno
			sdaep.denunciante.apellidoMaterno=params.trabajador_apellidoMaterno
		}
		if (params.trabajador_fechaNacimiento instanceof String)
			params.trabajador_fechaNacimiento = FechaHoraHelper.stringToDate(params.trabajador_fechaNacimiento)

		params.trabajador.fechaNacimiento = params.trabajador_fechaNacimiento

		if (params.fechaIngresoEmpresa instanceof String && !"".equals(params.fechaIngresoEmpresa))
			params.fechaIngresoEmpresa = FechaHoraHelper.stringToDate(params.fechaIngresoEmpresa)

		params.nacionalidadTrabajador=Nacion.findByCodigo(params.nacionalidadTrabajador)
		params.direccionTrabajadorTipoCalle=TipoCalle.findByCodigo(params.direccionTrabajadorTipoCalle)
		params.direccionTrabajadorComuna=Comuna.findByCodigo(params.direccionTrabajadorComuna)
		params.etnia=Etnia.findByCodigo(params.etnia)
		params.duracionContrato=TipoDuracionContrato.findByCodigo(params.duracionContrato)
		params.tipoRemuneracion=TipoRemuneracion.findByCodigo(params.tipoRemuneracion)
		params.categoriaOcupacion=CategoriaOcupacion.findByCodigo(params.categoriaOcupacion)
		if (params.ciiuPrincipal)
			params.ciiuPrincipal=TipoActividadEconomica.findByCodigo(params.ciiuPrincipal)
		if (params.ciiuEmpleador)
			params.ciiuEmpleador=TipoActividadEconomica.findByCodigo(params.ciiuEmpleador)
		params.fechaEmision = FechaHoraHelper.hace10minutos()

		def diep = sdaep?.diep
		
		if (!diep) {
			params.sistemaSalud = SistemaSalud.findByCodigo(params?.sistemaSalud)
			diep = new DIEP(params)
		}else {
			/*
			 * Secci���n A
			 */
			diep.empleador = params.empleador
			diep.direccionEmpleadorTipoCalle = params.direccionEmpleadorTipoCalle
			diep.direccionEmpleadorComuna = params.direccionEmpleadorComuna
			diep.direccionEmpleadorNombreCalle = params.direccionEmpleadorNombreCalle
			diep.direccionEmpleadorRestoDireccion = params.direccionEmpleadorRestoDireccion
			diep.direccionEmpleadorNumero = "".equals(params.direccionEmpleadorNumero) ? null : Integer.parseInt(params.direccionEmpleadorNumero)

			diep.telefonoEmpleador = "".equals(params.telefonoEmpleador) ? null : Integer.parseInt(params.telefonoEmpleador)

			if (params.ciiuPrincipal)
				diep.ciiuPrincipal=params.ciiuPrincipal
			if (params.ciiuEmpleador)
				diep.ciiuEmpleador=params.ciiuEmpleador
			diep.propiedadEmpresa = params.propiedadEmpresa
			diep.tipoEmpresa = params.tipoEmpresa
			diep.nTrabajadoresHombre = "".equals(params.nTrabajadoresHombre) ? null : Integer.parseInt(params.nTrabajadoresHombre)
			diep.nTrabajadoresMujer = "".equals(params.nTrabajadoresMujer) ? null : Integer.parseInt(params.nTrabajadoresMujer)

			/*
			 * Secci���n B
			 */
			diep.trabajador = params.trabajador
			diep.nacionalidadTrabajador = params.nacionalidadTrabajador
			diep.direccionTrabajadorTipoCalle = params.direccionTrabajadorTipoCalle
			diep.direccionTrabajadorNombreCalle = params.direccionTrabajadorNombreCalle
			diep.direccionTrabajadorNumero = "".equals(params.direccionTrabajadorNumero) ? null : Integer.parseInt(params.direccionTrabajadorNumero)
			diep.direccionTrabajadorComuna = params.direccionTrabajadorComuna
			diep.etnia = params.etnia
			diep.otroPueblo = params.otroPueblo

			diep.direccionTrabajadorRestoDireccion = params.direccionTrabajadorRestoDireccion

			diep.telefonoTrabajador = "".equals(params.telefonoTrabajador) ? null : Integer.parseInt(params.telefonoTrabajador)

			diep.fechaIngresoEmpresa = "".equals(params.fechaIngresoEmpresa) ? null : params.fechaIngresoEmpresa
			diep.etnia = params.etnia
			diep.duracionContrato = params.duracionContrato
			diep.tipoRemuneracion = params.tipoRemuneracion
			diep.categoriaOcupacion = params.categoriaOcupacion
			diep.profesionTrabajador = params.profesionTrabajador

		}

		log.info("Inicializando validacion numero de trabajadores")
		def d = new DIEP()
		if(diep?.nTrabajadoresHombre && diep?.nTrabajadoresMujer){
			log.info("Inicializando validacion numero de trabajadores")
			def numeroTrabajadores = diep.nTrabajadoresHombre + diep.nTrabajadoresMujer
			if(numeroTrabajadores < 1){
				log.info("Cantidad de trabajadores debe ser superior 0 : ${diep.hasErrors()}")
				d.errors.reject("cl.adexus.isl.spm.diep.numeroTrabajadores.fail",
						["0"] as Object[],
						"[El total de trabajadores debe ser superior a [{0}]]")
			}
		}
		
		if(diep?.nTrabajadoresHombre == 0 || diep?.nTrabajadoresMujer == 0){
			log.info("Inicializando validacion numero de trabajadores denunciante distinto a empleador")
			def numeroTrabajadores = (diep?.nTrabajadoresHombre?:0) + (diep?.nTrabajadoresMujer?:0)
			if(numeroTrabajadores < 1){
				log.info("Cantidad de trabajadores debe ser superior 0 : ${diep.hasErrors()}")
				d.errors.reject("cl.adexus.isl.spm.diep.numeroTrabajadores.fail",
						["0"] as Object[],
						"[El total de trabajadores debe ser superior a [{0}]]")
			}
		}
		
		// Validacioneso
		if (!params.empleador.validate() || !params.trabajador.validate() || !diep.validate() || d?.hasErrors()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			log.info "Existen errores al validar diep"
			d.errors.each{error->
				log.info "--> error : $error"
			}
			return (['next': [action:'dp02'],  model: ['empleador': params.empleador, 'trabajador': params.trabajador, 'diep': diep, d:d]])
		}
		params.empleador.save()
		sdaep.empleador=params.empleador
		params.trabajador.save()
		sdaep.trabajador=params.trabajador

		diep.save()
		sdaep.diep=diep
		sdaep.save()
		return (['next': [action:'dp02_2'],  model: ['diep': diep]])
	}

	def diepCU02_2(params,SDAEP sdaep){
		def diep=sdaep.diep

		diep.sintoma=params.sintoma
		if (params.fechaSintoma instanceof String && !"".equals(params.fechaSintoma))
			params.fechaSintoma = FechaHoraHelper.stringToDate(params.fechaSintoma)
		diep.fechaSintoma=params.fechaSintoma
		diep.parteCuerpo=params.parteCuerpo
		diep.esAntecedentePrevio=FormatosISLHelper.booleanString(params.esAntecedentePrevio)
		diep.esAntecedenteCompanero=FormatosISLHelper.booleanString(params.esAntecedenteCompanero)
		diep.descripcionTrabajo=params.descripcionTrabajo
		diep.puestoTrabajo=params.puestoTrabajo
		diep.agenteSospechoso=params.agenteSospechoso
		diep.fechaAgente=params.fechaAgente

		//diep.calificacionDenunciante=CalificacionDenunciante.findByCodigo(params.calificacionDenunciante)
		diep.calificacionDenunciante=sdaep?.tipoDenunciante

		def denunciante=PersonaNatural.findByRun(params.denunciante_run)
		denunciante.nombre=params.denunciante_nombre
		denunciante.apellidoPaterno=params.denunciante_apellidoPaterno
		denunciante.apellidoMaterno=params.denunciante_apellidoMaterno
		denunciante.save()
		diep.denunciante=denunciante
		sdaep.denunciante=denunciante

		if(sdaep.tipoDenunciante.codigo == '2'){ //Trabajador
			sdaep?.diep?.trabajador?.nombre=params.denunciante_nombre
			sdaep?.diep?.trabajador?.apellidoPaterno=params.denunciante_apellidoPaterno
			sdaep?.diep?.trabajador?.apellidoMaterno=params.denunciante_apellidoMaterno
		}
		diep.telefonoDenunciante=params.telefonoDenunciante

		// validar antes de guardar y si no valida devolver al formulario
		if (!diep.validate()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			return (['next': [action:'dp02_2'],  model: ['diep': diep]])
		}

		diep.save()
		sdaep.diep=diep
		sdaep.save();
		return (['next': [action:'dp03', model: ['sdaep': sdaep]]])
	}

	def diepCU02_2BackCU02(params,SDAEP sdaep){
		def diep=sdaep.diep

		diep.sintoma=params.sintoma

		try{
			diep.fechaSintoma = FechaHoraHelper.stringToDate(params.fechaSintoma)
		}catch (Exception e){
			//diat.errors.rejectValue("fechaAccidente", "hora.mala",e.toString())
		}


		diep.parteCuerpo=params.parteCuerpo
		diep.esAntecedentePrevio=FormatosISLHelper.booleanString(params.esAntecedentePrevio)
		diep.esAntecedenteCompanero=FormatosISLHelper.booleanString(params.esAntecedenteCompanero)
		diep.descripcionTrabajo=params.descripcionTrabajo
		diep.puestoTrabajo=params.puestoTrabajo
		diep.agenteSospechoso=params.agenteSospechoso
		diep.fechaAgente=params.fechaAgente

		//diep.calificacionDenunciante=CalificacionDenunciante.findByCodigo(params.calificacionDenunciante)
		diep.calificacionDenunciante=sdaep?.tipoDenunciante

		def denunciante=PersonaNatural.findByRun(params.denunciante_run)
		denunciante.nombre=params.denunciante_nombre
		denunciante.apellidoPaterno=params.denunciante_apellidoPaterno
		denunciante.apellidoMaterno=params.denunciante_apellidoMaterno

		//denunciante.save()

		diep.denunciante=denunciante
		sdaep.denunciante=denunciante

		if(sdaep.tipoDenunciante.codigo == '2'){ //Trabajador
			sdaep?.diep?.trabajador?.nombre=params.denunciante_nombre
			sdaep?.diep?.trabajador?.apellidoPaterno=params.denunciante_apellidoPaterno
			sdaep?.diep?.trabajador?.apellidoMaterno=params.denunciante_apellidoMaterno
		}
		diep.telefonoDenunciante=params.telefonoDenunciante

		sdaep.diep=diep

		return (['next': [action:'dp02', model: ['sdaep': sdaep]]])
	}

	def diepGenBorradorPdf(params,SDAEP sdaep){

		def FormatosISLHelper f=new FormatosISLHelper()
		byte[] b

		def tipoPDF = Constantes.TIPOS_PDF_DIEP
		def DietPDF dataPDF = new DietPDF()

		//Mapear todos los datos del sdaat y sdaat.diat a dataPDF
		dataPDF.setEsBorrador('BORRADOR documento no tiene validez legal');
		dataPDF.setEsBorradorFirma(' ')

		dataPDF.setCodigoCUN(sdaep?.siniestro?.cun);
		dataPDF.setFechaEmision(f.fechaCorta(sdaep?.diep?.fechaEmision))
		dataPDF.setNumeroFolio(sdaep?.diep?.id.toString())
		// Secci�n A
		dataPDF.setEmpleadorRut(f.run(sdaep?.empleador?.rut));
		dataPDF.setEmpleadorRazonSocial(sdaep?.diep?.empleador?.razonSocial);
		dataPDF.setEmpleadorDireccion(f.direccionCompletaEmpleador(sdaep?.diep));
		dataPDF.setEmpleadorReferencias(sdaep?.diep?.direccionEmpleadorRestoDireccion);
		dataPDF.setEmpleadorComuna(sdaep?.diep?.direccionEmpleadorComuna?.descripcion);
		dataPDF.setEmpleadorTelefono(sdaep?.diep?.telefonoEmpleador?.toString());
		dataPDF.setEmpleadorPropiedad(sdaep?.diep?.propiedadEmpresa?.descripcion);
		dataPDF.setEmpleadorTipo(sdaep?.diep?.tipoEmpresa?.descripcion);
		dataPDF.setEmpleadorActividadEconomica(sdaep?.diep?.ciiuEmpleador?.descripcion);
		dataPDF.setEmpleadorActividadEconomicaSecundaria(sdaep?.diep?.ciiuPrincipal?.descripcion);
		dataPDF.setEmpleadorNroTrabajadoresHombres(sdaep?.diep?.nTrabajadoresHombre?.toString());
		dataPDF.setEmpleadorNroTrabajadoresMujeres(sdaep?.diep?.nTrabajadoresMujer?.toString());
		// Secci�n B
		dataPDF.setTrabajadorRun(f.run(sdaep?.trabajador?.run));
		dataPDF.setTrabajadorNombresApellidos(f.nombreCompleto(sdaep?.diep?.trabajador));
		dataPDF.setTrabajadorDireccion(f.direccionCompletaTrabajador(sdaep?.diep));
		dataPDF.setTrabajadorReferencias(sdaep?.diep?.direccionTrabajadorRestoDireccion);
		dataPDF.setTrabajadorComuna(sdaep?.diep?.direccionTrabajadorComuna?.descripcion);
		dataPDF.setTrabajadorTelefono(sdaep?.diep?.telefonoTrabajador?.toString());
		dataPDF.setTrabajadorSexo(sdaep?.diep?.trabajador?.sexo == 'M' ? 'Masculino' : 'Femenino');
		dataPDF.setTrabajadorFechaNacimiento(f.fechaCorta(sdaep?.diep?.trabajador?.fechaNacimiento));
		dataPDF.setTrabajadorNacionalidad(sdaep?.diep?.nacionalidadTrabajador?.descripcion);

		if ("OTRO".equals(sdaep?.diep?.etnia?.descripcion?.toUpperCase()))
			dataPDF.setTrabajadorPuebloOriginario(sdaep?.diep?.otroPueblo);
		else
			dataPDF.setTrabajadorPuebloOriginario(sdaep?.diep?.etnia?.descripcion);

		dataPDF.setTrabajadorProfesion(sdaep?.diep?.profesionTrabajador);
		dataPDF.setTrabajadorFechaIngreso(f.fechaCorta(sdaep?.diep?.fechaIngresoEmpresa));
		dataPDF.setTrabajadorTipoContrato(sdaep?.diep?.duracionContrato?.descripcion);
		dataPDF.setTrabajadorTipoIngreso(sdaep?.diep?.tipoRemuneracion?.descripcion);
		dataPDF.setTrabajadorCatOcupacional(sdaep?.diep?.categoriaOcupacion?.descripcion);
		// Secci�n C
		dataPDF.setEnfermedadMolestias(sdaep?.diep?.sintoma);
		dataPDF.setEnfermedadFecha(f.fechaCorta(sdaep?.diep?.fechaSintoma));
		dataPDF.setEnfermedadAnteriores(sdaep?.diep?.esAntecedentePrevio == null ? '' : (sdaep?.diep?.esAntecedentePrevio ? 'Si' : 'No'));
		dataPDF.setEnfermedadParteCuerpo(sdaep?.diep?.parteCuerpo);
		dataPDF.setEnfermedadTrabajo(sdaep?.diep?.descripcionTrabajo);
		dataPDF.setEnfermedadPuesto(sdaep?.diep?.puestoTrabajo);
		dataPDF.setEnfermedadCompaneros(sdaep?.diep?.esAntecedenteCompanero == null ? '' : (sdaep?.diep?.esAntecedenteCompanero ? 'Si' : 'No'));
		dataPDF.setEnfermedadAgentes(sdaep?.diep?.agenteSospechoso);
		dataPDF.setEnfermedadAgentesFecha(f.fechaCorta(sdaep?.diep?.fechaAgente));
		// Secci�n D
		dataPDF.setDenuncianteRun(f.run(sdaep?.diep?.denunciante?.run));
		dataPDF.setDenuncianteNombresApellidos(f.nombreCompleto(sdaep?.diep?.denunciante));
		dataPDF.setDenuncianteClasificacion(sdaep?.diep?.calificacionDenunciante?.descripcion);
		dataPDF.setDenuncianteTelefono(sdaep?.diep?.telefonoDenunciante);

		ByteArrayOutputStream pdf = PDFService.doPdf(dataPDF, tipoPDF)
		b = pdf.toByteArray();
		return b;
	}

	def diepGenPdf(params,SDAEP sdaep){

		def FormatosISLHelper f=new FormatosISLHelper()
		byte[] b

		def tipoPDF = Constantes.TIPOS_PDF_DIEP
		def DietPDF dataPDF = new DietPDF()

		//Mapear todos los datos del sdaep y sdaep.diep a dataPDF
		dataPDF.setEsBorrador(' ');
		dataPDF.setEsBorradorFirma('Firma denunciante')

		dataPDF.setCodigoCUN(sdaep?.siniestro?.cun);
		dataPDF.setFechaEmision(f.fechaCorta(sdaep?.diep?.fechaEmision))
		dataPDF.setNumeroFolio(sdaep?.diep?.id.toString())
		// Sección A
		dataPDF.setEmpleadorRut(f.run(sdaep?.empleador?.rut));
		dataPDF.setEmpleadorRazonSocial(sdaep?.empleador?.razonSocial);
		dataPDF.setEmpleadorDireccion(f.direccionCompletaEmpleador(sdaep?.diep));
		dataPDF.setEmpleadorReferencias(sdaep?.diep?.direccionEmpleadorRestoDireccion);
		dataPDF.setEmpleadorComuna(sdaep?.diep?.direccionEmpleadorComuna?.descripcion);
		dataPDF.setEmpleadorTelefono(sdaep?.diep?.telefonoEmpleador?.toString());
		dataPDF.setEmpleadorPropiedad(sdaep?.diep?.propiedadEmpresa?.descripcion);
		dataPDF.setEmpleadorTipo(sdaep?.diep?.tipoEmpresa?.descripcion);
		dataPDF.setEmpleadorActividadEconomica(sdaep?.diep?.ciiuEmpleador?.descripcion);
		dataPDF.setEmpleadorActividadEconomicaSecundaria(sdaep?.diep?.ciiuPrincipal?.descripcion);
		dataPDF.setEmpleadorNroTrabajadoresHombres(sdaep?.diep?.nTrabajadoresHombre?.toString());
		dataPDF.setEmpleadorNroTrabajadoresMujeres(sdaep?.diep?.nTrabajadoresMujer?.toString());
		// Secci�n B
		dataPDF.setTrabajadorRun(f.run(sdaep?.trabajador?.run));
		dataPDF.setTrabajadorNombresApellidos(f.nombreCompleto(sdaep?.trabajador));
		dataPDF.setTrabajadorDireccion(f.direccionCompletaTrabajador(sdaep?.diep));
		dataPDF.setTrabajadorReferencias(sdaep?.diep?.direccionTrabajadorRestoDireccion);
		dataPDF.setTrabajadorComuna(sdaep?.diep?.direccionTrabajadorComuna?.descripcion);
		dataPDF.setTrabajadorTelefono(sdaep?.diep?.telefonoTrabajador?.toString());
		dataPDF.setTrabajadorSexo(sdaep?.diep?.trabajador?.sexo == 'M' ? 'Masculino' : 'Femenino');
		dataPDF.setTrabajadorFechaNacimiento(f.fechaCorta(sdaep?.diep?.trabajador?.fechaNacimiento));
		dataPDF.setTrabajadorNacionalidad(sdaep?.diep?.nacionalidadTrabajador?.descripcion);
		dataPDF.setTrabajadorPuebloOriginario(sdaep?.diep?.etnia?.descripcion);
		dataPDF.setTrabajadorProfesion(sdaep?.diep?.profesionTrabajador);
		dataPDF.setTrabajadorFechaIngreso(f.fechaCorta(sdaep?.diep?.fechaIngresoEmpresa));
		dataPDF.setTrabajadorTipoContrato(sdaep?.diep?.duracionContrato?.descripcion);
		dataPDF.setTrabajadorTipoIngreso(sdaep?.diep?.tipoRemuneracion?.descripcion);
		dataPDF.setTrabajadorCatOcupacional(sdaep?.diep?.categoriaOcupacion?.descripcion);
		// Secci�n C
		dataPDF.setEnfermedadMolestias(sdaep?.diep?.sintoma);
		dataPDF.setEnfermedadFecha(f.fechaCorta(sdaep?.diep?.fechaSintoma));
		dataPDF.setEnfermedadAnteriores(sdaep?.diep?.esAntecedentePrevio == null ? '' : (sdaep?.diep?.esAntecedentePrevio ? 'Si' : 'No'));
		dataPDF.setEnfermedadParteCuerpo(sdaep?.diep?.parteCuerpo);
		dataPDF.setEnfermedadTrabajo(sdaep?.diep?.descripcionTrabajo);
		dataPDF.setEnfermedadPuesto(sdaep?.diep?.puestoTrabajo);
		dataPDF.setEnfermedadCompaneros(sdaep?.diep?.esAntecedenteCompanero == null ? '' : (sdaep?.diep?.esAntecedenteCompanero ? 'Si' : 'No'));
		dataPDF.setEnfermedadAgentes(sdaep?.diep?.agenteSospechoso);
		dataPDF.setEnfermedadAgentesFecha(f.fechaCorta(sdaep?.diep?.fechaAgente));
		// Secci�n D
		dataPDF.setDenuncianteRun(f.run(sdaep?.diep?.denunciante?.run));
		dataPDF.setDenuncianteNombresApellidos(f.nombreCompleto(sdaep?.diep?.denunciante));
		dataPDF.setDenuncianteClasificacion(sdaep?.diep?.calificacionDenunciante?.descripcion);
		dataPDF.setDenuncianteTelefono(sdaep?.diep?.telefonoDenunciante);

		ByteArrayOutputStream pdf = PDFService.doPdf(dataPDF, tipoPDF)
		b = pdf.toByteArray();
		return b;
	}

	def diepR04(params, SDAEP sdaep){
		def denuncia = DocumentacionAdicional.findByDenunciaEP(sdaep.diep)
		if (!denuncia) {
			log.info("diep04 no hay denuncia")
			denuncia = new DocumentacionAdicional()
			denuncia.errors.reject('cl.adexus.isl.spm.DocumentacionAdicional.denunciaDiep.null')
			return (['next': [action:'dp04'],  model: ['denuncia': denuncia]])
		}
		// Procesa la diep, guardando la diep en el siniestro si es que no existe
		sdaep.save();

		def siniestro
		if(sdaep.siniestro){
			log.info 'sini 1'
			siniestro=sdaep.siniestro
		}else{
			//Es un siniestro nuevo
			log.info 'sini 2'
			siniestro = new Siniestro()
			siniestro.setEmpleador(sdaep.diep.empleador)
			siniestro.setTrabajador(sdaep.diep.trabajador)
			siniestro.setFecha(sdaep.diep.fechaSintoma)
			siniestro.setRelato(sdaep.diep.sintoma)
			siniestro.tipoPatologia = sdaep.cuestionarioOrigen.tipoEnfermedad
			siniestro.setEsEnfermedadProfesional(true)
			siniestro.setUsuario(sdaep.usuario)
			siniestro.save()
		}

		//Asociamos el siniestro a 77bis
		if (params['bisId']){
			log.info "Asociando el siniestro "+siniestro+" a 77bis "+params['bisId']
			def bis = Bis.findById(params['bisId'])
			bis.siniestro = siniestro
			bis.save(flush: true)
		}

		sdaep.diep.setSiniestro(siniestro)
		sdaep.siniestro=siniestro
		sdaep.diep.save()

		log.info("Enviando DIEP a SUSESO")
		SUSESOService.enviarDIEP(sdaep.diep);
		log.info("Envio finalizado")


		log.info("Obteniendo valor de retorno desde el xml recibido desde SUSESO")
		def retorno = ""
		retorno = SUSESOService.getRetorno(sdaep.diep.xmlRecibido)
		log.info("SUSESO service retorno el valor : $retorno")
		def mensaje = "valor de retorno $retorno : ${RetornoWSEnum.findByKey(retorno.text()).getDescripcion()}"
		log.debug(mensaje)
		log.info("Obtencion de valor de retorno desde xml recibido desde SUSESO finalizada")


		// Luego de procesar la diep, va a dp05
		return (['next': [action:'dp05', mensaje:mensaje]])
	}

	// ------------- OPAEP --------------

	/**
	 * Va a buscar una OPAEP previa para el siniestro 
	 * 
	 * Si la encuentra
	 * 	-y esta vigente -> dp03
	 *  -y no esta vigente -> dp04
	 * Si no la encuentra
	 *  - y es primera denuncia -> dp01
	 *  - y no es primera denuncia -> dp04
	 */

	def opaepR01(params,SDAEP sdaep){
		def opaepPrevia = Siniestro.get(sdaep.siniestro.id).getOpaep()
		log.info "OPAEP PREVIA: "+opaepPrevia
		if(opaepPrevia){
			//Encuentra OPAEP previa
			sdaep.opaep=opaepPrevia
			def ahora= new Date()
			long diff = ahora.getTime() - sdaep.opaep.inicioVigencia.getTime();
			def estaVigente= ( (diff/(24*60*60*1000))  < sdaep.opaep.duracionDias )
			if(estaVigente){
				return ([next: [action:'dp03']])  //Esta vigente
			}else{
				return ([next: [action:'dp04']]) // No esta vigente
			}
		}else{
			//No hay OPAEP previa
			//Vemos si es la primera denuncia
			def diepsSiniestro = DIEP.findAllBySiniestro(sdaep.diep.siniestro)

			log.info "diepsSiniestro:"+diepsSiniestro
			if(diepsSiniestro.size()==1){
				return ([next: [action:'dp01']]) // Primera denuncia
			}else{
				return ([next: [action:'dp04']]) // No es primera denuncia
			}
		}
	}

	/**
	 * Emite la OPA y la Guarda
	 *
	 * Si se puede -> dp03
	 * Si no se puede -> dp02
	 */
	def opaepR02(params,SDAEP sdaep){
		// Emisi���n de la OPAEP, incluyendo guardarla.
		params['centroAtencion']=CentroSalud.findById(params['centroAtencion'])
		//Inicializa la OPAEP
		def opaep = new OPAEP(params)
		opaep.siniestro = sdaep.diep.siniestro
		opaep.fechaCreacion = new Date()
		opaep.inicioVigencia = new Date() // TODO: O definir si se usa sdaep.fechaSiniestro
		opaep.duracionDias = 60 // TODO: Parametrizar la duraci���n en dias de la OPA
		if(sdaep.diep?.direccionTrabajadorNombreCalle!=null && sdaep.diep?.direccionTrabajadorNumero!=null)
			opaep.direccionTrabajador = sdaep.diep?.direccionTrabajadorNombreCalle+" "+sdaep.diep?.direccionTrabajadorNumero
		opaep.comunaTrabajador = sdaep.diep?.direccionTrabajadorComuna
		opaep.telefonoTrabajador= sdaep.diep?.telefonoTrabajador
		opaep.usuarioEmisor = SecurityUtils.subject?.principal
		opaep.siniestro=sdaep.siniestro //Esto porque?
		if(!opaep.validate()){ // TODO:Excepci���n
			// Hay algo malo en los datos y no se puede crear la opa
			return (['next': [action: 'dp02'],  model: ['opaep': opaep]])
		}
		opaep.save(flush: true)
		//Agregamos al sdaep
		sdaep.opaep=opaep
		sdaep.save(flush: true)

		//Agregamos al Siniestro
		def s=Siniestro.get(opaep.siniestro.id)
		s.opaep=opaep;
		s.save(flush:true)
		return ([next: [action: 'dp03']])
	}

	def opaepR03(params,SDAEP sdaep){

		//Reattachar para evitar el lazy error o como se llame
		if (!sdaep.opaep.isAttached()) {
			sdaep.opaep.attach()
		}

		return DenunciaService.getOP([id: sdaep.opaep.id, tipo: Constantes.TIPOS_PDF_OPAEP]);
	}

	def opaepR04(params,SDAEP sdaep){
		// Guarda en base de datos la solicitud y avisa
		def solicitud = new SolicitudReingreso(params)
		solicitud.siniestro = sdaep.diep.siniestro
		solicitud.fechaCreacion = new Date()
		solicitud.solicitante = sdaep.diep.denunciante
		solicitud.solicitud = sdaep

		if(!solicitud.validate()){ // TODO:Excepci���n
			// Hay algo malo en los datos y no se puede crear la opa
			return (['next': [action: 'dp04'],  model: ['solicitud': solicitud]])
		}
		solicitud.save()
		sdaep.solicitudReingreso = solicitud
		return ([next: [action: 'cu02termina']])
	}
}
