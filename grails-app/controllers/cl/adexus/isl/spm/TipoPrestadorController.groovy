package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class TipoPrestadorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tipoPrestadorInstanceList: TipoPrestador.list(params), tipoPrestadorInstanceTotal: TipoPrestador.count()]
    }

    def create() {
        [tipoPrestadorInstance: new TipoPrestador(params)]
    }

    def save() {
        def tipoPrestadorInstance = TipoPrestador.findByCodigo(params.codigo)
		if (tipoPrestadorInstance) {
			tipoPrestadorInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [tipoPrestadorInstance: tipoPrestadorInstance])
			return
		}
        tipoPrestadorInstance = new TipoPrestador(params)
        if (!tipoPrestadorInstance.save(flush: true)) {
            render(view: "create", model: [tipoPrestadorInstance: tipoPrestadorInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tipoPrestador.label', default: 'TipoPrestador'), tipoPrestadorInstance.codigo])
        redirect(action: "show", id: tipoPrestadorInstance.codigo)
    }

    def show(String id) {
        def tipoPrestadorInstance = TipoPrestador.findByCodigo(id)
        if (!tipoPrestadorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPrestador.label', default: 'TipoPrestador'), id])
            redirect(action: "list")
            return
        }

        [tipoPrestadorInstance: tipoPrestadorInstance]
    }

    def edit(String id) {
        def tipoPrestadorInstance = TipoPrestador.findByCodigo(id)
        if (!tipoPrestadorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPrestador.label', default: 'TipoPrestador'), id])
            redirect(action: "list")
            return
        }

        [tipoPrestadorInstance: tipoPrestadorInstance]
    }

    def update(String id, Long version) {
        def tipoPrestadorInstance = TipoPrestador.findByCodigo(id)
        if (!tipoPrestadorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPrestador.label', default: 'TipoPrestador'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tipoPrestadorInstance.version > version) {
                tipoPrestadorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tipoPrestador.label', default: 'TipoPrestador')] as Object[],
                          "Another user has updated this TipoPrestador while you were editing")
                render(view: "edit", model: [tipoPrestadorInstance: tipoPrestadorInstance])
                return
            }
        }

        tipoPrestadorInstance.properties = params

        if (!tipoPrestadorInstance.save(flush: true)) {
            render(view: "edit", model: [tipoPrestadorInstance: tipoPrestadorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoPrestador.label', default: 'TipoPrestador'), tipoPrestadorInstance.codigo])
        redirect(action: "show", id: tipoPrestadorInstance.codigo)
    }

    def delete(String id) {
        def tipoPrestadorInstance = TipoPrestador.findByCodigo(id)
        if (!tipoPrestadorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPrestador.label', default: 'TipoPrestador'), id])
            redirect(action: "list")
            return
        }

        try {
            tipoPrestadorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoPrestador.label', default: 'TipoPrestador'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tipoPrestador.label', default: 'TipoPrestador'), id])
            redirect(action: "show", id: id)
        }
    }
}
