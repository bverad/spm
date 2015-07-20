package cl.adexus.isl.spm

class ShiroEmpresa {
	
	String rut_empresa
	String name
	
	static mapping = {
		datasource 'auth'
		table "empresa"
		id generator: 'assigned', name: "rut_empresa"
		name column: "nombre_empresa", sqlType: "nvarchar"
		rut_empresa sqlType: "varchar"
		version false
	}
}





