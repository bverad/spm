<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="SDAEP/diep_dp01.js" />
<g:form name="dp01" class="pure-form pure-form-stacked" >
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
 		<legend>Identifica Denunciante</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:combo cols="1-8" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Clasificación" nombre="tipoDenunciante" from="${calificaciones}" valor="${sdaep?.tipoDenunciante?.codigo}"/>
    			<isl:textInput cols="1-8" requerido="true" nombre="run" rotulo="RUN Denunciante" valor="${denunciante?.run}"/>
    			<g:hiddenField name="trabajador_run" value="${FormatosHelper.runFormatStatic(sdaep?.trabajador?.run)}" />
			</div>
		</div>
	</fieldset>
	<div class="pure-g-r">
		<isl:button tipo="siguiente" action="r01"/>		
	</div>
</g:form>