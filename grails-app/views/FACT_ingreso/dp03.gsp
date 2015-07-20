<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:javascript src="FACT/ingreso_dp03.js" />
<g:form name="dp03" class="pure-form pure-form-stacked" >

		<fieldset>
		<legend>Decide Solicitar ${tipoDocumento}</legend>

		<input type="hidden" name="facturaId" id="facturaId" value="${factura?.id}" />
		<input type="hidden" name="taskId" value="${taskId}" />
		<input type="hidden" name="tipoDocumento" value="${tipoDocumento}" />
		<input type="hidden" name="rutPrestador" value="${rutPrestador}" />

		<isl:textOutput cols="1-8"  rotulo="Folio Factura Origen" valor="${factura?.folio}" />
		<isl:textOutput cols="1-8"  rotulo="RUT Prestador" valor="${rutPrestador}" />
		<isl:textOutput cols="1-8"  rotulo="Nombre Prestador" valor="${nombrePrestador}" />
		<isl:textOutput cols="2-8"  rotulo="Dirección Casa Matriz" valor="${factura?.prestador?.direccion}" />
		<isl:textOutput cols="2-8"  rotulo="Email" valor="${factura?.prestador?.email}" />
		<isl:textOutput cols="1-8"  rotulo="Teléfono" valor="${factura?.prestador?.telefono}" />
		<div class='salto-de-linea'></div>
		<br />

		<isl:textInput cols="1-8"  nombre="notaCreditoNumero" requerido="true" rotulo="${tipoDocumento} N&deg;" valor=""/>
		<div class='salto-de-linea'></div>
		<br />

		<table id="tablaCM" class="pure-table" width="60%">
			<thead>
				<tr>
					<th> Cuenta Médica </th>
					<th> Facturado </th>
					<th> Cuenta Medica </th>
					<th> ${tipoDocumento} </th>
					<th> Diferencia </th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${cms}" var="s" status="i">
					<tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
						<td align="center" nowrap="nowrap">${s.folioCuenta}</td>
						<td align="right" nowrap="nowrap">${FormatosISLHelper.montoStatic(s.facturadoPesos)}</td>
						<td align="right" nowrap="nowrap">${FormatosISLHelper.montoStatic(s.cuentaMedicaPesos)}</td>
						<td align="right" nowrap="nowrap">
							<input type="text" class="detalle-fct-ndc" id="detalle-fct-ndc-${s.idDetalleFactura}" 
								   name="detalle-fct-ndc-${s.idDetalleFactura}" valor="" style="text-align: right;" />
						</td>
						<td> &nbsp; </td>
					</tr>
				</g:each>
			</tbody>
			<tfoot>
				<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
					<th> Total: </th>
					<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.totalFactura)} </th>
					<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.sumaCMS)} </th>
					<th id="notaCreditoValorTH" align="right"> &nbsp; </th>
					<th id="diferenciaPesosTH"  align="right"> &nbsp; </th>
				</tr>
			</tfoot>
		</table>

		<br />

		<g:hiddenField name="totalFactura" value="${agregados?.totalFactura}"/>
		<g:hiddenField name="sumaCuentasMedicas" value="${agregados?.sumaCMS}"/>
		<g:hiddenField name="notaCreditoValor" value=""/>
		<g:hiddenField name="diferenciaPesos" value=""/>
	</fieldset>

	<div class="pure-g-r">
		<input type="submit" id="btnAceptarDocumento" name="_action_cu03a" value="Aceptar ${tipoDocumento}" class="pure-button pure-button-success">
		<isl:button id="btnRechazarDocumento" action="cu03r" value="Rechazar ${tipoDocumento}"/>
	</div>
</g:form>
