import grails.util.Environment

grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6



if (Environment.current == Environment.DEVELOPMENT)  {
	grails.project.war.file = "target/dev/${appName}-${appVersion}.war"
}else if (Environment.current == Environment.TEST){
	grails.project.war.file = "target/test/${appName}-${appVersion}.war"
}else if (Environment.current == Environment.PRODUCTION){
	grails.project.war.file = "target/prod/${appName}-${appVersion}.war"
}else if(Environment.current == "desarrollo_isl"){
	grails.project.war.file = "target/desarrollo_isl/${appName}-${appVersion}.war"
}else{
	grails.project.war.file = "target/test_isl/${appName}-${appVersion}.war"
}


// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

//Para deploy en jboss 5
grails.war.resources = { stagingDir ->
	for (name in ['log4j', 'slf4j', 'jcl-over-slf4j', 'jul-to-slf4j']) {
	   delete {
		  fileset dir: "$stagingDir/WEB-INF/lib/",
				  includes: "$name*.jar"
	   }
	}
 }


def opencmisVersion = '0.9.0'

//Reportes Pentaho (en custom-plugins)
grails.plugin.location.'grails-report-generator' = "./custom-plugins/grails-report-generator-1.8"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
		
		//para ":jaxrs:0.8"
		mavenRepo "http://maven.restlet.org/"
		
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.


        //runtime 'mysql:mysql-connector-java:5.1.22'
		runtime 'itextpdf:itextpdf:5.4.3' //itextpdf-5.4.3

		
		// Para Usar OpenCMIS
		compile ("org.apache.chemistry.opencmis:chemistry-opencmis-commons-api:${opencmisVersion}",
				 "org.apache.chemistry.opencmis:chemistry-opencmis-client-api:${opencmisVersion}",
				 "org.apache.chemistry.opencmis:chemistry-opencmis-client-impl:${opencmisVersion}") {
			excludes 'jaxws-rt'
		}

		// Para Usar OpenCMIS
		runtime ("org.apache.chemistry.opencmis:chemistry-opencmis-commons-api:${opencmisVersion}",
				 "org.apache.chemistry.opencmis:chemistry-opencmis-commons-impl:${opencmisVersion}",
				 "org.apache.chemistry.opencmis:chemistry-opencmis-client-api:${opencmisVersion}",
				 "org.apache.chemistry.opencmis:chemistry-opencmis-client-bindings:${opencmisVersion}",
				 "org.apache.chemistry.opencmis:chemistry-opencmis-client-impl:${opencmisVersion}") {
			excludes 'jaxws-rt'
		}
				 
		//WS SUSESO (diego culpable)
		compile('wslite:groovy-wslite-master:1.0.0-SNAPSHOT')
		runtime('wslite:groovy-wslite-master:1.0.0-SNAPSHOT')
		
		//Para poder usar el JBPM
		compile('org.apache.httpcomponents:httpclient:4.2.2',
				'org.apache.httpcomponents:httpmime:4.2.2',
				'org.codehaus.jackson:jackson-mapper-asl:1.9.11',
				'com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.1.1',
				'RSJBMClient:RSJBMClient:0.1')
		runtime('org.apache.httpcomponents:httpclient:4.2.2',
				'org.apache.httpcomponents:httpmime:4.2.2',
				'org.codehaus.jackson:jackson-mapper-asl:1.9.11',
				'com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.1.1',
				'RSJBMClient:RSJBMClient:0.1')
		
		
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        //runtime ":jquery:1.8.3"
        runtime ":resources:1.2"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.5"

        build ":tomcat:$grailsVersion"
		build ':jbossas:1.0'  //Para produccion
		
		//Alega que le falta el dir migrations (moya pa que sirve)
        runtime ":database-migration:1.3.2" 

        compile ':cache:1.0.1'
		compile ":richui:0.8"
		
		compile ':constraints:0.8.0'
		
		compile ":jaxrs:0.8"
		
		//Audit
		compile ":audit-trail:2.0.1"
		
		//Veamos si esto funciona
		compile ":cxf-client:1.5.6"
		compile ":executor:0.3"
		compile ":fixtures:1.2"
		compile ":ic-alendar:0.3.8"
		compile ":mail:1.0.1"
		compile ":shiro:1.1.4"
		
    }
}

grails.server.port.http = 8091 //Para poder usarlo junto con el jbpm
