<%@ page import="cl.adexus.isl.spm.EstructuraJuridica" %>

<isl:textInput	cols="8-8"
				
				deshabilitado="${edit}"
				requerido="true"
				nombre="codigo"
				rotulo="${message(code: 'estructuraJuridica.codigo.label', default: 'Código')}"
				valor="${estructuraJuridicaInstance.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				
				nombre="descripcion"
				requerido="true"
				rotulo="${message(code: 'estructuraJuridica.descripcion.label', default: 'Descripción')}"
				valor="${estructuraJuridicaInstance.descripcion}" />
<div class='salto-de-linea'></div>
