import java.util.Date;

import cl.adexus.isl.spm.*

fixture {

	Alme.findById(1)?:(new Alme(//para consulta de siniestro
			[
				version: 1,
				xmlEnviado: "envio",
				xmlRecibido:"recibo",
				fechaOtorgamiento: new Date(),
				tipoAlta: "1",
				motivoAlta: "primera condicion",
				indicacionEvaluacion: true,
				medico: PersonaNatural.findByRun('56565655'),
		
				fechaAnulacion: new Date(),
				causaAnulacion: "anulado",
				siniestro: Siniestro.findById(8)
			]
			)).save(flush:true)

}