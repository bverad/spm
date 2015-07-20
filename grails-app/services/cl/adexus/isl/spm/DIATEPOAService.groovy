package cl.adexus.isl.spm

import java.text.DateFormat
import java.text.SimpleDateFormat

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.helpers.FormatosHelper;
import cl.adexus.isl.spm.enums.RetornoWSEnum;

class DIATEPOAService {
	
	def SUSESOService
	def UsuarioService
	def CalOrigenService
	def BISIngresoService
	def bpmPass='1234';
	
	/**
	 * Busca los siniestros que no se han enviado a SUSESO
	 *
	 */
	def r01(username){
		def usuarios = UsuarioService.getUsuariosRelacionados(username);
		usuarios.add(username); //Sus sinistros tambien son de el ;)
				
		log.info("Buscando siniestros sin OA para los usuarios:"+usuarios)
		def siniestros=Siniestro.executeQuery(
			"select s "+
			"  from Siniestro s LEFT OUTER JOIN s.diatOA diatOA LEFT OUTER JOIN s.diepOA diepOA"+
			" where ((diatOA is null and diepOA is null) or (diatOA.xmlEnviado is null and diepOA.xmlEnviado is null))"+
			"   and s.usuario in (:users)",[users: usuarios]);
		return (['next':[action: 'dp01'],  model: ['siniestros': siniestros]])
	}
	
	
	/**
	 * Arma la denuncia propuesta para el siniestro elegido
	 *
	 */
	def r02(params){
		log.info("Ejecutando action r02")
		log.info("Datos recibidos : ${params}")
		//Codigo de Denunciante OA
		def calDenOA=CalificacionDenunciante.findByCodigo('7')
		if (!params['id']) {
			return (['next':[action: 'dp01']])			
		}
		//Buscamos el siniestro
		def siniestro=Siniestro.get(params['siniestroId']?:params['id'])
		log.info("Valor siniestro $siniestro")
		//Vemos si el siniestro es de EP o de AT
		if(siniestro.esEnfermedadProfesional){
			def diepOAPropuesta = DIEP.findByCalificacionDenuncianteAndSiniestro(calDenOA, siniestro)
			if (!diepOAPropuesta) {
				diepOAPropuesta = new DIEP()
				diepOAPropuesta.calificacionDenunciante=calDenOA
				//Buscamos las DIEP
				def dieps=DIEP.findAllBySiniestro(siniestro)
				if(dieps.size()>0){
					// Arma diep propuesta
					def diepEmp, diepTrab, diepOtro
					for (diep in dieps) {
						if (diep.calificacionDenunciante.codigo == 1) diepEmp = diep
						else if (diep.calificacionDenunciante.codigo == 2) diepTrab = diep
						else diepOtro = diep
					}
					diepOAPropuesta=getDIEPPropuesta(diepEmp, diepTrab, diepOtro)
					if (diepEmp)
						diepOAPropuesta.empleador= diepEmp.empleador
					else
						diepOAPropuesta.empleador= getDIEPPropuesta(diepEmp, diepTrab, diepOtro).empleador
					diepOAPropuesta.trabajador=getDIEPPropuesta(diepEmp, diepTrab, diepOtro).trabajador
				}
				diepOAPropuesta.siniestro=siniestro
			}
			return (['next':[action: 'dp02ep'],  model: ['diepOAPropuesta': diepOAPropuesta]])
		}else{
			def diatOAPropuesta = DIAT.findByCalificacionDenuncianteAndSiniestro(calDenOA, siniestro)
			if (!diatOAPropuesta) {
				diatOAPropuesta=new DIAT()
				diatOAPropuesta.calificacionDenunciante=calDenOA
				//Buscamos las DIAT
				def diats=DIAT.findAllBySiniestro(siniestro)
				if(diats.size()>0){
					//diatOAPropuesta=diats[0]
					def diatEmp, diatTrab, diatOtro
					for (diat in diats) {
						if (diat.calificacionDenunciante.codigo == 1) diatEmp = diat
						else if (diat.calificacionDenunciante.codigo == 2) diatTrab = diat
						else diatOtro = diat
					}
					diatOAPropuesta=getDIATPropuesta(diatEmp, diatTrab, diatOtro)
					if (diatEmp)
						diatOAPropuesta.empleador= diatEmp.empleador
					else
						diatOAPropuesta.empleador= getDIATPropuesta(diatEmp, diatTrab, diatOtro).empleador
					diatOAPropuesta.trabajador=getDIATPropuesta(diatEmp, diatTrab, diatOtro).trabajador
				}
				diatOAPropuesta.siniestro=siniestro
			}
			return (['next':[action: 'dp02at'],  model: ['diatOAPropuesta': diatOAPropuesta]])			
		}		
	}

