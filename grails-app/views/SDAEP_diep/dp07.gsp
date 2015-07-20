<g:form name="dp07" class="pure-form pure-form-stacked" >
	<fieldset>
 		<legend>Guardar DIEP firmada</legend>
		<isl:header_identification aFecha="${flash.fechaAccidente}" 
			eRut="${flash.runTrabajador}" eNombre="${flash.nombreEmpleador}"
			tRun="${flash.rutEmpleador}" tNombre="${flash.nombreTrabajador}" />
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:box texto="La DIEP debe estar firmada por el denunciante antes de guardarla"/>
			</div>
		</div>
	</fieldset>	
		<g:actionSubmit value="Terminar" controller="SDAAT_ident" action="index" class="pure-button pure-button-warning"  />
		<g:actionSubmit value="Guardar DIAT firmada" action="r04" class="pure-button pure-button-success"  />
	</div>
</g:form>