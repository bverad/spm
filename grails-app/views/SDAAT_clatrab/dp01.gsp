<g:javascript>
YUI().use('event', 'node', function (Y) {
     Y.all('input[name=codigo]').on('click', function (e) {
		var value = Y.one('input[name=codigo]:checked').get('value');
		
		if (value != "OO" && value != "OE") {
			Y.one('input[name=otroEmpleado]').set("value", "");
			Y.one('input[name=otroObrero]').set("value", "");
		}
		
		if (value == "OE") {
			Y.one('input[name=otroObrero]').set("value", "");
		}
		
		if (value == "OO") {
			Y.one('input[name=otroEmpleado]').set("value", "");
		}
		
     });
	 
	 Y.one('input[name=otroObrero]').on('click', function (e) {
		seleccionaRadio('OO');
     });
	 Y.one('input[name=otroEmpleado]').on('click', function (e) {
		seleccionaRadio('OE');
     });
     
	 Y.one('input[name=otroObrero]').on('keypress', function (e) {
		seleccionaRadio('OO');
		Y.one('input[name=otroEmpleado]').set("value", "");
     });

	 Y.one('input[name=otroEmpleado]').on('keypress', function (e) {
		seleccionaRadio('OE');
		Y.one('input[name=otroObrero]').set("value", "");
     });
     
});

function seleccionaRadio(radio) {

	var radios = document.getElementsByName('codigo');

	length = radios.length;
	
	for (var i = 0; i < length; i++) {
		
		if (radios[i].value == radio) {
			radios[i].checked = true;
			break;
		}
	}
	
}
</g:javascript>
<g:form name="dp01" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
 		<div class="pure-g-r">
			<div class="pure-u-1">
		 		<legend>Clasificación del Trabajador</legend>
				<div class="pure-g-r">
					<div class="pure-u-1">
						<br>
						<g:each in="${actividadesTrabajador}" var="a" >
			   				<div class="isl-tiles">
				   				<g:radio name="codigo" value="${a.codigo}"/>
					        		${a.descripcion}
				   			</div>
						</g:each>
						<div class="isl-tiles isl-tiles-plus">
				   				<g:radio name="codigo" value="OO" checked="${cuestionarioObrero?.actividadTrabajador?.codigo == 'OO'}"/>
					        	Otro Obrero
								<g:textField name="otroObrero" value="${otroObrero}" class="opcional" style="width: 100%" />
				   		</div>
				   			
						<div class="isl-tiles isl-tiles-plus">
				   				<g:radio name="codigo" value="OE" checked="${cuestionarioObrero?.actividadTrabajador?.codigo == 'OE'}"/>
					        	Empleado
								<g:textField name="otroEmpleado" value="${otroEmpleado}" class="opcional" style="width: 100%"/>
				   		</div>

					</div>
				</div>
			</div>
		</div>

	</fieldset>	
	<div class="pure-g-r">
		<isl:button  action="r02"/>
	</div>
</g:form>
