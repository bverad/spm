<%@ page import="cl.adexus.helpers.FormatosHelper" %>
			
			<g:form action="update_cs" class="pure-form pure-form-stacked">
				<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
				<g:hiddenField name="centroSaludId" value="${centroSalud?.id}" />
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
								<isl:textInput cols="4-8" tipo="text" nombre="nombrePrestador" rotulo="Nombre/Razon Social" valor="${prestadorInstance?.personaNatural?.nombre}" deshabilitado="true"/>
							</g:else>
							
							<div class='salto-de-linea'></div>
							
							<isl:textInput cols="4-8" tipo="text" nombre="esPersonaJuridica" rotulo="Persona" valor="${prestadorInstance?.esPersonaJuridica ? "Jurídica" : "Persona"}" deshabilitado="true"/>
							<isl:textInput cols="4-8" tipo="text" nombre="tipoPrestador" rotulo="Tipo Prestador" valor="${prestadorInstance?.tipoPrestador?.descripcion}" deshabilitado="true"/>
							
							<div class='salto-de-linea'></div>
							<fieldset>
							<legend>Datos Centro de Salud</legend>
							<isl:radiogroup cols="3-8" requerido="false"  nombre="esActivo" name="esActivo" labels="['Activado','Desactivado']" values="[1,2]" valor="${centroSalud?.esActivo ? 1 : 2}">
								${it.label}
								${it.radio}
							</isl:radiogroup>							
							
							<div class='salto-de-linea'></div>
							<isl:textInput cols="3-8" requerido="true" tipo="text" nombre="nombre" rotulo="Nombre" valor="${centroSalud?.nombre}" maxLargo="255"/>	
							<isl:textInput cols="3-8" requerido="true" tipo="text" nombre="direccion" rotulo="Dirección" valor="${centroSalud?.direccion}" maxLargo="255"/>								
							<isl:combo cols="2-8" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Comuna" nombre="comuna" from="${comunas}" valor="${centroSalud?.comuna?.codigo}"/>
							
							<div class='salto-de-linea'></div>						
							<isl:textInput cols="2-8" requerido="true" tipo="email" nombre="email" rotulo="Email" valor="${centroSalud?.email}" maxLargo="255"/>
							<isl:textInput cols="1-8" requerido="true" tipo="numero" nombre="telefono" rotulo="Teléfono" valor="${centroSalud?.telefono}" maxLargo="20"/>
							
							<isl:textInput cols="1-8" tipo="numero" nombre="numeroCamas" rotulo="Número de Camas" valor="${centroSalud?.numeroCamas}" maxLargo="3"/>
							<isl:textInput cols="1-8" tipo="numero" nombre="numeroAmbulancias" rotulo="Número de Ambulancias" valor="${centroSalud?.numeroAmbulancias}" maxLargo="3"/>							
							
							<isl:combo cols="1-8" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Tipo de Centro" nombre="tipoCentroSalud" from="${tipoCentroSalud}" valor="${centroSalud?.tipoCentroSalud?.codigo}"/>
							<div id="dv_OtroCentro" style="display:none;">
								<isl:textInput cols="2-8" requerido="true" tipo="text" nombre="otroCentro" rotulo="Otro Centro" valor="${centroSalud?.otroCentro}" maxLargo="255"/>
							</div>
							
							<div class='salto-de-linea'></div>
							</fieldset>
							<fieldset>
								<legend>Servicios:<font color='red'>*</font></legend>				
								<g:checkBox id="atencionAmbulancia" name="atencionAmbulancia" value="ATM" checked="${centroSalud?.atencionAmbulancia ? 'true' : 'false' }" /> Atención médica ambulatoria
								<g:checkBox id="pabellon" name="pabellon" value="PBL" checked="${centroSalud?.pabellon ? 'true' : 'false' }" /> Pabellón
								<g:checkBox id="hospitalizacion" name="hospitalizacion" value="HSP" checked="${centroSalud?.hospitalizacion ? 'true' : 'false' }" />	Hospitalización
								<g:checkBox id="atencionUrgencias" name="atencionUrgencias" value="ATU" checked="${centroSalud?.atencionUrgencias ? 'true' : 'false' }" />	Atención de Urgencias
								<g:checkBox id="trasladoPacientes" name="trasladoPacientes" value="TDP" checked="${centroSalud?.trasladoPacientes ? 'true' : 'false' }" />	Traslado de Pacientes								
								<g:checkBox id="salaDeRayos" name="salaDeRayos" value="SDR" checked="${centroSalud?.salaDeRayos ? 'true' : 'false' }" />	Sala de Rayos
								<g:checkBox id="rescateUrgencias" name="rescateUrgencias" value="RDU" checked="${centroSalud?.rescateUrgencias ? 'true' : 'false' }" /> Rescate de Urgencias
								<g:checkBox id="kinesiologia" name="kinesiologia" value="KNS" checked="${centroSalud?.kinesiologia ? 'true' : 'false' }" /> Kinesiología
								<g:checkBox id="imagenologia" name="imagenologia" value="IMG" checked="${centroSalud?.imagenologia ? 'true' : 'false' }" /> Imagenología

								<div class='salto-de-linea'></div>

								<g:checkBox name="otro" value="OTR" checked="${centroSalud?.otro ? 'true' : 'false' }" /> Otro
								
								<div class='salto-de-linea'></div>
								<div id="dv_cual" style="display:none;">
									<isl:textInput cols="2-8" rotulo="cual ?" nombre="cual" valor="${centroSalud?.cual}" />
								</div>
							</fieldset>
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

					   var hideShowOtroCentro = function(e) {
						   var node = Y.one('#tipoCentroSalud');
						   var otroCentro = node.get('options').item(node.get('selectedIndex')).get('text');

						   if (otroCentro.toUpperCase() == "OTRO") {
							   document.getElementById("dv_OtroCentro").style.display = "block";
							   Y.one("#otroCentro").set('required', 'required');
							}else{
								document.getElementById("dv_OtroCentro").style.display = "none";
								Y.one("#otroCentro").set('value', '');
							    Y.one("#otroCentro").set('required', false);
							}
						   
						 };
							 					   
				      Y.on("click", hideShowOtro, "#otro");
				      Y.on("available", hideShowOtro, "#otro");

				      Y.on("click", hideShowOtroCentro, "#tipoCentroSalud");
				      Y.on("available", hideShowOtroCentro, "#otro");

				      var btnGuardar = Y.one(document.getElementsByName('_action_update_cs')[0]);
				      btnGuardar.on('click', function (e) {
				      	this.get('id'); 
				       	e.target.get('id'); 
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
				    	  
					  };
				  				      
				      
				      
				   });
				</script>
					
				<div class='salto-de-linea'></div>
				
				<div class="pure-g-r">
					<isl:button tipo="siguiente" value="Actualizar" action="update_cs"/>
					<button class="pure-button pure-button-secondary" onclick="document.forms[0].action='edit';document.forms[0].submit();">Volver</button>
				</div>
					
			</g:form>