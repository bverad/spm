<%@ page import="cl.adexus.helpers.FormatosHelper" %>

		<g:form action="save_cs" class="pure-form pure-form-stacked">
			<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
				<fieldset>
					<legend>Incorporar Centro de Salud</legend>
					
						<div class="pure-g-r">
							<div class="pure-u-1">	
								
								<g:if test="${prestadorInstance?.esPersonaJuridica}">
									<isl:textInput cols="1-10" tipo="text" nombre="run" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaJuridica?.rut)}" deshabilitado="true"/>
								</g:if>
								<g:else>
									<isl:textInput cols="1-10" tipo="text" nombre="run" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaNatural?.run)}" deshabilitado="true"/>
								</g:else>							
		
								<g:if test="${prestadorInstance?.esPersonaJuridica}">
									<isl:textInput cols="4-10" tipo="text" nombre="nombrePrestador" rotulo="Nombre/Razón social" valor="${prestadorInstance?.personaJuridica?.razonSocial}" deshabilitado="true"/>
								</g:if>
								<g:else>
									<isl:textInput cols="4-10" tipo="text" nombre="nombrePrestador" rotulo="Nombre/Razón social" valor="${prestadorInstance?.personaNatural?.nombre} ${prestadorInstance?.personaNatural?.apellidoPaterno} ${prestadorInstance?.personaNatural?.apellidoMaterno}" deshabilitado="true"/>
								</g:else>
								
								<isl:textInput cols="2-10" tipo="text" nombre="esPersonaJuridica" rotulo="Persona" valor="${prestadorInstance?.esPersonaJuridica ? "Juridica" : "Natural"}" deshabilitado="true"/>
								<isl:textInput cols="3-10" tipo="text" nombre="tipoPrestador" rotulo="Tipo Prestador" valor="${prestadorInstance?.tipoPrestador?.descripcion}" deshabilitado="true"/>
					
								<div class="salto-de-linea"></div>
								<div class="form-subtitle">Datos Centro de Salud</div>
								<isl:radiogroup cols="3-8" requerido="false"  rotulo="Estado" nombre="esActivo" name="esActivo" labels="['Activado','Desactivado']" values="[1,2]" valor="${params?.esActivo ? 1 : 2}">
									${it.label}
									${it.radio}
								</isl:radiogroup>							
									
								<isl:textInput cols="3-10" requerido="true" tipo="text" nombre="nombre" rotulo="Nombre" valor="${centroSalud?.nombre}" maxLargo="255"/>	
								<isl:textInput cols="3-10" requerido="true" tipo="text" nombre="direccion" rotulo="Dirección" valor="${centroSalud?.direccion}" maxLargo="255"/>								
								<isl:combo cols="1-10" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Comuna" nombre="comuna" from="${comunas}" valor="${centroSalud?.comuna?.codigo}"/>
		
								
								<isl:textInput cols="1-10" requerido="true" tipo="email" nombre="email" rotulo="Email" valor="${centroSalud?.email}" maxLargo="255"/>
								<isl:textInput cols="1-10" requerido="true" tipo="numero" nombre="telefono" rotulo="Teléfono" valor="${centroSalud?.telefono}" maxLargo="20"/>
															
								<isl:textInput cols="1-10" tipo="numero" nombre="numeroCamas" rotulo="Número de camas" valor="${centroSalud?.numeroCamas}" maxLargo="3"/>
								<isl:textInput cols="2-10" tipo="numero" nombre="numeroAmbulancias" rotulo="Número de ambulancias" valor="${centroSalud?.numeroAmbulancias}" maxLargo="3"/>							
															
								<isl:combo cols="2-10" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Tipo de centro" nombre="tipoCentroSalud" from="${tipoCentroSalud}"  valor="${centroSalud?.tipoCentroSalud?.codigo}"/>
								<div id="dv_OtroCentro" style="display:none;">
									<isl:textInput cols="2-10" requerido="true" tipo="text" nombre="otroCentro" rotulo="Otro Centro" valor="${centroSalud?.otroCentro}" maxLargo="255"/>
								</div>
								<div class="salto-de-linea"></div>
	
								<div class="form-subtitle">Servicios:<font color='red'>*</font></div>				
								<g:checkBox id="atencionAmbulancia" name="atencionAmbulancia" value="ATM" checked="${centroSalud?.atencionAmbulancia ? 'checked' : 'false' }" />	Atención médica ambulatoria
								<g:checkBox id="pabellon" name="pabellon" value="PBL" checked="${centroSalud?.pabellon ? 'checked' : 'false' }" />	Pabellón
								<g:checkBox id="hospitalizacion" name="hospitalizacion" value="HSP" checked="${centroSalud?.hospitalizacion ? 'checked' : 'false' }" />	Hospitalización
								<g:checkBox id="atencionUrgencias" name="atencionUrgencias" value="ATU" checked="${centroSalud?.atencionUrgencias ? 'checked' : 'false' }" />	Atención de Urgencias
								<g:checkBox id="trasladoPacientes" name="trasladoPacientes" value="TDP" checked="${centroSalud?.trasladoPacientes ? 'checked' : 'false' }" />	Traslado de Pacientes								
								<g:checkBox id="salaDeRayos" name="salaDeRayos" value="SDR" checked="${centroSalud?.salaDeRayos ? 'checked' : 'false' }" />	Sala de Rayos
								<g:checkBox id="rescateUrgencias" name="rescateUrgencias" value="RDU" checked="${centroSalud?.rescateUrgencias ? 'checked' : 'false' }" />	Rescate de Urgencias
								<g:checkBox id="kinesiologia" name="kinesiologia" value="KNS" checked="${centroSalud?.kinesiologia ? 'checked' : 'false' }" />	Kinesiología
								<g:checkBox id="imagenologia" name="imagenologia" value="IMG" checked="${centroSalud?.imagenologia ? 'checked' : 'false' }" />	Imagenología
	
								<div class='salto-de-linea'></div>
	
								<g:checkBox id="otro" name="otro" value="OTR" checked="${centroSalud?.otro ? 'checked' : 'false' }" /> Otro
								
								<div class='salto-de-linea'></div>
								<div id="dv_cual" style="display:none;">
									<isl:textInput cols="2-8" rotulo="cual ?" nombre="cual" valor="${centroSalud?.cual}" />
								</div>
																																													
				</div>
			</div>														
		</fieldset>	
		
		<script type="text/javascript">
		   YUI().use('node', 'event', function(Y) {

			   var hideShowOtro = function(e) {
				   var otro = document.getElementById("otro");
				   document.getElementById("cual").value = "";

				   if (otro.checked) {
					   document.getElementById("dv_cual").style.display = "block";
					}else{
						document.getElementById("dv_cual").style.display = "none";
					}
				   
				 };

			   var hideShowOtroCentro = function(e) {
				   var node = Y.one('#tipoCentroSalud');
				   var otroCentro = node.get('options').item(node.get('selectedIndex')).get('text');

				   if (otroCentro.toUpperCase() == "OTRO") {
					   document.getElementById("dv_OtroCentro").style.display = "block";
					   Y.one("#otroCentro").set('required', true);
					}else{
						document.getElementById("dv_OtroCentro").style.display = "none";
						Y.one("#otroCentro").set('value', '');
					    Y.one("#otroCentro").set('required', false);
					}
				   
				 };
				 

		      Y.on("click", hideShowOtro, "#otro");
		      Y.on("click", hideShowOtroCentro, "#tipoCentroSalud");		      
		      Y.on("available", hideShowOtroCentro, "#tipoCentroSalud");		      
			
		      var btnGuardar = Y.one(document.getElementsByName('_action_save_cs')[0]);
		      btnGuardar.on('click', function (e) {

					return serviciosSeleccionados();
		      });
			
		      var serviciosSeleccionados= function() {
					var result = false;

					if (document.getElementsByName('atencionAmbulancia')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('pabellon')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('hospitalizacion')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('atencionUrgencias')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('trasladoPacientes')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('salaDeRayos')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('rescateUrgencias')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('kinesiologia')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('imagenologia')[0].checked == true)
						result = true;
					
					if (document.getElementsByName('otro')[0].checked == true && document.getElementsByName('cual')[0].value != "")
						result = true;

					if (!result) {
						document.getElementById("atencionAmbulancia").required = true;   
					} else {
						document.getElementById("atencionAmbulancia").required = false;
					}

					if (!result)
						alert("Se debe tener seleccionado al menos un servicio. En el caso de Otro se debe indicar cual.");

					return result;
		      }
		      
		   });
		</script>
				
		<div class='salto-de-linea'></div>
		
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:button tipo="siguiente" value="Guardar" action="save_cs"/>
				<button class="pure-button pure-button-secondary" onclick="document.forms[0].action='edit';document.forms[0].submit();">Volver</button>
			</div>
		</div>		
			
	</g:form>
		
							
