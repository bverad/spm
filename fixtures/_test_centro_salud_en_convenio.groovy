import cl.adexus.isl.spm.*

fixture {
	
	CentroSaludEnConvenio.findById(1)?:(new CentroSaludEnConvenio(
			[
				convenio: Convenio.findById(1),
				centroSalud: CentroSalud.findById(1),
				desde: new Date()
			]
			)).save(flush:true)

	CentroSaludEnConvenio.findById(2)?:(new CentroSaludEnConvenio(
			[
				convenio: Convenio.findById(2),
				centroSalud: CentroSalud.findById(2),
				desde: new Date()
			]
			)).save(flush:true)
}