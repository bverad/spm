import cl.adexus.isl.spm.*

fixture {
	
	TipoPropiedadEmpresa.findByCodigo('1')?:(new TipoPropiedadEmpresa([codigo: '1', descripcion: 'Privada'])).save(flush:true)
	TipoPropiedadEmpresa.findByCodigo('2')?:(new TipoPropiedadEmpresa([codigo: '2', descripcion: 'Pública'])).save(flush:true)
	
}