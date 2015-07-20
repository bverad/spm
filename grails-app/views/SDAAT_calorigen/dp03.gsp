<g:form name="dp03" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>Cuestionario de Calificación de Origen - Accidente de Trabajo</legend>

		<div class="pure-g-r">
			<div class="pure-u-1">
			
				<isl:radiogroup cols="4-8" nombre="pregunta1" name="pregunta1"
								requerido="true"
								labels="['Sí', 'No']"
								values="[true, false]"
								valor="${cuestionarioTrabajo?.pregunta1}"
								rotulo="A. ¿Ud/el trabajador se encontraba dentro de las dependencias de la empresa?">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<isl:radiogroup cols="4-8" nombre="pregunta2" name="pregunta2"
								requerido="true"
								labels="['Sí', 'No']"
								values="[true, false]"
								valor="${cuestionarioTrabajo?.pregunta2}"
								rotulo="B. ¿Ud/el trabajador se encontraba dentro de su horario de trabajo habitual?">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<isl:textArea cols="4-8" nombre="pregunta1_1" valor="${cuestionarioTrabajo?.pregunta1_1}" />			
				
				<isl:textArea cols="4-8" nombre="pregunta2_1" valor="${cuestionarioTrabajo?.pregunta2_1}" />			
				

				<isl:radiogroup cols="4-8" nombre="pregunta3" name="pregunta3"
								requerido="true"
								labels="['Sí', 'No']"
								values="[true, false]"
								valor="${cuestionarioTrabajo?.pregunta3}"
								rotulo="C. ¿Ud/el trabajador realizaba sus labores habituales?">
					${it.label}
					${it.radio}
				</isl:radiogroup>

				<isl:radiogroup cols="4-8" nombre="pregunta4" name="pregunta4"
								requerido="true"
								labels="['Sí', 'No']"
								values="[true, false]"
								valor="${cuestionarioTrabajo?.pregunta4}"
								rotulo="D. ¿Ud/el trabajador se encontraba en: comisión de servicio, capacitación, horas extras, actividad sindical o actividad extraprogramática organizada por la empresa?">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<isl:textArea	cols="4-8" nombre="pregunta3_1" valor="${cuestionarioTrabajo?.pregunta3_1}" />
				
				<isl:textArea	cols="4-8" nombre="pregunta4_1" valor="${cuestionarioTrabajo?.pregunta4_1}" />

				<isl:radiogroup cols="4-8" nombre="pregunta5" name="pregunta5"
								requerido="true"
								labels="['Sí', 'No']"
								values="[true, false]"
								valor="${cuestionarioTrabajo?.pregunta5}"
								rotulo="E. ¿Hubo en el accidente un vehículo involucrado?">
					${it.label}
					${it.radio}
				</isl:radiogroup>

				<isl:radiogroup cols="4-8" name="origenDanyo" nombre="origenDanyo"
					rotulo="¿Cómo se produjo el daño?" labels="${tipoDanyoTrabajo?.descripcion}"
					values="${tipoDanyoTrabajo?.codigo}"
					valor="${cuestionarioTrabajo?.origenDanyo?.codigo}"
					requerido="false">
					${it.radio} ${it.label} <br>
				</isl:radiogroup>

				<isl:textArea cols="8-8" requerido="true" nombre="relatoSiniestro" rotulo="Describa el accidente" valor="${sdaat?.relato}"/>
				
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit value="Continuar"  action="r02" class="pure-button pure-button-success"  />
	</div>
</g:form>
