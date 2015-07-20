import cl.adexus.isl.spm.*

fixture {
	ActividadTrabajador.findByCodigo('01')?:(new ActividadTrabajador([codigo: '01', descripcion: 'Peoneta'])).save(flush:true)
	ActividadTrabajador.findByCodigo('02')?:(new ActividadTrabajador([codigo: '02', descripcion: 'Pescador Artesanal'])).save(flush:true)
	ActividadTrabajador.findByCodigo('03')?:(new ActividadTrabajador([codigo: '03', descripcion: 'Pirquinero'])).save(flush:true)
	ActividadTrabajador.findByCodigo('04')?:(new ActividadTrabajador([codigo: '04', descripcion: 'Trabajadora de Casa Particular'])).save(flush:true)
	ActividadTrabajador.findByCodigo('05')?:(new ActividadTrabajador([codigo: '05', descripcion: 'Trabajador Agrícola'])).save(flush:true)
	ActividadTrabajador.findByCodigo('06')?:(new ActividadTrabajador([codigo: '06', descripcion: 'Albañil'])).save(flush:true)
	ActividadTrabajador.findByCodigo('07')?:(new ActividadTrabajador([codigo: '07', descripcion: 'Rondín'])).save(flush:true)
	ActividadTrabajador.findByCodigo('08')?:(new ActividadTrabajador([codigo: '08', descripcion: 'Camarero'])).save(flush:true)
	ActividadTrabajador.findByCodigo('09')?:(new ActividadTrabajador([codigo: '09', descripcion: 'Junior'])).save(flush:true)
	ActividadTrabajador.findByCodigo('10')?:(new ActividadTrabajador([codigo: '10', descripcion: 'Mariscador'])).save(flush:true)
	ActividadTrabajador.findByCodigo('11')?:(new ActividadTrabajador([codigo: '11', descripcion: 'Suplementero'])).save(flush:true)
	ActividadTrabajador.findByCodigo('12')?:(new ActividadTrabajador([codigo: '12', descripcion: 'Maestro de la Construcción'])).save(flush:true)
	ActividadTrabajador.findByCodigo('13')?:(new ActividadTrabajador([codigo: '13', descripcion: 'Panadero'])).save(flush:true)
	ActividadTrabajador.findByCodigo('14')?:(new ActividadTrabajador([codigo: '14', descripcion: 'Tornero si tiene menos de 2 años de experiencia.'])).save(flush:true)
	ActividadTrabajador.findByCodigo('15')?:(new ActividadTrabajador([codigo: '15', descripcion: 'Electricista si tiene menos de 5 años de experiencia'])).save(flush:true)
	ActividadTrabajador.findByCodigo('16')?:(new ActividadTrabajador([codigo: '16', descripcion: 'Mecánico si tiene menos de 5 años de experiencia'])).save(flush:true)
	ActividadTrabajador.findByCodigo('17')?:(new ActividadTrabajador([codigo: '17', descripcion: 'Carpintero si tiene menos de 3 años de experiencia'])).save(flush:true)
	ActividadTrabajador.findByCodigo('18')?:(new ActividadTrabajador([codigo: '18', descripcion: 'Calderero si tiene menos de 5 años de experiencia'])).save(flush:true)
	ActividadTrabajador.findByCodigo('OE')?:(new ActividadTrabajador([codigo: 'OE', descripcion: 'Otro Empleado'])).save(flush:true)
	ActividadTrabajador.findByCodigo('OO')?:(new ActividadTrabajador([codigo: 'OO', descripcion: 'Otro Obrero'])).save(flush:true)
}