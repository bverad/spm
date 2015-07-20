package cl.adexus.isl.spm

import cl.adexus.helpers.FormatosHelper
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import grails.converters.JSON
import grails.gorm.DetachedCriteria

import java.text.Format
import java.text.SimpleDateFormat
import java.util.logging.Logger;

import jxl.Cell
import jxl.Sheet
import jxl.SheetSettings;
import jxl.Workbook

import org.apache.commons.fileupload.FileItemIterator
import org.apache.commons.fileupload.FileItemStream
import org.apache.commons.fileupload.servlet.ServletFileUpload
import org.apache.commons.io.monitor.FileEntry
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import cl.adexus.helpers.DataSourceHelper;



class ArancelesController {
	
	def ArancelService
	def PrestadorService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		log.info("************* Ejecutando accion index ****************")
		log.info("Datos obtenidos : ${params}")
		def arancel
		def prestacionesCargadas = []
		def desde
		def hasta
		
		if(params.get('model')) {
			if(params.get('model').arancel){
				arancel = params.get('model').arancel
			}
			if(params.get('model').prestacionesCargadas){
				prestacionesCargadas = params.get('model').prestacionesCargadas
			}
			if(params.get('model').desde){
				desde = params.get('model').desde
			}
			if(params.get('model').hasta){
				hasta = params.get('model').hasta
			}
		}
		
		
		def model = ['arancel': arancel, 'prestacionesCargadas': prestacionesCargadas, 'desde': desde, hasta: hasta]
		
