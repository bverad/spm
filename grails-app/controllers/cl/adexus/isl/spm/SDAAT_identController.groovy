package cl.adexus.isl.spm

class SDAAT_identController {
	
	def SDAATService 
	def BISIngresoService
	
	def index() { redirect(action: 'dp01') }
	
	/**
	 * Formulario
	 * 	- RUN Trabajador
	 *  - Fecha Siniestro
	 *  - [Siguiente] -> r01()
	 */
	def dp01(){ 
		session['sdaat']=null
		params.get('model') 
	} 
	
	
	/**
	 * Llamado por dp01
	 *
	 *  Busca empresas del afiliado al SGA. Si existen va a dp02 para elegir
	 *  la empresa. Si el SGA retorna nulo o se cae va a dp03 para ingresar 
	 *  RUT empresa
	 *
	 *  @params run
	 *  @params fechaSiniestro
	 */
	def r01(){ 
		log.info("Ejecutando action r01")
		log.info("Datos recibidos : $params")
		
		//Vemos si venimos de un 77bis
		params?.bisId? (session['77bis'] = params?.bisId) : (session['77bis'] = null)
		
		//Llamamos al servicio 
		def r = SDAATService.identR01(params)
		params.put('model', r.get('model'))
		
		//Setea el session['sdaat'] con el id de la denuncia
		session['sdaat'] = r.get('model')['sdaatId']
		
		log.info("r01: next->" +  r.get('next'))
		forward action: r.next, params: params
	}
	
	/**
	 * Formulario: 
	 * 	- Lista de Empresas para elegir (u otro)
	 *  - [Siguiente] -> cu02()
	 */
	def dp02(){ 
		
		def sdaat = SDAAT.findById(session['sdaat'])
		def model = [ 'sdaat': sdaat ]
		
		if (params.get('model').empresas)
			model += [empresas:params.get('model').empresas]
	
		model
	}  //Elegir Empresa -> cu02

	
	def cu02(){
		log.info("Ejecutando action cu02")
		log.info("Datos recibidos : $params")
		//Vemos si eligio la opcion "otro"
		if(!params['rut'] || params['rut'].equals('') ){
			log.info("cu02: next->dp03")
			//redirect(action: 'dp03')
			forward action: 'dp03', params: params
		}else{
			//Buscamos si la empresa es ISL o no
			def sdaat = SDAAT.findById(session['sdaat'])
			def r = SDAATService.identR02(params,sdaat)
			log.info("cu02: next->" +  r.get('next'))
			//redirect (action: r.get('next'))
			forward action: r.next, params: params
		}
	}
	
	def dp03(){ 
		//Rescatamos el objeto sdaat
		def sdaat = SDAAT.findById(session['sdaat'])
		def model = [ 'empleador': sdaat?.empleador, 'sdaat': sdaat ]
		
		//Venimos desde un 77bis
		if (session['77bis']){
			def bis = Bis.findById(session['77bis'])
			model += ['rutEmpleador':bis.rutEmpleador]
		}
		
		log.info "MODEL ORIGINAL: ${model}"
		if (params.model) {
			model += params.model
		}
		log.info "MODEL MODIFICA: ${model}"
		
		model 
	} //RUT Empresa -> r02
	
	//Busca empresa
	def r02(){
		//Rescatamos el objeto sdaat
		def sdaat = SDAAT.findById(session['sdaat'])
		
		//Venimos desde un 77bis
		if (session['77bis']){
			def bis = Bis.findById(session['77bis'])
			params['rut'] = bis.rutEmpleador
			log.info "parametros en r02"+params
		}
		
		//Llamamos al servicio
		def r = SDAATService.identR02(params,sdaat)
		params.put('model', r.get('model'))
		//redirect (action: r.get('next'))
		log.info "r02 next->${r.next}"
		forward action: r.next, params: params
	}
 
	def dp04(){ 
		def sdaat = SDAAT.findById(session['sdaat'])
		def model = [ 'sdaat': sdaat ]
		if (params.model) {
			model += params.model
		}
		model
		//flash.get('model') 
		
	}  //Excepcion
	def cu04s(){ //Excepcionar
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
		def r = SDAATService.excepcionar(params,sdaat,'ident')
		params.put('model', r.get('model'))
		forward action: r.next.action, params: params
	}
	def cu04t(){ //Terminar
		//Rescatamos el objeto sdaat
		def sdaat = SDAAT.findById(session['sdaat'])
		
		//Venimos desde un 77bis
		if (session['77bis']){
			params['bisId'] = (session['77bis'])
		}
						
		//Llamamos al servicio
		def r = SDAATService.terminar(params,sdaat,'Trabajador no Afiliado a ISL')
		forward controller: r.next.controller, action: r.next.action
	}
	
	//Busca siniestro preexistente
	def r03(){
		flash.mensajes = null
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.identR03(params,sdaat)
		log.info("r03: sdaat: " + sdaat)
		log.info("r03: next->" +  r.get('next'))
		//redirect (r.get('next'))
		forward controller: r.next.controller, action: r.next.action
	}
}
