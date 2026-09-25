package mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model.InfoPatronEntradaxRFC;


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
 *         &lt;element name="infoEntrada" type="{java:vo}InfoPatronEntradaxRFC"/>
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
    "infoEntrada"
})
@XmlRootElement(name = "getInformacionPatronxRFC")
public class GetInformacionPatronxRFC {

    @XmlElement(required = true)
    protected InfoPatronEntradaxRFC infoEntrada;

    /**
     * Gets the value of the infoEntrada property.
     * 
     * @return
     *     possible object is
     *     {@link InfoPatronEntradaxRFC }
     *     
     */
    public InfoPatronEntradaxRFC getInfoEntrada() {
        return infoEntrada;
    }

    /**
     * Sets the value of the infoEntrada property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoPatronEntradaxRFC }
     *     
     */
    public void setInfoEntrada(InfoPatronEntradaxRFC value) {
        this.infoEntrada = value;
    }

}
