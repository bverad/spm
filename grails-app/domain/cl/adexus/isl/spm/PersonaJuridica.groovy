package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class PersonaJuridica {
	
	String rut
	String razonSocial
	String nombreFantasia
	

	static mapping = {
		id generator: 'assigned', name: "rut", type: 'string'
	}

	static constraints = {
		rut maxSize: 9, blank: true, mod11: true
		razonSocial nullable: false, maxSize: 255
		nombreFantasia nullable: true, maxSize: 255
	}
    
}
