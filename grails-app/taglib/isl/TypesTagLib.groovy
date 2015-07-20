package isl

import org.h2.command.dml.Explain;

import de.andreasschmitt.richui.taglib.renderer.*
import cl.adexus.helpers.FormatosHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper

class TypesTagLib {
	
	static namespace = "isl"

	Renderer dateChooserRenderer
	FormatosHelper fh
	
	/**
	 * Este tag es para los textInput
	 *
	 * @attr nombre REQUIRED Es obligatorio y debiera ser el id.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-5.
	 * @attr tipo por defecto se toma text y puede ser text|email|fecha|hora
	 * @attr valor valor del campo
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr ayuda toma el atributo placeholder de HTML y son tips de llenado del campo.
	 * @attr requerido por defecto se toma como falso.
	 * @attr deshabilitado por defecto se toma como falso.
	 * 
	 * @attr btnEdicion muestra boton de ediciï¿½n in-line | se necsita JS
	 */
	def textInput = {attrs ->
		def writer = out
		if (attrs.nombre == null) throwTagError("Tag [textInput] requiere el attribute [nombre] y usted en un acto deliberado no lo  hizo !!!! [1 horas de infierno]")
		if (attrs.nombre.find(" ")) throwTagError("Tag [textInput] requiere que el attribute [nombre] no tenga espacios !!!! [200 horas de infierno]")
		if (attrs.cols == null) throwTagError("Tag [textInput] requiere el attribute [cols] y usted en un acto deliberado no lo  hizo !!!! [10 horas de infierno]")
		
		//def locale = RequestContextUtils.getLocale(request)
		def cols = attrs.cols
		def nombre = attrs.nombre
		def tipo = attrs.tipo
		def valor = attrs.valor
		def rotulo = attrs.rotulo
		def ayuda = attrs.ayuda
		def requerido = attrs.requerido
		def deshabilitado = attrs.deshabilitado
		def maxlength = attrs.maxLargo
		def pattern = ""
		def margen = "-30px;"
		def strRequerido = ""
		def titulo = ""
		// Identificador fecha para boton edicion
		def btnTipo = ""
		
		if (!tipo) tipo = 'text' 
		
		if (requerido) strRequerido = "<font color='red'>*</font>"
		
		if (tipo.equalsIgnoreCase("fecha")){
			//ayuda = 'DD-MM-AAAA'
			tipo = 'text' 
			btnTipo = 'fecha'
			titulo = "El formato es correcto es DD-MM-AAAA."
			pattern = '(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}'
		}
		
		if (tipo.equalsIgnoreCase("hora")){
			tipo = 'time'
			ayuda = 'HH:MM'
			pattern = '(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9])'
		}

		if (tipo.equalsIgnoreCase("numero")){
			tipo = 'text' //tipo = 'number'
			//margen = "-48px;"
			pattern = "[0-9]+"
		}
		
		if (tipo.equalsIgnoreCase("rut")){
			tipo = "text"
			pattern = "0*(\\d{1,3}(\\.?\\d{3})*)\\-?([\\dkK])"
		}

		writer << "<div class='label-contenedor pure-input-${cols}' style='${attrs.style}'>"
		
		if (rotulo?.trim()) {
			writer << "<label for='${nombre}'>${rotulo}${strRequerido}:</label>"
		}
		writer << "<input id='${nombre}' name='${nombre}' type='${tipo}'"
		
		if (maxlength)
			writer << " maxlength='${maxlength}'"
		else
			writer << " maxlength='255'"
				
		if (requerido) {
			writer << " required='required'   "
			//rotulo = rotulo + "<font color='red'>*</font>"
		}
		if (deshabilitado) {
			writer << " readonly ='true'   "
		}
		if (valor || valor == 0) {
			writer << " value='${valor}'   "
		}
		if (ayuda) {
			writer << " placeholder='${ayuda}'"
		}
		if (pattern?.trim()) {
			writer << " pattern='${pattern}'"
		}
		
		if ("true".equals(attrs.btnEdicion)) {
			writer << "style='width:100%; margin-right:"+margen+"' class='with-button-inline' />"
			writer << "<button " 
			if (deshabilitado) {
				writer << " disabled ='disabled'  "
			}
			writer << " deParteDe='${nombre}' glosa='${rotulo}' onclick='return false;' class='inline-button btn-show' tipo='${btnTipo}'><i class='icon-external-link'></i></button>"
		} else {
			if (attrs?.style) {
				writer << "style='${attrs?.style}'/>"
			} else {
				writer << "style='width:100%'/>"
			}
		}
		
		writer << "</div>"
	}

