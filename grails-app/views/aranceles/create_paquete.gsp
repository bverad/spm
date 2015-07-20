<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="Arancel/c_paquete.js" />

<g:form action="save_paquete" class="pure-form pure-form-stacked">
	<g:hiddenField name="paquete" />
	<g:hiddenField name="desdeEdicion" value="false"/>
	<isl:combo cols="3-10" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Tipo" nombre="tipo" from="${tipoPaquetes}" valor="${paquete?.tipoPaquete?.codigo}"/>
	<isl:combo cols="2-10" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Grupo" nombre="grupo" from="${grupo}" valor="${paquete?.subGrupo?.grupo?.codigo}"/>
	<isl:combo cols="2-10" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Sub Grupo" nombre="subgrupo" from="${subgrupos}" valor="${paquete?.subGrupo?.codigo}"/>
	<isl:textInput cols="2-10" tipo="numero" requerido="true" nombre="codigo" rotulo="Código" valor="${paquete?.codigo}" maxLargo="3" />
	<isl:textInput cols="1-10" tipo="numero" requerido="true" nombre="efectividad" rotulo="Efectividad %" valor="${paquete?.efectividad}" maxLargo="3" />
	
	<div class='salto-de-linea'></div>
	
	<isl:textInput cols="3-10" tipo="text" requerido="true" nombre="glosa" rotulo="Glosa" valor="${paquete?.glosa}" />
	<isl:textInput cols="1-10" tipo="numero" requerido="true" nombre="complejidad" rotulo="Complejidad" valor="${paquete?.complejidad}" maxLargo="10" />
	<isl:combo cols="1-10" noSelection="${['':'Seleccione ...']}" requerido="true" rotulo="Escalamiento" nombre="escalamiento" from="${['Cirugía','Consulta']}" valor="${paquete?.escalamiento}" optionKey="" optionValue=""/>
	<isl:textInput cols="1-10" tipo="text" requerido="true" nombre="reposo" rotulo="Reposo estimado" valor="${paquete?.reposoEstimado}" />
	<isl:calendar cols="2-10" tipo="calendar" requerido="true" nombre="desde" rotulo="Vigencia desde" valor="${paquete?.desde}"/>

	<div class="pure-g-r">
		<div class="pure-u-1">
			<input type="button" class="pure-button pure-button-secondary" onclick="document.forms[0].action='mantener_aranceles';document.forms[0].submit();" value="Volver" />
			<isl:button tipo="siguiente" value="Grabar" action="save_paquete"/>
		</div>
	</div>
	
</g:form>