import cl.adexus.isl.spm.*

fixture {
	TipoMedioPruebaAccidente.findByCodigo('1')?:(new TipoMedioPruebaAccidente([codigo:'1', descripcion: 'Parte Carabineros'])).save(flush:true)
	TipoMedioPruebaAccidente.findByCodigo('2')?:(new TipoMedioPruebaAccidente([codigo:'2', descripcion: 'Testigos'])).save(flush:true)
	TipoMedioPruebaAccidente.findByCodigo('3')?:(new TipoMedioPruebaAccidente([codigo:'3', descripcion: 'Declaración'])).save(flush:true)
	TipoMedioPruebaAccidente.findByCodigo('4')?:(new TipoMedioPruebaAccidente([codigo:'4', descripcion: 'Otro'])).save(flush:true)
}
