package cl.adexus.isl.spm

import cl.adexus.helpers.DataSourceHelper
import grails.converters.JSON
import groovy.sql.Sql


class PrestadorController {

	def PrestadorService
	def dataSource


	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {

		log.info '********** Ejecutando accion list *******************'
		log.debug("Datos recibidos : ${params}")
		//quitando mensaje
		if (params.action == "list")
			flash.default = null

		DataSourceHelper dsHelper = new DataSourceHelper()
		params?.run			= params?.run == null ? null : ((String)params?.run).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		def nombre			= "${params?.nombre == null			? '' : params?.nombre}"
		def run				= "${params?.run == null			? '' : params?.run}"
		def tipoPrestador	= "${params?.tipoPrestador == null	? '' : params?.tipoPrestador}"
		params.max			= params.max ? params.max : 10
		params.offset		= params.offset ?: 0

		def prestadorList	= new ArrayList()
		def sql = new Sql(dataSource)
		def sqlQuery =
				"SELECT	pres.id " +
				"FROM	Prestador pres, Persona_Natural pena " +
				"WHERE	pres.PERSONA_NATURAL_ID	= 		pena.RUN " +
				"AND	pena.RUN				LIKE 	:run " +
				"AND	" + dsHelper.getConcatIni() + "pena.NOMBRE" + dsHelper.getConcatOperator() +
				"pena.APELLIDO_PATERNO" + dsHelper.getConcatOperator() +
				"pena.APELLIDO_MATERNO" + dsHelper.getConcatFin() + " LIKE 	:nombre " +
				"AND	pres.tipo_prestador_id	LIKE	:tipoPrestador " +
				"UNION " +
				"SELECT	pres.id " +
				"FROM	Prestador pres, Persona_Juridica peju " +
				"WHERE	pres.PERSONA_JURIDICA_ID = 		peju.RUT " +
				"AND	peju.RUT				 LIKE 	:run " +
				"AND	peju.RAZON_SOCIAL		 LIKE 	:nombre " +
				"AND	pres.tipo_prestador_id	 LIKE	:tipoPrestador " +
				"ORDER BY 1"
		def queryParams = 	[ run			: '%' + run + '%'
			, nombre		: '%' + nombre + '%'
			, tipoPrestador	: '%' + tipoPrestador + '%']

		log.debug("query value  : " + sqlQuery)
		log.debug("query params : " + queryParams)
		def rows		= sql.rows(sqlQuery, queryParams, params.offset.toInteger()+1, params.max.toInteger())
		def rowsCount	= sql.rows(sqlQuery, queryParams)
		rows.each { row ->
			prestadorList.add(Prestador.get(row.id))
		}
		def totalRow 			= rowsCount.size()
		log.info "max: [${params.max}] - totalRow: [${totalRow}] - offset: [${params.offset}] - nombre: [${nombre}] - rut: [${run}] - tipoPrestador: [${tipoPrestador}]"
		def tipoPrestadorList 	= TipoPrestador.listOrderByDescripcion()

		def model = [ prestadorInstanceList	: prestadorList
			, prestadorInstanceTotal: totalRow
			, tipoPrestadorList		: tipoPrestadorList
			, nombre				: params.nombre
			, run					: params.run
			, tipoPrestador			: params.tipoPrestador]

		if(params.get('model')) { model + params.get('model') }
		sql.close()
		model
	}

	def create() {
		log.info '********** Ejecutando accion create *******************'
		log.info ("datos recibidos : $params")

		def errores
		def tipoPrestador = TipoPrestador.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()
		def estructuraJuridica = EstructuraJuridica.listOrderByDescripcion()
		def prestadorInstance

		def model = [ tipoPrestador: tipoPrestador, comunas: comunas, estructuraJuridica: estructuraJuridica ]


		if(params.get('model')) {

			if (params.get('model').prestadorInstance) {
				log.info("recibimos de vuelta prestadorInstance")
				prestadorInstance = params.get('model').prestadorInstance
			}

			if (params.get('model').personaJuridica) {
				log.info("recibimos de vuelta personaJuridica")
				prestadorInstance?.personaJuridica = params.get('model').personaJuridica
			}

			if (params.get('model').personaNatural) {
				log.info("recibimos de vuelta personaNatural")
				prestadorInstance?.personaNatural = params.get('model').personaNatural
			}

			if (params.get('model').representanteLegal) {
				log.info("recibimos de vuelta representanteLegal")
				prestadorInstance?.representanteLegal = params.get('model').representanteLegal
			}

			if (params.get('model').apoderado) {
				log.info("recibimos de vuelta apoderado")
				prestadorInstance?.apoderado = params.get('model').apoderado
			}
			model = params.get('model') + model
		}

		model.put("prestadorInstance", prestadorInstance)

		model

	}