	/**
	 * Este tag es para los textArea
	 *
	 * @attr nombre REQUIRED Es obligatorio y debiera ser el id.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-5.
	 * @attr tipo por defecto se toma text y puede ser text|email|number|....
	 * @attr valor valor del campo
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr ayuda toma el atributo placeholder de HTML y son tips de llenado del campo.
	 * @attr requerido por defecto se toma como falso.
	 * @attr deshabilitado por defecto se toma como falso.
	 * @attr filas por defecto se toma como falso.
	 * 
	 * @attr rows por defecto es 2 y rempresenta el mismo atributo HTML 
	 */
	def textArea = {attrs ->
		def writer = out
		if (attrs.nombre == null) throwTagError("Tag [textArea] requiere el attribute [nombre] y usted en un acto deliberado no lo  hizo !!!! [1 horas de infierno]")
		if (attrs.nombre.find(" ")) throwTagError("Tag [textArea] requiere que el attribute [nombre] no tenga espacios !!!! [200 horas de infierno]")
		if (attrs.cols == null) throwTagError("Tag [textArea] requiere el attribute [cols] y usted en un acto deliberado no lo  hizo !!!! [10 horas de infierno]")
		
		//def locale = RequestContextUtils.getLocale(request)
		def nombre = attrs.nombre
		def tipo = attrs.tipo
		def valor = attrs.valor
		def rotulo = attrs.rotulo
		def ayuda = attrs.ayuda
		def requerido = attrs.requerido
		def cols = attrs.cols
		def filas = attrs.filas
		def deshabilitado = attrs.deshabilitado
		def maxlength = attrs.maxLargo
		def strRequerido = ""
		
		if (!tipo) tipo = 'text' 
		if (requerido) strRequerido = "<font color='red'>*</font>" 
		if (!filas) filas = '2'

		writer << "<div class='label-contenedor pure-input-${cols}'>"
		if (rotulo?.trim()) {
			writer << "<label for='${nombre}'>${rotulo}${strRequerido}:</label>"
		}
		writer << "<textArea id='${nombre}' name='${nombre}' type='${tipo}' rows='${filas}' "
		
		if (maxlength)
			writer << " maxlength='${maxlength}'"
		
		if (requerido) {
			writer << " required='required'   "
		}
		if (deshabilitado) {
			writer << " readonly ='true'   "
		}
		if (ayuda) {
			writer << " placeholder='${ayuda}'"
		}
		writer << "style='width:100%'>"
		if (valor || valor == 0) {
			writer << valor
		}
		writer <<  "</textarea>"
		writer << "</div>"
	}

	
	
	/**
	 * Este tag es para los botones
	 *
	 * @attr tipo por defecto es siguiente y puede ser siguiente|cancelar|volver|pdf|....
	 * @attr value es la glosa del botï¿½n.
	 * @attr action
	 * @attr icon debiera poder incluir un icon en el botï¿½n (pendiente).
	 *
	 * @attr validar Sirve para realizar la validaciï¿½n de los campos requeridos en el formulario por defecto es true
	 * @attr id Identificador del botï¿½n por defecto genera uno aleatoreamente
	 * 
	 */	
	def button = {attrs ->
		def writer = out

		def tipo = attrs.tipo
		//def action = attrs.action
		def icon = attrs.icon
		/////////////////////////////////////
		
		if (!tipo) tipo = 'siguiente' // valores por defecto
		
		def local_class = "--"
		def local_value = "--";
		def validar = "true"
		
		if (tipo.equalsIgnoreCase("siguiente")){
			local_class = "success"
			local_value = "Siguiente"
			
		} else if (tipo.equalsIgnoreCase("cancelar")){
			local_class = "error"
			local_value = "Cancelar"
			
		} else if (tipo.equalsIgnoreCase("terminar")){
			local_class = "error"
			local_value = "Terminar"
			
		} else if (tipo.equalsIgnoreCase("volver")){			
			local_class = "secondary"
			local_value = "Volver"
			
		} else if (tipo.equalsIgnoreCase("oempledor")){
			local_class = "secondary"
			local_value = "Otro empleador"
			
		} else if  (tipo.equalsIgnoreCase("pdf")){
			local_class = "warning"
			local_value = "PDF"
		} else if (tipo.equalsIgnoreCase("agregar")){
			local_class = "secondary"
			local_value = "Agregar"
		} else if  (tipo.equalsIgnoreCase("excel")){
			local_class = "warning"
			local_value = "EXCEL"			
		}
		local_class = "pure-button pure-button-" + local_class
		
		
		if (attrs.value != null) {
			local_value = attrs.value
		}
		def map = [action:attrs.action, class:local_class, value:local_value]
		
		if (attrs.validar != null  && (attrs.validar.equalsIgnoreCase("false")||attrs.validar.equalsIgnoreCase("falso"))) {
			map.put('formnovalidate', attrs.validar)
		}
		
		String expandGroovyComponent = g.actionSubmit(map)
		writer << "${expandGroovyComponent}"
	}
	
