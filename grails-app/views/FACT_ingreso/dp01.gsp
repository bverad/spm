<g:javascript src="FACT/ingreso_dp01.js" />
<g:form name="dp01" class="pure-form pure-form-stacked" >
		<fieldset>
 		<legend>Ingreso Datos Factura</legend>

		<input id="id" name="id" type="hidden" value="${factura?.id}" />
		<isl:textInput cols="1-8"  requerido="true" nombre="folio" rotulo="Folio Factura" valor="${factura?.folio}"/>
		<isl:textInput cols="1-8" requerido="true" nombre="rutPrestador" rotulo="RUT Prestador" tipo="rut" valor="${p?.rutPrestador}"/>
		<isl:textInput cols="6-8" requerido="true" nombre="nombrePrestador" rotulo="Nombre Prestador" valor="" deshabilitado="true"/>

		<isl:textInput cols="4-8"  nombre="direccionCasaMatriz" rotulo="Dirección Casa Matriz" valor="${p?.direccion}" deshabilitado="true"/>
		<isl:textInput cols="2-8"  nombre="email" rotulo="Email" valor="${p?.email}" deshabilitado="true"/>
		<isl:textInput cols="2-8"  nombre="telefono" rotulo="Teléfono" valor="${p?.telefono}"  deshabilitado="true"/>

		<isl:textInput cols="1-8" nombre="folioCuentaMedica"    rotulo="Folio Cuenta Médica" />

		<isl:textInput cols="1-8" nombre="valorCuentaMedica" rotulo="Valor Cuenta Médica" tipo="numero" />

		<div class="pure-u-1-8" style="padding-top: 17px; padding-left: 5px;">
			<button id="btnAgregarCM" class="pure-button pure-button-secondary" onclick="return false;">Agregar</button>
		</div>
		<div class='salto-de-linea'></div>
		
		<input id="listaCM" name="listaCM" type="hidden" value="${listaCM}" />
			
		<br>
		<table id="tablaCM" class="pure-table" width="30%">
			<thead>
				<tr>
					<th> Folio Cuenta Médica </th>
					<th> Valor </th>
					<th> Eliminar </th>
				</tr>
			</thead>
			<tbody></tbody>
			<tfoot>
				<tr>
					<th> Total </th>
					<th id="totalFacturaTable" align="right"> &nbsp; </th>
					<th> &nbsp; </th>
				</tr>
			</tfoot>
		</table>
		
		<!--  TOTAL FACTURA (No lo pase por alto)-->
		<g:hiddenField id="totalFactura" name="totalFactura" />
		
		<div class='salto-de-linea'></div>

		</fieldset>
	<div class="pure-g-r">
		<isl:button action="postDp01" />
	</div>
	
	<g:if test="${flash.default}">
		<fieldset>
			<legend>Mensaje : </legend>
			<div class="pure-u-1 messages">
				<ul>
					<li><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}" /></li>
				</ul>
			</div>
		</fieldset>
	</g:if>
</g:form>
