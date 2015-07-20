package cl.adexus.isl.spm

@gorm.AuditStamp
class AgenteC3 {

    String codigo
	String descripcion
	AgenteC2 agente2
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
    }
}
