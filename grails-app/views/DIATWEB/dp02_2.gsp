<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp02_2" class="pure-form pure-form-stacked" >
	<fieldset>
		<legend>C. Datos del accidente - Denuncia Individual de Accidente de Trabajo</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" requerido="true"  rotulo="Fecha Accidente" valor="${FormatosHelper.fechaCortaStatic(diat?.fechaAccidente)}" />
				<isl:textInput  cols="1-8" requerido="true"  tipo="hora" ayuda="hh:mm" nombre="fechaAccidente_hora" rotulo="Hora accidente" valor="${FormatosHelper.horaCortaStatic(diat?.fechaAccidente)}"/>
				<isl:textInput  cols="1-8" requerido="true"  tipo="hora" ayuda="hh:mm" nombre="horaIngreso" rotulo="Ingreso al trabajo" valor="${FormatosHelper.horaCortaStatic(diat?.horaIngreso)}"/>
				<isl:textInput  cols="1-8" requerido="true"  tipo="hora" ayuda="hh:mm" nombre="horaSalida" rotulo="Salida del trabajo" valor="${FormatosHelper.horaCortaStatic(diat?.horaSalida)}"/>
				<isl:textInput  cols="3-8" requerido="true"  nombre="direccionAccidenteNombreCalle" rotulo="Dirección" valor="${diat?.direccionAccidenteNombreCalle}"/>
				<isl:combo      cols="1-8" requerido="true"  noSelection="${['':'Seleccione ...']}"  rotulo="Comuna" nombre="direccionAccidenteComuna" from="${comunas}" select="disable" valor="${diat?.direccionAccidenteComuna}"/>
				<isl:textArea   cols="4-8" requerido="true"  nombre="que" rotulo="Qué estaba haciendo el trabajador al momento o justo antes del accidente" valor="${diat?.que}"/>
				<isl:textArea   cols="4-8" requerido="true"  nombre="lugarAccidente" rotulo="Lugar donde ocurrió el accidente (nombre de la sección, edificio, área, etc.)" valor="${diat?.lugarAccidente}"/>
				<isl:textArea   cols="4-8" requerido="true"  nombre="como" rotulo="Descripción de cómo ocurrió el accidente" valor="${diat?.relato}" valor="${diat?.como}"/>
				<isl:textArea   cols="3-8" requerido="true"  nombre="trabajoHabitualCual" rotulo="Trabajo habitual del accidentado" valor="${diat?.trabajoHabitualCual}"/>
				<isl:radiogroup cols="1-8" requerido="true"  nombre="esTrabajoHabitual" name="esTrabajoHabitual" rotulo="¿Lo desarrollaba?" labels="['Si','No']" values="[true, false]" valor="${diat?.esTrabajoHabitual}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<div class='salto-de-linea'></div>
				<isl:radiogroup cols="2-8" requerido="true"  nombre="gravedad" name="gravedad" rotulo="Clasificación del accidente" from="${criterioGravedades}" valor="${diat?.gravedad?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<isl:radiogroup cols="2-8" requerido="true" deshabilitado="true" nombre="esAccidenteTrayecto" name="esAccidenteTrayecto" rotulo="Tipo de accidente" labels="['Trabajo','Trayecto']" values="[1,2]" valor="${diat?.esAccidenteTrayecto ? 2 : 1}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<isl:radiogroup cols="4-8" requerido="${diat?.esAccidenteTrayecto}"  deshabilitado="${!diat?.esAccidenteTrayecto}" nombre="tipoAccidenteTrayecto" name="tipoAccidenteTrayecto" rotulo="Accidente de trayecto" from="${tiposAccidenteTrayecto}" valor="${diat?.tipoAccidenteTrayecto?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<isl:radiogroup cols="3-8" 					 nombre="medioPrueba" name="medioPrueba" rotulo="Medio de prueba" from="${tipoMedioPruebaAccidentes}" valor="${diat?.medioPrueba?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<isl:textInput cols="5-8"  					 nombre="detallePrueba" rotulo="Detalle del medio de prueba" valor="${diat?.detallePrueba}"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button tipo="siguiente" action="cu02_2"/>
	</div>
</g:form>