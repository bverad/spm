package cl.adexus.isl.spm

import cl.adexus.isl.spm.domain.DIATSuseso;

@gorm.AuditStamp
class DIAT {
	
	String xmlEnviado
	String xmlRecibido
	
	//ZONA A
	// id XML: folio
	Date fechaEmision //XML: fecha_emision
	//Siniestro siniestro //Reemplazado por un belongsTo
	//siniestro.id XML: codigo_caso
	
	
	//ZONA B (Seccion A)
	PersonaJuridica empleador 
	//empleador_rut XML:rut_empleador
	//empleador_razonSocial XML: nombre_empleador, 
	TipoActividadEconomica ciiuEmpleador //XML: ciiu_empleador, texto 
	TipoActividadEconomica ciiuPrincipal //XML: ciiu_secundario, texto
	TipoCalle direccionEmpleadorTipoCalle //XML: direccion_empleador.tipo_calle
	String direccionEmpleadorNombreCalle //XML: direccion_empleador.nombre_calle
	Integer direccionEmpleadorNumero //XML: direccion_empleador.numero
	String direccionEmpleadorRestoDireccion //XML: direccion_empleador.resto_direccion
	Comuna direccionEmpleadorComuna //XML: direccion_empleador.comuna
	Integer telefonoEmpleador //XML: telefono_empleador.numero
	Integer nTrabajadoresHombre //XML: n_trabajadores_hombre
	Integer nTrabajadoresMujer //XML: n_trabajadores_mujer
	TipoPropiedadEmpresa propiedadEmpresa //XML: propiedad_empresa
	TipoEmpresa tipoEmpresa //XML: tipo_empresa
	
	//ZONA C (Seccion B)
	PersonaNatural trabajador
	//trabajador_nombre XML: trabajador.nombres
	//trabajador_apellidoPaterno XML: trabajador.apellido_paterno
	//trabajador_apellidoMaterno XML: trabajador.apellido_materno
	//trabajador_sexo XML: trabajador.sexo
	//trabajador_fechaNacimiento XML: trabajador.fecha_nacimiento
	Nacion nacionalidadTrabajador
	TipoCalle direccionTrabajadorTipoCalle //XML: direccion_trabajador.tipo_calle
	String direccionTrabajadorNombreCalle //XML: direccion_trabajador.nombre_calle
	Integer direccionTrabajadorNumero //XML: direccion_trabajador.numero
	String direccionTrabajadorRestoDireccion //XML: direccion_trabajador.resto_direccion
	Comuna direccionTrabajadorComuna //XML: direccion_trabajador.comuna
	Integer telefonoTrabajador //XML: telefono_trabajador.numero
	Etnia etnia //XML: codigo_etnia
	String otroPueblo
	String profesionTrabajador //XML: profesion_trabajador
	Date fechaIngresoEmpresa //XML: fecha_ingreso
	TipoDuracionContrato duracionContrato //XML: duracion_contrato
	TipoRemuneracion tipoRemuneracion //XML: tipo_remuneracion
	CategoriaOcupacion categoriaOcupacion //XML: categoria_ocupacion
	SistemaSalud sistemaSalud //XML: sistema_comun
	
	//ZONA D (Seccion C)
	Date fechaAccidente //XML: fecha_accidente
	Date horaIngreso //XML: hora_ingreso
	Date horaSalida //XML: hora_salida
	String direccionAccidenteNombreCalle //XML: direccion_accidente.nombre_calle
	Comuna direccionAccidenteComuna //XML: direccion_accidente.comuna
	String que //XML: que
	String como //XML: como --
	String lugarAccidente //XML: lugar_accidente
	String trabajoHabitualCual //XML: trabajo_habitual_cual
	Boolean esTrabajoHabitual //XML: trabajo_habitual
	CriterioGravedad gravedad //XML: gravedad
	Boolean esAccidenteTrayecto //XML: tipo_accidente (1:trabajo, 2:trayecto)
	TipoAccidenteTrayecto tipoAccidenteTrayecto //XML: tipo_accidente_trayecto
	TipoMedioPruebaAccidente medioPrueba //XML: medio_prueba
	String detallePrueba //XML: detalle_prueba
	
