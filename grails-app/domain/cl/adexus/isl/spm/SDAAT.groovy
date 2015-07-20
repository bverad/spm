package cl.adexus.isl.spm

@gorm.AuditStamp
class SDAAT {
	Date inicioSolicitud
	Date finSolicitud
	String salida
	String usuario
	Date fechaSiniestro
	PersonaNatural trabajador
	PersonaJuridica empleador
	Integer codigoActividadEmpresa
	Siniestro siniestro
	CuestionarioObrero cuestionarioObrero
	String relato
	Boolean esAccidenteTrayecto
	CuestionarioCalificacionOrigenAccidenteTrayecto cuestionarioOrigenTrayecto
	CuestionarioCalificacionOrigenAccidenteTrabajo cuestionarioOrigenTrabajo
	CuestionarioComplejidad cuestionarioComplejidad
	PersonaNatural denunciante
	CalificacionDenunciante tipoDenunciante
	DIAT diat
	OPA opa
	 
    static constraints = {
		finSolicitud nullable: true
		salida nullable: true, maxSize: 255
		usuario maxSize: 255
		fechaSiniestro fechaHoy: true
		empleador nullable: true
		codigoActividadEmpresa nullable: true
		siniestro nullable: true
		cuestionarioObrero nullable: true
		relato nullable: true
		esAccidenteTrayecto nullable: true
		cuestionarioOrigenTrayecto nullable: true
		cuestionarioOrigenTrabajo nullable: true
		cuestionarioComplejidad nullable: true
		denunciante nullable: true
		tipoDenunciante nullable: true
		diat nullable: true
		opa nullable: true
		
    }
	
	static mapping = {
		relato type: 'text'
	}
}
