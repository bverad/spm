<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:javascript src="CM/revision_dp04.js" />
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
				    	
				    	<td><g:textField class="input-cantidad-final"         name="cantidadFinal_${s.id}"         style="width:30px;text-align:right;" value="${s.cantidadFinal}"/></td>
				    	<td><g:textField class="input-valor-unitario-final"   name="valorUnitarioFinal_${s.id}"    style="width:60px;text-align:right;" value="${FormatosISLHelper.montoStatic(s.valorUnitarioFinal)}"/></td>
				    	<td><g:textField class="input-descuento-final"        name="descuentoFinal_${s.id}"        style="width:60px;text-align:right;" value="${FormatosISLHelper.montoStatic(s.descuentoFinal)}"/></td>				    	
				    	<td><g:textField class="input-recargo-unitario-final" name="recargoUnitarioFinal_${s.id}"  style="width:60px;text-align:right;" value="${FormatosISLHelper.montoStatic(s.recargoUnitarioFinal)}"/></td>
				    	<td><g:textField class="input-valor-total-final"      name="valorTotalFinal_${s.id}"       style="width:60px;text-align:right;" value="${s.valorTotalFinal}"  deshabilitado="true"/></td>
				    	
				    	<td nowrap="nowrap">
				    		<g:checkBox name="${s.id + "_M"}" value="${s.id + "_M"}"
				    					checked="${s.consultaMedica != null}" disabled="disabled" />
				    			<g:if test="${s.consultaMedica != null}">
				    				&nbsp;<a id="${s.id + "__M"}" class="link-consulta" href="#" onclick="return false;">(M)</a>
				    			</g:if>
				    			<g:else><div style="display: inline-block;"> (M)</div></g:else>
				    		<br>
				    		<g:checkBox name="${s.id + "_C"}" value="${s.id + "_C"}"
				    					checked="${s.consultaConvenio != null}" disabled="disabled" />
				    			<g:if test="${s.consultaConvenio != null}">
				    				&nbsp;<a id="${s.id + "__C"}" class="link-consulta" href="#" onclick="return false;">(C)</a>
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
		<isl:button action="postDp04" value="Aceptar"/>
		<isl:button action="rechazoDp04" tipo="cancelar" value="Rechazar"/>
	</div>
</g:form>

<!--  MODAL PARA CONSULTA MEDICA -->
<div id="modalsrc-medica" class="yui-pe-content">
	<div id="modal-marco" >
		<g:form class="pure-form pure-form-stacked modalForm"  name="modalForm" >
		    <fieldset>
				<div>
					<div id="modal-content" style="margin: 0 5px 0 5px;">
						<textarea id="modal-content-textarea" style="resize: none; height: 100%; width: 100%;" disabled="disabled"></textarea>
						<div id="modal-clickers" style="letter-spacing: 0em; margin: 0 5px 0 5px;">
							<p>Se sugiere:</p>
							<input type="radio" disabled="disabled" id="modal-content-clickers-aprobar" name="modal-content-clickers" class="modal-content-clickers" value="Aprobar" checked="checked" /> Aprobar
							<input type="radio" disabled="disabled" id="modal-content-clickers-rechazar" name="modal-content-clickers" class="modal-content-clickers" value="Rechazar" /> Rechazar
							<input type="radio" disabled="disabled" id="modal-content-clickers-modificar" name="modal-content-clickers" class="modal-content-clickers" value="Modificar" /> Modificar Cantidad
							<input type="text"  disabled="disabled" id="modal-content-modificar-monto" name="modal-content-modificar-monto" class="modal-content-clickers" value="" size="5" maxlength="5" style="display: inline;" />
							<button id="modal-content-copiar-valor-medica" class="yui3-button pure-button-success btn-modal-proceed">Copiar Valor</button>
						</div>
						<div style="margin: 0 5px 0 5px;">
							<label style=": 0 10px 0 10px';">Comentarios:</label>
							<textarea disabled="disabled" id="modal-content-respuesta-texto" style="resize: none; height: 100%; width: 100%;"></textarea>
						</div>						
					</div>
				</div>
			</fieldset>
		</g:form>
	</div>
</div>

<!--  MODAL PARA CONSULTA CONVENIO-->
<div id="modalsrc-convenio" class="yui-pe-content">
	<div id="modal-marco" >
		<g:form class="pure-form pure-form-stacked modalForm"  name="modalForm" >
		    <fieldset>
				<div>
					<div id="modal-content" style="margin: 0 5px 0 5px;">
						<textarea disabled="disabled" id="modal-content-textarea" style="resize: none; height: 100%; width: 100%;" disabled="disabled"></textarea>

						<div id="modal-clickers" style="letter-spacing: 0em; margin: 0 5px 0 5px;">
							<p>Se sugiere:</p>
							<input type="radio" disabled="disabled" id="modal-content-clickers-aprobar" name="modal-content-clickers" class="modal-content-clickers" value="Aprobar" checked="checked" /> Aprobar
							<input type="radio" disabled="disabled" id="modal-content-clickers-rechazar" name="modal-content-clickers" class="modal-content-clickers" value="Rechazar" /> Rechazar
							<input type="radio" disabled="disabled" id="modal-content-clickers-modificar" name="modal-content-clickers" class="modal-content-clickers" value="Modificar" /> Modificar Valor Unitario
						</div>

						<div id="modal-content-modifica-monto" style="margin: 0 5px 0 2px; width: 50%; float: left; border-color: #BBBBBB; border-width: 1px; border-style: solid; padding: 2px 2px 2px 2px;">
							<label style=": 0 10px 0 10px';"><span style="font-weight: bold;">Valor Propuesto</span></label>
							
							<label for="modal-content-valor-unitario" style="float: left;">Valor Unitario</label>
							<input disabled="disabled" type="text" size="3" maxlength="7" id="modal-content-valor-unitario" name="modal-content-valor-unitario" style="margin-left: 5px; float: right;" />
							
							<label for="modal-content-cantidad-prestaciones" style="float: left;">Cant. de Prestaciones</label>
							<input type="text" disabled="disabled" size="3" maxlength="3" id="modal-content-cantidad-prestaciones" name="modal-content-cantidad-prestaciones" style="margin-left: 5px; float: right;" />
							
							<label for="modal-content-valor-total" style="float: left; margin-right: 30px;">Valor Total</label>
							<input type="text" disabled="disabled" size="3" maxlength="3" id="modal-content-valor-total" name="modal-content-valor-total" style="margin-left: 5px; float: right;" />
						</div>

						<div id="modal-content-comment-wrapper" style="margin: 0 2px 0 5px; width: 45%; float: left;">
							<label style=": 0 10px 0 10px';">Comentarios:</label>
							<textarea disabled="disabled" id="modal-content-respuesta-texto" style="resize: none; height: 100%; width: 100%;"></textarea>
							<button id="modal-content-copiar-valor-convenio" class="yui3-button pure-button-success btn-modal-proceed">Copiar Valor</button>
						</div>
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
