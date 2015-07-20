/**
 * Agrega opa y oda
 */


YUI().use("node", "transition", "panel", "io", "dump", "json-parse","dd-plugin",  function (Y) {  // loading escape only for security on this page
		
	// saco la clase de invisibilidad :)
	YAHOO.util.Dom.removeClass("modalsrc", "yui-pe-content");
	
	// modal con buenos modales
    var dialog = new Y.Panel({
    	srcNode    : '#modalsrc',
    	// headerContent: 'Add A New Product',
        centered   : true,
        width      : 410,
        zIndex     : 6,
        centered   : true,
        modal      : true, // modal behavior
        render     : true,
        visible    : false, // make visible explicitly with .show()
        plugins      : [Y.Plugin.Drag],
        buttons    : {
            footer: [
                {
                	classNames    : 'pure-button-warning',
                    name  : 'cancel',
                    label : 'Cancelar',
                    action: 'onCancel'
                },
                {
                	classNames  : 'pure-button-success btn-modal-proceed',
                    name     : 'proceed',
                    label    : 'Agregar',
                    action   : 'onOK'
                }
            ]
        }
    });
	
    dialog.onCancel = function (e) {
        e.preventDefault();
        this.hide();
        // the callback is not executed, and is callback reference removed, so it won't persist
        this.callback = false;
    }

    dialog.onOK = function (e) {
        e.preventDefault();
        this.hide();
        // code that executes the user confirmed action goes here

        // 1- get inputs from modal
        // var inputs = Y.one('#modalsrc').all('input');
       
        // 0- is checked? clever and sharp :) 
        var valor = Y.one("#id-add").get("value");
          
        if (valor != null && valor != "" ){
        	
        	var tipoDoc = Y.one("#tDocumentoTipo").get('innerHTML');
   
        	var domId = tipoDoc + '-table';
        	var last_row = YAHOO.util.Dom.getLastChild(domId);

            var row = document.createElement("tr");
            row.setAttribute('id', valor);  

            var td  = document.createElement("td");
            td.innerHTML = valor;  
            row.appendChild(td);
            
            var td  = document.createElement("td");
            td.innerHTML = '<button title="Eliminar" onclick="deleteRow(this);" class="inline-button-destacado btn-show btn-eliminar"><i class="icon-minus-sign icon-large"></i></button>';  
            row.appendChild(td);

            var el = YAHOO.util.Dom.insertAfter(row, last_row); // el is being set to NULL so the insert is failing
        }

        if(this.callback){
           this.callback();
        }
        // callback reference removed, so it won't persist
        this.callback = false;
    }


    // a function to do when the user clicks the "OK" button in the dialog.
	var doSomething = function(){
    	Y.log('Something was done.');
	};


	
/*
 * Se hace el clic en el botón de un componente.
 *    - deParteDe -> a quien le llegará la actualizacion.
 *    
 */	
	Y.all('.btn-show').on('click', function(e){
		var btnA = Y.one('.yui3-widget-ft').one('.pure-button-success');		
		btnA.removeAttribute('class'); 		// Delete 'de clases' without revolution
		btnA.setAttribute('class', 'pure-button pure-button-success');

		var btnB = Y.one('.yui3-widget-ft').one('.pure-button-warning');
		btnB.removeAttribute('class'); 		
		btnB.setAttribute('class', 'pure-button pure-button-warning');
		
		e.preventDefault();					// Stop the event's default behavior	   
		dialog.callback = doSomething;    	// Set the callback to reference a function
    	
        var tipoDocumento = e.currentTarget.getAttribute('tipoDocumento');
        var idClickedButton = e.currentTarget.get("id");
        var glosa = e.currentTarget.getAttribute("glosa");
        var tipo = e.currentTarget.getAttribute("tipo");

        // se setean los valores en el modal.
 		Y.one('#tTitulo').setHTML(glosa); 		
 		Y.one('#tDocumentoTipo').setHTML(tipoDocumento);
 		
 		Y.one('#id-add').set('value', null);
 		dialog.show();

	    e.stopPropagation(); // Stop the event from bubbling up the DOM tree
	});
});
