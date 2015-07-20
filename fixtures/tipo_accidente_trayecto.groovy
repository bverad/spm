import cl.adexus.isl.spm.*

fixture {
	TipoAccidenteTrayecto.findByCodigo('1')?:(new TipoAccidenteTrayecto([codigo: '1', descripcion: 'De la casa al trabajo'])).save(flush:true)
	TipoAccidenteTrayecto.findByCodigo('2')?:(new TipoAccidenteTrayecto([codigo: '2', descripcion: 'Del trabajo a la casa'])).save(flush:true)
	TipoAccidenteTrayecto.findByCodigo('3')?:(new TipoAccidenteTrayecto([codigo: '3', descripcion: 'Entre trabajos'])).save(flush:true)
}