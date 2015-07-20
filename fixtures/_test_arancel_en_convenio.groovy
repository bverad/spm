import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat


fixture {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	ArancelConvenio.findById(1)?:(new ArancelConvenio([
		codigoPrestacion: ArancelBase.findById(1).codigo, 
		calculo: '10%', 
		cargo: true, 
		convenio: Convenio.findById(1), 
		descuento: false, 
		nivel: 'Nivel 1', 
		pesos: false,
		porcentaje: true, 
		valor: 10, 
		valor_nuevo: 8822, 
		valor_original: 8020,
		desde: df.parse('2014-01-01 00:00:00'),
		])).save(flush:true)
	ArancelConvenio.findById(2)?:(new ArancelConvenio([
		codigoPrestacion: ArancelBase.findById(2).codigo, 
		calculo: '10%', 
		cargo: true, 
		convenio: Convenio.findById(1), 
		descuento: false, 
		nivel: 'Nivel 1', 
		pesos: false, 
		porcentaje: true, 
		valor: 10, 
		valor_nuevo: 12144, 
		valor_original: 11040,
		desde: df.parse('2014-01-01 00:00:00'),
		])).save(flush:true)
	ArancelConvenio.findById(3)?:(new ArancelConvenio([ 
		codigoPrestacion: ArancelBase.findById(5).codigo, 
		calculo: '10%', 
		cargo: true, 
		convenio: Convenio.findById(1), 
		descuento: false, 
		nivel: 'Nivel 1', 
		pesos: false, 
		porcentaje: true, 
		valor: 10, 
		valor_nuevo: 15642, 
		valor_original: 14220,
		desde: df.parse('2014-01-01 00:00:00'),
		])).save(flush:true)
	ArancelConvenio.findById(4)?:(new ArancelConvenio([ 
		codigoPrestacion: ArancelBase.findById(3).codigo, 
		calculo: '10%', 
		cargo: true, 
		convenio: Convenio.findById(1), 
		descuento: false, 
		nivel: 'Nivel 1', 
		pesos: false, 
		porcentaje: true, 
		valor: 10, 
		valor_nuevo: 13552, 
		valor_original: 12320,
		desde: df.parse('2014-01-01 00:00:00'),
		])).save(flush:true)
	ArancelConvenio.findById(5)?:(new ArancelConvenio([ 
		codigoPrestacion: ArancelBase.findById(4).codigo, 
		calculo: '10%', 
		cargo: true, 
		convenio: Convenio.findById(1), 
		descuento: false, 
		nivel: 'Nivel 1', 
		pesos: false, 
		porcentaje: true, 
		valor: 10, 
		valor_nuevo: 10428, 
		valor_original: 9480,
		desde: df.parse('2014-01-01 00:00:00'),
		])).save(flush:true)

}