package cl.adexus.isl.spm

import javax.servlet.ServletOutputStream

class OTP_revisionController {

	def documentacionService
	def OTPRevisionService
	
    def index () {
		redirect (action: 'dp01')
	}
	
	/**
	 * Actividad que sirve al Encargado de Revisar las Solicitudes
	 * de reembolso el valor por el cual se están enviando a pago,
	 * es un punto de control que se tiene.
	 * Actualmente este punto se ha configurado para que todas las
	 * solicitudes pasen por revisión antes de ser enviadas a pago.
     * En un futuro este punto de control será parametrizado para que
     * las solicitudes sobre un determinado monto se envíen automáticamente a pago.
	 */
	def dp01 () {

		log.info("*** dp01 ***")
		
		def reembolsos            = Reembolso.findAll()
		def reembolsosAutorizados = []
		def montoAutorizado
		def detalles
		def reembolso             = [:]
		def sumaAutorizados       = 0
		
		reembolsos.each {
			if (it.fechaAprobacion != null && it.fechaEnvioPago == null) {
				// Necesitamos calcular el montoAutorizado
				montoAutorizado = 0
				detalles = DetalleGastosReembolso.findAllByReembolso(it)
								
				for (detalle in detalles) {
					montoAutorizado += detalle.valorAprobado
				}				
				
				reembolso.id              = it.id
				reembolso.fechaAprobacion = it.fechaAprobacion
				reembolso.siniestro       = it.siniestro
				reembolso.cobrador        = it.cobrador
				reembolso.montoAutorizado = montoAutorizado
				reembolso.montoSolicitado = it.montoSolicitado
				
				sumaAutorizados += reembolso.montoAutorizado
				
				// Agregamos finalmente
				reembolsosAutorizados.add(reembolso)
				reembolso = [:]	
			}
		}		
		
		def model = [ reembolsos      : reembolsosAutorizados,
			          sumaAutorizados : sumaAutorizados 
	    ]
	}

	def verFormularioReembolso () {
		// fallback
		if (params.id == null) return null
		
		def reembolso = Reembolso.findById(params.id)
		
		// otro fallback
		if (reembolso == null) return null
		
		def documento = DocumentacionAdicional.findByReembolso(reembolso)
		
		// otro fallback más!
		if (documento == null) return null
		
		def r = documentacionService.view(Long.toString(documento.id))
		def doc = r.doc
		
		response.setContentLength((int)doc.contentLength)
		response.setHeader("Content-disposition", "attachment; filename=\"" + doc.name + "\"")
		
		ServletOutputStream outputStream = null
		DataInputStream docStream = null
		int length = 0
		outputStream = response.getOutputStream()
		docStream = new DataInputStream(doc.inputStream)
		byte[] bbuf = new byte[1024]
		while ((docStream != null) && ((length = docStream.read(bbuf)) != -1)) {
				outputStream.write(bbuf, 0, length)
		}
		outputStream?.flush()
		docStream?.close()
		outputStream?.close()
		
		// Debido a un bug de grails se asigna el content type despues de haber escrito al outputStream del response
		response.setContentType(doc.mimeType)
		
		null
	}
	
	def analisis_seguimiento() {
		def reembolsoId		= params?.reembolsoId
		log.info("reembolsoId : " + reembolsoId)
		def reembolso		= Reembolso.findById(reembolsoId)
		// Saquemos los detalles anteriores
		def dts				= DetalleGastosReembolso.findAllByReembolso(reembolso)		
		def detalles     	= []
		def rutProveedor	= ""
		def nombreProveedor	= ""
		def sumaGastos   	= 0
		
		dts.each {
			if (it.tipoProveedorPersonaJuridica) {
				rutProveedor    = it.proveedorJuridico.rut
				nombreProveedor = it.proveedorJuridico.razonSocial
			} else {
				rutProveedor    = it.proveedorNatural.run
				nombreProveedor = it.proveedorNatural.nombre + " " +
				                  it.proveedorNatural.apellidoPaterno + " " +
								  it.proveedorNatural.apellidoMaterno
			}
			detalles.add([
				'id'              : it.id,
				'fechaGasto'      : it.fechaGasto,
				'numero'          : it.numero,
				'concepto'        : it.concepto.descripcion,
				'rutProveedor'    : rutProveedor,
				'nombreProveedor' : nombreProveedor,
				'valorDocumento'  : it.valorDocumento,
				'valorSolicitado' : it.valorSolicitado
			])
			sumaGastos += it.valorSolicitado
		}
		def model = [	reembolso	: reembolso
					,	detalles	: detalles
					,	sumaGastos	: sumaGastos ]
	}
	
	/**
	 * Boton "Enviar a Pago" de dp01
	 */
	def r01 () {
		def r = OTPRevisionService.r01(params)		
		redirect ([controller: 'nav', action: 'area6'])
	}

	/**
	 *
	 */
	def r02 () {
		// TODO
	}

	/**
	 *
	 */
	def r03 () {
		// TODO
	}
}
