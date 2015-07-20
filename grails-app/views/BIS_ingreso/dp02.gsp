<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:form name="dp02" class="pure-form pure-form-stacked" >

	<g:hiddenField name="siniestroId"/>
	<g:hiddenField name="bisId" value="${bis_id}" />
	<g:hiddenField name="runTrabajador" value="${trabajador?.run}"/>
	
	<fieldset>		
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
		

		<legend>Historial Trabajador</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:if test="${siniestros?.size() > 0}">	
					<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th width="15%">Tipo Siniestro</th>
							<th width="10%">Fecha Siniestro</th>
							<th width="40%">Relato</th>
							<th width="15%">Calificación</th>
							<th width="1%">Ver</th>
							<th width="1%">&nbsp;</th>
						</tr>
						</thead>
						<tbody>
						<g:each in="${siniestros}" status="i" var="siniestro">
							<tr>
								<td>${siniestro?.tipoSiniestro}</td>
								<td>${cl.adexus.helpers.FormatosHelper.fechaCortaStatic(siniestro?.fecha)}</td>
								<td>${siniestro?.relato}</td>
								<td>${siniestro?.calificacion}</td>
								<td>
									<button title="Ver" class="pure-button pure-button-secondary" onclick="document.forms[0].siniestroId.value='${siniestro?.id}';document.forms[0].action='verSiniestro';document.forms[0].submit();">
										<i class="icon-search"></i>
									</button>
								</td>
								<td><g:radio name="myGroup" required="required" value="${siniestro?.id}" onclick="document.forms[0].siniestroId.value = this.value;" /></td>
							</tr>
						</g:each>
						</tbody>
					</table>
				</g:if>
				<g:else>
					<div>
						<div>
							<p>No hay archivos adjuntos </p>
						</div>
					</div>
				</g:else>
			</div>
		</div>					
	</fieldset>
	
	<div class="pure-g-r">
		<g:actionSubmit id="terminar" value="Terminar"  action="cu02" class="pure-button pure-button-error" formnovalidate="formnovalidate"  />
		<isl:button action="postDp02" value="Siguiente"/>
	</div>
</g:form>
