package cl.adexus.isl.spm

class SDAAT_calorigenController {

	def documentacionService
	def SDAATService

	def index() { redirect(action: 'dp01') }

	def dp01(){  
		flash.mensajes = null
		def sdaat = SDAAT.findById(session['sdaat'])
		def model = ['sdaat': sdaat ]
		
		log.info("dp01: sdaat: " + sdaat)
		log.info("dp01: model: " + model)
		
		model
	} //Relato

	def cu01y() {
		// Trayecto
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.calorigenCU01(params, sdaat, 'trayecto')
		params.put('model', r.get('model'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}

	def cu01t() {
		// Trabajo
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.calorigenCU01(params, sdaat, 'trabajo')
		params.put('model', r.get('model'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}

	def dp02(){ // Cuestionario Trayecto
		
		// Cargar tipos de accidente trayecto
		def tiposAccidenteTrayecto=TipoAccidenteTrayecto.list()
		// Pasamos los datos al view
		def model=[tiposAccidenteTrayecto: tiposAccidenteTrayecto]
		if(params.get('model')){
			model += params.get('model') 
		}else{
			model
		}
	}

	def dp03(){ // Cuestionario Trabajo
		// Cargar tipos de accidente trabajo
		def sdaat = SDAAT.findById(session['sdaat'])
		def tipoDanyoTrabajo=OrigenDanyo.list()
		
		// Pasamos los datos al view
		def model=[ tipoDanyoTrabajo: tipoDanyoTrabajo, sdaat: sdaat ]
		if(params.get('model')){
			model += params.get('model')
		}
		
		log.info("sdaat: " + sdaat)
		log.info("model: " + model)
		
		model
	}

	def r01() {
		// Procesa cuestionario accidente de trayecto para determinar si es origen com�n
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.calorigenR01(params, sdaat)
		params.put('model', r.get('model'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}

	def r02() {
		// Procesa cuestionario accidente de trabajo para determinar si es origen comun
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.calorigenR02(params, sdaat)
		params.put('model', r.get('model'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}
	
	def dp04(){ //Informa origen comun y permite excepcionar
		params.get('model')
	}

	def cu04s() {
		//Llamamos al servicio
		def sdaat = SDAAT.findById(session['sdaat'])
		if (!params?.motivo || params?.motivo == "")
		{
			flash.mensajes = 'Debe indicarse el motivo de la excepcion'
		}
		if (!params?.autorizador || params?.autorizador == "")
		{
			if (!flash.mensajes || flash.mensajes.length() > 0)
				flash.mensajes +=" y "
			flash.mensajes += 'Debe indicarse la persona que autoriza'
		}
		def r = SDAATService.excepcionar(params, sdaat,'calorigen')
		params.put('model', r.get('model'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}

	def cu04t() {
		//Nada que hacer aca, derecho a dp05 (PDF y subir)
		def sdaat = SDAAT.findById(session['sdaat'])
		if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
			sdaat.trabajador.attach()
		}

		if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
			sdaat.empleador.attach()
		}
		//model: ['sdaat':sdaat]
		def model = [sdaat:sdaat]
		params.put('model', model)
		def next = [action: 'dp05']
		next.params=params
		forward (next)
	}


	def dp05(){ //Formulario para generar PDF y subirlo
		params.get('model')
	}

	def genPdf(){ //Genera el PDF con el cuestionario para firmar
		def sdaat = SDAAT.findById(session['sdaat'])
		def b = SDAATService.calorigenGenPdf(params, sdaat)

		if(b!=null){
			response.setContentType("application/pdf")
			response.setHeader("Content-disposition", "attachment; filename=origenComun.pdf")
			response.setContentLength(b.length)
			response.getOutputStream().write(b)
		} else {
			//No se genero el PDF
		}
	}

	def cu05s() { //Guarda el PDF y termina
		
	}
	
	def cu05t() { //Terminar sin guardar el PDF
		//Llamamos al servicio
		def sdaat = SDAAT.findById(session['sdaat'])
		
		//Venimos desde un 77bis
		if (session['77bis']){
			log.info "77BIS ->"+session['77bis']
			params['bisId'] = (session['77bis'])
		}
		
		def r = SDAATService.terminar(params, sdaat,'Origen es Comun')
		def next = r.get('next')
		next.params=params
		forward (next)
	}

	def uploadCuestionario() {
		def f = request.getFile('fileCuestionario')

		def result = documentacionService.upload(session,
									'Cuestionario de calificacion de origen firmado', 'DIAT', f)
		if (result.status == 0) {
			flash.mensajes = 'El archivo se ha subido exitosamente'
		} else {
			flash.mensajes = 'Ha ocurrido un error con la subida del archivo: ' + result.mensaje
		}
		def sdaat = SDAAT.findById(session['sdaat'])
		if (sdaat.trabajador!=null && !sdaat.trabajador.isAttached()) {
			sdaat.trabajador.attach()
		}

		if (sdaat.empleador!=null && !sdaat.empleador.isAttached()) {
			sdaat.empleador.attach()
		}
		//model: ['sdaat':sdaat]
		def model = [sdaat:sdaat]
		
		render view: 'dp05'
	}
}
