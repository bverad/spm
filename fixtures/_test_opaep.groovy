import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	OPAEP.findById(1)?:(new OPAEP(
			[
				version:	0,
				centroAtencion:	CentroSalud.findById(1),
				duracionDias:	60,
				fechaCreacion:	new Date(),
				inicioVigencia:	new Date(),
				usuarioEmisor:	'user123',
				siniestro: Siniestro.findById(3)
			]
			)).save(flush:true)

	OPAEP.findById(2)?:(new OPAEP(
				[
					version:	0,
					centroAtencion:	CentroSalud.findById(1),
					duracionDias:	60,
					fechaCreacion:	df.parse('2013-01-01 16:31:14'),
					inicioVigencia:	df.parse('2013-01-01 16:31:14'),
					usuarioEmisor:	'user123',
					siniestro: Siniestro.findById(5)
				]
				)).save(flush:true)
			
}