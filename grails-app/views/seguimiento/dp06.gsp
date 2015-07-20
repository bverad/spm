<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FechaHoraHelper" %>
<g:form class="pure-form pure-form-stacked">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}" />
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	<g:hiddenField name="docAdicionalId" />
	
	<fieldset>
		<legend>Registrar Actividad</legend>
 		<div class="form-subtitle">Datos Siniestro</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
				<isl:textOutput cols="1-8" rotulo="N° Siniestro"			valor="${siniestro?.id}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Siniestro"			valor="${siniestro?.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro?.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Siniestro"			valor="${FormatosISLHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<isl:textOutput cols="1-8" rotulo="Complejidad" 			valor="${FormatosISLHelper.getNivelComplejidadStr(siniestro?.nivelComplejidad)}" />
				<isl:textOutput cols="1-8" rotulo="Días en Seguimiento"		valor="${FechaHoraHelper.diffDates(seguimiento?.fechaIngreso) == -1 ? 'Sin Seguimiento' : FechaHoraHelper.diffDates(seguimiento?.fechaIngreso)}" />
				<isl:textOutput cols="1-8" rotulo="ODA Principal Vigente"	valor="${isOPAVigente ? 'Sí' : 'No'}" />
				<isl:textOutput cols="1-8" rotulo="Nivel Seguimiento"		valor="${seguimiento?.nivel}" />
				<!-- Info Trab/prest -->
				<isl:textOutput cols="1-8" rotulo="RUN Trabajador"			valor="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}" />
			</div>
		</div>

 		<div class="form-subtitle">Registro Actividad</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" rotulo="Fecha" valor="${FormatosISLHelper.fechaCortaStatic(actividadSeguimiento?.fechaActividad)}" />
				<isl:textOutput cols="2-8" rotulo="Tipo Actividad" valor="${actividadSeguimiento?.tipoActividad?.descripcion}" />
				<!-- TODO JOSE: Si el tipo de actividad es Otro se visualiza este componente -->
				<g:if test="${actividadSeguimiento?.tipoActividad?.codigo == '7'}">
					<isl:textOutput cols="3-8" rotulo="Otro" valor="${actividadSeguimiento?.otro}" />
				</g:if>
				<div class='salto-de-linea'></div>
				
				<isl:textOutput cols="4-8" rotulo="Resumen" valor="${actividadSeguimiento?.resumen}" />
				<isl:textOutput cols="4-8" rotulo="Comentarios u observaciones" valor="${actividadSeguimiento?.comentario}" />
			</div>
		</div>
		
		<div class="form-subtitle">Archivos Adjuntos</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Nombre Archivo</th>
							<th>Ver</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${actividadSeguimiento == null || actividadSeguimiento?.documentacion?.size() == 0}">
							<tr>						
								<td colspan="2">No hay archivos adjuntos</td>
							</tr>
						</g:if>		 		
						<g:each in="${actividadSeguimiento?.documentacion}" var="doc">
							<tr>						
								<td>${doc.descripcion}</td>
							   	<td align="center">
							   		<a href="#" onclick="document.forms[0].docAdicionalId.value='${doc.id}';document.forms[0].action='viewDoc';document.forms[0].submit();" style="text-decoration: none;">
							   			<font color=red><i class="icon-download-alt icon-2x"></i></font>
							   		</a>
								</td>	
							</tr>
						</g:each>
					</tbody>
				</table>					
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="dp04"  class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
	</div>
</g:form>