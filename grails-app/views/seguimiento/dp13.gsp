<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:javascript src="Seguimiento/dp13.js" />
<g:form class="pure-form pure-form-stacked">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="volverSeguimiento" value="${params?.volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${params?.volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="dp14"/>
	<g:hiddenField name="origen" value="${params?.origen}"/>
	<g:hiddenField name="nivelSeguimiento" value="${seguimiento?.nivel}"/>
	<g:hiddenField name="cesarODA" value="${cesarODA}"/>

	<fieldset>
		<legend>Emitir ODA</legend>
 		<div class="form-subtitle">Datos de la orden</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:combo		cols="2-8" nombre="tipoODA"			requerido="true" rotulo="Tipo ODA" 				valor="${oda?.tipoODA?.codigo}"	noSelection="${['':'Seleccione ...']}" from="${listadoTipoODA}"/>
				<isl:textOutput	cols="1-8" 											 rotulo="Fecha Creación"		valor="${FormatosISLHelper.fechaCortaStatic(oda?.fechaCreacion)}" />
				<isl:calendar 	cols="1-8" nombre="inicioVigencia"  requerido="true" rotulo="Inicio Vigencia"		valor="${oda?.inicioVigencia}"/>
				<isl:calendar 	cols="1-8" nombre="terminoVigencia" requerido="true" rotulo="Término Vigencia"		valor="${oda?.terminoVigencia}"/>
				<div class='salto-de-linea'></div>
				
				<isl:combo		cols="2-8" nombre="region" 			requerido="true" rotulo="Región"	 			valor="${region}"				noSelection="${['':'Seleccione ...']}" from="${listadoRegiones}"/>
				<isl:combo		cols="2-8" nombre="prestador" 		requerido="true" rotulo="Prestador" 			valor="${prestador}"			noSelection="${['':'Seleccione ...']}" from="${listadoPrestadores}"/>
				<isl:combo		cols="2-8" nombre="centroAtencion" 	requerido="true" rotulo="Centro de Salud" 		valor="${oda?.centroAtencion?.id}" noSelection="${['':'Seleccione ...']}" from="${listadoCentroSalud}" />
				<isl:textOutput	cols="2-8" nombre="direccionPrestador" 				 rotulo="Dirección Prestador"	valor="${direccionPrestador != null ? direccionPrestador : ''}" />
			</div>
		</div>

 		<div class="form-subtitle">Datos del Trabajador</div>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textOutput cols="1-8" 										 		 rotulo="RUN"	 			valor="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}" />
				<isl:textOutput cols="1-8" 										 		 rotulo="Nombre"		 	valor="${FormatosISLHelper.nombreCompletoStatic(siniestro?.trabajador)}" />
				<isl:textInput	cols="2-8" nombre="direccionTrabajador"	requerido="true" rotulo="Dirección" 		valor="${oda?.direccionTrabajador}"/>
				<isl:combo		cols="2-8" nombre="comunaTrabajador" 	requerido="true" rotulo="Comuna" 			valor="${oda?.comunaTrabajador?.codigo}" noSelection="${['':'Seleccione ...']}" from="${comunas}"/>
				<isl:textInput	cols="1-8" nombre="telefonoTrabajador" 	requerido="true" rotulo="Teléfono" 			valor="${oda?.telefonoTrabajador}" tipo="numero"/>
				<div class='salto-de-linea'></div>
				
				<isl:textOutput cols="1-8" 										 		 rotulo="Sexo"		 		valor="${siniestro?.trabajador?.sexo == 'M' ? 'Masculino' : 'Femenino'}" />
				<isl:textOutput cols="1-8" 										 		 rotulo="Fecha Nacimiento"	valor="${FormatosISLHelper.fechaCortaStatic(siniestro?.trabajador?.fechaNacimiento)}" />
				<isl:textInput	cols="3-8" nombre="emailTrabajador"  rotulo="E-Mail" valor="${oda?.emailTrabajador}" tipo="email"/>
			</div>
		</div>

 		<div id="atenciones" hidden>

	 		<div class="form-subtitle" id="">Atenciones Indicadas</div>
			<div class="pure-g-r" id="">
				<div class="pure-u-1">
					<isl:textInput	cols="1-8" nombre="codigo"		rotulo="Código Arancel" 	valor="" tipo="numero"/>
					<isl:textOutput	cols="3-8" nombre="glosa"		rotulo="Glosa Arancel"		valor=""/>
					<isl:textInput	cols="1-8" nombre="cantidad" 	rotulo="Cantidad" 			valor="" tipo="numero"/>
					<g:hiddenField 	name="idArancelConvenio" />
					<g:hiddenField 	name="tipoPrestacion" />
					<g:hiddenField 	name="listaPrestaciones" value="${listaPrestaciones}" />
					
					<div style="height: 19px;"></div>
					<button class="pure-button pure-button-secondary" id="btnAgregarPrestacion" disabled="disabled">Agregar</button>
					<br/>
					<br/>
					
					<table id="prestacionesTable" class="pure-table pure-table-bordered pure-table-striped" width="100%">
						<thead>
							<tr>
								<th>Código Arancel</th>
								<th>Glosa Arancel</th>
								<th>Cantidad</th>
								<th>Eliminar</th>
							</tr>
						</thead>
						<tbody>
							<tr id="sinDatos">						
								<td colspan="4">No hay aranceles asociados...</td>
							</tr>
						</tbody>
					</table>					
				</div>
			</div>
		</div>

	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit action="dp12"     class="pure-button pure-button-secondary" value="Volver" formnovalidate="formnovalidate"/>
		<g:actionSubmit action="postDp13" class="pure-button pure-button-success"   value="Emitir ODA" />
	</div>
</g:form>