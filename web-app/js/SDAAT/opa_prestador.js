
YUI().use("node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page

	
    var buscarCentroAtencionPorRegion = function(e) {
    	var regionId=Y.one("#region").get('value');
    	Y.io('centroSaludPorPrestadorJSON?&regionId='+regionId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarCentroAtencion(r.responseText);
    	        }
    	    }
    	});
    }
    
    var llenarCentroAtencion= function(jsonString){
    	var centroAtencionSelect=Y.one("#centroAtencion");
    	centroAtencionSelect.get('childNodes').remove();
    	
    	//alert(jsonString)
    	var data = Y.JSON.parse(jsonString);
    	for (var i = 0; i <=data.length - 1; i++) {
    		var oStr = '';
    	    var cs = data[i];
    	    var n=cs.nombre;
    	    //alert(p.name)
    	    if (i == 0) {
    	    	oStr = '<option value="">Seleccione...</option>';
    	    	centroAtencionSelect.appendChild( Y.Node.create(oStr));
    	    }
    	    oStr = '<option value="'+cs.id+'">'+n+'</option>';
    	    centroAtencionSelect.appendChild( Y.Node.create(oStr));
    	}
    	if(data.length>1){
    		centroAtencionSelect.set("disabled",false);
    	}else{
    		centroAtencionSelect.set("disabled",true);
    	}
	    data=null
    }
    
    Y.one("#centroAtencion").set("disabled",true);
    Y.one("#region").on("change", buscarCentroAtencionPorRegion);
    
});