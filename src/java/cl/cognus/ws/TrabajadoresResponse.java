
package cl.cognus.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para trabajadoresResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="trabajadoresResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="empresas" type="{http://ws.cognus.cl/}IntegerEmpresaMap" minOccurs="0"/>
 *         &lt;element name="respCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="respMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trabajadoresResponse", propOrder = {
    "empresas",
    "respCode",
    "respMsg"
})
public class TrabajadoresResponse {

    protected IntegerEmpresaMap empresas;
    protected Integer respCode;
    protected String respMsg;

    /**
     * Obtiene el valor de la propiedad empresas.
     * 
     * @return
     *     possible object is
     *     {@link IntegerEmpresaMap }
     *     
     */
    public IntegerEmpresaMap getEmpresas() {
        return empresas;
    }

    /**
     * Define el valor de la propiedad empresas.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegerEmpresaMap }
     *     
     */
    public void setEmpresas(IntegerEmpresaMap value) {
        this.empresas = value;
    }

    /**
     * Obtiene el valor de la propiedad respCode.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRespCode() {
        return respCode;
    }

    /**
     * Define el valor de la propiedad respCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRespCode(Integer value) {
        this.respCode = value;
    }

    /**
     * Obtiene el valor de la propiedad respMsg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespMsg() {
        return respMsg;
    }

    /**
     * Define el valor de la propiedad respMsg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespMsg(String value) {
        this.respMsg = value;
    }

}
