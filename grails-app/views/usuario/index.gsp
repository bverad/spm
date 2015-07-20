<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="index" class="pure-form pure-form-stacked" >
	<fieldset>
	 <legend>Usuario</legend>
	 <div class="pure-u-6-8" style="padding-top: 20px;">
           <table id="datos" border="1" bordercolor="#BBBBBB" cellpadding="5" width="80%">
           <tbody>
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Username:</span></td>
	 				<td><shiro:principal/></td>
	 			</tr>
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">RUN:</span></td>
	 				<td>${FormatosHelper.runFormatStatic(usuario.run)}</td>
	 			</tr>
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Nombre:</span></td>
	 				<td>${usuario.nombres} ${usuario.apellidoPaterno} ${usuario.apellidoMaterno}</td>
	 			</tr>
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Email:</span></td>
	 				<td>${usuario.correoElectronico}</td>
	 			</tr>
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Tipo Usuario:</span></td>
	 				<td>${usuario.tipoUsuario.name}</td>
	 			</tr>
	 			<g:if test="${usuario.unidadOrganizativa!=null }">
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Unidad Organizativa:</span></td>
	 				<td>${usuario.unidadOrganizativa?.name} - ${usuario.unidadOrganizativa?.region?.name}</td>
	 			</tr>
	 			</g:if>
	 			<g:if test="${usuario.agencia!=null }">
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Agencia:</span></td>
	 				<td>${usuario.agencia?.name} - ${usuario.agencia?.region?.name}</td>
	 			</tr>
	 			</g:if>
	 			<g:if test="${usuario.sucursal!=null }">
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Sucursal:</span></td>
	 				<td>${usuario.sucursal?.name} - ${usuario.sucursal?.agencia?.name} - ${usuario.sucursal?.agencia?.region?.name}</td>
	 			</tr>
	 			</g:if>
	 			<g:if test="${usuario.empresa!=null }">
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Empresa:</span></td>
	 				<td>${usuario.empresa?.name} - [${FormatosHelper.runFormatStatic(usuario.empresa.rut_empresa)}]</td>
	 			</tr>
	 			</g:if>
	 			<tr>
	 				<td bgcolor="#42B8DD"><span style="font-weigth: bold; color: white;">Roles:</span></td>
	 				<td>
	 					<ul>
		 				<g:each in="${usuario.roles}" var="r">
		 				<li>${r.name}</li>
		 				</g:each>
		 				</ul>
	 				</td>
	 			</tr>
		   </tbody>	 
	 </fieldset>
</g:form>