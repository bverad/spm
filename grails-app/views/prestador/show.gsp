
<%@ page import="cl.adexus.isl.spm.Prestador" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'prestador.label', default: 'Prestador')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-prestador" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-prestador" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list prestador">
			
				<g:if test="${prestadorInstance?.personaNatural}">
				<li class="fieldcontain">
					<span id="personaNatural-label" class="property-label"><g:message code="prestador.personaNatural.label" default="Persona Natural" /></span>
					
						<span class="property-value" aria-labelledby="personaNatural-label"><g:link controller="personaNatural" action="show" id="${prestadorInstance?.personaNatural?.id}">${prestadorInstance?.personaNatural?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.personaJuridica}">
				<li class="fieldcontain">
					<span id="personaJuridica-label" class="property-label"><g:message code="prestador.personaJuridica.label" default="Persona Juridica" /></span>
					
						<span class="property-value" aria-labelledby="personaJuridica-label"><g:link controller="personaJuridica" action="show" id="${prestadorInstance?.personaJuridica?.id}">${prestadorInstance?.personaJuridica?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.representanteLegal}">
				<li class="fieldcontain">
					<span id="representanteLegal-label" class="property-label"><g:message code="prestador.representanteLegal.label" default="Representante Legal" /></span>
					
						<span class="property-value" aria-labelledby="representanteLegal-label"><g:link controller="personaNatural" action="show" id="${prestadorInstance?.representanteLegal?.id}">${prestadorInstance?.representanteLegal?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.apoderado}">
				<li class="fieldcontain">
					<span id="apoderado-label" class="property-label"><g:message code="prestador.apoderado.label" default="Apoderado" /></span>
					
						<span class="property-value" aria-labelledby="apoderado-label"><g:link controller="personaNatural" action="show" id="${prestadorInstance?.apoderado?.id}">${prestadorInstance?.apoderado?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.desdeRL}">
				<li class="fieldcontain">
					<span id="desdeRL-label" class="property-label"><g:message code="prestador.desdeRL.label" default="Desde RL" /></span>
					
						<span class="property-value" aria-labelledby="desdeRL-label"><g:formatDate date="${prestadorInstance?.desdeRL}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.hastaRL}">
				<li class="fieldcontain">
					<span id="hastaRL-label" class="property-label"><g:message code="prestador.hastaRL.label" default="Hasta RL" /></span>
					
						<span class="property-value" aria-labelledby="hastaRL-label"><g:formatDate date="${prestadorInstance?.hastaRL}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.desdeAP}">
				<li class="fieldcontain">
					<span id="desdeAP-label" class="property-label"><g:message code="prestador.desdeAP.label" default="Desde AP" /></span>
					
						<span class="property-value" aria-labelledby="desdeAP-label"><g:formatDate date="${prestadorInstance?.desdeAP}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.hastaAP}">
				<li class="fieldcontain">
					<span id="hastaAP-label" class="property-label"><g:message code="prestador.hastaAP.label" default="Hasta AP" /></span>
					
						<span class="property-value" aria-labelledby="hastaAP-label"><g:formatDate date="${prestadorInstance?.hastaAP}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.direccion}">
				<li class="fieldcontain">
					<span id="direccion-label" class="property-label"><g:message code="prestador.direccion.label" default="Direccion" /></span>
					
						<span class="property-value" aria-labelledby="direccion-label"><g:fieldValue bean="${prestadorInstance}" field="direccion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.telefono}">
				<li class="fieldcontain">
					<span id="telefono-label" class="property-label"><g:message code="prestador.telefono.label" default="Telefono" /></span>
					
						<span class="property-value" aria-labelledby="telefono-label"><g:fieldValue bean="${prestadorInstance}" field="telefono"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="prestador.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${prestadorInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.designacion}">
				<li class="fieldcontain">
					<span id="designacion-label" class="property-label"><g:message code="prestador.designacion.label" default="Designacion" /></span>
					
						<span class="property-value" aria-labelledby="designacion-label"><g:fieldValue bean="${prestadorInstance}" field="designacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.tipoPrestador}">
				<li class="fieldcontain">
					<span id="tipoPrestador-label" class="property-label"><g:message code="prestador.tipoPrestador.label" default="Tipo Prestador" /></span>
					
						<span class="property-value" aria-labelledby="tipoPrestador-label"><g:link controller="tipoPrestador" action="show" id="${prestadorInstance?.tipoPrestador?.id}">${prestadorInstance?.tipoPrestador?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.esActivo}">
				<li class="fieldcontain">
					<span id="esActivo-label" class="property-label"><g:message code="prestador.esActivo.label" default="Es Activo" /></span>
					
						<span class="property-value" aria-labelledby="esActivo-label"><g:formatBoolean boolean="${prestadorInstance?.esActivo}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${prestadorInstance?.esPersonaJuridica}">
				<li class="fieldcontain">
					<span id="esPersonaJuridica-label" class="property-label"><g:message code="prestador.esPersonaJuridica.label" default="Es Persona Juridica" /></span>
					
						<span class="property-value" aria-labelledby="esPersonaJuridica-label"><g:formatBoolean boolean="${prestadorInstance?.esPersonaJuridica}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${prestadorInstance?.id}" />
					<g:link class="edit" action="edit" id="${prestadorInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
