package cl.adexus.helpers

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class DataSourceHelper {

	def formatDatetimeFunction(){
		//TODO: Usar inyeccion de dependencia
		def fFunc="FORMAT"
		switch(ConfigurationHolder.config.dataSource.dialect) {
			case "org.hibernate.dialect.H2Dialect" :
				fFunc="FORMATDATETIME"
				break;
			case "org.hibernate.dialect.SQLServerDialect":
				fFunc="FORMAT"
				break;
		}
		return fFunc
	}
	
	def getConcatIni(){
		//TODO: Usar inyeccion de dependencia
		def text=""
		switch(ConfigurationHolder.config.dataSource.dialect) {
			case "org.hibernate.dialect.H2Dialect" :
				text= "CONCAT("
				break;
			case "org.hibernate.dialect.SQLServerDialect":
				text= ""
				break;
		}
		return text
	}

	def getConcatOperator(){
		//TODO: Usar inyeccion de dependencia
		def text=""
		switch(ConfigurationHolder.config.dataSource.dialect) {
			case "org.hibernate.dialect.H2Dialect" :
				text= ", ' ', "
				break;
			case "org.hibernate.dialect.SQLServerDialect":
				text= " + ' ' + "
				break;
		}
		return text
	}
	
	def getConcatFin(){
		//TODO: Usar inyeccion de dependencia
		def text=""
		switch(ConfigurationHolder.config.dataSource.dialect) {
			case "org.hibernate.dialect.H2Dialect" :
				text= ")"
				break;
			case "org.hibernate.dialect.SQLServerDialect":
				text= ""
				break;
		}
		return text
	}
	def booleanValueFunction(booleanValue) {
		def fFunc
		if (booleanValue instanceof Boolean)
			fFunc = booleanValue
		else if (booleanValue instanceof String)
			fFunc = booleanValue.toBoolean()
		else
			return null
		switch(ConfigurationHolder.config.dataSource.dialect) {
			case "org.hibernate.dialect.H2Dialect" :
				fFunc = "TRUE"
				break
			case "org.hibernate.dialect.SQLServerDialect":
				fFunc = booleanValue ? 1 : 0
				break
		}
		return fFunc
	}
	
	def dateValueFuncion(dateValue) {
		if (!dateValue) { return null }
		def fFunc
		switch(ConfigurationHolder.config.dataSource.dialect) {
			case "org.hibernate.dialect.H2Dialect" :
				Calendar cal = Calendar.getInstance()
				cal.setTime(dateValue)
				cal.set(Calendar.HOUR_OF_DAY, 0)
				cal.set(Calendar.MINUTE, 0)
				cal.set(Calendar.SECOND, 0)
				cal.set(Calendar.MILLISECOND, 0)
				fFunc = cal.getTime()
				break
			case "org.hibernate.dialect.SQLServerDialect":
				fFunc = new java.text.SimpleDateFormat("yyyy-MM-dd").format(dateValue)
				break
		}
		return fFunc
	}
	
}
