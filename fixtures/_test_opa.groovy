import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	OPA.findById(1)?:(new OPA(
			[
				version:	0,
				centroAtencion:	CentroSalud.findById(1),
				comunaTrabajador:	null,
				direccionTrabajador:	null,
				duracionDias:	60,
				fechaCreacion:	new Date(), 
				inicioVigencia:	new Date(),
				telefonoTrabajador:	null,
				usuarioEmisor:	'user123',
				siniestro: Siniestro.findById(1)
			]
			)).save(flush:true)
		
	OPA.findById(2)?:(new OPA(
				[
					version:	0,
					centroAtencion:	CentroSalud.findById(1),
					comunaTrabajador:	null,
					direccionTrabajador:	null,
					duracionDias:	60,
					fechaCreacion:	df.parse('2013-01-01 16:31:14'),
					inicioVigencia:	df.parse('2013-01-01 16:31:14'),
					usuarioEmisor:	'user123',
					siniestro: Siniestro.findById(4)
				]
				)).save(flush:true)
				
	OPA.findById(3)?:(new OPA(//para consulta de siniestro
				[
					version:	0,
					centroAtencion:	CentroSalud.findById(1),
					comunaTrabajador:	null,
					direccionTrabajador:	null,
					duracionDias:	60,
					fechaCreacion:	new Date(),
					inicioVigencia:	new Date(),
					telefonoTrabajador:	null,
					usuarioEmisor:	'iarancibias',
					siniestro: Siniestro.findById(8)
				]
				)).save(flush:true)

			
}