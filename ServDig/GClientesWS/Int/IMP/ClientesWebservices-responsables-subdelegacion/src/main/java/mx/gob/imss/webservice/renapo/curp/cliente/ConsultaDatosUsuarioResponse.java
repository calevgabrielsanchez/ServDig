
package mx.gob.imss.webservice.renapo.curp.cliente;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * <p>Java class for consultaDatosUsuarioResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaDatosUsuarioResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}usuarioInfoDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "consultaDatosUsuarioResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaDatosUsuarioResponse", propOrder = {
    "_return"
})
public class ConsultaDatosUsuarioResponse {

    @XmlElement(name = "return")
    protected UsuarioInfoDTO _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link UsuarioInfoDTO }
     *     
     */
	
    public UsuarioInfoDTO getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsuarioInfoDTO }
     *     
     */
    public void setReturn(UsuarioInfoDTO value) {
        this._return = value;
    }

}
