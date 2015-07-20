package cl.adexus.isl.spm

import cl.adexus.isl.spm.helpers.FormatosISLHelper

import org.apache.shiro.SecurityUtils

class ReingresoService {

	def UsuarioService
	
    def cu01(params) {
		params.run = ((String)params.run).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		def trabajador = PersonaNatural.findByRun(params.run)
		def next
		if (!trabajador) {
			log.info "El trabajador con run [${params.run}] no existe"
			trabajador = new PersonaNatural(params)
			trabajador.errors.reject("run", "El trabajador con run [${params.run}] no existe")
			next = [next: [action: 'dp01'], model: [trabajador: trabajador]]
		} else {
			next = [next: [action: 'dp02', params: [run: params.run]]]
		}
		return next
    }
	
	def dp02(params) {
		def username = SecurityUtils.subject?.principal
		def usuarios = UsuarioService.getUsuariosRegion(username);
		usuarios.add(username);
		def siniestros
		def query  = "SELECT	sini "
			query += "FROM		Siniestro as sini "
			query += "WHERE		sini.trabajador.run  = :run "
			query += "AND		sini.usuario IN (:users) "
			query += "ORDER BY 	sini.id "
		def sqlParams		= [run: params.run, users: usuarios]
		def siniestroList	= Siniestro.executeQuery(query,sqlParams)
		def trabajador		= PersonaNatural.findByRun(params.run)
		def model			= [model: [siniestroList: siniestroList, trabajador: trabajador]]
		return model
	}
	
	def cu02s(params) {
		def siniestro = Siniestro.get(params.id)
		def seguimiento = Seguimiento.findBySiniestro(siniestro)
		def reca = RECA.findBySiniestro(siniestro)
		if(!reca || !reca?.xmlRecibido){
			log.info "Caso no calificado no puede tener seguimiento"
			siniestro.errors.reject("cun", "Caso no calificado no puede tener seguimiento")
			return [next: [action: 'dp02', params: [run: siniestro.trabajador.run]], model: [siniestro: siniestro, trabajador: siniestro.trabajador]]
		}
		
		if (!seguimiento) {
			log.info "El caso está calificado como común por lo tanto no puede tener re-ingreso"
			siniestro.errors.reject("cun", "El caso está calificado como común por lo tanto no puede tener re-ingreso")
			return [next: [action: 'dp02', params: [run: siniestro.trabajador.run]], model: [siniestro: siniestro, trabajador: siniestro.trabajador]]
		}
		seguimiento = Seguimiento.findByFechaAltaIsNullAndFechaIngresoIsNotNullAndSiniestro(siniestro)
		if (seguimiento) {
			log.info "El siniestro N° ${siniestro.id} ya tiene un seguimiento vigente"
			siniestro.errors.reject("cun", "El siniestro N° ${siniestro.id} ya tiene un seguimiento vigente")
			return [next: [action: 'dp02', params: [run: siniestro.trabajador.run]], model: [siniestro: siniestro, trabajador: siniestro.trabajador]]
		}
		def reingreso = Reingreso.findByFechaIngresoIsNotNullAndFechaAprobacionIsNullAndSiniestro(siniestro)
		if (reingreso) {
			log.info "El siniestro N° ${siniestro.id} ya tiene una solicitud de reingreso vigente"
			siniestro.errors.reject("cun", "El siniestro N° ${siniestro.id} ya tiene una solicitud de reingreso vigente")
			return [next: [action: 'dp02', params: [run: siniestro.trabajador.run]], model: [siniestro: siniestro, trabajador: siniestro.trabajador]]
		}
		return [next: [action: 'dp03'], model: [siniestro: siniestro]]
	}
	