	def save() {
		log.info '********** Ejecutando accion save *******************'
		try{
			def r = PrestadorService.addPrestador(params)
			params.put('model', r.model)
			def next = r.next
			next.params = params

			//mensaje el registro se almaceno con exito
			flash.message = "cl.adexus.isl.spm.Prestador.save.sucess"
			flash.args = []
			flash.default = "El registro asociado al prestador ${params.rut} ha sido almacenado con exito"

			forward next
		}catch(e){
			def prestador = new Prestador()
			prestador.errors.reject("Hubo un error no controlado al intentar almacenar el registro : ${e.message}")
			model = ['prestador': prestador]
			params.put('model', model)
			forward (action: 'edit', params: params)
		}





	}

	def edit() {
		log.info '********** Ejecutando accion edit *******************'
		log.debug "Datos recibidos $params"
		
		def prestadorId
		def errorConvenio
		def errores
		def prestadorInstance
		def model = [:]


		//prestadorInstance = params?.prestadorInstance
		log.info("Verificando existencia de id de centro de salud y de map model")
		if (!params?.id && !params?.prestadorId && !params.get('model')) {
			log.info("id de centro de salud y map model no existen, redirigiendo a accion list")
			redirect(action: "list")
			return
		}

		
		if (params?.id) {
			log.debug("Seteando prestadorId 1")
			prestadorId = params?.id
			prestadorInstance = Prestador.findById(prestadorId)
		}
		if (params?.prestadorId) {
			log.debug("Seteando prestadorId 2")
			prestadorId = params?.prestadorId
		}
		if (params.get('model') && params.get('model').prestadorId) {
			log.debug("Seteando prestadorId 3")
			prestadorId = params.get('model').prestadorId
		}
		if (params.get('model') && params.get('model').errorConvenio) {
			errorConvenio = params.get('model').errorConvenio
			log.debug("Error convenio: $errorConvenio")
		}
		if (prestadorId && !params.get('model')) {
			log.debug("Cargamos prestador por id : $prestadorId")
			prestadorInstance = Prestador.findById(prestadorId)
		}

		log.info("Verificando existencia de model")
		if(params.get('model')) {
			log.debug("Model existe. verificando existencia de prestadorInstance en model")
			if (params.get('model').prestadorInstance) {
				log.info("recibimos de vuelta prestadorInstance")
				prestadorInstance = params.get('model').prestadorInstance
				log.debug ("prestadorInstance.id: ${prestadorInstance.id}")
			}else
				log.debug("prestadorInstance no existe")
			
			if (params.get('model').personaJuridica) {
				log.info("recibimos de vuelta personaJuridica")
				prestadorInstance?.personaJuridica = params.get('model').personaJuridica
			}
			if (params.get('model').personaNatural) {
				log.info("recibimos de vuelta personaNatural")
				prestadorInstance?.personaNatural = params.get('model').personaNatural
			}
			if (params.get('model').representanteLegal) {
				log.info("recibimos de vuelta representanteLegal")
				prestadorInstance?.representanteLegal = params.get('model').representanteLegal
			}
			if (params.get('model').apoderado) {
				log.info("recibimos de vuelta apoderado")
				prestadorInstance?.apoderado = params.get('model').apoderado
			}
			if (params.get('model').errores) {
				errores = params.get('model').errores
				log.info("edit errors: ")
				if(errores){
					errores.each{error->
						log.info(error)
					}
				}
				//prestadorInstance?.errors?.reject(errores)
			}
			model = params.get('model')

		}
		log.info("Verificacion concluida")

		log.info("Seteando centros de salud, convenios, tipo de prestador, comuna y estructura juridica para desplegarlos en pantalla de edicion")
		def listaCentrosDeSalud = CentroSalud.findAllByPrestador(prestadorInstance)
		def listaConvenio = Convenio.findAllByPrestador(prestadorInstance)
		def tipoPrestador = TipoPrestador.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()
		def estructuraJuridica = EstructuraJuridica.listOrderByDescripcion()
		log.info("Seteo concluido")
		
		log.info("Verificando si existen errores en convenio y en prestador")
		if (errorConvenio && prestadorInstance) {
			log.debug("Existen errores, registrando...")
			prestadorInstance.errors.reject(errorConvenio)
			log.debug("Errores registrados")
		}
		log.info("Verificacion de errores en convenio y en prestador concluidas")
		

		
		model += [ prestadorInstance	: prestadorInstance
			, listaCentrosDeSalud	: listaCentrosDeSalud
			, tipoPrestador		: tipoPrestador
			, comunas				: comunas
			, listaConvenio		: listaConvenio
			, estructuraJuridica	: estructuraJuridica ]
		log.debug("El valor de retorno es el siguiente : $model")
		log.info("Finalizando accion edit")
		model
	}


