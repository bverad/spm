
<%@ page import="cl.adexus.isl.spm.TipoConceptoReembolso" %>
<!DOCTYPE html>

<g:form class="pure-form pure-form-stacked">

	<g:set var="entityName" value="${message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso')}" />

	<fieldset>
		<legend><g:message code="default.list.label" args="[entityName]" /></legend>
		
		<table class="pure-table" width="50%">
			<thead>
				<tr>
					<g:sortableColumn property="codigo" title="${message(code: 'tipoConceptoReembolso.codigo.label', default: 'Código')}" />
					<g:sortableColumn property="descripcion" title="${message(code: 'tipoConceptoReembolso.descripcion.label', default: 'Descripción')}" />
				</tr>
			</thead>
			<tbody>
			<g:each in="${tipoConceptoReembolsoInstanceList}" status="i" var="tipoConceptoReembolsoInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
				
					<td><g:link action="show" id="${tipoConceptoReembolsoInstance?.codigo}">${fieldValue(bean: tipoConceptoReembolsoInstance, field: "codigo")}</g:link></td>
					<td>${fieldValue(bean: tipoConceptoReembolsoInstance, field: "descripcion")}</td>
				
				</tr>
			</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${tipoConceptoReembolsoInstanceTotal}" />
		</div>
	</fieldset>
	
	<div class="pure-g-r">
		<div class="pure-u-1">
			<isl:button action="create" tipo="siguiente" value="${message(code: 'default.new.label', args: [entityName])}" />
		</div>			
	</div>		
		
	<g:if test="${flash.message}">
		<div class="pure-u-1 messages" role="status">
		<ul><li>${flash.message}</li></ul>
		</div>
	</g:if>
</g:form>
