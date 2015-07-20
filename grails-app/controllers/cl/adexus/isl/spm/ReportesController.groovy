package cl.adexus.isl.spm

import cl.cognus.*

import cl.adexus.helpers.DataSourceHelper
import grails.converters.JSON
import groovy.sql.Sql
import java.text.SimpleDateFormat

class ReportesController {

	def reportGeneratorService   //inyeccion de dependencia
	def dataSource
	
    def index() { render(view : 'default') }
	
	// NOT DONE
	/*
	def r1_1 () {				
		render(view: 'r1_1')
	}
	*/
	def rp_1 () {
		
		log.info("*** rp_1")
		def listaReportes = []
		def i = 0
		
		def regiones = Region.listOrderByDescripcion()
		
		listaReportes[i++] = [codigo: 'r1_1', descripcion: 'Prestaciones en Convenio']
		listaReportes[i++] = [codigo: 'r1_2', descripcion: 'Prestaciones sin Convenio']
		listaReportes[i++] = [codigo: 'r1_3', descripcion: 'Vencimiento de Convenios (próximo mes)']
		listaReportes[i++] = [codigo: 'r1_4', descripcion: 'Vencimiento de Convenios (próximos 3 meses)']
		listaReportes[i++] = [codigo: 'r1_5', descripcion: 'Vencimiento de Convenios (próximos 12 meses)']
		listaReportes[i++] = [codigo: 'r1_6', descripcion: 'Reajustes de Convenios (próximo mes)']
		listaReportes[i++] = [codigo: 'r1_7', descripcion: 'Reajustes de Convenios (próximos 3 meses)']
		listaReportes[i++] = [codigo: 'r1_8', descripcion: 'Reajustes de Convenios (próximos 12 meses)']
		
		def model = [listaReportes: listaReportes, regiones: regiones]
		
		model
	}
	
	def rp1_sp() {
		
		log.info("*** rp1_sp ***")
		def reporte = params?.reporte
		
		if (!reporte) {
			redirect(action: 'rp_1')
		}
		
		redirect(action: reporte)
	}
	
