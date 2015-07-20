
<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="Arancel/cargaArancelesFONASA.js" />
	<g:form action="save" enctype="multipart/form-data" method="post" class="pure-form pure-form-stacked">
		<fieldset>
			<legend>Cargar aranceles Fonasa</legend>
			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:upload cols="3-8" nombre="fileAranceles" tipo="arancel" rotulo="Seleccionar Archivo"/>
					<isl:calendar cols="1-8" requerido="true" tipo="calendar" nombre="desde" requerido="true" rotulo="Vigencia desde" valor="${desde}"/>
					<isl:calendar cols="1-8" requerido="true" tipo="calendar" nombre="hasta" requerido="true" rotulo="Vigencia hasta" valor="${hasta}"/>
					<label for="Historica">
					Es historica
					<g:checkBox name="isCargaArancelHistorico" value="${params.isCargaArancelHistorico}" />
					</label>
				</div>
			</div>
		</fieldset>
		
		<isl:button action="save" tipo="excel" value="Cargar Arancel Fonasa" onclick="return confirm('Are you sure???')"/>
		
		<g:if test="${prestacionesCargadas}">
			<fieldset>
			<div class="form-subtitle">Aranceles Fonasa que se acaban de cargar</div>
				<div style="width:100%; overflow: auto;" >
					<table width="100%" class="pure-table pure-table-bordered">
						<thead>
							<tr>
								<th>Prestación</th>
								<th>Glosa</th>
								<th nowrap="nowrap">Nivel 1</th>
								<th nowrap="nowrap">Nivel 2</th>
								<th nowrap="nowrap">Nivel 3</th>
							</tr>
						</thead>
						<g:each var="prestacion" in="${prestacionesCargadas}">
							<tr>
								<td>${prestacion?.codigo}</td>
								<td>${prestacion?.glosa}</td>
								<td>${prestacion?.valorN1}</td>
								<td>${prestacion?.valorN2}</td>
								<td>${prestacion?.valorN3}</td>
							</tr>
						</g:each>				
					</table>
				</div>
			</fieldset>
		</g:if>	
		
		<g:if test="${errores}">
			<fieldset>
				<legend>Log de Errores</legend>
				<g:each var="error" in="${errores}">
		    		<p>${error}</p><br/>	
				</g:each>
			</fieldset>
		</g:if>
		
		<g:if test="${flash.default}">
			<fieldset>
				<legend>Mensaje : </legend>
				<div class="pure-u-1 messages">
					<ul>
						<li><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></li>
					</ul>
				</div>
			</fieldset>
		</g:if>
		
		<g:if test="${prestacionesCargadas}">
			<div class="pure-g-r">
				<g:actionSubmit value="Cancela Carga" action="delete" onclick="return confirm('¿Esta seguro de borrar la carga?')" class="pure-button pure-button-error"/>
				<g:actionSubmit value="Guardar Carga" action="update" class="pure-button pure-button-success"/>
			</div>
		</g:if>	
	</g:form>