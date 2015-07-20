import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {

	SDAAT.findById(1)?:(new SDAAT([
		version: 1,
		inicioSolicitud: new Date(),
		finSolicitud: new Date(),
		salida: "01-01-2013",
		usuario: "wañaño",
		fechaSiniestro: new Date(),
		trabajador: PersonaNatural.findByRun('123456785'),
		empleador: PersonaJuridica.findByRut('567891232'),
		codigoActividadEmpresa: 011111,
		siniestro: Siniestro.findById(1),
		CuestionarioObrero: null,
		relato: "Cosas que pasan en la vida",
		esAccidenteTrayecto: false,
		cuestionarioOrigenTrayecto: null,
		cuestionarioOrigenTrabajo: null,
		cuestionarioComplejidad: CuestionarioComplejidad.findById(1),
		complejidadCalculada: 1,
		denunciante:PersonaNatural.findByRun('123456785'),
		tipoDenunciante:null,
		diat:DIAT.findById(1),
		opa: OPA.findById(1)])).save(flush:true)
	
}