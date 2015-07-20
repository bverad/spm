<g:javascript>
    
</g:javascript>
<g:javascript src="SDAAT/opa.js" />
<g:form name="dp02" class="pure-form pure-form-stacked" >
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
		<legend>Elegir prestador</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
	 			<isl:combo cols="2-8"  requerido="true"  noSelection="${['':'Seleccione ...']}" rotulo="Region" nombre="region" from="${listadoRegiones}"/>
	 			<isl:combo cols="2-8"  rotulo="Prestador" nombre="prestador" from=""/>
	 			<isl:combo cols="2-8"  rotulo="Centro de Salud" nombre="centroAtencion"  from=""/>
			</div>	
		</div>
		
		<div class="pure-g-r">
			<div class="pure-u-1">		
				<g:actionSubmit value="Emitir OPA" action="r02"  class="pure-button pure-button-success"  />
			</div>	
		</div>
	</fieldset>
</g:form>