	def postDp03(params) {
		def next
		params.siniestro = Siniestro.get(params.siniestroId)
		def reingreso
		if (params.reingresoId) {
			reingreso = Reingreso.get(params.reingresoId)
			next = [controller: 'nav', action: 'area2']
		} else {
			reingreso = new Reingreso()
			next = [action: 'dp03']
		}
		reingreso.siniestro			= params.siniestro
		reingreso.fechaIngreso		= new Date()
		reingreso.nombre			= params.nombre
		reingreso.apellidoPaterno	= params.apellidoPaterno
		reingreso.apellidoMaterno	= params.apellidoMaterno
		reingreso.direccion			= params.direccion
		reingreso.telefono			= Long.parseLong(params.telefono)
		reingreso.email				= params.email
		reingreso.motivo			= params.motivo
		
		if (!reingreso.validate()) {
			log.info "Ocurrio un error al guardar reingreso"
			return [next: [action: 'dp03'], model: [reingreso: reingreso, siniestro: params.siniestro]]
		}
		reingreso.save()
		return [next: next, model: [reingreso: reingreso, siniestro: params.siniestro]]
	}
	
	def dp04(params) {
		def username 	= SecurityUtils.subject?.principal
		def usuarios 	= UsuarioService.getUsuariosRegion(username);
		usuarios.add(username);
		def sqlParams	= [users: usuarios]
		def query  		=  "SELECT	sini "
			query 		+= "FROM	Siniestro as sini "
			query 		+= "WHERE	sini.usuario in (:users) "
		if (params.siniestroId) {
			query		+= "AND		sini.id  = :siniestroId "
			sqlParams 	+= [siniestroId: params.siniestroId.toLong()]
		}
			query 		+= "AND		EXISTS (FROM	Reingreso rein "
			query		+= 				   "WHERE	rein.fechaIngreso IS NOT NULL "
			query		+=				   "AND		rein.fechaAprobacion IS NULL "
			query		+=				   "AND		rein.siniestro = sini) "
			query 		+= "ORDER BY 	sini.id "
		def siniestroList= Siniestro.executeQuery(query, sqlParams)
		def model		= [model: [siniestros: siniestroList]]
		return model
	}
	
	def cu04s(params) {
		log.info "Ejecutando metodo cu04s"
		log.info "Datos recibidos : $params"
		def siniestro 
		if(!params?.id){
			log.info "Se debe seleccionar un siniestro antes de efectuar un ingreso"
			siniestro = new Siniestro()
			siniestro.errors.reject("Se debe seleccionar un siniestro antes de efectuar un ingreso")
			return [next: [action: 'dp04', params: [siniestroId: siniestro.id]], model: [siniestro: siniestro]]
		}
		
		siniestro = Siniestro.get(params.id)
		def reingreso = Reingreso.findByFechaIngresoIsNotNullAndFechaAprobacionIsNullAndSiniestro(siniestro)
		if (!reingreso) {
			log.info "El siniestro N� ${siniestro.id} no tiene una solicitud de reingreso vigente"
			siniestro.errors.reject("cun", "El siniestro N° ${siniestro.id} no tiene una solicitud de reingreso vigente")
			return [next: [action: 'dp04', params: [siniestroId: siniestro.id]], model: [siniestro: siniestro]]
		}
		
		
		def prestador
		def vencimientoOPA
		if (siniestro?.opa) {
			prestador 			= FormatosISLHelper.nombrePrestadorStatic(siniestro?.opa?.centroAtencion?.prestador)
			vencimientoOPA 		= siniestro?.opa?.inicioVigencia + siniestro?.opa?.duracionDias
		} else if (siniestro?.opaep) {
			prestador 			= FormatosISLHelper.nombrePrestadorStatic(siniestro?.opaep?.centroAtencion?.prestador)
			vencimientoOPA 		= siniestro?.opaep?.inicioVigencia + siniestro?.opaep?.duracionDias
		} else {
			prestador 			= "No tiene OPA"
		}
		def adjuntos			= DocumentacionAdicional.findAllByReingreso(reingreso)
		def nivel				= [[codigo: "1", descripcion : "Nivel 1"]
							  	  ,[codigo: "2", descripcion : "Nivel 2"]
								  ,[codigo: "3", descripcion : "Nivel 3"]
								  ,[codigo: "4", descripcion : "Nivel 4"]]
		return [next: [action: 'dp05'], model:  [ siniestro			: siniestro
												, prestador			: prestador
												, vencimientoOPA	: vencimientoOPA
												, reingreso			: reingreso
												, nivel				: nivel]]
	}
	
