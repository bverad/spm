import java.util.Date;

import cl.adexus.isl.spm.*

fixture {
	
	CuentaMedica.findById(1)?:(new CuentaMedica(
			[
				version: 1,
				folioCuenta: '10',
				centroSalud: CentroSalud.findById(1),
				trabajador: PersonaNatural.findByRun('154567844'),
				fechaDesde: new Date(),
				fechaHasta: new Date(),
				fechaEmision: new Date(),
				fechaAceptacion: new Date(),
				tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
				formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
				valorCuenta: 10000,
				valorCuentaAprobado: 10000,
				esAprobada: true
			]
			)).save(flush:true)
	
	CuentaMedica.findById(2)?:(new CuentaMedica(
			[
				version: 1,
				folioCuenta: '11',
				centroSalud: CentroSalud.findById(1),
				trabajador: PersonaNatural.findByRun('154567844'),
				fechaDesde: new Date(),
				fechaHasta: new Date(),
				fechaEmision: new Date(),
				fechaAceptacion: new Date(),
				tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
				formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
				valorCuenta: 25000,
				valorCuentaAprobado: 25000,
				esAprobada: true
			]
			)).save(flush:true)

			CuentaMedica.findById(3)?:(new CuentaMedica(
				[
					version: 1,
					folioCuenta: '12',
					centroSalud: CentroSalud.findById(1),
					trabajador: PersonaNatural.findByRun('123456785'),
					fechaDesde: new Date(),
					fechaHasta: new Date(),
					fechaEmision: new Date(),
					fechaAceptacion: new Date(),
					tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
					formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
					valorCuenta: 8000,
					valorCuentaAprobado: 8000,
					esAprobada: true
				]
				)).save(flush:true)
		
		CuentaMedica.findById(4)?:(new CuentaMedica(
				[
					version: 1,
					folioCuenta: '13',
					centroSalud: CentroSalud.findById(1),
					trabajador: PersonaNatural.findByRun('123456785'),
					fechaDesde: new Date(),
					fechaHasta: new Date(),
					fechaEmision: new Date(),
					fechaAceptacion: new Date(),
					tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
					formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
					valorCuenta: 30000,
					valorCuentaAprobado: 30000,
					esAprobada: true
				]
				)).save(flush:true)
		
		CuentaMedica.findById(5)?:(new CuentaMedica(
					[
						version: 1,
						folioCuenta: '14',
						centroSalud: CentroSalud.findById(1),
						trabajador: PersonaNatural.findByRun('123456785'),
						fechaDesde: new Date(),
						fechaHasta: new Date(),
						fechaEmision: new Date(),
						fechaAceptacion: new Date(),
						tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
						formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
						valorCuenta: 69000,
						valorCuentaAprobado: 69000,
						esAprobada: true
					]
					)).save(flush:true)
					
		CuentaMedica.findById(6)?:(new CuentaMedica(
					[
						version: 1,
						folioCuenta: '69',
						centroSalud: CentroSalud.findById(1),
						trabajador: PersonaNatural.findByRun('123456785'),
						fechaDesde: new Date(),
						fechaHasta: new Date(),
						fechaEmision: new Date(),
						fechaAceptacion: new Date(),
						tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
						formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
						valorCuenta: 69000,
						valorCuentaAprobado: 60000,
						esAprobada: false
					]
					)).save(flush:true)

		CuentaMedica.findById(7)?:(new CuentaMedica(//para consulta de siniestro
				[
					version: 1,
					folioCuenta: '691',
					centroSalud: CentroSalud.findById(1),
					trabajador: PersonaNatural.findByRun('123456785'),
					fechaDesde: new Date(),
					fechaHasta: new Date(),
					fechaEmision: new Date(),
					tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
					formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
					valorCuenta: 69000,
					valorCuentaAprobado: 0,
					esAprobada: false,
					odas:[3]
				]
				)).save(flush:true)

		CuentaMedica.findById(8)?:(new CuentaMedica(//para consulta de siniestro
				[
					version: 1,
					folioCuenta: '692',
					centroSalud: CentroSalud.findById(1),
					trabajador: PersonaNatural.findByRun('123456785'),
					fechaDesde: new Date(),
					fechaHasta: new Date(),
					fechaEmision: new Date(),
					fechaAceptacion: new Date(),
					tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
					formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
					valorCuenta: 69000,
					valorCuentaAprobado: 0,
					esAprobada: false,
					opas:[3]
				]
				)).save(flush:true)
		
		CuentaMedica.findById(9)?:(new CuentaMedica(//para consulta de siniestro
				[
					version: 1,
					folioCuenta: '70',
					centroSalud: CentroSalud.findById(1),
					trabajador: PersonaNatural.findByRun('123456785'),
					fechaDesde: new Date(),
					fechaHasta: new Date(),
					fechaEmision: new Date(),
					fechaAceptacion: new Date(),
					tipoCuenta: TipoCuentaMedica.findByCodigo('2'),
					formatoOrigen: FormatoOrigen.findByCodigo('PAPEL'),
					valorCuenta: 69000,
					valorCuentaAprobado: 60000,
					esAprobada: true,
					opas:[3]
				]
				)).save(flush:true)
}
