<meta name='layout' content='isl_layout_modal_plain'/>
<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form class="pure-form pure-form-stacked">
	<fieldset>
	<legend>Información de Análisis de Seguimiento</legend>
	</fieldset>
</g:form>	
<table class="pure-table" width="100%">
<thead>
	<tr>
		<th>Fecha</th>
		<th>N° B/F</th>
		<th>Concepto</th>
		<th>Rut Proveedor</th>
		<th>Nombre o R.S.</th>
		<th>V. Doc</th>
		<th>V. Sol</th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${detalles}" status="i" var="detalle">
		<tr>
			<td>${FormatosISLHelper.fechaCortaStatic(detalle?.fechaGasto)}</td>
			<td>${detalle?.numero}</td>
			<td>${detalle?.concepto}</td>
			<td>${FormatosISLHelper.runFormatStatic(detalle?.rutProveedor)}</td>
			<td>${detalle?.nombreProveedor}</td>
			<td>${FormatosISLHelper.montoStatic(detalle?.valorDocumento)}</td>
			<td>${FormatosISLHelper.montoStatic(detalle?.valorSolicitado)}</td>
		</tr>
	</g:each>
	</tbody>
	<tfoot> 
		<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
			<th colspan="6" align="right">Suma Gastos</th>
			<th align="right">${FormatosISLHelper.montoStatic(sumaGastos)}</th>
		</tr>
		<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
			<th colspan="6" align="right">Monto Solicitado</th>
			<th align="right">${FormatosISLHelper.montoStatic(reembolso?.montoSolicitado)}</th>
		</tr>
	</tfoot>
</table>				
