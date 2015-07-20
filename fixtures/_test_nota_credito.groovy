import cl.adexus.isl.spm.*

import java.text.DateFormatimport java.util.Date;

import java.text.SimpleDateFormat

fixture {
		NotaCredito.findById(1)?:(new NotaCredito(
			[
				folio: 'NC1',
				prestador: Prestador.findById(1),
				fechaEnvioPago: new Date(),
				facturaOrigen: Factura.findById(5)
			]
			)).save(flush:true)
					
}