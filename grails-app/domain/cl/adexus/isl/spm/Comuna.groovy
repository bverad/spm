package cl.adexus.isl.spm

class Comuna {

    String codigo
	String descripcion
	Provincia provincia
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		descripcion nullable:true, maxSize: 255
    }
}
