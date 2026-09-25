
package mx.gob.imss.cit.dpes.core.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SolicitudRequest" type="{java:mx.gob.imss.cit.dpes.core.modelo.osb}SolicitudRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "solicitudRequest"
})
@XmlRootElement(name = "crearSolicitud", namespace = "http://www.openuri.org/")
public class CrearSolicitud {

    @XmlElement(name = "SolicitudRequest", namespace = "http://www.openuri.org/", required = true)
    protected SolicitudRequest solicitudRequest;

    /**
     * Gets the value of the solicitudRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudRequest }
     *     
     */
    public SolicitudRequest getSolicitudRequest() {
        return solicitudRequest;
    }

    /**
     * Sets the value of the solicitudRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudRequest }
     *     
     */
    public void setSolicitudRequest(SolicitudRequest value) {
        this.solicitudRequest = value;
    }

}
