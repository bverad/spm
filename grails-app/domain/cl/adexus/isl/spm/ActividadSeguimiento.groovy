package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class ActividadSeguimiento {

	Date fechaActividad
	TipoActividadSeguimiento tipoActividad
	String otro
	String resumen
	String comentario
	
	static hasMany = [documentacion: DocumentacionAdicional]
	
	static belongsTo = [seguimiento: Seguimiento]

    static constraints = {
		otro nullable: true, maxSize: 255
		comentario nullable: true
    }
	
	static mapping = {
		tipoActividad lazy: false
		documentacion lazy: false
		resumen type: 'text'
		comentario type: 'text'
	}
}
