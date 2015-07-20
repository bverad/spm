package cl.adexus.constraints

class FechaHastaHoyConstraint {
	static name = "fechaHoy"
	static defaultMessageCode = "default.not.fechaHoy.message"
	static defaultMessage = "El fecha no puede ser mayor a hoy"

	def supports = { type ->
		return type!= null && Date.class.isAssignableFrom(type)
	}

	def validate = { fecha ->
		try{
			def fecVal = Calendar.getInstance()
			fecVal.setTime(fecha)
			fecVal.set(Calendar.HOUR_OF_DAY, 0)
			fecVal.set(Calendar.MINUTE, 0)
			fecVal.set(Calendar.SECOND, 0)
			fecVal.set(Calendar.MILLISECOND, 0)
			def fecHoy = Calendar.getInstance()
			fecHoy.set(Calendar.HOUR_OF_DAY, 0)
			fecHoy.set(Calendar.MINUTE, 0)
			fecHoy.set(Calendar.SECOND, 0)
			fecHoy.set(Calendar.MILLISECOND, 0)
			return !fecVal.after(fecHoy) 
		} catch (Exception e) {
			return false
		}
	}

}
