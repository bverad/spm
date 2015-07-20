package cl.adexus.isl.spm

class CodigoIntencionalidad {

    String codigo
	String descripcion
	GrupoIntencionalidad grupo
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
		grupo nullable:true
    }
}
