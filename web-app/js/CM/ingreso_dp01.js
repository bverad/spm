
YUI().use("node", "transition", "panel", "io", "dump", "json-parse", function (Y) {
	// Desactivar los campos
	Y.one("#centroSalud").set("disabled", true);

	Y.on("domready", function(){
		// Borrar _hidden_
		//Y.one("#listaOXA").set("value", "");
		var listaOXA = Y.one("#listaOXA").get('value');
		if (listaOXA.length > 0) {
			// Agregar la fila
			var loadOxa = listaOXA.split(':');
			if (loadOxa.length > 0) {
				Y.Array.each(loadOxa, function (o) {
					var key = o.split('_')[0];
					var value = o.split('_')[1];
					var id = "OXA_" + key + "_" + value;
					var html = '<tr class="OXA" id="'+ id + '">' +
									'<td>' + key + ' ' + value + '</td>' +
									'<td align="center"><a href="#" onclick="return false;" style="text-decoration: none;"><font color=red><i class="icon-trash icon-2x"></i></font></a></td>' +
								'</tr>'
					Y.one("#tablaOXA tbody").append(html);

					// Asignar el event handler
					Y.one("#" + id)
					 .get('children')
					 .item(1)
					 .get('children')
					 .on('click', eliminarOXA);
				});
			}
		}	
		// Asignar los handlers
		Y.one("#rutPrestador").on("change", obtenDatosPorRutPrestador);
		Y.one("#runTrabajador").on("change", obtenDatosPorRunTrabajador);
		Y.one("#opaNumero").on("change", borraOtrosOXAs);
		Y.one("#odaNumero").on("change", borraOtrosOXAs);
		Y.one("#opaepNumero").on("change", borraOtrosOXAs);
		Y.one("#btnAgregarOXA").on("click", agregarOXA);
		// Si es una recarga por errores, debemos obtener datos de prestador
		// y trabajador
		if (Y.one("#folioCuenta").get("value") !== "") {
			obtenDatosPorRutPrestador();
			obtenDatosPorRunTrabajador();
		}
		
		return false;
	});

	/**
	 * Event Handlers
	 */
	function obtenDatosPorRutPrestador () {
				
		// Quitamos mensajes de alerta
		if (Y.all(".obtenDatosPorRutPrestador")) Y.all(".obtenDatosPorRutPrestador").remove();
		
		var rutPrestador = Y.one("#rutPrestador").get("value");
		// Saquemos el guion internamente
		rutPrestador = rutPrestador.replace(/-/, '');
		
		Y.io("datosPrestadorJSON?rutPrestador=" + rutPrestador, {
			on : {
				success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
					
					if (data.razon_social) {
						Y.one("#nombrePrestador").set("value", data.razon_social);
						if (data.centrosSalud) {
							var centroSaludSelect = Y.one("#centroSalud");
							centroSaludSelect.get('childNodes').remove();
							
							var seleccioneElement='<option value="">Seleccione..</option>';
							centroSaludSelect.appendChild( Y.Node.create(seleccioneElement) );
							
							for (var i = 0; i < data.centrosSalud.length; i++) {
								var key     = data.centrosSalud[i].codigo;
								var value   = data.centrosSalud[i].descripcion;
								var element = '<option value="' + key + '">' + value + '</option>';
								centroSaludSelect.appendChild( Y.Node.create(element) );
							}
							Y.one("#centroSalud").removeAttribute("disabled");
						}
					} else {
						mensajeError("obtenDatosPorRutPrestador", "El RUT ingresado no existe");
					}
					return false;
				}
			}
		});
	}

	function obtenDatosPorRunTrabajador () {
		// Quitamos mensajes de alerta
		if (Y.one(".obtenDatosPorRunTrabajador")) Y.one(".obtenDatosPorRunTrabajador").remove();
		var runTrabajador = Y.one("#runTrabajador").get("value");
		// Saquemos el guion internamente
		runTrabajador = runTrabajador.replace(/-/, '');
		
		Y.io("datosTrabajadorJSON?runTrabajador=" + runTrabajador, {
			on : {
				success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
					if (data.nombre) {
						Y.one("#nombreTrabajador").set("value", data.nombre);
					} else {
						mensajeError("obtenDatosPorRunTrabajador", "El RUN ingresado no existe");
					}
					return false;
				}
			}
		});
	}

	function borraOtrosOXAs (e) {
		var callerName = Y.one(e._currentTarget).get("name");
		switch (callerName) {
		case "opaNumero":
			Y.one("#odaNumero").set("value", "");
			Y.one("#opaepNumero").set("value", "");
			break;
		case "odaNumero":
			Y.one("#opaNumero").set("value", "");
			Y.one("#opaepNumero").set("value", "");
			break;
		case "opaepNumero":
			Y.one("#opaNumero").set("value", "");
			Y.one("#odaNumero").set("value", "");
			break;
		}		
		return false;
	}

	function agregarOXA () {
		// Borramos mensajes de errores anteriores
		if (Y.one(".messages.agregarOXA")) Y.one(".messages.agregarOXA").remove();

		// Necesitamos ver los oxaNumero primero
		var opa   = Y.one("#opaNumero").get("value");
		var oda   = Y.one("#odaNumero").get("value");
		var opaep = Y.one("#opaepNumero").get("value");

		// Si estan los tres vacios, le informamos al usuario
		if (opa === "" && oda === "" && opaep === "") {
			mensajeError("agregarOXA", "Debe ingresar un valor en OPA, ODA o OPAEP!");
			return false;
		}

		// Busquemos cual es el item con valor.
		// Asumimos que solo uno de los tres tiene valor.
		var key, value;
		if (opa !== "") {
			key   = 'OPA';
			value = opa;
		} else if (oda !== "") {
			key   = 'ODA';
			value = oda;
		} else if (opaep !== ""){
			key   = 'OPAEP';
			value = opaep;
		}

		// Solo podemos agregar OxA cuyo valor sea solo numero
		var m = value.match(/[0-9]+/);
		if (m == null || value !== m[0]) {
			mensajeError("agregarOXA", "OPA/ODA/OPAEP solo puede llevar valor numerico!");
			return false;
		}

		// Ahora, veamos si no tenemos el valor ya.
		// Para eso, usemos nuestro _input_hidden_
		var lista = Y.one("#listaOXA").get("value").split(":");
		if (lista.indexOf(key + "_" + value.toString()) !== -1) {
			mensajeError("agregarOXA", "El valor de la " + key + " No " + value + " ya esta ingresado en la tabla!");
		} else if (lista.toString().indexOf(key) !== -1 && key !== "ODA") {
			mensajeError("agregarOXA", "Ya existe una OPA/OPAEP ingresada en la tabla!");
		} else if (key !== "ODA" && (lista.toString().indexOf("OPA") !== -1 || lista.toString().indexOf("OPAEP") !== -1)) {
			mensajeError("agregarOXA", "Ya existe una OPA/OPAEP ingresada en la tabla!");
		} else {
			// Agregar la fila
			var id = "OXA_" + key + "_" + value;
			var html = '<tr class="OXA" id="'+ id + '">' +
							'<td>' + key + ' ' + value + '</td>' +
							'<td align="center"><a href="#" onclick="return false;" style="text-decoration: none;"><font color=red><i class="icon-trash icon-2x"></i></font></a></td>' +
						'</tr>'
			Y.one("#tablaOXA tbody").append(html);

			// Asignar el event handler
			Y.one("#" + id)
			 .get('children')
			 .item(1)
			 .get('children')
			 .on('click', eliminarOXA);

			// Agregar el valor a nuestro _input_hidden_
			if (lista[0].length == 0) {
				lista[0] = key + "_" + value;
			} else {
				lista.push(key + "_" + value);
			}

			Y.one("#listaOXA").set("value", lista.join(":"));
		}

		// Dejar en blanco los input fields
		Y.all("#opaNumero").set("value", "");
		Y.all("#opaepNumero").set("value", "");
		Y.all("#odaNumero").set("value", "");
		
		return false;
	}

	function eliminarOXA (e) {
		var element = Y.one(e._currentTarget);

		// Remover el handler
		element.detach('click', eliminarOXA);

		// Remover el elemento del documento
		var id = element.get('parentNode').get('parentNode').get("id").substr(4);
		element.get('parentNode').get('parentNode').remove();

		// Remover la referencia desde nuestro _input_hidden_
		var lista = Y.one("#listaOXA").get("value").split(":");
		lista.splice(lista.indexOf(id), 1);
		Y.one("#listaOXA").set("value", lista.join(":"));

		return false;
	}

	function mensajeError (tipo, msg) {
		var bodyNode = Y.one(".workarea");
		var messageNode = Y.one(".messages ul");
		if (messageNode != null) {
			messageNode.append("<li class='" + tipo + "'>" + msg + "</li>");
		} else {
			bodyNode.append("<div class='pure-u-1 messages " + tipo + "'><ul><li>" + msg + "</li></ul></div>");
		}
		Y.one("#nombreTrabajador").set("value", null);

		return false;
	}
});
