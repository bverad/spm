<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="Arancel/e_prestacion.js" />

<g:form action="update_prestacion" class="pure-form pure-form-stacked">
	<g:hiddenField name="arancelBaseCodigo" value="${arancelBase?.codigo}"/>
	<g:hiddenField name="arancelBaseId" value="${arancelBase?.id}"/>
	<isl:radiogroup cols="2-10" requerido="true"  nombre="activado" name="activado" labels="['Activado','Desactivado']" values="[1,2]" valor="${arancelBase?.activado ? 1 : 2}" rotulo="Estado">
		${it.label}
		${it.radio}
	</isl:radiogroup>
	<div class='salto-de-linea'></div>
	
	<isl:combo cols="4-8" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Tipo" nombre="tipo" from="${['General','Quirurgica']}" valor="${tipo}" optionKey="" optionValue="" deshabilitado="true"/>
	<isl:textOutput cols="4-8" rotulo="Prestación" valor="${arancelBase?.codigo}"/>
	<div class='salto-de-linea'></div>
	
	<isl:combo cols="4-8" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Grupo" nombre="grupo" from="${grupo}" valor="${arancelBase?.subGrupo?.grupo?.codigo}" deshabilitado="true"/>
	<isl:combo cols="4-8" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Sub Grupo" nombre="subgrupo" from="${subGrupo}" valor="${arancelBase?.subGrupo?.codigo}" deshabilitado="true"/>
	<div class='salto-de-linea'></div>

	<isl:textInput cols="2-8" tipo="numero" requerido="true" nombre="codigo" maxLargo="3" rotulo="Código" valor="${codigo}" deshabilitado="true"/>
	<isl:textInput cols="6-8" tipo="text" requerido="true" nombre="glosa" rotulo="Glosa" valor="${arancelBase?.glosa}" />
	<div class='salto-de-linea'></div>

	<isl:textInput cols="2-8" tipo="numero" deshabilitado="true" nombre="equipo" rotulo="Equipo" valor="${equipo}" />
	<isl:textInput cols="2-8" tipo="numero" requerido="true" nombre="nivelPabellon" rotulo="Nivel Pabellon" valor="${arancelBase?.nivelPabellon}" maxLargo="2" />
	<isl:textOutput cols="2-8" rotulo="Vigencia Desde" valor="${FormatosHelper.fechaCortaStatic(arancelBase?.desde)}" />
	<div class='salto-de-linea'></div>
	<br>

	<table class="pure-table" width="50%">
		<thead>
			<tr>
				<th> &nbsp; </th>
				<th> Valor </th>
			</tr>
		</thead>
		<tbody>
			<tr class="pure-table-odd">
				<td>General / Procedimiento</td>
				<td><isl:textInput cols="" tipo="numero" nombre="valorN1" valor="${arancelBase?.valorN1}" deshabilitado="true" style="text-align: right;" /></td>
			<tr class="pure-table-nodd">
				<td>Anestesista</td>
				<td align="right"><isl:textInput cols="" tipo="numero" nombre="anestesiaN1" valor="${arancelBase?.anestesiaN1}" deshabilitado="true" style="text-align: right;" /></td>
			</tr>
			<tr class="pure-table-odd">
				<td>Cirujano 1</td>
				<td align="right"><isl:textInput cols="" tipo="numero" nombre="cirujano1N1" valor="${arancelBase?.cirujano1N1}" deshabilitado="true" style="text-align: right;" /></td>
			</tr>
			<tr class="pure-table-nodd">
				<td>Cirujano 2</td>
				<td align="right"><isl:textInput cols="" tipo="numero" nombre="cirujano2N1" valor="${arancelBase?.cirujano2N1}" deshabilitado="true" style="text-align: right;" /></td>
			</tr>
			<tr class="pure-table-odd">
				<td>Cirujano 3</td>
				<td align="right"><isl:textInput cols="" tipo="numero" nombre="cirujano3N1" valor="${arancelBase?.cirujano3N1}" deshabilitado="true" style="text-align: right;" /></td>
			</tr>
			<tr class="pure-table-nodd">
				<td>Cirujano 4</td>
				<td align="right"><isl:textInput cols="" tipo="numero" nombre="cirujano4N1" valor="${arancelBase?.cirujano4N1}" deshabilitado="true" style="text-align: right;" /></td>
			</tr>
			<tr class="pure-table-odd">
				<td>Arcenalera</td>
				<td align="right"><isl:textInput cols=""  tipo="numero" nombre="arcenaleraN1" valor="${arcenalera}" deshabilitado="true" /></td>
			</tr>
		</tbody>
	</table>

	<div id="dv_data"></div>
	<div class="pure-g-r" style="float:right;">
		<isl:button tipo="volver" value="Volver" action="mantener_aranceles"/>
		<isl:button tipo="terminar" value="Eliminar" action="delete_prestacion"/>
		<isl:button tipo="siguiente" value="Guardar" action="update_prestacion"/>
	</div>
	
</g:form>