package cl.adexus.isl.spm

@gorm.AuditStamp
class Reingreso {

	Date fechaIngreso
	String nombre
	String apellidoPaterno
	String apellidoMaterno
	String direccion
	Long telefono
	String email
	String motivo
	Date fechaAprobacion
	
	String resumenCaso
	String observacion
	String nivelSeguimiento

	static belongsTo = [siniestro: Siniestro]
	
    static constraints = {
		nombre maxSize: 255
		apellidoPaterno maxSize: 255
		apellidoMaterno nullable: true, maxSize: 255
		direccion maxSize: 255
		email maxSize: 255
		resumenCaso nullable: true
		observacion nullable: true
		nivelSeguimiento nullable: true
		fechaAprobacion nullable: true
		nivelSeguimiento maxSize: 255
    }

	static mapping = {
		motivo type: 'text'
		resumenCaso type: 'text'
		observacion type: 'text'
	}
}
