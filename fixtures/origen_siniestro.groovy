import cl.adexus.isl.spm.*

fixture {
	
	OrigenSiniestro.findByCodigo(1)?:(new OrigenSiniestro([codigo:'1',descripcion: 'Laboral'])).save(flush:true)
	OrigenSiniestro.findByCodigo(2)?:(new OrigenSiniestro([codigo:'2',descripcion: 'Común'])).save(flush:true)
	OrigenSiniestro.findByCodigo(3)?:(new OrigenSiniestro([codigo:'3',descripcion: 'Especial'])).save(flush:true)

}
