import java.util.Date;

import cl.adexus.isl.spm.*

fixture {

	Alla.findById(1)?:(new Alla(//para consulta de siniestro
		[
			version: 1,
			xmlEnviado: "envio",
			xmlRecibido:"recibo",
			siniestro: Siniestro.findById(8),
			fechaAlta: new Date(),
			altaInmediata: true,
			condiciones: false,
			tipoCondicion: "primera condicion",
			continuaTratamiento: true,
			tipoTratamiento: "sangramiento con sanguijuelas",
			medico: PersonaNatural.findByRun('56565655'),
			diagnostico: Diagnostico.findById(1),
			periodoCondiciones: 10,
			fechaAnulacion: new Date(),
			causaAnulacion: "anulado"
		]
		)).save(flush:true)
		
}