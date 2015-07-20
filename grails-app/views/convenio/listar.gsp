<%@ page import="cl.adexus.helpers.FormatosHelper" %>
		<g:form action="index" class="pure-form pure-form-stacked">
			<g:hiddenField name="id"/>
			
			<fieldset>
			<legend>Listar Convenios</legend>
			
			<isl:textInput cols="1-8" tipo="text" nombre="rut" rotulo="RUT Prestador" valor="${rut}"/>
			<isl:textInput cols="1-8" tipo="text" nombre="licitacion" rotulo="N° Licitación" valor="${licitacion}"/>
			<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Tipo Convenio" nombre="tipoConvenio" from="${listaTipoConvenio}" valor="${tipoConvenio}"/>
			<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}"  rotulo="Tipo prestador" nombre="tipoPrestador" from="${listaTipoPrestador}" valor="${tipoPrestador}"/>
			
			<div style="height: 20px;"></div>
			
			<isl:button tipo="volver" value="Filtrar" action="listar"/>
			
			</fieldset>
		
			<fieldset>
			<div id="list-prestador" class="content scaffold-list" role="main">
				<legend>Convenios</legend>

				<table class="pure-table" width="100%">
					<thead>
						<tr>
							<th>RUT</th>
							<th>Prestador</th>
							<th>Licitación</th>
							<th>Tipo Convenio</th>
							<th>Estado Convenio</th>
							<th>Prox. Reajuste</th>
							<th>Término</th>
							<th nowrap="nowrap">Acción</th>
						</tr>
					</thead>
					<tbody>
					<g:if test="${listaConvenios == null}">
						<tr  class="pure-table-odd'}">
							<td> No existen convenios... </td>
					</tr>
					</g:if>
					<g:each in="${listaConvenios}" status="i" var="convenio">
					<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
						<td>
							<g:if test="${convenio?.prestador?.personaNatural?.run}">${FormatosHelper.runFormatStatic(convenio?.prestador?.personaNatural?.run)}</g:if>
							<g:else>${FormatosHelper.runFormatStatic(convenio?.prestador?.personaJuridica?.rut)}</g:else>
						</td>
						<td>
							<g:if test="${convenio?.prestador?.personaNatural}">${convenio?.prestador?.personaNatural?.nombre + " " + convenio?.prestador?.personaNatural?.apellidoPaterno + " " + convenio?.prestador?.personaNatural?.apellidoMaterno}</g:if>
							<g:else>${convenio?.prestador?.personaJuridica?.razonSocial}</g:else>
						</td>
						<td>${convenio?.numeroLicitacion ? convenio?.numeroLicitacion : ''}</td>
						<td>${convenio?.tipoConvenio ? convenio?.tipoConvenio?.descripcion : ''}</td>
						<td>${convenio?.esActivo ? 'Activo' : 'Inactivo'}</td>
						<td>${convenio?.fechaProximoReajuste ? FormatosHelper.fechaCortaStatic(convenio?.fechaProximoReajuste) : ''}</td>
						<td>${convenio?.termino ? FormatosHelper.fechaCortaStatic(convenio?.termino) : ''}</td>
						<td>
						<!--  
							<button title="Editar" class="pure-button pure-button-success" onclick="document.forms[0].id.value='${convenio?.id}';document.forms[0].action='edit';document.forms[0].submit();">
								<i class="icon-wrench"></i>
							</button>
						-->
							<button title="Ver" class="pure-button pure-button-secondary" onclick="document.forms[0].id.value='${convenio?.id}';document.forms[0].action='ver';document.forms[0].submit();">
								<i class="icon-info-sign"></i>
							</button>
						</td>
					</tr>
					</g:each>	
					</tbody>
				</table>	
				<div class="salto-de-linea"></div>
				<!--
				<button title="Imprimir" class="pure-button pure-button-error" onclick="PrintContent();">
					Imprimir
				</button>
				
				  
				<isl:button tipo="cancelar" value="Imprimir Listado" action="imprimir"/>
				-->		
			</div>
			</fieldset>
		</g:form>
		<script>
		function PrintContent()
	    {
	        var DocumentContainer = document.getElementById('list-prestador');
			
	        
	        var WindowObject = window.open('', "Impresión", 
	                              "width=740,height=325,top=200,left=250,toolbars=no,scrollbars=yes,status=no,resizable=no");
	        WindowObject.document.writeln(data);
	        WindowObject.document.close();
	        WindowObject.focus();
	        WindowObject.print();
	        WindowObject.close();
	    }
		</script>
		