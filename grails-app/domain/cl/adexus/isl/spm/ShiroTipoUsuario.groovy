package cl.adexus.isl.spm

class ShiroTipoUsuario {
	
	String id_tipo_usuario
	String name
	
	static mapping = {
		datasource 'auth'
		table "tipo_usuario"
		id generator: 'assigned', name: "id_tipo_usuario"
		name column: "nombre_tipo_usuario", sqlType: "nvarchar"
		id_tipo_usuario sqlType: "integer"
		version false
	}
}





