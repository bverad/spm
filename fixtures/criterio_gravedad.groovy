import cl.adexus.isl.spm.*

fixture {
	/*
	CGO1(cl.adexus.isl.spm.CriterioGravedad,[codigo:'1', descripcion: 'Otro'])
	CGO2(cl.adexus.isl.spm.CriterioGravedad,[codigo:'2', descripcion: 'Grave'])
	CGO3(cl.adexus.isl.spm.CriterioGravedad,[codigo:'3', descripcion: 'Fatal'])
	*/
	CriterioGravedad.findByCodigo('1')?:(new CriterioGravedad([codigo:'1', descripcion: 'Otro'])).save(flush:true)
	CriterioGravedad.findByCodigo('2')?:(new CriterioGravedad([codigo:'2', descripcion: 'Grave'])).save(flush:true)
	CriterioGravedad.findByCodigo('3')?:(new CriterioGravedad([codigo:'3', descripcion: 'Fatal'])).save(flush:true)
				
}