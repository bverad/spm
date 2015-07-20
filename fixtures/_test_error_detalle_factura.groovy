import java.util.Date;

import cl.adexus.isl.spm.*

fixture {
	
	ErrorDetalleFactura.findById(1)?:(new ErrorDetalleFactura(
			[
				version: 1,
				fecha: new Date(),
				mensaje:'aaaaa2z',
				detalleFactura: DetalleFactura.findById(4)
			]
			)).save(flush:true)
	
	ErrorDetalleFactura.findById(2)?:(new ErrorDetalleFactura(
			[
				version: 1,
				fecha: new Date(),
				mensaje:'bbbbbbbz',
				detalleFactura: DetalleFactura.findById(3)
			]
			)).save(flush:true)

	ErrorDetalleFactura.findById(3)?:(new ErrorDetalleFactura(
			[
				version: 1,
				fecha: new Date(),
				mensaje:'cccccz',
				detalleFactura: DetalleFactura.findById(3)
			]
			)).save(flush:true)
		
}
