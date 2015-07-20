import cl.adexus.isl.spm.*

fixture {

	AntecedenteAdicional.findById(1)?:(new AntecedenteAdicional([	version: 0, 
																	siniestro: Siniestro.findById(1),
																	tipoAntecedente: TipoAntecedente.findByCodigo('1'), 
																	regionResponsable: Region.findByCodigo('1'), 
																	solicitud: "Examen para el fiambre, please", 
																	fechaSolicitud: new Date() - 4, 
																	estado: false])).save(flush:true);
																	
	AntecedenteAdicional.findById(2)?:(new AntecedenteAdicional([	version: 0,
																	siniestro: Siniestro.findById(1),
																	tipoAntecedente: TipoAntecedente.findByCodigo('2'),
																	regionResponsable: Region.findByCodigo('5'),
																	solicitud: "Se solicita revisar la salud mental de la sra. juanita",
																	fechaSolicitud: new Date() - 2,
																	estado: true])).save(flush:true);
																	
	AntecedenteAdicional.findById(3)?:(new AntecedenteAdicional([	version: 0,
																	siniestro: Siniestro.findById(2),
																	tipoAntecedente: TipoAntecedente.findByCodigo('2'),
																	regionResponsable: Region.findByCodigo('10'),
																	solicitud: "El paciente sergio cayumil, presenta gonorrea, pasar a EP",
																	fechaSolicitud: new Date() - 4,
																	estado: false])).save(flush:true);
	
}
