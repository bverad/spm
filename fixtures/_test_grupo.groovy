import cl.adexus.isl.spm.*

fixture {

	Grupo.findByCodigo('01')?:(new Grupo(
			[
				codigo: '01',
				descripcion: 'I.- SOLO MODALIDAD LIBRE ELECCION',
			]
			)).save(flush:true)

}