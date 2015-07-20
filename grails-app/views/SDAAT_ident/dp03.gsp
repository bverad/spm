<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp03" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
 		<legend>Identificación del empleador</legend>
 		<div class="pure-g-r">
			<div class="pure-u-1">
			
			<isl:textInput cols="1-8" requerido="true" requires="true" 
					nombre="rut" rotulo="RUT Empleador" valor="${FormatosHelper.runFormatStatic(rutEmpleador? rutEmpleador : empleador?.rut)}"/>
			</div>
		</div>							
	</fieldset>
	<div class="pure-g-r">
		<isl:button  action="r02"/>
	</div>
</g:form>