	/**
	 * Retorna la DIEP propuesta
	 * @param diepEmp
	 * @param diepTrab
	 * @param diepOtro
	 * @return
	 */
	def private getDIEPPropuesta(diepEmp, diepTrab, diepOtro) {
		if (diepTrab)
			return diepTrab
		if (diepEmp)
			return diepEmp
		if (diepOtro)
			return diepOtro
	}

	/**
	 * Retorna la DIAT propuesta
	 * @param diatEmp
	 * @param diatTrab
	 * @param diatOtro
	 * @return
	 */
	def private getDIATPropuesta(diatEmp, diatTrab, diatOtro) {
		if (diatTrab)
			return diatTrab
		if (diatEmp)
			return diatEmp
		if (diatOtro)
			return diatOtro
	}

	/**
	 * Entrega las alternativas para una variable y en que tipo de DIAT/DIEP estaba
	 *
	 */
	def alternativas(params){
		def varName = params['varName']
		def sinId = params['siniestroId'] 
		//Buscamos el siniestro
		def siniestro=Siniestro.get(sinId)
		//Vemos si el siniestro es de EP o de AT
		if(siniestro.esEnfermedadProfesional){
			def alts=[]
			//Buscamos las DIEP
			def dieps=DIEP.findAllBySiniestro(siniestro)
			dieps.each() {
				def tipo = it.calificacionDenunciante.descripcion
				def valor = it.properties[varName]
				def s = [nombre: varName, tipo: tipo, valor: valor]
				alts.add(s)
			}
			return alts
		}else{
			def alts=[]
			//Buscamos las DIAT
			def diats=DIAT.findAllBySiniestro(siniestro)
			diats.each() {
				def tipo = it.calificacionDenunciante.descripcion
				def valor = it.properties[varName]
				def s = [nombre: varName, tipo: tipo, valor: valor]
				alts.add(s)
			}
			return alts
		}
	}

