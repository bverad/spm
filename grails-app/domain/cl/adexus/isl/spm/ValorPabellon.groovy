package cl.adexus.isl.spm

@gorm.AuditStamp
class ValorPabellon {
	
	Convenio convenio
	int nivelPabellon
	int valor

    static constraints = {
    }
}
