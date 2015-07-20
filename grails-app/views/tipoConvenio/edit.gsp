<%@ page import="cl.adexus.isl.spm.TipoConvenio" %>
<!DOCTYPE html>
<g:form class="pure-form pure-form-stacked" method="post" >
	<g:set var="entityName" value="${message(code: 'tipoConvenio.label', default: 'TipoConvenio')}" />
	
	<fieldset>
		<legend><g:message code="default.edit.label" args="[entityName]" /></legend>
		
		<g:render template="form" model="['edit': true]"/>
	</fieldset>
	
	<div class="pure-g-r">
		<div class="pure-u-1">
			<g:hiddenField name="id" value="${tipoConvenioInstance?.codigo}" />
			<g:hiddenField name="version" value="${tipoConvenioInstance?.version}" />
			
			<g:actionSubmit class="pure-button pure-button-secondary" action="list"   value="${message(code: 'default.button.back.label', default: 'Back')}" formnovalidate="" />
			<g:actionSubmit class="pure-button pure-button-warning"   action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			<g:actionSubmit class="pure-button pure-button-success"   action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
		</div>			
	</div>		
	<g:if test="${flash.message}">
		<div class="pure-u-1 messages" role="status">
		<ul><li>${flash.message}</li></ul>
		</div>
	</g:if>
</g:form>
