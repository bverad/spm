<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="CalOrigenAT/cie10.js" />

<g:form name="dp06" class="pure-form pure-form-stacked" >

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="numeroDiagnostico" value="${numeroDiagnostico}"/>	
	<g:hiddenField name="backTo" value="${params.backTo}"/>
	<g:hiddenField name="backToController" value="${params.backToController}"/>	
	<g:hiddenField name="volverSeguimiento" value="${params.volverSeguimiento}"/>
	
	<fieldset>
 		<legend>Datos Siniestro</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
				<isl:textOutput cols="6-8" requerido="true" deshabilitado="true" rotulo="N° Siniestro" valor="${siniestro?.id}" />			
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="dias_restantes" rotulo="Días Restantes" valor="${diasR}"/>
				<div class="salto-de-linea"></div>
				<!-- Info Trab/prest -->
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(siniestro.trabajador?.run)}"/>
				<isl:textInput  cols="4-8" requerido="true" deshabilitado="true" nombre="tipo_siniestro" rotulo="Tipo Siniestro" valor="${siniestro.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}"/>
				<isl:textInput  cols="2-8" requerido="true" deshabilitado="true" nombre="fecha_siniestro" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="Fecha Siniestro"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}" />
				
			</div>
		</div>
		
		<legend>Diagnósticos</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
			
				<isl:radiogroup cols="1-8" nombre="esLaboral" name="esLaboral"
								labels="['Sí', 'No']"
								values="[1,2]"
								rotulo="Laboral"
								requerido="true"
								valor="${diagnostico.esLaboral ? 1 : 2}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<isl:textArea cols="7-8" requerido="true" nombre="diagnostico" rotulo="Diagnóstico" valor="${diagnostico.diagnostico}"/>
				
				<isl:combo cols="1-5" requerido="true" noSelection="${['':'Seleccione...']}" nombre="parte" from="${parte}" rotulo="Parte" valor="${diagnostico?.parte?.codigo}" optionValue="${{it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['':'Seleccione...']}" nombre="lateralidad" from="${lateralidad}" rotulo="Lateralidad" valor="${diagnostico.lateralidad.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="2-5" requerido="true" nombre="origen" from="${origenDiag}" rotulo="Origen" valor="${diagnostico.origen.codigo}" optionValue="${{it.descripcion}}"/>
				<isl:calendar cols="1-5" requerido="true" nombre="fechaDiagnostico" rotulo="Fecha del Diagnóstico"  valor="${diagnostico?.fechaDiagnostico}"/>
				
			</div>
	    </div>
	    
	    <legend>CIE 10</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-5" requerido="true" rotulo="Código CIE-10" nombre="cie10" valor="${diagnostico?.cie10?.codigo}"/>
				<isl:textInput cols="4-5" requerido="true" nombre="cie10_descrip" rotulo="Enfermedad CIE-10" valor="${diagnostico?.cie10?.descripcion}" deshabilitado="true"/>
				<div class="salto-de-linea"></div>	
				<button id="btnBuscar" class="pure-button pure-button-secondary" onclick="return false;">Buscar</button>				
			</div>
		</div>
		
		<div class="salto-de-linea"></div>
		<legend></legend>
		
	</fieldset>	
	<div class="pure-g-r">
		<isl:button tipo="siguiente" value="Actualizar" action="update_dg"/>
		<button class="pure-button pure-button-secondary" onclick="document.forms[0].action='${params?.volverSeguimiento == null ? 'go_back' : params?.volverSeguimiento}';document.forms[0].submit();" formnovalidate="formnovalidate">Volver</button>
	</div>
</g:form>