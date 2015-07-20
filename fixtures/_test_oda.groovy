import cl.adexus.isl.spm.*

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date;

fixture {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	ODA.findById(1)?:(new ODA(
			[
				version:	0,
				centroAtencion:	CentroSalud.findById(1),
				duracionDias:	60,
				fechaCreacion:	new Date(), 
				inicioVigencia:	new Date(),
				descripcionEvento: 'Operacion de algo',
				fechaEvento: new Date(),
				siniestro: Siniestro.findById(1),
				terminoVigencia: new Date(),
				tipoODA: TipoODA.findByCodigo('1'),
				direccionTrabajador: 'PLAPALPALA',
				comunaTrabajador: Comuna.findByCodigo('01101'),
				telefonoTrabajador: 123123L,
				emailTrabajador: 'asdasd@asdasd.cl'
			]
			)).save(flush:true)
		
	ODA.findById(2)?:(new ODA(
				[
				version:	0,
				centroAtencion:	CentroSalud.findById(1),
				duracionDias:	60,
				fechaCreacion:	df.parse('2013-01-01 16:31:14'),
				inicioVigencia:	df.parse('2013-01-01 16:31:14'),
				descripcionEvento: 'Operacion exotica',
				fechaEvento: df.parse('2013-01-02 16:31:14'),
				siniestro: Siniestro.findById(4),
				terminoVigencia: new Date(),
				tipoODA: TipoODA.findByCodigo('1'),
				direccionTrabajador: 'PLAPALPALA',
				comunaTrabajador: Comuna.findByCodigo('01101'),
				telefonoTrabajador: 123123L,
				emailTrabajador: 'asdasd@asdasd.cl'
			]
			)).save(flush:true)

			
		ODA.findById(3)?:(new ODA(//para consulta de siniestro
			[
				version:	0,
				centroAtencion:	CentroSalud.findById(1),
				duracionDias:	60,
				fechaCreacion:	df.parse('2013-01-01 16:31:14'),
				inicioVigencia:	df.parse('2013-01-01 16:31:14'),
				descripcionEvento: 'Operacion exotica',
				fechaEvento: df.parse('2013-05-10 16:31:14'),
				siniestro: Siniestro.findById(8),
				terminoVigencia: new Date(),
				tipoODA: TipoODA.findByCodigo('1'),
				direccionTrabajador: 'PLAPALPALA',
				comunaTrabajador: Comuna.findByCodigo('13101'),
				telefonoTrabajador: 123123L,
				emailTrabajador: 'asdasd@asdasd.cl'
			]
			)).save(flush:true)
	
}