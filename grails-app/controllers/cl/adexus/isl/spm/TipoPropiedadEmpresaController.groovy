package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class TipoPropiedadEmpresaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tipoPropiedadEmpresaInstanceList: TipoPropiedadEmpresa.list(params), tipoPropiedadEmpresaInstanceTotal: TipoPropiedadEmpresa.count()]
    }

    def create() {
        [tipoPropiedadEmpresaInstance: new TipoPropiedadEmpresa(params)]
    }

    def save() {
        def tipoPropiedadEmpresaInstance = TipoPropiedadEmpresa.findByCodigo(params.codigo)
		if (tipoPropiedadEmpresaInstance) {
			tipoPropiedadEmpresaInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [tipoPropiedadEmpresaInstance: tipoPropiedadEmpresaInstance])
			return
		}
        tipoPropiedadEmpresaInstance = new TipoPropiedadEmpresa(params)
        if (!tipoPropiedadEmpresaInstance.save(flush: true)) {
            render(view: "create", model: [tipoPropiedadEmpresaInstance: tipoPropiedadEmpresaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa'), tipoPropiedadEmpresaInstance.codigo])
        redirect(action: "show", id: tipoPropiedadEmpresaInstance.codigo)
    }

    def show(String id) {
        def tipoPropiedadEmpresaInstance = TipoPropiedadEmpresa.findByCodigo(id)
        if (!tipoPropiedadEmpresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa'), id])
            redirect(action: "list")
            return
        }

        [tipoPropiedadEmpresaInstance: tipoPropiedadEmpresaInstance]
    }

    def edit(String id) {
        def tipoPropiedadEmpresaInstance = TipoPropiedadEmpresa.findByCodigo(id)
        if (!tipoPropiedadEmpresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa'), id])
            redirect(action: "list")
            return
        }

        [tipoPropiedadEmpresaInstance: tipoPropiedadEmpresaInstance]
    }

    def update(String id, Long version) {
        def tipoPropiedadEmpresaInstance = TipoPropiedadEmpresa.findByCodigo(id)
        if (!tipoPropiedadEmpresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tipoPropiedadEmpresaInstance.version > version) {
                tipoPropiedadEmpresaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa')] as Object[],
                          "Another user has updated this TipoPropiedadEmpresa while you were editing")
                render(view: "edit", model: [tipoPropiedadEmpresaInstance: tipoPropiedadEmpresaInstance])
                return
            }
        }

        tipoPropiedadEmpresaInstance.properties = params

        if (!tipoPropiedadEmpresaInstance.save(flush: true)) {
            render(view: "edit", model: [tipoPropiedadEmpresaInstance: tipoPropiedadEmpresaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa'), tipoPropiedadEmpresaInstance.codigo])
        redirect(action: "show", id: tipoPropiedadEmpresaInstance.codigo)
    }

    def delete(String id) {
        def tipoPropiedadEmpresaInstance = TipoPropiedadEmpresa.findByCodigo(id)
        if (!tipoPropiedadEmpresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa'), id])
            redirect(action: "list")
            return
        }

        try {
            tipoPropiedadEmpresaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tipoPropiedadEmpresa.label', default: 'TipoPropiedadEmpresa'), id])
            redirect(action: "show", id: id)
        }
    }
}
