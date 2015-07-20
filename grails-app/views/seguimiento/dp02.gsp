<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form name="dp02" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">

	<g:hiddenField name="id" value="${siniestro?.id}"/>	
	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="docAdicionalId" />
	<g:hiddenField name="seguimientoId" value="${seguimiento?.id}"/>
	<g:hiddenField name="actividadSeguimientoId" value="${actividadSeguimiento?.id}"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	
	<fieldset>
 		<legend>Datos Siniestro</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
				<isl:textOutput cols="1-8" rotulo="N° Siniestro" valor="${siniestro?.id}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Siniestro" valor="${siniestro?.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro?.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}" />
				<isl:textOutput cols="1-8" rotulo="Complejidad" valor="${FormatosISLHelper.getNivelComplejidadStr(siniestro?.nivelComplejidad)}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Siniestro" valor="${FormatosISLHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<!-- Info Trab/prest -->
				<isl:textOutput cols="1-8" rotulo="RUN Trabajador" valor="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}" />
				<isl:textOutput cols="2-8" rotulo="Prestador OPA" valor="${prestador}" />
				<isl:textOutput cols="1-8" rotulo="Vencimiento OPA" valor="${FormatosISLHelper.fechaCortaStatic(vencimientoOPA)}" />
			</div>
		</div>
		
		<legend>Diagnósticos</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Laboral</th>
							<th>Diagnóstico</th>
							<th>Parte</th>
							<th>Lateralidad</th>
							<th>CIE-10</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!diagnosticos}">
							<tr>						
								<td colspan="5">No hay Diagnósticos asociados a este Siniestro...</td>								   	
							</tr>
						</g:if>
						<g:each in="${diagnosticos}" var="d">	
							<tr>						
								<td>${d.esLaboral ? 'Sí' : 'No'}</td>								   	
							   	<td title="${d.diagnostico}">${FormatosISLHelper.truncateStatic(d.diagnostico)}</td>
							   	<td title="${d.parte.descripcion}">${FormatosISLHelper.truncateStatic(d.parte.descripcion)}</td>
							   	<td>${d.lateralidad.descripcion}</td>
							   	<td>${d.cie10.codigo}</td>
							</tr>
						</g:each>
					</tbody>
				</table>					
			</div>
		</div>
		
		<legend>Ingreso</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textArea cols="4-8" requerido="true" deshabilitado="${actividadSeguimiento?.id != null}" nombre="resumen" rotulo="Resumen del caso" valor="${seguimiento?.resumen}"/>
				<isl:textArea cols="4-8"				  deshabilitado="${actividadSeguimiento?.id != null}" nombre="observaciones" rotulo="Comentarios u observaciones" valor="${seguimiento?.observaciones}"/>
				<isl:combo 	  cols="2-8"				  deshabilitado="${actividadSeguimiento?.id != null}" nombre="nivel" from="${nivel}" rotulo="Nivel de Seguimiento" valor="${seguimiento?.nivel}" noSelection="${['0':'Sin Seguimiento']}" />
			</div>
		</div>

		<g:if test="${actividadSeguimiento?.id != null}">
			<legend>Archivos Adjuntos</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:upload cols="3-8" nombre="fileDenuncia" tipo="seguimiento" />
					<g:actionSubmit action="uploadDocSeguimiento" class="pure-button pure-button-secondary" value="Adjuntar" formnovalidate="formnovalidate"/>
					<br/>
					<br/>
					
					<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
						<thead>
							<tr>
								<th>Nombre Archivo</th>
								<th>Eliminar</th>
							</tr>
						</thead>
						<tbody>
							<g:if test="${!adjuntos}">
								<tr>						
									<td colspan="2">No hay archivos adjuntos</td>
								</tr>
							</g:if>		 		
							<g:each in="${adjuntos}" var="doc">
								<tr>						
									<td>${doc.descripcion}</td>
								   	<td align="center">
								   		<a href="#" onclick="document.forms[0].docAdicionalId.value='${doc.id}';document.forms[0].siniestroId.value='${siniestro?.id}';document.forms[0].action='postDp02Eliminar';document.forms[0].submit();" style="text-decoration: none;">
								   			<font color=red><i class="icon-trash icon-2x"></i></font>
								   		</a>
									</td>	
								</tr>
							</g:each>
						</tbody>
					</table>					
				</div>
			</div>
		</g:if>
		

	</fieldset>	
	<div class="pure-g-r">
		<g:if test="${actividadSeguimiento?.id != null}">
			<g:actionSubmit id="ingresar_caso" value="Terminar"  action="cu02t" class="pure-button pure-button-success"  />
		</g:if>
		<g:else>
			<g:actionSubmit id="posponer_siniestro" value="Posponer"  action="cu02p" class="pure-button pure-button-warning" formnovalidate="formnovalidate" />
			<g:actionSubmit id="ingresar_caso" value="Siguiente"  action="cu02i" class="pure-button pure-button-success"  />
		</g:else>
	</div>
</g:form>