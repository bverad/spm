package cl.adexus.isl.spm

@gorm.AuditStamp
class Agente {

    String codigo
	String descripcion
	String codigoSUSESO
	AgenteC5 agente5
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
		codigoSUSESO maxSize: 255
    }
}
