import cl.adexus.isl.spm.*

fixture{
	CalificacionDenunciante.findByCodigo('1')?:(new CalificacionDenunciante([codigo: '1', descripcion: 'Empleador'])).save(flush:true)
	CalificacionDenunciante.findByCodigo('2')?:(new CalificacionDenunciante([codigo: '2', descripcion: 'Trabajador'])).save(flush:true)
	CalificacionDenunciante.findByCodigo('3')?:(new CalificacionDenunciante([codigo: '3', descripcion: 'Familiar'])).save(flush:true)
	CalificacionDenunciante.findByCodigo('4')?:(new CalificacionDenunciante([codigo: '4', descripcion: 'Comité Paritario de Higiene y Seguridad'])).save(flush:true)
	CalificacionDenunciante.findByCodigo('5')?:(new CalificacionDenunciante([codigo: '5', descripcion: 'Médico tratante'])).save(flush:true)
	CalificacionDenunciante.findByCodigo('6')?:(new CalificacionDenunciante([codigo: '6', descripcion: 'Empresa usuaria'])).save(flush:true)
	CalificacionDenunciante.findByCodigo('7')?:(new CalificacionDenunciante([codigo: '7', descripcion: 'Organismo administrador'])).save(flush:true)
	CalificacionDenunciante.findByCodigo('8')?:(new CalificacionDenunciante([codigo: '8', descripcion: 'Otro'])).save(flush:true)
}