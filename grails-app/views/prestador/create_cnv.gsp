<%@ page import="cl.adexus.helpers.FormatosHelper" %>
			
			<g:form action="save" class="pure-form pure-form-stacked">
				<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
					<legend>Nuevo Convenio</legend>
					<div class="pure-g-r">
						<div class="pure-u-1">	

							
							<div class='salto-de-linea'></div>
							
							<g:if test="${prestadorInstance?.esPersonaJuridica}">
								<isl:textInput cols="4-8" tipo="text" nombre="run" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaJuridica?.rut)}" deshabilitado="true"/>
							</g:if>
							<g:else>
								<isl:textInput cols="4-8" tipo="text" nombre="run" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaNatural?.run)}" deshabilitado="true"/>
							</g:else>							

							<g:if test="${prestadorInstance?.esPersonaJuridica}">
								<isl:textInput cols="4-8" tipo="text" nombre="nombrePrestador" rotulo="Nombre/Razon Social" valor="${prestadorInstance?.personaJuridica?.razonSocial}" deshabilitado="true"/>
							</g:if>
							<g:else>
								<isl:textInput cols="4-8" tipo="text" nombre="nombrePrestador" rotulo="Nombre/Razon Social" valor="${prestadorInstance?.personaNatural?.nombre} ${prestadorInstance?.personaNatural?.apellidoPaterno} ${prestadorInstance?.personaNatural?.apellidoMaterno}" deshabilitado="true"/>
							</g:else>
							
							<div class='salto-de-linea'></div>
							
							<isl:textInput cols="4-8" tipo="text" nombre="esPersonaJuridica" rotulo="Persona" valor="${prestadorInstance?.esPersonaJuridica ? "Juridica" : "Natural"}" deshabilitado="true"/>
							<isl:textInput cols="4-8" tipo="text" nombre="tipoPrestador" rotulo="Tipo Prestador" valor="${prestadorInstance?.tipoPrestador?.descripcion}" deshabilitado="true"/>
							
							<div class='salto-de-linea'></div>
							<legend>Datos Convenio</legend>
							<isl:radiogroup cols="4-8" requerido="true" nombre="esActivo" name="esActivo" labels="['Activado','Desactivado']" values="[1,2]" valor="${prestadorInstance?.esActivo ? 1 : 2}">
								${it.label}
								${it.radio}
							</isl:radiogroup>							
							
							<div class='salto-de-linea'></div>
							<isl:textInput cols="4-8" requerido="true" tipo="text" nombre="nombre" rotulo="Nombre Convenio" valor="${convenio?.nombre}" maxLargo="255"/>	
							<isl:combo cols="4-8" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Tipo Convenio" nombre="tipoConvenio" from="${tipoConvenio}" valor="${convenio?.tipoConvenio?.codigo}"/>
							
							<div class='salto-de-linea'></div>
							
							<isl:textInput cols="2-8" requerido="true" tipo="text" nombre="numeroResolucion" rotulo="Número Resolución" valor="${convenio?.numeroResolucion}"/>
							<isl:calendar cols="2-8" requerido="true" tipo="calendar" nombre="fechaResolucion" rotulo="Fecha de Resolución" valor="${convenio?.fechaResolucion}"/>
														
							<isl:textInput cols="2-8" requerido="true" tipo="text" nombre="numeroLicitacion" rotulo="ID Licitación" valor="${convenio?.numeroLicitacion}"/>
							<isl:calendar cols="2-8" requerido="true" tipo="calendar" nombre="fechaAdjudicacion" rotulo="Fecha de Adjudicación" valor="${convenio?.fechaAdjudicacion}"/>
							
							<div class='salto-de-linea'></div>
							
							<isl:calendar cols="2-8" requerido="true" tipo="calendar" nombre="inicio" rotulo="Fecha de Inicio" valor="${convenio?.inicio}"/>
							<isl:calendar cols="2-8" requerido="true" tipo="calendar" nombre="termino" rotulo="Fecha de Termino" valor="${convenio?.termino}"/>
							
							<isl:textInput cols="2-8" requerido="true" tipo="text" nombre="periodoReajustable" rotulo="Periodo de Reajuste" valor="${convenio?.periodoReajustable}"/>
							<isl:calendar cols="2-8" requerido="true" tipo="calendar" nombre="fechaProximoReajuste" rotulo="Fecha próx reajuste" valor="${convenio?.fechaProximoReajuste}"/>

							<div class='salto-de-linea'></div>
							
							<isl:textInput cols="2-8" requerido="true" tipo="numero" nombre="recargoHorarioInhabil" rotulo="% Recargo horario inhabil" valor="${convenio?.recargoHorarioInhabil}" maxLargo="3"/>
							<isl:textInput cols="2-8" requerido="true" tipo="numero" nombre="montoConvenido" rotulo="Monto Convenido" valor="${convenio?.montoConvenido}" maxLargo="15"/>

							<div class='salto-de-linea'></div>
							
							<fieldset>
							<legend>Responsable Convenio Prestador</legend>
							<isl:textInput cols="3-8" requerido="true" tipo="text" nombre="nombreResponsable" rotulo="Nombre" valor="${convenio?.nombreResponsable}" maxLargo="255"/>
							<isl:textInput cols="2-8" requerido="true" tipo="text" nombre="cargoResponsable" rotulo="Cargo" valor="${convenio?.cargoResponsable}" maxLargo="255"/>
							<isl:textInput cols="1-8" requerido="true" tipo="numero" nombre="telefonoResponsable" rotulo="Teléfono" valor="${convenio?.telefonoResponsable}" maxLargo="20"/>
							<isl:textInput cols="2-8" requerido="true" tipo="email" nombre="emailResponsable" rotulo="Email" valor="${convenio?.emailResponsable}" maxLargo="255"/>
							</fieldset>
							
							<fieldset>
							<legend>Responsable ISL</legend>
							<isl:textInput cols="3-8" requerido="true" tipo="text" nombre="nombreISL" rotulo="Nombre" valor="${convenio?.nombreISL}" maxLargo="255"/>
							<isl:textInput cols="2-8" requerido="true" tipo="text" nombre="cargoISL" rotulo="Cargo" valor="${convenio?.cargoISL}" maxLargo="255"/>
							<isl:textInput cols="1-8" requerido="true" tipo="numero" nombre="telefonoISL" rotulo="Teléfono" valor="${convenio?.telefonoISL}" maxLargo="20"/>
							<isl:textInput cols="2-8" requerido="true" tipo="email" nombre="emailISL" rotulo="Email" valor="${convenio?.emailISL}" maxLargo="255"/>
							</fieldset>

						</div>
					</div>
					
					<div class='salto-de-linea'></div>
					
					<div class="pure-g-r">
						<isl:button tipo="siguiente" value="Guardar" action="save_cnv"/>
						<button class="pure-button pure-button-secondary" onclick="document.forms[0].action='edit';document.forms[0].submit();">Volver</button>
					</div>
					
			</g:form>

