<g:form class="pure-form pure-form-stacked" >
    <fieldset>
 		<legend>Ingrese RUN Paciente</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" rotulo="RUN Trabajador" nombre="run" value="${trabajador?.run}" requerido="true" />
			</div>
		</div>
	</fieldset>
	
	<div class="pure-g-r">		
		<isl:button action="cu01" tipo="siguiente" />
	</div>
</g:form>
