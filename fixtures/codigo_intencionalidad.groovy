import cl.adexus.isl.spm.*

fixture {
	
	CodigoIntencionalidad.findByCodigo(1)?:(new CodigoIntencionalidad([codigo:'1',descripcion: 'No intencional'])).save(flush:true)
	CodigoIntencionalidad.findByCodigo(2)?:(new CodigoIntencionalidad([codigo:'2',descripcion: 'Daño intencional auto infligido'])).save(flush:true)
	CodigoIntencionalidad.findByCodigo(3)?:(new CodigoIntencionalidad([codigo:'3',descripcion: 'Agresión'])).save(flush:true)
	CodigoIntencionalidad.findByCodigo(4)?:(new CodigoIntencionalidad([codigo:'4',descripcion: 'Otro tipo de Violencia'])).save(flush:true)
	CodigoIntencionalidad.findByCodigo(5)?:(new CodigoIntencionalidad([codigo:'5',descripcion: 'Intencionalidad no determinada'])).save(flush:true)
	CodigoIntencionalidad.findByCodigo(6)?:(new CodigoIntencionalidad([codigo:'6',descripcion: 'Complicaciones de atención médica o quirúrgica'])).save(flush:true)
	CodigoIntencionalidad.findByCodigo(8)?:(new CodigoIntencionalidad([codigo:'8',descripcion: 'Otro tipo de intencionalidad específica'])).save(flush:true)
	CodigoIntencionalidad.findByCodigo(9)?:(new CodigoIntencionalidad([codigo:'9',descripcion: 'Intencionalidad no específica'])).save(flush:true)

}
