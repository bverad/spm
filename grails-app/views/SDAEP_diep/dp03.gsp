<g:form name="dp03" class="pure-form pure-form-stacked" >
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
 		<legend>Revisión DIEP</legend>
	</fieldset>	
	<div class="pure-g-r">
		<div class="pure-u-1">
		<!-- 
			<isl:button  action="genBorradorPdf" tipo="pdf"       value="Generar PDF DIEP Borrador" />
			<isl:button  action="dp02" 			 tipo="volver"    value="Corregir DIEP" />
			<isl:button  action="genPdf" 		 tipo="pdf"       value="Generar PDF DIEP Definitiva" />			
			<isl:button  action="dp04" 			 tipo="siguiente" value="Denunciante aprueba DIEP" />
		 -->
			<input type="submit" name="_action_genBorradorPdf" value="Generar PDF DIEP Borrador" class="pure-button pure-button-warning" onClick= "activarBoton()">
			<input type="submit" name="_action_dp02" value="Corregir DIEP" class="pure-button pure-button-secondary">
			<input type="submit" name="_action_genPdf" value="Generar PDF DIEP Definitiva" class="pure-button pure-button-warning" disabled>
			<input type="submit" name="_action_dp04" value="Denunciante aprueba DIEP" class="pure-button pure-button-success" disabled>
			
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