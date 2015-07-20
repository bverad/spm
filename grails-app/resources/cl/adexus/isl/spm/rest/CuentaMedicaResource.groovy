package cl.adexus.isl.spm.rest

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.QueryParam
import grails.converters.JSON


@Path('/api/cuentaMedica')
@Consumes('application/json')
@Produces('application/json')
class CuentaMedicaResource {

	def CMRestService
	
    @POST
	@Path('/r01')
    String r01(Map params) {
        CMRestService.r01(params) as JSON
    }
	
	@POST
	@Path('/r02')
	String r02(Map params) {
		CMRestService.r02(params) as JSON
	}
	
	@POST
	@Path('/r04')
	String r04(Map params) {
		CMRestService.r04(params) as JSON
	}
	
	@POST
	@Path('/r05')
	String r05(Map params) {
		CMRestService.r05(params) as JSON
	}
	
	@POST
	@Path('/r06')
	String r06(Map params) {
		CMRestService.r06(params) as JSON
	}
	
	@POST
	@Path('/rr01')
	String rr01(Map params) {
		CMRestService.rr01(params) as JSON
	}
	
	@POST
	@Path('/rr02')
	String rr02(Map params) {
		CMRestService.rr02(params) as JSON
	}
}
