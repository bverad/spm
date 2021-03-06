package cl.cognus.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.6.9
 * 2013-09-15T20:44:24.524-04:00
 * Generated source version: 2.6.9
 * 
 */
@WebServiceClient(name = "ConsultasAfiliadosService", 
                  wsdlLocation = "http://172.16.6.51:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl",
                  targetNamespace = "http://ws.cognus.cl/") 
public class ConsultasAfiliadosService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ws.cognus.cl/", "ConsultasAfiliadosService");
    public final static QName ConsultasAfiliadosPort = new QName("http://ws.cognus.cl/", "ConsultasAfiliadosPort");
    static {
        URL url = null;
        try {
            url = new URL("http://172.16.6.51:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ConsultasAfiliadosService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://172.16.6.51:8080/islws-consultaafiliados/services/consultas_afiliados?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ConsultasAfiliadosService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ConsultasAfiliadosService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConsultasAfiliadosService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns ConsultasAfiliados
     */
    @WebEndpoint(name = "ConsultasAfiliadosPort")
    public ConsultasAfiliados getConsultasAfiliadosPort() {
        return super.getPort(ConsultasAfiliadosPort, ConsultasAfiliados.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConsultasAfiliados
     */
    @WebEndpoint(name = "ConsultasAfiliadosPort")
    public ConsultasAfiliados getConsultasAfiliadosPort(WebServiceFeature... features) {
        return super.getPort(ConsultasAfiliadosPort, ConsultasAfiliados.class, features);
    }

}
