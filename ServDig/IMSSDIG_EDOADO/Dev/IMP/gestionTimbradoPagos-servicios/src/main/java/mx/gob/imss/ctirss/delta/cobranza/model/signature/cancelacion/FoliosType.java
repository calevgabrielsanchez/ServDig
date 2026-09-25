package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(namespace="http://cancelacfd.sat.gob.mx", propOrder = {
@XmlType(name="FoliosType", propOrder = {
    "uuid",
    "estatusUUID"
})

public class FoliosType {

    @XmlElement(name = "UUID", required = true)
    protected String uuid;
    @XmlElement(name = "EstatusUUID", required = false)
    protected String estatusUUID;

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUUID(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the estatusUUID property.
     * 
     */
    public String getEstatusUUID() {
        return estatusUUID;
    }

    /**
     * Sets the value of the estatusUUID property.
     * 
     */
    public void setEstatusUUID(String value) {
        this.estatusUUID = value;
    }

}
