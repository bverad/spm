package cl.adexus.isl.spm.rest

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.QueryParam
import javax.ws.rs.PathParam
import grails.converters.JSON
import cl.adexus.isl.spm.CIE10


@Path('/api/calorigen')
@Consumes('application/json')
@Produces('application/json')
class CalOrigenResource {

	def CalOrigenRestService
	
	@POST
	@Path('/r01at')
	String r01at(Map params) {
		CalOrigenRestService.r01at(params) as JSON
	}
	
	/*@POST
	@Path('/r01at/{sid}')
	@Produces('application/json')
	String r01at(@PathParam('sid') String sid) {
		CalOrigenRestService.r01at(sid)
	}*/
	
	
	@POST
	@Path('/r02at')
	String r02at(Map params) {
		log.info("Ejecutando r02at ")
		CalOrigenRestService.r02at(params) as JSON
	}

	@POST
	@Path('/r03at')
	String r03at(Map params) {
		CalOrigenRestService.r03at(params) as JSON
	}
	
	@POST
	@Path('/r04at')
	String r04at(Map params) {
		CalOrigenRestService.r04at(params) as JSON
	}
	
	@POST
	@Path('/r05at')
	String r05at(Map params) {
		CalOrigenRestService.r05at(params) as JSON
	}
	
	//EP
	
    @POST
	@Path('/r02ep')
	String r02ep(Map params) {
		CalOrigenRestService.r02ep(params) as JSON
	}

	@POST
	@Path('/r03ep')
	String r03ep(Map params) {
		CalOrigenRestService.r03ep(params) as JSON
	}
	
	@POST
	@Path('/r04ep')
	String r04ep(Map params) {
		CalOrigenRestService.r04ep(params) as JSON
	}
	
	@POST
	@Path('/r05ep')
	String r05ep(Map params) {
		CalOrigenRestService.r05ep(params) as JSON
	}
	
	/*@GET
	@Produces('text/plain')
	String getTestRepresentation(@QueryParam('name') String name) {
		"Hello ${name ? name : 'unknown'}"
	}
	
	@POST
	@Produces('application/json')
	@Path('/cieByCodigo/{codigo}')
	CIE10 cieByCodigo(@PathParam('codigo') String codigo) {
		CIE10.findByCodigo(codigo)
	}
	
	@GET
	@Produces('application/json')
	@Path('/listCIE')
	List cieByCodigo() {
		CIE10.findAll()
	}*/
	
	
	
}
