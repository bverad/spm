<script type="text/javascript">
YUI().use('event', "node", "transition", "panel", "io", "json-parse",  function (Y) {  // loading escape only for security on this page

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
    	    oStr='<option value="'+p.codigo+'">'+p.descripcion+'</option>'
    	    comunasSelect.appendChild( Y.Node.create(oStr))
    	}
    	if(data.length>0){
    		comunasSelect.set("disabled",false)
    	}else{
    		comunasSelect.set("disabled",true)
    	}
	    data=null
    }
	
	
	var revisarTipoReporte= function() {
    	var index = Y.one("#reporte").get('selectedIndex');
    	var tipo = Y.one("#reporte").get("options").item(index).getAttribute('value');

    	if (tipo == "r1_1") {
    		Y.one("#dv_fields").show();
        } else {
        	Y.one("#dv_fields").hide();
        }

    	if (tipo == "r1_1") {
    		document.forms[0].action = "cu1_1";
    	} else {
    		document.forms[0].action = "rp1_sp";
        }

	}
	
	Y.one("#reporte").on("change", revisarTipoReporte);
	Y.one("#region").on("change", buscarComunas);

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

    	if (tipo == "r1_1") {
        	
			//valida rut prestador
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

        	//Valida Selección de región
			pos = Y.one("#region").get('selectedIndex');
			var region = Y.one("#region").get("options").item(pos).getAttribute('value');

			if (region == ""){
				msgError += "Error, se debe seleccionar una region. \n";
			}
			
			//Valida Selección de comuna
			pos = Y.one("#comuna").get('selectedIndex');
			var comuna = Y.one("#comuna").get("options").item(pos).getAttribute('value');

			if (comuna == ""){
				msgError += "Error, se debe seleccionar una comuna. \n";
			}

			//Validación de ingreso código prestación
			var prestacion = Y.one("#prestacion").get("value")
			if (prestacion == ""){
				msgError += "Error, se debe ingresar un código de prestación. \n";
			}
			
			//Validación de selección de fecha
			var dia = Y.one("#fecha_day").get("value")
			var mes = Y.one("#fecha_month").get("value")
			var año = Y.one("#fecha_year").get("value")
			
			if (dia == "" || mes == "" || año == ""){
				msgError += "Error, se debe ingresar una fecha. \n";
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

	<div id="dv_fields" style="display: none;">
		<fieldset>
	 	<legend>Parámetros</legend>
		<isl:textInput cols="1-8"  nombre="rut"     rotulo="Rut Prestador"     valor=""/>
		<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Region" nombre="region" from="${regiones}"/>
		<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Comuna" nombre="comuna" from="${comunas}"/>
		<isl:textInput cols="1-8"  nombre="prestacion" rotulo="Código Prestación" valor=""/>
		<isl:calendar cols="1-8"  nombre="fecha"      rotulo="Fecha"             valor=""/>
		</fieldset>
		<div class='salto-de-linea'></div>
	</div>
	
	<div class='salto-de-linea'></div>
	<div class='salto-de-linea'></div>
	
	<div class="pure-g-r">
		<button title="Generar Reporte"
			class="pure-button pure-button-success"
		    id="btnReporte">Generar Reporte</button>
	</div>	
</g:form>
