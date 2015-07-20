package cl.adexus.isl.spm

class ShiroRole {
	String id_rol
    String name

    static hasMany = [ users: ShiroUser]
	static transients = [ "permisos" ]
    
	static belongsTo =  ShiroUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
	
	static mapping = {
		id generator: 'assigned', name: "id_rol"
		datasource 'auth'
		//table "shiro_role"
		table "rol"
		name column: "nombre_rol", sqlType: "nvarchar"
		id_rol sqlType: "integer"
		version false
		cache usage: 'read-only'
		
		users joinTable: [name: "usuario_sistema_rol", key: 'id_rol' ]
		
	}

	def getPermisos() {
		/*
		 * Ejecutivo de Plataforma. Area 2
		 * id_rol = 1
		 */
		if ( name.equalsIgnoreCase("ejecutivo_plataforma") ) {
			return  ["Nav:*",
						"SDAAT_calorigen:*", //Solicitud de Atencion AT
						"SDAAT_clatrab:*",
						"SDAAT_ident:*", 
						"SDAAT_complej:*", 
						"SDAAT_diat:*", 
						"SDAAT_ident:*", 
						"SDAAT_opa:*", 
						"SDAEP_clatrab:*", 	//Solicitud de Atencion EP
						"SDAEP_diep:*", 
						"SDAEP_ident:*", 
						"SDAEP_opaep:*", 
						"SDAEP_previo:*", 
						"DIATEPOA:*",		//DIAT-OA y DIEP-OA
					]
		}
		
		/*
		 * Empleador 
		 * id_rol = 3
		 */
		if ( name.equalsIgnoreCase("empleador") ) {
			return  ["Nav:*",
						"DIATWEB:*", 		//DIAT WEB
					]
		}
		
		/*
		 * Prestador
		 * id_rol = 2
		 */
		if ( name.equalsIgnoreCase("prestador") ) {
			return  ["Nav:*",
						"SDAAT_calorigen:*", //Solicitud de Atencion AT
						"SDAAT_clatrab:*",
						"SDAAT_ident:*", 
						"SDAAT_complej:*", 
						"SDAAT_diat:*", 
						"SDAAT_ident:*", 
						"SDAAT_opa:*",
					]
		}
		
		/*
		 * Usuario BPM (3-Calificacion y 5-Cuentas Medicas)
		 * id_rol = 10
		 */
		if ( name.equalsIgnoreCase("user") ) {
			return  ["Nav:*",
						"Inbox:*",			//Bandeja de Entrada
					]
		}
		
		/*
		 * Digitador de Cuenta Medica. Area 5
		 * id_rol = 4
		 */
		if ( name.equalsIgnoreCase("digitador_cm") ) {
			return  ["Nav:*",
						"CM_ingreso:index",	//Ingreso de Cuenta Medica
						"CM_ingreso:dp01",
						"CM_ingreso:datosPrestadorJSON",
						"CM_ingreso:datosTrabajadorJSON",
						"CM_ingreso:postDp01",
						"CM_ingreso:dp03",	//Carga Archivo detalle CM
						"CM_ingreso:postDp03",
					]
		}
		
		/*
		 * Digitador de Detalle Cuenta Medica. Area 5
		 * id_rol = 5
		 */
		if ( name.equalsIgnoreCase("digitador_detalle_cm") ) {
			return  ["Nav:*",
						"CM_ingreso:dp02",	//Ingreso Detalle Cuenta Medica
						"CM_ingreso:agregaDetalleCuentaMedica",
						"CM_ingreso:eliminaDetalleCuentaMedica",
						"CM_ingreso:postDp02",
					]
		}
		
		/*
		 * Analista Cuenta Medica. Area 5
		 * id_rol = 6
		 */
		if ( name.equalsIgnoreCase("analista_cm") ) {
			return  ["Nav:*",
						"CM_revision:index",	//Revision Cuenta Medica
						"CM_revision:dp01",
						"CM_revision:guardaConsultaJSON",
						"CM_revision:obtenConsultaJSON",
						"CM_revision:postDp01",
						"CM_revision:dp04",		//Revision Final CM
						"CM_revision:cu04a",
						"CM_revision:cu04am",
						"CM_revision:cu04r",
						"CM_revision:postDp04",
						"CM_revision:rechazoDp04",
					]
		}
		
		/*
		 * Especialista Medico para Resolver Consultas de Cuenta Medica
		 * id_rol = 8
		 */
		if ( name.equalsIgnoreCase("especialista_medico_cm") ) {
			return  ["Nav:*",
						"CM_revision:dp02",		//Consulta Pertinencia Medica
						"CM_revision:obtenConsultaJSON",
						"CM_revision:guardaRespuestaJSON",
						"CM_revision:postDp02",
					]
		}
		
		/*
		 * Especialista Convenio para Resolver Consultas de Cuenta Medica
		 * id_rol = 7
		 */
		if ( name.equalsIgnoreCase("especialista_convenio_cm") ) {
			return  ["Nav:*",
						"CM_revision:dp03",		//Consulta Pertinencia Arancelaria
						"CM_revision:obtenConsultaJSON",
						"CM_revision:guardaRespuestaJSON",
						"CM_revision:postDp03",
					]
		}
		
		
		/*
		 * Ingresador y Revisor de Factura. Area 5
		 * id_rol = 9 
		 */
		if ( name.equalsIgnoreCase("revisor_factura") ) {
			return  ["Nav:*",
						"FACT_ingreso:*",
					]
		}	
		
		/*
		 * Administrador de Convenio. Area 1
		 * id_rol = 11
		 */
		if ( name.equalsIgnoreCase("administrador_convenio") ) {
			return  ["Nav:*",
						"Aranceles:*",
						"Prestador:*",
						"Convenio:*",
						"CentroSalud:*"
					]
		}
		
		/*
		 * Backend. Tareas Administrativas
		 * id_rol = 12
		 */
		if ( name.equalsIgnoreCase("backend") ) {
			return  ["Nav:*",
					"Backend:*",
					"Usuario:*",
					"Reportes:*",
					"reportGenerator:*",
					"TEST:*",
					"tipoCentroSalud:*",
					"tipoConceptoReembolso:*",
					"tipoConvenio:*",
					"tipoCuenta:*",
					"tipoPaquete:*",
					"tipoPrestador:*",
					"tipoPropiedadEmpresa:*",
					"banco:*",
					"estructuraJuridica:*"					
					]
		}
		
		
		/*
		 * Analista Calificacion Nivel 1. Area 3
		 * id_rol = 20
		 */
		if ( name.equalsIgnoreCase("analista_calificacion_1") ) {
			return  ["Nav:*",
						"CalOrigenAT:*",	//TODO: Solo debiera ser "Revisa, califica y codifica." 
					]						//TODO: Identificar cuales exactamente
		}
		
		/*
		 * Analista Calificacion Nivel 2. Area 3
		 * id_rol = 21
		 */
		if ( name.equalsIgnoreCase("analista_calificacion_2") ) {
			return  ["Nav:*",
						"CalOrigenAT:*",	//TODO: No son todos. 
						"CalOrigenEP:*",
						"SolAnteAdic:*",		
					]	
		}

		/*
		 * Digitador de Informe OPA. Area 3
		 * id_rol = 22
		 */
		if ( name.equalsIgnoreCase("digitador_informe_opa") ) {
			return  ["Nav:*",
						"InformeOPA:*",	
					]
		}

		/*
		 * Ejecutivo de Seguimiento. Area 4
		 * id_rol = 19
		 */
		if ( name.equalsIgnoreCase("ejecutivo_seguimiento") ) {
			return  ["Nav:*",
						"Seguimiento:*",
						"SolAnteAdic:*",
						"calOrigenAT:*", //Para los diagnosticos
						"calOrigenEP:*",
					]
		}
		
		/*
		 * Responsable de Solicitudes de Antecedentes Area Calificacion (3)
		 * Para resolver solicitudes de Seguimiento
		 * id_rol = 23
		 */
		if ( name.equalsIgnoreCase("profesional_calificacion") ) {
			return  ["Nav:*",
						"SolAnteAdic:*",
					]
		}
		
		/*
		 * Responsable de Solicitudes de Antecedentes Area Prevencion
		 * Para resolver solicitudes de Calificacion
		 * id_rol = 24
		 */
		if ( name.equalsIgnoreCase("profesional_prevencion") ) {
			return  ["Nav:*",
						"SolAnteAdic:*",
					]
		}
		
		/*
		 * Responsable de Solicitudes de Antecedentes Area Seguimiento (4)
		 * Para resolver solicitudes de Calificacion
		 * id_rol = 25
		 */
		if ( name.equalsIgnoreCase("profesional_seguimiento") ) {
			return  ["Nav:*",
						"SolAnteAdic:*",
					]
		}
		
		/*
		 * Ingresador de Solicitudes de Reembolso+
		 * id_rol = 26
		 */
		if ( name.equalsIgnoreCase("digitador_reembolso") ) {
			return  ["Nav:*",
						"OTP_ingreso:*",
						"OTP_revision:verFormularioReembolso"
					]
		}
		
		/*
		 * Analista de Solicitudes de Reembolso (de Seguimiento)
		 * id_rol = 27
		 */
		if ( name.equalsIgnoreCase("analista_reembolso") ) {
			return  ["Nav:*",
						"OTP_ingreso:listDp07",
						"OTP_ingreso:dp07",  
						"OTP_ingreso:cu07",
						"OTP_ingreso:verSiniestro",
						"OTP_ingreso:verDetalleAdjunto",
						"OTP_ingreso:descargaDocumento",
						"OTP_ingreso:index",
						"OTP_ingreso:dp01",
						"OTP_revision:verFormularioReembolso"
					]
		}
		
		/*
		 * Revisor de Pagos de Solicitudes de Reembolso (de 5)
		 * id_rol = 28
		 */
		if ( name.equalsIgnoreCase("pagador_reembolso") ) {
			return  ["Nav:*",
						"OTP_revision:*",
					]
		}
		
		/*
		 * Ingresador de 77bis (de ??)
		 * id_rol = 29
		 */
		if ( name.equalsIgnoreCase("ingresador_77bis") ) {
			return  ["Nav:*",
						"BIS_ingreso:*",
						"BIS_revision:*",
						"Siniestro:*",		//Consulta Siniestro,
						"Denuncia:*",		//Ver denuncias
					]
		}
		
		/*
		 * Regularizador de 77bis (de ??)
		 * id_rol = 36
		 */
		if ( name.equalsIgnoreCase("regularizador_77bis") ) {
			return  ["Nav:*",
						"SDAAT_calorigen:*", //Solicitud de Atencion AT
						"SDAAT_clatrab:*",
						"SDAAT_ident:*", 
						"SDAAT_complej:*", 
						"SDAAT_diat:*", 
						"SDAAT_ident:*", 
						"SDAAT_opa:*", 
						"SDAEP_clatrab:*", 	//Solicitud de Atencion EP
						"SDAEP_diep:*", 
						"SDAEP_ident:*", 
						"SDAEP_opaep:*", 
						"SDAEP_previo:*", 
						"DIATEPOA:*",		//DIAT-OA y DIEP-OA
						"BIS_ingreso:*",
					]
		}
		
		/*
		 * analista de 77bis (de ??)
		 * id_rol = 30
		 */
		if ( name.equalsIgnoreCase("analista_77bis") ) {
			return  ["Nav:*",
						"BIS_ingreso:*",
						"BIS_revision:*",
					]
		}
		
		/*
		 * Calculador de pagos de 77bis (de ??)
		 * id_rol = 31
		 */
		if ( name.equalsIgnoreCase("calculador_77bis") ) {
			return  ["Nav:*",
						"BIS_ingreso:*",
						"BIS_revision:*",
					]
		}
		
		/*
		 * Ingresador de reingreso (de 2)
		 * id_rol = 32
		 */
		if ( name.equalsIgnoreCase("ingresador_reingreso") ) {
			return  ["Nav:*",
						"Reingreso:*",
					]
		}

		/*
		 * Evaluador de reingreso (de 4)
		 * id_rol = 33
		 */
		if ( name.equalsIgnoreCase("evaluador_reingreso") ) {
			return  ["Nav:*",
						"Reingreso:*",
					]
		}
		
		/*
		 * Consulta Siniestro
		 * id_rol = 34
		 */
		if ( name.equalsIgnoreCase("consulta_siniestro") ) {
			return  ["Nav:*",
						"Siniestro:*",		//Consulta Siniestro,
						"Denuncia:*",		//Ver denuncias
					]
		}
		
		/*
		 * Consulta Siniestro
		 * id_rol = 35
		 */
		if ( name.equalsIgnoreCase("administrativo_carga") ) {
			return  ["Nav:*",
						"Seguimiento:dp15",		//Carga de Reposos y Altas
						"Seguimiento:postDp15",		//
					]
		}
		
		/*
		 * Consulta Siniestro
		 * id_rol = 37
		 */
		if ( name.equalsIgnoreCase("ejecutivo_reporte_rp1") ) {
			return  ["Nav:*",
						"Reportes:*",
						"reportGenerator:*",
					]
		}

		/*
		 * Consulta Siniestro
		 * id_rol = 38
		 */
		if ( name.equalsIgnoreCase("ejecutivo_reporte_rp2") ) {
			return  ["Nav:*",
						"Reportes:*",
						"reportGenerator:*",
					]
		}

		/*
		 * Consulta Siniestro
		 * id_rol = 39
		 */
		if ( name.equalsIgnoreCase("ejecutivo_reporte_rp3") ) {
			return  ["Nav:*",
						"Reportes:*",
						"reportGenerator:*",
					]
		}

		/*
		 * Consulta Siniestro
		 * id_rol = 40
		 */
		if ( name.equalsIgnoreCase("ejecutivo_reporte_rp4") ) {
			return  ["Nav:*",
						"Reportes:*",
						"reportGenerator:*",
					]
		}

		/*
		 * Consulta Siniestro
		 * id_rol = 41
		 */
		if ( name.equalsIgnoreCase("ejecutivo_reporte_rp5") ) {
			return  ["Nav:*",
						"Reportes:*",
						"reportGenerator:*",
					]
		}

	 }
	
 }


