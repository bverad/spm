import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {

	Bis.findById(1)?:(new Bis([ version: 1, 
								emisor: PersonaJuridica.findById(1), 
								runTrabajador: "123456785",
								rutEmpleador: "456678882",
								dictamen: false,
								numeroDictamen: "F234"
								numeroDictamen: null,
								fechaDictamen: new Date(),
								tipoSiniestro: TipoEventoSiniestro.findById(1),
								fechaRecepcion: new Date(),
								fechaSiniestro: new Date(),
								montoSolicitado: 60000,
								montoAprobado: null,
								ufAprobado: null,
								comentarios: "Comentarios de ingreso?"])
	).save(flush:true)
	
}