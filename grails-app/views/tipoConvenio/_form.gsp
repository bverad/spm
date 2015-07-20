<%@ page import="cl.adexus.isl.spm.TipoConvenio" %>

<isl:textInput	cols="8-8"
				
				deshabilitado="${edit}"
				requerido="true"
				nombre="codigo"
				rotulo="${message(code: 'tipoConvenio.codigo.label', default: 'Código')}"
				valor="${tipoConvenioInstance.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				
				nombre="descripcion"
				requerido="true"
				rotulo="${message(code: 'tipoConvenio.descripcion.label', default: 'Descripción')}"
				valor="${tipoConvenioInstance.descripcion}" />
<div class='salto-de-linea'></div>
