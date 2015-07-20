<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="" />
<g:form name="dp01" class="pure-form pure-form-stacked" >

	<g:hiddenField name="siniestroId" value="${siniestro?.id ? siniestro?.id : siniestroId}"/>
	<g:hiddenField name="antecedenteId" value=""/>
	<g:hiddenField name="taskId" value="${params?.taskId ? params.taskId : taskId}"/>
	<g:hiddenField name="backTo" value="${params?.backTo ? params.backTo : backTo}"/>
	<g:hiddenField name="backToController" value="${params?.backToController ? params.backToController : backToController}"/>
	<g:hiddenField name="volverSeguimiento" value="${volverSeguimiento}"/>
	<g:hiddenField name="volverHistorial" value="${volverHistorial}"/>
	<g:hiddenField name="verDetalle" value="${verDetalle}" />
	<g:hiddenField name="origen" value="${origen}" />
	<g:hiddenField name="cesarODA" value="${cesarODA}" />
	<g:hiddenField name="recaOrigen" value="${recaOrigen}" />
	
	
	

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
                                <th width="15%">Region del Responsable</th>
                                <th width="50%">Solicitud</th>
                                <th width="15%">Días Transcurridos</th>
                                <th width="5%">Estado</th>
                                <th width="1%" nowrap="nowrap">Revisar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${anteAdicionales}" var="a">
                                <tr>          
                               		<td>${a.tipoAntecedente.descripcion}</td>
                                  	<td>${a?.regionResponsable?.descripcion ? a.regionResponsable.descripcion : 'Nacional'}</td>
                                   	<td>${FormatosHelper.truncateStatic(a.solicitud)}</td> 
                                   	<td>${(a.fechaSolicitud == 1)?new Date() - a.fechaSolicitud + " Día":new Date() - a.fechaSolicitud + " Días"}</td>             
                                    <td>${a.estado? 'Entregado' : 'Pendiente'}</td>
                                    <td nowrap="nowrap">  
                                    	<button class="pure-button pure-button-secondary" onclick="document.forms[0].antecedenteId.value='${a?.id}'; document.forms[0].action='dp03';document.forms[0].submit();"><i class="icon-search"></i></button> 
                                    </td>
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
            	<g:if test="${volverSeguimiento}">   
			        <isl:combo cols="4-8" requerido="true" nombre="tipoAntecedente" from="${tipoAntecedentes}" rotulo="Tipo Antecedente" valor="${tAnte}" optionValue="${{it.descripcion}}"/>            
					<div class="salto-de-linea"></div>
				</g:if>
                <g:else>
                	<isl:combo cols="4-8" requerido="true" nombre="tipoAntecedente" from="${tipoAntecedentes}" rotulo="Tipo Antecedente" valor="${tAnte}" optionValue="${{it.descripcion}}"/>
                	<isl:combo cols="4-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="regionResponsable" from="${regionResponsable}" rotulo="Región del responsable" valor="${rResp}" optionValue="${{it.descripcion}}" />
                </g:else>
                <isl:textArea cols="8-8" requerido="true" nombre="solicitud" rotulo="Solicitud" valor="${solicitud}"/> 
                <g:actionSubmit id="enviar_solicitud" value="Solicitar Antecedentes"  action="postDp01" class="pure-button pure-button-success"  />                    
            </div>
        </div>
        
        <legend></legend>
        
    </fieldset> 
    <div class="pure-g-r">
         <g:actionSubmit id="volver" value="Volver"  action="go_back" class="pure-button pure-button-secondary" formnovalidate="formnovalidate" />  
    </div>
</g:form>