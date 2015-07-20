<%@page import="cl.adexus.isl.spm.Siniestro"%>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>
<g:javascript src="" />
<g:form name="dp02" class="pure-form pure-form-stacked" >

    <g:hiddenField name="backTo" value="${params?.action}"/>
    <g:hiddenField name="backToController" value="${params?.controller}"/>
    <g:hiddenField name="alta" value="${informeOpa?.altaMedica}"/>
    <g:hiddenField name="informeId"/>
    <g:hiddenField name="diagnosticoId"/>

    <fieldset>
        <legend>Datos OPA</legend>
        <div class="pure-g-r">
            <div class="pure-u-1">
                <!-- Info Informe -->
                <isl:textOutput cols="1-8" nombre="opa_asociada" rotulo="Nº OPA Asociada" valor="${informeOpa.siniestro.opa? informeOpa.siniestro.opa.id : informeOpa.siniestro.opaep.id}"/>
                <isl:textOutput cols="1-8" nombre="fecha_inicioVigencia" rotulo="Inicio Vigencia" valor="${FormatosHelper.fechaHoraStatic(informeOpa?.siniestro?.opa ? informeOpa?.siniestro?.opa?.inicioVigencia : informeOpa?.siniestro?.opaep?.inicioVigencia)}"/> 
                <isl:textOutput cols="1-8" nombre="prestador_medico" rotulo="Prestador Médico" valor="${prestadorOPA}"/>
                <isl:textOutput cols="2-8" nombre="centro_atencion" rotulo="Centro Atención" valor="${centroMedicoOPA}"/> 
                <isl:textOutput cols="1-8" nombre="run_paciente" rotulo="RUN Paciente" valor="${FormatosHelper.runFormatStatic(informeOpa.paciente.run)}"/>                
                <isl:textOutput cols="2-8" nombre="nombre_paciente" rotulo="Nombre Paciente" valor="${informeOpa.paciente.nombre+' '+informeOpa.paciente.apellidoPaterno+' '+informeOpa.paciente.apellidoMaterno}"/>          
            </div>
        </div>
        
        <legend>Datos Primera Atención</legend>
        <div class="pure-g-r">
            <div class="pure-u-1">
                <!-- Info Siniestro -->
                <isl:textInput cols="1-8" deshabilitado="true" nombre="fechaAtencion" ayuda="dd-mm-aaaa" rotulo="Fecha Atención"  valor="${FormatosHelper.fechaCortaStatic(informeOpa?.fechaAtencion)}"/>
                <isl:textInput cols="1-8" deshabilitado="true" nombre="horaAtencion"  ayuda="hh:mm"  tipo="hora" rotulo="Hora atención" valor="${FormatosHelper.horaCortaStatic(informeOpa?.fechaAtencion)}" />
                          
               	<isl:radiogroup cols="1-8" nombre="altaMedica" name="altaMedica"
								labels="['Sí', 'No']"
								values="[true,false]"
								rotulo="Alta Inmediata"
								deshabilitado="true"
								valor="${informeOpa?.altaMedica}">
					${it.label}
					${it.radio}
				</isl:radiogroup>
                
				<div id="DivProximo"> 
	                <isl:radiogroup cols="1-8" nombre="reposoLaboral" name="reposoLaboral"
									labels="['Sí', 'No']"
									values="[true,false]"
									rotulo="Reposo Laboral"
									deshabilitado="true"
									valor="${informeOpa?.reposoLaboral}">
						${it.label}
						${it.radio}
					</isl:radiogroup>				                
					<isl:textInput cols="1-8" deshabilitado="true" nombre="fechaProximoControl" ayuda="dd-mm-aaaa"rotulo="Fecha Próximo Control"  valor="${FormatosHelper.fechaCortaStatic(informeOpa?.fechaProximoControl)}"/>           
				</div>	
				
                <isl:textArea cols="8-8" deshabilitado="true" nombre="comentarioAtencion" rotulo="Comentario Atención" valor="${informeOpa?.comentarioAtencion}" />                               
            </div>
        </div>
        
         <legend>Datos del médico que emite la OPA</legend>
        <div class="pure-g-r">
            <div class="pure-u-1">
               <isl:textInput cols="2-8" deshabilitado="true" nombre="medicoRun" rotulo="RUN" valor="${FormatosHelper.runFormatStatic(informeOpa?.medico?.run)}"/>
               <isl:textInput  cols="2-8" deshabilitado="true" nombre="medicoNombre" rotulo="Nombre"  valor="${informeOpa?.medico?.nombre}" />
               <isl:textInput cols="2-8" deshabilitado="true" nombre="medicoPaterno" rotulo="Apellido Paterno" valor="${informeOpa?.medico?.apellidoPaterno}"/>
               <isl:textInput  cols="2-8" deshabilitado="true" nombre="medicoMaterno" rotulo="Apellido Materno"  valor="${informeOpa?.medico?.apellidoMaterno}" />
                                 
            </div>
        </div>
        
        <legend>Diagnósticos</legend>
          <div class="pure-g-r">
            <div class="pure-u-1">
                <g:if test="${diagnosticos?.size() > 0}">                   
                    <table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
                        <thead>
                            <tr>
                                <th width="20%">Diagnóstico</th>
                                <th width="20%">Parte</th>
                                <th width="20%">Lateralidad</th>
                                <th width="10%">CIE-10</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${diagnosticos?}" var="d">  
                                <tr>                        
                                    <td>${FormatosHelper.truncateStatic(d.diagnostico,50)}</td>
                                    <td>${d.parte.descripcion}</td>
                                    <td>${d.lateralidad.descripcion}</td>
                                    <td>${d.cie10?.codigo}</td>
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
        </div>
        
        <script type="text/javascript">
       	 	YUI().use("io", "json-parse", "node", "panel", function (Y) {
	        	Y.on("domready", function(){
	        		var alta = document.getElementById("alta").value;
	        		if (alta == "true"){
	        			Y.one("#DivProximo").setStyle("display", "none")	
	        		}else{
	        			Y.one("#DivProximo").setStyle("display", "");	
	        		}
	        	});
       	 	});	
		</script>
		
        <legend></legend>
    </fieldset> 
    <div class="pure-g-r">
         <button class="pure-button pure-button-secondary" onclick="document.forms[0].action='index';document.forms[0].submit();">Volver</button>
  </div>
</g:form>