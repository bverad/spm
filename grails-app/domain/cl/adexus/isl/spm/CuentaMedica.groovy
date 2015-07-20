package cl.adexus.isl.spm

@gorm.AuditStamp
class CuentaMedica {

	String	folioCuenta //Folio de Cuenta del Prestador
	CentroSalud centroSalud //Determina el prestador
	PersonaNatural trabajador //Trabajador
	Date fechaDesde
	Date fechaHasta
	Date fechaEmision
	TipoCuentaMedica tipoCuenta
	FormatoOrigen formatoOrigen
	int valorCuenta
	int valorCuentaAprobado
	String idArchivo
	boolean esAprobada
	Date fechaAceptacion
	
	static hasMany = [ opas: Integer,
					   odas: Integer,
					   opaeps: Integer,
					   errores: ErrorCuentaMedica,
					   detalleCuentaMedica: DetalleCuentaMedica]
	
    static constraints = {
		detalleCuentaMedica nullable: true
		errores nullable: true
		opas nullable: true
		odas nullable: true
		opaeps nullable: true
		idArchivo nullable: true, maxSize: 255
		valorCuentaAprobado nullable: true
		fechaAceptacion nullable: true
		folioCuenta maxSize: 255
    }
}
