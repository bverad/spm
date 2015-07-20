package cl.adexus.isl.spm

class ShiroPermission {
    String permission
	String rol
	
	static belongsTo = ShiroRole
	
	static constraints = {
	}
	
}
