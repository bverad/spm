
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
    	Y.all(".valor-aprobado").on("change", calcularSumaAprobados);
    	
    	// First Time
    	calcularSumaAprobados();    	
    	
    	function calcularSumaAprobados () {
    		// Necesito obtener una lista de los ids  		
    		var rows = Y.one("#detalle-table").get("children").item(1).get("children")
    		var ids  = [];
    		rows.each(function (i) { ids.push(i.get("id").replace(/tr_/, "")); });
    		var valorTotalFinalTabla = 0;    		
   		
    		// En realidad, podriamos iterar sobre el mismo rows, pero preferí
    		// por mantenibilidad y otros, iterar sobre ids
    		var id;
    		for (var i in ids) {
    			id = ids[i];			
            	// Con el id obtenemos los 4 valores
            	var cantidad  = Y.one("#valorAprobado_" + id).get("value");
            	// Un arreglo si los campos vienen vacios
            	if (cantidad === "") cantidad = 0;
            	// Ya po, llenemos las formulas
            	var cantidadInt = parseInt(cantidad);
            	if (isNaN(cantidadInt)) cantidadInt = 0;
            	
            	// Y seteemos los valores
            	Y.one("#valorAprobado_" + id).set("value", cantidadInt);            	
            	valorTotalFinalTabla += cantidadInt;
    		}
        	
    		// Pasemos a formato de miles este elemento
    		valorTotalFinalTabla = valorTotalFinalTabla.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");    		
    		Y.one("#sumaAprobados").setHTML('$ ' + valorTotalFinalTabla);
    		
    		return false;
    	}
    });
});
