import cl.adexus.isl.spm.*

fixture {
	
	TipoEventoSiniestro.findByCodigo(1)?:(new TipoEventoSiniestro([codigo:'1',descripcion: 'Trabajo'])).save(flush:true)
	TipoEventoSiniestro.findByCodigo(2)?:(new TipoEventoSiniestro([codigo:'2',descripcion: 'Trayecto'])).save(flush:true)
	TipoEventoSiniestro.findByCodigo(3)?:(new TipoEventoSiniestro([codigo:'3',descripcion: 'Enfermedad'])).save(flush:true)
	TipoEventoSiniestro.findByCodigo(4)?:(new TipoEventoSiniestro([codigo:'4',descripcion: 'Otro'])).save(flush:true)

}
