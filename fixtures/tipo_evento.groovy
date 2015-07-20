import cl.adexus.isl.spm.*

fixture {
	
	TipoEvento.findByCodigo('1')?:(new TipoEvento([codigo:'1',descripcion: 'Evento relacionado con la lesión de tránsito de transporte terrestre'])).save(flush:true)
	TipoEvento.findByCodigo('2')?:(new TipoEvento([codigo:'2',descripcion: 'Evento relacionado con la lesión  de transporte terrestre no considerada de tránsito'])).save(flush:true)
	TipoEvento.findByCodigo('3')?:(new TipoEvento([codigo:'3',descripcion: 'Evento relacionado con la lesión de transporte terrestre - sin especificar entre tránsito o no considerado de tránsito'])).save(flush:true)
	TipoEvento.findByCodigo('4')?:(new TipoEvento([codigo:'4',descripcion: 'Vehículo de transporte como sitio del evento que ocasionó la lesión'])).save(flush:true)
	TipoEvento.findByCodigo('5')?:(new TipoEvento([codigo:'5',descripcion: 'Choque o colisión de transporte acuático'])).save(flush:true)
	TipoEvento.findByCodigo('6')?:(new TipoEvento([codigo:'6',descripcion: 'Choque o colisión de transporte aéreo o espacial'])).save(flush:true)
	TipoEvento.findByCodigo('8')?:(new TipoEvento([codigo:'8',descripcion: 'Otro tipo de evento especificado, relacionado con la lesión de transporte'])).save(flush:true)
	TipoEvento.findByCodigo('9')?:(new TipoEvento([codigo:'9',descripcion: 'Tipo de evento, no especificado, relacionado con la lesión de transporte'])).save(flush:true)

}
