
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
    	if(data.length>0){
    		subGrupoSelect.set("disabled",false)
    	}else{
    		subGrupoSelect.set("disabled",true)
    	}
	    data=null
    }
    
    var desplegarTipo= function() {
    	
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
        	Y.one("#anestesiaN1").set('required', true);

        	Y.one("#cirujano1N1").show();
        	Y.one("#cirujano1N1").set('required', true);
        	Y.one("#cirujano2N1").show();
        	Y.one("#cirujano2N1").set('required', true);
        	Y.one("#cirujano3N1").show();
        	Y.one("#cirujano3N1").set('required', true);
        	Y.one("#cirujano4N1").show();
        	Y.one("#cirujano4N1").set('required', true);

        	//Y.one("#arcenaleraN1").set('value', "");
        	Y.one("#arcenaleraN1").show();    		

        	//Y.one("#nivelPabellon").set('value', "");
        	Y.one("#nivelPabellon").set('readOnly', false);
        	
    	}
    	
    }
  
    //Y.one("#subgrupo").set("disabled",true)   
    Y.one("#grupo").on("change", buscarSubGrupo);
    Y.one("#tipo").on("change", desplegarTipo);
    
    Y.on('contentready', desplegarTipo, '#dv_data');
    
    Y.one('#cirujano1N1').on('blur', function(e) {
    	
    	if (isNumber(Y.one("#cirujano1N1").get('value'))) {
    		var valor = 0;
    		valor = Math.floor(Y.one("#cirujano1N1").get('value')/10);
    		Y.one("#arcenaleraN1").set('value', valor);
    	}else {
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
    
    var calcularEquipo= function() {
    	var equipo = 0;
    	
    	if (isNumber(Y.one("#cirujano1N1").get('value')) && Y.one("#cirujano1N1").get('value') > 0) {
    		equipo++;
    	}
    	
    	if (isNumber(Y.one("#cirujano2N1").get('value')) && Y.one("#cirujano2N1").get('value') > 0)
    		equipo++;
    	
    	if (isNumber(Y.one("#cirujano3N1").get('value')) && Y.one("#cirujano3N1").get('value') > 0)
    		equipo++;
    	
    	if (isNumber(Y.one("#cirujano4N1").get('value')) && Y.one("#cirujano4N1").get('value') > 0)
    		equipo++;
    	
    	Y.one("#equipo").set('value', equipo);
    }
    
    
});