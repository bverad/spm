package cl.adexus.isl.spm

class CIE10 {

    String codigo
	String descripcion
	
	static mapping = {
		id generator: 'assigned', name: 'codigo', type: 'string'
	}
	
    static constraints = {
		//codigo(matches:'[A-Z][0-9]{2}.([0-9]{1,2}|[xX])')
		descripcion nullable:true, maxSize: 255
    }
}
