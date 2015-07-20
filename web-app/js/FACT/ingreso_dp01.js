
YUI().use("node", "transition", "panel", "io","io-base", "dump", "json-parse", "datatype-number", function (Y) {
	// Desactivar los campos
	
	Y.on("domready", function () {
		// Borrar _hidden_
		//Y.one("#listaCM").set("value", "");
		var listaCM = Y.one("#listaCM").get('value');
		if (listaCM.length > 0) {
			// Agregar la fila
			var loadOxa = listaCM.split(':');
			var suma = 0;
			if (loadOxa.length > 0) {
				Y.Array.each(loadOxa, function (o) {
					var key = o.split('_')[0];
					var value = parseInt(o.split('_')[1]);
					var id = "CM_" + key + "_" + value;
					var html = '<tr class="CM" id="'+ id + '">' +
									'<td align="center">' + key + '</td>' +
									'<td align="right">' + Y.Number.format(value, {thousandsSeparator: "."}) + '</td>' +
									'<td align="center"><a href="#" onclick="return false;" style="text-decoration: none;"><font color="red"><i class="icon-trash icon-2x"></i></font></a></td>' +
								'</tr>'
					Y.one("#tablaCM tbody").append(html);

					if (isNaN(value)) suma = 0;
					suma += parseInt(value);

					// Asignar el event handler
					Y.one("#" + id)
					 .get('children')
					 .item(2)
					 .get('children')
					 .on('click', eliminarCM);
				});
				Y.one("#totalFactura").set("value", suma);
				Y.one("#totalFacturaTable").setHTML(Y.Number.format(suma, {thousandsSeparator: ".", prefix: "$ "}));
			}
		}	
		// Asignar los handlers
		Y.one("#rutPrestador").on("change", obtenDatosPorRutPrestador);
		Y.one("#btnAgregarCM").on("click", agregarCM);
		// Si es una recarga por errores, debemos obtener datos de prestador
		// y trabajador
		if (Y.one("#rutPrestador").get("value") != "") {
			obtenDatosPorRutPrestador();
		}
		return false;
	});

	/**
	 * Event Handlers
	 */
	function obtenDatosPorRutPrestador () {
		// Quitamos mensajes de alerta
		if (Y.one(".messages.obtenDatosPorRutPrestador")) Y.one(".messages.obtenDatosPorRutPrestador").remove();
		
		var rutPrestador = Y.one("#rutPrestador").get("value");
		// Saquemos el guion internamente
		rutPrestador = rutPrestador.replace(/-/, '');
		
		Y.io("datosPrestadorJSON?rutPrestador=" + rutPrestador, {
			on : {
				success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
					
					if (data.razon_social) {
						Y.one("#nombrePrestador").set("value", data.razon_social);
						Y.one("#direccionCasaMatriz").set("value", data.direccion);
						Y.one("#email").set("value", data.email);
						Y.one("#telefono").set("value", data.telefono);
					} else {
						mensajeError("obtenDatosPorRutPrestador", "El RUT ingresado no existe");
					}
					

					return false;
				}
			}
		});
	}

	function agregarCM () {
		// Borramos mensajes de errores anteriores
		if (Y.one(".messages.agregarCM")) Y.one(".messages.agregarCM").remove();    	
    	//Validamos si existe la cuenta medica, de lo contrario no se permite el ingreso
		// Necesitamos ver los oxaNumero primero
		var folioCuentaMedica   = Y.one("#folioCuentaMedica").get("value");
		var valorCuentaMedica   = Y.one("#valorCuentaMedica").get("value");
		Y.io("existeCuentaMedicaJSON?id=" + folioCuentaMedica, {
			sync: true,
			on : {
				success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
					if (!data.existCuentaMedica) {			
						mensajeError("agregarCM", "La cuenta medica " + folioCuentaMedica + " no existe");
						return false;
					}
					
					if (folioCuentaMedica === "" || valorCuentaMedica === "" ) {
						mensajeError("agregarCM", "Debe ingresar un folio y un valor de cuenta m&eacute;dica!");
						return false;
					}

			    	if (isNaN(parseInt(valorCuentaMedica))) { 
						mensajeError("agregarCM", "El valor de la cuenta m&eacute;dica debe ser num&eacute;rico!");
						return false;
			     	}
			    	
			    	// Ahora, veamos si no tenemos el valor ya.
					// Para eso, usemos nuestro _input_hidden_
					var lista = Y.one("#listaCM").get("value").split(":");
					
					var key, value;
					key   = Y.Lang.trim(folioCuentaMedica);
					value = parseInt(valorCuentaMedica);
					
					var k1 = key + "_";
					if (lista.toString().indexOf(k1) !== -1){
						mensajeError("agregarCM", "La cuenta m&eacute;dica " + key + " ya est&aacute; ingresada!");
					} else {
						// Agregar la fila
						var id = "CM_" + key + "_" + value;
						var html = '<tr class="CM" id="'+ id + '">' +
										'<td align="center">' + key + '</td>' +
										'<td align="right">' + Y.Number.format(value, {thousandsSeparator: "."}) + '</td>' +
										'<td align="center"><a href="#" onclick="return false;" style="text-decoration: none;"><font color="red"><i class="icon-trash icon-2x"></i></font></a></td>' +
									'</tr>'
						Y.one("#tablaCM tbody").append(html);

						// Asignar el event handler
						Y.one("#" + id)
						 .get('children')
						 .item(2)
						 .get('children')
						 .on('click', eliminarCM);

						// Agregar el valor a nuestro _input_hidden_
						if (lista[0].length == 0) {
							lista[0] = key + "_" + value;
						} else {
							lista.push(key + "_" + value);
						}
						
						// Y sumemos los valores y reemplazamos y clp
						var suma = parseInt(Y.one("#totalFactura").get("value"));
						if (isNaN(suma)) suma = 0;
						suma += parseInt(value);
						Y.one("#totalFactura").set("value", suma);
						Y.one("#totalFacturaTable").setHTML(Y.Number.format(suma, {thousandsSeparator: ".", prefix: "$ "}));
						Y.one("#listaCM").set("value", lista.join(":"));
					}

					// Dejar en blanco los input fields
					Y.all("#folioCuentaMedica").set("value", "");
					Y.all("#valorCuentaMedica").set("value", "");
					
				}
			}
		
		});
    	
		return false;
	}

	function eliminarCM (e) {
		var element = Y.one(e._currentTarget);

		// Obtengamos el valor para poder restarlo
		var old_value = parseInt(element
									.get('parentNode')
									.get('parentNode')
									.get('children')
									.item(1)
									.get('text')
									.replace('.', ''));
		
		// Remover el handler
		element.detach('click', eliminarCM);

		// Remover el elemento del documento
		var id = element.get('parentNode').get('parentNode').get("id").substr(4);
		element.get('parentNode').get('parentNode').remove();

		// Remover la referencia desde nuestro _input_hidden_
		var lista = Y.one("#listaCM").get("value").split(":");
		lista.splice(lista.indexOf(id), 1);
		Y.one("#listaCM").set("value", lista.join(":"));
		
		// Y cambiemos el valor total
		var suma = parseInt(Y.one("#totalFactura").get("value"));
		suma -= parseInt(old_value);		
		Y.one("#totalFacturaTable").setHTML(Y.Number.format(suma, {thousandsSeparator: ".", prefix: "$ "}));
		Y.one("#totalFactura").set("value", suma);
		
		return false;
	}

	function mensajeError (tipo, msg) {
		var bodyNode = Y.one(".workarea");
		bodyNode.append("<div class='pure-u-1 messages " + tipo + "'><ul><li>" + msg + "</li></ul></div>");
		
		return false;
	}
});
