<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:form name="dp02" class="pure-form pure-form-stacked" >

	<fieldset>
 	<legend>Informe al Solicitante</legend>

	<h3>No existe siniestro que coincida con los datos ingresados</h3>
	<isl:textInput cols="1-8"  nombre="runTrabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(runTrabajador)}" deshabilitado="true" />
	<div class='salto-de-linea'></div>

	</fieldset>

	<div class="pure-g-r">
		<isl:button action="dp01" value="Volver" tipo="volver"/>	
	</div>
</g:form>
