package cl.adexus.isl.spm

class SDAAT_complejController {

	def SDAATService
	
    def index() { redirect (action:'dp01') }

	def dp01(){ 
		flash.mensajes = null
		def sdaat = SDAAT.findById(session['sdaat'])

		def model = [ sdaat: sdaat ]
		
		if(params.get('model')){
			model += params.get('model')
		}
		
		model  
	}
	
	def dp02(){
		def sdaat = SDAAT.findById(session['sdaat'])
		def model = [ sdaat: sdaat ]

		if(params.get('model')){
			model += params.get('model')
		}
				
		model
		
	}
		
	def r01() {
		// Procesa cuestionario de complejidad determinando si la complejidad es 0 
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.complejR01(params, sdaat)
		params.put('model', r.get('model'))
		log.info("r01: next->" +  r.get('next'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}

	def cu01() {
		// Despues de aceptar/rechazar propuesta de complejidad va a ingreso de denuncia
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.complejCU01(params, sdaat)
		log.info("cu01: next->" +  r.get('next'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}
}
