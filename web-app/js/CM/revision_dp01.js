
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
    	
    	// Necesitamos deshabilitar unos campos aca
    	Y.all(".input-valor-unitario-final").set("disabled", true);
    	Y.all(".input-recargo-unitario-final").set("disabled", true);
    	Y.all(".input-valor-total-final").set("disabled", true);
    	
    	// Y asignate handlers para los 4 siguientes casos
    	Y.all(".input-cantidad-final").on("change", editaFila);
    	Y.all(".input-descuento-final").on("change", editaFila);   	
    	
    	// Calculamos el total por primera vez
    	calculaTotal();
    	
    	function editaFila (e) {
    		// Determinar en que fila estamos
        	var element      = Y.one(e._currentTarget);
        	var id           = element.get("id").split("_")[1];
        	// Con el id obtenemos los 4 valores
        	var cantidad        = Y.one("#cantidadFinal_"        + id).get("value");
        	var descuento       = Y.one("#descuentoFinal_"       + id).get("value");
        	var valorUnitario   = Y.one("#valorUnitarioFinal_"   + id).get("value");
        	var recargoUnitario = Y.one("#recargoUnitarioFinal_" + id).get("value");
        	// Un arreglo si los campos vienen vacios
        	if (cantidad === "") cantidad = 0;
        	if (descuento === "") descuento = 0;
        	if (valorUnitario === "") valorUnitario = 0;
        	if (recargoUnitario === "") recargoUnitario = 0;
        	// Ya po, llenemos las formulas
        	var valorTotalFinal       = parseInt(cantidad) * (parseInt(valorUnitario) - parseInt(descuento) + parseInt(recargoUnitario));
        	var ivaFinal              = Math.ceil(0.19 * parseInt(valorUnitario));
        	var valorConIvaTotalFinal = valorTotalFinal + ivaFinal;
        	// Y seteemos los valores
        	Y.one("#valorTotalFinal_"       + id).set("value", valorTotalFinal);

        	// Debemos calcular el total también
        	calculaTotal();
        	
        	return false;
    	}
    	
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

        // Create the `dialog` object (para consulta medica y convenio)
        var dialog = new Y.Panel({
            srcNode    : '#modalsrc',
            headerContent: 'Consulta Médica',
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
                         classNames  : 'pure-button-success btn-modal-proceed',
                         name        : 'cancel',
                         label       : 'Cancelar',
                         action      : 'onCancel'
                     },
                    {
                        classNames  : 'pure-button-success btn-modal-proceed',
                        name        : 'proceed',
                        label       : 'Guardar Consulta',
                        action      : 'onOK'
                    }
                ]
            }
        });

        YAHOO.util.Dom.removeClass("modalsrc", "yui-pe-content");
        
        var dialogCurrents = {
        		tipoConsulta : null,
        		idDetalle    : null
        }        
        
        dialog.onCancel = function (e) {
            e.preventDefault();
            
            //Borra el check si es que no esta disabled
            var tipo = dialogCurrents.tipoConsulta;
        	var id   = dialogCurrents.idDetalle; 
            var c = Y.one(document.getElementsByName(id + "_" + tipo)[0])
            var cD = c.get('disabled');
            if(!cD){
            	//Si no esta disabled
            	c.set('checked',false);
            } 
            this.hide();
            Y.one("#modal-content-textarea").set("value","");
            // the callback is not executed, and is callback reference removed, so it won't persist
            this.callback = false;
        }
        
        dialog.onOK = function (e) {
            e.preventDefault();
            var texto =  Y.one("#modal-content-textarea").get("value");
            
            if (texto != null && texto != "") {
            	var tipo = dialogCurrents.tipoConsulta;
            	var id   = dialogCurrents.idDetalle;  	
            	            	
        		Y.io("guardaConsultaJSON?tipo=" + tipo + '&id=' + id + "&texto=" + texto, {
        			on : {
        				success : function (tx, r) {
        					// Disable el check
        					Y.one(document.getElementsByName(id + "_" + tipo)[0]).set('disabled', true)
        					// Crear el link para editar
        					var children = Y.one(document.getElementsByName(id + "_" + tipo)[0]).get("parentNode").get("children");
        					
        					if (tipo === "M") {
        						// Editamos el segundo elemento
        						children.item(2).replace('&nbsp;<a id="' + id + "_" + tipo +'" class="link-consulta" href="#" onclick="return false;">(M)</a>');
        					} else {
        						// Es "C" y reemplazamos el septimo elemento
        						children.item(6).replace('&nbsp;<a id="' + id + "_" + tipo +'" class="link-consulta" href="#" onclick="return false;">(C)</a>');
        					}
        					
        					// Desasignar y asignar
        					Y.all("a.link-consulta").detach("click", modificaConsulta);
        					Y.all("a.link-consulta").on("click", modificaConsulta);
        					
        					// Borrar el texto del modal
        					Y.one("#modal-content-textarea").set("value", "");
        					
        					return false;
        				}
        			}
        		});
        		this.hide();
        		
            }else{
            	alert("Por favor ingresar consulta.")
            	Y.one("#modal-content-textarea").focus()
            }
            
            // the callback is not executed, and is callback reference removed, so it won't persist
            this.callback = false;
        }
  
        Y.all(getAllCheckboxes()).on("change", generaConsulta);

        Y.all("a.link-consulta").on("click", modificaConsulta);
        
        function generaConsulta (e) {
        	// Solo haz el proceso si es un check a true. You know.
        	if (Y.one(e._currentTarget).get("checked")) {
            	var detalle                 = Y.one(e._currentTarget).get("value").split("_");
            	dialogCurrents.tipoConsulta = detalle[1];
            	dialogCurrents.idDetalle    = detalle[0];
            	dialogHeader                = detalle[1] === "M" ? "Consulta Médica" : "Consulta Convenio";
            	        	
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
        	
        	// Va a buscar la información
    		Y.io("obtenConsultaJSON?tipo=" + tipoConsulta + '&id=' + consultaId, {
    			on : {
    				success : function (tx, r) {
    					var data = Y.JSON.parse(r.responseText);	
    					if (data.texto) Y.one("#modal-content-textarea").set("value", data.texto);
     	            	
    					dialogCurrents.tipoConsulta = tipoConsulta;
    	            	dialogCurrents.idDetalle    = consultaId;
    	            	dialogHeader                = tipoConsulta === "M" ? "Consulta Médica" : "Consulta Convenio";
    					dialog.show();
    				}
    			}
    		});
        	
        	return false;
        }
        
        /**
         * Auxiliar
         */
        function getAllCheckboxes () {
			var inputs     = document.getElementsByTagName("input");
			var checkboxes = [];
			for (i in inputs) {
				if (inputs[i].type === 'checkbox') checkboxes.push(inputs[i]);
			}			
			return checkboxes;
        }
    });
});
