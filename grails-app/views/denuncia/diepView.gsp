<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.helpers.ColeccionesHelper" %>

<g:form name="dp02" class="pure-form pure-form-stacked" >
<g:hiddenField name="siniestroId" value="${diep?.siniestroId}"/>
	<fieldset>
 		<legend>A. Identificación del empleador - DIEP</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:textInput cols="4-10" deshabilitado="true" nombre="empleador_razonSocial"				rotulo="Razon Social"				valor="${diep?.empleador?.razonSocial}"/>
					<isl:textInput cols="2-10" deshabilitado="true" nombre="empleador_rut"						rotulo="RUT"						valor="${FormatosHelper.runFormatStatic(diep?.empleador?.rut)}" />
					<isl:combo     cols="4-10" deshabilitado="true" nombre="ciiuEmpleador"						rotulo="Actividad económica empresa principal" from="${tipoActividadEconomica}" valor="${diat?.ciiuEmpleador?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
					
					<isl:combo     cols="1-10" deshabilitado="true" nombre="propiedadEmpresa"					rotulo="Propiedad"					valor="${diep?.propiedadEmpresa?.codigo}" from="${tipoPropiedades}"/>
					<isl:combo 	   cols="1-10" deshabilitado="true" nombre="direccionEmpleadorTipoCalle"		rotulo="Tipo calle"					valor="${diep?.direccionEmpleadorTipoCalle?.codigo}" from="${tipoCalles}"/>
					<isl:textInput cols="2-10" deshabilitado="true" nombre="direccionEmpleadorNombreCalle"		rotulo="Dirección"			valor="${diep?.direccionEmpleadorNombreCalle}"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre="direccionEmpleadorNumero"			rotulo="Número"				valor="${diep?.direccionEmpleadorNumero}"/>
					<isl:textInput cols="3-10" deshabilitado="true" nombre="direccionEmpleadorRestoDireccion"	rotulo="Referencias"		valor="${diep?.direccionEmpleadorRestoDireccion}"/>								
					
					<isl:combo     cols="2-10" deshabilitado="true" nombre="direccionEmpleadorComuna" 			rotulo="Comuna"				valor="${diep?.direccionEmpleadorComuna?.codigo}" from="${comunas}" noSelection="${['':'Seleccione ...']}"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre="telefonoEmpleador"					rotulo="Teléfono"			valor="${diep?.telefonoEmpleador}" tipo="numero"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre="nTrabajadoresHombre"				rotulo="N° trab. hombres"	valor="${diep?.nTrabajadoresHombre}" tipo="numero"/>
					<isl:textInput cols="1-10" deshabilitado="true" nombre="nTrabajadoresMujer"					rotulo="N° trab. mujeres"	valor="${diep?.nTrabajadoresMujer}" tipo="numero"/>			
					<div id="div_tipoEmpresa">
						<isl:combo     cols="2-10" deshabilitado="true" nombre="tipoEmpresa"					rotulo="Tipo empresa"		valor="${diep?.tipoEmpresa?.codigo}" from="${tipoEmpresas}"/>
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
						<isl:combo      cols="2-8" noSelection="${['':'Seleccione ...']}" nombre="ciiuPrincipal" rotulo="" from="${tipoActividadEconomica}" valor="${diep?.ciiuPrincipal?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
					</div>
				</div>
			</div>
		<legend>B. Identificación del trabajador - DIEP</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:textInput cols="2-10" deshabilitado="true" nombre="trabajador_nombre" rotulo="Nombres" valor="${diep?.trabajador?.nombre}"/>
					<isl:textInput cols="2-10" deshabilitado="true" nombre="trabajador_apellidoPaterno" rotulo="Apellido paterno" valor="${diep?.trabajador?.apellidoPaterno}"/>
					<isl:textInput cols="2-10" deshabilitado="true" nombre="trabajador_apellidoMaterno" rotulo="Apellido materno" valor="${diep?.trabajador?.apellidoMaterno}"/>
					<isl:textInput cols="1-10" deshabilitado="true" rotulo="RUN"  nombre ="trabajador_run" valor="${FormatosHelper.runFormatStatic(diep?.trabajador?.run)}" />
					<isl:combo     cols="1-10" deshabilitado="true" nombre="trabajador_sexo" from="${sexos}" rotulo="Sexo" valor="${diep?.trabajador?.sexo}" />
					<isl:textInput cols="1-10" deshabilitado="true" nombre="trabajador_fechaNacimiento" rotulo="Fecha nacimiento" valor="${FormatosHelper.fechaCortaStatic(diep?.trabajador?.fechaNacimiento)}" tipo="fecha"/>			
					<isl:combo     cols="1-10" deshabilitado="true" nombre="nacionalidadTrabajador"  rotulo="Nacionalidad" from="${naciones}" valor="${diep?.nacionalidadTrabajador?.codigo}" />	
	    			<div class="salto-de-linea"></div>
	    			
	    			<isl:combo     cols="1-10" deshabilitado="true" nombre="direccionTrabajadorTipoCalle"  rotulo="Tipo calle" from="${tipoCalles}" valor="${diep?.direccionTrabajadorTipoCalle?.codigo}" />
					<isl:textInput cols="2-10" deshabilitado="true" nombre="direccionTrabajadorNombreCalle" rotulo="Dirección" valor="${diep?.direccionTrabajadorNombreCalle}" />
					<isl:textInput cols="1-10" deshabilitado="true" nombre="direccionTrabajadorNumero" tipo="text" rotulo="Número" valor="${diep?.direccionTrabajadorNumero}" />
					<isl:textInput cols="2-10" deshabilitado="true" nombre="direccionTrabajadorRestoDireccion" rotulo="Referencias" valor="${diep?.direccionTrabajadorRestoDireccion}" />
					<isl:combo     cols="1-10" deshabilitado="true" nombre="direccionTrabajadorComuna" rotulo="Comuna" from="${comunas}" valor="${diep?.direccionTrabajadorComuna?.codigo}" noSelection="${['':'Seleccione ...']}" />
					<isl:textInput cols="1-10" deshabilitado="true" tipo="numero"    nombre="telefonoTrabajador" rotulo="Teléfono" valor="${diep?.telefonoTrabajador}" />
					<isl:textInput cols="2-10" deshabilitado="true" nombre="profesionTrabajador" rotulo="Profesión" valor="${diep?.profesionTrabajador}" />
	    			<div class="salto-de-linea"></div>
	    			<isl:combo     cols="1-10" deshabilitado="true" nombre="etnia"  rotulo="Pueblo originario" from="${etnias}" valor="${diep?.etnia?.codigo}" />
					<isl:textInput cols="1-10" deshabilitado="true" rotulo="Ingreso empresa" nombre="fechaIngresoEmpresa" valor="${FormatosHelper.fechaCortaStatic(diep?.fechaIngresoEmpresa)}" tipo="fecha"/>
					<isl:combo     cols="2-10" deshabilitado="true" nombre="duracionContrato" rotulo="Tipo contrato" from="${tipoDuracionContratos}" valor="${diep?.duracionContrato?.codigo}" />
					<isl:combo     cols="2-10" deshabilitado="true" nombre="tipoRemuneracion" rotulo="Tipo ingreso" from="${tipoRemuneraciones}" valor="${diep?.tipoRemuneracion?.codigo}" />
					<isl:combo     cols="2-10" deshabilitado="true" nombre="categoriaOcupacion" rotulo="Categoria ocupacional" from="${categoriaOcupaciones}" valor="${diep?.categoriaOcupacion?.codigo}" />
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
		<legend>C. Datos de la enfermedad - DIEP</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">		    	
				<isl:textInput cols="4-10" deshabilitado="true" nombre="sintoma" rotulo="Describa las molestias o sintomas que actualmente tiene el trabajador/a" valor="${diep?.sintoma}" />				
				<isl:textInput cols="3-10" deshabilitado="true" nombre="parteCuerpo" rotulo="Parte del cuerpo afectada" valor="${diep?.parteCuerpo}" />
				<isl:textInput cols="3-10" deshabilitado="true" nombre="descripcionTrabajo" rotulo="Describa la actividad que realizaba cuando comenzaron las molestias" valor="${diep?.descripcionTrabajo}" />
				
				<isl:textInput cols="3-10" deshabilitado="true" nombre="puestoTrabajo" rotulo="Nombre del puesto de trabajo" valor="${diep?.puestoTrabajo}" />
				<isl:textInput cols="3-10" deshabilitado="true" nombre="agenteSospechoso" rotulo="¿Que agentes del trabajo cree usted que le causaron estas molestias?" valor="${diep?.agenteSospechoso}" />
				<isl:combo     cols="2-10" deshabilitado="true" nombre="esAntecedenteCompanero" rotulo="¿Existen compañeros de trabajo con las mismas molestias?" from="${esAntecedenteCompaneroList}" valor="${diep?.esAntecedenteCompanero}"/>
				<isl:combo     cols="2-10" deshabilitado="true" nombre="esAntecedentePrevio" rotulo="¿Habia tenido estas molestias en el puesto de trabajo actual anteriormente?" from="${esAntecedentePrevioList}" valor="${diep?.esAntecedentePrevio}"/>
				
				<isl:textInput cols="5-10" deshabilitado="true" nombre="fechaSintoma" ayuda="dd-mm-aaaa" rotulo="¿Aproximadamente en que fecha comenzaron los sintomas?" tipo="fecha" valor="${FormatosHelper.fechaCortaStatic(diep?.fechaSintoma)}" />
				<isl:textInput cols="5-10" deshabilitado="true" nombre="fechaAgente" rotulo="¿Aproximadamente desde que fecha ha estado expuesto a los agentes que causan las molestias?" tipo="fecha" valor="${FormatosHelper.fechaCortaStatic(diep?.fechaAgente)}" />
			</div>
		</div>
		
		<legend>D. Identificación del denunciante - Denuncia Individual de Enfermedad Profesional</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:hiddenField name="denunciante_run" value="${diep?.denunciante?.run}" />
				<isl:textOutput cols="2-10" deshabilitado="true" rotulo="Calificación Denunciante" valor="${diep?.calificacionDenunciante.descripcion}" />
				<isl:textInput  cols="2-10" deshabilitado="true" nombre="denunciante_nombre" rotulo="Nombres" valor="${diep?.denunciante?.nombre}"/>
				<isl:textInput  cols="2-10" deshabilitado="true" nombre="denunciante_apellidoPaterno" rotulo="Apellido paterno" valor="${diep?.denunciante?.apellidoPaterno}"/>
				<isl:textInput  cols="2-10" deshabilitado="true" nombre="denunciante_apellidoMaterno" rotulo="Apellido materno" valor="${diep?.denunciante?.apellidoMaterno}"/>
				<isl:textInput  cols="1-10" deshabilitado="true" nombre="denunciante_run" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(diep?.denunciante?.run)}"/>				
				<isl:textInput  cols="1-10" deshabilitado="true" nombre="telefonoDenunciante" tipo="numero" rotulo="Telefono" valor="${diep?.telefonoDenunciante}"/>				
			</div>
		</div>
		
	</fieldset>	
	<div class="pure-g-r">
		<isl:button tipo="volver"  action="volverSiniestro"/>
	</div>
</g:form>
