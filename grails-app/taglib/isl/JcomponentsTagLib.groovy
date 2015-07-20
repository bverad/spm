package isl

class JcomponentsTagLib {
	
	static namespace = "jisl"
	
	
	/**
	 * Permite mostrar atributosde una denuncia y editarlas en un popUp
	 * 		El popUp recibe datos de un servicio
	 * 
	 * @depende de librarías JS para funcionar !!!!
	 *
	 * @attr nombre REQUIRED Es obligatorio y debiera ser el id.
	 * @attr cols REQUIRED puede ser un 1-10 o 1-8.
	 * @attr tipo por defecto se toma text y puede ser text|email|number|....
	 * @attr valor del campo
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr ayuda toma el atributo placeholder de HTML y son tips de llenado del campo.
	 * 
	 * @attr requerido por defecto se toma como falso.
	 * @attr deshabilitado por defecto se toma falso. 
	 * @attr noEditable por defecto se toma como falso. Esta propiedad impide que se abra la ventana de edición
	 */
	def campoDenunciaOLD = {attrs ->
		def writer = out
		if (attrs.nombre == null) throwTagError("Tag [textInput] requiere el attribute [nombre]")
		if (attrs.nombre.find(" ")) throwTagError("Tag [textInput] requiere que el attribute [nombre]")
		if (attrs.cols == null) throwTagError("Tag [textInput] requiere el attribute [cols]")
		
		def cols = attrs.cols
		def nombre = attrs.nombre
		def tipo = attrs.tipo
		def valor = attrs.valor
		def rotulo = attrs.rotulo
		def ayuda = attrs.ayuda
		def requerido = attrs.requerido
		def deshabilitado = attrs.deshabilitado
		def noEditable = attrs.noEditable
		
		if (tipo == null) {
			tipo = 'text'
		}
		
		writer << "<div class='label-contenedor-campoDenuncia pure-input-${cols}'>"
		writer << "<label for='${nombre}'>${rotulo}:</label>"
		writer << "<span  tipo='${tipo}' deParteDe='${nombre}' glosa='${rotulo}' id='label_${nombre}' ayuda='${ayuda}' "
		if (requerido){
			writer << " required='required'  "
		}
		if (noEditable){ 
			writer << " class='span-inline btn-show-no'>"
		} else {
			writer << " class='span-inline btn-show'>"
		}
		
		
		writer << "${valor}</span>"
		writer << "<input id='${nombre}' name='${nombre}' type='hidden' value='${valor}' /> "		
		//writer << "<button deParteDe='${nombre}' glosa='${rotulo}' onclick='return false;' class='yui3-button btn-show' tipo='${tipo}'><i class='icon-edit-sign'></i></button>"
		writer << "</div>"
	}
	
	

	
	
	
	
	
	def campoDenuncia = {attrs ->
		def writer = out
		if (attrs.nombre == null) throwTagError("Tag [textInput] requiere el attribute [nombre]")
		if (attrs.nombre.find(" ")) throwTagError("Tag [textInput] requiere que el attribute [nombre]")
		if (attrs.cols == null) throwTagError("Tag [textInput] requiere el attribute [cols]")
		
		def cols = attrs.cols
		def nombre = attrs.nombre
		def tipo = attrs.tipo
		def valor = attrs.valor
		def rotulo = attrs.rotulo
		def ayuda = attrs.ayuda
		def requerido = attrs.requerido
		def deshabilitado = attrs.deshabilitado
		def noEditable = attrs.noEditable
		
		if (tipo == null) {
			tipo = 'text'
		}
		writer << "<div class='label-contenedor-campoDenuncia pure-input-${cols}'>"
		writer << "<label for='${nombre}'>${rotulo}:</label>"
		writer << isl.textInput(attrs)
		//writer << "<span  tipo='${tipo}' deParteDe='${nombre}' glosa='${rotulo}' id='label_${nombre}' ayuda='${ayuda}' "
		//if (requerido){
		//	writer << " required='required'  "
		//}
		//if (noEditable){
		//	writer << " class='span-inline btn-show-no'>"
		//} else {
		//	writer << " class='span-inline btn-show'>"
		//}
		//writer << "${valor}</span>"
		//writer << "<input id='${nombre}' name='${nombre}' type='hidden' value='${valor}' /> "
		//writer << "<button deParteDe='${nombre}' glosa='${rotulo}' onclick='return false;' class='yui3-button btn-show' tipo='${tipo}'><i class='icon-edit-sign'></i></button>"
		writer << "</div>"
	}
	
	
	
		

}
