package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class TipoPaqueteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tipoPaqueteInstanceList: TipoPaquete.list(params), tipoPaqueteInstanceTotal: TipoPaquete.count()]
    }

    def create() {
        [tipoPaqueteInstance: new TipoPaquete(params)]
    }

    def save() {
        def tipoPaqueteInstance = TipoPaquete.findByCodigo(params.codigo)
		if (tipoPaqueteInstance) {
			tipoPaqueteInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [tipoPaqueteInstance: tipoPaqueteInstance])
			return
		}
        tipoPaqueteInstance = new TipoPaquete(params)
        if (!tipoPaqueteInstance.save(flush: true)) {
            render(view: "create", model: [tipoPaqueteInstance: tipoPaqueteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tipoPaquete.label', default: 'TipoPaquete'), tipoPaqueteInstance.codigo])
        redirect(action: "show", id: tipoPaqueteInstance.codigo)
    }

    def show(String id) {
        def tipoPaqueteInstance = TipoPaquete.findByCodigo(id)
        if (!tipoPaqueteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPaquete.label', default: 'TipoPaquete'), id])
            redirect(action: "list")
            return
        }

        [tipoPaqueteInstance: tipoPaqueteInstance]
    }

    def edit(String id) {
        def tipoPaqueteInstance = TipoPaquete.findByCodigo(id)
        if (!tipoPaqueteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPaquete.label', default: 'TipoPaquete'), id])
            redirect(action: "list")
            return
        }

        [tipoPaqueteInstance: tipoPaqueteInstance]
    }

    def update(String id, Long version) {
        def tipoPaqueteInstance = TipoPaquete.findByCodigo(id)
        if (!tipoPaqueteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPaquete.label', default: 'TipoPaquete'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tipoPaqueteInstance.version > version) {
                tipoPaqueteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tipoPaquete.label', default: 'TipoPaquete')] as Object[],
                          "Another user has updated this TipoPaquete while you were editing")
                render(view: "edit", model: [tipoPaqueteInstance: tipoPaqueteInstance])
                return
            }
        }

        tipoPaqueteInstance.properties = params

        if (!tipoPaqueteInstance.save(flush: true)) {
            render(view: "edit", model: [tipoPaqueteInstance: tipoPaqueteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoPaquete.label', default: 'TipoPaquete'), tipoPaqueteInstance.codigo])
        redirect(action: "show", id: tipoPaqueteInstance.codigo)
    }

    def delete(String id) {
        def tipoPaqueteInstance = TipoPaquete.findByCodigo(id)
        if (!tipoPaqueteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPaquete.label', default: 'TipoPaquete'), id])
            redirect(action: "list")
            return
        }

        try {
            tipoPaqueteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoPaquete.label', default: 'TipoPaquete'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tipoPaquete.label', default: 'TipoPaquete'), id])
            redirect(action: "show", id: id)
        }
    }
}
