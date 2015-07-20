<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FechaHoraHelper" %>
<g:form class="pure-form pure-form-stacked">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="relaId"/>
	<g:hiddenField name="allaId"/>
	<g:hiddenField name="almeId"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="dp14" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	
	
	<fieldset>
		<legend>Resumen</legend>
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

 		<div class="form-subtitle">Reposos Laborales - RELA</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Fecha Inicio Reposo</th>
							<th>Fecha Término Reposo</th>
							<th>N° Días Reposo</th>
							<th>Diagnóstico</th>
							<th>Revisar</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!relas}">
							<tr>						
								<td colspan="5">No hay Reposos Laborales asociadas al siniestro</td>
							</tr>
						</g:if>		 		
						<g:each in="${relas}" var="rela">
							<tr>						
								<td align="center">${FormatosISLHelper.fechaCortaStatic(rela.inicioReposo)}</td>
								<td align="center">${FormatosISLHelper.fechaCortaStatic(rela.terminoReposo)}</td>
								<td align="right">${rela.nDias}</td>
								<td>${rela.diagnostico?.cie10?.descripcion}</td>
							   	<td align="center">
							   		<a href="#" onclick="document.forms[0].relaId.value='${rela.id}';document.forms[0].action='dp08';document.forms[0].submit();" style="text-decoration: none;">
							   			<font style="color: rgb(127,127,127);"><i class="icon-search icon-2x"></i></font>
							   		</a>
								</td>	
							</tr>
						</g:each>
					</tbody>
				</table>					
			</div>
		</div>
		
		<div class="form-subtitle">Altas Laborales - ALLA</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Fecha alta laboral</th>
							<th>Médico</th>
							<th>Revisar</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!allas}">
							<tr>						
								<td colspan="3">No hay Altas Laborales asociadas al siniestro</td>
							</tr>
						</g:if>		 		
						<g:each in="${allas}" var="alla">
							<tr>						
								<td align="center">${FormatosISLHelper.fechaCortaStatic(alla.fechaAlta)}</td>
								<td>${FormatosISLHelper.nombreCompletoStatic(alla.medico)}</td>
							   	<td align="center">
							   		<a href="#" onclick="document.forms[0].allaId.value='${alla.id}';document.forms[0].action='dp09';document.forms[0].submit();" style="text-decoration: none;">
							   			<font style="color: rgb(127,127,127);"><i class="icon-search icon-2x"></i></font>
							   		</a>
								</td>	
							</tr>
						</g:each>
					</tbody>
				</table>					
			</div>
		</div>

		<div class="form-subtitle">Altas Medicas - ALME</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Fecha Otorgamiento</th>
							<th>Tipo Alta Médica</th>
							<th>Revisar</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!almes}">
							<tr>						
								<td colspan="3">No hay Altas Medicas asociadas al siniestro</td>
							</tr>
						</g:if>		 		
						<g:each in="${almes}" var="alme">
							<tr>						
								<td align="center">${FormatosISLHelper.fechaCortaStatic(alme.fechaOtorgamiento)}</td>
								<td>${alme.tipoAlta}</td>
							   	<td align="center">
							   		<a href="#" onclick="document.forms[0].almeId.value='${alme.id}';document.forms[0].action='dp10';document.forms[0].submit();" style="text-decoration: none;">
							   			<font style="color: rgb(127,127,127);"><i class="icon-search icon-2x"></i></font>
							   		</a>
								</td>	
							</tr>
						</g:each>
					</tbody>
				</table>					
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="dp04"  class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
	</div>
</g:form>