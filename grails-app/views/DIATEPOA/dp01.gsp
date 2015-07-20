<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp02" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>Siniestros sin Denuncia OA</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<th>Siniestro</th>
							<th>Fecha Siniestro</th>
							<th>Trabajador</th>
							<th>Empleador</th>
							<th>Usuario</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${siniestros}" var="s">
					    <tr>
					    	<td><g:radio name="id" value="${s.id}"  required="required" /></td>
					   		<td>${s.id}-${(s.esEnfermedadProfesional?'EP':'AT')}</td>
					   		<td>${FormatosHelper.fechaCortaStatic(s.fecha)}</td>
					   		<td>${s.trabajador.nombre+' '+s.trabajador.apellidoPaterno+' '+s.trabajador.apellidoMaterno} 
					   			[${FormatosHelper.runFormatStatic(s.trabajador.run)}]</td>
					   		<td>${s.empleador.razonSocial} 
					   			[${FormatosHelper.runFormatStatic(s.empleador.rut)}]</td>
					   		<td>${s.usuario}</td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>

	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="r02"/>
	</div>
	
	
	<!-- Despliegue de informacion -->
	<g:if test="${flash.default}">
		<fieldset>
			<div class="pure-u-1 messages">
				<ul>
					<li><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></li>
				</ul>
			</div>
		</fieldset>
	</g:if>
	
</g:form>

