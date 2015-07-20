import cl.adexus.isl.spm.*

fixture {
	
	TipoODA.findByCodigo('1')?:(new TipoODA([codigo:'1',descripcion: 'Específica'])).save(flush:true)
	TipoODA.findByCodigo('2')?:(new TipoODA([codigo:'2',descripcion: 'Ambulatoria'])).save(flush:true)
	TipoODA.findByCodigo('3')?:(new TipoODA([codigo:'3',descripcion: 'Hospitalización'])).save(flush:true)
	TipoODA.findByCodigo('4')?:(new TipoODA([codigo:'4',descripcion: 'Intensiva'])).save(flush:true)
	TipoODA.findByCodigo('5')?:(new TipoODA([codigo:'5',descripcion: 'Cuidados AVD'])).save(flush:true)
	
}
