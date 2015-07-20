<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:javascript src="FACT/ingreso_dp02.js" />
<g:form name="dp02" action= "postDp02" class="pure-form pure-form-stacked" >

	<fieldset>
		<input type="hidden" name="facturaId" id="facturaId" value="${factura?.id}" />
		<input type="hidden" name="taskId"    value="${taskId}" />

 		<legend>Decide Solicitar Nota Crédito Factura</legend>

		<isl:textOutput cols="1-8"  rotulo="Folio Factura" valor="${factura?.folio}" />
		<isl:textOutput cols="1-8"  rotulo="RUT Prestador" valor="${runPrestador}" />
		<isl:textOutput cols="1-8"  rotulo="Nombre Prestador" valor="${nombrePrestador}" />
		<isl:textOutput cols="2-8"  rotulo="Dirección Casa Matriz" valor="${factura?.prestador?.direccion}" />
		<isl:textOutput cols="2-8"  rotulo="Email" valor="${factura?.prestador?.email}" />
		<isl:textOutput cols="1-8"  rotulo="Teléfono" valor="${factura?.prestador?.telefono}" />
		<div class='salto-de-linea'></div>
		<br />

		<table id="tablaCM" class="pure-table" width="50%">
			<thead>
				<tr>
					<th> Folio Cuenta Médica </th>
					<th> Facturado </th>
					<th> C.M. Aprobada </th>
					<th> Diferencia </th>
					<th> Diferencia % </th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${cms}" var="c" status="i">
					<tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
						<td align="center">${c.id}</td>
						<td align="right">${FormatosISLHelper.montoStatic(c.facturadoPesos)}</td>
						<td align="right">${FormatosISLHelper.montoStatic(c.valorCuentaAprobado)}</td>
						<td align="right">${FormatosISLHelper.montoStatic(c.diferencia)}</td>
						<td align="right"> &nbsp; </td>
					</tr>
				</g:each>
			</tbody>
			<tfoot>
				<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
					<th> Total: </th>
					<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.totalFactura)} </th>
					<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.sumaCMS)} </th>
					<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.diferenciaSuma)} </th>
					<th align="right"> ${agregados?.diferenciaPorcentaje} </th>
				</tr>
			</tfoot>
		</table>
		
		<g:hiddenField name="totalFactura" value="${agregados?.totalFactura}" />
		<g:hiddenField name="sumaCM" value="${agregados?.sumaCMS}" />
		<g:hiddenField name="DiferenciaSuma" value="${agregados?.diferenciaSuma}" />
		<g:hiddenField name="DiferenciaPorciento" value="${agregados?.diferenciaPorcentaje}" />

	</fieldset>

	<div class="pure-g-r">
		<input  id="resolucion" name="resolucion" type="hidden" value="Rechazada" />
		<button id="btnSolicitarNDC" class="pure-button pure-button-secondary" onclick="return false;" style="margin-right: 10px;">Solicitar Nota de Crédito</button>
		<button id="btnSolicitarFactura" class="pure-button pure-button-secondary" onclick="return false;" style="margin-right: 10px;">Solicitar Factura</button>
		<isl:button action="postDp02" value="Rechazar Factura"/>
	</div>
</g:form>

<!--  MODAL PARA SOLICITAR NOTA DE CREDITO -->
<div style="top: -300px; display:none;">
	<div id="modalsrc-ndc" class="yui-pe-content">
		<div id="modal-marco" >
			<g:form class="pure-form pure-form-stacked modalForm" name="modalForm" >
			    <fieldset>
					<div>
						<div id="modal-content">
							
							<isl:textOutput cols="2-8" rotulo="Folio Factura" valor="${factura?.folio}" />
							<isl:textOutput cols="2-8" rotulo="RUT Prestador" valor="${runPrestador}" />
							<isl:textOutput cols="2-8" rotulo="Nombre Prestador" valor="${nombrePrestador}" />
							<div class='salto-de-linea'></div>

							<isl:textOutput cols="3-8" rotulo="Email" valor="${factura?.prestador?.email}" />
							<isl:textOutput cols="1-8" rotulo="Teléfono" valor="${factura?.prestador?.telefono}" />
							<div class='salto-de-linea'></div>
							
							<label style="margin: 0 10px 0 10px;">Comentarios:</label>
								<div style="margin: 0 10px 0 10px;">
									<textarea id="modal-content-textarea-ndc" style="resize: none; height: 100%; width: 100%;"></textarea>
								</div>
						</div>
						<br />

						<div style="margin: 0 10px 0 10px;">
							<table id="tablaCM" class="pure-table" width="100%" style="background-color: #cbcbcb;">
								<thead>
									<tr>
										<th> Cuenta Médica </th>
										<th> Facturado </th>
										<th title="Valor Cuenta Médica"> Valor C.M. </th>
										<th title="Diferencia"> Dif. </th>
										<th title="Diferencia %"> Dif. % </th>
										<th> Comentarios ISL </th>
									</tr>
								</thead>

								</tbody>
									<g:each in="${cms}" var="c" status="i">
										<tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
											<td align="center">${c.id}</td>
											<td align="right">${FormatosISLHelper.montoStatic(c.facturadoPesos)}</td>
											<td align="right">${FormatosISLHelper.montoStatic(c.valorCuentaAprobado)}</td>
											<td align="center"> &nbsp; </td>
											<td align="center"> &nbsp; </td>
											<td>
												<div style="margin: 0 2px 0 2px;">
													<textarea class="comentario-cm-ndc" id="comentario-cm-ndc-${c.id}" style="resize: none; height: 100%; width: 100%;"></textarea>
												</div>
											</td>
										</tr>
									</g:each>
								</tbody>
								<tfoot>
									<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
										<th> Total: </th>
										<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.totalFactura)} </th>
										<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.sumaCMS)} </th>
										<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.diferenciaSuma)} </th>
										<th align="right"> ${agregados?.diferenciaPorcentaje} </th>
										<th align="center"> &nbsp; </th>
									</tr>
								</tfoot>
							</table>
						</div>

						<div style="margin: 0 10px 0 10px;">						
							<g:hiddenField name="ndcTotalFactura" value="${agregados?.totalFactura}" />
							<g:hiddenField name="ndcSumaCM" value="${agregados?.sumaCMS}" />
							<g:hiddenField name="ndcDiferenciaSuma" value="${agregados?.diferenciaSuma}" />
							<g:hiddenField name="ndcDiferenciaPorciento" value="${agregados?.diferenciaPorcentaje}" />
						
							<g:if test="${agregados}">							
								<div style="font-weight: bold; width: 100%; text-align: right;">
									<g:hiddenField value="${-1 * agregados?.diferenciaSuma}" name="ndcValorNotaCredito" />
									Valor Nota de Crédito: $ ${FormatosISLHelper.montoStatic(agregados?.diferenciaSuma)}
								</div>
							</g:if>

						</div>
					</div>
				</fieldset>
			</g:form>
		</div>
	</div>
