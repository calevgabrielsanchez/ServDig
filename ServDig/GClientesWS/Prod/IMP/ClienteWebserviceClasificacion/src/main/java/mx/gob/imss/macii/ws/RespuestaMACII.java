
package mx.gob.imss.macii.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaMACII complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaMACII">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.macii.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="infoMACIIVO" type="{http://ws.macii.imss.gob.mx/}infoMACIIVO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaMACII", propOrder = {
    "infoMACIIVO"
})
public class RespuestaMACII
    extends RespuestaWS
{

    @XmlElement(nillable = true)
    protected List<InfoMACIIVO> infoMACIIVO;

    /**
     * Gets the value of the infoMACIIVO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoMACIIVO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoMACIIVO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoMACIIVO }
     * 
     * 
     */
    public List<InfoMACIIVO> getInfoMACIIVO() {
        if (infoMACIIVO == null) {
            infoMACIIVO = new ArrayList<InfoMACIIVO>();
        }
        return this.infoMACIIVO;
    }

}
