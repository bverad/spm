import cl.adexus.isl.spm.*

fixture {

	
	DetalleNotaCredito.findById(1)?:(new DetalleNotaCredito(
			[
				version: 1,
				idCuentaMedica: 3,
				valorCuentaMedica: 12000,
				notaCredito: NotaCredito.findById(1)
			]
			)).save(flush:true)

	DetalleNotaCredito.findById(2)?:(new DetalleNotaCredito(
			[
				version: 1,
				idCuentaMedica: 4,
				valorCuentaMedica: 30000,
				notaCredito: NotaCredito.findById(1)
			]
			)).save(flush:true)
						
}