	def cu1_1 () {		
		log.info("cu1_1")
		log.info(params)
		
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		
		if (params?.rut){
			params.rut = params.rut ? ((String)params.rut).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim() : params.rut
			reportParams['param_rut_prestador'] = params?.rut
		}
			
		if (params?.prestacion)	
			reportParams['param_codigo_prestacion'] = params?.prestacion
		
		reportParams['param_codigo_region']     = params?.region
		
		if (params?.fecha)
			reportParams['param_fecha']             = new SimpleDateFormat("yyyy-MM-dd").format(params?.fecha)
			
		reportParams['param_codigo_comuna'] = params?.comuna
			
		try {
			reportGeneratorService.generate('reporte_1_1.pdf', '1_1.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view : 'default')
	}
	
	// DONE
	def r1_2 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_1_2.pdf', '1_2.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default') 		
	}
	
	// DONE - No probado
	def r1_3 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_1_3.pdf', '1_3.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}		
		render(view: 'default')
	}
	
	// DONE
	def r1_4 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_1_4.pdf', '1_4.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE
	def r1_5 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_1_5.pdf', '1_5.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE
	def r1_6 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_1_6.pdf', '1_6.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	
	// DONE - No probado
	def r1_7 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_1_7.pdf', '1_7.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')		
	}

	// DONE - No probado
	def r1_8 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_1_8.pdf', '1_8.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	def rp_2 () {
		def listaReportes = []
		def listaSucursales = []
		def i = 0
		
		def regiones = Region.listOrderByDescripcion()
		def sucursales = ShiroSucursal.listOrderByName()
		
		log.info("*** rp2 ***")
		log.info("total regiones : " + regiones.size())

		listaReportes[i++] = [codigo: 'r2_1', descripcion: 'Carga de Trabajo (Nacional diario y acumulado)']
		listaReportes[i++] = [codigo: 'r2_2', descripcion: 'Carga de Trabajo (Regional Diario y Acumulado)']
		listaReportes[i++] = [codigo: 'r2_3', descripcion: 'Excepciones a Nivel Nacional (Diario y acumulado)']
		listaReportes[i++] = [codigo: 'r2_4', descripcion: 'Excepciones Regional (Diario y acumulado)']
		listaReportes[i++] = [codigo: 'r2_5', descripcion: 'Emisión nacional de OPAS/ODAS (diario y acumulado)']
		listaReportes[i++] = [codigo: 'r2_6', descripcion: 'Emisión regional de OPAS/ODAS (diario y acumulado)']
		listaReportes[i++] = [codigo: 'r2_7', descripcion: 'Emisión Nacional Pendiente DIAT/DIEP OA']
		listaReportes[i++] = [codigo: 'r2_8', descripcion: 'Emisión Regional Pendiente DIAT/DIEP OA']
		listaReportes[i++] = [codigo: 'r2_9', descripcion: 'Emisión Nacional DIATEP OA']
		listaReportes[i++] = [codigo: 'r2_10', descripcion: 'Emisión Regional DIAT/DIEP OA']
		
		i = 0
		for (sucursal in sucursales) {		
			listaSucursales[i++] = [codigo: sucursal?.id_sucursal, descripcion: sucursal?.name]
		}
		
		Date date = new Date()
		Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		
		def month = cal.get(Calendar.MONTH)
		month++ 
		def year  = cal.get(Calendar.YEAR)
		
		log.info("month: " + month + " year: " + year)

		def model = [listaReportes: listaReportes, regiones: regiones, month: month, year: year, mes: 1000]
		model	
	}
	
	def rp2_sp() {
		
		log.info("*** rp2_sp ***")
		def reporte = params?.reporte
		
		if (!reporte) {
			redirect(action: 'rp_4')
		}
		
		redirect(action: reporte)
	}
	// NOT DONE
	def r2_1 () { 
		log.info(params)
		
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region // Integer, de la tabla codigos de región
		reportParams['param_anio'] = params?.year // Integer
		reportParams['param_mes'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_2_1.pdf', '2_1.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_2 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region 
		reportParams['param_anio'] = params?.year 
		reportParams['param_mes'] = params?.month 
		reportParams['param_sucursal'] = params?.sucursal 
		
		try {
			reportGeneratorService.generate('reporte_2_2.pdf', '2_2.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_3 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region // Integer, de la tabla codigos de regi�n
		reportParams['param_anio'] = params?.year // Integer
		reportParams['param_mes'] = params?.month // Integer, de la tabla codigos de regi�n
		
		try {
			reportGeneratorService.generate('reporte_2_3.pdf', '2_3.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_4 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region 
		reportParams['param_anio'] = params?.year
		reportParams['param_mes'] = params?.month 
		reportParams['param_sucursal'] = params?.sucursal 
		
		try {
			reportGeneratorService.generate('reporte_2_2.pdf', '2_2.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_5 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region // Integer, de la tabla codigos de regi�n
		reportParams['param_anio'] = params?.year // Integer
		reportParams['param_mes'] = params?.month // Integer, de la tabla codigos de regi�n
		
		try {
			reportGeneratorService.generate('reporte_2_5.pdf', '2_5.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_6 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		
		if (!params?.region) {
			reportParams['param_region'] = '0' 
		} else { 
			reportParams['param_region'] = params?.region 
		}
		
		reportParams['param_anio'] = params?.year 
		reportParams['param_mes'] = params?.month 
		reportParams['param_sucursal'] = params?.sucursal 
		
		log.info(reportParams)
		
		try {
			reportGeneratorService.generate('reporte_2_6.pdf', '2_6.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_7 () {
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region
		
		try {
			reportGeneratorService.generate('reporte_2_7.pdf', '2_7.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_8 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region // Integer, de la tabla codigos de regi�n
		reportParams['param_anio'] = params?.year // Integer
		reportParams['param_mes'] = params?.month // Integer, de la tabla codigos de regi�n
		reportParams['param_sucursal'] = params?.sucursal  // Integer
		
		try {
			reportGeneratorService.generate('reporte_2_8.pdf', '2_8.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_9 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region // Integer, de la tabla codigos de regi�n
		
		try {
			reportGeneratorService.generate('reporte_2_9.pdf', '2_9.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r2_10 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_region'] = params?.region // Integer, de la tabla codigos de regi�n
		reportParams['param_anio'] = params?.year // Integer
		reportParams['param_mes'] = params?.month // Integer, de la tabla codigos de regi�n
		reportParams['param_sucursal'] = params?.sucursal // Integer
		
		try {
			reportGeneratorService.generate('reporte_2_10.pdf', '2_10.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	def rp_3 () {
		def listaReportes = []
		def i = 0
		
		listaReportes[i++] = [codigo: 'r3_1', descripcion: 'DIATEPOA Emitidas']
		listaReportes[i++] = [codigo: 'r3_2', descripcion: 'DIATEPOA Pendientes']
		listaReportes[i++] = [codigo: 'r3_3', descripcion: 'Calificación pendiente AT']
		listaReportes[i++] = [codigo: 'r3_4', descripcion: 'Calificación pendiente EP']
		listaReportes[i++] = [codigo: 'r3_5', descripcion: 'Solicitud de Antecedentes Pendientes']
		listaReportes[i++] = [codigo: 'r3_6', descripcion: 'Envío de RECA Pendiente']
		
		Date date = new Date()
		Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		
		def month = cal.get(Calendar.MONTH)
		month++
		def year  = cal.get(Calendar.YEAR)
		
		log.info("month: " + month + " year: " + year)
		
		def model = [listaReportes: listaReportes, year: year, month: month]
		model

	}
	// NOT DONE
	def r3_1 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		
		def year = params?.year
		def month = params?.month
		
		if (!month || !year) {
			redirect(action: 'rp_3')
		}
		
		reportParams['param_year'] = year// Integer
		reportParams['param_month'] = month // Integer
		
		try {
			reportGeneratorService.generate('reporte_3_1.pdf', '3_1.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE - No probado
	def r3_2 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_3_2.pdf', '3_2.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE - No probado
	def r3_3 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_3_3.pdf', '3_3.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE - No probado
	def r3_4 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_3_4.pdf', '3_4.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}

	// DONE - No probado
	def r3_5 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_3_5.pdf', '3_5.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE - No probado
	def r3_6 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_3_6.pdf', '3_6.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	def rp_4 () {
		def listaReportes = []
		def i = 0
		
		listaReportes[i++] = [codigo: 'r4_1', descripcion: 'Siniestro con Ingreso Pendiente']
		listaReportes[i++] = [codigo: 'r4_2', descripcion: 'Siniestros en Seguimiento Activo']
		listaReportes[i++] = [codigo: 'r4_3', descripcion: 'Siniestros en Seguimiento Cerrado']
		listaReportes[i++] = [codigo: 'r4_4', descripcion: 'ODAS Activas']
		listaReportes[i++] = [codigo: 'r4_5', descripcion: 'ODAS Historicas']
		
		def model = [listaReportes: listaReportes]
		
		model

	}
	
	def rp4_sp() {
		
		log.info("*** rp4_sp ***")
		def reporte = params?.reporte
		
		if (!reporte) {
			redirect(action: 'rp_4')
		}
		
		redirect(action: reporte)
	}
	
	def r4_1 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_4_1.pdf', '4_1.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE - No probado
	def r4_2 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_4_2.pdf', '4_2.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE - No probado
	def r4_3 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_4_3.pdf', '4_3.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE - No probado
	def r4_4 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_4_4.pdf', '4_4.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// DONE - No probado
	def r4_5 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]

		try {
			reportGeneratorService.generate('reporte_4_5.pdf', '4_5.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	def rp_5 () {
		DataSourceHelper dsHelper = new DataSourceHelper()
		def listaReportes = []
		def i = 0
		def regiones = Region.listOrderByDescripcion()
		def sucursales = ShiroSucursal.listOrderByName()
		def grupos = Grupo.listOrderByCodigo()
		def listaSucursales = []
		def prestadores = []
		def run
		def nombre
		def tipoPrestador
		
		listaReportes[i++] = [codigo: 'r5_1', descripcion: 'Informe Mensual Revisión de Cuentas por Prestador']
		listaReportes[i++] = [codigo: 'r5_2', descripcion: 'Informe Mensual Revisión de Cuentas por Agencia']
		listaReportes[i++] = [codigo: 'r5_3', descripcion: 'Informe Cuentas por Prestador (ODA-CM)']
		//listaReportes[i++] = [codigo: 'r5_4', descripcion: 'Informe de cuentas por Prestador (ODA-CM) - Detalle']
		listaReportes[i++] = [codigo: 'r5_5', descripcion: 'Informe de cuentas por Prestador (ODA-FACT)']
		//listaReportes[i++] = [codigo: 'r5_6', descripcion: 'Informe de cuentas por Prestador (ODA-FACT) - Detalle']
		listaReportes[i++] = [codigo: 'r5_7', descripcion: 'Cta. Corriente Convenio - Acumulado mensual']
		listaReportes[i++] = [codigo: 'r5_8', descripcion: 'Gasto por Centro de Salud']
		listaReportes[i++] = [codigo: 'r5_9', descripcion: 'Gasto por Paciente']
		listaReportes[i++] = [codigo: 'r5_10', descripcion: 'Gasto por Prestaciones']
		listaReportes[i++] = [codigo: 'r5_11', descripcion: 'Gasto por Diagnostico']
		listaReportes[i++] = [codigo: 'r5_12', descripcion: 'Gasto por Tipo de Siniestro']
		listaReportes[i++] = [codigo: 'r5_13', descripcion: 'Productividad por Funcionario']
		listaReportes[i++] = [codigo: 'r5_14', descripcion: 'Gasto por Obrero']
		
		i = 0
		for (sucursal in sucursales) {
			listaSucursales[i++] = [codigo: sucursal?.id_sucursal, descripcion: sucursal?.name]
		}
		
		def rows = Prestador.listOrderById()
		
		i = 0
		rows.each { row ->
			def pr = Prestador.get(row?.id)
			
			if (pr?.personaNatural) {
				nombre =  pr?.id + " - " + pr?.personaNatural?.run + " - " + pr?.personaNatural?.nombre + " " + pr?.personaNatural?.apellidoPaterno + " " + pr?.personaNatural?.apellidoMaterno
				prestadores[i++] = [codigo: pr?.id, descripcion: nombre]
			} else {
				nombre = pr?.id + " - " + pr?.personaJuridica?.rut + " - " +  pr?.personaJuridica?.razonSocial
				prestadores[i++] = [codigo: pr?.id, descripcion: nombre]
			}
		}
		log.info("prestadores size: " + prestadores.size())
				
		Date date = new Date()
		Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		
		def month = cal.get(Calendar.MONTH)
		month++
		def year  = cal.get(Calendar.YEAR)
		
		log.info("month: " + month + " year: " + year)
		log.info("sucursales size :" + listaSucursales.size())
		log.info("grupos size :" + grupos.size())
		
		def model = [listaReportes: listaReportes, year: year, month: month, regiones: regiones, sucursales: listaSucursales, grupos: grupos, prestadores: prestadores]
		model

	}
	
	// NOT DONE
	def r5_1 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_1.pdf', '5_1.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_2 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_2.pdf', '5_2.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_3 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_3.pdf', '5_3.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE (SUBREPORTE)
	def r5_4 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.param_year // Integer
		reportParams['param_month'] = params?.param_month // Integer
		reportParams['param_prestador'] = params?.param_prestador
		
		try {
			def rName;
			if(params?.param_detail=='54dc'){
				rName='5_4_detalle_cuentas.prpt';
			}
			if(params?.param_detail=='54do'){
				rName='5_4_detalle_ordenes_con_cta_medica.prpt';
			}
			if(params?.param_detail=='54dop'){
				rName='5_4_detalle_ordenes_pendientes.prpt';
			}
			
			reportGeneratorService.generate('reporte_5_4.pdf', rName, reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			render "exception:" + e.getMessage().toString()
		}
		render(view: 'default')
	}
	
	// NOT DONE 
	def r5_5 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_5.pdf', '5_5.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			render "exception:" + e.getMessage().toString()
		}
		render(view: 'default')
	}
	
	// NOT DONE (SUBREPORTE)
	def r5_6 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.param_year // Integer
		reportParams['param_month'] = params?.param_month // Integer
		reportParams['param_prestador'] = params?.param_prestador
		
		try {
			def rName;
			if(params?.param_detail=='56df'){
				rName='5_6_detalle_facturas.prpt';
			}
			if(params?.param_detail=='56dc'){
				rName='5_6_detalle_cuentas.prpt';
			}
			if(params?.param_detail=='56dcp'){
				rName='5_6_detalle_cuentas.prpt';
			}
			
			reportGeneratorService.generate('reporte_5_6.pdf', rName, reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			render "exception:" + e.getMessage().toString()
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_7 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_7.pdf', '5_7.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_8 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_8.pdf', '5_8.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_9 () { 
		
		params['rut']=((String)params['rut']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		params['rut']=params['rut'].substring(0,params['rut'].length()-1)+"-"+params['rut'].substring(params['rut'].length()-1)
				
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		def sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		reportParams['param_desde'] = sdf.format(params?.desde) // Integer
		reportParams['param_hasta'] = sdf.format(params?.hasta) // Integer
		reportParams['param_rut'] = params?.rut
		
		log.info "reportParams->"+reportParams
		
		try {
			reportGeneratorService.generate('reporte_5_9.pdf', '5_9.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_10 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		
		//params['rut']=((String)params['rut']).replaceAll("\\.", "").replaceAll("-", "").toUpperCase().trim()
		
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		reportParams['param_region'] = params?.region // Integer
		reportParams['param_comuna'] = params?.comuna // Integer
		reportParams['param_centrosalud'] = params?.centrosalud // Integer
		reportParams['param_grupo'] = params?.grupo // Integer
		reportParams['param_subgrupo'] = params?.subgrupo // Integer
		reportParams['param_codigo'] = params?.codigo // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_10.pdf', '5_10.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_11 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_11.pdf', '5_11.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_12 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_12.pdf', '5_12.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_13 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		def sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		reportParams['param_fechadesde'] = sdf.format(params?.desde) // Integer
		reportParams['param_fechahasta'] = sdf.format(params?.hasta) // Integer
		
		log.info "reportParams->"+reportParams
		
		try {
			reportGeneratorService.generate('reporte_5_13.pdf', '5_13.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	// NOT DONE
	def r5_14 () { 
		File tempFile = File.createTempFile("report", "generator")
		def reportParams = [:]
		reportParams['param_year'] = params?.year // Integer
		reportParams['param_month'] = params?.month // Integer
		
		try {
			reportGeneratorService.generate('reporte_5_14.pdf', '5_14.prpt', reportParams, ReportOutputType.PDF, tempFile)
			def fis = new FileInputStream(tempFile)
			response.outputStream << fis
			response.outputStream.flush()
		} catch (Exception e) {
			println e
		}
		render(view: 'default')
	}
	
	def SubGruposJSON(){
		log.info("SubGruposJSON")
		//def grupo = params?.grupo
		log.info("buscando subgrupo para grupo: " + params?.grupo)
		def grupo = Grupo?.findByCodigo(params?.grupo)
		//Llamamos al servicio
		def sg = SubGrupo.findAllByGrupo(grupo)
		log.info("subgrupo : " + sg)
		
		JSON.use("deep"){ render sg as JSON }
	}
	
	def CentrosDeSaludJSON(){
		log.info("CentrosDeSaludJSON")
		//def grupo = params?.grupo
		log.info("buscando prestador para prestador: " + params?.prestador)
		def prestador = Prestador.findById(params?.prestador)
		def cs = CentroSalud?.findAllByPrestador(prestador)
		log.info("cs : " + cs)
		
		JSON.use("deep"){ render cs as JSON }
	}
	
	def ComunasJSON(){
		log.info("ComunasJSON")
		//def grupo = params?.grupo
		log.info("buscando prestador para region: " + params?.region)
		def comunas = Comuna.executeQuery("SELECT	c FROM Comuna c WHERE 	c.provincia.region.codigo = :region ", [region: params?.region])
		log.info("cs : " + comunas)
		
		JSON.use("deep"){ render comunas as JSON }
	}
	
	def SucursalesJSON(){
		log.info("buscando Sucursales para region: " + params?.region)
		
		def agencia = ShiroAgencia.find("from ShiroAgencia as agencia where agencia.region.codigo_region = :region",[region: params?.region])
		def sucursales = ShiroSucursal.findAll("from ShiroSucursal as sucursal where sucursal.agencia = :agencia",[agencia: agencia])
		def listaSucursales = []
		def i = 0
		
		for (sucursal in sucursales) {
			listaSucursales[i++] = [codigo: sucursal?.id_sucursal, descripcion: sucursal?.name]
		}
		log.info("Sucursales : " + listaSucursales)
		
		JSON.use("deep"){ render listaSucursales as JSON }
	}
}
