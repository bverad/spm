<%@ page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>
<g:form class="pure-form pure-form-stacked" >
	<g:hiddenField name="siniestroId" value="${siniestro.id}" />
	<g:hiddenField name="docAdicionalId" /> 
	<g:hiddenField name="reingresoId" value="${reingreso?.id}" /> 
    <fieldset>
 		<legend>Evalua Reingreso del caso a Seguimiento</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
			
				<div class="form-subtitle">Datos Siniestro</div>
				<isl:textOutput cols="1-8" rotulo="N° Siniestro" valor="${siniestro?.id}" />
				<isl:textOutput cols="1-8" rotulo="Tipo Siniestro" valor="${siniestro?.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro?.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}" />
				<isl:textOutput cols="1-8" rotulo="Complejidad" valor="${FormatosISLHelper.getNivelComplejidadStr(siniestro?.nivelComplejidad)}" />
				<isl:textOutput cols="1-8" rotulo="Fecha Siniestro" valor="${FormatosISLHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<isl:textOutput cols="1-8" rotulo="RUN Trabajador" valor="${FormatosISLHelper.runFormatStatic(siniestro?.trabajador?.run)}" />
				<isl:textOutput cols="1-8" rotulo="Prestador OPA" valor="${prestador}" />
				<isl:textOutput cols="1-8" rotulo="Vencimiento OPA" valor="${FormatosISLHelper.fechaCortaStatic(vencimientoOPA)}" />
				
				<div class='salto-de-linea'></div>
				<div class="form-subtitle">Datos de Contacto</div>
				<isl:textOutput cols="2-8" valor="${reingreso?.nombre}"				rotulo="Nombre" />
				<isl:textOutput cols="1-8" valor="${reingreso?.apellidoPaterno}"	rotulo="Apellido Paterno" />
				<isl:textOutput cols="1-8" valor="${reingreso?.apellidoMaterno}"	rotulo="Apellido Materno" />
				<isl:textOutput cols="2-8" valor="${reingreso?.direccion}"			rotulo="Dirección" />
				<isl:textOutput cols="1-8" valor="${reingreso?.telefono}"			rotulo="Teléfono" />
				<isl:textOutput cols="1-8" valor="${reingreso?.email}"				rotulo="E-mail" />

				<div class='salto-de-linea'></div>
				<div class="form-subtitle">Datos de la Solicitud</div>
				<isl:textOutput cols="8-8" requerido="true" valor="${reingreso?.motivo}" rotulo="Motivo de la Solicitud" nombre="motivo"/>
				
				<div class="form-subtitle">Documentación Adjunta</div>
				
				<table class="pure-table pure-table-bordered pure-table-striped" width="100%">
					<thead>
						<tr>
							<th>Nombre Archivo</th>
							<th>Ver</th>
						</tr>
					</thead>
					<tbody>
						<g:if test="${!adjuntos}">
							<tr>						
								<td colspan="2">No hay archivos adjuntos</td>
							</tr>
						</g:if>		 		
						<g:each in="${adjuntos}" var="doc">
							<tr>						
								<td>${doc.descripcion}</td>
							   	<td align="center">
							   		<a href="#" onclick="document.forms[0].docAdicionalId.value='${doc.id}';document.forms[0].action='cu05v';document.forms[0].submit();" style="text-decoration: none;">
							   			<font color=red><i class="icon-edit icon-2x"></i></font>
							   		</a>
								</td>	
							</tr>
						</g:each>
					</tbody>
				</table>
									
				<div class='salto-de-linea'></div>
				<div class="form-subtitle">Reingreso</div>
				<isl:textArea cols="4-8" requerido="true" nombre="resumen" rotulo="Resumen del caso" valor="${seguimiento?.resumen}"/>
				<isl:textArea cols="4-8"				  nombre="observaciones" rotulo="Comentarios u observaciones" valor="${seguimiento?.observaciones}"/>
				<isl:combo 	  cols="2-8"				  nombre="nivel" from="${nivel}" rotulo="Nivel de Seguimiento" valor="${seguimiento?.nivel}" noSelection="${['0':'Sin Seguimiento']}" />
			</div>
		</div>
	</fieldset>
	<div class="pure-g-r">		
		<isl:button action="cu05s" tipo="siguiente" value="Guardar" />
	</div>
</g:form>
