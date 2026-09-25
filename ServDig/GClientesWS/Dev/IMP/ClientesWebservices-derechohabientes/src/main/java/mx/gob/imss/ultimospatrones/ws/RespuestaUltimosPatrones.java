
package mx.gob.imss.ultimospatrones.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaUltimosPatrones complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaUltimosPatrones">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.ultimospatrones.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="ultimosPatronesVO" type="{http://ws.ultimospatrones.imss.gob.mx/}UltimosPatronesVO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaUltimosPatrones", propOrder = {
    "ultimosPatronesVO"
})
public class RespuestaUltimosPatrones
    extends RespuestaWS
{

    @XmlElement(nillable = true)
    protected List<UltimosPatronesVO> ultimosPatronesVO;

    /**
     * Gets the value of the ultimosPatronesVO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ultimosPatronesVO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUltimosPatronesVO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UltimosPatronesVO }
     * 
     * 
     */
    public List<UltimosPatronesVO> getUltimosPatronesVO() {
        if (ultimosPatronesVO == null) {
            ultimosPatronesVO = new ArrayList<UltimosPatronesVO>();
        }
        return this.ultimosPatronesVO;
    }

}
