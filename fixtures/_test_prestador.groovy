import cl.adexus.isl.spm.*

fixture {
	
	
	def pj456789331=PersonaJuridica.findByRut('456789331')
	Prestador.findByPersonaJuridica(pj456789331)?:(new Prestador(
		[
			banco: Banco.findByCodigo('037'),
			comuna: Comuna.findByCodigo('13101'),
			direccion: 'Calle Mutual # 1',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj456789331,
			esPersonaJuridica: true,
			telefono: '82098188',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
		)).save(flush:true)
	
		
	def pj567891232=PersonaJuridica.findByRut('567891232')
	Prestador.findByPersonaJuridica(pj567891232)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj567891232,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
		)).save(flush:true)

	
	def pj456678882=PersonaJuridica.findByRut('456678882')
	Prestador.findByPersonaJuridica(pj456678882)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj456678882,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)
	
	


	

	
		
			

	/*
	 def pj777246194=PersonaJuridica.findByRut('777246194')
	 def pj777646117=PersonaJuridica.findByRut('777646117')
	 def pj777296116=PersonaJuridica.findByRut('777296116')
	 def pj777249118=PersonaJuridica.findByRut('777249118')
	 def pj777246518=PersonaJuridica.findByRut('777246518')
	 def pj777246437=PersonaJuridica.findByRut('777246437')
	 def pj747246114=PersonaJuridica.findByRut('747246114')
	 */

	def pj777246186=PersonaJuridica.findByRut('777246186')
	Prestador.findByPersonaJuridica(pj777246186)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj777246186,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)

	
	def pj777247115=PersonaJuridica.findByRut('777247115')
	Prestador.findByPersonaJuridica(pj777247115)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj777247115,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)
	

	def pj777246119=PersonaJuridica.findByRut('777246119')
	Prestador.findByPersonaJuridica(pj777246119)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj777246119,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)

	
	def pj777246194=PersonaJuridica.findByRut('777246194')
	Prestador.findByPersonaJuridica(pj777246194)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj777246194,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)
	
	/////////////////////////////////////////////////////////////////////////////////	
	def pj777646117=PersonaJuridica.findByRut('777646117')
	Prestador.findByPersonaJuridica(pj777646117)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj777646117,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)
	
	def pj777296116=PersonaJuridica.findByRut('777296116')
	Prestador.findByPersonaJuridica(pj777296116)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj777296116,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)
	
	
	def pj777249118=PersonaJuridica.findByRut('777249118')
	Prestador.findByPersonaJuridica(pj777249118)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj777249118,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)
	
	
	
	def pj777246437=PersonaJuridica.findByRut('777246437')
	Prestador.findByPersonaJuridica(pj777246437)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			estructuraJuridica: EstructuraJuridica.findByCodigo('01'),
			personaJuridica: pj777246437,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)
	
	def pn56565655=PersonaJuridica.findByRut('56565655')
	Prestador.findByPersonaNatural(pn56565655)?:(new Prestador(
		[
			banco: Banco.findByCodigo('012'),
			comuna: Comuna.findByCodigo('13102'),
			direccion: 'Calle Mutual # 2',
			email: 'qaprestadores@gmail.com',
			esActivo: true,
			esPersonaJuridica: false,
			estructuraJuridica: EstructuraJuridica.findByCodigo('04'),
			personaNatural: pn56565655,
			esPersonaJuridica: true,
			telefono: '12345678',
			tipoPrestador: TipoPrestador.findByCodigo('1')
		]
	)).save(flush:true)
}