package cl.adexus.isl.spm

@gorm.AuditStamp
class CuestionarioCalificacionOrigenAccidenteTrayecto {
	
	Date pregunta1 //Hora accidente
	Boolean pregunta2
	String pregunta2_1
	Float pregunta3
	Date pregunta4 //Hora salida casa o trabajo
	TipoAccidenteTrayecto tipoAccidenteTrayecto
	Boolean esOrigenComun

    static constraints = {
		pregunta1 nullable:false, validator: {val, obj ->
					if  (val < obj.pregunta4) {
								return ['invalid.horaAccidente.menor']
							}
					}
		pregunta2 nullable:false
		pregunta2_1 nullable:false, maxSize: 255
		pregunta3 nullable:false
		pregunta4 nullable:false
		tipoAccidenteTrayecto nullable:false
		esOrigenComun nullable:true
    }
	
	static mapping = {
		tipoAccidenteTrayecto lazy: false
	}
}
