package cl.adexus.isl.spm

class DroolsService {

	/*
	 * Retorna si un codigo de empresa pueda considerarse exclusivamente como empleado.
	 */
    def esEmpleado(Integer codActEmp) {
		if(codActEmp==null) return false;
		
		switch (codActEmp){
			//	Trabajadores Independientes pre reforma
			case (	1	):  //	TAXISTA PROP. (I) NO ESPE. (G)
			case (	2	):  //	TAXISTA PROP. (I) JUBILADO (G)
			case (	8	):  //	IND. HIPICA NACIONAL - PREPARADORES
			case (	9	):  //	IND. HIPICA NACIONAL - PREPARADORES
			case (	10	):  //	IND. HIPICA NACIONAL - JINETES
			case (	11	):  //	IND. HIPICA NACIONAL - JINETES
			//	DS 110 EN CLASIFICADOR DE ACTIVIDADES ECONÓMICAS SII  
			case (	52010	):  //	PESCA INDUSTRIAL
			case (	52020	):  //	ACTIVIDAD PESQUERA DE BARCOS FACTORIAS
			case (	222101	):  //	IMPRESION PRINCIPALMENTE DE LIBROS
			case (	222109	):  //	OTRAS ACTIVIDADES DE IMPRESION N.C.P.
			case (	611001	):  //	TRANSPORTE MARITIMO Y DE CABOTAJE DE PASAJEROS
			case (	611002	):  //	TRANSPORTE MARITIMO Y DE CABOTAJE DE CARGA
			case (	612001	):  //	TRANSPORTE DE PASAJEROS POR VIAS DE NAVEGACION INTERIORES
			case (	612002	):  //	TRANSPORTE DE CARGA POR VIAS DE NAVEGACION INTERIORES
			case (	630910	):  //	AGENCIAS DE ADUANAS
			case (	651100	):  //	BANCA CENTRAL
			case (	651910	):  //	BANCOS
			case (	751110	):  //	GOBIERNO CENTRAL
			case (	751120	):  //	MUNICIPALIDADES
			case (	751200	):  //	ACTIVIDADES DEL PODER JUDICIAL
			case (	751300	):  //	ACTIVIDADES DEL PODER LEGISLATIVO
			case (	752100	):  //	RELACIONES EXTERIORES
			case (	752200	):  //	ACTIVIDADES DE DEFENSA
			case (	752300	):  //	ACTIVIDADES DE MANTENIMIENTO DEL ORDEN PUBLICO Y DE SEGURIDAD
			case (	922000	):  //	ACTIVIDADES DE AGENCIAS DE NOTICIAS
			case (	922001	):  //	AGENCIAS DE NOTICIAS
			case (	924140	):  //	HIPODROMOS
				return true
				break;
			default:
				return false
				break;
		}
    }
	
	def esOrigenComunTrayecto(CuestionarioCalificacionOrigenAccidenteTrayecto cuestionario){
		if (cuestionario.pregunta2) {
			def diff = (cuestionario.pregunta1.time - cuestionario.pregunta4.time) / (1000*60*60) 
			def difHoras = (cuestionario.pregunta1.time - cuestionario.pregunta4.time) / (1000*60*60) 
			// (A – D) =< (C * 2) Es Accidente Trayecto
			if (difHoras <= (cuestionario.pregunta3 * 2)) {
				return false
			} else {
				// (A – D) > (C * 2) Es de origen Común
				return difHoras > (cuestionario.pregunta3 * 2)
			}
		}
		return true;
	}
	
	def esOrigenComunTrabajo(CuestionarioCalificacionOrigenAccidenteTrabajo cuestionario){
		return !( cuestionario.pregunta1 || cuestionario.pregunta2 || cuestionario.pregunta3 || cuestionario.pregunta4 )
	}
	
	def calcularComplejidad(CuestionarioComplejidad cuestionario){
		
		if (!cuestionario.pregunta1 && !cuestionario.pregunta2 && !cuestionario.pregunta3) {
			return 0
		} else {
			return 1
		}
	}
}
