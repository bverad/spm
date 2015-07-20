<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form name="dp02" class="pure-form pure-form-stacked" >
	<g:hiddenField name="cuentaMedicaId" value="${params.cuentaMedicaId}"/>
	<g:hiddenField name="taskId" value="${params.taskId}"/>
	
	<isl:header_cm	idSiniestro="${cuentaMedica?.id}"
					tRun="${FormatosISLHelper.runFormatStatic(trabajador?.run)}"
					tNombreCompleto="${FormatosISLHelper.nombreCompletoStatic(trabajador)}"
					pRut="${FormatosISLHelper.rutPrestadorStatic(prestador)}"
					pCentro="${centroSalud?.nombre}"
					pNombre="${FormatosISLHelper.nombrePrestadorStatic(prestador)}"
					opa="${cuentaMedica?.opas}"
					opaep="${cuentaMedica?.opaeps}"
					odas="${cuentaMedica?.odas}"
					folioCuenta="${cuentaMedica?.folioCuenta}"
					mtoCuenta="${cuentaMedica?.valorCuenta}"/>
	<fieldset>
		<legend>Ingresar Detalle Cuenta Médica</legend>

		<isl:calendar  cols="1-8"  nombre="fecha"  rotulo="Fecha Prestación" requerido="true" valor="${detalleCM?.fecha}"/>
		<isl:textInput cols="1-8"  nombre="hora"   tipo="hora" rotulo="Hora Prestación" requerido="true" valor="${FormatosHelper.horaCortaStatic(detalleCM?.fecha)}"/>
		<isl:textInput cols="1-8"  nombre="codigo" rotulo="Código Prestación" valor="${detalleCM?.codigo}"/>
		<isl:textInput cols="4-8"  nombre="glosa"  rotulo="Glosa" valor="${detalleCM?.glosa}"/>
		<div class='salto-de-linea'></div>

		<isl:textInput cols="1-8"  nombre="cantidad"          rotulo="Cantidad"         requerido="true" tipo="numero" valor="${detalleCM?.cantidad}"/>
		<isl:textInput cols="1-8"  nombre="valorUnitario"     rotulo="Valor Unitario"   requerido="true" tipo="numero" valor="${detalleCM?.valorUnitario}"/>
		<isl:textInput cols="1-8"  nombre="descuentoUnitario" rotulo="Descuento"        tipo="numero"    valor="${detalleCM?.descuentoUnitario}"/>
		<isl:textInput cols="1-8"  nombre="recargoUnitario"   rotulo="Recargo Horario"  tipo="numero"    valor="${detalleCM?.recargoUnitario}"/>
		<isl:textInput cols="1-8"  nombre="valorTotal"        rotulo="Valor Total Neto" requerido="true" tipo="numero" valor="${detalleCM?.valorTotal}"/>

		<div class="pure-u-1-8" style="padding-top: 17px; padding-left: 5px;">
			<isl:button action="agregaDetalleCuentaMedica" tipo='Agregar'/>
		</div>
		<div class='salto-de-linea'></div>

		<div class="pure-u-8-8" style="padding-top: 20px;">
			<table id="tablaOXA" class="pure-table" width="100%">
			<thead>
				<tr>
					<th>Fecha</th>
					<th>Hora</th>
					<th>Código</th>
					<th>Glosa</th>
					<th>Cantidad</th>
					<th>V.U.D.</th>
					<th>Descuento</th>
					<th>Recargo Horario</th>
					<th>Valor Total Neto</th>
					<th>Eliminar</th>
				</tr>
				</thead>
				<tbody>
					<g:each var="detalle" in="${detallesCuenta}" status="i">
						<tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
							<td align="center">${FormatosISLHelper.fechaCortaStatic(detalle.fecha)}</td>
							<td align="center">${FormatosISLHelper.horaCortaStatic(detalle.fecha)}</td>
							<td>${detalle.codigo}</td>
							<td>${detalle.glosa}</td>
							<td align="right">${detalle.cantidad}</td>
							<td align="right">${FormatosISLHelper.montoStatic(detalle.valorUnitario)}</td>
							<td align="right">${FormatosISLHelper.montoStatic(detalle.descuentoUnitario)}</td>
							<td align="right">${FormatosISLHelper.montoStatic(detalle.recargoUnitario)}</td>
							<td align="right">${FormatosISLHelper.montoStatic(detalle.valorTotal)}</td>							
							<td align="center">
                				<g:link action="eliminaDetalleCuentaMedica" 
                        			style="text-decoration: none;"
                        			id="${cuentaMedica.id + "_" + detalle.id + "_" + params.taskId}">
                        			<font color="red"><i class="icon-trash icon-2x"></i></font>
								</g:link>
              				</td>
						</tr>
					</g:each>
				</tbody>
				<tfoot>
					<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
						<th colspan="7"></th>
						<th>Total</th>
						<th align="right"> ${FormatosISLHelper.montoStatic(cm?.sumaValores)} </th>
						<th> &nbsp; </th>
					</tr>
				</tfoot>
			</table>
		</div>
	</fieldset>
	<div class="pure-g-r">
		<g:actionSubmit action="postDp02" value="Crear Detalle" class="pure-button pure-button-success" formnovalidate="formnovalidate"/>
	</div>
</g:form>
