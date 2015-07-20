<g:form name="dp01" class="pure-form pure-form-stacked" >
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
		<legend>Generar OPAEP</legend>
	</fieldset>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:button tipo="cancelar" value="Terminar" action="cu01termina" />
				<isl:button tipo="siguiente" value="Generar OPAEP" action="dp02" />
			</div>	
		</div>
</g:form>