	/**
	 * Este tag es para los header muestra la informaciï¿½n que se ingresa.
	 *
	 * @attr siniestro  ID Siniestr.
	 * @attr tipoSin  	Tipo Siniestro.
	 * @attr aFecha  	Fecha accidente.
	 * @attr tRun    	Run trabajador.
	 * @attr tNombre 	Nombre trabajador.
	 * @attr eRut    	Rut empleador
	 * @attr eNombre 	Nombre empleador
	 * @attr aFechaS 	Fecha siniestro.
	 */
	def header_identification = {attrs ->
		def writer = out
		
		def siniestro = attrs.siniestro
		def tipoSin   = attrs.tipoSin
		def aFecha    = attrs.aFecha
		def tRun      = attrs.tRun
		def tNombre   = attrs.tNombre
		def eRut      = attrs.eRut
		def eNombre   = attrs.eNombre
		def aFechaS   = attrs.aFechaS
		
		writer << "<div class='header-identificacion'>"
		
		if (siniestro){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Siniestro: <span>${siniestro}</span></div>"
			writer << "  </div>"
		}
		if (tipoSin){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Tipo Siniestro: <span>${tipoSin}</span></div>"
			writer << "  </div>"
		}
		if (aFechaS){
			writer << "  <div class='header-blockX'>"
			writer << "    <div>Fecha Siniestro: <span>${aFechaS}</span></div>"
			writer << "  </div>"
		}
		if (siniestro || tipoSin || aFechaS ){
			writer << "<div class='salto-de-linea'></div>" 
		}
		if (aFecha){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Fecha accidente: <span>${aFecha}</span></div>"
			writer << "  </div>"
		}
		if (tRun || tNombre ){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Trabajador: <span>${tNombre} [${tRun}]</span></div>"
			writer << "  </div>"
		}
		if (eRut || eNombre ){
			writer << "  <div class='header-blockX'>"
			writer << "    <div>Empleador: <span>${eNombre} [${eRut}]</span></div>"
			writer << "  </div>"
		}
		writer << "</div>"
		writer << "<div class='salto-de-linea'></div>" 
	}
	
	/**
	 * Este tag es para los box
	 *
	 * @attr tipo    Tipo de color-icono definidas: alert|error|info.
	 * @attr texto   REQUIRED texto interior de recuadro.
	 */
	def box = {attrs ->
		def writer = out
		if (attrs.texto == null) throwTagError("Tag [box] requiere el attribute [texto] y usted en un acto deliberado no lo  hizo !!!! [2 dias de infierno]")
		
		def tipo = attrs.tipo
		def cols = attrs.cols
		def texto = attrs.texto
		////////////////////////////////////
		if (!tipo) tipo = '' // valores por defecto
		if (!cols) cols = '4-8'
		
		writer << "<div class='box pure-input-${cols}'>"

		if (tipo.equalsIgnoreCase("alert")){
			writer << "  <i class='icon-warning-sign icon-4x pull-left icon-muted'></i>"
			
		} else if (tipo.equalsIgnoreCase("error")){
			writer << "  <i class='icon-remove icon-4x pull-left icon-muted'></i>"

		} else if (tipo.equalsIgnoreCase("info")){
			writer << "  <i class='icon-info icon-4x pull-left icon-muted'></i>"

		}

		writer << "   ${texto} "
		writer << "  </div>"
	}
	
	/**
	 * Este tag es para el header de SDAAT
	 */
	def header_sdaat = {attrs ->
		def f=new FormatosISLHelper()
		
		def writer = out
		
		def sdaat = attrs.sdaat
		def fSin = f.fechaCorta(sdaat?.fechaSiniestro)
		def tRun = f.run(sdaat?.trabajador?.run)
		def tNombreCompleto=f.nombreCompleto(sdaat?.trabajador)
		def eRut= f.run(sdaat?.empleador?.rut)
		def eNombre = f.blankStatic(sdaat?.empleador?.razonSocial)
		
		
		writer << "<div class='header-identificacion'>"
		
		if (sdaat?.fechaSiniestro){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Fecha siniestro: <span>${fSin}</span></div>"
			writer << "  </div>"
		}

		if (sdaat?.trabajador){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Trabajador: <span>${tNombreCompleto} [${tRun}]</span></div>"
			writer << "  </div>"
		}

		if (sdaat?.empleador){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Empleador: <span>${eNombre} [${eRut}]</span></div>"
			writer << "  </div>"
		}
		
		if (sdaat?.siniestro){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Siniestro: <span>${sdaat.siniestro.id}</span></div>"
			writer << "  </div>"
		}
		
		writer << "</div>"
		writer << "<div class='salto-de-linea'></div>"
	}
	
