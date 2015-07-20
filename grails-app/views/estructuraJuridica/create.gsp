<%@ page import="cl.adexus.isl.spm.EstructuraJuridica" %>
<!DOCTYPE html>
<g:form action="save" class="pure-form pure-form-stacked">
	<g:set var="entityName" value="${message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica')}" />

	<fieldset>
		<legend><g:message code="default.create.label" args="[entityName]" /></legend>
		
		<g:render template="form" model="['edit': false]"/>
	</fieldset>
	
	<div class="pure-g-r">
		<div class="pure-u-1">
			<g:actionSubmit class="pure-button pure-button-secondary" action="list" value="${message(code: 'default.button.back.label', default: 'Back')}" formnovalidate="" />
			<g:submitButton name="create" class="pure-button pure-button-success" value="${message(code: 'default.button.create.label', default: 'Create')}" />
		</div>
	</div>		
	<g:if test="${flash.message}">
		<div class="pure-u-1 messages" role="status">
		<ul><li>${flash.message}</li></ul>
		</div>
	</g:if>
</g:form>
