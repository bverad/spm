import java.util.Date;

import cl.adexus.isl.spm.*

fixture {

	DIAT.findById(1)?:(new DIAT([version: 0, calificacionDenunciante: CalificacionDenunciante.findByCodigo('1'), categoriaOcupacion: CategoriaOcupacion.findByCodigo('1'), ciiuPrincipal: TipoActividadEconomica.findByCodigo('154120'), como: 'cajero',	direccionAccidenteComuna: Comuna.findByCodigo('13101'), direccionAccidenteNombreCalle: 'Miraflores', direccionEmpleadorComuna: Comuna.findByCodigo('13101'), direccionEmpleadorNombreCalle: 'Miraflores', direccionEmpleadorNumero: 383, direccionEmpleadorRestoDireccion: 'Piso 20', direccionEmpleadorTipoCalle: TipoCalle.findByCodigo('1'), direccionTrabajadorComuna: Comuna.findByCodigo('13101'), direccionTrabajadorNombreCalle: 'Jose Arrieta', direccionTrabajadorNumero: 9876, direccionTrabajadorRestoDireccion: 'Casa G o H', direccionTrabajadorTipoCalle: TipoCalle.findByCodigo('2'), duracionContrato: TipoDuracionContrato.findByCodigo('1'), empleador: PersonaJuridica.findByRut('456678882'), esAccidenteTrayecto: false, esTrabajoHabitual: true, fechaAccidente: new Date(), fechaEmision: new Date(), fechaIngresoEmpresa: new Date(), gravedad: CriterioGravedad.findByCodigo('1'), horaIngreso: new Date(), horaSalida: new Date(), lugarAccidente: 'en la calle', nTrabajadoresHombre: 20, nTrabajadoresMujer: 5, nacionalidadTrabajador: Nacion.findByCodigo('32'), profesionTrabajador: 'No se bien', propiedadEmpresa: TipoPropiedadEmpresa.findByCodigo('1'), que: 'Se cayo y se pego', siniestro: Siniestro.findById(1), telefonoEmpleador: 26861000, telefonoTrabajador: 2782477, tipoEmpresa: TipoEmpresa.findByCodigo('1'), tipoRemuneracion: TipoRemuneracion.findByCodigo('1'), trabajador: PersonaNatural.findByRun('123456785'), trabajoHabitualCual: 'sale a ver clientes'])).save(flush:true)
	DIAT.findById(2)?:(new DIAT([version: 0, calificacionDenunciante: CalificacionDenunciante.findByCodigo('1'), categoriaOcupacion: CategoriaOcupacion.findByCodigo('1'), ciiuPrincipal: TipoActividadEconomica.findByCodigo('154120'), como: 'regando',	direccionAccidenteComuna: Comuna.findByCodigo('13101'), direccionAccidenteNombreCalle: 'Miraflores', direccionEmpleadorComuna: Comuna.findByCodigo('13101'), direccionEmpleadorNombreCalle: 'Miraflores', direccionEmpleadorNumero: 383, direccionEmpleadorRestoDireccion: 'Piso 20', direccionEmpleadorTipoCalle: TipoCalle.findByCodigo('1'), direccionTrabajadorComuna: Comuna.findByCodigo('13101'), direccionTrabajadorNombreCalle: 'Jose Arrieta', direccionTrabajadorNumero: 9876, direccionTrabajadorRestoDireccion: 'Casa G o H', direccionTrabajadorTipoCalle: TipoCalle.findByCodigo('2'), duracionContrato: TipoDuracionContrato.findByCodigo('1'), empleador: PersonaJuridica.findByRut('456678882'), esAccidenteTrayecto: false, esTrabajoHabitual: true, fechaAccidente: new Date(), fechaEmision: new Date(), fechaIngresoEmpresa: new Date(), gravedad: CriterioGravedad.findByCodigo('1'), horaIngreso: new Date(), horaSalida: new Date(), lugarAccidente: 'en la calle', nTrabajadoresHombre: 20, nTrabajadoresMujer: 2, nacionalidadTrabajador: Nacion.findByCodigo('32'), profesionTrabajador: 'No se bien', propiedadEmpresa: TipoPropiedadEmpresa.findByCodigo('1'), que: 'Se cayo y se pego', siniestro: Siniestro.findById(1), telefonoEmpleador: 26861000, telefonoTrabajador: 2782477, tipoEmpresa: TipoEmpresa.findByCodigo('1'), tipoRemuneracion: TipoRemuneracion.findByCodigo('1'), trabajador: PersonaNatural.findByRun('34567875'), trabajoHabitualCual: 'sale a ver clientes'])).save(flush:true)
	DIAT.findById(3)?:(new DIAT([
		version: 1,
		xmlEnviado: "envio",
		xmlRecibido:"recibo",
		fechaEmision: new Date(), 
		empleador: PersonaJuridica.findByRut('456678882'), 
		ciiuEmpleador: TipoActividadEconomica.findByCodigo('154120'), 
		ciiuPrincipal: TipoActividadEconomica.findByCodigo('154120'),
		direccionEmpleadorTipoCalle: TipoCalle.findByCodigo('1'),
		direccionEmpleadorNombreCalle: 'Miraflores',
		direccionEmpleadorNumero: 383,
		direccionEmpleadorRestoDireccion: 'Piso 20',
		direccionEmpleadorComuna: Comuna.findByCodigo('13101'),
		telefonoEmpleador: 26861000,
		nTrabajadoresHombre: 20, 
		nTrabajadoresMujer: 5, 
		propiedadEmpresa: TipoPropiedadEmpresa.findByCodigo('1'),
		tipoEmpresa: TipoEmpresa.findByCodigo('1'),
		trabajador: PersonaNatural.findByRun('123456785'),
		nacionalidadTrabajador: Nacion.findByCodigo('32'),
		direccionTrabajadorTipoCalle: TipoCalle.findByCodigo('2'),
		direccionTrabajadorNombreCalle: 'Jose Arrieta',
		direccionTrabajadorNumero: 9876,
		direccionTrabajadorRestoDireccion: 'Casa G o H',
		direccionTrabajadorComuna: Comuna.findByCodigo('13101'),
		telefonoTrabajador: 2782477,
		etnia:null,
		otroPueblo:"nerd",
		profesionTrabajador: 'No se bien', 
		fechaIngresoEmpresa: new Date(),
		duracionContrato: TipoDuracionContrato.findByCodigo('1'),
		tipoRemuneracion: TipoRemuneracion.findByCodigo('1'),
		categoriaOcupacion: CategoriaOcupacion.findByCodigo('1'),
		
		fechaAccidente: new Date(), 
		horaIngreso: new Date(), 
		horaSalida: new Date(), 
		direccionAccidenteNombreCalle: 'Miraflores', 
		direccionAccidenteComuna: Comuna.findByCodigo('13101'), 
		que: 'Se cayo y se pego', 
		como: 'cajero',	
		lugarAccidente: 'en la calle', 
		trabajoHabitualCual: 'sale a ver clientes',
		esTrabajoHabitual: true, 
		gravedad: CriterioGravedad.findByCodigo('1'), 
		esAccidenteTrayecto: false, 
		tipoAccidenteTrayecto:null,
		medioPrueba:null,
		detallePrueba:null,
		
		calificacionDenunciante: CalificacionDenunciante.findByCodigo('1'), 
		denunciante: PersonaNatural.findByRun('123456785'),
		telefonoDenunciante: 2782477,
		
		siniestro: Siniestro.findById(7)
	])).save(flush:true)

	DIAT.findById(4)?:(new DIAT([version: 0, calificacionDenunciante: CalificacionDenunciante.findByCodigo('1'), categoriaOcupacion: CategoriaOcupacion.findByCodigo('1'), ciiuPrincipal: TipoActividadEconomica.findByCodigo('154120'), como: 'regando', denunciante: PersonaNatural.findByRun('123456785'), direccionAccidenteComuna: Comuna.findByCodigo('13101'), direccionAccidenteNombreCalle: 'Miraflores', direccionEmpleadorComuna: Comuna.findByCodigo('13101'), direccionEmpleadorNombreCalle: 'Miraflores', direccionEmpleadorNumero: 383, direccionEmpleadorRestoDireccion: 'Piso 20', direccionEmpleadorTipoCalle: TipoCalle.findByCodigo('1'), direccionTrabajadorComuna: Comuna.findByCodigo('13101'), direccionTrabajadorNombreCalle: 'Jose Arrieta', direccionTrabajadorNumero: 9876, direccionTrabajadorRestoDireccion: 'Casa G o H', direccionTrabajadorTipoCalle: TipoCalle.findByCodigo('2'), duracionContrato: TipoDuracionContrato.findByCodigo('1'), empleador: PersonaJuridica.findByRut('456678882'), esAccidenteTrayecto: false, esTrabajoHabitual: true, fechaAccidente: new Date(), fechaEmision: new Date(), fechaIngresoEmpresa: new Date(), gravedad: CriterioGravedad.findByCodigo('1'), horaIngreso: new Date(), horaSalida: new Date(), lugarAccidente: 'en la calle', nTrabajadoresHombre: 20, nTrabajadoresMujer: 2, nacionalidadTrabajador: Nacion.findByCodigo('32'), profesionTrabajador: 'No se bien', propiedadEmpresa: TipoPropiedadEmpresa.findByCodigo('1'), que: 'Se cayo y se pego', siniestro: Siniestro.findById(8), telefonoEmpleador: 26861000, telefonoTrabajador: 2782477, tipoEmpresa: TipoEmpresa.findByCodigo('1'), tipoRemuneracion: TipoRemuneracion.findByCodigo('1'), trabajador: PersonaNatural.findByRun('34567875'), trabajoHabitualCual: 'sale a ver clientes'])).save(flush:true)//para consulta de siniestro

}
