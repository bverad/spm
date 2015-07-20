
YUI().use('event', "node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {

	function tipoActividadOnChange() {
		var tipoActividad = Y.one("#tipoActividad").get("value");
		if (tipoActividad == "7") { // Si es Otro
			Y.one("#otro").set("required", true);
			Y.one("#otro").ancestor().setStyle("display", "block")
		} else {
			Y.one("#otro").set("required", false);
			Y.one("#otro").ancestor().setStyle("display", "none")
		}
	}
	
	function quitarAlta() {
		Y.all("#tipoActividad option").each(function (option) {
			if (option.get("value") == "8" || option.get("value") == "1" || option.get("value") == "9" || option.get("value") == "10") { // Si es Alta Seguimiento, Sin seguimiento, Re-Ingreso o Ingreso
				option.setStyle("display", "none");
			}
		});
	}
	
	if (Y.one("#tipoActividad") != null) {
		Y.one("#tipoActividad").on("change", tipoActividadOnChange);		
	}
	Y.on("available", tipoActividadOnChange, "#tipoActividad");
	Y.on("available", quitarAlta, "#tipoActividad");
});


/******** Implementacion para adjuntar archivos ****************/
/**
 * Agrega un nuevo archivo a detalle de documentación adicional
 */
function addFile(){
	var table = document.getElementById("fileList");
	//var form = document.getElementById("atachmentsForm");
	var row = table.insertRow(table.rows.length);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	cell1.innerHTML = table.rows.length - 1;
	//agregando componentes a celda
	cell2.innerHTML = '<input type="file" name="file' + (table.rows.length - 1)+ '" value="Eliminar" />';
	cell3.innerHTML = '<input type="button" name="eliminar" value="Eliminar" onclick="deleteFile(this)" />';
	//enviando cantidad de archivos adjuntos
	var hiddenField = document.getElementById("fileLength");
	setHiddenField(hiddenField, table.rows.length);
	return true;
 }

/**
 * Modifica el valor de un componente tipo hidden
 *@param hiddenField
 *@param value
 *
 */
function setHiddenField(hiddenField, value) {
    // Create a hidden input element, and append it to the form
    hiddenField.value = value;
    return hiddenField;
}


/**
 * Elimina registro segun indice entregado
 * @param index
 */
function deleteFile(r){
	var table = document.getElementById("fileList");
    var i = r.parentNode.parentNode.rowIndex;
    table.deleteRow(i);
    //cada vez que se borra una fila se actualiza el hidden de cantidad de archivos adjuntos
	var hiddenField = document.getElementById("fileLength");
	setHiddenField(hiddenField, table.rows.length);
	
}

/**
 * Despliega detalles de 1 o mas archivos adjuntos
 * @param fileInput archivo en cuestion
 * @returns {Array}
 */
function displayDetailsFiles(fileInput){
	var files = [];
	var txt = "";
	if ('files' in fileInput) {
	    if (fileInput.files.length == 0) {
	        alert("Selecciona uno o mas archivos");
	    } else {
	        for (var i = 0; i < fileInput.files.length; i++) {
	            var file = fileInput.files[i];
	            files[i] = file;
	        }
	    }
	}
	
	return files;
}
