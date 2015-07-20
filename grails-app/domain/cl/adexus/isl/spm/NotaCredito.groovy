package cl.adexus.isl.spm

@gorm.AuditStamp
class NotaCredito {

	String folio
	Prestador prestador
	Date fechaEnvioPago
	Date fechaPagado	
	Factura facturaOrigen
	
	static hasMany = [ detalleNotaCredito: DetalleNotaCredito ]
	
    static constraints = {
		folio maxSize: 255
		fechaEnvioPago nullable: true
		fechaPagado nullable: true
    }
}
