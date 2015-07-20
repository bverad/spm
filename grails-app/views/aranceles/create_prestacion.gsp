<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="Arancel/c_prestacion.js" />

<g:form action="save_prestacion" class="pure-form pure-form-stacked">
	
	<isl:radiogroup cols="2-10" requerido="true"  nombre="activado" name="activado" labels="['Activado','Desactivado']" values="[1,2]" valor="${arancelBase?.activado ? 1 : 2}" rotulo="Estado">
		${it.label}
		${it.radio}
	</isl:radiogroup>
						
	<div class='salto-de-linea'></div>

	<isl:combo cols="4-8" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Tipo" nombre="tipo" from="${['General','Quirurgica']}" valor="${tipo}" optionKey="" optionValue=""/>
	<isl:textOutput cols="4-8" rotulo="Prestación" valor=""/>

	<div class='salto-de-linea'></div>

	<isl:combo cols="4-8" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Grupo" nombre="grupo" from="${grupo}" valor="${grupoCodigo}"/>
	<isl:combo cols="4-8" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Sub Grupo" nombre="subgrupo" from="${subGrupo}" valor="${subGrupoCodigo}"/>


	<div class='salto-de-linea'></div>

	<isl:textInput cols="2-8" tipo="numero" requerido="true" nombre="codigo" rotulo="Código" maxLargo="3" valor="${arancelBase?.codigo}" />
	<isl:textInput cols="6-8" tipo="text" requerido="true" nombre="glosa" rotulo="Glosa" valor="${arancelBase?.glosa}" />
	
	<div class='salto-de-linea'></div>

	<isl:textInput cols="2-8" tipo="numero" deshabilitado="true" nombre="equipo" rotulo="Equipo" valor="" />
	<isl:textInput cols="2-8" tipo="numero" requerido="true" nombre="nivelPabellon" rotulo="Nivel Pabellon" valor="${arancelBase?.nivelPabellon}" maxLargo="2" />
	<isl:calendar cols="2-8" tipo="calendar" requerido="true" nombre="desde" rotulo="Vigencia Desde" valor="${cl.adexus.helpers.FormatosHelper.fechaCortaStatic(arancelBase?.desde)}"/>
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
			<tr>
				<td>General / Procedimiento</td>
				<td><isl:textInput cols="" tipo="numero" nombre="valorN1" valor="${arancelBase?.valorN1}" style="text-align: right;" /></td>
			</tr>
			<tr>
				<td>Anestesista</td>
				<td><isl:textInput cols="" tipo="numero" nombre="anestesiaN1" valor="${arancelBase?.anestesiaN1}" style="text-align: right;" /></td>
			</tr>
			<tr>
				<td>Cirujano 1</td>
				<td><isl:textInput cols="" tipo="numero" nombre="cirujano1N1" valor="${arancelBase?.cirujano1N1}" style="text-align: right;" /></td>
			</tr>
			<tr>
				<td>Cirujano 2</td>
				<td><isl:textInput cols="" tipo="numero" nombre="cirujano2N1" valor="${arancelBase?.cirujano2N1}" style="text-align: right;" /></td>
			</tr>
			<tr>
				<td>Cirujano 3</td>
				<td><isl:textInput cols="" tipo="numero" nombre="cirujano3N1" valor="${arancelBase?.cirujano3N1}" style="text-align: right;" /></td>
			</tr>
			<tr>
				<td>Cirujano 4</td>
				<td><isl:textInput cols="" tipo="numero" nombre="cirujano4N1" valor="${arancelBase?.cirujano4N1}" style="text-align: right;" /></td>
			</tr>
			<tr>
				<td>Arcenalera</td>
				<td><isl:textInput cols=""  tipo="numero" deshabilitado="true" nombre="arcenaleraN1" valor="${arcenaleraN1}" style="text-align: right;" /></td>
			</tr>
		</tbody>
	</table>

	<div class="pure-g-r" style="float:right;">	
		<input type="button" class="pure-button pure-button-secondary" onclick="document.forms[0].action='mantener_aranceles';document.forms[0].submit();" value="Volver" />
		<isl:button tipo="siguiente" value="Guardar" action="save_prestacion"/>
	</div>
	
</g:form>