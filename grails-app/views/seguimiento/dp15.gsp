<%@ page import="cl.adexus.helpers.FormatosHelper" %>


<g:javascript src="Seguimiento/dp15.js" />
	<g:form action="postDp15" enctype="multipart/form-data" method="post" class="pure-form pure-form-stacked">
		<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
		<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
		<g:hiddenField name="verDetalle" value="dp14" />
		<g:hiddenField name="origen" value="${origen}" />
		<g:hiddenField name="cesarODA" value="${cesarODA}" />
		
		<fieldset>
			<legend>Cargar Reposos y Altas</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:combo cols="1-8" noSelection="${['':'Seleccione ...']}" rotulo="Tipo Documento" nombre="tipoDocumento" from="${tipoDocumento}" valor="${tipoDocumentoSeleccionado}"/>
					<isl:upload cols="3-8" nombre="fileAranceles" tipo="arancel" rotulo="Seleccionar Archivo"/>
	
	
	
				</div>
			</div>
		</fieldset>
		<isl:button action="postDp15" tipo="excel" value="Cargar Documento" />
		
		<div class='salto-de-linea'></div>
		<g:if test="${tipoDocumentoSeleccionado=="ALLA"}">
				<div class='salto-de-linea'></div>
				<g:if test="${totalRegistros != null}">Total de registros procesados: ${totalRegistros}</g:if>
				<div class='salto-de-linea'></div>
				<table id="dv_lista_alla" class="pure-table" width="100%">
					<thead>
						<tr>
							<th>Estado</th>
							<th>Siniestro</th>							
							<th>Fecha Alta</th>
							<th>Alta Inmediata</th>
							<th>Condiciones</th>
							<th>Tipo Condicion</th>
							<th>Periodo</th>
							<th>Continua Tratamiento</th>
							<th>Tipo Tratamiento</th>
							<th>Médico</th>
							<th>Diagnóstico</th>
							<th>Código Diagnóstico</th>
							<th>Ubicación Diagnóstico</th>
							<th>Fecha Diagnóstico</th>
							<th>Detalle</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${listaRegistros}">
							<g:each in="${listaRegistros}" status="i" var="alla">
								<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
									<td align="center" style="color:${alla?.status == "ERROR"? 'red' : 'green'}">${alla?.status}</td>
									<td align="center">${alla?.idSiniestro}</td>
									<td align="center">${FormatosHelper.fechaCortaStatic(alla?.fechaAltaLaboral)}</td>
									<td align="center">${alla?.altaInmediata}</td>
									<td align="center">${alla?.condiciones}</td>
									<td align="center">${alla?.tipoCondiciones}</td>
									<td align="center">${alla?.periodoCondiciones}</td>
									<td align="center">${alla?.continuaTratamiento}</td>
									<td align="center">${alla?.tipoTratamiento}</td>
									<td align="center">${alla?.runMedico} ${alla?.nombreMedico} ${alla?.apellidoPaternoMedico} ${alla?.apellidoMaternoMedico}</td>
									<td align="center">${alla?.diagnostico}</td>
									<td align="center">${alla?.codigoDiagnostico}</td>
									<td align="center">${alla?.codigoUbicacionDiagnostico}</td>
									<td align="center">${FormatosHelper.fechaCortaStatic(alla?.fechaDiagnostico)}</td>
									<td align="center">${alla?.detalle}</td>
								</tr>
							</g:each>
						</g:if>
					</tbody>
				</table>
		</g:if>		
		
		<g:if test="${tipoDocumentoSeleccionado=="ALME"}">
				<div class='salto-de-linea'></div>
				<g:if test="${totalRegistros != null}">Total de registros procesados: ${totalRegistros}</g:if>
				<div class='salto-de-linea'></div>
				<table id="dv_lista_alla" class="pure-table" width="100%">
					<thead>
						<tr>
							<th>Estado</th>
							<th>Siniestro</th>
							<th>Fecha Otorgamiento</th>
							<th>Tipo Alta</th>
							<th>Motivo Alta</th>
							<th>Indicación Evaluación</th>
							<th>Médico</th>
							<th>Diagnóstico</th>
							<th>Código Diagnóstico</th>
							<th>Ubicación Diagnóstico</th>
							<th>Fecha Diagnóstico</th>
							<th>Detalle</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${listaRegistros}">
							<g:each in="${listaRegistros}" status="i" var="alme">
								<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
									<td align="center" style="color:${alme?.status == "ERROR"? 'red' : 'green'}">${alme?.status}</td>
									<td align="center">${alme?.idSiniestro}</td>
									<td align="center">${FormatosHelper.fechaCortaStatic(alme?.fechaOtorgamiento)}</td>
									<td align="center">${alme?.tipoAlta}</td>
									<td align="center">${alme?.motivoAlta}</td>
									<td align="center">${alme?.indicacionEvaluacion}</td>
									<td align="center">${alme?.runMedico} ${alme?.nombreMedico} ${alme?.apellidoPaternoMedico} ${alme?.apellidoMaternoMedico}</td>
									<td align="center">${alme?.diagnostico}</td>
									<td align="center">${alme?.codigoDiagnostico}</td>
									<td align="center">${alme?.codigoUbicacionDiagnostico}</td>
									<td align="center">${FormatosHelper.fechaCortaStatic(alme?.fechaDiagnostico)}</td>
									<td align="center">${alme?.detalle}</td>
								</tr>
							</g:each>
						</g:if>
					</tbody>
				</table>
		</g:if>		

		<g:if test="${tipoDocumentoSeleccionado=="RELA"}">
				<div class='salto-de-linea'></div>
				<g:if test="${totalRegistros != null}">Total de registros procesados: ${totalRegistros}</g:if>
				<div class='salto-de-linea'></div>
				<table id="dv_lista_alla" class="pure-table" width="100%">
					<thead>
						<tr>
							<th>Estado</th>
							<th>Siniestro</th>
							<th>Fecha Inicio</th>
							<th>Alta Termino</th>
							<th>Total Días</th>
							<th>Médico</th>
							<th>Diagnóstico</th>
							<th>Código Diagnóstico</th>
							<th>Ubicación Diagnóstico</th>
							<th>Fecha Diagnóstico</th>
							<th>Detalle</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${listaRegistros}">
							<g:each in="${listaRegistros}" status="i" var="rela">
								<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
									<td align="center" style="color:${rela?.status == "ERROR"? 'red' : 'green'}">${rela?.status}</td>
									<td align="center">${rela?.idSiniestro}</td>
									<td align="center">${FormatosHelper.fechaCortaStatic(rela?.fechaInicioIncapacidad)}</td>
									<td align="center">${FormatosHelper.fechaCortaStatic(rela?.fechaTerminoIncapacidad)}</td>
									<td align="center">${rela?.nDias}</td>
									<td align="center">${rela?.runMedico} ${rela?.nombreMedico} ${rela?.apellidoPaternoMedico} ${rela?.apellidoMaternoMedico}</td>
									<td align="center">${rela?.diagnostico}</td>
									<td align="center">${rela?.codigoDiagnostico}</td>
									<td align="center">${rela?.codigoUbicacionDiagnostico}</td>
									<td align="center">${FormatosHelper.fechaCortaStatic(rela?.fechaDiagnostico)}</td>
									<td align="center">${rela?.detalle}</td>
								</tr>
							</g:each>
						</g:if>
					</tbody>
				</table>
		</g:if>



	<div class='salto-de-linea'></div>
		<g:if test="${errorMessage}">
			<div class="pure-u-1 messages">
				<ul><li>${errorMessage}</li></ul>
			</div>
		</g:if>
		
	</g:form>
	
	
	
	
