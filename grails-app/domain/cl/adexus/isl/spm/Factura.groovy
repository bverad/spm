package cl.adexus.isl.spm

@gorm.AuditStamp
class Factura {

	String folio
	Prestador prestador
	Date fechaEnvioPago
	Date fechaPagado
	String comentarioIngreso
	Factura facturaOrigen
	String status // ok, ndc, fct, nok; Tiene que ver con dp02 de ingreso: nok es para el "rechazada"
	
	static hasMany = [ errores: ErrorFactura,
					   detalleFactura: DetalleFactura]
	
	static constraints = {
		folio maxSize: 255
		fechaEnvioPago nullable: true
		fechaPagado nullable: true
		comentarioIngreso nullable: true, maxSize: 255
		facturaOrigen nullable: true
		status nullable: true, maxSize: 255
	}
}
