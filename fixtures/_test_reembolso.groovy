import java.util.Date;

import cl.adexus.isl.spm.*

fixture {

	Reembolso.findById(1)?:(new Reembolso(
			[
				version: 1,
				siniestro: Siniestro.findById(7),
				
				// I. IDENTIFICACION DEL BENEFICIO
				trasladoPaciente: true,
				medicamentos: false,
				hospitalizacion: false,
				alojamiento: true,
				
				// II. IDENTIFICACION DEL TRABAJADOR
				trabajador: PersonaNatural.findByRun('123456785'),
				trabajadorDireccion: "av. TUCASA 12345",
				trabajadorComuna: Comuna.findByCodigo('13101'),
				trabajadorTelefonoFijo: "22222222",
				trabajadorCelular: "99999999",
				trabajadorEmail: "administrator@your.ass",
				
				// III. IDENTIFICACION DEL SOLICITANTE
				solicitante: PersonaNatural.findByRun('34567875'),
				solicitanteTipo: "Otro",
				solicitanteRelacion: "Madre",
			
				// IV. OPCION DE PAGO
				montoSolicitado: 99999,
				cobrador: PersonaNatural.findByRun('45678970'),
				tipoPagoDeposito: false,
				tipoPagoPresencial: true,
				tipoCuenta: TipoCuenta.findByCodigo('01'),
				numero: "123456",
				banco: Banco.findByCodigo('041'),
				
				// V. OBSERVACIONES
				observaciones: "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz ",
			
				// X. FECHAS APROBACION Y RECHAZO
				// Si ambos son null, aún no se resuelve.
				// Si uno de los dos (!= null), entonces se aprobó o rechazó
				fechaAprobacion: new Date()
			]
			)).save(flush:true)
			
	Reembolso.findById(2)?:(new Reembolso(
		[
			version: 1,
			siniestro: Siniestro.findById(7),
			
			// I. IDENTIFICACION DEL BENEFICIO
			trasladoPaciente: true,
			medicamentos: false,
			hospitalizacion: false,
			alojamiento: true,
			
			// II. IDENTIFICACION DEL TRABAJADOR
			trabajador: PersonaNatural.findByRun('123456785'),
			trabajadorDireccion: "av. TUCASA 12345",
			trabajadorComuna: Comuna.findByCodigo('13101'),
			trabajadorTelefonoFijo: "22222222",
			trabajadorCelular: "99999999",
			trabajadorEmail: "administrator@your.ass",
			
			// III. IDENTIFICACION DEL SOLICITANTE
			solicitante: PersonaNatural.findByRun('34567875'),
			solicitanteTipo: "Empleador",
			solicitanteRelacion: null,
		
			// IV. OPCION DE PAGO
			montoSolicitado: 88888,
			cobrador: PersonaNatural.findByRun('45678970'),
			tipoPagoDeposito: false,
			tipoPagoPresencial: true,
			tipoCuenta: TipoCuenta.findByCodigo('01'),
			numero: "123456",
			banco: Banco.findByCodigo('041'),
			
			// V. OBSERVACIONES
			observaciones: "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz ",
		
			// X. FECHAS APROBACION Y RECHAZO
			// Si ambos son null, aún no se resuelve.
			// Si uno de los dos (!= null), entonces se aprobó o rechazó
			fechaAprobacion: new Date()
		]
		)).save(flush:true)

		Reembolso.findById(3)?:(new Reembolso(//para consulta de siniestro
			[
				version: 1,
				siniestro: Siniestro.findById(8),
				
				// I. IDENTIFICACION DEL BENEFICIO
				trasladoPaciente: true,
				medicamentos: false,
				hospitalizacion: false,
				alojamiento: true,
				
				// II. IDENTIFICACION DEL TRABAJADOR
				trabajador: PersonaNatural.findByRun('123456785'),
				trabajadorDireccion: "av. TUCASA 12345",
				trabajadorComuna: Comuna.findByCodigo('13101'),
				trabajadorTelefonoFijo: "22222222",
				trabajadorCelular: "99999999",
				trabajadorEmail: "administrator@your.ass",
				
				// III. IDENTIFICACION DEL SOLICITANTE
				solicitante: PersonaNatural.findByRun('34567875'),
				solicitanteTipo: "Empleador",
				solicitanteRelacion: null,
			
				// IV. OPCION DE PAGO
				montoSolicitado: 40000,
				cobrador: PersonaNatural.findByRun('45678970'),
				tipoPagoDeposito: false,
				tipoPagoPresencial: true,
				tipoCuenta: TipoCuenta.findByCodigo('01'),
				numero: "123456",
				banco: Banco.findByCodigo('041'),
				
				// V. OBSERVACIONES
				observaciones: "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz ",
			
				// X. FECHAS APROBACION Y RECHAZO
				// Si ambos son null, aún no se resuelve.
				// Si uno de los dos (!= null), entonces se aprobó o rechazó
				fechaAprobacion: new Date()
			]
			)).save(flush:true)

		Reembolso.findById(4)?:(new Reembolso(//para consulta de siniestro
			[
				version: 1,
				siniestro: Siniestro.findById(8),
				
				// I. IDENTIFICACION DEL BENEFICIO
				trasladoPaciente: true,
				medicamentos: false,
				hospitalizacion: false,
				alojamiento: true,
				
				// II. IDENTIFICACION DEL TRABAJADOR
				trabajador: PersonaNatural.findByRun('123456785'),
				trabajadorDireccion: "av. TUCASA 12345",
				trabajadorComuna: Comuna.findByCodigo('13101'),
				trabajadorTelefonoFijo: "22222222",
				trabajadorCelular: "99999999",
				trabajadorEmail: "administrator@your.ass",
				
				// III. IDENTIFICACION DEL SOLICITANTE
				solicitante: PersonaNatural.findByRun('34567875'),
				solicitanteTipo: "Empleador",
				solicitanteRelacion: null,
			
				// IV. OPCION DE PAGO
				montoSolicitado: 88888,
				cobrador: PersonaNatural.findByRun('45678970'),
				tipoPagoDeposito: false,
				tipoPagoPresencial: true,
				tipoCuenta: TipoCuenta.findByCodigo('01'),
				numero: "123456",
				banco: Banco.findByCodigo('041'),
				
				// V. OBSERVACIONES
				observaciones: "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz ",
			
				// X. FECHAS APROBACION Y RECHAZO
				// Si ambos son null, aún no se resuelve.
				// Si uno de los dos (!= null), entonces se aprobó o rechazó
	//			fechaAprobacion: new Date()
			]
			)).save(flush:true)

			
}