<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp03" class="pure-form pure-form-stacked" >
	<isl:header_identification 
		siniestro="${siniestro.id.toString()}"
		tipoSin="${siniestro.esEnfermedadProfesional ? 'Enfermedad Profesional': 
			(siniestro.diatOA?.esAccidenteTrayecto ? 'Accidente de Trayecto' : 'Accidente del Trabajo')}" 
		aFechaS="${FormatosISLHelper.fechaCortaStatic(siniestro.fecha)}"
		tRun="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}"
		tNombre="${FormatosISLHelper.nombreCompletoStatic(siniestro?.trabajador)}"
		eRut="${FormatosISLHelper.runFormatStatic(siniestro?.empleador?.rut)}"
		eNombre="${siniestro?.empleador?.razonSocial}"
		/>
	<fieldset>
	<g:hiddenField name="siniestroControllerHome" value="${siniestroControllerHome}"/>
	<g:hiddenField name="runTrabajador" value="${siniestro?.trabajador?.run}"/>
	
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
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${diats}" var="d">
					    <tr>
					    	<td>DIAT</td>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.fechaEmision)}</td>
					   		<td>${FormatosISLHelper.nombreCompletoStatic(d.denunciante)}</td>
					   		<td>${d.calificacionDenunciante?.descripcion }</td>
					   		<td><g:link action="r01" controller="denuncia" id="${d.id}" params="['tipo': 'DIAT']">Ver más</g:link></td>
					   	</tr>
					</g:each>
					<g:each in="${dieps}" var="d">
					    <tr>
					    	<td>DIEP</td>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.fechaEmision)}</td>
					   		<td>${FormatosISLHelper.nombreCompletoStatic(d.denunciante)}</td>
					   		<td>${d.calificacionDenunciante?.descripcion }</td>
					   		<td><g:link action="r01" controller="denuncia" id="${d.id}" params="['tipo': 'DIEP']">Ver más</g:link></td>
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
							<th>Fin Vigencia</th>
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
					   		<td>${FormatosISLHelper.fechaCortaStatic(siniestro.opa.inicioVigencia + siniestro.opa.duracionDias)} </td>
					   		<td>${FormatosISLHelper.nombrePrestadorStatic(siniestro.opa.centroAtencion.prestador) }</td>
					   		<td><g:link action="r02" controller="denuncia" id="${siniestro.opa.id}" params="['tipo': 'OPA']">Ver más</g:link></td>
					   	</tr>
					</g:if>
					<g:if test="${siniestro.opaep!=null}">
					    <tr>
					    	<td>OPAEP</td>
					    	<td>${siniestro.opaep.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(siniestro.opaep.inicioVigencia)}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(siniestro.opaep.inicioVigencia + siniestro.opaep.duracionDias)} </td>
					   		<td>${FormatosISLHelper.nombrePrestadorStatic(siniestro.opaep.centroAtencion.prestador) }</td>
					   		<td><g:link action="r02" controller="denuncia" id="${siniestro.opaep.id}" params="['tipo': 'OPAEP']">Ver más</g:link></td>
					   	</tr>
					</g:if>
					<g:each in="${odas}" var="d">
					    <tr>
					    	<td>ODA</td>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.inicioVigencia)}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.terminoVigencia)}</td>
					   		<td>${FormatosISLHelper.nombrePrestadorStatic(d.centroAtencion.prestador)}</td>
					   		<td><g:link action="genPdfODA" odaId="${d.id}" params='["odaId": "${d.id}"]'>Ver más</g:link></td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		
		<g:if test="${cuentasMedicas?.size() > 0 }">
 		<legend>Cuentas Médicas</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Nro. Cuenta</th>
							<th>Fecha Cuenta</th>
							<th>Prestador</th>
							<th>Monto Cobrado</th>
							<th>Monto Aprobado</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${cuentasMedicas}" var="d">
					    <tr>
					    	<td>${d.folioCuenta}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.creadoEl)}</td>
					   		<td>${FormatosISLHelper.nombrePrestadorStatic(d.centroSalud.prestador) }</td>
					   		<td>${FormatosHelper.montosStatic(d.valorCuenta)}</td>
					   		<td>${(d.fechaAceptacion != null)  ? ((d.esAprobada) ? FormatosHelper.montosStatic(d.valorCuentaAprobado) : "Rechazada") : "En Proceso"}</td>
					   	</tr>
					</g:each>
					    <tr>
					    	<td colspan = 3>Total: </td>
					   		<td>${FormatosHelper.montosStatic(sumCMMontosCobr)}</td>
					   		<td>${FormatosHelper.montosStatic(sumCMMontosAprob)}</td>
					   	</tr>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		
		<g:if test="${facturas?.size() > 0 }">
 		<legend>Facturas</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Nro. Factura</th>
							<th>Fecha Factura</th>
							<th>Prestador</th>
							<th>Monto enviado a pago</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${facturas}" var="d">
					    <tr>
					    	<td>${d.folio}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.creadoEl)}</td>
					   		<td>${FormatosISLHelper.nombrePrestadorStatic(d.prestador) }</td>
					   		<td>${(d.status != null && d.status.equals("nok")) ? "Rechazada" : ((d.fechaEnvioPago!= null) ? FormatosISLHelper.montoFactEnvPagoStatic(d) : "En Proceso")}</td>
					   	</tr>
					</g:each>
					    <tr>
					    	<td colspan = 3>Total enviado a pago:</td>
					   		<td>${FormatosHelper.montosStatic(sumFactMontoAprob)}</td>
					   	</tr>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		
		<g:if test="${reembolsos?.size() > 0 }">
 		<legend>Reembolsos</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Nro. Solicitud</th>
							<th>Fecha Solicitud</th>
							<th>Solicitante</th>
							<th>Monto Solicitado</th>
							<th>Monto Aprobado</th>
							<th>Estado</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${reembolsos}" var="d">
					    <tr>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.creadoEl)}</td>
					   		<td>${FormatosISLHelper.nombreCompletoStatic(d.solicitante)}</td>
					   		<td>${FormatosHelper.montosStatic(d.montoSolicitado)}</td>
					   		<td>${(d.fechaAprobacion!= null) ? FormatosISLHelper.montoReemAprobStatic(d) : "--"}</td>
					   		<td>${(d.fechaAprobacion!= null) ? "Enviado a Pago" : "Pendiente"}</td>
					   		<td><g:link action="genPdfReembolso" reembolsoId="${d.id}" params='["reembolsoId": "${d.id}"]'>Ver más</g:link></td>
					   	</tr>
					</g:each>
				    <tr>
				    	<td colspan = 3>Total :</td>
					   	<td>${FormatosHelper.montosStatic(sumReemMontosSolic)}</td>
					   	<td>${FormatosHelper.montosStatic(sumReemMontosAprob)}</td>
				   		<td colspan = 2></td>
				   	</tr>
					
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		
		<g:if test="${reca!=null }">
 		<legend>Calificación</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Nro. Resolución</th>
							<th>Fecha Resolución</th>
							<th>Calificación</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:if test="${reca!=null}">
					    <tr>
					    	<td>${reca.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(reca.creadoEl)}</td>
					   		<td>${reca.calificacion?.descripcion}</td>
					   		<td><g:link action="genPdfReca" recaId="${reca.id}" siniestroId= "${siniestro.id}" params='["recaId": "${reca.id}", "siniestroId": "${siniestro.id}"]'>Ver más</g:link></td>
					   	</tr>
					</g:if>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		
		<g:if test="${relas?.size() > 0 }">
 		<legend>Reposos Laborales</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Nro. Reposo Laboral</th>
							<th>Fecha Desde</th>
							<th>Fecha Hasta</th>
							<th>Período de Reposo</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${relas}" var="d">
					    <tr>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.inicioReposo)}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.terminoReposo)}</td>
					   		<td>${d.nDias?.toString()} dias</td>
					   		<td><g:link action="genPdfRela" relaId="${d.id}" params='["relaId": "${d.id}"]'>Ver más</g:link></td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		
		<g:if test="${allas?.size() > 0 }">
 		<legend>Altas Laborales</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Nro. Alta Laboral</th>
							<th>Fecha Alta</th>
							<th>Condiciones Prescritas</th>
							<th>Período de Alta</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${allas}" var="d">
					    <tr>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.fechaAlta)}</td>
					   		<td>${d.tipoCondicion}</td>
					   		<td>${d.periodoCondiciones} dias</td>
					   		<td><g:link action="genPdfAlla" allaId="${d.id}" params='["allaId": "${d.id}"]'>Ver más</g:link></td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		
		<g:if test="${almes?.size() > 0 }">
 		<legend>Altas Médicas</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped" width="80%">
					<thead>
						<tr>
							<th>Nro. Alta Médica</th>
							<th>Fecha Alta</th>
							<th>Tipo Alta</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${almes}" var="d">
					    <tr>
					    	<td>${d.id}</td>
					   		<td>${FormatosISLHelper.fechaCortaStatic(d.fechaOtorgamiento)}</td>
					   		<td>${d.tipoAlta}</td>
					   		<td><g:link action="genPdfAlme" almeId="${d.id}" params='["almeId": "${d.id}"]'>Ver más</g:link></td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
		
		
		<g:if test="${docsDIAT?.size() > 0 || docsDIEP?.size() > 0 }">
 		<legend>Documentos Adicionales</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered pure-table-striped"  width="80%">
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
					   		<td><g:link action="viewDoc" id="${d.id}" >Ver más</g:link></td>
					   	</tr>
					</g:each>
					<g:each in="${docsDIEP}" var="d">
					    <tr>
					    	<td>${d.id}</td>
					   		<td>${d.descripcion}</td>
					   		<td>DIEP-${d.denunciaEP.id}</td>
					   		<td><g:link action="viewDoc" id="${d.id}" >Ver más</g:link></td>
					   	</tr>
					</g:each>
					</tbody>
					</table>
				</div>
			</div>
		</g:if>
	</fieldset>	
	<g:if test="${siniestroControllerHome}">
		<div class="pure-g-r">
			<isl:button  tipo="volver" action="volver"/>
		</div>	
	</g:if>
	<g:else>
		<div class="pure-g-r">
			<isl:button  tipo="terminar" action="index"/>
		</div>
	</g:else>	
</g:form>

