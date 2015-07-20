package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class TipoCuentaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tipoCuentaInstanceList: TipoCuenta.list(params), tipoCuentaInstanceTotal: TipoCuenta.count()]
    }

    def create() {
        [tipoCuentaInstance: new TipoCuenta(params)]
    }

    def save() {
        def tipoCuentaInstance = TipoCuenta.findByCodigo(params.codigo)
		if (tipoCuentaInstance) {
			tipoCuentaInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [tipoCuentaInstance: tipoCuentaInstance])
			return
		}
        tipoCuentaInstance = new TipoCuenta(params)
        if (!tipoCuentaInstance.save(flush: true)) {
            render(view: "create", model: [tipoCuentaInstance: tipoCuentaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tipoCuenta.label', default: 'TipoCuenta'), tipoCuentaInstance.codigo])
        redirect(action: "show", id: tipoCuentaInstance.codigo)
    }

    def show(String id) {
        def tipoCuentaInstance = TipoCuenta.findByCodigo(id)
        if (!tipoCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoCuenta.label', default: 'TipoCuenta'), id])
            redirect(action: "list")
            return
        }

        [tipoCuentaInstance: tipoCuentaInstance]
    }

    def edit(String id) {
        def tipoCuentaInstance = TipoCuenta.findByCodigo(id)
        if (!tipoCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoCuenta.label', default: 'TipoCuenta'), id])
            redirect(action: "list")
            return
        }

        [tipoCuentaInstance: tipoCuentaInstance]
    }

    def update(String id, Long version) {
        def tipoCuentaInstance = TipoCuenta.findByCodigo(id)
        if (!tipoCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoCuenta.label', default: 'TipoCuenta'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tipoCuentaInstance.version > version) {
                tipoCuentaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tipoCuenta.label', default: 'TipoCuenta')] as Object[],
                          "Another user has updated this TipoCuenta while you were editing")
                render(view: "edit", model: [tipoCuentaInstance: tipoCuentaInstance])
                return
            }
        }

        tipoCuentaInstance.properties = params

        if (!tipoCuentaInstance.save(flush: true)) {
            render(view: "edit", model: [tipoCuentaInstance: tipoCuentaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoCuenta.label', default: 'TipoCuenta'), tipoCuentaInstance.codigo])
        redirect(action: "show", id: tipoCuentaInstance.codigo)
    }

    def delete(String id) {
        def tipoCuentaInstance = TipoCuenta.findByCodigo(id)
        if (!tipoCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoCuenta.label', default: 'TipoCuenta'), id])
            redirect(action: "list")
            return
        }

        try {
            tipoCuentaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoCuenta.label', default: 'TipoCuenta'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tipoCuenta.label', default: 'TipoCuenta'), id])
            redirect(action: "show", id: id)
        }
    }
}
