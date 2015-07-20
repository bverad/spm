/**
 * Busca las enfermedades CIE-10
 */

YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){
		
		Y.one("#btnBuscar").on("click", BuscaCIEOnClick);
		Y.one("#cie10").on("change", CIEOnChange);
		Y.one("#guardar_diagnostico").set('disabled', true);
		
	});
	
	function BuscaCIEOnClick() {
		
		var cie10 = document.getElementById("cie10").value;
		if (Y.one(".messages-info.BuscaCIEOnClick")) Y.one(".messages-info.BuscaCIEOnClick").remove();
		
		if (cie10 == null || cie10 ==""){
			mensajeError("BuscaCIEOnClick", "Debes ingresar un Código!");
			return false;
		}
		
		Y.io("datosCIE10JSON?codigos=" + cie10, {
			on : {
				success : function (tx, r) {
			    	try {
			    		var data = Y.JSON.parse(r.responseText);
			    		if (data){
				    		Y.one("#cie10_descrip").set("value", null);	
				    		Y.one("#cie10_descrip").set("value", data.descripcion);
				    		Y.one("#guardar_diagnostico").set('disabled', false);
			    		}else{
			    			mensajeError("BuscaCIEOnClick", "No se Encontró la Enfemedad con el Código Indicado");
			    			Y.one("#guardar_diagnostico").set('disabled', true);
			    		}
			    	}			    	
			    	catch (e) {
			    		//mensajeError("BuscaCIEOnClick", "Error al conectar con la base de datos");
			    	    return false;
			    	}	    			    	
				    data=null					
				}
			}
		});	
		return false;		
	}
	
	function CIEOnChange(){
		var cie10 = document.getElementById("cie10").value;
		
		if (cie10 == null || cie10 ==""){
			Y.one("#cie10_descrip").set("value", null);	
			Y.one("#guardar_diagnostico").set('disabled', true);
		}
		return false;
	}
	
	function mensajeError (tipo, msg) {
		var bodyNode = Y.one(".workarea");
		var messageNode = Y.one(".messages-info");
		if (messageNode != null) {
			Y.all(".messages-info.BuscaCIEOnClick").remove();
			bodyNode.append('<div class="pure-u-1 messages-info ' + tipo + '"><i class="icon-exclamation icon-2x pull-left icon-muted"></i>'+ msg +'</div>');
		} else {
			bodyNode.append('<div class="pure-u-1 messages-info ' + tipo + '"><i class="icon-exclamation icon-2x pull-left icon-muted"></i>'+ msg +'</div>');
		}
		return false;
	}
	
});