
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
    	// Necesitamos deshabilitar unos campos aca
    	Y.all(".input-valor-unitario-final").setAttribute("readonly", "readonly");
    	Y.all(".input-recargo-unitario-final").setAttribute("readonly", "readonly");
    	Y.all(".input-valor-total-final").setAttribute("readonly", "readonly");    	
   	
    	// Handlers
    	Y.all("a.link-consulta").on("click", obtenConsulta);
    	Y.all(".input-cantidad-final").on("change", calcularTotal);
    	Y.all(".input-descuento-final").on("change", calcularTotal);
    	
    	// Calcula por primera vez
    	calcularTotal();
    	
    	function calcularTotal () {
    		// Necesito obtener una lista de los ids  		
    		var rows = Y.one("#tablaCM").get("children").item(1).get("children")
    		var ids  = [];
    		rows.each(function (i) { ids.push(i.get("id").replace(/tr_/, "")); });
    		var valorTotalFinalTabla = 0;    		
    		
    		// En realidad, podriamos iterar sobre el mismo rows, pero preferí
    		// por mantenibilidad y otros, iterar sobre ids
    		var id;
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
            	var valorTotalFinalInt = ( parseInt(cantidad) *
            							  ( parseInt(valorUnitario) - parseInt(descuento) +
								            parseInt(recargoUnitario)));
            	var valorTotalFinal    = valorTotalFinalInt
            						      .toString()
            							  .replace(/\B(?=(\d{3})+(?!\d))/g, ",");
           	
            	// Y seteemos los valores
            	Y.one("#valorTotalFinal_"       + id).set("value", valorTotalFinal);
            	
            	valorTotalFinalTabla += parseInt(valorTotalFinalInt);
    		}
        	
    		// Pasemos a formato de miles este elemento
    		valorTotalFinalTabla = valorTotalFinalTabla.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");    		
    		Y.one("#valorTotalFinalTabla").setHTML(valorTotalFinalTabla);
    		
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
         * MODAL para consulta medica
         * 
         *********************************************************************************/
        YAHOO.util.Dom.removeClass("modalsrc-medica", "yui-pe-content");
        
        // Create the `dialog` object (para consulta medica)
        var dialog_medica = new Y.Panel({
            srcNode    : '#modalsrc-medica',
            headerContent: 'Consulta Médica',
            centered   : true,
            width      : 450,
            zIndex     : 6,
            centered   : true,
            modal      : true,
            render     : true,
            visible    : false,
            plugins      : [Y.Plugin.Drag],
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

        dialog_medica.onCancel = function (e) {
            this.hide();
            this.callback = false;	
        }        
        
        dialog_medica.onCopiarValor = function (e) {
        	// TODO: Hacer alguna wea acá
        }
        
        
        /*********************************************************************************
         * MODAL para consulta convenio
         * 
         *********************************************************************************/       
        YAHOO.util.Dom.removeClass("modalsrc-convenio", "yui-pe-content");
        
        // Create the `dialog` object (para consulta medica y convenio)
        var dialog_convenio = new Y.Panel({
            srcNode    : '#modalsrc-convenio',
            headerContent: 'Consulta Convenio',
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
                     }
                ]
            }
        });

        dialog_convenio.onCancel = function (e) {
            this.hide();
            this.callback = false;	
        }   
        
        dialog_convenio.onCopiarValor = function (e) {
        	// TODO: Hacer alguna wea acá
        }
        
        function obtenConsulta (e) {
        	// Limpiamos los inputs
        	limpiaInputs();
        	
			// Identifica quien hizo click
			var element      = Y.one(e._currentTarget);
			var id           = element.get("id").split("_");
			var consultaId   = id[0];
			var tipoConsulta = id[2];
			var dialog;
			
			if (tipoConsulta === "M") {
				dialog = dialog_medica;
			} else {
				dialog = dialog_convenio;
			}
			
			Y.io("obtenConsultaJSON?tipo=" + tipoConsulta + '&id=' + consultaId, {
				on : {
					success : function (tx, r) {
						var data = Y.JSON.parse(r.responseText);	
						if (data.texto) Y.all("#modal-content-textarea").set("value", data.texto);

						if (data.respuestaTexto != null)
							Y.all("#modal-content-respuesta-texto").set("value", data.respuestaTexto);

						// Especifico al modal de medica
						if (data.respuestaModificarCantidad != null) {
							Y.all("#modal-content-modificar-monto").setStyle("display", "inline");
							Y.one("#modal-content-copiar-valor-medica").setStyle("display", "");
							Y.one("#modal-content-copiar-valor-medica").on("click", copiarValor.bind(null, "cantidad", consultaId));
							Y.all("#modal-content-clickers-modificar").set("checked", "checked");
							Y.all("#modal-content-modificar-monto").set("value", data.respuestaModificarCantidad);
						} else {
							// Keep it hidden
							Y.all("#modal-content-modificar-monto").setStyle("display", "none");
							Y.all("#modal-content-copiar-valor-medica").setStyle("display", "none");
						}

						if (data.respuestaSugiereAprobar != null) {
							if (data.respuestaSugiereAprobar === true)
								Y.all("#modal-content-clickers-aprobar").set("checked", "checked");
							if (data.respuestaSugiereAprobar === false)
								Y.all("#modal-content-clickers-rechazar").set("checked", "checked");
						}						
						
						// Especifico al modal de convenio
						if (data.respuestaModificarMonto != null) {
							Y.all("#modal-content-clickers-modificar").set("checked", "checked");
							Y.all("#modal-content-modifica-monto").setStyle("display", "");
							Y.one("#modal-content-copiar-valor-convenio").setStyle("display", "");
							Y.one("#modal-content-copiar-valor-convenio").on("click", copiarValor.bind(null, "valor", consultaId));

							Y.all("#modal-content-comment-wrapper").setStyle("width", "45%");
							Y.one("#modal-content-valor-unitario").set("value", data.respuestaModificarMonto);
							
							// Necesitamos la cantidad para multiplicarla
							var _element = Y.one(document.getElementById(data.id__tipo));
							var cantidad = _element.get("parentNode").get("parentNode").get("children").item(11).get("children").get("value").toString();
							Y.all("#modal-content-cantidad-prestaciones").set("value", cantidad);
							var total    = cantidad * data.respuestaModificarMonto;
							Y.all("#modal-content-valor-total").set("value", total);
						} else {
							Y.all("#modal-content-modifica-monto").setStyle("display", "none");
							Y.all("#modal-content-copiar-valor-convenio").setStyle("display", "none");
							Y.all("#modal-content-comment-wrapper").setStyle("width", "95%");
						}
						
						dialog.show();
					}
				}
			});
        }

        function copiarValor (tipo, consultaId, e) {
        	e.preventDefault();
        	var val;
        	
        	if (tipo === "valor") {
        		val = Y.one("#modal-content-valor-unitario").get("value");
        		Y.one("#valorUnitarioFinal_" + consultaId).set("value", val);
        		dialog_convenio.onCancel();
        	} else if (tipo === "cantidad") {
        		val = Y.one("#modal-content-modificar-monto").get("value");
        		Y.one("#cantidadFinal_" + consultaId).set("value", val);
        		dialog_medica.onCancel();
        	}
       	
        	// Despues que copiamos el valor, debemos recalcular
        	calcularTotal();
        	
        	return false;
        }
        
        function limpiaInputs () {
        	// Limpiamos todo para futuras consultas
        	Y.all("#modal-content-textarea").set("value", "");
        	Y.all("#modal-content-respuesta-texto").set("value", "");        	
        	Y.all("#modal-content-modificar-monto").set("value", "");
        	Y.all("#modal-content-valor-unitario").set("value", "");
        	
        	// Detachear handler si es necesario!
        	Y.one("#modal-content-copiar-valor-medica").detach();
        	Y.one("#modal-content-copiar-valor-convenio").detach();        	
        	
        	return false;
        }
    });
});
