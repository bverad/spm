<g:form name="index" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>DIEP</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" requerido="true" requires="true" 
					nombre="diepId" rotulo="ID de DIEP" valor="${diep.id}"/>
				<isl:textOutput cols="7-8" requerido="true" requires="true" 
					nombre="error" rotulo="Error" valor="${error}"/>
	    	</div>
	    	<div class="pure-u-1">
				<isl:textArea cols="4-8" requerido="true" requires="true" 
					nombre="xmlEnviado" rotulo="xmlEnviado" valor="${diep.xmlEnviado}"/>
				<isl:textArea cols="4-8" requerido="true" requires="true" 
					nombre="xmlRecibido" rotulo="xmlRecibido" valor="${diep.xmlRecibido}"/>
			</div>
    	</div>
    	<g:hiddenField name="diepId" value="${diep.id}" />
	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="borrarXMLEnviadoEP" value="Borrar xmlEnviado y xmlRecibido"/>
		<isl:button  action="borrarXMLRecibidoEP" value="Borrar xmlRecibido"/>
	</div>
</g:form>