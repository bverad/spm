
YUI().use('event', "node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page
	
	var myVar;
	
	var buscarEmisor = function (e) {
		
		var rutEmisor=Y.one("#rut_emisor").get('value');
		
		Y.io('getEmisorJSON?rutEmisor='+rutEmisor, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarEmisor(r.responseText)
    	        }
    	    }
    	});
		
	} 
    
    var llenarEmisor= function(jsonString){
    	
    	var nombreEmisor=Y.one("#nombre_emisor")
    	nombreEmisor.set("value", null);	
    	
    	try {
    	    var data = Y.JSON.parse(jsonString);
    	}
    	catch (e) {
    	    alert("Error al recibir datos del emisor.");
    	    return;
    	}
    	
    	if (data) {
    		if (data.razonSocial){
    			nombreEmisor.set("value",data.razonSocial)
    		}else{
    			nombreEmisor.set("value",data.nombreFantasia)	
    		}
    		
    	}else{
    		nombreEmisor.set("disabled",false)
    	}
	    data=null
    }
  
    //Y.one("#subgrupo").set("disabled",true)   
    Y.one("#rut_emisor").on("change", buscarEmisor);	
    
});