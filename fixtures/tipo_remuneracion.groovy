import cl.adexus.isl.spm.*

fixture {
	
	TipoRemuneracion.findByCodigo('1')?:(new TipoRemuneracion([codigo:'1', descripcion: 'Remuneración fija'])).save(flush:true)
	TipoRemuneracion.findByCodigo('2')?:(new TipoRemuneracion([codigo:'2', descripcion: 'Remuneración variable'])).save(flush:true)
	TipoRemuneracion.findByCodigo('3')?:(new TipoRemuneracion([codigo:'3', descripcion: 'Honorarios'])).save(flush:true)
}