package cl.adexus.isl.spm

class ShiroAgencia {
	
	String id_agencia
	String name
	
	static belongsTo = [ region: ShiroRegion ]
	
	static mapping = {
		datasource 'auth'
		table "agencia_isl"
		id generator: 'assigned', name: "id_agencia"
		name column: "nombre_agencia", sqlType: "varchar"
		id_agencia sqlType: "integer"
		version false
		
		region column: "codigo_region",  sqlType: 'varchar'
		region joinTable: [name: "region", column:'codigo_region', key: 'codigo_region' ]
	}
}