	//ZONA E (Seccion D)
	CalificacionDenunciante calificacionDenunciante
	PersonaNatural denunciante
	String telefonoDenunciante
	
	String codigoActividadEmpresa
	
	static belongsTo = [siniestro: Siniestro]
	
    static constraints = {
		xmlEnviado nullable: true
		xmlRecibido nullable: true
		siniestro nullable: true
		fechaEmision nullable: true
		
		empleador nullable: true
		ciiuEmpleador nullable: true
		ciiuPrincipal nullable: true
		direccionEmpleadorTipoCalle nullable: true
		direccionEmpleadorNombreCalle nullable: true, maxSize: 255
		direccionEmpleadorNumero nullable: true
		direccionEmpleadorRestoDireccion nullable: true, maxSize: 255
		direccionEmpleadorComuna nullable: true
		telefonoEmpleador nullable: true
		nTrabajadoresHombre nullable: true
		nTrabajadoresMujer nullable: true
		propiedadEmpresa nullable: true
		tipoEmpresa nullable: true
				
		trabajador nullable: true
		nacionalidadTrabajador nullable: true
		direccionTrabajadorTipoCalle nullable: true
		direccionTrabajadorNombreCalle nullable: true, maxSize: 255
		direccionTrabajadorNumero nullable: true
		direccionTrabajadorRestoDireccion nullable: true, maxSize: 255
		direccionTrabajadorComuna nullable: true
		telefonoTrabajador nullable: true
		etnia nullable: true
		otroPueblo nullable: true, maxSize: 255
		profesionTrabajador nullable: true, maxSize: 255
		fechaIngresoEmpresa nullable: true, fechaHoy: new Date()
		duracionContrato nullable: true
		tipoRemuneracion nullable: true
		categoriaOcupacion nullable: true
		sistemaSalud nullable: true
		
		fechaAccidente nullable: true
		horaIngreso nullable: true
		horaSalida nullable: true
		direccionAccidenteNombreCalle nullable: true, maxSize: 255
		direccionAccidenteComuna nullable: true
		que nullable: true, maxSize: 255
		como nullable: true, maxSize: 255
		lugarAccidente nullable: true, maxSize: 255
		trabajoHabitualCual nullable: true, maxSize: 255
		esTrabajoHabitual nullable: true
		gravedad nullable: true
		esAccidenteTrayecto nullable: true
		tipoAccidenteTrayecto nullable: true
		medioPrueba nullable: true
		detallePrueba nullable: true, maxSize: 255
		
		calificacionDenunciante nullable: true
		denunciante nullable: true
		telefonoDenunciante nullable: true, maxSize: 255
		
		codigoActividadEmpresa nullable: true, maxSize: 255
		
    }
	
	static mapping = {
		empleador lazy: false
		direccionEmpleadorTipoCalle lazy: false
		direccionEmpleadorComuna lazy: false
		propiedadEmpresa lazy: false
		tipoEmpresa lazy: false
		ciiuEmpleador lazy: false 
		ciiuPrincipal lazy: false
		trabajador lazy: false
		nacionalidadTrabajador lazy: false
		direccionTrabajadorTipoCalle lazy: false
		direccionTrabajadorComuna lazy: false
		etnia lazy: false
		duracionContrato lazy: false
		tipoRemuneracion lazy: false
		categoriaOcupacion lazy: false
		direccionAccidenteComuna lazy: false
		gravedad lazy: false
		tipoAccidenteTrayecto lazy: false
		medioPrueba lazy: false
		calificacionDenunciante lazy:false
		denunciante lazy: false
		
		xmlEnviado type: "text"
		xmlRecibido type: "text"
		
		como type: "text"
		
	}
}
