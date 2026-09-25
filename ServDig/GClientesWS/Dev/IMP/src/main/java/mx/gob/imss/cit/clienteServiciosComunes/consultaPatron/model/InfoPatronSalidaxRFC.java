package mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoPatronSalidaxRFC complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoPatronSalidaxRFC">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="infoPatronVOxRFC" type="{java:vo}InfoPatronVOxRFC" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoPatronSalidaxRFC", propOrder = {
    "codigo",
    "descripcion",
    "exito",
    "infoPatronVOxRFC"
})
public class InfoPatronSalidaxRFC {

    protected int codigo;
    @XmlElement(required = true, nillable = true)
    protected String descripcion;
    protected int exito;
    @XmlElement(nillable = true)
    protected List<InfoPatronVOxRFC> infoPatronVOxRFC;

    /**
     * Gets the value of the codigo property.
     * 
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     */
    public void setCodigo(int value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the exito property.
     * 
     */
    public int getExito() {
        return exito;
    }

    /**
     * Sets the value of the exito property.
     * 
     */
    public void setExito(int value) {
        this.exito = value;
    }

    /**
     * Gets the value of the infoPatronVOxRFC property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoPatronVOxRFC property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoPatronVOxRFC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoPatronVOxRFC }
     * 
     * 
     */
    public List<InfoPatronVOxRFC> getInfoPatronVOxRFC() {
        if (infoPatronVOxRFC == null) {
            infoPatronVOxRFC = new ArrayList<InfoPatronVOxRFC>();
        }
        return this.infoPatronVOxRFC;
    }

}
