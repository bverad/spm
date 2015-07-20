<g:form name="dp03" class="pure-form pure-form-stacked" >
	<isl:header_sdaat sdaat="${sdaat}"/>
	<!-- TODO: En este caso falta incluir al isl:header_sdaat el N° Siniestro -->
	<fieldset>
		<legend>Informar OPA</legend>	
		
		<div class="pure-g-r">
			<div class="pure-u-1">		
				<g:if test="${hayOpaPrevia=='Si'}">
					<!--  la OPA no fue generada en esta solicitud -->
				    <div class="pure-g-r">
							<div class="pure-u-1">
						<isl:box texto="Existe una OPA ya emitida para este  siniestro
						                ¿Desea re-imprir?."/>
						</div>
					</div>
				</g:if>				
				<g:if test="${hayOpaPrevia=='No'}">
					<!--  la OPA fue generada en esta solicitud -->
				    <div class="pure-g-r">
							<div class="pure-u-1">
								<isl:box texto="¿Desea Imprimir la OPA de este siniestro?"/>
							</div>
					</div>
				</g:if>	
			</div>
		</div>
		
		<div class="pure-g-r">
			<div class="pure-u-1">
				<g:actionSubmit value="Terminar" action="end"  class="pure-button pure-button-warning"  />
				<isl:button tipo="siguiente" action="r03" value="Imprimir" />
			</div>
		</div>
	</fieldset>
</g:form>