import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {

	InformeOPA.findById(1)?:(new InformeOPA([ version: 1, 
											siniestro: Siniestro.findById(1), 
											paciente: PersonaNatural.findByRun('123456785'),
											medico: PersonaNatural.findByRun('34567875'),
											fechaAtencion: new Date(),
											horaAtencion: new Date(),
											fechaProximoControl: new Date() + 3,										
											altaMedica: false,
											reposoLaboral: true,
											comentarioAtencion: "Amputado de una pierna, requiere dosis de morfina extremas hell yeah!"])
	).save(flush:true)
	
	InformeOPA.findById(2)?:(new InformeOPA([ version: 1,
											siniestro: Siniestro.findById(2),
											paciente: PersonaNatural.findByRun('45678970'),
											medico: PersonaNatural.findByRun('34567875'),
											fechaAtencion: new Date(),
											horaAtencion: new Date(),
											fechaProximoControl: new Date() + 4,
											altaMedica: false,
											reposoLaboral: false,
											comentarioAtencion: "Presunta peste negra, aislar"])
	).save(flush:true)

	InformeOPA.findById(3)?:(new InformeOPA([ version: 1,
											siniestro: Siniestro.findById(3),
											paciente: PersonaNatural.findByRun('34567891'),
											medico: PersonaNatural.findByRun('56565655'),
											fechaAtencion: new Date(),
											horaAtencion: new Date(),
											fechaProximoControl: null,
											altaMedica: true,
											reposoLaboral: false,
											comentarioAtencion: "Rasgos psicopaticos extremos, se recomienda dejar el catolisismo"])
	).save(flush:true)
	
}