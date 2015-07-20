import cl.adexus.isl.spm.*

fixture {
	
	TipoEnfermedad.findByCodigo('1')?:(new TipoEnfermedad([codigo: '1', descripcion: 'Tipo musculoesqueletica (dolor de manos, codos, brazos, piernas, espalda)'])).save(flush:true)
	TipoEnfermedad.findByCodigo('2')?:(new TipoEnfermedad([codigo: '2', descripcion: 'Tipo dermatologica (piel, pelo)'])).save(flush:true)
	TipoEnfermedad.findByCodigo('3')?:(new TipoEnfermedad([codigo: '3', descripcion: 'Tipo respiratoria (pulmones)'])).save(flush:true)
	TipoEnfermedad.findByCodigo('4')?:(new TipoEnfermedad([codigo: '4', descripcion: 'Tipo otorrinolaringologica (oidos, gargantas y nariz)'])).save(flush:true)
	TipoEnfermedad.findByCodigo('5')?:(new TipoEnfermedad([codigo: '5', descripcion: 'Tipo psiquiatrica (angustia, depresión, acoso laboral, sobrecarga laboral)'])).save(flush:true)
	TipoEnfermedad.findByCodigo('6')?:(new TipoEnfermedad([codigo: '6', descripcion: 'Otra no clasificada'])).save(flush:true)

}