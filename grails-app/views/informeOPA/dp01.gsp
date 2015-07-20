<%@page import="cl.adexus.isl.spm.Siniestro"%>
<%@page import="cl.adexus.helpers.FormatosHelper" %>
<%@page import="cl.adexus.isl.spm.helpers.FormatosISLHelper" %>

<g:javascript src="InformeOPA/opa.js" />

<g:form name="dp01" class="pure-form pure-form-stacked" >

    <g:hiddenField name="siniestroId" value=""/> 
    
    <fieldset>
    <legend>Filtro de Busqueda</legend>
    <div class="pure-g-r">
            <div class="pure-u-1">
                <!-- Info Siniestro -->        
                <isl:textInput cols="1-5" nombre="nSiniestro"  tipo="numero" rotulo="N° Siniestro" valor="${nSiniestro}" />
                <isl:textInput cols="1-5" nombre="runPaciente" rotulo="RUN Paciente" valor="${runPaciente}" />
               	<div class='salto-de-linea'></div>
                <isl:combo cols="1-5" noSelection="${['0':'Seleccione...']}" nombre="prestador"  	 from="${pre}" 		rotulo="Prestador" 		 valor="${Cprestador}" optionValue="${{it.descripcion}}"  /> 
                <isl:combo cols="1-5" nombre="centroAtencion" from=""	rotulo="Centro Atencion" />                
                <isl:combo cols="1-5" noSelection="${['0':'Seleccione...']}" nombre="informeEstado"  from="${estado}" 	rotulo="Estado" 		 valor="${informeEstado}" optionValue="${{it.descripcion}}" />         
                <div style="height: 20px;"></div>           
                <isl:button tipo="volver" value="Filtrar" action="dp01"/>            
            </div>
        </div>
        
        <legend>Resultado</legend>
        <div class="pure-g-r">
            <div class="pure-u-1">
                <g:if test="${informesOPA?.size() > 0}">                   
                    
                    <table class="pure-table" width="100%">
                    <thead>
                        <tr>
                            <th>N° Siniestro</th>
                            <th>N° OPA</th>
                            <th>Prestador</th>
                            <th>RUN Paciente</th>
                            <th>Estado</th>
                           <th width="1%"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${informesOPA}" status="i" var="Informes">
                            <tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">        
                                <td align="center">
                                	${Informes.esEnfermedadProfesional? Informes.id+'-EP' : Informes.id+'-AT'}	
                                </td>               
                                <td align="center">
                                	${Informes?.opa? Informes?.opa?.id : Informes?.opaep?.id}	
                                </td>  
                                <td align="center">
                                	${Informes?.opa? (FormatosISLHelper.nombrePrestadorStatic(Informes?.opa?.centroAtencion?.prestador)) : (FormatosISLHelper.nombrePrestadorStatic(Informes?.opaep?.centroAtencion?.prestador))}	
                                </td>
                                <td align="center">
                                	${FormatosHelper.runFormatStatic(Informes.trabajador.run)}	
                                </td>
                                <td align="center">
                                	${Informes.estado? 'Ingresado' : 'Pendiente'}	
                                </td>    
                                <td nowrap="nowrap"> 
                               		<g:if test="${Informes.estado}"> 
	                                	<button title="Ver" class="pure-button pure-button-success" onclick="document.forms[0].siniestroId.value='${Informes?.id}';document.forms[0].action='dp02';document.forms[0].submit();">
	                                    <i class="icon-search"></i>
                                     	</button>
                                    </g:if>
                					<g:else>
	                                    <button title="Crear" class="pure-button pure-button-secondary" onclick="document.forms[0].siniestroId.value='${Informes?.id}';document.forms[0].action='dp03';document.forms[0].submit();">
		                                <i class="icon-wrench"></i>
	                                </button>
	                                </g:else>
	                            </td>                   
                            </tr>
                        </g:each>
                    </tbody>
                </table>
                    
                </g:if>
                <g:else>
                    <div>
                        <div>
                            <p> No hay información asociada </p>
                        </div>
                    </div>
                </g:else>
               
            </div>
        </div>
        
        <legend></legend>
    </fieldset> 
    <div class="pure-g-r">
        <g:link controller="nav"  action="index" >
             <input type="button" value="Volver" class="pure-button pure-button-secondary"/> 
        </g:link>
   </div>
</g:form>