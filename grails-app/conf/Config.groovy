import org.apache.shiro.UnavailableSecurityManagerException

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
	all:           '*/*',
	atom:          'application/atom+xml',
	css:           'text/css',
	csv:           'text/csv',
	form:          'application/x-www-form-urlencoded',
	html:          ['text/html','application/xhtml+xml'],
	js:            'text/javascript',
	json:          ['application/json', 'text/json'],
	multipartForm: 'multipart/form-data',
	rss:           'application/rss+xml',
	text:          'text/plain',
	xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "html" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

//jboss eap deploy
/*grails.plugin.jbossas.fixHibernateValidator=true
grails.plugin.jbossas.removeLog4jWebxml=true
grails.plugin.jbossas.removeLog4jJars=true
grails.plugin.jbossas.deleteJarPatterns=['log4j', 'slf4j', 'jcl-over-slf4j', 'jul-to-slf4j']
grails.plugin.jbossas.templates=none*/

environments {
	development {
		/*grails.logging.jul.usebridge = true
		grails.dbconsole.enabled = true*/
		grails.gorm.failOnError= true
	}
	desarrollo_isl {
		grails.logging.jul.usebridge = false
		grails.dbconsole.enabled = true
		grails.gorm.failOnError= true
	}
	test {
		grails.logging.jul.usebridge = false
		grails.dbconsole.enabled = true
		//grails.app.context = "/isl-spm-test"
	}
	production {
		grails.dbconsole.enabled = false
		grails.logging.jul.usebridge = false
		//grails.app.context = "/"
		//grails.serverURL = "http://spm.isl.gob.cl"
	}
}

// log4j configuration
log4j = {
	//warn 'grails.app'
	//info 'grails.app.controller'
	
	//debug 'grails.app.service'
	
	
	appenders {
		console name:'stdout'
		rollingFile name: 'applog',
		file:'/opt/jboss-eap-6.1/log/grails.log',
		maxFileSize: '100MB'
		'null' name:'stacktrace' // prevent Grails trying to create stacktrace.log

		environments {
			desarrollo_isl {
				console name:'stdout'
				rollingFile name: 'applog',
				file:'D:/log/grails.log',
				maxFileSize: '100MB'
				'null' name:'stacktrace' // prevent Grails trying to create stacktrace.log
			}
		}
	}

	root {
		info 'stdout', 'applog'		
	}
	
	debug 'grails.app.controllers',
		  'grails.app.services'

	
	error  'org.codehaus.groovy.grails.web.servlet',        // controllers
			'org.codehaus.groovy.grails.web.pages',          // GSP
			'org.codehaus.groovy.grails.web.sitemesh',       // layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping',        // URL mapping
			'org.codehaus.groovy.grails.commons',            // core / classloading
			'org.codehaus.groovy.grails.plugins',            // plugins
			'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate'


	//Explicacion en http://grails.org/doc/2.2.x/guide/single.html#logging
	//level:   off    fatal    error    warn    info    debug    trace    all
	/*environments {
	 development {
	 off   "grails.app.taglib"
	 off   "grails.app.resourceMappers.org.grails.plugin.resource"
	 info  "grails.app"
	 //trace "org.hibernate.transaction", "org.hibernate.jdbc"
	 // Enable Hibernate SQL logging with param values
	 //trace 'org.hibernate.type'
	 //debug 'org.hibernate.SQL'
	 }
	 //Esto no lo pesca el JBOSS
	 desarollo_isl {
	 info "grails.app"
	 trace "org.hibernate.transaction", "org.hibernate.jdbc"
	 }
	 test {
	 warn "grails.app"
	 }
	 production {
	 error "grails.app"
	 }
	 }*/
}

//Aportados
grails.sitemesh.default.layout = 'isl_layout'

