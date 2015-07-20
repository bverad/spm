package cl.adexus.isl.spm

import cl.adexus.helpers.FormatosHelper
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.helpers.DataSourceHelper

import org.hibernate.exception.ConstraintViolationException

import org.apache.shiro.SecurityUtils
import org.h2.jdbc.JdbcSQLException
import org.h2.message.DbException

import java.util.Date
import java.util.logging.Logger;

import groovy.sql.Sql

class PrestadorService {

	def dataSource

	def existePrestador(rut, esPersonaJuridica) {
		log.info 'Ejecutando metodo existe prestador'
		log.debug 'Datos recibidos: rut: ${rut}, esPersonaJuridica : ${esPersonaJuridica}'

		def result = false
		def personaJuridica
		def personaNatural

		//if (esPersonaJuridica) {
		personaJuridica = PersonaJuridica.findByRut(rut)
		if (personaJuridica){
			if (Prestador.findByPersonaJuridica(personaJuridica)) {
				log.info("el prestador ya existe como persona juridica")
				return true
			}
		} else {
			log.info("prestador: persona juridica con rut " + rut + " no se encontro")
		}
		//} else {
		personaNatural = PersonaNatural.findByRun(rut)
		if (personaNatural) {
			if (Prestador.findByPersonaNatural(personaNatural)) {
				log.info("el prestador ya existe como persona natural")
				return true
			}
		} else {
			log.info("prestador: persona natural con rut " + rut + " no se encontro")
		}
		//}

		return result
	}

