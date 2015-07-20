<g:form name="dp03" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<legend>Revisión DIAT</legend>
	<!-- TODO: En este caso falta incluir al isl:header_sdaat el N° Siniestro -->
	<div class="pure-g-r">
		
			<!-- isl:button  action="genBorradorPdf" tipo="pdf"       value="Generar PDF DIAT Borrador" />
			<isl:button  action="dp02" 			 tipo="volver"    value="Corregir DIAT" />
			<isl:button  action="genPdf" 		 tipo="pdf"       value="Generar PDF DIAT Definitiva"  />			
			<isl:button  action="dp04" 			 tipo="siguiente" value="Denunciante aprueba DIAT"  />
			 -->
		
		<div class="pure-u-1">
			<input type="submit" name="_action_genBorradorPdf" value="Generar PDF DIAT Borrador" class="pure-button pure-button-warning" onClick= "activarBoton()">
			<input type="submit" name="_action_dp02" value="Corregir DIAT" class="pure-button pure-button-secondary">
			<input type="submit" name="_action_genPdf" value="Generar PDF DIAT Definitiva" class="pure-button pure-button-warning" disabled >			
			<input type="submit" name="_action_dp04" value="Denunciante aprueba DIAT" class="pure-button pure-button-success" disabled >
		</div>			
	</div>
			
	
</g:form>

<script>
function activarBoton(){

	//alert('Hola');
	document.dp03._action_genPdf.disabled=false;
	document.dp03._action_dp04.disabled=false;
	//alert('Chao');
}

</script>

