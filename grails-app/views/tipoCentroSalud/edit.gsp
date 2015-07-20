<%@ page import="cl.adexus.isl.spm.TipoCentroSalud" %>

<g:form action="save" class="pure-form pure-form-stacked">
	<g:set var="entityName" value="${message(code: 'tipoCentroSalud.label', default: 'Tipo Centro de Salud')}" />
	
	<fieldset>
		<legend><g:message code="default.show.label" args="[entityName]" /></legend>
		
		<g:render template="form" model="['edit': true]"/>
	</fieldset>
	
	<div class="pure-g-r">
		<div class="pure-u-1">
			<g:hiddenField name="id" value="${tipoCentroSaludInstance?.codigo}" />
			<g:hiddenField name="version" value="${tipoCentroSaludInstance?.version}" />
			
			<isl:button action="list" tipo="volver" formnovalidate="" />
			<g:actionSubmit class="pure-button pure-button-warning" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			<isl:button action="update" tipo="siguiente" id="${tipoCentroSaludInstance?.codigo}" value="${message(code: 'default.button.update.label', args: [entityName])}"/>
		</div>			
	</div>		
	<g:if test="${flash.message}">
		<div class="pure-u-1 messages" role="status">
		<ul><li>${flash.message}</li></ul>
		</div>
	</g:if>
</g:form>
