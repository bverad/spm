<g:form name="dp06" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
		<legend>Escanea y guarda documentación adicional</legend>

		<!-- Muestra Documentos Anteriores -->
		<g:if test="${docAdicionales != []}">
			<div>
				<div class="pure-u-l">
					<table class="pure-table pure-table-bordered pure-table-striped">
						<thead>
							<tr>
								<th colspan="2">Descripci&oacute;n</th>
							</tr>
						</thead>
						<tbody>
							<g:each var="docAdicional" in="${docAdicionales}">
								<tr>
									<td>${docAdicional.descripcion}</td>
									<td>
										<g:link action="deleteDocumentoAdicional" 
														style="text-decoration: none;"
														id="${docAdicional.id}">(e)</g:link>
									</td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</div>
			</div>
		</br>
		</g:if>


		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="4-8" nombre="descripcionDocumento"  tipo="SDAEP"  rotulo="Descripción documento"/>
				<div class='salto-de-linea' />
				<isl:upload cols="4-8" nombre="fileAdicional" rotulo="Documento"/>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<div class="pure-u-1">
			<isl:button action="uploadDocumentoAdicional" tipo="pdf" value="Subir documentación adicional" />
			<isl:button action="dp05" tipo='siguiente' value="Siguiente" />

		</div>	
	</div>
</g:form>

