package cl.adexus.isl.spm

class SDAEP_previoController {
	
	def SDAEPService
	
    def index() { redirect(action: 'dp01') }
	
	/**
	 * Formulario
	 * 	- RUT Trabajador
	 *  - [Siguiente] -> r01()
	 */
	def dp01() {
		session['sdaep']=null
		params.get('model')
	}
	
	
	/**
	 * Llamado por dp01
	 *
	 * 	Determina si existen siniestros de enfermedad profesional 
	 *  asociados al trabajador. Si existen va a dp02 para elegir
	 *  el siniestro previo. Si no existe es nuevo y va a SDAEP_ident::dp01
	 *  
	 *  @params run
	 */
	def r01(){
		log.info "Parametros sdaep_previo/r01 ->"+params
		
		//Llamamos al servicio	
		def r = SDAEPService.previoR01(params)
		
		//Vemos si venimos de un 77bis
		params?.bisId? (session['77bis'] = params?.bisId) : (session['77bis'] = null)
		if (r.get('model')['sdaep']!=null)
		{
			if (r.get('model')['sdaep'] instanceof Long)
			{
				session['sdaep']=r.get('model')['sdaep']
			}
			else {
				session['sdaep']=r.get('model')['sdaep'].id
			}
		}
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}	

	/**
	 * Formulario
	 * 	- Lista de Siniestros previos para elegir (u otro)
	 *  - [Siguiente] -> cu02()
	 */
	def dp02() {
		params.get('model')
	}
	
		
	/**
	 * Llamado por dp02
	 * 
	 *  Guarda el relato. Si eligio un siniestro guarda
	 *  los datos en el sdaep y se va a SDAEP_diep::dp01,
	 *  si no eligio siniestro se va a SDAEP_ident::dp01
	 *  
	 *  @params relato
	 *  @params id (de siniestro, en blanco si es nuevo)
	 */
	def cu02(){
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.previoCU02(params, sdaep)
		
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}
}
