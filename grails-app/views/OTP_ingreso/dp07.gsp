<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>

<g:javascript src="OTP/ingreso_dp07.js" />
<g:form name="cu07" class="pure-form pure-form-stacked" >

	<fieldset>
 		<legend>Analiza Reembolso Seguimiento</legend>

		<div class="form-subtitle">Datos Trabajador</div>
		<div class="formulario-fieldset">
			<label for="nombreTrabajador" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Nombre Trabajador</label>
			<input type="text" name="nombreTrabajador" disabled="disabled" style="display: inline-block; width: 517px;" value="${trabajadorNombreCompleto}"/><br/>
			
			<label for="runTrabajador" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">RUN Trabajador</label>
			<input type="text" name="runTrabajador" disabled="disabled" style="display: inline-block; width: 200px;" value="${FormatosHelper.runFormatStatic(reembolso?.siniestro?.trabajador?.run)}"/><br/>

			<label for="siniestroNumero" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Siniestro N°</label>
			<input type="text" name="siniestroNumero" style="display: inline-block; width: 200px;" disabled="disabled" value="${reembolso?.siniestro?.id}" />
			<label for="siniestroTipo" style="display: inline-block; width: 100px; text-align: right; margin-right: 10px;">Tipo Siniestro</label>
			<input type="text" name="siniestroTipo" style="display: inline-block; width: 200px;" disabled="disabled" value="${tipoSiniestro}"/><br />
		</div>
		
		<div class="form-subtitle">Datos Solicitante</div>
		<div class="formulario-fieldset">
			<label for="nombreSolicitante" style="display: inline-block; width: 200px; text-align: right; margin-right: 10px;">Nombre Solicitante</label>
			<input type="text" name="nombreSolicitante" disabled="disabled" style="display: inline-block; width: 500px;" value="${solicitanteNombreCompleto}"/><br />
			
			<label for="runSolicitante" style="display: inline-block; width: 200px; text-align: right; margin-right: 10px;">RUN Solicitante</label>
			<input type="text" name="runSolicitante" disabled="disabled" style="display: inline-block; width: 200px;" value="${FormatosHelper.runFormatStatic(reembolso?.solicitante?.run)}"/><br/>

			<label for="relacionConTrabajador" style="display: inline-block; width: 200px; text-align: right; margin-right: 10px;">Relacion con Trabajador</label>
			<input type="text" name="relacionConTrabajador" style="display: inline-block; width: 200px;" disabled="disabled" value="${relacionDelSolicitante}" />
		</div>
		<g:hiddenField name="reembolsoId" value="${reembolso.id}"/>
		
		<legend>Detalle de Reembolso</legend>
			<div style="margin: 10px 10px 10px 10px;">
			
			   	<g:link action="verFormularioReembolso" 
			   			controller="OTP_revision"
	   					class="pure-button pure-button-secondary"
	   					id="${reembolso.id}">
	   				Ver Formulario de Reembolso
				</g:link>
			
			
				<table class="pure-table" width="100%" id="detalle-table">
				<thead>
					<tr>
						<th>Fecha</th>
						<th>N° B/F</th>
						<th>Concepto</th>
						<th>Rut Proveedor</th>
						<th>Nombre o R.S.</th>
						<th>V. Doc</th>
						<th>V. Sol</th>
						<th>V. Aprob</th>
						<th>Ver</th>
						<th>Comentario</th>
					</tr>
					</thead>
					<tbody>
					<g:each in="${detalles}" status="i" var="detalle">
						<tr id="tr_${detalle.id}">
							<td>${FormatosHelper.fechaCortaStatic(detalle?.fechaGasto)}</td>
							<td>${detalle?.numero}</td>
							<td>${detalle?.concepto}</td>
							<td>${FormatosHelper.runFormatStatic(detalle?.rutProveedor)}</td>
							<td>${detalle?.nombreProveedor}</td>
							<td>${FormatosISLHelper.montoStatic(detalle?.valorDocumento)}</td>
							<td>${FormatosISLHelper.montoStatic(detalle?.valorSolicitado)}</td>
							<td>
								<g:textField class="valor-aprobado" name="valorAprobado_${detalle.id}"
											 style="width: 100%; text-align:right;" value="${detalle.valorAprobado}"/>
							</td>
							<td>
								<button title="Ver" class="pure-button pure-button-secondary" onclick="window.location.href = 'verDetalleAdjunto?detalleId=${detalle.id}'; return false;">
									<i class="icon-info-sign"></i>
								</button>
							</td>
							<td>
								<g:textField name="comentario_${detalle.id}"
											 style="width: 100%; text-align:right;" value="${detalle.comentario}"/>
							</td>
						</tr>
					</g:each>
					</tbody>
					<tfoot> 
						<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
							<th colspan="6" align="right"></th>
							<th align="right">${FormatosISLHelper.montoStatic(sumaGastos)}</th>
							<th align="right" id="sumaAprobados">${FormatosISLHelper.montoStatic(sumaAprobados)}</th>
							<th colspan="2" align="right"></th>
						</tr>
					</tfoot>
				</table>
			</div>
	</fieldset>
	
	<div class="pure-g-r">
		<isl:button action="cu07" value="Informar Análisis"/>
	</div>
</g:form>
