package cl.adexus.isl.spm

class SistemaSalud {
	Integer codigo
	String descripcion

	static mapping = {
		id generator: 'assigned', name:'codigo', type:'int'
	}

	static constraints = {
		descripcion nullable:true, maxSize: 50
	}
}