	/**
	 * 
	 * @return
	 */
	def update() {
		log.info '********** Ejecutando accion update *******************'
		log.info "Datos recibidos : ${params}"

		try{
			
			def r = PrestadorService.updatePrestadorModificado(params)
			params.put('model', r.model)
			def next = r.next
			next.params = params
			
			flash.default = null
			def prestadorInstance = next?.params?.model?.prestadorInstance
			def errores = next?.params?.model?.errores
			log.info("Calculando errores para prestadorInstance : ${prestadorInstance.id}")
			
			if(errores){
				log.info("Existen errores")
				errores.each{error->
					log.debug("--> $error")
				}
				prestadorInstance.discard()//descarta todos los datos seteados con anterioridad
			}else{
				log.info("El prestador no tiene errores asociados")
				log.debug("Guardando prestador")
				prestadorInstance.save(flush:true)
				def identificador = prestadorInstance?.personaNatural?.run?:prestadorInstance?.personaJuridica?.rut
				log.debug("El registro asociado al prestador ${identificador} ha sido actualizado con exito")
				//mensaje el registro se almaceno con exito
				flash.message = "cl.adexus.isl.spm.Prestador.save.sucess"
				flash.args = []
				log.info("Identificador del prestador: $identificador")
				flash.default = "El registro asociado al prestador [${identificador}] ha sido almacenado con exito"
			}
			log.info("Termino de validaciones para prestador")			
			//redirecciona a accion edit
			forward next
		}catch(e){
			log.debug("error no controlado : ${e.message}")
			e.printStackTrace()
			def prestador = new Prestador()
			prestador.errors.reject("Hubo un error no controlado al intentar actualizar el registro : ${e.message}")
			def model = ['prestadorInstance': prestador]
			params.put('model', model)
			forward (action: 'edit', params: params)
		}
	}

	/**
	 * 
	 * @return
	 */
	def delete() {
		log.info '********** Ejecutando accion delete *******************'
		log.debug("Datos recibidos : ${params}")

		try{
			def r = PrestadorService.deletePrestador(params)
			params.tipoPrestador = null;
			params.put('model', r.model)
			def next = r.next
			next.params = params
			

			//mensaje el registro se borro con exito
			flash.message = "cl.adexus.isl.spm.Prestador.delete.sucess"
			flash.args = []
			flash.default = "El borrado del prestador ${params.rut} fue realizado con exito"
			forward next
		}catch(e){
			log.info(e.message)
			def prestadorInstance = Prestador.get(params?.id)
			def p = new Prestador() 
			p.errors.reject(e.message)
			params.p = p
			params.prestadorInstance = prestadorInstance
			params.listaCentrosDeSalud = CentroSalud.findAllByPrestador(prestadorInstance)
			params.tipoPrestador = TipoPrestador.listOrderByDescripcion()
			params.comunas = Comuna.listOrderByDescripcion()
			params.listaConvenio	= Convenio.findAllByPrestador(prestadorInstance)
			params.estructuraJuridica = EstructuraJuridica.listOrderByDescripcion()
			
			
			render (view: 'edit', model: params)
		}






	}

	def ver() {
		log.info '********** Ejecutando accion ver *******************'
		log.info ("cargando prestador : " + params?.id)
		if (!params?.id) {
			forward(action: "list")
			return
		}

		def prestadorInstance = Prestador.findById(params?.id)
		def listaCentrosDeSalud = CentroSalud.findAllByPrestador(prestadorInstance)
		def listaConvenio = Convenio.findAllByPrestador(prestadorInstance)
		def tipoPrestador = TipoPrestador.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()

		[prestadorInstance: prestadorInstance, listaCentrosDeSalud: listaCentrosDeSalud, tipoPrestador: tipoPrestador, comunas: comunas, listaConvenio: listaConvenio ]

	}

