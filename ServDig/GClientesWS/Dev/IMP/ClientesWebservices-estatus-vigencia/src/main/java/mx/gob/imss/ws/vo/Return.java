
package mx.gob.imss.ws.vo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para return complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="return">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="claveError" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="mensajeError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultado" type="{http://vo.ws.imss.gob.mx/}resultado" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "return", propOrder = {
    "claveError",
    "mensajeError",
    "resultado"
})
public class Return {

    @XmlElementRef(name = "claveError", type = JAXBElement.class)
    protected JAXBElement<Integer> claveError;
    @XmlElementRef(name = "mensajeError", type = JAXBElement.class)
    protected JAXBElement<String> mensajeError;
    @XmlElementRef(name = "resultado", type = JAXBElement.class)
    protected JAXBElement<Resultado> resultado;

    /**
     * Obtiene el valor de la propiedad claveError.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getClaveError() {
        return claveError;
    }

    /**
     * Define el valor de la propiedad claveError.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setClaveError(JAXBElement<Integer> value) {
        this.claveError = value;
    }

    /**
     * Obtiene el valor de la propiedad mensajeError.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMensajeError() {
        return mensajeError;
    }

    /**
     * Define el valor de la propiedad mensajeError.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMensajeError(JAXBElement<String> value) {
        this.mensajeError = value;
    }

    /**
     * Obtiene el valor de la propiedad resultado.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Resultado }{@code >}
     *     
     */
    public JAXBElement<Resultado> getResultado() {
        return resultado;
    }

    /**
     * Define el valor de la propiedad resultado.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Resultado }{@code >}
     *     
     */
    public void setResultado(JAXBElement<Resultado> value) {
        this.resultado = value;
    }

}
