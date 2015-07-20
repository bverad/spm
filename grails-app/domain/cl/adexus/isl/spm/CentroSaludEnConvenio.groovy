package cl.adexus.isl.spm

@gorm.AuditStamp
class CentroSaludEnConvenio {
	
	Convenio convenio
	CentroSalud centroSalud
	//Date desde
	//Date hasta
	
    static constraints = {
		//hasta nullable: true
    }
}
