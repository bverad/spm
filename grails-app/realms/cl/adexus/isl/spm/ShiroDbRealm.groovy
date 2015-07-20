package cl.adexus.isl.spm

import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.permission.WildcardPermission
import org.apache.shiro.authc.UsernamePasswordToken

import cl.adexus.isl.spm.ShiroRole;

class ShiroDbRealm {
    static authTokenClass = org.apache.shiro.authc.UsernamePasswordToken

    def credentialMatcher
    def shiroPermissionResolver

    def authenticate(authToken) {
        //log.info "Intentando autenticar '${authToken.username}' en DB realm..."
        def username = authToken.username

        // Null username is invalid
        if (username == null) {
            throw new AccountException("No es permitido usuario NULL en DB realm.")
        }
		
		// Null password is invalid
		if (authToken.password == null) {
			throw new AccountException("No es permitida la clave NULL en DB realm.")
		}

        // Get the user with the given username. If the user is not
        // found, then they don't have an account and we throw an exception.
		def user = ShiroUser.findByUsername(username as String)
		//log.info "Encontrado usuario '${user.username}' en DB realm."
        
		//Usuario encontrado igual al solicitado??? ??????
		if (user ==null || !user.username.equals(username)) {
			throw new UnknownAccountException("El usuario [${username}] no existe.")
			//log.info "Usuario '${username}' no existe. El solicitado no es el encontrado"
		}
		
		// Is user active ?
		if (!user.active) {
            throw new UnknownAccountException("El usuario [${username}] inactivo.")
			//log.info "Usuario '${user.username}' inactivo."
        }


        // Now check the user's password against the hashed value stored in the database.
		def account = new SimpleAccount(username, user.passwordHash, "ShiroDbRealm")
		
		// Adapt usernameToken to match criteria		
		def authToken2 = new UsernamePasswordToken(username, user.salt + new org.apache.shiro.crypto.hash.Sha256Hash( authToken.password ))
		
        //def account = new SimpleAccount(username, user.passwordHash, "ShiroDbRealm")
        if (!credentialMatcher.doCredentialsMatch(authToken2, account)) {
            //log.info "Clave incorrecta (DB realm)"
            throw new IncorrectCredentialsException("Clave incorrecta para usuario '${username}'")
        }
				
        return account
    }

    def hasRole(principal, roleName) {
        def roles = ShiroUser.withCriteria {
            roles {
                eq("name", roleName)
            }
            eq("username", principal)
        }

        return roles.size() > 0
    }

    def hasAllRoles(principal, roles) {
        def r = ShiroUser.withCriteria {
            roles {
                'in'("name", roles)
            }
            eq("username", principal)
        }

        return r.size() == roles.size()
    }

    def isPermitted(principal, requiredPermission) {
		
		/* ----------------------------------------------------------------------------
		 * No se considera permisos a nivel de usuario.
		 * 
        // Does the user have the given permission directly associated with himself?
        //
        // First find all the permissions that the user has that match
        // the required permission's type and project code.
        def user = ShiroUser.findByUsername(principal)
        def permissions = user.permissions

        // Try each of the permissions found and see whether any of
        // them confer the required permission.
        def retval = permissions?.find { permString ->
            // Create a real permission instance from the database permission.
            def perm = shiroPermissionResolver.resolvePermission(permString)

            // Now check whether this permission implies the required one.
            if (perm.implies(requiredPermission)) {
                // User has the permission!
                return true
            }
            else {
                return false
            }
        }

        if (retval != null) {
            // Found a matching permission!
            return true
        }
		---------------------------------------------------------------------------- */
		
        // If not, does he gain it through a role?
        // Get the permissions from the roles that the user does have.
        // GP 
		// def results =     ShiroUser.executeQuery("select distinct p from ShiroUser as user join user.role as role join role.permissions as p where user.username = '$principal'")
		// def results = "*:*"

		def user = ShiroUser.findByUsername(principal)
		
		// get permission from domain
		def permissions = []
		for (rol in user.roles) {
			//log.debug "rol:" + rol.name
			//log.debug "permisos:" + rol.permisos
			
			if (rol.permisos) (
				permissions += rol.permisos
			)
			
		}

		
        // There may be some duplicate entries in the results, but
        // at this stage it is not worth trying to remove them                                                                                                                                                                                                                                                                                                                                                                                                                                           . Now,
        // create a real permission from each result and check it
        // against the required one.
        def retval = permissions.find { permString ->
            // Create a real permission instance from the database
            // permission.
            def perm = shiroPermissionResolver.resolvePermission(permString)

            // Now check whether this permission implies the required
            // one.
            if (perm.implies(requiredPermission)) {
                // User has the permission!
                return true
            }
            else {
                return false
            }
        }

        if (retval != null) {
            // Found a matching permission!
            return true
        }
        else {
			log.info("NO AUTORIZADO principal:"+principal+" requiredPermission:"+requiredPermission);
            return false
        }
    }
}
                                                                                                                                                                                                                                                                         