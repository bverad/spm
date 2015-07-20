package cl.adexus.isl.spm

@gorm.AuditStamp
class Excepcion {
	SDAAT solicitudAT
	SDAEP solicitudEP
	String motivo
	String autorizador
	String contexto
	
    static constraints = {
		solicitudAT nullable:true
		solicitudEP nullable:true
		motivo blank: false, maxSize: 255
		autorizador blank: false, maxSize: 255
		contexto inList: ["ident", "clatrab", "calorigen"], maxSize: 255
    }
}
