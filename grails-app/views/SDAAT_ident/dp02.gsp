<g:form name="dp02" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		 	<legend>Empresas del trabajador</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<th>RUT Empleador</th>
							<th>Razon Social</th>
							<th>Seguro</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${empresas}" var="e">
					    <tr>
					    	<td><g:radio name="rut" value="${e.rut}"/></td>
					   		<td>${e.rut}</td>
					   		<td>${e.nombre}</td>
					   		<td>${e.seguroLaboral}</td>
					   		
					   	</tr>
					</g:each>
						<tr>
							<td><g:radio name="rut" value=""/></td>
							<td colspan="3">Otro</td>
						</tr>
					</tbody>
					</table>
				</div>
			</div>
	</fieldset>
	<div class="pure-g-r">
		<isl:button  action="cu02"/>
	</div>
</g:form>