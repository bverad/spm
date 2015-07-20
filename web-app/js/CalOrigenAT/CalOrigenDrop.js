/**
 * Llena los combos con los correspondiente valores
 */

YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){	
		//veremos si esta con datos
		var tipoEvento = document.getElementById("tipoEvento").value;
		var alim = document.getElementById("alim").value;
		var califica = document.getElementById("califica").value;
		
		if (tipoEvento){
			Y.one("#tipoEvento").set('disabled', true);
		}
		if (alim){
			Y.one("#alim").set('disabled', true);
		}
		if (califica){
			Y.one("#califica").set('disabled', true);
		}
		
		//carga datos
		Y.one("#origen").on("change", llenaEventoOnChange);
		Y.one("#tipoEvento").on("change", llenaAltaOnChange);
		Y.one("#alim").on("change", llenaCalificaOnChange);
	
	});
	
	function llenaEventoOnChange() {
		var origen = document.getElementById("origen").value;
		var eventoCodigos = [];
		var oStr='<option value="0">Seleccione...</option>';
		
		if (origen == '0'){			
			Y.one("#tipoEvento").appendChild( Y.Node.create(oStr));
			return;
		}
		
		Y.one("#alim").get('childNodes').remove();
		Y.one("#califica").get('childNodes').remove();
		
		Y.one("#alim").appendChild( Y.Node.create(oStr));
		Y.one("#califica").appendChild( Y.Node.create(oStr));
		
		Y.one("#alim").set('disabled', true);
		Y.one("#califica").set('disabled', true);
			
		if (origen == '1' || origen == '2'){
			eventoCodigos.push("1");
			eventoCodigos.push("2");
			eventoCodigos.push("3");
		}else if (origen == '3'){
			eventoCodigos.push("4"); 
		}
		
		Y.io("datosEventosJSON?codigos=" + eventoCodigos, {
			on : {
				success : function (tx, r) {					
			    	try {
			    		var data = Y.JSON.parse(r.responseText);
						Y.one("#tipoEvento").get('childNodes').remove();				    	
			    	}			    	
			    	catch (e) {
			    	    alert("Error al recibir la lista de Eventos.");
			    	    return;
			    	}
			    	if (data.length > 0) {
			    		var oStr='<option value="0">Seleccione...</option>';
			    			Y.one("#tipoEvento").appendChild( Y.Node.create(oStr));
			    	}
			    	for (var i = 0; i < data.length; i++) {
			    	    var p = data[i];
			    	    oStr='<option value="'+p.codigo+'">'+p.codigo+' - '+p.descripcion+'</option>';
			    	    Y.one("#tipoEvento").appendChild( Y.Node.create(oStr));
			    	}
			    	if(data.length>0){
			    		Y.one("#tipoEvento").set('disabled', false);
			    	}else{
			    		Y.one("#tipoEvento").set('disabled', true);
			    	}			    	
				    data=null					
				}
			}
		});	
		
	}
	
	//**************************************************************************************************
	
	function llenaAltaOnChange() {
		
		var evento = document.getElementById("tipoEvento").value;
		var origen = document.getElementById("origen").value;
		var altaCodigos = [];

		if (evento == '0'){
			var oStr='<option value="0">Seleccione...</option>';
			Y.one("#alim").appendChild( Y.Node.create(oStr));
			return;
		}
		
		if (evento == '1' || evento == '2' || evento == '3'){
			Y.one("#alim").set('disabled', false);
			if (origen == '2'){
				Y.one("#alim").get('childNodes').remove();
			
				var oStr='<option value="0">Seleccione...</option>';		
				var oStr3='<option value="null">No Aplica</option>';
				
				Y.one("#alim").appendChild( Y.Node.create(oStr));
	    	    Y.one("#alim").appendChild( Y.Node.create(oStr3));
	    	    
			}else{		
				Y.one("#alim").get('childNodes').remove();
				
				var oStr='<option value="0">Seleccione...</option>';
				var oStr1='<option value="true">Sí</option>';
				var oStr2='<option value="false">No</option>';
				
				Y.one("#alim").appendChild( Y.Node.create(oStr));
	    	    Y.one("#alim").appendChild( Y.Node.create(oStr1));
	    	    Y.one("#alim").appendChild( Y.Node.create(oStr2));
			}
		}
		
		if (evento == '4'){
			Y.one("#alim").set('disabled', false);
			Y.one("#alim").get('childNodes').remove();
			
			var oStr='<option value="0">Seleccione...</option>';
			var oStr3='<option value="null">No Aplica</option>';
			
			Y.one("#alim").appendChild( Y.Node.create(oStr));
    	    Y.one("#alim").appendChild( Y.Node.create(oStr3));		
		}
		
	}
	
	//**************************************************************************************************	
	
	function llenaCalificaOnChange() {
		var origen = document.getElementById("origen").value;
		var evento = document.getElementById("tipoEvento").value;
		var alta = document.getElementById("alim").value;
		
		var calificacionCodigo = [];
		
		if (alta == '0'){
			var oStr='<option value="0">Seleccione...</option>';
			Y.one("#califica").get('childNodes').appendChild( Y.Node.create(oStr));
			return;
		}
		
		//Muchos IF
		if (origen == '1' && evento == '1' && alta =='false'){calificacionCodigo.push("01");}
		if (origen == '1' && evento == '2' && alta =='false'){calificacionCodigo.push("02");}
		if (origen == '1' && evento == '3' && alta =='false'){calificacionCodigo.push("03");}
		if (origen == '1' && evento == '1' && alta =='true'){calificacionCodigo.push("04");}
		if (origen == '1' && evento == '3' && alta =='true'){calificacionCodigo.push("05");}
		if (origen == '2' && (evento == '1' || evento == '2') && alta =='null'){calificacionCodigo.push("06");}
		if (origen == '2' && evento == '3' && alta =='null'){calificacionCodigo.push("07");}
		if (origen == '1' && evento == '2' && alta =='true'){calificacionCodigo.push("09");}
		
		if (origen == '3' && evento == '4' && alta =='null'){
			calificacionCodigo.push("08");
			calificacionCodigo.push("10");
			calificacionCodigo.push("11");
			calificacionCodigo.push("12");
			calificacionCodigo.push("13");
		}
		
		Y.io("datosCalificacionJSON?codigos=" + calificacionCodigo, {
			on : {
				success : function (tx, r) {					
			    	try {
			    		var data = Y.JSON.parse(r.responseText);
						Y.one("#califica").get('childNodes').remove();				    	
			    	}			    	
			    	catch (e) {
			    	    alert("Error al recibir la lista de Calificaciones.");
			    	    return;
			    	}
			    	if (data.length > 0) {
			    		var oStr='<option value="0">Seleccione...</option>';
			    			Y.one("#califica").appendChild( Y.Node.create(oStr));
			    	}
			    	for (var i = 0; i < data.length; i++) {
			    	    var p = data[i];
			    	    oStr='<option value="'+p.codigo+'">'+p.codigo+' - '+p.descripcion+'</option>';
			    	    Y.one("#califica").appendChild( Y.Node.create(oStr));
			    	}
			    	if(data.length>0){
			    		Y.one("#califica").set('disabled', false);
			    	}else{
			    		Y.one("#califica").set('disabled', true);
			    	}			    	
				    data=null					
				}
			}
		});		
	}


});