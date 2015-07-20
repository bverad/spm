
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
    	// Necesitamos deshabilitar unos campos aca
    	var diferenciaSuma = Y.one("#DiferenciaSuma").get("value");
    	if (diferenciaSuma >= 0) {
    		Y.one("#btnSolicitarNDC").set("disabled", true);
    	} else {
    		Y.one("#btnSolicitarFactura").set("disabled", true);
    	}
    
    	/*********************************************************************************
    	 * MODAL para Solicitud de Nota de Credito
    	 * 
    	 *********************************************************************************/
    	var dialog_NDC = new Y.Panel({
	        srcNode    : '#modalsrc-ndc',
	        headerContent: 'Decide Solicitar Nota de Crédito',
	        centered   : true,
	        width      : 600,
	        zIndex     : 6,
	        modal      : true, // modal behavior
	        render     : true,
	        visible    : false, // make visible explicitly with .show()
	        plugins      : [Y.Plugin.Drag],
	        buttons    : {
	            footer: [
	                 {
	                     classNames  : 'pure-button-success btn-modal-proceed',
	                     name        : 'notificarPrestador',
	                     label       : 'Notificar al Prestador',
	                     action      : 'onNotificarNDC'
	                 }
	            ]
	        }
	    });
	    
	    dialog_NDC.onNotificarNDC = function (e) {
	        e.preventDefault();
	        // the callback is not executed, and is callback reference removed, so it won't persist
	        this.callback = false;
	        var jsonReadyCount = 0;
	        
	        var facturaId  = Y.one("#facturaId").get("value");
	        var comentario = Y.one("#modal-content-textarea-ndc").get("value");
	        
	        // Si hay un comentario de factura, guardalo
	        guardarComentario();
	        // Si hay comentarios en los detalles de factura, guardalos
	        guardarComentariosCM();
	        
	        function guardarComentario () {		        
		        if ( comentario !== "" ) {
		    		Y.io("guardaComentarioFacturaJSON?facturaId=" + facturaId + "&comentario=" + comentario, {
		    			on : {
		    				success : function (tx, r) {
		    					return notificaPrestador();
		    				}
		    			}
		    		});
		        } else {
		        	return notificaPrestador();
		        }	        	
	        }
	        
	        function guardarComentariosCM () {
	        	var filasCM         = Y.all(".comentario-cm-ndc")
		        var cantidadCM      = filasCM.size();
	        	var commentsMessage = "";
	        	var comment;
		        if (cantidadCM > 0) {
		        	for (var i = 0; i < cantidadCM; i++) {
		        		// Necesito el id de CM y el comentario
		        		comment = filasCM.item(i).get("value");
		        		if (comment !== "") {
		        			commentsMessage += filasCM.item(i).get("id").replace("comentario-cm-ndc-", "")
		        			                 + ","
		        			                 + encodeURIComponent(comment)
		        			                 + ",";
		        		}
		        	}		        	
		        	// removamos ese ultimo ampersand
		        	commentsMessage = commentsMessage.slice(0, -1);
		        	
		        	// Mandamos el mensaje al server
		    		Y.io("guardaDetallesComentariosFacturaJSON?comentarios=" + commentsMessage, {
		    			on : {
		    				success : function (tx, r) {
		    					var data = Y.JSON.parse(r.responseText);
		    					if(data.respuesta){
		    						return notificaPrestador();
		    					}else{
		    						alert("Debe agregar comentarios antes de efectuar solicitud");
		    						return false;
		    					}	
		    				}
		    			}
		    		});		        	
		        } else {
		        	return notificaPrestador();
		        }	        	
	        }

	        function notificaPrestador () {
	        	jsonReadyCount += 1;
	        	
	        	if (jsonReadyCount == 2) {
			        // Estando listos modificamos el valor de resolucion y "presionamos" el boton.
			        Y.one("#resolucion").set("value", "Solicitar NC");
			        Y.config.doc.forms['dp02'].submit();        		
	        	}	        	

		        return false;
	        }
	        
	        return false;
	    }
    
        // remover la clase del modal para partir
        YAHOO.util.Dom.removeClass("modalsrc-ndc", "yui-pe-content");

    	/*********************************************************************************
    	 * MODAL para Solicitud de Factura
    	 * 
    	 *********************************************************************************/
    	var dialog_factura = new Y.Panel({
	        srcNode    : '#modalsrc-factura',
	        headerContent: 'Decide Solicitar factura',
	        centered   : true,
	        width      : 600,
	        zIndex     : 6,
	        modal      : true, // modal behavior
	        render     : true,
	        visible    : false, // make visible explicitly with .show()
	        plugins      : [Y.Plugin.Drag],
	        buttons    : {
	            footer: [
	                 {
	                     classNames  : 'pure-button-success btn-modal-proceed',
	                     name        : 'notificarPrestador',
	                     label       : 'Notificar al Prestador',
	                     action      : 'onNotificarFactura'
	                 }
	            ]
	        }
	    });
	    
	    dialog_factura.onNotificarFactura = function (e) {
	        e.preventDefault();
	        // the callback is not executed, and is callback reference removed, so it won't persist
	        this.callback = false;
	        var jsonReadyCount = 0;
	        
	        var facturaId  = Y.one("#facturaId").get("value");
	        var comentario = Y.one("#modal-content-textarea-fct").get("value");
	        
	        // Si hay un comentario de factura, guardalo
	        guardarComentario();
	        // Si hay comentarios en los detalles de factura, guardalos
	        guardarComentariosCM();
	        
	        function guardarComentario () {
		        if ( comentario !== "" ) {
		    		Y.io("guardaComentarioFacturaJSON?facturaId=" + facturaId + "&comentario=" + comentario, {
		    			on : {
		    				success : function (tx, r) {
		    					return notificaPrestador();
		    				}
		    			}
		    		});
		        } else {
		        	return notificaPrestador();
		        }	        	
	        }
	        
	        function guardarComentariosCM () {
	        	var filasCM         = Y.all(".comentario-cm-fct")
		        var cantidadCM      = filasCM.size();
	        	var commentsMessage = "";
	        	var comment;
		        if (cantidadCM > 0) {
		        	for (var i = 0; i < cantidadCM; i++) {
		        		// Necesito el id de CM y el comentario
		        		comment = filasCM.item(i).get("value");
		        		if (comment !== "") {
		        			commentsMessage += filasCM.item(i).get("id").replace("comentario-cm-fct-", "")
		        			                 + ","
		        			                 + encodeURIComponent(comment)
		        			                 + ",";
		        		}
		        	}		        	
		        	// removamos ese ultimo ampersand
		        	commentsMessage = commentsMessage.slice(0, -1);
		        	
		        	// Mandamos el mensaje al server
		    		Y.io("guardaDetallesComentariosFacturaJSON?comentarios=" + commentsMessage, {
		    			on : {
		    				success : function (tx, r) {
		    					return notificaPrestador();
		    				}
		    			}
		    		});		        	
		        } else {
		        	return notificaPrestador();
		        }
	        }

	        function notificaPrestador () {
				jsonReadyCount += 1;
				
				if (jsonReadyCount == 2) {
				    // Estando listos modificamos el valor de resolucion y "presionamos" el boton.
				    Y.one("#resolucion").set("value", "Solicitar Factura");
				    Y.config.doc.forms['dp02'].submit();
				}

		        return false;
	        }
	        
	        return false;
	    }
    
        // remover la clase del modal para partir
        YAHOO.util.Dom.removeClass("modalsrc-factura", "yui-pe-content");
                
	    // Asignemos estos handlers al ultimo
		Y.one("#btnSolicitarNDC").on("click", function () {
			Y.one("#modalsrc-ndc").get("parentNode").setStyle('display', '');
			Y.one("#modalsrc-ndc").get("parentNode").get("parentNode").setStyle('display', '');
			dialog_NDC.show();
			Y.one('.yui3-panel-focused')
				.setStyle('top', '50px')
				.setStyle('left', Math.floor((document.body.clientWidth - 600) / 2));	
			document.body.scrollTop = 0;
		});
		
		Y.one("#btnSolicitarFactura").on("click", function () {
			Y.one("#modalsrc-factura").get("parentNode").setStyle('display', '');
			Y.one("#modalsrc-factura").get("parentNode").get("parentNode").setStyle('display', '');
			dialog_factura.show();
			Y.one('.yui3-panel-focused')
				.setStyle('top', '50px')
				.setStyle('left', Math.floor((document.body.clientWidth - 600) / 2));			
			document.body.scrollTop = 0
		});
		
		return false;
    });
});
