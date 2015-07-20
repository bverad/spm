<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript>

YUI().use('event', 'node', 'io', 'json-parse',  function (Y) {  
	
	Y.on("load", function(e) {
		traerCentrosSaludEnConvenio();
		
	})
	
	var traerCentrosSaludEnConvenio= function() {
		var convenioId = Y.one("#convenioId").get('value');
		var params = "convenioId=" + convenioId;
		
		Y.io('traerCentrosSaludEnConvenio?' + params, {
    	    on : {
    	        success : function (tx, r) {
    	        	try {
    	    			var data = Y.JSON.parse(r.responseText);
    				}
    				catch (e) {
    	    			alert("Error al recibir la los Centros de Salud con Convenio.");
    	    			return;
    				}
    	        	//Y.one("#dv_aranceles_convenio").setHTML(r.responseText);
    	        	
    	        	for (var i = 0; i < data.length; i++) {
    	        		var p = data[i];
    	        		var idCentroSalud = p.centroSalud.id;
    	        		// TODO JOSE: Se debe agregar el id de centro de salud en convenio
    	        		marcarCentrosDeSalud(idCentroSalud);
    	        	}
    	        	  
    	        }
    	    }
    	});
	}
	
	var marcarCentrosDeSalud= function(idCentroSalud) {
    	var msg = "";
    	var arancelBase = "";
    	var msg = "";
    	var cntElem = 0;
   		var ch = document.getElementsByName("cs");
    	    	   
    	//alert("Vamos a marcar el centro de salud : " + idCentroSalud)
    			
    	try {
    	
    		cntElem = ch.length;
        	msg += "Total de checkbox: " + cntElem + "\n";
        	//alert(msg);
        	for (var i = 0; i < cntElem; i++) {
        		msg += i + ": " + ch[i].checked + "\n";
        		//alert(ch[i].value + " == " + idCentroSalud);
        		if(ch[i].value == idCentroSalud) {
        			ch[i].checked = true;        			
        		} else {
        			msg += "No seleccionado : " +  ch[i].value + "\n";
        		}
        	}    		
        	
    	} catch (err) { alert(err); }

	}
	
	var addAllCentros = function (e) {
    	Y.all("#dv_centros_salud table tbody input").each(function (checkbox) {
    	    checkbox.set("checked", e.target.get("checked"));
    	});
        e.stopPropagation();
	}

    Y.one("#sel_all_centro").on("click", addAllCentros);

});

