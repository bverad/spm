
YUI().use('event', "node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page
	
	var myVar;
	
	var isNumber = function (input) {
		var RE = /^-{0,1}\d*\.{0,1}\d+$/;
	    return (RE.test(input));
	}

	var buscarSubGrupo = function (e) {
		var grupoId = Y.one("#grupo").get('value');
		generarCodigoPaquete();
		Y.io('SubGruposJSON?grupo='+grupoId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarSubGrupo(r.responseText)
    	        }
    	    }
    	});
	} 
    
    var llenarSubGrupo = function(jsonString){
    	var subGrupoSelect=Y.one("#subgrupo")
    	subGrupoSelect.get('childNodes').remove();
    	try {
    	    var data = Y.JSON.parse(jsonString);
    	} catch (e) {
    	    alert("Error al recibir la lista de Sub Grupos.");
    	    return;
    	}
    	if (data.length > 0) {
    		var oStr='<option value="">Seleccione ...</option>'
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
    	} else {
    		subGrupoSelect.set("disabled",true)
    	}
	    data=null
    }
    
    
    var generarCodigoPaquete = function() {
    	/*
    	var grupo = Y.one("#grupo").get('value');
    	var subgrupo = Y.one("#subgrupo").get('value');
    	var codigo = Y.one("#codigo").get('value');
    	var paquete = grupo + " " + subgrupo + " " + codigo;
    	if (grupo != "" && subgrupo != "" && codigo != "")
    		Y.one("#paquete").set("value", paquete);
    	else
    		Y.one("#paquete").set("value", "");*/
    	// Invocar busqueda de prestacion
    	buscarPaquete();
    }

    function buscarPaquete() {
        var grupo		= Y.one("#grupo")	.get("value");
        var subgrupo	= Y.one("#subgrupo").get("value");
        var codigo		= Y.one("#codigo")	.get("value");
        if (grupo != "" && subgrupo != "" && codigo != "") {
    		var params	= "?grupo=" + grupo + "&subgrupo=" + subgrupo + "&codigo=" + codigo;
    		Y.io("buscarPaqueteJSON" + params, {
        	    on : {
        	        success : function (tx, r) {
        	        	var data = Y.JSON.parse(r.responseText);
        	        	if (data.hayDatos) {
        	        		var paquete = data.prestacion;
        	        		Y.one("#tipo")			.set("value", paquete.tipoPaquete.codigo);
        	        		Y.one("#efectividad")	.set("value", paquete.efectividad);
        	        		Y.one("#glosa")			.set("value", paquete.glosa);
        	        		Y.one("#complejidad")	.set("value", paquete.complejidad);
        	        		Y.one("#escalamiento")	.set("value", paquete.escalamiento);
        	        		Y.one("#reposo")		.set("value", paquete.reposoEstimado);
        	        		Y.one("#desde")			.get("parentNode").get("children").item(0).set('value', paquete.desde);
        	        	} else {
        	        		//Y.one("#tipo")			.set("value", null);
        	        		Y.one("#efectividad")	.set("value", null);
        	        		Y.one("#glosa")			.set("value", null);
        	        		Y.one("#complejidad")	.set("value", null);
        	        		Y.one("#escalamiento")	.set("value", null);
        	        		Y.one("#reposo")		.set("value", null);
        	        		Y.one("#desde")			.get("parentNode").get("children").item(0).set('value', null);
        	        	}
        	        }
        	    }
        	});
        }
        
    }

    Y.one("#grupo")		.on("change", buscarSubGrupo);
    Y.one("#subgrupo")	.on("change", generarCodigoPaquete);    
    Y.one('#codigo')	.on('change', generarCodigoPaquete);
});