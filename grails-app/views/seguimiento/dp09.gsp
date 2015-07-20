<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FechaHoraHelper" %>
<g:form class="pure-form pure-form-stacked">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="allaId" value="${alla?.id}"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	
	<fieldset>
		<legend>Detalle de Alta Laboral</legend>
 		<div class="form-subtitle">Datos Siniestro</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
				<isl:textOutput cols="1-8" rotulo="N° Siniestro"			valor="${siniestro?.id}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Siniestro"			valor="${siniestro?.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro?.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Siniestro"			valor="${FormatosISLHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<isl:textOutput cols="1-8" rotulo="Complejidad" 			valor="${FormatosISLHelper.getNivelComplejidadStr(siniestro?.nivelComplejidad)}" />
				<isl:textOutput cols="1-8" rotulo="Días en Seguimiento"		valor="${FechaHoraHelper.diffDates(seguimiento?.fechaIngreso) == -1 ? 'Sin Seguimiento' : FechaHoraHelper.diffDates(seguimiento?.fechaIngreso)}" />
				<isl:textOutput cols="1-8" rotulo="ODA Principal Vigente"	valor="${isOPAVigente ? 'Sí' : 'No'}" />
				<isl:textOutput cols="1-8" rotulo="Nivel Seguimiento"		valor="${seguimiento?.nivel}" />
				<!-- Info Trab/prest -->
				<isl:textOutput cols="1-8" rotulo="RUN Trabajador"			valor="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}" />
			</div>
		</div>

 		<div class="form-subtitle">Información de Alta Laboral</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" rotulo="Fecha Alta Laboral"	 valor="${FormatosISLHelper.fechaCortaStatic(alla.fechaAlta)}" />
				<isl:textOutput cols="1-8" rotulo="Alta Inmediata"		 valor="${alla.altaInmediata ? 'Sí' : 'No'}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Condición" 		 valor="${alla.tipoCondicion}" />
				<isl:textOutput cols="3-8" rotulo="Condición" 			 valor="${alla.condiciones ? 'Sí' : 'No'}" />
				<isl:textOutput cols="1-8" rotulo="Continua Tratamiento" valor="${alla.continuaTratamiento ? 'Sí' : 'No'}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Tratamiento"	 valor="${alla.tipoTratamiento}" />
				
				<isl:textOutput cols="1-8" rotulo="RUN Médico" 				valor="${FormatosISLHelper.runFormatStatic(alla.medico.run)}" />
				<isl:textOutput cols="2-8" rotulo="Nombre Médico" 			valor="${FormatosISLHelper.nombreCompletoStatic(alla.medico)}" />
				<isl:textOutput cols="1-8" rotulo="CIE-10" 					valor="${alla.diagnostico?.cie10?.descripcion}" />
				<isl:textOutput cols="1-8" rotulo="Ubicación" 				valor="${alla.diagnostico?.parte?.descripcion}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Diagnóstico" 		valor="${FormatosISLHelper.fechaCortaStatic(alla.diagnostico?.fechaDiagnostico)}" />

				<isl:textOutput cols="7-8" rotulo="Diagnóstico" 			valor="${alla.diagnostico?.diagnostico}" />
			</div>
		</div>
		
 		<div class="form-subtitle">Anulación</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textArea cols="8-8" nombre="causaAnulacion"  deshabilitado="${isAnulado}" requerido="${!isAnulado}" rows="3" rotulo="Causa Anulación" valor="${alla.causaAnulacion}" />
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="dp07"     class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
		<g:if test="${!isAnulado}">
			<g:actionSubmit action="postDp09" class="pure-button pure-button-warning"   value="Anular" />
		</g:if>
	</div>
</g:form>