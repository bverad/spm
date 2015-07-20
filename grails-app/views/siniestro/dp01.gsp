<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp01" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>Identificación del trabajador</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8"  
					nombre="run" rotulo="RUN Trabajador" valor="${t?.run}"/>
				<isl:textInput cols="1-8"  
					nombre="rutEmpresa" rotulo="RUT Empresa" valor="${e?.rut}"/>	
	    	</div>
    	</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="r01"/>
	</div>
</g:form>
