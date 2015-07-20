package cl.adexus.isl.spm

class UsuarioService {

    def getUsuario(username) {
		log.info "ejecutando getUsuario : $username"
		def nombre = username.split("@")
		def usuario=ShiroUser.findByUsername(nombre[0])
		return usuario
    }
	
	def getRegionesUsuario(username){
		
		def usuario = getUsuario(username)
		def dios = false
		def region

		if (usuario.agencia!=null)
			region = usuario.agencia.region.codigo_region
		else if (usuario.sucursal!=null)
			region = usuario.sucursal.agencia.region.codigo_region
		else if (usuario.unidadOrganizativa!=null){
			dios = true
		}
		//Buscar region del usuario.
		//En sucursal.agencia.region si el usuario es de sucursal
		//En agencia.region si el usuario es de agencia

		return [region: region, central: dios]
	}
	
	/*
	 * Si el usuario es de sucursal, son todos los usuarios de su sucursal
	 * Si el usuario es de agencia, son todos los usuarios de su agencia y de todas las sucursales de su agencia
	 * Si es usuario es de nivel central, son todos los usuarios del universo
	 */
	def getUsuariosRelacionados(username){
		def users=[];
		
		def usuario=getUsuario(username);
		if(usuario.sucursal!=null){
			log.debug("Usuario '"+username+"' es de sucursal. Sucursal: "+usuario.sucursal.name);
			log.debug("Buscando usuarios de la sucursal "+usuario.sucursal.id_sucursal);
			
			def usuariosSucursal=ShiroUser.findAllBySucursal(usuario.sucursal)
			usuariosSucursal.each {
				if(it.username!=username)
					users.add(it.username)
			}
		}

		if(usuario.agencia!=null){
			log.debug("Usuario '"+username+"' es de agencia. Agencia: "+usuario.agencia.name);
			log.debug("Buscando usuarios de la agencia "+usuario.agencia.id_agencia);
			def usuariosAgencia=ShiroUser.findAllByAgencia(usuario.agencia)
			usuariosAgencia.each {
				if(it.username!=username)
					users.add(it.username)
			}
			
			log.debug("Buscando sucursales de la agencia "+usuario.agencia.id_agencia);
			def sucursalesAgencia=ShiroSucursal.findAllByAgencia(usuario.agencia)
			sucursalesAgencia.each {
				log.debug("Buscando usuarios de la sucursal "+it.id_sucursal+" "+it.name);
				
				def usuariosSucursal=ShiroUser.findAllBySucursal(it)
				usuariosSucursal.each {
					if(it.username!=username)
						users.add(it.username)
				}
			}
		}
		
		if(usuario.unidadOrganizativa!=null){
			//Todos los usuarios
			def usuariosISL=ShiroUser.findAll()
			usuariosISL.each {
				if(it.username!=username)
					users.add(it.username)
			}
		}

		log.debug("Usuarios relacionados :"+users)
		return users;
	}

	def getUsuariosRegion(username){
		def users=[];
		def usuario=getUsuario(username);
		
		def cod_region = null;
				
		if(usuario.sucursal!=null){
			log.debug("Usuario '"+username+"' es de sucursal. region: "+usuario.sucursal.agencia.region.name);
			cod_region = usuario.sucursal.agencia.region.codigo_region
		}
		
		if(usuario.agencia!=null){
			log.debug("Usuario '"+username+"' es de agencia. region: "+usuario.agencia.region.name);
			cod_region = usuario.agencia.region.codigo_region
		}
		
		if(cod_region!=null){
			//usuarios de sucursal
			def querySucursal = ShiroUser.where {
				(sucursal.agencia.region.codigo_region == cod_region)
				}
			def usuariosSucursal = querySucursal.list()
			usuariosSucursal.each {
				if(it.username!=username && it.sucursal.agencia.region.codigo_region==cod_region) //Feo y que!
				users.add(it.username)
			}
			//usuarios agencia
			def queryAgencia = ShiroUser.where {
				(agencia.region.codigo_region == cod_region)
				}
			def usuariosAgencia = queryAgencia.list()
			
			usuariosAgencia.each {
				if(it.username!=username && it.agencia.region.codigo_region==cod_region)
				users.add(it.username)
			}
		}
		
		if(usuario.unidadOrganizativa!=null){
			//Todos los usuarios
			def usuariosISL=ShiroUser.findAll()
			usuariosISL.each {
				if(it.username!=username)
					users.add(it.username)
			}
		}	
		
		log.debug("Usuarios en la region :"+users)
		return users;
		
	}
	
	def getRegion(username){
		
		def usuario = getUsuario(username);		
		def region = [:];
				
		if(usuario.sucursal!=null){
			log.debug("Usuario '"+username+"' es de sucursal. region: "+usuario.sucursal.agencia.region.name);
			region.put('codigo', usuario.sucursal.agencia.region.codigo_region)
			region.put('descripcion', usuario.sucursal.agencia.region.name)
		}
		
		if(usuario.agencia!=null){
			log.debug("Usuario '"+username+"' es de agencia. region: "+usuario.agencia.region.name);
			region.put('codigo', usuario.agencia.region.codigo_region)
			region.put('descripcion', usuario.agencia.region.name)
		}
		
		if(usuario.unidadOrganizativa!=null){
			log.debug("Usuario '"+username+"' es de agencia. region: "+usuario.unidadOrganizativa.region.name);
			region.put('codigo', usuario.unidadOrganizativa.region.codigo_region)
			region.put('descripcion', usuario.unidadOrganizativa.region.name)
		}
		
		//retorno la región
		return region
		
	}
	
	
}
