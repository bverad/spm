package cl.adexus.isl.spm


import java.util.Date;
import java.util.logging.Logger;

import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.helpers.DataSourceHelper
import cl.adexus.helpers.FechaHoraHelper
import cl.adexus.isl.spm.domain.CuestionarioTrabajoPDF
import cl.adexus.isl.spm.domain.CuestionarioTrayectoPDF
import cl.adexus.isl.spm.domain.DiatPDF
import cl.adexus.isl.spm.domain.OpaPDF
import cl.adexus.isl.spm.domain.Constantes
import cl.adexus.isl.spm.enums.RetornoWSEnum

import org.apache.shiro.SecurityUtils

class SDAATService {
	
	def SGAService
	def DroolsService
	def PDFService
	def SUSESOService
	def DenunciaService
	def SiniestroService
	def UsuarioService
	def BISIngresoService
	
	// ------------ COMUNES ----------
	def excepcionar(params, SDAAT sdaat, contexto){
		log.info("Ejecutando metodo excepcionar")
		log.debug("Datos recibidos : $params")
		def excepcion = new Excepcion(params)
		excepcion.contexto=contexto
		excepcion.solicitudAT=sdaat
		log.info("Verificando si existen errores de definicion en el dominio Excepcion")
		if(!excepcion.validate()){
			log.debug("Existen errores")
			excepcion.errors.each{error->
				log.debug("--> $error")
			}
			//Hay algo malo con los datos y no se puede crear excepcion
			def deDondeVengo
			if(contexto=='ident') {deDondeVengo='dp04'}
			if(contexto=='clatrab') {deDondeVengo='dp02'}
			if(contexto=='calorigen') {deDondeVengo='dp04'}
			/*
			 * SVC: No se como solucionar exception para cluster
			 * return (['next':['action':deDondeVengo],  model: ['excepcion': excepcion]])
			 */
			if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
				sdaat.trabajador.attach()
			}
	
			if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
				sdaat.empleador.attach()
			}
	
