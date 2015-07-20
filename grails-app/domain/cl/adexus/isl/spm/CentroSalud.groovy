package cl.adexus.isl.spm

@gorm.AuditStamp
class CentroSalud {

	Prestador prestador
	String nombre
	String direccion
	Comuna comuna
	String telefono
	String email
	Integer numeroCamas
	Integer numeroAmbulancias
	TipoCentroSalud tipoCentroSalud
	Boolean esActivo
	Boolean atencionAmbulancia
	Boolean pabellon
	Boolean hospitalizacion
	Boolean atencionUrgencias
	Boolean trasladoPacientes
	Boolean salaDeRayos
	Boolean rescateUrgencias
	Boolean kinesiologia
	Boolean imagenologia
	Boolean otro
	String cual
	String otroCentro 
	
    static constraints = {
		prestador nullable: false
		nombre nullable: false, maxSize: 255
		direccion nullable: false, maxSize: 255
		comuna  nullable: false
		telefono nullable: false, maxSize: 255
		email nullable: false, maxSize: 255
		numeroCamas nullable: true
		numeroAmbulancias nullable: true
		tipoCentroSalud nullable: false
		atencionAmbulancia nullable: true
		pabellon nullable: true
		hospitalizacion nullable: true
		atencionUrgencias nullable: true
		trasladoPacientes nullable: true
		salaDeRayos nullable: true
		rescateUrgencias nullable: true
		kinesiologia nullable: true
		imagenologia nullable: true
		otro nullable: true
		cual nullable: true, maxSize: 255
		otroCentro nullable: true, maxSize: 255
	}
	
	static mapping = {
		prestador lazy: false
	}
}
