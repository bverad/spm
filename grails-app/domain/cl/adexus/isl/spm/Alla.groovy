package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class Alla {

	String xmlEnviado
	String xmlRecibido
	
	Siniestro siniestro
	Date fechaAlta
	Boolean altaInmediata
	Boolean condiciones
	String tipoCondicion
	Boolean continuaTratamiento
	String tipoTratamiento
	PersonaNatural medico
	Diagnostico diagnostico
	Long periodoCondiciones
	Date fechaAnulacion
	String causaAnulacion
	
	static hasMany = [diagnostico: Diagnostico]
	
	static belongsTo = [siniestro: Siniestro]
	
    static constraints = {
		siniestro nullable: false
		fechaAnulacion nullable: true
		causaAnulacion nullable: true
		altaInmediata nullable: true
		condiciones nullable: true
		tipoCondicion nullable: true, maxSize: 255
		continuaTratamiento nullable: true
		tipoTratamiento nullable: true, maxSize: 255
		medico nullable: true
		periodoCondiciones nullable: true
		xmlEnviado nullable: true
		xmlRecibido nullable: true
    }
	
	static mapping = {
		xmlEnviado type: "text"
		xmlRecibido type: "text"
		causaAnulacion type: 'text'
		//condiciones type: 'text'
	}
}
