package cl.adexus.isl.spm

@gorm.AuditStamp
class CuestionarioComplejidad {
	
	Boolean pregunta1
	Boolean pregunta2
	Boolean pregunta3
	Boolean pregunta4
	Boolean pregunta5
	Boolean pregunta6
	Integer complejidadCalculada
	Boolean aceptaPropuesta
	
    static constraints = {
		complejidadCalculada nullable: true
		aceptaPropuesta nullable: true
		pregunta1 nullable: false
		pregunta2 nullable: false
		pregunta3 nullable: false
		pregunta4 nullable: false
		pregunta5 nullable: false
		pregunta6 nullable: false
    }
}
