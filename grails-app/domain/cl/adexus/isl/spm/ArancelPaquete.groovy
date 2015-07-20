package cl.adexus.isl.spm

@gorm.AuditStamp
class ArancelPaquete {

	Paquete paquete
	String codigoPrestacion
	String nivel
	String calculo
	int cantidad
	int valorOriginal
	int valor
	int valorNuevo
	boolean cargo
	boolean descuento
	boolean pesos
	boolean porcentaje
	Date desde
	Date hasta
	
    static constraints = {
		valorOriginal nullable: true
		valorNuevo nullable: true
		calculo nullable: true
		hasta nullable: true
		codigoPrestacion maxSize: 255
		nivel maxSize: 255
		calculo maxSize: 255
    }
	
}
