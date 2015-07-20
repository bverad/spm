package cl.adexus.isl.spm

@gorm.AuditStamp
class Diagnostico {
	
	Boolean esLaboral
	String diagnostico
	CodigoUbicacionLesion parte
	TipoLateralidad lateralidad
	OrigenDiagnostico origen
	Date fechaDiagnostico
	CIE10 cie10
	Boolean desdeSeguimiento
	
	
	static belongsTo = [siniestro: Siniestro]
	
    static constraints = {
		fechaDiagnostico fechaHoy: new Date()
		desdeSeguimiento nullable: true
    }
	
	static mapping = {
		diagnostico type: "text"	
	}
	
}
