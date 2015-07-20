<g:form name="dp04" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">
	<isl:header_sdaat sdaat="${sdaat}"/>	
	<fieldset>
		<legend>Escanea y Guarda</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:upload cols="4-8" nombre="fileDenuncia" tipo="SDAAT" rotulo="Denuncia firmada"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button action="uploadDiatFirmada" tipo="pdf" value="Subir denuncia firmada" />
		<isl:button action="r04" tipo='siguiente'/>
	</div>
</g:form>
