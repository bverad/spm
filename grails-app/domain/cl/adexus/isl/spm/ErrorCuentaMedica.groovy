package cl.adexus.isl.spm

@gorm.AuditStamp
class ErrorCuentaMedica {

	Date fecha
	String mensaje
	
	static belongsTo = [cuentaMedica: CuentaMedica]
	
    static constraints = {

    }

	static mapping = {
		mensaje type: "text"
	}
}
