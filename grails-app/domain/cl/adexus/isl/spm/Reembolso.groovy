package cl.adexus.isl.spm

@gorm.AuditStamp
class Reembolso {

	Siniestro siniestro
	
	// I. IDENTIFICACION DEL BENEFICIO
	boolean trasladoPaciente
	boolean medicamentos
	boolean hospitalizacion
	boolean alojamiento
	
	// II. IDENTIFICACION DEL TRABAJADOR
	PersonaNatural trabajador
	String trabajadorDireccion
	Comuna trabajadorComuna
	String trabajadorTelefonoFijo
	String trabajadorCelular
	String trabajadorEmail
	
	// III. IDENTIFICACION DEL SOLICITANTE
	PersonaNatural solicitante
	String solicitanteTipo
	String solicitanteRelacion

	// IV. OPCION DE PAGO	
	long montoSolicitado
	PersonaNatural cobrador
	boolean tipoPagoDeposito
	boolean tipoPagoPresencial
	TipoCuenta tipoCuenta
	String numero
	Banco banco
	
	// V. OBSERVACIONES
	String observaciones

	// X. FECHAS APROBACION Y RECHAZO
	// Si ambos son null, aún no se resuelve.
	// Si uno de los dos (!= null), entonces se aprobó o rechazó
	Date fechaAprobacion
	Date fechaRechazo
	
	// Este ocurre en OTP_revisión
	Date fechaEnvioPago
	
    static constraints = {
		siniestro           nullable: false
		trabajador          nullable: false
		solicitante         nullable: false
		trabajadorDireccion maxSize: 255
		trabajadorTelefonoFijo maxSize: 255
		trabajadorCelular 	maxSize: 255
		trabajadorEmail 	maxSize: 255
		solicitanteTipo 	maxSize: 255
		solicitanteRelacion nullable: true, maxSize: 255
		tipoCuenta          nullable: true
		numero              nullable: true, maxSize: 255
		banco               nullable: true
		fechaAprobacion     nullable: true
		fechaRechazo        nullable: true
		fechaEnvioPago      nullable: true
    }
	
	static mapping = {
		observaciones type: 'text'
	}
}
