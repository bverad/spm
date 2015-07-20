package cl.adexus.isl.spm

class OTPRevisionService {

	/**
	 *
	 */
	def r01 (params) {
		def reembolsos = Reembolso.findAll()
		reembolsos.each {
			if (it.fechaAprobacion != null && it.fechaEnvioPago == null) {
				// Revisemos si está chequeado
				if (params['reembolso_' + it.id] == 'on') {
					// Guardamos la fecha de envío de pago
					log.info "Enviado a pago el reembolso id: " + it.id
					it.fechaEnvioPago = new Date()
					it.save(flush: true)
				}
			}
		}
		return
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