	def getPrestadorInstance(params){
		log.info 'Ejecutando metodo getPrestadorInstance'
		def personaJuridica
		def personaNatural
		def representanteLegal
		def apoderado
		def prestadorInstance  = new Prestador()

		params['run']=((String)params['run']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

		if (params?.esActivo && params?.esActivo == "1")
			prestadorInstance.esActivo = true
		else
			prestadorInstance.esActivo = false

		if (params?.estructuraJuridica) {
			log.info("estructuraJuridica : " + params?.estructuraJuridica);
			prestadorInstance?.estructuraJuridica = EstructuraJuridica.findByCodigo(params?.estructuraJuridica)
		}

		if (params?.tipoPrestador != null) {
			log.info("tipoPrestador : " + params?.tipoPrestador);
			prestadorInstance?.tipoPrestador = TipoPrestador.findByCodigo(params?.tipoPrestador)
		}

		prestadorInstance?.direccion = params?.direccion
		prestadorInstance?.telefono = params?.telefono
		prestadorInstance?.email = params?.email

		if (params?.comuna)
			prestadorInstance?.comuna = Comuna.findByCodigo(params?.comuna)

		if (params?.desdeRL)
			prestadorInstance?.desdeRL = params?.desdeRL
		if (params?.desdeAP)
			prestadorInstance?.desdeAP = params?.desdeAP

		if (params?.hastaRL)
			prestadorInstance?.hastaRL = params?.hastaRL
		if (params?.hastaAP)
			prestadorInstance?.hastaAP = params?.hastaAP

		if (params?.esPersonaJuridica == "1") {
			personaJuridica = PersonaJuridica.findByRut (params?.run)

			if (!personaJuridica) {
				personaJuridica = new PersonaJuridica()
				personaJuridica?.rut = params?.run
			}
			personaJuridica?.razonSocial = params?.nombre

			prestadorInstance?.esPersonaJuridica = true

			prestadorInstance.personaJuridica = personaJuridica
		}

		if (params?.esPersonaJuridica == "2") {

			personaNatural = PersonaNatural.findByRun(params?.run)

			if (!personaNatural) {
				personaNatural = new PersonaNatural()
				personaNatural?.run = params?.run
			}

			prestadorInstance?.esPersonaJuridica = false

			personaNatural?.nombre = params?.nombre
			personaNatural?.apellidoPaterno = " "

			prestadorInstance?.personaNatural = personaNatural
		}

		/*
		 * Representante Legal
		 */
		if (params?.runRL != "") {
			params['runRL']=((String)params['runRL']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

			representanteLegal = PersonaNatural.findByRun(params?.runRL)

			if (!representanteLegal) {
				representanteLegal = new PersonaNatural()
				representanteLegal?.run = params?.runRL
			}

			if (params?.nombreRL)
				representanteLegal?.nombre = params?.nombreRL
			if (params?.paternoRL)
				representanteLegal?.apellidoPaterno = params?.paternoRL
			if (params?.maternoRL)
				representanteLegal?.apellidoMaterno = params?.maternoRL

			prestadorInstance?.representanteLegal = representanteLegal
		}

		/*
		 * Apoderado
		 */
		if (params?.runAP != "") {
			params['runAP']=((String)params['runAP']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

			apoderado = PersonaNatural.findByRun(params?.runAP)
			if (!apoderado) {
				apoderado = new PersonaNatural()
				apoderado?.run = params?.runAP
			}

			if (params?.nombreAP)
				apoderado?.nombre = params?.nombreAP
			if (params?.paternoAP)
				apoderado?.apellidoPaterno = params?.paternoAP
			if (params?.maternoAP)
				apoderado?.apellidoMaterno = params?.maternoAP

			prestadorInstance?.apoderado = apoderado
		}

		def map = ['prestadorInstance': prestadorInstance, 'personaJuridica': personaJuridica, 'personaNatural': personaNatural, 'representanteLegal': representanteLegal, 'apoderador': apoderado]
		return map
	}

	def addPrestador(params) {
		log.info 'Ejecutando metodo addPrestador'
		def personaJuridica = null
		def personaNatural = null
		def yaExiste = false
		def rutPrestador
		def prestadorInstance
		def esPersonaJuridica = false
		def representanteLegal
		def apoderado
		def map
		def next

		params.rut		= params.rut	? params.rut.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()		: params.rut
		params.run		= params.run	? params.run.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()		: params.run
		params.runRL	= params.runRL	? params.runRL.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()	: params.runRL
		params.runAP	= params.runAP	? params.runAP.replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()	: params.runAP
		/*
		 * Vamos a validar que no exista otro prestador con este RUT
		 */
		esPersonaJuridica = params?.esPersonaJuridica ? params?.esPersonaJuridica.toBoolean() : false
		rutPrestador = esPersonaJuridica ? params.rut : params.run

		log.info("Vamos a buscar si ya existe el prestador")
		if (existePrestador(rutPrestador, esPersonaJuridica)) {
			log.info("El prestador ya existe")
			map = getPrestadorInstance(params)
			map['prestadorInstance'].errors.reject("cl.adexus.isl.spm.Prestador.already.exist")
			def model = [:]
			model.put('prestadorInstance', map['prestadorInstance'])
			model.put('personaJuridica', map['personaJuridica'])
			model.put('personaNatural', map['personaNatural'])
			model.put('representanteLegal', map['representanteLegal'])
			model.put('apoderado', map['apoderado'])

			next = ['next': [action: 'create'],  model: model]
			return next
		}

		log.info("Prestador no existe, procedemos a crearlo")
		prestadorInstance = new Prestador()

		if (params?.estructuraJuridica)
			prestadorInstance?.estructuraJuridica = EstructuraJuridica.findByCodigo(params?.estructuraJuridica)
		if (params?.tipoPrestador)
			prestadorInstance?.tipoPrestador = TipoPrestador.findByCodigo(params?.tipoPrestador)
		if (params?.comuna)
			prestadorInstance?.comuna = Comuna.findByCodigo(params?.comuna)

		prestadorInstance.esActivo	 = params?.esActivo ? params?.esActivo.toBoolean() : false
		prestadorInstance?.direccion = params?.direccion
		prestadorInstance?.telefono	 = params?.telefono
		prestadorInstance?.email	 = params?.email
		prestadorInstance?.desdeRL	 = params?.desdeRL
		prestadorInstance?.desdeAP	 = params?.desdeAP
		prestadorInstance?.hastaRL	 = params?.hastaRL
		prestadorInstance?.hastaAP	 = params?.hastaAP

		// Agregamos a la Persona Juridica
		prestadorInstance?.esPersonaJuridica = esPersonaJuridica
		if (esPersonaJuridica) {
			map = agregarPersonaJuridica(params)
			prestadorInstance?.personaJuridica = map.personaJuridica
		} else {
			map = agregarPersonaNatural(params)
			prestadorInstance?.personaNatural = map.personaNatural
		}
		// Representante Legal
		if (params?.runRL && params?.desdeRL) {
			map = agregarRepresentanteLegal(params)
			if (map.representanteLegal) {
				prestadorInstance?.representanteLegal = map.representanteLegal
				if (prestadorInstance?.desdeRL && prestadorInstance?.desdeRL > new Date()){
					prestadorInstance?.representanteLegal.errors.reject("cl.adexus.isl.spm.Prestador.RepresentanteLegal.date.fail")
				}
			}
		}
		//Apoderado
		if (params?.runAP && params?.desdeAP) {
			map = agregarApoderado(params)
			if (map.apoderado) {
				prestadorInstance?.apoderado = map.apoderado
				if (prestadorInstance?.desdeAP && prestadorInstance?.desdeAP > new Date()){
					prestadorInstance?.apoderado.errors.reject("cl.adexus.isl.spm.Prestador.Apoderado.date.fail")
				}
			}
		}
		if (prestadorInstance?.personaJuridica?.hasErrors() ||
		prestadorInstance?.personaNatural?.hasErrors() ||
		prestadorInstance?.representanteLegal?.hasErrors() ||
		prestadorInstance?.apoderado?.hasErrors() ||
		!prestadorInstance.validate()) {
			next = ['next'	: [action: 'create'],
				model	: ['prestadorInstance'	: prestadorInstance
					, 'personaJuridica'	: prestadorInstance?.personaJuridica
					, 'personaNatural'		: prestadorInstance?.personaNatural
					, 'representanteLegal'	: prestadorInstance?.representanteLegal
					, 'apoderado'			: prestadorInstance?.apoderado]]
			return next
		}
		prestadorInstance.save()
		next = ['next': [action: 'edit'],  model: ['prestadorInstance': prestadorInstance,'prestadorId': prestadorInstance?.id]]
		return next

	}

	/*
	 * 
	 * 
	 * 
	 * */
	def updatePrestadorModificado(params){
		log.info "************ Ejecutando metodo updatePrestadorModificado ***************"
		log.debug "Datos recibidos :  ${params}"
		//validar prestador
		def errores = []
		def prestadorInstance = Prestador.get(params?.id)
		log.debug("prestadorInstance.id : ${prestadorInstance.id}")
		prestadorInstance?.direccion = params?.direccion
		prestadorInstance?.comuna = Comuna.findByCodigo(params?.comuna)
		prestadorInstance?.telefono = params?.telefono
		prestadorInstance?.email = params?.email
		prestadorInstance.esActivo = params?.esActivo.toBoolean()

		/*************************validar representante legal****************************/
		//si representante legal existe
		def representanteLegal = null
		def isDelete = false
		log.info("Comienzan validaciones para representante legal ${prestadorInstance.representanteLegal}")

		//validar cuando exista representante legal y se pretende modificar por uno nuevo
		if(prestadorInstance.representanteLegal){
			log.info("Representante legal asociado al prestador existe")
			//desea ser quitado
			if(params?.runRL == "" && params?.nombreRL == "" && params?.paternoRL == "" && params?.maternoRL == "" && !params?.desdeRL && !params?.hastaRL){
				log.info("--> Eliminando representante legal")
				prestadorInstance.representanteLegal = representanteLegal
				prestadorInstance.desdeRL = null
				prestadorInstance.hastaRL = null
				isDelete = true
			}
			//la modificacion del registro se realiza siempre y cuando no se desee borrar el registro lo que implica que todos los valores del formulario esten vacios
			if(!isDelete){
				log.info("No se ha quitado representante legal actual, comienzan validaciones de existencia para representante legal")
				//deja modificar solo contando con run y fecha desde
				if(params?.runRL && params?.desdeRL){
					params.runRL =((String) params.runRL).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
					log.debug("Buscando representante legal asociado a persona natural ${params?.runRL}")
					representanteLegal = PersonaNatural.findByRun(params?.runRL)
					if(!representanteLegal){
						log.debug("Representante legal no existe, creando")
						representanteLegal = new PersonaNatural()
						log.info("Representante legal creado")
					}
					if(!params?.hastaRL){
						log.info("Modificando representante legal en el caso de que la fecha hasta sea vacia")
						representanteLegal.run = params?.runRL
						representanteLegal.nombre = params?.nombreRL
						representanteLegal.apellidoPaterno = params?.paternoRL
						representanteLegal.apellidoMaterno = params?.maternoRL
						prestadorInstance.desdeRL = params?.desdeRL
						prestadorInstance.hastaRL = null
						//prestadorInstance.representanteLegal = representanteLegal
						log.info("Modificacion representante legal en el caso de que la fecha hasta sea vacia concluida")
					}else if(params?.desdeRL < params?.hastaRL){
						log.info("Modificando representante legal si las fechas desde y hasta son validas")
						representanteLegal.run = params?.runRL
						representanteLegal.nombre = params?.nombreRL
						representanteLegal.apellidoPaterno = params?.paternoRL
						representanteLegal.apellidoMaterno = params?.maternoRL


						prestadorInstance.desdeRL = params?.desdeRL
						prestadorInstance.hastaRL = params?.hastaRL
						//prestadorInstance.representanteLegal = representanteLegal
						log.info("Modificando representante legal si las fechas desde y hasta son validas concluida")
					}else{
						log.debug("--> Registrando error : La  fecha inicio de vigencia del representante legal no puede ser superior a su fecha fin de vigencia")
						prestadorInstance.errors.reject('cl.adexus.isl.spm.Prestador.RepresentanteLegal.fechaVigencia.fail',,
								['fecha inicio de vigencia', 'fecha fin de vigencia'] as Object[],
								'[Error: la [{0}] del apoderado no puede ser superior a su [{1}] ]')
					}
				}else{
					log.debug("Registrando error : Para modificar representante legal debe ingresar al menos su rut y su fecha inicio de vigencia ")
					prestadorInstance.errors.reject(
							'cl.adexus.isl.spm.Prestador.RepresentanteLegal.minimumdata.empty',
							['rut', 'fecha inicio de vigencia'] as Object[],
							'[Error : para modificar representante legal debe ingresar al menos su [{0}] y [{1}] ]')
				}
			}
		}

		//creacion de un nuevo representante legal
		if(!prestadorInstance.representanteLegal && !isDelete){
			log.info("No existe representante legal")
			//si no hay registros no hace nada
			if(params?.runRL == "" && params?.nombreRL == "" && params?.paternoRL == "" && params?.maternoRL == "" && !params?.desdeRL && !params?.hastaRL){
				log.info("no hay cambios en representante legal por lo que no realiza modificaciones")
			}else if(params?.runRL && params?.desdeRL){
				params.runRL =((String) params.runRL).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
				representanteLegal = PersonaNatural.findByRun(params?.runRL)
				if(!representanteLegal)
					representanteLegal = new PersonaNatural()
				if(!params?.hastaRL){
					log.debug("--> Creando representante legal")
					representanteLegal.run = params?.runRL
					representanteLegal.nombre = params?.nombreRL
					representanteLegal.apellidoPaterno = params?.paternoRL
					representanteLegal.apellidoMaterno = params?.maternoRL
					prestadorInstance.desdeRL = params?.desdeRL
					prestadorInstance.hastaRL = null
					//prestadorInstance.representanteLegal = representanteLegal
				}else if(params?.desdeRL < params?.hastaRL){
					log.debug("--> Creando representante legal aplicando validacion de fechas")
					representanteLegal.run = params?.runRL
					representanteLegal.nombre = params?.nombreRL
					representanteLegal.apellidoPaterno = params?.paternoRL
					representanteLegal.apellidoMaterno = params?.maternoRL
					prestadorInstance.desdeRL = params?.desdeRL
					prestadorInstance.hastaRL = params?.hastaRL
					//prestadorInstance.representanteLegal = representanteLegal
				}else{
					log.debug("--> Registrando error : La  fecha inicio de vigencia del representante legal no puede ser superior a su fecha fin de vigencia")
					prestadorInstance.errors.reject(
							'cl.adexus.isl.spm.Prestador.RepresentanteLegal.fechaVigencia.fail',
							['fecha inicio de vigencia', 'fecha fin de vigencia'] as Object[],
							'[Error: la  [{0}] del representante legal no puede ser superior a su [{1}] ]')
				}
			}else{
				log.debug("--> Registrando error : Para agregar representante legal debe ingresar al menos su rut y su fecha inicio de vigencia ")
				prestadorInstance.errors.reject(
						'cl.adexus.isl.spm.Prestador.RepresentanteLegal.minimumdata.empty',
						['rut', 'fecha inicio de vigencia'] as Object[],
						'[Error: para agregar un representante legal al prestador debe ingresar al menos su [{0}] y su [{1}] ]')
			}
		}
		log.info("Validaciones para representante legal concluidas")


		/**************************validar apoderado****************************/
		log.info("Comienzan validaciones para apoderado : ${prestadorInstance.apoderado}")
		def apoderado = null
		isDelete = false //flag que verifica si el registro desea ser desvinculado del empleador
		if(prestadorInstance.apoderado){
			//desea ser quitado
			log.info("Apoderado asociado a prestador existe")
			if(params?.runAP == "" && params?.nombreAP == "" && params?.paternoAP == "" && params?.maternoAP == "" && !params?.desdeAP && !params?.hastaAP){
				log.debug("--> eliminando apoderado")
				prestadorInstance.apoderado = apoderado
				prestadorInstance.desdeAP = null
				prestadorInstance.hastaAP = null
				isDelete = true
			}
			//realiza la modificacion siempre y cuando no se desee eliminar el registro
			if(!isDelete){
				if(params?.runAP && params?.desdeAP){
					params.runAP =((String) params.runAP).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
					apoderado = PersonaNatural.findByRun(params?.runAP)
					if(!apoderado)
						apoderado = new PersonaNatural()
					if(!params?.hastaAP){
						log.debug("--> modificando apoderado")
						apoderado.run = params?.runAP
						apoderado.nombre = params?.nombreAP
						apoderado.apellidoPaterno = params?.paternoAP
						apoderado.apellidoMaterno = params?.maternoAP
						prestadorInstance.desdeAP = params?.desdeAP
						prestadorInstance.hastaAP = null
						//prestadorInstance.apoderado = apoderado
					}else if(params?.desdeAP < params?.hastaAP){
						log.debug("--> modificando apoderado aplicando validacion de fechas de vigencia")
						apoderado.run = params?.runAP
						apoderado.nombre = params?.nombreAP
						apoderado.apellidoPaterno = params?.paternoAP
						apoderado.apellidoMaterno = params?.maternoAP
						prestadorInstance.desdeAP = params?.desdeAP
						prestadorInstance.hastaAP = params?.hastaAP
						//prestadorInstance.apoderado = apoderado
						log.debug("--> asociando datos de apoderado a prestador")
					}else{
						log.debug("--> Registrando error : La  fecha inicio de vigencia del apoderado no puede ser superior a su fecha fin de vigencia")
						prestadorInstance.errors.reject(
								'cl.adexus.isl.spm.Prestador.Apoderado.fechaVigencia.fail',
								['fecha inicio de vigencia', 'fecha fin de vigencia'] as Object[],
								'[La  [{0}] del apoderado no puede ser superior a su [{1}] ]')
					}
				}else{
					log.debug("Registrando error : Para modificar apoderado debe ingresar al menos su rut y su fecha inicio de vigencia")
					prestadorInstance.errors.reject(
							'cl.adexus.isl.spm.Prestador.Apoderado.minimumdata.empty',
							['rut', 'fecha inicio de vigencia'] as Object[],
							'[Para modificar apoderado debe ingresar al menos su [{0}] y su [{1}] ]')
				}
			}

		}
		//creacion de un nuevo representante legal
		if(!prestadorInstance.apoderado && !isDelete){
			log.info("Apoderado no existe")

			if(params?.runAP == "" && params?.nombreAP == "" && params?.paternoAP == "" && params?.maternoAP == "" && !params?.desdeAP && !params?.hastaAP){
				log.info("no hay cambios en apoderado por lo que no realiza modificaciones")
			}else if(params?.runAP && params?.desdeAP){
				params.runAP =((String) params.runAP).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
				apoderado = PersonaNatural.findByRun(params?.runAP)
				if(!apoderado)
					apoderado = new PersonaNatural()
				if(!params?.hastaAP){
					log.debug("--> creando apoderado")
					apoderado.run = params?.runAP
					apoderado.nombre = params?.nombreAP
					apoderado.apellidoPaterno = params?.paternoAP
					apoderado.apellidoMaterno = params?.maternoAP
					prestadorInstance.desdeAP = params?.desdeAP
					prestadorInstance.hastaAP = null
					//prestadorInstance.apoderado = apoderado
				}else if(params?.desdeAP < params?.hastaAP){
					log.debug("--> creando apoderado aplicando validacion de fechas de vigencia")
					apoderado.run = params?.runAP
					apoderado.nombre = params?.nombreAP
					apoderado.apellidoPaterno = params?.paternoAP
					apoderado.apellidoMaterno = params?.maternoAP
					prestadorInstance.desdeAP = params?.desdeAP
					prestadorInstance.hastaAP = params?.hastaAP
					//prestadorInstance.apoderado = apoderado
				}else{
					log.debug("--> Registrando error : La  fecha inicio de vigencia del apoderado no puede ser superior a su fecha fin de vigencia")
					prestadorInstance.errors.reject(
							'cl.adexus.isl.spm.Prestador.Apoderado.fechaVigencia.fail',
							['fecha inicio de vigencia', 'fecha fin de vigencia'] as Object[],
							'[Error: la  [{0}] del apoderado no puede ser superior a la [{1}] ]')
				}
			}else{
				log.debug("--> Registrando error : Para crear apoderado debe ingresar al menos su rut y su fecha inicio de vigencia")
				prestadorInstance.errors.reject(
						'cl.adexus.isl.spm.Prestador.Apoderado.minimumdata.empty',
						['rut', 'fecha inicio de vigencia'] as Object[],
						'[Error: para crear apoderado debe ingresar al menos su [{0}] y su [{1}] ]')
			}
		}

		log.info("Validaciones para apoderado concluidas")


		log.info("Comprobando errores en prestadorInstance")
		if(prestadorInstance.hasErrors() || !prestadorInstance.validate()){
			log.debug("Existen errores")
			prestadorInstance.errors.each{error->
				log.debug("--> $error")
			}
			//registrando errores
			errores = prestadorInstance.errors

		}else{
			log.info("No existen errores en prestadorInstance")
			log.info("Comprobando errores en representante legal")
			//mientras sea distinto a nulo lo guarda
			if(representanteLegal){
				representanteLegal.save()
				prestadorInstance.representanteLegal = representanteLegal
			}

			if(apoderado){
				apoderado.save()
				prestadorInstance.apoderado = apoderado
			}
		}

		//retorna a controlador
		def next = ['next'	: [action: 'edit'],
			model	: [prestadorInstance	: prestadorInstance
				, prestadorId		: prestadorInstance?.id
				, personaJuridica	: prestadorInstance?.personaJuridica
				, personaNatural	: prestadorInstance?.personaNatural
				, representanteLegal: prestadorInstance?.representanteLegal
				, apoderado			: prestadorInstance?.apoderado
				, errores:errores]]

		return next
	}

	def updatePrestador(params) {
		log.info "************ Ejecutando metodo updatePrestador ***************"
		log.debug "Datos recibidos :  ${params}"
		def next
		def personaJuridica
		def personaNatural
		def prestadorId
		def someErrors = false
		def representanteLegal
		def apoderado
		def map

		//obtiene prestador
		prestadorId = params?.id
		def prestadorInstance = Prestador.get(prestadorId)
		prestadorInstance.esActivo = params?.esActivo.toBoolean()

		if (params?.estructuraJuridica) {
			log.info("estructuraJuridica : " + params?.estructuraJuridica);
			prestadorInstance?.estructuraJuridica = EstructuraJuridica.findByCodigo(params?.estructuraJuridica)
		}

		if (params?.tipoPrestador != null) {
			log.info("tipoPrestador : " + params?.tipoPrestador);
			prestadorInstance?.tipoPrestador = TipoPrestador.findByCodigo(params?.tipoPrestador)
		}

		prestadorInstance?.direccion = params?.direccion
		prestadorInstance?.telefono = params?.telefono
		prestadorInstance?.email = params?.email

		if (params?.comuna)
			prestadorInstance?.comuna = Comuna.findByCodigo(params?.comuna)

		prestadorInstance?.desdeRL = params?.desdeRL
		prestadorInstance?.desdeAP = params?.desdeAP
		prestadorInstance?.hastaRL = params?.hastaRL
		prestadorInstance?.hastaAP = params?.hastaAP
		// Agregar validacion si corresponde a persona natural o a persona juridica


		//validaciones representante legal
		if (params?.runRL && params?.desdeRL) {
			if(!(prestadorInstance?.desdeRL < prestadorInstance?.hastaRL)){
				log.debug("fecha vigencia hasta superior a fecha vigencia desde en representante legal")
				prestadorInstance?.errors.reject("cl.adexus.isl.spm.Prestador.RepresentanteLegal.date.fail")
				someErrors = false
			}else{
				map = agregarRepresentanteLegal(params)
				someErrors = map.errores
				if (map.representanteLegal) {
					prestadorInstance?.representanteLegal = map.representanteLegal
				}
			}

			/*map = agregarRepresentanteLegal(params)
			 someErrors = map.errores
			 if (map.representanteLegal) {
			 prestadorInstance?.representanteLegal = map.representanteLegal
			 if (prestadorInstance?.desdeRL && prestadorInstance?.desdeRL > new Date()){
			 prestadorInstance?.representanteLegal.errors.reject("cl.adexus.isl.spm.Prestador.RepresentanteLegal.date.fail")
			 someErrors = true
			 }
			 }*/
		}else{
			log.debug("rut o fecha de representante legal vacia")
			prestadorInstance?.errors.reject("cl.adexus.isl.spm.Prestador.RepresentanteLegal.date.empty")
			someErrors = true
		}

		//Apoderado
		if (params?.runAP && params?.desdeAP) {

			map = agregarApoderado(params)
			someErrors = someErrors ? someErrors : map.errores
			if (map.apoderado) {
				prestadorInstance?.apoderado = map.apoderado
				if (prestadorInstance?.desdeAP && prestadorInstance?.desdeAP > new Date()){
					prestadorInstance?.apoderado.errors.reject("cl.adexus.isl.spm.Prestador.Apoderado.date.fail")
					someErrors = true
				}
			}
		}else{
			prestadorInstance?.errors.reject("cl.adexus.isl.spm.Prestador.Apoderado.date.empty")
			someErrors = true
		}

		log.info("calculando errores en prestador")
		if(!prestadorInstance.validate()){
			log.debug("errores prestadorInstance")
			someErrors = true
			prestadorInstance.errors.each{error->
				log.debug("--> $error")
			}
		}else
			log.info("no existen errores para prestador")


		if(!someErrors){
			log.debug("no existen errores, guardando el registro")
			prestadorInstance.save()
		}

		next = ['next'	: [action: 'edit'],
			model	: ['prestadorInstance'	: prestadorInstance
				, 'prestadorId'		: prestadorId
				, 'personaJuridica'	: prestadorInstance?.personaJuridica
				, 'personaNatural'		: prestadorInstance?.personaNatural
				, 'representanteLegal'	: prestadorInstance?.representanteLegal
				, 'apoderado'			: prestadorInstance?.apoderado
				, 'someErrors'			: someErrors]]
		return next
	}


	/**
	 * 
	 * 
	 * 
	 * */
	def agregarPersonaJuridica(params) {
		log.info 'Ejecutando metodo agregarPersonaJuridica'
		def personaJuridica = PersonaJuridica.findByRut (params?.rut)
		if (!personaJuridica) {
			personaJuridica = new PersonaJuridica()
			personaJuridica?.rut = params?.rut
		}
		personaJuridica?.razonSocial = params?.razonSocial
		if (!personaJuridica.validate()) {
			log.info("agregarPersonaJuridica : Fallo en validación de persona jurídica")
			return ['errores': true, 'personaJuridica': personaJuridica]
		}
		personaJuridica.save()
		log.info("agregarPersonaJuridica : La persona jurídica se creo correctamente")
		return ['errores': false, 'personaJuridica': personaJuridica]
	}

	def agregarPersonaNatural(params) {
		log.info 'Ejecutando metodo agregarPersonaNatural'
		def personaNatural
		personaNatural = PersonaNatural.findByRun(params?.run)
		if (!personaNatural) {
			personaNatural = new PersonaNatural()
			personaNatural?.run = params?.run
		}
		personaNatural?.nombre = params?.nombre
		personaNatural?.apellidoPaterno = params?.apePaterno
		personaNatural?.apellidoMaterno = params?.apeMaterno
		if (!personaNatural.validate()) {
			log.info("agregarPersonaNatural : Fallo en validación de persona natural")
			return ['errores': true, 'personaNatural': personaNatural]
		}
		personaNatural.save()
		log.info("agregarPersonaNatural : La persona natural se creo correctamente")
		return ['errores': false, 'personaNatural': personaNatural]
	}

	def agregarRepresentanteLegal(params) {
		log.info '********* Ejecutando metodo agregarRepresentanteLegal ********* '
		log.info "Datos recibidos : ${params}"
		def representanteLegal
		def result
		if (params?.runRL != "") {
			params['runRL']=((String)params['runRL']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

			log.info("agregarRepresentanteLegal runRL : " + params?.runRL)
			representanteLegal = PersonaNatural.findByRun(params?.runRL)
			if (!representanteLegal) {
				log.info("agregarRepresentanteLegal: No se encontro representante legal")
				representanteLegal = new PersonaNatural()
				representanteLegal?.run = params?.runRL
			}
			if (params?.nombreRL)
				representanteLegal?.nombre = params?.nombreRL
			if (params?.paternoRL)
				representanteLegal?.apellidoPaterno = params?.paternoRL
			if (params?.maternoRL)
				representanteLegal?.apellidoMaterno = params?.maternoRL

			if (!representanteLegal.validate()) {
				log.info("agregarRepresentanteLegal: Error en el validate")
				def runError			 = representanteLegal.errors.getFieldError('run')
				def nombreError			 = representanteLegal.errors.getFieldError('nombre')
				def apellidoPaternoError = representanteLegal.errors.getFieldError('apellidoPaterno')
				representanteLegal.clearErrors()
				if (runError)
					representanteLegal.errors.reject("cl.adexus.isl.spm.Prestador.RepresentanteLegal.run.validation.fail",
							[
								'run',
								'class PersonaNatural'] as Object[], "")
				if (nombreError)
					representanteLegal.errors.reject("cl.adexus.isl.spm.Prestador.RepresentanteLegal.nombre.validation.fail",
							[
								'nombre',
								'class PersonaNatural'] as Object[], "")
				if (apellidoPaternoError)
					representanteLegal.errors.reject("cl.adexus.isl.spm.Prestador.RepresentanteLegal.apellidoPaterno.validation.fail",
							[
								'apellidoPaterno',
								'class PersonaNatural'] as Object[], "")

				log.info("Comprobando errores en representante legal")
				representanteLegal?.errors?.each{errors->
					log.debug ("error : $error")
				}

				return ['errores': true, 'representanteLegal': representanteLegal]
			}
			representanteLegal.save()
			result = ['errores': false, 'representanteLegal': representanteLegal]
			return result
		}
	}

	def agregarApoderado(params) {
		log.info 'Ejecutando metodo agregarApoderado'
		def result
		if (params?.runAP != "") {
			params['runAP']=((String)params['runAP']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()

			log.info("runAP : " + params?.runAP)
			def apoderado = PersonaNatural.findByRun(params?.runAP)
			if (!apoderado) {
				log.info("No se encontro el apoderado")
				apoderado = new PersonaNatural()
				apoderado?.run = params?.runAP
			}
			if (params?.nombreAP)
				apoderado?.nombre = params?.nombreAP
			if (params?.paternoAP)
				apoderado?.apellidoPaterno = params?.paternoAP
			if (params?.maternoAP)
				apoderado?.apellidoMaterno = params?.maternoAP

			if (!apoderado.validate()) {
				log.info("agregarApoderado: Error en el validate")
				def runError			 = apoderado.errors.getFieldError('run')
				def nombreError			 = apoderado.errors.getFieldError('nombre')
				def apellidoPaternoError = apoderado.errors.getFieldError('apellidoPaterno')
				apoderado.clearErrors()
				if (runError)
					apoderado.errors.reject("cl.adexus.isl.spm.Prestador.Apoderado.run.validation.fail",
							[
								'run',
								'class PersonaNatural'] as Object[], "")
				if (nombreError)
					apoderado.errors.reject("cl.adexus.isl.spm.Prestador.Apoderado.nombre.validation.fail",
							[
								'nombre',
								'class PersonaNatural'] as Object[], "")
				if (apellidoPaternoError)
					apoderado.errors.reject("cl.adexus.isl.spm.Prestador.Apoderado.apellidoPaterno.validation.fail",
							[
								'apellidoPaterno',
								'class PersonaNatural'] as Object[], "")
				return ['errores': true, 'apoderado': apoderado]
			}

			log.info("Comprobando errores en apoderado")
			if(!apoderado.validate()){
				apoderado?.errors?.each{error->
					log.debug ("--> $error")
				}
			}else{
				log.info("No existen errores en apoderado")
				apoderado.save()
			}



			result = ['errores': false, 'apoderado': apoderado]
			return result
		}

	}

	def deletePrestador(params) {
		log.info 'Ejecutando metodo deletePrestador'
		log.info "Datos recibidos : $params"
		def next
		def prestadorId
		def errores

		prestadorId = params?.id

		log.info("deletePrestador: eliminando prestador : " + prestadorId)
		def prestadorInstance = Prestador.findById(prestadorId)

		try {
			prestadorInstance.delete(flush:true)
			next = ['next': [action: 'list']]
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			log.info("Error no controlado : No es posible eliminar el Prestador mientras aún tenga asociado lo siguiente: Centros de Salud, Convenios, ODAS, OPAS") 
			throw new org.springframework.dao.DataIntegrityViolationException("No es posible eliminar el Prestador mientras aún tenga asociado lo siguiente: Centros de Salud, Convenios, ODAS, OPAS")
		}
		
		return next
	}


	/*
	 * Centros de Salud	
	 */

	def addCentroSalud(params) {
		log.info 'Ejecutando metodo addCentroSalud'
		log.debug("Buscando prestador : ${params?.id}");
		def prestadorInstance = Prestador.findById(params?.id)
		def centroSalud = new CentroSalud()

		log.debug("Asignando prestador a centro de salud")
		centroSalud?.prestador = prestadorInstance
		log.debug("Asignacion de prestador concluida")

		log.debug("Seteando nombre, direccion, telefono e email")
		centroSalud?.nombre = params?.nombre
		centroSalud?.direccion = params?.direccion
		centroSalud?.telefono = params?.telefono
		centroSalud?.email = params?.email
		log.debug("Seteo concluido")

		log.info("Verificando si se ingresaron cantidad de camas")
		if (params?.numeroCamas)
			centroSalud?.numeroCamas = Integer.parseInt(params?.numeroCamas)

		log.info("Verificando si se ingresaron numero de ambulancias")
		if (params?.numeroAmbulancias)
			centroSalud?.numeroAmbulancias = Integer.parseInt(params?.numeroAmbulancias)

		log.info("Verificando si el centro de salud esta activo o inactivo")
		if (params?.esActivo && params?.esActivo == "1")
			centroSalud.esActivo = true
		else
			centroSalud.esActivo = false

		log.info("Verificando existencia de la comuna asociada al centro de salud")
		if (params?.comuna != null) {
			centroSalud?.comuna = Comuna.findByCodigo(params?.comuna)
		}

		log.info("Verificando el tipo de centro de salud")
		if (params?.tipoCentroSalud) {
			centroSalud?.tipoCentroSalud = TipoCentroSalud.findByCodigo(params?.tipoCentroSalud)
		}

		if (params?.otroCentro) {
			centroSalud?.otroCentro = params?.otroCentro
		}

		log.debug("Seteando flags restantes asociados al centro de salud")
		centroSalud?.atencionAmbulancia = false
		centroSalud?.pabellon = false
		centroSalud?.hospitalizacion = false
		centroSalud?.atencionUrgencias = false
		centroSalud?.trasladoPacientes = false
		centroSalud?.salaDeRayos = false
		centroSalud?.rescateUrgencias = false
		centroSalud?.kinesiologia = false
		centroSalud?.imagenologia = false
		centroSalud?.otro = false
		log.debug("Seteo concluido")


		log.info("Verificando existencia de atencion ambulancia")
		if (params?.atencionAmbulancia)
			centroSalud?.atencionAmbulancia = true

		log.info("Verificando existencia de pabellon")
		if (params?.pabellon)
			centroSalud?.pabellon = true

		log.info("Verificando existencia de hospitalizacion")
		if (params?.hospitalizacion)
			centroSalud?.hospitalizacion = true

		log.info("Verificando existencia de atencion de urgencias")
		if (params?.atencionUrgencias)
			centroSalud?.atencionUrgencias = true

		log.info("Verificando existencia de traslado de pacientes")
		if (params?.trasladoPacientes)
			centroSalud?.trasladoPacientes = true

		log.info("Verificando existencia de sala de rayos")
		if (params?.salaDeRayos)
			centroSalud?.salaDeRayos = true

		log.info("Verificando existencia de rescate urgencias")
		if (params?.rescateUrgencias)
			centroSalud?.rescateUrgencias = true

		log.info("Verificando existencia de kinesiologia")
		if (params?.kinesiologia)
			centroSalud?.kinesiologia = true

		log.info("Verificando existencia de imagenologia")
		if (params?.imagenologia)
			centroSalud?.imagenologia = true

		log.info("Verificando existencia de otros")
		if (params?.otro)
			centroSalud?.otro = true

		if (params?.cual)
			centroSalud?.cual = params?.cual

		def r

		//Vemos si ya existe
		log.info("Verificando existencia de centro de salud")
		if (params?.nombre){
			def otroCentroSalud = CentroSalud.findByPrestadorAndNombre(prestadorInstance, params?.nombre)

			if (otroCentroSalud){
				log.debug("Centro de Salud ya existe, registrando error");
				centroSalud.errors.reject('cl.adexus.isl.spm.Prestador.CentroSalud.already.exists')
				log.debug("Redirigiendo a accion create_cs para envio de mensaje")
				r = ['next': [action: 'create_cs', params: ['prestadorId': params?.id]],  model: ['centroSalud': centroSalud]]
				return r
			}
		}
		log.info("Verificacion de existencia concluida")

		log.info("Verificando errores de definicion en centro de saluds")
		if (!centroSalud.validate()) {
			log.debug("Existen errores")
			centroSalud.errors.each{error->
				log.debug("--> $error")
			}
			log.debug("Redirigiendo a accion create_cs para envio de mensaje")
			r = ['next': [action: 'create_cs', params: ['prestadorId': params?.id]],  model: ['centroSalud': centroSalud]]
			return r
		}

		log.info("Verificando si existen errores al momento de almacenar centro de salud")
		if (!centroSalud.save(flush: true)) {
			log.debug("Existen errores, redirigiendo a accion create_cs para envio de error")

			r = ['next': [action: 'create_cs', params: ['prestadorId': params?.id]],  model: ['centroSalud': centroSalud]]
			return r
		}
		log.info("Verificacion de existencia de errores concluida")


		r = ['next': [action: 'edit', params: ['prestadorId': params?.id]],  model: ['centroSalud': centroSalud]]
		log.info("Finalizando metodo addCentroSalud con el siguiente resultado : $r")
		return r

	}


	def updateCentroSalud(params) {
		log.info 'Ejecutando metodo updateCentroSalud'
		log.info("Datos recibidos: centroSaludId: " + params?.centroSaludId);
		def next

		def centroSalud = CentroSalud?.findById(params?.centroSaludId)

		centroSalud?.nombre = params?.nombre
		centroSalud?.direccion = params?.direccion
		centroSalud?.telefono = params?.telefono
		centroSalud?.email = params?.email
		if (params?.numeroCamas)
			centroSalud?.numeroCamas = Integer.parseInt(params?.numeroCamas)

		if (params?.numeroAmbulancias)
			centroSalud?.numeroAmbulancias = Integer.parseInt(params?.numeroAmbulancias)

		if (params?.esActivo && params?.esActivo == "1")
			centroSalud?.esActivo = true
		else
			centroSalud?.esActivo = false

		if (params?.comuna != null) {
			centroSalud?.comuna = Comuna.findByCodigo(params?.comuna)
		}

		if (params?.tipoCentroSalud) {
			centroSalud?.tipoCentroSalud = TipoCentroSalud.findByCodigo(params?.tipoCentroSalud)
		}

		if (params?.otroCentro) {
			centroSalud?.otroCentro = params?.otroCentro
		}

		centroSalud?.atencionAmbulancia = false
		centroSalud?.pabellon = false
		centroSalud?.hospitalizacion = false
		centroSalud?.atencionUrgencias = false
		centroSalud?.trasladoPacientes = false
		centroSalud?.salaDeRayos = false
		centroSalud?.rescateUrgencias = false
		centroSalud?.kinesiologia = false
		centroSalud?.imagenologia = false
		centroSalud?.otro = false

		if (params?.atencionAmbulancia)
			centroSalud?.atencionAmbulancia = true

		if (params?.pabellon)
			centroSalud?.pabellon = true

		if (params?.hospitalizacion)
			centroSalud?.hospitalizacion = true

		if (params?.atencionUrgencias)
			centroSalud?.atencionUrgencias = true

		if (params?.trasladoPacientes)
			centroSalud?.trasladoPacientes = true

		if (params?.salaDeRayos)
			centroSalud?.salaDeRayos = true

		if (params?.rescateUrgencias)
			centroSalud?.rescateUrgencias = true

		if (params?.kinesiologia)
			centroSalud?.kinesiologia = true

		if (params?.imagenologia)
			centroSalud?.imagenologia = true

		if (params?.otro)
			centroSalud?.otro = true

		if (params?.cual)
			centroSalud?.cual = params?.cual

		if (!centroSalud.validate()) {
			next = ['next': [action: 'edit_cs', params: ['prestadorId': params?.id]],  model: ['centroSalud': centroSalud]]
			return next
		}

		if (!centroSalud.save(flush: true)) {
			next = ['next': [action: 'edit_cs', params: ['prestadorId': params?.id]],  model: ['centroSalud': centroSalud]]
		} else {
			next = ['next': [action: 'edit', params: ['prestadorId': params?.id]],  model: ['centroSalud': centroSalud]]
		}

		return next

	}

	def deleteCentroSalud(params) {
		log.info 'Ejecutando metodo DeleteCentroSalud'
		def prestadorInstance = Prestador.findById(params?.id)
		def centroSalud = CentroSalud.findById(params?.centroSaludId)

		def next
		try {
			centroSalud.delete(flush:true)
			next = ['next': [action: 'edit', params: ['prestadorId': params?.id]],  model: ['prestadorInstance': prestadorInstance]]
		} catch (org.h2.jdbc.JdbcSQLException e) {
			prestadorInstance.errors.reject('No puede eliminar prestador.')
			next = ['next': [action: 'edit', params: ['prestadorId': params?.id]],  model: ['prestadorInstance': prestadorInstance]]
		} catch (Exception e) {
			prestadorInstance.errors.reject('cl.adexus.isl.spm.Prestador.CentroSalud.delete.invalid')
			next = ['next': [action: 'edit', params: ['prestadorId': params?.id]],  model: ['prestadorInstance': prestadorInstance]]
		}
		//Issue #1031 Representante Legal
		if (prestadorInstance.representanteLegal!=null && !prestadorInstance.representanteLegal.isAttached()) {
			prestadorInstance.representanteLegal.attach()
		}
		//Apoderado
		if (prestadorInstance.apoderado!=null && !prestadorInstance.apoderado.isAttached()) {
			prestadorInstance.apoderado.attach()
		}

		return next
	}

	/*
	 * Convenio
	 */

	def agregarConvenio(params) {
		log.info 'Ejecutando metodo agregarConvenio'
		log.info("Datos recibidos : prestadorId :  " + params?.id);
		def next
		def errores


		def prestadorInstance = Prestador.findById(params?.id)

		def convenio = new Convenio()

		convenio?.prestador = prestadorInstance

		convenio?.nombre = params?.nombre

		if (params?.tipoConvenio)
			convenio?.tipoConvenio = TipoConvenio?.findByCodigo(params?.tipoConvenio)

		if (params?.numeroResolucion)
			convenio?.numeroResolucion = params?.numeroResolucion
		if (params?.fechaResolucion)
			convenio?.fechaResolucion = params?.fechaResolucion
		if (params?.numeroLicitacion)
			convenio?.numeroLicitacion = params?.numeroLicitacion
		if (params?.fechaAdjudicacion)
			convenio?.fechaAdjudicacion = params?.fechaAdjudicacion

		if (params?.inicio)
			convenio?.inicio = params?.inicio
		if (params?.termino)
			convenio?.termino = params?.termino

		if (params?.periodoReajustable)
			convenio?.periodoReajustable = params?.periodoReajustable
		if (params?.fechaProximoReajuste)
			convenio?.fechaProximoReajuste = params?.fechaProximoReajuste

		if (params?.recargoHorarioInhabil)
			convenio?.recargoHorarioInhabil = Integer.parseInt(params?.recargoHorarioInhabil)

		if (params?.montoConvenido)
			convenio?.montoConvenido = Long.parseLong(params?.montoConvenido)

		convenio?.nombreResponsable = params?.nombreResponsable
		convenio?.cargoResponsable = params?.cargoResponsable
		convenio?.telefonoResponsable = params?.telefonoResponsable
		convenio?.emailResponsable = params?.emailResponsable

		convenio?.nombreISL = params?.nombreISL
		convenio?.cargoISL = params?.cargoISL
		convenio?.telefonoISL = params?.telefonoISL
		convenio?.emailISL = params?.emailISL

		if (params?.esActivo && params?.esActivo == "1")
			convenio.esActivo = true
		else
			convenio.esActivo = false

		if (convenio?.inicio > convenio?.termino) {
			errores = "cl.adexus.isl.spm.Convenio.fechas.fail"
			log.info("error: ${params?.inicio} > ${params?.termino}")
			next = ['next': [action: 'create_cnv'],  model: ['convenio': convenio, 'errores': errores, 'prestadorId': prestadorInstance?.id]]
			return next
		}

		if (convenio?.fechaProximoReajuste > convenio?.termino || convenio?.fechaProximoReajuste < convenio?.inicio) {
			errores = "cl.adexus.isl.spm.Convenio.fechas.fail"
			log.info("error: ${convenio?.inicio} > ${convenio?.termino}")
			next = ['next': [action: 'create_cnv'],  model: ['convenio': convenio, 'errores': errores, 'prestadorId': prestadorInstance?.id]]
			return next
		}

		if (!convenio.validate()) {
			log.info("error: ${convenio?.inicio} > ${convenio?.termino}")
			next = ['next': [action: 'create_cnv'],  model: ['convenio': convenio, 'errores': errores, 'prestadorId': prestadorInstance?.id]]
			return next
		}


		if (!convenio.save(flush: true)) {
			log.info("!convenio.save")
			next = ['next': [action: 'create_cnv',  model: ['convenio': convenio, 'prestadorId': prestadorInstance?.id]]]
		} else {
			next = ['next': [action: 'edit', params: ['prestadorId': params?.id]]]
		}

		return next

	}

	def updateConvenio(params) {
		log.info 'Ejecutando metodo updateConvenio'
		def next
		def result
		def errores
		def csConvenio
		def cntCsConvenio = 0
		def flagError = false
		def listResult = []

		def convenioId = params?.convenioId
		def convenio = Convenio.findById(convenioId)
		def prestadorId = convenio?.prestador?.id

		convenio?.nombre = params?.nombre

		if (params?.tipoConvenio)
			convenio?.tipoConvenio = TipoConvenio?.findByCodigo(params?.tipoConvenio)

		if (params?.numeroResolucion)
			convenio?.numeroResolucion = params?.numeroResolucion
		if (params?.fechaResolucion)
			convenio?.fechaResolucion = params?.fechaResolucion
		if (params?.numeroLicitacion)
			convenio?.numeroLicitacion = params?.numeroLicitacion
		if (params?.fechaAdjudicacion)
			convenio?.fechaAdjudicacion = params?.fechaAdjudicacion

		if (params?.inicio)
			convenio?.inicio = params?.inicio
		if (params?.termino)
			convenio?.termino = params?.termino

		if (params?.periodoReajustable)
			convenio?.periodoReajustable = params?.periodoReajustable
		if (params?.fechaProximoReajuste)
			convenio?.fechaProximoReajuste = params?.fechaProximoReajuste

		if (params?.recargoHorarioInhabil)
			convenio?.recargoHorarioInhabil = Integer.parseInt(params?.recargoHorarioInhabil)

		convenio?.nombreResponsable = params?.nombreResponsable
		convenio?.cargoResponsable = params?.cargoResponsable
		convenio?.telefonoResponsable = params?.telefonoResponsable
		convenio?.emailResponsable = params?.emailResponsable

		convenio?.nombreISL = params?.nombreISL
		convenio?.cargoISL = params?.cargoISL
		convenio?.telefonoISL = params?.telefonoISL
		convenio?.emailISL = params?.emailISL

		if (params?.montoConvenido)
			convenio?.montoConvenido = Long.parseLong(params?.montoConvenido)

		if (params?.esActivo && params?.esActivo == "1")
			convenio.esActivo = true
		else
			convenio.esActivo = false

		if (convenio?.inicio > convenio?.termino) {
			errores = "cl.adexus.isl.spm.Convenio.fechas.fail"
			log.info("error: ${convenio?.inicio} > ${convenio?.termino}")
			next = ['next': [action: 'edit_cnv'],  model: ['convenioId': convenioId, 'errores': errores, 'prestadorId': prestadorId]]
			return next
		}

		if (convenio?.fechaProximoReajuste > convenio?.termino || convenio?.fechaProximoReajuste < convenio?.inicio) {
			errores = "cl.adexus.isl.spm.Convenio.fechas.fail"
			log.info("error: ${convenio?.inicio} > ${convenio?.termino}")
			log.info "convenio tiene errores : ${convenio.hasErrors()}"
			next = ['next': [action: 'edit_cnv'],  model: ['convenioId': convenioId, 'errores': errores, 'prestadorId': prestadorId]]
			return next
		}

		if (!convenio.save(flush: true)) {
			next = ['next': [action: 'create_cnv'],  model: ['convenio': convenio, 'errores': errores, 'prestadorId': convenio?.prestador?.id]]
		} else {
			next = ['next': [action: 'edit'],  model: ['convenio': convenio, 'errores': errores, 'prestadorId': convenio?.prestador?.id]]
		}

		// Ya no se tiene que borrar
		CentroSaludEnConvenio.executeUpdate("DELETE FROM CentroSaludEnConvenio ccs WHERE ccs.convenio=?", [convenio ])
		log.info("Actualizando los centros de salud y el convenio")
		log.info("CS: " + params?.cs)

		if (params?.cs) {
			boolean isArrayList = params?.cs?.getClass().isArray()
			log.info("CS es lista: " + isArrayList)

			if (isArrayList) {
				params?.cs?.each() {
					def  convenioCS = new CentroSaludEnConvenio()
					def centroSalud = CentroSalud.findById(it)

					//Validamos que se pueda agregar el centro de salud al convenio
					result = existArancelesConvenioInCentroSalud(convenio, centroSalud)
					listResult.add(result)
					if (!result["result"])
					{
						log.info("Vamos a agregar el convenio al centro de salud")
						convenioCS.convenio = convenio
						convenioCS.centroSalud = centroSalud

						if (convenio && centroSalud) {
							convenioCS.save(flush: true)
							log.info("Asociación de convenio y centro de salud : " + convenio?.id + " " + centroSalud?.id)
						} else {
							log.info("Error, el convenio " + convenio?.id + " tiene aranceles que se repiten en otro convenio para este centro de salud: " + centroSalud?.id)
						}
					}else{
						log.info("Ya existen prestaciones en otro convenio")
						convenio.errors.reject('cl.adexus.isl.spm.ArancelConvenio.fail',[result.prestacion] as Object[],
						'[La prestacion [{0}] ya existe en otro convenio')
						if(convenio.hasErrors()){
							log.info("Se ha registrado error en actualizacion del registro")
							convenio.errors.each{error->
								log.info ("--> error : $error")
							}
						}
					}
				}

			} else {
				def centroSalud = CentroSalud.findById(params?.cs)

				//Validamos que se pueda agregar el centro de salud al convenio
				result = existArancelesConvenioInCentroSalud(convenio, centroSalud)
				listResult.add(result)
				if (!result["result"])
				{
					def  convenioCS = new CentroSaludEnConvenio()
					convenioCS.convenio = convenio
					convenioCS.centroSalud = centroSalud

					if (convenio && centroSalud) {
						convenioCS.save(flush: true)
						log.info("Asociación de convenio: " + convenio?.id + " y cs: " + centroSalud?.id)
					} else {
						log.info("Error, el convenio " + convenio?.id + " tiene aranceles que se repiten en otro convenio para este centro de salud: " + centroSalud?.id)
					}
				}else{
					log.info("Ya existen prestaciones en otro convenio")
					convenio.errors.reject('cl.adexus.isl.spm.ArancelConvenio.fail',[result.prestacion] as Object[],
					'[La prestacion [{0}] ya existe en otro convenio')
					if(convenio.hasErrors()){
						log.info("Se ha registrado error en actualizacion del registro")
						convenio.errors.each{error->
							log.info ("--> error : $error")
						}
					}
					next = ['next': [action: 'edit_cnv'],  model: ['convenioId': convenioId, 'errores': errores, 'prestadorId': prestadorId]]
					return next
				}
			}
		}

		next.put("listResult", listResult)
		return next

	}

	def existArancelesConvenioInCentroSalud(convenio, centroSalud) {
		log.info 'Ejecutando metodo existArancelesConvenioInCentroSalud'
		def result = [:]
		def arancelesDelConvenio, arancelesEnOtroConvenio, arancelDelConvenio, arancelEnOtroConvenio
		def arancelesCentroDeSalud
		def conveniosCentroDeSalud
		def centroDeSaludEnConvenios
		def i, j, k
		def otroConvenio
		def arancelBase
		def errores

		result.put("result",false)
		result.put("errorCode","")
		result.put("errorMessage","")

		if (!convenio || !centroSalud) {
			return result
		}

		//Obtenemos todos los aranceles del convenio
		arancelesDelConvenio = ArancelConvenio.findAllByConvenio(convenio)
		log.info("total de aranceles encontrados para el convenio: " + arancelesDelConvenio.size())

		//Obtenemos la lista de convenios activos para el Centro de Saludos aranceles asociados al centro de salud
		centroDeSaludEnConvenios = CentroSaludEnConvenio.findAllByCentroSalud(centroSalud)
		log.info("cantidad de convenios asociados al centro de salud: " + centroDeSaludEnConvenios.size())

		if (centroDeSaludEnConvenios.size() == 0) {
			return result
		}

		//Obtenemos todos los Aranceles del Convenio del Centro de Salud
		for (i = 0; i < centroDeSaludEnConvenios.size(); i++) {
			log.info ("Centro Salud: " + centroSalud?.id + " en Convenio : " + centroDeSaludEnConvenios[i]?.convenio?.id)

			otroConvenio = centroDeSaludEnConvenios[i]?.convenio

			arancelesEnOtroConvenio = ArancelConvenio.findAllByConvenio(otroConvenio)

			//Vamos a buscar si los aranceles en el convenio se encuentran en los aranceles del otro convenio
			if (convenio?.id != otroConvenio?.id) {
				for (j = 0;j < arancelesDelConvenio.size(); j++) {
					arancelDelConvenio = arancelesDelConvenio[j]
					for (k = 0;k < arancelesEnOtroConvenio.size(); k++) {
						arancelEnOtroConvenio = arancelesEnOtroConvenio[k]
						if (arancelDelConvenio?.codigoPrestacion == arancelEnOtroConvenio?.codigoPrestacion) {
							log.info("Encontramos arancel duplicado en convenio: " + otroConvenio?.nombre + " :: arancel " +  arancelDelConvenio?.codigoPrestacion)
							result["errorCode"] = "cl.adexus.isl.spm.ArancelConvenio.fail"
							result["errorMessage"] = "Encontramos arancel duplicado en convenio: " + otroConvenio?.nombre + " :: arancel " +  arancelDelConvenio?.codigoPrestacion
							result["result"] = true
							result["prestacion"] = arancelEnOtroConvenio?.codigoPrestacion
							return result
						}
					}
				}
			}
		}

		return result
	}

	def deleteConvenio(params) {
		log.info "Ejecutando metodo deleteConvenio"
		log.info "Datos recibidos : ${params}"
		def next
		def error

		def convenio = Convenio.findById(params?.convenioId)
		def prestadorId = convenio?.prestador?.id


		try {
			if (convenio)
				convenio.delete(flush:true)
		}
		catch(org.springframework.dao.DataIntegrityViolationException e) {
			error = "cl.adexus.isl.spm.Convenio.delete.fail"
			e.printStackTrace();
		}



		next = ['next': [action: 'edit'],  model: ['errorConvenio': error, prestadorId: prestadorId]]
		return next

	}

	/**
	 * Aranceles
	 * @param grupo
	 * @param subgrupo
	 * @param codigo
	 * @return
	 */
	def cargarAranceles(grupo, subgrupo, codigo, incluirPaquetes) {
		log.info 'Ejecutando metodo cargarAranceles'
		def arancelesBase
		def paquetesBase
		def likeCodigo

		if (codigo)	{ likeCodigo = codigo }
		else 		{ likeCodigo = (grupo ? grupo : "") + (subgrupo ? subgrupo : "")}
		def booleanFunction		= (new DataSourceHelper()).booleanValueFunction("TRUE");
		log.info("Vamos a buscar los aranceles basados en el codigo ${likeCodigo}")
		def sql = new Sql(dataSource)
		def sqlQuery =	"SELECT	p.id " +
				", p.codigo " +
				", p.glosa " +
				", p.valorN1 valorN1 " +
				", p.valorN2 valorN2 " +
				", p.valorN3 valorN3 " +
				", 'A' es_paquete " +
				"FROM	Arancel_Base  p " +
				"WHERE 	((:fechaHoy >= p.desde AND p.hasta IS NULL) OR (:fechaHoy >= p.desde AND :fechaHoy <= p.hasta)) " +
				"AND	p.codigo LIKE :likeCodigo " +
				"AND	p.carga_Aprobada = " + booleanFunction + " "
		if (incluirPaquetes) {
			sqlQuery +=	"UNION " +
					"SELECT	p.id " +
					", p.codigo " +
					", p.glosa " +
					", p.valor valorN1 " +
					", 0 valorN2 " +
					", 0 valorN3 " +
					", 'P' es_paquete " +
					"FROM	Paquete p " +
					//"WHERE	((:fechaHoy >= p.desde AND p.hasta IS NULL) OR (:fechaHoy >= p.desde AND :fechaHoy <= p.hasta)) " +
					"WHERE	p.hasta IS NULL " +
					"AND	p.codigo LIKE :likeCodigo "
		}
		sqlQuery +=	"ORDER BY 2"
		def fechaHoy = (new DataSourceHelper()).dateValueFuncion(new Date())
		def queryParams = [fechaHoy: fechaHoy, likeCodigo: likeCodigo + '%']
		def rows		= sql.rows(sqlQuery, queryParams)
		def listaFinal
		rows.each { row ->
			if (!listaFinal) { listaFinal = new ArrayList() }
			listaFinal += [	id: row.id
				, codigo: row.codigo
				, glosa: row.glosa
				, valorN1: row.valorN1
				, valorN2: row.valorN2
				, valorN3: row.valorN3
				, esPaquete: (row.es_paquete == "P")]
		}
		return getTableAranceles(listaFinal)
	}

	def agregarArancelConvenio(params) {
		def convenioId = params?.convenioId
		def arancelBaseId = params?.arancelBaseId
		def nivel = params?.nivel
		def operacion = params?.operacion
		def tipo = params?.tipo
		def esPaquete = params?.esPaquete.toBoolean()
		def dia = params?.fechaArancel.split("-")[0]
		def mes = params?.fechaArancel.split("-")[1]
		def ano = params?.fechaArancel.split("-")[2]
		Calendar fecha = Calendar.getInstance()
		fecha.set(Calendar.DAY_OF_MONTH, dia.toInteger())
		fecha.set(Calendar.MONTH, (mes.toInteger() - 1))
		fecha.set(Calendar.YEAR, ano.toInteger())
		fecha.set(Calendar.HOUR_OF_DAY, 0)
		fecha.set(Calendar.MINUTE, 0)
		fecha.set(Calendar.SECOND, 0)
		fecha.set(Calendar.MILLISECOND, 0)
		def fechaArancel = fecha.getTime()

		def valor = 0
		def valorCalculado = 0
		def valorOriginal = 0
		def calculo

		def convenio = Convenio.findById(convenioId)

		if (params?.valor)
			valor += Integer.parseInt(params?.valor)

		def prestacion
		log.info "Es paquete: ${esPaquete}"
		if (esPaquete) {
			log.info("paqueteId: " + arancelBaseId)
			prestacion = Paquete.findById(arancelBaseId)
			log.info("codigoPrestacion: ${prestacion?.codigo}")
			valorOriginal = prestacion.valor
		} else {
			log.info("arancelBaseId: " + arancelBaseId)
			prestacion = ArancelBase.findById(arancelBaseId)
			log.info("codigoPrestacion: ${prestacion?.codigo}")
			valorOriginal = getValorOriginalPrestacionActivaByNivel(prestacion, nivel)
		}

		def descuento	= operacion.toUpperCase() == "DESCUENTO"
		def cargo		= operacion.toUpperCase() == "CARGO"
		def pesos		= tipo?.toUpperCase() == "PESOS"
		def porcentaje	= tipo?.toUpperCase() == "PRC"

		if (cargo && pesos) {
			calculo = "+" + valor + "\$"
			valorCalculado = valorOriginal + valor
		} else if (cargo && porcentaje) {
			calculo = "+" + valor + "%"
			valorCalculado = valorOriginal * (1 + (valor / 100))
		} else if (descuento  && pesos) {
			calculo = "-" + valor + "\$"
			valorCalculado = valorOriginal - valor
		} else if (descuento  && porcentaje) {
			calculo = "-" + valor + "%"
			valorCalculado = valorOriginal * (1 - (valor / 100))
		}

		if (valorCalculado < 0) {
			log.info("Error, no se agrega el arancel [${prestacion?.codigo}], ya que el nuevo valor de calculo dio menor a cero : " + valorCalculado)
			return [status: false, mensaje: "Error, no se agrega el arancel [${prestacion?.codigo}], ya que el nuevo valor de calculo dio menor a cero : " + valorCalculado]
		}

		// Validar fecha de arancel mayor que fecha desde convenio
		if (fechaArancel < convenio.inicio) {
			log.info("Error, no se agrega el arancel [${prestacion?.codigo}], ya que la fecha ingresada no puede ser menor a la fecha del convenio [${FormatosISLHelper.fechaCortaStatic(convenio.inicio)}]")
			return [status: false, mensaje: "Error, no es posible se agrega el o los aranceles, ya que la fecha seleccionada no puede ser menor a la fecha del convenio [${FormatosISLHelper.fechaCortaStatic(convenio.inicio)}]"]
		}
		// Validar fecha de arancel sea mayor que la fecha desde del arancel base
		if (fechaArancel < prestacion.desde) {
			log.info("Error, no se agrega el arancel [${prestacion?.codigo}], ya que la fecha ingresada no puede ser menor a la fecha del arancel base [${FormatosISLHelper.fechaCortaStatic(prestacion.desde)}]")
			return [status: false, mensaje: "Error, no se agrega el arancel [${prestacion?.codigo}], ya que la fecha ingresada no puede ser menor a la fecha del arancel base [${FormatosISLHelper.fechaCortaStatic(prestacion.desde)}]"]
		}
		// Aquí va la logica de creación de nuevos aranceles convenio
		def arancelConvenioVigente = ArancelConvenio.findByConvenioAndCodigoPrestacionAndHastaIsNull(convenio, prestacion?.codigo)
		// Validar fecha de arancel mayor+1 que fecha desde del arancel convenio anterior
		if (arancelConvenioVigente) {
			if (arancelConvenioVigente.desde > (fechaArancel - 1)) {
				log.info("Error, no se agrega el arancel [${prestacion?.codigo}], ya que la fecha ingresada debe ser mayor a la fecha del arancel anterior [${FormatosISLHelper.fechaCortaStatic(arancelConvenioVigente.desde)}]")
				return [status: false, mensaje: "Error, no es posible el o los aranceles, ya que la fecha ingresada debe ser mayor a la fecha del arancel anterior [${FormatosISLHelper.fechaCortaStatic(arancelConvenioVigente.desde)}]"]
			}
			arancelConvenioVigente?.hasta = (fechaArancel)
			log.info ("Fecha final: "+ arancelConvenioVigente?.hasta)
			if (!arancelConvenioVigente.save()) {
				log.info("Fallo al grabar el arancel convenio [${arancelConvenioVigente?.codigoPrestacion}] \nErrores:${arancelConvenioVigente.errors.allErrors}")
				return [status: false, mensaje: "Fallo al grabar el arancel convenio [${arancelConvenioVigente?.codigoPrestacion}] \nErrores:${arancelConvenioVigente.errors.allErrors}"]
			}
			def arancelConvenioPostCambio = ArancelConvenio.findByConvenioAndCodigoPrestacion(convenio, prestacion?.codigo)
		}
		def arancelConvenioNuevo = new ArancelConvenio()
		arancelConvenioNuevo?.convenio = convenio
		arancelConvenioNuevo?.codigoPrestacion = prestacion?.codigo
		arancelConvenioNuevo?.valor = valor
		arancelConvenioNuevo?.nivel = nivel
		arancelConvenioNuevo?.valor = valor
		arancelConvenioNuevo?.descuento = descuento
		arancelConvenioNuevo?.cargo = cargo
		arancelConvenioNuevo?.porcentaje = porcentaje
		arancelConvenioNuevo?.pesos = pesos
		arancelConvenioNuevo?.calculo = calculo
		arancelConvenioNuevo?.valorOriginal = valorOriginal
		arancelConvenioNuevo?.valorNuevo = valorCalculado
		arancelConvenioNuevo?.desde = fechaArancel
		if (!arancelConvenioNuevo.save()) {
			log.info("Fallo al grabar el arancel convenio [${arancelConvenioNuevo?.codigoPrestacion}] \nErrores:${arancelConvenioNuevo.errors.allErrors}")
			return [status: false, mensaje: "Fallo al grabar el arancel convenio [${arancelConvenioNuevo?.codigoPrestacion}] \nErrores:${arancelConvenioNuevo.errors.allErrors}"]
		}
		log.info("[Ok]: agregarArancelConvenio")
		return [status: true, mensaje: "OK"]
	}


	def agregarArancelPaquete(params) {
		log.info 'Ejecutando metodo agregarArancelPaquete'
		log.info "agregarArancelPaquete: ${params}"
		def paqueteId		= params?.paqueteId
		def arancelBaseId	= params?.arancelBaseId
		def nivel			= params?.nivel
		def operacion		= params?.operacion
		def cantidad		= params?.cantidad
		def valor			= params?.valor
		def tipo			= params?.tipo
		def paquete			= Paquete.findById(paqueteId)
		def arancelBase		= ArancelBase.findById(arancelBaseId)
		if (!arancelBase) {
			log.info("El paquete ID [${paqueteId}] : ")
			return
		}
		def arancelPaqueteVigente				= ArancelPaquete.findByPaqueteAndCodigoPrestacionAndNivelAndHastaIsNull(paquete, arancelBase?.codigo, nivel)
		def arancelPaqueteNuevo					= new ArancelPaquete()
		arancelPaqueteNuevo?.paquete			= paquete
		arancelPaqueteNuevo?.codigoPrestacion 	= arancelBase.codigo
		if (valor.isInteger()) {
			arancelPaqueteNuevo?.valor			= valor.toInteger()
		}
		if (cantidad.isInteger()) {
			arancelPaqueteNuevo?.cantidad		= cantidad.toInteger()
		}
		arancelPaqueteNuevo?.nivel				= nivel
		arancelPaqueteNuevo?.descuento			= (operacion.toUpperCase() == "DESCUENTO")
		arancelPaqueteNuevo?.cargo				= (operacion.toUpperCase() == "CARGO")
		arancelPaqueteNuevo?.pesos 				= (tipo == "PESOS")
		arancelPaqueteNuevo?.porcentaje 		= (tipo == "PRC")

		if (arancelPaqueteNuevo?.cargo && arancelPaqueteNuevo?.pesos) {
			arancelPaqueteNuevo?.calculo		= "+" + arancelPaqueteNuevo?.valor + "\$"
		} else if (arancelPaqueteNuevo?.cargo && arancelPaqueteNuevo?.porcentaje) {
			arancelPaqueteNuevo?.calculo		= "+" + arancelPaqueteNuevo?.valor + "%"
		} else if (arancelPaqueteNuevo?.descuento  && arancelPaqueteNuevo?.pesos) {
			arancelPaqueteNuevo?.calculo 		= "-" + arancelPaqueteNuevo.valor + "\$"
		} else if (arancelPaqueteNuevo?.descuento  && arancelPaqueteNuevo?.porcentaje) {
			arancelPaqueteNuevo?.calculo 		= "-" + arancelPaqueteNuevo?.valor + "%"
		}
		arancelPaqueteNuevo?.valorOriginal		= 0
		arancelPaqueteNuevo?.valorNuevo			= 0
		// Aqui va la logica de fechas para creación de los nuevos paquetes
		Calendar cal = Calendar.getInstance()
		cal.set(Calendar.HOUR_OF_DAY, 0)
		cal.set(Calendar.MINUTE, 0)
		cal.set(Calendar.SECOND, 0)
		cal.set(Calendar.MILLISECOND, 0)
		def hoy = cal.getTime()
		arancelPaqueteNuevo.desde = hoy
		if (arancelPaqueteVigente) {
			arancelPaqueteVigente.hasta = arancelPaqueteNuevo.desde
			if (!arancelPaqueteVigente.validate()) {
				log.info("Fallo al validar el arancel paquete vigente : " + arancelPaqueteNuevo)
				return
			}
			arancelPaqueteVigente.save()
			log.info("Grabo correctamente el arancel paquete vigente : [${arancelPaqueteVigente.id}]")
		}

		if (!arancelPaqueteNuevo.validate()) {
			log.info("Fallo al validar el arancel convenio : " + arancelPaqueteNuevo)
			return
		}
		arancelPaqueteNuevo.save()
		log.info("Grabo correctamente el arancel paquete : [${arancelPaqueteNuevo.id}]")
	}

	def getTableAranceles(aranceles) {
		log.info 'Ejecutando metodo getTableAranceles'
		def result = ""
		if (!aranceles) {
			result += "	<tr>"
			result += "		<td colspan=\"6\"> No existen prestaciones con el filtro seleccionado... </td>"
			result += "	</tr>"
		} else {
			aranceles.each {
				result += "	<tr>"
				result += "		<td align=\"center\"><input type=\"checkbox\" id=\"ch_aranceles\"  name=\"ch_aranceles\" value=\"${it?.id}\" class=\"${it?.esPaquete}\"></td>"
				result += "		<td align=\"center\">${it.codigo}</td>"
				result += "		<td title=\"${it.glosa}\">${FormatosHelper.truncateStatic(it.glosa, 70)}</td>"
				result += "		<td align=right>${FormatosHelper.montoStatic(it.valorN1)}</td>"
				result += "		<td align=right>${FormatosHelper.montoStatic(it.valorN2)}</td>"
				result += "		<td align=right>${FormatosHelper.montoStatic(it.valorN3)}</td>"
				result += "	</tr>"
			}
		}
		return result;
	}


	def getListaArancelesByConvenioId(convenioId) {
		log.info 'Ejecutando metodo getListaArancelesByConvenioId'
		def result = new ArrayList()
		if (!convenioId) { return null }

		def sql = new Sql(dataSource)
		def sqlQuery =	"SELECT	'A' tipo_prestacion, arba.codigo, arco.desde, arco.*, arba.id prestacion_id " +
				"FROM	Arancel_Convenio arco, Arancel_Base arba " +
				"WHERE	arco.codigo_Prestacion = arba.codigo " +
				"AND	((:fechaHoy >= arba.desde AND arba.hasta IS NULL) OR (:fechaHoy >= arba.desde AND :fechaHoy <= arba.hasta)) " +
				//"AND	((:fechaHoy >= arco.desde AND arco.hasta IS NULL) OR (:fechaHoy >= arco.desde AND :fechaHoy <= arco.hasta)) " +
				//"AND	arco.hasta IS NULL " +
				"AND	arco.convenio_id = :convenio " +
				"UNION " +
				"SELECT	'P' tipo_prestacion, arba.codigo, arco.desde, arco.*, arba.id prestacion_id " +
				"FROM	Arancel_Convenio arco, Paquete arba " +
				"WHERE	arco.codigo_Prestacion = arba.codigo " +
				"AND	((:fechaHoy >= arba.desde AND arba.hasta IS NULL) OR (:fechaHoy >= arba.desde AND :fechaHoy <= arba.hasta)) " +
				//"AND	((:fechaHoy >= arco.desde AND arco.hasta IS NULL) OR (:fechaHoy >= arco.desde AND :fechaHoy <= arco.hasta)) " +
				//"AND	arco.hasta IS NULL " +
				"AND	arco.convenio_id = :convenio " +
				"ORDER BY 2, 3"
		def queryParams = [fechaHoy: (new DataSourceHelper()).dateValueFuncion(new Date()), convenio: convenioId]
		def rows		= sql.rows(sqlQuery, queryParams)
		log.info "getListaArancelesByConvenioId: ${rows?.size()}"
		rows.each { row ->
			def arancelConvenio = [ id					: row.id
				, codigoPrestacion	: row.codigo_Prestacion
				, nivel				: row.nivel
				, calculo				: row.calculo
				, valorOriginal		: row.valor_Original
				, valor				: row.valor
				, valorNuevo			: row.valor_Nuevo
				, cargo				: row.cargo
				, descuento			: row.descuento
				, pesos				: row.pesos
				, porcentaje			: row.porcentaje
				, desde				: row.desde
				, hasta				: row.hasta]
			def prestacion
			if (row.tipo_prestacion == "A") {
				prestacion = ArancelBase.get(row.prestacion_id)
			} else {
				prestacion = Paquete.get(row.prestacion_id)
			}
			result.add([arancelConvenio, prestacion])
		}
		if (result.size() == 0) return null

		log.info("result.size :" + result.size())
		result.each {
			def arancelConvenio = it[0]
			def arancelBase = it[1]
			arancelConvenio.valorOriginal = getValorOriginalPrestacionActivaByNivel(arancelBase, arancelConvenio.nivel)

			if (arancelConvenio.cargo && arancelConvenio.pesos) {
				arancelConvenio.calculo = "+" + arancelConvenio.valor + "\$"
				arancelConvenio.valorNuevo = arancelConvenio.valorOriginal + arancelConvenio.valor
			} else if (arancelConvenio.cargo && arancelConvenio.porcentaje) {
				arancelConvenio.calculo = "+" + arancelConvenio.valor + "%"
				arancelConvenio.valorNuevo = arancelConvenio.valorOriginal * (1 + (arancelConvenio.valor / 100))
			} else if (arancelConvenio.descuento  && arancelConvenio.pesos) {
				arancelConvenio.calculo = "-" + arancelConvenio.valor + "\$"
				arancelConvenio.valorNuevo = arancelConvenio.valorOriginal - arancelConvenio.valor
			} else if (arancelConvenio.descuento  && arancelConvenio.porcentaje) {
				arancelConvenio.calculo = "-" + arancelConvenio.valor + "%"
				arancelConvenio.valorNuevo = arancelConvenio.valorOriginal * (1 - (arancelConvenio.valor / 100))
			}
		}
		return result

	}

	def getValorRecargo(Paquete paquete, ArancelConvenio arancelesConvenio) {
		log.info 'Ejecutando metodo getValorRecargo 1'
		if (paquete == null || arancelesConvenio == null) { return 0 }
		def valorPactado = getValorPactado(paquete, arancelesConvenio)
		def recargoHorario = arancelesConvenio.convenio?.recargoHorarioInhabil
		return valorPactado * (recargoHorario / 100)
	}

	def getValorRecargo(ArancelBase arancelBase, ArancelConvenio arancelesConvenio) {
		log.info 'Ejecutando metodo getValorRecargo 2'
		if (arancelBase == null || arancelesConvenio == null) { return 0 }
		def valorPactado = getValorPactado(arancelBase, arancelesConvenio)
		def recargoHorario = arancelesConvenio.convenio?.recargoHorarioInhabil
		return valorPactado * (recargoHorario / 100)
	}

	def getValorOriginalPrestacionActivaByNivel(Paquete paquete, String nivel) {
		log.info 'Ejecutando metodo getValorOriginalPrestacionActivaByNivel 1'
		return !paquete ? 0 : paquete.valor
	}

	def getValorOriginalPrestacionActivaByNivel(ArancelBase arancelBase, String nivel) {
		log.info 'Ejecutando metodo getValorOriginalPrestacionActivaByNivel 2'
		if (!arancelBase)
			return 0
		if (nivel.toUpperCase() == "NIVEL 1")
			return arancelBase?.valorN1
		if (nivel.toUpperCase() == "NIVEL 2")
			return arancelBase?.valorN2
		if (nivel.toUpperCase() == "NIVEL 3")
			return arancelBase?.valorN3
		return 0
	}

	def getValorPactado(Paquete paquete, ArancelConvenio arancelConvenio) {
		log.info 'Ejecutando metodo getValorPactado 1'
		def valorOriginal = getValorOriginalPrestacionActivaByNivel(paquete, arancelConvenio?.nivel)
		def valorCalculado = 0

		if (arancelConvenio?.cargo && arancelConvenio.pesos) {
			valorCalculado = valorOriginal + arancelConvenio?.valor
		}
		if (arancelConvenio?.cargo && arancelConvenio?.porcentaje) {
			valorCalculado = valorOriginal * (1 + (arancelConvenio?.valor / 100))
		}
		if (arancelConvenio?.descuento  && arancelConvenio?.pesos) {
			valorCalculado = valorOriginal - arancelConvenio?.valor
		}
		if (arancelConvenio?.descuento  && arancelConvenio?.porcentaje) {
			valorCalculado = valorOriginal * (1 - (arancelConvenio?.valor / 100))
		}
		return valorCalculado
	}

	def getValorPactado(ArancelBase arancelBase, ArancelConvenio arancelConvenio) {
		log.info 'Ejecutando metodo getValorPactado 2'
		def valorOriginal = getValorOriginalPrestacionActivaByNivel(arancelBase, arancelConvenio?.nivel)
		def valorCalculado = 0

		if (arancelConvenio?.cargo && arancelConvenio.pesos) {
			valorCalculado = valorOriginal + arancelConvenio?.valor
		}
		if (arancelConvenio?.cargo && arancelConvenio?.porcentaje) {
			valorCalculado = valorOriginal * (1 + (arancelConvenio?.valor / 100))
		}
		if (arancelConvenio?.descuento  && arancelConvenio?.pesos) {
			valorCalculado = valorOriginal - arancelConvenio?.valor
		}
		if (arancelConvenio?.descuento  && arancelConvenio?.porcentaje) {
			valorCalculado = valorOriginal * (1 - (arancelConvenio?.valor / 100))
		}
		return valorCalculado
	}

	def getListaArancelesByPaqueteId(paqueteId) {
		log.info 'Ejecutando metodo getListaArancelesByPaqueteId'
		def result = null

		def paquete = Paquete.findById(paqueteId)
		if (!paquete) return result

		def arancelesPaquete = ArancelPaquete.executeQuery(
				"SELECT	arpa, arba " +
				"FROM	ArancelPaquete arpa, ArancelBase arba " +
				"WHERE	arpa.codigoPrestacion = arba.codigo " +
				"AND	((:fechaHoy >= arba.desde AND arba.hasta IS NULL) OR (:fechaHoy BETWEEN arba.desde AND arba.hasta)) " +
				"AND	arpa.hasta IS NULL " +
				"AND	arpa.paquete = :paquete " +
				"ORDER BY arba.codigo "
				, [fechaHoy: new Date(), paquete: paquete])
		log.info("arancelesPaquete.size: ${arancelesPaquete?.size()}")

		if (!arancelesPaquete) return result

		def arancelesPaqueteList = new ArrayList()
		def valorTotal = 0
		arancelesPaquete.each {
			def arancelPaquete = it[0]
			def arancelBase = it[1]
			arancelPaquete.valorOriginal = getValorOriginalPrestacionActivaByNivel(arancelBase, arancelPaquete.nivel)

			if (arancelPaquete.cargo && arancelPaquete.pesos) {
				arancelPaquete.calculo = "+" + arancelPaquete.valor + "\$"
				arancelPaquete.valorNuevo = (arancelPaquete.cantidad * arancelPaquete.valorOriginal) + arancelPaquete.valor
			}
			if (arancelPaquete.cargo && arancelPaquete.porcentaje) {
				arancelPaquete.calculo = "+" + arancelPaquete.valor + "%"
				arancelPaquete.valorNuevo = (arancelPaquete.cantidad * arancelPaquete.valorOriginal) * (1 + (arancelPaquete.valor / 100))
			}
			if (arancelPaquete.descuento  && arancelPaquete.pesos) {
				arancelPaquete.calculo = "-" + arancelPaquete.valor + "\$"
				arancelPaquete.valorNuevo = (arancelPaquete.cantidad * arancelPaquete.valorOriginal) - arancelPaquete.valor
			}
			if (arancelPaquete.descuento  && arancelPaquete.porcentaje) {
				arancelPaquete.calculo = "-" + arancelPaquete.valor + "%"
				arancelPaquete.valorNuevo = (arancelPaquete.cantidad * arancelPaquete.valorOriginal) * (1 - (arancelPaquete.valor / 100))
			}
			valorTotal += arancelPaquete.valorNuevo
			arancelesPaqueteList += [ "id"				: arancelPaquete.id
				, "codigoPrestacion": arancelPaquete.codigoPrestacion
				, "glosa"			: arancelBase.glosa
				, "cantidad"		: arancelPaquete.cantidad
				, "nivel"			: arancelPaquete.nivel
				, "valorOriginal"	: arancelPaquete.valorOriginal
				, "calculo"			: arancelPaquete.calculo
				, "valorNuevo"		: arancelPaquete.valorNuevo]
		}
		paquete.valor = valorTotal
		paquete.save()
		result = arancelesPaqueteList
		return result
	}

	def desplegarArancelesConvenio(convenioId) {
		log.info 'Ejecutando metodo desplegarArancelesConvenio'
		def result	= ""
		def convenio = Convenio.findById(convenioId)
		if (!convenio) {
			result += "	<tr>"
			result += "		<td colspan=8> No se encontro convenio: ${convenioId} </td>"
			result += "	</tr>"
			return result;
		}

		def listaArancelesEnConvenio = getListaArancelesByConvenioId(convenioId)
		if (!listaArancelesEnConvenio) {
			result += "	<tr>"
			result += "		<td colspan=8> Convenio sin Aranceles. </td>"
			result += "	</tr>"
			return result;
		}
		listaArancelesEnConvenio.each {
			def arancelConvenio = it[0]
			def arancelBase = it[1]
			result += "	<tr>"
			result += "		<td align=center><input type=\"checkbox\" id=\"ch_cnv_arc\"  name=\"ch_cnv_arc\" value=\"${arancelConvenio?.id}\"></td>"
			result += "		<td align=center>${arancelConvenio.codigoPrestacion}</td>"
			result += "		<td title='${arancelBase.glosa}'>${FormatosISLHelper.truncateStatic(arancelBase.glosa, 70)}</td>"
			result += "		<td align=center>${arancelConvenio.nivel}</td>"
			result += "		<td align=right>${FormatosISLHelper.montoStatic(arancelConvenio.valorOriginal)}</td>"
			result += "		<td align=right>${arancelConvenio.calculo}</td>"
			result += "		<td align=right>${FormatosISLHelper.montoStatic(arancelConvenio.valorNuevo)}</td>"
			result += "		<td align=center>${FormatosHelper.fechaCortaStatic(arancelConvenio.desde)}</td>"
			result += "		<td align=center>${FormatosHelper.sumarDiasStatic(arancelConvenio.hasta, -1)}</td>"
			if (arancelConvenio.hasta == null) {
				result += "		<td align=center><a title=\"Más información\" onclick=\"TINY.box.show({iframe:'../aranceles/ver_prestacion_detalle/list?arancelBaseId=${arancelBase.id}&codigoPrestacion=${arancelConvenio.codigoPrestacion}',boxid:'frameless',width:1000,height:400,fixed:false,maskid:'bluemask',maskopacity:40})\" ><i class=\"icon-2x icon-info-sign color-success\" style=\"cursor: pointer;\"></i></a></td>"
			} else {
				result += "		<td align=center>&nbsp;</td>"
			}
			result += "	</tr>"
		}
		return result;
	}

	def desplegarArancelesPaquete(paqueteId) {
		log.info 'Ejecutando metodo desplegarArancelesPaquete'
		def result = ""
		def paquete = Paquete.findById(paqueteId)
		if (!paquete) {
			result += "	<tr>"
			result += "		<td colspan=8> No se encontro paquete: ${paqueteId} </td>"
			result += "	</tr>"
			return result;
		}
		def arancelesPaquete = getListaArancelesByPaqueteId(paqueteId)
		if (!arancelesPaquete) {
			result += "	<tr>"
			result += "		<td colspan=8> Paquete sin aranceles asociados. </td>"
			result += "	</tr>"
		}
		arancelesPaquete.each {
			result += "	<tr>"
			result += "		<td align=center><input type=\"checkbox\" id=\"ch_cnv_arc\"  name=\"ch_cnv_arc\" value=\"${it?.id}\"></td>"
			result += "		<td align=center>${it.codigoPrestacion}</td>"
			result += "		<td title='${it.glosa}'>${FormatosISLHelper.truncateStatic(it.glosa)}</td>"
			result += "		<td align=center>${it.nivel}</td>"
			result += "		<td align=right>${it.cantidad}</td>"
			result += "		<td align=right>${FormatosISLHelper.montoStatic(it.valorOriginal)}</td>"
			result += "		<td align=right>${it.calculo}</td>"
			result += "		<td align=right>${FormatosISLHelper.montoStatic(it.valorNuevo)}</td>"
			result += "	</tr>"
		}
		return result;
	}

	def getArancelesConvenioGlosaById(arancelConvenioId) {
		log.info 'Ejecutando metodo getArancelesConvenioGlosaById 1'
		def result = "Glosa no encontrada"
		if (!arancelConvenioId) return result
		def arancelConvenio = ArancelConvenio.findById(arancelConvenioId)
		if (!arancelConvenio) return result
		def prestacion = getPrestacionByCodigo(arancelConvenio?.codigoPrestacion)
		if (prestacion) return prestacion?.glosa
		return result
	}

	def getArancelesPaqueteGlosaById(arancelPaqueteId) {
		log.info 'Ejecutando metodo getArancelesConvenioGlosaById 2'
		def result = "Glosa no encontrada"
		if (!arancelPaqueteId) return result
		def arancelPaquete = ArancelPaquete.findById(arancelPaqueteId)
		if (!arancelPaquete) return result
		def prestacion = getPrestacionByCodigo(arancelPaquete?.codigoPrestacion)
		if (prestacion) return prestacion?.glosa
		return result

	}

	def eliminarArancelConvenio(arancelConvenioId) {
		log.info 'Ejecutando metodo eliminarArancelConvenio'
		def result = null
		if (!arancelConvenioId) return result
		def arancelConvenio = ArancelConvenio.findById(arancelConvenioId)
		if (!arancelConvenio) return result
		arancelConvenio.delete(flush: true)
		return
	}

	def eliminarArancelPaquete(arancelPaqueteId) {
		log.info 'Ejecutando metodo eliminarArancelPaquete'
		def result = null
		if (!arancelPaqueteId) return result
		def arancelPaquete = ArancelPaquete.findById(arancelPaqueteId)
		if (!arancelPaquete) return result
		arancelPaquete.delete(flush: true)
		return
	}

	def getPersonaNatural (String run) {
		log.info 'Ejecutando metodo getPersonaNatural'
		log.info "Datos recibidos: run:  ${run}"
		def personaNatural = PersonaNatural.findByRun(run)
		return personaNatural
	}

	def getPersonaJuridica (String rut) {
		log.info "Buscar persona juridica: ${rut}"
		def personaJuridica = PersonaJuridica.findByRut(rut)
		return personaJuridica
	}

	def getPrestacionByCodigo(codigoPrestacion) {
		log.info 'Ejecutando metodo getPrestacionByCodigo'
		return getPrestacionByCodigo(codigoPrestacion, null)
	}

	def getPrestacionByCodigo(codigoPrestacion, fecha) {
		log.info 'Ejecutando metodo getArancelesConvenioGlosaById 1'
		def arancelesBase
		fecha = !fecha ? new Date() : fecha
		//log.info("Vamos a buscar los aranceles basados en el codigo [${codigoPrestacion}] con fecha [${FormatosISLHelper.fechaCortaStatic(fecha)}]")
		arancelesBase = ArancelBase.executeQuery(
				"SELECT	p " +
				"FROM	ArancelBase  p " +
				"WHERE 	((:fechaHoy >= p.desde AND p.hasta IS NULL) OR (:fechaHoy BETWEEN p.desde AND p.hasta)) " +
				"AND 	p.codigo = '${codigoPrestacion}' ",
				[fechaHoy: fecha])

		// Ahora se tiene que agregar logica para que las consultas contemplen los PAQUETES...FUCK ME RIGHT :(
		if (arancelesBase?.size() == 0) {
			log.info "No existe prestación [${codigoPrestacion}] en ARANCEL_BASE, vamos a buscarla a PAQUETES"
			arancelesBase = Paquete.executeQuery(
					"SELECT	p " +
					"FROM	Paquete p " +
					"WHERE 	((:fechaHoy >= p.desde AND p.hasta IS NULL) OR (:fechaHoy BETWEEN p.desde AND p.hasta)) " +
					"AND 	p.codigo = '${codigoPrestacion}' ",
					[fechaHoy: fecha])
		}
		return arancelesBase[0]
	}
}
