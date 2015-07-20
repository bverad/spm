package cl.adexus.isl.spm.helpers

class TaskHelper {
	
	def static taskDefs=[
			//CM_ingreso 
			'cl.isl.spm.cm.ingreso.digitarDetalleCM'  		: [name:'Digitar Detalle de Cuenta Medica', 
								   		   form: [controller: 'CM_ingreso', action:'dp02'] ],
			'cl.isl.spm.cm.ingreso.SeleccionarArchivo'        : [name:'Ingresar Archivo de Detalle de Cuenta Medica', 
				                           form: [controller: 'CM_ingreso', action:'dp03'] ],
			//CM_revision						   
			'cl.isl.spm.cm.revision.analisisPertinenciaMedica'	: [name:'Analisis de Cuenta Médica', 
				                   		   form: [controller: 'CM_revision', action:'dp01'] ],
			'cl.isl.spm.cm.revision.respondeSolicitudMedica'	: [name:'Responder Consulta Médica',
										   form: [controller: 'CM_revision', action:'dp02'] ],
			'cl.isl.spm.cm.revision.respondeSolicitudConvenio'	: [name:'Responder Consulta sobre Convenio',
										   form: [controller: 'CM_revision', action:'dp03'] ],
  		    'cl.isl.spm.cm.revision.definirDetalleFinal'		: [name:'Definir Cuenta Médica Final',
										   form: [controller: 'CM_revision', action:'dp04'] ],
			//FACT_ingreso						   
 		    'cl.isl.spm.cm.fact-ingreso.SolicitarNCND'		        : [name:'Solicitar Nota Crédito / Factura por Diferencia',
										   form: [controller: 'FACT_ingreso', action:'dp02'] ],
		    'cl.isl.spm.cm.fact-ingreso.revisarNCND'		        : [name:'Ingresar Nota Crédito / Factura por Diferencia',
									       form: [controller: 'FACT_ingreso', action:'dp03'] ],
		   
		   //CalOrigenAT
		   'cl.isl.spm.calor.calor-calificacion.revisarCalificarCodificar'  : [name:'Revisar, Calificar y Codificar',
										   form: [controller: 'CalOrigenAT', action:'dp01'] ],
		   'cl.isl.spm.calor.calor-calificacion.revisarCalificarCodificar2'  : [name:'Revisar, Calificar y Codificar (Nivel 2)',
										   form: [controller: 'CalOrigenAT', action:'dp02'] ],
		   'cl.isl.spm.calor.calor-calificacion.evaluarComplejidadNivel2'  	: [name:'Evaluar Complejidad',
										   form: [controller: 'CalOrigenAT', action:'dp03'] ],
		   'cl.isl.spm.calor.calor-calificacion.ingresaSeccionC'  			: [name:'Ingresar Seccion C para EP',
										   form: [controller: 'CalOrigenAT', action:'dp04'] ],
	
		   //CalOrigenEP
		   'cl.isl.spm.calor.calor-calificacion-ep.revisarCalificarCodificar2'  : [name:'Revisar, Calificar y Codificar',
										   form: [controller: 'CalOrigenEP', action:'dp01'] ],
		   'cl.isl.spm.calor.calor-calificacion-ep.evaluarComplejidadNivel2'  	: [name:'Evaluar Complejidad',
										   form: [controller: 'CalOrigenEP', action:'dp02'] ],
		   'cl.isl.spm.calor.calor-calificacion-ep.ingresaSeccionC'  			: [name:'Ingresar Seccion C para AT',
										   form: [controller: 'CalOrigenEP', action:'dp03'] ],
									   
			//SolAnteAdic
			'cl.isl.spm.calor.calor-solantedic.ingresarRespuestaN2'  : [name:'Ingresar respuesta (Calificación)',
										   form: [controller: 'SolAnteAdic', action:'dp02'] ],
			'cl.isl.spm.calor.calor-solantedic.ingresarRespuestaPrevencion'  : [name:'Ingresar respuesta (Prevención)',
										   form: [controller: 'SolAnteAdic', action:'dp02'] ],
			'cl.isl.spm.calor.calor-solantedic.ingresarRespuestaSeguimiento'  : [name:'Ingresar respuesta (Seguimiento)',
										   form: [controller: 'SolAnteAdic', action:'dp02'] ],
									   
			//77bis
			'cl.isl.spm.otp.otp-77bisingreso.iniciaProcesoRegularizacion'  : [name:'Iniciar Proceso Regularización 77bis',
										   form: [controller: 'BIS_ingreso', action: 'regularizar'] ],
			'cl.isl.spm.otp.otp-77bisingreso.notificarSolicitante'  : [name:'Notificar al Solicitante el rechazo',
										   form: [controller: 'BIS_ingreso', action: 'dp04'] ],
									   
			'cl.isl.spm.otp.otp-77bispago.analizarSolicitud'  : [name:'Analizar Solicitud 77bis',
										   form: [controller: 'BIS_revision', action: 'dp01'] ],
			'cl.isl.spm.otp.otp-77bispago.generarInformePago'  : [name:'Generar Informe de Pago 77bis',
										   form: [controller: 'BIS_revision', action: 'dp04'] ],
			'cl.isl.spm.otp.otp-77bispago.informarSolicitanteRechazoTotal'  : [name:'Informar el rechazo total de la solicitud 77bis',
										   form: [controller: 'BIS_revision', action: 'dp02'] ],
			'cl.isl.spm.otp.otp-77bispago.informarAnalisisPago'  : [name:'Informar Analisis de pago solicitud 77bis',
										   form: [controller: 'BIS_revision', action: 'dp03'] ],
						   
		]
	
	def static processName(cod){
		def name=cod
		switch (cod){
			case 'cl.isl.spm.cm.ingreso':
				name='Ingreso de Cuenta Médica';
				break;
			case 'cl.isl.spm.cm.revision':
				name='Revision de Cuenta Médica';
				break;
			case 'cl.isl.spm.cm.fact-ingreso':
				name='Ingreso de Factura';
				break;
			case 'cl.isl.spm.calor.calor-calificacion':
				name='Calificacion de Origen (AT)';
				break;
			case 'cl.isl.spm.calor.calor-calificacion-ep':
				name='Calificacion de Origen (EP)';
				break;
			case 'cl.isl.spm.calor.calor-solantedic':
				name='Solicitud de Antecedentes Adicionales ';
				break;
			case 'cl.isl.spm.otp.otp-77bisingreso':
				name='Solicitud 77bis (regularización) ';
				break;
			case 'cl.isl.spm.otp.otp-77bispago':
				name='Solicitud 77bis (pago) ';
				break;
		}
		return name;
	}
	
	def static taskName(cod){
		def name=cod
		def a=taskDefs[cod]
		if(a){
			name=a.name
		}
		return name;
	}
	
	def static taskForm(cod){
		def form=null
		def a=taskDefs[cod]
		if(a){
			form=a.form
		}
		return form;
	}
	
	
}
