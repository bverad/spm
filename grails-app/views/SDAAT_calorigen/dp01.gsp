<g:form name="dp01" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>Ingreso relato de accidente</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textArea cols="4-8" requerido="true" nombre="relatoSiniestro" rotulo="Relato del accidente" valor=""/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit value="Trayecto"  action="cu01y" class="pure-button pure-button-warning"  />
		<g:actionSubmit value="Trabajo"  action="cu01t" class="pure-button pure-button-success"  />
	</div>
</g:form>