<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp02" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>Cuestionario de Calificación de Origen - Accidente de Trayecto</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="2-8" rotulo="A. ¿En qué fecha fue el accidente de trayecto que Ud/El sufrió?" valor="${FormatosHelper.fechaCortaStatic(sdaat.fechaSiniestro)}"/>
				<isl:textInput cols="2-8" nombre="pregunta1"
								requerido="true" ayuda="hh:mm"
								valor="${FormatosHelper.horaCortaStatic(cuestionarioTrayecto?.pregunta1)}"
								tipo="hora"
								rotulo="A. ¿A qué hora fue el accidente de trayecto que Ud/El sufrió?" />

			  <div class=".salto-de-linea"></div>
				<isl:radiogroup cols="4-8" nombre="pregunta2" name="pregunta2"
								requerido="true"
								labels="['Sí', 'No']"
								values="[true, false]"
								valor="${cuestionarioTrayecto?.pregunta2}"
								rotulo="B. ¿Ud/el trabajador realizaba el trayecto directo habitual de ida o regreso a su domicilio o trabajo?">
					${it.label}
					${it.radio}
				</isl:radiogroup>
	
				<isl:textArea	cols="4-8" filas="8"
								nombre="pregunta2_1"
								valor="${cuestionarioTrayecto?.pregunta2_1}" />				
				<isl:textInput cols="4-8" nombre="pregunta3"
								requerido="true" ayuda="Cantidad de Horas" valor="${FormatosHelper.decimalComoHoraStatic(cuestionarioTrayecto?.pregunta3)}"
								tipo="hora"
								rotulo="C. ¿Cuanto se demora habitualmente el trabajador en su trayecto de su casa al trabajo o viceversa?" />
			
				<isl:calendar cols="2-8"
								nombre="pregunta4_1"
								requerido="true"
								valor="${cuestionarioTrayecto?.pregunta4}"
								rotulo="D. ¿En que fecha salió de su casa o trabajo el día del accidente?" />

				<isl:textInput cols="2-8" nombre="pregunta4"
								requerido="true" ayuda="hh:mm" valor="${FormatosHelper.horaCortaStatic(cuestionarioTrayecto?.pregunta4)}"
								tipo="hora"
								rotulo="D. ¿A que hora salió de su casa o trabajo el día del accidente?" />
	
				<isl:radiogroup cols="4-8"
								name="tipoAccidenteTrayecto"
								nombre="tipoAccidenteTrayecto"
								requerido="true" 
								rotulo="E. Indique el tipo de accidente de trayecto"
								labels="${tiposAccidenteTrayecto.descripcion}"
								values="${tiposAccidenteTrayecto.codigo}"
								valor="${cuestionarioTrayecto?.tipoAccidenteTrayecto?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>

			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit value="Continuar"  action="r01" class="pure-button pure-button-success"  />
	</div>
</g:form>
