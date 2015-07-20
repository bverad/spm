package cl.adexus.isl.spm

@gorm.AuditStamp
class SubGrupo {
	
	Grupo grupo
	String codigo
	String descripcion
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
    }

	static mapping = {
		grupo lazy: false
	}
}
