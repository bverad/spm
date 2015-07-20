import cl.adexus.isl.spm.*

fixture {

	CuestionarioComplejidad.findById(1)?:(new CuestionarioComplejidad([ version: 1, pregunta1: true, pregunta2: true, pregunta3: true, pregunta4: true, pregunta5: true, pregunta6: true, complejidadCalculada: 1, aceptaPropuesta: false])).save(flush:true)
	CuestionarioComplejidad.findById(2)?:(new CuestionarioComplejidad([ version: 1, pregunta1: true, pregunta2: true, pregunta3: true, pregunta4: true, pregunta5: true, pregunta6: true, complejidadCalculada: 0, aceptaPropuesta: false])).save(flush:true)
	CuestionarioComplejidad.findById(3)?:(new CuestionarioComplejidad([ version: 1, pregunta1: true, pregunta2: true, pregunta3: true, pregunta4: true, pregunta5: true, pregunta6: true, complejidadCalculada: 1, aceptaPropuesta: true])).save(flush:true)

}