
YUI().use('event', "node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page
	
	var myVar;
	
	var isNumber = function (input) {
		var RE = /^-{0,1}\d*\.{0,1}\d+$/;
	    return (RE.test(input));
	}
	
	
	var buscarSubGrupo = function (e) {
		
		var grupoId=Y.one("#grupo").get('value');
		
		Y.io('SubGruposJSON?grupo='+grupoId, {
    	    on : {
    	        success : function (tx, r) {
    	        	llenarSubGrupo(r.responseText)
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
    
    Y.one('#dv_aranceles_paquete tbody').delegate('click', function (e) {
    	confirmarEliminarPaquete(e.target);
        e.stopPropagation();
    }, 'input[type=checkbox]');
    
    
    var confirmarEliminarPaquete= function(checkbox){
    	var params = "arancelPaqueteId=" + checkbox.get('value');
    	var msg = "";
    	Y.io('getArancelesPaqueteGlosaById?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	msg += "¿Confirma que desea eliminar el arancel base?:\n";
    	        	msg += r.responseText
			    	if (confirm(msg)) {
			    		eliminarArancelPaquete(checkbox.get('value'));
			    	} else {
			    		checkbox.set('checked', false);
			    	}
    	        }
    	    }
    	});
    }    
    
    var eliminarArancelPaquete= function(arancelPaqueteId) {
    	var params = "arancelPaqueteId=" + arancelPaqueteId
    	var msg = "";  
    	Y.io('eliminarArancelPaquete?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	myVar = window.setInterval(function(){traerArancelesPaquete()},500);
    	        }
    	    }
    	});
    }    

    var btnFiltrar = Y.one("#btnFiltrarArancel");
    if (btnFiltrar != null) {
	    btnFiltrar.on('click', function (e) {
	        
	        this.get('id');      
	        e.target.get('id'); 
	        e.preventDefault();
	        e.stopPropagation();
	        
	        cargarArancelesBase();
	    });
    }

    var cargarArancelesBase = function (e) {
		var grupoId	= Y.one("#grupo").get('value');
		var subgrupo= Y.one("#subgrupo").get('value');
		var codigo	= Y.one("#codigoPrestacion").get('value');
		var params 	= 'grupo='+grupoId+'&subgrupo='+subgrupo+'&codigo='+codigo
		Y.io('cargarAranceles?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	desplegarAranceles(r.responseText)
    	        }
    	    }
    	});
	} 
    
    var desplegarAranceles= function(tableHtml) {
    	Y.one("#dv_aranceles_base tbody").setHTML(tableHtml);
    	if (tableHtml.indexOf("No existen prestaciones con el filtro seleccionado...") > -1 || 
    		tableHtml.indexOf("Seleccione un filtro para obtener los aranceles...") > -1 ||
    		tableHtml === "") {
    		document.getElementById('dv_cargos').style.display = "none";
    	} else {
    		document.getElementById('dv_cargos').style.display = "block";
    	}
    }

    var btnAgregar = Y.one("#btnAgregarArancel");
    if (btnAgregar != null) {
        btnAgregar.on('click', function (e) {
        	this.get('id'); 
            e.target.get('id'); 
            e.preventDefault();
            e.stopPropagation();
            agregarAranceles();
        });    
    }
    
    var agregarAranceles= function(){
    	var ch_aranceles = Y.all("#dv_aranceles_base tbody input[type=checkbox]:checked");
    	var hayError = false;
		var mensaje = "";
		if (ch_aranceles._nodes.length === 0) {
			alert("Dede seleccionar al menos una prestación.");
			return;
		}
		if (ch_aranceles._nodes.length > 512) {
			alert("No pueden seleccionar más de 512 prestaciones simultaneamente.");
			return;
		}
		ch_aranceles.each(function (checkbox) {
			if (!hayError) {
				try {
					agregarArancelPaquete(checkbox._node.value);
				} catch (msg) {
					hayError = true;
					mensaje = msg;
				}
			}
		});
		if (!hayError)
			myVar = window.setInterval(function(){traerArancelesPaquete()},500);
		else
			alert(mensaje);
    }    
    
    var agregarArancelPaquete= function(arancelBaseId) {
    	var paqueteId	= Y.one("#paqueteId").get('value');
    	var nivel		= Y.one("#nivel").get('value');
		var operacion	= Y.one("#operacion").get('value');
		var valor		= Y.one("#valor").get('value');
		var cantidad	= Y.one("#cantidad").get('value');
		var tipo		= Y.one("input[name=tipo]:checked");
		if (tipo != null) { tipo = tipo.get('value'); }
		
    	if (nivel == "") {
    		Y.one("#nivel").focus();
    		throw "Debe seleccionar nivel.";
    	} else if (operacion == "") {
    		Y.one("#operacion").focus();
    		throw "Debe seleccionar operación.";
    	} else if (cantidad == "" || isNaN(cantidad)) {
    		Y.one("#cantidad").focus();
    		throw "La cantidad ingresada debe ser númerica.";
		} else if (valor == "" || isNaN(valor)){
    		Y.one("#valor").focus();
    		throw "El valor ingresado debe ser númerico.";
    	} else if (tipo == null) {
    		Y.one("#tipo_0").focus();
    		throw "Debe seleccionar un tipo de descuento.";
		} else if (operacion == "Descuento" && tipo == "PRC" && valor > 100) {
    		Y.one("#valor").focus();
			throw "El valor ingresado no puede superar el 100%";
		}

		var params = 'paqueteId='+paqueteId+'&arancelBaseId='+arancelBaseId+'&nivel='+nivel
			params += '&operacion='+operacion+'&tipo='+tipo+'&valor='+valor+'&cantidad='+cantidad
    			    			
		Y.io('agregarArancelPaquete?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	Y.one("#nivel").set('value', null);
    	    		Y.one("#operacion").set('value', null);
    	    		Y.one("#valor").set('value', null);
    	    		Y.one("#cantidad").set('value', null);
    	    		Y.one("#tipo_0").set('checked', false);
    	    		Y.one("#tipo_1").set('checked', false);
    	        }
    	    }
    	});
    }    
    
    var traerArancelesPaquete= function(){
    	var paqueteId = Y.one("#paqueteId").get('value');
    	var params = "paqueteId=" + paqueteId
		Y.io('desplegarArancelesPaquete?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	Y.one("#dv_aranceles_paquete tbody").setHTML(r.responseText);
    	        }
    	    }
    	});    	
    	window.clearInterval(myVar);
    }   

	var addAllPrestaciones = function (e) {
    	Y.all("#dv_aranceles_base tbody input").each(function (checkbox) {
    	    checkbox.set("checked", e.target.get("checked"));
    	});
        e.stopPropagation();
	}
    
	var delAllPrestaciones = function (e) {
    	if (confirm("¿ Desea eliminar TODOS los aranceles asociados ?")) {
        	Y.all("#dv_aranceles_paquete tbody input[type=checkbox]").each(function (checkbox) {
            	Y.io("eliminarArancelPaquete?arancelPaqueteId=" + checkbox.get('value'), {
            	    on : {
            	        success : function (tx, r) { }
            	    }
            	});
        	});
        	var paqueteId = Y.one("#paqueteId").get('value');
        	var params = "paqueteId=" + paqueteId
    		Y.io('desplegarArancelesPaquete?' + params, {
        	    on : {
        	        success : function (tx, r) {
        	        	Y.one("#dv_aranceles_paquete tbody").setHTML(r.responseText);
        	        }
        	    }
        	});    	
    	}
		e.target.set("checked", false);
        e.stopPropagation();
	}

	if (Y.one("#grupo") != null) {
		Y.one("#grupo").on("change", buscarSubGrupo);
	}
	if (Y.one("#all_add_prestaciones")) {
		Y.one("#all_add_prestaciones").on("click", addAllPrestaciones);
	}
    if (Y.one("#all_del_prestaciones") != null) {
    	Y.one("#all_del_prestaciones").on("click", delAllPrestaciones);
    }
    
});