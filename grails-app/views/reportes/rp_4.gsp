<g:form name="r4_sp" class="pure-form pure-form-stacked" >

	<fieldset>
 	<legend>Reportes sin Parametros</legend>
 	<isl:combo cols="3-8" noSelection="${['':'Seleccione ...']}" rotulo="Reportes" nombre="reporte" from="${listaReportes}"/>
	<div class='salto-de-linea'></div>

	</fieldset>
	
	<div class="pure-g-r">
		<isl:button action="rp4_sp" value="Generar Reporte" target="_blank"/>
	</div>	
	
</g:form>