		model
		
    }

    def list(Integer max) {
		log.info "************* Ejecutando accion list ****************"
        params.max = Math.min(max ?: 10, 100)
        [arancelesInstanceList: Aranceles.list(params), arancelesInstanceTotal: Aranceles.count()]
    }

    def create() {
		log.info "************* Ejecutando accion create ****************"
        [arancelesInstance: new Aranceles(params)]
		
    }

    def save() {
		log.info "************* Ejecutando accion save ****************"
		log.info("Datos obtenidos : ${params}")
		String fileLabel = params.fileLabel
		MultipartFile uploadedFile = null
		String fileName = ""
		String originalFileName
		def col = 0
		def row = 1
		def grupo
		def subGrupo
		def titulo
		def glosa
		def codigo
		def correlativo
		def errores = []
		def gruposCargados = 0
		def subGruposCargados = 0
		def arancelesBaseCargados = 0
		def arancel
		//def fechaHastaAnterior
		def fechaDesde
		def fechaHasta
		def model
		def isCargaArancelHistorico = params?.isCargaArancelHistorico
		def prestacionesCargadas = []
		/*
		 * Validamos que la fecha ingresada sea igual o superior a la última hasta que se haya establecido
		 */
		if (params?.desde)
			fechaDesde = params?.desde
			fechaHasta = params?.hasta
			
			log.debug("isCargaArancelHistorico : $isCargaArancelHistorico")
			//valida relacion entre fecha de inicio y fin solo si la carga es historica
			if(isCargaArancelHistorico == "on"){
				log.debug("procesando nueva carga")
				log.debug("fecha inicio vigencia : ${fechaDesde} - fecha fin vigencia: ${fechaHasta}")
				//fechaHasta no debe ser inferior a fecha desde, ni fecha desde superior a fecha hasta
				if(fechaHasta < fechaDesde){
					log.info("La fecha de fin de vigencia del arancel, no debe ser inferior su fecha de inicio")
					arancel = new Aranceles()
					arancel.errors.reject("cl.adexus.isl.spm.aranceles.fechaDesde.fail")
					model = [arancel: arancel, desde:fechaDesde, hasta:fechaHasta]
					params.put('model', model)
					render(view:'index', model: model)
					return
				}
			}

			//instancia para registro de errores
			def prestador = new Prestador()
			try{
				//verifica si el request es un archivo multipart
				if (request instanceof MultipartHttpServletRequest){
					//Get the file's name from request
					fileName = request.getFileNames()[0]
					
					//Get a reference to the uploaded file.
					uploadedFile = request.getFile(fileName)
					originalFileName = uploadedFile.originalFilename																				
					
					if (uploadedFile.empty) {
						log.info("ERROR: Archivo vacío no puede ser cargado.")
						prestador.errors.reject("Archivo vacio no puede ser cargado")
						params.p = prestador
						//errores[errores.size()] = "ERROR: Archivo vacío no puede ser cargado."
						model = [p: prestador, desde:fechaDesde, hasta:fechaHasta]
						render (view: 'index', model: model)
						return
					}
					
					
					def values = originalFileName.split("\\.")
					log.info("extension del archivo: " + values[1])
					
					if (values[1] != "xls") {
						log.info("archivo de aranceles con extension erronea")
						prestador.errors.reject("cl.adexus.isl.spm.Aranceles.extension.fail")
						params.p = prestador						
						model = [p: prestador, desde:fechaDesde, hasta:fechaHasta]
						render (view: 'index', model: model)
						return
					}
					
					log.info("SVC: File size :" + uploadedFile.size)
		
				}else {
					prestador.errors.reject("El formulario no es MultipartHttpServletRequest")
					log.info("ERROR: El formulario no es MultipartHttpServletRequest")
					model = [p: prestador, desde:fechaDesde, hasta:fechaHasta]
					render (view: 'index', model: model)
					return
					//errores[errores.size()] = "ERROR: El formulario no es MultipartHttpServletRequest"
				}

				
				log.info("Archivo excel :  " + uploadedFile.originalFilename)
				InputStream inputStream = uploadedFile.inputStream
				Workbook workbook = Workbook.getWorkbook(inputStream)
				Sheet sheet = workbook.getSheet(0)
				
				
				/*log.info("Eliminando cargas anteriores no aprobadas")
				ArancelBase.executeUpdate("DELETE FROM ArancelBase ab WHERE ab.cargaAprobada=false")*/
				
				
				while (sheet.getCell(col, row)?.getContents() != null && sheet.getCell(col, row)?.getContents() != "") {
					grupo = sheet.getCell(1, row).getContents()
					subGrupo = sheet.getCell(2, row).getContents()
					titulo = sheet.getCell(5, row).getContents()
					glosa = sheet.getCell(14, row).getContents()
					codigo = grupo + subGrupo + sheet.getCell(3, row).getContents()
					//generando correlativo
					if (sheet.getCell(4, row).getContents()?.isInteger())
						correlativo = Integer.parseInt(sheet.getCell(4, row).getContents())
					
					//Agregamos los Grupos en caso que no existan y actualizamos en el caso que ya exista
					if (grupo != "" && grupo != "00" && subGrupo == "00" && titulo == "1") {
						def grpDomain = Grupo.findByCodigo(grupo)
						
						if (grpDomain == null)
							grpDomain = new Grupo([codigo: grupo, descripcion: glosa])
						else
							grpDomain.descripcion = glosa
							
						try {
							log.info("GRUPO : " + glosa)
							grpDomain.save(flush:true)
							gruposCargados++
						}catch (Exception e){
							//errores[errores.size()] = "ERROR: fila:${row} Exception al grabar el GRUPO: " + e.getMessage()
							prestador.errors.reject("Error no controlado en la lectura de archivo: ${e.message}, contacte a soporte informatico")
							log.info("CAIDA EN : fila: " + row + " grp:" + grupo + " sbgrp: " + subGrupo + " tit: " + titulo + " glosa: " + glosa)
							log.info("Error no controlado en la lectura de archivo: ${e.message}, contacte a soporte informatico")
							e.printStackTrace()
						}
						
					}
					//Agregamos los SubGrupos en caso que no existan y actualizamos en el caso que ya exista
					if (grupo != "" && grupo != "00" && subGrupo != "00" && titulo == "1") {
						log.debug("determinando existencia de grupo")
						def grpDomain = Grupo.findByCodigo(grupo)	
						log.debug("valor grupo : ${grpDomain}" )
											
						def sbgrpDomain = SubGrupo.findByCodigoAndGrupo(subGrupo, grpDomain)
						log.debug("valor subgrupo : ${sbgrpDomain}" )
																										
						if (sbgrpDomain == null) {
							log.info("CREANDO NUEVO SUBGRUPO")
							sbgrpDomain = new SubGrupo([codigo: subGrupo, grupo: grpDomain, descripcion: glosa])
						} else {
							log.info("ACTUALIZANDO SUBGRUPO")
							log.info("properties: " + sbgrpDomain.properties)
							sbgrpDomain.descripcion = glosa
						}	
						
						try {
							sbgrpDomain.save(flush:true)
							subGruposCargados++
						}catch (Exception e){
							errores[errores.size()] = "ERROR: fila:${row} Exception al grabar el SUBGRUPO: " + e.getMessage()
							prestador.errors.reject("Error no controlado en la lectura de archivo: ${e.message}, contacte a soporte informatico")
							log.info("CAIDA EN : fila: " + row + " grp:" + grupo + " sbgrp: " + subGrupo + " tit: " + titulo + " glosa: " + glosa)
							log.info("Error no controlado en la lectura de archivo: ${e.message}, contacte a soporte informatico")
							e.printStackTrace()
						}
						
					}	
					
					//Agregamos los SubGrupos en caso que no existan y actualizamos en el caso que ya exista
					if (titulo == "5") {
						def grpDomain = Grupo.findByCodigo(grupo)												
						def sbgrpDomain = SubGrupo.findByCodigoAndGrupo(subGrupo, grpDomain)
						
						if (sbgrpDomain == null) {
							log.info("ERROR: Fallo al traer el subgrupo para el grupo: " + grupo + " sb: " + subGrupo)
						}
						
												
						if (sbgrpDomain == null) {
							log.info("Creando nuevo SubGrupo")
							sbgrpDomain = new SubGrupo([codigo: subGrupo, grupo: grpDomain, descripcion: grpDomain?.descripcion])
							try {
								log.info("SUBGRUPO : " + glosa)
								sbgrpDomain.save(flush:true)
								subGruposCargados++
							}catch (Exception e){
								//errores[errores.size()] = "ERROR: fila:${row} Exception al grabar el SUBGRUPO: " + e.getMessage()
								prestador.errors.reject("Error no controlado en la lectura de archivo: ${e.message}, contacte a soporte informatico")
								log.info("CAIDA EN : fila: " + row + " grp:" + grupo + " sbgrp: " + subGrupo + " tit: " + titulo + " glosa: " + glosa)
								log.info("Error no controlado en la lectura de archivo: ${e.message}, contacte a soporte informatico")
								e.printStackTrace()
							}
						}
						
						//validando unicidad
						//def prestacion = ArancelBase.findByCodigoAndDesde(codigo, params?.desde)
						def prestacionList = ArancelBase.executeQuery("SELECT ab FROM ArancelBase ab WHERE ab.codigo = ? and ab.desde = ? and ab.hasta = ?", [codigo, params?.desde, params?.hasta])
						def prestacion
						if (prestacionList.size() == 0) {
							log.info("Creando arancel base")
							prestacion = new ArancelBase()	
							prestacion.codigo = codigo
							arancelesBaseCargados++
						} else {
							prestacion = prestacionList.get(0)
							log.info("Actualizando arancel base")
						}
						
						log.info("valor sbgrpDomain : ${sbgrpDomain}")
						prestacion.subGrupo = sbgrpDomain
						prestacion.activado = true
						
						if (correlativo > 0)
							prestacion.glosa = prestacion.glosa + " " + glosa
						else
							prestacion.glosa = glosa
						
						if (correlativo == 0) {
							if (sheet.getCell(15, row).getContents()?.isInteger()) {
								//prestacion.valorN1 = Integer.parseInt(sheet.getCell(15, row).getContents())
								prestacion.totalFonasaN1 = Integer.parseInt(sheet.getCell(15, row).getContents())
							} else {
								prestacion.totalFonasaN1 = 0
							}
							
							if (sheet.getCell(17, row).getContents()?.isInteger()) {
								//prestacion.valorN2 = Integer.parseInt(sheet.getCell(17, row).getContents())
								prestacion.totalFonasaN2 = Integer.parseInt(sheet.getCell(17, row).getContents())
							} else {
								prestacion.totalFonasaN2 = 0
							}
							
							if (sheet.getCell(19, row).getContents()?.isInteger()) {
								//prestacion.valorN3 = Integer.parseInt(sheet.getCell(19, row).getContents())
								prestacion.totalFonasaN3 = Integer.parseInt(sheet.getCell(19, row).getContents())
							} else {
								prestacion.totalFonasaN3 = 0
							}
	
							if (sheet.getCell(6, row).getContents()?.isInteger())
								prestacion.anestesiaN1 = Integer.parseInt(sheet.getCell(6, row).getContents())
								
							if (sheet.getCell(8, row).getContents()?.isInteger())
								prestacion.anestesiaN2 = Integer.parseInt(sheet.getCell(8, row).getContents())
							
							if (sheet.getCell(10, row).getContents()?.isInteger())
								prestacion.anestesiaN3 = Integer.parseInt(sheet.getCell(10, row).getContents())
	
							if (sheet.getCell(21, row).getContents()?.isInteger())
								prestacion.cirujano1N1 = Integer.parseInt(sheet.getCell(21, row).getContents())
							if (sheet.getCell(22, row).getContents()?.isInteger())
								prestacion.cirujano1N2 = Integer.parseInt(sheet.getCell(22, row).getContents())
							if (sheet.getCell(23, row).getContents()?.isInteger())
								prestacion.cirujano1N3 = Integer.parseInt(sheet.getCell(23, row).getContents())
							if (sheet.getCell(24, row).getContents()?.isInteger())
								prestacion.cirujano2N1 = Integer.parseInt(sheet.getCell(24, row).getContents())
							if (sheet.getCell(25, row).getContents()?.isInteger())
								prestacion.cirujano2N2 = Integer.parseInt(sheet.getCell(25, row).getContents())
							if (sheet.getCell(26, row).getContents()?.isInteger())
								prestacion.cirujano2N3 = Integer.parseInt(sheet.getCell(26, row).getContents())
							if (sheet.getCell(27, row).getContents()?.isInteger())
								prestacion.cirujano3N1 = Integer.parseInt(sheet.getCell(27, row).getContents())
							if (sheet.getCell(28, row).getContents()?.isInteger())
								prestacion.cirujano3N2 = Integer.parseInt(sheet.getCell(28, row).getContents())
							if (sheet.getCell(29, row).getContents()?.isInteger())
								prestacion.cirujano3N3 = Integer.parseInt(sheet.getCell(29, row).getContents())
							if (sheet.getCell(30, row).getContents()?.isInteger())
								prestacion.cirujano4N1 = Integer.parseInt(sheet.getCell(30, row).getContents())
							if (sheet.getCell(31, row).getContents()?.isInteger())
								prestacion.cirujano4N2 = Integer.parseInt(sheet.getCell(31, row).getContents())
							if (sheet.getCell(32, row).getContents()?.isInteger())
								prestacion.cirujano4N3 = Integer.parseInt(sheet.getCell(32, row).getContents())
								
							prestacion.valorN1 = prestacion.totalFonasaN1 + prestacion.anestesiaN1 + (prestacion.cirujano1N1 / 10)
							prestacion.valorN2 = prestacion.totalFonasaN2 + prestacion.anestesiaN2 + (prestacion.cirujano1N2 / 10)
							prestacion.valorN3 = prestacion.totalFonasaN3 + prestacion.anestesiaN3 + (prestacion.cirujano1N3 / 10)
							
						}
						
						//fechas de vigencia de arancel
						if (params?.desde) {
							prestacion.desde = params?.desde
						}
						prestacion.hasta = isCargaArancelHistorico == "on" ?  params?.hasta : null
						prestacion?.origen = "FONASA"
						prestacion?.cargaAprobada = true

						try {
							log.info("Guardando prestacion")
							prestacion.save(flush:true)
							log.info("Prestacion guardada con exito")
						}catch (Exception e){
							arancelesBaseCargados--
							//errores[errores.size()] = "ERROR: fila:${row} Exception al grabar el ARANCELBASE: " + e.getMessage()
							prestador.errors.reject("Error no controlado en la lectura de archivo: ${e.message}, contacte a soporte informatico")
							log.info("Error no controlado en la lectura de archivo, fila: " + row + " grp:" + grupo + " sbgrp: " + subGrupo + " tit: " + titulo + " glosa: " + glosa)
							log.info("Error no controlado en la lectura de archivo: ${e.message}")
							e.printStackTrace()
						}	
					}	
					
					row++
				}
				
		} catch (NullPointerException ne) {
			log.info("Error en la lectura de archivo: " + ne.getMessage())
			//errores[errores.size()] = "Error no controlado en la lectura de archivo: ${ne.message}, contacte a soporte informatico"
			prestador.errors.reject("Error no controlado en la lectura de archivo: ${ne.message}, contacte a soporte informatico") 
			ne.printStackTrace()
		} catch (ArrayIndexOutOfBoundsException ae) {
			log.info("Error no controlado en la lectura de archivo: " + ae.getMessage())
			//errores[errores.size()] =  "Error no controlado en la lectura de archivo: ${ae.message}, contacte a soporte informatico"
			prestador.errors.reject("Error no controlado en la lectura de archivo: ${ae.message}, contacte a soporte informatico")
			ae.printStackTrace()
		}
		
	
		
		prestacionesCargadas = getPrestaciones(params);
		log.info("Cantidad de prestaciones cargadas : " + prestacionesCargadas?.size())
		model = [desde: params?.desde, hasta:fechaHasta, prestacionesCargadas: prestacionesCargadas, p:prestador]
		params.put('model', model)
		render (view: 'index', model: params.model)

    }
	
	def getPrestaciones(params){
		log.info("Ejecutando metodo getPrestaciones")
		log.debug("Datos recibidos : ${params}" )
		try{
			def query = "SELECT a FROM ArancelBase a WHERE a.origen = ? AND a.desde = ? AND a.hasta is null"
			def paramList = []
			paramList << "FONASA"
			paramList << params?.desde
			if(params?.hasta != null){
				query = "SELECT a FROM ArancelBase a WHERE a.origen = ? AND a.desde = ? AND a.hasta = ?"
				paramList << params.hasta
			}
			
			
			def prestacionesList = ArancelBase.executeQuery(query,paramList)
			return prestacionesList
			
		}catch(Exception e){
			log.info("error al obtener cantidad de prestaciones cargadas")
			e.printStackTrace()
		}	
	}
	

    def show(Long id) {
		log.info ("************* Ejecutando accion show ****************")
        def arancelesInstance = Aranceles.get(id)
        if (!arancelesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'aranceles.label', default: 'Aranceles'), id])
            forward(action: "list")
            return
        }

        [arancelesInstance: arancelesInstance]
    }

    def edit(Long id) {
		log.info ("************* Ejecutando accion edit ****************")
        def arancelesInstance = Aranceles.get(id)
        if (!arancelesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'aranceles.label', default: 'Aranceles'), id])
            forward(action: "list")
            return
        }

        [arancelesInstance: arancelesInstance]
    }

	/*
	 * Actualiza fecha de fin de vigencia de los aranceles en el caso que la fecha no sea igual o inferior a la carga anterior.
	 * 
	 * */
    def update() {
		log.info ("************* Ejecutando accion update ****************")
		/*def hasta = ""	
		 if (params?.desde) {
			Format formatter = new SimpleDateFormat("dd-MM-yyyy")
			hasta = formatter.format(params?.desde)
		 }
		 
		 log.info("Actualizando la fecha hasta:" + hasta)
		 ArancelBase.executeUpdate("UPDATE ArancelBase ab SET ab.hasta = ? WHERE ab.hasta is null AND ab.cargaAprobada=true AND origen='FONASA'", [params?.desde])
		 
		 ArancelBase.executeUpdate("UPDATE ArancelBase ab SET ab.cargaAprobada=true WHERE ab.cargaAprobada=false AND origen='FONASA'")*/
		flash.message = "book.delete.message"
		flash.args = []
		flash.default = "La carga fue realizada con exito"
		redirect(action: 'index')
					
    }

    def delete() {
		log.info ("************* Ejecutando accion delete ****************")
		log.info("Datos obtenidos : ${params}")
		log.info("Eliminando Aranceles Base")
		def fechaDesde = params?.desde
		def fechaHasta = params?.hasta
		def paramList = []
		paramList << 'FONASA'
		paramList << fechaDesde
		
		def query = "DELETE FROM ArancelBase ab WHERE ab.origen = ? and ab.desde = ? and ab.hasta is null"
		if(params?.hasta != null){
			query = "DELETE FROM ArancelBase ab WHERE ab.origen = ? and ab.desde = ? and ab.hasta = ?"
			paramList << fechaHasta
		}
		
		
		ArancelBase.executeUpdate(query,paramList)
		log.info("Los registros de aranceles base han sido eliminados")
		flash.message = "book.delete.message"
		flash.args = []
		flash.default = "Los registros de aranceles base han sido eliminados"
		
		redirect(action: "index")			
    }
	
	def mantener_aranceles() {
		log.info ("************* Ejecutando accion mantener_aranceles ****************")
		// Buscar lista de prestadores
		def prestacionList	= ArancelService.obtenerAranceles(params)
		log.info "params?.countPrestaciones: ${params?.countPrestaciones}"
		// Buscar grupos y subgrupos
		def grupos			= Grupo.listOrderByCodigo()
		def subGrupos
		if (params?.grupo) {
			def grupo = Grupo.findByCodigo(params?.grupo)
			subGrupos  = SubGrupo.findAllByGrupo(grupo)
		}
		def model = [ grupo			: grupos
					, subGrupo		: subGrupos
					, prestacionList: prestacionList
					, totalPaginate	: params?.countPrestaciones ]
		model
	}
	
	/*
	 * Prestaciones ISL
	 */
	
	def ver_prestacion_detalle() {
		log.info ("************* Ejecutando accion ver_prestacion_detalle ****************")
		def arcenaleraN1	= 0
		def arcenaleraN2	= 0
		def arcenaleraN3	= 0
		def equipo			= 0
		def tipo			= ""
		def codigo
		def arancelBase		= PrestadorService.getPrestacionByCodigo(params?.codigoPrestacion)
		
		if (arancelBase?.cirujano1N1)
			arcenaleraN1 = arancelBase?.cirujano1N1 / 10
		if (arancelBase?.cirujano1N2)
			arcenaleraN2 = arancelBase?.cirujano1N2 / 10
		if (arancelBase?.cirujano1N3)
			arcenaleraN3 = arancelBase?.cirujano1N3 / 10
			
		if (arancelBase?.cirujano1N1 || arancelBase?.cirujano1N2 || arancelBase?.cirujano1N3 ||
			arancelBase?.cirujano2N1 || arancelBase?.cirujano2N2 || arancelBase?.cirujano2N3 ||
			arancelBase?.cirujano3N1 || arancelBase?.cirujano3N2 || arancelBase?.cirujano3N3) {
			tipo = "Quirúrgica"
		} else {
			tipo = "General / Procedimiento"
		}
		
		if (arancelBase?.cirujano1N1 || arancelBase?.cirujano1N2 || arancelBase?.cirujano1N3)
			equipo++
		if (arancelBase?.cirujano2N1 || arancelBase?.cirujano2N2 || arancelBase?.cirujano2N3)
			equipo++
		if (arancelBase?.cirujano3N1 || arancelBase?.cirujano3N2 || arancelBase?.cirujano3N3)
			equipo++
		if (arancelBase?.cirujano4N1 || arancelBase?.cirujano4N2 || arancelBase?.cirujano4N3)
			equipo++

		if (arancelBase?.codigo.length() > 4)
			codigo = arancelBase?.codigo.substring(4)
		else
			codigo = arancelBase?.codigo

		def grupo = arancelBase?.subGrupo?.grupo
		def model = [ arancelBase	: arancelBase
					, grupo			: grupo
					, arcenaleraN1	: arcenaleraN1
					, arcenaleraN2	: arcenaleraN2
					, arcenaleraN3	: arcenaleraN3
					, tipo			: tipo
					, equipo		: equipo
					, codigo		: codigo]
		model
	}
	
	
	def ver_prestacion() {
		log.info ("************* Ejecutando accion ver_prestacion ****************")
		def arcenaleraN1	= 0
		def arcenaleraN2	= 0
		def arcenaleraN3	= 0
		def equipo			= 0
		def tipo			= ""
		def codigo
		def arancelBase		= ArancelBase.findById(params?.arancelBaseId)
		
		if (arancelBase?.cirujano1N1)
			arcenaleraN1 = arancelBase?.cirujano1N1 / 10
		if (arancelBase?.cirujano1N2)
			arcenaleraN2 = arancelBase?.cirujano1N2 / 10
		if (arancelBase?.cirujano1N3)
			arcenaleraN3 = arancelBase?.cirujano1N3 / 10
			
		if (arancelBase?.cirujano1N1 || arancelBase?.cirujano1N2 || arancelBase?.cirujano1N3 ||
			arancelBase?.cirujano2N1 || arancelBase?.cirujano2N2 || arancelBase?.cirujano2N3 ||
			arancelBase?.cirujano3N1 || arancelBase?.cirujano3N2 || arancelBase?.cirujano3N3) {
			tipo = "Quirúrgica"
		} else {
			tipo = "General / Procedimiento"
		}
		
		if (arancelBase?.cirujano1N1 || arancelBase?.cirujano1N2 || arancelBase?.cirujano1N3)
			equipo++
		if (arancelBase?.cirujano2N1 || arancelBase?.cirujano2N2 || arancelBase?.cirujano2N3)
			equipo++
		if (arancelBase?.cirujano3N1 || arancelBase?.cirujano3N2 || arancelBase?.cirujano3N3)
			equipo++
		if (arancelBase?.cirujano4N1 || arancelBase?.cirujano4N2 || arancelBase?.cirujano4N3)
			equipo++

		if (arancelBase?.codigo.length() > 4)
			codigo = arancelBase?.codigo.substring(4)
		else
			codigo = arancelBase?.codigo

		def grupo = arancelBase?.subGrupo?.grupo
		def model = [ arancelBase	: arancelBase
					, grupo			: grupo
					, arcenaleraN1	: arcenaleraN1
					, arcenaleraN2	: arcenaleraN2
					, arcenaleraN3	: arcenaleraN3
					, tipo			: tipo
					, equipo		: equipo
					, codigo		: codigo]
		model
	}
	
	/*
	 * Editar detalle prestación ISL
	 */
	def edit_prestacion() {
		log.info ("************* Ejecutando accion edit_prestacion ****************")
		def arcenalera = 0
		def equipo = 0
		def tipo = ""
		def codigo
		def arancelBaseId
		
		log.info("*** edit_prestacion ***")
		
		if(params.get('model')) {
			if(params.get('model').arancelBaseId){
				arancelBaseId = params.get('model').arancelBaseId
			}
		}
		if (params?.arancelBaseId)
			arancelBaseId = params?.arancelBaseId
			
		log.info("arancelBaseId: " + arancelBaseId)
		if (!arancelBaseId) {
			return
		}
		def arancelBase = ArancelBase.findByIdAndOrigen(arancelBaseId, "ISL")
		if (!arancelBase) {
			log.info("Error, no encontramos el arancelBase para el id: " + arancelBaseId)
			return
		}
		if (arancelBase?.cirujano1N1)
			arcenalera = arancelBase?.cirujano1N1 / 10

		if (arancelBase?.cirujano1N1 || arancelBase?.cirujano1N2 || arancelBase?.cirujano1N3 ||
			arancelBase?.cirujano2N1 || arancelBase?.cirujano2N2 || arancelBase?.cirujano2N3 ||
			arancelBase?.cirujano3N1 || arancelBase?.cirujano3N2 || arancelBase?.cirujano3N3) {
			tipo = "Quirurgica"
		} else {
			tipo = "General"
		}
		
		log.info("Cargando prestación tipo : " + tipo)
		if (arancelBase?.cirujano1N1 || arancelBase?.cirujano1N2 || arancelBase?.cirujano1N3) {
			equipo++
		}

		if (arancelBase?.cirujano2N1 || arancelBase?.cirujano2N2 || arancelBase?.cirujano2N3) {
			equipo++
		}

		if (arancelBase?.cirujano3N1 || arancelBase?.cirujano3N2 || arancelBase?.cirujano3N3) {
			equipo++
		}
		
		if (arancelBase?.cirujano4N1 || arancelBase?.cirujano4N2 || arancelBase?.cirujano4N3) {
			equipo++
		}
		
		if (arancelBase?.codigo.length() > 4)
			codigo = arancelBase?.codigo.substring(4)
		else
			codigo = arancelBase?.codigo

		def grupo = Grupo.listOrderByCodigo()
		def subGrupo = SubGrupo.findAllByGrupo(arancelBase?.subGrupo?.grupo) 
		def model = [ arancelBase: arancelBase
					, grupo: grupo
					, subGrupo: subGrupo
					, arcenalera: arcenalera
					, tipo: tipo
					, equipo: equipo
					, codigo: codigo ]
		model
	}

	def create_prestacion() {
		log.info ("************* Ejecutando accion create_prestacion ****************")
		def model
		def arancelBase
		def grupo = Grupo.listOrderByCodigo()
		def grupoCodigo
		def subGrupoCodigo
		def subGrupo
		def tipo
		def equipo = 0
		
		if(params.get('model')) {
			if(params.get('model').arancelBase) {
				arancelBase = params.get('model').arancelBase
				subGrupoCodigo = arancelBase?.subGrupo?.codigo
				
				subGrupo = SubGrupo.findByCodigo(subGrupoCodigo)
				subGrupoCodigo = subGrupo?.id
				grupoCodigo = subGrupo?.grupo?.id
				
				if (arancelBase?.cirujano1N1)
					arcenalera = arancelBase?.cirujano1N1 / 10
		
				if (arancelBase?.cirujano1N1 || arancelBase?.cirujano1N2 || arancelBase?.cirujano1N3 ||
					arancelBase?.cirujano2N1 || arancelBase?.cirujano2N2 || arancelBase?.cirujano2N3 ||
					arancelBase?.cirujano3N1 || arancelBase?.cirujano3N2 || arancelBase?.cirujano3N3) {
					tipo = "Quirurgica"
				} else {
					tipo = "General"
				}
				
				if (arancelBase?.cirujano1N1 || arancelBase?.cirujano1N2 || arancelBase?.cirujano1N3) {
					equipo++
				}
		
				if (arancelBase?.cirujano2N1 || arancelBase?.cirujano2N2 || arancelBase?.cirujano2N3) {
					equipo++
				}
		
				if (arancelBase?.cirujano3N1 || arancelBase?.cirujano3N2 || arancelBase?.cirujano3N3) {
					equipo++
				}
				
				if (arancelBase?.cirujano4N1 || arancelBase?.cirujano4N2 || arancelBase?.cirujano4N3) {
					equipo++
				}	
				
				log.info("create_prestacion: ${grupoCodigo} ${subGrupoCodigo}")
			
				if(params.get('model').errores) {
					arancelBase.errors.reject(params.get('model').errores)
				}
				
				if (arancelBase?.codigo.size() == 7) {
					arancelBase?.codigo = arancelBase?.codigo?.substring(4)
				}
			}
		}
						
		model = [ grupo: grupo, arancelBase: arancelBase, grupoCodigo: grupoCodigo, subGrupoCodigo: subGrupoCodigo ]
		
		model.put('tipo', tipo)
		model.put('equipo', equipo)
		
		model
	}
	
	
	def save_prestacion() {
		log.info ("************* Ejecutando accion save_prestacion ****************")
		def r = ArancelService.agregarPrestacion(params)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
		
	}
	
	def update_prestacion() {
		log.info ("************* Ejecutando accion update_prestacion ****************")
		def r = ArancelService.actualizarPrestacion(params)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}
	
	def delete_prestacion() {
		log.info ("************* Ejecutando accion delete_prestacion ****************")
		def r = ArancelService.deletePrestacion(params)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}

	/*
	 * Paquetes ISL
	 */
	def create_paquete() {
		log.info ("************* Ejecutando accion create_paquete ****************")
		def model
		def grupo = Grupo.listOrderByCodigo()
		def subgrupos = SubGrupo.listOrderByCodigo()
		def tipoPaquetes = TipoPaquete.listOrderByDescripcion()
		def paquete
		
		if(params.get('model')) {
			
			paquete = params.get('model').paquete
		
		}
						
		model = [ grupo: grupo, subgrupos: subgrupos, tipoPaquetes: tipoPaquetes, paquete: paquete ]
		model
	}
	
	def save_paquete() {
		log.info ("************* Ejecutando save_paquete ****************")
		def r = ArancelService.agregarPaquete(params)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
	}
	
	def update_paquete() {
		log.info ("************* Ejecutando accion update_paquete ****************")
		log.info("params: " + params)
		def r = ArancelService.actualizarPaquete(params)
		def model = r.get('model')
		model += [desdeEdicion: params?.desdeEdicion]
		params.put('model', model)
		def next = r.next
		next.params = params
		forward (next)
	}
	
	def edit_paquete() {
		log.info ("************* Ejecutando edit_paquete ****************")
		log.info ("Datos recibidos: ${params}")
		def model
		def grupo = Grupo.listOrderByCodigo()
		def subGrupo
		def tipoPaquetes = TipoPaquete.listOrderByDescripcion()
		def paquete
		def paqueteId
		def p
		
		if (params?.paqueteId) {
			log.info("paqueteId:" + params?.paqueteId)
			paquete = Paquete.findById(params?.paqueteId)
		}
		if (params?.codigo && !paquete) {
			log.info("codigo:" + params?.codigo)
			paquete = Paquete.findByCodigo(params?.codigo)
		}
		if (params.get('model') && !paquete) {
			paqueteId = params.get('model').paqueteId
			paquete = Paquete.findById(paqueteId)
			log.info("Cargando paqueteId : " + paqueteId)
			
			if (params.get('model').paquete) {
				p = params.get('model').paquete
				if (p.errors.hasErrors()) {
					paquete.errors.reject("cl.adexus.isl.spm.Paquete.delete.fail")
				}
			}
		}
		// Identificar si es edición
		def desdeEdicion = params?.desdeEdicion?.toBoolean()
		if (!desdeEdicion) {
			desdeEdicion = params?.model?.desdeEdicion?.toBoolean()
			if (!desdeEdicion) { desdeEdicion = false }
		}
		
		def arancelesPaquete = PrestadorService.getListaArancelesByPaqueteId(paquete?.id)
						
		model = [ grupo				: grupo
				, subGrupo			: subGrupo
				, tipoPaquetes		: tipoPaquetes
				, paquete			: paquete
				, arancelesPaquete	: arancelesPaquete
				, desdeEdicion		: desdeEdicion ]
		log.info "model: edit paquete: ${model}"
		model
	}
	
	def getArancelesPaqueteGlosaById() {
		log.info ("************* Ejecutando accion getArancelesPaqueteGlosaById ****************")
		def arancelPaqueteId = params?.arancelPaqueteId
		log.info("arancelPaqueteId: " + arancelPaqueteId)
		def result = PrestadorService.getArancelesPaqueteGlosaById(arancelPaqueteId)
		render result
	}
	
	def eliminarArancelPaquete() {
		log.info ("************* Ejecutando accion eliminarArancelPaquete ****************")
		def arancelPaqueteId = params?.arancelPaqueteId
		PrestadorService.eliminarArancelPaquete(arancelPaqueteId)
		render ("OK")
	}
	
	def delete_paquete() {
		log.info ("************* Ejecutando accion delete_paquete ****************")
		def r = ArancelService.deletePaquete(params)
		params.put('model', r.get('model'))
		def next = r.next
		next.params = params
		forward (next)
		
	}
	
	def cargarAranceles(){
		log.info ("************* Ejecutando accion cargarAranceles ****************")
		def result = ""
		def grupo = null
		def subgrupo = null
		def codigo = null
		
		if (params?.grupo)
			grupo = params?.grupo
		if (params?.subgrupo)
			subgrupo = params?.subgrupo
		if (params?.codigo)
			codigo = params?.codigo
		
		log.info("Datos recibidos :: grupo :${grupo} : subgrupo : ${subgrupo} : codigo : ${codigo}")
			
		result = PrestadorService.cargarAranceles(grupo, subgrupo, codigo, false)
		
		render result
		
	}
	
	def agregarArancelPaquete() {
		log.info ("************* Ejecutando accion getArancelPaquete ****************")
		def result = PrestadorService.agregarArancelPaquete(params)
		render ("OK")
	}

	def desplegarArancelesPaquete() {
		log.info ("************* Ejecutando accion desplegarArancelesPaquete ****************")
		log.info("desplegarArancelesPaquete: [${params?.paqueteId}]")
		def result = PrestadorService.desplegarArancelesPaquete(params?.paqueteId)
		render result
	}
	
	def ver_paquete() {
		log.info ("************* Ejecutando accion ver_paquete ****************")
		def paquete = Paquete.findById(params?.paqueteId)
		if (!paquete) {
			log.info "El paquete [${params?.paqueteId}] no existe!"
		}
		def arancelesPaquete = PrestadorService.getListaArancelesByPaqueteId(paquete?.id)
		[paquete: paquete, arancelesPaquete: arancelesPaquete]
	}
	
	def ver_paquete_detalle() {
		log.info ("************* Ejecutando accion ver_paquete_detalle ****************")
		def paquete		= PrestadorService.getPrestacionByCodigo(params?.codigoPrestacion)
		if (!paquete) {
			log.info "El paquete [${params?.paqueteId}] no existe!"
		}
		def arancelesPaquete = PrestadorService.getListaArancelesByPaqueteId(paquete?.id)
		[paquete: paquete, arancelesPaquete: arancelesPaquete]
	}

	/*
	 * funciones Ajax
	 */
	
	def SubGruposJSON(){
		log.info ("************* Ejecutando accion SubGruposJSON ****************")
		//def grupo = params?.grupo
		def grupo = Grupo?.findByCodigo(params?.grupo)
		//Llamamos al servicio
		def sg = SubGrupo.findAllByGrupo(grupo)
		
		JSON.use("deep"){ render sg as JSON }
	}
	
	def buscarPrestacionJSON() {
		log.info ("************* Ejecutando accion buscarPrestacionJSON ****************")
		log.info "Datos recibidos: ${params}"
		def r = ArancelService.buscarPrestacionJSON(params)
		JSON.use("deep"){ render r as JSON }
	}
	
	def buscarPaqueteJSON() {
		log.info ("************* Ejecutando accion buscarPaqueteJSON ****************")
		log.info "Datos recibidos: ${params}"
		def r = ArancelService.buscarPaqueteJSON(params)
		JSON.use("deep"){ render r as JSON }
	}
	
	/**
	 * (arancel.js) Permite determinar si la carga en cuestión corresponde la ultima , o en su defecto, a una carga historica
	 *  resultados
	 *  "true" : significa que la carga es historica
	 *  "false": significa que la carga no es historica
	 *  "": significa que no se ingresó la fecha de carga
	 */
	def isCargaHistoricaArancelJSON () {
		log.info("Ejecutando accion isCargaHistoricaJSON")
		log.info("Datos recibidos : ${params}")
		def result = "false"
		def fechaHastaAnterior
		def sdf
		def fechaDesde = params?.desde == "" ?: null
		
		if(!fechaDesde){
			log.debug("Calculando la ultima fecha de carga")
			sdf = new SimpleDateFormat("dd-MM-yyyy")
			fechaDesde = sdf.parse(params?.desde)
			def c = ArancelBase.createCriteria()
			fechaHastaAnterior = c.get {
			    projections {
			        max "hasta"
			    }
			} as Date
			
			log.debug("fechaHastaAnterior : $fechaHastaAnterior" )
			result = fechaDesde < fechaHastaAnterior? "true" : "false"
		}else{
			log.debug("Ultima fecha de carga vacia")
			result = ""
		}
					
		log.debug("result : $result" )
		//valor con el que finalmente se entregara la respuesta en formato JSON
		def resultList = []
		resultList << [isCargaHistoricaArancel : result]
		
		JSON.use("deep"){ render resultList as JSON }
	}
}
