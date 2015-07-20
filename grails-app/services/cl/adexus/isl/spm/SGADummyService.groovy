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

class SGADummyService {
	
	
	/**
	 * Consulta las empresas de un trabajador
	 *
	 */
	def consultaTrabajador(String rutTrabajador, Date fechaSiniestro){
		def empresas = []
		
		log.info("consultaTrabajador : " + rutTrabajador)
		if(rutTrabajador == "111111111"){
			empresas.add([nombre: 'Adexus', rut: '96580060-3', seguroLaboral: 'ISL', codActEmp: 1])
			empresas.add([nombre: 'El Pollo Farsante', rut: '2-7', seguroLaboral: 'ACHS', codActEmp: 3])
			empresas.add([nombre: 'Bar El Tufo', rut: '3-5', seguroLaboral: 'ISL', codActEmp: 3])
		}
		
		if (rutTrabajador == "128840885") {
			empresas.add([nombre: 'Adexus', rut: '96580060-3', seguroLaboral: 'ISL', codActEmp: 1])
			empresas.add([nombre: 'El Pollo Farsante', rut: '2-7', seguroLaboral: 'ACHS', codActEmp: 3])
			empresas.add([nombre: 'Bar El Tufo', rut: '3-5', seguroLaboral: 'ISL', codActEmp: 3])
		}
		
		if (rutTrabajador == "123456785") {
			empresas.add([nombre: 'MyEnterprise', rut: '45667888-2', seguroLaboral: 'ISL', codActEmp: 1])
		}
		
		return empresas
	}

	/**
	 * Consulta los datos de una empresa
	 */
	def consultaEmpresa(String rut, Date fechaSiniestro) {
		if(rut=='965800603') return ([nombre: 'Adexus', rut: '96580060-3', seguroLaboral: 'ISL', codActEmp: 1])
		if(rut=='27') return ([nombre: 'El Pollo Farsante', rut: '2-7', seguroLaboral: 'ACHS', codActEmp: 3])
		if(rut=='35') return ([nombre: 'Bar El Tufo', rut: '3-5', seguroLaboral: 'ISL', codActEmp: 3])
		return null;
	}

}
