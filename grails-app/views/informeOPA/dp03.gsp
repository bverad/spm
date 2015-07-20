<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="InformeOPA/dp03.js" />

<g:form name="dp03" class="pure-form pure-form-stacked" >
    
    <g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
    <g:hiddenField name="alta" value="${informeOpa?.altaMedica}"/>
    <g:hiddenField name="backTo" value="dp03"/>
	<g:hiddenField name="backToController" value="${params.controller}"/>	
    <g:hiddenField name="numeroDiagnostico" />

    <fieldset>
        <legend>Datos OPA</legend>
        <div class="pure-g-r">
            <div class="pure-u-1">
                <!-- Info Siniestro -->
                <isl:textOutput cols="1-8" nombre="opa_asociada" rotulo="Nº OPA Asociada" valor="${siniestro.opa ? siniestro.opa.id : siniestro.opaep.id}"/>
                <isl:textOutput cols="1-8" nombre="fecha_inicioVigencia" rotulo="Inicio Vigencia" valor="${FormatosHelper.fechaHoraStatic(siniestro?.opa ? siniestro?.opa?.inicioVigencia : siniestro?.opaep?.inicioVigencia)}"/>   
                <isl:textOutput cols="1-8" nombre="prestador_medico" rotulo="Prestador Medico" valor="${prestadorOPA}"/>
                <isl:textOutput cols="2-8" nombre="centro_atencion" rotulo="Centro Atencion" valor="${centroMedicoOPA}"/>  
                <isl:textOutput cols="1-8" nombre="run_paciente" rotulo="RUN Paciente" valor="${FormatosHelper.runFormatStatic(siniestro.trabajador.run)}"/>                   
                <isl:textOutput cols="2-8" nombre="nombre_paciente" rotulo="Nombre Paciente" valor="${siniestro.trabajador.nombre+' '+siniestro.trabajador.apellidoPaterno+' '+siniestro.trabajador.apellidoMaterno}"/>
                                          
            </div>
        </div>

        <legend>Datos Primera Atención</legend>
        <div class="pure-g-r">
            <div class="pure-u-1">
                <!-- Info Siniestro -->
                <isl:calendar cols="1-8" requerido="true" nombre="fechaAtencion" ayuda="dd-mm-aaaa" rotulo="Fecha Atención" valor="${informeOpa?.fechaAtencion}"/>
                <isl:textInput cols="1-8" requerido="true" nombre="horaAtencion"  ayuda="hh:mm"  tipo="hora" rotulo="Hora atención" valor="${FormatosHelper.horaCortaStatic(informeOpa?.fechaAtencion)}" />
                          
               	<isl:radiogroup cols="1-8" nombre="altaMedica" name="altaMedica" requerido="true" 
								labels="['Sí', 'No']"
								values="[true,false]"
								rotulo="Alta Inmediata"
								valor="${informeOpa? informeOpa.altaMedica : null }">
					${it.label}
					${it.radio}
				</isl:radiogroup>
				
           		<div id="DivProximo"> 
	                <isl:radiogroup cols="1-8" nombre="reposoLaboral" name="reposoLaboral"
									labels="['Sí', 'No']"
									values="[true,false]"
									rotulo="Reposo Laboral"
									valor="${informeOpa?.reposoLaboral}">
						${it.label}
						${it.radio}
					</isl:radiogroup>				                
					<isl:calendar cols="1-8" nombre="fechaProximoControl" ayuda="dd-mm-aaaa" rotulo="Fecha Próximo Control" valor="${informeOpa?.fechaProximoControl}"/>           
				</div>	
				   
		        <isl:textArea cols="8-8" requerido="true" nombre="comentarioAtencion" rotulo="Comentario Atención" valor="${informeOpa?.comentarioAtencion}" />                             
            </div>
        </div>
        
         <legend>Datos del medico que emite la OPA</legend>
        <div class="pure-g-r">
            <div class="pure-u-1">
               <isl:textInput cols="2-8" requerido="true" nombre="medicoRun" rotulo="RUN" valor="${informeOpa?.medico?.run}"/>
               <isl:textInput  cols="2-8" requerido="true" nombre="medicoNombre" rotulo="Nombre"  valor="${informeOpa?.medico?.nombre}" />
               <isl:textInput cols="2-8" requerido="true" nombre="medicoPaterno" rotulo="Apellido Paterno" valor="${informeOpa?.medico?.apellidoPaterno}"/>
               <isl:textInput  cols="2-8" requerido="true" nombre="medicoMaterno" rotulo="Apellido Materno"  valor="${informeOpa?.medico?.apellidoMaterno}" />                              
            </div>
        </div>
        
        <legend>Diagnosticos</legend>
          <div class="pure-g-r">
            <div class="pure-u-1">
                <g:if test="${diagnosticos?.size() > 0}">                   
                    <table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
                        <thead>
                            <tr>
                            	<th width="5%">Laboral</th>
                                <th width="40%">Diagnóstico</th>
                                <th width="20%">Parte</th>
                                <th width="20%">Lateralidad</th>
                                <th width="10%">CIE-10</th>
                                <th width="1%" nowrap="nowrap">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${diagnosticos}" var="d" status="i">  
                                <tr>      
									<td>${d.esLaboral ? 'Sí' : 'No'}</td>                 
                                    <td>${FormatosHelper.truncateStatic(d.diagnostico,50)}</td>
                                    <td>${d.parte.descripcion}</td>
                                    <td>${d.lateralidad.descripcion}</td>
                                    <td>${d.cie10?.codigo}</td>
                                    <td nowrap="nowrap">
                                    	<g:if test="${d?.origen?.codigo == '2'}">
                                        	<button class="pure-button pure-button-success" onclick="document.forms[0].numeroDiagnostico.value='${i}';document.forms[0].action='dp05';document.forms[0].submit();"><i class="icon-wrench"></i></button>                           
                                   		</g:if>
                                    </td>   
                                </tr>
                            </g:each>
                        </tbody>
                    </table>                    
                </g:if>
                <g:else>
                    <div>
                        <div>
                            <p> No hay Diagnósticos asociados a este Siniestro </p>
                        </div>
                    </div>
                </g:else>
            </div>
            <button class="pure-button button-tool" formnovalidate="formnovalidate" onclick="document.forms[0].siniestroId.value='${siniestro.id}';document.forms[0].action='dp04';document.forms[0].submit();">
                <i class="icon-plus"></i> Agregar diagnóstico
            </button>
        </div>
        
        <script type="text/javascript">
			YUI().use('node', function (Y) {
				
	        	Y.on("domready", function(){
	        		var alta = document.getElementById("alta").value;
	        		if (alta == "true"){
	        			Y.one("#DivProximo").setStyle("display", "none")	
	        		}else{
	        			Y.one("#DivProximo").setStyle("display", "");	
	        		}
	        	});
	        	
				var labels = document.getElementsByTagName('LABEL');			    		
				Y.one('#altaMedica_0').delegate('click', function (e) {						
						document.getElementById("fechaProximoControl").required = true;
						Y.one("#reposoLaboral_1").set("checked",true);
						Y.one("#DivProximo").setStyle("display", "none")
				}, 'input[type=radio]');
				/*
				Y.one('#altaMedica_1').delegate('click', function (e) {
					if (e.target.get('value')== '0'){						
						document.getElementById("fechaProximoControl").required = true;
						Y.one("#DivProximo").setStyle("display", "")
					}
				}, 'input[type=radio]');
				*/					  	
			});
		</script>
		
        <legend></legend>
    </fieldset> 
    <div class="pure-g-r">
        <g:actionSubmit id="volver" value="Volver"  action="index" class="pure-button pure-button-secondary" formnovalidate="formnovalidate"  />
        <g:actionSubmit id="guardar" value="Guardar"  action="postDp03" class="pure-button pure-button-success"  />
   </div>
</g:form>