package cl.adexus.isl.spm

@gorm.AuditStamp
class InformeOPA {
	
	Siniestro siniestro
	PersonaNatural paciente
	PersonaNatural medico
	
	Date fechaAtencion
	Date fechaProximoControl
	
	boolean altaMedica
	boolean reposoLaboral
	boolean estado
	
	String comentarioAtencion
	
    static constraints = {
		fechaProximoControl nullable:true
		fechaAtencion fechaHoy: new Date()
    }
	
	static mapping = {
		comentarioAtencion type: "text"
	}

	
}
