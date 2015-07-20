import cl.adexus.isl.spm.*

fixture {
	
	TipoCuentaMedica.findByCodigo('1')?:(new TipoCuentaMedica([codigo: '1', descripcion: 'Ambulatoria'])).save()
	TipoCuentaMedica.findByCodigo('2')?:(new TipoCuentaMedica([codigo: '2', descripcion: 'Hospitalizada'])).save()
	
}
