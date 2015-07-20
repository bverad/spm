<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form class="pure-form pure-form-stacked">
	<fieldset>
		<legend>Filtros</legend>
		<isl:textInput cols="2-8" nombre="siniestroId" 	rotulo="N° Siniestro"  valor="${params?.siniestroId}" tipo="numero"/>
		<isl:textInput cols="1-8" nombre="run" 			rotulo="RUN Paciente"  valor="${params?.run}"/>
		<isl:textInput cols="1-8" nombre="rut" 			rotulo="RUT Empleador" valor="${params?.rut}"/>
		<g:hiddenField name="origen" value="dp11"/>
		<g:hiddenField name="volverSeguimiento" value="dp11"/>
		<g:hiddenField name="volverHistorial" value="dp12"/>
		<g:hiddenField name="verDetalle" value="dp14"/>
		<g:hiddenField name="cesarODA" value="../postDp12Cesar" />
		
		
		<div style="height: 20px;"></div>
		<button class="pure-button pure-button-secondary" id="btnFiltrar" formaction="dp11">Filtrar</button>
	</fieldset>
	
 	<fieldset>
 		<legend>Siniestros para Ingreso</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="100%">
					<thead>
						<tr>
							<th>Siniestro</th>
							<th>Tipo Siniestro</th>
							<th>RUN Paciente</th>
							<th>RUT Empleador</th>
							<th>Fecha Siniestro</th>
							<th>Administrar</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!siniestros}">
						    <tr class="even">
						    	<td colspan="6"> No hay siniestros... </td>
						   	</tr>
						</g:if>
						<g:each in="${siniestros}" var="s" status="i">
						    <tr>
						   		<td align="center">${s.id}-${(s.esEnfermedadProfesional?'EP':'AT')}</td>
						   		<td>${(s.esEnfermedadProfesional?'Enfermedad Profesional':'Accidente Trabajo')}</td>
						   		<td align="right">${FormatosHelper.runFormatStatic(s.trabajador.run)}</td>
						   		<td align="right">${FormatosHelper.runFormatStatic(s.empleador.rut)}</td>
						   		<td align="center">${FormatosHelper.fechaCortaStatic(s.fecha)}</td>
							   	<td align="center">
							   		<g:link action="dp12" id="${s.id}" params="[siniestroId: s.id, estado: s.estado, verDetalle:'../dp14', volverSeguimiento:'dp11', volverHistorial:'dp12', origen:'dp11', cesarODA:'../postDp12Cesar']">
								     	<font style="color: rgb(127,127,127);"><i class="icon-gears icon-2x"></i></font>
									</g:link>
								</td>	
						   	</tr>
						</g:each>
					</tbody>
					</table>
				</div>
			</div>
	</fieldset>	
</g:form>
