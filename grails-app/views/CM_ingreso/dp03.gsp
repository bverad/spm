<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:javascript src="CM/ingreso_dp03.js" />

<g:form name="dp03" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">
	<g:hiddenField name="cuentaMedicaId" value="${params.cuentaMedicaId}"/>
	<g:hiddenField name="taskId" value="${params.taskId}"/>
	
	<isl:header_cm  idSiniestro="${cuentaMedica?.id}"
					tRun="${FormatosISLHelper.runFormatStatic(trabajador?.run)}"
					tNombreCompleto="${FormatosISLHelper.nombreCompletoStatic(trabajador)}"
					pRut="${FormatosISLHelper.rutPrestadorStatic(prestador)}"
					pCentro="${centroSalud?.nombre}"
					pNombre="${FormatosISLHelper.nombrePrestadorStatic(prestador)}"
					opa="${cuentaMedica?.opas}"
					opaep="${cuentaMedica?.opaeps}"
					odas="${cuentaMedica?.odas}"
					folioCuenta="${cuentaMedica?.folioCuenta}"
					mtoCuenta="${cuentaMedica?.valorCuenta}"/>
	
	<fieldset>
		<legend>Selecciona archivo</legend>

		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:upload cols="4-8" nombre="detalleCuentaFile" tipo="SDAAT" rotulo="Seleccionar archivo"/>
			</div>
		</div>

	</fieldset>	
	<div class="pure-g-r">
		<isl:button action="postDp03" tipo='siguiente' />
	</div>
</g:form>
 