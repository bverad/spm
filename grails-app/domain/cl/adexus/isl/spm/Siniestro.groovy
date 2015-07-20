package cl.adexus.isl.spm

@gorm.AuditStamp
class Siniestro {
	
	String cun
	Date fecha
	PersonaNatural trabajador
	PersonaJuridica empleador 
	String relato
	Boolean esEnfermedadProfesional
	DIAT diatOA
	DIEP diepOA
	OPA opa
	OPAEP opaep
	TipoEnfermedad tipoPatologia 
	String usuario
	
	Integer nivelComplejidad
	Date fechaComplejidad
	
	static hasMany = [ diagnostico: Diagnostico
					 , pronostico: Pronostico
					 , seguimientos: Seguimiento
					 , reingresos: Reingreso]

    static constraints = {
		cun nullable: true, maxSize: 255
		relato nullable: true
		opa nullable: true
		opaep nullable: true
		diatOA nullable: true
		diepOA nullable: true
		tipoPatologia nullable:true
		
		nivelComplejidad nullable: true
		fechaComplejidad nullable: true
		usuario maxSize: 255
    }
	
	static mapping = {
		trabajador lazy: false
		diatOA lazy: false
		empleador lazy: false
		relato type: 'text'
	}
	
	static mappedBy = [ diatOA: "none", diepOA: "none" ]
}