environments {
	development {
		//Un correo gmail de prueba
		/*grails.mail.host = "smtp.gmail.com"
		grails.mail.port = 465
		grails.mail.username = "qaprestadores@gmail.com"
		grails.mail.password = "isl123456"
		grails.mail.props = ["mail.smtp.auth":"true",
			"mail.smtp.socketFactory.port":"465",
			"mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
			"mail.smtp.socketFactory.fallback":"false"]
		bccMail = ["<estefa.adexus@gmail.com>",
			"<francisca.quinones.a@gmail.com>"]
		logo = "web-app/images/logo.jpg"
		correoFijo = 1
		correo = "qaprestadores@gmail.com"*/
		grails.mail.host = "172.16.6.49"
		grails.mail.port = 25
		bccMail = ["<bverad@isl.gob.cl>",
			"<bruno.zeroleft@gmail.com>"]
		logo = "../images/logo.jpg"
		correoFijo = 1
		correo = "bverad@isl.gob.cl"
	}
	desarrollo_isl {
		grails.mail.host = "172.16.6.49"
		grails.mail.port = 25
		bccMail = ["<bverad@isl.gob.cl>",
			"<bruno.zeroleft@gmail.com>"]
		logo = "../images/logo.jpg"
		correoFijo = 1
		correo = "bverad@isl.gob.cl"

	}
	test {
		grails.mail.host = "172.16.6.49"
		grails.mail.port = 25
		bccMail = ["<slunam@isl.gob.cl>",
			"<bverad@isl.gob.cl>"]
		logo = "../images/logo.jpg"
		correoFijo = 1
		correo = "fjimenezv@isl.gob.cl"
	}
	
	test_isl {
		grails.mail.host = "172.16.6.49"
		grails.mail.port = 25
		bccMail = ["<bverad@isl.gob.cl>",
			"<bruno.zeroleft@gmail.com>"]
		logo = "../images/logo.jpg"
		correoFijo = 1
		correo = "bverad@isl.gob.cl"

	}
	
	production {
		grails.mail.host = "172.16.6.49"
		grails.mail.port = 25
		bccMail = ["LIZZY SOLEDAD VIDAL NEIRA <lvidaln@isl.gob.cl>"]
		logo = "../images/logo.jpg"
		correoFijo = 0
		correo = ""

	}
}

//Audit
grails{
	plugin{
		audittrail{
			createdBy.field = "creadoPor"
			createdBy.type   = "java.lang.String"
			editedBy.field = "editadoPor"
			editedBy.type   = "java.lang.String"
			createdDate.field = "creadoEl"
			editedDate.field = "editadoEl"

			currentUserClosure = {ctx->
				//ctx is the applicationContext
				try{
					if(org.apache.shiro.SecurityUtils.subject?.authenticated){
						return org.apache.shiro.SecurityUtils.subject?.principal?.toString() + "@"+
						org.apache.shiro.SecurityUtils.subject?.session.host
					}else{
						return "no autenticado"
					}
				}catch (org.apache.shiro.UnavailableSecurityManagerException usme){
					return "anonimo"
				}
			}

		}
	}
}

// Reportes Pentaho - Ambiente Desarrollo
environments {
	development {
		grails {
			'report-generator' {
				pentaho {
					/*protocol	= 'http'
					 host		= '172.16.6.27'
					 port		= '8080'
					 path		= '/pentaho/content/reporting/execute/Adexus/SPM/'
					 pathParam	= '/SPM'
					 solution	= 'Adexus'
					 userid		= 'adminbi'
					 password	= '123isl'
					 locale		= 'es_CL'*/

					//no hay cuenta válida en desarrollo
					protocol    = 'http'
					host        = '172.16.6.58'
					port        = '8080'
					path        = '/pentaho/content/reporting/execute/Adexus/SPMQA/'
					pathParam   = '/SPMQA'
					solution    = 'Adexus'
					userid      = 'adminbi'
					password    = '123isl'
					locale      = 'es_CL'

				}
			}
		}
	}
	desarrollo_isl {
		grails {
			'report-generator' {
				pentaho {
					/*protocol	= 'http'
					 host		= '172.16.6.27'
					 port		= '8080'
					 path		= '/pentaho/content/reporting/execute/Adexus/SPM/'
					 pathParam	= '/SPM'
					 solution	= 'Adexus'
					 userid		= 'adminbi'
					 password	= '123isl'
					 locale		= 'es_CL'*/

					//no hay cuenta válida en desarrollo
					protocol    = 'http'
					host        = '172.16.6.58'
					port        = '8080'
					path        = '/pentaho/content/reporting/execute/Adexus/SPMQA/'
					pathParam   = '/SPMQA'
					solution    = 'Adexus'
					userid      = 'adminbi'
					password    = '123isl'
					locale      = 'es_CL'

				}
			}
		}
	}
	test {
		grails {
			'report-generator' {
				pentaho {
					protocol    = 'http'
					host        = '172.16.6.58'
					port        = '8080'
					path        = '/pentaho/content/reporting/execute/Adexus/SPMQA/'
					pathParam   = '/SPMQA'
					solution    = 'Adexus'
					userid      = 'adminbi'
					password    = '123isl'
					locale      = 'es_CL'
				}
			}
		}
	}
	production {
		grails {
			'report-generator' {
				pentaho {
					protocol	= 'http'
					host		= '172.16.6.58'
					port		= '8080'
					path		= '/pentaho/content/reporting/execute/Adexus/SPM/'
					pathParam	= '/SPM'
					solution	= 'Adexus'
					userid		= 'adminbi'
					password	= '123isl'
					locale		= 'es_CL'
				}
			}
		}
	}
}