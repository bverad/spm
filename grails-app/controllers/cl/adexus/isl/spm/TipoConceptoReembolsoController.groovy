package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class TipoConceptoReembolsoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tipoConceptoReembolsoInstanceList: TipoConceptoReembolso.list(params), tipoConceptoReembolsoInstanceTotal: TipoConceptoReembolso.count()]
    }

    def create() {
        [tipoConceptoReembolsoInstance: new TipoConceptoReembolso(params)]
    }

    def save() {
        def tipoConceptoReembolsoInstance = TipoConceptoReembolso.findByCodigo(params.codigo)
		if (tipoConceptoReembolsoInstance) {
			tipoConceptoReembolsoInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [tipoConceptoReembolsoInstance: tipoConceptoReembolsoInstance])
			return
		}
        tipoConceptoReembolsoInstance = new TipoConceptoReembolso(params)
        if (!tipoConceptoReembolsoInstance.save(flush: true)) {
            render(view: "create", model: [tipoConceptoReembolsoInstance: tipoConceptoReembolsoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso'), tipoConceptoReembolsoInstance.codigo])
        redirect(action: "show", id: tipoConceptoReembolsoInstance.codigo)
    }

    def show(String id) {
        def tipoConceptoReembolsoInstance = TipoConceptoReembolso.findByCodigo(id)
        if (!tipoConceptoReembolsoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso'), id])
            redirect(action: "list")
            return
        }

        [tipoConceptoReembolsoInstance: tipoConceptoReembolsoInstance]
    }

    def edit(String id) {
        def tipoConceptoReembolsoInstance = TipoConceptoReembolso.findByCodigo(id)
        if (!tipoConceptoReembolsoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso'), id])
            redirect(action: "list")
            return
        }

        [tipoConceptoReembolsoInstance: tipoConceptoReembolsoInstance]
    }

    def update(String id, Long version) {
        def tipoConceptoReembolsoInstance = TipoConceptoReembolso.findByCodigo(id)
        if (!tipoConceptoReembolsoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tipoConceptoReembolsoInstance.version > version) {
                tipoConceptoReembolsoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso')] as Object[],
                          "Another user has updated this TipoConceptoReembolso while you were editing")
                render(view: "edit", model: [tipoConceptoReembolsoInstance: tipoConceptoReembolsoInstance])
                return
            }
        }

        tipoConceptoReembolsoInstance.properties = params

        if (!tipoConceptoReembolsoInstance.save(flush: true)) {
            render(view: "edit", model: [tipoConceptoReembolsoInstance: tipoConceptoReembolsoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso'), tipoConceptoReembolsoInstance.codigo])
        redirect(action: "show", id: tipoConceptoReembolsoInstance.codigo)
    }

    def delete(String id) {
        def tipoConceptoReembolsoInstance = TipoConceptoReembolso.findByCodigo(id)
        if (!tipoConceptoReembolsoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso'), id])
            redirect(action: "list")
            return
        }

        try {
            tipoConceptoReembolsoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tipoConceptoReembolso.label', default: 'TipoConceptoReembolso'), id])
            redirect(action: "show", id: id)
        }
    }
}
