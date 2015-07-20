package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class TipoCentroSaludController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tipoCentroSaludInstanceList: TipoCentroSalud.list(params), tipoCentroSaludInstanceTotal: TipoCentroSalud.count()]
    }

    def create() {
        [tipoCentroSaludInstance: new TipoCentroSalud(params)]
    }

    def save() {
        def tipoCentroSaludInstance = TipoCentroSalud.findByCodigo(params.codigo)
		if (tipoCentroSaludInstance) {
			tipoCentroSaludInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [tipoCentroSaludInstance: tipoCentroSaludInstance])
			return
		}
        tipoCentroSaludInstance = new TipoCentroSalud(params)
        if (!tipoCentroSaludInstance.save(flush: true)) {
            render(view: "create", model: [tipoCentroSaludInstance: tipoCentroSaludInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud'), tipoCentroSaludInstance.codigo])
        redirect(action: "show", id: tipoCentroSaludInstance.codigo)
    }

    def show(String id) {
        def tipoCentroSaludInstance = TipoCentroSalud.findByCodigo(id)
        if (!tipoCentroSaludInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud'), id])
            redirect(action: "list")
            return
        }

        [tipoCentroSaludInstance: tipoCentroSaludInstance]
    }

    def edit(String id) {
        def tipoCentroSaludInstance = TipoCentroSalud.findByCodigo(id)
        if (!tipoCentroSaludInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud'), id])
            redirect(action: "list")
            return
        }

        [tipoCentroSaludInstance: tipoCentroSaludInstance]
    }

    def update(String id, Long version) {
        def tipoCentroSaludInstance = TipoCentroSalud.findByCodigo(id)
        if (!tipoCentroSaludInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tipoCentroSaludInstance.version > version) {
                tipoCentroSaludInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud')] as Object[],
                          "Another user has updated this TipoCentroSalud while you were editing")
                render(view: "edit", model: [tipoCentroSaludInstance: tipoCentroSaludInstance])
                return
            }
        }

        tipoCentroSaludInstance.properties = params

        if (!tipoCentroSaludInstance.save(flush: true)) {
            render(view: "edit", model: [tipoCentroSaludInstance: tipoCentroSaludInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud'), tipoCentroSaludInstance.codigo])
        redirect(action: "show", id: tipoCentroSaludInstance.codigo)
    }

    def delete(String id) {
        def tipoCentroSaludInstance = TipoCentroSalud.findByCodigo(id)
        if (!tipoCentroSaludInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud'), id])
            redirect(action: "list")
            return
        }

        try {
            tipoCentroSaludInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tipoCentroSalud.label', default: 'TipoCentroSalud'), id])
            redirect(action: "show", id: id)
        }
    }
}
