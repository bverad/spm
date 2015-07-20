<%@ page import="cl.adexus.helpers.FormatosHelper" %>
	<g:form action="edit" class="pure-form pure-form-stacked">
		<fieldset>
			<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
			<legend>Información prestador</legend>
				<div class="pure-g-r">
					<div class="pure-u-1">
						<isl:textOutput cols="1-10" rotulo="Estado" valor="${prestadorInstance?.esActivo ? 'Activado' : 'Desactivado'}" />
						<isl:textOutput cols="1-10" rotulo="Tipo" valor="${prestadorInstance?.esPersonaJuridica ? 'Juridica' : 'Natural'}" />							
														
						<g:if test="${prestadorInstance?.esPersonaJuridica}">
							<isl:textOutput cols="1-10" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaJuridica?.rut)}"/>
						</g:if>
						<g:else>
							<isl:textOutput cols="1-10" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaNatural?.run)}"/>
						</g:else>							
										
						<isl:textOutput cols="1-10" rotulo="Tipo prestador" valor="${prestadorInstance?.tipoPrestador?.descripcion}"/>								
						<g:if test="${prestadorInstance?.esPersonaJuridica}">
							<isl:textOutput cols="3-10" rotulo="Nombre/Razón social" valor="${prestadorInstance?.personaJuridica?.razonSocial}"/>
						</g:if>
						<g:else>
							<isl:textOutput cols="3-10" rotulo="Nombre/Razón social" valor="${prestadorInstance?.personaNatural?.nombre} ${prestadorInstance?.personaNatural?.apellidoPaterno} ${prestadorInstance?.personaNatural?.apellidoMaterno}"/>
						</g:else>
							
						<isl:textOutput cols="4-10" rotulo="Dirección (Casa matriz)" valor="${prestadorInstance?.direccion}"/>
						<isl:textOutput cols="1-10" rotulo="Comuna" valor="${prestadorInstance?.comuna?.descripcion}"/>
						<isl:textOutput cols="1-10" rotulo="Teléfono" valor="${prestadorInstance?.telefono}"/>							
						<isl:textOutput cols="2-10" rotulo="Email" valor="${prestadorInstance?.email}"/>
					</div>
				</div>
				
				
				<div class="form-subtitle">Representante Legal</div>								
				<div class="pure-g-r">
					<div class="pure-u-1">							
						<isl:textOutput cols="2-10" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.representanteLegal?.run)}"/>
						<isl:textOutput cols="2-10" rotulo="Nombre" valor="${prestadorInstance?.representanteLegal?.nombre} ${prestadorInstance?.representanteLegal?.apellidoPaterno} ${prestadorInstance?.representanteLegal?.apellidoMaterno}"/>														
						<isl:textOutput cols="2-10" rotulo="Vigencia desde" valor="${FormatosHelper.fechaCortaStatic(prestadorInstance?.desdeRL)}" />
						<isl:textOutput cols="2-10" rotulo="Vigencia hasta" valor="${FormatosHelper.fechaCortaStatic(prestadorInstance?.hastaRL)}" />
						
					</div>
				</div>
					
				<div class="form-subtitle">Apoderado</div>
				<div class="pure-g-r">
					<div class="pure-u-1">							
						<isl:textOutput cols="2-10" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.apoderado?.run)}"/>
						<isl:textOutput cols="2-10" rotulo="Nombre" valor="${prestadorInstance?.apoderado?.nombre} ${prestadorInstance?.apoderado?.apellidoPaterno} ${prestadorInstance?.apoderado?.apellidoMaterno}"/>							
						<isl:textOutput cols="2-10" rotulo="Vigencia desde" valor="${FormatosHelper.fechaCortaStatic(prestadorInstance?.desdeAP)}" />
						<isl:textOutput cols="2-10" rotulo="Vigencia hasta" valor="${FormatosHelper.fechaCortaStatic(prestadorInstance?.hastaAP)}" />
					</div>
				</div>

				<div class="form-subtitle">Centros de Salud</div>
				<g:hiddenField name="centroSaludId" />
				<table class="pure-table" width="100%">
					<thead>
						<tr>
							<th width="40%">Nombre</th>
							<th width="10%">Comuna</th>
							<th width="25%">Tipo</th>
							<th width="5%">Estado</th>
							<th width="1%" nowrap="nowrap">&nbsp;</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${listaCentrosDeSalud}" status="i" var="centroSalud">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td>
									<g:if test="${centroSalud?.nombre}">${centroSalud?.nombre}</g:if>
								</td>					
								<td>
									<g:if test="${centroSalud?.comuna?.descripcion}">${centroSalud?.comuna?.descripcion}</g:if>
								</td>					
								<td>${centroSalud?.tipoCentroSalud?.descripcion}</td>		
								<td>
									${centroSalud?.esActivo ? 'Activo' : 'Inactivo'}
								</td>										
								<td>
									<button title="Más información" class="pure-button pure-button-success" onclick="document.forms[0].centroSaludId.value='${centroSalud?.id}';document.forms[0].action='ver_cs';document.forms[0].submit();">
										<i class="icon-info-sign"></i>
									</button>
								</td>					
							</tr>
						</g:each>
					</tbody>
				</table>
				
				<div class="form-subtitle">Convenios</div>
				<g:hiddenField name="convenioId" />
				<table class="pure-table" width="100%">
					<thead>
						<tr>
							<th width="40%">Nombre</th>
							<th width="10%">Próx. reajuste</th>
							<th width="25%">Tipo</th>
							<th width="5%">Estado</th>
							<th width="1%">&nbsp;</th>						
						</tr>
					</thead>
					<tbody>
						<g:each in="${listaConvenio}" status="i" var="convenio">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td>
									<g:if test="${convenio?.nombre}">${convenio?.nombre}</g:if>
								</td>					
								<td>
									<g:if test="${convenio?.fechaProximoReajuste}">${FormatosHelper.fechaCortaStatic(convenio?.fechaProximoReajuste)}</g:if>
								</td>					
								<td>${convenio?.tipoConvenio?.descripcion}</td>	
								<td>${convenio?.esActivo ? 'Activo' : 'Inactivo' }</td>				
								<td>
									<button title="Más información" class="pure-button pure-button-success" onclick="document.forms[0].convenioId.value='${convenio?.id}';document.forms[0].action='ver_cnv';document.forms[0].submit();">
										<i class="icon-info-sign"></i>
									</button>
								</td>					
							</tr>
						</g:each>
					</tbody>
				</table>
		</fieldset>
				
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:button tipo="siguiente" value="Editar" action="edit"/>
				<input type="button" class="pure-button pure-button-secondary" onclick="document.forms[0].action='list';document.forms[0].submit();" value="Volver" />
			</div>	
		</div>	
</g:form>