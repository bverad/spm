<g:form name="dp01" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>¿Desea generar la OPA?</legend>	
		&nbsp; 
		<div class="pure-g-r">
			<isl:button tipo="cancelar" value="Terminar" action="cu01termina" />
			<isl:button tipo="siguiente" value="Generar OPA" action="dp02" />
		</div>
	</fieldset>
</g:form>