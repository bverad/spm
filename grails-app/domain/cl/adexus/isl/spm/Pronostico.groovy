package cl.adexus.isl.spm

@gorm.AuditStamp
class Pronostico {
	Date fecha
	String comentario

	static belongsTo = [seguimiento: Seguimiento]

	static constraints = {
		comentario blank: false
	}
	
	static mapping = {
		comentario type: 'text'
	}
}
