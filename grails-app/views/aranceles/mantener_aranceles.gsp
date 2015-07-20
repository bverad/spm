<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="Arancel/arancel.js" />

<g:form action="mantener_aranceles" class="pure-form pure-form-stacked">
	<g:hiddenField name="arancelBaseId" value=""/>
	<g:hiddenField name="paqueteId" value=""/>
	<g:hiddenField name="desdeEdicion" value="true"/>
	
	<fieldset>
		<legend>Prestaciones</legend>

		<isl:combo cols="2-10" noSelection="${['':'Seleccione ...']}" rotulo="Grupo" nombre="grupo" from="${grupo}" valor="${params?.grupo}"/>
		<isl:combo cols="2-10" noSelection="${['':'Seleccione ...']}" rotulo="Sub grupo" nombre="subgrupo" from="${subGrupo}" valor="${params?.subgrupo}"/>
		<isl:textInput cols="1-10" tipo="numero" nombre="codigoPrestacion" rotulo="Código" valor="${params?.codigoPrestacion}" />
		<isl:calendar cols="1-10" tipo="calendar" nombre="desde" rotulo="Vigencia desde" valor="${params?.desde}" />
		<isl:calendar cols="1-10" tipo="calendar" nombre="hasta" rotulo="Vigencia hasta" valor="${params?.hasta}" />

		<div style="height: 17px;"></div>
		<input class="pure-button pure-button-secondary" type="submit" value="Buscar" name="_action_mantener_aranceles">
			
		<div class='salto-de-linea'></div>
		<br>
		<div class='salto-de-linea'></div>
	
		<table class="pure-table" width="100%">
			<thead>
				<tr>
					<th>Prestación</th>
					<th>Glosa</th>
					<th>Nivel 1</th>
					<th>Nivel 2</th>
					<th>Nivel 3</th>
					<th>Estado</th>
					<th>Desde</th>
					<th>Hasta</th>
					<th>Acción</th>
				</tr>
			<thead>	
			<tbody>
				<g:each in="${prestacionList}" status="i" var="arancelBase">
					<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">	
						<td align="center">${arancelBase?.codigo}</td>
						<td title="${arancelBase?.glosa}">${FormatosHelper.truncateStatic(arancelBase?.glosa, 70)}</td>
						<td align="right">${FormatosHelper.montoStatic(arancelBase?.valorN1)}</td>
						<td align="right">${FormatosHelper.montoStatic(arancelBase?.valorN2)}</td>
						<td align="right">${FormatosHelper.montoStatic(arancelBase?.valorN3)}</td>
						<td>
							<g:if test="${arancelBase?.hasta == null}">
								Vigente
							</g:if>
							<g:else>
								No Vigente
							</g:else>	
						</td>
						<td align="center">${FormatosHelper.fechaCortaStatic(arancelBase?.desde)}</td>
						<td align="center">${FormatosHelper.sumarDiasStatic(arancelBase?.hasta, -1)}</td>
						
						<g:if test="${arancelBase?.tipo?.equals('ARANCEL')}">
							<td width="129px">
								<button title="Más información"
										class="pure-button pure-button-secondary"
										onclick="document.forms[0].arancelBaseId.value='${arancelBase?.id}';document.forms[0].action='ver_prestacion';document.forms[0].submit();">
										<i class="icon-info-sign"></i>
								</button>
								<g:if test="${arancelBase?.origen?.equals('ISL') && arancelBase.hasta == null}">
									<button title="Editar"
											class="pure-button pure-button-success"
											onclick="document.forms[0].arancelBaseId.value='${arancelBase?.id}';document.forms[0].action='edit_prestacion';document.forms[0].submit();">
										<i class="icon-wrench"></i>
									</button>
								</g:if>
							</td>	
						</g:if>				
						<g:else>
							<td width="129px">
								<button title="Ver"
										class="pure-button pure-button-secondary"
										onclick="document.forms[0].paqueteId.value='${arancelBase?.id}';document.forms[0].action='ver_paquete';document.forms[0].submit();">
									<i class="icon-info-sign"></i>
								</button>
								<g:if test="${arancelBase?.origen?.equals('ISL') && arancelBase.hasta == null}">
									<button title="Editar"
											class="pure-button pure-button-success"
											onclick="document.forms[0].paqueteId.value='${arancelBase?.id}';document.forms[0].action='edit_paquete';document.forms[0].submit();">
										<i class="icon-wrench"></i>
									</button>
								</g:if>
							</td>
						</g:else>
					</tr>	
				</g:each>
			</tbody>									
		</table>
				<div class="pagination">
					<g:paginate next="Siguiente" prev="Previo" params="${[grupo: params?.grupo, subgrupo: params?.subgrupo, codigoPrestacion: params?.codigoPrestacion ]}" maxsteps="0" controller="Aranceles" action="mantener_aranceles" total="${totalPaginate}" />
				</div>
	
	</fieldset>
	
	<div class="pure-g-r">
		<div class="pure-u-1">
			<isl:button action="create_paquete" tipo="siguiente" value="Nuevo Paquete ISL" />
			<isl:button action="create_prestacion" tipo="excel" value="Nueva Prestación ISL" />	
		</div>			
	</div>		
</g:form>