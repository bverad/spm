import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {

	Diagnostico.findById(1)?:(new Diagnostico([ version: 1,
												esLaboral: true,
												diagnostico: "Infeccion en el tracto digestivo inferior",
												parte: CodigoUbicacionLesion.findByCodigo('7.1'),
												lateralidad: TipoLateralidad.findByCodigo('4'),
												origen: OrigenDiagnostico.findByCodigo('1'),
												cie10: CIE10.findByCodigo('A00.0'),
												fechaDiagnostico: new Date()-1,
												siniestro: Siniestro.findById('1')])
	).save(flush:true)
	
	Diagnostico.findById(2)?:(new Diagnostico([ version: 1,
												esLaboral: false,
												diagnostico: "Gastroenteritis",
												parte: CodigoUbicacionLesion.findByCodigo('7.1'),
												lateralidad: TipoLateralidad.findByCodigo('3'),
												origen: OrigenDiagnostico.findByCodigo('2'),
												cie10: CIE10.findByCodigo('A08.4'),
												fechaDiagnostico: new Date()-1,
												siniestro: Siniestro.findById('1')])
	).save(flush:true)

	Diagnostico.findById(3)?:(new Diagnostico([ version: 1,
												esLaboral: true,
												diagnostico: "Corte en brazo, sangrado abundante",
												parte: CodigoUbicacionLesion.findByCodigo('5.7'),
												lateralidad: TipoLateralidad.findByCodigo('2'),
												origen: OrigenDiagnostico.findByCodigo('2'),
												cie10: CIE10.findByCodigo('A40.0'),
												fechaDiagnostico: new Date()-1,
												siniestro: Siniestro.findById('2')])
	).save(flush:true)

	Diagnostico.findById(4)?:(new Diagnostico([ version: 1,
												esLaboral: true,
												diagnostico: "Amputación de emergencia",
												parte: CodigoUbicacionLesion.findByCodigo('5.7'),
												lateralidad: TipoLateralidad.findByCodigo('2'),
												origen: OrigenDiagnostico.findByCodigo('3'),
												cie10: CIE10.findByCodigo('A40.0'),
												fechaDiagnostico: new Date()-1,
												siniestro: Siniestro.findById('2')])
	).save(flush:true)
	
	Diagnostico.findById(5)?:(new Diagnostico([ version: 1,
												esLaboral: true,
												diagnostico: "Infeccion en aparato sexual, probable gonorrea",
												parte: CodigoUbicacionLesion.findByCodigo('4.4'),
												lateralidad: TipoLateralidad.findByCodigo('4'),
												origen: OrigenDiagnostico.findByCodigo('2'),
												cie10: CIE10.findByCodigo('A60.0'),
												fechaDiagnostico: new Date()-1,
												siniestro: Siniestro.findById('3')])
	).save(flush:true)

	Diagnostico.findById(6)?:(new Diagnostico([ version: 1,
												esLaboral: true,
												diagnostico: "Se detecto SIDA",
												parte: CodigoUbicacionLesion.findByCodigo('10'),
												lateralidad: TipoLateralidad.findByCodigo('4'),
												origen: OrigenDiagnostico.findByCodigo('4'),
												cie10: CIE10.findByCodigo('A64.X'),
												fechaDiagnostico: new Date()-1,
												siniestro: Siniestro.findById('3')])
	).save(flush:true)
	
}