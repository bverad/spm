package cl.adexus.isl.spm

@gorm.AuditStamp
class Paquete {
	SubGrupo subGrupo
	String codigo
	String glosa
	TipoPaquete tipoPaquete
	String reposoEstimado
	String escalamiento
	String origen
	int complejidad
	int valor
	int efectividad
	Date desde
	Date hasta

    static constraints = {
		subGrupo nullable: false
		codigo nullable: false, maxSize: 255
		glosa nullable: false, maxSize: 255
		tipoPaquete nullable: false
		reposoEstimado nullable: false, maxSize: 255
		escalamiento maxSize: 255
		origen maxSize: 255
		complejidad: nullable: false
		desde nullable: false
		hasta nullable: true
    }
	
	static mapping = {
		subGrupo lazy: false
	}
}
