

<%@ page import="cl.adexus.isl.spm.Banco" %>
<!DOCTYPE html>

<g:form class="pure-form pure-form-stacked">
	<g:set var="entityName" value="${message(code: 'banco.label', default: 'Banco')}" />

	<fieldset>
		<legend><g:message code="default.show.label" args="[entityName]" /></legend>

			<isl:textOutput	cols="8-8"
							rotulo="${message(code: 'banco.codigo.label', default: 'Código')}"
							valor="${bancoInstance?.codigo}" />
			<div class='salto-de-linea'></div>
	
			<isl:textOutput	cols="8-8"
							rotulo="${message(code: 'banco.descripcion.label', default: 'Descripción')}"
							valor="${bancoInstance?.descripcion}" />
			<div class='salto-de-linea'></div>

	</fieldset>

	<div class="pure-g-r">
		<div class="pure-u-1">
			<g:hiddenField name="id" value="${bancoInstance?.codigo}" />
			
			<isl:button action="list" tipo="volver"/>
			<g:actionSubmit class="pure-button pure-button-warning" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			<isl:button action="edit" tipo="siguiente" id="${bancoInstance?.codigo}" value="${message(code: 'default.button.edit.label', args: [entityName])}"/>
		</div>			
	</div>		

	<g:if test="${flash.message}">
		<div class="pure-u-1 messages" role="status">
		<ul><li>${flash.message}</li></ul>
		</div>
	</g:if>
</g:form>
