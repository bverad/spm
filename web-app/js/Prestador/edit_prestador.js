   /*
   * Vamos a realizar las validaciones en los formularios
   */
YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){
		// Asignar los handlers
		Y.one("#esPersonaJuridica_0").on("click", tipoPersonaOnClick);
		Y.one("#esPersonaJuridica_1").on("click", tipoPersonaOnClick);
		
		//Y.one("#rut").on("change", obtenPersonaJuridicaPorRUT);
		//Y.one("#run").on("change", obtenPersonaNaturalPorRUN);
		Y.one("#runRL").on("change", findRepresentanteLegalByRun);
		Y.one("#runAP").on("change", findApoderadoByRun);
		// Si es una recarga por errores, debemos obtener datos de prestador y trabajador
		if (Y.one("#estructuraJuridica").get("value") !== "") {
			tipoPersonaOnClick();
		}
	});

  	function tipoPersonaOnClick () {
		var esPersonaJuridica = Y.one("[name=esPersonaJuridica]:checked").get("value");
		if (esPersonaJuridica == "true") {
			Y.one("#rut")			.setStyle("display", "");
			Y.one("#run")			.setStyle("display", "none");
			Y.one("#razonSocial")	.setStyle("display", "");
			Y.one("#nombre")		.setStyle("display", "none");
			Y.one("#apePaterno")	.setStyle("display", "none");
			Y.one("#apeMaterno")	.setStyle("display", "none");
			Y.one("#rut").get("parentNode")			.setStyle("display", "");
			Y.one("#run").get("parentNode")			.setStyle("display", "none");
			Y.one("#razonSocial").get("parentNode")	.setStyle("display", "");
			Y.one("#nombre").get("parentNode")		.setStyle("display", "none");
			Y.one("#apePaterno").get("parentNode")	.setStyle("display", "none");
			Y.one("#apeMaterno").get("parentNode")	.setStyle("display", "none");
			Y.one("#run")			.set("value", " ");
			Y.one("#nombre")		.set("value", " ");
			Y.one("#apePaterno")	.set("value", " ");
			Y.one("#apeMaterno")	.set("value", " ");
		} else {
			Y.one("#rut")			.setStyle("display", "none");
			Y.one("#run")			.setStyle("display", "");
			Y.one("#razonSocial")	.setStyle("display", "none");
			Y.one("#nombre")		.setStyle("display", "");
			Y.one("#apePaterno")	.setStyle("display", "");
			Y.one("#apeMaterno")	.setStyle("display", "");
			Y.one("#rut").get("parentNode")			.setStyle("display", "none");
			Y.one("#run").get("parentNode")			.setStyle("display", "");
			Y.one("#razonSocial").get("parentNode")	.setStyle("display", "none");
			Y.one("#nombre").get("parentNode")		.setStyle("display", "");
			Y.one("#apePaterno").get("parentNode")	.setStyle("display", "");
			Y.one("#apeMaterno").get("parentNode")	.setStyle("display", "");
			Y.one("#rut")			.set("value", " ");
			Y.one("#razonSocial")	.set("value", " ");
		}
		Y.one("#rut")			.setStyle("width", "100%");
		Y.one("#run")			.setStyle("width", "100%");
		Y.one("#razonSocial")	.setStyle("width", "100%");
		Y.one("#nombre")		.setStyle("width", "100%");
		Y.one("#apePaterno")	.setStyle("width", "100%");
		Y.one("#apeMaterno")	.setStyle("width", "100%");
	}
	
  	function findApoderadoByRun(e){
  		var run =  Y.one(e._currentTarget).get("value");
		var id  =  Y.one(e._currentTarget).get("id");
		var idPrestador = Y.one("#id").get("value"); 
		
		// Saquemos el guion internamente
		run = run.replace(/\,|\.|\-/g, '');

		Y.io("datosApoderadoJSON?run=" + run  + "&id=" + idPrestador, {
			on : {
				success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
					var elements = document.getElementsByTagName('input')			
					var desdeAP = elements[32].id; //se busca componente por tag ya que el widget calendar genera ids automaticos
					var hastaAP = elements[37].id; //se busca componente por tag ya que el widget calendar genera ids automaticos
					if(!data.isEmpty){
						Y.one("#nombreAP").set("value", data.apoderado["nombre"]);
						Y.one("#paternoAP").set("value", data.apoderado["apellidoPaterno"]);
						Y.one("#maternoAP").set("value", data.apoderado["apellidoMaterno"]);
						Y.one("#" + desdeAP).set("value", data["desdeAP"]);
						Y.one("#" + hastaAP).set("value", data["hastaAP"]);					
					}else{
						Y.one("#nombreAP").set("value", "");
						Y.one("#paternoAP").set("value", "");
						Y.one("#maternoAP").set("value", "");
						Y.one("#" + desdeAP).set("value", "");
						Y.one("#desdeAP_day").set("value","");
						Y.one("#desdeAP_month").set("value","");
						Y.one("#desdeAP_year").set("value","");
						Y.one("#" + hastaAP).set("value", "");
						Y.one("#hastaAP_day").set("value","");
						Y.one("#hastaAP_month").set("value","");
						Y.one("#hastaAP_year").set("value","");
						
						
						if(run != "")
							alert("El run " + run + " indicado para el apoderado no existe" );

					}
				}
			}
		});
  	}
  	
  	function findRepresentanteLegalByRun(e){
  		var run =  Y.one(e._currentTarget).get("value");
		var id  =  Y.one(e._currentTarget).get("id");
		var idPrestador = Y.one("#id").get("value")
		// Saquemos el guion internamente
		run = run.replace(/\,|\.|\-/g, '');

		Y.io("datosRepresentanteLegalJSON?run=" + run + "&id=" + idPrestador, {
			on : {
				success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
					var elements = document.getElementsByTagName('input')
					var desdeRL = elements[18].id; //se busca componente por tag ya que el widget calendar genera ids automaticos
					var hastaRL = elements[23].id; //se busca componente por tag ya que el widget calendar genera ids automaticos
					
					if(!data.isEmpty){
						Y.one("#nombreRL").set("value", data.representanteLegal["nombre"]);
						Y.one("#paternoRL").set("value", data.representanteLegal["apellidoPaterno"]);
						Y.one("#maternoRL").set("value", data.representanteLegal["apellidoMaterno"]);
						Y.one("#" + desdeRL).set("value", data["desdeRL"]);
						Y.one("#" + hastaRL).set("value", data["hastaRL"]);					
					}else{
						Y.one("#nombreRL").set("value", "");
						Y.one("#paternoRL").set("value", "");
						Y.one("#maternoRL").set("value", "");
						Y.one("#" + desdeRL).set("value", "");
						Y.one("#desdeRL_day").set("value","");
						Y.one("#desdeRL_month").set("value","");
						Y.one("#desdeRL_year").set("value","");
						Y.one("#" + hastaRL).set("value", "");
						Y.one("#hastaRL_day").set("value","");
						Y.one("#hastaRL_month").set("value","");
						Y.one("#hastaRL_year").set("value","");
						
						if(run != "")
							alert("El run " + run + " indicado para el representante legal no existe" );
					}
				}
			}
		});
  	}
  	
  	
	function obtenPersonaNaturalPorRUN (e) {
		var run =  Y.one(e._currentTarget).get("value");
		var id  =  Y.one(e._currentTarget).get("id");
		// Saquemos el guion internamente
		run = run.replace(/\,|\.|\-/g, '');

		Y.io("datosPersonaNaturalJSON?run=" + run, {
			on : {
				success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
					if (id === "run") {
						if (data != null) {
							Y.one("#nombre").set("value", data.nombre);
							Y.one("#apePaterno").set("value", data.apellidoPaterno);
							Y.one("#apeMaterno").set("value", data.apellidoMaterno);
						} else {
							Y.one("#nombre").set("value", null);
							Y.one("#apePaterno").set("value", null);
							Y.one("#apeMaterno").set("value", null);
						}
					} else if (id === "runRL") {
						if (data != null) {
							Y.one("#nombreRL").set("value", data.nombre);
							Y.one("#paternoRL").set("value", data.apellidoPaterno);
							Y.one("#maternoRL").set("value", data.apellidoMaterno);
						} else {
							Y.one("#nombreRL").set("value", null);
							Y.one("#paternoRL").set("value", null);
							Y.one("#maternoRL").set("value", null);
						}
					} else if (id === "runAP") {
						if (data != null) {
							Y.one("#nombreAP").set("value", data.nombre);
							Y.one("#paternoAP").set("value", data.apellidoPaterno);
							Y.one("#maternoAP").set("value", data.apellidoMaterno);
						} else {
							Y.one("#nombreAP").set("value", null);
							Y.one("#paternoAP").set("value", null);
							Y.one("#maternoAP").set("value", null);
						}
					}
					return false;
				}
			}
		});
	}
	
	function obtenPersonaJuridicaPorRUT (e) {
		var rut =  Y.one(e._currentTarget).get("value");
		// Saquemos el guion internamente
		rut = rut.replace(/\,|\.|\-/g, '');

		Y.io("datosPersonaJuridicaJSON?rut=" + rut, {
			on : {
				success : function (tx, r) {
					var data = Y.JSON.parse(r.responseText);
					if (data.razonSocial) { Y.one("#razonSocial").set("value", data.razonSocial); }
					return false;
				}
			}
		});
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




/*
 * Vamos a realizar las validaciones en los formularios
 */
		/*	
    YUI().use('event', 'node', function (Y) {
			
          	var btnActualizar = Y.one(document.getElementsByName('_action_update')[0]);
          	
	    	btnActualizar.on('click', function (e) {
	        	var msg = "";
		        this.get('id');      
		        e.target.get('id'); 

		       	        
		        msg += validarPrestador();
		        msg += validarRepresentanteLegal();
		        msg += validarApoderado();
		        
		        if (msg != "") {
		        	document.getElementById("js_msg").innerHTML = "<div class=\"pure-u-1 messages\"><ul>" + msg + "</ul></div>";
			        e.preventDefault();
				    e.stopPropagation();		        	
		        } else {
		        	document.getElementById("js_msg").innerHTML = "";
		        }
		        
	    });
	    
	    
	    var validarPrestador = function() {
	    	var msgError = "";
	    	var rut = Y.one(document.getElementsByName('run')[0]);
	    	
	    	var rutPrestador = rut.get("value");
	    	
	    	if (rutPrestador.length == 0) {
	    		msgError += "<li>Error, el rut o run del prestador no puede estar vacio.</li>";
	    	} else {
	    		if (validaRut(rutPrestador) == false) {
	    			msgError += "<li>Error, el rut o run del prestador es invalido, por favor corregir.</li>";
	    		}
	    	}
	    	
	    	return msgError;
	    }
	    
	    var validarRepresentanteLegal = function() {
	    	var msgError = "";
	    	var rut = Y.one(document.getElementsByName('runRL')[0]);
	    	var obj
	    	var rutRL = rut.get("value").trim();
	    	
	    	obj = Y.one(document.getElementsByName('nombreRL')[0]);
	    	nombreRL = obj.get("value").trim();
	    	
	    	obj = Y.one(document.getElementsByName('paternoRL')[0]);
	    	paternoRL  = obj.get("value").trim();
	    	
	    	if (rutRL.length > 0 || nombreRL.length > 0 || paternoRL > 0) {
	    		if (!validaRut(rutRL)) {
	    			msgError += "<li>Error, el run del Representante Legal es invalido, por favor corregir.</li>";
	    		}
	    		if (nombreRL.length == 0) {
	    			msgError += "<li>Error, el nombre del Representante Legal no puede ir vacio.</li>";
	    		}
	    		if (paternoRL.length == 0) {
	    			msgError += "<li>Error, el apellido paterno del Representante Legal no puede ir vacio.</li>";
	    		}
	    	}
	    	
	    	return msgError;
	    }
	    
	    var validarApoderado = function() {
	    	var msgError = "";
	    	var rut = Y.one(document.getElementsByName('runAP')[0]);
	    	var obj
	    	var rutAP = rut.get("value").trim();
	    	
	    	obj = Y.one(document.getElementsByName('nombreAP')[0]);
	    	nombreAP = obj.get("value").trim();
	    	
	    	obj = Y.one(document.getElementsByName('paternoAP')[0]);
	    	paternoAP  = obj.get("value").trim();
	    	
	    	if (rutAP.length > 0 || nombreAP.length > 0 || paternoAP.length > 0) {
	    		if (!validaRut(rutAP)) {
	    			msgError += "<li>Error, el run del apoderado es invalido, por favor corregir.</li>";
	    		}
	    		if (nombreAP.length == 0) {
	    			msgError += "<li>Error, el nombre del apoderado no puede ir vacio.</li>";
	    		}
	    		if (paternoAP.length == 0) {
	    			msgError += "<li>Error, el apellido paterno del apoderado no puede ir vacio.</li>";
	    		}
	    		if (desdeAP.length == 0) {
	    			msgError += "<li>Error, la fecha vigencia desde del apoderado no puede ir vacia.</li>";
	    		}
	    		
	    	}
	    	
	    	return msgError;

	    }
	    
	    var validaRut = function(rut) {
	    	tmpstr = "";
	    	rut = rut.replace(/\./g,"");
	    	
			crut = rut
			largo = crut.length;
			if ( largo <2 )
			{
				return false;
			}
	    	
			for ( i=0; i < crut.length ; i++ )
			if ( crut.charAt(i) != ' ' && crut.charAt(i) != '.' && crut.charAt(i) != '-' )
			{
				tmpstr += crut.charAt(i);
			}
	    	
			rut = tmpstr;
			crut=tmpstr;
			largo = crut.length;
	    		
			if ( largo > 2 )
				rut = crut.substring(0, largo - 1);
			else
				rut = crut.charAt(0);
		
			dv = crut.charAt(largo-1);
	
			if ( rut == null || dv == null )
				return false;
	
			var dvr = '0';
			suma = 0;
			mul  = 2;
	
			for (i= rut.length-1 ; i>= 0; i--)
			{
				suma = suma + rut.charAt(i) * mul;
				if (mul == 7)
					mul = 2;
				else
					mul++;
			}
	
			res = suma % 11;
			if (res==1)
				dvr = 'k';
			else if (res==0)
				dvr = '0';
			else
			{
				dvi = 11-res;
				dvr = dvi + "";
			}
	
			if ( dvr != dv.toLowerCase() )
			{
				return false;
			}
			return true;
	    };
	    
 
      });
 */
