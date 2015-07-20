package cl.adexus.isl.spm

class ShiroUnidadOrganizativa {
	
	String id_unidad_organizativa
	String name
	
	static belongsTo = [ region: ShiroRegion ]
	
	static mapping = {
		datasource 'auth'
		table "nivel_central_isl"
		id generator: 'assigned', name: "id_unidad_organizativa"
		name column: "nombre_unidad", sqlType: "nvarchar"
		id_unidad_organizativa sqlType: "integer"
		version false
		
		region column: "codigo_region",  sqlType: 'varchar'
		region joinTable: [name: "region", column:'codigo_region', key: 'codigo_region' ]
	}
}





