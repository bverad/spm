package cl.adexus.isl.spm

@gorm.AuditStamp
class DetalleNotaCredito {
	
	Long idCuentaMedica
	Long valorCuentaMedica

	static belongsTo = [ notaCredito: NotaCredito ]
}
