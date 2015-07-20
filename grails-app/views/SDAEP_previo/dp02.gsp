<g:form name="dp02" class="pure-form pure-form-stacked" >
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
 		<legend>Ingresar Relato</legend>
		
		<isl:textArea cols="4-8" nombre="relato" valor="${relato}" rotulo="Relato Siniestro" requerido="true" rows="2"/>
		
		<legend>Siniestros previos del trabajador</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<th>Siniestro</th>
							<th>Relato</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${siniestros}" var="s">
					    <tr>
					    	<td><g:radio name="id" value="${s.id}"/></td>
					   		<td>${s.id}</td>
					   		<td>${s.relato}</td>
					   	</tr>
					</g:each>
						<tr>
							<td><g:radio name="id" value=""/></td>
							<td colspan="3">Nuevo Siniestro</td>
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

