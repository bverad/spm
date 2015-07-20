<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="" />
<g:form name="dp03" class="pure-form pure-form-stacked" >

	<g:hiddenField name="siniestroId" value="${siniestro.id}"/>
	<g:hiddenField name="solicituId" value="${params.solicituId}"/>
	<g:hiddenField name="antecedenteId" value=""/>
	<g:hiddenField name="taskId" value="${params.taskId}"/>
	<g:hiddenField name="backTo" value="${params?.backTo}"/>
	<g:hiddenField name="backToController" value="${params?.backToController}"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	<g:hiddenField name="recaOrigen" value="${recaOrigen}" />
	<g:hiddenField name="volverAntecedentes" value="${volverAntecedentes}" />
	
	

    <fieldset>
        <legend>Datos Siniestro</legend>
        <div class="pure-g-r">
            <div class="pure-u-1">
                <!-- Info Siniestro -->
                <isl:textOutput cols="6-8" requerido="true" deshabilitado="true" rotulo="N° Siniestro" valor="${siniestro?.id}" />          
                <isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="dias_restantes" rotulo="Días Restantes" valor="${diasR}"/>
                <div class="salto-de-linea"></div>
                <!-- Info Trab/prest -->
                <isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(siniestro.trabajador?.run)}"/>
                <isl:textInput  cols="4-8" requerido="true" deshabilitado="true" nombre="tipo_siniestro" rotulo="Tipo Siniestro" valor="${siniestro.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}"/>
                <isl:textInput  cols="2-8" requerido="true" deshabilitado="true" nombre="fecha_siniestro" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="Fecha Siniestro"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}" />               
            </div>
        </div>
        
        <legend>Solicitud</legend>
        
        <div class="pure-g-r">
            <div class="pure-u-1">
            	<g:if test="${antecedentes?.tipoAntecedente?.codigo == '3' }">
	           	 	<isl:textOutput cols="2-8" requerido="true" deshabilitado="true" nombre="tipoAntecedentes" rotulo="Tipo Antecedente" valor="${antecedentes?.tipoAntecedente?.descripcion}"/>
	                <div class="salto-de-linea"></div>
               	</g:if>
               	<g:else>
               		<isl:textOutput cols="2-8" requerido="true" deshabilitado="true" nombre="tipoAntecedentes" rotulo="Tipo Antecedente" valor="${antecedentes?.tipoAntecedente?.descripcion}"/>
	                <isl:textOutput cols="2-8" requerido="true" deshabilitado="true" nombre="regionResponsable" rotulo="Región del responsable" valor="${antecedentes?.regionResponsable?.descripcion}" />
               	</g:else>
                <isl:textOutput cols="2-8" requerido="true" deshabilitado="true" nombre="fechaSolicitud" rotulo="Fecha de Solicitud"  valor="${FormatosHelper.fechaCortaStatic(antecedentes?.fechaSolicitud)}"/>
                <isl:textOutput cols="2-8" requerido="true" deshabilitado="true" nombre="fechaRespuesta" rotulo="Fecha de Respuesta"  valor="${FormatosHelper.fechaCortaStatic(antecedentes?.fechaRespuesta)}"/>
                <isl:textArea cols="8-8" requerido="true" deshabilitado="true" nombre="solicitud" rotulo="Solicitud" valor="${antecedentes?.solicitud}"/>   
                <isl:textArea cols="8-8" requerido="true" deshabilitado="true" nombre="respuesta" rotulo="Respuesta" valor="${antecedentes?.respuesta}"/>                 
            </div>
        </div>
        
        <div class="pure-g-r">
            <div class="pure-u-1">  
                <legend>Archivos Adjuntos</legend>              
                <g:if test="${archivosAdjuntos?.size() > 0 }">              
                    <table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
                        <thead>
                            <tr>
                                <th width="65%">Descripción del antecedente</th>
                                <th width="10%">Fecha creación</th>
                                <th width="10%">Autor</th>
                                <th width=1%">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${archivosAdjuntos}" var="d">
                                <tr>          
                               		<td>${d.descripcion}</td>
                               		<td>${FormatosHelper.fechaCortaStatic(d.creadoEl)}</td>
                               		<td>${d.creadoPor}</td>
                                  	<td>
                                  		<button class="pure-button pure-button-secondary" onclick="document.forms[0].antecedenteId.value='${d?.id}';document.forms[0].action='viewAntecedente';document.forms[0].submit();"><i class="icon-info-sign"></i></button>
                                  	</td>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>                    
                </g:if>
                <g:else>
                    <div>
                        <div>
                            <p>No hay Archivos adjuntos para este siniestro. </p>
                        </div>
                    </div>
                </g:else>             
            </div>
        </div>     
        
        <legend></legend>
        
    </fieldset> 
    <div class="pure-g-r">
        <button class="pure-button pure-button-secondary" onclick="document.forms[0].action='go_back';document.forms[0].submit();">Volver</button> 
    </div>
</g:form>