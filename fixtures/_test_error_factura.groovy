import java.util.Date;

import cl.adexus.isl.spm.*

fixture {
	
	ErrorFactura.findById(1)?:(new ErrorFactura(
			[
				version: 1,
				fecha: new Date(),
				mensaje:'aaaaa2',
				factura: Factura.findById(2)
			]
			)).save(flush:true)
	
}
