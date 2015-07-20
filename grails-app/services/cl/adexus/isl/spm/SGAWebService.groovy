package cl.adexus.isl.spm

import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.cognus.ws.ConsultaEmpresa
import cl.cognus.ws.ConsultaTrabajador
import cl.cognus.ws.ConsultasAfiliados
import cl.cognus.ws.ConsultasAfiliadosService
import cl.cognus.ws.EmpresaResponse
import cl.cognus.ws.TrabajadoresResponse

/**
 * Encapsula la llamada a los webservices hacia el SGA
 *
 */

class SGAWebService {

	def wsdl

	def ConsultasAfiliadosService consultaAfiliadosService

	def ConsultasAfiliados _getPort(){
		if(!consultaAfiliadosService){
			consultaAfiliadosService=new ConsultasAfiliadosService(new URL(wsdl))
		}
		ConsultasAfiliados port=consultaAfiliadosService.getConsultasAfiliadosPort()
		return port
	}

	/**
	 * Consulta las empresas de un trabajador
	 *
	 */
	def consultaTrabajador(String rutTrabajador, Date fechaSiniestro){
		def empresas = []
		try{
			def FormatosISLHelper f=new FormatosISLHelper()
			TrabajadoresResponse wsResponse=_getPort().consultaTrabajador(f.run(rutTrabajador),f.periodoSGA(fechaSiniestro))
			//TODO: Revisar wsResponse.getRespCode() y wsResponse.getRespMsg()
			log.info "wsResponse:"+wsResponse
			if(wsResponse!=null){
				def wsEmpresas=wsResponse.getEmpresas().getEntry()
				for (int i=0; i < wsEmpresas.size(); i++){
					def emp=wsEmpresas.get(i).getEmpresa()
					def seguroLaboral=(emp.getSeguroLaboral()=='IPS'?'ISL':emp.getSeguroLaboral())
					empresas.add(['nombre': emp.getNombre(),
						'rut': emp.getRut(),
						'seguroLaboral': seguroLaboral,
						'codActEmp': emp.getCodActEmp()])
				}
			}
			return empresas
		} catch (Exception e){
			log.error "Error en consultaTrabajador(${rutTrabajador}, ${fechaSiniestro}):" + e.getMessage();
			e.printStackTrace();
		}
		return empresas
	}

	/**
	 * Consulta los datos de una empresa
	 */
	def consultaEmpresa(String rut, Date fechaSiniestro) {
		try{
			def FormatosISLHelper f=new FormatosISLHelper()

			EmpresaResponse wsResponse=_getPort().consultaEmpresa(f.run(rut),f.periodoSGA(fechaSiniestro))
			//TODO: Revisar wsResponse.getRespCode() y wsResponse.getRespMsg()
			log.info "wsResponse:"+wsResponse
			def empresa
			if(wsResponse!=null){
				def emp=wsResponse.getEmpresa()
				if (emp!=null) {
					def seguroLaboral=(emp.getSeguroLaboral()=='IPS'?'ISL':emp.getSeguroLaboral())
					empresa=(['nombre': emp.getNombre(),
						'rut': emp.getRut(),
						'seguroLaboral': seguroLaboral,
						'codActEmp': emp.getCodActEmp()])
					return empresa
				}
			}
		} catch (Exception e){
			log.error "Error en consultaEmpresa(${rut}, ${fechaSiniestro}):" + e.getMessage();
		}
		return null;
	}

}
