/**
 * Disable al boton calificar cuando: origen != laboral & tipo_evento != al preseleccionado
 */

YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){
		
		//Para cambio, desactivar el boton calificar
		Y.one("#origen").on("change", DisableButtonOnChange);
		Y.one("#tipoEvento").on("change", DisableButtonOnChange);	
		Y.one("#tipoEvento").on("change", setVisibleOnChange);
		Y.one("#grupoIntencionalidad").on("change", buscarIntencionalidad);
		Y.one("#cod_intencion").set('disabled', true)

		
		//Deshabilita los combos de calificación
		var califica = document.getElementById("califica").value;
		if (califica == null || califica == ""){
			Y.one("#DivClasificacion").setStyle("display", "none");
		}	
		
		//Habilita & Deshabilitar boton de calificar
		var tipoEvento = document.getElementById("tipoEvento").value;
		if (tipoEvento == "3" || tipoEvento == "4"){
			Y.one("#califica_siniestro").set('disabled', true)
			Y.one("#DivClasificacion").setStyle("display", "none")
		}else{
			Y.one("#califica_siniestro").set('disabled', false)
			Y.one("#DivClasificacion").setStyle("display", "");
		}
		
	});
	
	function DisableButtonOnChange() {
		var origen = document.getElementById("origen").value;
		var tipoEvento = document.getElementById("tipoEvento").value;
		var tipoEventoOriginal = document.getElementById("tipoEventoOriginal").value;
		var permitido = true;

		if (tipoEventoOriginal == "1" || tipoEventoOriginal == "2"){
			if (tipoEvento == "1" || tipoEvento == "2"){
				permitido = true
			}else{
				permitido = false
			}
		}else if(tipoEventoOriginal == "3"){
			if (tipoEvento == "3"){
				permitido = true
			}else{
				permitido = false
			}
		}else{
			permitido = false
		}
			
		if (origen != "1" || permitido == false){
			Y.one("#califica_siniestro").set('disabled', true)
		}else{
			Y.one("#califica_siniestro").set('disabled', false);
		}		
	}
	
	function setVisibleOnChange(){
		var tipoEvento = document.getElementById("tipoEvento").value;
		
		if (tipoEvento == "1" || tipoEvento == "2"){
			Y.one("#DivClasificacion").setStyle("display", "")
		}else{
			Y.one("#DivClasificacion").setStyle("display", "none");
		}
		
	}
	
	var buscarIntencionalidad = function (e) {
    	var grupo =Y.one("#grupoIntencionalidad").get('value');
    	Y.io('getIntencionalidadListJSON?codigo='+ grupo, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarIntencionalidad(r.responseText);
    	        }
    	    }
    	});
	}
	
	var llenarIntencionalidad = function(jsonString){
    	var intencionalidadSelect=Y.one("#cod_intencion");
    	intencionalidadSelect.get('childNodes').remove();
    	
    	var data = Y.JSON.parse(jsonString);
    	for (var i = 0; i <= data.length - 1; i++) {
    	    var intencionalidad = data[i];
    	    var n = intencionalidad.descripcion;
    	    var oStr='<option value="'+intencionalidad.codigo+'">'+ intencionalidad.codigo + " - " + intencionalidad.descripcion + '</option>';
    	    intencionalidadSelect.appendChild(Y.Node.create(oStr));
    	}
    	if(data.length>0){
    		intencionalidadSelect.set("disabled",false);
    	}else{
    		intencionalidadSelect.set("disabled",true);
    	}
    	
	    data=null
    } 
		
	
		
});