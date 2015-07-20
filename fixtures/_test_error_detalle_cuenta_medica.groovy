import java.util.Date;

import cl.adexus.isl.spm.*

fixture {
	
	ErrorDetalleCuentaMedica.findById(1)?:(new ErrorDetalleCuentaMedica(
			[
				version: 1,
				fecha: new Date(),
				mensaje:'aaaaa2',
				detalleCuentaMedica: DetalleCuentaMedica.findById(2)
			]
			)).save(flush:true)
	
	ErrorDetalleCuentaMedica.findById(2)?:(new ErrorDetalleCuentaMedica(
			[
				version: 1,
				fecha: new Date(),
				mensaje:'bbbbbbb',
				detalleCuentaMedica: DetalleCuentaMedica.findById(3)
			]
			)).save(flush:true)

	ErrorDetalleCuentaMedica.findById(3)?:(new ErrorDetalleCuentaMedica(
			[
				version: 1,
				fecha: new Date(),
				mensaje:'ccccc',
				detalleCuentaMedica: DetalleCuentaMedica.findById(3)
			]
			)).save(flush:true)
		
	ErrorDetalleCuentaMedica.findById(4)?:(new ErrorDetalleCuentaMedica(
			[
				version: 1,
				fecha: new Date(),
				mensaje:'ddddddd',
				detalleCuentaMedica: DetalleCuentaMedica.findById(3)
			]
			)).save(flush:true)
}