			return (['next':['action':deDondeVengo],  model: ['excepcion': excepcion, 'sdaat':sdaat]])
		}else
			log.debug("No existen errores")
		log.info("La verificación de errores para dominio excepcion ha concluido")
		
		
		excepcion.save()
		
		//TODO: Avisarle a alguien que hubo una excepcion?
		if(contexto=='ident') { return (['next':['controller': 'SDAAT_ident', 'action':'r03']]) }
		if(contexto=='clatrab') { return (['next':['controller': 'SDAAT_calorigen', 'action':'dp01']]) }
		if(contexto=='calorigen') { return (['next':['controller': 'SDAAT_complej', 'action':'dp01']]) }
		
		log.info("Ejecucion de metodo excepcionar finalizada")
	}

	def terminar(params, SDAAT sdaat, salida){
		log.info("Ejecutando metodo terminar")
		log.info("Datos recibidos : $params")
		//Cerramos el SDAAT
		sdaat.finSolicitud=new Date()
		sdaat.salida=salida
		sdaat.save(flush: true)
		
		//Muere el sdaat?
		sdaat=null
		
		//Terminamos el proceso de regularizacion y vamos a notificar solicitante (BIS_ingreso/dp04)
		log.info("Verificando existencia de bis ingreso")
		if (params?.bisId){
			log.debug("bis ingreso existe")
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
		}else
			log.debug("bis ingreso no existe")
		
		log.info("Ejecucion de metodo terminar finalizada")
		return (['next':['controller':'nav', 'action':'area2']]) 
	}
	// ------------- IDENT --------------
	
	def identR01(params) {
		log.info("Ejecutando metodo identR01")
		log.debug("Datos recibidos : $params")

		log.info("Limpiando rut")
		params['run']=((String)params['run']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		log.info("Limpieza de rut exitosa")
		
		//Busca si existe el trabajador (run)
		log.info("Verificando existencia de trabajador")
		def trabajador=PersonaNatural.findByRun(params['run'])
		if(!trabajador){
			log.debug("Trabajador no existe")
			// Se agregan valores por defecto para nombre y apellido paterno
			params.nombre = ' '
			params.apellidoPaterno = ' '
			trabajador=new PersonaNatural(params)
			log.info("Verificando errores en trabajador")
			if(!trabajador.validate()){
				log.debug("Existen errores")
				trabajador.errors.each{error->
					log.debug("--> $error")
				}
				//Hay algo malo en los datos y no se puede crear el trabajador
				return (['next': 'dp01',  model: ['trabajador': trabajador]])
			}else
				log.debug("No existen errores en trabajador")
			log.info("Verificacion de errores en trabajador concluida")
				
			log.info("Guardando trabajador")
			trabajador.save()
			log.info("Trabajador guardado con exito")
		}
		
		//Inicializa el SDAAT
		def sdaat = new SDAAT(params)
		sdaat.trabajador=trabajador
		sdaat.inicioSolicitud = new Date()
		def username=SecurityUtils.subject?.principal
		sdaat.usuario=username
		log.info("Verificando errores en sdaat")
		if(!sdaat.validate()){
			log.debug("sdaat tiene errores")
			sdaat.errors.each{error->
				log.debug("--> $error")
			}
			
			//Hay algo malo con los datos y no se puede crear el SDAAT
			return (['next': 'dp01',  model: ['trabajador': trabajador, 'sdaatId': sdaat?.id]])
		}
		
		log.info("Guardando cambios en sdaat")
		sdaat.save()
		log.info("Cambios en sdaat guardados exitosamente")
		
		//Busca los datos en el WS del SGA
		log.info("Consultando a empresas por trabajador en SGA")
		def empresas = SGAService.consultaTrabajador(trabajador.run,sdaat.fechaSiniestro)
		log.info("Consulta concluida y retornando el valor : $empresas")

		//Si retorna algun dato va a dp02 si no a dp03
		def next
		log.info("empresas.size: " + empresas.size() + " bisId: " + params?.bisId)
		if( empresas.size() != 0 && !params?.bisId){
			next="dp02"
		}else{
			if (params?.bisId){
				return (['next': 'r02', model: ['sdaatId': sdaat?.id,'empresas': empresas ]])
			}else{
				next="dp03"
			}			
		}
		log.info("identR01: next: " + next)
		//Retorna las empresas, la proxima pagina y el SDAAT inicializado
		def r=['next': next, model: ['sdaatId': sdaat?.id,'empresas': empresas ]]
		log.info("Ejecucion de metodo identR01 finalizada")
		return (r)
    }
	
	def identR02(params,SDAAT sdaat) {
		log.info("Ejecutando metodo identR02")
		def next = (['next': 'dp04'])
		//Limpiamos el rut
		log.info("Limpiando rut")
		params['rut']=((String)params['rut']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		log.info("Limpieza de rut completada")
		
		//Busca si existe el empleador (por rut)
		def empleador=PersonaJuridica.findByRut(params['rut'])
		log.info("Verificando existencia empleador")
		if(!empleador){
			log.info("Empleador no existe")
			params.razonSocial = ' '
			log.info("Creando nuevo empleador")
			empleador=new PersonaJuridica(params)
			log.info("Verificando errores en empleador")
			if(!empleador.validate()){
				//Hay algo malo en los datos y no se puede crear el empleador
				log.debug("empleador tiene errores")
				empleador.errors.each{error->
					log.debug("--> $error")
				}
				log.info("Redireccionando a accion dp03")
				empleador.rut = null
				return (['next': 'dp03',  model: ['empleador': empleador, rutEmpleador:""]])
			}
			log.info("Nuevo empleador: [${params?.rut}]")
		}
		
		//Busca los datos en el el WS del SGA
		log.info("Consultando a empresas por trabajador en SGA")
		def empresa=SGAService.consultaEmpresa(empleador.rut,sdaat.fechaSiniestro)
		log.info("Consulta a SGA concluida")
		log.info("Verificando existencia de empresa")
		if(empresa != null){
			log.debug("empresa existe")
			//Actualizamos los datos de la empresa (OJOPIOJO: Si el WS retorna otro nombre para ese rut se cambiara
			log.debug("seteando razon social y actividad de la empresa")
			empleador.razonSocial=empresa['nombre']
			sdaat.codigoActividadEmpresa=empresa['codActEmp']
			log.debug("seteo finalizado")
			
			//Revisamos si la empresa es ISL
			log.debug("verificando si el seguro laboral corresponde a ISL")
			if(empresa['seguroLaboral'].equals('ISL')){
				//Estamos listos. Vamos a buscar siniestros preexistentes
				log.debug("Corresponde a seguro laboral ISL, redireccionando a accion r03")
				next = (['next': 'r03'])
			}
		}
		
		log.info("Guardando cambios en empleador")
		empleador.save()
		log.info("Cambios en empleador guardados exitosamente")
		//Guarda los datos de la empresa en la solicitud
		log.info("Guardando cambios en sdaat")
		sdaat.empleador=empleador
		sdaat.save()
		log.info("Cambios en sdaat guardados exitosamente")
		
		//Si llegamos aca, la empresa o no existe o no es ISL
		//Solo podemos ir al termino con excepcion
		return next
	}
	
	
	//Busca Siniestros Preexistentes
	def identR03(params,SDAAT sdaat) {
			def formatDateTimeFunction = (new DataSourceHelper()).formatDatetimeFunction();
		def siniestrosPrevios = Siniestro.executeQuery(
				"select s "+
				"  	from DIAT as d " +
				"  	join d.siniestro s" + 
				" 	where "+formatDateTimeFunction+"(s.fecha, 'dd-MM-yyyy')=?"+
				"   and s.empleador=?"+
				"   and s.trabajador=?",[new java.text.SimpleDateFormat("dd-MM-yyyy").format(sdaat.fechaSiniestro),
					sdaat.empleador,sdaat.trabajador])
		
		//def siniestrosPrevios = SiniestroService.findSiniestrosPrevios(sdaat.fechaSiniestro,sdaat.empleador,sdaat.trabajador)
		if(siniestrosPrevios){
			sdaat.siniestro=siniestrosPrevios[0]
			return ([next: [controller:'SDAAT_diat',action:'dp01']])	
		}else{
			return ([next: [controller: 'SDAAT_clatrab', action: 'r01']])
		}
	}
	
	// ------------- CALORIGEN --------------
	
	def calorigenCU01(params, SDAAT sdaat, tipoAccidente) {
		sdaat.relato = params.get('relatoSiniestro')
		def next
		if(tipoAccidente=='trayecto'){
			sdaat.esAccidenteTrayecto=true
			next=['action': 'dp02']
		}else{
			sdaat.esAccidenteTrayecto=false
			next=['action': 'dp03']
		}
		sdaat.save()
		if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
			sdaat.trabajador.attach()
		}

		if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
			sdaat.empleador.attach()
		}

		return (['next': next,  model: ['sdaat': sdaat]])
	}

	/**
	 * Procesa el cuestionario de calificacion de origen Accidente de Trayecto
	 * Determina si es origen comun o laboral y retorna para donde ir.
	 * 
	 * @param params
	 * @param sdaat
	 * @return
	 */
	def calorigenR01(params, SDAAT sdaat) {
		def parametros = [
			pregunta1: null,
			pregunta2: params.pregunta2,
			pregunta2_1: params.pregunta2_1,
			pregunta3: FechaHoraHelper.HoursToFloat(params.pregunta3),
			pregunta4: null,
			tipoAccidenteTrayecto: TipoAccidenteTrayecto.findByCodigo(params['tipoAccidenteTrayecto'])
		]
		
		def cuestionarioTrayecto = new CuestionarioCalificacionOrigenAccidenteTrayecto(parametros)

		try {
			cuestionarioTrayecto.pregunta1 = FechaHoraHelper.horaToDate(params.pregunta1 ,sdaat.fechaSiniestro)
		} catch (Exception e) {
			cuestionarioTrayecto.errors.rejectValue('pregunta1', 'hora.mala', e.toString())
		}

		try {
			cuestionarioTrayecto.pregunta4 = FechaHoraHelper.horaToDate(params.pregunta4 ,params.pregunta4_1)
		} catch (Exception e) {
			cuestionarioTrayecto.errors.rejectValue('pregunta4', 'hora.mala', e.toString())
		}
		
		//validar antes de guardar y si no valida devolver al formulario
		if (!cuestionarioTrayecto.validate()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			def extraCuestionarioTrayecto = [
				pregunta1_1: params.pregunta1_1,
				pregunta4_1: params.pregunta4_1
			]
			if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
				sdaat.trabajador.attach()
			}
	
			if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
				sdaat.empleador.attach()
			}
	
			return (['next': [action:'dp02'],
				model: ['cuestionarioTrayecto': cuestionarioTrayecto, 'sdaat': sdaat]])
		}
		cuestionarioTrayecto.save()
		
		//Si esta todo mapeado ok enviar al motor de reglas
		def esComun = DroolsService.esOrigenComunTrayecto(cuestionarioTrayecto)
		cuestionarioTrayecto.esOrigenComun = esComun
		sdaat.cuestionarioOrigenTrayecto = cuestionarioTrayecto
		sdaat.save()
		if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
			sdaat.trabajador.attach()
		}

		if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
			sdaat.empleador.attach()
		}

		def next = [:]
		if(esComun){
			// Si es origen común debe ir a dp04 (informar origen común)
			next = [action:'dp04']
		}else{
			// Si no es origen común debe ir a dp01 de complej
			next = [controller: 'SDAAT_complej', action: 'dp01']
		}
		return (['next': next,  model: ['sdaat': sdaat]])
	}
	
	def calorigenR02(params, SDAAT sdaat) { //Procesa cuestionario trabajo
		params['origenDanyo']=OrigenDanyo.findByCodigo(params['origenDanyo'])
		
		def cuestionarioTrabajo = new CuestionarioCalificacionOrigenAccidenteTrabajo(params)
		// validar antes de guardar y si no valida devolver al formulario
		if (!cuestionarioTrabajo.validate()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
				sdaat.trabajador.attach()
			}
	
			if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
				sdaat.empleador.attach()
			}
	
	
			return (['next': [action:'dp03'],  model: ['cuestionarioTrabajo': cuestionarioTrabajo, 'sdaat': sdaat]])
		}
		cuestionarioTrabajo.save()
		
		//Si esta todo mapeado ok enviar al motor de reglas
		def esComun=DroolsService.esOrigenComunTrabajo(cuestionarioTrabajo)
		cuestionarioTrabajo.esOrigenComun=esComun
		sdaat.cuestionarioOrigenTrabajo=cuestionarioTrabajo
		sdaat.relato = params['relatoSiniestro']
		sdaat.save()

		if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
			sdaat.trabajador.attach()
		}

		if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
			sdaat.empleador.attach()
		}

		def next
		if(esComun){
			// Si es origen común debe ir a dp04 (informar origen común)
			next = [action:'dp04']
		}else{
			// Si no es origen común debe ir a dp01 de complej
			next = [controller: 'SDAAT_complej', action: 'dp01']
		}
		return (['next': next,  model: ['sdaat': sdaat]])
	}
	
	
	def calorigenGenPdf(params,SDAAT sdaat){
		def FormatosISLHelper f=new FormatosISLHelper()
		byte[] b
		//Vemos si es el cuestionario de trayecto o de trabajo
		//Creo que debiera ser sdaat.cuestionarioOrigenTrayecto!=null 
		if(sdaat.esAccidenteTrayecto){
			def tipoPDF = Constantes.TIPOS_PDF_CUESTIONARIO_TRAYECTO
			def CuestionarioTrayectoPDF dataPDF = new CuestionarioTrayectoPDF()
			
			//Mapear todos los datos del sdaat y sdaat.cuestionarioOrigenTrayecto a dataPDF
			dataPDF.setEmpleadorRut(f.run(sdaat.empleador.rut))
			dataPDF.setEmpleadorRazonSocial(f.blankStatic(sdaat.empleador.razonSocial))
			dataPDF.setFecha(f.fechaCorta(sdaat.fechaSiniestro))
			dataPDF.setTrabajadorRun(f.run(sdaat.trabajador.run))
			dataPDF.setTrabajadorNombres(f.nombreCompleto(sdaat.trabajador))
			dataPDF.setA(f.horaCorta(sdaat.cuestionarioOrigenTrayecto.pregunta1))
			dataPDF.setbSiNo(sdaat.cuestionarioOrigenTrayecto.pregunta2 ? 'Si' : 'No')
			dataPDF.setbDescripcion(sdaat.cuestionarioOrigenTrayecto.pregunta2_1)
			dataPDF.setC(sdaat.cuestionarioOrigenTrayecto.pregunta3 + '')
			dataPDF.setD(f.horaCorta(sdaat.cuestionarioOrigenTrayecto.pregunta4))
			dataPDF.setE(sdaat.cuestionarioOrigenTrayecto.tipoAccidenteTrayecto.descripcion)

			ByteArrayOutputStream pdf = PDFService.doPdf(dataPDF, tipoPDF)
			b = pdf.toByteArray();
		} else {
			def tipoPDF = Constantes.TIPOS_PDF_CUESTIONARIO_TRABAJO
			def CuestionarioTrabajoPDF dataPDF = new CuestionarioTrabajoPDF()
			
			//Mapear todos los datos del sdaat y sdaat.cuestionarioTrabajo a dataPDF
			dataPDF.setEmpleadorRut(f.run(sdaat.empleador.rut))
			dataPDF.setEmpleadorRazonSocial(f.blankStatic(sdaat.empleador.razonSocial))
			dataPDF.setFecha(f.fechaCorta(sdaat.fechaSiniestro))
			dataPDF.setTrabajadorRun(f.run(sdaat.trabajador.run))
			dataPDF.setTrabajadorNombres(f.nombreCompleto(sdaat.trabajador))
			dataPDF.setaSiNo(sdaat.cuestionarioOrigenTrabajo.pregunta1 ? 'Si' : 'No')
			dataPDF.setaDescripcion(sdaat.cuestionarioOrigenTrabajo.pregunta1_1)
			dataPDF.setbSiNo(sdaat.cuestionarioOrigenTrabajo.pregunta2 ? 'Si' : 'No')
			dataPDF.setbDescripcion(sdaat.cuestionarioOrigenTrabajo.pregunta2_1)
			dataPDF.setcSiNo(sdaat.cuestionarioOrigenTrabajo.pregunta3 ? 'Si' : 'No')
			dataPDF.setcDescripcion(sdaat.cuestionarioOrigenTrabajo.pregunta3_1)
			dataPDF.setdSiNo(sdaat.cuestionarioOrigenTrabajo.pregunta4 ? 'Si' : 'No')
			dataPDF.setdDescripcion(sdaat.cuestionarioOrigenTrabajo.pregunta4_1)
			dataPDF.setdComo(sdaat.cuestionarioOrigenTrabajo.origenDanyo.descripcion)
			dataPDF.setdDescriba(sdaat.relato)
			dataPDF.seteSiNo(sdaat.cuestionarioOrigenTrabajo.pregunta5 ? 'Si' : 'No')
			
			ByteArrayOutputStream pdf = PDFService.doPdf(dataPDF, tipoPDF)
			b = pdf.toByteArray();
		}
		return b;
	}
	
	// ------------- COMPLEJ --------------
	
	def complejR01(params,SDAAT sdaat) {
		def cuestionario = new CuestionarioComplejidad(params)
		// validar antes de guardar y si no valida devolver al formulario
		if (!cuestionario.validate()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
				sdaat.trabajador.attach()
			}
	
			if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
				sdaat.empleador.attach()
			}
			return (['next': [action:'dp01'],  model: ['cuestionario': cuestionario, 'sdaat': sdaat]])
		}
		
		def calculo = DroolsService.calcularComplejidad(cuestionario)
		cuestionario.complejidadCalculada = calculo
		cuestionario.save()
		sdaat.cuestionarioComplejidad = cuestionario
		sdaat.save()
		if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
			sdaat.trabajador.attach()
		}

		if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
			sdaat.empleador.attach()
		}

		def next
		if(calculo == 0){
			// Si la complejidad del accidente es igual a 0 va a dp02
			log.info "complejidad 0"
			next = [action:'dp02']
		}else{
			// Si la complejidad del accidente es distinto de 0 va a dp01 de diat
			log.info "complejidad distinta a 0"
			next = [controller: 'SDAAT_diat', action: 'dp01']
		}
		return (['next': next, model: ['sdaat': sdaat]])
	}
	
	def complejCU01(params,SDAAT sdaat) {
		log.info "Ejecutando metodo complejCU01"
		log.info "Datos recibidos : $params"
		sdaat.cuestionarioComplejidad.aceptaPropuesta = params['aceptaPropuesta'].toBoolean()
		//TODO: Validar si se agrega logica de mayor a cero en caso de que no se acepte
		if (!sdaat.cuestionarioComplejidad.aceptaPropuesta) {
			sdaat.cuestionarioComplejidad.complejidadCalculada = 1
		}
		log.info "Complejidad calculada : ${sdaat?.cuestionarioComplejidad?.complejidadCalculada}"
		sdaat.save(flush:true)
		return (['next': [controller:'SDAAT_diat', action:'dp01'], model: ['sdaat': sdaat]])
	}
	
	// ------------- CLATRAB --------------
	
	def clatrabR01(params,SDAAT sdaat){
		//Detecta si es empleado. CIU de la Empresa
		def esEmpleado=DroolsService.esEmpleado(sdaat.codigoActividadEmpresa)
		if(esEmpleado){
			// Si es empleado va al dp01 de calorigen
			return ([next: [controller: 'SDAAT_calorigen', action: 'dp01']])
		}else{
			// Si no es empleado va al dp01 (Cuestionario Obrero)
			return ([next: [controller: 'SDAAT_clatrab', action: 'dp01']])
		}
	}
	
	def clatrabR02(params,SDAAT sdaat){
		//Guarda los datos del cuestionario
		def cuestionario=sdaat.cuestionarioObrero
		if(!cuestionario){
			cuestionario=new CuestionarioObrero()
		}
		//Buscamos el codigo
		def actividadTrabajador = ActividadTrabajador.findByCodigo(params['codigo'])
		if(!actividadTrabajador){
			//Codigo de actividad no existe. Devuelta al cuestionario
			actividadTrabajador = new ActividadTrabajador()
			actividadTrabajador.errors.reject('cl.adexus.isl.spm.CuestionarioObrero.actividadTrabajador.selected')
			return ([next: [controller:'SDAAT_clatrab', action:'dp01'], model: ['actividadTrabajador': actividadTrabajador]])
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
			return ([next: [controller:'SDAAT_clatrab', action:'dp01'],  model: ['cuestionarioObrero': cuestionario]])
		}
		cuestionario.save()
		
		//Lo agregamos a la solicitud
		sdaat.cuestionarioObrero=cuestionario
		sdaat.save(failOnError: true)
		
		//Vemos si es obrero
		def esObrero=(cuestionario.actividadTrabajador.codigo!='OE')
		if(esObrero){
			return ([next: [controller:'SDAAT_clatrab',action:'dp02']])	
		}else{
			return ([next: [controller: 'SDAAT_calorigen', action: 'dp01']])
		}
	}
	
	
	// ------------ DIAT ----------
	
	def diatR01(params,SDAAT sdaat){
		if(params['tipoDenunciante']=='2'){ //Trabajador
			params['run']=sdaat.trabajador.run
		}
		
		//Limpiamos el run
		params['run']=((String)params['run']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		sdaat.tipoDenunciante=CalificacionDenunciante.findByCodigo(params['tipoDenunciante'])
		
		//Busca si existe el denunciante (run)
		def denunciante=PersonaNatural.findByRun(params['run'])
		if(!denunciante){
			params.nombre = ' '
			params.apellidoPaterno = ' '
			denunciante=new PersonaNatural(params)
			if(!denunciante.validate()){
				//Hay algo malo en los datos y no se puede crear el denunciante
				if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
					sdaat.trabajador.attach()
				}
		
				if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
					sdaat.empleador.attach()
				}
	
				return ([next:  [action:'dp01'],  model: ['denunciante': denunciante, 'sdaat': sdaat]])
			}
			denunciante.save()
		}
		sdaat.denunciante=denunciante
		sdaat.save()
		
		//Busca denuncia previa para ese denunciante
		def diatPrevias = DenunciaService.diatPrevias(sdaat.fechaSiniestro,sdaat.empleador,sdaat.trabajador,sdaat.tipoDenunciante);
		if(diatPrevias.size==0){
			return ([next: [action:'dp02']])
		}else{
			sdaat.diat=diatPrevias[0]
			sdaat.diat.siniestro  //Solo para hacer el lazyLoading (supongo que hay una forma mas enchulada, pero tengo sueño)
			return ([next: [action:'dp05']]) //Informacion adicional
		}
	}

	def diatCU02(params,SDAAT sdaat){
		log.info("Ejecutando metodo diatCU02")
		log.info("Datos recibidos : $params")
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

		if(sdaat.tipoDenunciante.codigo == '2'){ //Trabajador
			sdaat.denunciante.nombre=params.trabajador_nombre
			sdaat.denunciante.apellidoPaterno=params.trabajador_apellidoPaterno
			sdaat.denunciante.apellidoMaterno=params.trabajador_apellidoMaterno
			sdaat.denunciante.apellidoMaterno=params.trabajador_apellidoMaterno
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
			
		def diat = sdaat?.diat
		if (!diat){
			params.sistemaSalud = SistemaSalud.findByCodigo(params?.sistemaSalud)
			diat = new DIAT(params)
		}else {
			/*
			 * Sección A
			 */
			diat.empleador = params.empleador
			diat.direccionEmpleadorTipoCalle = params.direccionEmpleadorTipoCalle
			diat.direccionEmpleadorComuna = params.direccionEmpleadorComuna
			diat.direccionEmpleadorNombreCalle = params.direccionEmpleadorNombreCalle
			diat.direccionEmpleadorRestoDireccion = params.direccionEmpleadorRestoDireccion
			diat.direccionEmpleadorNumero = "".equals(params.direccionEmpleadorNumero) ? null : Integer.parseInt(params.direccionEmpleadorNumero)
			
			diat.telefonoEmpleador = "".equals(params.telefonoEmpleador) ? null : Integer.parseInt(params.telefonoEmpleador)
			if (params.ciiuPrincipal)
				diat.ciiuPrincipal=params.ciiuPrincipal
			if (params.ciiuEmpleador)
				diat.ciiuEmpleador=params.ciiuEmpleador
			diat.propiedadEmpresa = params.propiedadEmpresa
			diat.tipoEmpresa = params.tipoEmpresa
			diat.nTrabajadoresHombre = "".equals(params.nTrabajadoresHombre) ? null : Integer.parseInt(params.nTrabajadoresHombre) 
			diat.nTrabajadoresMujer = "".equals(params.nTrabajadoresMujer) ? null : Integer.parseInt(params.nTrabajadoresMujer)

			/*
			 * Sección B
			 */
			diat.trabajador = params.trabajador
			diat.nacionalidadTrabajador = params.nacionalidadTrabajador
			diat.direccionTrabajadorTipoCalle = params.direccionTrabajadorTipoCalle
			diat.direccionTrabajadorNombreCalle = params.direccionTrabajadorNombreCalle
			diat.direccionTrabajadorNumero = "".equals(params.direccionTrabajadorNumero) ? null : Integer.parseInt(params.direccionTrabajadorNumero)
			diat.direccionTrabajadorComuna = params.direccionTrabajadorComuna
			
			diat.direccionTrabajadorRestoDireccion = params.direccionTrabajadorRestoDireccion
			
			diat.telefonoTrabajador = "".equals(params.telefonoTrabajador) ? null : Integer.parseInt(params.telefonoTrabajador)
			
			diat.fechaIngresoEmpresa = "".equals(params.fechaIngresoEmpresa) ? null : params.fechaIngresoEmpresa 
			diat.etnia = params.etnia
			diat.otroPueblo = params.otroPueblo
			diat.duracionContrato = params.duracionContrato
			diat.tipoRemuneracion = params.tipoRemuneracion
			diat.categoriaOcupacion = params.categoriaOcupacion
			diat.profesionTrabajador = params.profesionTrabajador
			if(params?.sistemaSalud)
				diat.sistemaSalud = SistemaSalud.findByCodigo(params?.sistemaSalud)
			
		}
		if (!diat.fechaAccidente) {
			if(sdaat?.cuestionarioOrigenTrayecto?.pregunta1)
				diat.fechaAccidente = sdaat?.cuestionarioOrigenTrayecto?.pregunta1
			else
				diat.fechaAccidente = sdaat?.fechaSiniestro
		}
		
		//si no es empleador validar el caso de que si esta vacio no realizar validacion
		def tipoDenunciante = params?.tipoDenunciante
		if(diat?.nTrabajadoresHombre && diat?.nTrabajadoresMujer){
			log.info("Inicializando validacion numero de trabajadores")
			log.info("Cantidad trabajadores : ${diat?.nTrabajadoresHombre} + ${diat?.nTrabajadoresMujer}")
			def numeroTrabajadores = diat.nTrabajadoresHombre + diat.nTrabajadoresMujer
			if(numeroTrabajadores < 1){
				log.info("Cantidad de trabajadores debe ser superior 0 : ${diat.hasErrors()}")
				diat.errors.rejectValue("nTrabajadoresHombre",
					"cl.adexus.isl.spm.DIAT.numeroTrabajadores.invalid")
			}
		}
		
		if(diat?.nTrabajadoresHombre == 0 || diat?.nTrabajadoresMujer == 0){
			log.info("Inicializando validacion numero de trabajadores denunciante distinto a empleador")
			def numeroTrabajadores = (diat?.nTrabajadoresHombre?:0) + (diat?.nTrabajadoresMujer?:0)
			log.info("Cantidad de trabajadores : $numeroTrabajadores")
			if(numeroTrabajadores < 1){
				log.info("Cantidad de trabajadores debe ser superior a 0 (en caso de permitir nulos en cantidad de trabajadores hombre y mujer)")
				diat.errors.rejectValue("nTrabajadoresHombre",
					"cl.adexus.isl.spm.DIAT.numeroTrabajadores.invalid")
			}
		}
		
		if(diat?.fechaIngresoEmpresa){
			if(diat.fechaAccidente < diat.fechaIngresoEmpresa){
				log.debug("Registrando error fecha accidente inferior a fecha de ingreso a empresa")
				diat.errors.rejectValue(
					'fechaAccidente',
					'cl.adexus.isl.spm.DIAT.fechaIngresoEmpresa.fechaAccidente.invalid')
			
			}
		}
	
		log.debug("Validando que fecha de siniestro sea superior a fecha de nacimiento")
		if(sdaat?.fechaSiniestro <= diat.trabajador.fechaNacimiento){
			log.debug("Registrando error fecha accidente inferior a fecha de ingreso a empresa")
			diat.errors.rejectValue(
				'fechaIngresoEmpresa',
				'cl.adexus.isl.spm.DIAT.fechaSiniestro.fechaNacimiento.invalid')
		}
		
		log.debug("Validando que fecha de ingreso a empresa sea superior a fecha de nacimiento")
		if(diat?.fechaIngresoEmpresa){
			if(diat.fechaIngresoEmpresa <= diat.trabajador.fechaNacimiento){
				log.debug("Registrando error fecha accidente inferior a fecha de ingreso a empresa")
				diat.errors.rejectValue(
					'fechaIngresoEmpresa',
					'cl.adexus.isl.spm.DIAT.fechaIngresoEmpresa.fechaNacimiento.invalid')
			}
		}
		
		
		
		
		// Validaciones
		def errores = []
		log.info("Verificando si existen errores : ")
		if(diat.hasErrors()){
			errores = diat.errors
			diat.errors.each{error->
				log.info("--> $error")
			}
			//errores = diat.errors
			log.info("Verificando si existen errores en trabajador asociado a DIAT")
			if(diat.trabajador.hasErrors() || !diat.trabajador.validate()){
				diat.trabajador.errors.each{terror->
					log.info("--> $terror")
					errores << terror
				}
			}else{
				log.info("No existen errores en trabajador asociado a DIAT")
			}
			
			return (['next': [action:'dp02'],  model: ['empleador': params.empleador, 'trabajador': params.trabajador, 'diat': diat, errores:errores]])
		}
		
		if (!params.empleador.validate() || !params.trabajador.validate() || !diat.validate()) {
			//Hay algo malo en los datos
			log.debug("Existen errores : ")
			log.debug("--> Empleador : ${params?.empleador.errors}")
			log.debug("--> Trabajador : ${params?.trabajador.errors}")
			log.debug("--> DIAT : ${diat.empleador.errors}")
			params.empleador.errors.each{error-> errores << error }
			params.trabajador.errors.each{error-> errores << error }
			diat.errors.each{error-> errores << error }			
			
			return (['next': [action:'dp02'],  model: ['empleador': params.empleador, 'trabajador': params.trabajador, 'diat': diat, errores:errores]])
		}
		
		
		params.empleador.save()
		sdaat.empleador=params.empleador
		params.trabajador.save()
		sdaat.trabajador=params.trabajador
		
		diat.save()
		sdaat.diat=diat
		sdaat.save()
		return (['next': [action:'dp02_2'],  model: ['diat': diat, errores:errores]])
	}
	

	def diatCU02_2(params,SDAAT sdaat){
		log.info("")
		log.info "params:"+params

		def diat=sdaat.diat
		
		diat.clearErrors()
		
		diat.esAccidenteTrayecto=FormatosISLHelper.booleanString(params.esAccidenteTrayecto_h)
		log.info("Es de Trayecto : " + diat.esAccidenteTrayecto)
				
		diat.direccionAccidenteNombreCalle=params.direccionAccidenteNombreCalle
		diat.direccionAccidenteComuna=Comuna.findByCodigo(params.direccionAccidenteComuna)
		diat.que=params.que
		diat.lugarAccidente=params.lugarAccidente
		diat.como=params.como
		diat.trabajoHabitualCual=params.trabajoHabitualCual
		diat.esTrabajoHabitual=FormatosISLHelper.booleanString(params.esTrabajoHabitual)
		diat.gravedad=CriterioGravedad.findByCodigo(params.gravedad)
		
		diat.tipoAccidenteTrayecto=TipoAccidenteTrayecto.findByCodigo(params.tipoAccidenteTrayecto)
		diat.medioPrueba=TipoMedioPruebaAccidente.findByCodigo(params.medioPrueba)
		diat.detallePrueba=params.detallePrueba
		
		//diat.calificacionDenunciante=CalificacionDenunciante.findByCodigo(sdaat?.diat?.calificacionDenunciante.codigo)
		diat.calificacionDenunciante=sdaat?.tipoDenunciante
		
		def denunciante=PersonaNatural.findByRun(params.denunciante_run)
		denunciante.nombre=params.denunciante_nombre
		denunciante.apellidoPaterno=params.denunciante_apellidoPaterno
		denunciante.apellidoMaterno=params.denunciante_apellidoMaterno
		denunciante.save()
		diat.denunciante=denunciante
		sdaat.denunciante=denunciante
		
		if(sdaat.tipoDenunciante.codigo == '2'){ //Trabajador
			sdaat?.diat?.trabajador?.nombre=params.denunciante_nombre
			sdaat?.diat?.trabajador?.apellidoPaterno=params.denunciante_apellidoPaterno
			sdaat?.diat?.trabajador?.apellidoMaterno=params.denunciante_apellidoMaterno
		}
		diat.telefonoDenunciante=params.telefonoDenunciante

		/*
		 * Agregamos criterios de validacion para la hora del accidente		
		 */
		
		try{
			diat.fechaAccidente = FechaHoraHelper.horaToDate(params.fechaAccidente_hora,diat.fechaAccidente) //Hora del accidente
		}catch (Exception e){
			diat.errors.rejectValue("fechaAccidente", "hora.mala",e.toString())
		}
		
		try{
			diat.horaIngreso = FechaHoraHelper.horaToDate(params.horaIngreso,sdaat.fechaSiniestro) //Hora del accidente
		}catch (Exception e){
			diat.errors.rejectValue("horaIngreso", "hora.mala",e.toString())
		}
		try{
			diat.horaSalida = FechaHoraHelper.horaToDate(params.horaSalida,sdaat.fechaSiniestro) //Hora del accidente
		}catch (Exception e){
			diat.errors.rejectValue("horaSalida", "hora.mala",e.toString())
		}		
		
		// Validar el detalle de medio de prueba
		if (params.medioPrueba && !params.detallePrueba) {
			diat.errors.reject('cl.adexus.isl.spm.DIAT.detallePrueba.invalid')
		}
		
		// validar antes de guardar y si no valida devolver al formulario
		if (!diat.validate()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			return (['next': [action:'dp02_2'],  model: ['diat': diat]])
		}
		
		diat.save()
		sdaat.diat=diat
		sdaat.save();
		
		return (['next': [action:'dp03']])
		
	}

	def diatCU02_2BackCU02(params,SDAAT sdaat){
		log.info "params: diatCU02_2BackCU02 :: " + params

		def diat=sdaat.diat
		
		diat.clearErrors()
		
		diat.esAccidenteTrayecto=FormatosISLHelper.booleanString(params.esAccidenteTrayecto_h)
		log.info("Es de Trayecto : " + diat.esAccidenteTrayecto)
				
		diat.direccionAccidenteNombreCalle=params.direccionAccidenteNombreCalle
		diat.direccionAccidenteComuna=Comuna.findByCodigo(params.direccionAccidenteComuna)
		diat.que=params.que
		diat.lugarAccidente=params.lugarAccidente
		diat.como=params.como
		diat.trabajoHabitualCual=params.trabajoHabitualCual
		diat.esTrabajoHabitual=FormatosISLHelper.booleanString(params.esTrabajoHabitual)
		diat.gravedad=CriterioGravedad.findByCodigo(params.gravedad)
		
		diat.tipoAccidenteTrayecto=TipoAccidenteTrayecto.findByCodigo(params.tipoAccidenteTrayecto)
		diat.medioPrueba=TipoMedioPruebaAccidente.findByCodigo(params.medioPrueba)
		diat.detallePrueba=params.detallePrueba
		
		//diat.calificacionDenunciante=CalificacionDenunciante.findByCodigo(sdaat?.diat?.calificacionDenunciante.codigo)
		diat.calificacionDenunciante=sdaat?.tipoDenunciante
		
		def denunciante=PersonaNatural.findByRun(params.denunciante_run)
		denunciante.nombre=params.denunciante_nombre
		denunciante.apellidoPaterno=params.denunciante_apellidoPaterno
		denunciante.apellidoMaterno=params.denunciante_apellidoMaterno
		
		//denunciante.save()
		
		diat.denunciante=denunciante
		sdaat.denunciante=denunciante
		
		if(sdaat.tipoDenunciante.codigo == '2'){ //Trabajador
			sdaat?.diat?.trabajador?.nombre=params.denunciante_nombre
			sdaat?.diat?.trabajador?.apellidoPaterno=params.denunciante_apellidoPaterno
			sdaat?.diat?.trabajador?.apellidoMaterno=params.denunciante_apellidoMaterno
		}
		diat.telefonoDenunciante=params.telefonoDenunciante

		try{
			diat.fechaAccidente = FechaHoraHelper.horaToDate(params.fechaAccidente_hora,diat.fechaAccidente) //Hora del accidente
		}catch (Exception e){
			//diat.errors.rejectValue("fechaAccidente", "hora.mala",e.toString())
		}
		
		try{
			diat.horaIngreso = FechaHoraHelper.horaToDate(params.horaIngreso,sdaat.fechaSiniestro) //Hora del accidente
		}catch (Exception e){
			//diat.errors.rejectValue("horaIngreso", "hora.mala",e.toString())
		}
		try{
			diat.horaSalida = FechaHoraHelper.horaToDate(params.horaSalida,sdaat.fechaSiniestro) //Hora del accidente
		}catch (Exception e){
			//diat.errors.rejectValue("horaSalida", "hora.mala",e.toString())
		}

		
		//diat.save()
		sdaat.diat=diat
		
		//session['sdaat'] = sdaat
		
		return (['next': [action:'dp02']])
		
	}

	def diatGenPdf(params,SDAAT sdaat,boolean esBorrador){
		log.info("Ejecutando diatGenPdf")
		def FormatosISLHelper f=new FormatosISLHelper()
		byte[] b
		
		def tipoPDF = Constantes.TIPOS_PDF_DIAT
		def DiatPDF dataPDF = new DiatPDF()
		
		//Mapear todos los datos del sdaat y sdaat.diat a dataPDF
		log.info("Verificando si corresponde a una diat definitiva o a un borrador")
		if(esBorrador){
			dataPDF.setEsBorrador('BORRADOR documento no tiene validez legal');
			dataPDF.setEsBorradorFirma(' ')
		}else{
			dataPDF.setEsBorrador(' ');
			dataPDF.setEsBorradorFirma('Firma denunciante')
		}
		log.info("Corresponde a una diat ${esBorrador ? 'borrador' : 'definitiva'}")
		log.info("Verificacion finalizada")
		// Se refresca campo de siniestro para obtener cun
		log.info("Seteando datos de la denuncia para generar la diat")
		dataPDF.setCodigoCUN(sdaat?.siniestro?.cun);
		dataPDF.setFechaEmision(f.fechaCorta(sdaat?.diat?.fechaEmision))
		dataPDF.setNumeroFolio(sdaat?.diat?.id.toString())
		dataPDF.setEmpresaRut(f.run(sdaat?.diat?.empleador?.rut));
		dataPDF.setEmpresaRazonSocial(sdaat?.diat?.empleador?.razonSocial);
		dataPDF.setEmpresaDireccion(f.direccionCompletaEmpleador(sdaat?.diat));
		dataPDF.setEmpresaReferencias(sdaat?.diat?.direccionEmpleadorRestoDireccion);
		dataPDF.setEmpresaComuna(sdaat?.diat?.direccionEmpleadorComuna?.descripcion);
		dataPDF.setEmpresaTelefono(sdaat?.diat?.telefonoEmpleador?.toString());
		dataPDF.setEmpresaPropiedad(sdaat?.diat?.propiedadEmpresa?.descripcion);
		dataPDF.setEmpresaPropiedad_2(sdaat?.diat?.tipoEmpresa?.descripcion);
		dataPDF.setEmpresaActividadEconomica(sdaat?.diat?.ciiuEmpleador?.descripcion);
		dataPDF.setEmpresaActividadEconomicaSecundaria(sdaat?.diat?.ciiuPrincipal?.descripcion);
		dataPDF.setEmpresaNroTrabajadoresHombres(sdaat?.diat?.nTrabajadoresHombre?.toString());
		dataPDF.setEmpresaNroTrabajadoresMujeres(sdaat?.diat?.nTrabajadoresMujer?.toString());
		dataPDF.setTrabajadorRun(f.run(sdaat?.diat?.trabajador?.run));
		dataPDF.setTrabajadorNombresApellidos(f.nombreCompleto(sdaat?.diat?.trabajador));
		dataPDF.setTrabajadorDireccion(f.direccionCompletaTrabajador(sdaat?.diat));
		dataPDF.setTrabajadorReferencias(sdaat?.diat?.direccionTrabajadorRestoDireccion);
		dataPDF.setTrabajadorComuna(sdaat?.diat?.direccionTrabajadorComuna?.descripcion);		
		dataPDF.setTrabajadorTelefono(sdaat?.diat?.telefonoTrabajador?.toString());
		dataPDF.setTrabajadorSexo(sdaat?.diat?.trabajador?.sexo == 'M' ? 'Masculino' : 'Femenino');
		dataPDF.setTrabajadorFechaNacimiento(f.fechaCorta(sdaat?.diat?.trabajador?.fechaNacimiento));
		dataPDF.setTrabajadorNacionalidad(sdaat?.diat?.nacionalidadTrabajador?.descripcion);
		
		if ("OTRO".equals(sdaat?.diat?.etnia?.descripcion?.toUpperCase()))
			dataPDF.setTrabajadorPuebloOriginario(sdaat?.diat?.otroPueblo);
		else
			dataPDF.setTrabajadorPuebloOriginario(sdaat?.diat?.etnia?.descripcion);
		
			
		dataPDF.setTrabajadorProfesion(sdaat?.diat?.profesionTrabajador);
		dataPDF.setTrabajadorFechaIngreso(f.fechaCorta(sdaat?.diat?.fechaIngresoEmpresa));
		dataPDF.setTrabajadorTipoContrato(sdaat?.diat?.duracionContrato?.descripcion);
		dataPDF.setTrabajadorTipoIngreso(sdaat?.diat?.tipoRemuneracion?.descripcion);
		dataPDF.setTrabajadorCatOcupacional(sdaat?.diat?.categoriaOcupacion?.descripcion);
		dataPDF.setAccidenteFecha(f.fechaCorta(sdaat?.diat?.fechaAccidente));
		dataPDF.setAccidenteHora(f.horaCorta(sdaat?.diat?.fechaAccidente));
		dataPDF.setAccidenteHoraIngresoTrabajo(f.horaCorta(sdaat?.diat?.horaIngreso));
		dataPDF.setAccidenteHoraSalidaTrabajo(f.horaCorta(sdaat?.diat?.horaSalida));
		dataPDF.setAccidenteDireccion(sdaat?.diat?.direccionAccidenteNombreCalle);
		dataPDF.setAccidenteReferencias("");
		dataPDF.setAccidenteComuna(sdaat?.diat?.direccionAccidenteComuna?.descripcion);
		dataPDF.setAccidenteClasificacion(sdaat?.diat?.gravedad?.descripcion);
		dataPDF.setAccidenteTipo(sdaat?.diat?.esAccidenteTrayecto ? "Trayecto" : "Trabajo");
		if (sdaat?.diat?.esAccidenteTrayecto)
			dataPDF.setAccidenteTrayecto(sdaat?.diat?.tipoAccidenteTrayecto?.descripcion);
		dataPDF.setAccidenteMedioPrueba(sdaat?.diat?.medioPrueba?.descripcion);
		dataPDF.setAccidenteDetalleMedioPrueba(sdaat?.diat?.detallePrueba);
		dataPDF.setAccidenteQueEstaba(sdaat?.diat?.que);
		dataPDF.setAccidenteLugar(sdaat?.diat?.lugarAccidente);
		dataPDF.setAccidenteDescripcion	(sdaat?.diat?.como);
		dataPDF.setAccidenteTrabajoHabitual(sdaat?.diat?.trabajoHabitualCual);
		dataPDF.setAccidenteLoDesarrollaba(sdaat?.diat?.esTrabajoHabitual == null ? '' : (sdaat?.diat?.esTrabajoHabitual ? 'Si' : 'No'));
		dataPDF.setDenuncianteRun(f.run(sdaat?.diat?.denunciante?.run));
		dataPDF.setDenuncianteNombresApellidos(sdaat?.diat?.denunciante?.nombre + " "  + sdaat?.diat?.denunciante?.apellidoPaterno + " "  + sdaat?.diat?.denunciante?.apellidoMaterno);
		dataPDF.setDenuncianteClasificacion(sdaat?.diat?.calificacionDenunciante?.descripcion);
		dataPDF.setDenuncianteTelefono(sdaat?.diat?.telefonoDenunciante?.toString());
		log.info("Seteo de datos para pdf finalizado")
		
		ByteArrayOutputStream pdf = PDFService.doPdf(dataPDF, tipoPDF)
		b = pdf.toByteArray();
		return b;
	}
	
	def diatR04(params, SDAAT sdaat){
		log.info("Ejecutando metodo diatR04")
		//Validar si existe denuncia firmada. Pero solo si el usuario no es prestador.
		log.info("Verificando si el usuario posee rol prestador ")
		if(!SecurityUtils.getSubject().hasRole("prestador")){	
			log.debug("Usuario tiene rol prestador")
			log.debug("Verificando existencia de denuncia en documentacion adicional")
			def denuncia = DocumentacionAdicional.findByDenunciaAT(sdaat.diat)
			if (!denuncia) {
				log.debug("Denuncia no existe")
				log.debug("Creando documentacion adicional vacio para asignarle errores")
				denuncia = new DocumentacionAdicional()
				denuncia.errors.reject('cl.adexus.isl.spm.DocumentacionAdicional.denunciaDiat.null')
				log.debug("Redireccionando a accion dp04")
				return (['next': [action:'dp04'],  model: ['denuncia': denuncia]])
			}
		}
		
		
		// Procesa la diat, guardando la diat en el siniestro si es que no existe
		//sdaat.save();
		log.info("Verificando existencia de siniestro en sdaat")
		def siniestro
		if(sdaat.siniestro){
			log.debug("Siniestro existe")
			siniestro=sdaat.siniestro
		}else{
			//Si no existe se crea uno nuevo
			log.debug("Siniestro no existe")
			log.debug("Creando siniestro")
			siniestro = new Siniestro()
			siniestro.setFecha(sdaat.fechaSiniestro)
			siniestro.setTrabajador(sdaat.diat.trabajador)
			siniestro.setEmpleador(sdaat.diat.empleador)
			siniestro.setRelato(sdaat.diat.como)
			siniestro.setEsEnfermedadProfesional(false)
			siniestro.setUsuario(sdaat.usuario)
			siniestro.save()
			log.debug("Siniestro creado con exito")
		}
		
		//Asociamos el siniestro a 77bis
		log.info("Verificando existencia de ingreso bis")
		if (params['bisId']){
			log.info "Asociando el siniestro "+siniestro+" a 77bis "+params['bisId']
			def bis = Bis.findById(params['bisId'])
			bis.siniestro = siniestro
			bis.save()
			log.info("Asociacion de siniestro a bis exitosa : $siniestro")
		}
		
		
		sdaat.diat.siniestro=siniestro
		sdaat.siniestro=siniestro
		//sdaat.diat.save()
		log.info("Guardando cambios en sdaat")
		sdaat.save()
		log.info("Cambios en sdaat guardados exitosamente")
		
		// Envia a SUSESO
		log.info("Enviando DIAT a SUSESO")
		SUSESOService.enviarDIAT(sdaat.diat)
		log.info("Envio finalizado")
		
		log.info("Obteniendo valor de retorno desde el xml recibido desde SUSESO")
		def retorno = ""
		retorno = SUSESOService.getRetorno(sdaat.diat.xmlRecibido)
		log.info("SUSESO service retorno el valor : $retorno")
		def mensaje = "valor de retorno $retorno : ${RetornoWSEnum.findByKey(retorno?.text())?.getDescripcion()}"
		log.debug(mensaje)
		log.info("Obtencion de valor de retorno desde xml recibido desde SUSESO finalizada")
		
		// Luego de procesar la diat, va a dp05
		log.info("Redireccionando a accion dp05")
		return (['next': [action:'dp05', mensaje:mensaje]])
	}

// ------------- OPA --------------

	// Busca OPA previa para el Siniestro
	def opaR01(params,SDAAT sdaat){
		def opaPrevia = Siniestro.get(sdaat.siniestro.id).getOpa()
		if(opaPrevia){
			sdaat.opa=opaPrevia
			return ([next: [action:'dp03']]) // Encuentra OPA 
		}else{
			return ([next: [action:'dp01']]) // No encuentra OPA
		}
	}

	// 
	def opaR02(params,SDAAT sdaat){
	// Emisión de la OPA, incluyendo guardarla.
		params['centroAtencion']=CentroSalud.findById(params['centroAtencion'])
		//Inicializa la OPA
	 	def opa = new OPA(params)
	 	opa.fechaCreacion = new Date()
		opa.inicioVigencia = new Date() // TODO: O definir si se usa sdaat.fechaSiniestro
		opa.duracionDias = 60 // TODO: Parametrizar la duración en dias de la OPA
		if(sdaat.diat?.direccionTrabajadorNombreCalle!=null && sdaat.diat?.direccionTrabajadorNumero!=null) 
			opa.direccionTrabajador = sdaat.diat?.direccionTrabajadorNombreCalle+" "+sdaat.diat?.direccionTrabajadorNumero
		opa.comunaTrabajador = sdaat.diat?.direccionTrabajadorComuna
		opa.telefonoTrabajador= sdaat.diat?.telefonoTrabajador
		opa.usuarioEmisor = SecurityUtils.subject?.principal
		opa.siniestro=sdaat.siniestro //Esto porque?
		if(!opa.validate()){ // TODO:Excepción
			// Hay algo malo en los datos y no se puede crear la opa
			return (['next': [action: 'dp02'],  model: ['opa': opa]])
		}
		opa.save(flush: true)
		//Agregamos al SDAAT
		sdaat.opa=opa
		sdaat.save(flush: true)
		
		//Agregamos al Siniestro
		def s=Siniestro.get(opa.siniestro.id)
		s.opa=opa;
		s.save(flush:true)
		
		return ([next: [action: 'dp03']])
	}
		
	def opaR03(params,SDAAT sdaat){
		
		//Reattachar para evitar el lazy error o como se llame
		if (!sdaat.opa.isAttached()) {
			sdaat.opa.attach()
		}
	
		return DenunciaService.getOP([id: sdaat.opa.id, tipo: Constantes.TIPOS_PDF_OPA]);
	}
	
	
}
