<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>

<g:form name="dp04" class="pure-form pure-form-stacked" >
	
	<g:hiddenField name="bisId" value="${bis?.id}" />
	<g:hiddenField name="taskId" value="${params?.taskId}" />	
	<g:hiddenField name="aprobado" value="${bis?.montoAprobado}" />
	<g:hiddenField name="reembolsoTotal" value="${null}" />
	<g:hiddenField name="interesTotal" value="${null}" />
	<g:hiddenField name="recepcionCarta" value="${bis?.fechaRecepcion}" />
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
		
		<g:if test="${dias > 10}">
			<g:javascript src="BIS_revision/calculoInteres.js" />
			<legend>Calculo Interés</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">	
					<isl:link cols="1-8" nombre="Tabla Interés Corriente" enlace="http://www.sbif.cl/sbifweb/servlet/InfoFinanciera?indice=4.1&idCategoria=555&tipocont=556"/>
					<isl:link cols="1-8" nombre="Tabla U.F." enlace="http://www.sii.cl/pagina/valores/uf/uf2014.htm"/>	
					<isl:textOutput cols="2-8" nombre="" rotulo = "" />
					<isl:textInput cols="2-8" deshabilitado="true" nombre="monto_solicitado" rotulo="Monto Solicitado"  ayuda="\$" tipo="numero" valor="${FormatosHelper.montosStatic(bis?.montoSolicitado)}" />
					<isl:textInput cols="2-8" deshabilitado="true" nombre="monto_aprobado" rotulo="Monto Aprobado"  ayuda="\$" tipo="numero" valor="${FormatosHelper.montosStatic(bis?.montoAprobado)}" />	
					<isl:textInput cols="1-8" deshabilitado="true" nombre="fechaRecepcion" rotulo = "Fecha Recepcion Carta" valor="${FormatosHelper.fechaCortaStatic(bis?.fechaRecepcion)}" />	
					<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
						<thead>
							<tr>
								<th width="10%" title="Fecha de Pago">Fecha Pago ISL</th>
								<th width="10%" title="U.F. de la fecha de Pago">UF Fecha Pago</th>
								<th width="10%" title="Días Transcurridos">Días</th>
								<th width="10%" title="Tasa de interes expresada en porciento">Interés Anual(%)</th>
								<th width="10%" title="Interes Diarios expresado en porciento">Interés Diario(%)</th>
								<th width="10%" title="Interes del periodo expresado en porciento">Interés Periodo(%)</th>
								<th width="10%" title="Monto prestacion en U.F.">Monto Prest (UF)</th>
								<th width="10%" title="Interés en U.F.">Interés UF</th>
							</tr>
						</thead>
						<tbody>
							<tr>						
								<td><g:textField required="required" name="fechaPago" style="text-align:left;" type="text" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" value="${null}" placeholder="DD-MM-AAAA"/></td>
								<td><g:textField required="required" name="ufPago" style="text-align:right;" type="text" maxlength="5" pattern="[0-9]+" placeholder="Unidad de fomento" value="${bis?.ufPago}"/></td>
								<td><g:textField name="dias" style="text-align:right;" value="${null}" readonly="true"/></td>
								<td><g:textField required="required" name="tasaInteres" style="text-align:right;" type="number" maxlength="5" pattern="[0-9]+[.][0-9]{2}" value="${bis?.tasaInteres}"/></td>
								<td><g:textField name="interesDiario" style="text-align:right;" value="${null}" readonly="true"/></td>
								<td><g:textField name="interesPeriodo" style="text-align:right;" value="${null}" readonly="true"/></td>
								<td><g:textField name="montoPrestacion" style="text-align:right;" value="${null}" readonly="true"/></td>
								<td><g:textField name="interesUf" style="text-align:right;" value="${null}" readonly="true"/></td>
							</tr>					
						</tbody>
					</table>		
					<isl:textOutput cols="8-8" nombre="" rotulo = "" />
					
					<isl:textInput cols="2-8" deshabilitado="true" nombre="sucursal" rotulo="Sucursal" valor="Nivel Central" />
					<isl:textInput cols="2-8" requerido="true" nombre="revisadoPor" rotulo="Revisado por" valor="${bis?.revisadoPor}" />
					<isl:textInput cols="1-8" deshabilitado="true" nombre="fechaRevision" rotulo="Fecha" valor="${FormatosISLHelper.fechaCortaStatic(new Date())}" />
					<isl:textOutput cols="2-8" nombre="" rotulo = "" />
					
					<isl:textInput cols="1-8" deshabilitado="true" nombre="totalUf" rotulo="Total (UF)" valor="${null}" />
					<isl:textOutput cols="7-8" nombre="" rotulo = "" />
					<isl:textInput cols="1-8" deshabilitado="true" nombre="totalInteres" rotulo="Total Intereses" valor="${null}" />
					<isl:textOutput cols="7-8" nombre="" rotulo = "" />
					<isl:textInput cols="1-8" deshabilitado="true" nombre="totalPrestaciones" tipo="numero" rotulo="Total Prestaciones" valor="${bis?.montoAprobado}" />				
					<isl:textOutput cols="6-8" nombre="" rotulo = "" />
					<isl:textInput cols="2-8" deshabilitado="true" nombre="reembolso" rotulo="Total a Reembolsar" valor="${null}" />			
				</div>
			</div>
		</g:if>
		<g:else>
			<legend>Resumen</legend>		
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
						
						<isl:infoAdicional valor="${dictamen}" variable="docId" accion="viewDoc" rotulo="Dictamen" cols="2-8"/>
						<isl:textArea cols="5-8" deshabilitado="true" requerido="true" nombre="comentarios_seguimiento" rotulo="Comentarios Seguimiento" valor="${bis?.comentariosSeguimiento}"/>
					</g:if>	
					<g:else>
						<isl:textArea cols="7-8" deshabilitado="true" requerido="true" nombre="comentarios_seguimiento" rotulo="Comentarios Seguimiento" valor="${bis?.comentariosSeguimiento}"/>
					</g:else>
					
					<div class="salto-de-linea"></div>
					<isl:textInput cols="2-8" deshabilitado="true" nombre="montoSolicitado" rotulo="Monto Solicitado"  ayuda="\$" tipo="numero" valor="${FormatosHelper.montosStatic(bis?.montoSolicitado)}" /><div class="salto-de-linea"></div>	
					<isl:textInput cols="2-8" deshabilitado="true" nombre="montoAprobado" rotulo="Monto Aprobado"  ayuda="\$" tipo="numero" valor="${FormatosHelper.montosStatic(bis?.montoAprobado)}" />	
					<isl:textOutput cols="1-8" nombre="" rotulo="" valor="" />
					<isl:textInput cols="2-8" deshabilitado="true" nombre="totalReembolso" rotulo="Total a Reembolsar"  ayuda="\$" tipo="numero" valor="${FormatosHelper.montosStatic(bis?.montoAprobado)}" />			
				</div>
			</div>
		</g:else>
		
		<legend></legend>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit id="enviar_pago" value="Enviar a Pago"  action="postDp04" class="pure-button pure-button-success"  />
	</div>
</g:form>