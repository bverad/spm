package cl.adexus.isl.spm

class GrupoIntencionalidad {

    Integer codigo
	String descripcion
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'numeric(19,0)'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
    }
}
