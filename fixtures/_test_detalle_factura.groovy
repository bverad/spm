import cl.adexus.isl.spm.*

fixture {
	DetalleFactura.findById(1)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 1,
			valorCuentaMedica: 12000,
			factura: Factura.findById(1)
		]
		)).save(flush:true)

DetalleFactura.findById(2)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 2,
			valorCuentaMedica: 30000,
			factura: Factura.findById(1)
		]
		)).save(flush:true)

DetalleFactura.findById(3)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 3,
			valorCuentaMedica: 6000,
			factura: Factura.findById(2)
		]
		)).save(flush:true)

DetalleFactura.findById(4)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 4,
			valorCuentaMedica: 28000,
			factura: Factura.findById(2)
		]
		)).save(flush:true)
		
DetalleFactura.findById(5)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 3,
			valorCuentaMedica: 6000,
			factura: Factura.findById(3)
		]
		)).save(flush:true)

DetalleFactura.findById(6)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 4,
			valorCuentaMedica: 28000,
			factura: Factura.findById(3)
		]
		)).save(flush:true)
		
DetalleFactura.findById(7)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 3,
			valorCuentaMedica: 2000,
			factura: Factura.findById(4)
		]
		)).save(flush:true)
	
DetalleFactura.findById(8)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 4,
			valorCuentaMedica: 2000,
			factura: Factura.findById(4)
		]
		)).save(flush:true)
					
DetalleFactura.findById(9)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 3,
			valorCuentaMedica: 2000,
			factura: Factura.findById(5)
		]
		)).save(flush:true)
	
DetalleFactura.findById(10)?:(new DetalleFactura(
		[
			version: 1,
			idCuentaMedica: 4,
			valorCuentaMedica: 2000,
			factura: Factura.findById(5)
		]
		)).save(flush:true)

DetalleFactura.findById(11)?:(new DetalleFactura(//para consulta de siniestro
		[
			version: 1,
			idCuentaMedica: 7,
			valorCuentaMedica: 2000,
			factura: Factura.findById(6)
		]
		)).save(flush:true)
	
DetalleFactura.findById(12)?:(new DetalleFactura(//para consulta de siniestro
		[
			version: 1,
			idCuentaMedica: 8,
			valorCuentaMedica: 2000,
			factura: Factura.findById(6)
		]
		)).save(flush:true)

DetalleFactura.findById(13)?:(new DetalleFactura(//para consulta de siniestro
		[
			version: 1,
			idCuentaMedica: 9,
			valorCuentaMedica: 2000,
			factura: Factura.findById(7)
		]
		)).save(flush:true)

}