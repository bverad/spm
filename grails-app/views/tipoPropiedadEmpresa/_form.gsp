<%@ page import="cl.adexus.isl.spm.TipoPropiedadEmpresa" %>

<isl:textInput	cols="8-8"
				
				deshabilitado="${edit}"
				requerido="true"
				nombre="codigo"
				rotulo="${message(code: 'tipoPropiedadEmpresa.codigo.label', default: 'Código')}"
				valor="${tipoPropiedadEmpresaInstance.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				
				nombre="descripcion"
				requerido="true"
				rotulo="${message(code: 'tipoPropiedadEmpresa.descripcion.label', default: 'Descripción')}"
				valor="${tipoPropiedadEmpresaInstance.descripcion}" />
<div class='salto-de-linea'></div>
