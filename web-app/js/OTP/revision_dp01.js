
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
    	Y.all(".pagar-checkers").on("change", calcularSumaAPagar);
    	
    	// First Time
    	calcularSumaAPagar();    	
    	
    	function calcularSumaAPagar () {
    		// Así lo hacemos: Itero sobre las filas y veo cuales están chequeadas.
    		// Luego, leo el valor de las chequeadas y lo sumo.
    		
    		// Necesito obtener una lista de los ids  		
    		var rows = Y.one("#detalle-table").get("children").item(1).get("children")
    		var ids  = [];
    		rows.each(function (i) { ids.push(i.get("id").replace(/tr_/, "")); });
    		var valorTotalFinalTabla = 0;    		

    		// En realidad, podriamos iterar sobre el mismo rows, pero preferí
    		// por mantenibilidad y otros, iterar sobre ids
    		var id;
    		var sumaAPagar = 0;
    		var cantidad;

    		for (var i in ids) {
    			id = ids[i];	
    			if (Y.one("#reembolso_" + id).get("checked") === true) {
    				cantidad = parseInt(Y.one("#reembolso_" + id).get("parentNode")
                                .siblings().item(5).get("text").replace(/\,|\$/g, ""));
    				
    				if (isNaN(cantidad)) cantidad = 0; // fallback. Suponemos siempre número acá.
    				
    				sumaAPagar += cantidad;
    			}
    		}
        	
    		// Pasemos a formato de miles este elemento
    		sumaAPagar = sumaAPagar.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");    		
    		Y.one("#sumaAPagar").setHTML('$ ' + sumaAPagar);
    		
    		return false;
    	}
    });
});