	/**
	 * Este tag es para el header de SDAEP
	 */
	def header_sdaep = {attrs ->
		def f=new FormatosISLHelper()
		
		def writer = out
		
		def sdaep = attrs.sdaep
		def fSin = f.fechaCorta(sdaep?.fechaSintomas)
		def tRun = f.run(sdaep?.trabajador?.run)
		def tNombreCompleto=f.nombreCompleto(sdaep?.trabajador)
		def eRut= f.run(sdaep?.empleador?.rut)
		def eNombre = f.blankStatic(sdaep?.empleador?.razonSocial)
		
		
		writer << "<div class='header-identificacion'>"
		
		if (sdaep?.fechaSintomas){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Fecha sintomas: <span>${fSin}</span></div>"
			writer << "  </div>"
		}

		if (sdaep?.trabajador){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Trabajador: <span>${tNombreCompleto} [${tRun}]</span></div>"
			writer << "  </div>"
		}

		if (sdaep?.empleador){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Empleador: <span>${eNombre} [${eRut}]</span></div>"
			writer << "  </div>"
		}
		if (sdaep?.siniestro){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Siniestro: <span>${sdaep.siniestro.id}</span></div>"
			writer << "  </div>"
		}
		
		writer << "</div>"
		writer << "<div class='salto-de-linea'></div>"
	}

	/**
	 * Este tag es para el header de 77bis revision
	 */
	def header_77bis = {attrs ->
		def f=new FormatosISLHelper()
		
		def writer = out
		
		def bis = attrs.bis
		def NumeroFormulario = bis?.id
		def fechaRecepcion = f.fechaCorta(bis?.fechaRecepcion)
		def NumeroDictamen = bis?.numeroDictamen
		def fechaDictamen = f.fechaCorta(bis?.fechaDictamen)
		
		writer << "<div class='header-identificacion'>"
		
		if (bis){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>N° Formulario: <span>${NumeroFormulario}</span></div>"
			writer << "  </div>"
		}

		if (bis?.fechaRecepcion){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Fecha Recepción Carta: <span>${fechaRecepcion}</span></div>"
			writer << "  </div>"
		}

		if (bis?.numeroDictamen){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>N° Dictamen: <span>${NumeroDictamen}</span></div>"
			writer << "  </div>"
		}
		if (bis?.fechaDictamen){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Fecha Dictamen: <span>${fechaDictamen}</span></div>"
			writer << "  </div>"
		}
		
		writer << "</div>"
		writer << "<div class='salto-de-linea'></div>"
	}
	
	/**
	 * Este tag es para el header de SDAAT
	 * 
	 * @attr tRun Run del trabajandor 
	 * @attr tNombreCompleto Nombre del trabajandor
	 * @attr pRut Rut del prestador
	 * @attr pNombre Nombre del prestador
	 * @attr pCentro Nombre del centro de salud
	 * @attr opa Orden de primera atenciÃ³n
	 * @attr odas Lista de ordenes de atenciÃ³n
	 * @attr idSiniestro Identificador de siniestro
	 * @attr folioCuenta Folio de la cuenta mÃ©dica
	 * @attr mtoCuenta Monto total de la cuenta mÃ©dica
	 */
	def header_cm = {attrs ->
		def f=new FormatosISLHelper()
		
		def writer = out
		
		// def sdaat = attrs.sdaat
		def tRun = attrs.tRun
		def tNombreCompleto = attrs.tNombreCompleto
		def pRut = attrs.pRut
		def pNombre = attrs.pNombre
		def pCentro = attrs.pCentro
		def opa = attrs.opa
		def opaep = attrs.opaep
		def odas = attrs.odas
		def idSiniestro = attrs.idSiniestro
		def folioCuenta = attrs.folioCuenta
		def mtoCuenta = attrs.mtoCuenta

		writer << "<div class='header-identificacion'>"
		
		if (attrs?.tRun){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Trabajador: <span>${tNombreCompleto} [${tRun}]</span></div>"
			writer << "  </div>"
		}

		if (attrs?.pRut){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Prestador: <span>${pNombre} [${pRut}]</span></div>"
			writer << "  </div>"
		}

		if (attrs?.pCentro){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Centro de salud: <span>${pCentro}</span></div>"
			writer << "  </div>"
		}
		
		if (attrs?.opa){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>OPA : <span>${opa}</span></div>"
			writer << "  </div>"
		}
		
		if (attrs?.opaep){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>OPA-EP : <span>${opaep}</span></div>"
			writer << "  </div>"
		}
		
		if (attrs?.odas){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>ODA : <span>${odas}</span></div>"
			writer << "  </div>"
		}

		if (attrs?.idSiniestro){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Siniestro: <span>${idSiniestro}</span></div>"
			writer << "  </div>"
		}
		
		if (attrs?.folioCuenta){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Folio Cuenta: <span>${folioCuenta}</span></div>"
			writer << "  </div>"
		}
		
		if (attrs?.mtoCuenta){
			writer << "  <div class='header-blockX'>"
			writer << "   <div>Valor Cuenta: <span> ${f.montosStatic(mtoCuenta)}</span></div>"
			writer << "  </div>"
		}

		writer << "</div>"
		writer << "<div class='salto-de-linea'></div>"
	}
	
