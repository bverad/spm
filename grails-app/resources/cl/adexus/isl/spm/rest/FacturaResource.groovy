package cl.adexus.isl.spm.rest

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.QueryParam
import grails.converters.JSON


@Path('/api/factura')
@Consumes('application/json')
@Produces('application/json')
class FacturaResource {

	def FACTRestService
	
    @POST
	@Path('/r01')
    String r01(Map params) {
        FACTRestService.r01(params) as JSON
    }
	
	@POST
	@Path('/r03')
	String r03(Map params) {
		FACTRestService.r03(params) as JSON
	}

	@POST
	@Path('/r04')
	String r04(Map params) {
		FACTRestService.r04(params) as JSON
	}
	
	@POST
	@Path('/r05')
	String r05(Map params) {
		FACTRestService.r05(params) as JSON
	}
	
	@POST
	@Path('/r06')
	String r06(Map params) {
		FACTRestService.r06(params) as JSON
	}
	
	@POST
	@Path('/r07')
	String r07(Map params) {
		FACTRestService.r07(params) as JSON
	}
	
	@POST
	@Path('/r09')
	String r09(Map params) {
		FACTRestService.r09(params) as JSON
	}
	
}
