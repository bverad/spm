import cl.adexus.isl.spm.*

fixture {
	
	TipoPaquete.findByCodigo('01')?:(new TipoPaquete([codigo:'01', descripcion: 'Diagnósticos y tratamientos ambulatorios de enfermedades profesionales'])).save(flush:true)
	TipoPaquete.findByCodigo('02')?:(new TipoPaquete([codigo:'02', descripcion: 'Diagnósticos y tratamientos ambulatorios de accidentes laborales'])).save(flush:true)	
	TipoPaquete.findByCodigo('03')?:(new TipoPaquete([codigo:'03', descripcion: 'Quirúrgicos de baja complejidad'])).save(flush:true)
	TipoPaquete.findByCodigo('04')?:(new TipoPaquete([codigo:'04', descripcion: 'Tratamiento traumatológico ambulatorio'])).save(flush:true)
	TipoPaquete.findByCodigo('05')?:(new TipoPaquete([codigo:'05', descripcion: 'Terapia ocupacional'])).save(flush:true)
	
}
