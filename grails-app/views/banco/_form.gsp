<%@ page import="cl.adexus.isl.spm.Banco" %>
<isl:textInput	cols="8-8"
				
				deshabilitado="${edit}"
				nombre="codigo"
				requerido="true"
				rotulo="${message(code: 'banco.codigo.label', default: 'Código')}"
				valor="${bancoInstance.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				
				nombre="descripcion"
				requerido="true"
				rotulo="${message(code: 'banco.descripcion.label', default: 'Descripción')}"
				valor="${bancoInstance.descripcion}" />
<div class='salto-de-linea'></div>
