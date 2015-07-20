<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form name="dp02" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>Solicitudes de Reembolso para Analizar</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<th>Id Solicitud</th>
							<th>Siniestro</th>
							<th>Trabajador</th>
							<th>Solicitante</th>
							<th>Monto Solicitado</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${reembolsos}" var="r">
					    <tr>
					    	<td><g:radio name="reembolsoId" value="${r.id}"  required="required" /></td>
					    	<td>${r.id }</td>
					   		<td>${r.siniestro.id}-${(r.siniestro.esEnfermedadProfesional?'EP':'AT')}</td>
					   		<td>${FormatosISLHelper.nombreCompletoStatic(r.trabajador)}
					   			 [${FormatosISLHelper.runFormatStatic(r.trabajador?.run)}] </td>
					   		<td>${FormatosISLHelper.nombreCompletoStatic(r.solicitante)}
					   			 [${FormatosISLHelper.runFormatStatic(r.solicitante?.run)}] </td>
					   		<td  style="text-align:right;">$ ${FormatosHelper.montoStatic(r.montoSolicitado)}</td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>

	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="dp07"/>
	</div>
</g:form>

