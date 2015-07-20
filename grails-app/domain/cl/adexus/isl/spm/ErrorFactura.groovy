package cl.adexus.isl.spm

@gorm.AuditStamp
class ErrorFactura {

	Date fecha
	String mensaje
	
	static belongsTo = [factura: Factura]
	
    static constraints = {
		
    }
	
	static mapping = {
		mensaje type: "text"
	}
}
