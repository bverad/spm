package cl.adexus.isl.spm

class FormatoOrigen {
	String codigo
	String descripcion
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
	static constraints = {
		descripcion nullable:true, maxSize: 255
	}
}
