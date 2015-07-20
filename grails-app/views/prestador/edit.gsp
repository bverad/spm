<g:javascript src="Prestador/edit_prestador.js" />
<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form action="update" class="pure-form pure-form-stacked">
	<fieldset>
		<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
		<legend>Editar Prestador</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">	
				<isl:radiogroup cols="2-10" requerido="true"  nombre="esActivo" name="esActivo" labels="['Activado','Desactivado']" values="[true,false]" valor="${prestadorInstance?.esActivo}"  rotulo="Estado">
					${it.label}
					${it.radio}
				</isl:radiogroup>
													
				<isl:radiogroup cols="2-10" requerido="true"  nombre="esPersonaJuridica" name="esPersonaJuridica" labels="['Jurídica','Natural']" values="[true,false]" valor="${prestadorInstance?.esPersonaJuridica}" rotulo="Tipo persona" deshabilitado="true">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<isl:combo cols="3-10" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Estructura juridica" nombre="estructuraJuridica" from="${estructuraJuridica}" valor="${prestadorInstance?.estructuraJuridica?.codigo}" deshabilitado="true"/>
				<isl:combo cols="3-10" requerido="true" noSelection="${['':'Seleccione ...']}"  rotulo="Tipo prestador" nombre="tipoPrestador" from="${tipoPrestador}" valor="${prestadorInstance?.tipoPrestador?.codigo}"/>		
				
				<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="rut" rotulo="RUT" valor="${prestadorInstance?.personaJuridica?.rut}" style="display: none" deshabilitado="true"/>
				<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="run" rotulo="RUN" valor="${prestadorInstance?.personaNatural?.run}" style="display: none" deshabilitado="true"/>

				<isl:textInput cols="3-10" requerido="true" tipo="text" nombre="razonSocial" rotulo="Razón social" valor="${prestadorInstance?.personaJuridica?.razonSocial}" style="display: none" deshabilitado="true"/>
				<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="nombre" rotulo="Nombre" valor="${prestadorInstance?.personaNatural?.nombre}" style="display: none" deshabilitado="true"/>
				<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="apePaterno" rotulo="Apellido paterno" valor="${prestadorInstance?.personaNatural?.apellidoPaterno}" style="display: none" deshabilitado="true"/>
				<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="apeMaterno" rotulo="Apellido materno" valor="${prestadorInstance?.personaNatural?.apellidoMaterno}" style="display: none" deshabilitado="true"/>
				
				<isl:textInput cols="2-10" requerido="true" tipo="text" nombre="direccion" rotulo="Dirección (Casa matriz)" valor="${prestadorInstance?.direccion}"/>

				<isl:combo cols="1-10" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Comuna" nombre="comuna" from="${comunas}" valor="${prestadorInstance?.comuna?.codigo}"/>
				<isl:textInput cols="1-10" requerido="true" tipo="numero" nombre="telefono" rotulo="Teléfono" valor="${prestadorInstance?.telefono}"/>
						
				<isl:textInput cols="2-10" requerido="true" tipo="text" nombre="email" rotulo="Email" valor="${prestadorInstance?.email}"/>
			</div>
		</div>
											
 		<div class="form-subtitle">Representante Legal</div>
		<div class="pure-g-r">
			<div class="pure-u-1">							
				<g:if test="${prestadorInstance?.representanteLegal?.run}">
					<isl:textInput cols="1-8" tipo="text" nombre="runRL" rotulo="RUN" valor="${FormatosHelper.isValidRutStatic(prestadorInstance?.representanteLegal?.run) ? FormatosHelper.runFormatStatic(prestadorInstance?.representanteLegal?.run) : prestadorInstance?.representanteLegal?.run}"/>
				</g:if>
				<g:else>
					<isl:textInput cols="1-8" tipo="text" nombre="runRL" rotulo="RUN" valor=""/>
				</g:else>	
						
				<isl:textInput cols="2-8" tipo="text" nombre="nombreRL" rotulo="Nombre" valor="${prestadorInstance?.representanteLegal?.nombre}"/>							
				<isl:textInput cols="2-8" tipo="text" nombre="paternoRL" rotulo="Apellido paterno" valor="${prestadorInstance?.representanteLegal?.apellidoPaterno}"/>							
				<isl:textInput cols="1-8" tipo="text" nombre="maternoRL" rotulo="Apellido materno" valor="${prestadorInstance?.representanteLegal?.apellidoMaterno}"/>							
											
				<isl:calendar cols="1-8" nombre="desdeRL" tipo="calendar" rotulo="Vigencia desde"  valor="${prestadorInstance?.desdeRL}" />
				<isl:calendar cols="1-8" nombre="hastaRL" tipo="calendar" rotulo="Vigencia hasta"  valor="${prestadorInstance?.hastaRL}" />
			</div>
		</div>
 	
		<div class="form-subtitle">Apoderado</div>
		<div class="pure-g-r">
			<div class="pure-u-1">							
				<g:if test="${prestadorInstance?.apoderado?.run}">
					<isl:textInput cols="1-8" tipo="text" nombre="runAP" rotulo="RUN" valor="${FormatosHelper.isValidRutStatic(prestadorInstance?.apoderado?.run) ? FormatosHelper.runFormatStatic(prestadorInstance?.apoderado?.run) : prestadorInstance?.apoderado?.run}"/>
				</g:if>
				<g:else>
					<isl:textInput cols="1-8" tipo="text" nombre="runAP" rotulo="RUN" valor=""/>
				</g:else>	
						
				<isl:textInput cols="2-8" tipo="text" nombre="nombreAP" rotulo="Nombre" valor="${prestadorInstance?.apoderado?.nombre}"/>							
				<isl:textInput cols="2-8" tipo="text" nombre="paternoAP" rotulo="Apellido paterno" valor="${prestadorInstance?.apoderado?.apellidoPaterno}"/>							
				<isl:textInput cols="1-8" tipo="text" nombre="maternoAP" rotulo="Apellido materno" valor="${prestadorInstance?.apoderado?.apellidoMaterno}"/>							
											
				<isl:calendar cols="1-8" nombre="desdeAP" tipo="calendar" rotulo="Vigencia desde"  valor="${prestadorInstance?.desdeAP}" />
				<isl:calendar cols="1-8" nombre="hastaAP" tipo="calendar" rotulo="Vigencia hasta"  valor="${prestadorInstance?.hastaAP}" />
				
			</div>
		</div>
		
		<div class="form-subtitle">Centros de Salud 
			<button class="pure-button button-tool" onclick="document.forms[0].action='create_cs';document.forms[0].submit();">
    			<i class="icon-plus"></i> Agregar Centro de Salud
			</button>
		</div>			
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
						<td nowrap="nowrap">
							<button title="Editar" class="pure-button pure-button-success" onclick="document.forms[0].centroSaludId.value='${centroSalud?.id}';document.forms[0].action='edit_cs';document.forms[0].submit();"><i class="icon-wrench"></i></button>
							<button title="Eliminar" class="pure-button pure-button-error" onclick="document.forms[0].centroSaludId.value='${centroSalud?.id}';document.forms[0].action='delete_cs';document.forms[0].submit();"><i class="icon-minus-sign"></i></button>
						</td>					
					</tr>
				</g:each>
			</tbody>
		</table>

		<div class="form-subtitle">Convenios 
			<button class="pure-button button-tool" onclick="document.forms[0].action='create_cnv';document.forms[0].submit();">
    			<i class="icon-plus"></i> Agregar Convenio
			</button>
		</div>
		
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
						<td nowrap="nowrap">
							<button title="Editar" name="editarConvenio" class="pure-button pure-button-success" onclick="document.forms[0].convenioId.value='${convenio?.id}';document.forms[0].action='edit_cnv';document.forms[0].submit();">
								<i class="icon-wrench">
							</i></button>
							<button title="Eliminar" name="eliminarConvenio" class="pure-button pure-button-error" onclick="document.forms[0].convenioId.value='${convenio?.id}';document.forms[0].action='delete_cnv';document.forms[0].submit();">
								<i class="icon-minus-sign"></i>
							</button>
							
						</td>					
					</tr>
				</g:each>
			</tbody>
		</table>
	</fieldset>	
			
	<!-- <div class="pure-g-r"> -->
		<!-- <div class="pure-u-1">-->
			<!--<isl:button tipo="siguiente" value="Actualizar" action="update"/>
			<isl:button tipo="terminar" value="Eliminar" action="delete"/>-->
	<div class="pure-g-r">
		<div class="pure-u-1">
			<g:actionSubmit value="Actualizar" action="update" class="pure-button pure-button-error"/>
			<g:actionSubmit value="Eliminar" action="delete" onclick="return confirm('¿Esta seguro de borrar el prestador ingresado?')" class="pure-button pure-button-success"/>
			<input type="button" class="pure-button pure-button-secondary" onclick="document.location='list';" value="Volver" />
		</div>
	</div>
			
			
		<!-- </div>-->
	<!-- </div> -->


	<!-- Despliegue de informacion -->
	<g:if test="${flash.default}">
		<fieldset>
			<div class="pure-u-1 messages">
				<ul>
					<li><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></li>
				</ul>
			</div>
		</fieldset>
	</g:if>
	
	
</g:form>
<div id="js_msg">
</div>