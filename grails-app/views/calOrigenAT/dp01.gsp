<%@page import="cl.adexus.isl.spm.Siniestro"%>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="CalOrigenAT/dp01.js" />
<g:javascript src="CalOrigenAT/CalOrigenDrop.js" />

<g:form name="dp01" class="pure-form pure-form-stacked" >

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="taskId" value="${params.taskId}"/>
	<g:hiddenField name="tipoEventoOriginal" value="${siniestro.esEnfermedadProfesional ? 3 : (siniestro.diatOA?.esAccidenteTrayecto ? 2 : 1)}"/>
	<g:hiddenField name="calificacionId" value="${reca?.calificacion?.codigo}"/>
	<g:hiddenField name="tipoEventoId" value="${reca?.eventoSiniestro}"/>
	<g:hiddenField name="backTo" value="dp01"/>
	<g:hiddenField name="backToController" value="${params?.controller}"/>
	<g:hiddenField name="diagnosticoId"/>
    <g:hiddenField name="recaOrigen" value="calOrigenAT"/>
	<g:hiddenField name="docId"/>
	<g:hiddenField name="antecedentesOrigen" value="dp01"/>
	
	
	<fieldset>
 		<legend>Datos Siniestro</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
				<isl:textOutput cols="2-8" requerido="true" deshabilitado="true" rotulo="N° Siniestro" valor="${siniestro?.id}" />
				<isl:textInput cols="3-8" requerido="true" deshabilitado="true" nombre="tipo_siniestro" rotulo="Tipo Siniestro" valor="${siniestro.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}"/>
				<isl:textInput cols="3-8" requerido="true" deshabilitado="true" nombre="region_diatOA" rotulo="Región denuncia"  valor="${region['codigo']+'. '+region['descripcion']}" />			
				<div class="salto-de-linea"></div>
				<!-- Info Trab/prest -->
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(siniestro.trabajador?.run)}"/>
				<isl:textInput cols="4-8" requerido="true" deshabilitado="true" nombre="prestador" rotulo="Prestador" valor="${prestador}"/>
				<isl:textInput cols="1-8" requerido="true" deshabilitado="true" nombre="fecha_siniestro" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="Fecha Siniestro"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<isl:textInput cols="1-8" requerido="true" deshabilitado="true" nombre="dias_restantes" rotulo="Días Restantes" valor="${diasR}"/>
			</div>
		</div>
		
		<legend>DIAT-OA</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
			<!-- Horas de todo -->
			<isl:textInput cols="2-8" nombre="hora_accidente"
								requerido="true" ayuda="hh:mm"
								valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.fechaAccidente)}"
								tipo="hora"
								deshabilitado="true"
								rotulo="Hora del Accidente" />
								
			<isl:textInput cols="2-8" nombre="hora_ingreso_t"
								requerido="true" ayuda="hh:mm"
								valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.horaIngreso)}"
								tipo="hora"
								deshabilitado="true"
								rotulo="Hora de Ingreso al Trabajo" />
								
			<isl:textInput cols="2-8" nombre="hora_salida_t"
								requerido="true" ayuda="hh:mm"
								valor="${FormatosHelper.horaCortaStatic(siniestro?.diatOA?.horaSalida)}"
								tipo="hora"
								deshabilitado="true"
								rotulo="Hora de Salida del Trabajo" />
								
			<!-- Direccion -->					
			<isl:textInput cols="4-8" requerido="true" deshabilitado="true" nombre="diat_direccion" rotulo="Dirección" valor="${siniestro?.diatOA?.direccionAccidenteNombreCalle}"/>
			<isl:textInput cols="2-8" deshabilitado="true" nombre="diat_referencias" rotulo="Referencias" valor="${siniestro?.diatOA?.direccionEmpleadorRestoDireccion}"/>
			<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="diat_comuna" rotulo="Comuna" valor="${siniestro?.diatOA?.direccionAccidenteComuna?.descripcion}"/>
			<!-- Preguntas -->
			
			<legend></legend>
			
			<isl:textArea cols="4-8" requerido="true" nombre="pregunta1" 
						   rotulo="Que estaba haciendo el trabajador al momento o justo antes del accidente" 
						   valor="${siniestro?.diatOA?.que}"
						   deshabilitado="true"/>
						   
			<isl:textArea cols="4-8" requerido="true" nombre="pregunta2" 
						   rotulo="Lugar donde ocurrió el accidente (nombre de la sección, edificio, área, etc.)" 
			               valor="${siniestro?.diatOA?.lugarAccidente}"
			               deshabilitado="true"/>
			               
			<isl:textArea cols="4-8" requerido="true" nombre="pregunta3" 
			               rotulo="Descripción de como ocurrió el accidente" 
			               valor="${siniestro?.diatOA?.como}"
			               deshabilitado="true"/>
			               
			<isl:textArea cols="4-8" requerido="true" nombre="pregunta4" 
			               rotulo="Trabajo habitual del accidentado" 
			               valor="${siniestro?.diatOA?.trabajoHabitualCual}"
			               deshabilitado="true"/>
			 
			<legend></legend>
			               			
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
			<isl:textInput cols="2-8"  deshabilitado="true" nombre="acc_tray" rotulo="Accidente de Trayecto" valor="${siniestro?.diatOA?.tipoAccidenteTrayecto?.descripcion}"/>				
			<isl:textInput cols="2-8"  deshabilitado="true" nombre="medio_prueba" rotulo="Medio de Prueba" valor="${siniestro?.diatOA?.medioPrueba?.descripcion}"/>
			<isl:textArea cols="8-8"  deshabilitado="true" nombre="det_medio_prueba" rotulo="Detalle Medio de Prueba" valor="${siniestro?.diatOA?.detallePrueba}"/>				
			</div>
		</div>
		
		<legend>Archivos Adjuntos</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:if test="${docsDIAT?.size() > 0}">		 		
					<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
						<thead>
							<tr>
								<th width="90%" >Descripción de los Archivos Adjuntos</th>
								<th width="1%" nowrap="nowrap">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${docsDIAT}" var="doc">
								<tr>						
									<td>${doc.descripcion}</td>
								   	<td nowrap="nowrap">
										<button class="pure-button pure-button-secondary" onclick="document.forms[0].docId.value='${doc.id}';document.forms[0].action='viewDoc';document.forms[0].submit();"><i class="icon-info-sign"></i></button>
									</td>	
								</tr>
							</g:each>
						</tbody>
					</table>					
				</g:if>
				<g:else>
					<div>
						<div>
							<p>No hay archivos adjuntos </p>
						</div>
					</div>
				</g:else>
			</div>
		</div>
		
		<legend>Diagnósticos</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:if test="${diagnosticos?.size() > 0}">			 		
					<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
						<thead>
							<tr>
								<th width="10%">Laboral</th>
								<th width="30%">Diagnóstico</th>
								<th width="25%">Parte</th>
								<th width="15%">Lateralidad</th>
								<th width="10%">CIE-10</th>
								<th width="1%" nowrap="nowrap">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${diagnosticos?}" var="d">	
								<tr>						
									<td>${d.esLaboral ? 'Sí' : 'No'}</td>								   	
								   	<td>${FormatosHelper.truncateStatic(d.diagnostico,50)}</td>
								   	<td>${d.parte.descripcion}</td>
								   	<td>${d.lateralidad.descripcion}</td>
								   	<td>${d.cie10.codigo}</td>
								   	<td nowrap="nowrap">
										<button class="pure-button pure-button-success" onclick="document.forms[0].diagnosticoId.value='${d?.id}';document.forms[0].action='../calOrigenAT/dp06';document.forms[0].submit();"><i class="icon-wrench"></i></button>
										<!-- <button class="pure-button pure-button-error" onclick="document.forms[0].diagnosticoId.value='${d?.id}';document.forms[0].action='delete_cs';document.forms[0].submit();"><i class="icon-minus-sign"></i></button>  -->
									</td>	
								</tr>
							</g:each>
						</tbody>
					</table>					
				</g:if>
				<g:else>
					<div>
						<div>
							<p> No hay Diagnósticos asociados a este Siniestro </p>
						</div>
					</div>
				</g:else>
			</div>
			<button class="pure-button button-tool" onclick="document.forms[0].taskId.value='${taskId}'; document.forms[0].action='../calOrigenAT/dp05';document.forms[0].submit();">
    			<i class="icon-plus"></i> Agregar Diagnóstico
			</button>
		</div>
		
		<legend>Calificación</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="origen" from="${origenSiniestro}" rotulo="Origen" valor="${reca?.calificacion?.origen?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="tipoEvento" from="${eventoS}" rotulo="Tipo de Evento" valor="${reca?.calificacion?.eventoSiniestro?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="alim" from="${altaInmediata}" rotulo="Alta Inmediata" valor="${reca?.calificacion?.altaInmediata}" />			
				<isl:combo cols="2-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="califica" from="${calificacion}" rotulo="Calificación" valor="${reca?.calificacion?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
			</div>
		</div>
		
		<div class="pure-g-r" id="DivClasificacion">	
			<div class="pure-u-1">
				<legend>Codificación de Causa del Accidente</legend>
				<isl:combo cols="2-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_forma" from="${forma}" rotulo="Forma" valor="${reca?.forma?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="2-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_agente" from="${agente}" rotulo="Agente Accidente" valor="${reca?.agenteAccidente?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="2-8" requerido="true" nombre="grupoIntencionalidad" from="${grupoIntencionalidad}" rotulo="Grupo intencionalidad" valor="${reca?.intencionalidad?.grupo?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="2-8" requerido="true" nombre="cod_intencion" from="${intencion}" rotulo="Intencionalidad" valor="${reca?.intencionalidad?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				
				<!--<isl:combo cols="2-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_modoTransporte" from="${modoT}" rotulo="Modo de transporte" valor="${reca?.transporte?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-3" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_papelLesionado" from="${papelLes}" rotulo="Papel lesionado" valor="${reca?.lesionado?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />		
				<isl:combo cols="1-3" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_contraparte" from="${contraparte}" rotulo="Contraparte" valor="${reca?.contraparte?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />	
				<isl:combo cols="1-3" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_tipoEvento" from="${tipoEvento}" rotulo="Tipo Evento" valor="${reca?.evento?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />-->		
			</div>
		</div>
		
	<legend></legend>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit id="posponer_siniestro" value="Posponer"  action="cu01p" class="pure-button pure-button-warning" formnovalidate="formnovalidate" />
		<g:actionSubmit id="derivar_siniestro" value="Derivar"  action="cu01d" class="pure-button pure-button-error"  />
		<g:actionSubmit id="califica_siniestro" value="Calificar"  action="cu01c" class="pure-button pure-button-success"  />
	</div>
</g:form>