<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:javascript src="" />

<g:form name="dp01" class="pure-form pure-form-stacked" >

	<g:hiddenField name="bis_id" value="${bis.id}"/>
	<g:hiddenField name="taskId" value="${taskId}"/>
	<g:hiddenField name="docId" value=""/>
		
	<isl:header_77bis bis="${bis}"/>
	
	<fieldset>
 		<legend>Datos Trabajador</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="3-8" deshabilitado="true" nombre="nombre_trabajador" rotulo="Nombre Trabajador" valor="${FormatosISLHelper.nombreCompletoStatic(bis?.siniestro?.trabajador)}" />
				<isl:textInput cols="1-8" deshabilitado="true" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(bis?.siniestro?.trabajador?.run)}" />
				<isl:textOutput cols="1-8" deshabilitado="true" nombre="nombre_siniestro" rotulo="Siniestro N°" valor="${bis?.siniestro?.id}" />
				<isl:textInput cols="2-8" deshabilitado="true" nombre="tipo_siniestro" rotulo="Tipo de Siniestro" valor="${bis?.siniestro?.esEnfermedadProfesional? 'Enfermedad Profesional' : 'Accidente de Trabajo'}" />
			</div>
		</div>
		
 		<legend>Datos Emisor</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="3-8" deshabilitado="true" nombre="nombre_emisor" rotulo="Nombre Emisor" valor="${bis?.emisor?.razonSocial? bis?.emisor?.razonSocial : bis?.emisor?.nombreFantasia}" />
				<isl:textInput cols="1-8" deshabilitado="true" nombre="rut_emisor" rotulo="RUT Emisor" valor="${FormatosHelper.runFormatStatic(bis?.emisor?.rut)}" />
			</div>
		</div>
		<legend></legend>
		
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:if test="${docs?.size() > 0}">	
					<div class="form-subtitle">Archivos Adicionales 77bis</div>	 		
					<table class="pure-table pure-table-bordered pure-table-striped"  width="87%">
						<thead>
							<tr>
								<th width="90%" >Descripción de los Archivos Adjuntos</th>
								<th width="5%" nowrap="nowrap">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${docs}" var="doc">
								<tr>						
									<td>${doc.descripcion}</td>
								   	<td nowrap="nowrap">
										<button class="pure-button pure-button-secondary" onclick="document.forms[0].docId.value='${doc.id}';document.forms[0].action='viewDoc';document.forms[0].submit();"><i class="icon-search"></i></button>
									</td>	
								</tr>
							</g:each>
						</tbody>
					</table>					
				</g:if>	
				<div class="salto-de-linea"></div>
				
				<g:if test="${dictamen}">
					
					<isl:infoAdicional valor="${dictamen}" variable="docId" accion="viewDoc" rotulo="Ver Dictamen Adjunto" cols="2-8"/>
					<isl:textArea cols="5-8" requerido="true" nombre="comentarios_seguimiento" rotulo="Comentarios Seguimiento" valor="${comentario}"/>
				</g:if>	
				<g:else>
					<isl:textArea cols="7-8" requerido="true" nombre="comentarios_seguimiento" rotulo="Comentarios Seguimiento" valor="${comentario}"/>
				</g:else>
				
				<div class="salto-de-linea"></div>
				<isl:textInput cols="2-8" deshabilitado="true" nombre="montoSolicitado" rotulo="Monto Solicitado"  ayuda="\$" tipo="numero" valor="${bis?.montoSolicitado}" /><div class="salto-de-linea"></div>		
				<isl:textInput cols="2-8" requerido="true" nombre="montoAprobado" rotulo="Monto Aprobado"  ayuda="\$" tipo="numero" valor="${montoAprobado}" maxLargo="9" /><div class="salto-de-linea"></div>	
			</div>
		</div>
		<legend></legend>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit id="informar_analisis" value="Informar Análisis"  action="postDp01" class="pure-button pure-button-success"  />
	</div>
</g:form>