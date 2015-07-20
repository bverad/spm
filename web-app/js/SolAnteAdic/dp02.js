/**
 * Busca las enfermedades CIE-10
 */

YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){
		
		Y.one("#Adjuntar").on("click", uploadAntecedenteOnClick);
		
	});
	
	function uploadAntecedenteOnClick() {
		
		var descripcion = document.getElementById("descripcion").value;
		
	}
	
	function mensajeError (tipo, msg) {
		var bodyNode = Y.one(".workarea");
		var messageNode = Y.one(".messages ul");
		if (messageNode != null) {
			messageNode.append("<li class='" + tipo + "'>" + msg + "</li>");
		} else {
			bodyNode.append("<div class='pure-u-1 messages " + tipo + "'><ul><li>" + msg + "</li></ul></div>");
		}
		return false;
	}
	
});