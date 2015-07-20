<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="" />
<g:form name="dp02" action="" class="pure-form pure-form-stacked" >

<g:hiddenField name="bisId" value="${bis.id}"/>
<g:hiddenField name="taskId" value="${params?.taskId}"/>

	<fieldset>
 		<legend>Informar al Solicitante el Rechazo Total</legend>
 		<label for="orden" style="float: left; margin-right: 3px;margin-left:50%">ORD. N°</label>
		<input type="text" name="ordinario" style="float: left; margin-right: 10px; margin-left:2%; width:5%;" placeholder="" required="required" value="${bis?.ordinario}" />
 		<div class='salto-de-linea'></div>
 		
		<div class="form-subtitle"> I.Información de la Entidad Solicitante y Emisor </div>
		
		<label for="de" style="float: left; margin-right: 3px;margin-left:1%">De:</label>
		<label for="de" style="float: left; margin-right: 3px;margin-left:2%">JESSICA NAVARRETE FUENTES</label>
		<div class='salto-de-linea'></div>
		<label for="de" style="float: left; margin-right: 3px;margin-left:5%">JEFE DE UNIDAD DE REVISÓN DE CUENTAS</label>
		<div class='salto-de-linea'></div>
		<input type="text" name="isl" style="float: left; margin-right: 10px; margin-left:5%; width:30%;" disabled="disabled" value="INSTITUTO DE SEGURIDAD LABORAL" />		
		<div class='salto-de-linea'></div>
		
		<label for="A" style="float: left; margin-right: 4px;margin-left:1%">A:&nbsp;&nbsp;</label>
		<input type="text" name="encargadoCobranza" style="float: left; margin-right: 10px; margin-left:2%; width:30%;" placeholder="Encargado cobranza" required="required" value="${bis?.encargadoCobranza}" />
		<div class='salto-de-linea'></div>
		<input type="text" name="encargadoCobranzaTitle" style="float: left; margin-right: 10px; margin-left:5%; width:30%;" disabled="disabled" value="ENCARGADO DE COBRANZAS" />
		<div class='salto-de-linea'></div>
		<input type="text" name="isapre" style="float: left; margin-left:5%; width:5%;" disabled="disabled" value="ISAPRE" />		
		<input type="text" name="entidadCobradora" style="float: left; margin-right: 10px; width:25%;" placeholder="Nombre entidad" required="required" value="${bis?.entidadCobradora }" />
		<div class='salto-de-linea'></div>
		<input type="text" name="direccionEntidad" style="float: left; margin-right: 10px; margin-left:5%; width:30%;" placeholder="Dirección" required="required" value="${bis?.direccionEntidad}" />
		<div class='salto-de-linea'></div>
		
		<legend></legend>
		<label for="folio" style="float: left; margin-right: 4px;margin-left:1%">Folio&nbsp;&nbsp;</label>
		<input type="text" name="folio" style="float: left; margin-right: 10px; width:15%;" disabled="disabled" placeholder="3342" value="${bis?.id}" />
		<label for="fechaRevision" style="float: left; margin-right: 4px;margin-left:1%">Fecha de Revisión&nbsp;&nbsp;</label>
		<input type="text" name="folio" style="float: left; margin-right: 10px; width:15%;" disabled="disabled" value="${FormatosHelper.fechaCortaStatic(bis?.fechaRevision)}" />
		<legend></legend>
		
		<div class="form-subtitle"> II. Motivo del Rechazo </div>
		<label for="declaracion" style="float: left; margin-right: 4px;margin-left:1%">Mediante la presente, informo a usted el rechazo total de su solicitud de 77 bis por el motivo que se indica:</label>
		<div class='salto-de-linea'></div>
		<isl:textArea cols="5-8" deshabilitado="true" filas="5" nombre="comentariosSeguimiento" valor="${bis?.comentariosSeguimiento}"/>
		<div class='salto-de-linea'></div>
		<label for="monto" style="float: left; margin-right: 4px;margin-left:1%">La cual fue efectuada por un monto de: </label>
		<input type="text" name="montoSolicitud" style="float: left; width:10%;" placeholder="$" disabled="disabled" value="${FormatosHelper.montosStatic(bis?.montoSolicitado)}" />
		<div class='salto-de-linea'></div>
		
		<div class="form-subtitle"> III. Comentarios Revisión de Cuentas</div>
		<isl:textArea cols="5-8" filas="5" requerido="true" nombre="comentariosRevision" ayuda="Comentarios de revision" valor="${bis?.comentariosRevision}"/>
		<div class='salto-de-linea'></div>
		
		<label for="cc" style="float: left; margin-right: 4px;margin-left:1%">CC: Archivo</label>
		<div class='salto-de-linea'></div>
		
		<legend></legend>
	</fieldset>
	
	<div class="pure-g-r">
		<g:actionSubmit action="postDp02" value="Notificar" class="pure-button pure-button-success" />
	</div>
</g:form>