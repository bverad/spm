<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FechaHoraHelper" %>
<g:form class="pure-form pure-form-stacked">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="relaId" value="${rela?.id}"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	
	<fieldset>
		<legend>Detalle de Reposo Laboral</legend>
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

 		<div class="form-subtitle">Información de Reposo Laboral</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" rotulo="Fecha Inicio Reposo" 	valor="${FormatosISLHelper.fechaCortaStatic(rela.inicioReposo)}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Término Reposo"	valor="${FormatosISLHelper.fechaCortaStatic(rela.terminoReposo)}" />
				<isl:textOutput cols="1-8" rotulo="N° Días" 				valor="${rela.nDias}" />
				<isl:textOutput cols="1-8" rotulo="RUN Médico" 				valor="${FormatosISLHelper.runFormatStatic(rela.medico.run)}" />
				<isl:textOutput cols="2-8" rotulo="Nombre Médico" 			valor="${FormatosISLHelper.nombreCompletoStatic(rela.medico)}" />
				<isl:textOutput cols="1-8" rotulo="CIE-10" 					valor="${rela.diagnostico?.cie10?.descripcion}" />
				<isl:textOutput cols="1-8" rotulo="Ubicación" 				valor="${rela.diagnostico?.parte?.descripcion}" />
				
				<isl:textOutput cols="1-8" rotulo="Fecha Diagnóstico" 		valor="${FormatosISLHelper.fechaCortaStatic(rela.diagnostico?.fechaDiagnostico)}" />
				<isl:textOutput cols="7-8" rotulo="Diagnóstico" 			valor="${rela.diagnostico?.diagnostico}" />
			</div>
		</div>
		
 		<div class="form-subtitle">Anulación</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textArea cols="8-8" nombre="causaAnulacion" deshabilitado="${isAnulado}" requerido="${!isAnulado}" rows="3" rotulo="Causa Anulación" valor="${rela.causaAnulacion}" />
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="dp07"     class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
		<g:if test="${!isAnulado}">
			<g:actionSubmit action="postDp08" class="pure-button pure-button-warning"   value="Anular" />
		</g:if>
	</div>
</g:form>