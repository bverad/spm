<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="OTP/ingreso_dp06.js" />
<g:form name="dp06" class="pure-form pure-form-stacked" enctype="multipart/form-data">
<g:hiddenField name="reembolsoId" value="${reembolso.id}"/>

	<style>
		.formulario-fieldset {
			padding: 5px 0 20px 10px; 
		}	
	</style>

	<fieldset>
 		<legend>Digita y Escanea el Reembolso</legend>

 		<legend>Datos Trabajador</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" nombre="runTrabajador"	rotulo="RUN Trabajador"		deshabilitado="true" valor="${FormatosHelper.runFormatStatic(reembolso?.siniestro?.trabajador?.run)}" />
				<isl:textInput cols="3-8" nombre="nombreTrabajador" rotulo="Nombre Trabajador"	deshabilitado="true" valor="${trabajadorNombreCompleto}" />
				<isl:textInput cols="1-8" nombre="siniestroNumero"	rotulo="Siniestro N°"		deshabilitado="true" valor="${reembolso?.siniestro?.id}" />
				<isl:textInput cols="1-8" nombre="siniestroTipo"	rotulo="Tipo Siniestro"		deshabilitado="true" valor="${FormatosISLHelper.fechaCortaStatic(siniestro?.fecha)}" />
			</div>
		</div>

 		<legend>Datos Solicitante</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" nombre="runSolicitante"			rotulo="RUN Solicitante"			deshabilitado="true" valor="${FormatosHelper.runFormatStatic(reembolso?.solicitante?.run)}" />
				<isl:textInput cols="3-8" nombre="nombreSolicitante" 		rotulo="Nombre Solicitante"			deshabilitado="true" valor="${solicitanteNombreCompleto}" />
				<isl:textInput cols="1-8" nombre="relacionConTrabajador"	rotulo="Relacion con Trabajador"	deshabilitado="true" valor="${relacionDelSolicitante}" />
			</div>
		</div>
		
		<!--  ESCANEAR FORMULARIO SOLICITADO -->		
 		<legend>Escanear Formulario Solicitado</legend>
		<g:if test="${documento != null}">
			<div class="box pure-input-8-8">
				¿El formulario está completamente correcto? Asegúrese de que el documento esté firmado antes de guardar.
			</div>
		</g:if>				
		<isl:upload cols="4-8" nombre="fileFormularioFirmado" tipo="SDAAT" rotulo="Adjuntar Archivo"/>
		<div class="pure-u-1-8" style="padding-top: 17px; padding-left: 5px;">
			<g:actionSubmit action="uploadArchivoReembolsoEscaneado" class="pure-button pure-button-warning" value="Subir" formnovalidate="formnovalidate"/>
		</div>
		<br/>
		<br/>
		
		<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
			<thead>
				<tr>
					<th>Nombre Archivo</th>
					<th>Ver</th>
					<th>Eliminar</th>
				</tr>
			</thead>
			<tbody>
				<g:if test="${!documento}">
					<tr>						
						<td colspan="3">No hay archivos adjuntos</td>
					</tr>
				</g:if>
				<g:else>
					<tr>						
						<td>Formulario de Reembolso Firmado</td>
					   	<td align="center">
				<g:link action="verFormularioReembolso"
						controller="OTP_revision"
  							style="text-decoration: none;"
  							id="${reembolso.id}">
  						<font color="#1F8DD6"><i class="icon-info-sign icon-2x"></i></font>
				</g:link>
						</td>	
					   	<td align="center">
				<g:link action="deleteArchivoReembolsoEscaneado"
						controller="OTP_ingreso"
						style="text-decoration: none;"
  							id="${documento.id}"
  							params="[reembolsoId: reembolso.id]">
  						<font color=red><i class="icon-trash icon-2x"></i></font>
				</g:link>
						</td>	
					</tr>
				</g:else>	 		
			</tbody>
		</table>					

 		<legend>Detalle de Gastos</legend>
		<div class="formulario-fieldset">

			<isl:calendar  cols="1-8"  nombre="detalleFechaGasto"          rotulo="Fecha Gasto"         requerido="true" valor="${detalleReembolso?.fechaGasto}"/>
			<isl:textInput cols="1-8"  nombre="detalleNumeroBoletaFactura" rotulo="N° Boleta / Factura" requerido="true" valor="${detalleReembolso?.numero}"/>
			<isl:combo nombre="detalleConcepto" cols="2-8" requerido="true" rotulo="Concepto" name="detalleConcepto" optionKey="codigo" optionValue="${{it.codigo + '. ' + it.descripcion}}" valor="${detalleReembolso?.concepto?.codigo}" from="${conceptos}" noSelection="${['':'Seleccione ...']}" /><br />
			
			<!--  PROVEEDOR -->
			
			<label for="tipoProveedor" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Tipo de Proveedor</label>
			<input type="radio" name="tipoProveedor" class="clicker-tipo-proveedor" id="clicker-tipo-proveedor-juridico" value="juridico" <g:if test="${detalleReembolso?.tipoProveedorPersonaJuridica == true || detalleReembolso?.tipoProveedorPersonaNatural != true }">checked="checked"</g:if> /> <span style="margin-right: 20px;">Jurídico</span>
			<input type="radio" name="tipoProveedor" class="clicker-tipo-proveedor" id="clicker-tipo-proveedor-natural"  value="natural"  <g:if test="${detalleReembolso?.tipoProveedorPersonaNatural == true }">checked="checked"</g:if> /> Natural<br />

			<div id="datos-juridico">
				<isl:textInput cols="1-8" rotulo="RUT" nombre="proveedorJuridicoRut" valor="${FormatosHelper.runFormatStatic(personaJuridica?.rut)}"/>
				<isl:textInput cols="4-8" rotulo="Nombre o Razón Social" nombre="proveedorJuridicoRazonSocial" deshabilitado="true" valor="${personaJuridica?.razonSocial}"/>
			</div>
			<div id="datos-natural">
				<isl:textInput cols="1-8" rotulo="RUT" nombre="proveedorNaturalRut" valor="${FormatosHelper.runFormatStatic(personaNatural?.run)}"/>
				<isl:textInput cols="3-8" rotulo="Nombres" nombre="proveedorNaturalNombres" deshabilitado="true" valor="${personaNatural?.nombre}"/>
				<isl:textInput cols="2-8" rotulo="Apellido Paterno" nombre="proveedorNaturalApellidoPaterno" deshabilitado="true" valor="${personaNatural?.apellidoPaterno}"/>
				<isl:textInput cols="2-8" rotulo="Apellido Materno" nombre="proveedorNaturalApellidoMaterno" deshabilitado="true" valor="${personaNatural?.apellidoMaterno}"/>
			</div>
			
			<div class='salto-de-linea'></div>
			
			<!-- VALORES -->

			<isl:textInput cols="1-8" requerido="true" tipo="numero" rotulo="Valor Documento \$"  nombre="detalleValorDocumento"  valor="${detalleReembolso?.valorDocumento}"/>
			<isl:textInput cols="1-8" requerido="true" tipo="numero" rotulo="Valor Solicitado \$" nombre="detalleValorSolicitado" valor="${detalleReembolso?.valorSolicitado}"/>

			<div class='salto-de-linea'></div>

			<!--  ADJUNTE DEL ARCHIVO -->
			<isl:upload cols="4-8" nombre="detalleArchivoAdjunto" tipo="REEM" rotulo="Adjuntar Archivo" />
			
			<div class="pure-u-1-8" style="padding-top: 17px; padding-left: 5px;">
				<isl:button action="agregaDetalleReembolso" tipo='Agregar' valor="${detalleArchivo}"/>
			</div>
			<div class='salto-de-linea'></div>

			<div style="margin: 10px 10px 10px 10px;">
				<table id="detalleTable" class="pure-table" width="100%">
				<thead>
					<tr>
						<th>Fecha</th>
						<th>N° B/F</th>
						<th>Concepto</th>
						<th>Rut Proveedor</th>
						<th>Nombre o R.S.</th>
						<th>V. Doc</th>
						<th>V. Sol</th>
						<th>Ver</th>
						<th>Eliminar</th>
					</tr>
					</thead>
					<tbody>
					<g:each in="${detalles}" status="i" var="detalle">
						<tr>
							<td align="center">${FormatosHelper.fechaCortaStatic(detalle?.fechaGasto)}</td>
							<td>${detalle?.numero}</td>
							<td>${detalle?.concepto}</td>
							<td>${FormatosHelper.runFormatStatic(detalle?.rutProveedor)}</td>
							<td>${detalle?.nombreProveedor}</td>
							<td align="right">${FormatosISLHelper.montoStatic(detalle?.valorDocumento)}</td>
							<td align="right">${FormatosISLHelper.montoStatic(detalle?.valorSolicitado)}</td>
							<td align="center">
								<button title="Ver" class="pure-button pure-button-secondary" onclick="window.location.href = 'verDetalleAdjunto?detalleId=${detalle.id}'; return false;">
									<i class="icon-info-sign"></i>
								</button>
							</td>
							<td align="center">
							    <g:link action="eliminaDetalleReembolso" 
	                       			style="text-decoration: none;"
	                       			id="${detalle?.id}">
	                       			<font color="red"><i class="icon-trash icon-2x"></i></font>
								</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
					<tfoot> 
						<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
							<th colspan="6" align="right">Suma Gastos</th>
							<th align="right">${FormatosISLHelper.montoStatic(sumaGastos)}</th>
							<th colspan="2" align="right"></th>
						</tr>
						<tr style="border-top-width: 1px; border-top-color: #CBCBCB; border-top-style: solid; background-color: #E0E0E0;">
							<th colspan="6" align="right">Monto Solicitado</th>
							<th align="right">${FormatosISLHelper.montoStatic(reembolso?.montoSolicitado)}</th>
							<th colspan="2" align="right"></th>
						</tr>
					</tfoot>
				</table>				
			</div>			
		</div>
	</fieldset>
	
	<div class="pure-g-r">
		<input id="terminarButton" type="submit" name="_action_cu06" value="Terminar" class="pure-button pure-button-success" formnovalidate="formnovalidate">
	</div>
</g:form>