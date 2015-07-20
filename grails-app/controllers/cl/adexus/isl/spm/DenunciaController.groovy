package cl.adexus.isl.spm

class DenunciaController {

	def DenunciaService
	
    def index() { redirect(action: 'r01') }

	def r01(){
		//Llamamos al servicio
		def r = DenunciaService.getDenuncia(params)
		params.put('model', r.model)
		def next = r.next
		next.params = params		
		forward (next)
	}

	def diatView() {
		log.info "diatView params: ${params}"
		def tiposAccidenteTrayecto=TipoAccidenteTrayecto.list()
		def tipoCalles=TipoCalle.list()
		def comunas=Comuna.list()
		def tipoPropiedades=TipoPropiedadEmpresa.list()
		def tipoEmpresas=TipoEmpresa.list()
		def naciones = Nacion.list()
		def etnias = Etnia.list()
		def tipoDuracionContratos = TipoDuracionContrato.list()
		def tipoRemuneraciones = TipoRemuneracion.list()
		def categoriaOcupaciones = CategoriaOcupacion.list()
		def criterioGravedades=CriterioGravedad.list()
		def tipoMedioPruebaAccidentes=TipoMedioPruebaAccidente.list()
		def calificaciones=CalificacionDenunciante.list()
		def sexos=[[codigo:'F', descripcion:"Femenino"], [codigo:'M', descripcion:"Masculino"]]
		def esTrabajoHabitualList = [[codigo:true, descripcion:"Si"], [codigo:false, descripcion:"No"]]
		def tipoActividadEconomica = TipoActividadEconomica.listOrderByDescripcion()
		def tipoAccidentes = [['codigo':'1','descripcion':'Trabajo'],['codigo':'2','descripcion':'Trayecto']]
		//Pasamos los datos al view
		def model=[	  comunas					: comunas
					, tipoCalles				: tipoCalles
					, tipoPropiedades			: tipoPropiedades
					, tipoEmpresas				: tipoEmpresas
					, naciones					: naciones
					, etnias					: etnias
					, tipoDuracionContratos		: tipoDuracionContratos
					, tipoRemuneraciones		: tipoRemuneraciones
					, categoriaOcupaciones		: categoriaOcupaciones
					, tipoMedioPruebaAccidentes	: tipoMedioPruebaAccidentes
					, calificaciones			: calificaciones
					, sexos						: sexos
					, tipoAccidentes			: tipoAccidentes
					, criterioGravedades		: criterioGravedades
					, tiposAccidenteTrayecto	: tiposAccidenteTrayecto
					, esTrabajoHabitualList		: esTrabajoHabitualList
					, tipoActividadEconomica	: tipoActividadEconomica ]
		if(params.get('model')){
			model += params.get('model')
		}else{
			model
		}
	}
	
	def diepView() {
		def tiposAccidenteTrayecto=TipoAccidenteTrayecto.list()
		def tipoCalles=TipoCalle.list()
		def comunas=Comuna.list()
		def tipoPropiedades=TipoPropiedadEmpresa.list()
		def tipoEmpresas=TipoEmpresa.list()
		def naciones = Nacion.list()
		def etnias = Etnia.list()
		def tipoDuracionContratos = TipoDuracionContrato.list()
		def tipoRemuneraciones = TipoRemuneracion.list()
		def categoriaOcupaciones = CategoriaOcupacion.list()
		def criterioGravedades=CriterioGravedad.list()
		def tipoMedioPruebaAccidentes=TipoMedioPruebaAccidente.list()
		def calificaciones=CalificacionDenunciante.list()
		def sexos=[[codigo:'F', descripcion:"Femenino"], [codigo:'M', descripcion:"Masculino"]]
		def esAntecedenteCompaneroList = [[codigo:'true', descripcion:"Si"], [codigo:'false', descripcion:"No"]]
		def esAntecedentePrevioList = [[codigo:'true', descripcion:"Si"], [codigo:'false', descripcion:"No"]]
		def tipoActividadEconomica = TipoActividadEconomica.listOrderByDescripcion()
		def tipoAccidentes = [['codigo':'1','descripcion':'Trabajo'],['codigo':'2','descripcion':'Trayecto']]
	
		//Pasamos los datos al view
		def model=[	comunas: comunas,
					tipoCalles: tipoCalles,
					tipoPropiedades: tipoPropiedades,
					tipoEmpresas: tipoEmpresas,
					naciones: naciones,
					etnias: etnias,
					tipoDuracionContratos: tipoDuracionContratos,
					tipoRemuneraciones: tipoRemuneraciones,
					categoriaOcupaciones: categoriaOcupaciones,
					tipoMedioPruebaAccidentes: tipoMedioPruebaAccidentes,
					calificaciones: calificaciones,
					sexos: sexos,
					tipoAccidentes: tipoAccidentes,
					criterioGravedades:criterioGravedades,
					tiposAccidenteTrayecto: tiposAccidenteTrayecto,
					esAntecedenteCompaneroList:esAntecedenteCompaneroList,
					esAntecedentePrevioList: esAntecedentePrevioList,
					tipoActividadEconomica: tipoActividadEconomica]
		if(params.get('model')){
			model + params.get('model')
		}else{
			model
		}
	}
	
	def r02() {
		def b=DenunciaService.getOP(params)
		
		 if(b!=null){
			 response.setContentType("application/pdf")
			 response.setHeader("Content-disposition", "attachment; filename=Re-Impresion.pdf")
			 response.setContentLength(b.length)
			 response.getOutputStream().write(b)
		 } else {
			 //No se genera el PDF
			 
		 }

	}
	
	def volverSiniestro(){
		forward controller:"siniestro", action: "cu02", params: [id: params.siniestroId]
	}
	
	def volver2URL () {
		// Por si me llaman los visores de DIAT desde otro lado, así podemos retornarlos
		def volver      = params.volver
		def volverArray = volver.split(":")
		forward controller: volverArray[0], action: volverArray[1], params: [siniestroId: volverArray[2]]
	}
}
