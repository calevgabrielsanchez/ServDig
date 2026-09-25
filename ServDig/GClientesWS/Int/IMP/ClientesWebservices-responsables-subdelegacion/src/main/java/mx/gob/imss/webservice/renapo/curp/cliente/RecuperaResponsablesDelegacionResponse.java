
package mx.gob.imss.webservice.renapo.curp.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recuperaResponsablesDelegacionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recuperaResponsablesDelegacionResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}responsablesDelegacionDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperaResponsablesDelegacionResponse", propOrder = {
    "_return"
})
public class RecuperaResponsablesDelegacionResponse {

    @XmlElement(name = "return")
    protected ResponsablesDelegacionDTO _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ResponsablesDelegacionDTO }
     *     
     */
    public ResponsablesDelegacionDTO getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponsablesDelegacionDTO }
     *     
     */
    public void setReturn(ResponsablesDelegacionDTO value) {
        this._return = value;
    }

}
