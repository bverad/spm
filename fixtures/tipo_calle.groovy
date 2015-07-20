import cl.adexus.isl.spm.*

fixture {
	
	TipoCalle.findByCodigo('1')?:(new TipoCalle([codigo: '1', descripcion: 'Avenida'])).save(flush:true)
	TipoCalle.findByCodigo('2')?:(new TipoCalle([codigo: '2', descripcion: 'Calle'])).save(flush:true)
	TipoCalle.findByCodigo('3')?:(new TipoCalle([codigo: '3', descripcion: 'Pasaje'])).save(flush:true)
	
}