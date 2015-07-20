package cl.adexus.isl.spm

import java.util.Date;

@gorm.AuditStamp
class DIEP {

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
		
	//ZONA E (Seccion C)
	String sintoma //XML: sintoma --
	Date fechaSintoma //XML: fecha_sintoma
	String parteCuerpo //XML: parte_cuerpo
	Boolean esAntecedentePrevio //XML: antecedente_previo
	Boolean esAntecedenteCompanero //XML: antecedente_companero
	String descripcionTrabajo //XML: direccion_trabajo (no esta malo, es asi )
	String puestoTrabajo //XML puesto_trabajo
	String agenteSospechoso //XML: agente_sospechoso
	Date fechaAgente //XML: fecha_agente
	SistemaSalud sistemaSalud //XML: sistema_comun
	
	//ZONA F (Seccion D)
	PersonaNatural denunciante
	CalificacionDenunciante calificacionDenunciante
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
		
		sintoma nullable: true
		fechaSintoma nullable: true, fechaHoy: true
		parteCuerpo nullable: true, maxSize: 255
		esAntecedentePrevio nullable: true
		esAntecedenteCompanero nullable: true
		descripcionTrabajo nullable: true, maxSize: 255
		puestoTrabajo nullable: true, maxSize: 255
		agenteSospechoso nullable: true, maxSize: 255
		fechaAgente nullable: true, fechaHoy: true
		sistemaSalud nullable: true	
		
		denunciante nullable: true
		telefonoDenunciante nullable: true, maxSize: 255
		calificacionDenunciante nullable: true
		
		codigoActividadEmpresa nullable: true, maxSize: 255
    }
	
	static mapping = {
		trabajador lazy: false
		empleador lazy: false
		denunciante lazy: false
		empleador lazy: false
		calificacionDenunciante lazy:false
		direccionTrabajadorTipoCalle lazy:false
		direccionTrabajadorComuna lazy:false
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
		puestoTrabajo lazy:false
		xmlEnviado type: "text"
		xmlRecibido type: "text"
		
		sintoma type: "text"
	}
}
