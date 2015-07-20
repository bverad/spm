<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp02_2" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>C. Datos del accidente - Denuncia Individual de Accidente de Trabajo</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" requerido="true"  rotulo="Fecha Accidente" valor="${FormatosHelper.fechaCortaStatic(diat?.fechaAccidente)}" />
				<isl:textInput  cols="1-8" requerido="true"  tipo="hora" ayuda="hh:mm" nombre="fechaAccidente_hora" rotulo="Hora accidente" valor="${FormatosHelper.horaCortaStatic(diat?.fechaAccidente)}"/>
				<isl:textInput  cols="1-8" requerido="${attrsReq.horaIngreso}"  tipo="hora" ayuda="hh:mm" nombre="horaIngreso" rotulo="Ingreso al trabajo" valor="${FormatosHelper.horaCortaStatic(diat?.horaIngreso)}"/>
				<isl:textInput  cols="1-8" requerido="${attrsReq.horaSalida}"  tipo="hora" ayuda="hh:mm" nombre="horaSalida" rotulo="Salida del trabajo" valor="${FormatosHelper.horaCortaStatic(diat?.horaSalida)}"/>
				<isl:textInput  cols="3-8" requerido="true"  nombre="direccionAccidenteNombreCalle" rotulo="Dirección" valor="${diat?.direccionAccidenteNombreCalle}"/>
				<isl:combo      cols="1-8" requerido="true"  noSelection="${['':'Seleccione ...']}"  rotulo="Comuna" nombre="direccionAccidenteComuna" from="${comunas}" select="disable" valor="${diat?.direccionAccidenteComuna?.codigo}"/>
				<isl:textArea   cols="4-8" requerido="${attrsReq.que}"  nombre="que" rotulo="Qué estaba haciendo el trabajador al momento o justo antes del accidente" valor="${diat?.que}"/>
				<isl:textArea   cols="4-8" requerido="${attrsReq.lugarAccidente}"  nombre="lugarAccidente" rotulo="Lugar donde ocurrió el accidente (nombre de la sección, edificio, área, etc.)" valor="${diat?.lugarAccidente}"/>
				<isl:textArea   cols="4-8" requerido="true"  nombre="como" rotulo="Descripción de cómo ocurrió el accidente" valor="${diat?.relato}" valor="${diat?.como}"/>
				<isl:textArea   cols="3-8" requerido="${attrsReq.trabajoHabitualCual}"  nombre="trabajoHabitualCual" rotulo="Trabajo habitual del accidentado" valor="${diat?.trabajoHabitualCual}"/>
				<isl:radiogroup cols="1-8" requerido="${attrsReq.esTrabajoHabitual}"  nombre="esTrabajoHabitual" name="esTrabajoHabitual" rotulo="¿Lo desarrollaba?" labels="['Si','No']" values="[true, false]" valor="${diat?.esTrabajoHabitual}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<div class='salto-de-linea'></div>
				<isl:radiogroup cols="2-8" requerido="${attrsReq.gravedad}"  nombre="gravedad" name="gravedad" rotulo="Clasificación del accidente" from="${criterioGravedades}" valor="${diat?.gravedad?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<g:hiddenField name="esAccidenteTrayecto_h" value="${diat?.esAccidenteTrayecto}"/>
				<isl:radiogroup cols="2-8" requerido="${attrsReq.esAccidenteTrayecto}" deshabilitado="true" nombre="esAccidenteTrayecto" name="esAccidenteTrayecto" rotulo="Tipo de accidente" labels="['Trabajo','Trayecto']" values="[1,2]" valor="${diat?.esAccidenteTrayecto ? 2 : 1}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>
					<isl:radiogroup cols="4-8" requerido="${attrsReq.esAccidenteTrayecto}"  deshabilitado="true" nombre="tipoAccidenteTrayecto" name="tipoAccidenteTrayecto" rotulo="Accidente de trayecto" from="${tiposAccidenteTrayecto}" valor="${diat?.tipoAccidenteTrayecto?.codigo}">
						${it.label}
						${it.radio}
					</isl:radiogroup>				
				<isl:radiogroup cols="3-8" requerido="${attrsReq.medioPrueba}" nombre="medioPrueba" name="medioPrueba" rotulo="Medio de prueba" from="${tipoMedioPruebaAccidentes}" valor="${diat?.medioPrueba?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<isl:textInput cols="5-8" requerido="${attrsReq.detallePrueba}"  nombre="detallePrueba" rotulo="Detalle del medio de prueba" valor="${diat?.detallePrueba}"/>
			</div>
		</div>
		<legend>D. Identificación del denunciante - Denuncia Individual de Accidente de Trabajo</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:hiddenField name="denunciante_run" value="${sdaat?.diat?.denunciante?.run}" />
				<isl:textOutput cols="1-8" rotulo="Calificación Denunciante" valor="${sdaat?.diat?.calificacionDenunciante?.descripcion}" />
				<isl:textInput cols="2-8" requerido="true"  nombre="denunciante_nombre" rotulo="Nombres" valor="${sdaat?.diat?.denunciante?.nombre}"/>
				<isl:textInput cols="1-8" requerido="true"  nombre="denunciante_apellidoPaterno" rotulo="Apellido paterno" valor="${sdaat?.diat?.denunciante?.apellidoPaterno}"/>
				<isl:textInput cols="1-8" requerido="true"  nombre="denunciante_apellidoMaterno" rotulo="Apellido materno" valor="${sdaat?.diat?.denunciante?.apellidoMaterno}"/>
				<isl:textInput cols="1-8" requerido="true"  deshabilitado="true" nombre="denunciante_run" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(sdaat?.diat?.denunciante?.run)}"/>				
				<isl:textInput cols="1-8" nombre="telefonoDenunciante" tipo="numero" rotulo="Telefono" valor="${sdaat?.diat?.telefonoDenunciante}"/>				
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button tipo="siguiente" action="cu02_2"/>
		<button class="pure-button pure-button-secondary" onclick="document.forms[0].action='cu02_2back_cu02';document.forms[0].submit();">Volver</button>
	</div>
</g:form>
