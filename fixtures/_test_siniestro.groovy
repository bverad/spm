import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Siniestro.findById(1)?:(new Siniestro([ version: 1, cun: null, diatOA: DIAT.findById(2), diepOA: null, empleador: PersonaJuridica.findByRut('456678882'), esEnfermedadProfesional: false, fecha: new Date(), opa: OPA.findById(1)	, opaep: null				, relato: null, tipoPatologia: null, trabajador: PersonaNatural.findByRun('123456785')	, usuario: 'iarancibias', nivelComplejidad: 2])).save(flush:true)
	Siniestro.findById(2)?:(new Siniestro([ version: 1, cun: null, diatOA: null, diepOA: null, empleador: PersonaJuridica.findByRut('456678882'), esEnfermedadProfesional: false, fecha: new Date(), opa: null				, opaep: null				, relato: null, tipoPatologia: null, trabajador: PersonaNatural.findByRun('34567875')	, usuario: 'iarancibias', nivelComplejidad: 2])).save(flush:true)
	Siniestro.findById(3)?:(new Siniestro([ version: 1, cun: null, diatOA: null, diepOA: null, empleador: PersonaJuridica.findByRut('456678882'), esEnfermedadProfesional: true, fecha: new Date(), opa: null				, opaep: OPAEP.findById(1)	, relato: null, tipoPatologia: null, trabajador: PersonaNatural.findByRun('45678970')	, usuario: 'iarancibias', nivelComplejidad: 2])).save(flush:true)
	
	Siniestro.findById(4)?:(new Siniestro([ version: 1, cun: null, diatOA: null, diepOA: null, empleador: PersonaJuridica.findByRut('456678882'), esEnfermedadProfesional: false, fecha: df.parse('2013-01-01 00:00:00'), opa: OPA.findById(2)	, opaep: null 				, relato: null, tipoPatologia: null, trabajador: PersonaNatural.findByRun('34567891')	, usuario: 'iarancibias', nivelComplejidad: 2])).save(flush:true)
	Siniestro.findById(5)?:(new Siniestro([ version: 1, cun: null, diatOA: null, diepOA: null, empleador: PersonaJuridica.findByRut('456678882'), esEnfermedadProfesional: true, fecha: new Date(), opa: null, opaep: OPAEP.findById(2)	, relato: null, tipoPatologia: null, trabajador: PersonaNatural.findByRun('56781234')	, usuario: 'iarancibias', nivelComplejidad: 2])).save(flush:true)
	Siniestro.findById(6)?:(new Siniestro([ version: 1, cun: null, diatOA: null, diepOA: null, empleador: PersonaJuridica.findByRut('456678882'), esEnfermedadProfesional: false, fecha: df.parse('2013-01-01 00:00:00'), opa: null				, opaep: null 				, relato: null, tipoPatologia: null, trabajador: PersonaNatural.findByRun('34567891')	, usuario: 'iarancibias', nivelComplejidad: 2])).save(flush:true)
	Siniestro.findById(7)?:(new Siniestro([ version: 1, cun: 12348, diatOA: DIAT.findById(3), diepOA: null, empleador: PersonaJuridica.findByRut('456678882'), esEnfermedadProfesional: false, fecha: new Date(), opa: OPA.findById(1)	, opaep: null				, relato: null, tipoPatologia: null, trabajador: PersonaNatural.findByRun('123456785')	, usuario: 'iarancibias', nivelComplejidad: 2])).save(flush:true)
	Siniestro.findById(8)?:(new Siniestro([ version: 1, cun: 123489, diatOA: DIAT.findById(4), diepOA: null, empleador: PersonaJuridica.findByRut('456678882'), esEnfermedadProfesional: false, fecha: new Date(), opa: OPA.findById(3)	, opaep: null				, relato: null, tipoPatologia: null, trabajador: PersonaNatural.findByRun('123456785')	, usuario: 'iarancibias', nivelComplejidad: 2])).save(flush:true)//para consulta de siniestro
	
}