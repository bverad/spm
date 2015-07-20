package cl.adexus.isl.spm

import org.springframework.dao.DataIntegrityViolationException

class EstructuraJuridicaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [estructuraJuridicaInstanceList: EstructuraJuridica.list(params), estructuraJuridicaInstanceTotal: EstructuraJuridica.count()]
    }

    def create() {
        [estructuraJuridicaInstance: new EstructuraJuridica(params)]
    }

    def save() {
        def estructuraJuridicaInstance = EstructuraJuridica.findByCodigo(params.codigo)
		if (estructuraJuridicaInstance) {
			estructuraJuridicaInstance.errors.rejectValue('codigo', 'El código ' + params.codigo + ' ya existe', null)
			render(view: "create", model: [estructuraJuridicaInstance: estructuraJuridicaInstance])
			return
		}
        estructuraJuridicaInstance = new EstructuraJuridica(params)
        if (!estructuraJuridicaInstance.save(flush: true)) {
            render(view: "create", model: [estructuraJuridicaInstance: estructuraJuridicaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica'), estructuraJuridicaInstance.codigo])
        redirect(action: "show", id: estructuraJuridicaInstance.codigo)
    }

    def show(String id) {
        def estructuraJuridicaInstance = EstructuraJuridica.findByCodigo(id)
        if (!estructuraJuridicaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica'), id])
            redirect(action: "list")
            return
        }

        [estructuraJuridicaInstance: estructuraJuridicaInstance]
    }

    def edit(String id) {
        def estructuraJuridicaInstance = EstructuraJuridica.findByCodigo(id)
        if (!estructuraJuridicaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica'), id])
            redirect(action: "list")
            return
        }

        [estructuraJuridicaInstance: estructuraJuridicaInstance]
    }

    def update(String id, Long version) {
        def estructuraJuridicaInstance = EstructuraJuridica.findByCodigo(id)
        if (!estructuraJuridicaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (estructuraJuridicaInstance.version > version) {
                estructuraJuridicaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica')] as Object[],
                          "Another user has updated this EstructuraJuridica while you were editing")
                render(view: "edit", model: [estructuraJuridicaInstance: estructuraJuridicaInstance])
                return
            }
        }

        estructuraJuridicaInstance.properties = params

        if (!estructuraJuridicaInstance.save(flush: true)) {
            render(view: "edit", model: [estructuraJuridicaInstance: estructuraJuridicaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica'), estructuraJuridicaInstance.codigo])
        redirect(action: "show", id: estructuraJuridicaInstance.codigo)
    }

    def delete(String id) {
        def estructuraJuridicaInstance = EstructuraJuridica.findByCodigo(id)
        if (!estructuraJuridicaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica'), id])
            redirect(action: "list")
            return
        }

        try {
            estructuraJuridicaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'estructuraJuridica.label', default: 'EstructuraJuridica'), id])
            redirect(action: "show", id: id)
        }
    }
}
