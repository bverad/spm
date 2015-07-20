/**
 *
 *
 */
YUI().use("io", "json-parse", "node", "panel", "datatype-number", function (Y) {
	Y.on("domready", function () {
		Y.one("#btnAceptarDocumento").set("disabled", true);
		obtenDiferenciaYHabilita(0);

		Y.all('input.detalle-fct-ndc').on('change', calculaValorDocumento);

		function calculaValorDocumento  () {
			var suma = 0;
			// Iteramos por todos los campos y sumamos
			var detalles = Y.all('input.detalle-fct-ndc')

			var i = detalles.size();
			var valor, valorCalculado;
			while (i--) {
				suma += obtenNumerico(detalles.item(i).get('value'));
			}

			// Reemplaza
			Y.one('input#notaCreditoValor').set("value", suma);
			Y.one('#notaCreditoValorTH').setHTML(Y.Number.format(suma, {thousandsSeparator: "."}));

			return obtenDiferenciaYHabilita(suma);
		}

		function obtenDiferenciaYHabilita (suma) {
			var valorFacturaOrigen = obtenNumerico(Y.one('input#totalFactura').get('value'));
			var sumaCM             = obtenNumerico(Y.one('input#sumaCuentasMedicas').get('value'));
			var diferencia_1       = Math.abs(valorFacturaOrigen- sumaCM);
			var diferencia         = suma - diferencia_1;

			Y.one('input#diferenciaPesos').set('value', diferencia);
			Y.one('#diferenciaPesosTH').setHTML(Y.Number.format(diferencia, {thousandsSeparator: "."}));

			if (diferencia === 0) {
				Y.one("#btnAceptarDocumento").set("disabled", '');
			} else {
				Y.one("#btnAceptarDocumento").set("disabled", true);
			}

			return false;
		}

		// Auxiliar
		function obtenNumerico (value) {
			var valorCalculado = parseInt(value);
			return (valorCalculado.toString() == 'NaN' ? 0 : valorCalculado);
		}
	});
});
