YUI().use('event', "node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {
	function tipoActividadOnChange() {
		var tipoActividad = Y.one("#tipoActividad").get("value");
		if (tipoActividad === "7") { // Si es Otro
			Y.one("#descOtro").set("required", true);
			Y.one("#descOtro").ancestor().setStyle("display", "block")
		} else {
			Y.one("#descOtro").set("required", false);
			Y.one("#descOtro").ancestor().setStyle("display", "none")
		}
	}
	
	Y.one("#tipoActividad").on("change", tipoActividadOnChange);
	Y.on("available", tipoActividadOnChange, "#descOtro");
});