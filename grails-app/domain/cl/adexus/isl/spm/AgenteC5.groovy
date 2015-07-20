package cl.adexus.isl.spm

@gorm.AuditStamp
class AgenteC5 {

    String codigo
	String descripcion
	AgenteC4 agente4
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
    }
}
