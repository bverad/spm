<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="OTP/ingreso_dp04.js" />
<g:form name="dp04" action="guardarReembolso" class="pure-form pure-form-stacked" >
<g:hiddenField name="siniestroId" value="${siniestroId}"/>
<g:hiddenField name="trabajadorRun" value="${siniestro?.trabajador?.run}" />
	<style>
		.formulario-fieldset {
			border: 0px;
			border-color:#000000;
			border-style: solid;
			padding: 5px 0 20px 10px; 
		}	
	</style>

	<fieldset>
 		<legend>Completar Formulario de Reembolso<br /><br />(Accidentes del Trabajo y Enfermedades Profesionales)</legend>

		<label for="siniestroNumero" style="float: left; margin-right: 3px;">Siniestro N°</label>
		<input type="text" name="siniestroNumero" style="float: left; margin-right: 10px;" disabled="disabled" value="${siniestro?.id}" />
		<label for="siniestroTipo" style="float: left; margin-right: 3px;">Tipo Siniestro</label>
		<input type="text" name="siniestroTipo" style="float: left; margin-right: 3px;" disabled="disabled" value="${tipoSiniestro}"/>

		<div class='salto-de-linea'></div>
		
		<div class="form-subtitle">I. IDENTIFICACIÓN DEL BENEFICIO (Marque las alternativas que correspondan)</div>
		<div class="formulario-fieldset">
			<input type="checkbox" name="idBeneficio" value="traslado" <g:if test="${idBeneficio?.traslado == true }">checked="checked"</g:if>               /> 1. Traslado de Paciente<br />
			<input type="checkbox" name="idBeneficio" value="medicamentos" <g:if test="${idBeneficio?.medicamentos == true }">checked="checked"</g:if>       /> 2. Medicamentos e Insumos Médicos<br />
			<input type="checkbox" name="idBeneficio" value="hospitalizacion" <g:if test="${idBeneficio?.hospitalizacion == true }">checked="checked"</g:if> /> 3. Hospitalización o Atención de 
			Urgencia<br />
			<input type="checkbox" name="idBeneficio" value="alojamiento" <g:if test="${idBeneficio?.alojamiento == true }">checked="checked"</g:if>         /> 4. Alojamiento y/o Colación<br />
		</div>
		
		<div class="form-subtitle">II. IDENTIFICACIÓN DEL TRABAJADOR</div>
		
		<isl:textInput cols="1-8"  nombre="runTrabajador"    rotulo="RUN Trabajador" deshabilitado="true" requerido="true" tipo="rut" valor="${FormatosHelper.runFormatStatic(siniestro?.trabajador?.run)}"/>

		<div class='salto-de-linea'></div>
		
		<isl:textInput cols="4-8"  nombre="nombreTrabajador" rotulo="Nombre Trabajador" deshabilitado="true" requerido="true" valor="${trabajadorNombreCompleto}"/>
		
		<div class='salto-de-linea'></div>
		
		<isl:textInput cols="4-8"  nombre="direccion"        rotulo="Dirección"         readonly="true" requerido="true" valor="${otrosDatosTrabajador?.direccion}"/>
		
		<div class='salto-de-linea'></div>
	
	    <isl:combo cols="1-8"        requerido="true"           noSelection="${['':'Seleccione ...']}"       nombre="comuna"
	               rotulo="Comuna"   from="${comunas}"          valor="${otrosDatosTrabajador.comuna}"/>
		<isl:textInput cols="1-8"    nombre="telefonoFijo"      rotulo="Telefono Fijo"      tipo="numero" valor="${otrosDatosTrabajador?.telefonoFijo}"/>
		<isl:textInput cols="1-8"    nombre="celular"           id="celular"         rotulo="Celular"            tipo="numero" valor="${otrosDatosTrabajador?.celular}"/>
		<isl:textInput cols="1-8"    nombre="correoElectronico" id="correoElectrico" rotulo="Correo Electrónico" tipo="email"  valor="${otrosDatosTrabajador?.email}"/>
		
		<div class='salto-de-linea'></div>		
		
		<div class="form-subtitle">III. IDENTIFICACIÓN DEL SOLICITANTE</div>

		<input type="radio" class="clicker-solicitante" name="clicker-solicitante" id="clicker-solicitante-trabajador" value="Trabajador" <g:if test="${solicitante?.tipo == "Trabajador" }">checked="checked"</g:if> /> Trabajador&nbsp;
		<input type="radio" class="clicker-solicitante" name="clicker-solicitante" id="clicker-solicitante-empleador"  value="Empleador" <g:if test="${solicitante?.tipo == "Empleador" }">checked="checked"</g:if> /> Empleador&nbsp;
		<input type="radio" class="clicker-solicitante" name="clicker-solicitante" id="clicker-solicitante-prestador"  value="Prestador Médico" <g:if test="${solicitante?.tipo == "Prestador Méçdico" }">checked="checked"</g:if> /> Prestador Médico&nbsp;
		<input type="radio" class="clicker-solicitante" name="clicker-solicitante" id="clicker-solicitante-otro"       value="Otro" <g:if test="${solicitante?.tipo == "Otro" }">checked="checked"</g:if> /> Otro&nbsp;

		<div class='salto-de-linea'></div>

		<isl:textInput cols="1-8"  nombre="solicitanteRut" rotulo="RUT" requerido="true" tipo="rut" valor="${FormatosHelper.runFormatStatic(solicitantePersona?.run)}"/>		
		<div class='salto-de-linea'></div>
		
		<isl:textInput cols="4-8"  nombre="solicitanteNombres" rotulo="Nombres" requerido="true" valor="${solicitantePersona?.nombre}"/>		
		<div class='salto-de-linea'></div>
		
		<isl:textInput cols="4-8"  nombre="solicitanteApellidoPaterno" rotulo="Apellido Paterno" requerido="true" valor="${solicitantePersona?.apellidoPaterno}"/>		
		<div class='salto-de-linea'></div>
		
		<isl:textInput cols="4-8"  nombre="solicitanteApellidoMaterno" rotulo="Apellido Materno" requerido="true" valor="${solicitantePersona?.apellidoMaterno}"/>		
		<div class='salto-de-linea'></div>
	
		<isl:textInput cols="4-8"  nombre="solicitanteRelacion" rotulo="Relación" valor="${solicitantePersonaRelacion}"/>
		<div class='salto-de-linea'></div>	
		
		<div class="form-subtitle">IV. OPCION DE PAGO</div>
			
		<isl:textInput cols="1-8"  nombre="montoSolicitado" rotulo="Monto Solicitado" requerido="true" tipo="numero" valor="${opcionDePago?.montoSolicitado}"/>
		<div class="salto-de-linea"></div>

		<isl:textInput cols="1-8"  nombre="cobradorRut" rotulo="Cobrador RUT" requerido="true" tipo="rut" valor="${FormatosHelper.runFormatStatic(odpPersona?.run)}"/>
		<div class="salto-de-linea"></div>

		<isl:textInput cols="4-8"  nombre="cobradorNombres" rotulo="Nombres" requerido="true" valor="${odpPersona?.nombre}"/>		
		<div class='salto-de-linea'></div>
		
		<isl:textInput cols="4-8"  nombre="cobradorApellidoPaterno" rotulo="Apellido Paterno" requerido="true" valor="${odpPersona?.apellidoPaterno}"/>		
		<div class='salto-de-linea'></div>
		
		<isl:textInput cols="4-8"  nombre="cobradorApellidoMaterno" rotulo="Apellido Materno" requerido="true" valor="${odpPersona?.apellidoMaterno}"/>		
		<div class='salto-de-linea'></div>

		<label for="tipoPago" style="display: inline-block; text-align: right; margin-right: 10px;">Tipo de Pago</label>
		<input type="radio" name="tipoPago" class="clicker-tipo-pago" id="clicker-tipo-pago-deposito" value="deposito" <g:if test="${opcionDePago?.tipoPago == "deposito" || opcionDePago?.tipoPago != "pagoPresencial"}">checked="checked"</g:if> /> <span style="margin-right: 20px;">Depósito</span>
		<input type="radio" name="tipoPago" class="clicker-tipo-pago" id="clicker-tipo-pago-presencial" value="pagoPresencial" <g:if test="${opcionDePago?.tipoPago == "pagoPresencial" }">checked="checked"</g:if> /> Pago Presencial Banco Estado<br />
			
		<div class="formulario-fieldset" style="margin: 0 0 10px 20px;" id="datos-deposito">
			<label for="cuentaTipo" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Tipo Cuenta</label>
			<g:select style="display: inline-block; width: 150px;" name="tipoCuenta" optionKey="codigo" optionValue="descripcion" value="${opcionDePago?.tipoCuenta}" from="${tipoCuentas}" noSelection="${['':'Seleccione ...']}" />
			<label for="cuentaNumero" style="display: inline-block; width: 132px; text-align: right; margin-right: 10px;">Número</label>
			<input type="text" name="cuentaNumero" style="display: inline-block; width: 150px;" value="${opcionDePago?.cuentaNumero}" /><br/>

			<label for="cuentaBanco" style="display: inline-block; width: 150px; text-align: right; margin-right: 10px;">Banco</label>
			<g:select style="display: inline-block; width: 150px;" name="cuentaBanco" optionKey="codigo" optionValue="descripcion" value="${opcionDePago?.cuentaBanco}" from="${bancos}" noSelection="${['':'Seleccione ...']}" />
		</div>
		
		<div class="form-subtitle">V. OBSERVACIONES (Indicar información relevante relativa al siniestro)</div>		
		<div class="formulario-fieldset" style="height: 150px; padding: 10px 10px 10px 10px;">
			<textarea id="observaciones-textarea" name="observaciones" style="resize: none; height: 100%; width: 100%;"><g:if test="${observaciones != null }">${observaciones}</g:if></textarea>
		</div>
		
	</fieldset>
	
	<div class="pure-g-r" >
		<g:actionSubmit action="cu04" class="pure-button pure-button-success imprimirSolicitud" value="Imprimir Solicitud de Reembolso"/>
		<input disabled="disabled" type="submit" id="btn-guardar" value="Guardar" class="pure-button pure-button-success" onclick="return false;">
	</div>
</g:form>

<!--  MODAL PARA PREGUNTAR SI ESTA CORRECTO O CORRIGE -->
<div style="letter-spacing: 0em;">
	<div id="modalsrc" class="yui-pe-content">
		<div id="modal-marco" >
			<g:form class="pure-form pure-form-stacked modalForm" name="modalForm" >
			    <fieldset>			
					<isl:textOutput cols="4-8" rotulo="Nombre Trabajador" valor="${trabajadorNombreCompleto}"/>
					<isl:textOutput cols="4-8" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(siniestro?.trabajador?.run)}"/>
					<div class='salto-de-linea'></div>
					<isl:textOutput cols="4-8"  nombre="cun" rotulo="Siniestro N°" valor="${siniestro?.id}"/>
					<isl:textOutput cols="4-8"  nombre="tipoSiniestro" rotulo="Tipo de Siniestro" valor="${tipoSiniestro}"/>
					<div class='salto-de-linea'></div>
			
					<div>¿ El formulario está completamente correcto ?<br />Asegurese de que el documento esté firmado antes de guardar</div>
			
					<div class='salto-de-linea'></div>
				</fieldset>
			</g:form>
		</div>
	</div>
</div>