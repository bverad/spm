<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form class="pure-form pure-form-stacked">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="arancelId" />
	<g:hiddenField name="tipoArancel" />
	<g:hiddenField name="odaId" value="${params?.odaId}" />
	<g:hiddenField name="estado" value="${estado}" />
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}" />
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	
	
		
	<fieldset>
		<legend>Emitir ODA</legend>
 		<div class="form-subtitle">Datos de la orden</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput	cols="1-8" rotulo="ID Documento"		valor="${oda?.id}" />
				<isl:textOutput	cols="2-8" rotulo="Tipo ODA" 			valor="${oda?.tipoODA?.descripcion}" />
				<isl:textOutput	cols="1-8" rotulo="Fecha Creación"		valor="${FormatosISLHelper.fechaCortaStatic(oda?.fechaCreacion)}" />
				<isl:textOutput cols="1-8" rotulo="Inicio Vigencia"		valor="${FormatosISLHelper.fechaCortaStatic(oda?.inicioVigencia)}" />
				<isl:textOutput cols="1-8" rotulo="Término Vigencia"	valor="${FormatosISLHelper.fechaCortaStatic(oda?.terminoVigencia)}" />
				<div class='salto-de-linea'></div>
				
				<isl:textOutput	cols="2-8" rotulo="Prestador" 			valor="${FormatosISLHelper.nombrePrestadorStatic(oda?.centroAtencion?.prestador)}"/>
				<isl:textOutput	cols="3-8" rotulo="Centro de Salud" 	valor="${oda?.centroAtencion?.nombre}"/>
				<isl:textOutput	cols="3-8" rotulo="Dirección Prestador"	valor="${oda?.centroAtencion?.prestador?.direccion}" />
			</div>
		</div>

 		<div class="form-subtitle">Datos del Trabajador</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" rotulo="RUN"	 				valor="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}" />
				<isl:textOutput cols="1-8" rotulo="Nombre"		 		valor="${FormatosISLHelper.nombreCompletoStatic(siniestro?.trabajador)}" />
				<isl:textOutput	cols="3-8" rotulo="Dirección" 			valor="${oda?.direccionTrabajador}"/>
				<isl:textOutput	cols="2-8" rotulo="Comuna" 				valor="${oda?.comunaTrabajador?.descripcion}"/>
				<isl:textOutput	cols="1-8" rotulo="Teléfono" 			valor="${oda?.telefonoTrabajador}"/>
				
				<isl:textOutput cols="1-8" rotulo="Sexo"		 		valor="${siniestro?.trabajador?.sexo == 'M' ? 'Masculino' : 'Femenino'}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Nacimiento"	valor="${FormatosISLHelper.fechaCortaStatic(siniestro?.trabajador?.fechaNacimiento)}" />
				<isl:textOutput	cols="3-8" rotulo="E-Mail" 				valor="${oda?.emailTrabajador}"/>
			</div>
		</div>
		
 		<div class="form-subtitle">Atenciones Indicadas</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<table class="pure-table pure-table-bordered pure-table-striped" width="100%">
					<thead>
						<tr>
							<th>Código Arancel</th>
							<th>Glosa Arancel</th>
							<th>Cantidad</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!aranceles}">
							<tr>						
								<td colspan="3">No hay aranceles asociados...</td>
							</tr>
						</g:if>		 		
						<g:each in="${aranceles}" var="arancel">
							<tr>						
								<td align="center">${arancel.codigo}</td>
								<td title="${arancel.glosa}">${FormatosISLHelper.truncateStatic(arancel.glosa, 70)}</td>
								<td align="right">${arancel.cantidad}</td>
							</tr>
						</g:each>
					</tbody>
				</table>					
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="dp12" class="pure-button pure-button-secondary" value="Volver" />
		<isl:button action="genPdf" tipo="pdf" value="Imprimir" />
	</div>
</g:form>