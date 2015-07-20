package cl.adexus.isl.spm

@gorm.AuditStamp
class PersonaNatural {

	String run
	String nombre
	String apellidoPaterno
	String apellidoMaterno
	String sexo
	Date fechaNacimiento
	Date fechaFallecimiento

	static mapping = {
		id generator: 'assigned', name: "run", type: 'string'
	}

	static constraints = {
		run maxSize: 9, blank: true, mod11: true
		nombre nullable: false, maxSize: 255
		apellidoPaterno nullable: false, maxSize: 255
		apellidoMaterno nullable: true, maxSize: 255
		sexo nullable: true, inList: ["M", "F"]
		fechaNacimiento nullable: true, fechaHoy: new Date()
		fechaFallecimiento nullable: true, fechaHoy: new Date()
	}
}
