/**
 * Disable al boton calificar cuando: origen != laboral & tipo_evento != al preseleccionado
 */

YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){
		
		var tipoEvento = document.getElementById("tipoEvento").value;
		if (tipoEvento != "3"){
			Y.one("#DivIndicacion").setStyle("display", "none")
			Y.one("#DivAgente").setStyle("display", "none")
			Y.one("#DivClasificacion").setStyle("display", "")	
		}else{
			Y.one("#DivIndicacion").setStyle("display", "")
			Y.one("#DivAgente").setStyle("display", "")
			Y.one("#DivClasificacion").setStyle("display", "none");	
		}
		
		//Deshabilita indicación si evento es enfermedad profesional
		Y.one("#tipoEvento").on("change", DisableInputOnChange);
		
		//Para habilitar combos sucesivamente
		Y.one("#origen").on("change", enableComboOnChange);
		Y.one("#tipoEvento").on("change", enableComboOnChange);	
		Y.one("#alim").on("change", enableComboOnChange);
		Y.one("#tipoEvento").on("change", setVisibleOnChange);
		
		//deshabilita los combos de codificacion de agentes
		Y.one("#cod_agente_2").set('disabled', true);
		Y.one("#cod_agente_3").set('disabled', true);
		Y.one("#cod_agente_4").set('disabled', true);
		Y.one("#cod_agente_5").set('disabled', true);
		Y.one("#cod_agente_6").set('disabled', true);
	});
	
	function DisableInputOnChange() {
		var tipoEvento = document.getElementById("tipoEvento").value;

		if (tipoEvento != "3"){
			Y.one("#DivIndicacion").setStyle("display", "none")
			Y.one("#indicacion").set('required', false)
		}else{
			Y.one("#DivIndicacion").setStyle("display", "")
			Y.one("#indicacion").set('required', true);
		}
	}
	
	function enableComboOnChange() {
		var origen = document.getElementById("origen").value; 
		var tipoEvento = document.getElementById("tipoEvento").value;
		var altaInmediata = document.getElementById("alim").value;
		
		if (origen != "0"){
			Y.one("#tipoEvento").set('disabled', false)
		}else{
			Y.one("#tipoEvento").set('disabled', true)
			Y.one("#tipoEvento").set('value', '0');
		}
		
		if (tipoEvento != "0"){
			Y.one("#alim").set('disabled', false)
		}else{
			Y.one("#alim").set('disabled', true)
			Y.one("#alim").set('value', '0');
		}
		
		if (altaInmediata != "0"){
			Y.one("#califica").set('disabled', false)
		}else{
			Y.one("#califica").set('disabled', true)
			Y.one("#califica").set('value', '0');
		}
		
	}
	
	function setVisibleOnChange(){
		var tipoEvento = document.getElementById("tipoEvento").value;
		
		if (tipoEvento == "1" || tipoEvento == "2" || tipoEvento == "4"){
			Y.one("#DivClasificacion").setStyle("display", "")
			Y.one("#DivAgente").setStyle("display", "none")
		}else if (tipoEvento == "3"){
			Y.one("#DivClasificacion").setStyle("display", "none")
			Y.one("#DivAgente").setStyle("display", "");
		}
		
	}

		
});