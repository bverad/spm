package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class BancoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bancoInstanceList: Banco.list(params), bancoInstanceTotal: Banco.count()]
    }

    def create() {
        [bancoInstance: new Banco(params)]
    }

    def save() {
        def bancoInstance = Banco.findByCodigo(params.codigo)
		if (bancoInstance) {
			bancoInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [bancoInstance: bancoInstance])
			return
		}
        bancoInstance = new Banco(params)
        if (!bancoInstance.save(flush: true)) {
            render(view: "create", model: [bancoInstance: bancoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'banco.label', default: 'Banco'), bancoInstance.codigo])
        redirect(action: "show", id: bancoInstance.codigo)
    }

    def show(String id) {
        def bancoInstance = Banco.findByCodigo(id)
        if (!bancoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'banco.label', default: 'Banco'), id])
            redirect(action: "list")
            return
        }

        [bancoInstance: bancoInstance]
    }

    def edit(String id) {
        def bancoInstance = Banco.findByCodigo(id)
        if (!bancoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'banco.label', default: 'Banco'), id])
            redirect(action: "list")
            return
        }

        [bancoInstance: bancoInstance]
    }

    def update(String id, Long version) {
        def bancoInstance = Banco.findByCodigo(id)
        if (!bancoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'banco.label', default: 'Banco'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bancoInstance.version > version) {
                bancoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'banco.label', default: 'Banco')] as Object[],
                          "Another user has updated this Banco while you were editing")
                render(view: "edit", model: [bancoInstance: bancoInstance])
                return
            }
        }

        bancoInstance.properties = params

        if (!bancoInstance.save(flush: true)) {
            render(view: "edit", model: [bancoInstance: bancoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'banco.label', default: 'Banco'), bancoInstance.codigo])
        redirect(action: "show", id: bancoInstance.codigo)
    }

    def delete(String id) {
        def bancoInstance = Banco.findByCodigo(id)
        if (!bancoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'banco.label', default: 'Banco'), id])
            redirect(action: "list")
            return
        }

        try {
            bancoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'banco.label', default: 'Banco'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'banco.label', default: 'Banco'), id])
            redirect(action: "show", id: id)
        }
    }
}
