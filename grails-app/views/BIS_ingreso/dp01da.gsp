<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:form name="dp01da" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post" >

	<g:hiddenField name="bis_id" value="${bis?.id}"/>
	<g:hiddenField name="dictamen" value="${bis?.dictamen}"/>
	<g:hiddenField name="docId" value = ""/>
	<g:hiddenField name="fecha_siniestro" value="${fecha_siniestro}"/>
	
	<fieldset>
 		<legend>Ingreso Datos 77Bis</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				
				<div class="form-subtitle">Adjuntar Dictamen</div>
				
				<g:if test="${dictamen?.size() > 0}">			
					<table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
						<thead>
							<tr>
								<th width="90%" >Descripción de los Archivos Adjuntos</th>
								<th width="5%" nowrap="nowrap">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${dictamen}" var="dic">
								<tr>						
									<td>${dic?.descripcion}</td>
								   	<td nowrap="nowrap">
										<button class="pure-button pure-button-secondary" onclick="document.forms[0].docId.value='${dic?.id}';document.forms[0].action='viewDoc';document.forms[0].submit();"><i class="icon-search"></i></button>
										<button class="pure-button pure-button-error" onclick="document.forms[0].docId.value='${dic?.id}';document.forms[0].action='deleteDoc';document.forms[0].submit();"><i class="icon-trash"></i></button>
									</td>	
								</tr>
							</g:each>
						</tbody>
					</table>					
				</g:if>	
				<g:else>
					<g:javascript src="BIS_ingreso/dp01da.js" />
					<isl:radiogroup cols="2-8" nombre="dictamen" name="dictamen"
									requerido="true"
									labels="['Sí','No']"	
									values="[true,false]"
									valor="${bis ? bis?.dictamen : null}"
									rotulo="¿Tiene Dictamen?">
							${it.label}
							${it.radio}
					</isl:radiogroup>			
					<div id="DivDictamen">	
						<isl:textInput cols="1-8" nombre="numeroDictamen" rotulo="N°Dictamen" valor="${bis?.numeroDictamen}" />	
						<isl:calendar cols="1-8" nombre="fechaDictamen" ayuda="dd-mm-aaaa" rotulo="Fecha del Dictamen" valor="${bis?.fechaDictamen}"/>					            
			            <div class="salto-de-linea"></div>
			            <div style="height: 20px;"></div>
			            <isl:upload cols="3-8" nombre="DictamenAdjunto" tipo="pdf"/> 
			            <button value="Adjuntar" action="guardaDictamen" class="pure-button pure-button-secondary" onclick="document.forms[0].action='guardaDictamen';document.forms[0].submit();">Adjuntar</button>					
					</div>		
				</g:else>
				
				<legend></legend>
					
				<div class="form-subtitle">Archivos Adicionales</div>	 
				<g:if test="${docs?.size() > 0}">			
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
									<td>${doc?.descripcion}</td>
								   	<td nowrap="nowrap">
										<button class="pure-button pure-button-secondary" onclick="document.forms[0].docId.value='${doc.id}';document.forms[0].action='viewDoc';document.forms[0].submit();"><i class="icon-search"></i></button>
										<button class="pure-button pure-button-error" onclick="document.forms[0].docId.value='${doc.id}';document.forms[0].action='deleteDoc';document.forms[0].submit();"><i class="icon-trash"></i></button>
									</td>	
								</tr>
							</g:each>
						</tbody>
					</table>					
				</g:if>			
		
				<div class="salto-de-linea"></div>
				<isl:textInput cols="3-8" nombre="descripcion_aadicional" rotulo="Descripción Archivo Adicional" valor="" />
				<div class="salto-de-linea"></div>
		        <div style="height: 20px;"></div>
		        <isl:upload cols="3-8" nombre="archivoAdjunto" tipo="pdf"/>
		        <button id="adjuntarArchivoAdicional" value="Adjuntar" action="adjuntarArchivoAdicional" class="pure-button pure-button-secondary" onclick="document.forms[0].action='adjuntarArchivoAdicional';document.forms[0].submit();return null;">Adjuntar</button>
							
			</div>
		</div>
		<legend></legend>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit id="buscarSiniestros" value="Buscar Siniestros" action="buscarSiniestros" class="pure-button pure-button-success"/>
	</div>
</g:form>