<%@ page import="cl.adexus.isl.spm.TipoPrestador" %>

<isl:textInput	cols="8-8"
				
				deshabilitado="${edit}"
				requerido="true"
				nombre="codigo"
				rotulo="${message(code: 'tipoPrestador.codigo.label', default: 'Código')}"
				valor="${tipoPrestadorInstance.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				
				nombre="descripcion"
				requerido="true"
				rotulo="${message(code: 'tipoPrestador.descripcion.label', default: 'Descripción')}"
				valor="${tipoPrestadorInstance.descripcion}" />
<div class='salto-de-linea'></div>
