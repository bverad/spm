package cl.adexus.isl.spm

import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.helpers.DataSourceHelper

import org.apache.shiro.SecurityUtils
import org.h2.jdbc.JdbcSQLException
import org.h2.message.DbException

import groovy.sql.Sql

import java.util.Calendar
import java.util.Date;

class ArancelService {

	def dataSource
	def PrestadorService
	
	def obtenerAranceles(params) {
		def sql					= new Sql(dataSource)
		def booleanFunction		= (new DataSourceHelper()).booleanValueFunction("TRUE");
		def prestacionesList	= new ArrayList()
		// Parametros
		params?.grupo			= "${params?.grupo == null ? '' : params?.grupo}"
		params?.subgrupo		= "${params?.subgrupo == null ? '' : params?.subgrupo}"
		params?.codigoPrestacion= "${params?.codigoPrestacion == null ? '' : params?.codigoPrestacion}"
		def paramsQuery = 	[ grupo				: '%' + params?.grupo + '%'
							, subgrupo			: '%' + params?.subgrupo + '%'
							, codigoPrestacion	: '%' + params?.codigoPrestacion + '%']
		// Definición de query ArancelBase
		def query = "SELECT	ab.codigo, ab.glosa, ab.valorN1, ab.valorN2, ab.valorN3, ab.desde, ab.hasta, ab.origen, ab.id, 'ARANCEL' tipo " +
					"FROM 	Arancel_Base ab, Sub_Grupo sugr, Grupo grup " +
					"WHERE	ab.carga_Aprobada		 = " + booleanFunction + " " +
					"AND	ab.SUB_GRUPO_ID			 = sugr.id " +
					"AND	sugr.grupo_id			 = grup.codigo " +
					"AND	grup.codigo				 LIKE :grupo " +
					"AND	sugr.codigo				 LIKE :subgrupo " +
					"AND	ab.codigo		 		 LIKE :codigoPrestacion "
		//if ()
		if (params?.desde) {
			query +="AND	ab.desde >= :fechaDesde "
			paramsQuery += [fechaDesde	: (new DataSourceHelper()).dateValueFuncion(params?.desde)]
		}
		if (params?.hasta) {
			query +="AND	ab.hasta <= :fechaHasta "
			paramsQuery += [fechaHasta	: (new DataSourceHelper()).dateValueFuncion(params?.hasta)]
		}
		if (!params?.codigoPrestacion)
			query +="AND	ab.hasta IS NULL "
		
			// Definición de query Paquete
		def queryPaquete 	= query.replace("Arancel_Base", "Paquete")
			queryPaquete 	= queryPaquete.replace("SELECT	ab.codigo, ab.glosa, ab.valorN1, ab.valorN2, ab.valorN3, ab.desde, ab.hasta, ab.origen, ab.id, 'ARANCEL' tipo"
												 , "SELECT	ab.codigo, ab.glosa, ab.valor valorN1, 0 valorN2, 0 valorN3, ab.desde, ab.hasta, ab.origen, ab.id, 'PAQUETE' tipo")
			queryPaquete 	= queryPaquete.replace("ab.carga_Aprobada		 = " + booleanFunction + " AND", " ")

		// Variables de paginacion
		params.max		= 10
		params.offset	= params.offset ?: 0
		// Se invocan las querys
		def sqlQuery	= query + " UNION " + queryPaquete + "ORDER BY 1, 6"
		log.info "query: ${sqlQuery}"
		def rows		= sql.rows(sqlQuery, paramsQuery, (params.offset.toInteger()+1), params.max.toInteger())
		def rowsCount	= sql.rows(sqlQuery, paramsQuery)
		rows.each { row ->
			prestacionesList.add([ codigo	: row.codigo
								 , glosa	: row.glosa
								 , valorN1	: row.valorN1
								 , valorN2	: row.valorN2
								 , valorN3	: row.valorN3
								 , desde	: row.desde
								 , hasta	: row.hasta
								 , origen	: row.origen
								 , id		: row.id
								 , tipo		: row.tipo])
		}
		params?.countPrestaciones = rowsCount.size()
		log.info "max: [${params.max}] - totalRow: [${rowsCount.size()}] - offset: [${params.offset}] - grupo: [${params?.grupo}] - subgrupo: [${params?.subgrupo}] - codigoPrestacion: [${params?.codigoPrestacion}]"
		return prestacionesList
	}
	
