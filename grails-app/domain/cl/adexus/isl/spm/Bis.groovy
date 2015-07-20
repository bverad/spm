package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class Bis {
	
	String taskId
	
	PersonaJuridica emisor
	String runTrabajador
	String rutEmpleador
	
	boolean dictamen
	String numeroDictamen
	Date fechaDictamen
	
	TipoEventoSiniestro tipoSiniestro
	Siniestro siniestro
	Date fechaRecepcion
	Date fechaSiniestro
	
	Long montoSolicitado
	Long montoAprobado 
	Long totalInteres
	Long totalReembolso
	
	//Pago
	Date fechaPago
	Long ufPago
	Double tasaInteres 
	
	//revision
	String revisadoPor
	Date fechaRevision
	
	//**********************************************
	//       Datos extras para armar los PDF
	//**********************************************
	
	//Datos de la entidad que solicita el 77bis
	String ordinario
	String encargadoCobranza
	String entidadCobradora
	String direccionEntidad
	
	//THE rechazo
	String motivoRechazo
	Date fechaCartaRechazo
	Date fechaRechazo
	
	//Comentarios sobre la solitud y pago 77bis
	String comentariosRechazo
	String comentariosRevision
	String comentariosSeguimiento
		
	
	
    static constraints = {
		
		//ID de la tarea
		taskId nullable: true
		
		//Validaciones fechas y rut/run
		fechaRecepcion fechaHoy: new Date()
		fechaSiniestro fechaHoy: new Date()
		runTrabajador maxSize: 9, blank: false, mod11: true
		rutEmpleador maxSize: 9, blank: false, mod11: true
		
		fechaDictamen nullable: true, fechaHoy: new Date()
		
		montoAprobado nullable: true
		numeroDictamen nullable: true
		totalInteres nullable: true
		totalReembolso nullable: true
		
		fechaPago nullable: true
		ufPago nullable: true
		
		tasaInteres nullable: true
		revisadoPor nullable: true
		fechaRevision nullable: true
		
		fechaCartaRechazo nullable: true
		fechaRechazo nullable: true
		motivoRechazo nullable: true
		
		encargadoCobranza nullable: true
		entidadCobradora nullable: true
		direccionEntidad nullable: true
		ordinario nullable: true
		
		comentariosRechazo nullable: true
		comentariosRevision nullable: true
		comentariosSeguimiento nullable: true
		
		siniestro nullable: true
    }
	
	static mapping = {
		comentariosRechazo type: "text"
		comentariosRevision type: "text"
		comentariosSeguimiento type: "text"
	}
	
}
