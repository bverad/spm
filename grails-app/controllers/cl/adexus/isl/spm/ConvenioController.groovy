package cl.adexus.isl.spm

class ConvenioController {

	def PrestadorService
	
    def index() { 
		redirect(action: "listar")
		return
	}
	
	def listar() {
		params.rut 				= params.rut ? ((String)params.rut).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim() : params.rut
		def rutPrestador		= "%${params.rut == null ? '' : params.rut}%"
		def nLicitacion			= "%${params.licitacion == null ? '' : params.licitacion}%"
		def tipoConvenio		= "%${params.tipoConvenio == null ? '' : params.tipoConvenio}%"
		def tipoPrestador		= "%${params.tipoPrestador == null ? '' : params.tipoPrestador}%"
		def listaTipoPrestador 	= TipoPrestador.listOrderByDescripcion()
		def listaTipoConvenio 	= TipoConvenio.listOrderByDescripcion()
		def query 				= "SELECT conv " +
								"FROM	Convenio AS conv " +
								"WHERE	( conv.prestador.personaJuridica.rut LIKE :rutPrestador OR conv.prestador.personaNatural.run LIKE :rutPrestador ) " +
								"AND	conv.numeroLicitacion				LIKE :nLicitacion " +
								"AND	conv.tipoConvenio.codigo			LIKE :tipoConvenio " +
								"AND	conv.prestador.tipoPrestador.codigo	LIKE :tipoPrestador " +
								"ORDER BY conv.nombre"
								
								println query
		def paramsQuery 		= [rutPrestador : rutPrestador
								 , nLicitacion	: nLicitacion
								 , tipoConvenio	: tipoConvenio
								 , tipoPrestador: tipoPrestador]
		log.info "params query ----> "+paramsQuery
		def listaConvenios		= Convenio.executeQuery(query, paramsQuery)
		//TODO Falta Paginación
		
		def model = [ listaTipoPrestador: listaTipoPrestador
					, listaTipoConvenio	: listaTipoConvenio
					, listaConvenios	: listaConvenios
					, rut				: params?.rut
					, licitacion		: params?.licitacion
					, tipoPrestador		: params?.tipoPrestador
					, tipoConvenio		: params?.tipoConvenio ]
		model
	}
	
	def ver() {
		
		def convenioId
		def prestadorInstance
		def listaRegistrosArancelesEnConvenio = new ArrayList()
		
		convenioId = params?.id
		
		log.info("*** ver ***")
		
		if (!convenioId) {
			redirect(action: "listar")
			return
		}
		
		//def prestadorInstance = Prestador.findById(convenioId)
		
		def convenio = Convenio.findById(convenioId)
		prestadorInstance = convenio?.prestador
		
		def listaArancelesEnConvenio = PrestadorService.getListaArancelesByConvenioId(convenioId)
		log.info("Cantidad de listaArancelesEnConvenio : ${listaArancelesEnConvenio?.size()}")
		
		listaArancelesEnConvenio.each {
			def arancelConvenio = it[0]
			def arancelBase = it[1]
			listaRegistrosArancelesEnConvenio += ["id"				 : arancelConvenio.id
												, "codigoPrestacion" : arancelConvenio.codigoPrestacion
												, "glosa"			 : arancelBase.glosa
												, "nivel"			 : arancelConvenio.nivel
												, "valorOriginal"	 : arancelConvenio.valorOriginal
												, "calculo"			 : arancelConvenio.calculo
												, "valorNuevo"		 : arancelConvenio.valorNuevo
												, "desde"		 	 : arancelConvenio.desde
												, "hasta"		 	 : arancelConvenio.hasta]
		}
		
		
		def tipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()
		def listaCentrosDeSalud = CentroSalud.findAllByPrestador(prestadorInstance)
		
		def model = [prestadorInstance					: prestadorInstance
				   , tipoCentroSalud					: tipoCentroSalud
				   , comunas							: comunas
				   , convenio							: convenio
				   , listaCentrosDeSalud				:listaCentrosDeSalud
				   , listaRegistrosArancelesEnConvenio	: listaRegistrosArancelesEnConvenio ]
		model
	}
	
	
}
