package cl.cognus

import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.FileUtils


class ReportGeneratorService {

    def grailsApplication

    def generate(String filename, String reportName, Map params, ReportOutputType outputTarget, File out) {
        params['renderMode'] = 'REPORT'
        params['output-target'] = outputTarget.getTarget()


        def config = grailsApplication.config.grails.'report-generator'.pentaho
		
		log.info "config: ${config}"

        params['userid'] = config.userid
        params['password'] = config.password
        params['solution'] = config.solution
        params['path'] = config.pathParam
        params['locale'] = config.locale
        params['name'] = reportName

        List paramsString = []

        params.each { k, v ->
            paramsString += "${k}=${URLEncoder.encode(v, 'UTF-8')}"
        }

        String fileString = "${config.path}${filename}?${paramsString.join("&")}"

        URI uri = new URI("${config.protocol}://${config.host}:${config.port}${fileString}")
        log.info "uri path : ${uri.getPath()}"

        URLConnection uc = uri.toURL().openConnection();
        //String basicAuth = "Basic " + new String(new Base64().encode(("${config.userid}:${config.password}").getBytes()));
        //uc.setRequestProperty("Authorization", basicAuth);
        InputStream is = uc.getInputStream();
        FileUtils.copyInputStreamToFile(is, out);
        is.close();
    }
}
