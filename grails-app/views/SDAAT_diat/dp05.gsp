<g:form name="dp05" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<fieldset>
		<legend>¿Existe documentación adicional?</legend>
	</fieldset>
	<div class="pure-g-r">
		<div class="pure-u-1">
			<isl:button action="dp06" tipo='volver' value="Agregar" />
			<isl:button action="cu05" tipo='siguiente'/>
		</div>	
	</div>
	
		<!-- Despliegue de informacion -->
	<g:if test="${flash.default}">
		<fieldset>
			<div class="pure-u-1 messages">
				<ul>
					<li><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></li>
				</ul>
			</div>
		</fieldset>
	</g:if>
</g:form>
