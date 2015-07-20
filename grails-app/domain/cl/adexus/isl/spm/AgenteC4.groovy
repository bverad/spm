package cl.adexus.isl.spm

@gorm.AuditStamp
class AgenteC4 {

    String codigo
	String descripcion
	AgenteC3 agente3
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
    }
}
