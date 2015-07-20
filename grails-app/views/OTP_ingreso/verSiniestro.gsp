<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form name="dp02" class="pure-form pure-form-stacked" >
<g:hiddenField name="runTrabajador" value="${siniestro?.trabajador?.run}"/>
	<isl:header_identification 
		tRun="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}"
		tNombre="${FormatosISLHelper.nombreCompletoStatic(siniestro?.trabajador)}"
		eRut="${FormatosISLHelper.runFormatStatic(siniestro?.empleador?.rut)}"
		eNombre="${siniestro?.empleador?.razonSocial}"
		siniestro="${siniestro?.id}"
		/>
	<fieldset>
		<g:if test="${diats?.size() > 0 || dieps?.size() > 0}">
 		<legend>Denuncias</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Tipo</th>
							<th>Id</th>
							<th>Fecha Denuncia</th>
							<th>Denunciante</th>
							<th>Clasificacion Denunciante</th>
							<th>Ver</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${diats}" var="d">
					    <tr>
					    	<td>DIAT</td>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.fechaEmision)}</td>
					   		<td>${FormatosISLHelper.nombreCompletoStatic(d.denunciante)}</td>
					   		<td>${d.calificacionDenunciante?.descripcion}</td>
					   		<td><g:link action="r01" controller="Denuncia" id="${d.id}" params="${[tipo: 'DIAT', volver: 'OTP_ingreso:verSiniestro:' + siniestro?.id]}">Ver más</g:link></td>
					   	</tr>
					</g:each>
					<g:each in="${dieps}" var="d">
					    <tr>
					    	<td>DIEP</td>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.fechaEmision)}</td>
					   		<td>${FormatosISLHelper.nombreCompletoStatic(d.denunciante)}</td>
					   		<td>${d.calificacionDenunciante?.descripcion}</td>
							<td><g:link action="r01" controller="Denuncia" id="${d.id}" params="['tipo': 'DIEP']">Ver más</g:link></td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		<g:if test="${siniestro.opa!=null || siniestro.opaep!=null || odas?.size() > 0 }">
 		<legend>OPA / ODA</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Tipo</th>
							<th>Id</th>
							<th>Inicio Vigencia</th>
							<th>Duracion</th>
							<th>Prestador</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:if test="${siniestro.opa!=null}">
					    <tr>
					    	<td>OPA</td>
					    	<td>${siniestro.opa.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(siniestro.opa.inicioVigencia)}</td>
					   		<td>${siniestro.opa.duracionDias} dias</td>
					   		<td>${FormatosISLHelper.nombrePrestadorStatic(siniestro.opa.centroAtencion.prestador) }</td>
					   		<td><g:link action="r02" controller="Denuncia" id="${siniestro.opa.id}" params="['tipo': 'OPA']">Ver más</g:link></td>
					   	</tr>
					</g:if>
					<g:if test="${siniestro.opaep!=null}">
					    <tr>
					    	<td>OPAEP</td>
					    	<td>${siniestro.opaep.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(siniestro.opaep.inicioVigencia)}</td>
					   		<td>${siniestro.opaep.duracionDias} dias</td>
					   		<td>${FormatosISLHelper.nombrePrestadorStatic(siniestro.opaep.centroAtencion.prestador) }</td>
					   		<td><g:link action="r02" controller="Denuncia" id="${siniestro.opaep.id}" params="['tipo': 'OPAEP']">Ver más</g:link></td>
					   	</tr>
					</g:if>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>		
		
		<g:if test="${docsDIAT?.size() > 0 || docsDIEP?.size() > 0 }">
 		<legend>Documentos Adicionales</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" style="width:80%">
					<thead>
						<tr>
							<th>Id</th>
							<th>Descripcion</th>
							<th>Relacionado con</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${docsDIAT}" var="d">
					    <tr>
					    	<td>${d.id}</td>
					   		<td>${d.descripcion}</td>
					   		<td>DIAT-${d.denunciaAT.id}</td>
					   		<td><g:link action="verDocumentoAdjunto" controller="OTP_ingreso" id="${d.id}">Ver más</g:link></td>
					   	</tr>
					</g:each>
					<g:each in="${docsDIEP}" var="d">
					    <tr>
					    	<td>${d.id}</td>
					   		<td>${d.descripcion}</td>
					   		<td>DIEP-${d.denunciaEP.id}</td>
					   		<td><g:link action="verDocumentoAdjunto" controller="OTP_ingreso" id="${d.id}">Ver más</g:link></td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button  tipo="volver" action="dp03"/>
	</div>
</g:form>

