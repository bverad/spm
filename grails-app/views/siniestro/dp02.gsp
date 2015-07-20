<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form name="dp02" class="pure-form pure-form-stacked" >
	<isl:header_identification 
		tRun="${FormatosISLHelper.runFormatStatic(trabajador?.run)}"
		tNombre="${FormatosISLHelper.nombreCompletoStatic(trabajador)}"
		/>
	<fieldset>
		<legend>Siniestros del trabajador</legend>

			<g:if test="${siniestros != null}">
				<div class="pure-g-r">
					<div class="pure-u-1">
						<table class="pure-table pure-table-bordered pure-table-striped">
							<thead>
								<tr>
									<th>Cantidad de siniestros</th>
									<th>${siniestros.size()}</th>
								</tr>
							</thead>
						</table></br>
					
						<table class="pure-table pure-table-bordered pure-table-striped">
						<thead>
							<tr>
								<th>&nbsp;</th>
								<th>Siniestro</th>
								<th>Fecha Siniestro</th>
								<th>Empleador</th>
							</tr>
						</thead>
						<tbody>
						<g:each in="${siniestros}" var="s">
							<tr>
								<td><g:radio name="id" value="${s.id}"  required="required" /></td>
								<td>${s.id}-${(s.esEnfermedadProfesional?'EP':'AT')}</td>
								<td>${FormatosISLHelper.fechaCortaStatic(s.fecha)}</td>
								<td>${s.empleador.razonSocial} 
									[${FormatosISLHelper.runFormatStatic(s.empleador.rut)}]</td>
							</tr>
						</g:each>
						</tbody>
						</table>
					</div>
				</div>
			</g:if>

			<g:else>
				<div>
					<div>
						<p>No hay siniestros para este RUT.</p>
					</div>
				</div>
			</g:else>

	</fieldset>

	<g:if test="${siniestros != null}">
		<div class="pure-g-r">
			<isl:button  action="cu02"/>
		</div>
	</g:if>

	<g:else>
		<div class="pure-g-r">
			<isl:button  tipo="terminar" action="index"/>
		</div>
	</g:else>
</g:form>

