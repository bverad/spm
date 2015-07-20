package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class DIATWEB {
	Date fechaSiniestro
	PersonaNatural trabajador
	PersonaJuridica empleador
	DIAT diat
	
    static constraints = {
		fechaSiniestro fechaHoy: new Date() //No puede ser mayor que ahora
		empleador nullable: true
		diat nullable: true
    }
}
