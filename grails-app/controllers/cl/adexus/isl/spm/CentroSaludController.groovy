package cl.adexus.isl.spm

import grails.converters.JSON

class CentroSaludController {

    def index() { }
	
	def listar() { 
		
		def listaCentros
		def listaRegiones
		def listaComunas
		def listaTipoCentroSalud
		def model
		def tipoCentro
		
		params?.region 		= "${params?.region		== null ? '' : params?.region}"
		params?.comuna 		= "${params?.comuna		== null ? '' : params?.comuna}"
		params?.tipoCentro 	= "${params?.tipoCentro	== null ? '' : params?.tipoCentro}"

		listaRegiones = Region.listOrderByDescripcion()
		if  (params?.region != null) {
			listaComunas = Comuna.executeQuery("SELECT	c FROM Comuna c WHERE 	c.provincia.region.codigo = :region ", [region: params?.region])
		}
		listaTipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def listaEstadosCs =[ [codigo: "true" , descripcion: 'Activo']
							, [codigo: "false", descripcion: 'Inactivo'] ]
		
		def paramsQuery = 	[tipoCs: "%" + params?.tipoCentro + "%"]
		
		
		def query = """
			SELECT cs FROM CentroSalud cs
			INNER JOIN cs.comuna c 
			INNER JOIN c.provincia p
			INNER JOIN p.region r
			WHERE cs.tipoCentroSalud.codigo LIKE :tipoCs 
		"""
		if(params?.region){
			query += " AND r.codigo =:region"
			paramsQuery = 	[tipoCs: "%" + params?.tipoCentro + "%",region:params?.region]
			if(params?.comuna){
				query += " AND c.codigo =:comuna"
				paramsQuery = 	[tipoCs: "%" + params?.tipoCentro + "%", region:params?.region, comuna:params?.comuna] 
			}
		}
		/*def query  = "SELECT	cs "
			query += "FROM		CentroSalud cs "
			query += "WHERE		cs.comuna.provincia.region.codigo 	LIKE :region "
			query += "AND		cs.comuna.codigo					LIKE :comuna "*/
		if (params?.estadoCentro) {
			params?.estadoCentro = params?.estadoCentro.toBoolean()
			query += " AND cs.esActivo =:estado "
			paramsQuery += [estado: params?.estadoCentro]
		}
			query += " AND cs.tipoCentroSalud.codigo LIKE :tipoCs "
		log.info "paramsQuery: ${paramsQuery}"
		listaCentros = CentroSalud.executeQuery(query, paramsQuery)
		model = [ listaCentros			: listaCentros
				, listaTipoCentroSalud	: listaTipoCentroSalud
				, listaRegiones			: listaRegiones
				, listaComunas			: listaComunas
				, listaEstadosCs		: listaEstadosCs
				, region				: params?.region
				, comuna				: params?.comuna
				, estadoCentroSeleccionado: params?.estadoCentro
				, tipoCentro			: params?.tipoCentro ]
		model
	}
	
	def ver() {
		def centroSaludId
		def prestadoId
		def centroSalud
		def prestador
		
		if (params?.id)
			centroSaludId = params?.id
			
		if (!centroSaludId) {
			redirect(action: "listar")
			return
		}
		
		log.info("*** ver ***")
		
		log.info ("cargando Centro de Salud : " + centroSaludId)
		centroSalud = CentroSalud.findById(centroSaludId)
		prestador = centroSalud?.prestador
		
		def tipoCentroSalud = TipoCentroSalud.listOrderByDescripcion()
		def comunas = Comuna.listOrderByDescripcion()
		
		[ prestadorInstance: prestador, tipoCentroSalud: tipoCentroSalud, comunas: comunas, centroSalud: centroSalud ]
		
	}
	
	def traerComunasPorRegionJSON(){
		def regionId
		
		if (params?.regionId)
			regionId = params?.regionId

		//Llamamos al servicio
		def cs = Comuna.executeQuery(
				"SELECT c "+
				"FROM Comuna c " +
				"WHERE c.provincia.region.codigo = ?",[regionId]);
			
		JSON.use("deep"){ render cs as JSON }
	}
}