	def header_historial_cm = {attrs ->
		def f=new FormatosISLHelper()
		def writer = out
		if (attrs?.cuentas.size() > 0) {
			writer << "<legend style='color: #0C67AE; font-size: 110%; margin-bottom: 0px; padding-bottom: 0px; margin-top: 12px; margin-bottom: 8px; width: 100%; border-color: #91BADA; line-height: 80%;'> Historial de Cuentas Médicas </legend>"
			
			writer << "<table class='pure-table' width='100%'>"
			writer << 	"<thead>"
			writer << 	"<th> N° Siniestro </th>"
			writer << 	"<th> N° Cuenta Médica </th>"
			writer << 	"<th> Fecha Aceptación </th>"
			writer << 	"<th> Fecha Pago </th>"
			writer << 	"<th> Valor </th>"
			writer << 	"</thead>"
			
			writer << 	"<tbody>"
			def i = 0
			attrs?.cuentas.each {
				writer << "<tr class='${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}'>"
				writer << 	"<td align='center'>${it.idSiniestro}</td>"
				writer << 	"<td align='center'>${it.idCuentaMedica}</td>"
				writer << 	"<td align='center'>${f.fechaCortaStatic(it.fechaAceptacion)}</td>"
				writer << 	"<td align='center'>${f.fechaCortaStatic(it.fechaPago)}</td>"
				writer << 	"<td align='right'>${f.montosStatic(it.valorCuentaMedica)}</td>"
				writer << "</tr>"
				i++
			}
			writer << 	"</tbody>"
			writer << "</table>"
			writer << "<br>"
		}
	}

	/**
	 * Este tag es para los calendar
	 *
	 * @attr nombre REQUIRED Es obligatorio y debiera ser el id.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-5.
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr valor es el valor del campo.
	 * @attr requerido por defecto se toma como falso.
	 * @attr deshabilitado por defecto se toma como falso.
	 * @attr mostrarCal indicador para desplegar el calendario, por defecto se toma como verdadero.
	 * 
	 */
	def calendar = {attrs ->
		def writer = out
		
		if (attrs.nombre == null) throwTagError("Tag [textInput] requiere el attribute [nombre] y usted en un acto deliberado no lo  hizo !!!! [1 horas de infierno]")
		if (attrs.nombre.find(" ")) throwTagError("Tag [textInput] requiere que el attribute [nombre] no tenga espacios !!!! [200 horas de infierno]")
		if (attrs.cols == null) throwTagError("Tag [textInput] requiere el attribute [cols] y usted en un acto deliberado no lo  hizo !!!! [10 horas de infierno]")
		
		def cols = attrs.cols
		def nombre = attrs.nombre
		def ayuda = attrs.ayuda
		def rotulo = attrs.rotulo
		def requerido = attrs.requerido ? attrs.requerido.toBoolean() : false
		def deshabilitado = attrs.deshabilitado
		def valor = attrs.valor
		def strRequerido = ""
		def mostrarCal = attrs.mostrarCal ? attrs.mostrarCal.toBoolean() : false
		////////////////////////////////////////////////////
		if (requerido){
			attrs.class = "required requerido"
			strRequerido = "<font color='red'>*</font>"
		} else {
			attrs.class = "opcional"
		}
		
		attrs.name = nombre
		attrs.format = "dd-MM-yyyy"
		attrs.locale = "es"
		attrs.firstDayOfWeek = "Mo"
		if (deshabilitado) {
			attrs.readonly = true
		}
		if (valor) attrs.value = valor
		attrs.style = "width:100%"
		
		String expandGroovyComponent = "";
		//Render output
		try {
			if (mostrarCal) {
				// Se debe parsear la fecha date
				if (valor != null)
					attrs.valor = new java.text.SimpleDateFormat("dd-MM-yyyy").format(attrs.valor)
				attrs.tipo = 'fecha'
				expandGroovyComponent = textInput(attrs)
				//expandGroovyComponent = expandGroovyComponent.replace("dateChooser.init();", "")
			} else {
				expandGroovyComponent = dateChooserRenderer.renderTag(attrs)
				String a = expandGroovyComponent.split(" ", 2)[0]
				String b = expandGroovyComponent.split(" ", 2)[1]
				StringBuffer options = new StringBuffer();
				options.append(" pattern='(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}'")
				options.append(" title='El formato es correcto es dd-mm-aaaa.'")
				if (requerido)
					options.append(" required='required'")
				expandGroovyComponent = a + options.toString() + b
			}
		}
		catch(RenderException e){
			log.error(e)
		}
		
		if (mostrarCal) {
			writer << "${expandGroovyComponent}"
		} else {
			writer << "<div class='label-contenedor pure-input-${cols}'>"
			if (rotulo) {
				writer << "<label for='${nombre}'>${rotulo}${strRequerido}:</label>"
			}
			writer << "${expandGroovyComponent}"
			writer << "</div>"
		}
	}
	

