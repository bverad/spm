import cl.adexus.isl.spm.*

fixture {
	
	EstructuraJuridica.findByCodigo('01')?:(new EstructuraJuridica([codigo:'01', descripcion: 'Corporación'])).save(flush:true)
	EstructuraJuridica.findByCodigo('02')?:(new EstructuraJuridica([codigo:'02', descripcion: 'Sociedad Anonima'])).save(flush:true)
	EstructuraJuridica.findByCodigo('03')?:(new EstructuraJuridica([codigo:'03', descripcion: 'Sociedad Limitada'])).save(flush:true)
	EstructuraJuridica.findByCodigo('04')?:(new EstructuraJuridica([codigo:'04', descripcion: 'Persona Natural'])).save(flush:true)

}