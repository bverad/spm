package cl.adexus.isl.spm

@gorm.AuditStamp
class Convenio {
	
	Prestador prestador
	String nombre
	TipoConvenio tipoConvenio
	String numeroResolucion
	String numeroLicitacion
	Date fechaResolucion
	Date fechaAdjudicacion
	Date inicio
	Date termino
	String periodoReajustable
	Date fechaProximoReajuste
	int recargoHorarioInhabil
	long montoConvenido
	String nombreResponsable
	String cargoResponsable
	String emailResponsable
	String telefonoResponsable
	String nombreISL
	String cargoISL
	String emailISL
	String telefonoISL
	boolean esActivo

    static constraints = {
		nombre nullable: false, maxSize: 255
		tipoConvenio nullable: false
		numeroResolucion nullable: false, maxSize: 255
		numeroLicitacion nullable: false, maxSize: 255
		fechaResolucion nullable: false
		fechaAdjudicacion nullable: false
		inicio nullable: false
		termino nullable: false
		montoConvenido nullable: false
		periodoReajustable nullable: false, maxSize: 255
		fechaProximoReajuste nullable: false
		recargoHorarioInhabil nullable: false
		nombreResponsable nullable: false, maxSize: 255
		cargoResponsable nullable: false, maxSize: 255
		emailResponsable nullable: false, maxSize: 255
		telefonoResponsable nullable: false, maxSize: 255
		nombreISL nullable: false, maxSize: 255
		cargoISL nullable: false, maxSize: 255
		emailISL nullable: false, maxSize: 255
		telefonoISL nullable: false, maxSize: 255
		esActivo nullable: false
    }
}
