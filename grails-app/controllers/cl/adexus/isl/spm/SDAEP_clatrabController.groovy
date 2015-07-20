package cl.adexus.isl.spm

class SDAEP_clatrabController {
	
    def SDAEPService

	def index() { redirect(action: 'r01') }

	/**
	 * 	Determina si la empresa tiene solo empleados
	 *  Si es asi, derecho a la SDAEP_diep::dp01
	 *  Si no es asi hay que ir al cuestionario en dp01
	 *
	 */
	def r01() {
		flash.mensajes = null
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.clatrabR01(params, sdaep)
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
		// Busca los actividades  para el Cuestionario Obrero
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def acts=ActividadTrabajador.findAllByCodigoNotInList(["OE", "OO"])
		def model=[ actividadesTrabajador: acts, sdaep: sdaep ]
		if(params.get('model')){
			model += params.get('model')
		}else{
			model
		}
	}
	
	
	
	/**
	 * 	Procesa el cuestionario de Obrero 
	 *  para determinar si es empleado.
	 *  Si es asi, a dp03 (Calificacion de Origen reducida)
	 *  Si no es asi a dp02 que termina o excepciona
	 *
	 */
	def r02(){
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.clatrabR02(params, sdaep)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}
	
	/**
	 * Formulario Termino/Excepcion
	 * 	- Motivo
	 * 	- Autorizador
	 *  - [Terminar ] -> cu03t()
	 *  - [Siguiente] -> cu03s()
	 */
	def dp02() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def model = [ 'sdaep': sdaep ]
	}
	
	/**
	 * Llamado por dp02
	 * Excepciona y continua
	 *
	 *  @params motivo
	 *  @params autorizador
	 */
	def cu02s(){ //Excepcionar
		//Llamamos al servicio
		flash.mensajes = null
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.excepcionar(params, sdaep,'clatrab')
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}
	/**
	 * Llamado por dp02
	 * Informa y termina
	 *
	 */
	def cu02t(){ //Terminar
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
		
		//Venimos desde un 77bis
		if (session['77bis']){
			params['bisId'] = (session['77bis'])
		}
		
		def r = SDAEPService.terminar(params, sdaep,'Trabajador es Obrero')
		forward (r.get('next'))
	}
	
	
	
	/**
	 * Formulario Calificacion Origen (encubierto)
	 * 
	 * 
	 * 
	 *  - [Siguiente] -> cu03s()
	 */
	def dp03() {
		//Vemos si podemos prellenar con algo
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def tipoEnfermedades=TipoEnfermedad.list()
		def cuestionarioOrigen = sdaep?.cuestionarioOrigen
		if (!cuestionarioOrigen) {
			cuestionarioOrigen = new CuestionarioCalificacionOrigenEnfermedadProfesional()
			cuestionarioOrigen?.pregunta5 = sdaep?.relato
		} 
		def model=[
			tipoEnfermedades: tipoEnfermedades,
			cuestionarioOrigen: cuestionarioOrigen
			,sdaep: sdaep]			
		if(params.get('model')){
			model += params.get('model')
		}else{
			model
		}
	}
	
	/**
	 * Llamado por dp03
	 * Guarda Cuestionario Origen
	 *
	 *  @params 
	 */
	def cu03(){ 
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.clatrabCU03(params, sdaep)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}

		
}