import cl.adexus.isl.spm.*

fixture {

	DIEP.findById(1)?:(new DIEP(
		[
			version: 1,
			agenteSospechoso: 'bicho',
			calificacionDenunciante: CalificacionDenunciante.findByCodigo('2'),
			denunciante: PersonaNatural.findByRun('45678970'),
			descripcionTrabajo: 'cajero',
			empleador: PersonaJuridica.findByRut('456678882'),
			fechaAgente: new Date(),
			fechaEmision: new Date(),
			fechaSintoma: new Date(),
			nacionalidadTrabajador: Nacion.findByCodigo('152'),
			parteCuerpo: 'espalda',
			puestoTrabajo: 'cajero',
			siniestro: Siniestro.findById(3),
			sintoma: 'dolor',
			trabajador: PersonaNatural.findByRun('45678970')
		]
		)).save(flush:true)

}
