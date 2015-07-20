import java.util.Date;

import cl.adexus.isl.spm.*

fixture {

	DetalleGastosReembolso.findById(1)?:(new DetalleGastosReembolso(
		[
			version: 1,
			reembolso: Reembolso.findById(1),
			
			fechaGasto: new Date(),
			numero: "434535",
			concepto: TipoConceptoReembolso.findByCodigo('1'),
			
			// (TODO) Consultar: ¿Estos se ingresan a priori?
			tipoProveedorPersonaJuridica: true,
			tipoProveedorPersonaNatural: false,
			proveedorJuridico: PersonaJuridica.findByRut('777646117'),
			proveedorNatural: null,
			
			valorDocumento: 4358,
			valorSolicitado: 232,
			valorAprobado: 232,
			comentario: "sadad"
		]
		)).save(flush:true)
			
	DetalleGastosReembolso.findById(2)?:(new DetalleGastosReembolso(
		[
			version: 1,
			reembolso: Reembolso.findById(1),
			
			fechaGasto: new Date(),
			numero: "34245",
			concepto: TipoConceptoReembolso.findByCodigo('3'),
			
			// (TODO) Consultar: ¿Estos se ingresan a priori?
			tipoProveedorPersonaJuridica: false,
			tipoProveedorPersonaNatural: true,
			proveedorJuridico: null,
			proveedorNatural: PersonaNatural.findByRun('34567891'),
			
			valorDocumento: 43580,
			valorSolicitado: 40000,
			valorAprobado: 2320,
			comentario: null
		]
		)).save(flush:true)
			
	DetalleGastosReembolso.findById(3)?:(new DetalleGastosReembolso(
		[
			version: 1,
			reembolso: Reembolso.findById(2),
			
			fechaGasto: new Date(),
			numero: "34245",
			concepto: TipoConceptoReembolso.findByCodigo('3'),
			
			// (TODO) Consultar: ¿Estos se ingresan a priori?
			tipoProveedorPersonaJuridica: false,
			tipoProveedorPersonaNatural: true,
			proveedorJuridico: null,
			proveedorNatural: PersonaNatural.findByRun('34567891'),
			
			valorDocumento: 43580,
			valorSolicitado: 40000,
			valorAprobado: 2320,
			comentario: null
		]
		)).save(flush:true)
				
	DetalleGastosReembolso.findById(4)?:(new DetalleGastosReembolso(
		[
			version: 1,
			reembolso: Reembolso.findById(3),
			
			fechaGasto: new Date(),
			numero: "34245",
			concepto: TipoConceptoReembolso.findByCodigo('3'),
			
			// (TODO) Consultar: ¿Estos se ingresan a priori?
			tipoProveedorPersonaJuridica: false,
			tipoProveedorPersonaNatural: true,
			proveedorJuridico: null,
			proveedorNatural: PersonaNatural.findByRun('34567891'),
			
			valorDocumento: 43580,
			valorSolicitado: 40000,
			valorAprobado: 2320,
			comentario: null
		]
		)).save(flush:true)
				
	DetalleGastosReembolso.findById(5)?:(new DetalleGastosReembolso(
		[
			version: 1,
			reembolso: Reembolso.findById(2),
			
			fechaGasto: new Date(),
			numero: "34245",
			concepto: TipoConceptoReembolso.findByCodigo('3'),
			
			// (TODO) Consultar: ¿Estos se ingresan a priori?
			tipoProveedorPersonaJuridica: false,
			tipoProveedorPersonaNatural: true,
			proveedorJuridico: null,
			proveedorNatural: PersonaNatural.findByRun('34567891'),
			
			valorDocumento: 43580,
			valorSolicitado: 40000,
			valorAprobado: 2320,
			comentario: null
		]
		)).save(flush:true)

}