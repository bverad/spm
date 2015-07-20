
package cl.cognus.ws;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.9
 * 2013-09-15T20:44:24.438-04:00
 * Generated source version: 2.6.9
 * 
 */
public final class ConsultasAfiliados_ConsultasAfiliadosPort_Client {

    private static final QName SERVICE_NAME = new QName("http://ws.cognus.cl/", "ConsultasAfiliadosService");

    private ConsultasAfiliados_ConsultasAfiliadosPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ConsultasAfiliadosService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        ConsultasAfiliadosService ss = new ConsultasAfiliadosService(wsdlURL, SERVICE_NAME);
        ConsultasAfiliados port = ss.getConsultasAfiliadosPort();  
        
        {
        System.out.println("Invoking consultaTrabajador...");
        java.lang.String _consultaTrabajador_rut = "";
        int _consultaTrabajador_periodo = 0;
        cl.cognus.ws.TrabajadoresResponse _consultaTrabajador__return = port.consultaTrabajador(_consultaTrabajador_rut, _consultaTrabajador_periodo);
        System.out.println("consultaTrabajador.result=" + _consultaTrabajador__return);


        }
        {
        System.out.println("Invoking consultaEmpresa...");
        java.lang.String _consultaEmpresa_rut = "";
        int _consultaEmpresa_periodo = 0;
        cl.cognus.ws.EmpresaResponse _consultaEmpresa__return = port.consultaEmpresa(_consultaEmpresa_rut, _consultaEmpresa_periodo);
        System.out.println("consultaEmpresa.result=" + _consultaEmpresa__return);


        }

        System.exit(0);
    }

}