<%@ page import="cl.adexus.isl.spm.TipoCentroSalud" %>

<isl:textInput	cols="8-8"
				requerido="true"
				deshabilitado="${edit}"
				nombre="codigo"
				rotulo="${message(code: 'tipoCentroSalud.codigo.label', default: 'Código')}"
				valor="${tipoCentroSaludInstance?.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				requerido="true"
				nombre="descripcion"
				rotulo="${message(code: 'tipoCentroSalud.descripcion.label', default: 'Descripción')}"
				valor="${tipoCentroSaludInstance?.descripcion}" />
<div class='salto-de-linea'></div>
