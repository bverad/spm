

YUI().use("node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page

    var buscaPrestardoresPorRegion = function(e) {
    	var comunaId=Y.one("#comuna").get('value');
    	Y.io('prestadoresPorRegionJSON?comunaId='+comunaId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarPrestador(r.responseText)
    	        }
    	    }
    	});
    }
    
    var llenarPrestador= function(jsonString){
    	var centroAtencionSelect=Y.one("#centroAtencion")
    	var prestadorSelect=Y.one("#prestador")
    	prestadorSelect.get('childNodes').remove();
    	centroAtencionSelect.set("disabled",true)
    	centroAtencionSelect.get('childNodes').remove();
    	
    	//alert(jsonString)
    	var data = Y.JSON.parse(jsonString);
    	for (var i = 0; i <=data.length - 1; i++) {
    	    var p = data[i];
    	    var n=p.personaJuridica.razonSocial
    	    //alert(p.name)
    	    var oStr='<option value="'+p.id+'">'+n+'</option>'
    	    prestadorSelect.appendChild( Y.Node.create(oStr))
    	}
    	if(data.length>0){
    		prestadorSelect.set("disabled",false)
    		buscaCentroAtencionPorPrestador()
    	}else{
    		prestadorSelect.set("disabled",true)
    	}
	    data=null
    }
    	

    var buscaCentroAtencionPorPrestador = function(e) {
    	var prestadorId=Y.one("#prestador").get('value');
    	var comunaId=Y.one("#comuna").get('value');
    	Y.io('centroSaludPorPrestadorJSON?prestadorId='+prestadorId+"&comunaId="+comunaId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarCentroAtencion(r.responseText)
    	        }
    	    }
    	});
    }
    
    var llenarCentroAtencion= function(jsonString){
    	var centroAtencionSelect=Y.one("#centroAtencion")
    	centroAtencionSelect.get('childNodes').remove();
    	
    	//alert(jsonString)
    	var data = Y.JSON.parse(jsonString);
    	for (var i = 0; i <=data.length - 1; i++) {
    	    var cs = data[i];
    	    var n=cs.nombre
    	    //alert(p.name)
    	    var oStr='<option value="'+cs.id+'">'+n+'</option>'
    	    centroAtencionSelect.appendChild( Y.Node.create(oStr))
    	}
    	if(data.length>0){
    		centroAtencionSelect.set("disabled",false)
    	}else{
    		centroAtencionSelect.set("disabled",true)
    	}
	    data=null
    }
    
    Y.one("#prestador").set("disabled",true)
    Y.one("#centroAtencion").set("disabled",true)
    
    Y.one("#comuna").on("change", buscaPrestardoresPorRegion);
    Y.one("#prestador").on("change", buscaCentroAtencionPorPrestador);
   
    
    
});