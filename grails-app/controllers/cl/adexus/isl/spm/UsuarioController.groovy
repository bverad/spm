package cl.adexus.isl.spm

import grails.converters.JSON
import org.apache.shiro.SecurityUtils

class UsuarioController {

	def UsuarioService
	
    def index() { 
		def usuario=UsuarioService.getUsuario(SecurityUtils.subject?.principal)
		log.info("Usuario:"+usuario.run)
		return [usuario: usuario]
	}
}
