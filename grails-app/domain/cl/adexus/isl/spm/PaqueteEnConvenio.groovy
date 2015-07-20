package cl.adexus.isl.spm

class PaqueteEnConvenio {
	Convenio convenio
	Paquete paquete

    static constraints = {
		convenio nullable: false
		paquete nullable: false
    }
}
