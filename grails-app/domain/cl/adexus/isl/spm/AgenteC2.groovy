package cl.adexus.isl.spm

@gorm.AuditStamp
class AgenteC2 {

    String codigo
	String descripcion
	AgenteC1 agente1
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
    }
}
