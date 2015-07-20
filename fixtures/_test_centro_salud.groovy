import cl.adexus.isl.spm.*

fixture {

	def p456789331=Prestador.findByPersonaJuridica(PersonaJuridica.findByRut('456789331'))
	def p567891232=Prestador.findByPersonaJuridica(PersonaJuridica.findByRut('567891232'))
	
		CentroSalud.findByPrestador(p456789331)?:(new CentroSalud(
			[
				comuna: Comuna.findByCodigo('13101'),
				direccion: 'Juan Noé N° 1367',
				email: 'qaprestadores@gmail.com',
				esActivo: true,
				nombre: 'Sede Centro Salud 1',
				numeroAmbulancias: 1,
				numeroCamas: 0,
				prestador: p456789331,
				telefono: '58 - 231239',
				tipoCentroSalud: TipoCentroSalud.findByCodigo('03')
			]
			)).save(flush:true)
		
		CentroSalud.findByPrestador(p567891232)?:(new CentroSalud(
			[
				comuna: Comuna.findByCodigo('13102'),
				direccion: 'Miraflores N°123',
				email: 'qaprestadores@gmail.com',
				esActivo: true,
				nombre: 'Sede Centro Salud 2',
				numeroAmbulancias: 1,
				numeroCamas: 0,
				prestador: p567891232,
				telefono: '58 - 231239',
				tipoCentroSalud: TipoCentroSalud.findByCodigo('03')
			]
			)).save(flush:true)
	
}