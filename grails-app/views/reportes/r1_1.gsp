<g:form name="r1_1" class="pure-form pure-form-stacked" >

	<fieldset>
 	<legend>Ingresar Parámetros</legend>

	<isl:textInput cols="1-8"  nombre="rutPrestador"     rotulo="Rut Prestador"     valor=""/>
	<isl:textInput cols="1-8"  nombre="codigoRegion"     rotulo="Codigo Región"     valor=""/>
	<isl:textInput cols="1-8"  nombre="codigoPrestacion" rotulo="Código Prestación" valor=""/>
	<isl:textInput cols="1-8"  nombre="fecha"            rotulo="Fecha"             valor=""/>
	<div class='salto-de-linea'></div>

	</fieldset>
	
	<div class="pure-g-r">
		<isl:button action="cu1_1" value="Generar Reporte"/>
	</div>
</g:form>
