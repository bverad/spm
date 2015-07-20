import cl.adexus.isl.spm.*

fixture {
	
	CategoriaOcupacion.findByCodigo('1')?:(new CategoriaOcupacion([codigo:'1', descripcion: 'Empleador'])).save(flush:true)
	CategoriaOcupacion.findByCodigo('2')?:(new CategoriaOcupacion([codigo:'2', descripcion: 'Trabajador dependiente'])).save(flush:true)
	CategoriaOcupacion.findByCodigo('3')?:(new CategoriaOcupacion([codigo:'3', descripcion: 'Trabajador independiente'])).save(flush:true)
	CategoriaOcupacion.findByCodigo('4')?:(new CategoriaOcupacion([codigo:'4', descripcion: 'Familiar'])).save(flush:true)
	CategoriaOcupacion.findByCodigo('5')?:(new CategoriaOcupacion([codigo:'5', descripcion: 'Trabajador voluntario'])).save(flush:true)
		
}