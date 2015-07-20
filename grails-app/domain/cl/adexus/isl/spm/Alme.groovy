package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class Alme {

	String xmlEnviado
	String xmlRecibido
	
	Date fechaOtorgamiento
	String tipoAlta
	String motivoAlta
	Boolean indicacionEvaluacion
	PersonaNatural medico
	
	Date fechaAnulacion
	String causaAnulacion
	Diagnostico diagnostico
	
	static hasMany = [diagnostico: Diagnostico]
	
	static belongsTo = [siniestro: Siniestro]
	
    static constraints = {
		fechaAnulacion nullable: true
		causaAnulacion nullable: true
		fechaOtorgamiento nullable: true
		tipoAlta nullable: true, maxSize: 255
		motivoAlta nullable: true, maxSize: 255
		indicacionEvaluacion nullable: true
		medico nullable: true
		diagnostico nullable: true
		xmlEnviado nullable: true
		xmlRecibido nullable: true
    }
	
	static mapping = {
		xmlEnviado type: "text"
		xmlRecibido type: "text"
		causaAnulacion type: 'text'
	}
}