	/**
	 * Este tag es para los combo
	 *
	 * @attr nombre REQUIRED Es obligatorio y debiera ser el id.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-5.
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr valor es el valor del campo.
	 * @attr requerido por defecto se toma como falso.
	 * 
	 * @attr from = propiedad que g:select.
	 * @attr optionKey = propiedad que g:select por defecto es "codigo".
	 * @attr optionValue = propiedad que g:select por defecto es "descripcion".
	 * 
	 * @attr deshabilitado por defecto se toma como falso.
	 * @attr btnEdicion muestra boton de ediciï¿½n in-line | se necsita JS
	 */	
	def combo = {attrs ->
		def writer = out
		
		if (attrs.nombre == null) throwTagError("Tag [textInput] requiere el attribute [nombre] y usted en un acto deliberado no lo  hizo !!!! [1 horas de infierno]")
		if (attrs.nombre.find(" ")) throwTagError("Tag [textInput] requiere que el attribute [nombre] no tenga espacios !!!! [200 horas de infierno]")
		if (attrs.cols == null) throwTagError("Tag [textInput] requiere el attribute [cols] y usted en un acto deliberado no lo  hizo !!!! [10 horas de infierno]")
		
		def cols = attrs.cols
		def nombre = attrs.nombre
		def rotulo = attrs.rotulo
		def valor = attrs.valor
		def requerido = attrs.requerido
		def deshabilitado = attrs.deshabilitado
		def strRequerido = ""
		////////////////////////////////////////////////////
		if (attrs.requerido){
			attrs.required = "required" 
			strRequerido = "<font color='red'>*</font>"
		}
		
		if (deshabilitado){
			attrs.disabled = "disabled"
		}
		
		attrs.value = valor
		attrs.name = nombre
		
		if (attrs.optionKey == null){
			attrs.optionKey = "codigo"
		}

		if (attrs.optionValue == null){
			attrs.optionValue = "descripcion"
		}
		attrs.style = "width:100%"
		
		if ("true".equals(attrs.btnEdicion)) {
			attrs.style = "width:100%; margin-right:-30px;"	
			attrs.class='with-button-inline'
		}
		
		String expandGroovyComponent = g.select(attrs)

		writer << "<div class='label-contenedor pure-input-${cols}'>"
		if (rotulo){
			writer << "<label for='${nombre}'>${rotulo}${strRequerido}:</label>"
		}
		writer << "${expandGroovyComponent}"
		
		if ("true".equals(attrs.btnEdicion)) {
			writer << "<button "
			if (deshabilitado) {
				writer << " disabled ='disabled'  "
			}
			writer << " deParteDe='${nombre}' glosa='${rotulo}' onclick='return false;' class='inline-button btn-show' tipo='combo'><i class='icon-external-link'></i></button>"
		}

		
		
		writer << "</div>"
	}
	
	
	/**
	 * Este tag es para los radiobutton
	 *
	 * @attr nombre REQUIRED Es obligatorio y debiera ser el id.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-10.
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr valor es el valor del campo.
	 * @attr requerido por defecto se toma como falso.
	 * @attr deshabilitado por defecto se toma como falso.
	 *
	 * @attr labels array [nombre1,nombre2,nombre3]
	 * @attr values array [valor1,valor2,valor3]
	 *
	 * 	<isl:radiogroup cols="1-8" name="propiedad" nombre="propiedad"
	 * 		rotulo="Propiedades" labels="['privada','publica']"
	 * 		values="[1,2]">
	 * 		${it.label}
	 * 		${it.radio}
	 * 	</isl:radiogroup>
	 */
	def radiogroup = {attrs, body ->
		def cols = attrs.cols
		def rotulo = attrs.rotulo
		def nombre = attrs.nombre
		def requerido = attrs.requerido
		def deshabilitado = attrs.deshabilitado
		def valor = attrs.valor
		
		def value = attrs.remove('value')
		def values = attrs.remove('values')
		def labels = attrs.remove('labels')
		def name = attrs.remove('name')
		def from = attrs.remove('from')
		def strRequerido = ""
		
		if(from){
			values = []
			labels = []
			from.each() { 
				values.add(it.codigo)
				labels.add(it.descripcion)
			}
		}
				
		def writer = out
		
		writer << "<div class='label-contenedor pure-input-${cols}'>"
		if (attrs.requerido){
			strRequerido = "<font color='red'>*</font>"
		}
		if (rotulo){
			writer << "<label for='${nombre}'>${rotulo}${strRequerido}:</label>"
		}
		writer << "<div class='radiobuttongrup'>"
		
		values.eachWithIndex {val, idx ->
			def it = new Expando();
			it.radio = "<input type=\"radio\" name=\"${name}\" id=\"${name}_${idx}\""
			if (valor?.toString().equals(val.toString())) {
				it.radio += 'checked '
			}
			if (requerido) {
				it.radio += " required='required' "
			}
			if (deshabilitado) {
				it.radio += " disabled "
			}
			
			
			it.radio += "value=\"${val.toString().encodeAsHTML()}\" />"

			it.label = labels == null ? 'Radio ' + val : labels[idx]

			
			writer << body(it).decodeHTML(); //El encode va site-wide
			
		}
		writer << "</div>"
		writer << "</div>"
	}
	