</div>

<!--  MODAL PARA SOLICITAR FACTURA -->
<div style="top: -300px; letter-spacing: 0em; display:none;">
	<div id="modalsrc-factura" class="yui-pe-content">
		<div id="modal-marco" >
			<g:form class="pure-form pure-form-stacked modalForm" name="modalForm" >
			    <fieldset>
					<div>
						<div id="modal-content">

							<isl:textOutput cols="2-8" rotulo="Folio Factura" valor="${factura?.folio}" />
							<isl:textOutput cols="2-8" rotulo="RUT Prestador" valor="${runPrestador}" />
							<isl:textOutput cols="2-8" rotulo="Nombre Prestador" valor="${nombrePrestador}" />
							<div class='salto-de-linea'></div>
							
							<isl:textOutput cols="3-8" rotulo="Email" valor="${factura?.prestador?.email}" />
							<isl:textOutput cols="1-8" rotulo="Teléfono" valor="${factura?.prestador?.telefono}" />
							<div class='salto-de-linea'></div>
					
							<label style="margin: 0 10px 0 10px';">Comentarios:</label>
							<div style="margin: 0 10px 0 10px;">
								<textarea id="modal-content-textarea-fct" style="resize: none; height: 100%; width: 100%;"></textarea>
							</div>
						</div>
						<br />
							
						<div style="margin: 0 10px 0 10px;">
							<table id="tablaCM" class="pure-table" width="100%" style="background-color: #cbcbcb;">
								<thead>
									<tr>
										<th> Cuenta Médica </th>
										<th> Facturado </th>
										<th title="Valor Cuenta Médica"> Valor C.M. </th>
										<th title="Diferencia"> Dif. </th>
										<th title="Diferencia %"> Dif. % </th>
										<th> Comentarios ISL </th>
									</tr>
								</thead>
								
								<tbody>
									<g:each in="${cms}" var="c" status="i">
										<tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
											<td align="center">${c.id}</td>
											<td align="right">${FormatosISLHelper.montoStatic(c.facturadoPesos)}</td>
											<td align="right">${FormatosISLHelper.montoStatic(c.valorCuentaAprobado)}</td>
											<td align="center"> &nbsp; </td>
											<td align="center"> &nbsp; </td>
											<td>
												<div style="margin: 0 2px 0 2px;">
													<textarea class="comentario-cm-fct" id="comentario-cm-fct-${c.id}" style="resize: none; height: 100%; width: 100%;"></textarea>
												</div>
											</td>											
										</tr>			
									</g:each>
								</tbody>
								<tfoot>
									<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
										<th> Total: </th>
										<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.totalFactura)} </th>
										<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.sumaCMS)} </th>
										<th align="right"> ${FormatosISLHelper.montoStatic(agregados?.diferenciaSuma)} </th>
										<th align="right"> ${agregados?.diferenciaPorcentaje} </th>
										<th align="center"> &nbsp; </th>
									</tr>
								</tfoot>
							</table>
						</div>
							
						<div style="margin: 0 10px 0 10px;">						
							<g:hiddenField name="fctTotalFactura" value="${agregados?.totalFactura}" />
							<g:hiddenField name="fctSumaCM" value="${agregados?.sumaCMS}" />
							<g:hiddenField name="fctDiferenciaSuma" value="${agregados?.diferenciaSuma}" />
							<g:hiddenField name="fctDiferenciaPorciento" value="${agregados?.diferenciaPorcentaje}" />
						
							<div style="font-weight: bold; width: 100%; text-align: right;">
								Valor Factura: $ ${FormatosISLHelper.montoStatic(agregados?.diferenciaSuma)}
							</div>
						</div>	
					</div>
				</fieldset>
			</g:form>
		</div>
	</div>
</div>
