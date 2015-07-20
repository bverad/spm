package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class RECA {
	
	String xmlEnviado
	String xmlRecibido
	
	Date fechaCalificacion
	Siniestro siniestro
	
	//calificación accidente
	TipoCalificacion calificacion
	TipoEventoSiniestro eventoSiniestro
	
	CodigoForma forma
	CodigoAgenteAccidente agenteAccidente
	CodigoIntencionalidad intencionalidad
	CodigoModoTransporte transporte
	CodigoPapelLesionado lesionado
	CodigoContraparte contraparte
	TipoEvento evento
	
	//codificación enfermedad profesional
	String indicacion
	Agente codificacionAgente
	
	static constraints = {
		xmlEnviado nullable: true
		xmlRecibido nullable: true
		
		siniestro nullable: false
		eventoSiniestro nullable: true
		
		calificacion nullable: true
		fechaCalificacion nullable: true
			
		forma nullable: true
		agenteAccidente nullable: true
		intencionalidad nullable: true
		transporte nullable: true
		lesionado nullable: true
		contraparte nullable: true
		evento nullable: true
		
		indicacion nullable: true, maxSize: 255
		codificacionAgente nullable: true
	}
	
	static mapping = {
		xmlEnviado type: "text"
		xmlRecibido type: "text"
		indicacion type: "text"
	}
	
}
