<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<%@ page import="cl.adexus.helpers.ColeccionesHelper" %>



<g:form name="dp02" class="pure-form pure-form-stacked" >
<g:hiddenField name="siniestroId" value="${diepOAPropuesta?.siniestroId}"/>
	<fieldset>
 		<legend>A. Identificación del empleador - DIEP-OA</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:textInput btnEdicion="true" cols="4-10" requerido="true" nombre="empleador_razonSocial"		rotulo="Razon Social"				valor="${diepOAPropuesta?.empleador?.razonSocial}" />
					<isl:textOutput cols="2-10" rotulo="RUT" valor="${FormatosHelper.runFormatStatic(diepOAPropuesta?.empleador?.rut)}"/>
					<g:hiddenField 	name="empleador_rut" value="${FormatosHelper.runFormatStatic(diepOAPropuesta?.empleador?.rut)}" />
					<isl:combo     btnEdicion="true" cols="4-10" requerido="true" noSelection="${['':'Seleccione ...']}" nombre="ciiuEmpleador" rotulo="Actividad económica empresa principal" from="${tipoActividadEconomica}" valor="${diepOAPropuesta?.ciiuEmpleador?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
					
					<isl:combo     btnEdicion="true" cols="1-10" requerido="true" nombre="propiedadEmpresa"				rotulo="Propiedad"					valor="${diepOAPropuesta?.propiedadEmpresa?.codigo}" from="${tipoPropiedades}"/>
					<isl:combo 	   btnEdicion="true" cols="1-10" requerido="true" nombre="direccionEmpleadorTipoCalle"	rotulo="Tipo calle"					valor="${diepOAPropuesta?.direccionEmpleadorTipoCalle?.codigo}" from="${tipoCalles}"/>
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="direccionEmpleadorNombreCalle"	rotulo="Dirección"			valor="${diepOAPropuesta?.direccionEmpleadorNombreCalle}"/>
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="direccionEmpleadorNumero"			rotulo="Número"				valor="${diepOAPropuesta?.direccionEmpleadorNumero}" tipo="numero"/>
					<isl:textInput btnEdicion="true" cols="3-10" 				  nombre="direccionEmpleadorRestoDireccion"	rotulo="Referencias"		valor="${diepOAPropuesta?.direccionEmpleadorRestoDireccion}"/>								
					
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" noSelection="${['':'Seleccione ...']}" nombre="direccionEmpleadorComuna" 		rotulo="Comuna"				valor="${diepOAPropuesta?.direccionEmpleadorComuna?.codigo}" from="${comunas}"/>
					<isl:textInput btnEdicion="true" cols="1-10"                  nombre="telefonoEmpleador"				rotulo="Teléfono"			valor="${diepOAPropuesta?.telefonoEmpleador}" tipo="numero"/>
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="nTrabajadoresHombre"				rotulo="N° trab. hombres"	valor="${diepOAPropuesta?.nTrabajadoresHombre}" tipo="numero"/>
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="nTrabajadoresMujer"				rotulo="N° trab. mujeres"	valor="${diepOAPropuesta?.nTrabajadoresMujer}" tipo="numero"/>			
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="tipoEmpresa"						rotulo="Tipo empresa"		valor="${diepOAPropuesta?.tipoEmpresa?.codigo}" from="${tipoEmpresas}"/>
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
						<isl:combo btnEdicion="true" cols="4-10" noSelection="${['':'Seleccione ...']}" nombre="ciiuPrincipal" rotulo="" from="${tipoActividadEconomica}" valor="${diepOAPropuesta?.ciiuPrincipal?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
					</div>
				</div>
			</div>
		<legend>B. Identificación del trabajador - DIEP-OA</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="trabajador_nombre" rotulo="Nombres" valor="${diepOAPropuesta?.trabajador?.nombre}"/>
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="trabajador_apellidoPaterno" rotulo="Apellido paterno" valor="${diepOAPropuesta?.trabajador?.apellidoPaterno}"/>
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="trabajador_apellidoMaterno" rotulo="Apellido materno" valor="${diepOAPropuesta?.trabajador?.apellidoMaterno}"/>
					<isl:textOutput cols="1-10" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(diepOAPropuesta?.trabajador?.run)}"/>
					<g:hiddenField 	name="trabajador_run" value="${FormatosHelper.runFormatStatic(diepOAPropuesta?.trabajador?.run)}" />
					<isl:combo     btnEdicion="true" cols="1-10" requerido="true" nombre="trabajador_sexo" from="${sexos}" rotulo="Sexo" valor="${diepOAPropuesta?.trabajador?.sexo}" />
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="trabajador_fechaNacimiento" rotulo="Fecha nacimiento" requerido="true" valor="${FormatosHelper.fechaCortaStatic(diepOAPropuesta?.trabajador?.fechaNacimiento)}" tipo="fecha"/>			
					<isl:combo     btnEdicion="true" cols="1-10" requerido="true" nombre="nacionalidadTrabajador"  rotulo="Nacionalidad" from="${naciones}" valor="${diepOAPropuesta?.nacionalidadTrabajador?.codigo}" />	
	    			<div class="salto-de-linea"></div>
	    			
	    			<isl:combo     btnEdicion="true" cols="1-10" requerido="true" nombre="direccionTrabajadorTipoCalle"  rotulo="Tipo calle" from="${tipoCalles}" valor="${diepOAPropuesta?.direccionTrabajadorTipoCalle?.codigo}" />
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="direccionTrabajadorNombreCalle" rotulo="Dirección" valor="${diepOAPropuesta?.direccionTrabajadorNombreCalle}" />
					<isl:textInput btnEdicion="true" cols="1-10" requerido="true" nombre="direccionTrabajadorNumero" tipo="text" rotulo="Número" valor="${diepOAPropuesta?.direccionTrabajadorNumero}" tipo="numero" />
					<isl:textInput btnEdicion="true" cols="2-10" 				  nombre="direccionTrabajadorRestoDireccion" rotulo="Referencias" valor="${diepOAPropuesta?.direccionTrabajadorRestoDireccion}" />
					<isl:combo     btnEdicion="true" cols="1-10" requerido="true" noSelection="${['':'Seleccione ...']}" nombre="direccionTrabajadorComuna" rotulo="Comuna" from="${comunas}" valor="${diepOAPropuesta?.direccionTrabajadorComuna?.codigo}" />
					<isl:textInput btnEdicion="true" cols="1-10" tipo="numero"    nombre="telefonoTrabajador" rotulo="Teléfono" valor="${diepOAPropuesta?.telefonoTrabajador}" />
					<isl:textInput btnEdicion="true" cols="2-10" requerido="true" nombre="profesionTrabajador" rotulo="Profesión" valor="${diepOAPropuesta?.profesionTrabajador}" />
	    			<div class="salto-de-linea"></div>
	    			<isl:combo     btnEdicion="true" cols="1-10" requerido="false" nombre="etnia"  rotulo="Pueblo originario" from="${etnias}" valor="${diepOAPropuesta?.etnia?.codigo}" />
					<isl:textInput btnEdicion="true" cols="1-10" requerido="false" rotulo="Ingreso empresa" nombre="fechaIngresoEmpresa" valor="${FormatosHelper.fechaCortaStatic(diepOAPropuesta?.fechaIngresoEmpresa)}" tipo="fecha"/>
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="duracionContrato" rotulo="Tipo contrato" from="${tipoDuracionContratos}" valor="${diepOAPropuesta?.duracionContrato?.codigo}" />
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="tipoRemuneracion" rotulo="Tipo ingreso" from="${tipoRemuneraciones}" valor="${diepOAPropuesta?.tipoRemuneracion?.codigo}" />
					<isl:combo     btnEdicion="true" cols="2-10" requerido="true" nombre="categoriaOcupacion" rotulo="Categoria ocupacional" from="${categoriaOcupaciones}" valor="${diepOAPropuesta?.categoriaOcupacion?.codigo}" />
					<isl:combo     btnEdicion="true" cols="2-10" noSelection="${['':'Seleccione ...']}" nombre="sistemaSalud" rotulo="Sistema salud" from="${sistemaSalud}" valor="${diepOAPropuesta?.sistemaSalud?.codigo}" />
					
					<div id='dv_otro_pueblo'>
						<isl:textInput cols="3-10" nombre="otroPueblo" rotulo="Otro Pueblo" valor="${diat?.otroPueblo}" />
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
		<legend>C. Datos de la enfermedad - DIEP-OA</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">		    	
				<isl:textInput btnEdicion="true" cols="4-10" requerido="true"  nombre="sintoma" rotulo="Describa las molestias o sintomas que actualmente tiene el trabajador/a" valor="${diepOAPropuesta?.sintoma}" />				
				<isl:textInput btnEdicion="true" cols="3-10" requerido="true"  nombre="parteCuerpo" rotulo="Parte del cuerpo afectada" valor="${diepOAPropuesta?.parteCuerpo}" />
				<isl:textInput btnEdicion="true" cols="3-10" requerido="true"  nombre="descripcionTrabajo" rotulo="Describa la actividad que realizaba cuando comenzaron las molestias" valor="${diepOAPropuesta?.descripcionTrabajo}" />
				
				<isl:textInput btnEdicion="true" cols="3-10" requerido="false" nombre="puestoTrabajo" rotulo="Nombre del puesto de trabajo" valor="${diepOAPropuesta?.puestoTrabajo}" />
				<isl:textInput btnEdicion="true" cols="3-10" requerido="true"  nombre="agenteSospechoso" rotulo="¿Que agentes del trabajo cree usted que le causaron estas molestias?" valor="${diepOAPropuesta?.agenteSospechoso}" />
				<isl:combo     btnEdicion="true" cols="2-10"                   nombre="esAntecedenteCompanero" rotulo="¿Existen compañeros de trabajo con las mismas molestias?" from="${esAntecedenteCompaneroList}" valor="${diepOAPropuesta?.esAntecedenteCompanero}"/>
				<isl:combo     btnEdicion="true" cols="2-10"                   nombre="esAntecedentePrevio" rotulo="¿Habia tenido estas molestias en el puesto de trabajo actual anteriormente?" from="${esAntecedentePrevioList}" valor="${diepOAPropuesta?.esAntecedentePrevio}"/>
				
				<isl:textInput btnEdicion="true" cols="5-10" requerido="true"  nombre="fechaSintoma" ayuda="dd-mm-aaaa" rotulo="¿Aproximadamente en que fecha comenzaron los sintomas?" tipo="fecha" valor="${FormatosHelper.fechaCortaStatic(diepOAPropuesta?.fechaSintoma)}" />
				<isl:textInput btnEdicion="true" cols="5-10" requerido="true"  nombre="fechaAgente" rotulo="¿Aproximadamente desde que fecha ha estado expuesto a los agentes que causan las molestias?" tipo="fecha" valor="${FormatosHelper.fechaCortaStatic(diepOAPropuesta?.fechaAgente)}" />
			</div>
		</div>
		<!-- 
		<legend>D. Identificación del denunciante - Denuncia Individual de Enfermedad Profesional</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:hiddenField name="denunciante_run" value="${diepOAPropuesta?.denunciante?.run}" />
				<isl:textOutput                   cols="2-10" rotulo="Calificación Denunciante" valor="${diepOAPropuesta?.calificacionDenunciante?.descripcion}" />
				<isl:textInput  btnEdicion="true" cols="2-10" requerido="true"  nombre="denunciante_nombre" rotulo="Nombres" valor="${diepOAPropuesta?.denunciante?.nombre}"/>
				<isl:textInput  btnEdicion="true" cols="2-10" requerido="true"  nombre="denunciante_apellidoPaterno" rotulo="Apellido paterno" valor="${diepOAPropuesta?.denunciante?.apellidoPaterno}"/>
				<isl:textInput  btnEdicion="true" cols="2-10" requerido="true"  nombre="denunciante_apellidoMaterno" rotulo="Apellido materno" valor="${diepOAPropuesta?.denunciante?.apellidoMaterno}"/>
				<isl:textInput  btnEdicion="true" cols="1-10" deshabilitado="true" requerido="true"  nombre="denunciante_run" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(diepOAPropuesta?.denunciante?.run)}"/>				
				<isl:textInput  btnEdicion="true" cols="1-10" nombre="telefonoDenunciante" tipo="numero" rotulo="Telefono" valor="${diepOAPropuesta?.telefonoDenunciante}"/>				
			</div>
		</div>
		 -->
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
	var jsSinisetro = ${diepOAPropuesta?.siniestroId};
</g:javascript>
<g:javascript src="DIATEPOA/diatepoa.js" />
