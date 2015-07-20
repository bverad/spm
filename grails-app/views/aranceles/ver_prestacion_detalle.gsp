<meta name='layout' content='isl_layout_modal_plain'/>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form class="pure-form pure-form-stacked">
	<fieldset>
	<legend>Información de prestación en arancel base</legend>
	</fieldset>
</g:form>	
	
	<div class="pure-g-r">
        <div class="pure-u-3-5">
			<!-- 1 columna  -->
		
			<g:form class="pure-form pure-form-stacked">
				<isl:textOutput cols="7-10" rotulo="Tipo" valor="${tipo}"/>
				<isl:textOutput cols="3-10" rotulo="Prestación" valor="${arancelBase?.codigo}"/>
				
				<isl:textOutput cols="7-10" rotulo="Grupo" valor="${grupo?.descripcion}"/>
				<isl:textOutput cols="3-10" rotulo="Código" valor="${arancelBase?.codigo}"/>
				
				<isl:textOutput cols="7-10" rotulo="Sub Grupo" valor="${arancelBase?.subGrupo?.descripcion}"/>
				<isl:textOutput cols="3-10" rotulo="Equipo" valor="${equipo}"/>
				
				<isl:textOutput cols="7-10" rotulo="Glosa" valor="${arancelBase?.glosa}"/>
				<isl:textOutput cols="3-10" rotulo="Nivel pabellón" valor="${arancelBase?.nivelPabellon}"/>
				
				<isl:textOutput cols="10-10" rotulo="Vigencia desde" valor="${FormatosHelper.fechaCortaStatic(arancelBase?.desde)}"/>
				<isl:textOutput cols="10-10" rotulo="Vigencia hasta" valor="${FormatosHelper.fechaCortaStatic(arancelBase?.hasta)}"/>
			</g:form>
			

			<!-- 1 columna  -->
        </div>




        <div class="pure-u-1-5">
        	<!-- 2 columna  -->
	        <g:if test="${arancelBase?.origen?.equals('FONASA')}">
				<table class="pure-table">
					<thead>
						<tr>
							<th width="70%"></td>
							<th width="10%">Nivel&nbsp;1</th>
							<th width="10%">Nivel&nbsp;2</th>
							<th width="10%">Nivel&nbsp;3</th>
						</tr>
					</thead>
					<tbody>	
						<tr>
							<td>General&nbsp;/&nbsp;Procedimiento</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.valorN1)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.valorN2)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.valorN3)}</td>		
						</tr>
						<tr class="pure-table-odd">		
							<td>Anestesista</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.anestesiaN1)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.anestesiaN2)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.anestesiaN3)}</td>
						</tr>
						<tr>
							<td>Cirujano 1</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano1N1)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano1N2)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano1N3)}</td>
						</tr>
						<tr class="pure-table-odd">
							<td>Cirujano 2</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano2N1)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano2N2)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano2N3)}</td>
						</tr>
						<tr>
							<td>Cirujano 3</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano3N1)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano3N2)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano3N3)}</td>
						</tr>
						<tr class="pure-table-odd">
							<td>Cirujano 4</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano4N1)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano4N2)}</td>
							<td align=right>${FormatosHelper.montoStatic(arancelBase?.cirujano4N3)}</td>
						</tr>
						<tr>
							<td>Arcenalera</td>
							<td align=right>${FormatosHelper.montoStatic(arcenaleraN1)}</td>
							<td align=right>${FormatosHelper.montoStatic(arcenaleraN2)}</td>
							<td align=right>${FormatosHelper.montoStatic(arcenaleraN3)}</td>
						</tr>
					</tbody>
				</table>
			</g:if>				
			
			<g:if test="${arancelBase?.origen?.equals('ISL')}">
				<table class="pure-table" width="100%">
					<thead>
						<tr>
							<th width="90%"></th>
							<th width="10%">Valor</th>
						</tr>
					</thead>
					<tbody>	
						<tr>
							<td>General / Procedimiento</td>
							<td align=right><isl:textOutput cols="6-8" rotulo="" valor="${FormatosHelper.montoStatic(arancelBase?.valorN1)}" /></td>
						<tr>
							<td>Anestesista</td>
							<td align=right><isl:textOutput cols="6-8" rotulo="" valor="${FormatosHelper.montoStatic(arancelBase?.anestesiaN1)}" /></td>
						</tr>
						<tr>
							<td>Cirujano 1</td>
							<td align=right><isl:textOutput cols="6-8" rotulo="" valor="${FormatosHelper.montoStatic(arancelBase?.cirujano1N1)}" /></td>
						</tr>
						<tr>
							<td>Cirujano 2</td>
							<td align=right><isl:textOutput cols="6-8" rotulo="" valor="${FormatosHelper.montoStatic(arancelBase?.cirujano2N1)}" /></td>
						</tr>
						<tr>
							<td>Cirujano 3</td>
							<td align=right><isl:textOutput cols="6-8" rotulo="" valor="${FormatosHelper.montoStatic(arancelBase?.cirujano3N1)}" /></td>
						</tr>
						<tr>
							<td>Cirujano 4</td>
							<td align=right><isl:textOutput cols="6-8" rotulo="" valor="${FormatosHelper.montoStatic(arancelBase?.cirujano4N1)}" /></td>
						</tr>
						<tr>
							<td>Arcenalera</td>
							<td align=right><isl:textOutput cols="6-8" rotulo="" valor="${FormatosHelper.montoStatic(arcenaleraN1)}" /></td>
						</tr>
					</tbody>
				</table>
			
			</g:if>
            <!-- 2 columna  -->
        </div>






    </div>
	
	
	

		
		
