
YUI().use("node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page

    var buscaCentroAtencionPorPrestador = function(e) {
    	var prestadorId=Y.one("#prestador").get('value');
    	Y.io('centroSaludPorPrestadorJSON?prestadorId='+prestadorId, {
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
    	    var cs = data[i];
    	    var n=cs.nombre;
    	    //alert(p.name)
    	    var oStr1='<option value="0">Seleccione ...</option>';
    	    centroAtencionSelect.appendChild( Y.Node.create(oStr1));
    	    var oStr='<option value="'+cs.id+'">'+n+'</option>';
    	    centroAtencionSelect.appendChild( Y.Node.create(oStr));    	    
    	}
    	if(data.length>0){
    		centroAtencionSelect.set("disabled",false);
    	}else{
    		centroAtencionSelect.set("disabled",true);
    	}
	    data=null
    }
    
    Y.one("#centroAtencion").set("disabled",true);   
    Y.one("#prestador").on("change", buscaCentroAtencionPorPrestador);
    
});