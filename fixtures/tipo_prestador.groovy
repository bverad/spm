import cl.adexus.isl.spm.TipoPrestador

fixture {
	
	TipoPrestador.findByCodigo('1')?:(new TipoPrestador([codigo:'1', descripcion: 'Independiente'])).save(flush: true)
	TipoPrestador.findByCodigo('2')?:(new TipoPrestador([codigo:'2', descripcion: 'Establecimiento de Salud'])).save(flush: true)
	TipoPrestador.findByCodigo('3')?:(new TipoPrestador([codigo:'3', descripcion: 'Proveedor de Insumos'])).save(flush: true)
	
}