	def show(Long id) {
		log.info '********** Ejecutando accion show *******************'
		def prestadorInstance = Prestador.get(id)
		if (!prestadorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'prestador.label', default: 'Prestador'), id])
			forward(action: "list")
			return
		}

		[ prestadorInstance: prestadorInstance ]
	}

	/*
	 * Centros de Salud para Prestador
	 */
	def lst_centro_salud() {

	}

	def create_cs() {
		log.info '********** Ejecutando accion create_cs *******************'
		log.debug "Datos recibidos : ·$params"
		def prestadorId

		
		if (params?.id){
			log.debug("Seteando prestador 1")
			prestadorId = params?.id
		}

		if (params?.prestadorId){
			log.debug("Seteando prestador 2")
			prestadorId = params?.prestadorId
		}
			

		def prestadorInstance = Prestador.findById(prestadorId)
		def tipoPrestador = TipoPrestador.listOrderByDescripcion()
		def tipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()

		def model = ([prestadorInstance: prestadorInstance, tipoPrestador: tipoPrestador, tipoCentroSalud: tipoCentroSalud, comunas: comunas])

		if (params.get('model')) {
			def centroSalud = params.get('model').centroSalud
			model.put('centroSalud', centroSalud)
		}
		model
	}

	def save_cs() {
		log.info '********** Ejecutando accion save_cs *******************'
		log.info("Checkbox: " + params?.servicios)

		def r = PrestadorService.addCentroSalud(params)

		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward next

	}

	def edit_cs() {
		log.info '********** Ejecutando edit_cs *******************'
		log.info ("cargando Prestador : " + params?.id)
		log.info ("cargando Centro de Salud : " + params?.centroSaludId)

		if (!params?.id) {
			forward(action: "list")
			return
		}

		def prestadorInstance = Prestador.findById(params?.id)
		def centroSalud = CentroSalud.findById(params?.centroSaludId)
		def tipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()

		log.info("atencionAmbulancia : " + centroSalud?.atencionAmbulancia)

		[ prestadorInstance: prestadorInstance, tipoCentroSalud: tipoCentroSalud, comunas: comunas, centroSalud: centroSalud ]

	}

	def ver_cs() {
		log.info '********** Ejecutando ver_cs *******************'
		log.info ("cargando Prestador : " + params?.id)
		log.info ("cargando Centro de Salud : " + params?.centroSaludId)

		if (!params?.id) {
			forward(action: "list")
			return
		}

		def prestadorInstance = Prestador.findById(params?.id)
		def centroSalud = CentroSalud.findById(params?.centroSaludId)
		def tipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()

		log.info("atencionAmbulancia : " + centroSalud?.atencionAmbulancia)

		[ prestadorInstance: prestadorInstance, tipoCentroSalud: tipoCentroSalud, comunas: comunas, centroSalud: centroSalud ]

	}

	def update_cs() {
		log.info '********** Ejecutando accion update_cs *******************'
		def r = PrestadorService.updateCentroSalud(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward next

	}

	def delete_cs() {
		log.info '********** Ejecutando accion delete_cs *******************'
		log.info 'PARAMS: ' + params
		def next

		try{
			def r = PrestadorService.deleteCentroSalud(params)
			params.put('model', r.model)
			next = r.next
			next.params = params
			forward next
		}catch(e){
			def prestador = new Prestador()
			prestador.errors.reject("Hubo un error no controlado al intentar borrar convenio")
			def model = ['prestadorInstance': prestador]
			params.put('model', model)
			forward next
		}


	}

	/*
	 * Convenios para Prestador
	 */

	def create_cnv() {
		log.info '********** Ejecutando create_cnv *******************'
		log.info("*** create_cnv ***")
		def prestadorId
		def convenio
		def errores
		def prestadorInstance

		if (params?.id) {
			prestadorId = params?.id
			prestadorInstance = Prestador.findById(prestadorId)
		}

		def tipoPrestador = TipoPrestador.listOrderByDescripcion()
		def tipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()
		def tipoConvenio = TipoConvenio.listOrderByDescripcion()

		def model = [tipoPrestador: tipoPrestador, tipoConvenio: tipoConvenio, comunas: comunas]

		if(params.get('model')) {
			log.info("create cnv: model")
			prestadorId = params.get('model').prestadorId
			prestadorInstance = Prestador.findById(prestadorId)

			if (params.get('model').convenio) {
				convenio = params.get('model').convenio
			}

			if (params.get('model').errores) {
				errores = params.get('model').errores
				convenio.errors.reject(errores)
			}

			model.put("convenio", convenio)

		}

		model.put("prestadorInstance", prestadorInstance)
		log.info("Vamos agregar Convenios al prestador : " + prestadorId)

		model
	}

	def save_cnv() {
		log.info '********** Ejecutando accion save_cnv *******************'
		log.info("*** save_cnv ***")
		def r = PrestadorService.agregarConvenio(params)

		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward next

	}

	def edit_cnv() {
		log.info '********** Ejecutando accion edit_cnv *******************'
		log.info "Datos recibidos: $params"

		def prestadorId
		def prestadorInstance
		def errores
		def convenioId
		def cntRegistros = 0
		def listaRegistrosArancelesEnConvenio = new ArrayList()

		if(params.get('model')) {
			log.info("edit_cnv: model")
			prestadorId = params.get('model').prestadorId

			if (params.get('model').errores) {
				errores = params.get('model').errores
			}

			if (params.get('model').convenioId) {
				convenioId = params.get('model').convenioId
			}

		}

		if (params?.id) {
			prestadorId = params?.id
		}

		if (params?.convenioId) {
			convenioId = params?.convenioId
		}



		if (!prestadorId) {
			forward(action: "edit")
			return
		}

		if (!convenioId) {
			forward(action: "edit")
			return
		}

		prestadorInstance = Prestador.findById(prestadorId)

		if (errores) {
			prestadorInstance.errors.reject(errores)
		}

		def convenio = Convenio.findById(convenioId)
		def tipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()
		def listaCentrosDeSalud = CentroSalud.findAllByPrestadorAndEsActivo(prestadorInstance, true)

		if (listaCentrosDeSalud)
			log.info("Total Centros de Salud :" + listaCentrosDeSalud.size())

		def tipoConvenio = TipoConvenio.listOrderByDescripcion()

		/*
		 * Tenemos que obtener todos los aranceles
		 */
		def listaArancelesEnConvenio = PrestadorService.getListaArancelesByConvenioId(convenioId)
		log.info("Cantidad de listaArancelesEnConvenio : ${listaArancelesEnConvenio?.size()}")

		listaArancelesEnConvenio.each {
			def arancelConvenio = it[0]
			def arancelBase = it[1]
			listaRegistrosArancelesEnConvenio += [id				: arancelConvenio.id
				, codigoPrestacion	: arancelConvenio.codigoPrestacion
				, glosa				: arancelBase.glosa
				, nivel				: arancelConvenio.nivel
				, valorOriginal		: arancelConvenio.valorOriginal
				, calculo			: arancelConvenio.calculo
				, valorNuevo		: arancelConvenio.valorNuevo
				, desde				: arancelConvenio.desde
				, hasta				: arancelConvenio.hasta]
		}
		def model = [ prestadorInstance					: prestadorInstance
			, tipoCentroSalud					: tipoCentroSalud
			, comunas							: comunas
			, tipoConvenio						: tipoConvenio
			, convenio							: convenio
			, listaCentrosDeSalud				: listaCentrosDeSalud
			, listaRegistrosArancelesEnConvenio	: listaRegistrosArancelesEnConvenio]
		model
	}

	def ver_cnv() {
		log.info '********** Ejecutando accion ver_cnv *******************'
		log.info ("cargando Prestador : " + params?.id)
		log.info ("cargando Convenio : " + params?.convenioId)

		if (!params?.id) {
			forward(action: "edit")
			return
		}

		def prestadorInstance = Prestador.findById(params?.id)
		def convenio = Convenio.findById(params?.convenioId)
		def tipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()
		def listaCentrosDeSalud = CentroSalud.findAllByPrestador(prestadorInstance)

		def arancelesConvenio = PrestadorService.getListaArancelesByConvenioId(params?.convenioId)

		def arancelConGlosa
		arancelesConvenio.each {
			arancelConGlosa = !arancelConGlosa ? new ArrayList() : arancelConGlosa
			def arancelConvenio = it[0]
			def arancelBase = it[1]
			arancelConGlosa += [  codigoPrestacion	: arancelConvenio.codigoPrestacion
				, glosa				: arancelBase?.glosa
				, nivel				: arancelConvenio.nivel
				, valorOriginal		: arancelConvenio.valorOriginal
				, calculo			: arancelConvenio.calculo
				, valorNuevo		: arancelConvenio.valorNuevo
				, desde				: arancelConvenio.desde
				, hasta				: arancelConvenio.hasta]
		}

		def model = [prestadorInstance	: prestadorInstance
			, tipoCentroSalud	: tipoCentroSalud
			, comunas			: comunas
			, convenio			: convenio
			, listaCentrosDeSalud: listaCentrosDeSalud
			, arancelesConvenio	: arancelConGlosa ]
		model
	}

	def update_cnv() {
		log.info '********** Ejecutando update cnv *******************'
		def r = PrestadorService.updateConvenio(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params
		forward next

	}

	def delete_cnv() {
		log.info '********** Ejecutando accion delete_cnv *******************'
		def next

		try{
			def r = PrestadorService.deleteConvenio(params)
			params.put('model', r.model)
			next = r.next
			next.params = params
			forward next
		}catch(e){
			def convenio = new Convenio()
			prestador.errors.reject("Hubo un error no controlado al intentar borrar convenio")
			def model = ['convenio': convenio]
			params.put('model', model)
			forward next
		}


	}

	/*
	 * Aranceles para Prestador
	 */

	def mnt_arnclcnv() {
		log.info '********** Ejecutando accion mnt_arnclcnv *******************'
		log.info ("cargando Prestador : " + params?.id)
		log.info ("cargando Convenio : " + params?.convenioId)

		def convenioId
		def listaRegistrosArancelesEnConvenio = new ArrayList()

		if (!params?.id) {
			redirect(action: "edit")
			return
		}

		def prestadorInstance = Prestador.findById(params?.id)
		def convenio = Convenio.findById(params?.convenioId)
		def grupo = Grupo.listOrderByCodigo()

		convenioId = convenio?.id

		def listaArancelesEnConvenio = PrestadorService.getListaArancelesByConvenioId(convenioId)
		log.info("Cantidad de listaArancelesEnConvenio : ${listaArancelesEnConvenio?.size()}")

		listaArancelesEnConvenio.each {
			def arancelConvenio = it[0]
			def arancelBase = it[1]
			listaRegistrosArancelesEnConvenio += [id				: arancelConvenio.id
				, codigoPrestacion	: arancelConvenio.codigoPrestacion
				, glosa				: arancelBase.glosa
				, nivel				: arancelConvenio.nivel
				, valorOriginal		: arancelConvenio.valorOriginal
				, calculo			: arancelConvenio.calculo
				, valorNuevo		: arancelConvenio.valorNuevo
				, desde				: arancelConvenio.desde
				, hasta				: arancelConvenio.hasta
				, tipoPrestacion	: arancelBase instanceof Paquete ? "PAQUETE" : "ARANCEL"]
		}
		def model = [ prestadorInstance					: prestadorInstance
			, convenio							: convenio
			, grupo								: grupo
			, listaRegistrosArancelesEnConvenio	: listaRegistrosArancelesEnConvenio ]
		model
	}


	/*
	 * 	Funciones AJAX
	 */
	/**
	 * Obtiene persona natural
	 * @return
	 */
	def datosPersonaNaturalJSON () {
		log.info '********** Ejecutando accion datosPersonaNaturalJSON *******************'
		def datos = PrestadorService.getPersonaNatural(params.run)
		JSON.use("deep") { render datos as JSON }
	}

	/**
	 * Obtiene apoderado
	 * @return
	 * */
	def datosApoderadoJSON(){
		log.info("********** Ejecutando accion datosApoderadoJSON *******************")
		log.debug("datos recibidos : $params")
		def datos = [:]
		def prestador
		def isEmpty = false
		def apoderado = PrestadorService.getPersonaNatural(params.run)
		if(!apoderado)
			isEmpty = true
		else if(params?.id){
			prestador = Prestador.get(params?.id.toLong())
			if(!prestador)
				isEmpty = true
		}else{
			isEmpty = true
		}
		
		if(isEmpty){
			datos.isEmpty = isEmpty
			JSON.use("deep") {render datos as JSON}
		}
		//si apoderado y prestador existen, setea los datos
		datos.apoderado = apoderado
		datos.desdeAP = !prestador?.desdeAP ? "" : String.format('%td-%<tm-%<tY', prestador?.desdeAP)
		datos.hastaAP = !prestador?.hastaAP ? "" : String.format('%td-%<tm-%<tY', prestador?.hastaAP)
		JSON.use("deep") {render datos as JSON}
	}

	/**
	 * Obtiene representante legal
	 * @return
	 * */
	def datosRepresentanteLegalJSON(){
		log.info("********** Ejecutando accion datosRepresentanteLegalJSON *******************")
		log.debug("Datos recibidos : ${params}")
		def datos = [:]
		def prestador
		def isEmpty = false
		def representanteLegal = PrestadorService.getPersonaNatural(params.run)
		if(!representanteLegal)
			isEmpty = true
		else{
			prestador = Prestador.get(params?.id.toLong())
			if(!prestador)
				isEmpty = true
		}
		if(isEmpty){
			datos.isEmpty = isEmpty
			JSON.use("deep") {render datos as JSON}
		}
		//si apoderado y prestador existen, setea los datos
		datos.representanteLegal = representanteLegal
		datos.desdeRL = !prestador?.desdeRL ? "" : String.format('%td-%<tm-%<tY', prestador?.desdeRL)
		datos.hastaRL = !prestador?.hastaRL ? "" : String.format('%td-%<tm-%<tY', prestador?.hastaRL)
		JSON.use("deep") {render datos as JSON}
	}



	/**
	 * Obtiene persona jur�dica
	 * @return
	 */
	def datosPersonaJuridicaJSON () {
		log.info '********** Ejecutando accion datosPersonaJuridicaJSON *******************'
		def datos = PrestadorService.getPersonaJuridica(params.rut)
		JSON.use("deep") { render datos as JSON }
	}

	def SubGruposJSON(){
		log.info '********** Ejecutando accion SubGrupoJSON *******************'
		//def grupo = params?.grupo
		def grupo = Grupo?.findByCodigo(params?.grupo)
		//Llamamos al servicio
		def sg = SubGrupo.findAllByGrupo(grupo)

		JSON.use("deep"){ render sg as JSON }
	}

	def traerCentrosSaludEnConvenio(){
		log.info '********** Ejecutando accion traerCentrosSaludEnConvenio *******************'

		def convenioId = params?.convenioId
		def convenio = Convenio.findById(convenioId)
		def result = CentroSaludEnConvenio.findAllByConvenio(convenio)

		log.info("Total de Centros de Salud en convenio : " + result.size() + " cnv: " + convenioId)

		JSON.use("deep"){ render result as JSON }
	}

	def cargarAranceles(){
		log.info '********** Ejecutando accion cargarAranceles *******************'
		log.info("Datos recibidos :: grupo :${params?.grupo} : subgrupo : ${params?.subgrupo} : codigo : ${params?.codigo}")
		render PrestadorService.cargarAranceles(params?.grupo, params?.subgrupo, params?.codigo, true)
	}

	def agregarArancelConvenio() {
		log.info '********** Ejecutando accion agregarArancelConvenio *******************'
		log.info("Datos recibidos : ${params}")
		def r = PrestadorService.agregarArancelConvenio(params)
		JSON.use("deep"){ render r as JSON }
	}

	def desplegarArancelesConvenio() {
		log.info '********** Ejecutando accion desplegarArancelesConvenio *******************'
		log.info("Datos recibidos : ${params?.convenioId}")
		render PrestadorService.desplegarArancelesConvenio(params?.convenioId)
	}

	def getArancelesConvenioGlosaById() {
		log.info '********** Ejecutando getArancelesConvenioGlosaById *******************'
		log.info("Datos obtenidos: arancelConvenioId : ${params?.arancelConvenioId}")
		render PrestadorService.getArancelesConvenioGlosaById(params?.arancelConvenioId)
	}

	def eliminarArancelConvenio() {
		log.info '********** Ejecutando accion eliminarArancelConvenio *******************'
		log.info("Datos obtenidos: arancelConvenioId : ${params?.arancelConvenioId}")
		PrestadorService.eliminarArancelConvenio(params?.arancelConvenioId)
		render ("OK")
	}

}