	def agregarPrestacion(params) {
		log.info("*** agregarPrestacion *** \n${params}")
		// Inicialización de variables
		if (params?.grupo && params?.subgrupo && params?.codigo)
			params?.codigo		= params?.grupo.toString() + params?.subgrupo.toString() + params?.codigo.toString()
		params?.activado		= params?.activado == "1"
		params?.grupo			= Grupo.findByCodigo(params?.grupo)
		params?.subGrupo		= SubGrupo.findByCodigoAndGrupo(params?.subgrupo, params?.grupo)
		params?.origen			= "ISL"
		params?.cargaAprobada	= true
		params?.nivelPabellon	= params?.nivelPabellon ? Integer.parseInt(params?.nivelPabellon) : 0
		params?.anestesiaN1		= params?.anestesiaN1 ? Integer.parseInt(params?.anestesiaN1) : 0
		params?.cirujano1N1 	= params?.cirujano1N1 ? Integer.parseInt(params?.cirujano1N1) : 0
		params?.cirujano2N1 	= params?.cirujano2N1 ? Integer.parseInt(params?.cirujano2N1) : 0
		params?.cirujano3N1 	= params?.cirujano3N1 ? Integer.parseInt(params?.cirujano3N1) : 0
		params?.cirujano4N1 	= params?.cirujano4N1 ? Integer.parseInt(params?.cirujano4N1) : 0
		params?.valorN1 		= params?.valorN1 ? Integer.parseInt(params?.valorN1) : 0
		def equipo				= 0
		if (params?.tipo != 'General') {
			if (params?.cirujano1N1)
				equipo++
			if (params?.cirujano2N1)
				equipo++
			if (params?.cirujano3N1)
				equipo++
			if (params?.cirujano4N1)
				equipo++
			params?.valorN1 	= params?.cirujano1N1 + params?.cirujano2N1 + params?.cirujano3N1  + params?.cirujano4N1 + (params?.cirujano1N1 / 10) + params?.anestesiaN1
		}
		params?.equipo			= equipo
		// Logica de fecha hasta para el arancel anterior
		def arancelAnterior = ArancelBase.findByCodigoAndHastaIsNull(params?.codigo)
		if (arancelAnterior) {
			log.info("Hay arancel anterior para la prestación [${params?.codigo}] se actualiza la fecha hasta con [${FormatosISLHelper.fechaCortaStatic(params?.desde)}]")
			arancelAnterior.hasta = params?.desde
			arancelAnterior.save()
		}
		log.info("Se crea nuevo arancel para la prestación [${params?.codigo}]")
		def arancelBase = new ArancelBase(params)
		def next = [:]
		if (!arancelBase.save()) {
			errores = "cl.adexus.isl.spm.ArancelBase.save.fail"
			log.info("error: " + errores)	
			next = ['next': [action: 'create_prestacion'],  model: ['arancelBase': arancelBase, 'errores': errores]]
			return next
		}
		next = ['next': [action: 'mantener_aranceles'],  model: ['arancelBase': arancelBase]]
		return next
	}
	
	def actualizarPrestacion(params) {
		log.info("*** actualizarPrestacion *** \n${params}")
		// Inicialización de variables
		params?.activado		= params?.activado == "1"
		params?.nivelPabellon	= params?.nivelPabellon ? params?.nivelPabellon.toInteger() : 0
		params?.anestesiaN1		= params?.anestesiaN1 ? params?.anestesiaN1.toInteger() : 0
		params?.cirujano1N1 	= params?.cirujano1N1 ? params?.cirujano1N1.toInteger() : 0
		params?.cirujano2N1 	= params?.cirujano2N1 ? params?.cirujano2N1.toInteger() : 0
		params?.cirujano3N1 	= params?.cirujano3N1 ? params?.cirujano3N1.toInteger() : 0
		params?.cirujano4N1 	= params?.cirujano4N1 ? params?.cirujano4N1.toInteger() : 0
		def equipo				= 0
		if (params?.tipo != 'General') {
			if (params?.cirujano1N1)
				equipo++
			if (params?.cirujano2N1)
				equipo++
			if (params?.cirujano3N1)
				equipo++
			if (params?.cirujano4N1)
				equipo++
			params?.valorN1 		= params?.cirujano1N1 + params?.cirujano2N1 + params?.cirujano3N1  + params?.cirujano4N1 + (params?.cirujano1N1 / 10) + params?.anestesiaN1
		}
		params?.equipo			= equipo

		// Logica de fecha hasta para el arancel anterior
		def arancelBase = ArancelBase.findById(params?.arancelBaseId)
		if (arancelBase) {
			log.info("Hay arancel anterior para la prestación [${params?.arancelBaseId}] se actualiza la fecha hasta con [${FormatosISLHelper.fechaCortaStatic(params?.desde)}]")
			def arancelAnterior = ArancelBase.findByCodigoAndHasta(params?.codigo, arancelBase.desde)
			if (arancelAnterior) {
				arancelAnterior.hasta = params?.desde
				arancelAnterior.save()
			}
		}
		log.info("Se actualiza arancel para la prestación [${params?.codigo}]")
		arancelBase?.nivelPabellon	= params?.nivelPabellon
		arancelBase?.anestesiaN1	= params?.anestesiaN1
		arancelBase?.cirujano1N1 	= params?.cirujano1N1
		arancelBase?.cirujano2N1 	= params?.cirujano2N1
		arancelBase?.cirujano3N1 	= params?.cirujano3N1
		arancelBase?.cirujano4N1 	= params?.cirujano4N1
		arancelBase?.valorN1 		= params?.valorN1.toInteger()
		arancelBase?.equipo			= params?.equipo
		arancelBase?.glosa			= params?.glosa
		if (params?.desde)
			arancelBase?.desde		= params?.desde
		def next = [:]

		if (!arancelBase.save(flush: true)) {
			log.info("Fallo la grabación de la prestación")
			arancelBase.errors.reject("XXX","Error, al actualizar la prestación")
			next = ['next': [action: 'edit_prestacion'],  model: ['arancelBase': arancelBase]]
			return next
		}

		next = ['next': [action: 'mantener_aranceles'],  model: ['arancelBase': arancelBase]]
		return next

	}
	
