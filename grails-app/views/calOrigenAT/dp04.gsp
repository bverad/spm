<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp04" class="pure-form pure-form-stacked" >

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="taskId" value="${params.taskId}"/>
	
	<fieldset>
 		<legend>Datos Siniestro</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
				<isl:textOutput cols="6-8" requerido="true" deshabilitado="true" rotulo="N° Siniestro" valor="${siniestro?.id}" />			
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="dias_restantes" rotulo="Días Restantes" valor="${diasR}"/>
				<div class="salto-de-linea"></div>
				<!-- Info Trab/prest -->
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(siniestro.trabajador?.run)}"/>
				<isl:textInput  cols="4-8" requerido="true" deshabilitado="true" nombre="tipo_siniestro" rotulo="Tipo Siniestro" valor="${siniestro.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}"/>
				<isl:textInput  cols="2-8" requerido="true" deshabilitado="true" nombre="fecha_siniestro" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="Fecha Siniestro"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}" />
				
			</div>
		</div>
		<!-- SUPER MEGA ARREGLO Y TAL -->
		<legend>Datos del Cliente&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Datos de la Enfermedad</legend>
		
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Fecha Accidente -->
				<isl:textInput  cols="1-8" requerido="true" deshabilitado="true" nombre="fecha_accidente" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="Fecha del Accidente"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<!-- Horas de todo -->
				<isl:textInput cols="1-8" nombre="hora_accidente"
									requerido="true" ayuda="hh:mm"
									valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.fechaAccidente)}"
									tipo="hora"
									deshabilitado="true"
									rotulo="Hora ocurrencia del accidente" />
				<isl:textInput cols="1-8" nombre="hora_ingreso_t"
									requerido="true" ayuda="hh:mm"
									valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.horaIngreso)}"
									tipo="hora"
									deshabilitado="true"
									rotulo="Hora de Ingreso al Trabajo" />
				<isl:textInput cols="1-8" nombre="hora_salida_t"
									requerido="true" ayuda="hh:mm"
									valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.horaSalida)}"
									tipo="hora"
									deshabilitado="true"
									rotulo="Hora de Salida del Trabajo" />
				<!-- Pregunta 1 -->					
				<isl:textArea cols="4-8" requerido="true" nombre="sintoma" 
							   rotulo="Describa las molestias o síntomas que actualmente presenta el trabajador/a" 
							   valor="${diep?.sintoma}"/>
				<div class="salto-de-linea"></div>
							   
				<!-- Direccion parte 1-->	
				<isl:textInput cols="1-8" requerido="true" deshabilitado="true" nombre="diat_direccion" rotulo="Tipo Calle" valor="${siniestro?.diatOA?.direccionTrabajadorTipoCalle?.descripcion}"/>				
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="diat_direccion" rotulo="Dirección" valor="${siniestro?.diatOA?.direccionAccidenteNombreCalle}"/>
				<isl:textInput cols="1-8" requerido="true" deshabilitado="true" nombre="diat_direccion" rotulo="Número" valor="${siniestro?.diatOA?.direccionTrabajadorNumero}"/>
				
				<!-- Pregunta 2 -->					
				<isl:textInput cols="4-8" requerido="true" nombre="fechaSintoma" 
							   rotulo="¿Aproximadamente en que fecha comenzaron las molestias?" 
							   valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}"
							   deshabilitado="true"/>
				<div class="salto-de-linea"></div>
				
				
				<!-- Direccion Parte 2 -->	
				<isl:textInput cols="2-8" deshabilitado="true" nombre="diat_referencias" rotulo="Referencias" valor="${siniestro?.diatOA?.direccionEmpleadorRestoDireccion}"/>
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="diat_comuna" rotulo="Comuna" valor="${siniestro?.diatOA?.direccionAccidenteComuna?.descripcion}"/>
				
				<!-- Pregunta 3 -->					
				<isl:radiogroup cols="4-8" nombre="pregunta3" name="esAntecedentePrevio"
									requerido="true"
									labels="['Sí', 'No']"
									values="[true, false]"
									valor="${diep?.esAntecedentePrevio}"
									rotulo="¿Había tenido estas molestias en el puesto de trabajo actual, anteriormente?">
						${it.label}
						${it.radio}
				</isl:radiogroup>
				<div class="salto-de-linea"></div>
				
				<!-- Presentación Respuestas 1 -->
				<isl:textArea cols="4-8" requerido="true" nombre="presentacion1" 
							   rotulo="Qué estaba haciendo el trabajador al momento o justo antes del accidente" 
				               valor="${siniestro?.diatOA?.que}"
				               deshabilitado="true"/>
				               
				<!-- Pregunta 4 -->	
				<isl:textInput cols="4-8" requerido="true" nombre="parteCuerpo" 
							   rotulo="Parte del cuerpo afectada" 
							   valor="${diep?.parteCuerpo}"/>
				<div class="salto-de-linea"></div>
				
				<!-- Presentación Respuestas 2 -->								   
				<isl:textArea cols="4-8" requerido="true" nombre="presentacion2" 
							   rotulo="Lugar donde ocurrió el accidente (nombre de la sección, edificio, área, etc.)" 
				               valor="${siniestro?.diatOA?.lugarAccidente}"
				               deshabilitado="true"/>
				<!-- Pregunta 5 -->
				<isl:textArea cols="4-8" requerido="true" nombre="descripcionTrabajo" 
							   rotulo="Describa el trabajo o actividad que realizaba cuando comenzaron las molestias" 
				               valor="${diep?.descripcionTrabajo}"/>
				<div class="salto-de-linea"></div>
				                              
				<!-- Presentación Respuestas 3 -->	               
				<isl:textArea cols="4-8" requerido="true" nombre="presentacion3" 
				               rotulo="Descripción de como ocurrió el accidente" 
				               valor="${siniestro?.diatOA?.como}"
				               deshabilitado="true"/>
				<!-- Pregunta 6 -->	
				<isl:textInput cols="4-8" requerido="true" nombre="puestoTrabajo" 
							   rotulo="Nombre del puesto de trabajo o actividad que realizaba cuando comenzaron las molestias" 
							   valor="${diep?.puestoTrabajo}"/>
				<div class="salto-de-linea"></div>              
				              
				               
				<!-- Presentación Respuestas 4 -->	               
				<isl:textArea cols="4-8" requerido="true" nombre="presentacion4" 
				               rotulo="Trabajo habitual del accidentado" 
				               valor="${siniestro?.diatOA?.trabajoHabitualCual}"
				               deshabilitado="true"/>
				<!-- Pregunta 7 -->					
				<isl:radiogroup cols="4-8" nombre="pregunta3" name="esAntecedenteCompanero"
									requerido="true"
									labels="['Sí', 'No']"
									values="[true, false]"
									valor="${diep?.esAntecedenteCompanero}"
									rotulo="¿Existen compañeros de trabajo con las mismas molestias?">
						${it.label}
						${it.radio}
				</isl:radiogroup>			
				<div class="salto-de-linea"></div>               
				
				<!-- Presentación Respuestas 5 -->
				<isl:radiogroup cols="2-8" nombre="d_trabajo_h" name="d_trabajo_h"
									requerido="true"
									labels="['Sí', 'No']"
									values="[true, false]"
									valor="${siniestro?.diatOA?.esTrabajoHabitual}"
									rotulo="Desarrollaba su trabajo habitual"
									deshabilitado="true">
						${it.label}
						${it.radio}
				</isl:radiogroup>			
				<isl:textInput cols="2-8"  deshabilitado="true" requerido="true" nombre="clas_accidente" rotulo="Clasificación del Accidente" valor="${siniestro?.diatOA?.gravedad?.descripcion}"/>
			    <!-- Pregunta 8 -->	
				<isl:textArea cols="4-8" requerido="true" nombre="agenteSospechoso" 
							   rotulo="¿Que cosas o agentes del trabajo cree ud. que le causan estas molestias?" 
				               valor="${diep?.agenteSospechoso}"/>
				<div class="salto-de-linea"></div>
				
				<!-- Presentación Respuestas 6 -->
				<isl:textInput  cols="2-8" requerido="true" deshabilitado="true" nombre="tipo_accidente" rotulo="Tipo Accidente" valor="${siniestro.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo'}"/>		
				<isl:textInput cols="2-8"  deshabilitado="true" nombre="acc_tray" rotulo="Accidente de Trayecto" valor="${siniestro?.diatOA?.tipoAccidenteTrayecto?.descripcion}"/>
				<!-- Pregunta 9 -->	
				<isl:calendar cols="4-8" requerido="true" nombre="fechaAgente" 
							   rotulo="¿Aproximadamente desde que fecha ha estado expuesto a los agentes que causan la molestia?" 
							   valor="${diep?.fechaAgente}"/>
				<div class="salto-de-linea"></div>
				
				<!-- Presentación Respuestas 7 -->
					
				<isl:textInput cols="4-8"  deshabilitado="true" nombre="medio_prueba" rotulo="Medio de Prueba" valor="${siniestro?.diatOA?.medioPrueba?.descripcion}"/>
				<div class="salto-de-linea"></div>
				<isl:textArea cols="4-8"  deshabilitado="true" nombre="det_medio_prueba" rotulo="Detalle Medio de Prueba" valor="${siniestro?.diatOA?.detallePrueba}"/>			
			</div>
		</div>
		
	<div class="salto-de-linea"></div>
	<legend></legend>	
	</fieldset>	

	<div class="pure-g-r">
		<g:actionSubmit value="Posponer"  action="cu04p" class="pure-button pure-button-warning" formnovalidate="formnovalidate" />
		<g:actionSubmit value="Regularizar tipo de Siniestro"  action="cu04r" class="pure-button pure-button-success"  />
	</div>
</g:form>