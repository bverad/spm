/**
 * Disable al boton calificar cuando: origen != laboral & tipo_evento != al preseleccionado
 */

YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){
		
		//Para cambio, desactivar el boton calificar
		Y.one("#altaMedica_0").on("change", DisableProximoControl);
		Y.one("#altaMedica_1").on("change", DisableProximoControl);
		
		//Habilita & Deshabilitar proximo control
		var proximo = document.getElementById("altaMedica_0").value;
		if (proximo == true){
			Y.one("#DivProximo").setStyle("display", "none")
		}else{
			Y.one("#DivProximo").setStyle("display", "")
		}
		
	});
	
	function DisableProximoControl() {
		var proximo = document.getElementById("altaMedica_0").value;
		
		if (proximo == true){
			Y.one("#DivProximo").setStyle("display", "none")
		}else{
			Y.one("#DivProximo").setStyle("display", "")
		}		
	}

		
});