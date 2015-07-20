package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class ErrorDetalleFactura {

	Date fecha
	String mensaje
	
	static belongsTo = [detalleFactura: DetalleFactura]
	
    static constraints = {
		
    }
	
	static mapping = {
		mensaje type: "text"
	}
}
