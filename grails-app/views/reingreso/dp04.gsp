<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form class="pure-form pure-form-stacked" >
	<fieldset>
		<legend>Filtros</legend>
		<isl:textInput cols="2-8" nombre="siniestroId" rotulo="Numero Siniestro" valor="${params?.siniestroId}" tipo="numero"/>
		<div style="height: 20px;"></div>
		<button class="pure-button pure-button-secondary" id="btnFiltrar" formaction="dp04">Filtrar</button>
	</fieldset>
	
 	<fieldset>
 		<legend>Solicitud de Reingreso</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="100%">
					<thead>
						<tr>
							<th>Selección</th>
							<th>Siniestro</th>
							<th>Tipo Siniestro</th>
							<th>RUN Paciente</th>
							<th>Fecha Siniestro</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!siniestros}">
						    <tr class="even">
						    	<td colspan="5"> No hay siniestros... </td>
						   	</tr>
						</g:if>
						<g:each in="${siniestros}" var="s" status="i">
						    <tr>
						    	<td align="center"><g:radio name="id" value="${s.id}" /></td>
						   		<td align="center">${s.id}-${(s.esEnfermedadProfesional?'EP':'AT')}</td>
						   		<td>${(s.esEnfermedadProfesional?'Enfermedad Profesional':'Accidente Trabajo')}</td>
						   		<td align="right">${FormatosISLHelper.runFormatStatic(s.trabajador.run)}</td>
						   		<td align="center">${FormatosISLHelper.fechaCortaStatic(s.fecha)}</td>
						   	</tr>
						</g:each>
					</tbody>
					</table>
				</div>
			</div>
	</fieldset>	
	<g:if test="${siniestros}">
		<div class="pure-g-r">
			<isl:button  action="cu04s"/>
		</div>
	</g:if>
</g:form>
