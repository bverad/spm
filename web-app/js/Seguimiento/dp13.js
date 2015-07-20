YUI().use('event', "node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {
	function codigoOnChange() {
		var codigo = Y.one("#codigo").get("value");
		var centroAtencion = Y.one("#centroAtencion").get("value");
		// Quitamos mensajes de alerta
		if (Y.one(".messages.codigoOnChange")) Y.one(".messages.codigoOnChange").remove();
		// Validamos ingreso de informacion
		if (centroAtencion == null || centroAtencion == "") {
			mensajeError("codigoOnChange", "Debe seleccionar un centro de salud!");
		} else {
			Y.io("buscarPrestacionJSON?codigo=" + codigo + "&centroSalud=" + centroAtencion, {
				on : {
					success : function (tx, r) {
						var data = Y.JSON.parse(r.responseText);
						if (data != null) {
							Y.one("#glosa").setHTML(data.glosa);
							Y.one("#idArancelConvenio").set("value", data.id);
							Y.one("#tipoPrestacion").set("value", data.tipo);
							Y.one("#btnAgregarPrestacion").set("disabled", false);
						} else {
							mensajeError("codigoOnChange", "El código [" + codigo + "] no existe");
						}
						return false;
					}
				}
			});
		}
	}

	function mensajeError (tipo, msg) {
		var bodyNode = Y.one(".workarea");
		var messageNode = Y.one(".messages ul");
		if (messageNode != null) {
			messageNode.append("<li class='" + tipo + "'>" + msg + "</li>");
		} else {
			bodyNode.append("<div class='pure-u-1 messages " + tipo + "'><ul><li>" + msg + "</li></ul></div>");
		}
		Y.one("#codigo").set("value", null);
		Y.one("#glosa").setHTML("");
		Y.one("#cantidad").set("value", null);

		return false;
	}

	var buscarPrestadoresPorRegion = function (e) {
		var region = Y.one("#region").get('value');
		Y.io('prestadorPorRegionJSON?regionId='+region, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarPrestador(r.responseText);
    	        }
    	    }
    	});
	}
	
    var llenarPrestador = function(jsonString){
    	var centroAtencionSelect = Y.one("#centroAtencion");
    	var prestadorSelect 	 = Y.one("#prestador");
    	prestadorSelect.get('childNodes').remove();
    	centroAtencionSelect.set("disabled",true);
    	centroAtencionSelect.get('childNodes').remove();
    	
    	var data = Y.JSON.parse(jsonString);
    	for (var i = 0; i <= data.length - 1; i++) {
    	    var p = data[i];
    	    if (p.personaJuridica != null) {
        	    var n=p.personaJuridica.razonSocial;
        	    var oStr='<option value="'+p.id+'">'+n+'</option>';
        	    prestadorSelect.appendChild( Y.Node.create(oStr) );
    	    }
    	    if (p.personaNatural != null) {
        	    var n=p.personaNatural.nombre+" "+p.personaNatural.apellidoPaterno+" "+p.personaNatural.apellidoMaterno;
        	    var oStr='<option value="'+p.id+'">'+n+'</option>';
        	    prestadorSelect.appendChild( Y.Node.create(oStr) );
    	    }
    	}
    	if(data.length>0) {
    		prestadorSelect.set("disabled",false);
    		buscaCentroAtencionPorPrestador();
    	} else {
    		prestadorSelect.set("disabled",true);
    	}
	    data=null
    }

    var buscaCentroAtencionPorPrestador = function(e) {
    	var prestadorId=Y.one("#prestador").get('value');
    	var regionId=Y.one("#region").get('value');
    	Y.io('centroSaludPorPrestadorJSON?prestadorId='+prestadorId+"&regionId="+regionId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarCentroAtencion(r.responseText);
    	        }
    	    }
    	});
    	Y.io('buscarPrestadorJSON?prestadorId='+prestadorId, {
    	    on : {
    	        success : function (tx, r) {
    	        	var data = Y.JSON.parse(r.responseText);
    	        	Y.one("#direccionPrestador").setHTML(data.direccion);
    	        }
    	    }
    	});
    }
    
    var llenarCentroAtencion= function(jsonString){
    	var centroAtencionSelect=Y.one("#centroAtencion");
    	centroAtencionSelect.get('childNodes').remove();
    	
    	var data = Y.JSON.parse(jsonString);
    	for (var i = 0; i <= data.length - 1; i++) {
    	    var cs = data[i];
    	    var n=cs.nombre;
    	    var oStr='<option value="'+cs.id+'">'+n+'</option>';
    	    centroAtencionSelect.appendChild( Y.Node.create(oStr));
    	}
    	if(data.length>0){
    		centroAtencionSelect.set("disabled",false);
    	}else{
    		centroAtencionSelect.set("disabled",true);
    	}
	    data=null
    }

    function agregarPrestacion(e) {
        e.preventDefault();
		// Borramos mensajes de errores anteriores
		if (Y.one(".messages.agregarPrestacion")) Y.one(".messages.agregarPrestacion").remove();

		// Necesitamos ver los oxaNumero primero
		var codigo				= Y.one("#codigo").get("value");
		var glosa				= Y.one("#glosa").getHTML();
		var cantidad			= Y.one("#cantidad").get("value");
		var idArancelConvenio	= Y.one("#idArancelConvenio").get("value");
		var tipoPrestacion		= Y.one("#tipoPrestacion").get("value");

		// Si estan los tres vacios, le informamos al usuario
		if (codigo === "" && glosa === "" && cantidad === "") {
			mensajeError("agregarPrestacion", "Debe ingresar un valor en código, glosa y cantidad!");
			return false;
		}

		var m = cantidad.match(/[0-9]+/);
		if (m == null || cantidad !== m[0]) {
			mensajeError("agregarPrestacion", "La cantidad solo puede llevar valor numerico!");
			return false;
		}

		// Ahora, veamos si no tenemos el valor ya.
		// Para eso, usemos nuestro _input_hidden_
		var lista = Y.one("#listaPrestaciones").get("value").split(":");
		
		if (Y.one("#listaPrestaciones").get("value").indexOf(codigo + "_") !== -1) {
			mensajeError("agregarPrestacion", "La prestación " + codigo + " ya está ingresada en la tabla!");
		} else {
			// Se elimina la fila que indica ausencia de datos
			var sinDatos = Y.one("#sinDatos");
			if (sinDatos != null) {
				sinDatos.get('parentNode').removeChild(sinDatos);
			}
			// Agregar la fila
			var id = idArancelConvenio + "_" + codigo + "_" + cantidad + "_" + tipoPrestacion;
			var html = '<tr class="OXA" id="'+ id + '">' +
							'<td align="center">' + codigo + '</td>' +
							'<td title="' + glosa + '">' + glosa.substring(0,70) + '...' + '</td>' +
							'<td align="right">' + cantidad + '</td>' +
							'<td align="center"><a href="#" onclick="return false;" style="text-decoration: none;"><font color=red><i class="icon-trash icon-2x"></i></font></a></td>' +
						'</tr>'
			Y.one("#prestacionesTable tbody").append(html);

			// Asignar el event handler
			Y.all("#prestacionesTable a").on('click', eliminarPrestacion);

			// Agregar el valor a nuestro _input_hidden_
			if (lista[0].length == 0) {
				lista[0] = id;
			} else {
				lista.push(id);
			}

			Y.one("#listaPrestaciones").set("value", lista.join(":"));
			// Se inicializan los ingresos
			Y.one("#codigo").set("value", null);
			Y.one("#glosa").setHTML("");
			Y.one("#cantidad").set("value", null);
			Y.one("#idArancelConvenio").set("value", null);
			Y.one("#tipoPrestacion").set("value", null);
			// Deshabilita el botón agregar
			Y.one("#btnAgregarPrestacion").set("disabled", true);
		}

		// Dejar en blanco los input fields
		Y.all("#opaNumero").set("value", "");
		Y.all("#opaepNumero").set("value", "");
		Y.all("#odaNumero").set("value", "");
		
		return false;
    }

	function eliminarPrestacion(e) {
		var element = Y.one(e._currentTarget);

		// Remover el handler
		element.detach('click', eliminarPrestacion);

		// Remover el elemento del documento
		var id = element.get('parentNode').get('parentNode').get("id");
		element.get('parentNode').get('parentNode').remove();

		// Remover la referencia desde nuestro _input_hidden_
		var lista = Y.one("#listaPrestaciones").get("value").split(":");
		lista.splice(lista.indexOf(id), 1);
		Y.one("#listaPrestaciones").set("value", lista.join(":"));

		return false;
	}

	Y.on("domready", function(){
		buscarPrestadoresPorRegion();
		var listaPrestaciones = Y.one("#listaPrestaciones").get('value');
		if (listaPrestaciones.length > 0) {
			// Agregar la fila
			var lista = listaPrestaciones.split(':');
			if (lista.length > 0) {
				Y.Array.each(lista, function (o) {
					var idArancelConvenio	= o.split('_')[0];
					var codigo				= o.split('_')[1];
					var cantidad			= o.split('_')[2];
					var tipoPrestacion		= o.split('_')[3];
					var centroAtencion		= Y.one("#centroAtencion").get("value");
					
					Y.io("buscarPrestacionJSON?codigo=" + codigo + "&centroSalud=" + centroAtencion, {
						on : {
							success : function (tx, r) {
								var data = Y.JSON.parse(r.responseText);
								if (data != null) {
									// Se elimina la fila que indica ausencia de datos
									var sinDatos = Y.one("#sinDatos");
									if (sinDatos != null) {
										sinDatos.get('parentNode').removeChild(sinDatos);
									}
									var id = idArancelConvenio + "_" + codigo + "_" + cantidad + "_" + tipoPrestacion;
									var html = '<tr class="OXA" id="'+ id + '">' +
													'<td align="center">' + codigo + '</td>' +
													'<td title="' + glosa + '">' + glosa.substring(0,70) + '...' + '</td>' +
													'<td align="right">' + cantidad + '</td>' +
													'<td align="center"><a href="#" onclick="return false;" style="text-decoration: none;"><font color=red><i class="icon-trash icon-2x"></i></font></a></td>' +
												'</tr>'
									Y.one("#prestacionesTable tbody").append(html);
								} else {
									mensajeError("codigoOnChange", "El código [" + codigo + "] no existe");
								}
								return false;
							}
						}
					});
					// Asignar el event handler
					Y.all("#prestacionesTable a").on('click', eliminarPrestacion);
				});
			}
		}	
		return false;
	});
	
	function tipoODAOnChange(e) {
		var tipoODA = Y.one(e._currentTarget).get("value");
		if ((tipoODA != null) && ((tipoODA == 1) || (tipoODA == 5)))
		{
			Y.one('#atenciones').show();
		}
		else
		{
			Y.one('#atenciones').hide();
		}
	}
	
	function tipoODAOnLoad() {
		var tipoODA = Y.one("#tipoODA").get("value");
		if ((tipoODA != null) && ((tipoODA == 1) || (tipoODA == 5)))
		{
			Y.one('#atenciones').show();
		}
		else
		{
			Y.one('#atenciones').hide();
		}
	}
	
	var prestador = Y.one("#prestador").get("value")
    Y.one("#prestador").set("disabled", (prestador == null || prestador == ""));
	var centroAtencion
	if (Y.one("#centroAtencion") != null) {
		centroAtencion = Y.one("#centroAtencion").get("value")
	}
    Y.one("#centroAtencion").set("disabled", (centroAtencion == null || centroAtencion == ""));
    Y.one("#region").on("change", buscarPrestadoresPorRegion);
    Y.one("#prestador").on("change", buscaCentroAtencionPorPrestador);
    Y.one("#grupoIntencionalidad").on("change", buscaIntencionalidad);
    
    
    Y.one("#codigo").on("change", codigoOnChange);
    Y.one("#btnAgregarPrestacion").on("click", agregarPrestacion);
    
    Y.one("#tipoODA").on("change", tipoODAOnChange);
	Y.on("available", tipoODAOnLoad, "#tipoODA");
});