import java.util.Date;

import cl.adexus.isl.spm.*

fixture {

	RECA.findById(1)?:(new RECA(
			[
				version: 1,
				xmlEnviado: "envia",
				xmlRecibido: "recibe",
				fechaCalificacion: new Date(),
				siniestro: Siniestro.findById(7),
				calificacion: TipoCalificacion.findByCodigo('08'),
				eventoSiniestro: TipoEventoSiniestro.findByCodigo(3),
	
				forma: CodigoForma.findByCodigo(11),
				agenteAccidente: CodigoAgenteAccidente.findByCodigo(111),
				intencionalidad: CodigoIntencionalidad.findByCodigo(9),
				transporte: CodigoModoTransporte.findByCodigo('3.1'),
				lesionado: CodigoPapelLesionado.findByCodigo(1),
				contraparte: CodigoContraparte.findByCodigo('1.1'),
				evento: TipoEvento.findByCodigo('6'),
				
				indicacion: "indicacion 1",
				codificacionAgente: null
				
			]
			)).save(flush:true)
			
	RECA.findById(2)?:(new RECA(//para consulta de siniestro
			[
				version: 1,
				xmlEnviado: "<envia/>",
				xmlRecibido: "<recibe/>",
				fechaCalificacion: new Date(),
				siniestro: Siniestro.findById(8),
				calificacion: TipoCalificacion.findByCodigo('01'),
				eventoSiniestro: TipoEventoSiniestro.findByCodigo(3),
	
				forma: CodigoForma.findByCodigo(11),
				agenteAccidente: CodigoAgenteAccidente.findByCodigo(111),
				intencionalidad: CodigoIntencionalidad.findByCodigo(9),
				transporte: CodigoModoTransporte.findByCodigo('3.1'),
				lesionado: CodigoPapelLesionado.findByCodigo(1),
				contraparte: CodigoContraparte.findByCodigo('1.1'),
				evento: TipoEvento.findByCodigo('6'),
				
				indicacion: "indicacion 1",
				codificacionAgente: null
				
			]
			)).save(flush:true)

}