<g:form name="dp02" class="pure-form pure-form-stacked">	
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>¿Acepta la propuesta del sistema?</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:box texto="De acuerdo a las
					respuestas del cuestionario, el sistema propone que la complejidad
					del accidente sea cero."/>
			</div>
		</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<div class="pure-g-r">
					<div class="pure-u-1">
						<isl:radiogroup cols="1-8" nombre="aceptaPropuesta" name="aceptaPropuesta"
										requerido="true" valor="true"
										labels="['Sí', 'No']"
										values="[true, false]">
							${it.label}
							${it.radio}
						</isl:radiogroup>
					</div>
				</div>
			</div>
		</div>
	</fieldset>
	
		<div class="pure-g-r">
			<isl:button action="cu01"/>
		</div>
</g:form>