/**
 * 
 * 
 * 
 * 
 */

YUI().use("node", "transition", "panel", "io", "dump", "json-parse",
		function(Y) {
			// Desactivar los campos

			
			Y.on("domready", function() {
				var fechaDesde = document.getElementsByTagName("input")[1];
				var fechaHasta = document.getElementsByTagName("input")[6];
				//Y.one("#" + fechaDesde.name).on("change", isCargaHistoricaArancel);
				var checkValue = document.getElementById("isCargaArancelHistorico");
				if(checkValue.checked){						
					fechaHasta.disabled =false; 
				}else	
					fechaHasta.disabled = true;
				
				Y.one("#isCargaArancelHistorico").on("click", function(e){
				    var checked = e.target.get('checked');
				    if(checked){
				    	fechaHasta.disabled = false;
				    }else{
				    	fechaHasta.disabled = true;
				    }
				})
			});

			/**
			 * Event Handlers
			 */
		

			function isCargaHistoricaArancel() {
				// corresponde al indice de la fecha hasta, se maneja de esta
				// manera debido a que fue generado por un taglib que impide
				// acceder al nombre
				var fechaDesde = document.getElementsByTagName("input")[1];
				var fechaHasta = document.getElementsByTagName("input")[6];
				
				var parsedResponse
				Y.io('isCargaHistoricaArancelJSON?desde=' + fechaDesde.value, {
					on : {

						success : function(tx, r) {
							try {
								/*
								 * H:true
								 * NH:false
								 * */
								parsedResponse = Y.JSON.parse(r.responseText);
								var result = parsedResponse[0].isCargaHistoricaArancel;
								//alert("fechaDesde : " + fechaDesde.value + "----> result : " + result);
									if(result == "false" && fechaDesde.value != ""){
										fechaHasta.disabled = true;
									}else if(result == "true" && fechaDesde.value != ""){
										fechaHasta.disabled = false;
									}else if(result == ""){
										fechaHasta.value = "";
										fechaHasta.disabled = false;
									}
																	
							} catch (e) {
								alert("Error al parsear JSON!");
								return;
							}
							
						}
					}
				});

			}
		});


function existeCarga(ec){
	alert("valor existe carga : " + ec);
}
