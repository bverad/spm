package cl.adexus.isl.spm

@gorm.AuditStamp
class AntecedenteAdicional {
	
	Siniestro siniestro
	TipoAntecedente	tipoAntecedente
	Region regionResponsable
	String solicitud
	String respuesta
	Boolean estado
	Date fechaSolicitud
	Date fechaRespuesta
	
    static constraints = {
		solicitud nullable:true
		respuesta nullable:true
		fechaSolicitud nullable:true
		fechaRespuesta nullable:true
		regionResponsable nullable:true
    }
	
	static mapping = {
		solicitud type: "text"
		respuesta type: "text"
	}
	
}
