<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp03" class="pure-form pure-form-stacked" >
    <g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
    <g:hiddenField name="taskId" value="${params.taskId}"/>
	<fieldset>
 		<legend>Datos Siniestro</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
				<isl:textOutput cols="6-8" requerido="true" deshabilitado="true" rotulo="N° Siniestro" valor="${siniestro?.id}" />							
				<div class="salto-de-linea"></div>
				<!-- Info Trab/prest -->
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(siniestro.trabajador?.run)}"/>
				<isl:textInput  cols="4-8" requerido="true" deshabilitado="true" nombre="tipo_siniestro" rotulo="Tipo Siniestro" valor="${siniestro.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}"/>
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="dias_restantes" rotulo="Días Restantes" valor="${diasR}"/>
				
			</div>
		</div>
		<!-- corregir mas adelante-->
		<legend>Datos de la enfermedad&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Datos del accidente</legend>
		
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Presentación Respuestas 1 -->				
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta1" 
							   rotulo="Describa las molestias o síntomas que actualmente presenta el trabajador/a" 
							   valor="${siniestro?.diepOA?.sintoma}"
							   deshabilitado="true" />
				
				<!-- Fecha Accidente -->
				<isl:textInput  cols="1-8" deshabilitado="true" requerido="true" nombre="fechaAccidente" ayuda="dd-mm-aaaa" rotulo="Fecha del Accidente"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}" />

				<isl:textInput 	cols="1-8" requerido="true" 
								tipo="hora" 
								nombre="fechaAccidente_hora" 
								rotulo="Hora accidente" 
								valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.fechaAccidente)}"/>
													
				<isl:textInput cols="1-8" nombre="horaIngreso"
									requerido="true" ayuda="hh:mm"
									valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.horaIngreso)}"
									tipo="hora"								
									rotulo="Hora de Ingreso al Trabajo" />

									
				<isl:textInput cols="1-8" nombre="horaSalida"
									requerido="true" ayuda="hh:mm"
									valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.horaSalida)}"
									tipo="hora"								
									rotulo="Hora de Salida del Trabajo" />
				
				<div class="salto-de-linea"></div>
				
				<!-- Presentación Respuestas 2 -->					
				
				<isl:textInput  cols="4-8" requerido="true" deshabilitado="true" nombre="fechaSintoma" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="¿Aproximadamente en que fecha comenzaron las molestias?"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.diepOA?.fechaSintoma)}" />      	   
				<!-- Direccion parte 1-->				
				
				<isl:textInput cols="3-8" requerido="true"  nombre="direccionAccidenteNombreCalle" rotulo="Dirección" valor="${siniestro?.diatOA?.direccionAccidenteNombreCalle}"/>
				<isl:combo cols="1-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="direccionAccidenteComuna" from="${comuna}" rotulo="Comuna" valor="${siniestro?.diatOA?.direccionAccidenteComuna?.codigo}" optionValue="${{it.descripcion}}" />     
				
				<!-- Presentación Respuestas 3 -->					
				<isl:radiogroup cols="4-8" nombre="pregunta3" name="pregunta3"
									requerido="true"
									labels="['Sí', 'No']"
									values="[true, false]"
									deshabilitado="true"
									valor="${siniestro?.diepOA?.esAntecedentePrevio}"
									rotulo="¿Había tenido estas molestias en el puesto de trabajo actual, anteriormente?">
						${it.label}
						${it.radio}
				</isl:radiogroup>
				
				<!-- pregunta -->
				<isl:textArea cols="4-8" requerido="true" nombre="que" 
							   rotulo="Que estaba haciendo el trabajador al momento o justo antes del accidente" 
				               valor="${siniestro?.diatOA?.que}" />  
				               
				<!-- Presentación Respuestas 4 -->	
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta4" 
                               rotulo="Parte del cuerpo afectada" 
                               valor="${siniestro?.diepOA?.parteCuerpo}"
                               deshabilitado="true" />
                               
				<!-- pregunta -->								   
				<isl:textArea cols="4-8" requerido="true" nombre="lugarAccidente" 
							   rotulo="Lugar donde ocurrió el accidente (nombre de la sección, edificio, área, etc.)" 
				               valor="${siniestro?.diatOA?.lugarAccidente}"/>  												
				            			
				<!-- Presentación Respuestas 5 -->
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta5" 
							   rotulo="Describa el trabajo o actividad que realizaba cuando comenzaron las molestias" 
				               valor="${siniestro?.diepOA?.descripcionTrabajo}"
				               deshabilitado="true"/>
 				 <!-- pregunta -->                 
				<isl:textArea cols="4-8" requerido="true" nombre="como" 
				               rotulo="Descripción de como ocurrió el accidente" 
				               valor="${siniestro?.diatOA?.como}" />   
				                         
				<!-- Presentación Respuestas 6-->	
				<isl:textInput cols="4-8" requerido="true" nombre="pregunta6" 
							   rotulo="Nombre del puesto de trabajo o actividad que realizaba cuando comenzaron las molestias" 
							   valor="${siniestro?.diepOA?.puestoTrabajo}"
							   deshabilitado="true"/>
							   
				<!-- pregunta -->	               
				<isl:textArea cols="4-8" requerido="true" nombre="trabajoHabitualCual" 
				               rotulo="Trabajo habitual del accidentado" 
				               valor="${siniestro?.diatOA?.trabajoHabitualCual}"/>				
            
				<!-- Presentación Respuestas 7 -->
				<isl:radiogroup cols="4-8" nombre="pregunta3" name="pregunta7"
									requerido="true"
									labels="['Sí', 'No']"
									values="[true, false]"
									deshabilitado="true"
									valor="${siniestro?.diepOA?.esAntecedenteCompanero}"
									rotulo="¿Existen compañeros de trabajo con las mismas molestias?">
						${it.label}
						${it.radio}
				</isl:radiogroup>              
				               
				<!-- pregunta -->
				<isl:radiogroup cols="2-8" nombre="esTrabajoHabitual"
									requerido="true"
									labels="['Sí', 'No']"
									values="[true, false]"
									valor="${siniestro?.diatOA?.esTrabajoHabitual}"
									rotulo="Desarrollaba su trabajo habitual" >
						${it.label}
						${it.radio}
				</isl:radiogroup>	
				
				<isl:radiogroup cols="2-8" nombre="gravedad" name="gravedad"
									requerido="true"
									labels="['otro', 'Grave', 'Fatal']"
									values="[1, 2, 3]"
									valor="${siniestro?.diatOA?.gravedad?.codigo}"
									rotulo="Clasificacion del Accidente" >
						${it.label}
						${it.radio}
				</isl:radiogroup>
				
				<!-- Presentación Respuestas 8 -->					           				
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta8" 
							   rotulo="¿Que cosas o agentes del trabajo cree ud. que le causan estas molestias?" 
				               valor="${siniestro?.diepOA?.agenteSospechoso}"
				               deshabilitado="true"/>
				
				<!-- pregunta -->
				<isl:radiogroup cols="2-8" nombre="esAccidenteTrayecto" name="esAccidenteTrayecto"
                                    requerido="true"
                                    deshabilitado="${reca?.calificacion?.codigo == '06'? false : true}"
                                    labels="['Trabajo', 'Trayecto']"
                                    values="[1, 2]"
                                    valor="${reca?.calificacion?.eventoSiniestro?.codigo}"
                                    rotulo="Tipo de Accidente" >
                        ${it.label}
                        ${it.radio}
                </isl:radiogroup>	
				
				<isl:combo cols="2-8" requerido="true" deshabilitado="${(reca?.calificacion?.eventoSiniestro?.codigo == '2' || reca?.calificacion?.codigo == '06') ? false : true}" nombre="tipoAccidenteTrayecto" rotulo="Tipo de Accidente de trayecto" from="${tiposAccidenteTrayecto}" valor="${siniestro?.diatOA?.tipoAccidenteTrayecto?.codigo}" />
				<div class="salto-de-linea"></div>
				
				<!-- Presentación Respuestas 9 -->				
				<isl:textInput  cols="4-8" requerido="true" deshabilitado="true" nombre="pregunta9" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="¿Aproximadamente desde que fecha ha estado expuesto a los agentes que causan la molestia?"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.diepOA?.fechaAgente)}" />			   
							
				<!-- pregunta -->	
				<isl:combo cols="1-8" nombre="medioPrueba" requerido="${(reca?.calificacion?.eventoSiniestro?.codigo == 2) ? 'true' : 'false'}" rotulo="Medio de prueba" from="${tipoMedioPruebaAccidentes}" valor="${siniestro?.diatOA?.medioPrueba?.codigo}" noSelection="${['':'Seleccione...']}" />
				<isl:textArea cols="3-8"  nombre="detallePrueba" requerido="${(reca?.calificacion?.eventoSiniestro?.codigo == 2) ? 'true' : 'false'}" rotulo="Detalle Medio de Prueba" valor="${siniestro?.diatOA?.detallePrueba}"/>	
				
				<script type="text/javascript">
					YUI().use('node', function (Y) {

			    		var labels = document.getElementsByTagName('LABEL');
			    		
						Y.one('#esAccidenteTrayecto_0').delegate('click', function (e) {
					    	if (e.target.get('value')!= 2){
					    		document.getElementById("tipoAccidenteTrayecto").disabled = true;
					    		document.getElementById("medioPrueba").required = false;
					    		document.getElementById("detallePrueba").required = false;
					    		for (i = 0; i < labels.length; i++) {
									if (labels[i].htmlFor == 'medioPrueba')
										labels[i].innerHTML = "Medio de prueba";
									if (labels[i].htmlFor == 'detallePrueba')
										labels[i].innerHTML = "Detalle Medio de Prueba";
								}
						    }
					  	}, 'input[type=radio]');
					  	
						Y.one('#esAccidenteTrayecto_1').delegate('click', function (e) {
					    	if (e.target.get('value')== 2){
					    		document.getElementById("tipoAccidenteTrayecto").disabled = false;
					    		document.getElementById("medioPrueba").required = true;
					    		document.getElementById("detallePrueba").required = true;
					    		for (i = 0; i < labels.length; i++) {
									if (labels[i].htmlFor == 'medioPrueba')
										labels[i].innerHTML = "Medio de prueba<font color='red'>*</font>";
									if (labels[i].htmlFor == 'detallePrueba')
										labels[i].innerHTML = "Detalle Medio de Prueba<font color='red'>*</font>";
								}
						    }
					  	}, 'input[type=radio]');
					  	
					});
				</script>
						
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit id="posponer_siniestro" value="Posponer"  action="cu03p" class="pure-button pure-button-warning" formnovalidate="formnovalidate" />
		<g:actionSubmit value="Regularizar tipo de Siniestro"  action="cu03r" class="pure-button pure-button-success"  />
	</div>
</g:form>
