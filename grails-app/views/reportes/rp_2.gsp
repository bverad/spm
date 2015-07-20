<script type="text/javascript">
YUI().use('json-parse', 'event', "node", "transition", "panel", "io",  function (Y) {  // loading escape only for security on this page

	var cnt = 0;
	
	var buscarSucursales = function (e) {
		
		var regionId=Y.one("#region").get('value');
		if (regionId == "0"){
			var oStr='<option value="0">Todas las Sucursales</option>';
			Y.one("#sucursal").appendChild( Y.Node.create(oStr));	
		}else{
			Y.io('SucursalesJSON?region='+regionId, {
	    	    on : {
	    	        success : function (tx, r) {
	    	        	llenarSucursales(r.responseText)
	    	        }
	    	    }
	    	});
		}
	} 

    var llenarSucursales= function(jsonString){
    	
    	var sucursalSelect=Y.one("#sucursal")
    	sucursalSelect.get('childNodes').remove();
    	
    	try {
    	    var data = Y.JSON.parse(jsonString);
    	}
    	catch (e) {
    	    alert("Error al recibir la lista de Sucursales." + e);
    	    return;
    	}
    	
    	if (data.length > 0) {
    		var oStr='<option value="0">Seleccione ...</option>'
    			sucursalSelect.appendChild( Y.Node.create(oStr))
    	}
    	
    	for (var i = 0; i < data.length; i++) {
    	    var p = data[i];
    	    oStr='<option value="'+p.codigo+'">'+p.descripcion+'</option>'
    	    sucursalSelect.appendChild( Y.Node.create(oStr))
    	}
    	if(data.length>0){
    		sucursalSelect.set("disabled",false)
    	}else{
    		sucursalSelect.set("disabled",true)
    	}
	    data=null
    }
    
	var isPositiveInteger= function (n) {
	    return n >>> 0 === parseFloat(n);
	}
	
	var revisarTipoReporte= function() {
    	var index = Y.one("#reporte").get('selectedIndex');
    	var tipo = Y.one("#reporte").get("options").item(index).getAttribute('value');

		if (cnt == 0) {
			var oStr='<option value="0">Todas las Regiones</option>';
			Y.one("#region").appendChild( Y.Node.create(oStr));
			cnt = 1;
		}
    	
    	if (tipo != "")
    		Y.one("#dv_fields").show();
    	else
    		Y.one("#dv_fields").hide();


		if (tipo == "r2_1" || tipo == "r2_3" || tipo == "r2_5") {
			Y.one("#dv_region").show();
			Y.one("#dv_sucursal").hide();
			Y.one("#dv_year").show();
			Y.one("#dv_month").show();
		}
		if (tipo == "r2_2" || tipo == "r2_4" || tipo == "r2_6" || tipo == "r2_8" || tipo == "r2_10") {
			Y.one("#dv_region").show();
			Y.one("#dv_sucursal").show();
			Y.one("#dv_year").show();
			Y.one("#dv_month").show();
		}
		if (tipo == "r2_7" || tipo == "r2_9") {
			Y.one("#dv_region").set('required', true);
			Y.one("#dv_region").show();
			Y.one("#dv_sucursal").hide();
			Y.one("#dv_year").hide();
			Y.one("#dv_month").hide();
		} else {
			Y.one("#dv_region").set('required', false);
		}
		
    	document.forms[0].action = tipo;

	}
	
	Y.one("#reporte").on("change", revisarTipoReporte);
	Y.one("#region").on("change", buscarSucursales);

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

		if (tipo == "r2_1" || tipo == "r2_3" || tipo == "r2_5") {
			pos = Y.one("#region").get('selectedIndex');
			var region = Y.one("#region").get("options").item(pos).getAttribute('value');
			if (region == "")
				msgError += "Error, se debe seleccionar una region.\n";

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

		if (tipo == "r2_2" || tipo == "r2_6" || tipo == "r2_8" 
			|| tipo == "r2_4" || tipo == "r2_10") {
			pos = Y.one("#region").get('selectedIndex');
			var region = Y.one("#region").get("options").item(pos).getAttribute('value');
			if (region == "")
				msgError += "Error, se debe seleccionar una region.\n";			

			pos = Y.one("#sucursal").get('selectedIndex');
			var sucursal = Y.one("#sucursal").get("options").item(pos).getAttribute('value');
			if (sucursal == "")
				msgError += "Error, se debe seleccionar una sucursal.\n";	

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

    	if (tipo == "r2_7" || tipo == "r2_9") {
			pos = Y.one("#region").get('selectedIndex');
			var region = Y.one("#region").get("options").item(pos).getAttribute('value');

			if (region == "")
			msgError += "Error, se debe seleccionar una region.";
		}
    	
		if (msgError == "")
			document.forms[0].submit();
		else
			alert(msgError);

		return false;
    });
	
	
})

</script>

<g:form name="r2_1" class="pure-form pure-form-stacked" >

	<fieldset>
 	<legend>Reportes</legend>
 	<isl:combo cols="3-8" noSelection="${['':'Seleccione ...']}" rotulo="Reportes" nombre="reporte" from="${listaReportes}"/>
	</fieldset>

	<div id="dv_fields" style="display: none;">
		<fieldset>
	 	<legend>Parámetros</legend>
			<div id ="dv_region"><isl:combo 	   cols="2-8"  noSelection="${['':'Seleccione ...']}" rotulo="Region" nombre="region" from="${regiones}"/></div>
			<div id ="dv_sucursal"><isl:combo 	   cols="2-8"  noSelection="${['':'Seleccione ...']}" rotulo="Sucursal" nombre="sucursal" from="${sucursales}"/></div>
			<div id ="dv_year"><isl:textInput cols="1-8"  tipo="numero" nombre="year" rotulo="Año"     valor="${year}"/></div>
			<div id ="dv_month"><isl:textInput cols="1-8"  tipo="numero" nombre="month" rotulo="Mes"     valor="${month}"/></div>
		</fieldset>
		<div class='salto-de-linea'></div>
	</div>
	
	<div class="pure-g-r">
		<button title="Generar Reporte"
			class="pure-button pure-button-success"
		    id="btnReporte">Generar Reporte</button>
	</div>	
</g:form>
