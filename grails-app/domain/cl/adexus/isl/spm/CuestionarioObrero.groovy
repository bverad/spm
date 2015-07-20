package cl.adexus.isl.spm

@gorm.AuditStamp
class CuestionarioObrero {
	ActividadTrabajador actividadTrabajador
	String otro
	
    static constraints = {
		otro nullable: true, maxSize: 255, validator: { val, obj ->
            if ((obj.actividadTrabajador.codigo == 'OE' || obj.actividadTrabajador.codigo == 'OO') && !val) {
				return (false) }
        }
    }
}
