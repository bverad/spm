<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="" />
<g:form name="dp03" class="pure-form pure-form-stacked" >

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="taskId" value="${params.taskId}"/>
	
	<fieldset>
	
 		<legend>Datos Siniestro</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
				<isl:textOutput cols="2-8" requerido="true" deshabilitado="true" rotulo="N° Siniestro" valor="${siniestro?.id}" />
				<isl:textInput  cols="4-8" requerido="true" deshabilitado="true" nombre="tipo_siniestro" rotulo="Tipo Siniestro" valor="${siniestro?.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro?.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}"/>
				<isl:textInput  cols="1-8" requerido="true" deshabilitado="true" nombre="fecha_siniestro" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="Fecha Siniestro"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<div class="salto-de-linea"></div>
				<!-- Info Trab/prest -->
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(siniestro.trabajador?.run)}"/>
				<isl:textInput cols="4-8" requerido="true" deshabilitado="true" nombre="prestador" rotulo="Prestador" valor="${prestador}"/>
				<isl:textInput cols="1-8" requerido="true" deshabilitado="true" nombre="dias_restantes" rotulo="Días Restantes" valor="${diasR}"/>
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
				
				<div class="form-subtitle">Comentarios Atención</div>
				<isl:textArea cols="8-8" deshabilitado="true" nombre="comentarios" rotulo="" valor="${informeOpa?.comentarioAtencion}"/>
				
			</div>
		</div>
		
		<legend>Evaluación de Complejidad</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:radiogroup cols="8-8" name="eval_complejidad" nombre="Evaluacion de complejidad"
					labels="${e_complejidad.descripcion}"
					values="${e_complejidad.codigo}"
					valor="${siniestro?.nivelComplejidad}"
					requerido="true">
					<br>${it.radio} ${it.label}<br><br>
				</isl:radiogroup>
			</div>
		</div>
		
		<legend></legend>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit value="Posponer"  action="cu03p" class="pure-button pure-button-warning" formnovalidate="formnovalidate" />
		<g:actionSubmit value="Asignar Complejidad"  action="cu03a" class="pure-button pure-button-success"  />
	</div>
</g:form>