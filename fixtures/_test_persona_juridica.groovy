import cl.adexus.isl.spm.*

fixture {
	
		PersonaJuridica.findByRut('567891232')?:(new PersonaJuridica(
			[
				rut: '567891232',nombreFantasia: 'Mutual 1',razonSocial: 'Mutual 1'
			]
			)).save(flush:true)
	
		PersonaJuridica.findByRut('456789331')?:(new PersonaJuridica(
			[
				rut: '456789331',nombreFantasia: 'Hospital 1',razonSocial: 'Hospital 1'
			]
			)).save(flush:true)
	
		PersonaJuridica.findByRut('456678882')?:(new PersonaJuridica(
			[
				rut: '456678882',nombreFantasia: 'Empleador 1',razonSocial: 'Empleador 1'
			]
			)).save(flush:true)
			
		PersonaJuridica.findByRut('345678999')?:(new PersonaJuridica(
			[
				rut: '345678999',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)
				
		PersonaJuridica.findByRut('777246135')?:(new PersonaJuridica(
			[
				rut: '777246135',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)
			
		PersonaJuridica.findByRut('777246186')?:(new PersonaJuridica(
			[
				rut: '777246186',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)

			
		PersonaJuridica.findByRut('777246119')?:(new PersonaJuridica(
			[
				rut: '777246119',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)

		PersonaJuridica.findByRut('777247115')?:(new PersonaJuridica(
			[
				rut: '777247115',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)
						
		PersonaJuridica.findByRut('777246186')?:(new PersonaJuridica(
			[
				rut: '777246186',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)

		PersonaJuridica.findByRut('777246194')?:(new PersonaJuridica(
			[
				rut: '777246194',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)
			
		PersonaJuridica.findByRut('777646117')?:(new PersonaJuridica(
			[
				rut: '777646117',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)
						
		PersonaJuridica.findByRut('777296116')?:(new PersonaJuridica(
			[
				rut: '777296116',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)

		PersonaJuridica.findByRut('777249118')?:(new PersonaJuridica(
			[
				rut: '777249118',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)
			
		
		PersonaJuridica.findByRut('777246437')?:(new PersonaJuridica(
			[
				rut: '777246437',nombreFantasia: 'Empleador 2',razonSocial: 'Empleador 2'
			]
			)).save(flush:true)
			

			
			
			
			
			
			
					
}