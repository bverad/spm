<g:form name="index" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>DIAT</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" requerido="true" requires="true" 
					nombre="diatId" rotulo="ID de DIAT" valor="${diat.id}"/>
				<isl:textOutput cols="7-8" requerido="true" requires="true" 
					nombre="error" rotulo="Error" valor="${error}"/>
	    	</div>
	    	<div class="pure-u-1">
				<isl:textArea cols="4-8" requerido="true" requires="true" 
					nombre="xmlEnviado" rotulo="xmlEnviado" valor="${diat.xmlEnviado}"/>
				<isl:textArea cols="4-8" requerido="true" requires="true" 
					nombre="xmlRecibido" rotulo="xmlRecibido" valor="${diat.xmlRecibido}"/>
			</div>
    	</div>
    	<g:hiddenField name="diatId" value="${diat.id}" />
	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="borrarXMLEnviado" value="Borrar xmlEnviado y xmlRecibido"/>
		<isl:button  action="borrarXMLRecibido" value="Borrar xmlRecibido"/>
	</div>
</g:form>