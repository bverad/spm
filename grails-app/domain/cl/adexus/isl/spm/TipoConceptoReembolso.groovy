package cl.adexus.isl.spm

@gorm.AuditStamp
class TipoConceptoReembolso {
	String codigo
	String descripcion
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
	static constraints = {
		descripcion nullable:true, maxSize: 255
	}
}
