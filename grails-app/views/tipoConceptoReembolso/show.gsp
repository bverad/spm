

<%@ page import="cl.adexus.isl.spm.TipoConceptoReembolso" %>
<!DOCTYPE html>

<g:form class="pure-form pure-form-stacked">
	<g:set var="entityName" value="${message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso')}" />

	<fieldset>
		<legend><g:message code="default.show.label" args="[entityName]" /></legend>
			<isl:textOutput	cols="8-8"
							rotulo="${message(code: 'tipoConceptoReembolso.codigo.label', default: 'Código')}"
							valor="${tipoConceptoReembolsoInstance?.codigo}" />
			<div class='salto-de-linea'></div>

			<isl:textOutput	cols="8-8"
							rotulo="${message(code: 'tipoConceptoReembolso.descripcion.label', default: 'Descripción')}"
							valor="${tipoConceptoReembolsoInstance?.descripcion}" />
			<div class='salto-de-linea'></div>
	</fieldset>

	<div class="pure-g-r">
		<div class="pure-u-1">
			<g:hiddenField name="id" value="${tipoConceptoReembolsoInstance?.codigo}" />
			
			<isl:button action="list" tipo="volver"/>
			<g:actionSubmit class="pure-button pure-button-warning" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			<isl:button action="edit" tipo="siguiente" id="${tipoConceptoReembolsoInstance?.codigo}" value="${message(code: 'default.button.edit.label', args: [entityName])}"/>
		</div>			
	</div>		

	<g:if test="${flash.message}">
		<div class="pure-u-1 messages" role="status">
		<ul><li>${flash.message}</li></ul>
		</div>
	</g:if>
</g:form>
