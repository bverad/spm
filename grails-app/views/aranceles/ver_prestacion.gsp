<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form action="mantener_aranceles" class="pure-form pure-form-stacked">
	<fieldset>
		<isl:textOutput cols="3-10" rotulo="Tipo" valor="${tipo}"/>
		<isl:textOutput cols="1-10" rotulo="Prestación" valor="${arancelBase?.codigo}"/>
		<isl:textOutput cols="3-10" rotulo="Grupo" valor="${grupo?.descripcion}"/>
		<isl:textOutput cols="2-10" rotulo="Sub Grupo" valor="${arancelBase?.subGrupo?.descripcion}"/>
		<isl:textOutput cols="1-10" rotulo="Código" valor="${arancelBase?.codigo}"/>
		<isl:textOutput cols="4-10" rotulo="Glosa" valor="${arancelBase?.glosa}"/>
		<isl:textOutput cols="1-10" rotulo="Equipo" valor="${equipo}"/>
		<isl:textOutput cols="1-10" rotulo="Nivel pabellón" valor="${arancelBase?.nivelPabellon}"/>
		<isl:textOutput cols="2-10" rotulo="Vigencia desde" valor="${cl.adexus.helpers.FormatosHelper.fechaCortaStatic(arancelBase?.desde)}"/>
		<isl:textOutput cols="2-10" rotulo="Vigencia hasta" valor="${cl.adexus.helpers.FormatosHelper.fechaCortaStatic(arancelBase?.hasta)}"/>
		<div class="salto-de-linea" style="height: 20px;"></div>
		
		<g:if test="${arancelBase?.origen?.equals('FONASA')}">
			<table class="pure-table" width="100%">
				<thead>
					<tr>
						<th></td>
						<th>Nivel 1</th>
						<th>Nivel 2</th>
						<th>Nivel 3</th>
					</tr>
				</thead>
				<tbody>	
					<tr>
						<td>General / Procedimiento</td>
						<td>${arancelBase?.valorN1}</td>
						<td>${arancelBase?.valorN2}</td>
						<td>${arancelBase?.valorN3}</td>		
					</tr>
					<tr class="pure-table-odd">		
						<td>Anestesista</td>
						<td>${arancelBase?.anestesiaN1}</td>
						<td>${arancelBase?.anestesiaN2}</td>
						<td>${arancelBase?.anestesiaN3}</td>
					</tr>
					<tr>
						<td>Cirujano 1</td>
						<td>${arancelBase?.cirujano1N1}</td>
						<td>${arancelBase?.cirujano1N2}</td>
						<td>${arancelBase?.cirujano1N3}</td>
					</tr>
					<tr class="pure-table-odd">
						<td>Cirujano 2</td>
						<td>${arancelBase?.cirujano2N1}</td>
						<td>${arancelBase?.cirujano2N2}</td>
						<td>${arancelBase?.cirujano2N3}</td>
					</tr>
					<tr>
						<td>Cirujano 3</td>
						<td>${arancelBase?.cirujano3N1}</td>
						<td>${arancelBase?.cirujano3N2}</td>
						<td>${arancelBase?.cirujano3N3}</td>
					</tr>
					<tr class="pure-table-odd">
						<td>Cirujano 4</td>
						<td>${arancelBase?.cirujano4N1}</td>
						<td>${arancelBase?.cirujano4N2}</td>
						<td>${arancelBase?.cirujano4N3}</td>
					</tr>
					<tr>
						<td>Arcenalera</td>
						<td>${arcenaleraN1}</td>
						<td>${arcenaleraN2}</td>
						<td>${arcenaleraN3}</td>
					</tr>
				</tbody>
			</table>
		</g:if>				
		
		<g:if test="${arancelBase?.origen?.equals('ISL')}">
			<table class="pure-table" width="50%">
				<thead>
					<tr>
						<th></th>
						<th>Valor</th>
					</tr>
				</thead>
				<tbody>	
					<tr>
						<td>General / Procedimiento</td>
						<td align="right"> ${FormatosHelper.montoStatic(arancelBase?.valorN1)} </td>
					<tr>
						<td>Anestesista</td>
						<td align="right"> ${FormatosHelper.montoStatic(arancelBase?.anestesiaN1)} </td>
					</tr>
					<tr>
						<td>Cirujano 1</td>
						<td align="right"> ${FormatosHelper.montoStatic(arancelBase?.cirujano1N1)} </td>
					</tr>
					<tr>
						<td>Cirujano 2</td>
						<td align="right"> ${FormatosHelper.montoStatic(arancelBase?.cirujano2N1)} </td>
					</tr>
					<tr>
						<td>Cirujano 3</td>
						<td align="right"> ${FormatosHelper.montoStatic(arancelBase?.cirujano3N1)} </td>
					</tr>
					<tr>
						<td>Cirujano 4</td>
						<td align="right"> ${FormatosHelper.montoStatic(arancelBase?.cirujano4N1)} </td>
					</tr>
					<tr>
						<td>Arcenalera</td>
						<td align="right"> ${FormatosHelper.montoStatic(arcenaleraN1)} </td>
					</tr>
				</tbody>
			</table>
		
		</g:if>
	</fieldset>
	<div class="pure-g-r">
		<div class="pure-u-1">
			<isl:button tipo="volver" value="Volver" action="mantener_aranceles"/>
		</div>
	</div>
	
</g:form>