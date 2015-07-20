package cl.adexus.isl.spm

class CambioNivelSeguimiento {

	Integer nivelSeguimiento
	TipoODA principal
	
	static hasMany = [complementarias: TipoODA]
	
	static mapping = {
		id generator: 'assigned', name: 'nivelSeguimiento', type: 'integer'
	}
	
    static constraints = { 
		complementarias nullable: true
	}
}
