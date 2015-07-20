
YUI().use('event', "node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page
	
	var myVar;
	
	var rowClean = 	"<tr> <td colspan=\"6\"> Seleccione un filtro para obtener los aranceles... </td> </tr>";
	
	var buscarSubGrupo = function (e) {
		
		var grupoId=Y.one("#grupo").get('value');
		document.getElementById('dv_cargos').style.display = "none";
		
		Y.io('SubGruposJSON?grupo='+grupoId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarSubGrupo(r.responseText)
    	        	desplegarAranceles(rowClean);
    	        }
    	    }
    	});
		
	} 
    
    var llenarSubGrupo= function(jsonString){
    	
    	var subGrupoSelect=Y.one("#subgrupo")
    	subGrupoSelect.get('childNodes').remove();
    	
    	try {
    	    var data = Y.JSON.parse(jsonString);
    	}
    	catch (e) {
    	    alert("Error al recibir la lista de Sub Grupos.");
    	    return;
    	}
    	
    	if (data.length > 0) {
    		var oStr='<option value="">Seleccionar ...</option>'
    		subGrupoSelect.appendChild( Y.Node.create(oStr))
    	}
    	
    	for (var i = 0; i < data.length; i++) {
    	    var p = data[i];
    	    var n=p.descripcion	
    	    oStr='<option value="'+p.codigo+'">'+n+'</option>'
    	    subGrupoSelect.appendChild( Y.Node.create(oStr))
    	}
    	if(data.length>0){
    		subGrupoSelect.set("disabled",false)
    	}else{
    		subGrupoSelect.set("disabled",true)
    	}
	    data=null
    }
    
    var cargarArancelesBase = function (e) {
		var grupoId=Y.one("#grupo").get('value');
		var subgrupo=Y.one("#subgrupo").get('value');
		var codigo=Y.one("#codigo").get('value');
	
		var params = 'grupo='+grupoId+'&subgrupo='+subgrupo+'&codigo='+codigo
		
		Y.io('cargarAranceles?'+params, {
    	    on : {
    	        success : function (tx, r) {
    	        	desplegarAranceles(r.responseText)
    	        }
    	    }
    	});
	} 
    
    var desplegarAranceles= function(tableHtml){
    	if (tableHtml.indexOf("No existen prestaciones con el filtro seleccionado...") > -1 || 
    		tableHtml.indexOf("Seleccione un filtro para obtener los aranceles...") > -1 ||
    		tableHtml === "")
    		document.getElementById('dv_cargos').style.display = "none";
    	else
    		document.getElementById('dv_cargos').style.display = "block";
    	Y.one("#dv_aranceles_base tbody").setHTML(tableHtml);
    }
    
    var btnFiltrar = Y.one("#btnFiltrarArancel");
    btnFiltrar.on('click', function (e) {
        
        this.get('id');      
        e.target.get('id'); 
        e.preventDefault();
        e.stopPropagation();
        
        cargarArancelesBase();
    });  
    
    var btnAgregar = Y.one("#btnAgregarArancel");
    btnAgregar.on('click', function (e) {

    	this.get('id'); 
        e.target.get('id'); 
        e.preventDefault();
        e.stopPropagation();
        
        agregarAranceles();
    });
    
    var agregarAranceles= function(){
    	var hayError = false;
		var mensaje = "";
		try {
	    	var ch_aranceles = Y.all("#dv_aranceles_base tbody input[type=checkbox]:checked");
	    	var convenioId	 = Y.one("#convenioId").get('value');
	    	var nivel		 = Y.one("#nivel").get('value');
			var operacion	 = Y.one("#operacion").get('value');
			var valor		 = Y.one("#valor").get('value');
			var tipo		 = Y.one("input[name=tipo]:checked");
    		var fechaArancel = Y.one("#fechaArancel").get("parentNode").get("children").item(0).get('value');
    		var fechaArancelDay = Y.one("#fechaArancel_day").get("value");
    		var fechaArancelMonth = Y.one("#fechaArancel_month").get("value");
    		var fechaArancelYear = Y.one("#fechaArancel_year").get("value");
    		
    		var fechaArancel = fechaArancelDay+"-"+fechaArancelMonth+"-"+fechaArancelYear
    		
    		if (tipo != null) { tipo = tipo.get('value'); }

			if (ch_aranceles._nodes.length === 0) {
	    		throw "Debe seleccionar al menos una prestación.";
			}
			if (ch_aranceles._nodes.length > 512) {
				throw "No pueden seleccionar más de 512 prestaciones simultaneamente.";
			}
	    	if (nivel == "") {
	    		Y.one("#nivel").focus();
	    		throw "Debe seleccionar nivel.";
	    	} else if (operacion == "") {
	    		Y.one("#operacion").focus();
	    		throw "Debe seleccionar operación.";
	    	} else if (valor == "" || isNaN(valor)){
	    		Y.one("#valor").focus();
	    		throw "El valor ingresado debe ser númerico.";
	    	} else if (valor < 0){
	    		Y.one("#valor").focus();
	    		throw "El valor ingresado debe ser mayor o igual a 0";
	    	} else if (tipo == null) {
	    		Y.one("#tipo_0").focus();
	    		if (operacion == "Descuento") {
	    			throw "Debe seleccionar un tipo de descuento.";
	    		} else {
	    			throw "Debe seleccionar un tipo de cargo.";
	    		}
			} else if (operacion == "Descuento" && tipo == "PRC" && valor > 100) {
	    		Y.one("#valor").focus();
				throw "El valor ingresado no puede superar el 100%";
			} else if (fechaArancel == "--") {
				Y.one("#fechaArancel").get("parentNode").get("children").item(0).focus();
	    		throw "Debe ingresar una fecha";
	    	}
	    	
			if (confirm("¿Está seguro que desea guardar este arancel?")) {
				ch_aranceles.each(function (checkbox) {
					if (!hayError) {
						try {
							var esPaquete = checkbox._node.className;
							var arancelBaseId = checkbox._node.value;
							//agregarArancelConvenio(checkbox._node.value, esPaquete);
							var params =  'convenioId='+convenioId+'&arancelBaseId='+arancelBaseId+'&nivel='+nivel
								params += '&operacion='+operacion+'&tipo='+tipo+'&valor='+valor+'&esPaquete='+esPaquete+'&fechaArancel='+fechaArancel

							Y.io('agregarArancelConvenio?' + params, {
					    	    on : {
					    	        success : function (tx, r) {
										var data = Y.JSON.parse(r.responseText);
					    	        	if (!data.status) {
					    	        		if (!hayError) {
						    	        		alert( data.mensaje );
					    	        		}
					    	        		hayError = true;
					    	        	} else {
						    	        	Y.one("#nivel").set('value', null);
						    	    		Y.one("#operacion").set('value', null);
						    	    		Y.one("#valor").set('value', null);
						    	    		Y.one("#tipo_0").set('checked', false);
						    	    		Y.one("#tipo_1").set('checked', false);
						    	    		Y.one("#fechaArancel").get("parentNode").get("children").item(0).set('value', null);
					    	        	}
					    	        }
					    	    }
					    	});
						} catch (msg) {
							hayError = true;
							mensaje = msg;
						}
					}
				});
			}
		} catch (msg) {
			hayError = true;
			mensaje = msg;
		}
		if (!hayError)
			myVar = window.setInterval(function(){traerArancelesConvenio()},500);
		else
			alert(mensaje);
    }
    
    var agregarArancelConvenio= function(arancelBaseId, esPaquete) {
    	var convenioId	 = Y.one("#convenioId").get('value');
    	var nivel		 = Y.one("#nivel").get('value');
		var operacion	 = Y.one("#operacion").get('value');
		var valor		 = Y.one("#valor").get('value');
		var tipo		 = Y.one("input[name=tipo]:checked");
		var dia = Y.one("#fechaArancel_day").get('value');
		var mes = Y.one("#fechaArancel_month").get('value');
		var ano = Y.one("#fechaArancel_year").get('value');
		if (tipo != null) { tipo = tipo.get('value'); }
		var fechaArancel = dia + "-" + mes + "-" + ano;

		var params = 'convenioId='+convenioId+'&arancelBaseId='+arancelBaseId+'&nivel='+nivel
			params += '&operacion='+operacion+'&tipo='+tipo+'&valor='+valor+'&esPaquete='+esPaquete+'&fechaArancel='+fechaArancel

		Y.io('agregarArancelConvenio?' + params, {
    	    on : {
    	        success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
    	        	if (!data.status) {
    	        		throw data.mensaje ;
    	        	} else {
	    	        	Y.one("#nivel").set('value', null);
	    	    		Y.one("#operacion").set('value', null);
	    	    		Y.one("#valor").set('value', null);
	    	    		Y.one("#tipo_0").set('checked', false);
	    	    		Y.one("#tipo_1").set('checked', false);
	    	    		Y.one("#fechaArancel").get("parentNode").get("children").item(0).set('value', null);
    	        	}
    	        }
    	    }
    	});
    }

    var traerArancelesConvenio= function(){
    	var convenioId = Y.one("#convenioId").get('value');
    	var params = "convenioId=" + convenioId
		Y.io('desplegarArancelesConvenio?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	Y.one("#dv_aranceles_convenio tbody").setHTML(r.responseText);
    	        }
    	    }
    	});    	
    	window.clearInterval(myVar);
    }
    
    Y.one('#dv_aranceles_convenio tbody').delegate('click', function (e) {
    	confirmarEliminarArancel(e.target);
        e.stopPropagation();
    }, 'input[type=checkbox]');
    
    
    var confirmarEliminarArancel= function(checkbox){
    	var params = "arancelConvenioId=" + checkbox.get("value")
    	var msg = "";
 
    	Y.io('getArancelesConvenioGlosaById?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	msg += "¿Confirma que desea eliminar el arancel base?:\n";
    	        	msg += r.responseText
			    	if (confirm(msg)) {
			    		eliminarArancelConvenio(checkbox.get("value"));
			    	} else {
			    		checkbox.set("checked", false);
			    	}
    	        }
    	    }
    	});
    }
    
    var eliminarArancelConvenio= function(arancelConvenioId) {
    	var params = "arancelConvenioId=" + arancelConvenioId
    	var msg = "";  
    	Y.io('eliminarArancelConvenio?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	myVar = window.setInterval(function(){traerArancelesConvenio()},500);
    	        }
    	    }
    	});
    }
    
	var addAllPrestaciones = function (e) {
    	Y.all("#dv_aranceles_base tbody input").each(function (checkbox) {
    	    checkbox.set("checked", e.target.get("checked"));
    	});
        e.stopPropagation();
	}
    
	var delAllPrestaciones = function (e) {
    	var convenioId = Y.one("#convenioId").get('value');
    	if (confirm("¿ Desea eliminar TODOS los aranceles asociados ?")) {
        	Y.all("#dv_aranceles_convenio tbody input[type=checkbox]").each(function (checkbox) {
            	Y.io("eliminarArancelConvenio?arancelConvenioId=" + checkbox.get('value'), {
            	    on : {
            	        success : function (tx, r) { }
            	    }
            	});
        	});
    		Y.io("desplegarArancelesConvenio?convenioId=" + convenioId, {
        	    on : {
        	        success : function (tx, r) {
        	        	Y.one("#dv_aranceles_convenio tbody").setHTML(r.responseText);
        	        }
        	    }
        	});    	
    	}
		e.target.set("checked", false);
        e.stopPropagation();
	}

	Y.one("#subgrupo").set("disabled",true)   
    Y.one("#grupo").on("change", buscarSubGrupo);	
    Y.one("#all_add_prestaciones").on("click", addAllPrestaciones);
    Y.one("#all_del_prestaciones").on("click", delAllPrestaciones);
});