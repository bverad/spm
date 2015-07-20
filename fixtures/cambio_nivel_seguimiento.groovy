import cl.adexus.isl.spm.*

fixture {

	def s1 = [TipoODA.findByCodigo("1")]
	def s4 = [TipoODA.findByCodigo("1"), TipoODA.findByCodigo("3"), TipoODA.findByCodigo("2")]

	CambioNivelSeguimiento.findByNivelSeguimiento(1)?:(new CambioNivelSeguimiento([nivelSeguimiento:1, principal: TipoODA.findByCodigo("2"), complementarias: s1])).save(flush:true)
	CambioNivelSeguimiento.findByNivelSeguimiento(2)?:(new CambioNivelSeguimiento([nivelSeguimiento:2, principal: TipoODA.findByCodigo("3"), complementarias: s1])).save(flush:true)
	CambioNivelSeguimiento.findByNivelSeguimiento(3)?:(new CambioNivelSeguimiento([nivelSeguimiento:3, principal: TipoODA.findByCodigo("4")					   ])).save(flush:true)
	CambioNivelSeguimiento.findByNivelSeguimiento(4)?:(new CambioNivelSeguimiento([nivelSeguimiento:4, principal: TipoODA.findByCodigo("5"), complementarias: s4])).save(flush:true)

}