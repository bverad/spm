import java.util.Date;

import cl.adexus.isl.spm.*

fixture {

	DetalleCuentaMedica.findById(1)?:(new DetalleCuentaMedica(
		[
			version: 1,
			fecha: new Date(),
			codigo: 'cod69',
			glosa: 'gl69',
			cantidad: 1,
			valorUnitario: 7500,
			descuentoUnitario: 2,
			recargoUnitario: 3,
			valorTotal: 7500,
		
			//Datos obtenidos del convenio
			valorUnitarioPactado: 4,
			recargoUnitarioPactado: 5,
			valorTotalPactado: 6, //Se calcula con: cantidad*(valorUnitarioPactado-descuentoUnitario+recargoUnitarioPactado)
			glosaPactada: '69',
		
			//Datos finales resultado del analisis
			cantidadFinal: 7,
			descuentoFinal: 8,
			valorUnitarioFinal: 9,
			recargoUnitarioFinal: 10,
			valorTotalFinal: 11, //Se calcula con: cantidadFinal*(valorUnitarioFinal-descuentoFinal+recargoUnitarioFinal)
		
			consultaMedica: '69',
			respuestaMedicaTexto: '69',
			respuestaMedicaSugiereAprobar: false,  // null, true (Aprobar), false (Rechazar)
			respuestaMedicaModificarCantidad: 12, // null o una cantidad
		
			consultaConvenio: '69',
			respuestaConvenioTexto: '69',
			respuestaConvenioSugiereAprobar: false, // null, true (Aprobar), false (Rechazar)
			respuestaConvenioModificarMonto: 13, // null o una cantidad
		
			cuentaMedica: CuentaMedica.findById(6)
		]
		)).save(flush:true)
		
		DetalleCuentaMedica.findById(2)?:(new DetalleCuentaMedica(
			[
				version: 1,
				fecha: new Date(),
				codigo: '1cod69',
				glosa: '1gl69',
				cantidad: 11,
				valorUnitario: 17500,
				descuentoUnitario: 12,
				recargoUnitario: 13,
				valorTotal: 17500,
			
				//Datos obtenidos del convenio
				valorUnitarioPactado: 14,
				recargoUnitarioPactado: 15,
				valorTotalPactado: 16, //Se calcula con: cantidad*(valorUnitarioPactado-descuentoUnitario+recargoUnitarioPactado)
				glosaPactada: '169',
			
				//Datos finales resultado del analisis
				cantidadFinal: 17,
				descuentoFinal: 18,
				valorUnitarioFinal: 19,
				recargoUnitarioFinal: 110,
				valorTotalFinal: 111, //Se calcula con: cantidadFinal*(valorUnitarioFinal-descuentoFinal+recargoUnitarioFinal)
			
				consultaMedica: '169',
				respuestaMedicaTexto: '169',
				respuestaMedicaSugiereAprobar: false,  // null, true (Aprobar), false (Rechazar)
				respuestaMedicaModificarCantidad: 112, // null o una cantidad
			
				consultaConvenio: '169',
				respuestaConvenioTexto: '169',
				respuestaConvenioSugiereAprobar: false, // null, true (Aprobar), false (Rechazar)
				respuestaConvenioModificarMonto: 113, // null o una cantidad
			
				cuentaMedica: CuentaMedica.findById(6)
			]
			)).save(flush:true)

			DetalleCuentaMedica.findById(3)?:(new DetalleCuentaMedica(
			[
				version: 1,
				fecha: new Date(),
				codigo: '2cod69',
				glosa: '2gl69',
				cantidad: 21,
				valorUnitario: 27500,
				descuentoUnitario: 22,
				recargoUnitario: 213,
				valorTotal: 217500,
			
				//Datos obtenidos del convenio
				valorUnitarioPactado: 214,
				recargoUnitarioPactado: 215,
				valorTotalPactado: 216, //Se calcula con: cantidad*(valorUnitarioPactado-descuentoUnitario+recargoUnitarioPactado)
				glosaPactada: '2169',
			
				//Datos finales resultado del analisis
				cantidadFinal: 217,
				descuentoFinal: 218,
				valorUnitarioFinal: 219,
				recargoUnitarioFinal: 2110,
				valorTotalFinal: 2111, //Se calcula con: cantidadFinal*(valorUnitarioFinal-descuentoFinal+recargoUnitarioFinal)
			
				consultaMedica: '2169',
				respuestaMedicaTexto: '2169',
				respuestaMedicaSugiereAprobar: false,  // null, true (Aprobar), false (Rechazar)
				respuestaMedicaModificarCantidad: 2112, // null o una cantidad
			
				consultaConvenio: '2169',
				respuestaConvenioTexto: '2169',
				respuestaConvenioSugiereAprobar: false, // null, true (Aprobar), false (Rechazar)
				respuestaConvenioModificarMonto: 2113, // null o una cantidad
			
				cuentaMedica: CuentaMedica.findById(6)
			]
			)).save(flush:true)
}
