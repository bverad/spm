<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FechaHoraHelper" %>
<g:javascript src="Seguimiento/dp05.js" />
<g:form class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">
	<g:hiddenField name="fileLength" value="${fileCount}"/>
	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="actividadSeguimientoId" value="${actividadSeguimiento?.id}"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="dp14" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	
	
	<fieldset>
		<legend>Registrar Actividad</legend>
 		<div class="form-subtitle">Datos Siniestro</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" rotulo="N° Siniestro"			valor="${siniestro?.id}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Siniestro"			valor="${siniestro?.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro?.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Siniestro"			valor="${FormatosISLHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<isl:textOutput cols="1-8" rotulo="Complejidad" 			valor="${FormatosISLHelper.getNivelComplejidadStr(siniestro?.nivelComplejidad)}" />
				<isl:textOutput cols="1-8" rotulo="Días en Seguimiento"		valor="${FechaHoraHelper.diffDates(seguimiento?.fechaIngreso) == -1 ? 'Sin Seguimiento' : FechaHoraHelper.diffDates(seguimiento?.fechaIngreso)}" />
				<isl:textOutput cols="1-8" rotulo="ODA Principal Vigente"	valor="${isOPAVigente ? 'Sí' : 'No'}" />
				<isl:textOutput cols="1-8" rotulo="Nivel Seguimiento"		valor="${seguimiento?.nivel}" />
				<isl:textOutput cols="1-8" rotulo="RUN Trabajador"			valor="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}" />
			</div>
		</div>

 		<div class="form-subtitle">Registro Actividad</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:calendar  cols="1-8" nombre="fechaActividad" requerido="true" rotulo="Fecha" valor="${actividadSeguimiento?.fechaActividad}" />
				<isl:combo 	   cols="2-8" nombre="tipoActividad"  requerido="true" rotulo="Tipo Actividad" from="${tipoActividad}" noSelection="${['':'Seleccione ...']}" valor="${actividadSeguimiento?.tipoActividad?.codigo}" />
				<isl:textInput cols="3-8" nombre="otro"		  	  rotulo="Otro" valor="${actividadSeguimiento?.otro}" />
				<div class='salto-de-linea'></div>
				
				<isl:textArea  cols="3-8" nombre="resumen"		requerido="true" rotulo="Resumen" valor="${actividadSeguimiento?.resumen}" />
				<isl:textArea  cols="4-8" nombre="comentario"	rotulo="Comentarios u observaciones" valor="${actividadSeguimiento?.comentario}" />
			</div>
		</div>
	</fieldset>	
	
	<fieldset>
		<legend>Archivos a adjuntar</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">	
				<table id="fileList" style="width: 80%;text-align:center;margin-left: 5%;" class="pure-table pure-table-bordered pure-table-striped" width="100%">
				<thead>
					<tr>
						<th>orden</th>
						<th>Archivo</th>
						<th>Accion</th>
					</tr>
				</thead>

				<g:if test="${fileDetail != []}">
					<tbody>
					<g:each status="i" in="${fileDetail}" var="item">
						<tr>
							<td>${i + 1}</td>
							<td>
								<div class="fieldcontain ${item.errors ? 'error' : ''}" style="margin:0;width:100%;">
									<input type="file" name="${item.field}">
								</div>
							</td>
							<td><button name="eliminar" onclick="deleteFile(this)">Eliminar</button></td>
						</tr>
					</g:each>
					</tbody>
				</g:if>
				</table></br>
				<div class="pure-g-r">
					<g:actionSubmit action="dp04"  class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
					<g:actionSubmit action="postDp05" class="pure-button pure-button-success" value="Actualizar"/>
				</div>
			</div>
		</div>	
	</fieldset>

		<fieldset>
		<legend>Archivos ya adjuntos</legend>
		<table class="pure-table pure-table-bordered pure-table-striped"
			width="100%">
			<thead>
				<tr>
					<th>Nombre Archivo</th>
					<th>Eliminar</th>
				</tr>
			</thead>
			<tbody>
				<g:if test="${actividadSeguimiento?.documentacion == null || !actividadSeguimiento?.documentacion}">
					<tr>
						<td colspan="2">No hay archivos adjuntos, para adjuntar un
							archivo primero debe registrar una actividad de seguimiento...</td>
					</tr>
				</g:if>
				<g:else>
					<g:each in="${actividadSeguimiento?.documentacion}" var="doc">
						<tr>
							<td>
								${doc.descripcion}
							</td>
							<td align="center">
								<g:link action="postDp05Eliminar" params="[docAdicionalId: doc.id, fileLength:fileCount, siniestroId: siniestro.id, actividadSeguimientoId: actividadSeguimiento.id]" onclick="return confirm('¿Esta seguro que desea eliminar el archivo ${doc.descripcion}?')"><font color=red><i class="icon-trash icon-2x"></i></font></g:link>
							</td>
						</tr>
					</g:each>
				</g:else>
			</tbody>
		</table>
	</fieldset>

	<g:if test="${flash.default}">
		<fieldset>
			<div class="pure-u-1 messages">
				<ul>
					<li><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></li>
				</ul>
			</div>
		</fieldset>
	</g:if>
</g:form>

<table>
	<tr>
		<td>Accion :  </td>
		<td>			
			<button name="upload" class="pure-button pure-button-warning" onclick="addFile()">Agregar nuevo archivo</button>
		</td>
	<tr>
</table>