	def deletePrestacion(params) {
		def codigo
		def paquete
		
		log.info("deletePrestacion: " + params?.arancelBaseId.toLong())
		def arancelBase = ArancelBase.get(params?.arancelBaseId.toLong())
		def next = ['next': [action: 'mantener_aranceles'],  model: ['arancelBase': arancelBase]]
		
		if (!arancelBase) {
			log.info("No encontramos el arancel base: " + codigo)
			next = ['next': [action: 'edit_prestacion'],  model: ['arancelBaseId': codigo]]
			return next
		}
		try {
			arancelBase.hasta = new Date()
			arancelBase.save()
			log.info("deletePrestacion: prestación " + codigo + " eliminada")
		} catch(org.springframework.dao.DataIntegrityViolationException e) {
			log.info("cl.adexus.isl.spm.ArancelBase.delete.fail")
			arancelBase.errors.reject("cl.adexus.isl.spm.ArancelBase.delete.fail")
			next = ['next': [action: 'edit_prestacion'],  model: ['arancelBase': arancelBase, arancelBaseId: arancelBase?.id]]
		}
		return next
	}
	
	def agregarPaquete(params) {
		def next
		def cnt = 0
		
		def codigoPrestacion	= params?.grupo.trim() + params?.subgrupo.trim() + params?.codigo.trim()
		def subGrupo 			= SubGrupo.findByCodigo(params?.subgrupo)
		def tipoPaquete			= TipoPaquete.findByCodigo(params?.tipo)
		
		def paquete				= new Paquete()
		paquete?.codigo			= codigoPrestacion
		paquete?.glosa			= params?.glosa
		paquete?.subGrupo		= subGrupo
		paquete?.tipoPaquete	= tipoPaquete
		paquete?.reposoEstimado = params?.reposo
		paquete?.escalamiento	= params?.escalamiento
		paquete?.origen			= "ISL"
		paquete?.valor			= 0
		paquete?.efectividad	= Integer.parseInt(params?.efectividad)
		paquete?.complejidad	= Integer.parseInt(params?.complejidad)
		paquete?.desde			= params?.desde
		if (params?.hasta)
			paquete?.hasta		= params?.hasta

		log.info("Validando la existencia del codigo : " + codigoPrestacion)
		cnt = ArancelBase.countByCodigo(codigoPrestacion)
		log.info("cantidad : " + cnt)
		if (cnt > 0) {
			paquete.errors.reject("paquete.error", "Error, ya existe una arancel base con este codigo")
			// Hay que volver el codigo a su versión anterior, maravillas del diseño
			paquete.codigo = params?.codigo.trim()
			next = ['next': [action: 'create_paquete'],  model: ['paquete': paquete]]
			return next
		}
		log.info("Validando si existe un paquete con el mismo codigo")
		def paqueteAnterior = Paquete.findAllByCodigo(codigoPrestacion, [sort: "id", order: "desc"])
			paqueteAnterior = paqueteAnterior[0]
		if (paqueteAnterior) {
			log.info("Validando si la fecha del paquete anterior es válida")
			if (params?.desde <= paqueteAnterior.desde) {
				paquete.errors.reject("desde", "Error, la fecha ingresada debe ser mayor a la fecha del paquete anterior [${FormatosISLHelper.fechaCortaStatic(paqueteAnterior.desde)}]")
				// Hay que volver el codigo a su versión anterior, maravillas del diseño
				paquete.codigo = params?.codigo.trim()
				next = ['next': [action: 'create_paquete'],  model: ['paquete': paquete]]
				return next
			} else {
				paqueteAnterior.hasta = params?.desde
			}
		}
		if (!paquete.save(flush: true)) {
			paquete.errors.reject("paquete.error", "Error, no fue posible agregar el paquete al sistema")
			// Hay que volver el codigo a su versión anterior, maravillas del diseño
			paquete.codigo = params?.codigo.trim()
			next = ['next': [action: 'create_paquete'],  model: [paquete: paquete]]
			return next
		}
		if (paqueteAnterior) {
			paqueteAnterior.save()
			// Agregar prestaciones anteriores al nuevo paquete
			def arancelesPaquete = PrestadorService.getListaArancelesByPaqueteId(paqueteAnterior?.id)
			arancelesPaquete.each {
				def ap = ArancelPaquete.get(it.id)
				log.info "Copiando arancel paquete [${ap.codigoPrestacion}]"
				def arancelPaquete = new ArancelPaquete()
				arancelPaquete.paquete			= paquete
				arancelPaquete.codigoPrestacion	= ap.codigoPrestacion
				arancelPaquete.nivel			= ap.nivel
				arancelPaquete.calculo			= ap.calculo
				arancelPaquete.cantidad			= ap.cantidad
				arancelPaquete.valorOriginal	= ap.valorOriginal
				arancelPaquete.valor			= ap.valor
				arancelPaquete.valorNuevo		= ap.valorNuevo
				arancelPaquete.cargo			= ap.cargo
				arancelPaquete.descuento		= ap.descuento
				arancelPaquete.pesos			= ap.pesos
				arancelPaquete.porcentaje		= ap.porcentaje
				arancelPaquete.desde			= ap.desde
				arancelPaquete.hasta			= ap.hasta
				arancelPaquete.save()
			}
		}
		next = ['next': [action: 'edit_paquete'],  model: [paqueteId: paquete?.id]]
		return next

	}
	
