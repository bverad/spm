package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class ErrorDetalleCuentaMedica {
	Date fecha
	String mensaje
	
	static belongsTo = [detalleCuentaMedica: DetalleCuentaMedica]
	
    static constraints = {

    }

	static mapping = {
		mensaje type: "text"
	}
}
