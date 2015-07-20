package cl.adexus.isl.spm

import org.apache.shiro.SecurityUtils

class DIATWEBController {
	
	def DIATWEBService
	def DenunciaService

    def index() { redirect(action: 'r00') }
	
	/**
	 * Busca el RUT de la empresa en base al login
	 * e inicializa DIATWEB
	 */
	def r00(){
		session['diatweb']=null
		def username=SecurityUtils.subject?.principal
		def r = DIATWEBService.r00(username)
		session['empleador']=r.get('model')['empleador']
		flash.put('model', r.get('model'))
		redirect (r.get('next'))
	}
	
	/**
	 * Formulario
	 * 	- RUN Trabajador
	 *  - Fecha Siniestro
	 *  - [Siguiente] -> r01()
	 */
	def dp01(){
		flash.get('model') 
	} 
	
	/**
	 * Llamado por dp01
	 *
	 *  Busca siniestros para run, fechasiniestro 
	 *  y el rut empleador
	 *
	 *  @params run
	 *  @params fechaSiniestro
	 */
	def r01(){
		//Llamamos al servicio
		def r = DIATWEBService.r01(params,session['empleador'])
		session['diatweb']=r.get('model')['diatweb']
		
		flash.put('model', r.get('model'))
		redirect (r.get('next'))
	}
	
	
	/**
	 * Formulario DIAT  Secci�n A y B
	 *
	 * - [Siguiente] -> cu02()
	 * 
	 */
	def dp02() {
		def comunas=Comuna.listOrderByDescripcion()
		def tipoCalles=TipoCalle.listOrderByCodigo()
		def tipoPropiedades=TipoPropiedadEmpresa.listOrderByCodigo()
		def tipoEmpresas=TipoEmpresa.listOrderByCodigo()
		def naciones = Nacion.listOrderByDescripcion()
		def etnias = Etnia.listOrderByCodigo()
		def tipoDuracionContratos = TipoDuracionContrato.listOrderByCodigo()
		def tipoRemuneraciones = TipoRemuneracion.listOrderByCodigo()
		def categoriaOcupaciones = CategoriaOcupacion.listOrderByCodigo()
		def tipoActividadEconomica = TipoActividadEconomica.listOrderByCodigo()
		
		//Vemos si podemos prellenar con algo
		def diat = session?.diatweb?.diat
		if (!diat) {
			diat = new DIAT()
			diat.setTrabajador(session?.diatweb?.trabajador)
			diat.setEmpleador(session?.diatweb?.empleador)
		}
		if (!diat.nacionalidadTrabajador)
			diat.nacionalidadTrabajador=Nacion.findByCodigo('152') //Chileno por defecto
	
		
		def model=[comunas: comunas,
				tipoCalles: tipoCalles,
				tipoPropiedades: tipoPropiedades,
				tipoEmpresas: tipoEmpresas,
				naciones: naciones,
				etnias: etnias,
				tipoDuracionContratos: tipoDuracionContratos,
				tipoRemuneraciones: tipoRemuneraciones,
				categoriaOcupaciones: categoriaOcupaciones,
				diat: diat,
				tipoActividadEconomica: tipoActividadEconomica]
		if(flash.get('model')){
			model + flash.get('model')
		}else{
			model
		}
		
		
	}
	
	/**
	 * Procesa la primera parte de la DIAT
	 *
	 */
	def cu02(){
		def r = DIATWEBService.cu02(params,session['diatweb'])
		flash.put('model', r.get('model'))
		redirect (r.get('next'))
	}
	
	/**
	 * Formulario DIAT  Secci�n C
	 *
	 * - [Siguiente] -> cu02_2()
	 *
	 */
	def dp02_2(){
		//DIAT Seccion C y D
		def comunas=Comuna.listOrderByDescripcion()
		def criterioGravedades=CriterioGravedad.listOrderByCodigo()
		def tiposAccidenteTrayecto=TipoAccidenteTrayecto.list()
		def tipoMedioPruebaAccidentes=TipoMedioPruebaAccidente.list()
		
		//Vemos si podemos prellenar con algo
		def diat=session.diatweb.diat
		if (!diat.fechaAccidente) {
			diat.fechaAccidente=session?.diatweb?.fechaSiniestro
		}
		
		def diatPrevias = DenunciaService.diatPrevias(session?.diatweb?.fechaSiniestro, session?.diatweb?.empleador, session?.diatweb?.trabajador);
		if (diatPrevias.size > 0) {
			diat.esAccidenteTrayecto = diatPrevias[0].esAccidenteTrayecto
		}
		
		def model=[comunas: comunas,
			criterioGravedades: criterioGravedades,
			tiposAccidenteTrayecto: tiposAccidenteTrayecto,
			tipoMedioPruebaAccidentes: tipoMedioPruebaAccidentes,
			diat: diat]
		
		if(flash.get('model')){
			model + flash.get('model')
		}else{
			model
		}
		
	}
	
	/**
	 * Procesa la segunda parte de la DIAT
	 *
	 */
	def cu02_2(){
		def username=SecurityUtils.subject?.principal
		def r = DIATWEBService.cu02_2(params,session['diatweb'],username)
		flash.put('model', r.get('model'))
		redirect (r.get('next'))
	}
	
	/**
	 * Caja que despliega el error
	 * @return
	 */
	def dp03(){
		def m=flash.get('model')
		log.info m['error']
		if(m['error']=='no_siniestro'){
			[mensaje: 'No existe un siniestro']
		}else if(m['error']=='denuncia_previa'){
			[mensaje: 'Ya hay una denuncia previa']
		}else {
			[mensaje: '�?']
		}
	}
	
	def cu03t(){
		redirect (controller:'nav')
	}
}
