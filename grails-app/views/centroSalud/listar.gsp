<%@ page import="cl.adexus.helpers.FormatosHelper" %>

<script type="text/javascript">
	YUI().use("node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page
	
		
	    var buscarComunasPorRegion = function(e) {
	    	var regionId=Y.one("#region").get('value');
	    	Y.io('traerComunasPorRegionJSON?&regionId='+regionId, {
	    	    on : {
	    	        success : function (tx, r) {
	    	        	llenarCentroAtencion(r.responseText);
	    	        }
	    	    }
	    	});
	    }
	    
	    var llenarCentroAtencion= function(jsonString){
	    	var comunaSelect=Y.one("#comuna");
	    	var oldValue = comunaSelect.get("value");
	    	comunaSelect.get('childNodes').remove();
	    	
	    	//alert(jsonString)
	    	var data = Y.JSON.parse(jsonString);
	    	for (var i = 0; i <=data.length - 1; i++) {
	    	    var cs = data[i];
	    	    var n=cs.descripcion;
	    	    //alert(p.name)
	    	    if (i == 0) {
	    	    	comunaSelect.appendChild( Y.Node.create('<option value="">Seleccione...</option>'));
		    	}
	    	    
	    	    var oStr='<option value="'+cs.codigo+'">'+n+'</option>';
	    	    comunaSelect.appendChild( Y.Node.create(oStr));
	    	}
	    	if(data.length>0){
	    		comunaSelect.set("disabled",false);
	    		comunaSelect.set("value", oldValue);
	    	}else{
	    		comunaSelect.set("disabled",true);
	    	}
		    data=null
	    }

	    Y.one("#comuna").set("disabled",true);
	    Y.one("#region").on("change", buscarComunasPorRegion);
		Y.on("available", buscarComunasPorRegion, "#region");
	    
	});
</script>

		<g:form action="index" class="pure-form pure-form-stacked">
			<g:hiddenField name="id"/>
			
			<fieldset>
			<legend>Listar Centros de Salud</legend>
			
			<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Región" nombre="region" from="${listaRegiones}" valor="${region}"/>
			<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}"  rotulo="Comuna" nombre="comuna" from="${listaComunas}" valor="${comuna}"/>
			<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}" rotulo="Estado Centro" nombre="estadoCentro" from="${listaEstadosCs}" valor="${estadoCentroSeleccionado}"/>
			<isl:combo cols="2-8" noSelection="${['':'Seleccione ...']}"  rotulo="Tipo Centro" nombre="tipoCentro" from="${listaTipoCentroSalud}" valor="${tipoCentro}"/>
			
			<div class="salto-de-linea"></div>
			
			<isl:button tipo="volver" value="Filtrar" action="listar"/>
			
			</fieldset>
		
			<fieldset>
			<div id="list-prestador" class="content scaffold-list" role="main">
				<legend>Centros</legend>

				<table class="pure-table" width="100%">
					<thead>
						<tr>
							<th>Centro de Salud</th>
							<th>Región</th>
							<th>Comuna</th>
							<th>Estado Centro</th>
							<th>Tipo Centro</th>
							<th nowrap="nowrap">&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${listaCentros}" status="i" var="cs">
					<tr  class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
						<td>${cs?.nombre ? cs?.nombre : ''}</td>
						<td>${cs?.comuna?.provincia?.region ? cs?.comuna?.provincia?.region?.descripcion : ''}</td>
						<td>${cs?.comuna ? cs?.comuna?.descripcion : ''}</td>
						<td>${cs?.esActivo ? 'Activo' : 'Inactivo'}</td>
						<td>${cs?.tipoCentroSalud ? cs?.tipoCentroSalud?.descripcion : ''}</td>
						<td>
							<!-- 
							<button title="Editar" class="pure-button pure-button-success" onclick="document.forms[0].id.value='${cs?.id}';document.forms[0].action='edit';document.forms[0].submit();">
								<i class="icon-wrench"></i>
							</button>
							-->
							<button title="Ver" class="pure-button pure-button-secondary" onclick="document.forms[0].id.value='${cs?.id}';document.forms[0].action='ver';document.forms[0].submit();">
								<i class="icon-info-sign"></i>
							</button>
						</td>
					</tr>
					</g:each>	
					</tbody>
				</table>	
				<div class="salto-de-linea"></div>
				<!--  
				<isl:button tipo="cancelar" value="Imprimir Listado" action="imprimir"/>
				-->		
			</div>
			</fieldset>
		</g:form>
