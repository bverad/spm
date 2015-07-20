package cl.adexus.isl.spm

@gorm.AuditStamp
class DetalleGastosReembolso {
	
	Reembolso reembolso
	
	Date fechaGasto
	String numero
	TipoConceptoReembolso concepto
	
	// (TODO) Consultar: ¿Estos se ingresan a priori?
	boolean tipoProveedorPersonaJuridica
	boolean tipoProveedorPersonaNatural
	PersonaJuridica proveedorJuridico
	PersonaNatural  proveedorNatural
	
	long valorDocumento
	long valorSolicitado
	long valorAprobado
	String comentario

    static constraints = {
		reembolso nullable: false
		proveedorJuridico nullable: true
		proveedorNatural  nullable: true
		valorAprobado nullable: true
		comentario nullable: true, maxSize: 255
		numero maxSize: 255
    }
}
