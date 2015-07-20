<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="" />

<g:form name="dp03" class="pure-form pure-form-stacked" >

	<g:hiddenField name="dictamen" value="${bis?.dictamen}"/>
	<g:hiddenField name="bisId" value="${bis?.id}"/>
	<g:hiddenField name="docId" value=""/>
	
	<fieldset>
		<legend>Regularizar 77Bis</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:box tipo="alert" cols="8-8" texto="NO EXISTE SINIESTRO QUE COINCIDA CON LOS DATOS INGRESADOS" />
				<isl:textOutput cols="2-8" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(bis?.runTrabajador)}" />	
				
				<div class="salto-de-linea"></div>
				<isl:combo cols="2-8" deshabilitado="true"  noSelection="${['':'Seleccione...']}" nombre="tipoSiniestro" rotulo="Siniestro en Cobro" from="${tipoSiniestro}"  optionValue="${{it.descripcion}}" valor="${bis?.tipoSiniestro?.codigo}"  />
				<isl:textInput cols="2-8" deshabilitado="true" nombre="fecha_recepcion" ayuda="dd-mm-aaaa" rotulo="Fecha Recepción Carta" valor="${FormatosHelper.fechaCortaStatic(bis?.fechaRecepcion)}"/>
				<isl:textInput cols="2-8" deshabilitado="true" nombre="fecha_siniestro" ayuda="dd-mm-aaaa" rotulo="Fecha Siniestro o Primeros Sintomas" valor="${FormatosHelper.fechaCortaStatic(bis?.fechaSiniestro)}"/>			
				
				<div class="salto-de-linea"></div>
				<isl:textInput cols="2-8" deshabilitado="true" tipo="rut" nombre="run_empleador" rotulo="RUN Empleador" valor="${FormatosHelper.runFormatStatic(bis?.rutEmpleador)}" />
				<isl:textInput cols="2-8" deshabilitado="true" nombre="montoSolicitado" tipo="numero" ayuda="\$" rotulo="Monto Solicitado" valor="${FormatosHelper.montosStatic(bis?.montoSolicitado)}"/>
				
				<div id="DivDictamen">						
					<isl:radiogroup cols="1-8" nombre="dictamen" name="dictamen"
										deshabilitado="true"
										labels="['Sí', 'No']"
										values="[true, false]"
										valor="${bis? bis?.dictamen : null}"
										rotulo="¿Tiene Dictamen?">
							${it.label}
							${it.radio}
					</isl:radiogroup>
					<isl:textOutput cols="1-8" nombre="fechaDictamen" deshabilitado="true" ayuda="dd-mm-aaaa" rotulo="Fecha del Dictamen" valor="${FormatosHelper.fechaCortaStatic(bis?.fechaDictamen)}"/>
					<g:if test="${dictamen}">
						<isl:infoAdicional valor="${dictamen}" variable="docId" accion="viewDoc" rotulo="Ver Dictamen Adjunto" cols="2-8"/>
					</g:if>			
				</div>
				<div class="salto-de-linea"></div>
				
				<g:if test="${docs?.size() > 0}">	
					<div class="form-subtitle">Archivos Adicionales</div>			
					<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
						<thead>
							<tr>
								<th width="90%" >Descripción de los Archivos Adjuntos</th>
								<th width="5%" nowrap="nowrap">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${docs}" var="doc">
								<tr>						
									<td>${doc.descripcion}</td>
								   	<td nowrap="nowrap">
										<button class="pure-button pure-button-secondary" onclick="document.forms[0].docId.value='${doc.id}';document.forms[0].action='viewDoc';document.forms[0].submit();"><i class="icon-search"></i></button>
									</td>	
								</tr>
							</g:each>
						</tbody>
					</table>					
				</g:if>
					
			</div>
		</div>
		
		<script type="text/javascript">
			YUI().use('node', function (Y) {
				
	        	Y.on("domready", function(){
	        		var dictamen = document.getElementById("dictamen").value;
	        		if (dictamen == "true"){
	        			Y.one("#DivDictamen").setStyle("display", "")	
	        		}else{
	        			Y.one("#DivDictamen").setStyle("display", "none");	
	        		}
	        	});
		    		
				Y.one('#dictamen_0').delegate('click', function (e) {	
						Y.one("#DivDictamen").setStyle("display", "")
				}, 'input[type=radio]');

				Y.one('#dictamen_1').delegate('click', function (e) {		
					Y.one("#DivDictamen").setStyle("display", "none")
				}, 'input[type=radio]');
							  	
			});
		</script>
		
		<legend></legend>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit id="envio_regularizar" value="Enviar a Regularizar"  action="EnvioRegularizar" class="pure-button pure-button-warning"  />
	</div>
</g:form>