<g:form name="dp05" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">
	<isl:header_sdaat sdaat="${sdaat}"/>
	
	
	<fieldset>
		<legend>Escanea y guarda cuestionario</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:upload cols="4-8" nombre="fileCuestionario" tipo="SDAAT" rotulo="Cuestionario firmado"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">

		<isl:button action="genPdf" tipo="pdf" value="Generar PDF para firmar" />
		<isl:button action="uploadCuestionario" tipo="siguiente" value="Subir PDF firmado" />
		<isl:button action="cu05t" tipo='terminar'/>
	</div>
</g:form>