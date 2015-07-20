import cl.adexus.isl.spm.*

fixture {
		OrigenDanyo.findByCodigo('GOLPE')?:(new OrigenDanyo([codigo: 'GOLPE', descripcion: 'Golpe con'])).save(flush:true)
		OrigenDanyo.findByCodigo('CONTA')?:(new OrigenDanyo([codigo: 'CONTA', descripcion: 'Contacto con'])).save(flush:true)
		OrigenDanyo.findByCodigo('CAIDA')?:(new OrigenDanyo([codigo: 'CAIDA', descripcion: 'Caida'])).save(flush:true)
		OrigenDanyo.findByCodigo('SOBRE')?:(new OrigenDanyo([codigo: 'SOBRE', descripcion: 'Sobre-esfuerzo'])).save(flush:true)
		OrigenDanyo.findByCodigo('EXPOS')?:(new OrigenDanyo([codigo: 'EXPOS', descripcion: 'Exposicion'])).save(flush:true)
		OrigenDanyo.findByCodigo('VEHIC')?:(new OrigenDanyo([codigo: 'VEHIC', descripcion: 'Vehicular'])).save(flush:true)
		OrigenDanyo.findByCodigo('OTROS')?:(new OrigenDanyo([codigo: 'OTROS', descripcion: 'Otros'])).save(flush:true)
}