<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="tinybox.js" />
<g:form action="mantener_aranceles" class="pure-form pure-form-stacked">
	<fieldset>
		<g:hiddenField name="paqueteId" value="${paquete?.id}"/>
		<g:hiddenField name="codigo" value="${paquete?.codigo}"/>
		
		<isl:textOutput cols="2-8" rotulo="Tipo"			valor="${paquete?.tipoPaquete?.descripcion}"/>
		<isl:textOutput cols="3-8" rotulo="Grupo"			valor="${paquete?.subGrupo?.grupo?.descripcion}"/>
		<isl:textOutput cols="3-8" rotulo="Sub Grupo"		valor="${paquete?.subGrupo?.descripcion}"/>
		
		<isl:textOutput cols="1-8" rotulo="Código"			valor="${paquete?.codigo}"/>
		<isl:textOutput cols="4-8" rotulo="Glosa"			valor="${paquete?.glosa}"/>
		<isl:textOutput cols="1-8" rotulo="Paquete"			valor="${paquete?.codigo}"/>
		<isl:textOutput cols="1-8" rotulo="Complejidad"		valor="${paquete?.complejidad}"/>
		<isl:textOutput cols="1-8" rotulo="Efectividad %"	valor="${paquete?.efectividad}"/>
		
		<isl:textOutput cols="1-8" rotulo="Escalamiento"	valor="${paquete?.escalamiento}"/>
		<isl:textOutput cols="1-8" rotulo="Reposo Estimado"	valor="${paquete?.reposoEstimado}"/>	
		<isl:textOutput cols="1-8" rotulo="Vigencia Desde"	valor="${FormatosHelper.fechaCortaStatic(paquete?.desde)}"/>
		<isl:textOutput cols="1-8" rotulo="Vigencia Hasta"	valor="${FormatosHelper.fechaCortaStatic(paquete?.hasta)}"/>	
	
		<div class="salto-de-linea"></div>
		<div class="form-subtitle"> Prestaciones del Paquete </div>
		<div class='salto-de-linea'></div>

		<table class="pure-table" width="100%">
			<thead>
				<tr>
					<th>Prestación</th>
					<th>Glosa</th>
					<th>Nivel</th>
					<th>Valor</th>
					<th>Calculo</th>
					<th>Cantidad</th>
					<th>Total</th>
					<th>Ver</th>
				</tr>
			</thead>
			<tbody>
				<g:if test="${!arancelesPaquete}">
					<tr class="even">
						<td colspan="8"> Paquete sin aranceles asociados. </td>
					</tr>
				</g:if>
				<g:each in="${arancelesPaquete}" status="i" var="arancelPaquete">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td align=center>${arancelPaquete.codigoPrestacion}</td>
						<td title="${arancelPaquete.glosa}">${FormatosHelper.truncateStatic(arancelPaquete.glosa, 70)}</td>
						<td align=center>${arancelPaquete.nivel}</td>
						<td align=right>${arancelPaquete.cantidad}</td>
						<td align=right>${FormatosHelper.montoStatic(arancelPaquete.valorOriginal)}</td>
						<td align=right>${arancelPaquete.calculo}</td>
						<td align=right>${FormatosHelper.montoStatic(arancelPaquete.valorNuevo)}</td>
						<td align=center><a title="Más información" onclick="TINY.box.show({iframe:'../aranceles/ver_prestacion_detalle/list?arancelBaseId=${arancelPaquete.id}&codigoPrestacion=${arancelPaquete.codigoPrestacion}',boxid:'frameless',width:1000,height:400,fixed:false,maskid:'bluemask',maskopacity:40})" ><i class="icon-2x icon-info-sign color-success" style="cursor: pointer;"></i></a></td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</fieldset>
	<div class="pure-g-r">
		<div class="pure-u-1">
			<isl:button tipo="volver" value="Volver" action="mantener_aranceles"/>
		</div>
	</div>
	
</g:form>