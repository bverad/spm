package cl.adexus.isl.spm

import cl.adexus.helpers.FechaHoraHelper

class DIATWEBService {
	
	def PDFService
	def SUSESOService
	def SiniestroService
	def DenunciaService 
	def UsuarioService
	
	/**
	 * Busca el RUT de la empresa en base al login
	 */
	def r00(username){
		//Busca la empresa en base al login. 
		def usuario=UsuarioService.getUsuario(username)
		log.info "Usuario:${username} Rut Empresa:${usuario?.empresa?.rut_empresa}"
		def empleador=PersonaJuridica.findByRut(usuario?.empresa?.rut_empresa)
		return (['next': [action:'dp01'],  model: ['empleador': empleador]])
	}
	
	/**
	 *  Busca siniestros para run, fechasiniestro
	 *  y el rut empleador
	 *
	 */
	def r01(params,PersonaJuridica empleador){
		//Limpiamos el run
		params['run']=((String)params['run']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		
		//Busca si existe el trabajador (run)
		def trabajador=PersonaNatural.findByRun(params['run'])
		if(!trabajador){
			params.nombre = ' '
			params.apellidoPaterno = ' '
			trabajador=new PersonaNatural(params)
			if(!trabajador.validate()){
				log.info "trabajador no validate"
				//Hay algo malo en los datos y no se puede crear el trabajador
				return (['next': [action:'dp01'],  model: ['trabajador': trabajador]])
			}
			trabajador.save()
		}
		
		//Inicializa el DIATWEB
		def diatweb = new DIATWEB(params)
		diatweb.trabajador=trabajador
		diatweb.empleador=empleador
		diatweb.fechaSiniestro=params.fechaSiniestro
		if(!diatweb.validate()){
			//Hay algo malo con los datos y no se puede crear el diatweb
			return (['next': [action:'dp01'],  model: ['diatweb': diatweb]])
		}
		diatweb.save()

		//Busca siniestro previo
		def siniestrosPrevios = SiniestroService.findSiniestrosPrevios(diatweb.fechaSiniestro,empleador,trabajador)
		log.info siniestrosPrevios
		
		if(siniestrosPrevios){
			//Hay siniestro previo
			def siniestro=siniestrosPrevios[0]
			//Tipo Empleador
			def tEmp=CalificacionDenunciante.findByCodigo('1')
			//Busca denuncia previa para ese denunciante
			
			def diatPrevias = DenunciaService.diatPrevias(diatweb.fechaSiniestro,empleador,trabajador,tEmp)
			log.info diatPrevias
			
			if(diatPrevias.size>0){
				return (['next': [action:'dp03'], model: ['diatweb': diatweb, 'error': 'denuncia_previa' ]])
			}else{
				return (['next': [action:'dp02'], model: ['diatweb': diatweb,'siniestro': siniestro ]])
			}
		}else{
			//No Hay siniestro previo
			return (['next': [action: 'dp03'], model: ['diatweb': diatweb, 'error': 'no_siniestro' ]])
		}
	}
	
	/**
	 * Procesa la primera parte de la DIAT
	 *
	 */
	def cu02(params,DIATWEB diatweb){
		log.info "params:"+params
		params.empleador=PersonaJuridica.findByRut(params.empleador_rut)
		params.empleador.razonSocial=params.empleador_razonSocial
		if (!params.empleador.validate()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			return (['next': [action:'dp02'],  model: ['empleador': params.empleador]])
		}
		params.empleador.save()
		
		params.direccionEmpleadorTipoCalle=TipoCalle.findByCodigo(params.direccionEmpleadorTipoCalle)
		params.direccionEmpleadorComuna=Comuna.findByCodigo(params.direccionEmpleadorComuna)
		params.propiedadEmpresa=TipoPropiedadEmpresa.findByCodigo(params.propiedadEmpresa)
		params.tipoEmpresa=TipoEmpresa.findByCodigo(params.tipoEmpresa)
		if (params.ciiuPrincipal)
			params.ciiuPrincipal=TipoActividadEconomica.findByCodigo(params.ciiuPrincipal)
		if (params.ciiuEmpleador)
			params.ciiuEmpleador=TipoActividadEconomica.findByCodigo(params.ciiuEmpleador)

		params.trabajador=PersonaNatural.findByRun(params.trabajador_run)
		params.trabajador.nombre=params.trabajador_nombre
		params.trabajador.apellidoPaterno=params.trabajador_apellidoPaterno
		params.trabajador.apellidoMaterno=params.trabajador_apellidoMaterno
		params.trabajador.sexo=params.trabajador_sexo

		if (params.trabajador_fechaNacimiento instanceof String)
			params.trabajador_fechaNacimiento = FechaHoraHelper.stringToDate(params.trabajador_fechaNacimiento)

		if (params.fechaIngresoEmpresa instanceof String && !"".equals(params.fechaIngresoEmpresa))
			params.fechaIngresoEmpresa = FechaHoraHelper.stringToDate(params.fechaIngresoEmpresa)

		params.trabajador.fechaNacimiento=params.trabajador_fechaNacimiento
		if (!params.trabajador.validate()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			return (['next': [action:'dp02'],  model: ['trabajador': params.trabajador]])
		}
		params.trabajador.save()
		
		params.nacionalidadTrabajador=Nacion.findByCodigo(params.nacionalidadTrabajador)
		params.direccionTrabajadorTipoCalle=TipoCalle.findByCodigo(params.direccionTrabajadorTipoCalle)
		params.direccionTrabajadorComuna=Comuna.findByCodigo(params.direccionTrabajadorComuna)
		params.etnia=Etnia.findByCodigo(params.etnia)
		params.duracionContrato=TipoDuracionContrato.findByCodigo(params.duracionContrato)
		params.tipoRemuneracion=TipoRemuneracion.findByCodigo(params.tipoRemuneracion)
		params.categoriaOcupacion=CategoriaOcupacion.findByCodigo(params.categoriaOcupacion)
		
		def diat = diatweb?.diat
		if (!diat) diat = new DIAT(params)
		
		// validar antes de guardar y si no valida devolver al formulario
		if (!diat.validate()) {
			//Hay algo malo en los datos y no se puede crear el empleador
			return (['next': [action:'dp02'],  model: ['diat': diat]])
		}
		diat.save()
		
		diatweb.diat=diat;
		diatweb.save();
		
		//No se que le pasa al Lazy
		diat.direccionEmpleadorComuna.provincia.codigo
		diat.direccionTrabajadorComuna.provincia.codigo
		
		return (['next': [action:'dp02_2'],  model: ['diat': diat]])
	}
	
	/**
	 * Procesa la segunda parte de la DIAT
	 *
	 */
	def cu02_2(params,DIATWEB diatweb,username){
		log.info "params:"+params
		
		DIAT.withTransaction{
			
			def diat=diatweb.diat
			diat.direccionEmpleadorComuna.provincia.codigo
			
			try{
				diat.fechaAccidente = FechaHoraHelper.horaToDate(params.fechaAccidente_hora,diat.fechaAccidente) //Hora del accidente
			}catch (Exception e){
				diat.errors.rejectValue("fechaAccidente", "hora.mala",e.toString())
			}
			
			try{
				diat.horaIngreso = FechaHoraHelper.horaToDate(params.horaIngreso,diatweb.fechaSiniestro) //Hora del accidente
			}catch (Exception e){
				diat.errors.rejectValue("horaIngreso", "hora.mala",e.toString())
			}
			try{
				diat.horaSalida = FechaHoraHelper.horaToDate(params.horaSalida,diatweb.fechaSiniestro) //Hora del accidente
			}catch (Exception e){
				diat.errors.rejectValue("horaSalida", "hora.mala",e.toString())
			}
			diat.direccionAccidenteNombreCalle=params.direccionAccidenteNombreCalle
			diat.direccionAccidenteComuna=Comuna.findByCodigo(params.direccionAccidenteComuna)
			diat.que=params.que
			diat.lugarAccidente=params.lugarAccidente
			diat.como=params.como
			diat.trabajoHabitualCual=params.trabajoHabitualCual
			diat.esTrabajoHabitual=params.esTrabajoHabitual.equals('Si')?true:false
			diat.gravedad=CriterioGravedad.findByCodigo(params.gravedad)
			diat.esAccidenteTrayecto=params.esAccidenteTrayecto.equals('2')?true:false
			diat.tipoAccidenteTrayecto=TipoAccidenteTrayecto.findByCodigo(params.tipoAccidenteTrayecto)
			diat.medioPrueba=TipoMedioPruebaAccidente.findByCodigo(params.medioPrueba)
			diat.detallePrueba=params.detallePrueba
			diat.calificacionDenunciante=CalificacionDenunciante.findByCodigo('1')
			
			def usuario=UsuarioService.getUsuario(username)
			
			def denunciante=PersonaNatural.findByRun(usuario.run)
			if(!denunciante){
				denunciante=new PersonaNatural()
				denunciante.run=usuario.run
				denunciante.nombre=usuario.nombres
				denunciante.apellidoPaterno=usuario.apellidoPaterno
				denunciante.apellidoMaterno=usuario.apellidoMaterno
				denunciante.save(failOnError: true, flush: true)
			}
			diat.denunciante=denunciante
			
			// validar antes de guardar y si no valida devolver al formulario
			if (!diat.validate()) {
				//Hay algo malo en los datos y no se puede crear el empleador
				return (['next': [action:'dp02_2'],  model: ['diat': diat]])
			}
			diat.save(failOnError: true, flush: true)
			
			//Asociamos la DIAT al siniestro
			def siniestrosPrevios = SiniestroService.findSiniestrosPrevios(diatweb.fechaSiniestro,diatweb.empleador,diatweb.trabajador)
			log.info siniestrosPrevios
			
			def siniestro=siniestrosPrevios[0]
			log.info siniestro
			diat.siniestro=siniestro
			diat.fechaEmision = FechaHoraHelper.hace10minutos()
			diat.save(failOnError: true)
			
			//No se que le pasa al Lazy
			diat.direccionAccidenteComuna.provincia.codigo
			
			
			
			// Envia a SUSESO
			SUSESOService.enviarDIAT(diat)
			
			diatweb.diat=diat
			diatweb.save(failOnError: true);
			
		} //Fin de transaction
		
		
		
		
		
		diatweb=null
		return (['next': [action:'cu03t']])
	}

	
}
