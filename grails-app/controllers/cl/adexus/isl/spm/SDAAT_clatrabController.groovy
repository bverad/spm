package cl.adexus.isl.spm

class SDAAT_clatrabController {

	def SDAATService

	def index() { redirect(action: 'r01') }

	def dp01() {
		// Busca los actividades  para el Cuestionario Obrero
		def sdaat = SDAAT.findById(session['sdaat'])
		
		def acts=ActividadTrabajador.findAllByCodigoNotInList(["OE", "OO"])
		def model=[actividadesTrabajador: acts]
		
		model.put("sdaat", sdaat)
		
		if(params.get('model')){
			model += params.get('model')
		}
		
		log.info("dp01: model: " + model)
		model
	}
	
	def r01() {
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.clatrabR01(params,sdaat)
		params.put('model', r.get('model'))
		log.info("r01: sdaat: " + sdaat)
		log.info("r01: next->" +  r.get('next'))
		//redirect (r.get('next'))
		forward controller: r.next.controller, action: r.next.action
	}
	
	def r02() {
		// Procesa cuestionario
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.clatrabR02(params,sdaat)
		params.put('model', r.get('model'))
		//redirect (r.get('next'))
		forward controller: r.next.controller, action: r.next.action, params: params
	}
	
	def dp02() { 
		def sdaat = SDAAT.findById(session['sdaat'])
		
		def model = [ 'sdaat': sdaat ]
		//flash.get('model') 
	}

	def cu02s() {
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
		def r = SDAATService.excepcionar(params,sdaat,'clatrab')
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward next
	}

	def cu02t() {
		//Rescata la denuncia
		def sdaat = SDAAT.findById(session['sdaat'])
		
		//Venimos desde un 77bis
		if (session['77bis']){
			params['bisId'] = (session['77bis'])
		}
		
		//Llamamos al servicio
		def r = SDAATService.terminar(params,sdaat,'Trabajador es Obrero')
		//redirect (r.get('next'))
		forward controller: r.next.controller, action: r.next.action
		
	}
		
}