	def cu05s(params) {
		log.info("Ejecutando metodo cu05s")
		log.info("Datos recibidos : $params")
		def siniestro						= Siniestro.get(params?.siniestroId)
		def reingreso						= Reingreso.findByFechaIngresoIsNotNullAndFechaAprobacionIsNullAndSiniestro(siniestro)
		log.info("Valor reingreso : $reingreso")
		// Crear nuevo seguimiento
		def seguimiento 					= new Seguimiento()
		seguimiento.siniestro				= siniestro
		seguimiento.usuario					= SecurityUtils.subject?.principal
		seguimiento.nivelComplejidadIngreso	= params.nivel.toInteger()
		seguimiento.fechaCambioNivel		= new Date()
		seguimiento.resumen					= params.resumen
		seguimiento.observaciones			= params.observaciones
		seguimiento.nivel					= params.nivel.toInteger()

		def r								= validarODA(siniestro, seguimiento.nivel)
		if (r.error) {
			log.info "No se puede ingresar seguimiento por validación ODA - [${r.mensaje}]"
			seguimiento.errors.reject("nivel", r.mensaje)
			def prestador
			def vencimientoOPA
			if (siniestro?.opa) {
				prestador 			= FormatosISLHelper.nombrePrestadorStatic(siniestro?.opa?.centroAtencion?.prestador)
				vencimientoOPA 		= siniestro?.opa?.inicioVigencia + siniestro?.opa?.duracionDias
			} else if (siniestro?.opaep) {
				prestador 			= FormatosISLHelper.nombrePrestadorStatic(siniestro?.opaep?.centroAtencion?.prestador)
				vencimientoOPA 		= siniestro?.opaep?.inicioVigencia + siniestro?.opaep?.duracionDias
			} else {
				prestador 			= "No tiene OPA"
			}
			return ([ next	: [action: 'dp05']
					, model	: [ siniestroId		: params?.siniestroId
							  , siniestro		: siniestro
							  , seguimiento		: seguimiento
							  , prestador		: prestador
							  , vencimientoOPA	: vencimientoOPA
							  , reingreso		: reingreso]])
		}
		if (!seguimiento.validate()) {
			log.info "Error al guardar el seguimiento"
			def prestador
			def vencimientoOPA
			if (siniestro?.opa) {
				prestador 			= FormatosISLHelper.nombrePrestadorStatic(siniestro?.opa?.centroAtencion?.prestador)
				vencimientoOPA 		= siniestro?.opa?.inicioVigencia + siniestro?.opa?.duracionDias
			} else if (siniestro?.opaep) {
				prestador 			= FormatosISLHelper.nombrePrestadorStatic(siniestro?.opaep?.centroAtencion?.prestador)
				vencimientoOPA 		= siniestro?.opaep?.inicioVigencia + siniestro?.opaep?.duracionDias
			} else {
				prestador 			= "No tiene OPA"
			}
			return  [ next : [action: 'dp05']
					, model: [siniestroId		: params?.siniestroId
							 , siniestro		: siniestro
							 , seguimiento		: seguimiento
							 , prestador		: prestador
							 , vencimientoOPA	: vencimientoOPA
							 , reingreso		: reingreso]]
		}
		seguimiento.fechaIngreso = new Date()
		log.info "seguimiento.nivel: ${seguimiento.nivel}"
		if (seguimiento.nivel == 0) {
			seguimiento.fechaAlta = seguimiento.fechaIngreso
		}
		seguimiento.save()
		// Se crea actividad de alta si el nivel de seguimiento es distinto de cero
		def tipoActividad = TipoActividadSeguimiento.findByCodigo('10')
		def actSeg = new ActividadSeguimiento([ seguimiento		: seguimiento
											  , tipoActividad	: tipoActividad
											  , fechaActividad	: seguimiento.fechaIngreso
											  , resumen			: params.resumen
											  , comentario		: params.observaciones])
		def docs = DocumentacionAdicional.findAllByReingreso(reingreso)
		if (docs != null)
		{
			for (DocumentacionAdicional doc in docs)
			{
				doc.setActividadSeguimiento(actSeg)
			}
		}
		if (!actSeg.validate()) {
			log.info "Error al guardar la actividad seguimiento"
			def prestador
			def vencimientoOPA
			if (siniestro?.opa) {
				prestador 			= FormatosISLHelper.nombrePrestadorStatic(siniestro?.opa?.centroAtencion?.prestador)
				vencimientoOPA 		= siniestro?.opa?.inicioVigencia + siniestro?.opa?.duracionDias
			} else if (siniestro?.opaep) {
				prestador 			= FormatosISLHelper.nombrePrestadorStatic(siniestro?.opaep?.centroAtencion?.prestador)
				vencimientoOPA 		= siniestro?.opaep?.inicioVigencia + siniestro?.opaep?.duracionDias
			} else {
				prestador 			= "No tiene OPA"
			}
			return  [ next : [action: 'dp05']
					, model: [siniestroId			: params?.siniestroId
							 , actividadSeguimiento	: actSeg
							 , siniestro			: siniestro
							 , seguimiento			: seguimiento
							 , prestador			: prestador
							 , vencimientoOPA		: vencimientoOPA
							 , reingreso			: reingreso]]
		}
		actSeg.save();
		// Se cierra solicitud de reingreso
		reingreso.resumenCaso		= params.resumen
		reingreso.observacion		= params.observaciones
		reingreso.nivelSeguimiento	= params.nivel

		reingreso.fechaAprobacion = new Date()
		reingreso.save()
		if (reingreso.nivelSeguimiento == "0") {
			return  [ next	: [controller: 'seguimiento', action: 'dp01'], model: [siniestroId: params?.siniestroId]]
		}
		
		return  [ next	: [controller: 'seguimiento', action: 'dp12'], model: [siniestroId: params?.siniestroId, 
																				actionCrearODA:"../seguimiento/dp13",
																				origen:"dp11",
																				volverSeguimiento:"dp11",
																				volverHistorial:"dp12",
																				verDetalle:"dp14",
																				cesarODA:"../postDp12Cesar"]]
	}

