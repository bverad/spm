import cl.adexus.isl.spm.*

fixture {
	
	TipoEmpresa.findByCodigo('1')?:(new TipoEmpresa([codigo: '1', descripcion: 'Principal'])).save(flush:true)
	TipoEmpresa.findByCodigo('2')?:(new TipoEmpresa([codigo: '2', descripcion: 'Contratista'])).save(flush:true)
	TipoEmpresa.findByCodigo('3')?:(new TipoEmpresa([codigo: '3', descripcion: 'Subcontratista'])).save(flush:true)
	TipoEmpresa.findByCodigo('4')?:(new TipoEmpresa([codigo: '4', descripcion: 'de Servicios Transitorios'])).save(flush:true)

}
