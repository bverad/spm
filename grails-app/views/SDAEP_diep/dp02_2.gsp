<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp02_2" class="pure-form pure-form-stacked" >
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
		<legend>C. Datos de la enfermedad - Denuncia Individual de Enfermedad Profesional</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">		    	
				<isl:textArea cols="5-8"   requerido="true" nombre="sintoma" rotulo="Describa las molestias o sintomas que actualmente tiene el trabajador/a" valor="${diep?.sintoma}" />				
				<isl:textArea cols="3-8"   requerido="true" nombre="parteCuerpo" rotulo="Parte del cuerpo afectada" valor="${diep?.parteCuerpo}" />
				<isl:textArea cols="3-8"   requerido="true" nombre="descripcionTrabajo" rotulo="Describa la actividad que realizaba cuando comenzaron las molestias" valor="${diep?.descripcionTrabajo}" />
				<isl:textArea cols="2-8"   requerido="${attrsReq.puestoTrabajo}" nombre="puestoTrabajo" rotulo="Nombre del puesto de trabajo" valor="${diep?.puestoTrabajo}" />
				<isl:textArea cols="3-8"   requerido="true" nombre="agenteSospechoso" rotulo="¿Que agentes del trabajo cree usted que le causaron estas molestias?" valor="${diep?.agenteSospechoso}" />
				<isl:radiogroup cols="2-8" requerido="${attrsReq.esAntecedenteCompanero}" nombre="esAntecedenteCompanero" name="esAntecedenteCompanero" rotulo="¿Existen compañeros de trabajo con las mismas molestias?" labels="['Si','No']" values="[true, false]" valor="${diep?.esAntecedenteCompanero}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>				
				<isl:calendar  cols="2-8"  requerido="true" nombre="fechaAgente" rotulo="¿Aproximadamente desde que fecha ha estado expuesto a los agentes que causan las molestias?" valor="${diep?.fechaAgente}" />
				<isl:calendar  cols="2-8"  mostrarCal="false" requerido="true" nombre="fechaSintoma" ayuda="dd-mm-aaaa" rotulo="¿Aproximadamente en que fecha comenzaron los sintomas?" valor="${diep?.fechaSintoma}" />
				<isl:radiogroup cols="2-8" requerido="${attrsReq.sintomasPrevios}" nombre="sintomasPrevios" name="esAntecedentePrevio" rotulo="¿Habia tenido estas molestias en el puesto de trabajo actual anteriormente?" labels="['Si','No']" values="[true, false]" valor="${diep?.esAntecedentePrevio}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>				
			</div>
		</div>
		<legend>D. Identificación del denunciante - Denuncia Individual de Enfermedad Profesional</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:hiddenField name="denunciante_run" value="${sdaep?.diep?.denunciante?.run}" />
				<isl:textOutput cols="1-8" rotulo="Calificación Denunciante" valor="${sdaep?.diep?.calificacionDenunciante?.descripcion}" />
				<isl:textInput cols="2-8" requerido="true"  nombre="denunciante_nombre" rotulo="Nombres" valor="${sdaep?.diep?.denunciante?.nombre}"/>
				<isl:textInput cols="1-8" requerido="true"  nombre="denunciante_apellidoPaterno" rotulo="Apellido paterno" valor="${sdaep?.diep?.denunciante?.apellidoPaterno}"/>
				<isl:textInput cols="1-8" requerido="true"  nombre="denunciante_apellidoMaterno" rotulo="Apellido materno" valor="${sdaep?.diep?.denunciante?.apellidoMaterno}"/>
				<isl:textInput cols="1-8" deshabilitado="true" requerido="true"  nombre="denunciante_run" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(sdaep?.diep?.denunciante?.run)}"/>				
				<isl:textInput cols="1-8" nombre="telefonoDenunciante" tipo="numero" rotulo="Telefono" valor="${sdaep?.diep?.telefonoDenunciante}"/>				
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button tipo="siguiente" action="cu02_2"/>
		<button class="pure-button pure-button-secondary" onclick="document.forms[0].action='cu02_2back_cu02';document.forms[0].submit();">Volver</button>
	</div>
</g:form>