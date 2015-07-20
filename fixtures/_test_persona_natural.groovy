import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {
	
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
		PersonaNatural.findByRun('123456785')?:(new PersonaNatural(
			[
				run: '123456785',apellidoMaterno: 'Soto',apellidoPaterno: 'Pérez',
				fechaNacimiento: df.parse('28/01/1986'),nombre: 'Pedro', sexo: 'M'
			]
			)).save(flush:true)
		
		PersonaNatural.findByRun('34567875')?:(new PersonaNatural(
			[
				run: '34567875', apellidoMaterno: 'Pérez',	apellidoPaterno: 'Fernandez',
				fechaNacimiento: df.parse('08/12/1980'), nombre: 'María', sexo: 'F'
			]
			)).save(flush:true)

		PersonaNatural.findByRun('45678970')?:(new PersonaNatural(
			[
				run: '45678970', apellidoMaterno: 'Bastias',	apellidoPaterno: 'Laurido',
				fechaNacimiento: df.parse('08/09/1946'), nombre: 'Daniela', sexo: 'F'
			]
			)).save(flush:true)
	
		PersonaNatural.findByRun('154567844')?:(new PersonaNatural(
			[
				run: '154567844', apellidoMaterno: 'Berrios',	apellidoPaterno: 'Diaz',
				fechaNacimiento: df.parse('08/12/1980'), nombre: 'Gabriel', sexo: 'M'
			]
			)).save(flush:true)
		
		PersonaNatural.findByRun('34567891')?:(new PersonaNatural(
			[
				run: '34567891', apellidoMaterno: 'Quiñones',	apellidoPaterno: 'Arriagada',
				fechaNacimiento: df.parse('08/12/1980'), nombre: 'Francisca', sexo: 'F'
			]
			)).save(flush:true)
			
		PersonaNatural.findByRun('56781234')?:(new PersonaNatural(
			[
				run: '56781234', apellidoMaterno: 'Saravia',	apellidoPaterno: 'Hidalgo',
				fechaNacimiento: df.parse('08/12/1980'), nombre: 'Claudio', sexo: 'M'
			]
			)).save(flush:true)

		PersonaNatural.findByRun('56565655')?:(new PersonaNatural(
			[
				run: '56565655', apellidoMaterno: 'Ayala',	apellidoPaterno: 'Muñoz',
				fechaNacimiento: df.parse('08/12/1980'), nombre: 'Daniel', sexo: 'M'
			]
			)).save(flush:true)
			
}