
<%@ page import="cl.adexus.isl.spm.EstructuraJuridica" %>
<!DOCTYPE html>

<g:form class="pure-form pure-form-stacked">

	<g:set var="entityName" value="${message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica')}" />

	<fieldset>
		<legend><g:message code="default.list.label" args="[entityName]" /></legend>
		
		<table class="pure-table" width="50%">
			<thead>
				<tr>
				
					<g:sortableColumn property="codigo" title="${message(code: 'estructuraJuridica.codigo.label', default: 'Código')}" />
					<g:sortableColumn property="descripcion" title="${message(code: 'estructuraJuridica.descripcion.label', default: 'Descripción')}" />
				
				</tr>
			</thead>
			<tbody>
			<g:each in="${estructuraJuridicaInstanceList}" status="i" var="estructuraJuridicaInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
				
					<td><g:link action="show" id="${estructuraJuridicaInstance.codigo}">${fieldValue(bean: estructuraJuridicaInstance, field: "codigo")}</g:link></td>
				
					<td>${fieldValue(bean: estructuraJuridicaInstance, field: "descripcion")}</td>
				
				</tr>
			</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${estructuraJuridicaInstanceTotal}" />
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
