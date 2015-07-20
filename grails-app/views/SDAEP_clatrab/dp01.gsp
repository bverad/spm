<g:form name="dp01" class="pure-form pure-form-stacked" >
	<isl:header_sdaep sdaep="${sdaep}"/>
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
								<g:textField name="otroEmpleado" value="${otroEmpleado}" class="opcional" style="width: 100%" />
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
