<h3>Tareas Administrativas</h3>
<g:form name="diat" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>Enviar DIAT a SUSESO</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" requerido="true" requires="true" 
					nombre="diatId" rotulo="ID de DIAT" valor=""/>
	    	</div>
    	</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="enviarDIATaSUSESO"/>
	</div>
</g:form>

<g:form name="diep" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>Enviar DIEP a SUSESO</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" requerido="true" requires="true" 
					nombre="diepId" rotulo="ID de DIEP" valor=""/>
	    	</div>
    	</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="enviarDIEPaSUSESO"/>
	</div>
</g:form>