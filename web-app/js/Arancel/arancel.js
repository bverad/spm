YUI().use(
		'event',
		"node",
		"transition",
		"panel",
		"io",
		"dump",
		"json-parse",
		function(Y) { // loading escape only for security on this page

			var myVar;
			var buscarSubGrupo = function(e) {
				var grupoId = Y.one("#grupo").get('value');
				Y.io('SubGruposJSON?grupo=' + grupoId, {
					on : {
						success : function(tx, r) {
							llenarSubGrupo(r.responseText)
						}
					}
				});

			}

			var llenarSubGrupo = function(jsonString) {
				var subGrupoSelect = Y.one("#subgrupo")
				subGrupoSelect.get('childNodes').remove();

				try {
					var data = Y.JSON.parse(jsonString);
				} catch (e) {
					alert("Error al recibir la lista de Sub Grupos.");
					return;
				}

				if (data.length > 0) {
					var oStr = '<option value="">Seleccionar ...</option>'
					subGrupoSelect.appendChild(Y.Node.create(oStr))
				}

				for (var i = 0; i < data.length; i++) {
					var p = data[i];
					var n = p.descripcion
					oStr = '<option value="' + p.codigo + '">' + n
							+ '</option>'
					subGrupoSelect.appendChild(Y.Node.create(oStr))
				}
				if (data.length > 0) {
					subGrupoSelect.set("disabled", false)
				} else {
					subGrupoSelect.set("disabled", true)
				}
				data = null
			}
			
			/*
			 *Permite cargar arancel segun fecha
			 * 
			 * */
			var fechaDesde = Y.one("#desde").get('value');
			var isCargaHistoricaArancel = function(e) {
				Y.io('isCargaHistoricaArancelJSON?desde=' + grupoId, {
					on : {
						success : function(tx, r) {
							alert("sucess : " + fechaDesde)
							
						}
					}
				});

			}

			// Y.one("#subgrupo").set("disabled",true)
			Y.one("#grupo").on("change", buscarSubGrupo);
			Y.one("#desde").on("change", isCargaHistoricaArancel);
			
			//
			
			

		});