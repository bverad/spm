<script type="text/javascript">
YUI().use('event', "node", "transition", "panel", "io", "json-parse",  function (Y) {  // loading escape only for security on this page

	var cnt = 0;

	var buscarComunas = function (e) {
		
		var regionId=Y.one("#region").get('value');
		
		Y.io('ComunasJSON?region='+regionId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarComunas(r.responseText)
    	        }
    	    }
    	});
		
	} 

    var llenarComunas= function(jsonString){
    	
    	var comunasSelect=Y.one("#comuna")
    	comunasSelect.get('childNodes').remove();
    	
    	try {
    	    var data = Y.JSON.parse(jsonString);
    	}
    	catch (e) {
    	    alert("Error al recibir la lista de Comunas." + e);
    	    return;
    	}
    	
    	if (data.length > 0) {
    		var oStr='<option value="">Seleccionar ...</option>'
    			comunasSelect.appendChild( Y.Node.create(oStr))
    	}
    	
    	for (var i = 0; i < data.length; i++) {
    	    var p = data[i];
    	    var n=p.descripcion	
    	    oStr='<option value="'+p.id+'">'+n+'</option>'
    	    comunasSelect.appendChild( Y.Node.create(oStr))
    	}
    	if(data.length>0){
    		comunasSelect.set("disabled",false)
    	}else{
    		comunasSelect.set("disabled",true)
    	}
	    data=null
    }
	

	var buscarCentroSalud = function (e) {
		
		var prestadorId=Y.one("#prestador").get('value');
		
		Y.io('CentrosDeSaludJSON?prestador='+prestadorId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarCentrosDeSalud(r.responseText)
    	        }
    	    }
    	});
		
	} 
	
    var llenarCentrosDeSalud= function(jsonString){
    	
    	var centroSaludSelect=Y.one("#centrosalud")
    	centroSaludSelect.get('childNodes').remove();
    	
    	try {
    	    var data = Y.JSON.parse(jsonString);
    	}
    	catch (e) {
    	    alert("Error al recibir la lista de Centros de Salud." + e);
    	    return;
    	}
    	
    	if (data.length > 0) {
    		var oStr='<option value="">Seleccionar ...</option>'
    			centroSaludSelect.appendChild( Y.Node.create(oStr))
    	}
    	
    	for (var i = 0; i < data.length; i++) {
    	    var p = data[i];
    	    var n=p.nombre	
    	    oStr='<option value="'+p.id+'">'+n+'</option>'
    	    centroSaludSelect.appendChild( Y.Node.create(oStr))
    	}
    	if(data.length>0){
    		centroSaludSelect.set("disabled",false)
    	}else{
    		centroSaludSelect.set("disabled",true)
    	}
	    data=null
    }
	
	var buscarSubGrupo = function (e) {
		
		var grupoId=Y.one("#grupo").get('value');
		
		Y.io('SubGruposJSON?grupo='+grupoId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarSubGrupo(r.responseText)
    	        }
    	    }
    	});
		
	} 
    
    var llenarSubGrupo= function(jsonString){
    	
    	var subGrupoSelect=Y.one("#subgrupo")
    	subGrupoSelect.get('childNodes').remove();
    	
    	try {
    	    var data = Y.JSON.parse(jsonString);
    	}
    	catch (e) {
    	    alert("Error al recibir la lista de Sub Grupos." + e);
    	    return;
    	}
    	
    	if (data.length > 0) {
    		var oStr='<option value="">Seleccionar ...</option>'
    		subGrupoSelect.appendChild( Y.Node.create(oStr))
    	}
    	
    	for (var i = 0; i < data.length; i++) {
    	    var p = data[i];
    	    var n=p.descripcion	
    	    oStr='<option value="'+p.codigo+'">'+n+'</option>'
    	    subGrupoSelect.appendChild( Y.Node.create(oStr))
    	}
    	if(data.length>0){
    		subGrupoSelect.set("disabled",false)
    	}else{
    		subGrupoSelect.set("disabled",true)
    	}
	    data=null
    }
  
    //Y.one("#subgrupo").set("disabled",true)   
    Y.one("#grupo").on("change", buscarSubGrupo);
    Y.one("#prestador").on("change", buscarCentroSalud);
    Y.one("#region").on("change", buscarComunas);		
	
	
	var isPositiveInteger= function (n) {
	    return n >>> 0 === parseFloat(n);
	}
	
	var revisarTipoReporte= function() {
    	var index = Y.one("#reporte").get('selectedIndex');
    	var tipo = Y.one("#reporte").get("options").item(index).getAttribute('value');

    	var select = document.getElementById('region');
    	var slSucursal = document.getElementById('sucursal');
        var option = document.createElement('option');
        var optionSucursal = document.createElement('option');
    	option.text = "Todas las Regiones";
    	option.value = "0";
    	optionSucursal.text = "Todas las Sucursales"
    	optionSucursal.value = "0";	

    	var selectComuna = document.getElementById('comuna');
    	var optionComuna = document.createElement('option');
    	optionComuna.text = "Todas las Comunas";
    	optionComuna.value = "0";
     	

		if (cnt == 0) {
			select.appendChild(option);
			slSucursal.appendChild(optionSucursal);
			selectComuna.appendChild(optionComuna);
			cnt = 1;
		}

    	if (tipo != "")
    		Y.one("#fields").show();
    	else
    		Y.one("#fields").hide();

		Y.one("#dv_region").hide();
		Y.one("#dv_comuna").hide();
		Y.one("#dv_grupo").hide();
		Y.one("#dv_subgrupo").hide();
		Y.one("#dv_prestador").hide();
		Y.one("#dv_centrosalud").hide();
		Y.one("#dv_year").hide();
		Y.one("#dv_month").hide();
		Y.one("#dv_codigo").hide();
		Y.one("#dv_rut").hide();
		Y.one("#dv_desde").hide();
		Y.one("#dv_hasta").hide();	
		Y.one("#dv_sucursal").hide();				
		
    	if (tipo == "r5_4"|| tipo == "r5_6") {
    		Y.one("#fields").hide();
        }

		if (tipo == "r5_1" || tipo == "r5_2" || tipo == "r5_3" || tipo == "r5_5" || tipo == "r5_7" || tipo == "r5_8"
			 || tipo == "r5_12" || tipo == "r5_14") {
			Y.one("#dv_year").show();
			Y.one("#dv_month").show();
		}

		if (tipo == "r5_9") {
			Y.one("#dv_rut").show();
			Y.one("#dv_desde").show();
			Y.one("#dv_hasta").show();
		}		
		
		if (tipo == "r5_10") {
			Y.one("#dv_region").show();
			Y.one("#dv_comuna").show();
			Y.one("#dv_grupo").show();
			Y.one("#dv_subgrupo").show();
			Y.one("#dv_prestador").show();
			Y.one("#dv_centrosalud").show();
			Y.one("#dv_year").show();
			Y.one("#dv_month").show();
			Y.one("#dv_codigo").show();
		}		

		if (tipo == "r5_11") {
			Y.one("#dv_year").show();
			Y.one("#dv_month").show();
		}		

		if (tipo == "r5_13"){
			Y.one("#dv_desde").show();
			Y.one("#dv_hasta").show();	
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

		if (tipo == "r5_1" || tipo == "r5_2" || tipo == "r5_7" || tipo == "r5_3" 
			|| tipo == "r5_5" || tipo == "r5_11" || tipo == "r5_12" || tipo == "r5_13"
			|| tipo == "r5_14") {
			var year = Y.one("#year").get('value');
			var mes = Y.one("#month").get('value');
			
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

		if (tipo == "r5_9") {
			var rut = Y.one("#rut").get("value")
			var Fn = {
				    validaRut : function (rutCompleto) {
				    	rutCompleto = rutCompleto.replace('-','');
				    	rutCompleto = rutCompleto.replace(/\./g, '');
				        if (!/^[0-9]+[0-9kK]{1}$/.test( rutCompleto )) return false;
				        var rut = rutCompleto.substring(0,rutCompleto.length - 1)
				        var tmp = rutCompleto.substring(rutCompleto.length - 1);
				        if ( tmp == 'K' ) tmp = 'k';
				        return (Fn.dv(rut)) == tmp;
				    },
				    dv : function(T){
				       var M=0,S=1;
				       for(;T;T=Math.floor(T/10))
				           S=(S+T%10*(9-M++%6))%11;
				       return S?S-1:'k';
				   }
				}
		
			if (rut == ""){
				msgError += "Error, se debe ingresar un rut. \n";
			}else{
				if (!Fn.validaRut(rut)) {
					msgError += "Error, el rut ingresado no es válido. \n";
				}
			}
		}
		
		if (tipo == "r5_13" || tipo == "r5_9") {	
			//Validación de selección de fecha
			var desde_dia = Y.one("#desde_day").get("value")
			var desde_mes = Y.one("#desde_month").get("value")
			var desde_año = Y.one("#desde_year").get("value")
			
			//Validación de selección de fecha
			var hasta_dia = Y.one("#hasta_day").get("value")
			var hasta_mes = Y.one("#hasta_month").get("value")
			var hasta_año = Y.one("#hasta_year").get("value")
			
			if (desde_dia == "" || desde_mes == "" || desde_año == ""){
				msgError += "Error, se debe ingresar la fecha desde. \n";
			}		

			if (hasta_dia == "" || hasta_mes == "" || hasta_año == ""){
				msgError += "Error, se debe ingresar la fecha hasta. \n";
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

<g:form name="r1_1" class="pure-form pure-form-stacked" >

	<fieldset>
 	<legend>Reportes</legend>
 	<isl:combo cols="3-8" noSelection="${['':'Seleccione ...']}" rotulo="Reportes" nombre="reporte" from="${listaReportes}"/>
	</fieldset>

	<div class='salto-de-linea'></div>

	<div id="fields" style="display: none;">
		<fieldset>
	 	<legend>Parámetros</legend>
			<div id ="dv_region"><isl:combo 	   cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Region" nombre="region" from="${regiones}"/></div>
			<div id ="dv_comuna"><isl:combo 	   cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Comuna" nombre="comuna" from="${comunas}"/></div>
			<div id ="dv_sucursal"><isl:combo 	   cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Sucursal" nombre="sucursal" from="${sucursales}"/></div>
			<div id ="dv_grupo"><isl:combo 	       cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Grupo" nombre="grupo" from="${grupos}"/></div>
			<div id ="dv_subgrupo"><isl:combo 	   cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Sub Grupo" nombre="subgrupo" from="${subgrupos}"/></div>
			<div class='salto-de-linea'></div>
			<div id ="dv_prestador"><isl:combo     cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Prestador" nombre="prestador" from="${prestadores}"/></div>
			<div id ="dv_centrosalud"><isl:combo   cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Centros de Salud" nombre="centrosalud" from="${centrosalud}"/></div>
			<div id ="dv_year"><isl:textInput      cols="1-8" tipo="numero"   nombre="year"   rotulo="Año"     valor="${year}"/></div>
			<div id ="dv_month"><isl:textInput     cols="1-8" tipo="numero"   nombre="month"  rotulo="Mes"     valor="${month}"/></div>
			<div class='salto-de-linea'></div>
			<div id ="dv_rut"><isl:textInput       cols="1-8" tipo="rut"      nombre="rut"    rotulo="RUN"     valor=""/></div>
			<div id ="dv_desde"><isl:calendar      cols="1-8" tipo="calendar" nombre="desde"  rotulo="Desde"/></div>
			<div id ="dv_hasta"><isl:calendar      cols="1-8" tipo="calendar" nombre="hasta"  rotulo="Hasta"/></div>
			<div id ="dv_codigo"><isl:textInput    cols="1-8" tipo="texto"    nombre="codigo" rotulo="Código"     valor=""/></div>
		</fieldset>
		<div class='salto-de-linea'></div>
	</div>
	
	<div class="pure-g-r">
		<button title="Generar Reporte"
			class="pure-button pure-button-success"
		    id="btnReporte">Generar Reporte</button>
	</div>	
</g:form>
