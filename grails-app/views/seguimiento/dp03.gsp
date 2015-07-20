<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>


<g:form name="dp03" class="pure-form pure-form-stacked">
	<g:hiddenField name="origen" value="dp03"/>
	<g:hiddenField name="volverSeguimiento" value="dp03"/>
	<g:hiddenField name="volverHistorial" value="dp03"/>
	<g:hiddenField name="cesarODA" value="postDp12Cesar"/>
	

	<fieldset>
		<legend>Filtros</legend>
		<isl:textInput cols="2-8" nombre="siniestroId" rotulo="Numero Siniestro" valor="${params?.siniestroId}" tipo="numero"/>
		<isl:textInput cols="2-8" nombre="run" rotulo="RUN Paciente" valor="${FormatosHelper.runFormatStatic(params?.run)}"/>
		<isl:combo cols="1-10" noSelection="${['':'Seleccione ...']}" rotulo="Estado" nombre="estadoSeguimiento" from="${estadoList}" valor="${estado}" optionKey="codigo" optionValue="descripcion"/>
		<isl:combo cols="1-10" noSelection="${['':'Seleccione ...']}" rotulo="Nivel seguimiento" nombre="nivelSeguimiento" from="${nivelSeguimientoList}" valor="${nivelSeguimiento}" optionKey="codigo" optionValue="descripcion"/>
		<div style="height: 20px;"></div>
		<button class="pure-button pure-button-secondary" id="btnFiltrar" formaction="dp03">Filtrar</button>
	</fieldset>
	
 	<fieldset>
 		<legend>Casos</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="100%">
					<thead>
						<tr>
							<th>Siniestro</th>
							<th>Tipo Siniestro</th>
							<th>RUN Paciente</th>
							<th>Fecha Siniestro</th>
							<th>Nivel</th>
							<th>Estado</th>
							<th>Historial</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!siniestros}">
						    <tr class="even">
						    	<td colspan="7"> No hay siniestros... </td>
						   	</tr>
						</g:if>
						<g:each in="${siniestros}" var="s" status="i">
						    <tr>
						   		<td align="center">${s.id}-${(s.esEnfermedadProfesional?'EP':'AT')}</td>
						   		<td>${(s.esEnfermedadProfesional?'Enfermedad Profesional':'Accidente Trabajo')}</td>
						   		<td align="right">${FormatosHelper.runFormatStatic(s.trabajador.run)}</td>
						   		<td align="center">${FormatosHelper.fechaCortaStatic(s.fecha)}</td>
						   		<td align="center">${s.nivel}</td>
						   		<td align="center">${s.estado}</td>
						   		<td align="center">
						   			<g:if test="${s.estado != 'Sin Seguimiento'}">
								   		<a href="#" title="Ver Historial" onclick="document.forms[0].siniestroId.value='${s.id}';document.forms[0].action='dp04';document.forms[0].submit();" style="text-decoration: none;">
								   			<font style="color: rgb(127,127,127);"><i class="icon-calendar icon-2x"></i></font>
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
</g:form>
