<%@ page import="cl.adexus.helpers.FormatosHelper" %>	
<g:javascript src="Prestador/prestador.js" />
<!-- modal tinybox.js -->
<g:javascript src="tinybox.js" />
<g:form action="save" class="pure-form pure-form-stacked">
	<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
	<g:hiddenField name="convenioId" value="${convenio?.id}"/>
	<fieldset>
		<legend>Mantener Arancel en Convenio</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">	
				<g:if test="${prestadorInstance?.esPersonaJuridica}">
					<isl:textOutput cols="1-10" rotulo="RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaJuridica?.rut)}"/>
				</g:if>
				<g:else>
					<isl:textOutput cols="1-10" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaNatural?.run)}"/>
				</g:else>							

				<g:if test="${prestadorInstance?.esPersonaJuridica}">
					<isl:textOutput cols="3-10" rotulo="Razón social" valor="${prestadorInstance?.personaJuridica?.razonSocial}"/>
				</g:if>
				<g:else>
					<isl:textOutput cols="1-10" rotulo="Nombre" valor="${prestadorInstance?.personaNatural?.nombre}"/>
					<isl:textOutput cols="1-10" rotulo="Apellido paterno" valor="${prestadorInstance?.personaNatural?.apellidoPaterno}"/>
					<isl:textOutput cols="1-10" rotulo="Apellido materno" valor="${prestadorInstance?.personaNatural?.apellidoMaterno}"/>
				</g:else>
								
				<isl:textOutput cols="1-10" rotulo="Persona" valor="${prestadorInstance?.esPersonaJuridica ? "Jurídica" : "Persona"}"/>
				<isl:textOutput cols="1-10" rotulo="Tipo prestador" valor="${prestadorInstance?.tipoPrestador?.descripcion}"/>
				
				<isl:textOutput cols="1-10" rotulo="Nombre convenio" valor="${convenio?.nombre}"/>
				<isl:textOutput cols="3-10" rotulo="Tipo convenio" valor="${convenio?.tipoConvenio?.descripcion}"/>
				
				<div class='salto-de-linea'></div>

				<div class="form-subtitle">Agregar Prestaciones al Arancel en Convenio</div>
				<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Grupo" nombre="grupo" from="${grupo}" valor="${grupo?.codigo}" valor="${params?.grupo}"/>
				<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Sub grupo" nombre="subgrupo" from="${subgrupo}" valor="${subgrupo?.codigo}"/>
				<isl:textInput cols="1-8" tipo="text" nombre="codigo" rotulo="Prestación" /><div style="height: 17px;"></div>
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
					<div class='salto-de-linea'></div>
					<isl:combo rotulo ="Nivel" cols="1-8" noSelection="${['':'Seleccione ...']}" nombre="nivel" from="${['Nivel 1','Nivel 2', 'Nivel 3']}" optionKey="" optionValue=""/>
					<isl:combo rotulo ="Movimiento" cols="1-8" noSelection="${['':'Seleccione ...']}" nombre="operacion" from="${['Descuento','Cargo']}" optionKey="" optionValue=""/>
					<isl:textInput rotulo ="Valor" cols="1-8" tipo="numero" nombre="valor" />
					<isl:radiogroup rotulo="Tipo valor" cols="1-8" nombre="tipo" name="tipo" labels="['%','$']" values="['PRC','PESOS']" >
					${it.label}
					${it.radio}
					</isl:radiogroup>
					<isl:calendar cols="1-8" nombre="fechaArancel" rotulo ="Fecha"/>
					<button class="pure-button pure-button-secondary" id="btnAgregarArancel">Agregar al Arancel</button>			
				</div>
				<div class='salto-de-linea'></div>				
				
				<div class="form-subtitle">Mantener Prestaciones de Arancel</div>

				<div class='salto-de-linea'></div>
			
				<table id="dv_aranceles_convenio" class="pure-table" width="100%">
					<thead>
						<tr>
							<th>Eliminar&nbsp;&nbsp;&nbsp;<input type='checkbox' id='all_del_prestaciones'></th>
							<th>Prestación</th>
							<th>Glosa</th>
							<th>Nivel</th>
							<th>Valor</th>
							<th>Cálculo</th>
							<th>Convenido</th>
							<th>F. Desde</th>
							<th>F. Hasta</th>
							<th>Info.</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${listaRegistrosArancelesEnConvenio}">
							<g:each in="${listaRegistrosArancelesEnConvenio}" status="i" var="arancelConvenio">
								<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
									<td align="center"><input type="checkbox" id="ch_cnv_arc"  name="ch_cnv_arc" value="${arancelConvenio?.id}"></td>
									<td align="center">${arancelConvenio.codigoPrestacion}</td>
									<td title="${arancelConvenio?.glosa}">${FormatosHelper.truncateStatic(arancelConvenio?.glosa, 70)}</td>
									<td align="center">${arancelConvenio.nivel}</td>
									<td align="right">${FormatosHelper.montoStatic(arancelConvenio.valorOriginal)}</td>
									<td align="right">${arancelConvenio.calculo}</td>
									<td align="right">${FormatosHelper.montoStatic(arancelConvenio.valorNuevo)}</td>
									<td align="center">${FormatosHelper.fechaCortaStatic(arancelConvenio.desde)}</td>
									
									<td align="center">${FormatosHelper.sumarDiasStatic(arancelConvenio.hasta, -1)}</td>
									<td align="center">
										<g:if test="${arancelConvenio.hasta != null}">
											<g:if test="${arancelConvenio.tipoPrestacion == 'ARANCEL'}">
												<a title="Más información"
												   onclick="TINY.box.show({iframe:'../aranceles/ver_prestacion_detalle/list?arancelBaseId=${arancelConvenio.id}&codigoPrestacion=${arancelConvenio.codigoPrestacion}',boxid:'frameless',width:1000,height:400,fixed:false,maskid:'bluemask',maskopacity:40})" >
												   	<i class="icon-2x icon-info-sign color-success" style="cursor: pointer;"></i>
												</a>
											</g:if>
											<g:else>
												<a title="Más información"
												   onclick="TINY.box.show({iframe:'../aranceles/ver_paquete_detalle/list?codigoPrestacion=${arancelConvenio.codigoPrestacion}',boxid:'frameless',width:1000,height:400,fixed:false,maskid:'bluemask',maskopacity:40})" >
												   	<i class="icon-2x icon-info-sign color-success" style="cursor: pointer;"></i>
												</a>
											</g:else>
										</g:if>
										<g:else>
											&nbsp;
										</g:else>
									</td>
								</tr>
							</g:each>
						</g:if>
						<g:else>
							<tr  class="pure-table-odd">
								<td colspan="10"> Convenio sin Aranceles. </td>
							</tr>
							<input type="checkbox" id="ch_cnv_arc"  name="ch_cnv_arc" value="" style="display: none;">
						</g:else>										
					</tbody>
				</table>
			</div>
		</div>
	</fieldset>	
		
	<div class="pure-g-r">
		<div class="pure-u-1">
			<button class="pure-button pure-button-success" onclick="document.forms[0].action='edit_cnv';document.forms[0].submit();">Volver</button>
		</div>
	</div>
		
</g:form>

