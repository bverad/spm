import cl.adexus.isl.spm.*

fixture {

	SubGrupo.findByCodigo('01')?:(new SubGrupo(
			[
				codigo: '01',
				descripcion: 'ATENCION ABIERTA',
				grupo: Grupo.findByCodigo('01')
			]
			)).save(flush:true)

}