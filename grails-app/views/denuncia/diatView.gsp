<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.helpers.ColeccionesHelper" %>

<g:form name="dp02" class="pure-form pure-form-stacked" >
<g:hiddenField name="siniestroId" value="${diat?.siniestroId}"/>
	<fieldset>
 		<legend>A. Identificación del empleador - DIAT</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:textInput cols="4-10" deshabilitado="true" nombre="empleador_razonSocial" valor="${diat?.empleador?.razonSocial}" rotulo="Razon Social" />
					<isl:textInput cols="2-10" deshabilitado="true" nombre="empleador_rut" rotulo="RUT"  valor="${FormatosHelper.runFormatStatic(diat?.empleador?.rut)}" />
					<isl:combo     cols="4-10" deshabilitado="true" nombre="ciiuEmpleador" rotulo="Actividad económica empresa principal" from="${tipoActividadEconomica}" valor="${diat?.ciiuEmpleador?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
					
					<isl:combo     cols="1-10" deshabilitado="true" nombre="propiedadEmpresa"            rotulo="Propiedad"    from="${tipoPropiedades}" valor="${diat?.propiedadEmpresa?.codigo}" />
					<isl:combo 	   cols="1-10" deshabilitado="true" nombre="direccionEmpleadorTipoCalle" rotulo="Tipo calle"   from="${tipoCalles}"      valor="${diat?.direccionEmpleadorTipoCalle?.codigo}"/> 
					<isl:textInput cols="2-10" deshabilitado="true" nombre="direccionEmpleadorNombreCalle"  rotulo="Dirección"   valor="${diat?.direccionEmpleadorNombreCalle}"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre="direccionEmpleadorNumero"      tipo="text" rotulo="Número"      valor="${diat?.direccionEmpleadorNumero}"/>
					<isl:textInput cols="3-10" deshabilitado="true" nombre="direccionEmpleadorRestoDireccion" rotulo="Referencias" valor="${diat?.direccionEmpleadorRestoDireccion}"/>
													
					<isl:combo     cols="2-10" deshabilitado="true" nombre="direccionEmpleadorComuna" rotulo="Comuna"  from="${comunas}" valor="${diat?.direccionEmpleadorComuna?.codigo}" noSelection="${['':'Seleccione ...']}"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre="telefonoEmpleador" tipo="numero" rotulo="Teléfono" valor="${diat?.telefonoEmpleador}"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre="nTrabajadoresHombre" tipo="numero" rotulo="N° trab. hombres" valor="${diat?.nTrabajadoresHombre}"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre="nTrabajadoresMujer" tipo="numero" rotulo="N° trab. mujeres" valor="${diat?.nTrabajadoresMujer}"/>
					<div id="div_tipoEmpresa">
						<isl:combo     cols="2-10" deshabilitado="true" nombre="tipoEmpresa" rotulo="Tipo empresa" from="${tipoEmpresas}" valor="${diat?.tipoEmpresa?.codigo}" />
					</div>
					<script type="text/javascript">
						YUI().use('node', function(Y) {
							function tipoEmpresaOnClick(e) {
								var contratista = '2';
								var subcontratista = '3';
								if (e.target.get('value') == contratista || e.target.get('value') == subcontratista) {
									Y.one("#dv_ciiuPrincipal").setStyle('display', 'block');
								} else {
									Y.one("#ciiuPrincipal").set('value', '');
									Y.one("#dv_ciiuPrincipal").setStyle('display', 'none');
								}
							}
	
							var onLoad = function(e) {
								var contratista = '2';
								var subcontratista = '3';
								var tipoEmpresaSel = Y.one('#div_tipoEmpresa input[type=radio]:checked');
								if (tipoEmpresaSel) tipoEmpresaSel = tipoEmpresaSel.get('value');
								if (tipoEmpresaSel == contratista || tipoEmpresaSel == subcontratista) {
									Y.one("#dv_ciiuPrincipal").setStyle('display', 'block');
								} else {
									Y.one("#ciiuPrincipal").value = '';
									Y.one("#dv_ciiuPrincipal").setStyle('display', 'none');
								}
							}
							
							Y.one('#div_tipoEmpresa').delegate('click', tipoEmpresaOnClick, 'input[type=radio]');
							Y.on("available", onLoad, "#trabajador_nombre");
						});
					</script>
					<div id='dv_ciiuPrincipal'>
						<isl:combo      cols="2-8" noSelection="${['':'Seleccione ...']}" nombre="ciiuPrincipal" rotulo="" from="${tipoActividadEconomica}" valor="${diat?.ciiuPrincipal?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
					</div>
				</div>
			</div>
		<legend>B. Identificación del trabajador - DIAT</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:textInput cols="2-10" deshabilitado="true" nombre="trabajador_nombre" rotulo="Nombres" valor="${diat?.trabajador?.nombre}"/>
					<isl:textInput cols="2-10" deshabilitado="true" nombre="trabajador_apellidoPaterno" rotulo="Apellido paterno" valor="${diat?.trabajador?.apellidoPaterno}"/>
					<isl:textInput cols="2-10" deshabilitado="true" nombre="trabajador_apellidoMaterno" rotulo="Apellido materno" valor="${diat?.trabajador?.apellidoMaterno}"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre ="trabajador_run" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(diat?.trabajador?.run)}" />
					<isl:combo     cols="1-10" deshabilitado="true" nombre="trabajador_sexo" from="${sexos}" rotulo="Sexo" valor="${diat?.trabajador?.sexo}" />
					<isl:textInput cols="1-10" deshabilitado="true" nombre="trabajador_fechaNacimiento" rotulo="Fecha nacimiento" valor="${FormatosHelper.fechaCortaStatic(diat?.trabajador?.fechaNacimiento)}" tipo="fecha"/>			
					<isl:combo     cols="1-10" deshabilitado="true" nombre="nacionalidadTrabajador"  rotulo="Nacionalidad" from="${naciones}" valor="${diat?.nacionalidadTrabajador?.codigo}" />	
	    			<div class="salto-de-linea"></div>
	    			
	    			<isl:combo     cols="1-10" deshabilitado="true" nombre="direccionTrabajadorTipoCalle"  rotulo="Tipo calle" from="${tipoCalles}" valor="${diat?.direccionTrabajadorTipoCalle?.codigo}" />
					<isl:textInput cols="2-10" deshabilitado="true" nombre="direccionTrabajadorNombreCalle" rotulo="Dirección" valor="${diat?.direccionTrabajadorNombreCalle}" />
					<isl:textInput cols="1-10" deshabilitado="true" nombre="direccionTrabajadorNumero" tipo="text" rotulo="Número" valor="${diat?.direccionTrabajadorNumero}" />
					<isl:textInput cols="2-10" deshabilitado="true" nombre="direccionTrabajadorRestoDireccion" rotulo="Referencias" valor="${diat?.direccionTrabajadorRestoDireccion}" />
					<isl:combo     cols="1-10" deshabilitado="true" rotulo="Comuna" nombre="direccionTrabajadorComuna" from="${comunas}" valor="${diat?.direccionTrabajadorComuna?.codigo}" noSelection="${['':'Seleccione ...']}" />
					<isl:textInput cols="1-10" deshabilitado="true" tipo="numero" nombre="telefonoTrabajador" rotulo="Teléfono" valor="${diat?.telefonoTrabajador}" />
					<isl:textInput cols="2-10" deshabilitado="true" nombre="profesionTrabajador" rotulo="Profesión" valor="${diat?.profesionTrabajador}" />
	    			<div class="salto-de-linea"></div>
	    			<isl:combo     cols="1-10" deshabilitado="true" requerido="false" nombre="etnia"  rotulo="Pueblo originario" from="${etnias}" valor="${diat?.etnia?.codigo}" />
					<isl:textInput cols="1-10" deshabilitado="true" requerido="false" rotulo="Ingreso empresa" nombre="fechaIngresoEmpresa" valor="${FormatosHelper.fechaCortaStatic(diat?.fechaIngresoEmpresa)}" tipo="fecha"/>
					<isl:combo     cols="2-10" deshabilitado="true" nombre="duracionContrato" rotulo="Tipo contrato" from="${tipoDuracionContratos}" valor="${diat?.duracionContrato?.codigo}" />
					<isl:combo     cols="2-10" deshabilitado="true" nombre="tipoRemuneracion" rotulo="Tipo ingreso" from="${tipoRemuneraciones}" valor="${diat?.tipoRemuneracion?.codigo}" />
					<isl:combo     cols="2-10" deshabilitado="true" nombre="categoriaOcupacion" rotulo="Categoria ocupacional" from="${categoriaOcupaciones}" valor="${diat?.categoriaOcupacion?.codigo}" />
					<div id='dv_otro_pueblo'>
						<isl:textInput cols="3-10" deshabilitado="true" nombre="otroPueblo" rotulo="Otro Pueblo" valor="${diat?.otroPueblo}" />
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
		<legend>C. Datos del accidente - DIAT</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-10" deshabilitado="true" nombre="fechaAccidente" rotulo="Fecha Accidente" valor="${FormatosHelper.fechaCortaStatic(diat?.fechaAccidente)}" />
				<isl:textInput cols="1-10" deshabilitado="true" tipo="hora" nombre="fechaAccidente_hora" rotulo="Hora accidente" valor="${FormatosHelper.horaCortaStatic(diat?.fechaAccidente)}"/>
				<isl:textInput cols="1-10" deshabilitado="true" tipo="hora" nombre="horaIngreso" rotulo="Ingreso trabajo" valor="${FormatosHelper.horaCortaStatic(diat?.horaIngreso)}"/>
				<isl:textInput cols="1-10" deshabilitado="true" tipo="hora" nombre="horaSalida" rotulo="Salida del trabajo" valor="${FormatosHelper.horaCortaStatic(diat?.horaSalida)}"/>
				<isl:textInput cols="4-10" deshabilitado="true" nombre="direccionAccidenteNombreCalle" rotulo="Dirección" valor="${diat?.direccionAccidenteNombreCalle}"/>
				<isl:combo     cols="2-10" deshabilitado="true" rotulo="Comuna" nombre="direccionAccidenteComuna"  from="${comunas}" valor="${diat?.direccionAccidenteComuna?.codigo}" noSelection="${['':'Seleccione ...']}"/>
				
				<div class="salto-de-linea"></div>
				<isl:textInput cols="7-10" deshabilitado="true" nombre="que" rotulo="Qué estaba haciendo el trabajador al momento o justo antes del accidente" valor="${diat?.que}"/>
				<isl:textInput cols="3-10" deshabilitado="true" nombre="lugarAccidente" rotulo="Dónde ocurrió el accidente (sección, edificio, área, etc.)" valor="${diat?.lugarAccidente}"/>
				<isl:textInput cols="7-10" deshabilitado="true" nombre="como" rotulo="Descripción de cómo ocurrió el accidente" valor="${diat?.relato}" valor="${diat?.como}"/>
				<isl:textInput cols="3-10" deshabilitado="true" nombre="trabajoHabitualCual" rotulo="Trabajo habitual del accidentado" valor="${diat?.trabajoHabitualCual}"/>
				<isl:combo     cols="2-10" deshabilitado="true" nombre="gravedad" rotulo="Clasificación del accidente" from="${criterioGravedades}" valor="${diat?.gravedad?.codigo}" />			
				<isl:combo     cols="3-10" deshabilitado="true" nombre="esAccidenteTrayecto" rotulo="Tipo de accidente" from="${tipoAccidentes}" valor="${diat?.esAccidenteTrayecto ? '2' : '1'}" /> 
				<g:if test="${diat?.esAccidenteTrayecto}">
     				<isl:combo     cols="3-10" deshabilitado="true" nombre="tipoAccidenteTrayecto" rotulo="Accidente de trayecto" from="${tiposAccidenteTrayecto}" valor="${diat?.tipoAccidenteTrayecto?.codigo}" />
				</g:if>
				<isl:combo     cols="2-10" deshabilitado="true" nombre="esTrabajoHabitual" rotulo="¿Lo desarrollaba?" from="${esTrabajoHabitualList}" valor="${Boolean.valueOf(diat?.esTrabajoHabitual)}" noSelection="${['':'Seleccione ...']}"/>
				<isl:combo     cols="3-10" deshabilitado="true" nombre="medioPrueba" rotulo="Medio de prueba" from="${tipoMedioPruebaAccidentes}" valor="${diat?.medioPrueba?.codigo}" noSelection="${['':'Seleccione ...']}" />
				<isl:textInput cols="7-10" deshabilitado="true" nombre="detallePrueba" rotulo="Detalle del medio de prueba" valor="${diat?.detallePrueba}"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<g:if test="${volver == null}">
			<% /* Volver por default, a menos que se indique otra cosa */ %>
			<isl:button tipo="volver" action="volverSiniestro"/>
		</g:if>
		<g:else>
			<% volverArray = volver.split(":") %>
			<input value="Volver" class="pure-button pure-button-secondary" onclick="window.location.href='volver2URL?volver=${volver}';return false;">
		</g:else>
	</div>
</g:form>
