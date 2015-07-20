<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FechaHoraHelper" %>
<g:javascript src="Seguimiento/dp04.js" />
<g:form class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="backTo" value="${params?.action}"/>
	<g:hiddenField name="backToController" value="${params?.controller}"/>
	<g:hiddenField name="actividadSeguimientoId" />
	<g:hiddenField name="diagnosticoId" />
	<g:hiddenField name="antecedenteId" />
	<g:hiddenField name="estado" value="${estado}" />
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="dp14" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	
	<fieldset>
 		<legend>Historial</legend>
 		<div class="form-subtitle"> Datos Siniestro </div>
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
		
		<div class="form-subtitle">Historial
			<g:if test="${estado == 'VIGENTE'}">
				<button class="pure-button button-tool" onclick="document.forms[0].siniestroId.value='${siniestro?.id}';document.forms[0].action='../seguimiento/dp05';document.forms[0].submit();">
					<i class="icon-plus"></i> Registrar Actividad
				</button>
			</g:if>
		</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Tipo Actividad</th>
							<th>Fecha</th>
							<th>Resumen</th>
							<th>Tiene adjuntos</th>
							<th>Ver</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!historiales}">
							<tr>						
								<td colspan="5">No hay Historial asociado a este Siniestro...</td>								   	
							</tr>
						</g:if>
						<g:each in="${historiales}" var="hist">	
							<tr>						
								<td>${hist.tipoActividad.descripcion}
									<g:if test="${hist.tipoActividad.codigo == '7'}">
										 - ${hist.otro}
									</g:if> 
								</td>								   	
							   	<td align="center">${FormatosISLHelper.fechaCortaStatic(hist.fechaActividad)}</td>
							   	<td title="${hist.resumen}">${FormatosISLHelper.truncateStatic(hist.resumen)}</td>
							   	<td align="center">${hist.documentacion.size() > 0 ? 'Sí' : 'No'}</td>
							   	<td align="center">
							   		<a href="#" title="Ver Historial" onclick="document.forms[0].actividadSeguimientoId.value='${hist?.id}';document.forms[0].action='../seguimiento/dp06';document.forms[0].submit();" style="text-decoration: none;">
							   			<font style="color: rgb(127,127,127);"><i class="icon-search icon-2x"></i></font>
							   		</a>
							   	</td>
							</tr>
						</g:each>
					</tbody>
				</table>					
			</div>
		</div>

		<g:if test="${estado == 'VIGENTE'}">
			<div class="form-subtitle">Pronósticos / Comentarios</div>
			<div class="pure-g-r">
				<div class="pure-u-1">
				
					<isl:textInput cols="5-8" rotulo="Comentario" nombre="comentario" valor="" />
					<div style="height: 19px;"></div>
					<button class="pure-button pure-button-secondary" id="btnAgregarComentario" formaction="../seguimiento/postDp04Comentario" >Agregar</button>
				
					<table style="table-layout:fixed" class="pure-table pure-table-bordered pure-table-striped"  width="100%">
						<thead>
							<tr>
								<th width="100px">Fecha</th>
								<th >Pronósticos / Comentarios</th>
								<th width="150px">Autor</th>
							</tr>
						</thead>
						<tbody>
							<g:if test="${!pronosticos}">
								<tr>						
									<td colspan="3">No hay Pronósticos / Comentarios asociado a este Siniestro...</td>								   	
								</tr>
							</g:if>
							<g:each in="${pronosticos}" var="pron">	
								<tr>
									<td align="center">${FormatosISLHelper.fechaCortaStatic(pron.fecha)}</td>								   	
								   	<td style="word-wrap:break-word;">${pron.comentario}</td>
								   	<td>${pron.creadoPor}</td>
								</tr>
							</g:each>
						</tbody>
					</table>					
				</div>
			</div>
		</g:if>

		<div class="form-subtitle">Antecedentes Adicionales
			<g:if test="${estado == 'VIGENTE'}">
				<button class="pure-button button-tool" onclick="document.forms[0].volverSeguimiento.value='${volverSeguimiento}';document.forms[0].action='../seguimiento/postDp04Antecedentes';document.forms[0].submit();">
					<i class="icon-plus"></i> Solicitar más Antecedentes
				</button>
			</g:if>
		</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Tipo Antecedente</th>
							<th>Responsable</th>
							<th>Solicitud</th>
							<th>Días Transcurridos</th>
							<th>Estado</th>
							<th width="1%">Ver</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!antecedentes}">
							<tr>						
								<td colspan="6">No hay Antecedentes Adicionales asociado a este Siniestro...</td>								   	
							</tr>
						</g:if>
						<g:each in="${antecedentes}" var="d">	
							<tr>
								<td>${d.tipoAntecedente.descripcion}</td>
	                    		<td>${d?.regionResponsable ? d.regionResponsable.descripcion : 'Nacional'}</td>
	                   			<td>${FormatosISLHelper.truncateStatic(d.solicitud,70)}</td>  
	                   			<td>${(d.fechaSolicitud == 1)?new Date() - d.fechaSolicitud + " Día":new Date() - d.fechaSolicitud + " Días"}</td>       
	                 		  	<td>${d.estado? 'Entregado' : 'Pendiente'}</td>								   	
							   	<td>
							   		<button class="pure-button pure-button-none" onclick="document.forms[0].antecedenteId.value='${d?.id}'; document.forms[0].action='../seguimiento/postDp04VerSolicitudes';document.forms[0].submit();"><font style="color: rgb(127,127,127);"><i class="icon-search icon-2x"></i></button> 
							   	</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>

		<div class="form-subtitle">Diagnósticos
			<g:if test="${estado == 'VIGENTE'}">
				<button class="pure-button button-tool" onclick="document.forms[0].volverSeguimiento.value='${volverSeguimiento}';document.forms[0].action='../calOrigenAT/dp05';document.forms[0].submit();">
					<i class="icon-plus"></i> Agregar Diagnóstico
				</button>
			</g:if>
		</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
					<thead>
						<tr>
							<th>Laboral</th>
							<th>Diagnóstico</th>
							<th>Parte</th>
							<th>Lateralidad</th>
							<th>CIE-10</th>
							<th>Fecha Ingreso</th>
							<th>Editar</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!diagnosticos}">
							<tr>
								<td colspan="7">No hay Diagnósticos asociados a este Siniestro...</td>
							</tr>
						</g:if>
						<g:each in="${diagnosticos}" var="d">	
							<tr>						
								<td align="center">${d.esLaboral ? 'Sí' : 'No'}</td>								   	
							   	<td title="${d.diagnostico}">${FormatosISLHelper.truncateStatic(d.diagnostico)}</td>	
							   	<td title="${d.parte.descripcion}">${FormatosISLHelper.truncateStatic(d.parte.descripcion)}</td>
							   	<td>${d.lateralidad.descripcion}</td>
							   	<td align="center">${d.cie10.codigo}</td>
							   	<td align="center">${FormatosISLHelper.fechaCortaStatic(d.fechaDiagnostico)}</td>
							   	<td align="center">
							   		<g:if test="${d?.desdeSeguimiento && estado == 'VIGENTE'}">
								   		<a href="#" title="Editar Diagnóstico" onclick="document.forms[0].diagnosticoId.value='${d.id}';document.forms[0].volverSeguimiento.value='${volverSeguimiento}';document.forms[0].action='../calOrigenAT/dp06';document.forms[0].submit();" style="text-decoration: none;">
								   			<font style="color: rgb(127,127,127);"><i class="icon-edit icon-2x"></i></font>
								   		</a>
						   			</g:if>
							   	</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="${volverHistorial}"  class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
		<g:if test="${volverSeguimiento != 'dp11'}">
			<g:actionSubmit id="btnDp06" action="dp12"  class="pure-button pure-button-secondary" value="Administrar Seguimiento" />
		</g:if>
		
		<g:actionSubmit id="btnDp07" action="dp07"  class="pure-button pure-button-secondary" value="Revisar Reposos y Altas" />

		<g:if test="${estado == 'VIGENTE'}">
			<g:actionSubmit id="alta" action="cu04Alta" class="pure-button pure-button-warning"   value="Dar Alta de Seguimiento" />
		</g:if>
		<%-->button class="pure-button pure-button-warning" id="alta2" >Dar Alta de Seguimiento 2</button--%>
	</div>
</g:form>