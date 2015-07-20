<g:form name="dp04" class="pure-form pure-form-stacked" >
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
		<legend>Solicitud de reingreso</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="2-8" requerido="true" nombre="nombresSolicitanteReingreso" rotulo="Nombres" valor="${sdaep?.denunciante?.nombre}"/>
				<isl:textInput cols="1-8" requerido="true" nombre="apellidoPatSolicitanteReingreso" rotulo="Apellido paterno" valor="${sdaep?.denunciante?.apellidoPaterno}"/>
				<isl:textInput cols="1-8" requerido="true" nombre="apellidoMatSolicitanteReingreso" rotulo="Apellido materno" valor="${sdaep?.denunciante?.apellidoMaterno}"/>
				<isl:textInput cols="1-8" requerido="true" nombre="rutSolicitanteReingreso" rotulo="RUT" valor="${sdaep?.denunciante?.run}"/>
				<isl:textInput cols="1-8" requerido="true" nombre="telefonoSolicitanteReingreso" rotulo="Telefono" valor=""/>
				<isl:textInput cols="2-8" requerido="true" nombre="emailSolicitanteReingreso" rotulo="Email" valor=""/>			
			</div>
		</div>
	</fieldset> 
	<div class="pure-g-r">
		<div class="pure-u-1">
			<isl:button tipo="siguiente" value="Solicitar reingreso" action="r04" />			
		</div>
	</div>
</g:form>
