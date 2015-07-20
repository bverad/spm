<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.isl.spm.TipoCentroSalud" %>

<g:form class="pure-form pure-form-stacked">
	<g:set var="entityName" value="${message(code: 'tipoCentroSalud.label', default: 'Tipo Centro de Salud')}" />
	
	<fieldset>
		<legend><g:message code="default.show.label" args="[entityName]" /></legend>
		
		<isl:textInput	cols="8-8"
						requerido="true"
						deshabilitado="true"
						nombre="codigo"
						rotulo="${message(code: 'tipoCentroSalud.codigo.label', default: 'Código')}"
						valor="${tipoCentroSaludInstance?.codigo}" />
		<div class='salto-de-linea'></div>

		<isl:textInput	cols="8-8"
						requerido="true"
						deshabilitado="true"
						nombre="descripcion"
						rotulo="${message(code: 'tipoCentroSalud.descripcion.label', default: 'Descripción')}"
						valor="${tipoCentroSaludInstance?.descripcion}" />
		<div class='salto-de-linea'></div>
		
	</fieldset>
	
	<div class="pure-g-r">
		<div class="pure-u-1">
			<g:hiddenField name="id" value="${tipoCentroSaludInstance?.codigo}" />
			<isl:button action="list" tipo="volver"/>
			<g:actionSubmit class="pure-button pure-button-warning" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			<isl:button action="edit" tipo="siguiente" id="${tipoCentroSaludInstance?.codigo}" value="${message(code: 'default.button.edit.label', args: [entityName])}"/>
		</div>			
	</div>		

	<g:if test="${flash.message}">
		<div class="pure-u-1 messages" role="status">
		<ul><li>${flash.message}</li></ul>
		</div>
	</g:if>
</g:form>
