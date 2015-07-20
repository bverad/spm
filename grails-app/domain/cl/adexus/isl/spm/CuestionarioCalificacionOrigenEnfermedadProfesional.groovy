package cl.adexus.isl.spm

@gorm.AuditStamp
class CuestionarioCalificacionOrigenEnfermedadProfesional {

	String pregunta1
	String pregunta2
	String pregunta3
	String pregunta4
	String pregunta5
	String pregunta6
	String pregunta7
	TipoEnfermedad tipoEnfermedad
	
    static constraints = {
		pregunta1 maxSize: 255
		pregunta2 maxSize: 255
		pregunta3 maxSize: 255
		pregunta4 maxSize: 255
		pregunta5 maxSize: 255
		pregunta6 maxSize: 255
		pregunta7 maxSize: 255
    }
}
