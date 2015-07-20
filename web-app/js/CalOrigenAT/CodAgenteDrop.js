/**
 * Llena los combos con los correspondiente valores
 */

YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){		
		//handlers
		Y.one("#cod_agente_1").on("change", llenaCombo2OnChange);
		Y.one("#cod_agente_2").on("change", llenaCombo3OnChange);
		Y.one("#cod_agente_3").on("change", llenaCombo4OnChange);
		Y.one("#cod_agente_4").on("change", llenaCombo5OnChange);
		Y.one("#cod_agente_5").on("change", llenaCombo6OnChange);
	
	});
	
	function llenaCombo2OnChange() {
		var combo = document.getElementById("cod_agente_1").value;
		
		Y.io("comboAgentesJSON?codigo=" + combo, {
			on : {
				success : function (tx, r) {					
			    	try {
			    		var data = Y.JSON.parse(r.responseText);
						Y.one("#cod_agente_2").get('childNodes').remove();				    	
			    	}			    	
			    	catch (e) {
			    	    alert("Error al obtener los Agentes asociados.");
			    	    return;
			    	}
			    	if (data.length > 0) {
			    		var oStr='<option value="0">Seleccione ...</option>';
			    			Y.one("#cod_agente_2").appendChild( Y.Node.create(oStr));
			    	}
			    	for (var i = 0; i < data.length; i++) {
			    	    var p = data[i];
			    	    oStr='<option value="'+p.codigo+'">'+p.codigo+' - '+p.descripcion+'</option>';
			    	    Y.one("#cod_agente_2").appendChild( Y.Node.create(oStr));
			    	}
			    	if(data.length>0){
			    		Y.one("#cod_agente_2").set("disabled",false)
			    	}else{
			    		Y.one("#cod_agente_2").set("disabled",true)
			    	}			    	
				    data=null					
				}
			}
		});		
	}
	function llenaCombo3OnChange() {
		var combo = document.getElementById("cod_agente_2").value;
		
		Y.io("comboAgentesJSON?codigo=" + combo, {
			on : {
				success : function (tx, r) {					
			    	try {
			    		var data = Y.JSON.parse(r.responseText);
						Y.one("#cod_agente_3").get('childNodes').remove();				    	
			    	}			    	
			    	catch (e) {
			    	    alert("Error al obtener los Agentes asociados.");
			    	    return;
			    	}
			    	if (data.length > 0) {
			    		var oStr='<option value="0">Seleccione ...</option>';
			    			Y.one("#cod_agente_3").appendChild( Y.Node.create(oStr));
			    	}
			    	for (var i = 0; i < data.length; i++) {
			    	    var p = data[i];
			    	    oStr='<option value="'+p.codigo+'">'+p.codigo+' - '+p.descripcion+'</option>';
			    	    Y.one("#cod_agente_3").appendChild( Y.Node.create(oStr));
			    	}
			    	if(data.length>0){
			    		Y.one("#cod_agente_3").set("disabled",false)
			    	}else{
			    		Y.one("#cod_agente_3").set("disabled",true)
			    	}			    	
				    data=null					
				}
			}
		});		
	}
	function llenaCombo4OnChange() {
		var combo = document.getElementById("cod_agente_3").value;
		
		Y.io("comboAgentesJSON?codigo=" + combo, {
			on : {
				success : function (tx, r) {					
			    	try {
			    		var data = Y.JSON.parse(r.responseText);
						Y.one("#cod_agente_4").get('childNodes').remove();				    	
			    	}			    	
			    	catch (e) {
			    	    alert("Error al obtener los Agentes asociados.");
			    	    return;
			    	}
			    	if (data.length > 0) {
			    		var oStr='<option value="0">Seleccione ...</option>';
			    			Y.one("#cod_agente_4").appendChild( Y.Node.create(oStr));
			    	}
			    	for (var i = 0; i < data.length; i++) {
			    	    var p = data[i];
			    	    oStr='<option value="'+p.codigo+'">'+p.codigo+' - '+p.descripcion+'</option>';
			    	    Y.one("#cod_agente_4").appendChild( Y.Node.create(oStr));
			    	}
			    	if(data.length>0){
			    		Y.one("#cod_agente_4").set("disabled",false)
			    	}else{
			    		Y.one("#cod_agente_4").set("disabled",true)
			    	}			    	
				    data=null					
				}
			}
		});		
	}
	function llenaCombo5OnChange() {
		var combo = document.getElementById("cod_agente_4").value;
		
		Y.io("comboAgentesJSON?codigo=" + combo, {
			on : {
				success : function (tx, r) {					
			    	try {
			    		var data = Y.JSON.parse(r.responseText);
						Y.one("#cod_agente_5").get('childNodes').remove();				    	
			    	}			    	
			    	catch (e) {
			    	    alert("Error al obtener los Agentes asociados.");
			    	    return;
			    	}
			    	if (data.length > 0) {
			    		var oStr='<option value="0">Seleccione ...</option>';
			    			Y.one("#cod_agente_5").appendChild( Y.Node.create(oStr));
			    	}
			    	for (var i = 0; i < data.length; i++) {
			    	    var p = data[i];
			    	    oStr='<option value="'+p.codigo+'">'+p.codigo+' - '+p.descripcion+'</option>';
			    	    Y.one("#cod_agente_5").appendChild( Y.Node.create(oStr));
			    	}
			    	if(data.length>0){
			    		Y.one("#cod_agente_5").set("disabled",false)
			    	}else{
			    		Y.one("#cod_agente_5").set("disabled",true)
			    	}			    	
				    data=null					
				}
			}
		});		
	}
	function llenaCombo6OnChange() {
		var combo = document.getElementById("cod_agente_5").value;
		
		Y.io("comboAgentesJSON?codigo=" + combo, {
			on : {
				success : function (tx, r) {					
			    	try {
			    		var data = Y.JSON.parse(r.responseText);
						Y.one("#cod_agente_6").get('childNodes').remove();				    	
			    	}			    	
			    	catch (e) {
			    	    alert("Error al obtener los Agentes asociados.");
			    	    return;
			    	}
			    	if (data.length > 0) {
			    		var oStr='<option value="0">Seleccione ...</option>';
			    			Y.one("#cod_agente_6").appendChild( Y.Node.create(oStr));
			    	}
			    	for (var i = 0; i < data.length; i++) {
			    	    var p = data[i];
			    	    oStr='<option value="'+p.codigo+'">'+p.codigo+' - '+p.descripcion+'</option>';
			    	    Y.one("#cod_agente_6").appendChild( Y.Node.create(oStr));
			    	}
			    	if(data.length>0){
			    		Y.one("#cod_agente_6").set("disabled",false)
			    	}else{
			    		Y.one("#cod_agente_6").set("disabled",true)
			    	}			    	
				    data=null					
				}
			}
		});		
	}
	



});