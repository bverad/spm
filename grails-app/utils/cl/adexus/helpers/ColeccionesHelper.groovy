package cl.adexus.helpers

import java.lang.StringBuilder;
import org.apache.commons.logging.LogFactory;

class ColeccionesHelper {
	private static final log = LogFactory.getLog(this);

	def static toJsArrat(java.util.ArrayList lista) {
		
		
		if (lista == null || lista.isEmpty()){
			return  "[]";
		}

		StringBuilder salida = new StringBuilder("[");
		
		try {
			for (Object obj : lista) {
				salida.append('{key:"' + obj.codigo + '", value:"' + obj.descripcion + '"},');
			}
		salida.setLength(salida.length() - 1)
		} catch (Exception e) {
			log.error ("Error toJsArrat " + e.toString());
			e.printStackTrace()
		}
		
		salida.append("]");

		return salida;		
	}
	
}
