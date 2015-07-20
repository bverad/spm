import cl.adexus.isl.spm.*

fixture {
	
	TipoDuracionContrato.findByCodigo('1')?:(new TipoDuracionContrato([codigo:'1', descripcion: 'Indefinido'])).save(flush:true)
	TipoDuracionContrato.findByCodigo('2')?:(new TipoDuracionContrato([codigo:'2', descripcion: 'Plazo fijo'])).save(flush:true)
	TipoDuracionContrato.findByCodigo('3')?:(new TipoDuracionContrato([codigo:'3', descripcion: 'Por obra o faena'])).save(flush:true)
	TipoDuracionContrato.findByCodigo('4')?:(new TipoDuracionContrato([codigo:'4', descripcion: 'Temporada'])).save(flush:true)

}
