import cl.adexus.isl.spm.*

fixture {
	TipoCentroSalud.findByCodigo('01')?:(new TipoCentroSalud([codigo:'01', descripcion: 'Hospital'])).save(flush:true)
	TipoCentroSalud.findByCodigo('02')?:(new TipoCentroSalud([codigo:'02', descripcion: 'Clínica'])).save(flush:true)
	TipoCentroSalud.findByCodigo('03')?:(new TipoCentroSalud([codigo:'03', descripcion: 'Policlinico'])).save(flush:true)
	TipoCentroSalud.findByCodigo('04')?:(new TipoCentroSalud([codigo:'04', descripcion: 'Oficina Administrativa'])).save(flush:true)
	TipoCentroSalud.findByCodigo('05')?:(new TipoCentroSalud([codigo:'05', descripcion: 'Oficina Atención a Publico'])).save(flush:true)
	TipoCentroSalud.findByCodigo('06')?:(new TipoCentroSalud([codigo:'06', descripcion: 'Sede apoyo a giro'])).save(flush:true)
	TipoCentroSalud.findByCodigo('07')?:(new TipoCentroSalud([codigo:'07', descripcion: 'Otro'])).save(flush:true)	
	TipoCentroSalud.findByCodigo('08')?:(new TipoCentroSalud([codigo:'08', descripcion: 'Centro de salud'])).save(flush:true)
	
}