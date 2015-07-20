package cl.adexus.helpers

import java.util.Date;

class FechaHoraHelper {
	
	/**
	 * Agrega la parte de "hora" a un Date.
	 * Si el date viene nulo, usa el date de hoy
	 *
	 */
	
	def static Date horaToDate(String horaStr){
		return horaToDate(horaStr,null);
	}
	
	def static Date horaToDate(String horaStr,Date soloFecha){
		println("Ejecutando horaToDate : $soloFecha, $horaStr")
		if("".equals(horaStr)) return null
		if(soloFecha==null) soloFecha=new Date()
		def cal = Calendar.instance
		//Agregamos la parte fecha:
		cal.setTime(soloFecha)
		
		//Agregamos la hora y los minutos
		if(horaStr != null){
			cal.set(Calendar.HOUR_OF_DAY, horaStr.split(":")[0].toInteger())
			cal.set(Calendar.MINUTE, horaStr.split(":")[1].toInteger())
			cal.set(Calendar.SECOND, 0)
			
			//La idea es detectar si la hora 00:00:00 es seteada o fue solo parte del new Date()
			cal.set(Calendar.MILLISECOND, 666) //La parte que no lo gusta a gonzalo ;)
		}

		return cal.getTime()
	}
	
	def static float HoursToFloat(String tmpHours){
     float result = 0;
     tmpHours = tmpHours.trim();

     // Try converting to float first
     try{
        result = new Float(tmpHours);
     }
     catch(NumberFormatException nfe){
		 
         // OK so that didn't work.  Did they use a colon?
         if(tmpHours.contains(":")){
             int hours = 0;
             int minutes = 0;
             int locationOfColon = tmpHours.indexOf(":");
             try {
                  hours = new Integer(tmpHours.substring(0, locationOfColon));
                  minutes = new Integer(tmpHours.substring(locationOfColon+1));
             }
             catch(NumberFormatException nfe2) {
                  return null
             }

             //add in partial hours (ie minutes if minutes are greater than zero.
             if(minutes > 0) {
                 result = minutes / 60;
             }

             //now add in the full number of hours.
             result += hours;
         }
     }

     return result;
 }

	def static Date stringToDate(String fecha, String formato) {
		return new java.text.SimpleDateFormat(formato).parse(fecha)
	}
	
	def static Date stringToDate(String fecha) {
		if (!fecha) return null
		if ("".equals(fecha)) return null
		return stringToDate(fecha, "dd-MM-yyyy")
	}
	
	def static Date hace10minutos(){
		def cal = Calendar.instance
		cal.add(Calendar.MINUTE, -10)
		return cal.getTime()
	}