	/**
	 * Este tag es para los ckeckbox
	 *
	 * @attr nombre REQUIRED Es obligatorio y debiera ser el id.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-10.
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr valor es el valor del campo.
	 * @attr requerido por defecto se toma como falso.
	 * @attr deshabilitado por defecto se toma como falso.
	 *
	 * @attr labels array [nombre1,nombre2,nombre3]
	 * @attr values array [valor1,valor2,valor3]
	 *
	 * 	<checkgroup cols="1-8" name="propiedad" nombre="propiedad"
	 * 		rotulo="Propiedades" labels="['privada','publica']"
	 * 		values="[1,2]">
	 * 		${it.label}
	 * 		${it.radio}
	 * 	</isl:checkgroup>
	 */
	def checkgroup = {attrs, body ->
		def cols = attrs.cols
		def rotulo = attrs.rotulo
		def nombre = attrs.nombre
		def requerido = attrs.requerido
		def deshabilitado = attrs.deshabilitado
		def value = attrs.remove('value')
		def values = attrs.remove('values')
		def labels = attrs.remove('labels')
		def name = attrs.remove('name')
		def strRequerido = ""
		def writer = out

		if (requerido) strRequerido = "<font color='red'>*</font>"
		writer << "<div class='label-contenedor pure-input-${cols}'>"
		if (rotulo){
			writer << "<label for='${nombre}'>${rotulo}${strRequerido}:</label>"
		}
		if (requerido) strRequerido = "<font color='red'>*</font>"
		writer << "<div class='radiobuttongrup'>"
		
		values.eachWithIndex {val, idx ->
			def it = new Expando();
			it.radio = "<input type=\"checkbox\" name=\"${name}\" "
			if (value?.toString().equals(val.toString())) {
				it.radio += 'checked '
			}
			/*
			if (requerido) {
				it.radio += " required='required' "
			}
			*/
			if (deshabilitado) {
				attrs.readonly = true
			}
			it.radio += "value=\"${val.toString().encodeAsHTML()}\" />"

			it.label = labels == null ? 'Radio ' + val : labels[idx]

			
			writer << body(it)
			
		}
		writer << "</div>"
		writer << "</div>"
	}
	
	/**
	 * Este tag es para los textOutput
	 * Es un par etiqueeta:valor
	 *
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr valor es el valor del campo.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-5.
	 */
	def textOutput = {attrs ->
		def writer = out

		if (attrs.cols == null) throwTagError("Tag [textOutput] requiere el attribute [cols]")
		def rotulo = attrs.rotulo
		def cols = attrs.cols
		def valor = attrs.valor != null ? attrs.valor : ''
		def nombre = attrs.nombre
		
		writer << "<div class='label-contenedor pure-input-${cols}' style='word-wrap:break-word;'>"
		if (rotulo)
			writer << "  <label>${rotulo}:</label>"
		if (nombre)
			writer << "  <span id='${nombre}' class='textOutput-valor'>${valor}</span>"
		else
			writer << "  <span class='textOutput-valor'>${valor}</span>"
		writer << "</div>"
	}

