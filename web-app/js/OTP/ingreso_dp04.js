
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
    	// Hagamos un "readonly"
		Y.one("#solicitanteNombres").setAttribute("readonly", "true");
		Y.one("#solicitanteApellidoPaterno").setAttribute("readonly", "true");
		Y.one("#solicitanteApellidoMaterno").setAttribute("readonly", "true");
		Y.one("#cobradorNombres").setAttribute("readonly", "true");
		Y.one("#cobradorApellidoPaterno").setAttribute("readonly", "true");
		Y.one("#cobradorApellidoMaterno").setAttribute("readonly", "true");
		Y.one(".imprimirSolicitud").on('click', habilitarGuardar);
  
		
		Y.one("input#btn-guardar").on('click', mostrarModal); // función definida más abajo del modal
    	Y.one("#solicitanteRut").on("change", obtenDatosSolicitante);
    	Y.one("#cobradorRut").on("change", obtenDatosCobrador);
    	Y.all(".clicker-solicitante").on('change', solicitanteRadioCheckHandler);
    	Y.all(".clicker-tipo-pago").on('change', tipoPagoRadioCheckHandler);
    	
    	// Todos estos chequeos para la partida
    	solicitanteRadioCheckHandler();
    	tipoPagoRadioCheckHandler();
    	obtenDatosSolicitante();
    	obtenDatosCobrador();
    	
    	function habilitarGuardar() {
    		Y.one("input#btn-guardar").set("disabled", false);
    	}
    	
    	function solicitanteRadioCheckHandler () {
    		Y.one("#solicitanteRut").set("value", null);
    		Y.one("#solicitanteNombres").set("value", null);
    		Y.one("#solicitanteApellidoPaterno").set("value", null);
    		Y.one("#solicitanteApellidoMaterno").set("value", null);
    		Y.one("#solicitanteRelacion").set("value", null);
    		// Validar si es Trabajador
    		if (Y.one("#clicker-solicitante-trabajador").get("checked") === true) {
    			Y.one("#solicitanteRut").set("value", Y.one("#runTrabajador").get("value"));
    			obtenDatosSolicitante();
    		}
    		if (Y.one("#clicker-solicitante-otro").get("checked") === true) {
    			// mostrar el `otro`
    			Y.one("#solicitanteRelacion").get("parentNode").setStyle("display", "");
    		} else {
    			// No mostrar el campo `otro`
    			Y.one("#solicitanteRelacion").get("parentNode").setStyle("display", "none");
    		}
    		
    		return false;
    	}
    	
		function tipoPagoRadioCheckHandler () {			
			if (Y.one("#clicker-tipo-pago-deposito").get("checked") === true) {
				Y.one("#datos-deposito").setStyle("display", "");
			} else if (Y.one("#clicker-tipo-pago-presencial").get("checked") === true) {
				Y.one("#datos-deposito").setStyle("display", "none");
			}
			
			return false;
		}		
		function obtenDatosSolicitante () {
			//Bloqueamos todo primmero
			Y.one("#solicitanteNombres").setAttribute("readonly", "true");
			Y.one("#solicitanteApellidoPaterno").setAttribute("readonly", "true");
			Y.one("#solicitanteApellidoMaterno").setAttribute("readonly", "true");
			
			var rutPrestador = Y.one("#solicitanteRut").get("value");
			// Saquemos el guion internamente
			rutPrestador = rutPrestador.replace(/-/, "");
			
			// Usamos la misma función de dp06
			Y.io("datosPrestadorNaturalJSON?rutPrestador=" + rutPrestador, {
				on : {
					success : function (tx, r) {
						var data = Y.JSON.parse(r.responseText);
						
						// Si encuentra los datos, llena los campos correspondientes y los deshabilita.
						// Si no encuentra los datos, habilita los campos deshabilitados.
						if (data.nombre) {
							Y.one("#solicitanteNombres").set("value", data.nombre);
							Y.one("#solicitanteApellidoPaterno").set("value", data.apellidoPaterno);
							Y.one("#solicitanteApellidoMaterno").set("value", data.apellidoMaterno);
						} else {							
							Y.one("#solicitanteNombres").set("value", "");
							Y.one("#solicitanteNombres").removeAttribute("readonly");
							Y.one("#solicitanteApellidoPaterno").set("value", "");
							Y.one("#solicitanteApellidoPaterno").removeAttribute("readonly");
							Y.one("#solicitanteApellidoMaterno").set("value", "");
							Y.one("#solicitanteApellidoMaterno").removeAttribute("readonly");
						}
						
						return false;						
					}
				}
			});
		}
		
		function obtenDatosCobrador () {
			//Bloqueamos todo primmero
			Y.one("#cobradorNombres").setAttribute("readonly", "true");
			Y.one("#cobradorApellidoPaterno").setAttribute("readonly", "true");
			Y.one("#cobradorApellidoMaterno").setAttribute("readonly", "true");
			
			
			var rutPrestador = Y.one("#cobradorRut").get("value");
			// Saquemos el guion internamente
			rutPrestador = rutPrestador.replace(/-/, "");
			
			// Usamos la misma función de dp06
			Y.io("datosPrestadorNaturalJSON?rutPrestador=" + rutPrestador, {
				on : {
					success : function (tx, r) {
						var data = Y.JSON.parse(r.responseText);
						
						// Si encuentra los datos, llena los campos correspondientes y los deshabilita.
						// Si no encuentra los datos, habilita los campos deshabilitados.
						if (data.nombre) {
							Y.one("#cobradorNombres").set("value", data.nombre);
							Y.one("#cobradorApellidoPaterno").set("value", data.apellidoPaterno);
							Y.one("#cobradorApellidoMaterno").set("value", data.apellidoMaterno);
						} else {							
							Y.one("#cobradorNombres").set("value", "");
							Y.one("#cobradorNombres").removeAttribute("readonly");
							Y.one("#cobradorApellidoPaterno").set("value", "");
							Y.one("#cobradorApellidoPaterno").removeAttribute("readonly");
							Y.one("#cobradorApellidoMaterno").set("value", "");
							Y.one("#cobradorApellidoMaterno").removeAttribute("readonly");
						}
						
						return false;						
					}
				}
			});
		}

    	/*********************************************************************************
    	 * MODAL para Preguntar si está correcto o corrige
    	 * 
    	 *********************************************************************************/

		var dialog_CorrectoOCorrige = new Y.Panel({
	        srcNode       : '#modalsrc',
	        headerContent : 'Revisión Formulario',
	        centered      : true,
	        width         : 600,
	        zIndex        : 6,
	        modal         : true, // modal behavior
	        render        : true,
	        visible       : false, // make visible explicitly with .show()
	        plugins       : [Y.Plugin.Drag],
	        buttons       : {
	            footer    : [
	                 {
	                     classNames  : 'pure-button-success btn-modal-proceed',
	                     name        : 'corregirFormulario',
	                     label       : 'Corregir Solicitud',
	                     action      : 'onCorregir'
	                 },
	                 {
	                     classNames  : 'pure-button-success btn-modal-proceed',
	                     name        : 'guardarFormulario',
	                     label       : 'Guardar Solicitud de Reembolso',
	                     action      : 'onGuardar'
	                 }	                 
	            ]
	        }
	    });
		
    	dialog_CorrectoOCorrige.onGuardar = function (e) {
	        e.preventDefault();
	        // the callback is not executed, and is callback reference removed, so it won't persist
	        this.callback = false;
	        
	        Y.config.doc.forms['dp04'].submit();
	    }
	    
    	dialog_CorrectoOCorrige.onCorregir = function (e) {
            e.preventDefault();
            this.hide();
            // the callback is not executed, and is callback reference removed, so it won't persist
            this.callback = false;
        }	    
	    
        // remover la clase del modal para partir
        YAHOO.util.Dom.removeClass("modalsrc", "yui-pe-content");
        
        function mostrarModal () {
        	dialog_CorrectoOCorrige.show();
        	return false;
        }
    });
});
