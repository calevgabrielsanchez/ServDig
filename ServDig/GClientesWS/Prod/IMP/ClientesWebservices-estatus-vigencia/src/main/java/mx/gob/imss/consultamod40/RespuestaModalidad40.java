
package mx.gob.imss.consultamod40;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaModalidad40 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaModalidad40">
 *   &lt;complexContent>
 *     &lt;extension base="{http://consultaMod40.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="modalidad40" type="{http://consultaMod40.imss.gob.mx/}Modalidad40VO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaModalidad40", propOrder = {
    "modalidad40"
})
public class RespuestaModalidad40
    extends RespuestaWS
{

    protected Modalidad40VO modalidad40;

    /**
     * Gets the value of the modalidad40 property.
     * 
     * @return
     *     possible object is
     *     {@link Modalidad40VO }
     *     
     */
    public Modalidad40VO getModalidad40() {
        return modalidad40;
    }

    /**
     * Sets the value of the modalidad40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Modalidad40VO }
     *     
     */
    public void setModalidad40(Modalidad40VO value) {
        this.modalidad40 = value;
    }

}
