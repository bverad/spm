package cl.adexus.isl.spm

import cl.adexus.isl.spm.SistemaSalud

class SDAEP_diepController {

	def documentacionService
	def SDAEPService

	def index() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def model = [ sdaep: sdaep ]
	}
	
	def end() {redirect(controller:'SDAEP_previo', action:'index')}
	
	def dp01() {
		//Identificacion del denunciante
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def calificaciones=CalificacionDenunciante.executeQuery("select cd from CalificacionDenunciante cd where cd.id <> ?",["7"])//CalificacionDenunciante.list()
		def model=[ calificaciones: calificaciones, sdaep: sdaep ]
		if(params.get('model')){
			model += params.get('model')
		}
		model
	}
	
	
	
	def dp02(){ 
		//DIEP Sección A y B
		def comunas=Comuna.listOrderByDescripcion()
		def tipoCalles=TipoCalle.listOrderByCodigo()
		def tipoPropiedades=TipoPropiedadEmpresa.listOrderByCodigo()
		def tipoEmpresas=TipoEmpresa.listOrderByCodigo()
		def naciones = Nacion.listOrderByDescripcion()
		def etnias = Etnia.listOrderByCodigo()
		def tipoDuracionContratos = TipoDuracionContrato.listOrderByCodigo()
		def tipoRemuneraciones = TipoRemuneracion.listOrderByCodigo()
		def categoriaOcupaciones = CategoriaOcupacion.listOrderByCodigo()
		def tipoActividadEconomica = TipoActividadEconomica.listOrderByDescripcion()
		
		//Vemos si podemos prellenar con algo
		def sdaep = SDAEP.findById(session['sdaep'])
		def diep = sdaep?.diep
		
		if (!diep) {
			diep = new DIEP()
			diep.setEmpleador(sdaep?.empleador)
			diep.setTrabajador(sdaep?.trabajador)
			diep.ciiuEmpleador = TipoActividadEconomica.findByCodigo(sdaep?.codigoActividadEmpresa)
		}
		//if (!diep.isAttached())
		//	diep.attach()
		if (!diep.nacionalidadTrabajador)
		diep.nacionalidadTrabajador=Nacion.findByCodigo('152') //Chileno por defecto

		// Se evaluan requeridos en función del tipo denunciante
		def tipoDenunciante = sdaep?.tipoDenunciante
		def esEmpleador = tipoDenunciante.codigo == '1'
		def attrsReq = ['ciiuEmpleador'					: esEmpleador
					  , 'direccionEmpleadorTipoCalle'	: esEmpleador
					  , 'direccionEmpleadorNombreCalle'	: esEmpleador
					  , 'direccionEmpleadorNumero'		: esEmpleador
					  , 'direccionEmpleadorComuna'		: esEmpleador
					  , 'nTrabajadoresHombre'			: esEmpleador
					  , 'nTrabajadoresMujer'			: esEmpleador
					  , 'propiedadEmpresa'				: esEmpleador
					  , 'tipoEmpresa'					: esEmpleador
					  , 'direccionTrabajadorTipoCalle'	: esEmpleador
					  , 'direccionTrabajadorNombreCalle': esEmpleador
					  , 'direccionTrabajadorNumero'		: esEmpleador
					  , 'direccionTrabajadorComuna'		: esEmpleador
					  , 'profesionTrabajador'			: esEmpleador
					  , 'duracionContrato'				: esEmpleador
					  , 'tipoRemuneracion'				: esEmpleador
					  , 'categoriaOcupacion'			: esEmpleador
					  , 'fechaIngresoEmpresa'			: esEmpleador]
		def model=[	comunas					: comunas
				  , tipoCalles				: tipoCalles
				  , tipoPropiedades			: tipoPropiedades
				  , tipoEmpresas			: tipoEmpresas
				  , naciones				: naciones
				  , etnias					: etnias
				  , tipoDuracionContratos	: tipoDuracionContratos
				  , tipoRemuneraciones		: tipoRemuneraciones
				  , categoriaOcupaciones	: categoriaOcupaciones
				  , diep					: diep
				  , attrsReq				: attrsReq
				  , tipoActividadEconomica	: tipoActividadEconomica
				  , sistemaSalud			: SistemaSalud.list()
				  , sdaep: sdaep ]
		if(params.get('model')){
			model += params.get('model')
		}else{
			model
		}
	}
	
	def cu02(){
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.diepCU02(params, sdaep)
		
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}

	
	def dp02_2(){
		//DIEP Seccion C y D
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def comunas=Comuna.listOrderByDescripcion()
		def calificaciones=CalificacionDenunciante.list()
		
		//Vemos si podemos prellenar con algo
		def diep=sdaep?.diep
		
		if (!diep?.sintoma) { 
			if (!sdaep?.cuestionarioOrigen?.pregunta5) 
			    diep.setSintoma(sdaep?.relato)
		    else 
		 	    diep.setSintoma(sdaep?.cuestionarioOrigen?.pregunta5)
		}
		if (!diep?.denunciante) {
			sdaep.denunciante.refresh()
			diep.setDenunciante(sdaep?.denunciante)
		}
		if (!diep?.calificacionDenunciante)
			diep.setCalificacionDenunciante(sdaep?.tipoDenunciante)
		
		// Se evaluan requeridos en funci�n del tipo denunciante
		def tipoDenunciante = sdaep?.tipoDenunciante
		def esEmpleador = tipoDenunciante.codigo == '1'
		def attrsReq = ['puestoTrabajo': esEmpleador,
						'esAntecedenteCompanero': esEmpleador,
						'sintomasPrevios': esEmpleador]
		def model=[ comunas: comunas
			, calificaciones: calificaciones
			, diep: diep
			, attrsReq: attrsReq
			, sdaep: sdaep ]
		
		if(params.get('model')){
			model += params.get('model')
		}else{
			model
		}
	}
	
	def cu02_2(){
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def r = SDAEPService.diepCU02_2(params, sdaep)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}

	def cu02_2back_cu02(){
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def r = SDAEPService.diepCU02_2BackCU02(params, sdaep)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}

	def dp03() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def model = [ sdaep: sdaep ]
	}
	def dp04() { 
		def sdaep = SDAEP.findById(session['sdaep'])
		def model = [ sdaep: sdaep ]
		model
	}
	def dp05() {
		flash.mensajes = null
		log.info("Ejecutando accion dp05")
		log.debug("Recuperando variable de sesion para denuncia")
		def sdaep = SDAEP.findById(session['sdaep'])
		log.debug("El valor de la variable corresponde a : $sdaep")
		
		log.debug("Verificando si existe documentacion adicional")
		def docAdicionales = DocumentacionAdicional.findAllByDenunciaEP(sdaep?.diep)
		if(!docAdicionales){
			flash.mensajes = "Debe existir al menos un documento en la documentacion adicional para continuar el flujo"
			render(view: 'dp06', model: [docAdicionales: docAdicionales, sdaep: sdaep])
		}else{
			def model = [ sdaep: sdaep ]
		}
		log.info("Accion dp05 finalizada")
	}
	def dp06() {
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def docAdicionales = DocumentacionAdicional.findAllByDenunciaEP(sdaep?.diep)
		render(view: 'dp06', model: [docAdicionales: docAdicionales, sdaep: sdaep])
	}
	def dp07() {}

	// Busca denuncia pre-existente
	def r01(){
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def r = SDAEPService.diepR01(params, sdaep)
		log.info ("r01->next->" + r.get('next'))
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}

	def genBorradorPdf(){
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def b=SDAEPService.diepGenBorradorPdf(params, sdaep)
		 if(b!=null){
			 response.setContentType("application/pdf")
			 response.setHeader("Content-disposition", "attachment; filename=diepBorrador.pdf")
			 response.setContentLength(b.length)
			 response.getOutputStream().write(b)
		 } 
	}

	def genPdf(){
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def b=SDAEPService.diepGenPdf(params, sdaep)
		 if(b!=null){
			 response.setContentType("application/pdf")
			 response.setHeader("Content-disposition", "attachment; filename=diep.pdf")
			 response.setContentLength(b.length)
			 response.getOutputStream().write(b)
		 } 
	}
	
	
	// Procesa la diat, guardando la diat en el siniestro si es que no existe y envia a SUSESO
	def r04(){
		def sdaep = SDAEP.findById(session['sdaep'])
		params['bisId'] = session['77bis']
		
		def r = SDAEPService.diepR04(params, sdaep)
		if(r.get('model')){
			params.put('model', r.get('model'))
		}
		def next = r.next
		if(next.action=='dp05'){
			log.info("Accion existe, redireccionando a ${next.action}")
			flash.message = "cl.adexus.isl.spm.sdaat.susesows.mensaje"
			flash.args = []
			flash.default = next?.mensaje
			redirect(action: next.action)
		}else{
			log.info("redirecting to:"+next.action)
			flash.mensajes = "Debes adjuntar DIEP en formato PDF para continuar"
			redirect (action: next.action)
		} 
	}
	
	def cu05(){
		forward ([controller: 'SDAEP_opaep', action:'r01'])
	}

	def uploadDiepFirmada() {
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def f = request.getFile('fileDenuncia')
		def result = documentacionService.upload(session, 'DIEPfirmada', 'DIEP', f)

		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha subido exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
		}

		def model = [ sdaep: sdaep ]
		log.info("uploadDiepFirmada->view->dp04")
		render (view: 'dp04', model: model)
	}

	/**
	 * Procesa el archivo de documento adicional
	 */
	def uploadDocumentoAdicional() {
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def descripcion = params.descripcionDocumento.trim()
		if (descripcion == "") {
			flash.mensajes = "Debe ingresar una descripcion al archivo"
		} else {
			def f = request.getFile('fileAdicional')
			def result = documentacionService.upload(session, descripcion, 'DIEP', f)
			if (result.status == 0) {
				flash.mensajes = 'El archivo se ha subido exitosamente'
			} else {
				flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
			}
		}

		def docAdicionales = DocumentacionAdicional.findAllByDenunciaEP(sdaep?.diep)
		
		render(view: 'dp06', model: [docAdicionales: docAdicionales, sdaep: sdaep])
	}

	/**
	 * Elimina el documento adicional indicado en link
	 */
	def deleteDocumentoAdicional() {
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def result = documentacionService.delete(params.id)

		if (result.status == 0) {
			flash.mensajes = "El archivo se ha borrado exitosamente."
		} else {
			flash.mensajes = "Hubo un error tratando de borrar el archivo: " + result.mensaje
		}

		def docAdicionales = DocumentacionAdicional.findAllByDenunciaEP(sdaep?.diep)
		render(view: 'dp06', model: [ docAdicionales: docAdicionales, sdaep: sdaep ])
	}
}
