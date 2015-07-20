<%@ page import="cl.adexus.helpers.FormatosHelper" %>
			
			<g:form action="edit_cnv" class="pure-form pure-form-stacked" >
				<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
				<g:hiddenField name="convenioId" value="${convenio?.id}"/>
					<legend>Nuevo Convenio</legend>
					<div class="pure-g-r">
						<div class="pure-u-1">	
							
							<div class='salto-de-linea'></div>
							
							<g:if test="${prestadorInstance?.esPersonaJuridica}">
								<isl:textOutput cols="4-8" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaJuridica?.rut)}"/>
							</g:if>
							<g:else>
								<isl:textOutput cols="4-8" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaNatural?.run)}"/>
							</g:else>							

							<g:if test="${prestadorInstance?.esPersonaJuridica}">
								<isl:textOutput cols="4-8" rotulo="Nombre/Razon Social" deshabilitado="true" valor="${prestadorInstance?.personaJuridica?.razonSocial}"/>
							</g:if>
							<g:else>
								<isl:textOutput cols="4-8" rotulo="Nombre/Razon Social" deshabilitado="true" valor="${prestadorInstance?.personaNatural?.nombre}"/>
							</g:else>
							
							<div class='salto-de-linea'></div>
							
							<isl:textOutput cols="4-8" rotulo="Persona" valor="${prestadorInstance?.esPersonaJuridica ? "Juridica" : "Persona"}"/>
							<isl:textOutput cols="4-8" rotulo="Tipo Prestador" valor="${prestadorInstance?.tipoPrestador?.descripcion}"/>
							
							<div class='salto-de-linea'></div>
							<fieldset>
							<legend>Datos Convenio</legend>
							<isl:textOutput cols="3-8" rotulo="Estado" valor="${convenio?.esActivo ? 'Activo' : 'Inactivo'}" />							
							
							<div class='salto-de-linea'></div>
							<isl:textOutput cols="4-8" rotulo="Nombre Convenio" valor="${convenio?.nombre}"/>
							<isl:textOutput cols="4-8" rotulo="Tipo Convenio" valor="${convenio?.tipoConvenio?.descripcion}"/>								
							
							<div class='salto-de-linea'></div>
							
							<isl:textOutput cols="2-8" rotulo="Número Reslución" valor="${convenio?.numeroResolucion}"/>
							<isl:textOutput cols="2-8" rotulo="Fecha de Resolución" valor="${FormatosHelper.fechaCortaStatic(convenio?.fechaResolucion)}"/>

							<isl:textOutput cols="2-8" rotulo="ID Licitación" valor="${convenio?.numeroLicitacion}"/>
							<isl:textOutput cols="2-8" rotulo="Fecha de Adjudicación" valor="${FormatosHelper.fechaCortaStatic(convenio?.fechaAdjudicacion)}"/>
							
							<div class='salto-de-linea'></div>
							
							<isl:textOutput cols="2-8" rotulo="Fecha de Inicio" valor="${FormatosHelper.fechaCortaStatic(convenio?.inicio)}"/>
							<isl:textOutput cols="2-8" rotulo="Fecha de Termino" valor="${FormatosHelper.fechaCortaStatic(convenio?.termino)}"/>
							
							<isl:textOutput cols="2-8" rotulo="Periodo de Reajuste" valor="${convenio?.periodoReajustable}"/>
							<isl:textOutput cols="2-8" rotulo="Fecha próx reajuste" valor="${FormatosHelper.fechaCortaStatic(convenio?.fechaProximoReajuste)}"/>

							<div class='salto-de-linea'></div>
							
							<isl:textOutput cols="2-8" rotulo="% Recargo horario inhabil" valor="${convenio?.recargoHorarioInhabil}"/>
							<isl:textOutput cols="2-8" rotulo="Monto Convenido" valor="${convenio?.montoConvenido}"/>

							<div class='salto-de-linea'></div>
							<fieldset>
							<legend>Responsable Convenio Prestador</legend>
							<isl:textOutput cols="2-8" rotulo="Nombre" valor="${convenio?.nombreResponsable}"/>
							<isl:textOutput cols="2-8" rotulo="Cargo" valor="${convenio?.cargoResponsable}"/>
							<isl:textOutput cols="2-8" rotulo="Teléfono" valor="${convenio?.telefonoResponsable}"/>
							<isl:textOutput cols="2-8" rotulo="Email" valor="${convenio?.emailResponsable}"/>
							</fieldset>
							
							<div class='salto-de-linea'></div>
							<fieldset>
							<legend>Responsable ISL</legend>
							<isl:textOutput cols="2-8" rotulo="Nombre" valor="${convenio?.nombreISL}"/>
							<isl:textOutput cols="2-8" rotulo="Cargo" valor="${convenio?.cargoISL}"/>
							<isl:textOutput cols="2-8" rotulo="Teléfono" valor="${convenio?.numeroResolucion}"/>
							<isl:textOutput cols="2-8" rotulo="Email" valor="${convenio?.emailISL}"/>													
							</fieldset>
							
							<div class='salto-de-linea'></div>
							
							<fieldset>
							<legend>Centros de Salud del Convenio</legend>
							<g:if test="${listaCentrosDeSalud}">
									<table class="pure-table" width="100%">
									<thead>
									<tr>
										<th>Nombre</th>
										<th>Comuna</th>
										<th>Tipo</th>
										<th>Estado</th>
									</tr>
									</thead>
									<g:each var="centroSalud" in="${listaCentrosDeSalud}">
									<tr>
										<td>${centroSalud?.nombre}</td>
										<td>${centroSalud?.comuna?.descripcion}</td>
										<td>${centroSalud?.tipoCentroSalud?.descripcion}</td>
										<td>
											${centroSalud?.esActivo ? 'Activo' : 'Inactivo'}
										</td>
									</tr>
									</g:each>				
									</table>
							</g:if>							
							</fieldset>							
							
							<div class='salto-de-linea'></div>
							
							<fieldset>
							<legend>Arancel de Convenio</legend>
							<div id="dv_aranceles_convenio">
							
								<g:if test="${listaRegistrosArancelesEnConvenio}">
									<div class='salto-de-linea'></div>
							
									<table class="pure-table" width="100%">
										<thead>
											<tr>
												<th>Prestación</th>
												<th>Glosa</th>
												<th>Nivel</th>
												<th>Valor</th>
												<th>Cálculo</th>
												<th>Convenido</th>
												<th>F. Desde</th>
												<th>F. Hasta</th>
											</tr>
										</thead>
										<g:if test="${listaRegistrosArancelesEnConvenio}">
											<g:each in="${listaRegistrosArancelesEnConvenio}" status="i" var="arancelConvenio">
											<tbody>
												<tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
													<td align="center">${arancelConvenio.codigoPrestacion}</td>
													<td title="${arancelConvenio.glosa}">${FormatosHelper.truncateStatic(arancelConvenio.glosa, 70)}</td>
													<td align="center">${arancelConvenio.nivel}</td>
													<td align="right">${FormatosHelper.montoStatic(arancelConvenio.valorOriginal)}</td>
													<td align="right">${arancelConvenio.calculo}</td>
													<td align="right">${FormatosHelper.montoStatic(arancelConvenio.valorNuevo)}</td>
													<td align="center">${FormatosHelper.fechaCortaStatic(arancelConvenio.desde)}</td>
													<td align="center">${FormatosHelper.sumarDiasStatic(arancelConvenio.hasta, -1)}</td>
												</tr>
											</tbody>
											</g:each>
										</g:if>
										<g:else>
											<tr>
												<td colspan="8"> No existen aranceles convenio... </td>
											</tr>
										</g:else>
									</table>
								</g:if>	
							</div>
							
							</fieldset>							
							
						</div>
					</div>
					
					<div class='salto-de-linea'></div>
					
					<div class="pure-g-r">
						<button class="pure-button pure-button-secondary" onclick="document.forms[0].action='listar';document.forms[0].submit();">Volver</button>
					</div>
					
			</g:form>