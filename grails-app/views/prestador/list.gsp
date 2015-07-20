<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
		<g:form action="index" class="pure-form pure-form-stacked">
			<g:hiddenField name="id"/>
			
			<fieldset>
			<legend>Filtros</legend>
			<isl:textInput cols="2-8" tipo="text" nombre="run" rotulo="RUN/RUT" valor="${run}"/>
			<isl:textInput cols="2-8" tipo="text" nombre="nombre" rotulo="Nombre" valor="${nombre}"/>
			<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}"  rotulo="Tipo prestador" nombre="tipoPrestador" from="${tipoPrestadorList}" valor="${tipoPrestador}"/>
			<div style="height: 20px;"></div>
			<button class="pure-button pure-button-secondary" id="btnFiltrar">Filtrar</button>
			</fieldset>
		
			<fieldset>
			<div id="list-prestador" class="content scaffold-list" role="main">
				<legend>Prestadores</legend>

				<table class="pure-table" width="100%">
					<thead>
						<tr>
							<th>RUT/RUN</th>
							<th>Nombre</th>
							<th>Tipo prestador</th>
							<th>Estado</th>
							<th width="127px">Acción</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${prestadorInstanceList}" status="i" var="prestadorInstance">
							<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">		
								<td align="center">
									<g:if test="${prestadorInstance?.personaNatural?.run}">
										${FormatosISLHelper.runFormatStatic(prestadorInstance?.personaNatural?.run)}
									</g:if>
									<g:else>
										${FormatosISLHelper.runFormatStatic(prestadorInstance?.personaJuridica?.rut)}
									</g:else>
								</td>					
								<td>
									<g:if test="${prestadorInstance?.personaNatural?.run}">
										${FormatosISLHelper.truncateStatic(FormatosISLHelper.nombreCompletoStatic(prestadorInstance?.personaNatural), 70)}
									</g:if>
									<g:else>
										${FormatosISLHelper.truncateStatic(prestadorInstance?.personaJuridica?.razonSocial, 70)}
									</g:else>						
								</td>					
								<td>${prestadorInstance?.tipoPrestador?.descripcion}</td>					
								<td>
									${prestadorInstance?.esActivo ? 'Activo' : 'Inactivo'}
								</td>
								<td nowrap="nowrap">
									<button title="Editar" class="pure-button pure-button-success" onclick="document.forms[0].id.value='${prestadorInstance?.id}';document.forms[0].action='edit';document.forms[0].submit();">
										<i class="icon-wrench"></i>
									</button>
									<button title="Ver" class="pure-button pure-button-secondary" onclick="document.forms[0].id.value='${prestadorInstance?.id}';document.forms[0].action='ver';document.forms[0].submit();">
										<i class="icon-info-sign"></i>
									</button>
								</td>					
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate next="Siguiente" prev="Previo" maxsteps="0" controller="Prestador" action="list" params="${params}" total="${prestadorInstanceTotal}" />
				</div>
			</div>
			</fieldset>
			
			<g:if test="${flash.default}">
			<fieldset>
				<legend>Mensaje : </legend>
				<div class="pure-u-1 messages">
					<ul>
						<li><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></li>
					</ul>
				</div>
			</fieldset>
		</g:if>
		
		</g:form>
