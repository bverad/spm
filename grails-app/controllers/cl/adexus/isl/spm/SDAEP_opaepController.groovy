package cl.adexus.isl.spm

import cl.adexus.isl.spm.domain.OpaepPDF

import cl.adexus.isl.spm.domain.Constantes

import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import java.text.DateFormat
import java.text.SimpleDateFormat


import grails.converters.JSON


class SDAEP_opaepController {
	
	def SDAEPService
	def PDFService

    def index() { }
	
	/**
	 * Va a buscar una OPAEP previa para el siniestro 
	 * 
	 * Si la encuentra
	 * 	-y esta vigente -> dp03
	 *  -y no esta vigente -> dp04
	 * Si no la encuentra
	 *  - y es primera denuncia -> dp01
	 *  - y no es primera denuncia -> dp04
     */
	def r01(){
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def r = SDAEPService.opaepR01(params, sdaep)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}
	
	/**
	 * Formulario
	 * [Generar OPA] --> dp02() 
	 * [Terminar] --> cu01termina()
	 */
	def dp01() { 
		def sdaep = SDAEP.findById(session['sdaep'])
		def model = [ sdaep: sdaep ]
	}
	
	/**
	 * Termina sin generar
	 */
	def cu01termina() {
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
		
		def r = SDAEPService.terminar(params, sdaep,'no genera opa')
		forward (r.get('next'))
	}
	
	
	/**
	 * Formulario
	 * 	- Comuna
	 *  - Prestador
	 *  - Centro Salud
	 *  - [Emitir OPA] -> r02()
	 */
	def dp02(){ 		
			// Cargar las regiones
		def sdaep = SDAEP.findById(session['sdaep'])
		def listadoRegiones = Region.listOrderByCodigo()

		// Pasamos los datos al view
		def model=['listadoRegiones': listadoRegiones, sdaep: sdaep ]
		if(params.get('model')){
			model + params.get('model')
		}else{
			model
		}
	}		

	/**
	 * Emite la OPA y la Guarda
	 *
	 * Si se puede -> dp03
	 * Si no se puede -> dp02
	 */
	def r02(){
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.opaepR02(params, sdaep)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}
	
	/**
	 * Formulario
	 * - BOX: opa previa
	 * - [Terminar] -> end()
	 * - [Imprimir] -> createPdfOPA()
	 * @return
	 */
	def dp03(){
		def sdaep = SDAEP.findById(session['sdaep'])
		if (!sdaep.opaep.isAttached()) {
			sdaep.opaep.attach()
		}
		log.info "mma:"+sdaep.opaep
		def hayOpaPrevia=(sdaep.opaep!=null)?'No':'Si'
		
		def model=[ hayOpaPrevia: hayOpaPrevia, sdaep: sdaep ]
		
		if(params.get('model')){
			model + params.get('model')
		}else{
			model
		}
	}
	
	
	/**
	 * Genera PDF de OPA
	 */
	def r03() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def b=SDAEPService.opaepR03(params, sdaep)
		 
		  if(b!=null){
			  response.setContentType("application/pdf")
			  response.setHeader("Content-disposition", "attachment; filename=OPA.pdf")
			  response.setContentLength(b.length)
			  response.getOutputStream().write(b)
		  }
	 }

	def dp04() {
		flash.get('model')
	}
	
	def r04() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.opaepR04(params, sdaep)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)   
	}
	
	/**
	 * Termina sin imprimir
	 */
	def cu02termina() {
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.terminar(params, sdaep,'no imprime opa')
		forward (r.get('next'))
	}

	/**
	 * Termina habiendo hecho reingreso
	 */
	def cu03termina() {
		//Llamamos al servicio
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.terminar(params, sdaep,'solicita re-ingreso')
		forward (r.get('next'))
	}
		
	/**
	 * Termina feliz
	 */
	def end() {
		def sdaep = SDAEP.findById(session['sdaep'])
		def r = SDAEPService.terminar(params,sdaep,'ok')
		forward (r.get('next'))
	}
	
	def createPdfOPAEP() {
		def b=SDAEPService.opaepGenPdf(params,session['sdaep'])
		 
		  if(b!=null){
			  response.setContentType("application/pdf")
			  response.setHeader("Content-disposition", "attachment; filename=OPAEP.pdf")
			  response.setContentLength(b.length)
			  response.getOutputStream().write(b)
		  } else {
			  //No se genera el PDF
			  
		  }
	 }
	
	//AJAX
	def prestadorPorRegionJSON() {
		def regionId = params.regionId
		log.info("prestadorPorRegionJSON + regionId : " + regionId)
		
		def fechaHoy = new Date()
		def prestadores = Prestador.executeQuery(
			"SELECT DISTINCT csc.centroSalud.prestador " +
			"FROM	CentroSaludEnConvenio csc " +
			"WHERE	(:fechaHoy >= csc.convenio.inicio AND :fechaHoy <= csc.convenio.termino) " +
			"AND	csc.centroSalud.comuna.provincia.region.codigo = :regionId " +
			"AND	csc.convenio.esActivo = true " +
			"AND	csc.centroSalud.esActivo = true "+
			"AND 	csc.centroSalud.prestador.esActivo = true "
			, [ regionId	: regionId
			  , fechaHoy	: fechaHoy ]);
		
		JSON.use("deep") { render prestadores as JSON }
		
	}
	
	def prestadoresPorComunaJSON(){ 
		def comunaId=params.comunaId
		//Llamamos al servicio
		def fechaHoy = new Date()
		def p = Prestador.executeQuery(
			"SELECT DISTINCT csc.centroSalud.prestador " +
			"FROM	CentroSaludEnConvenio csc " +
			"WHERE	(:fechaHoy >= csc.convenio.inicio AND :fechaHoy <= csc.convenio.termino) " +
			"AND	csc.centroSalud.comuna.id = :comunaId " +
			"AND	csc.convenio.esActivo = true " +
			"AND	csc.centroSalud.esActivo = true "+
			"AND 	csc.centroSalud.prestador.esActivo = true "
			, [ comunaId	: comunaId
			  , fechaHoy	: fechaHoy ]);
		JSON.use("deep"){ render p as JSON }
	}
	
	def centroSaludPorPrestadorJSON(){
		def prestadorId=Long.parseLong(params.prestadorId)
		def regionId=params.regionId
		//Llamamos al servicio
		def fechaHoy = new Date()
		def cs = CentroSalud.executeQuery(
				"SELECT DISTINCT csc.centroSalud " + 
				"FROM	CentroSaludEnConvenio csc " +
				"WHERE	(:fechaHoy >= csc.convenio.inicio AND :fechaHoy <= csc.convenio.termino) " +
				"AND	csc.centroSalud.prestador.id = :prestadorId " +
				"AND	csc.centroSalud.comuna.provincia.region.codigo = :regionId " +
				"AND	csc.convenio.esActivo = true " +
				"AND	csc.centroSalud.esActivo = true "
				, [ prestadorId	: prestadorId
				  , regionId	: regionId
				  , fechaHoy	: fechaHoy ]);

		JSON.use("deep"){ render cs as JSON }
	}
}