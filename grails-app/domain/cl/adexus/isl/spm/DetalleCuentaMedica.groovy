package cl.adexus.isl.spm

@gorm.AuditStamp
class DetalleCuentaMedica {

	//Datos ingresados por el prestador
	Date fecha // Guarda la fecha y la hora
	String codigo
	String glosa
	Integer cantidad
	Long valorUnitario
	Long descuentoUnitario
	Long recargoUnitario
	Long valorTotal

	//Datos obtenidos del convenio
	Long valorUnitarioPactado
	Long recargoUnitarioPactado
	Long valorTotalPactado //Se calcula con: cantidad*(valorUnitarioPactado-descuentoUnitario+recargoUnitarioPactado)
	String glosaPactada

	//Datos finales resultado del analisis
	Integer cantidadFinal
	Long descuentoFinal
	Long valorUnitarioFinal
	Long recargoUnitarioFinal
	Long valorTotalFinal //Se calcula con: cantidadFinal*(valorUnitarioFinal-descuentoFinal+recargoUnitarioFinal)

	String consultaMedica
	String respuestaMedicaTexto
	Boolean respuestaMedicaSugiereAprobar // null, true (Aprobar), false (Rechazar)
	Long respuestaMedicaModificarCantidad // null o una cantidad

	String consultaConvenio
	String respuestaConvenioTexto
	Boolean respuestaConvenioSugiereAprobar // null, true (Aprobar), false (Rechazar)
	Long respuestaConvenioModificarMonto // null o una cantidad

	static belongsTo = [cuentaMedica: CuentaMedica]

	static constraints = {
		codigo maxSize: 255
		glosa maxSize: 255
		
		descuentoUnitario nullable: true
		recargoUnitario nullable: true
		valorTotal nullable: true

		valorUnitarioPactado nullable: true
		recargoUnitarioPactado nullable: true
		valorTotalPactado nullable: true
		glosaPactada nullable: true, maxSize: 255

		cantidadFinal nullable: true
		descuentoFinal nullable: true
		valorUnitarioFinal nullable: true
		recargoUnitarioFinal nullable: true
		valorTotalFinal nullable: true

		consultaMedica nullable: true, maxSize: 255
		respuestaMedicaTexto nullable: true, maxSize: 255
		respuestaMedicaSugiereAprobar nullable: true
		respuestaMedicaModificarCantidad nullable: true

		consultaConvenio nullable: true, maxSize: 255
		respuestaConvenioTexto nullable: true, maxSize: 255
		respuestaConvenioSugiereAprobar nullable: true
		respuestaConvenioModificarMonto nullable: true
	}
}
