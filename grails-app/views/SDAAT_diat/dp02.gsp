<%@page import="cl.adexus.isl.spm.helpers.FormatosISLHelper"%>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:form name="dp02" class="pure-form pure-form-stacked" >
	<g:hiddenField name="tipoDenunciante" value="${sdaat?.tipoDenunciante?.codigo}" />
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>A. Identificación del empleador - Denuncia Individual de Accidente de Trabajo</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput  cols="3-8" requerido="true"  nombre="empleador_razonSocial" rotulo="Razón Social" valor="${diat?.empleador?.razonSocial}"/>
				<isl:textOutput cols="1-8" requerido="true"  rotulo="RUT" valor="${FormatosHelper.runFormatStatic(diat?.empleador?.rut)}" />
				<g:hiddenField 	name="empleador_rut" value="${diat?.empleador?.rut}" />
				<isl:combo      cols="4-8" requerido="${attrsReq.ciiuEmpleador}" noSelection="${['':'Seleccione ...']}" nombre="ciiuEmpleador" rotulo="Actividad económica empresa principal" from="${tipoActividadEconomica}" valor="${diat?.ciiuEmpleador?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
				<isl:combo      cols="1-8" requerido="${attrsReq.direccionEmpleadorTipoCalle}" noSelection="${['':'Seleccione ...']}"  nombre="direccionEmpleadorTipoCalle"  rotulo="Tipo calle" from="${tipoCalles}" valor="${diat?.direccionEmpleadorTipoCalle?.codigo}"/>
				<isl:textInput  cols="2-8" requerido="${attrsReq.direccionEmpleadorNombreCalle}" nombre="direccionEmpleadorNombreCalle" rotulo="Dirección" valor="${diat?.direccionEmpleadorNombreCalle}"/>
				<isl:textInput  cols="1-8" requerido="${attrsReq.direccionEmpleadorNumero}" tipo="numero" nombre="direccionEmpleadorNumero" rotulo="Número" valor="${diat?.direccionEmpleadorNumero}"/>
				<isl:textInput  cols="2-8" requerido="${attrsReq.direccionEmpleadorRestoDireccion}" nombre="direccionEmpleadorRestoDireccion" rotulo="Referencias" valor="${diat?.direccionEmpleadorRestoDireccion}"/>
				<isl:combo      cols="1-8" requerido="${attrsReq.direccionEmpleadorComuna}" noSelection="${['':'Seleccione ...']}"  nombre="direccionEmpleadorComuna" rotulo="Comuna" from="${comunas}" valor="${diat?.direccionEmpleadorComuna?.codigo}"/>
				<isl:textInput  cols="1-8" nombre="telefonoEmpleador" tipo="numero" rotulo="Teléfono" valor="${diat?.telefonoEmpleador}"/>
				<div class='salto-de-linea'></div>
				<isl:textInput  cols="1-8" requerido="${attrsReq.nTrabajadoresHombre}" nombre="nTrabajadoresHombre" tipo="numero" rotulo="N° trab. hombres" valor="${diat?.nTrabajadoresHombre}"/>
				<isl:textInput  cols="1-8" requerido="${attrsReq.nTrabajadoresMujer}" nombre="nTrabajadoresMujer" tipo="numero" rotulo="N° trab. mujeres" valor="${diat?.nTrabajadoresMujer}"/>
				<isl:radiogroup cols="1-8" requerido="${attrsReq.propiedadEmpresa}" nombre="propiedadEmpresa" name="propiedadEmpresa" rotulo="Propiedad" from="${tipoPropiedades}" valor="${diat?.propiedadEmpresa?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<div id="div_tipoEmpresa">
				<isl:radiogroup cols="3-8" requerido="${attrsReq.tipoEmpresa}" nombre="tipoEmpresa" name="tipoEmpresa" rotulo="Tipo empresa" from="${tipoEmpresas}" valor="${diat?.tipoEmpresa?.codigo}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				</div>
				<script type="text/javascript">
					YUI().use('node','event', function(Y) {
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
							// Limpiar vacios en nombre y apellido paterno
							var trabajador_nombre  = Y.one("#trabajador_nombre");
							if (trabajador_nombre.get("value") === " ")
								trabajador_nombre.set("value", "");
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

						var onLoadApe = function(e) {
							// Limpiar vacios en nombre y apellido paterno
							var ape_pat_trabajador = Y.one("#trabajador_apellidoPaterno");
							if (ape_pat_trabajador.get("value") === " ")
								ape_pat_trabajador.set("value", "");
						}

						var onLoadRazonSocial = function(e) {
							// Limpiar vacios en nombre y apellido paterno
							var ape_pat_trabajador = Y.one("#empleador_razonSocial");
							if (ape_pat_trabajador.get("value") === " ")
								ape_pat_trabajador.set("value", "");
						}
						
						Y.one('#div_tipoEmpresa').delegate('click', tipoEmpresaOnClick, 'input[type=radio]');
						Y.on("available", onLoad, "#trabajador_nombre");
						Y.on("available", onLoadApe, "#trabajador_apellidoPaterno");
						Y.on("available", onLoadRazonSocial, "#empleador_razonSocial");
					});
				</script>
				<div id='dv_ciiuPrincipal'>
					<isl:combo      cols="2-8" noSelection="${['':'Seleccione ...']}" nombre="ciiuPrincipal" rotulo="" from="${tipoActividadEconomica}" valor="${diat?.ciiuPrincipal?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}"/>
				</div>
			</div>
		</div>

		<legend>B. Identificación del trabajador - Denuncia Individual de Accidente de Trabajo</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="2-8"  requerido="true"  nombre="trabajador_nombre" rotulo="Nombres" valor="${diat?.trabajador?.nombre}"/>
				<isl:textInput cols="1-8"  requerido="true"  nombre="trabajador_apellidoPaterno" rotulo="Apellido paterno" valor="${diat?.trabajador?.apellidoPaterno}"/>
				<isl:textInput cols="1-8"  requerido="true"  nombre="trabajador_apellidoMaterno" rotulo="Apellido materno" valor="${diat?.trabajador?.apellidoMaterno}"/>
				<isl:textOutput cols="1-8" requerido="true"  rotulo="RUT" valor="${FormatosHelper.runFormatStatic(diat?.trabajador?.run)}" />
				<g:hiddenField name="trabajador_run" value="${diat?.trabajador?.run}" />
				<isl:radiogroup cols="1-8" requerido="true"  nombre="trabajador_sexo" name="trabajador_sexo" rotulo="Sexo" labels="['M','F']" values="['M','F']" valor="${diat?.trabajador?.sexo}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<isl:textInput cols="1-8"  requerido="true"  nombre="trabajador_fechaNacimiento" rotulo="Fecha nacimiento" tipo="fecha" valor="${FormatosISLHelper.fechaCortaStatic(diat?.trabajador?.fechaNacimiento)}"/>
				<isl:combo      cols="1-8" requerido="true"  nombre="nacionalidadTrabajador"  rotulo="Nacionalidad" from="${naciones}"   valor="${diat?.nacionalidadTrabajador?.codigo}" />
				<div class='salto-de-linea'></div>				
				<isl:combo      cols="1-8" requerido="${attrsReq.direccionTrabajadorTipoCalle}"  noSelection="${['':'Seleccione ...']}"  nombre="direccionTrabajadorTipoCalle"  rotulo="Tipo calle" from="${tipoCalles}" valor="${diat?.direccionTrabajadorTipoCalle?.codigo}" />
				<isl:textInput  cols="2-8" requerido="${attrsReq.direccionTrabajadorNombreCalle}"  nombre="direccionTrabajadorNombreCalle" rotulo="Dirección" valor="${diat?.direccionTrabajadorNombreCalle}" />
				<isl:textInput  cols="1-8" requerido="${attrsReq.direccionTrabajadorNumero}" tipo="numero" nombre="direccionTrabajadorNumero" rotulo="Número" valor="${diat?.direccionTrabajadorNumero}" />
				<isl:textInput  cols="2-8" requerido="${attrsReq.direccionTrabajadorRestoDireccion}"  nombre="direccionTrabajadorRestoDireccion" rotulo="Referencias" valor="${diat?.direccionTrabajadorRestoDireccion}" />
				<isl:combo      cols="1-8" requerido="${attrsReq.direccionTrabajadorComuna}"  noSelection="${['':'Seleccione ...']}"  nombre="direccionTrabajadorComuna" rotulo="Comuna" from="${comunas}" valor="${diat?.direccionTrabajadorComuna?.codigo}" />
				<isl:textInput  cols="1-8" nombre="telefonoTrabajador" tipo="numero" rotulo="Teléfono" valor="${diat?.telefonoTrabajador}" />
				
				<div class='salto-de-linea'></div>
				
				<isl:combo      cols="1-8" noSelection="${['':'Seleccione ...']}" nombre="etnia"  rotulo="Pueblo originario" from="${etnias}" valor="${diat?.etnia?.codigo}" />
				<isl:textInput cols="3-8"  requerido="${attrsReq.profesionTrabajador}" nombre="profesionTrabajador" rotulo="Profesión" valor="${diat?.profesionTrabajador}" />
				<isl:textInput cols="1-8" requerido="${attrsReq.fechaIngresoEmpresa}" nombre="fechaIngresoEmpresa" rotulo="Ingreso empresa" tipo="fecha" valor="${FormatosISLHelper.fechaCortaStatic(diat?.fechaIngresoEmpresa)}"/>
				<isl:radiogroup cols="3-8" requerido="${attrsReq.duracionContrato}"  nombre="duracionContrato" name="duracionContrato" rotulo="Tipo contrato" from="${tipoDuracionContratos}" valor="${diat?.duracionContrato?.codigo}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>
				<div class='salto-de-linea'></div>
				
				<div id='dv_otro_pueblo'>
					<isl:textInput cols="3-8" nombre="otroPueblo" rotulo="Otro Pueblo" valor="${diat?.otroPueblo}" />
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
							} else {
								document.getElementById("dv_otro_pueblo").style.display = "block";
							}
						};
						 
						Y.on("change", hideShowOtroPueblo, "#etnia");
						Y.on("available", hideShowOtroPueblo, "#etnia");
					});
				</script>
				
				<isl:radiogroup cols="3-8" requerido="${attrsReq.tipoRemuneracion}"  nombre="tipoRemuneracion" name="tipoRemuneracion" rotulo="Tipo ingreso" from="${tipoRemuneraciones}" valor="${diat?.tipoRemuneracion?.codigo}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<isl:radiogroup cols="3-8" nombre="sistemaSalud" name="sistemaSalud" rotulo="Sistema salud" from="${sistemaSalud}" valor="${diat?.sistemaSalud?.codigo}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
				<isl:radiogroup cols="5-8" requerido="${attrsReq.categoriaOcupacion}"  nombre="categoriaOcupacion" name="categoriaOcupacion" rotulo="Categoria ocupacional" from="${categoriaOcupaciones}"  valor="${diat?.categoriaOcupacion?.codigo}" >
					${it.label}
					${it.radio}
				</isl:radiogroup>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button tipo="siguiente" action="cu02"/>
	</div>
</g:form>
