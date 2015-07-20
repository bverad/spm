package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class ODA {
	
	Date fechaCreacion
	Date inicioVigencia
	Date terminoVigencia
	Boolean cesada
	Integer duracionDias
	CentroSalud centroAtencion
	String descripcionEvento
	Date fechaEvento
	
	TipoODA tipoODA
	
	String direccionTrabajador
	Comuna comunaTrabajador
	Long telefonoTrabajador
	String emailTrabajador
	
	static hasMany = [prestaciones: OdaArancelConvenio]
	
	static belongsTo = [siniestro: Siniestro]

    static constraints = {
		descripcionEvento nullable: true, maxSize: 255
		fechaEvento nullable: true
		prestaciones nullable: true
		cesada nullable: true
		direccionTrabajador maxSize: 255
		emailTrabajador maxSize: 255
    }
	
	static mapping = {
		centroAtencion lazy: false
		tipoODA lazy: false
		comunaTrabajador lazy: false
	}
}
