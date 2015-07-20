package cl.adexus.isl.spm

@gorm.AuditStamp
class Provincia {

    String codigo
	String descripcion
	Region region
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
    }
}
