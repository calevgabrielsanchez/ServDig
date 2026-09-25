
package mx.gob.imss.ws.estatusvigencia.individual.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSituacionAseguramientoXAsginacionNSSResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSituacionAseguramientoXAsginacionNSSResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://situacionAseguramiento.imss.gob.mx/}respuestaSituacionAseguramiento" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSituacionAseguramientoXAsginacionNSSResponse", propOrder = {
    "_return"
})
public class GetSituacionAseguramientoXAsginacionNSSResponse {

    @XmlElement(name = "return")
    protected RespuestaSituacionAseguramiento _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaSituacionAseguramiento }
     *     
     */
    public RespuestaSituacionAseguramiento getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaSituacionAseguramiento }
     *     
     */
    public void setReturn(RespuestaSituacionAseguramiento value) {
        this._return = value;
    }

}
