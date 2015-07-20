import cl.adexus.isl.spm.*

fixture {

	Factura.findById(1)?:(new Factura(
			[
				version: 1,
				folio: 'FX1',
				prestador: Prestador.findById(1),
				status: 'ndc'
			]
			)).save(flush:true)

	Factura.findById(2)?:(new Factura(
			[
				version: 1,
				folio: 'FX2',
				prestador: Prestador.findById(1),
				status: 'fct'
			]
			)).save(flush:true)

	Factura.findById(3)?:(new Factura(
		[
			version: 1,
			folio: 'FX3',
			prestador: Prestador.findById(1),
			status: 'fct'
		]
		)).save(flush:true)
		
	Factura.findById(4)?:(new Factura(
		[
			version: 1,
			folio: 'FX4',
			prestador: Prestador.findById(1),
			facturaOrigen: Factura.findById(3)
		]
	)).save(flush:true)
		
	Factura.findById(5)?:(new Factura(
		[
			version: 1,
			folio: 'FX45',
			prestador: Prestador.findById(1),
			status: 'ndc'
		]
	)).save(flush:true)

	Factura.findById(6)?:(new Factura(//para consulta de siniestro
		[
			version: 1,
			folio: 'FX6',
			prestador: Prestador.findById(1),
			status: 'ndc'
		]
	)).save(flush:true)

	
	Factura.findById(7)?:(new Factura(//para consulta de siniestro
		[
			version: 1,
			folio: 'FX7',
			prestador: Prestador.findById(1),
			status: 'ndc',
			fechaEnvioPago: new Date()
		]
	)).save(flush:true)
}