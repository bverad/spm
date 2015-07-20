<g:form name="dp04" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
 		<legend>Identificación del Trabajador</legend>
 	
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:box texto="El trabajador no esta afiliado al ISL"/>
			</div>
		</div>
		 		
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textArea cols="4-8" nombre="motivo" rotulo="Indique el motivo de la excepción" valor="${excepcion?.motivo}" requerido="true"/>
			</div>
		</div>
		&nbsp;
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="4-8" nombre="autorizador" rotulo="Indique quien autoriza" valor="${excepcion?.autorizador}" requerido="true"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button action="cu04t" tipo='terminar' validar="false"/>
		<isl:button action="cu04s" tipo='siguiente'/>
	</div>
</g:form>
