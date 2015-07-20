package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class Seguimiento {

	String resumen
	String observaciones
	Integer nivel
	Date fechaIngreso
	Date fechaAlta
	
	String usuario
	Integer nivelComplejidadIngreso
	Date fechaCambioNivel

	static hasMany = [ actividadSeguimiento: ActividadSeguimiento ]
	static belongsTo = [siniestro: Siniestro]
	
    static constraints = {
		resumen nullable: true
		observaciones nullable: true
		nivel nullable: true
		fechaIngreso nullable: true
		fechaAlta nullable: true

		usuario nullable: true, maxSize: 255
		nivelComplejidadIngreso nullable: true
		fechaCambioNivel nullable: true
    }

	static mapping = {
		resumen type: 'text'
		observaciones type: 'text'
	}
}
