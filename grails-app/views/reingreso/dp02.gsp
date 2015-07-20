<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form class="pure-form pure-form-stacked" >
    <fieldset>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" rotulo="RUN Trabajador" valor="${FormatosISLHelper.runFormatStatic(trabajador.run)}" />
				<isl:textOutput cols="3-8" rotulo="Nombre Trabajador" valor="${FormatosISLHelper.nombreCompletoStatic(trabajador)}" />
			</div>
		</div>
	</fieldset>
    <fieldset>
 		<legend>Seleccionar Siniestro</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table" width="100%">
					<thead>
						<tr>
							<th>Selección</th>
							<th>Tipo</th>
							<th>Siniestro</th>
							<th>Relato</th>
						</tr>
					<thead>	
					<tbody>
						<g:if test="${!siniestroList}">
							<tr  class="pure-table-odd">
								<td colspan="4"> El trabajador ingresado no tiene siniestros a procesar... </td>
							</tr>	
						</g:if>
						<g:each in="${siniestroList}" status="i" var="s">
							<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">	
						    	<td align="center"><g:radio name="id" value="${s.id}"  required="required" /></td>
								<td>${s.esEnfermedadProfesional ? 'Enfermedad Profesional' : 'Accidente Trabajo'}</td>	
								<td align="center">${s.id}</td>	
								<td title="${s.relato}">${FormatosISLHelper.truncateStatic(s.relato)}</td>
							</tr>
						</g:each>
					</tbody>
	</table>
			</div>
		</div>
	</fieldset>
	<g:if test="${siniestroList}">
		<br>
		<div class="pure-g-r">		
			<isl:button action="cu02t" tipo="cancelar" value="Terminar" validar="false" />
			<isl:button action="cu02s" tipo="siguiente" />
		</div>
	</g:if>
</g:form>
