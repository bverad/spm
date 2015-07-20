package cl.adexus.isl.spm

@gorm.AuditStamp
class Rela {

	String xmlEnviado
	String xmlRecibido
	
	Date inicioReposo
	Date terminoReposo
	Long nDias
	PersonaNatural medico
	Date fechaAnulacion
	String causaAnulacion
	
	Diagnostico diagnostico
	
	static belongsTo = [siniestro: Siniestro]
	
    static constraints = {
		inicioReposo nullable: true
		terminoReposo nullable: true
		nDias nullable: true
		medico nullable: true
		fechaAnulacion nullable: true
		causaAnulacion nullable: true
		diagnostico nullable: true
		xmlEnviado nullable: true
		xmlRecibido nullable: true
    }
	
	static mapping = {
		causaAnulacion type: 'text'
		xmlEnviado type: 'text'
		xmlRecibido type: 'text'
	}
}
