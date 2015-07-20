package cl.adexus.isl.spm

class TipoAntecedente {
	
	String codigo
	String descripcion
	String toBpm
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
		toBpm maxSize: 255
    }
}
