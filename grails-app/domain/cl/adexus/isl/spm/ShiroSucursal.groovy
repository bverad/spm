package cl.adexus.isl.spm

class ShiroSucursal {
	
	String id_sucursal
	String name
	
	static belongsTo = [ agencia: ShiroAgencia ]
	
	static mapping = {
		datasource 'auth'
		table "sucursal_isl"
		id generator: 'assigned', name: "id_sucursal"
		name column: "nombre_sucursal", sqlType: "varchar"
		id_sucursal sqlType: "integer"
		version false
		
		agencia column: "id_agencia",  sqlType: 'integer'
		agencia joinTable: [name: "agencia_isl", column:'id_agencia', key: 'id_agencia' ]
	}
}





