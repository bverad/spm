
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
    	// Necesitamos deshabilitar unos campos aca
    	Y.all(".input-cantidad-final").set("disabled", true);
    	Y.all(".input-valor-unitario-final").set("disabled", true);
    	Y.all(".input-descuento-final").set("disabled", true);
    	Y.all(".input-recargo-unitario-final").set("disabled", true);
    	Y.all(".input-valor-total-final").set("disabled", true);

    	// Calculamos el total
    	calculaTotal();

    	function calculaTotal () {
    		// Necesito obtener una lista de los ids  		
    		var rows = Y.one("#tablaCM").get("children").item(1).get("children")
    		var ids  = [];
    		rows.each(function (i) { ids.push(i.get("id").replace(/tr_/, "")); });
    		var valorTotalFinalTabla = 0;    		
    		
    		// En realidad, podriamos iterar sobre el mismo rows, pero preferí
    		// por mantenibilidad y otros, iterar sobre ids
    		var id, valorTotalFinalInt;
    		for (var i in ids) {
    			id = ids[i];    			
            	// Con el id obtenemos los 4 valores
            	var cantidad        = Y.one("#cantidadFinal_"        + id).get("value");
            	var descuento       = Y.one("#descuentoFinal_"       + id).get("value").replace(/\.|\,/g, "");
            	var valorUnitario   = Y.one("#valorUnitarioFinal_"   + id).get("value").replace(/\.|\,/g, "");
            	var recargoUnitario = Y.one("#recargoUnitarioFinal_" + id).get("value").replace(/\.|\,/g, "");
            	// Un arreglo si los campos vienen vacios
            	if (cantidad === "")        cantidad = 0;
            	if (descuento === "")       descuento = 0;
            	if (valorUnitario === "")   valorUnitario = 0;
            	if (recargoUnitario === "") recargoUnitario = 0;
            	// Ya po, llenemos las formulas
            	valorTotalFinalInt = ( parseInt(cantidad) *
            						   ( parseInt(valorUnitario) - parseInt(descuento) +
								         parseInt(recargoUnitario)));
            	
            	valorTotalFinalTabla += parseInt(valorTotalFinalInt);
    		}
        	
    		Y.one("#valorTotalFinalTabla").setHTML(valorTotalFinalTabla.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    		
        	return false;
    	}
    	
    	/**********************************************************************************
    	 * MODAL para warnings
    	 **********************************************************************************/
        var dialog_warning = new Y.Panel({
            srcNode        : '#modalsrc-warning',
            headerContent  : 'Alertas',
            centered   	   : true,
            width          : 410,
            zIndex         : 6,
            centered       : true,
            modal          : true, // modal behavior
            render         : true,
            visible        : false, // make visible explicitly with .show()
            plugins        : [Y.Plugin.Drag],
            buttons    : {
                footer: [
                     {
                         classNames  : 'pure-button-success btn-modal-proceed',
                         name        : 'cancel',
                         label       : 'Cancelar',
                         action      : 'onCancel'
                     }
                ]
            }
        });
    	
        // boton cancelar en dialog_warning
        dialog_warning.onCancel = function (e) {
            e.preventDefault();
            this.hide();
            // the callback is not executed, and is callback reference removed, so it won't persist
            this.callback = false;
        }       
                
        // remover la clase del modal para partir
        YAHOO.util.Dom.removeClass("modalsrc-warning", "yui-pe-content");
        
        // Asigna el event handler de los links
        Y.all("a.link-warning").on("click", despliegaWarnings);
        
        // Despliega warnings en un lindo modal
        function despliegaWarnings (e) {
        	// Identifica quien hizo click
        	var element      = Y.one(e._currentTarget);
        	var id           = element.get("id").split("_");
        	var detalleId    = id[0];
	    	
        	// Oculta los warnings de los otros detalles
        	Y.all(".warnings").hide();
        	
        	// Muestra el texto de warning del id adecuado
        	Y.one("#warning_" + detalleId).show();
        	
			dialog_warning.show();

			return false;
        }
        
        /*********************************************************************************
         * MODAL para consulta medica y convenio
         * 
         *********************************************************************************/        
        YAHOO.util.Dom.removeClass("modalsrc", "yui-pe-content");
        
        // Create the `dialog` object (para consulta medica y convenio)
        var dialog = new Y.Panel({
            srcNode    : '#modalsrc',
            headerContent: 'Consulta Médica',
            centered   : true,
            width      : 450,
            zIndex     : 6,
            centered   : true,
            modal      : true, // modal behavior
            render     : true,
            visible    : false, // make visible explicitly with .show()
            plugins      : [Y.Plugin.Drag],
            buttons    : {
                footer: [
                     {
                         classNames  : 'pure-button-success btn-modal-proceed',
                         name        : 'cancel',
                         label       : 'Cancelar',
                         action      : 'onCancel'
                     },
                    {
                        classNames  : 'pure-button-success btn-modal-proceed',
                        name        : 'proceed',
                        label       : 'Guardar Respuesta',
                        action      : 'onOK'
                    }
                ]
            }
        });
        
        var dialogCurrents = {
        		tipoConsulta : null,
        		idDetalle    : null
        }        
        
        dialog.onCancel = function (e) {
            e.preventDefault();
			// Borrar el texto del modal
			Y.one("#modal-content-textarea").set("value", "");
			Y.one("#modal-content-respuesta-texto").set("value", "");
			Y.one("#modal-content-clickers-aprobar").set("checked", "checked");
			Y.one("#modal-content-modificar-monto").set("disabled", true);
			Y.all(".modal-content-clickers").detach();

            this.hide();
            // the callback is not executed, and is callback reference removed, so it won't persist
            this.callback = false;
        }
        
        dialog.onOK = function (e) {
            e.preventDefault();
        	var tipo = dialogCurrents.tipoConsulta;
        	var id   = dialogCurrents.idDetalle;  	

        	// Recojamos los valores
        	var respuestaTexto =  Y.one("#modal-content-respuesta-texto").get("value");
        	if (respuestaTexto.trim() === '') respuestaTexto = null;
        	var respuestaSugiereAprobar = null;
        	if (Y.one("#modal-content-clickers-aprobar").get("checked") === true) respuestaSugiereAprobar = true;
        	if (Y.one("#modal-content-clickers-rechazar").get("checked") === true) respuestaSugiereAprobar = false;
        	var respuestaModificarCantidad = null;
        	if (Y.one("#modal-content-clickers-modificar").get("checked") === true) {
        		var monto = Y.one("#modal-content-modificar-monto").get("value");
        		if (parseInt(monto).toString() !== 'NaN') respuestaModificarCantidad = monto;
        	}

    		Y.io("guardaRespuestaJSON?tipo=" + tipo +
    				"&id=" + id +
    				"&respuestaTexto=" + (respuestaTexto != null? respuestaTexto : "")  +
    				"&respuestaSugiereAprobar=" + respuestaSugiereAprobar +
    				"&respuestaModificarCantidad=" + (respuestaModificarCantidad != null? respuestaModificarCantidad : "") , {
    			on : {
    				success : function (tx, r) {
    					// Borrar el texto del modal, reiniciar los checkboxes...
    					Y.one("#modal-content-textarea").set("value", "");
    					Y.one("#modal-content-respuesta-texto").set("value", "");
    					Y.one("#modal-content-clickers-aprobar").set("checked", "checked");
    					Y.one("#modal-content-modificar-monto").set("disabled", true);
    					Y.all(".modal-content-clickers").detach();
    					
    					return false;
    				}
    			}
    		});

            this.hide();
            // the callback is not executed, and is callback reference removed, so it won't persist
            this.callback = false;
        }
        
        Y.all("a.link-consulta").on("click", modificaConsulta);
        
		function generaConsulta (e) {
			// Solo haz el proceso si es un check a true. You know.
			if (Y.one(e._currentTarget).get("checked")) {
		    	var detalle                 = Y.one(e._currentTarget).get("value").split("_");
		    	dialogCurrents.tipoConsulta = detalle[1];
		    	dialogCurrents.idDetalle    = detalle[0];
		    	dialogHeader                = "Consulta Médica";
		    	        	
		    	Y.one(".yui3-widget-hd").set("text", dialogHeader);
		            	        	
		    	dialog.show(); 		
			}
		}

		function modificaConsulta (e) {
			// Identifica quien hizo click
			var element      = Y.one(e._currentTarget);
			var id           = element.get("id").split("_");
			var consultaId   = id[0];
			var tipoConsulta = id[1];

			var cantidad     = element.get('parentNode').get('parentNode').get('children').item(11).get('children').get('value')[0];
			
			// Va a buscar la información
			Y.io("obtenConsultaJSON?tipo=" + tipoConsulta + '&id=' + consultaId, {
				on : {
					success : function (tx, r) {
						var data = Y.JSON.parse(r.responseText);	
						if (data.texto) Y.one("#modal-content-textarea").set("value", data.texto);

						if (data.respuestaTexto != null)
							Y.one("#modal-content-respuesta-texto").set("value", data.respuestaTexto);

						if (data.respuestaModificarCantidad != null) {
							Y.one("#modal-content-clickers-modificar").set("checked", "checked");
							Y.one("#modal-content-modificar-monto").set("disabled", false);
							Y.one("#modal-content-modificar-monto").set("value", data.respuestaModificarCantidad);
						} else {
							Y.one("#modal-content-modificar-monto").set("disabled", true);
							Y.one("#modal-content-modificar-monto").set("value", cantidad);
						}
						
						if (data.respuestaSugiereAprobar != null) {
							if (data.respuestaSugiereAprobar === true)
								Y.one("#modal-content-clickers-aprobar").set("checked", "checked");
							if (data.respuestaSugiereAprobar === false)
								Y.one("#modal-content-clickers-rechazar").set("checked", "checked");
						}
						
						// Su event handler acá
						Y.all(".modal-content-clickers").on('change', modificaConsultaRadioCheckHandler);
						
						dialogCurrents.tipoConsulta = tipoConsulta;
						dialogCurrents.idDetalle    = consultaId;
						dialogHeader                = "Consulta Médica";
						dialog.show();
					}
				}
			});
			
			return false;
        }
		
		// Esta funcioncita nos ayudara a habilitar / deshabilitar el texto a modificar		
		function modificaConsultaRadioCheckHandler () {			
			if (Y.one("#modal-content-clickers-modificar").get("checked") === true) {
				// Al marcarse, habilita
				Y.one("#modal-content-modificar-monto").set("disabled", false);
			} else {
				// Al desmarcarse, deshabilita
				Y.one("#modal-content-modificar-monto").set("disabled", true);
			}
			
			return false;
		}
    });
});
