package cl.adexus.isl.spm

class ShiroUser {
	
    Long id_usuario
    String username
    String passwordHash  // byte[] 
    String salt
    boolean active = true
    String run
    String nombres
    String apellidoPaterno
    String apellidoMaterno
    String correoElectronico
    Date fechaNacimiento
    String idNacionalidad
    Integer sexo
	
    //static hasMany = [ role: ShiroRole, permissions: String ]
    static hasMany = [ roles: ShiroRole]
    static belongsTo = [ tipoUsuario: ShiroTipoUsuario,
        unidadOrganizativa: ShiroUnidadOrganizativa,
        agencia: ShiroAgencia,
        sucursal: ShiroSucursal,
        empresa: ShiroEmpresa
    ]
    
    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }

    static mapping = {
        datasource 'auth'
        //table "shiro_user"
        table "usuario"
        id generator: 'assigned', name: 'id_usuario'
        id_usuario sqlType: "numeric"
        passwordHash column: "contrasena", sqlType: 'varchar'
        username column: "nombre_usuario", sqlType: 'varchar'
        active column: "esta_activo" // , sqlType: 'varchar'
        salt column: "sal", sqlType: 'varchar'
        run column: "rut_usuario", sqlType: 'varchar'
        nombres sqlType: 'nvarchar'
        apellidoPaterno sqlType: 'nvarchar'
        apellidoMaterno sqlType: 'nvarchar'
        correoElectronico sqlType: 'nvarchar'
        sexo sqlType: 'tinyint'
        tipoUsuario column: "id_tipo_usuario",  sqlType: 'int'
        unidadOrganizativa column: "id_unidad_organizativa",  sqlType: 'int'
        agencia column: "id_agencia",  sqlType: 'int'
        sucursal column: "id_sucursal",  sqlType: 'int'
        empresa column: "rut_empresa",  sqlType: 'varchar'
		
        version false
        cache usage: 'read-only'
		
        roles joinTable: [name: "usuario_sistema_rol", key: 'id_usuario' ]
        tipoUsuario joinTable: [name: "tipo_usuario", column:'id_tipo_usuario', key: 'id_tipo_usuario' ]
        unidadOrganizativa joinTable: [name: "nivel_central_isl", column:'id_unidad_organizativa', key: 'id_unidad_organizativa' ]
        agencia joinTable: [name: "agencia_isl", column:'id_agencia', key: 'id_agencia' ]
        sucursal joinTable: [name: "sucursal_isl", column:'id_sucursal', key: 'id_sucursal' ]
        empresa joinTable: [name: "empresa", column:'rut_empresa', key: 'rut_empresa' ]
    }

	
    transient beforeInsert = {
        throw new RuntimeException('create not allowed')
    }
  
    transient beforeUpdate = {
        throw new RuntimeException('update not allowed')
    }
  
    transient beforeDelete = {
        throw new RuntimeException('delete not allowed')
    }
  
}