	/**
	 * Este tag es para los upload
	 *
	 * @attr nombre REQUIRED Es obligatorio y debiera ser el id.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-5.
	 * @attr rotulo es la etiqueta o label de campo.
	 * @attr ayuda toma el atributo placeholder de HTML y son tips de llenado del campo.
	 * @attr requerido por defecto se toma como falso.
	 */
	def upload = {attrs ->
		
		def writer = out
		if (attrs.nombre == null) throwTagError("Tag [textInput] requiere el attribute [nombre] y usted en un acto deliberado no lo  hizo !!!! [1 horas de infierno]")
		if (attrs.nombre.find(" ")) throwTagError("Tag [textInput] requiere que el attribute [nombre] no tenga espacios !!!! [200 horas de infierno]")
		if (attrs.cols == null) throwTagError("Tag [textInput] requiere el attribute [cols] y usted en un acto deliberado no lo  hizo !!!! [10 horas de infierno]")
		
		//def locale = RequestContextUtils.getLocale(request)
		def cols = attrs.cols
		def nombre = attrs.nombre
		def tipo = attrs.tipo
		def valor = attrs.valor
		def rotulo = attrs.rotulo
		def ayuda = attrs.ayuda
		def requerido = attrs.requerido
		def strRequerido = ""
		
		if (requerido) strRequerido = "<font color='red'>*</font>"
		if (!tipo) tipo = 'text' // (attrs.tipo ? attrs.tipo.toInteger() : 0)

		writer << "<div class='label-contenedor pure-input-${cols}'>"
		if (rotulo?.trim()) {
			writer << "<label for='${nombre}'>${rotulo}${strRequerido}:</label>"
		}
		writer << "<input id='${nombre}' name='${nombre}' type='file' " 
				
		if (requerido) {
			writer << " required='required'   "
		}
		if (valor) {
			writer << " value='${valor}'   "
		}
		if (ayuda) {
			writer << " placeholder='${ayuda}'"
		}
		writer << "style='width:100%' class='upload-button'/>"
		writer << "</div>"
	}
	
	/**
	 * Este tag es para los link
	 *
	 * @attr enlace REQUIRED es la url del link.
	 * @attr nombre es el nombre del link.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-5.
	 */
	def link = {attrs ->
		def writer = out

		if (attrs.cols == null) throwTagError("Tag [link] requiere el attribute [cols]")
		if (attrs.enlace == null) throwTagError("Tag [link] requiere el attribute [enlace]")
		
		def enlace = attrs.enlace
		def nombre = attrs.nombre
		def cols = attrs.cols
		
		writer << "<div class='label-contenedor pure-input-${cols}'>"
		
		if (nombre)
			writer << "  <a href='${enlace}' target='_blank'>${nombre}</a>"
		else
			writer << "  <a href='${enlace}' target='_blank'>${enlace}</a>"
			
		writer << "</div>"
	}
	
	/**
	 * Este tag es para los link
	 *
	 * @attr rotulo es el texo precedente al botón.
	 * @attr icon es el icono del botón.
	 * @attr variable variable para mandar a la acción.
	 * @attr valor valor para mandar a la acción.
	 * @attr cols REQUIRED puede ser un 1-8 o 1-5.
	 * @attr accion REQUIRED acción que gatilla el boton.
	 */
	def infoAdicional = {attrs ->
		def writer = out

		if (attrs.cols == null) throwTagError("Tag [infoAdicional] requiere el attribute [cols]")
		if (attrs.accion == null) throwTagError("Tag [infoAdicional] requiere el attribute [accion]")
		
		if (attrs.variable == null)
			if (attrs.valor != null) 
				throwTagError("Tag [infoAdicional] requiere el attribute [valor]")
		if (attrs.valor == null)
			if (attrs.variable != null)
				throwTagError("Tag [infoAdicional] requiere el attribute [variable]")
		
		def accion = attrs.accion
		def rotulo = attrs.rotulo
		def cols = attrs.cols
		def icon = attrs.icon? attrs.icon : 'search'
		def variable = attrs.variable
		def valor = attrs.valor
		
		writer << "<div class='label-contenedor pure-input-${cols}'>"
		writer << "  <div style='height: 20px;'></div>&nbsp;"
		
		if (rotulo?.trim()) 
			writer << "  ${rotulo}&nbsp;&nbsp;"
		else
			writer << "  Ver Información Adicional&nbsp;&nbsp;"
		if (variable != null && valor != null)		
			writer << "  <button class='pure-button pure-button-secondary' onclick='document.forms[0].${variable}.value=\"${valor}\";document.forms[0].action=\"${accion}\";document.forms[0].submit();'><i class=\"icon-${icon}\"></i></button>"
		else
			writer << "  <button class='pure-button pure-button-secondary' onclick='document.forms[0].action=\"${accion}\";document.forms[0].submit();'><i class=\"icon-${icon}\"></i></button>"
		
		
		writer << "</div>"
	}
}
