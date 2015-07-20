<script type="text/javascript">
YUI().use('event', "node", "transition", "panel", "io", "json-parse",  function (Y) {  // loading escape only for security on this page

	var isPositiveInteger= function (n) {
	    return n >>> 0 === parseFloat(n);
	}
		
	var revisarTipoReporte= function() {
    	var index = Y.one("#reporte").get('selectedIndex');
    	var tipo = Y.one("#reporte").get("options").item(index).getAttribute('value');

    	if (tipo == "r3_1") {
    		Y.one("#dv_fields").show();
        } else {
        	Y.one("#dv_fields").hide();
        }

    	document.forms[0].action = tipo;

	}
	
	Y.one("#reporte").on("change", revisarTipoReporte);

	var btnReporte = Y.one("#btnReporte");
	btnReporte.on('click', function (e) {

    	this.get('id'); 
        e.target.get('id'); 
        e.preventDefault();
        e.stopPropagation();
        		
		var msgError = "";
		var pos = 0;
    	var index = Y.one("#reporte").get('selectedIndex');
    	var tipo = Y.one("#reporte").get("options").item(index).getAttribute('value');

		if (tipo == "r3_1") {
			var year = Y.one("#year").get('value');
			if (year == "") {
				msgError += "Error, se debe ingresar el año.\n";
			} else {
				if (isPositiveInteger(year)) {
					if (year < 2000)
						msgError += "Error, el año debe ser un valor entero mayor a al año 2000.\n";
				} else {
					msgError += "Error, el año debe ser un valor entero mayor a al año 2000.\n";
				}
			}
	
			var mes = Y.one("#month").get('value');
			if (mes == "") {
				msgError += "Error, se debe ingresar el mes.\n";
			} else {
				if (isPositiveInteger(mes)) {
					if (mes > 12)
						msgError += "Error, el mes debe ser un valor entero menor o igual a 12.\n";
				} else {
					msgError += "Error, el mes debe ser un valor entero menor o igual a 12.\n";
				}				
			}
		}

		if (msgError == "")
			document.forms[0].submit();
		else
			alert(msgError);

		return false;
    });	
	
})

</script>

<g:form name="r3_1" class="pure-form pure-form-stacked" >

	<fieldset>
 	<legend>Reportes</legend>
 	<isl:combo cols="3-8" noSelection="${['':'Seleccione ...']}" rotulo="Reportes" nombre="reporte" from="${listaReportes}"/>
	</fieldset>
	<div class='salto-de-linea'></div>

	<div id="dv_fields" style="display: none;">	
		<fieldset>
	 	<legend>Parámetros</legend>
			<isl:textInput cols="1-8"  tipo="numero" nombre="month" rotulo="Mes"     valor="${month}"/>
			<isl:textInput cols="1-8"  tipo="numero" nombre="year" rotulo="Año"     valor="${year}"/>
		</fieldset>
		<div class='salto-de-linea'></div>
	</div>
		
	<div class="pure-g-r">
		<button title="Generar Reporte"
			class="pure-button pure-button-success"
		    id="btnReporte">Generar Reporte</button>
	</div>		
</g:form>
