<%@ page import="cl.adexus.helpers.FormatosHelper" %>
	<g:form action="save" class="pure-form pure-form-stacked">
		<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
		<g:hiddenField name="centroSaludId" value="${centroSalud?.id}" />
			<fieldset>
			<legend>Información de prestador</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">	
					
					<g:if test="${prestadorInstance?.esPersonaJuridica}">
						<isl:textOutput cols="1-10" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaJuridica?.rut)}"/>
					</g:if>
					<g:else>
						<isl:textOutput cols="1-10" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaNatural?.run)}"/>
					</g:else>							

					<g:if test="${prestadorInstance?.esPersonaJuridica}">
						<isl:textOutput cols="2-10" rotulo="Nombre/Razón Social" valor="${prestadorInstance?.personaJuridica?.razonSocial}"/>
					</g:if>
					<g:else>
						<isl:textOutput cols="2-10" rotulo="Nombre/Razón social" valor="${prestadorInstance?.personaNatural?.nombre}"/>
					</g:else>
					
					<div class='salto-de-linea'></div>
					
					<isl:textOutput cols="4-8" rotulo="Persona" valor="${prestadorInstance?.esPersonaJuridica ? "Juridica" : "Persona"}"/>
					<isl:textOutput cols="4-8" rotulo="Tipo Prestador" valor="${prestadorInstance?.tipoPrestador?.descripcion}"/>
					
					<div class='salto-de-linea'></div>
					<fieldset>
					<legend>Datos Centro de Salud</legend>
					<isl:textOutput cols="3-8" rotulo="Estado" valor="${prestadorInstance?.esActivo ? 'Activado' : 'Desactivado'}" />
					
					<div class='salto-de-linea'></div>
					<isl:textOutput cols="3-8" rotulo="Nombre" valor="${centroSalud?.nombre}"/>	
					<isl:textOutput cols="3-8" rotulo="Dirección" valor="${centroSalud?.direccion}"/>								
					<isl:textOutput cols="2-8" rotulo="Comuna" valor="${centroSalud?.comuna?.descripcion}"/>
					
					<div class='salto-de-linea'></div>						
					<isl:textOutput cols="2-8" rotulo="Email" valor="${centroSalud?.email}"/>
					<isl:textOutput cols="1-8" rotulo="Teléfono" valor="${centroSalud?.telefono}"/>
					
					<isl:textOutput cols="1-8" rotulo="Número de Camas" valor="${centroSalud?.numeroCamas ? centroSalud?.numeroCamas : '' }"/>
					<isl:textOutput cols="1-8" rotulo="Número de Ambulancias" valor="${centroSalud?.numeroAmbulancias ? centroSalud?.numeroAmbulancias : ''}"/>							
					
					<isl:textOutput cols="1-8" rotulo="Tipo de Centro" nombre="tipoCentroSalud" from="${tipoCentroSalud}" valor="${centroSalud?.tipoCentroSalud?.descripcion}"/>
					<isl:textOutput cols="2-8" rotulo="Otro Centro" nombre="otroCentro"  valor="${centroSalud?.otroCentro ? centroSalud?.otroCentro : ''}"/>
					
					<div class='salto-de-linea'></div>
					</fieldset>
					<fieldset>
						<legend>Servicios:</legend>				
						<g:checkBox name="atencionAmbulancia" disabled="true" value="ATM" checked="${centroSalud?.atencionAmbulancia ? 'true' : 'false' }" />	Atención médica ambulatoria
						<g:checkBox name="pabellon" value="PBL" disabled="true" checked="${centroSalud?.pabellon ? 'true' : 'false' }" />	Pabellón
						<g:checkBox name="hospitalizacion" disabled="true" value="HSP" checked="${centroSalud?.hospitalizacion ? 'true' : 'false' }" />	Hospitalización
						<g:checkBox name="atencionUrgencias" disabled="true" value="ATU" checked="${centroSalud?.atencionUrgencias ? 'true' : 'false' }" />	Atención de Urgencias
						<g:checkBox name="trasladoPacientes" disabled="true" value="TDP" checked="${centroSalud?.trasladoPacientes ? 'true' : 'false' }" />	Traslado de Pacientes								
						<g:checkBox name="salaDeRayos" disabled="true" value="SDR" checked="${centroSalud?.salaDeRayos ? 'true' : 'false' }" />	Sala de Rayos
						<g:checkBox name="rescateUrgencias" disabled="true" value="RDU" checked="${centroSalud?.rescateUrgencias ? 'true' : 'false' }" />	Rescate de Urgencias
						<g:checkBox name="kinesiologia" disabled="true" value="KNS" checked="${centroSalud?.kinesiologia ? 'true' : 'false' }" />	Kinesiología
						<g:checkBox name="imagenologia" disabled="true" value="IMG" checked="${centroSalud?.imagenologia ? 'true' : 'false' }" />	Imagenología

						<div class='salto-de-linea'></div>

						<g:checkBox name="otro" disabled="true" value="OTR" checked="${centroSalud?.otro ? 'true' : 'false' }" /> Otro
						
						<div class='salto-de-linea'></div>
						<div id="dv_cual" style="display:none;">
							<isl:textInput cols="2-8" rotulo="cual ?" nombre="cual" valor="${centroSalud?.cual}" />
						</div>								
										
				</div>
			</div>		
			
		<script type="text/javascript">
		   YUI().use('node', 'event', function(Y) {

			   var hideShowOtro = function(e) {
				   var otro = document.getElementById("otro");
				   

				   if (otro.checked) {
					   document.getElementById("dv_cual").style.display = "block";
					}else{
						document.getElementById("cual").value = "";
						document.getElementById("dv_cual").style.display = "none";
					}
				   
				 };
		      Y.on("click", hideShowOtro, "#otro");
		      Y.on("available", hideShowOtro, "#otro");
		      
		   });
		</script>
											
			
			</fieldset>
			
			<div class="pure-g-r">
				<div class="pure-u-1">
					<input type="button" class="pure-button pure-button-secondary" onclick="document.forms[0].action='listar';document.forms[0].submit();" value="Volver" />
				</div>
			</div>
			
	</g:form>

