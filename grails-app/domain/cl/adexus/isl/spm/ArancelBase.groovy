package cl.adexus.isl.spm

@gorm.AuditStamp
class ArancelBase {
	
	SubGrupo subGrupo
	String codigo
	String glosa
	int valorN1
	int valorN2
	int valorN3
	int totalFonasaN1
	int totalFonasaN2
	int totalFonasaN3
	int nivelPabellon
	int equipo
	int anestesiaN1
	int anestesiaN2
	int anestesiaN3
	int cirujano1N1
	int cirujano1N2
	int cirujano1N3
	int cirujano2N1
	int cirujano2N2
	int cirujano2N3
	int cirujano3N1
	int cirujano3N2
	int cirujano3N3
	int cirujano4N1
	int cirujano4N2
	int cirujano4N3
	int procedimientoN1
	int procedimientoN2
	int procedimientoN3
	Date desde
	Date hasta
	String origen
	boolean cargaAprobada
	boolean activado
	
    static constraints = {
		desde nullable: true
		hasta nullable: true
		subGrupo nullable: true
		glosa(maxSize: 5000)
		totalFonasaN1 nullable: true
		totalFonasaN2 nullable: true
		totalFonasaN3 nullable: true
		codigo maxSize: 255
		origen maxSize: 255
    }
	
	static mapping = {
		codigo indexColumn: [name: "idx_codigo"]
	}
}
