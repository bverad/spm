import cl.adexus.isl.spm.*

fixture {
	
	CodigoPapelLesionado.findByCodigo(1)?:(new CodigoPapelLesionado([codigo:'1',descripcion: 'Persona a pie, transeúnte'])).save(flush:true)
	CodigoPapelLesionado.findByCodigo(2)?:(new CodigoPapelLesionado([codigo:'2',descripcion: 'Conductor u operario'])).save(flush:true)
	CodigoPapelLesionado.findByCodigo(3)?:(new CodigoPapelLesionado([codigo:'3',descripcion: 'Pasajero'])).save(flush:true)
	CodigoPapelLesionado.findByCodigo(4)?:(new CodigoPapelLesionado([codigo:'4',descripcion: 'Persona que aborda o se baje de un vehículo'])).save(flush:true)
	CodigoPapelLesionado.findByCodigo(5)?:(new CodigoPapelLesionado([codigo:'5',descripcion: 'Persona en la parte exterior de un vehículo'])).save(flush:true)
	CodigoPapelLesionado.findByCodigo(6)?:(new CodigoPapelLesionado([codigo:'6',descripcion: 'Ocupante de vehículo no especificado de otra forma'])).save(flush:true)
	CodigoPapelLesionado.findByCodigo(8)?:(new CodigoPapelLesionado([codigo:'8',descripcion: 'Otro papel de la persona lesionada especificado'])).save(flush:true)
	CodigoPapelLesionado.findByCodigo(9)?:(new CodigoPapelLesionado([codigo:'9',descripcion: 'Rol de la persona lesionada no especificado'])).save(flush:true)

}
