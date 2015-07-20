<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="BIS_ingreso/dp01_emisor.js" />

<g:form name="dp01" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post" >

	<g:hiddenField name="dictamen" value="${bis?.dictamen}"/>
	
	<fieldset>
 		<legend>Ingreso Datos 77Bis</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" requerido="true" tipo="rut" nombre="rut_emisor" rotulo="RUT Emisor" valor="${rut_emisor}" />
				<isl:textInput cols="3-8" requerido="true" nombre="nombre_emisor" rotulo="Nombre Emisor"  valor="${nombre_emisor}" />
				<div class="salto-de-linea"></div>
				<isl:textInput cols="1-8" requerido="true" tipo="rut" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${bis?.runTrabajador}" />
				<isl:textInput cols="1-8" requerido="true" tipo="rut" nombre="rut_empleador" rotulo="RUT Empleador" valor="${bis?.rutEmpleador}" />
				<isl:calendar cols="1-8" requerido="true" nombre="fecha_recepcion" ayuda="dd-mm-aaaa" rotulo="Fecha Recepción Carta" valor="${bis?.fechaRecepcion}"/>				
				<isl:calendar cols="1-8" requerido="true" nombre="fecha_siniestro" ayuda="dd-mm-aaaa" rotulo="Fecha Siniestro o Primeros Sintomas" valor="${bis?.fechaSiniestro}"/>
			</div>
		</div>
		<legend></legend>
		
		<div class="pure-g-r">
			<div class="pure-u-1">			
				<div class="salto-de-linea"></div>
				<isl:combo cols="2-8" requerido="true"  noSelection="${['':'Seleccione...']}" 
				 			nombre="tipoSiniestro" rotulo="Siniestro en Cobro" 
				 			from="${tipoSiniestro}"  optionValue="${{it.descripcion}}" valor="${bis?.tipoSiniestro?.codigo}"  />
		        <isl:textInput cols="2-8" requerido="true" nombre="montoSolicitado" rotulo="Monto Solicitado" ayuda="\$" tipo="numero" valor="${bis?.montoSolicitado}" maxLargo="9" />
			</div>
		</div>
		

		
		<legend></legend>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit id="siguiente" value="Siguiente"  action="postDp01" class="pure-button pure-button-success"  />
	</div>
</g:form>