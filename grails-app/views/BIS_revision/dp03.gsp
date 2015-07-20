<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>

<g:javascript src="" />
<g:form name="dp03" class="pure-form pure-form-stacked" >

	<g:hiddenField name="bisId" value="${bis?.id}" />
	<g:hiddenField name="taskId" value="${params?.taskId}" />	

	<fieldset>
 		<legend>Informar al Solicitante Analisis de Pago</legend>
 		<label for="orden" style="float: left; margin-right: 3px;margin-left:50%">ORD. N°</label>
		<input type="text" name="ordenNumero" style="float: left; margin-right: 10px; margin-left:2%; width:5%;" required="required" value="${bis?.ordinario}" />
 		<div class='salto-de-linea'></div>
 		
		<div class="form-subtitle"> I.Información de la Entidad Solicitante y Emisor </div>
		
		<label for="de" style="float: left; margin-right: 3px;margin-left:1%">De:</label>
		<label for="de" style="float: left; margin-right: 3px;margin-left:2%">JESSICA NAVARRETE FUENTES</label>
		<div class='salto-de-linea'></div>
		<label for="de" style="float: left; margin-right: 3px;margin-left:5%">JEFE DE UNIDAD DE REVISI&Oacute;N DE CUENTAS M&Eacute;DICAS</label>
		<div class='salto-de-linea'></div>
		<input type="text" name="isl" style="float: left; margin-right: 10px; margin-left:5%; width:30%;" disabled="disabled" value="INSTITUTO DE SEGURIDAD LABORAL" />		
		<div class='salto-de-linea'></div>
		
		<label for="A" style="float: left; margin-right: 4px;margin-left:1%">A:&nbsp;&nbsp;</label>
		<input type="text" name="encargadoCobranza" style="float: left; margin-right: 10px; margin-left:2%; width:30%;" required="required" value="${bis?.encargadoCobranza}" />
		<div class='salto-de-linea'></div>
		<input type="text" name="encargadoCobranzaTitle" style="float: left; margin-right: 10px; margin-left:5%; width:30%;" disabled="disabled" value="ENCARGADO DE COBRANZAS" />
		<div class='salto-de-linea'></div>
		<input type="text" name="isapre" style="float: left; margin-left:5%; width:5%;" disabled="disabled" value="ISAPRE" />		
		<input type="text" name="entidadCobradora" style="float: left; margin-right: 10px; width:25%;" placeholder="Nombre del organismo" required="required" value="${bis?.entidadCobradora}" />
		<div class='salto-de-linea'></div>
		<input type="text" name="direccionEntidad" style="float: left; margin-right: 10px; margin-left:5%; width:30%;" placeholder="Dirección del organismo" required="required" value="${bis?.direccionEntidad}" />
		<div class='salto-de-linea'></div>
		
		<legend></legend>
		<label for="folio" style="float: left; margin-right: 4px;margin-left:1%">Folio C.C&nbsp;&nbsp;</label>
		<input type="text" name="folio" style="float: left; margin-right: 10px; width:15%;" disabled="disabled" value="${bis?.id}" />
		<label for="folio" style="float: left; margin-right: 4px;margin-left:1%">Fecha de Revisión&nbsp;&nbsp;</label>
		<input type="text" name="folio" style="float: left; margin-right: 10px; width:15%;" disabled="disabled" value="${FormatosISLHelper.fechaCortaStatic(bis?.fechaRevision)}" />
		<legend></legend>
		
		<div class="form-subtitle"> II. Resolución 77bis </div>
		<label for="declaracion" style="float: left; margin-right: 4px;margin-left:1%;">Mediante la presente, informo a usted el que su solicitud de 77 bis fue analizada y procesada según lo que se indica:</label>
		<div class='salto-de-linea'></div>
		
		<label for="montoSolicitado" style="float: left; margin-right: 4px;margin-left:1%">Monto Solicitado</label>
		<input type="text" name="montoSolicitado" style="float: left; width:10%; margin-left:3px;" disabled="disabled" value="${FormatosHelper.montosStatic(bis?.montoSolicitado)}" />

		<div id="interes">
			<label for="totalIntereses" style="float: left; margin-right: 4px;margin-left:9%;">Total Intereses</label>
			<input type="text" name="totalIntereses" style="float: left; width:10%;margin-left:2%;" disabled="disabled" value="${bis?.totalInteres != 0? (FormatosHelper.montosStatic(bis?.totalInteres.toLong())) : '$0'}" />	
		</div>
		
		<div class='salto-de-linea'></div>
		<label for="montoAprobado" style="float: left; margin-right: 4px;margin-left:1%">Monto Aprobado</label>
		<input type="text" name="montoAprobado" style="float: left; width:10%;" disabled="disabled" value="${FormatosHelper.montosStatic(bis?.montoAprobado)}" />
		<div class='salto-de-linea'></div>
		
		<label for="bla" style="float: left; margin-right: 4px;margin-left:1%;">_______________________________________________________________________________________</label>
		<div class='salto-de-linea'></div>
		<label for="totalReembolso" style="float: left; margin-right: 4px;margin-left:29%;">Total a Reembolsar</label>
		<input type="text" name="totalReembolso" style="float: left; width:10%;" disabled="disabled" value="${bis?.totalReembolso? (FormatosHelper.montosStatic(bis?.totalReembolso)) : FormatosHelper.montosStatic(bis?.montoAprobado)}" />
		<div class='salto-de-linea'></div>
		
		<div class="form-subtitle"> III. Comentarios Revisión de Cuentas</div>
		<isl:textArea cols="5-8" filas="5" requerido="true" nombre="comentariosRevision" ayuda="Comentarios de la revisión de pago" valor="${bis?.comentariosRevision}"/>
		<div class='salto-de-linea'></div>
		
		<label for="cc" style="float: left; margin-right: 4px;margin-left:1%;">CC: Archivo</label>
		<div class='salto-de-linea'></div>
		
		<legend></legend>
	</fieldset>
	
	<div class="pure-g-r">
		<g:actionSubmit action="postDp03" value="Notificar" class="pure-button pure-button-success" />
	</div>
</g:form>