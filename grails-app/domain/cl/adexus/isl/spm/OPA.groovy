package cl.adexus.isl.spm

@gorm.AuditStamp
class OPA {

	Date fechaCreacion
	Date inicioVigencia
	Integer duracionDias
	CentroSalud centroAtencion
	String direccionTrabajador
	Comuna comunaTrabajador
	Integer telefonoTrabajador
	String usuarioEmisor
	
	static belongsTo = [siniestro: Siniestro]
	
	static constraints = {
		direccionTrabajador nullable: true, maxSize: 255
		comunaTrabajador nullable: true
		telefonoTrabajador nullable: true
		usuarioEmisor maxSize: 255
	}
    
}
