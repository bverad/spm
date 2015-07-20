import groovy.xml.StreamingMarkupBuilder

/**
 * TODO JBOSS - Remove log4j configuration stuff (when running with JBoss or GlassFish a.s.o)
 */
eventWebXmlEnd = {String tmpfile ->

    def root = new XmlSlurper().parse(webXmlFile)

    // When running with JBoss (or GlassFish a.s.o) remove log4j configuration stuff
    def log4j = root.listener.findAll {node ->
        node.'listener-class'.text() == 'org.codehaus.groovy.grails.web.util.Log4jConfigListener'
    }
    log4j.replaceNode {}

    def log4jFile = root.'context-param'.findAll {node ->
        node.'param-name'.text() == 'log4jConfigLocation'
    }
    log4jFile.replaceNode {}

    webXmlFile.text = new StreamingMarkupBuilder().bind {
        mkp.declareNamespace("": "http://java.sun.com/xml/ns/j2ee")
        mkp.yield(root)
    }
}