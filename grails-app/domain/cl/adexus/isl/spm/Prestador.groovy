package cl.adexus.isl.spm

@gorm.AuditStamp
class Prestador {
	
	EstructuraJuridica estructuraJuridica
	PersonaNatural personaNatural
	PersonaJuridica personaJuridica
	PersonaNatural representanteLegal
	PersonaNatural apoderado
	String direccion
	Comuna comuna
	String telefono
	String email
	Date desdeRL
	Date hastaRL
	Date desdeAP
	Date hastaAP
	String cuenta
	String numeroCuenta
	TipoCuenta tipoCuenta
	Banco banco
	String designacion //Se debe cambiar de String a TiposDesignacion
	TipoPrestador tipoPrestador
	Boolean esActivo
	Boolean esPersonaJuridica
	
	
    static constraints = {
		estructuraJuridica nullable: false
		personaNatural nullable: true
		personaJuridica nullable: true
		representanteLegal nullable: true
		apoderado nullable: true
		desdeRL (nullable: true, validator: {val, obj ->
			if (val && obj.hastaRL) {
				if (val > obj.hastaRL)
					['cl.adexus.isl.spm.Prestador.desdeRL.fail']
			}
		})
		hastaRL nullable: true
		desdeAP (nullable: true, validator: {val, obj ->
			if (val && obj.hastaAP) {
				if (val > obj.hastaAP)
					['cl.adexus.isl.spm.Prestador.desdeAP.fail']
			}
		})
		hastaAP nullable: true
		direccion nullable: false, maxSize: 255
		telefono nullable: false, maxSize: 255
		email nullable: false, maxSize: 255
		comuna nullable: false
		cuenta nullable: true, maxSize: 255
		numeroCuenta nullable: true, maxSize: 255
		tipoCuenta nullable: true
		banco nullable: true
		designacion nullable: true, maxSize: 255
		tipoPrestador nullable: false
		esPersonaJuridica nullable: false
		esActivo nullable: false
    }

	static mapping = {
		estructuraJuridica lazy: false
		personaJuridica lazy: false
		personaNatural lazy: false
		tipoPrestador lazy: false
		comuna lazy: false
	}
}
