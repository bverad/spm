<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:form name="dp04" action="" class="pure-form pure-form-stacked" >

	<g:hiddenField name="taskId" value="${taskId}"/>
	<g:hiddenField name="bisId" value="${bis.id}"/>
	<g:hiddenField name="regularizacion" value="${regularizacion}"/>

	<fieldset>
 		<legend>Carta de Rechazo</legend>
 		<label for="orden" style="float: left; margin-right: 3px;margin-left:50%">ORD. N°</label>
		<input type="text" name="ordinario" style="float: left; margin-right: 10px; margin-left:2%; width:5%;" required="required" value="${bis?.ordinario}" />
 		<div class='salto-de-linea'></div>
 		
		<div class="form-subtitle"> I.Información de la Entidad Solicitante y Emisor </div>
		
		<label for="de" style="float: left; margin-right: 3px;margin-left:1%">De:</label>
		<label for="de" style="float: left; margin-right: 3px;margin-left:2%">LIZZY VIDAL NEIRA</label>
		<div class='salto-de-linea'></div>
		<label for="de" style="float: left; margin-right: 3px;margin-left:5%">JEFE DE UNIDAD DE REVISÓN DE CUENTAS</label>
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
		<legend></legend>
		
		<div class="form-subtitle"> II. Motivo del Rechazo </div>
		<label for="declaracion" style="float: left; margin-right: 4px;margin-left:1%">Mediante la presente, informo a usted el rechazo de su solicitud de 77 bis por el motivo que se indica:</label>
		<div class='salto-de-linea'></div>
		<label for="guion" style="float: left; margin-right: 4px;margin-left:1%">-</label>
		<input type="text" name="direccionEntidad" style="float: left; width:60%;" disabled="disabled" value="${resolucion}" />
		<div class='salto-de-linea'></div>
		<label for="monto" style="float: left; margin-right: 4px;margin-left:1%">Por un monto de $</label>
		<input type="text" name="montoSolicitado" style="float: left; width:10%;" disabled="disabled" value="${bis?.montoSolicitado}" />
		<div class='salto-de-linea'></div>
		
		<div class="form-subtitle"> III. Comentarios</div>
		<isl:textArea cols="5-8" filas="5" requerido="true" nombre="comentariosRechazo" valor="${bis?.comentariosRechazo}"/>
		<div class='salto-de-linea'></div>
		
		<label for="cc" style="float: left; margin-right: 4px;margin-left:1%">CC: Archivo</label>
		<div class='salto-de-linea'></div>
		
		<legend></legend>
	</fieldset>
	
	<div class="pure-g-r">
		<g:actionSubmit action="completeDp04" value="Notificar" class="pure-button pure-button-success" />
	</div>
</g:form>