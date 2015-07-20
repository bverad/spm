<g:form name="dp04" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
 		<legend>Escanea y guarda DIEP</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:upload cols="4-8" nombre="fileDenuncia" tipo="SDAEP" rotulo="Denuncia firmada"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<div class="pure-u-1">			
			<isl:button action="uploadDiepFirmada" tipo="pdf" value="Subir denuncia firmada" />
			<isl:button action="r04" tipo='siguiente'/>			
		</div>	
	</div>
</g:form>

