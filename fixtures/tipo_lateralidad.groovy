import cl.adexus.isl.spm.*

fixture {
	
	TipoLateralidad.findByCodigo('1')?:(new TipoLateralidad([codigo:'1', descripcion: 'Izquierda'])).save(flush:true)
	TipoLateralidad.findByCodigo('2')?:(new TipoLateralidad([codigo:'2', descripcion: 'Derecha'])).save(flush:true)
	TipoLateralidad.findByCodigo('3')?:(new TipoLateralidad([codigo:'3', descripcion: 'Bilateral'])).save(flush:true)
	TipoLateralidad.findByCodigo('4')?:(new TipoLateralidad([codigo:'4', descripcion: 'No Aplica'])).save(flush:true)
	
}