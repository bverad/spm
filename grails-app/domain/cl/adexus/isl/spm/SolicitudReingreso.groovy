package cl.adexus.isl.spm

@gorm.AuditStamp
class SolicitudReingreso {

	Siniestro siniestro
	Date fechaCreacion
	PersonaNatural solicitante
	SDAEP solicitud
	
	static constraints = {
		solicitud nullable:true
		solicitante nullable:true
	}
}