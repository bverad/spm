package cl.adexus.isl.spm

@gorm.AuditStamp
class DetalleFactura {

	Long idCuentaMedica
	Long valorCuentaMedica
	String comentarioIngreso

	static belongsTo = [factura: Factura]
	
    static constraints = {
		comentarioIngreso nullable: true, maxSize: 255
    }
}
