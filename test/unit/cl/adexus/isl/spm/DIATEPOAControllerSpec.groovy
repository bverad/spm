package cl.adexus.isl.spm

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(DIATEPOAController)
class DIATEPOAControllerSpec extends Specification {
	void "showMessage test"(){
		when:
		controller.showMessage()

		then:
		response.text == 'Hola mundo'
	}

	void "redirectExample test"(){
		when:
		controller.redirectExample()

		then:
		response.redirectedUrl == '/DIATEPOA/r01'

	}
}
