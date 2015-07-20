<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post" >
	<g:hiddenField name="siniestroId" value="${siniestro.id}" />
	<g:hiddenField name="docAdicionalId" /> 
	<g:hiddenField name="reingresoId" value="${reingreso?.id}" /> 
    <fieldset>
 		<legend>Solicitar Reingreso</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
			
				<isl:textOutput cols="1-8" rotulo="Nro. Siniestro" valor="${siniestro.id}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Siniestro" valor="${siniestro.esEnfermedadProfesional ? 'Enfermedad Profesional' : 'Accidente Trabajo'}" />
				<isl:textOutput cols="1-8" rotulo="RUN Trabajador" valor="${FormatosISLHelper.runFormatStatic(siniestro.trabajador.run)}" />
				<isl:textOutput cols="2-8" rotulo="Nombre Trabajador" valor="${FormatosISLHelper.nombreCompletoStatic(siniestro.trabajador)}" />
				<isl:textOutput cols="1-8" rotulo="RUT Empleador" valor="${FormatosISLHelper.runFormatStatic(siniestro.empleador.rut)}" />
				<isl:textOutput cols="2-8" rotulo="Nombre Empleador" valor="${siniestro.empleador.razonSocial}" />
				
				<div class="form-subtitle">Datos de Contacto</div>
				<isl:textInput cols="2-8" requerido="true" valor="${reingreso?.nombre}" 			rotulo="Nombre"				nombre="nombre" maxLargo="255"/>
				<isl:textInput cols="1-8" requerido="true" valor="${reingreso?.apellidoPaterno}"	rotulo="Apellido Paterno"	nombre="apellidoPaterno" maxLargo="255"/>
				<isl:textInput cols="1-8" requerido="true" valor="${reingreso?.apellidoMaterno}"	rotulo="Apellido Materno" 	nombre="apellidoMaterno" maxLargo="255"/>
				<isl:textInput cols="2-8" requerido="true" valor="${reingreso?.direccion}" 			rotulo="Dirección"			nombre="direccion" maxLargo="255"/>
				<isl:textInput cols="1-8" requerido="true" valor="${reingreso?.telefono}" 			rotulo="Teléfono"			nombre="telefono" 	tipo="numero" maxLargo="12"/>
				<isl:textInput cols="1-8" requerido="true" valor="${reingreso?.email}" 				rotulo="E-mail"				nombre="email" 		tipo="email" maxLargo="255"/>

				<div class='salto-de-linea'></div>
				<div class="form-subtitle">Datos de la Solicitud</div>
				<isl:textArea cols="8-8" requerido="true" valor="${reingreso?.motivo}" rotulo="Motivo de la Solicitud" nombre="motivo" />
				
				<g:if test="${reingreso?.id != null}">
					<div class="form-subtitle">Adjuntar Documentación</div>
					<isl:upload cols="3-8" nombre="file" tipo="reingreso" />
					<g:actionSubmit action="cu03u" class="pure-button pure-button-secondary" value="Adjuntar" formnovalidate="formnovalidate"/>
					
					<br>
					
					<table class="pure-table pure-table-bordered pure-table-striped" width="100%">
						<thead>
							<tr>
								<th>Nombre Archivo</th>
								<th>Eliminar</th>
							</tr>
						</thead>
						<tbody>
							<g:if test="${!adjuntos}">
								<tr>						
									<td colspan="2">No hay archivos adjuntos</td>
								</tr>
							</g:if>		 		
							<g:each in="${adjuntos}" var="doc">
								<tr>						
									<td>${doc.descripcion}</td>
								   	<td align="center">
								   		<a href="#" onclick="document.forms[0].docAdicionalId.value='${doc.id}';document.forms[0].action='cu03d';document.forms[0].submit();" style="text-decoration: none;">
								   			<font color=red><i class="icon-trash icon-2x"></i></font>
								   		</a>
									</td>	
								</tr>
							</g:each>
						</tbody>
				</table>
				</g:if>
									
			</div>
		</div>
	</fieldset>
	<div class="pure-g-r">		
		<g:if test="${reingreso?.id != null}">
			<isl:button action="cu03f" tipo="siguiente" value="Finalizar" />
		</g:if>
		<g:else>
			<isl:button action="cu03s" tipo="siguiente" />
		</g:else>
	</div>
</g:form>
