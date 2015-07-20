<%@ page import="cl.adexus.isl.spm.TipoCuenta" %>

<isl:textInput	cols="8-8"

				
				deshabilitado="${edit}"
				requerido="true"
				nombre="codigo"
				rotulo="${message(code: 'tipoCuenta.codigo.label', default: 'Código')}"
				valor="${tipoCuentaInstance.codigo}" />
<div class='salto-de-linea'></div>

<isl:textInput	cols="8-8"
				
				nombre="descripcion"
				requerido="true"
				rotulo="${message(code: 'tipoCuenta.descripcion.label', default: 'Descripción')}"
				valor="${tipoCuentaInstance.descripcion}" />
<div class='salto-de-linea'></div>
