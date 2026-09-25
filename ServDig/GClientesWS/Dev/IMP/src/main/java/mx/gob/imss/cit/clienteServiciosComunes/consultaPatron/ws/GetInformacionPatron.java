package mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model.InfoPatronEntrada;


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
 *         &lt;element name="inputPatron" type="{java:vo}InfoPatronEntrada"/>
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
    "inputPatron"
})
@XmlRootElement(name = "getInformacionPatron")
public class GetInformacionPatron {

    @XmlElement(required = true)
    protected InfoPatronEntrada inputPatron;

    /**
     * Gets the value of the inputPatron property.
     * 
     * @return
     *     possible object is
     *     {@link InfoPatronEntrada }
     *     
     */
    public InfoPatronEntrada getInputPatron() {
        return inputPatron;
    }

    /**
     * Sets the value of the inputPatron property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoPatronEntrada }
     *     
     */
    public void setInputPatron(InfoPatronEntrada value) {
        this.inputPatron = value;
    }

}
