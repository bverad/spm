<g:form name="dp01" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>Ingrese RUN Trabajador</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" requerido="true" 
					nombre="run" rotulo="RUN Trabajador"  valor="${trabajador?.run}"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="r01"/>
	</div>
</g:form>
