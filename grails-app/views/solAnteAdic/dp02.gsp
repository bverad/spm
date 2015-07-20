<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="" />
<g:form name="dp02" class="pure-form pure-form-stacked" enctype="multipart/form-data" method="post">

	<g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
	<g:hiddenField name="taskId" value="${params.taskId}"/>
	<g:hiddenField name="solicitudId" value="${antecedentes?.id}"/>
	<g:hiddenField name="antecedenteId" value=""/>

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
        
        <legend>Antecedentes Adicionales Solicitados</legend>
            
        <div class="pure-g-r">
            <div class="pure-u-1">
                <g:if test="${anteAdicionales?.size() > 0 }">              
                    <table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
                        <thead>
                            <tr>
                                <th width="15%">Tipo de Antecedente</th>
                                <th width="15%">Fecha Solicitud</th>
                                <th width="65%">Solicitud</th>
                                <th width="5%">Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${anteAdicionales}" var="a">
                                <tr>          
                               		<td>${a.tipoAntecedente.descripcion}</td>
                                  	<td>${FormatosHelper.fechaCortaStatic(a.fechaSolicitud)}</td>
                                   	<td>${FormatosHelper.truncateStatic(a.solicitud)}</td>          
                                    <td>${a.estado? 'Entregado' : 'Pendiente'}</td>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>                    
                </g:if>
                <g:else>
                    <div>
                        <div>
                            <p>No hay solicitudes de antecedentes adicionales. </p>
                        </div>
                    </div>
                </g:else>
            </div>
        </div>
        
        <legend>Solicitud</legend>
        
        <div class="pure-g-r">
            <div class="pure-u-1">
                <isl:textArea cols="8-8" requerido="true" deshabilitado="true" nombre="solicitud" rotulo="Solicitud" valor="${antecedentes?.solicitud}"/>  
                <isl:textArea cols="8-8" requerido="true" nombre="respuesta" rotulo="Respuesta" valor="${params.respuesta?:params.respuesta}"/>  
                
                <legend>Adjuntar Archivos</legend>  
                <isl:textInput cols="2-8" nombre="descripcion" rotulo="Descripción del Antecedente Solicitado" valor=""/>
                <div style="height: 20px;"></div>
              	<isl:upload cols="3-8" nombre="archivoAdjunto" tipo=""/> <div class="salto-de-linea"></div>
                <button value="Adjuntar" action="adjuntarArchivoAdicional" class="pure-button pure-button-warning" onclick="document.forms[0].action='adjuntarArchivoAdicional';document.forms[0].submit();">Adjuntar</button>	
                <div class="salto-de-linea"></div>              
                              
                <legend>Archivos Adjuntos</legend>              
                <g:if test="${archivosAdjuntos?.size() > 0 }">              
                    <table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
                        <thead>
                            <tr>
                                <th width="65%">Descripción del antecedente</th>
                                <th width="10%">Fecha creación</th>
                                <th width="10%">Autor</th>
                                <th width=10%">&nbsp;</th>
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
                                  		<button class="pure-button pure-button-error" onclick="document.forms[0].antecedenteId.value='${d?.id}';document.forms[0].action='deleteAntecedente';document.forms[0].submit();"><i class="icon-minus-sign"></i></button>
                                  	</td>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>                    
                </g:if>
                <g:else>
                    <div>
                        <div>
                            <p>No ha subido archivos adjuntos para esta Solicitud de Antecedentes Adicionales. </p>
                        </div>
                    </div>
                </g:else>                                 
            </div>
        </div>
        
        <legend></legend>
        
    </fieldset> 
    <div class="pure-g-r">
        <div class="pure-g-r">
       	 	<g:actionSubmit id="volver" value="Volver"  action="dp02_back" class="pure-button pure-button-secondary" formnovalidate="formnovalidate" />  
			<g:actionSubmit id="enviar_respuesta" value="Enviar Respuesta"  action="postDp02" class="pure-button pure-button-success"  />
		</div>
    </div>
</g:form>