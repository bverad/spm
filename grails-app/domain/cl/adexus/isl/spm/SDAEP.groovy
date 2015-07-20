package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class SDAEP {
	Date inicioSolicitud
	Date finSolicitud
	String salida
	String usuario
	Date fechaSintomas
	PersonaNatural trabajador
	Siniestro siniestro
	PersonaJuridica empleador
	Integer codigoActividadEmpresa
	CuestionarioObrero cuestionarioObrero
	String relato
	CuestionarioCalificacionOrigenEnfermedadProfesional cuestionarioOrigen
	PersonaNatural denunciante
	CalificacionDenunciante tipoDenunciante
	SolicitudReingreso solicitudReingreso
	DIEP diep
	OPAEP opaep
	
	 
    static constraints = {
		finSolicitud nullable: true
		salida nullable: true, maxSize: 255
		usuario maxSize: 255
		fechaSintomas nullable: true, fechaHoy: true
		empleador nullable: true
		siniestro nullable: true
		codigoActividadEmpresa nullable: true
		cuestionarioObrero nullable: true
		relato nullable: true
		cuestionarioOrigen nullable: true
		denunciante nullable: true
		tipoDenunciante nullable: true
		solicitudReingreso nullable: true
		diep nullable: true
		opaep nullable: true
    }
	
	static mapping = {
		relato type: 'text'
	}
}