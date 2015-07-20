import cl.adexus.isl.spm.*

fixture {
	TipoActividadSeguimiento.findByCodigo('1')?:(new TipoActividadSeguimiento([codigo:'1', descripcion: 'Ingreso'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('2')?:(new TipoActividadSeguimiento([codigo:'2', descripcion: 'Revisión Ficha'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('3')?:(new TipoActividadSeguimiento([codigo:'3', descripcion: 'Visita Paciente'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('4')?:(new TipoActividadSeguimiento([codigo:'4', descripcion: 'Contacto Paciente'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('5')?:(new TipoActividadSeguimiento([codigo:'5', descripcion: 'Preparación de Alta'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('6')?:(new TipoActividadSeguimiento([codigo:'6', descripcion: 'Epicrisis'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('7')?:(new TipoActividadSeguimiento([codigo:'7', descripcion: 'Otro'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('8')?:(new TipoActividadSeguimiento([codigo:'8', descripcion: 'Alta Seguimiento'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('9')?:(new TipoActividadSeguimiento([codigo:'9', descripcion: 'Sin Seguimiento'])).save(flush:true)
	TipoActividadSeguimiento.findByCodigo('10')?:(new TipoActividadSeguimiento([codigo:'10', descripcion: 'Re-Ingreso'])).save(flush:true)
}