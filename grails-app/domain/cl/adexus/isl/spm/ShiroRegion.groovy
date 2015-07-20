package cl.adexus.isl.spm

class ShiroRegion {
	
	String codigo_region
	String name
	
	static mapping = {
		datasource 'auth'
		table "region"
		id generator: 'assigned', name: "codigo_region"
		name column: "nombre_region", sqlType: "varchar"
		codigo_region sqlType: "varchar"
		version false
	}
}