	def deletePaquete(params) {
		def paquete
		def next = ['next': [action: 'mantener_aranceles'],  model: ['paquete': paquete]]
		if (params?.paqueteId) {
			log.info("recibimos paqueteId:" + params?.paqueteId)
			paquete = Paquete.findById(params?.paqueteId)
			try {
				paquete.hasta = new Date()
				paquete.save()
			} catch(org.springframework.dao.DataIntegrityViolationException e) {
				paquete.errors.reject("cl.adexus.isl.spm.Paquete.delete.fail") 
				next = ['next': [action: 'edit_paquete'],  model: ['paquete': paquete, paqueteId: paquete?.id]]
			}	
		}
		return next
	}
	
	def actualizarPaquete(params) {
		
		def next
		def paquete
		def cnt = 0
		def subGrupo
		def tipoPaquete
		def paqueteId
		def errores
		
		paqueteId = params?.paqueteId
		
		paquete = Paquete.findById(paqueteId)	
		
		log.info("glosa: " + params?.glosa)	
		
		paquete?.glosa = params?.glosa
		paquete?.complejidad = Integer.parseInt(params?.complejidad)
		paquete?.efectividad = Integer.parseInt(params?.efectividad)
		paquete?.escalamiento = params?.escalamiento
		paquete?.reposoEstimado = params?.reposo

		if (params?.desde)
			paquete?.desde = params?.desde
		if (params?.hasta)
			paquete?.hasta = params?.hasta
		
		paquete?.origen = "ISL"
		paquete?.valor = 0
					
		if (!paquete.save(flush: true)) {
			errores = "cl.adexus.isl.spm.Paquete.update.fail"
			next = ['next': [action: 'edit_paquete'],  model: ['paquete': paquete, 'errores': errores]]
			return next
		}
		
		next = ['next': [action: 'edit_paquete'],  model: ['paquete': paquete, 'paqueteId': paqueteId]]
		return next

	}

	def buscarPrestacionJSON(params) {
		def codigo = params.grupo.trim() + params.subgrupo.trim() + params.codigo.trim()
		if (codigo != "") {
			def prestacion = ArancelBase.findByCodigoAndDesdeIsNotNullAndHastaIsNullAndOrigen(codigo, "ISL")
			if (!prestacion) {
				return [hayDatos: false]
			}
			return [hayDatos: true, prestacion: prestacion]
		} else {
			return [hayDatos: false]
		}
	}
	
	def buscarPaqueteJSON(params) {
		def codigo = params.grupo.trim() + params.subgrupo.trim() + params.codigo.trim()
		if (codigo != "") {
			def prestacion = Paquete.findAllByCodigoAndOrigen(codigo, "ISL", [sort: "id", order: "desc"])
				prestacion = prestacion[0]
			if (!prestacion) {
				return [hayDatos: false]
			}
			return [hayDatos: true, prestacion: prestacion]
		} else {
			return [hayDatos: false]
		}
	}
}
