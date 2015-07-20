package cl.adexus.helpers

import java.text.NumberFormat
import java.util.Calendar
import cl.adexus.isl.spm.*;

class FormatosHelper {

	def run(String runConDv){
		if (!runConDv) return ''
		return NumberFormat.getInstance(new Locale("ES", "CL")).format(
			Integer.parseInt(runConDv.substring(0,runConDv.length()-1)))+"-"+runConDv.substring(runConDv.length()-1);
	}
	
	def fechaCorta(Date fecha){
		if(!fecha) return ''
		def sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(fecha)
	}
	
	def horaCorta(Date fecha){
		if(!fecha) return ''
		def cal=Calendar.instance
		cal.setTime(fecha)
		if(cal.get(Calendar.MILLISECOND)==000) return ''
		def sdf = new java.text.SimpleDateFormat("HH:mm");
		return sdf.format(fecha)
	}
	
	def horaLarga(Date fecha){
		if(!fecha) return ''
		def sdf = new java.text.SimpleDateFormat("HH:mm:ss");
		return sdf.format(fecha)
	}
	
	def fechaCompleta(Date fecha) {
		if (!fecha) return ''
		def sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return sdf.format(fecha)
	}
	
	def static runFormatStatic(String runConDv){
		if (!runConDv) return ''
		return NumberFormat.getInstance(new Locale("ES", "CL")).format(
			Integer.parseInt(runConDv.substring(0,runConDv.length()-1)))+"-"+runConDv.substring(runConDv.length()-1);
	}
	
	def static isValidRutStatic(String runConDv) {
		
		if (!runConDv) return false
		
		if (!runConDv.substring(0,runConDv.length()-1).isInteger()) {
			return false
		}
		
		if (runConDv.size() < 2) {
			return false
		}
		
		char dv = runConDv.substring(runConDv.length()-1)
		int rut = Integer.parseInt(runConDv.substring(0,runConDv.length()-1))
				
		return ValidarRut(rut, dv)
	}
	
	public static boolean ValidarRut(int rut, char dv)
	{
		int m = 0, s = 1;
		for (; rut != 0; rut /= 10)
		{
			s = (s + rut % 10 * (9 - m++ % 6)) % 11;
		}
		return dv == (char) (s != 0 ? s + 47 : 75);
	}
	
	def static fechaCortaStatic(Date fecha){
		if(!fecha) return ''
		def sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(fecha)
	}

	def static sumarDiasStatic(Date fecha, int numDias){
		if(!fecha) return ''
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(Calendar.DATE, numDias);
		return fechaCortaStatic(c.getTime())
	}
		
	def static fechaHoraStatic(Date fecha){
		if(!fecha) return ''
		def sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
		return sdf.format(fecha)
	}
	
	def static invierteFormatoFecha(String fecha, String separador){
		def result = ""
		if (!fecha) return result
		
		def tmp = fecha.split(separador)
		
		if (tmp.size() == 3) {
			result = tmp[2] + separador + tmp[1] + separador + tmp[0]
		}
		
		return result
	}
	
	def static horaCortaStatic(Date fecha){
		if(!fecha) return ''
		def cal=Calendar.instance
		cal.setTime(fecha)
		if(cal.get(Calendar.MILLISECOND)==000) return ''
		def sdf = new java.text.SimpleDateFormat("HH:mm");
		return sdf.format(fecha)
	}

	def static decimalStatic(Float valor) {
		if (!valor) return ''
		def df = new java.text.DecimalFormat("#.0")
		return df.format(valor)
	}
	
	def static decimalComoHoraStatic(Float valor) {
		if (!valor) return ''
		def horas = (int) valor;
		def minutos = (int) (60 * (valor - horas));
		horas = horas < 10 ? "0${horas}" : horas
		minutos = minutos == 0 ? "0${minutos}" : minutos
		return "${horas}:${minutos}"
	}
	
	def static blankStatic(String quizasNulo){
		if(!quizasNulo) return ''
		else quizasNulo
	}
	
	def static blankNumStatic(Integer quizasNulo){
		if(!quizasNulo) return ''
		else quizasNulo.toString()
	}
	
	def static booleanString(String var) {
		if (!var) return null
		if ("".equals(var)) return null
		return Boolean.parseBoolean(var)
	}
	
	def static montoStatic(Float valor) {
		if (!valor) return 0
		def df = new java.text.DecimalFormat("###,###,###,###")
		return df.format(valor)

	}
	
	def static montosStatic(long valor) {
		if (!valor) return 0
		def df = new java.text.DecimalFormat("\$###,###,###,###")
		return df.format(valor)

	}
	
	def static truncateStatic(String value) {
		return truncateStatic(value, 30)
	}

	def static truncateStatic(value, at) {
		if (!value) return ""
		value = value.length() > at ? ( value.substring(0, at) + "..." ) : value
		return value
	}
	
	def static String rellenarconBlancos(String input, int largo){
		int largoString = input.length()
		String output=""
		for (int i =0; i+largoString<largo;i++)
		{
			output+=" "
		}
		output=input+output
		return output
	}

	def static String nombreUsuarioStatic(ShiroUser usuario){
		if (usuario == null)
		{
			return ""
		}
		return (usuario.nombres + " " + usuario.apellidoPaterno + " " + usuario.apellidoMaterno)
	}

}