	def r03(params, dixx, guardadoTemporal){
		log.info("Ejecutando accion r03")
		log.info("Datos obtenidos : $params")
		log.info("--> dixx : $dixx")
		log.info("--> guardadoTemporal : $guardadoTemporal" )
		def siniestro						= Siniestro.get(params['siniestroId']?:params['id'])
		def diatOA 							= siniestro?.diatOA
		log.info("Valor siniestro : $siniestro")
		if (!diatOA) { diatOA = new DIAT() }
		def diepOA = siniestro?.diepOA
		if (!diepOA) { diepOA = new DIEP() }
		params['empleador_rut']				= dixx.empleador_rut//((String)params['empleador_rut']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		params['trabajador_run']			= dixx.trabajador_run//((String)params['trabajador_run']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		def direccionEmpleadorTipoCalle 	= TipoCalle.findByCodigo(params["direccionEmpleadorTipoCalle"])
		def direccionEmpleadorComuna 		= Comuna.findByCodigo(params["direccionEmpleadorComuna"])
		def propiedadEmpresa				= TipoPropiedadEmpresa.findByCodigo(params["propiedadEmpresa"])
		def tipoEmpresa 					= TipoEmpresa.findByCodigo(params["tipoEmpresa"])
		def nacionalidadTrabajador 			= Nacion.findByCodigo(params["nacionalidadTrabajador"])
		def direccionTrabajadorTipoCalle	= TipoCalle.findByCodigo(params["direccionTrabajadorTipoCalle"])
		def direccionTrabajadorComuna 		= Comuna.findByCodigo(params["direccionTrabajadorComuna"])
		def etnia 							= Etnia.findByCodigo(params["etnia"])
		def duracionContrato 				= TipoDuracionContrato.findByCodigo(params["duracionContrato"])
		def tipoRemuneracion 				= TipoRemuneracion.findByCodigo(params["tipoRemuneracion"])
		def categoriaOcupacion 				= CategoriaOcupacion.findByCodigo(params["categoriaOcupacion"])
		def direccionAccidenteComuna 		= Comuna.findByCodigo(params["direccionAccidenteComuna"])
		def gravedad 						= CriterioGravedad.findByCodigo(params["gravedad"])
		def tipoAccidenteTrayecto 			= TipoAccidenteTrayecto.findByCodigo(params["tipoAccidenteTrayecto"])
		def medioPrueba 					= TipoMedioPruebaAccidente.findByCodigo(params["medioPrueba"])
		log.info 'empleador_rut: ' + params['empleador_rut']
		log.info 'trabajador_run: ' + params['trabajador_run']
		log.info 'guardadoTemporal: ' + guardadoTemporal
		
		//Vemos si el siniestro es de EP o de AT
		if(!siniestro?.esEnfermedadProfesional){
			log.info("siniestro accidente del trabajo")
			def empleador
			if (PersonaJuridica.findByRut(params.empleador_rut)) {
				empleador=PersonaJuridica.findByRut(params.empleador_rut)
			} else {
				empleador = new PersonaJuridica()
				empleador.rut=params.empleador_rut
			}
			empleador.nombreFantasia=params.empleador_razonSocial
			empleador.razonSocial=params.empleador_razonSocial
			empleador.save()
			diatOA.empleador=empleador
			diatOA.fechaEmision=FechaHoraHelper.hace10minutos();
			if (params.ciiuPrincipal)
				diatOA.ciiuPrincipal=TipoActividadEconomica.findByCodigo(params.ciiuPrincipal)
			if (params.ciiuEmpleador)
				diatOA.ciiuEmpleador=TipoActividadEconomica.findByCodigo(params.ciiuEmpleador)
			diatOA.direccionEmpleadorTipoCalle = direccionEmpleadorTipoCalle
			diatOA.direccionEmpleadorNombreCalle=params.direccionEmpleadorNombreCalle
			diatOA.direccionEmpleadorNumero="".equals(params.direccionEmpleadorNumero) ? null : Integer.parseInt(params.direccionEmpleadorNumero)
			diatOA.direccionEmpleadorRestoDireccion=params.direccionEmpleadorRestoDireccion
			diatOA.direccionEmpleadorComuna = direccionEmpleadorComuna
			diatOA.telefonoEmpleador="".equals(params.telefonoEmpleador) ? null : Integer.parseInt(params.telefonoEmpleador)
			diatOA.nTrabajadoresHombre=params.nTrabajadoresHombre.toInteger()
			diatOA.nTrabajadoresMujer=params.nTrabajadoresMujer.toInteger()
			diatOA.propiedadEmpresa=propiedadEmpresa
			diatOA.tipoEmpresa=tipoEmpresa

			def trabajador
			def trabajadorErrors = []
			
			if(PersonaNatural.findByRun(params.trabajador_run)){
				trabajador=PersonaNatural.findByRun(params.trabajador_run)
			}else{
				trabajador = new PersonaNatural()
				trabajador.run=params.trabajador_run
			}
			trabajador.nombre=params.trabajador_nombre
			trabajador.apellidoPaterno=params.trabajador_apellidoPaterno
			trabajador.apellidoMaterno=params.trabajador_apellidoMaterno
			trabajador.sexo=params.trabajador_sexo
			trabajador.fechaNacimiento=FechaHoraHelper.stringToDate(params.trabajador_fechaNacimiento)
			
			//para comparacion con fecha de nacimiento
			diatOA.fechaIngresoEmpresa=FechaHoraHelper.stringToDate(params.fechaIngresoEmpresa)
			def t = new PersonaNatural()
			log.info("Inicializando validacion de total de trabajadores")
			def totalTrabajadores = diatOA.nTrabajadoresHombre + diatOA.nTrabajadoresMujer
			if(totalTrabajadores < 1){
				log.info("Cantidad de trabajadores debe ser superior 0")
				t.errors.reject("cl.adexus.isl.spm.diatOA.numeroTrabajadores.fail",
					["0"] as Object[],
					"[El total de trabajadores debe ser superior a [{0}]]")
			}
			
			log.info("Inicializando validacion de fecha ingreso a empresa y fecha de nacimiento")
			if(diatOA.fechaIngresoEmpresa < trabajador.fechaNacimiento){
				log.info("fecha ingreso empresa [${diatOA.fechaIngresoEmpresa}] menor a fecha de nacimiento del trabajador [${trabajador.fechaNacimiento}]")
				t.errors.reject("cl.adexus.isl.spm.diatOA.fechaIngresoEmpresa.fechaNacimiento.fail",
							["${String.format('%td/%<tm/%<tY', diatOA.fechaIngresoEmpresa)}", "${String.format('%td/%<tm/%<tY', trabajador.fechaNacimiento)}"] as Object[],
							"[La fecha de ingreso a la empresa del trabajador [{0}] no puede ser menor a su fecha de nacimiento [{1}]]")
				
			}else
				log.info("validacion de fecha ingreso a empresa y fecha de nacimiento completa")
			log.info("Validacion de fecha ingreso a empresa y fecha de nacimiento finalizada")
			
			Date fechaAccidente=FechaHoraHelper.stringToDate(params.fechaAccidente)
			fechaAccidente=FechaHoraHelper.horaToDate(params.fechaAccidente_hora, fechaAccidente)
			Date horaIngreso=FechaHoraHelper.horaToDate(params.horaIngreso)
			Date horaSalida=FechaHoraHelper.horaToDate(params.horaSalida)
			diatOA.fechaAccidente=fechaAccidente

			
			
			if(diatOA.fechaAccidente < trabajador.fechaNacimiento){
				log.info("fecha accidente [${diatOA.fechaAccidente}] menor a fecha de nacimiento del trabajador [${trabajador.fechaNacimiento}]")
				t.errors.reject("cl.adexus.isl.spm.diatOA.fechaAccidente.fechaNacimiento.fail",
							["${String.format('%td/%<tm/%<tY', diatOA.fechaIngresoEmpresa)}", "${String.format('%td/%<tm/%<tY', trabajador.fechaNacimiento)}"] as Object[],
							"[La fecha de accidente del trabajador [{0}] no puede ser menor a su fecha de nacimiento [{1}]]")
				
			}else
				log.info("validacion de fecha ingreso a empresa y fecha de nacimiento completa")
			log.info("Validacion de fecha accidente y fecha de nacimiento finalizada")
			
			if(diatOA.fechaAccidente < diatOA.fechaIngresoEmpresa){
				log.info("fecha accidente [${diatOA.fechaIngresoEmpresa}] menor a fecha de nacimiento del trabajador [${diatOA.fechaIngresoEmpresa}]")
				t.errors.reject("cl.adexus.isl.spm.diatOA.fechaAccidente.fechaIngresoEmpresa.fail",
							["${String.format('%td/%<tm/%<tY', diatOA.fechaIngresoEmpresa)}", "${String.format('%td/%<tm/%<tY', trabajador.fechaNacimiento)}"] as Object[],
							"[La fecha de accidente del trabajador [{0}] no puede ser menor a su fecha de ingreso a la empresa [{1}]]")
				
			}else
				log.info("validacion de fecha accidente y fecha de ingreso a empresa completa")
			
			
			log.info("Verificando errores en trabajador")
			if(!trabajador.validate() || trabajador.hasErrors()){
				log.info("Registrando errores para trabajador")
				trabajador.errors.each{error->
					log.info("error : $error")
					trabajadorErrors << error
				}
			}else{
				log.info("No existen errores asociados a entidad trabajador")
				trabajador.save()
			}
			
			
			diatOA.trabajador=trabajador
			diatOA.nacionalidadTrabajador = nacionalidadTrabajador
			diatOA.direccionTrabajadorTipoCalle = direccionTrabajadorTipoCalle
			diatOA.direccionTrabajadorNombreCalle=params.direccionTrabajadorNombreCalle
			diatOA.direccionTrabajadorNumero="".equals(params.direccionTrabajadorNumero) ? null : Integer.parseInt(params.direccionTrabajadorNumero)
			diatOA.direccionTrabajadorRestoDireccion=params.direccionTrabajadorRestoDireccion
			diatOA.direccionTrabajadorComuna = direccionTrabajadorComuna
			diatOA.telefonoTrabajador="".equals(params.telefonoTrabajador) ? null : Integer.parseInt(params.telefonoTrabajador)
			diatOA.etnia = etnia
			diatOA.profesionTrabajador=params.profesionTrabajador
			//diatOA.fechaIngresoEmpresa=FechaHoraHelper.stringToDate(params.fechaIngresoEmpresa)
			diatOA.duracionContrato = duracionContrato
			diatOA.tipoRemuneracion= tipoRemuneracion
			diatOA.categoriaOcupacion = categoriaOcupacion


			diatOA.horaIngreso=horaIngreso
			diatOA.horaSalida=horaSalida
			diatOA.direccionAccidenteNombreCalle=params.direccionAccidenteNombreCalle
			diatOA.direccionAccidenteComuna = direccionAccidenteComuna
			diatOA.que=params.que
			diatOA.como=params.como
			diatOA.lugarAccidente=params.lugarAccidente
			diatOA.trabajoHabitualCual=params.trabajoHabitualCual
			diatOA.esTrabajoHabitual=Boolean.valueOf(params.esTrabajoHabitual)
			diatOA.gravedad = gravedad
			diatOA.esAccidenteTrayecto=params.esAccidenteTrayecto.equals('2') ? true : false
			diatOA.tipoAccidenteTrayecto = tipoAccidenteTrayecto
			diatOA.medioPrueba = medioPrueba
			diatOA.detallePrueba=params.detallePrueba
			diatOA.siniestro = siniestro
			if(!params?.sistemaSalud.equals(""))
				diatOA.sistemaSalud = SistemaSalud.findByCodigo(params.sistemaSalud)
				

			def calDenOA=CalificacionDenunciante.findByCodigo('7')
			diatOA.calificacionDenunciante=calDenOA
			
			def usuario=UsuarioService.getUsuario(params.username)
			
			def denunciante=PersonaNatural.findByRun(usuario.run)
			def denuncianteErrors = []
			if(!denunciante){
				denunciante=new PersonaNatural()
				denunciante.run=((String)usuario.run).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
				denunciante.nombre=usuario.nombres
				denunciante.apellidoPaterno=usuario.apellidoPaterno
				denunciante.apellidoMaterno=usuario.apellidoMaterno
				if (!denunciante.validate() || denunciante.hasErrors()) {
					//Hay algo malo en los datos y no se puede crear la DIEP OA
					log.info("Registrando errores para denunciante")
					denunciante.errors.each{error->
						log.info("denunciante : $denunciante")
						denuncianteErrors << error
					}
					//return (['next': [action:'r01'], model:['error': 'RUT denunciante emisor invalido', errores:errores]])
				}
				denunciante.save(failOnError: true, flush: true)
			}
			diatOA.denunciante=denunciante
			
			diatOA.codigoActividadEmpresa = params.codigoActividadEmpresa
			def diatOAErrors = []
			// validar antes de guardar la DIAT OA y si no valida devolver al formulario			
			if (!diatOA.validate() || diatOA.hasErrors()) {
				log.info("Registrando errores para diatOA")	
				diatOA.errors.each{error->
					log.info("error : $error")
					diatOAErrors << error
				}
				//Hay algo malo en los datos y no se puede crear la DIAT OA
				//return (['next': [action:'dp02at'],  model: ['diatOAPropuesta': diatOA, errores:errores]])
			}else{
				diatOA.siniestro = siniestro
				diatOA.save()
			}
			// Se actualiza el siniestro con los datos oficiales de la DIAT OA
			siniestro.empleador=diatOA?.empleador
			siniestro.trabajador=diatOA?.trabajador
			siniestro.relato=diatOA.como
			siniestro.diatOA=diatOA
			
			def siniestroErrors = []
			// validar antes de guardar
			if (!siniestro.validate() || siniestro.hasErrors()) {
				//Hay algo malo en los datos y no se puede guardar el siniestro
				log.info("Registrando errores para siniestro")
				siniestro.errors.each{error->
					log.info("error : $error")
					siniestroErrors << error
				}
				//return (['next': [action:'dp02at'],  model: [siniestro: siniestro, trabajador:trabajador, errores:errores]])
			}
			
			log.info("Verificando registro de errores ${t.hasErrors()}")
			if(trabajadorErrors || denuncianteErrors || diatOAErrors || siniestroErrors || t.hasErrors()){
				log.info("Existen errores redireccionando a dp02at")
				return (['next': [action:'dp02at'],  
						  model: ['siniestro': siniestro,'diatOAPropuesta': diatOA, trabajador:trabajador, 
							  	   denunciante:denunciante, trabajadorErrors:trabajadorErrors,t:t,
								   denuncianteErrors:denuncianteErrors, diatOAErrors:diatOAErrors,
								   siniestroErrors:siniestroErrors]])
			}else
				log.info("No existen errores")
			log.info("Verificacion de registro de errores finalizada")
			
			
			siniestro.save()
			// Envia a SUSESO
			def retorno = ""
			def mensaje = ""
			log.info("Validando guardadoTemporal : $guardadoTemporal")
			log.info("El siniestro en cuestion corresponde al : ${siniestro?.id}")
			if (guardadoTemporal){
				if (!siniestro?.diatOA?.xmlEnviado){
					log.debug("Enviando DIAT a SUSESO")
					SUSESOService.enviarDIAT(siniestro.diatOA)
					log.debug("Envio de DIAT a SUSESO finalizando")
					//Comenzamos el flujo de Calificacion de Origen AT
					log.debug("Inicializando tarea calificacion de origen con los siguientes parametros : $bpmPass , y con el siguiente siniestro: ${siniestro?.id}")
					CalOrigenService.startCalificacionOrigenAT(params.username,bpmPass,siniestro.id);		
					log.debug("Inicializacion de tarea de calificacion de origen concluida")
					//Vemos si es 77Bis
					
					def bis=Bis.findBySiniestro(siniestro);
					log.debug("Validando existencia de 77 bis por siniestro : ${siniestro.id}")
					if(bis){
						//Cerramos la tarea de regularizacion
						log.debug("Completando tarea 77bis")
						def r = BISIngresoService.complete77bis(params.username, bpmPass, bis.taskId)
						log.debug("Tarea complete77bis terminada")
					}
				}
				
				log.info("Obteniendo valor de retorno desde el xml recibido desde SUSESO")
				retorno = SUSESOService.getRetorno(siniestro.diatOA.xmlRecibido)
				if(retorno != ""){
					log.info("SUSESO service retorno el valor : $retorno")
					mensaje = "valor de retorno $retorno : ${RetornoWSEnum.findByKey(retorno.text()).getDescripcion()}"
					log.info("Obtencion de valor de retorno desde xml recibido desde SUSESO finalizada")
				}else{
					mensaje = "No se retornaron valores"
				}
			}
			
			log.debug(mensaje)
			// Luego de procesar la diat oa, termina el flujo
			return (['next': [action:'index', mensaje:mensaje]])
		} else {
			log.info("Siniestro es una enfermedad profesional")
			def empleador
			if(PersonaJuridica.findByRut(params.empleador_rut)){
				empleador=PersonaJuridica.findByRut(params.empleador_rut)
			}else{
				empleador = new PersonaJuridica()
				empleador.rut=params.empleador_rut
			}
			empleador.nombreFantasia=params.empleador_razonSocial
			empleador.razonSocial=params.empleador_razonSocial
			empleador.save()
			diepOA.empleador=empleador
			diepOA.fechaEmision=FechaHoraHelper.hace10minutos();
			if (params.ciiuPrincipal)
				diepOA.ciiuPrincipal=TipoActividadEconomica.findByCodigo(params.ciiuPrincipal)
			if (params.ciiuEmpleador)
				diepOA.ciiuEmpleador=TipoActividadEconomica.findByCodigo(params.ciiuEmpleador)
			diepOA.direccionEmpleadorTipoCalle = direccionEmpleadorTipoCalle
			diepOA.direccionEmpleadorNombreCalle=params.direccionEmpleadorNombreCalle
			diepOA.direccionEmpleadorNumero="".equals(params.direccionEmpleadorNumero) ? null : Integer.parseInt(params.direccionEmpleadorNumero)
			diepOA.direccionEmpleadorRestoDireccion=params.direccionEmpleadorRestoDireccion
			diepOA.direccionEmpleadorComuna = direccionEmpleadorComuna
			diepOA.telefonoEmpleador="".equals(params.telefonoEmpleador) ? null : Integer.parseInt(params.telefonoEmpleador)
			diepOA.nTrabajadoresHombre=params.nTrabajadoresHombre.toInteger()
			diepOA.nTrabajadoresMujer=params.nTrabajadoresMujer.toInteger()
			diepOA.propiedadEmpresa=propiedadEmpresa
			diepOA.tipoEmpresa=tipoEmpresa

			def trabajador
			if(PersonaNatural.findByRun(params.trabajador_run)){
				trabajador=PersonaNatural.findByRun(params.trabajador_run)
			}else{
				trabajador = new PersonaNatural()
				trabajador.run=params.trabajador_run
			}
			trabajador.nombre=params.trabajador_nombre
			trabajador.apellidoPaterno=params.trabajador_apellidoPaterno
			trabajador.apellidoMaterno=params.trabajador_apellidoMaterno
			trabajador.sexo=params.trabajador_sexo
			trabajador.fechaNacimiento=FechaHoraHelper.stringToDate(params.trabajador_fechaNacimiento)
			
			
			//para comparacion con fecha de nacimiento
			diepOA.fechaIngresoEmpresa=FechaHoraHelper.stringToDate(params.fechaIngresoEmpresa)
			
			
			def t = new PersonaNatural()
			log.info("Inicializando validacion de total de trabajadores")
			def totalTrabajadores = diepOA.nTrabajadoresHombre + diepOA.nTrabajadoresMujer
			if(totalTrabajadores < 1){
				log.info("Cantidad de trabajadores debe ser superior 0")
				t.errors.reject("cl.adexus.isl.spm.diepOA.numeroTrabajadores.fail",
					["0"] as Object[],
					"[El total de trabajadores debe ser superior a [{0}]]")
			}
			
			log.info("Inicializando validacion de fecha ingreso a empresa y fecha de nacimiento")
			if(diepOA.fechaIngresoEmpresa < trabajador.fechaNacimiento){
				log.info("fecha ingreso empresa [${diepOA.fechaIngresoEmpresa}] menor a fecha de nacimiento del trabajador [${trabajador.fechaNacimiento}]")
				t.errors.reject("cl.adexus.isl.spm.diatOA.fechaIngresoEmpresa.fechaNacimiento.fail",
							["${String.format('%td/%<tm/%<tY', diepOA.fechaIngresoEmpresa)}", "${String.format('%td/%<tm/%<tY', trabajador.fechaNacimiento)}"] as Object[],
							"[La fecha de ingreso a la empresa del trabajador [{0}] no puede ser menor a su fecha de nacimiento [{1}]]")
				
			}else
				log.info("validacion de fecha ingreso a empresa y fecha de nacimiento completa")
			log.info("Validacion de fecha ingreso a empresa y fecha de nacimiento finalizada")	
			
			def trabajadorErrors = []
			if(!trabajador.validate() || trabajador.hasErrors()){
				log.info("Registrando errores para siniestro")
				trabajador.errors.each{error->
					log.info("error : error")
					trabajadorErrors << error
				}
			}else
				trabajador.save()
			
			diepOA.trabajador=trabajador
			diepOA.nacionalidadTrabajador = nacionalidadTrabajador
			diepOA.direccionTrabajadorTipoCalle = direccionTrabajadorTipoCalle
			diepOA.direccionTrabajadorNombreCalle=params.direccionTrabajadorNombreCalle
			diepOA.direccionTrabajadorNumero="".equals(params.direccionTrabajadorNumero) ? null : Integer.parseInt(params.direccionTrabajadorNumero)
			diepOA.direccionTrabajadorRestoDireccion=params.direccionTrabajadorRestoDireccion
			diepOA.direccionTrabajadorComuna = direccionTrabajadorComuna
			diepOA.telefonoTrabajador="".equals(params.telefonoTrabajador) ? null : Integer.parseInt(params.telefonoTrabajador)
			diepOA.etnia = etnia
			//diepOA.fechaIngresoEmpresa = FechaHoraHelper.stringToDate(params.fechaIngresoEmpresa)
			diepOA.profesionTrabajador=params.profesionTrabajador
			diepOA.duracionContrato = duracionContrato
			diepOA.tipoRemuneracion= tipoRemuneracion
			diepOA.categoriaOcupacion = categoriaOcupacion

			diepOA.sintoma = params.sintoma
			diepOA.parteCuerpo = params.parteCuerpo
			diepOA.descripcionTrabajo = params.descripcionTrabajo
			diepOA.puestoTrabajo = params.puestoTrabajo
			diepOA.agenteSospechoso = params.agenteSospechoso
			diepOA.fechaSintoma=FechaHoraHelper.stringToDate(params.fechaSintoma)
			diepOA.fechaAgente=FechaHoraHelper.stringToDate(params.fechaAgente)
			diepOA.esAntecedenteCompanero = FormatosHelper.booleanString(params.esAntecedenteCompanero)
			diepOA.esAntecedentePrevio = FormatosHelper.booleanString(params.esAntecedentePrevio)

			def calDenOA=CalificacionDenunciante.findByCodigo('7')
			diepOA.calificacionDenunciante=calDenOA
			diepOA.telefonoDenunciante=params.telefonoDenunciante
			
			def usuario=UsuarioService.getUsuario(params.username)
			
			def denuncianteErrors = []
			def denunciante=PersonaNatural.findByRun(usuario.run)
			if(!denunciante){
				denunciante=new PersonaNatural()
				denunciante.run=usuario.run
				denunciante.nombre=usuario.nombres
				denunciante.apellidoPaterno=usuario.apellidoPaterno
				denunciante.apellidoMaterno=usuario.apellidoMaterno
				if (!denunciante.validate() || denunciante.hasErrors()) {
					//Hay algo malo en los datos y no se puede crear la DIEP OA
					log.info("Registrando errores para denunciante")
					denunciante.errors.each{error->
						log.info("error : $error")
						denuncianteErrors << error
					}
					//return (['next': [action:'r01'], model:['error': 'RUT denunciante emisor invalido']])
				}
				denunciante.save(failOnError: true, flush: true)
			}
			diepOA.denunciante=denunciante
			diepOA.codigoActividadEmpresa = params.codigoActividadEmpresa
			def diepOAErrors = []
			// validar antes de guardar la DIEP OA y si no valida devolver al formulario
			if (!diepOA.validate() || diepOA.hasErrors()) {
				//Hay algo malo en los datos y no se puede crear la DIEP OA
				log.info("Registrando errores para diepOA")
				diepOA.errors.each{error->
					log.info("error : $error")
					diepOAErrors << error
				}
				//return (['next': [action:'dp02ep'],  model: ['diepOAPropuesta': diepOA]])
			}else{
				diepOA.siniestro = siniestro
				diepOA.save()
			}
			// Se actualiza el siniestro con los datos oficiales de la DIEP OA
			siniestro.empleador=diepOA.empleador
			siniestro.trabajador=diepOA.trabajador
			siniestro.relato=diepOA.sintoma
			siniestro.diepOA=diepOA
			
			def siniestroErrors = []
			// validar antes de guardar
			if (!siniestro.validate() || siniestro.hasErrors()) {
				//Hay algo malo en los datos y no se puede guardar el siniestro
				log.info("Registrando errores para siniestro")				
				siniestro.errors.each{error->
					log.info("error : $error")
					siniestroErrors << error
				}
				//return (['next': [action:'dp02ep'],  model: ['siniestro': siniestro]])
			}
			
			log.info("Verificando registro de errores ${t.hasErrors()}")
			if(trabajadorErrors || denuncianteErrors || diepOAErrors || siniestroErrors || t.hasErrors()){
				log.info("Existen errores redireccionando a dp02ep")
				return (['next': [action:'dp02ep'],
						  model: ['siniestro': siniestro,'diepOAPropuesta': diepOA, trabajador:trabajador,
									 denunciante:denunciante, trabajadorErrors:trabajadorErrors,
								   denuncianteErrors:denuncianteErrors, diepOAErrors:diepOAErrors,
								   siniestroErrors:siniestroErrors,t:t]])
			}else
				log.info("No existen errores")
			log.info("Verificacion de registro de errores finalizada")
			
			
			siniestro.save()
			// Envia a SUSESO
			def retorno = ""
			def mensaje = ""
			log.info("Validando guardadoTemporal : $guardadoTemporal")
			log.info("El siniestro en cuestion corresponde al : ${siniestro?.id}")
			if (guardadoTemporal){
				if (!siniestro?.diepOA?.xmlEnviado){
					log.debug("Enviando DIEP a SUSESO")
					SUSESOService.enviarDIEP(siniestro.diepOA)
					log.debug("Envio de DIEP a SUSESO finalizando")
					//Comenzamos el flujo de Calificacion de Origen AT
					log.debug("Inicializando tarea calificacion de origen con los siguientes parametros : $bpmPass , y con el siguiente siniestro: ${siniestro?.id}")
					CalOrigenService.startCalificacionOrigenEP(params.username,bpmPass,siniestro.id);
					log.debug("Inicializacion de tarea de calificacion de origen concluida")
					//Vemos si es 77Bis
					
					def bis=Bis.findBySiniestro(siniestro);
					log.debug("Validando existencia de 77 bis por siniestro : ${siniestro.id}")
					if(bis){
						//Cerramos la tarea de regularizacion
						log.debug("Completando tarea 77bis")
						def r = BISIngresoService.complete77bis(params.username, bpmPass, bis.taskId)
						log.debug("Tarea complete77bis terminada")
					}
				}
				
				log.info("Obteniendo valor de retorno desde el xml recibido desde SUSESO")
				retorno = SUSESOService.getRetorno(siniestro.diepOA?.xmlRecibido)
				if(retorno != ""){
					log.info("SUSESO service retorno el valor : $retorno")
					mensaje = "valor de retorno $retorno : ${RetornoWSEnum.findByKey(retorno.text()).getDescripcion()}"
					log.info("Obtencion de valor de retorno desde xml recibido desde SUSESO finalizada")
				}else{
					mensaje = "No se retornaron valores"
				}
			}
			
			log.debug(mensaje)	
			// Luego de procesar la diep oa, termina el flujo
			return (['next': [action:'index', mensaje:mensaje]])
		}
	}	
}
