
package mx.gob.imss.webservice.renapo.curp.cliente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responsablesDelegacionDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="responsablesDelegacionDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clave" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="responsables" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}responsableDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responsablesDelegacionDTO", propOrder = {
    "clave",
    "mensaje",
    "responsables"
})
public class ResponsablesDelegacionDTO {

    protected int clave;
    protected String mensaje;
    @XmlElement(nillable = true)
    protected List<ResponsableDTO> responsables;

    /**
     * Gets the value of the clave property.
     * 
     */
    public int getClave() {
        return clave;
    }

    /**
     * Sets the value of the clave property.
     * 
     */
    public void setClave(int value) {
        this.clave = value;
    }

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

    /**
     * Gets the value of the responsables property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responsables property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponsables().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResponsableDTO }
     * 
     * 
     */
    public List<ResponsableDTO> getResponsables() {
        if (responsables == null) {
            responsables = new ArrayList<ResponsableDTO>();
        }
        return this.responsables;
    }

}
