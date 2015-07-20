<g:form name="dp01" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>Cuestionario Complejidad</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<div class="pure-g-r">
					<div class="pure-u-1">
						<isl:radiogroup cols="8-8" nombre="pregunta1" name="pregunta1"
										requerido="true"
										labels="['Sí', 'No']"
										values="[true, false]"
										valor="${cuestionario?.pregunta1}"
										rotulo="A. El accidente denunciado fue producto de caída de altura (mas de 1,8 metros), atropello, accidente en moto, explosión, derrumbe o un accidente que afecto simultáneamente a más de un trabajador">
							${it.label}
							${it.radio}
						</isl:radiogroup>
						<div class="salto-de-linea separadorSalto"></div>
						<isl:radiogroup cols="8-8" nombre="pregunta2" name="pregunta2"
										requerido="true"
										labels="['Sí', 'No']"
										values="[true, false]"
										valor="${cuestionario?.pregunta2}"
										rotulo="B. El accidente denunciado produjo en el trabajador quemaduras, electrocución, amputación de alguna parte del cuerpo o pérdida de conocimiento">
							${it.label}
							${it.radio}
						</isl:radiogroup>

						<div class="salto-de-linea separadorSalto"></div>

						<isl:radiogroup cols="8-8" nombre="pregunta3" name="pregunta3"
										requerido="true"
										labels="['Sí', 'No']"
										values="[true, false]"
										valor="${cuestionario?.pregunta3}"
										rotulo="C. El trabajador se desempeñaba en la actividad forestal, minería, pesca, construcción o transporte">
							${it.label}
							${it.radio}
						</isl:radiogroup>

						<div class="salto-de-linea separadorSalto"></div>

						<isl:radiogroup cols="8-8" nombre="pregunta4" name="pregunta4"
										requerido="true"
										labels="['Sí', 'No']"
										values="[true, false]"
										valor="${cuestionario?.pregunta4}"
										rotulo="1. El trabajador es quien realiza la denuncia">
							${it.label}
							${it.radio}
						</isl:radiogroup>

						<div class="salto-de-linea separadorSalto"></div>

						<isl:radiogroup cols="8-8" nombre="pregunta5" name="pregunta5"
										requerido="true"
										labels="['Sí', 'No']"
										values="[true, false]"
										valor="${cuestionario?.pregunta5}"
										rotulo="2. El trabajador pudo continuar sus labores hasta el fin de la jornada luego del accidente">
							${it.label}
							${it.radio}
						</isl:radiogroup>

						<div class="salto-de-linea separadorSalto"></div>

						<isl:radiogroup cols="8-8" nombre="pregunta6" name="pregunta6"
										requerido="true"
										labels="['Sí', 'No']"
										values="[true, false]"
										valor="${cuestionario?.pregunta6}"
										rotulo="3. El dolor se produjo al realizar un movimiento habitual de su trabajo">
							${it.label}
							${it.radio}
						</isl:radiogroup>
					</div>
				</div>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit value="Siguiente"  action="r01" class="pure-button pure-button-success"  />
	</div>
</g:form>
