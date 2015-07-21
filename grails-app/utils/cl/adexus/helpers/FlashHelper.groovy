package cl.adexus.helpers

import grails.util.Holders;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import cl.adexus.isl.spm.*;

class FlashHelper {

   def seguimientoService

   def Map serializeModel(Map model) {
		Map newModel = new HashMap()
        for (Map.Entry<String, Object> entry : model.entrySet()) {
			newModel.put(entry.getKey(), getMappedValue(entry.getValue()))
        }
        return newModel
    }
    
    def Map deserializeModel(Map model) {
		Map newModel = new HashMap()
        for (Map.Entry<String, Object> entry : model.entrySet()) {
			newModel.put(entry.getKey(), getDomainObject(entry.getValue()))
        }
        return newModel
    }
	
	private def Object getMappedValue(_value) {
		if (!_value) { return null }
		// Validar si es lista o map para realizar una llamada recursiva
		if (_value instanceof java.util.List) {
			def newList = new ArrayList()
			// ver como hacerlo sin utilizar otro objeto lista
			for (Object obj : _value) {
				newList.add(getMappedValue(obj))
			}
			return newList
		} else if (_value instanceof java.util.Map) {
			for (Map.Entry<String, Object> entry : ((Map)_value).entrySet()) {
				((Map)_value).put(entry.getKey(), getMappedValue(entry.getValue()))
			}
			return _value
		}
		def isDomain = Holders.grailsApplication.isDomainClass(_value.class)
		if (!isDomain) { return _value }
		
		def mappedClass = [:]
		Class cls = _value.class
		// Se recorren atributos del objeto
		cls.declaredFields.each {
			// TODO: Se podría recorrer la variable $defaultDatabindingWhiteList que es
			// 		 un lindo map con todo lo de la entidad.
			//		 Por ahora tenemos esta hermosa solución definitiva
			//println "Atributo: [${it}]"
			if (it.getType() == java.lang.String	||
				it.getType() == java.lang.Integer	||
				it.getType() == java.lang.Double	||
				it.getType() == java.lang.Float		||
				it.getType() == java.lang.Long		||
				it.getType() == java.util.Date) {
				it.setAccessible(true)
				Object value = it.get(_value)
				if (value) {
					mappedClass.put(it.getName(), value)
				}
			}
		}
		// Veamos si tiene errores
		if (_value.hasErrors()) {
			def errores = []
			_value.errors.allErrors.each {
				// Se va a buscar el string en el archivo de mensajes
				def msg
				try {
					msg = Holders.grailsApplication.getMainContext().getMessage(it, Locale.default)
				} catch (NoSuchMessageException ex) {
					msg = e.defaultMessage ?: "Sin mensaje de error"
				} catch (Exception ex) {
					msg = e.defaultMessage ?: "Error al obtener mensaje de error"
				}
				errores.add(msg)
			}
			mappedClass.put("errores", errores)
		}
		// Se guardar clase en el map
		mappedClass.put("class", cls)
		return mappedClass
	}
	
	private def Object getDomainObject(_value) {
		if (!_value) { return null }
		// Si es distinto a un Map retornar
		if (!(_value instanceof java.util.Map)) { return _value }
		// Si es map pero no tiene flag de dominio retornar
		def isDomain = Holders.grailsApplication.isDomainClass(_value.class)
		if (!isDomain) { return _value }
		// Se crea instancia de dominio
		Object newInstance
		println "Map ID: ${_value}"
		// Validamos si el objeto a mapear tiene ID
		if (_value.id) {
			println "ID: ${_value.id}"
			newInstance = seguimientoService.getUtils(_value.class, _value.id)
			// TODO: Setear atributos del map al objeto obtenido
			println "newInstance: ${newInstance}"
		} else {
			newInstance = _value.class.newInstance(_value)
		}
		println "Obj ID: ${newInstance?.id}"
		// Agregar errores en caso de que tenga
		_value.errores?.each {
			println it
			newInstance.errors.reject("dummyAttribute", it)
		}
		return newInstance
	}
}