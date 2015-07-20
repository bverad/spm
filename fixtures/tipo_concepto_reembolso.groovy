import cl.adexus.isl.spm.*

fixture {
	
	TipoConceptoReembolso.findByCodigo('1')?:(new TipoConceptoReembolso([codigo:'1',descripcion: 'Traslado de Pacientes'])).save(flush:true)
	TipoConceptoReembolso.findByCodigo('2')?:(new TipoConceptoReembolso([codigo:'2',descripcion: 'Medicamentos o Insumos'])).save(flush:true)
	TipoConceptoReembolso.findByCodigo('3')?:(new TipoConceptoReembolso([codigo:'3',descripcion: 'Hospitalización o Atención de Urgencia'])).save(flush:true)
	TipoConceptoReembolso.findByCodigo('4')?:(new TipoConceptoReembolso([codigo:'4',descripcion: 'Alojamiento y/o Colación'])).save(flush:true)
}
