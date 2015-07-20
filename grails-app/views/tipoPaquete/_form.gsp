<%@ page import="cl.adexus.isl.spm.TipoPaquete" %>

<isl:textInput	cols="8-8"
				
				deshabilitado="${edit}"
				requerido="true"
				nombre="codigo"
				rotulo="${message(code: 'tipoPaquete.codigo.label', default: 'Código')}"
				valor="${tipoPaqueteInstance.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				
				nombre="descripcion"
				requerido="true"
				rotulo="${message(code: 'tipoPaquete.descripcion.label', default: 'Descripción')}"
				valor="${tipoPaqueteInstance.descripcion}" />
<div class='salto-de-linea'></div>
