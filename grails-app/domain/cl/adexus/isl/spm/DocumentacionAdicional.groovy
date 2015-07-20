package cl.adexus.isl.spm

@gorm.AuditStamp
class DocumentacionAdicional {
	String fileId
	String descripcion
	SDAAT solicitudAT
	SDAEP solicitudEP
	DIAT denunciaAT
	DIEP denunciaEP
	AntecedenteAdicional antecedente
	ActividadSeguimiento actividadSeguimiento
	Bis dictamen
	Bis documentacion
	Siniestro siniestro
	Reembolso reembolso
	DetalleGastosReembolso detalleReembolso
	Seguimiento seguimiento
	Reingreso reingreso
	
	static constraints = {
		fileId				  maxSize: 255
		descripcion           nullable: true, maxSize: 255
		denunciaAT            nullable: true
		denunciaEP            nullable: true
		solicitudAT           nullable: true
		solicitudEP           nullable: true
		antecedente           nullable: true
		actividadSeguimiento  nullable: true
		dictamen			  nullable: true
		documentacion         nullable: true
		siniestro             nullable: true
		reembolso             nullable: true
		detalleReembolso      nullable: true
		seguimiento			  nullable: true
		reingreso			  nullable: true
	}
}
