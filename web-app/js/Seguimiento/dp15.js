
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
   
    	/*********************************************************************************
    	 * MODAL para Solicitud de Nota de Credito
    	 * 
    	 *********************************************************************************/
    	//remover la clase del modal para partir
        YAHOO.util.Dom.removeClass("modalreporte-errores", "yui-pe-content");
    	
        //creando modal para errores
    	var dialogReporteErrores = new Y.Panel({
	        srcNode    : '#modalreporte-errores',
	        headerContent: 'Reporte errores',
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
	                     name        : 'cerrarModal',
	                     label       : 'cerrar',
	                     action      : 'onCerrarModal'
	                 }
	            ]
	        }
	    });
    	
	    // Asignemos estos handlers al ultimo
		Y.one("#reporteErrores").on("click", function (e) {
			var key = Y.one("key").get("value");
			alert("key value : " + key)
			e.preventDefault();
			Y.one("#modalreporte-errores").get("parentNode").setStyle('display', '');
			Y.one("#modalreporte-errores").get("parentNode").get("parentNode").setStyle('display', '');
			dialogReporteErrores.show();
			Y.one('.yui3-panel-focused')	
				.setStyle('top', '50px')
				.setStyle('left', Math.floor((document.body.clientWidth - 600) / 2));	
			document.body.scrollTop = 0;
		});
		
		
		//dialog para reporte errores
	    dialogReporteErrores.onCerrarModal = function (e) {
	    	e.preventDefault();
	    	dialogReporteErrores.hide();
	    }
		
		return false;
    });
});
