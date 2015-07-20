<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.helpers.ColeccionesHelper" %>



<g:form name="dp02" class="pure-form pure-form-stacked" >
<g:hiddenField name="siniestroId" value="${diatOAPropuesta?.siniestroId}"/>
	<fieldset>
 		<legend>A. Identificación del empleador - DIAT-OA</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:textInput btnEdicion="true" cols="4-10" requerido="true" nombre="empleador_razonSocial" valor="${diatOAPropuesta?.empleador?.razonSocial}" rotulo="Razon Social" />
					<isl:textOutput cols="2-10" rotulo="RUT" valor="${FormatosHelper.runFormatStatic(diatOAPropuesta?.empleador?.rut)}"/>
					<g:hiddenField 	name="empleador_rut" value="${FormatosHelper.runFormatStatic(diatOAPropuesta?.empleador?.rut)}" />
					<isl:combo     btnEdicion="true" cols="4-10" requerido="true" noSelection="${['':'Seleccione ...']}" nombre="ciiuEmpleador" rotulo="Actividad económica empresa principal" from="${tipoActividadEconomica}" valor="${diatOAPropuesta?.ciiuEmpleador?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
					
					<isl:combo     btnEdicion="true" cols="1-10" requerido="true" nombre="propiedadEmpresa"            rotulo="Propiedad"    from="${tipoPropiedades}" valor="${diatOAPropuesta?.propiedadEmpresa?.codigo}" />
					<isl:combo 	   btnEdicion="true" cols="1-10" requerido="true" nombre="direccionEmpleadorTipoCalle" rotulo="Tipo calle"   from="${tipoCalles}"      valor="${diatOAPropuesta?.direccionEmpleadorTipoCalle?.codigo}"/> 
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="direccionEmpleadorNombreCalle"  rotulo="Dirección"   valor="${diatOAPropuesta?.direccionEmpleadorNombreCalle}"/>
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="direccionEmpleadorNumero"      tipo="numero" rotulo="Número"      valor="${diatOAPropuesta?.direccionEmpleadorNumero}"/>
					<isl:textInput btnEdicion="true" cols="3-10" 				  nombre="direccionEmpleadorRestoDireccion" rotulo="Referencias" valor="${diatOAPropuesta?.direccionEmpleadorRestoDireccion}"/>
													
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" noSelection="${['':'Seleccione ...']}" nombre="direccionEmpleadorComuna" rotulo="Comuna"  from="${comunas}" valor="${diatOAPropuesta?.direccionEmpleadorComuna?.codigo}"/>
					<isl:textInput btnEdicion="true" cols="1-10"                  nombre="telefonoEmpleador" tipo="numero" rotulo="Teléfono" valor="${diatOAPropuesta?.telefonoEmpleador}"/>
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="nTrabajadoresHombre" tipo="numero" rotulo="N° trab. hombres" valor="${diatOAPropuesta?.nTrabajadoresHombre}"/>
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="nTrabajadoresMujer" tipo="numero" rotulo="N° trab. mujeres" valor="${diatOAPropuesta?.nTrabajadoresMujer}"/>
					<isl:combo                       cols="2-10" requerido="true" nombre="tipoEmpresa" rotulo="Tipo empresa" from="${tipoEmpresas}" valor="${diatOAPropuesta?.tipoEmpresa?.codigo}" />
					<script type="text/javascript">
						YUI().use('node', function(Y) {
							var contratista = '2';
							var subcontratista = '3';
	
							var onLoad = function(e) {
								var tipoEmpresaSel = Y.one('#tipoEmpresa');
								if (tipoEmpresaSel) tipoEmpresaSel = tipoEmpresaSel.get('value');
								if (tipoEmpresaSel == contratista || tipoEmpresaSel == subcontratista) {
									Y.one("#dv_ciiuPrincipal").setStyle('display', 'block');
								} else {
									Y.one("#ciiuPrincipal").set('value', '');
									Y.one("#dv_ciiuPrincipal").setStyle('display', 'none');
								}
							}
							
							Y.on("change", onLoad, "#tipoEmpresa");
							Y.on("available", onLoad, "#tipoEmpresa");
						});
					</script>
					<div id='dv_ciiuPrincipal'>
						<isl:combo btnEdicion="true" cols="4-10" noSelection="${['':'Seleccione ...']}" nombre="ciiuPrincipal" rotulo="" from="${tipoActividadEconomica}" valor="${diatOAPropuesta?.ciiuPrincipal?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
					</div>
				</div>
			</div>
		<legend>B. Identificación del trabajador - DIAT-OA</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:textInput 					 cols="2-10" requerido="true" nombre="trabajador_nombre" rotulo="Nombres" valor="${diatOAPropuesta?.trabajador?.nombre}"/>
					<isl:textInput 					 cols="2-10" requerido="true" nombre="trabajador_apellidoPaterno" rotulo="Apellido paterno" valor="${diatOAPropuesta?.trabajador?.apellidoPaterno}"/>
					<isl:textInput 					 cols="2-10" requerido="true" nombre="trabajador_apellidoMaterno" rotulo="Apellido materno" valor="${diatOAPropuesta?.trabajador?.apellidoMaterno}"/>
					<isl:textOutput cols="1-10" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(diatOAPropuesta?.trabajador?.run)}"/>
					<g:hiddenField 	name="trabajador_run" value="${FormatosHelper.runFormatStatic(diatOAPropuesta?.trabajador?.run)}" />
					<isl:combo     					 cols="1-10" requerido="true" nombre="trabajador_sexo" from="${sexos}" rotulo="Sexo" valor="${diatOAPropuesta?.trabajador?.sexo}" />
					<isl:textInput 					 cols="1-10" requerido="true" nombre="trabajador_fechaNacimiento" rotulo="Fecha nacimiento" requerido="true" valor="${FormatosHelper.fechaCortaStatic(diatOAPropuesta?.trabajador?.fechaNacimiento)}" tipo="fecha"/>			
					<isl:combo     btnEdicion="true" cols="1-10" requerido="true" nombre="nacionalidadTrabajador"  rotulo="Nacionalidad" from="${naciones}" valor="${diatOAPropuesta?.nacionalidadTrabajador?.codigo}" />	
	    			<div class="salto-de-linea"></div>
	    			
	    			<isl:combo     btnEdicion="true" cols="1-10" requerido="true" nombre="direccionTrabajadorTipoCalle"  rotulo="Tipo calle" from="${tipoCalles}" valor="${diatOAPropuesta?.direccionTrabajadorTipoCalle?.codigo}" />
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="direccionTrabajadorNombreCalle" rotulo="Dirección" valor="${diatOAPropuesta?.direccionTrabajadorNombreCalle}" />
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="direccionTrabajadorNumero" tipo="numero" rotulo="Número" valor="${diatOAPropuesta?.direccionTrabajadorNumero}" />
					<isl:textInput btnEdicion="true" cols="2-10" 				  nombre="direccionTrabajadorRestoDireccion" rotulo="Referencias" valor="${diatOAPropuesta?.direccionTrabajadorRestoDireccion}" />
					<isl:combo     btnEdicion="true" cols="1-10" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Comuna" nombre="direccionTrabajadorComuna" from="${comunas}" valor="${diatOAPropuesta?.direccionTrabajadorComuna?.codigo}" />
					<isl:textInput btnEdicion="true" cols="1-10"   tipo="numero"               nombre="telefonoTrabajador" rotulo="Teléfono" valor="${diatOAPropuesta?.telefonoTrabajador}" />
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="profesionTrabajador" rotulo="Profesión" valor="${diatOAPropuesta?.profesionTrabajador}" />
	    			<div class="salto-de-linea"></div>
	    			<isl:combo     btnEdicion="true" cols="1-10" requerido="false" nombre="etnia"  rotulo="Pueblo originario" from="${etnias}" valor="${diatOAPropuesta?.etnia?.codigo}" />
					<isl:textInput btnEdicion="true" cols="1-10" requerido="false" rotulo="Ingreso empresa" nombre="fechaIngresoEmpresa" valor="${FormatosHelper.fechaCortaStatic(diatOAPropuesta?.fechaIngresoEmpresa)}" tipo="fecha"/>
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="duracionContrato" rotulo="Tipo contrato" from="${tipoDuracionContratos}" valor="${diatOAPropuesta?.duracionContrato?.codigo}" />
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="tipoRemuneracion" rotulo="Tipo ingreso" from="${tipoRemuneraciones}" valor="${diatOAPropuesta?.tipoRemuneracion?.codigo}" />
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="categoriaOcupacion" rotulo="Categoria ocupacional" from="${categoriaOcupaciones}" valor="${diatOAPropuesta?.categoriaOcupacion?.codigo}" />
					<isl:combo     btnEdicion="true" cols="2-10" noSelection="${['':'Seleccione ...']}" nombre="sistemaSalud" rotulo="Sistema salud" from="${sistemaSalud}" valor="${diatOAPropuesta?.sistemaSalud?.codigo}" />
					
					<div id='dv_otro_pueblo'>
						<isl:textInput cols="3-10" nombre="otroPueblo" rotulo="Otro Pueblo" valor="${diatOAPropuesta?.otroPueblo}" />
						<div class='salto-de-linea'></div>
					</div>
					<script type="text/javascript">
					   YUI().use('node', 'event', function(Y) {
	
						   var hideShowOtroPueblo = function(e) {
							   var etnia = document.getElementById("etnia");
							   var str = etnia.options[etnia.selectedIndex].text.toUpperCase();
	
							   if (str != "OTRO") {
								   document.getElementById("otroPueblo").value = "";
								   document.getElementById("dv_otro_pueblo").style.display = "none";
								}else{
									document.getElementById("dv_otro_pueblo").style.display = "block";
								}
							   
							 };
	
						   
					      Y.on("change", hideShowOtroPueblo, "#etnia");
					      Y.on("available", hideShowOtroPueblo, "#etnia");
					      
					   });
					</script>				
				</div>
			</div>
		<legend>C. Datos del accidente - DIAT-OA</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput 					 cols="1-10" deshabilitado="true" requerido="true" nombre="fechaAccidente" rotulo="Fecha Accidente" valor="${FormatosHelper.fechaCortaStatic(diatOAPropuesta?.fechaAccidente)}" />
				<isl:textInput 					 cols="1-10" requerido="true" tipo="hora" nombre="fechaAccidente_hora" rotulo="Hora accidente" valor="${FormatosHelper.horaCortaStatic(diatOAPropuesta?.fechaAccidente)}"/>
				<isl:textInput btnEdicion="true" cols="1-10" requerido="true" tipo="hora" nombre="horaIngreso" rotulo="Ingreso trabajo" valor="${FormatosHelper.horaCortaStatic(diatOAPropuesta?.horaIngreso)}"/>
				<isl:textInput btnEdicion="true" cols="1-10" requerido="true" tipo="hora" nombre="horaSalida" rotulo="Salida del trabajo" valor="${FormatosHelper.horaCortaStatic(diatOAPropuesta?.horaSalida)}"/>
				<isl:textInput btnEdicion="true" cols="4-10" requerido="true" nombre="direccionAccidenteNombreCalle" rotulo="Dirección" valor="${diatOAPropuesta?.direccionAccidenteNombreCalle}"/>
				<isl:combo     btnEdicion="true"  cols="2-10" requerido="true" rotulo="Comuna" noSelection="${['':'Seleccione ...']}" nombre="direccionAccidenteComuna"  from="${comunas}" valor="${diatOAPropuesta?.direccionAccidenteComuna?.codigo}"/>
				
				<div class="salto-de-linea"></div>
				<isl:textInput btnEdicion="true" cols="7-10" requerido="true" nombre="que" rotulo="Qué estaba haciendo el trabajador al momento o justo antes del accidente" valor="${diatOAPropuesta?.que}"/>
				<isl:textInput btnEdicion="true" cols="3-10" requerido="true" nombre="lugarAccidente" rotulo="Dónde ocurrió el accidente (sección, edificio, área, etc.)" valor="${diatOAPropuesta?.lugarAccidente}"/>
				<isl:textInput btnEdicion="true" cols="7-10" requerido="true" nombre="como" rotulo="Descripción de cómo ocurrió el accidente" valor="${diatOAPropuesta?.como}"/>
				<isl:textInput btnEdicion="true" cols="3-10" requerido="true" nombre="trabajoHabitualCual" rotulo="Trabajo habitual del accidentado" valor="${diatOAPropuesta?.trabajoHabitualCual}"/>
				<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="gravedad" rotulo="Clasificación del accidente" from="${criterioGravedades}" valor="${diatOAPropuesta?.gravedad?.codigo}" />	
				<isl:combo     btnEdicion="true" cols="3-10" requerido="true" nombre="esAccidenteTrayecto" rotulo="Tipo de accidente" from="${tipoAccidentes}" valor="${diatOAPropuesta?.esAccidenteTrayecto ? '2' : '1'}" /> 
				<script type="text/javascript">
					YUI().use('node', 'event', function(Y) {
						var labels = document.getElementsByTagName('LABEL');
						
						var requiredTipoAccidenteTrayecto = function(e) {
							var esAccidenteTrayecto = document.getElementById("esAccidenteTrayecto");
							var str = esAccidenteTrayecto.options[esAccidenteTrayecto.selectedIndex].text.toUpperCase();
							if (str != "TRAYECTO") {
								for (i = 0; i < labels.length; i++) {
									if (labels[i].htmlFor == 'tipoAccidenteTrayecto')
										labels[i].innerHTML = "Accidente de trayecto";
								}
								document.getElementById("tipoAccidenteTrayecto").value = "";
								document.getElementById("tipoAccidenteTrayecto").disabled = true;
							} else {
								for (i = 0; i < labels.length; i++) {
									if (labels[i].htmlFor == 'tipoAccidenteTrayecto')
										labels[i].innerHTML = "Accidente de trayecto<font color='red'>*</font>";
								}
								document.getElementById("tipoAccidenteTrayecto").disabled = false;
							}
						};
						Y.on("change", requiredTipoAccidenteTrayecto, "#esAccidenteTrayecto");
						Y.on("available", requiredTipoAccidenteTrayecto, "#esAccidenteTrayecto");
					});
				</script>				
				<isl:combo     btnEdicion="true" cols="3-10" requerido="true" nombre="tipoAccidenteTrayecto" rotulo="Accidente de trayecto" from="${tiposAccidenteTrayecto}" valor="${diatOAPropuesta?.tipoAccidenteTrayecto?.codigo}" />
				<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="esTrabajoHabitual" rotulo="¿Lo desarrollaba?" from="${esTrabajoHabitualList}" valor="${Boolean.valueOf(diatOAPropuesta?.esTrabajoHabitual)}" noSelection="${['':'Seleccione ...']}"/>
				<isl:combo     btnEdicion="true" cols="3-10"                  nombre="medioPrueba" rotulo="Medio de prueba" from="${tipoMedioPruebaAccidentes}" valor="${diatOAPropuesta?.medioPrueba?.codigo}" noSelection="${['':'Seleccione ...']}" />
				<isl:textInput btnEdicion="true" cols="7-10"                  nombre="detallePrueba" rotulo="Detalle del medio de prueba" valor="${diatOAPropuesta?.detallePrueba}"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<div class="pure-u-1">
			<g:actionSubmit id="Salir" value="Salir"  action="r01" class="pure-button pure-button-error" formnovalidate="formnovalidate" />
			<isl:button action="r03Temp" tipo="pdf" 	             value="Guardar cambios"/>
			<isl:button action="r03"                                 value="Generar denuncia OA"/>
		</div>
	</div>
</g:form>

<!-- modal -->  
<div id="modalsrc" class="yui-pe-content">
	<div id="modal-marco" >
		<g:form class="pure-form pure-form-stacked modalForm"  name="modalForm" >
		    <fieldset>
		 		<legend id="tTitulo">_titulo_</legend>
				<div class="pure-g-r">
					<div class="pure-u-1">
						<div id="modal-content">_modal-content_</div>
					</div>
				</div>
			</fieldset>
		</g:form>
	</div>
</div>  

<g:javascript>
	var jsSinisetro = ${diatOAPropuesta?.siniestroId};
</g:javascript>
<g:javascript src="DIATEPOA/diatepoa.js" />
