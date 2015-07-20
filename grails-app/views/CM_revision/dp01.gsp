<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:javascript src="CM/revision_dp01.js" />

<g:form name="dp01" class="pure-form pure-form-stacked" >
	<g:hiddenField	name="folio_cuenta" value="${params.folio_cuenta}"/>
	<isl:header_cm  tRun="${FormatosISLHelper.runFormatStatic(trabajador?.run)}"
					tNombreCompleto="${FormatosISLHelper.nombreCompletoStatic(trabajador)}"
					pRut="${FormatosISLHelper.rutPrestadorStatic(prestador)}"
					pCentro="${centroSalud?.nombre}"
					pNombre="${FormatosISLHelper.nombrePrestadorStatic(prestador)}"
					opa="${cuentaMedica?.opas}"
					opaep="${cuentaMedica?.opaeps}"
					odas="${cuentaMedica?.odas}"
					folioCuenta="${cuentaMedica?.folioCuenta}"
					mtoCuenta="${cuentaMedica?.valorCuenta}"/>
	<isl:header_historial_cm cuentas="${historialCuentas}" />
	<fieldset>
		<input type="hidden" name="cuentaMedicaId" value="${cuentaMedica?.id}" />
		<input type="hidden" name="taskId" value="${taskId}" />

		<style type="text/css"> 
			a:visited { 
				text-decoration:none; 
			}
		</style>
		
		<legend>Detalle Prestaciones Cuenta Médica</legend>
		<table id="tablaCM" class="pure-table" width="100%">
			<thead>
				<tr style="border-bottom-width: 1px; border-bottom-color: #CBCBCB; border-bottom-style: solid;">
					<th colspan="5"> &nbsp; </th>
					<th colspan="4" align="center"> Digitado </th>
					<th colspan="2" align="center"> Pactado </th>
					<th colspan="5" align="center"> Final </th>
					<th colspan="2"> &nbsp; </th>
				</tr>
				<tr>
					<th> Fecha </th>
					<th> Hora </th>
					<th> Código </th>
					<th> Glosa </th>
					<th title="Cantidad"> Cant. </th>
					
					<th title="Valor unitario"> V.U. </th>
					<th> Dscto. </th>
					<th title="Recargo"> Recar. </th>
					<th title="Valor Total Neto"> V.T.N. </th>
					
					<th title="Valor unitario"> V.U. </th>
					<th title="Recargo"> Recar. </th>
					
					<th title="Valor unitario"> Cant. </th>
					<th title="Valor unitario"> V.U. </th>
					<th title="Descuento"> Dscto. </th>
					<th title="Recargo"> Recar. </th>
					<th title="Valor Total Neto"> V.T.N. </th>

					<th> Obs. </th>
					<th> Alertas </th>
				</tr>
			</thead>
			
			<tbody>
				<g:each in="${detallesCuenta}" var="s" status="i">
				    <tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}" id="tr_${s.id}">
				    	<td align="center" nowrap="nowrap">${FormatosISLHelper.fechaCortaStatic(s.fecha)}</td>
				    	<td align="center" nowrap="nowrap">${FormatosISLHelper.horaCortaStatic(s.fecha)}</td>
				    	<td>${s.codigo}</td>
				    	<td title="${s.glosa}" nowrap="nowrap">${FormatosISLHelper.truncateStatic(s.glosa, 5)}</td>
				    	<td align="center">${s.cantidad}</td>
				    	<td align="right">${FormatosISLHelper.montoStatic(s.valorUnitario)}</td>
				    	<td align="right">${FormatosISLHelper.montoStatic(s.descuentoUnitario)}</td>
				    	<td align="right">${FormatosISLHelper.montoStatic(s.recargoUnitario)}</td>
				    	<td align="right">${FormatosISLHelper.montoStatic(s.valorTotal)}</td>
				    	
				    	<td align="right">${FormatosISLHelper.montoStatic(s.valorUnitarioPactado)}</td>
				    	<td align="right">${FormatosISLHelper.montoStatic(s.recargoUnitarioPactado)}</td>
				    	
				    	<td><g:textField class="input-cantidad-final"         name="cantidadFinal_${s.id}"         style="width:50px;text-align:right;" value="${s.cantidadFinal}"/></td>
				    	<td><g:textField class="input-valor-unitario-final"   name="valorUnitarioFinal_${s.id}"    style="width:60px;text-align:right;" value="${s.valorUnitarioFinal}"/></td>
				    	<td><g:textField class="input-descuento-final"        name="descuentoFinal_${s.id}"        style="width:60px;text-align:right;" value="${s.descuentoFinal}"/></td>				    	
				    	<td><g:textField class="input-recargo-unitario-final" name="recargoUnitarioFinal_${s.id}"  style="width:60px;text-align:right;" value="${s.recargoUnitarioFinal}"/></td>
				    	<td><g:textField class="input-valor-total-final"      name="valorTotalFinal_${s.id}"       style="width:60px;text-align:right;" value="${s.valorTotalFinal}"       deshabilitado="true"/></td>
				    	
				    	<td nowrap="nowrap">
				    		<g:checkBox name="${s.id + "_M"}" value="${s.id + "_M"}"
				    					checked="${s.consultaMedica != null}" disabled="${s.consultaMedica != null}" />
				    			<g:if test="${s.consultaMedica != null}">
				    				&nbsp;<a id="${s.id + "_M"}" class="link-consulta" href="#" onclick="return false;">(M)</a>
				    			</g:if>
				    			<g:else><div style="display: inline-block;"> (M)</div></g:else>
				    		<br>
				    		<g:checkBox name="${s.id + "_C"}" value="${s.id + "_C"}"
				    					checked="${s.consultaConvenio != null}" disabled="${s.consultaConvenio != null}" />
				    			<g:if test="${s.consultaConvenio != null}">
				    				&nbsp;<a id="${s.id + "_C"}" class="link-consulta" href="#" onclick="return false;">(C)</a>
				    			</g:if>
				    			<g:else><div style="display: inline-block;"> (C)</div></g:else>
				    	</td>
				    	
				    	<!--  COLUMNA DE LOS WARNINGS -->
				    	<td align="center">
				    		<g:if test="${warnings[s.id]}">
						    	<a class="link-warning" href="#" id="${s.id + "_W"}" style="text-decoration: none;">
						    		<font color="red"><i class="icon-exclamation-sign icon-2x"></i></font>
						    	</a>
				    		</g:if>
				    		<g:else>
					    		<font color="green"><i class="icon-ok-sign icon-2x"></i></font>
				    		</g:else>
				    	</td>
				   	</tr>
				</g:each>
			</tbody>
			<tfoot> 
				<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
					<th colspan="8" align="right">V.T.N. Digitado</th>
					<th align="right">
						${FormatosISLHelper.montoStatic(cm?.sumValorTotal)}
					</th>
					<th colspan="6" align="right">V.T.N. Final</th>
					<th align="right" id="valorTotalFinalTabla"></th>
					<th colspan="2" align="right">&nbsp;</th>
				</tr>
			</tfoot>
		</table>
	</fieldset>
	<div class="pure-g-r">
		<isl:button action="postDp01" value="Siguiente"/>
	</div>
</g:form>

<!--  MODAL PARA CONSULTA MEDICA O CONVENIO-->
<div id="modalsrc" class="yui-pe-content">
	<div id="modal-marco" >
		<g:form class="pure-form pure-form-stacked modalForm"  name="modalForm" >
		    <fieldset>
				<div class="pure-g-r">
					<div id="modal-content">
						<textarea id="modal-content-textarea" required style="resize: none; height: 100%; width: 100%;"></textarea>
					</div>
				</div>
			</fieldset>
		</g:form>
	</div>
</div>

<!--  MODAL PARA WARNINGS -->
<div id="modalsrc-warning" class="yui-pe-content">
	<div id="modal-warning-marco" >
		<g:form class="pure-form pure-form-stacked modalForm"  name="modalForm" >
		    <fieldset>
				<div class="pure-g-r">
					<div id="modal-warning-content">
						<g:each in="${warnings}" var="w">
							<ul id="warning_${w.key}" class="warnings" style="text-align: left">
								<g:each in="${w.value}" var="wa">
									<li style="letter-spacing: 0;">${wa}&#13;&#10;</li>
								</g:each>	
							</ul>
						</g:each>
					</div>
				</div>
			</fieldset>
		</g:form>
	</div>
</div>