	/**
	 * Obtiene la cantidad de años que hay entre fechas
	 * @param hoy Fecha de termino
	 * @param nacimiento Fecha de inicio
	 * @return Diferencia de años que hay entre la fecha de inicio y termino
	 */
	def static Integer diffYears(Date hoy, Date nacimiento) {
		Calendar fechaNacimiento = Calendar.getInstance()
		Calendar fechaActual = Calendar.getInstance()
		fechaNacimiento.setTime(nacimiento)
		fechaActual.setTime(hoy)
		//Se restan la fecha actual y la fecha de nacimiento
		int ano = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR)
		int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH)
		int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE)
		//Se ajusta el año dependiendo el mes y el día
		if (mes < 0 || (mes == 0 && dia < 0))
			ano--
		return ano
	}
	
	/**
	 * Valida si la fecha <b>value</b> está entre las horas desde y hasta
	 * @param horaDesde Hora de inicio
	 * @param horaHasta Hora de termino
	 * @param value Fecha a evaluar
	 * @return Identificador booleano que indica si la fecha está entre las horas indicadas
	 */
	def static boolean betweenHours(String horaDesde, String horaHasta, Date value) {
		Calendar f1 = Calendar.getInstance()
		f1.setTime(value)
		f1.set(Calendar.HOUR_OF_DAY, horaDesde.split(":")[0].toInteger())
		f1.set(Calendar.MINUTE, horaDesde.split(":")[1].toInteger())
		f1.set(Calendar.SECOND, 0)
		f1.set(Calendar.MILLISECOND, 0)
		Calendar f2 = Calendar.getInstance()
		f2.setTime(value)
		f2.set(Calendar.HOUR_OF_DAY, horaHasta.split(":")[0].toInteger())
		f2.set(Calendar.MINUTE, horaHasta.split(":")[1].toInteger())
		f2.set(Calendar.SECOND, 0)
		f2.set(Calendar.MILLISECOND, 0)
		return betweenDates(f1.getTime(), f2.getTime(), value)
	}
	
	/**
	 * Valida si la fecha <b>value</b> est� entre fechas desde y hasta
	 * @param desde Fecha de inicio
	 * @param hasta Fecha de termino
	 * @param value Fecha a evaluar
	 * @return Identificador booleano que indica si la fecha esta entre las fechas indicadas
	 */
	def static boolean betweenDates(Date desde, Date hasta, Date value) {
		return	( value >= desde ) && ( value <= hasta)
	}
	
	/**
	 * Inicializa el tiempo de una fecha en cero
	 * @param date Fecha a inicializar
	 * @return Fecha con el tiempo inicializado en cero
	 */
	def static Date setTimeDate(Date date) {
		Calendar f1 = Calendar.getInstance()
		f1.setTime(date)
		f1.set(Calendar.HOUR_OF_DAY, 0)
		f1.set(Calendar.MINUTE, 0)
		f1.set(Calendar.SECOND, 0)
		f1.set(Calendar.MILLISECOND, 0)
		return f1.getTime()
	}
	
	/**
	 * Valida si una fecha est� en un rango de d�as y horas
	 * @param date Fecha a validar
	 * @param dayMin D�a inicial, se debe utilizar constantes del objeto Calendar
	 * @param dayMax D�a final, se debe utilizar constantes del objeto Calendar
	 * @param hourMin Hora inicial, formato HH:MM
	 * @param hourMax Hora final, formato HH:MM
	 * @return Identificador booleano que indica si la fecha se encuentra en el rango de d�as y horas indicados
	 */
	def static boolean isBetweenDayAndHours(Date date, int dayMin, int dayMax, String hourMin, String hourMax) {
		// Validar parametros
		if (hourMin?.trim().indexOf(":") == -1 ||
			hourMax?.trim().indexOf(":") == -1 ||
			dayMin > 7 || dayMax > 7 ||
			date == null) {
			return false
		}
			
		// Inicializar calendarios
		Calendar c1 = Calendar.getInstance()
		Calendar c2 = Calendar.getInstance()
		Calendar c3 = Calendar.getInstance()
		c1.setTime(date)
		c1.set(Calendar.MILLISECOND, 0)
		c2.setTime(setTimeDate(date))
		c2.set(Calendar.HOUR_OF_DAY, hourMin.split(":")[0].toInteger())
		c2.set(Calendar.MINUTE, hourMin.split(":")[1].toInteger())
		c3.setTime(setTimeDate(date))
		c3.set(Calendar.HOUR_OF_DAY, hourMax.split(":")[0].toInteger())
		c3.set(Calendar.MINUTE, hourMax.split(":")[1].toInteger())
		// Validar rango de dias
		def diasValidate = c1.get(Calendar.DAY_OF_WEEK) >= dayMin && c1.get(Calendar.DAY_OF_WEEK) <= dayMax
		if (diasValidate) {
			// Validar hora
			def valHoras = betweenHours(hourMin, hourMax, c1.getTime())
			return valHoras
		} else {
			return diasValidate
		}
	}
	
	def static long diffDates(Date desde) {
		if (desde == null) {return -1}
		return diffDates(desde, new Date())
	}
	
	def static long diffDates(Date desde, Date hasta) {
		if (desde == null || hasta == null) return -1
		long diferencia = hasta.getTime() - desde.getTime();
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ((long) dias);
	}
}
