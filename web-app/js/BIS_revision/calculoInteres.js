/**
 * Llena los combos con los correspondiente valores
 */

YUI().use("io", "json-parse", "node", "panel", function (Y) {

	Y.on("domready", function(){		
		//Llama a la funcion
		Y.one("#fechaPago").on("change", calculaInteres);
		Y.one("#ufPago").on("change", calculaInteres);
		Y.one("#tasaInteres").on("change", calculaInteres);	
	});
	
	function calculaInteres() {
		var fechaRecepcion = document.getElementById("recepcionCarta").value;
		var fechaPago = document.getElementById("fechaPago").value;
		var ufPago = document.getElementById("ufPago").value;
		var tasaInteres = document.getElementById("tasaInteres").value;
		var interesDiario = document.getElementById("interesDiario").value;
		var dias = document.getElementById("dias").value;
		var aprobado = document.getElementById("aprobado").value;
		var montoPrestacion = document.getElementById("montoPrestacion").value;
		var interesPeriodo = document.getElementById("interesPeriodo").value;
		var interesUf = document.getElementById("interesUf").value;
		var totalUf = document.getElementById("totalUf").value;
		var totalInteres = document.getElementById("totalInteres").value;
		var totalPrestaciones = document.getElementById("totalPrestaciones").value;
		
		if (fechaPago){
			var sFR=fechaRecepcion.split("-");
			sFR[2]=sFR[2].split(" ")[0];
			var dFR=new Date(sFR[0]+"-"+sFR[1]+"-"+sFR[2]);		
			var sFP=fechaPago.split("-");
			var dFP=new Date(sFP[2]+"-"+sFP[1]+"-"+sFP[0]);			
			var fechaResta = (dFP.getTime() - dFR.getTime());
								
		    fechaResta=(((fechaResta/1000)/60)/60)/24;
		    if (fechaResta <= 0){
		    	alert("La fecha de pago no puede ser menor que la fecha de recepción de la carta")
		    	Y.one("#dias").set("value", "")
		    	Y.one("#fechaPago").set("value", "")
		    }else{
		    	Y.one("#dias").set("value", fechaResta)	
		    }

			dias = fechaResta
		}
		
		if (tasaInteres){
			var tasa = tasaInteres.toString();
			tasa = parseFloat(tasa) / 360	
			Y.one("#interesDiario").set("value", tasa.toFixed(2))
			interesDiario = tasa.toFixed(2)
		}
		
		if (dias && interesDiario){
			interesPeriodo = interesDiario * dias
			Y.one("#interesPeriodo").set("value", interesPeriodo.toFixed(2))
		}
		
		if (ufPago){
			montoPrestacion = aprobado / ufPago
			Y.one("#montoPrestacion").set("value", montoPrestacion.toFixed(2))
		}
		
		if (montoPrestacion && interesPeriodo){
			interesUf = montoPrestacion * interesPeriodo
			Y.one("#interesUf").set("value", interesUf.toFixed(2))
		}
		
		if (interesUf && montoPrestacion){
			totalUf = interesUf + montoPrestacion
			Y.one("#totalUf").set("value", totalUf.toFixed(2))
		}
		
		if (ufPago && totalUf){
			totalInteres = ufPago * totalUf
			Y.one("#totalInteres").set("value", totalInteres.toFixed(0))
			Y.one("#interesTotal").set("value", totalInteres.toFixed(0))
			if (aprobado){
				var total = totalInteres + parseFloat(aprobado)
				Y.one("#reembolso").set("value", total.toFixed(0))
				Y.one("#reembolsoTotal").set("value", total.toFixed(0))
			}
		}
	
		
	}

	//**************************************************************************************************	
	
});