<!--  MODAL reporte errores-->
<div style="top: -300px; letter-spacing: 0em; display: none;">
	<div id="modalreporte-errores" class="yui-pe-content">
		<div id="modal-marco">
			<div>
				<div id="modal-content">
					<isl:textOutput cols="2-8" rotulo="Folio Factura" valor="111111111" />
					<isl:textOutput cols="2-8" rotulo="RUT Prestador" valor="111111111" />
					<isl:textOutput cols="2-8" rotulo="Nombre Prestador" valor="Cloto" />
					<div class='salto-de-linea'></div>

					<isl:textOutput cols="3-8" rotulo="Email" valor="asdf@asdf.cl" />
					<isl:textOutput cols="1-8" rotulo="Teléfono" valor="98521258" />
					<div class='salto-de-linea'></div>

					<label style="margin: 0 10px 0 10px;">Comentarios:</label>
					<div style="margin: 0 10px 0 10px;">
						<textarea id="modal-content-textarea-re"
							style="resize: none; height: 100%; width: 100%;"></textarea>
					</div>
				</div>
				<br />

				<div style="margin: 0 10px 0 10px;">
					<table id="tablaReporteErrores" class="pure-table" width="100%"
						style="background-color: #cbcbcb;">
						<thead>
							<tr>
								<th>Detalle errores</th>
							</tr>
						</thead>

						</tbody>
							<tr>
								<td>Un error de prueba</td>
							</tr>
						</tbody>
						
						
						<tfoot>
		
						</tfoot>
					</table>
				</div>
				
			</div>
		</div>
	</div>
</div>


