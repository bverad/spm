
package cl.cognus.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cl.cognus.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConsultaEmpresa_QNAME = new QName("http://ws.cognus.cl/", "consultaEmpresa");
    private final static QName _ConsultaTrabajadorResponse_QNAME = new QName("http://ws.cognus.cl/", "consultaTrabajadorResponse");
    private final static QName _ConsultaEmpresaResponse_QNAME = new QName("http://ws.cognus.cl/", "consultaEmpresaResponse");
    private final static QName _ConsultaTrabajador_QNAME = new QName("http://ws.cognus.cl/", "consultaTrabajador");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cl.cognus.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultaEmpresa }
     * 
     */
    public ConsultaEmpresa createConsultaEmpresa() {
        return new ConsultaEmpresa();
    }

    /**
     * Create an instance of {@link ConsultaEmpresaResponse }
     * 
     */
    public ConsultaEmpresaResponse createConsultaEmpresaResponse() {
        return new ConsultaEmpresaResponse();
    }

    /**
     * Create an instance of {@link ConsultaTrabajadorResponse }
     * 
     */
    public ConsultaTrabajadorResponse createConsultaTrabajadorResponse() {
        return new ConsultaTrabajadorResponse();
    }

    /**
     * Create an instance of {@link ConsultaTrabajador }
     * 
     */
    public ConsultaTrabajador createConsultaTrabajador() {
        return new ConsultaTrabajador();
    }

    /**
     * Create an instance of {@link IntegerEmpresaMap }
     * 
     */
    public IntegerEmpresaMap createIntegerEmpresaMap() {
        return new IntegerEmpresaMap();
    }

    /**
     * Create an instance of {@link Empresa }
     * 
     */
    public Empresa createEmpresa() {
        return new Empresa();
    }

    /**
     * Create an instance of {@link EmpresaRegistrada }
     * 
     */
    public EmpresaRegistrada createEmpresaRegistrada() {
        return new EmpresaRegistrada();
    }

    /**
     * Create an instance of {@link EmpresaResponse }
     * 
     */
    public EmpresaResponse createEmpresaResponse() {
        return new EmpresaResponse();
    }

    /**
     * Create an instance of {@link TrabajadoresResponse }
     * 
     */
    public TrabajadoresResponse createTrabajadoresResponse() {
        return new TrabajadoresResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaEmpresa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.cognus.cl/", name = "consultaEmpresa")
    public JAXBElement<ConsultaEmpresa> createConsultaEmpresa(ConsultaEmpresa value) {
        return new JAXBElement<ConsultaEmpresa>(_ConsultaEmpresa_QNAME, ConsultaEmpresa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaTrabajadorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.cognus.cl/", name = "consultaTrabajadorResponse")
    public JAXBElement<ConsultaTrabajadorResponse> createConsultaTrabajadorResponse(ConsultaTrabajadorResponse value) {
        return new JAXBElement<ConsultaTrabajadorResponse>(_ConsultaTrabajadorResponse_QNAME, ConsultaTrabajadorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaEmpresaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.cognus.cl/", name = "consultaEmpresaResponse")
    public JAXBElement<ConsultaEmpresaResponse> createConsultaEmpresaResponse(ConsultaEmpresaResponse value) {
        return new JAXBElement<ConsultaEmpresaResponse>(_ConsultaEmpresaResponse_QNAME, ConsultaEmpresaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaTrabajador }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.cognus.cl/", name = "consultaTrabajador")
    public JAXBElement<ConsultaTrabajador> createConsultaTrabajador(ConsultaTrabajador value) {
        return new JAXBElement<ConsultaTrabajador>(_ConsultaTrabajador_QNAME, ConsultaTrabajador.class, null, value);
    }

}
