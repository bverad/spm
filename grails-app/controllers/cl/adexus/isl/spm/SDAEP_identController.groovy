package cl.adexus.isl.spm

class SDAEP_identController {
	
	def SDAEPService

	def index() { redirect(action: 'r01') }
	
	/**
	 * 	Determina los empleadores del trabajador. 
	 *  Si existen va a dp01 para elegir. Si no existen 
	 *  va a dp02 para ingresar el RUT
	 *  
	 *  @params run   
	 *
	 */
	def r01() {
		log.info "PARAMETROS sdaep_ident/r01"+params
		
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def model=params.get('model')
		if(!session['sdaep']){
			sdaep = SDAAT.findById(model['sdaep'])
			session['sdaep'] = model['sdaep']
		}
		
		//Venimos desde un 77bis
		if (session['77bis']){
			params['bisId'] = (session['77bis'])
		}
		
		//Llamamos al servicio
		def r = SDAEPService.identR01(params,sdaep)
		
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}
	
	/**
	 * Formulario
	 * 	- Lista de Empresas para elegir (u otro)
	 *  - [Siguiente] -> r02()
	 */
	def dp01() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def model = [ 'sdaep': sdaep ]

		if (params.get('model')?.empresas)
			model += [empresas:params.get('model').empresas]
	}
	
	/**
	 * Llamado por dp01
	 * Busca el rut  elegido. Si es ISL va a SDAEP_clatrab::dp01
	 * Si no eligio rut o el RUT no es ISL va a dp02 para ingresar
	 * rut de empleador
	 *
	 *  @params rut (de empleador, blanco si es otro
	 */
	def r02(){
		//Vemos si eligio la opcion "otro"
		def sdaep = SDAEP.findById(session['sdaep'])
		
		//Venimos desde un 77bis
		if (session['77bis']){
			def bis = Bis.findById(session['77bis'])
			params['rut'] = bis.rutEmpleador
			log.info "parametros en r02"+params
		}
		
		if(!params['rut'] || params['rut'].equals('') ){
			forward (action: 'dp02')
		}else{
			//Buscamos si la empresa es ISL o no
			def r = SDAEPService.identR02(params, sdaep)
			
			params.put('model', r.get('model'))
			def next = r.next
			next.params = params
			forward (next)   
		}
	}
	
	/**
	 * Formulario
	 * 	- RUT Empleador
	 *  - [Siguiente] -> r02()
	 */
	def dp02() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def model = [ 'sdaep': sdaep ]
		
		//Venimos desde un 77bis
		if (session['77bis']){
			def bis = Bis.findById(session['77bis'])
			model += ['rutEmpleador':bis.rutEmpleador]
		}	
		if (params.model) {
			model += params.model
		}
		log.info "modelo "+model
		model
	}
	
	/**
	 * Formulario Termino/Excepcion
	 * 	- Motivo
	 * 	- Autorizador
	 *  - [Terminar ] -> cu03t()
	 *  - [Siguiente] -> cu03s()
	 */
	def dp03() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def model = [ 'sdaep': sdaep ]
	}
	
	/**
	 * Llamado por dp03
	 * Excepciona y continua
	 *
	 *  @params motivo
	 *  @params autorizador
	 */
	def cu03s(){ //Excepcionar
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
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

		def r = SDAEPService.excepcionar(params, sdaep,'ident')
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}
	/**
	 * Llamado por dp03
	 * Informa y termina
     *
	 */
	def cu03t(){ //Terminar
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
		
		//Venimos desde un 77bis
		if (session['77bis']){
			params['bisId'] = (session['77bis'])
		}
		
		def r = SDAEPService.terminar(params, sdaep,'Trabajador no Afiliado a ISL')
		forward (r.get('next'))
	}
	
	
}
