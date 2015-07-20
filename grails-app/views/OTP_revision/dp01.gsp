<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>

<g:javascript src="OTP/revision_dp01.js" />
<g:javascript src="tinybox.js" />
<g:form name="dp01" class="pure-form pure-form-stacked" >

	<fieldset>
 		<legend>Generar Informe de Pago</legend>

		<table class="pure-table" width="100%" id="detalle-table">
			<thead>
				<tr>
					<th>Fecha Solicitud</th>
					<th>ID Siniestro</th>
					<th>Rut Cobrador</th>
					<th>Nombre Cobrador</th>
					<th>M. Solicitado $</th>
					<th>M. Autorizado $</th>
					<th>Medio Pago</th>
					<th>Pagar</th>
					<th>Ver</th>
					<th>Análisis Seguimiento</th>
				</tr>
				</thead>
				<tbody>
				<g:each in="${reembolsos}" status="i" var="reembolso">
					<tr id="tr_${reembolso?.id}">
						<td>${FormatosISLHelper.fechaCortaStatic(reembolso?.fechaAprobacion)}</td>
						<td>${reembolso?.siniestro?.id}</td>
						<td>${reembolso?.cobrador?.run}</td>
						<td align="right">${FormatosISLHelper.nombreCompletoStatic(reembolso?.cobrador)}</td>
						<td align="right">${FormatosISLHelper.montoStatic(reembolso?.montoSolicitado)}</td>
						<td align="right">${FormatosISLHelper.montoStatic(reembolso?.montoAutorizado)}</td>
						<td>${reembolso?.tipoPagoDeposito ? 'Deposito' : 'Presencial'}</td>
						<td style="text-align: center;">
							<input type="checkbox" class="pagar-checkers" id="reembolso_${reembolso?.id}" name="reembolso_${reembolso?.id}"/>
						</td>
						<td align="center">
							<button title="Ver" class="pure-button pure-button-secondary"
							        onclick="window.location.href ='verFormularioReembolso/${reembolso.id}'; return false;">
								<i class="icon-info-sign"></i>
							</button>
						</td>
						<td align="center">
							<a title="Ver Análisis Seguimiento"
							   onclick="TINY.box.show({iframe:'../OTP_revision/analisis_seguimiento?reembolsoId=${reembolso.id}',boxid:'frameless',width:1000,height:300,fixed:false,maskid:'bluemask',maskopacity:40})" >
							   	<i class="icon-2x icon-info-sign color-success" style="cursor: pointer;"></i>
							</a>
						</td>
					</tr>
				</g:each>
				</tbody>
				<tfoot> 
					<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
						<th colspan="5" align="right"></th>
						<th align="right">${FormatosISLHelper.montoStatic(sumaAutorizados)}</th>
						<th align="right" colspan="4" id="sumaAPagar">${FormatosISLHelper.montoStatic(0)}</th>
					</tr>
					<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
						<th colspan="5" align="right"></th>
						<th align="right">Total</th>
						<th align="right" colspan="4">Total a enviar a Pago</th>
					</tr>
				</tfoot>
			</table>
	</fieldset>
	
	<div class="pure-g-r">
		<isl:button action="r01" value="Enviar a Pago"/>
	</div>
</g:form>