</g:javascript>
			<g:form action="save" class="pure-form pure-form-stacked">
				<g:hiddenField name="id" value="${prestadorInstance?.id}"/>
				<g:hiddenField name="convenioId" value="${convenio?.id}"/>
					<fieldset>
						<legend>Edición convenio</legend>
						<div class="pure-g-r">
							<div class="pure-u-1">	
	
								<g:if test="${prestadorInstance?.esPersonaJuridica}">
									<isl:textInput cols="1-10" tipo="text" nombre="run" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaJuridica?.rut)}" deshabilitado="true"/>
								</g:if>
								<g:else>
									<isl:textInput cols="1-10" tipo="text" nombre="run" rotulo="RUN/RUT" valor="${FormatosHelper.runFormatStatic(prestadorInstance?.personaNatural?.run)}" deshabilitado="true"/>
								</g:else>							
	
								<g:if test="${prestadorInstance?.esPersonaJuridica}">
									<isl:textInput cols="3-10" tipo="text" nombre="nombrePrestador" rotulo="Nombre/Razón social" valor="${prestadorInstance?.personaJuridica?.razonSocial}" deshabilitado="true"/>
								</g:if>
								<g:else>
									<isl:textInput cols="3-10" tipo="text" nombre="nombrePrestador" rotulo="Nombre/Razón social" valor="${prestadorInstance?.personaNatural?.nombre}" deshabilitado="true"/>
								</g:else>
								
								<isl:textInput cols="3-10" tipo="text" nombre="esPersonaJuridica" rotulo="Persona" valor="${prestadorInstance?.esPersonaJuridica ? "Juridica" : "Persona"}" deshabilitado="true"/>
								
								<isl:textInput cols="3-10" tipo="text" nombre="tipoPrestador" rotulo="Tipo Prestador" valor="${prestadorInstance?.tipoPrestador?.descripcion}" deshabilitado="true"/>
								<div class='salto-de-linea'></div>
	
								<div class="form-subtitle">Editar Convenio</div>
															
								<isl:radiogroup cols="2-10" requerido="true" rotulo="Estado" nombre="esActivo" name="esActivo" labels="['Activado','Desactivado']" values="[1,2]" valor="${convenio?.esActivo ? 1 : 2}">
									${it.label}
									${it.radio}
								</isl:radiogroup>
								<isl:textInput cols="3-10" requerido="true" tipo="text" nombre="nombre" rotulo="Nombre Convenio" valor="${convenio?.nombre}" maxLargo="255"/>	
								<isl:combo cols="3-10" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Tipo Convenio" nombre="tipoConvenio" from="${tipoConvenio}" valor="${convenio?.tipoConvenio?.codigo}"/>
								<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="numeroResolucion" rotulo="N° resolución" valor="${convenio?.numeroResolucion}"/>
								
								<isl:calendar cols="1-10" requerido="true" tipo="calendar" nombre="fechaResolucion" rotulo="F. resolución" valor="${convenio?.fechaResolucion}"/>
								
								<div class='salto-de-linea'></div>
								
								<isl:textInput cols="2-10" requerido="true" tipo="numero" nombre="recargoHorarioInhabil" rotulo="% Recargo horario inhábil" valor="${convenio?.recargoHorarioInhabil}" maxLargo="3"/>
								
															
								<isl:textOutput cols="1-10" rotulo="Fecha inicio" valor="${FormatosHelper.fechaCortaStatic(convenio?.inicio)}"/>
								<isl:textOutput cols="1-10" rotulo="Fecha término" valor="${FormatosHelper.fechaCortaStatic(convenio?.termino)}"/>
								
								<isl:textInput cols="2-10" requerido="true" tipo="text" nombre="periodoReajustable" rotulo="Período reajuste" valor="${convenio?.periodoReajustable}"/>
								<isl:calendar cols="1-10" requerido="true" tipo="calendar" nombre="fechaProximoReajuste" rotulo="Próx. reajuste" valor="${convenio?.fechaProximoReajuste}"/>	
								
								<isl:textInput cols="1-10" requerido="true" tipo="numero" nombre="montoConvenido" rotulo="Monto convenido" valor="${convenio?.montoConvenido}" maxLargo="20"/>
	
								<div class='salto-de-linea'></div>
								
								<div class="form-subtitle">Responsable convenio prestador</div>
								<isl:textInput cols="3-10" requerido="true" tipo="text" nombre="nombreResponsable" rotulo="Nombre" valor="${convenio?.nombreResponsable}" maxLargo="255"/>
								<isl:textInput cols="2-10" requerido="true" tipo="text" nombre="cargoResponsable" rotulo="Cargo" valor="${convenio?.cargoResponsable}" maxLargo="255"/>
								<isl:textInput cols="1-10" requerido="true" tipo="numero" nombre="telefonoResponsable" rotulo="Teléfono" valor="${convenio?.telefonoResponsable}" maxLargo="20"/>
								<isl:textInput cols="2-10" requerido="true" tipo="email" nombre="emailResponsable" rotulo="Email" valor="${convenio?.emailResponsable}" maxLargo="255"/>
								
								<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="numeroLicitacion" rotulo="ID licitación" valor="${convenio?.numeroLicitacion}"/>
								<isl:calendar cols="1-10" requerido="true" tipo="calendar" nombre="fechaAdjudicacion" rotulo="Adjudicación" valor="${convenio?.fechaAdjudicacion}"/>
	
	
								<div class='salto-de-linea'></div>
								<div class="form-subtitle">Responsable ISL</div>	
								<isl:textInput cols="2-8" requerido="true" tipo="text" nombre="nombreISL" rotulo="Nombre" valor="${convenio?.nombreISL}" maxLargo="255"/>
								<isl:textInput cols="2-8" requerido="true" tipo="text" nombre="cargoISL" rotulo="Cargo" valor="${convenio?.cargoISL}" maxLargo="255"/>
								<isl:textInput cols="2-8" requerido="true" tipo="numero" nombre="telefonoISL" rotulo="Teléfono" valor="${convenio?.telefonoISL}" maxLargo="20"/>
								<isl:textInput cols="2-8" requerido="true" tipo="email" nombre="emailISL" rotulo="Email" valor="${convenio?.emailISL}" maxLargo="255"/>													
								
								
								<div class="form-subtitle">Centros de salud del convenio</div>
								
								<div id="dv_centros_salud">
									<table class="pure-table" width="100%">
										<thead>
											<tr>
												<th align="center">Selección <br> <input type="checkbox" id="sel_all_centro"> </th>
												<th>Nombre</th>
												<th>Comuna</th>
												<th>Tipo</th>
												<th>Estado</th>
											</tr>
										</thead>
										<tbody>
											<g:if test="${listaCentrosDeSalud}">
												<g:each var="centroSalud" in="${listaCentrosDeSalud}">
													<tr>
														<td align="center" width="50px"><input type="checkbox" id="cs" name="cs" value="${centroSalud?.id}"></td>
														<td>${centroSalud?.nombre}</td>
														<td>${centroSalud?.comuna?.descripcion}</td>
														<td>${centroSalud?.tipoCentroSalud?.descripcion}</td>
														<td>${centroSalud?.esActivo ? 'Activo' : 'Inactivo'}</td>
													</tr>
												</g:each>	
											</g:if>
											<g:else>
												<tr>
													<td colspan="5"> No hay centros de salud del convenio activos... </td>
												</tr>
											</g:else>
										</tbody>	
									</table>
								</div>
								
	
								<div class="form-subtitle">Arancel en convenio 
									<button class="pure-button button-tool"  onclick="document.forms[0].action='mnt_arnclcnv';document.forms[0].submit();">
						    			<i class="icon-plus"></i> Agregar prestaciones al convenio
									</button>
								</div>

								
								<div id="dv_aranceles_convenio">
								
									<div class='salto-de-linea'></div>

									<table class="pure-table" width="100%">
										<thead>
											<tr>
												<th>Prestación</th>
												<th>Glosa</th>
												<th>Nivel</th>
												<th>Valor</th>
												<th>Cálculo</th>
												<th>Convenido</th>
												<th>F. Desde</th>
												<th>F. Hasta</th>
											</tr>
										</thead>
										<g:if test="${listaRegistrosArancelesEnConvenio}">
											<g:each in="${listaRegistrosArancelesEnConvenio}" status="i" var="arancelConvenio">
											<tbody>
												<tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
													<td align="center">${arancelConvenio.codigoPrestacion}</td>
													<td title="${arancelConvenio.glosa}">${FormatosHelper.truncateStatic(arancelConvenio.glosa, 70)}</td>
													<td align="center">${arancelConvenio.nivel}</td>
													<td align="right">${FormatosHelper.montoStatic(arancelConvenio.valorOriginal)}</td>
													<td align="right">${arancelConvenio.calculo}</td>
													<td align="right">${FormatosHelper.montoStatic(arancelConvenio.valorNuevo)}</td>
													<td align="center">${FormatosHelper.fechaCortaStatic(arancelConvenio.desde)}</td>
													<td align="center">${FormatosHelper.sumarDiasStatic(arancelConvenio.hasta, -1)}</td>
												</tr>
											</tbody>
											</g:each>
										</g:if>
										<g:else>
											<tr>
												<td colspan="8"> No existen aranceles convenio... </td>
											</tr>
										</g:else>
									</table>
								</div>
								
							</div>
						</div>
						
					</fieldset>	
					
					<div class="pure-g-r">
						<div class="pure-u-1">
							<isl:button tipo="siguiente" value="Actualizar" action="update_cnv"/>
							<button class="pure-button pure-button-secondary" onclick="document.forms[0].action='edit';document.forms[0].submit();">Volver</button>
						</div>
					</div>
					
			</g:form>

