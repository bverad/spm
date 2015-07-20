/**
 * Compaginaci�n de varias denuncias para su consolidaci�n.
 * 
 * Funciones de modal y env�o de XMLHTTPRequest. XXX
 * 
 */
YUI().use("node", "transition", "panel", "io", "dump", "json-parse","dd-plugin",  function (Y) {  // loading escape only for security on this page
	
	// Se setea la url del servicio si es "" no se invoca.
	var uriService = "";
	uriService = "../DIATEPOA/alternativasJson";
	//uriService = "../TEST/json";
	
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
                    label    : 'Cambiar valores',
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
        var obj = Y.one("#modalsrc input[type=radio]:checked");
          
        if (obj != null){
        	var valor;
        	var texto;
        	var tipo;
        	
        	var localId = obj.getAttribute("id");
        	//var localId2 = obj.get("id");
        	valor = obj.get("value");    	
        	/*
        	if (localId == 'id-input-edit-self'){ 
        		var campo = Y.one("#id-input-edit-self-value");
        		valor = campo.get("value");
        		tipo = campo.get('tipo');
        		
        	} else if (localId == 'id-combo-edit-self') {
        		var combo  = Y.one('#id-combo-edit-self-value');
        		combo.get("options").each( function() {
 	        			if (this.get('selected')){
	         			   valor  = this.get('value');
	         			   texto = this.get('text');
	        			}
        			});     		
        	} else {
        		valor = obj.get("value");
        	}
        	*/
        	//validaciones
        	
        	/*if (tipo == "fecha") {
        		var patt = new RegExp( '[(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-0-9]{4}') // fecha
        	}
        	
        	if (tipo == "hora") {
        		var patt = new RegExp( '(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9])') // hora
        		var myResult = patt.exec(valor)
        	}*/

        	// who is coming? 
        	var deParteDe = Y.one("#deParteDe").get("value");
        	
        	Y.one('#' + deParteDe).set('value', valor);     // campo hidden
        	//if (localId == 'id-combo-edit-self') {          // campo span
        	//	Y.one('#label_' + deParteDe).setHTML(texto); 
        	//} else {
        	//	Y.one('#label_' + deParteDe).setHTML(valor); 
        	//}
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
 * Se hace el clic en el bot�n de un componente.
 *    - deParteDe -> a quien le llegar� la actualizacion.
 *    
 */	
	Y.all('.btn-show').on('click', function(e){
		var btnA = Y.one('.yui3-widget-ft').one('.pure-button-success');		
		btnA.removeAttribute('class'); 		// Elimino las clases sin revoluci�n.
		btnA.setAttribute('class', 'pure-button pure-button-success');

		var btnB = Y.one('.yui3-widget-ft').one('.pure-button-warning');
		btnB.removeAttribute('class'); 		// Elimino las clases sin revoluci�n.
		btnB.setAttribute('class', 'pure-button pure-button-warning');
		
		e.preventDefault();					// Stop the event's default behavior	   
		dialog.callback = doSomething;    	// Set the callback to reference a function
    	
        var deParteDe = e.currentTarget.getAttribute('deParteDe');
        var idClickedButton = e.currentTarget.get("id");
        var glosa = e.currentTarget.getAttribute("glosa");
        var tipo = e.currentTarget.getAttribute("tipo");
        var ayuda = e.currentTarget.getAttribute("ayuda");
        var requerido = e.currentTarget.getAttribute("requerido");

	    // Subscribe to event "io:complete", and pass an array as an argument to the event handler "complete", since
	    // "complete" is global.   At this point in the transaction lifecycle, success or failure is not yet known.
	    Y.on('io:complete', complete, Y, [deParteDe, idClickedButton, glosa, tipo, ayuda, requerido]);
	    Y.on('io:failure', failure, Y, [deParteDe, idClickedButton, glosa, tipo, ayuda, requerido]);
	    
	    // Make the call to the server for JSON data
        // --> Y.io("http://localhost:8080/isl-spm/TEST/json", callback);
	    
    	// Make an HTTP request
	    if (uriService != "") {
	    	var url = uriService + '?varName=' + deParteDe + '&siniestroId='  + jsSinisetro;
	    	var request = Y.io(url);
	    } else {
	    	complete('servicesless', Y, [deParteDe, idClickedButton, glosa, tipo, ayuda, requerido])
	    }
	    e.stopPropagation(); // Stop the event from bubbling up the DOM tree
	});
	

	// Define a function to handle the failure.
	function failure(x,o) {
		Y.detach('io:complete', complete, Y);
		Y.detach('io:failure', failure, Y);
		
		alert("Async call ha fallado!");
	}
	
	// Define a function to handle the response data.
	function complete(id, o, args) {

		Y.detach('io:complete', complete, Y);
		Y.detach('io:failure', failure, Y);
		
		var id = id; // Transaction ID.
        var deParteDe = args[0]; 
        var idClickedButton = args[1]; 
        var glosa = args[2]; 
        var tipo = args[3]; 
        var ayuda = args[4]; 
        var requerido = args[5];

        var data = [],
		html = '', i, l;
	
        // Add more vars
        html = '<input type="hidden" name="deParteDe" id="deParteDe" value="'+ deParteDe +'" />';
        
        if (uriService != ""){
			// Process the JSON data returned from the server
			try {
				data = Y.JSON.parse(o.responseText);
			}
			catch (e) {
				alert("JSON Parse failed!");
				return;
			}
			
			// The returned data was parsed into an array of objects.
			var idComplejo = "";
			var hayValores = false;

			for (i=0, l=data.length; i < l; ++i) {
				idComplejo = 't-' + data[i].tipo + '-' + data[i].tipo;
				html +=    '<div class="label-contenedor pure-input-8-8">'; 
				html +=    '   <div class="denuncia"> Denuncia: ' + data[i].tipo + ' </div>';
				html +=    '   <label for="'+ idComplejo +'" class="pure-radio"> ';
				if (data[i].valor != null){
					hayValores = true;
					var dataquete = data[i].valor;
					
					if (typeof data[i].valor === 'string' || typeof data[i].valor === 'number'){
						if (tipo == 'fecha') {
							var fecha = new Date(dataquete);
							
							var dia = 0;
							if (fecha.getDate() < 10)
								dia = '0' + fecha.getDate(); 
							else
								dia = fecha.getDate();
							
							var mes = 0
							if (fecha.getMonth() < 9)
								mes = '0' + (fecha.getMonth() + 1);
							else
								mes = fecha.getMonth() + 1;
								
							var ano = fecha.getFullYear();
							dataquete = dia + '-' + mes + '-' + ano;
						}
						html +=    ' <input class="soy-radio input-edit" id="'+ idComplejo +'" type="radio" value="' + dataquete + '" name="radio" /> ';
						html +=    dataquete + ' </label>';
					} else if (typeof data[i].valor === 'boolean'){
						// Esto no es limpio pero se necesitan otro valores para tipos de accidente
						if (deParteDe == "esAccidenteTrayecto") {
							if (dataquete) {
								html +=    ' <input class="soy-radio input-edit" id="'+ idComplejo +'" type="radio" value="2" name="radio" /> ';
								html += 'Trayecto'
							} else {
								html +=    ' <input class="soy-radio input-edit" id="'+ idComplejo +'" type="radio" value="1" name="radio" /> ';
								html += 'Trabajo'
							}
						} else {
							html +=    ' <input class="soy-radio input-edit" id="'+ idComplejo +'" type="radio" value="' + dataquete + '" name="radio" /> ';
							if (dataquete) { html += 'Si' }
							else 		   { html += 'No' }
						}
						html +=    ' </label>';
						
					} else { // objetos que deberan ser todos iguales sino habr�a que serializar de mejor forma
						var val = data[i].valor;
						html +=    ' <input class="soy-radio input-edit" id="'+ idComplejo +'" type="radio" value="' + val.codigo + '" name="radio" /> ';
						html +=    val.descripcion + ' </label>';						
					}
					
					
				} else {
					html +=   ' No hay datos en la denuncia.</label>';
				}
						    
				html +=    '</div>';
			}
        }
        // se agrega el �ltimo campo para editar
        //html += renderEditField(tipo, deParteDe, ayuda, requerido);
        
        // se setean los valores en el modal.
 		Y.one('#tTitulo').setHTML(glosa); 		
 		Y.one('#modal-content').setHTML(html);
 		
 		//if (!hayValores){
 			//pure-button-success 			
 		//	Y.one("#modalsrc  .pure-button-success").setAttribute('style', 'visibility:hidden');
 		//}
 		dialog.show();
	};
	
	
	
	function renderEditField(tipo, deParteDe, ayuda, requerido) {
		var html = '';
		var pattern  = '';
		
		html += '<div class="label-contenedor pure-input-8-8">';
		html += '   <div class="denuncia" style="margin-bottom:5px;"> Ingrese otra informaci\u00f3n </div>';
		
		if (tipo == 'text' || tipo == 'hora' || tipo == 'fecha') {
			html +=	'<input class="soy-radio input-edit" id="id-input-edit-self" type="radio" /> ';
			html +=	'<input id="id-input-edit-self-value" ';

			if (tipo == 'fecha') {
				tipo = 'date' 
				ayuda = 'DD-MM-AAAA'
				pattern = '[(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-0-9]{4}'
			}
			if (tipo == 'hora') {
				tipo = 'time'
				ayuda = 'HH:MM'
				pattern = '(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9])'
			}
			html +=	' type="'+tipo+'"  tipo="'+tipo+'"   ';
						
			if (requerido) {
				html +=	' required="true"   '
			}
			if (pattern != "") {
				html +=	' pattern="'+pattern+'" ';
			}
			if (ayuda != 'null' ) {
				html +=	' placeholder="'+ayuda+'" ';				
			}
			html += 'style="width:90%"/>';
		}

		
		if (tipo == 'combo') {
			//var name = window['a'];
			var arregloName = deParteDe + 'Arreglo';
			var  arreglo=window[arregloName];
			html +=	'<input class="soy-radio input-edit" id="id-combo-edit-self" type="radio" /> ';
			html += '  <select name="${nombre}" id="id-combo-edit-self-value">';

			// obtengo un arreglo pasado como variable global.
			for (var i=0; i<arreglo.length; i++){
				var obj = arreglo[i];
				html +='<option value="'+obj.key+'">'+obj.value+'</option>';
			}
			html +='</select>'; 
		}
		
		
		
		
		html +=	'</div>';
		
		return html;
	}
	
});
