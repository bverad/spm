<g:form name="dp01" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>Identificación del trabajador siniestrado</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" requerido="true" requires="true" 
					nombre="run" rotulo="RUN Trabajador" valor="${trabajador?.run}"/>
		    	
		    	<isl:calendar  cols="1-8" rotulo="Fecha de Accidente" nombre="fechaSiniestro" requerido="true"/>
	    	</div>
    	</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="r01"/>
	</div>
</g:form>
