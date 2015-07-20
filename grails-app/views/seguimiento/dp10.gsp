<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FechaHoraHelper" %>
<g:form class="pure-form pure-form-stacked">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="almeId" value="${alme?.id}"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	
	<fieldset>
		<legend>Detalle de Alta Medica</legend>
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

 		<div class="form-subtitle">Información de Alta Medica</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" rotulo="Fecha Otorgamiento"	 	valor="${FormatosISLHelper.fechaCortaStatic(alme.fechaOtorgamiento)}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Alta"		 		valor="${alme.tipoAlta}" />
				<isl:textOutput cols="3-8" rotulo="Motivo Alta" 		 	valor="${alme.motivoAlta}" />
				<isl:textOutput cols="1-8" rotulo="Indicación Evaluación"	valor="${alme.indicacionEvaluacion ? 'Sí' : 'No'}" />
				<div class='salto-de-linea'></div>
				
				<isl:textOutput cols="1-8" rotulo="RUN Médico" 				valor="${FormatosISLHelper.runFormatStatic(alme.medico.run)}" />
				<isl:textOutput cols="2-8" rotulo="Nombre Médico" 			valor="${FormatosISLHelper.nombreCompletoStatic(alme.medico)}" />
				<isl:textOutput cols="1-8" rotulo="CIE-10" 					valor="${alme.diagnostico?.cie10?.descripcion}" />
				<isl:textOutput cols="1-8" rotulo="Ubicación" 				valor="${alme.diagnostico?.parte?.descripcion}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Diagnóstico" 		valor="${FormatosISLHelper.fechaCortaStatic(alme.diagnostico?.fechaDiagnostico)}" />

				<isl:textOutput cols="7-8" rotulo="Diagnóstico" 			valor="${alme.diagnostico?.diagnostico}" />
			</div>
		</div>
		
 		<div class="form-subtitle">Anulación</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textArea cols="8-8" nombre="causaAnulacion"  deshabilitado="${isAnulado}" requerido="${!isAnulado}" rows="3" rotulo="Causa Anulación" valor="${alme.causaAnulacion}" />
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="dp07"     class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
		<g:if test="${!isAnulado}">
			<g:actionSubmit action="postDp10" class="pure-button pure-button-warning"   value="Anular" />
		</g:if>
	</div>
</g:form>