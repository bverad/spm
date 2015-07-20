package cl.adexus.isl.spm

class CalendarController {
	def usuarioService
	
	def index(params) {
		log.info("Calendar.index.params:"+params)
		if(params.username==null || params.username==''){
			render "No username"
			return
		}
		def username=params.username;
		def usuario=usuarioService.getUsuario(username);
		if(usuario==null){
			render "Usuario: "+username+" no existe";
			return
		}
		def usernameEmail=usuario.correoElectronico;
		
		def seguimientos = Seguimiento.findAllByUsuarioAndFechaAltaIsNull(username);
		def eventos = []
		
		Calendar c = Calendar.getInstance();
		seguimientos.each() { seguimiento ->
			log.debug("Siniestro: "+seguimiento.siniestro.id+". Nivel: "+seguimiento.nivel);
			
			//Desde la fecha de ingreso de seguimiento o desde la fecha de cambio de nivel
			c.setTime(seguimiento.fechaIngreso);
			if(seguimiento.fechaCambioNivel!=null){
				c.setTime(seguimiento.fechaCambioNivel);
			}
			
			//Nivel de Seguimiento 1
			if(seguimiento.nivel == 1) {
				//Una revision ficha a los 30,60 y 90 dias
				for (def i = 1; i <= 3; i++) {
					c.add(Calendar.DATE, 30 * i);
					def e=[  siniestro: seguimiento.siniestro, 
						fechaActividad: c.getTime(), 
						   descripcion: 'Revision Ficha'];
					eventos.add(e)
				}
			}
			
			//Nivel de Seguimiento 2
			if(seguimiento.nivel == 2) {
				//Una revision ficha a los 5,10,15..,30 dias
				//Una visita paciente a los 15 y 30 dias
				//Un contacto paciente a los 15 y 30 dias
				for (def i = 1; i <= 6; i++) {
					c.add(Calendar.DATE, 5 * i);
					def e=[  siniestro: seguimiento.siniestro,
						fechaActividad: c.getTime(),
						   descripcion: 'Revision Ficha'];
					eventos.add(e)
					if(i==3 || i==6){
						def v=[  siniestro: seguimiento.siniestro,
						    fechaActividad: c.getTime(),
						       descripcion: 'Visita Paciente'];
					   	eventos.add(v)
						def cp=[  siniestro: seguimiento.siniestro,
						    fechaActividad: c.getTime(),
						       descripcion: 'Contacto Paciente'];
					   	eventos.add(cp)
					}
				}
			}
			
			//Nivel de Seguimiento 3
			if(seguimiento.nivel == 3) {
				//Una revision ficha a los 3,6,9..,30 dias
				//Una visita paciente a los 7,14,21,28 dias
				//Un contacto paciente a los 7,14,21,28 dias
				for (def i = 1; i <= 10; i++) {
					c.add(Calendar.DATE, 3 * i);
					def e=[  siniestro: seguimiento.siniestro,
						fechaActividad:c.getTime(),
						   descripcion: 'Revision Ficha'];
					eventos.add(e)
				}
				for (def i = 1; i <= 4; i++) {
					c.add(Calendar.DATE, 7 * i);
					def v=[  siniestro: seguimiento.siniestro,
						    fechaActividad:c.getTime(),
						       descripcion: 'Visita Paciente'];
					   	eventos.add(v)
					def cp=[  siniestro: seguimiento.siniestro,
					    fechaActividad:c.getTime(),
					       descripcion: 'Contacto Paciente'];
				   	eventos.add(cp)
				}
			}
			
			//Nivel de Seguimiento 4
			if(seguimiento.nivel == 4) {
				//Una revision ficha a los 90, 180 y 270 dias
				//Una visita paciente a los 90, 180 y 270 dias
				//Un contacto paciente a los 30,60,90,120,...,360 dias
				for (def i = 1; i <= 12; i++) {
					c.add(Calendar.DATE, 30 * i);
					def cp=[  siniestro: seguimiento.siniestro,
						fechaActividad:c.getTime(),
						   descripcion: 'Contacto Paciente'];
					eventos.add(cp)
					
					if(i==3 || i==6 || i==9 || i==12){
						def e=[  siniestro: seguimiento.siniestro,
							fechaActividad:c.getTime(),
							   descripcion: 'Revision Ficha'];
						eventos.add(e)
						def v=[  siniestro: seguimiento.siniestro,
							fechaActividad:c.getTime(),
							   descripcion: 'Visita Paciente'];
						   eventos.add(v)
					}
				}
			}
		}; 
		
		log.debug("Eventos.size:"+eventos.size())

		render(contentType: 'text/calendar', filename: 'calendario-'+username+'.ics') {
			calendar {
				events {
					for (e in eventos) {
						event(start: e.fechaActividad,
								end: e.fechaActividad,
							description: 'Código Siniestro: '+e.siniestro.id+"\n"+
									 	'Resumen  : '+e.descripcion,
							summary: '[S:'+e.siniestro.id+'] '+e.descripcion) {
								organizer(name: username, email: usernameEmail)
							}
					}
				}
			}
		}
	}


}
