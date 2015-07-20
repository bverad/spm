package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class TipoConvenioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tipoConvenioInstanceList: TipoConvenio.list(params), tipoConvenioInstanceTotal: TipoConvenio.count()]
    }

    def create() {
        [tipoConvenioInstance: new TipoConvenio(params)]
    }

    def save() {
        def tipoConvenioInstance = TipoConvenio.findByCodigo(params.codigo)
		if (tipoConvenioInstance) {
			tipoConvenioInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [tipoConvenioInstance: tipoConvenioInstance])
			return
		}
        tipoConvenioInstance = new TipoConvenio(params)
        if (!tipoConvenioInstance.save(flush: true)) {
            render(view: "create", model: [tipoConvenioInstance: tipoConvenioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tipoConvenio.label', default: 'TipoConvenio'), tipoConvenioInstance.codigo])
        redirect(action: "show", id: tipoConvenioInstance.codigo)
    }

    def show(String id) {
        def tipoConvenioInstance = TipoConvenio.findByCodigo(id)
        if (!tipoConvenioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoConvenio.label', default: 'TipoConvenio'), id])
            redirect(action: "list")
            return
        }

        [tipoConvenioInstance: tipoConvenioInstance]
    }

    def edit(String id) {
        def tipoConvenioInstance = TipoConvenio.findByCodigo(id)
        if (!tipoConvenioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoConvenio.label', default: 'TipoConvenio'), id])
            redirect(action: "list")
            return
        }

        [tipoConvenioInstance: tipoConvenioInstance]
    }

    def update(String id, Long version) {
        def tipoConvenioInstance = TipoConvenio.findByCodigo(id)
        if (!tipoConvenioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoConvenio.label', default: 'TipoConvenio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tipoConvenioInstance.version > version) {
                tipoConvenioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tipoConvenio.label', default: 'TipoConvenio')] as Object[],
                          "Another user has updated this TipoConvenio while you were editing")
                render(view: "edit", model: [tipoConvenioInstance: tipoConvenioInstance])
                return
            }
        }

        tipoConvenioInstance.properties = params

        if (!tipoConvenioInstance.save(flush: true)) {
            render(view: "edit", model: [tipoConvenioInstance: tipoConvenioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoConvenio.label', default: 'TipoConvenio'), tipoConvenioInstance.codigo])
        redirect(action: "show", id: tipoConvenioInstance.codigo)
    }

    def delete(String id) {
        def tipoConvenioInstance = TipoConvenio.findByCodigo(id)
        if (!tipoConvenioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoConvenio.label', default: 'TipoConvenio'), id])
            redirect(action: "list")
            return
        }

        try {
            tipoConvenioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoConvenio.label', default: 'TipoConvenio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tipoConvenio.label', default: 'TipoConvenio'), id])
            redirect(action: "show", id: id)
        }
    }
}