	private validarODA(siniestro, nivelSeguimiento) {
		log.info "validarODA: [${siniestro.id}] - [${nivelSeguimiento}]"
		if (!siniestro) { return [error: true, mensaje: "Siniestro nulo!"] }
		if (nivelSeguimiento == 0) { return [error: false, mensaje: "Sin Seguimiento"] }
	 	def error			= false
		def mensaje			= "OK"
		// Obtener ODA Principal para nivel seleccionado
		def odaPrincipal	= CambioNivelSeguimiento.findByNivelSeguimiento(nivelSeguimiento)
		if (odaPrincipal) {
			def tipoOdaList		= new ArrayList()
			def tipoOdaDesList	= new ArrayList()
			tipoOdaList.add(odaPrincipal.principal)
			tipoOdaDesList.add(odaPrincipal.principal.descripcion)
			odaPrincipal.complementarias.each {
				tipoOdaList.add(it)
				tipoOdaDesList.add(it.descripcion)
			}
			// Validar que la oda principal no este repetida
			def countODAPrincipal = ODA.countBySiniestroAndTipoODAAndCesada(siniestro, odaPrincipal.principal, false)
			if (countODAPrincipal > 1) {
				error = true
				mensaje = "El siniestro tiene más de una ODA principal"
			} else {
				// Obtener ODAS del siniestro
				def odas 			= ODA.executeQuery(
					"SELECT oda " +
					"FROM	ODA oda " +
					"WHERE	oda.siniestro = :siniestro " +
					"AND	(oda.cesada IS NULL OR oda.cesada = false) "
					, [ siniestro	: siniestro ])
				for (ODA oda : odas) {
					if (!tipoOdaDesList.contains(oda.tipoODA.descripcion)) {
						error = true
						break
					}
					// TODO: Validar fechas de odas complementarias
					
				}
				if (error) { mensaje = "El nivel [${nivelSeguimiento}] acepta solo ODAS del tipo ${tipoOdaDesList}" }
			}
		} else {
			error = true
			mensaje = "No existen ODA principal para el nivel [${nivelSeguimiento}]"
		}
		return [error: error, mensaje: mensaje]
	}
}
