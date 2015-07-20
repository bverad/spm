
YUI().use('event', "node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page
	
	var myVar;
	
	var isNumber = function (input) {
		var RE = /^-{0,1}\d*\.{0,1}\d+$/;
	    return (RE.test(input));
	}

	var buscarSubGrupo = function (e) {
		var grupoId=Y.one("#grupo").get('value');
		Y.io('SubGruposJSON?grupo='+grupoId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarSubGrupo(r.responseText);
    	        	buscarPrestacion();
    	        }
    	    }
    	});
	} 
    
    var llenarSubGrupo = function(jsonString){
    	var subGrupoSelect=Y.one("#subgrupo")
    	subGrupoSelect.get('childNodes').remove();
    	try {
    	    var data = Y.JSON.parse(jsonString);
    	}
    	catch (e) {
    	    alert("Error al recibir la lista de Sub Grupos.");
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
    	    subGrupoSelect.appendChild(Y.Node.create(oStr))
    	}
    	if(data.length>0) {
    		subGrupoSelect.set("disabled",false)
    	} else {
    		subGrupoSelect.set("disabled",true)
    	}
	    data = null
    }
    
    var desplegarTipo = function() {
    	var index = Y.one("#tipo").get('selectedIndex');
    	var tipo = Y.one("#tipo").get("options").item(index).getAttribute('value');
    	if (tipo.toUpperCase() == "GENERAL") {
        	Y.one("#valorN1").show();
        	Y.one("#valorN1").set('required', true);
        	Y.one("#anestesiaN1").hide();
        	Y.one("#cirujano1N1").hide();
        	Y.one("#cirujano2N1").hide();
        	Y.one("#cirujano3N1").hide();
        	Y.one("#cirujano4N1").hide();
        	Y.one("#anestesiaN1").hide();
        	Y.one("#arcenaleraN1").hide(); 
        	
        	Y.one("#anestesiaN1").set('value', "");
        	Y.one("#anestesiaN1").set('required', false);

        	Y.one("#cirujano1N1").set('value', "");
        	Y.one("#cirujano1N1").set('required', false);
        	Y.one("#cirujano2N1").set('value', "");
        	Y.one("#cirujano2N1").set('required', false);
        	Y.one("#cirujano3N1").set('value', "");
        	Y.one("#cirujano3N1").set('required', false);
        	Y.one("#cirujano4N1").set('value', "");
        	Y.one("#cirujano4N1").set('required', false);

        	Y.one("#arcenaleraN1").set('value', "");
        	
        	Y.one("#nivelPabellon").set('value', "");
        	Y.one("#nivelPabellon").set('readOnly', true);
        	Y.one("#equipo").set('value', "");
    	} else {
        	Y.one("#valorN1").hide();
        	Y.one("#valorN1").set('value', "");
        	Y.one("#valorN1").set('required', false);
        	Y.one("#anestesiaN1").show();
        	Y.one("#anestesiaN1").set('required', false);

        	Y.one("#cirujano1N1").show();
        	Y.one("#cirujano1N1").set('required', true);
        	Y.one("#cirujano2N1").show();
        	Y.one("#cirujano2N1").set('required', false);
        	Y.one("#cirujano3N1").show();
        	Y.one("#cirujano3N1").set('required', false);
        	Y.one("#cirujano4N1").show();
        	Y.one("#cirujano4N1").set('required', false);
        	//Y.one("#arcenaleraN1").set('value', "");
        	Y.one("#arcenaleraN1").show();    		
        	//Y.one("#nivelPabellon").set('value', "");
        	Y.one("#nivelPabellon").set('readOnly', false);
    	}
    }
    //Y.one("#subgrupo").set("disabled",true)   
    //Y.one("#grupo").on("change", buscarSubGrupo);
    Y.one("#tipo").on("change", desplegarTipo);
    Y.on('contentready', desplegarTipo, '#dv_data');
    Y.one('#cirujano1N1').on('blur', function(e) {
    	if (isNumber(Y.one("#cirujano1N1").get('value'))) {
    		var valor = 0;
    		valor = Math.floor(Y.one("#cirujano1N1").get('value')/10);
    		Y.one("#arcenaleraN1").set('value', valor);
    	} else {
    		Y.one("#arcenaleraN1").set('value', "");
    		Y.one("#cirujano1N1").set('value', "")
    	}
    });
    Y.one('#cirujano1N1').on('keyup', function (e) {
    	calcularEquipo();
    });
    Y.one('#cirujano2N1').on('keyup', function (e) {
    	calcularEquipo();
    });
    Y.one('#cirujano3N1').on('keyup', function (e) {
    	calcularEquipo();
    });
    Y.one('#cirujano4N1').on('keyup', function (e) {
    	calcularEquipo();
    });
    var calcularEquipo = function() {
    	var equipo = 0;
    	if (isNumber(Y.one("#cirujano1N1").get('value')) && Y.one("#cirujano1N1").get('value') > 0)
    		equipo++;
    	if (isNumber(Y.one("#cirujano2N1").get('value')) && Y.one("#cirujano2N1").get('value') > 0)
    		equipo++;
    	if (isNumber(Y.one("#cirujano3N1").get('value')) && Y.one("#cirujano3N1").get('value') > 0)
    		equipo++;
    	if (isNumber(Y.one("#cirujano4N1").get('value')) && Y.one("#cirujano4N1").get('value') > 0)
    		equipo++;
    	Y.one("#equipo").set('value', equipo);
    }
    
    function buscarPrestacion() {
        var grupo		= Y.one("#grupo")	.get("value");
        var subgrupo	= Y.one("#subgrupo").get("value");
        var codigo		= Y.one("#codigo")	.get("value");
        if (grupo != "" && subgrupo != "" && codigo != "") {
    		var params	= "?grupo=" + grupo + "&subgrupo=" + subgrupo + "&codigo=" + codigo;
    		Y.io("buscarPrestacionJSON" + params, {
        	    on : {
        	        success : function (tx, r) {
        	        	var data = Y.JSON.parse(r.responseText);
        	        	if (data.hayDatos) {
        	        		if (data.prestacion.cirujano1N1 > 0 ||
        	        			data.prestacion.cirujano2N1 > 0 ||
        	        			data.prestacion.cirujano3N1 > 0 ||
        	        			data.prestacion.cirujano4N1 > 0) {
        	        			Y.one("#tipo").set("value", "Quirurgica");
        	        		} else {
        	        			Y.one("#tipo").set("value", "General");
        	        		}
            	        	Y.one("#glosa").set("value", data.prestacion.glosa);
            	        	Y.one("#equipo").set("value", data.prestacion.equipo);
            	        	Y.one("#nivelPabellon").set("value", data.prestacion.nivelPabellon);
            	        	Y.one("#desde").get("parentNode").get("children").item(0).set('value', data.prestacion.desde);
            	        	Y.one("#valorN1").set("value", data.prestacion.valorN1);
            	        	Y.one("#anestesiaN1").set("value", data.prestacion.anestesiaN1);
            	        	Y.one("#cirujano1N1").set("value", data.prestacion.cirujano1N1);
            	        	Y.one("#cirujano2N1").set("value", data.prestacion.cirujano2N1);
            	        	Y.one("#cirujano3N1").set("value", data.prestacion.cirujano3N1);
            	        	Y.one("#cirujano4N1").set("value", data.prestacion.cirujano4N1);
        	        	} else {
    	        			//Y.one("#tipo").set("value", "");
	        	        	Y.one("#glosa").set("value", null);
	        	        	Y.one("#equipo").set("value", null);
	        	        	Y.one("#nivelPabellon").set("value", null);
	        	        	Y.one("#desde").get("parentNode").get("children").item(0).set('value', null);
	        	        	Y.one("#valorN1").set("value", null);
	        	        	Y.one("#anestesiaN1").set("value", null);
	        	        	Y.one("#cirujano1N1").set("value", null);
	        	        	Y.one("#cirujano2N1").set("value", null);
	        	        	Y.one("#cirujano3N1").set("value", null);
	        	        	Y.one("#cirujano4N1").set("value", null);
        	        	}
        	        	if (isNumber(Y.one("#cirujano1N1").get('value'))) {
        	        		var valor = 0;
        	        		valor = Math.floor(Y.one("#cirujano1N1").get('value')/10);
        	        		Y.one("#arcenaleraN1").set('value', valor);
        	        	}
        	        	desplegarTipo();
        	        }
        	    }
        	});
        }
        
    }
    
    Y.one("#grupo")		.on("change", buscarSubGrupo);
    Y.one("#subgrupo")	.on("change", buscarPrestacion);
    Y.one("#codigo")	.on("change", buscarPrestacion);
    
});