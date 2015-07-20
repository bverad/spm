<%@page import="cl.adexus.isl.spm.Siniestro"%>
<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<g:javascript src="CalOrigenEP/dp01.js" />
<g:javascript src="CalOrigenEP/CalOrigenDrop.js" />
<g:javascript src="CalOrigenEP/CodAgenteDrop.js" />

<g:form name="dp01" class="pure-form pure-form-stacked" >

    <g:hiddenField name="siniestroId" value="${siniestro?.id}"/>
    <g:hiddenField name="taskId" value="${params.taskId}"/>
    <g:hiddenField name="tipoEventoOriginal" value="${siniestro.esEnfermedadProfesional ? 3 : (siniestro.diepOA?.esAccidenteTrayecto ? 2 : 1)}"/>
    <g:hiddenField name="calificacionId" value="${reca?.calificacion?.codigo}"/>
    <g:hiddenField name="tipoEventoId" value="${reca?.eventoSiniestro}"/>
    <g:hiddenField name="backTo" value="dp01"/>
    <g:hiddenField name="backToController" value="${params?.controller}"/>
    <g:hiddenField name="antecedenteId" value="" />
    <g:hiddenField name="diagnosticoId"/>
    <g:hiddenField name="docId"/>
    <g:hiddenField name="recaOrigen" value="calOrigenEP"/>
    <g:hiddenField name="antecedentesOrigen" value="dp01"/>
    
	
	<fieldset>	
 		<legend>Datos Siniestro</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<!-- Info Siniestro -->
                <isl:textOutput cols="2-8" requerido="true" deshabilitado="true" rotulo="N° Siniestro" valor="${siniestro?.id}" />
				<isl:textInput  cols="3-8" requerido="true" deshabilitado="true" nombre="tipo_siniestro" rotulo="Tipo Siniestro" valor="${siniestro.esEnfermedadProfesional ? 'Enfermedad Profesional' : (siniestro.diatOA?.esAccidenteTrayecto ? 'Trayecto' : 'Trabajo')}"/>
				<isl:textInput cols="3-8" requerido="true" deshabilitado="true" nombre="region_diatOA" rotulo="Región denuncia"  valor="${region['codigo']+'. '+region['descripcion']}" />				
				<div class="salto-de-linea"></div>
				<!-- Info Trab/prest -->
				<isl:textInput cols="2-8" requerido="true" deshabilitado="true" nombre="run_trabajador" rotulo="RUN Trabajador" valor="${FormatosHelper.runFormatStatic(siniestro.trabajador?.run)}"/>
				<isl:textInput cols="4-8" requerido="true" deshabilitado="true" nombre="prestador" rotulo="Prestador" valor="${prestador}"/>
				<isl:textInput  cols="1-8" requerido="true" deshabilitado="true" nombre="fecha_siniestro" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="Fecha Siniestro"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.fecha)}" />
				<isl:textInput cols="1-8" requerido="true" deshabilitado="true" nombre="dias_restantes" rotulo="Días Restantes" valor="${diasR}"/>			
			</div>
		</div>
		<legend>DIEP-OA</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
			
			<!-- Preguntas -->
			<isl:textInput cols="4-8" requerido="true" nombre="pregunta1" 
						   rotulo="Describa las molestias o sintomas que actualente tiene el trabajador/a" 
						   valor="${siniestro?.diepOA?.sintoma}"
						   deshabilitado="true"/>
						   
			<isl:textInput  cols="4-8" deshabilitado="true" nombre="pregunta2" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="¿Aproximadamente en que fecha comenzaron las molestias?"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.diepOA?.fechaSintoma)}" />
             
			 <!-- revisar -->              
			<isl:radiogroup cols="4-8" nombre="m_trabajo_a" name="m_trabajo_a"
									requerido="true"
									labels="['Sí', 'No']"
									values="[true, false]"
									valor="${siniestro?.diepOA?.esAntecedentePrevio}"
									rotulo="¿Había tenido estas molestias en el puesto de trabajo actual, anteriormente?"
									deshabilitado="true">
						${it.label}
						${it.radio}
			</isl:radiogroup>	
			
			              
			
			<isl:textInput cols="4-8" requerido="true" nombre="pregunta4" 
			               rotulo="Parte del cuerpo afectada" 
			               valor="${siniestro?.diepOA?.parteCuerpo}"
			               deshabilitado="true"/>
			               
			<isl:textInput cols="4-8" requerido="true" nombre="pregunta5"
						  rotulo="Describa el trabajo o actividad que realizaba cuando comenzaron las molestias"
						  valor="${siniestro?.diepOA?.descripcionTrabajo}"
						  deshabilitado="true"/> 
						  
			<isl:textInput cols="4-8" requerido="true" nombre="pregunta6"
						  rotulo="Nombre del Puesto de Trabajo o Actividad que realizaba cuando comenzaron las molestias"
						  valor="${siniestro?.diepOA?.puestoTrabajo}"
						  deshabilitado="true"/> 

			<isl:radiogroup cols="4-8" nombre="c_trabajo_m" name="c_trabajo_m"
									requerido="true"
									labels="['Sí', 'No']"
									values="[true, false]"
									valor="${siniestro?.diepOA?.esAntecedenteCompanero}"
									rotulo="¿Existen compañeros de trabajo con las molestias?"
									deshabilitado="true">
						${it.label}
						${it.radio}
			</isl:radiogroup>
									  
			<isl:textInput cols="4-8" requerido="true" nombre="pregunta8"
						  rotulo="Que cosas o agentes del trabajo cree Ud. que le causan estas molestias?"
						  valor="${siniestro?.diepOA?.agenteSospechoso}"
						  deshabilitado="true"/>
						  
			<isl:textInput  cols="4-8" deshabilitado="true" nombre="pregunta9" ayuda="dd-mm-aaaa" mostrarCal="false" rotulo="¿Aproximadamente desde que fecha ha estado expuesto los agentes que causan la molestia?"  valor="${FormatosHelper.fechaCortaStatic(siniestro?.diepOA?.fechaAgente)}" />
            <!-- Clasificación y mas -->
				
			</div>
		</div>
				
		<legend>Archivos Adjuntos</legend>
		<div class="pure-g-r">
            <div class="pure-u-1">
                <g:if test="${docsDIEP?.size() > 0}">               
                    <table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
                        <thead>
                            <tr>
                                <th width="90%" >Descripción de los Archivos Adjuntos</th>
                                <th width="1%" nowrap="nowrap">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${docsDIEP}" var="doc">
                                <tr>                        
                                    <td>${doc.descripcion}</td>
                                    <td nowrap="nowrap">
                                        <button class="pure-button pure-button-secondary" onclick="document.forms[0].docId.value='${doc.id}';document.forms[0].action='../calOrigenEP/viewDoc';document.forms[0].submit();"><i class="icon-info-sign"></i></button>
                                    </td>   
                                </tr>
                            </g:each>
                        </tbody>
                    </table>                    
                </g:if>
                <g:else>
                    <div>
                        <div>
                            <p>No hay archivos adjuntos </p>
                        </div>
                    </div>
                </g:else>
            </div>
        </div>
		
		<legend>Diagnosticos</legend>
		  <div class="pure-g-r">
            <div class="pure-u-1">
                <g:if test="${diagnosticos?.size() > 0}">                   
                    <table class="pure-table pure-table-bordered pure-table-striped"  width="100%">
                        <thead>
							<tr>
								<th width="10%">Laboral</th>
								<th width="30%">Diagnóstico</th>
								<th width="25%">Parte</th>
								<th width="15%">Lateralidad</th>
								<th width="10%">CIE-10</th>
								<th width="1%" nowrap="nowrap">&nbsp;</th>
							</tr>
                        </thead>
                        <tbody>
                            <g:each in="${diagnosticos?}" var="d">  
								<tr>						
									<td>${d.esLaboral ? 'Sí' : 'No'}</td>								   	
								   	<td>${FormatosHelper.truncateStatic(d.diagnostico,50)}</td>
								   	<td>${d.parte.descripcion}</td>
								   	<td>${d.lateralidad.descripcion}</td>
								   	<td>${d.cie10.codigo}</td>
								   	<td nowrap="nowrap">
										<button class="pure-button pure-button-success" onclick="document.forms[0].diagnosticoId.value='${d?.id}';document.forms[0].action='../calOrigenEP/dp05';document.forms[0].submit();"><i class="icon-wrench"></i></button>
										<!-- <button class="pure-button pure-button-error" onclick="document.forms[0].diagnosticoId.value='${d?.id}';document.forms[0].action='delete_cs';document.forms[0].submit();"><i class="icon-minus-sign"></i></button> -->							
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
            <button class="pure-button button-tool" onclick="document.forms[0].taskId.value='${taskId}'; document.forms[0].action='../calOrigenEP/dp04';document.forms[0].submit();">
                <i class="icon-plus"></i> Agregar Diagnóstico
        </button>
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
	                            <th width="1%" nowrap="nowrap">Revisar</th>
	                        </tr>
	                  	</thead>
	                   	<tbody>
	                    	<g:each in="${anteAdicionales}" var="a">
	                        	<tr>          
	                        		<td>${a.tipoAntecedente.descripcion}</td>
	                    		   	<td>${FormatosHelper.fechaCortaStatic(a.fechaSolicitud)}</td>
	                   			  	<td>${FormatosHelper.truncateStatic(a.solicitud,100)}</td>          
	                 		   		<td>${a.estado? 'Entregado' : 'Pendiente'}</td>
	                 		   		<td nowrap="nowrap">  
                                    	<button class="pure-button pure-button-secondary" onclick="document.forms[0].antecedenteId.value='${a?.id}'; document.forms[0].action='../calOrigenEP/to_solicitud';document.forms[0].submit();"><i class="icon-search"></i></button> 
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
			<button class="pure-button button-tool" onclick="document.forms[0].action='../calOrigenEP/SolicitarAntecedentes';document.forms[0].submit();">
	    		<i class="icon-plus"></i> Solicitar Más Antecedentes
			</button>
		</div>		
			
		<legend>Calificación</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="origen" from="${origenSiniestro}" rotulo="Origen" valor="${reca?.calificacion?.origen?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="tipoEvento" from="${eventoS}" rotulo="Tipo de Evento" valor="${reca?.calificacion?.eventoSiniestro?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="alim" from="${altaInmediata}" rotulo="Alta Inmediata" valor="${reca?.calificacion?.altaInmediata}" />			
				<isl:combo cols="2-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="califica" from="${calificacion}" rotulo="Calificación" valor="${reca?.calificacion?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
			</div>
		</div>
		
		<div class="pure-g-r" id="DivIndicacion">	
			<div class="pure-u-1">
				<isl:textArea cols="8-8" nombre="indicacion" rotulo="Indicación" valor="${reca?.indicacion}"/>
			</div>
		</div>
		
		<div class="pure-g-r" id="DivClasificacion">	
			<div class="pure-u-1">
				<legend>Codificación de Causa del Accidente</legend>
				<isl:combo cols="2-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_forma" from="${forma}" rotulo="Forma" valor="${reca?.forma?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="2-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_agente" from="${agente}" rotulo="Agente Accidente" valor="${reca?.agenteAccidente?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="2-8" requerido="true" nombre="grupoIntencionalidad" from="${grupoIntencionalidad}" rotulo="Grupo intencionalidad" valor="${reca?.intencionalidad?.grupo?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="2-8" requerido="true" nombre="cod_intencion" from="${intencion}" rotulo="Intencionalidad" valor="${reca?.intencionalidad?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				
				<!--<isl:combo cols="2-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_modoTransporte" from="${modoT}" rotulo="Modo de transporte" valor="${reca?.transporte?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-3" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_papelLesionado" from="${papelLes}" rotulo="Papel lesionado" valor="${reca?.lesionado?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />		
				<isl:combo cols="1-3" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_contraparte" from="${contraparte}" rotulo="Contraparte" valor="${reca?.contraparte?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />	
				<isl:combo cols="1-3" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_tipoEvento" from="${tipoEvento}" rotulo="Tipo Evento" valor="${reca?.evento?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />-->		
			</div>
		</div>
		
		<div class="pure-g-r" id="DivAgente">	
			<div class="pure-u-1">
				<legend>Codificación de Agente</legend>
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_agente_1" from="${agenteCombo1}" valor="${cod1?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_agente_2" from="${cod2}" valor="${cod2?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_agente_3" from="${cod3}" valor="${cod3?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_agente_4" from="${cod4}" valor="${cod4?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />
				<isl:combo cols="1-5" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_agente_5" from="${cod5}" valor="${cod5?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />		
				<isl:combo cols="8-8" requerido="true" noSelection="${['0':'Seleccione...']}" nombre="cod_agente_6" from="${cod6}" valor="${cod6?.codigo}" optionValue="${{it.codigo +' - '+it.descripcion}}" />		
			</div>
		</div>
        
	<legend></legend>
	</fieldset>	
	<div class="pure-g-r">
		<g:actionSubmit id="posponer_siniestro" value="Posponer"  action="cu01p" class="pure-button pure-button-warning" formnovalidate="formnovalidate" />
		<g:actionSubmit id="califica_siniestro" value="Calificar"  action="cu01c" class="pure-button pure-button-success"  />
	</div>
</g:form>