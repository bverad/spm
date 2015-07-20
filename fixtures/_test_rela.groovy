import java.util.Date;

import cl.adexus.isl.spm.*

fixture {

	Rela.findById(1)?:(new Rela(//para consulta de siniestro
			[
				version: 1,
				xmlEnviado: "envio",
				xmlRecibido:"recibo",

				inicioReposo: new Date(),
				terminoReposo: new Date(),
				nDias: 1,
				medico: PersonaNatural.findByRun('56565655'),
		
				fechaAnulacion: new Date(),
				causaAnulacion: "anulado",
				siniestro: Siniestro.findById(8)				
			]
			)).save(flush:true)
			

}