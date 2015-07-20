<g:javascript src="Prestador/create_prestador.js" />
<%@ page import="cl.adexus.helpers.FormatosHelper" %>	
	<g:form action="save" class="pure-form pure-form-stacked">
		<fieldset>		
			<legend>Crear prestador</legend>
				<div class="pure-g-r">
					<div class="pure-u-1">	
						<isl:radiogroup cols="2-10" requerido="true"  nombre="esActivo" name="esActivo" labels="['Activado','Desactivado']" values="[true,false]" valor="${prestadorInstance?.esActivo}" rotulo="Estado">
							${it.label}
							${it.radio}
						</isl:radiogroup>
													
						<isl:radiogroup cols="2-10" requerido="true" nombre="esPersonaJuridica" name="esPersonaJuridica" labels="['Jurídica','Natural']" values="[true,false]" valor="${prestadorInstance?.esPersonaJuridica}" rotulo="Tipo persona">
							${it.label}
							${it.radio}
						</isl:radiogroup>

						<isl:combo cols="3-10" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Estructura jurídica" nombre="estructuraJuridica" from="${estructuraJuridica}" valor="${prestadorInstance?.estructuraJuridica?.codigo}"/>
						<isl:combo cols="3-10" requerido="true" noSelection="${['':'Seleccione ...']}"  rotulo="Tipo prestador" nombre="tipoPrestador" from="${tipoPrestador}" valor="${prestadorInstance?.tipoPrestador?.codigo}"/>	


						<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="rut" rotulo="RUT" valor="${prestadorInstance?.personaJuridica?.rut}" style="display: none"/>
						<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="run" rotulo="RUN" valor="${prestadorInstance?.personaNatural?.run}" style="display: none"/>

						<isl:textInput cols="3-10" requerido="true" tipo="text" nombre="razonSocial" rotulo="Razón social" valor="${prestadorInstance?.personaJuridica?.razonSocial}" style="display: none"/>
						<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="nombre" rotulo="Nombre" valor="${prestadorInstance?.personaNatural?.nombre}" style="display: none"/>
						<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="apePaterno" rotulo="Apellido paterno" valor="${prestadorInstance?.personaNatural?.nombre}" style="display: none"/>
						<isl:textInput cols="1-10" requerido="true" tipo="text" nombre="apeMaterno" rotulo="Apellido materno" valor="${prestadorInstance?.personaNatural?.nombre}" style="display: none"/>

						<isl:textInput cols="2-10" requerido="true" tipo="text" nombre="direccion" rotulo="Dirección (Casa matriz)" valor="${prestadorInstance?.direccion}"/>
						
						<isl:combo cols="1-10" requerido="true" noSelection="${['':'Seleccione ...']}" rotulo="Comuna" nombre="comuna" from="${comunas}" valor="${prestadorInstance?.comuna?.codigo}"/>
						
						
						<isl:textInput cols="1-10" requerido="true" tipo="numero" nombre="telefono" rotulo="Teléfono" valor="${prestadorInstance?.telefono}"/>
							
						<isl:textInput cols="2-10" requerido="true" tipo="email" nombre="email" rotulo="Email" valor="${prestadorInstance?.email}"/>
					</div>
				</div>
					
					
				
		 		<div class="form-subtitle">Representante Legal</div>
				<div class="pure-g-r">
					<div class="pure-u-1">							
						
						<g:if test="${prestadorInstance?.representanteLegal?.run}">
							<isl:textInput cols="1-8" tipo="text" nombre="runRL" rotulo="RUN" valor="${FormatosHelper.isValidRutStatic(prestadorInstance?.representanteLegal?.run) ? FormatosHelper.runFormatStatic(prestadorInstance?.representanteLegal?.run) : prestadorInstance?.representanteLegal?.run}"/>
						</g:if>
						<g:else>
							<isl:textInput cols="1-8" tipo="text" nombre="runRL" rotulo="RUN" valor=""/>
						</g:else>							
						
						<isl:textInput cols="2-8" tipo="text" nombre="nombreRL" rotulo="Nombre" valor="${prestadorInstance?.representanteLegal?.nombre}"/>							
						<isl:textInput cols="2-8" tipo="text" nombre="paternoRL" rotulo="Apellido paterno" valor="${prestadorInstance?.representanteLegal?.apellidoPaterno}"/>							
						<isl:textInput cols="1-8" tipo="text" nombre="maternoRL" rotulo="Apellido materno" valor="${prestadorInstance?.representanteLegal?.apellidoMaterno}"/>							
													
						<isl:calendar cols="1-8" nombre="desdeRL" tipo="calendar" rotulo="Vigencia desde"  valor="${prestadorInstance?.desdeRL}" />
						<isl:calendar cols="1-8" nombre="hastaRL" tipo="calendar" rotulo="Vigencia hasta"  valor="${prestadorInstance?.hastaRL}" />
						
					</div>
				</div>
					

				<div class="form-subtitle">Apoderado</div>	

				<div class="pure-g-r">
					<div class="pure-u-1">							

						<g:if test="${prestadorInstance?.apoderado?.run}">
							<isl:textInput cols="1-8" tipo="text" nombre="runAP" rotulo="RUN" valor="${FormatosHelper.isValidRutStatic(prestadorInstance?.apoderado?.run) ? FormatosHelper.runFormatStatic(prestadorInstance?.apoderado?.run) : prestadorInstance?.apoderado?.run}"/>
						</g:if>
						<g:else>
							<isl:textInput cols="1-8" tipo="text" nombre="runAP" rotulo="RUN" valor=""/>
						</g:else>							
						
						<isl:textInput cols="2-8" tipo="text" nombre="nombreAP" rotulo="Nombre" valor="${prestadorInstance?.apoderado?.nombre}"/>							
						<isl:textInput cols="2-8" tipo="text" nombre="paternoAP" rotulo="Apellido paterno" valor="${prestadorInstance?.apoderado?.apellidoPaterno}"/>							
						<isl:textInput cols="1-8" tipo="text" nombre="maternoAP" rotulo="Apellido Materno" valor="${prestadorInstance?.apoderado?.apellidoMaterno}"/>							
													
						<isl:calendar cols="1-8" nombre="desdeAP" tipo="calendar" rotulo="Vigencia desde"  valor="${prestadorInstance?.desdeAP}" />
						<isl:calendar cols="1-8" nombre="hastaAP" tipo="calendar" rotulo="Vigencia hasta"  valor="${prestadorInstance?.hastaAP}" />

					</div>
				</div>
			</fieldset>	

			<div class="pure-g-r">
				<div class="pure-u-1">
					<isl:button tipo="siguiente" value="Guardar" action="save"/>
				</div>
			</div>
	</g:form>
<div id="js_msg">
</div>