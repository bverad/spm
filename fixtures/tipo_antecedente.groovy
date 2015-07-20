import cl.adexus.isl.spm.*

fixture {
	
	TipoAntecedente.findByCodigo('1')?:(new TipoAntecedente([codigo:'1',descripcion: 'Seguimiento', toBpm:'profesional_seguimiento'])).save(flush:true)
	TipoAntecedente.findByCodigo('2')?:(new TipoAntecedente([codigo:'2',descripcion: 'Prevención', toBpm:'profesional_prevencion'])).save(flush:true)
	TipoAntecedente.findByCodigo('3')?:(new TipoAntecedente([codigo:'3',descripcion: 'Calificación', toBpm:'profesional_calificacion'])).save(flush:true)
}
