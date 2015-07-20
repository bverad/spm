
YUI().use("io", "json-parse", "node", "panel", function (Y) {
    Y.on("domready", function () {
    	
    	// Estos son los eventos para buscar los prestadores juridico o natural según sea el caso
		Y.one("#proveedorJuridicoRut").on("change", obtenDatosPorRutPrestadorJuridico);
		Y.one("#proveedorNaturalRut").on("change", obtenDatosPorRunPrestadorNatural);

    	Y.all(".clicker-tipo-proveedor").on('change', tipoProveedorRadioCheckHandler);
    	tipoProveedorRadioCheckHandler(); // Una para la partida
    	
		Y.on("available", activarTerminar, "#detalleTable");
		
		/**
		 * 
		 */
		function activarTerminar() {
			var filas = Y.all("#detalleTable tbody tr");
			if (filas._nodes.length == 0) {
				Y.one("#terminarButton").set('disabled', true);
			} else {
				Y.one("#terminarButton").set('disabled', false);
			}
		}

    	/**
    	 *  
    	 */    	
		function tipoProveedorRadioCheckHandler () {			
			if (Y.one("#clicker-tipo-proveedor-juridico").get("checked") === true) {
				Y.one("#datos-juridico").setStyle('display', '');
				Y.one("#datos-natural").setStyle('display', 'none');
			} else if (Y.one("#clicker-tipo-proveedor-natural").get("checked") === true) {
				Y.one("#datos-juridico").setStyle('display', 'none');
				Y.one("#datos-natural").setStyle('display', '');
			}
			
			return false;
		}
		
    	/**
    	 *  
    	 */
		function obtenDatosPorRutPrestadorJuridico () {
			var rutPrestador = Y.one("#proveedorJuridicoRut").get("value");
			// Saquemos el guion internamente
			rutPrestador = rutPrestador.replace(/-/, "");
			
			Y.io("datosPrestadorJuridicoJSON?rutPrestador=" + rutPrestador, {
				on : {
					success : function (tx, r) {
						
						var data = Y.JSON.parse(r.responseText);
						
						// Si encuentra el dato, llena el campo correspondiente y lo deshabilita.
						// Si no encuentra el dato, habilita el campos deshabilitado.
						if (data.razon_social) {
							Y.one("#proveedorJuridicoRazonSocial").set("value", data.razon_social);
							Y.one("#proveedorJuridicoRazonSocial").setAttribute("readonly", "true");
						} else {							
							Y.one("#proveedorJuridicoRazonSocial").set("value", "");
							Y.one("#proveedorJuridicoRazonSocial").removeAttribute("readonly");
						}
						
						return false;						
					}
				}
			});
		}
		
    	/**
    	 *  
    	 */
		function obtenDatosPorRunPrestadorNatural () {
			var rutPrestador = Y.one("#proveedorNaturalRut").get("value");
			// Saquemos el guion internamente
			rutPrestador = rutPrestador.replace(/-/, "");
			
			Y.io("datosPrestadorNaturalJSON?rutPrestador=" + rutPrestador, {
				on : {
					success : function (tx, r) {
						var data = Y.JSON.parse(r.responseText);
						
						// Si encuentra los datos, llena los campos correspondientes y los deshabilita.
						// Si no encuentra los datos, habilita los campos deshabilitados.
						if (data.nombre) {
							Y.one("#proveedorNaturalNombres").set("value", data.nombre);
							Y.one("#proveedorNaturalNombres").setAttribute("readonly", "true");
							Y.one("#proveedorNaturalApellidoPaterno").set("value", data.apellidoPaterno);
							Y.one("#proveedorNaturalApellidoPaterno").setAttribute("readonly", "true");
							Y.one("#proveedorNaturalApellidoMaterno").set("value", data.apellidoMaterno);
							Y.one("#proveedorNaturalApellidoMaterno").setAttribute("readonly", "true");
						} else {							
							Y.one("#proveedorNaturalNombres").set("value", "");
							Y.one("#proveedorNaturalNombres").removeAttribute("readonly");
							Y.one("#proveedorNaturalApellidoPaterno").set("value", "");
							Y.one("#proveedorNaturalApellidoPaterno").removeAttribute("readonly");
							Y.one("#proveedorNaturalApellidoMaterno").set("value", "");
							Y.one("#proveedorNaturalApellidoMaterno").removeAttribute("readonly");
						}
						
						return false;						
					}
				}
			});
		}
    });
});