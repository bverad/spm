<%@ page import="cl.adexus.isl.spm.TipoConceptoReembolso" %>

<isl:textInput	cols="8-8"
				deshabilitado="${edit}"
				requerido="true"
				nombre="codigo"
				rotulo="${message(code: 'tipoConceptoReembolso.codigo.label', default: 'Código')}"
				valor="${tipoConceptoReembolsoInstance.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				nombre="descripcion"
				requerido="true"
				rotulo="${message(code: 'tipoConceptoReembolso.descripcion.label', default: 'Descripción')}"
				valor="${tipoConceptoReembolsoInstance.descripcion}" />
<div class='salto-de-linea'></div>
