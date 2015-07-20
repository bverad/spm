<g:javascript src="CM/ingreso_dp01.js" />
<g:form name="dp01" class="pure-form pure-form-stacked" >
		<fieldset>
 		<legend>Ingreso Datos Encabezado Cuenta Médica</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" requerido="true" nombre="folioCuenta" rotulo="Folio Cuenta Médica" valor="${cuentaMedica?.folioCuenta}"/>
				<isl:textInput cols="1-8" requerido="true" nombre="rutPrestador" rotulo="RUT Prestador" tipo="rut" valor="${p?.rutPrestador}"/>
				<isl:textInput cols="3-8" requerido="true" nombre="nombrePrestador" rotulo="Nombre Prestador" valor="" deshabilitado="true"/>
				<isl:combo	   cols="3-8" requerido="true" nombre="centroSalud" from="" rotulo="Centro Salud" valor="" noSelection="${['':'Seleccione ...']}"/>

				<isl:textInput cols="2-8" requerido="true" nombre="runTrabajador" rotulo="RUN Trabajador" valor="${t?.runTrabajador}"/>
				<isl:textInput cols="6-8" requerido="true" nombre="nombreTrabajador" rotulo="Nombre Trabajador" valor="" deshabilitado="true"/>
				<div class='salto-de-linea'></div>

				<isl:calendar  cols="2-8" requerido="true" rotulo="Fecha cobro desde" nombre="fechaDesde"	value="${cuentaMedica?.fechaDesde}"/>
				<isl:calendar  cols="2-8" requerido="true" rotulo="Fecha cobro hasta" nombre="fechaHasta"	value="${cuentaMedica?.fechaHasta}"/>
				<isl:calendar  cols="2-8" requerido="true" rotulo="Fecha emisión" nombre="fechaEmision"	value="${cuentaMedica?.fechaEmision}"/>
				<div class='salto-de-linea'></div>

				<isl:textInput cols="2-8" nombre="opaNumero" tipo="numero" rotulo="OPA Nº" valor=""/>
				<isl:textInput cols="2-8" nombre="opaepNumero" tipo="numero" rotulo="OPAEP Nº" valor=""/>
				<isl:textInput cols="2-8" nombre="odaNumero" tipo="numero" rotulo="ODA Nº" valor=""/>
				<!-- BOTON AGREGAR y CAMPO hidden -->
				<div class="pure-u-1-8" style="padding-top: 17px; padding-left: 5px;">
					<button id="btnAgregarOXA" class="pure-button pure-button-secondary" onclick="return false;">Agregar</button>
				</div>
				<div class='salto-de-linea'></div>

				<isl:radiogroup cols="3-8" requerido="true" nombre="tipoCuenta" name="tipoCuenta"
								rotulo="Tipo Cuenta" labels="${tipoCuenta.descripcion}"
								values="${tipoCuenta.codigo}" valor="${cuentaMedica?.tipoCuenta?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<isl:radiogroup cols="3-8" requerido="true" nombre="formatoOrigen" name="formatoOrigen"
								rotulo="Detalle Prestaciones" labels="${formatoOrigen.descripcion}"
								values="${formatoOrigen.codigo}" valor="${cuentaMedica?.formatoOrigen?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<isl:textInput cols="2-8" tipo="numero" requerido="true" nombre="valorCuenta" rotulo="Valor Cuenta" valor="${cuentaMedica?.valorCuenta}"/>
				<div class='salto-de-linea'></div>

				<!-- Guardamos en texto plano. Ahora, Ud (si, usted) podria querer implementarlo en Base64  -->
				<input id="listaOXA" name="listaOXA" type="hidden" value="${p?.listaOXA}" />
				
				<!-- TABLA DE OPA/ODAs (OXAs tambien podriamos llamarlas) -->
				<div class="pure-u-2-8" style="padding-top: 20px;">
					<table id="tablaOXA" class="pure-table" width="20%">
						<thead>
							<tr>
								<th> OPA/ODAs </th>
								<th> Eliminar </th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
	</fieldset>
	<div class="pure-g-r">
		<isl:button action="postDp01" value="Crear Cuenta" />
	</div>
</g:form>
