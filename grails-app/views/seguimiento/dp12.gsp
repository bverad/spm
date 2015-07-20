<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FechaHoraHelper" %>
<g:form class="pure-form pure-form-stacked">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="odaId" />
	<g:hiddenField name="estado" value="${estado}" />
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}" />
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="postDp12Cesar" />
	
	
	<fieldset>
		<legend>Administrar</legend>
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

 		<div class="form-subtitle" style="${(seguimiento?.fechaIngreso != null && seguimiento?.fechaAlta == null) ? '' : 'display: none;'}">Nivel de Seguimiento</div>
		<div class="pure-g-r" style="${(seguimiento?.fechaIngreso != null && seguimiento?.fechaAlta == null) ? '' : 'display: none;'}">
			<div class="pure-u-1">
				<isl:combo cols="2-8" requerido="true" rotulo="Nivel de Seguimiento" nombre="nivel" from="${niveles}" valor="${seguimiento?.nivel}"/>
				<div style="height: 19px;"></div>
				<g:actionSubmit action="postDp12Nivel" class="pure-button pure-button-secondary" value="Cambiar" formnovalidate="formnovalidate"/>
			</div>
		</div>

 		<div class="form-subtitle">Ordenes de Atención
 			<g:if test="${estado != 'CERRADO'}">
				<button class="pure-button button-tool" onclick="document.forms[0].action='${actionCrearODA}';document.forms[0].submit();">
					<i class="icon-plus"></i> Crear nueva ODA
				</button>
			</g:if>
 		</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Tipo ODA</th>
							<th>Inicio Vigencia</th>
							<th>Termino Vigencia</th>
							<th title="Principal ó Complementaria">Prin. / Comp.</th>
							<th>Ver</th>
							<th>Cesar</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!odas}">
							<tr>						
								<td colspan="6">No hay ODAS asociado a este Siniestro...</td>								   	
							</tr>
						</g:if>
						<g:each in="${odas}" var="oda">
							<tr>
								<td>${oda.tipoODA.descripcion}</td>
								<td align="center">${FormatosISLHelper.fechaCortaStatic(oda.inicioVigencia)}</td>
								<td align="center">${FormatosISLHelper.fechaCortaStatic(oda.terminoVigencia)}</td>
								<td>${oda.tipoEstado}</td>
								<td align="center">
							   		<a href="#" title="Ver Detalle" onclick="document.forms[0].odaId.value='${oda.id}';document.forms[0].action='${verDetalle}';document.forms[0].submit();" style="text-decoration: none;">
							   			<font style="color: rgb(127,127,127);"><i class="icon-search icon-2x"></i></font>
							   		</a>
								</td>								   	
						 	   	<td align="center">
							   		<g:if test="${!oda.cesada}">
								   		<a href="#" title="Cesar ODA" onclick="if (confirm('¿ Está seguro que desea cesar ODA ?')) { document.forms[0].odaId.value='${oda.id}';document.forms[0].action='${cesarODA}';document.forms[0].submit(); }" style="text-decoration: none;">
								   			<font style="color: red;"><i class="icon-remove icon-2x"></i></font>
								   		</a>
								   	</g:if>
							   	</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="${volverHistorialSeguimiento ? 'dp04' : 'dp11'}" class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
		<g:if test="${origen != 'dp03'}">
			<g:actionSubmit action="dp04" class="pure-button pure-button-secondary" value="Historial" formnovalidate="formnovalidate"/>
		</g:if>
		
	
	</div>
</g:form>