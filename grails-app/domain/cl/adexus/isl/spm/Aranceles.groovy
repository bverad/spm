package cl.adexus.isl.spm

@gorm.AuditStamp
class Aranceles {
	
	Date desde 
	Date hasta

    static constraints = {
		desde nullable: true
		hasta nullable: true
    }
}
