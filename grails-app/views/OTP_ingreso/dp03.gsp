<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:form name="dp03" class="pure-form pure-form-stacked" >

	<fieldset>
 		<legend>Elegir Siniestro</legend>	
		
		<legend>Datos Trabajador</legend>
		
		<label for="runTrabajador" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">RUN Trabajador</label>
		<input type="text" name="runTrabajador" disabled="disabled" style="display: inline-block; width: 150px;" value="${FormatosHelper.runFormatStatic(trabajador?.run)}"/><br/>
		
		<label for="nombreTrabajador" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Nombre Trabajador</label>
		<input type="text" name="nombreTrabajador" disabled="disabled" style="display: inline-block; width: 620px;" value="${trabajador?.nombre} ${trabajador?.apellidoPaterno} ${trabajador?.apellidoMaterno}"/><br/>
		
		<label for="direccion" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Dirección</label>
		<input type="text" name="direccion" disabled="disabled" style="display: inline-block; width: 350px;" value="${datosTrabajador?.direccion}" />
		<label for="comuna" style="display: inline-block; width: 62px; text-align: right; margin-right: 10px;">Comuna</label>
		<input type="text" name="comuna" disabled="disabled" style="display: inline-block; width: 190px;" value="${datosTrabajador?.comuna}" /><br />
		
		<label for="telefono" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Telefono</label>
		<input type="text" name="telefono" disabled="disabled" style="display: inline-block; width: 100px;" value="${datosTrabajador?.telefono}" />
		<label for="sexo" style="display: inline-block; width: 42px; text-align: right; margin-right: 10px;">Sexo</label>
		<div style="display: inline-block; width: 184px;">
			<g:if test="${trabajador?.sexo == 'M'}">
				<input type="radio" name="sexo" disabled="disabled" checked="checked">
				<span style="font-size: 90%;">Hombre</span>
			</g:if>
			<g:elseif test="${trabajador?.sexo == 'F'}">
				<input type="radio" name="sexo" disabled="disabled" checked="checked">
				<span style="font-size: 90%;">Mujer</span>
			</g:elseif>
			<g:else>
				&nbsp;
			</g:else>
		</div>

		<label for="fechaNacimiento" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Fecha de Nacimiento</label>
		<input type="text" name="fechaNacimiento" disabled="disabled" style="display: inline-block; width: 108px;" value="${FormatosHelper.fechaCortaStatic(trabajador?.fechaNacimiento)}" /><br />
		
		<label for="nacionalidad" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Nacionalidad</label>
		<input type="text" name="nacionalidad" disabled="disabled" style="display: inline-block; width: 270px;" value="${datosTrabajador?.nacionalidad}" /></ br>
		
		<g:hiddenField name="siniestroId"/>
		<g:hiddenField name="runTrabajador" value="${trabajador?.run}"/>
		<legend>Historial Trabajador</legend>
		<table class="pure-table" style="width: 100%;">
		<thead>
			<tr>
				<th>Tipo Siniestro</th>
				<th>Fecha Siniestro</th>
				<th>Relato</th>
				<th>Calificación</th>
				<th>Ver</th>
				<th>Sel</th>
			</tr>
			</thead>
			<tbody>
			<g:each in="${siniestros}" status="i" var="siniestro">
				<tr>
					<td>${siniestro?.tipoSiniestro}</td>
					<td>${cl.adexus.helpers.FormatosHelper.fechaCortaStatic(siniestro?.fecha)}</td>
					<td>${siniestro?.relato}</td>
					<td>${siniestro?.calificacion?.descripcion}</td>
					<td>
						<button title="Ver" class="pure-button pure-button-secondary" onclick="document.forms[0].siniestroId.value='${siniestro?.id}';document.forms[0].action='verSiniestro';document.forms[0].submit();">
							<i class="icon-info-sign"></i>
						</button>
					</td>
					<td><g:radio name="myGroup" value="${siniestro?.id}" onclick="document.forms[0].siniestroId.value = this.value;" /></td>
				</tr>
			</g:each>
			</tbody>
		</table>
	</fieldset>
	
	<div class="pure-g-r">
		<isl:button action="cu03" value="Terminar" tipo="terminar"/>
		<isl:button action="cu03" value="Siguiente"/>
	</div>
</g:form>
