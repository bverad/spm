package cl.adexus.isl.spm

import cl.adexus.isl.spm.domain.OpaPDF
import cl.adexus.isl.spm.domain.Constantes

import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import java.text.DateFormat
import java.text.SimpleDateFormat

import org.apache.shiro.SecurityUtils

import grails.converters.JSON

class SDAAT_opaController {
	
	def SDAATService
	def PDFService
	def UsuarioService
	
	def index() {}
	
	/**
	 * Busca opa previa para siniestro
	 *
	 * Si es que encuentra -> dp03
	 * Si no encuentra -> dp01 
	 */
	def r01(){
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.opaR01(params, sdaat)
		
		params.put('model', r.get('model'))
		def next = r.get('next')
		next.params=params
		forward (next)
	}
	
	/**
	 * Formulario
	 *  - [Generar OPA] -> dp02()
	 *  - [Terminar] -> cu01termina()
	 */
	def dp01(){ 
		def sdaat = SDAAT.findById(session['sdaat'])
		def model = [ 'sdaat': sdaat]
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
		def sdaat = SDAAT.findById(session['sdaat'])
		def listadoRegiones = Region.listOrderByCodigo()
		def model=['listadoRegiones': listadoRegiones, 'sdaat': sdaat ]
			
	 }
	
	/**
	 * Emite la OPA y la Guarda
	 *
	 * Si se puede -> dp03
	 * Si no se puede -> dp02 
	 */
	def r02(){
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.opaR02(params, sdaat)
		params.put('model', r.get('model'))
		def next = r.get('next')
		next.params=params
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
		def sdaat = SDAAT.findById(session['sdaat'])
		
		if (!sdaat.opa.isAttached()) {
			sdaat.opa.attach()
		}
		def hayOpaPrevia=(sdaat.opa != null) ? 'No' : 'Si'
		
		def model=[hayOpaPrevia: hayOpaPrevia, 'sdaat': sdaat ]
		
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
		def sdaat = SDAAT.findById(session['sdaat'])
		def b=SDAATService.opaR03(params, sdaat)
		 
		  if(b!=null){
			  response.setContentType("application/pdf")
			  response.setHeader("Content-disposition", "attachment; filename=OPA.pdf")
			  response.setContentLength(b.length)
			  response.getOutputStream().write(b)
		  } else {
			  //No se genera el PDF
			  
		  }
	 }
	
	/**
	 * Termina sin imprimir
	 */
	def cu01termina() {
		//Llamamos al servicio
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.terminar(params, sdaat,'no imprime opa')
		forward (r.get('next'))
	}

	/**
	 * Termina feliz
	 */
	def end() {
		def sdaat = SDAAT.findById(session['sdaat'])
		def r = SDAATService.terminar(params, sdaat,'ok')
		forward (r.get('next'))
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
		def prestadorId
		
		//Si el usuario es prestador.
		if(SecurityUtils.getSubject().hasRole("prestador")){
			def username=SecurityUtils.subject?.principal
			//Busca el prestador en base al login.
			def usuario=UsuarioService.getUsuario(username)
			log.info "Usuario:"+username+" Rut Prestador:"+usuario.empresa.rut_empresa
			def personaJuridica = PersonaJuridica.findByRut(usuario.empresa.rut_empresa)
			def prestador=Prestador.findByPersonaJuridica(personaJuridica)
			log.info "Prestador:"+prestador
			prestadorId=prestador.id
		}else{
			prestadorId=Long.parseLong(params.prestadorId)
		}
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
