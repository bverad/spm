<%@page import="cl.adexus.isl.spm.helpers.FormatosISLHelper"%>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="Arancel/e_paquete.js" />

<g:form action="update_paquete" class="pure-form pure-form-stacked">
	<g:hiddenField name="paqueteId" value="${paquete?.id}"/>
	<g:hiddenField name="codigo" value="${paquete?.codigo}"/>
	<g:hiddenField name="desdeEdicion" value="${desdeEdicion}"/>
	
	<isl:textOutput cols="4-8" rotulo="Tipo" valor="${paquete?.tipoPaquete?.descripcion}"/>

	<div class='salto-de-linea'></div>
	
	<isl:textOutput cols="3-8" rotulo="Grupo" valor="${paquete?.subGrupo?.grupo?.descripcion}"/>
	<isl:textOutput cols="3-8" rotulo="SubGrupo" valor="${paquete?.subGrupo?.descripcion}"/>
	<isl:textOutput cols="2-8" rotulo="Prestación" valor="${paquete?.codigo}"/>	
	
	<isl:textInput cols="2-8" tipo="text" requerido="true" nombre="glosa" rotulo="Glosa" valor="${paquete?.glosa}" />
	<isl:textInput cols="1-8" tipo="numero" requerido="true" nombre="complejidad" rotulo="Complejidad" valor="${paquete?.complejidad}" maxLargo="10" />
	<isl:textInput cols="1-8" tipo="numero" requerido="true" nombre="efectividad" rotulo="Efectividad %" valor="${paquete?.efectividad}" maxLargo="3" />
	<isl:combo cols="1-8" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Escalamiento" nombre="escalamiento" from="${['Cirugía','Consulta']}" valor="${paquete?.escalamiento}" optionKey="" optionValue=""/>
	<isl:textInput cols="1-8" tipo="text" requerido="true" nombre="reposo" rotulo="Reposo Estimado" valor="${paquete?.reposoEstimado}" />	
	<isl:calendar cols="1-8" tipo="calendar" requerido="true" nombre="desde" rotulo="Vigencia Desde" valor="${paquete?.desde}"/>
	<div class='salto-de-linea'></div>
	
	<g:if test="${!desdeEdicion}">
		<fieldset>
		<legend>Agregar Prestaciones al Paquete</legend>
		<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Grupo" nombre="grupo" from="${grupo}" valor="${grupo?.codigo}" valor=""/>
		<isl:combo cols="3-8" noSelection="${['':'Seleccione ...']}" rotulo="Sub Grupo" nombre="subgrupo" from="${subgrupo}" valor=""/>
		<isl:textInput cols="1-8" tipo="text" nombre="codigoPrestacion" rotulo="Prestación" />
		<div style="height: 18px;"></div>
		<button class="pure-button pure-button-secondary" id="btnFiltrarArancel">Filtrar Aranceles</button>
		
		<table id="dv_aranceles_base" class="pure-table" width="100%">
			<thead>
				<tr>
					<th>Selección&nbsp;&nbsp;&nbsp;<input type='checkbox' id='all_add_prestaciones'></th>
					<th>Prestación</th>
					<th>Glosa</th>
					<th>Nivel-1</th>
					<th>Nivel-2</th>
					<th>Nivel-3</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="6"> Seleccione un filtro para obtener los aranceles... </td>
				</tr>
			</tbody>
		</table>
		
		<br>
		
		<div id="dv_cargos" style="display:none">
			<isl:combo rotulo ="Nivel" cols="1-8" noSelection="${['':'Seleccione ...']}" nombre="nivel" from="${['Nivel 1','Nivel 2', 'Nivel 3']}" optionKey="" optionValue=""/>
			<isl:combo rotulo ="Movimiento" cols="1-8" noSelection="${['':'Seleccione ...']}" nombre="operacion" from="${['Descuento','Cargo']}" optionKey="" optionValue=""/>
			<isl:textInput rotulo ="Cantidad" cols="1-8" tipo="numero" nombre="cantidad" />
			<isl:textInput rotulo="Valor" cols="1-8" tipo="numero" nombre="valor"  />
			<isl:radiogroup rotulo ="Tipo valor" cols="1-8" nombre="tipo" name="tipo" labels="['%','$']" values="['PRC','PESOS']" >
				${it.label}
				${it.radio}
			</isl:radiogroup>
			<button class="pure-button pure-button-secondary" id="btnAgregarArancel">Agregar al Arancel</button>			
		</div>	
		</fieldset>
	</g:if>						

	<fieldset>
	<legend>Mantener Prestaciones del Paquete</legend>
	
		<div class='salto-de-linea'></div>

		<table id="dv_aranceles_paquete" class="pure-table" width="100%">
			<thead>
				<tr>
					<g:if test="${!desdeEdicion}">
						<th>Eliminar&nbsp;&nbsp;&nbsp;<input type='checkbox' id='all_del_prestaciones'></th>
					</g:if>
					
					<th>Prestación</th>
					<th>Glosa</th>
					<th>Nivel</th>
					<th>Cantidad</th>
					<th>Valor</th>
					<th>Calculo</th>
					<th>Convenido</th>
				</tr>
			</thead>
			</tbody>
				<g:if test="${!arancelesPaquete}">
					<tr class="even">
						<td colspan="${!desdeEdicion ? 8 : 7}"> Paquete sin aranceles asociados. </td>
					</tr>
				</g:if>
				<g:each in="${arancelesPaquete}" status="i" var="arancelPaquete">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<g:if test="${!desdeEdicion}">
							<td align=center><input type="checkbox" id="ch_cnv_arc"  name="ch_cnv_arc" value="${arancelPaquete?.id}"></td>
						</g:if>
						<td align=center>${arancelPaquete.codigoPrestacion}</td>
						<td title="${arancelPaquete.glosa}">${FormatosISLHelper.truncateStatic(arancelPaquete.glosa, 70)}</td>
						<td align=center>${arancelPaquete.nivel}</td>
						<td align=right>${arancelPaquete.cantidad}</td>
						<td align=right>${FormatosISLHelper.montoStatic(arancelPaquete.valorOriginal)}</td>
						<td align=right>${arancelPaquete.calculo}</td>
						<td align=right>${FormatosISLHelper.montoStatic(arancelPaquete.valorNuevo)}</td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</fieldset>						

	<div class="pure-g-r" style="float:right;">
		<isl:button tipo="volver" value="Volver" action="mantener_aranceles"/>
		<isl:button tipo="terminar" value="Eliminar" action="delete_paquete"/>
		<isl:button tipo="siguiente" value="Actualizar" action="update_paquete"/>
	</div>
	
</g:form>