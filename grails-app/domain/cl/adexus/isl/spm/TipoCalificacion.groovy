package cl.adexus.isl.spm

@gorm.AuditStamp
class TipoCalificacion {
    String codigo
	String descripcion
	OrigenSiniestro origen 						
	TipoEventoSiniestro eventoSiniestro			
	Integer altaInmediata
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
		altaInmediata nullable:true
    }
}
