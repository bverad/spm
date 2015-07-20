package cl.adexus.isl.spm

class TipoODA {
    String codigo
	String descripcion
	Long valorReferencia
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
		valorReferencia nullable:true
    }
}
