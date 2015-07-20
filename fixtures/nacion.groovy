import cl.adexus.isl.spm.*

fixture {
	
	Nacion.findByCodigo('152')?:(new Nacion([codigo:'152', descripcion: 'Chile'])).save(flush:true)
	Nacion.findByCodigo('032')?:(new Nacion([codigo:'032', descripcion: 'Argentina'])).save(flush:true)
	Nacion.findByCodigo('170')?:(new Nacion([codigo:'170', descripcion: 'Colombia'])).save(flush:true)
	Nacion.findByCodigo('218')?:(new Nacion([codigo:'218', descripcion: 'Ecuador'])).save(flush:true)
	Nacion.findByCodigo('604')?:(new Nacion([codigo:'604', descripcion: 'Peru'])).save(flush:true)
	
}