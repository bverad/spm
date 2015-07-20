package cl.adexus.isl.spm

@gorm.AuditStamp
class CuestionarioCalificacionOrigenAccidenteTrabajo {
	
	Boolean pregunta1
	String pregunta1_1
	Boolean pregunta2
	String pregunta2_1
	Boolean pregunta3
	String pregunta3_1
	Boolean pregunta4
	String pregunta4_1
	OrigenDanyo origenDanyo
	Boolean pregunta5
	Boolean esOrigenComun
	

    static constraints = {
		esOrigenComun nullable:true
		pregunta1 nullable:false, validator: {val, obj ->
						if  (!val) {
							if (!obj.pregunta1_1)
							return ['invalid.pregunta1_1']
						}
					}
		pregunta2 nullable:false, validator: {val, obj ->
						if  (!val) {
							if (!obj.pregunta2_1)
							return ['invalid.pregunta2_1']
						}
					}
		pregunta3 nullable:false, validator: {val, obj ->
						if  (!val) {
							if (!obj.pregunta3_1)
							return ['invalid.pregunta3_1']
						}
					}
		pregunta4 nullable:false, validator: {val, obj ->
						if  (!val) {
							if (!obj.pregunta4_1)
							return ['invalid.pregunta4_1']
						}
					}
		pregunta1_1 maxSize: 255
		pregunta2_1 maxSize: 255
		pregunta3_1 maxSize: 255
		pregunta4_1 maxSize: 255
		origenDanyo nullable:false
		pregunta5 nullable:false
    }
}
