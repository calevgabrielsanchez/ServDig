
package mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaPatronesVigentes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaPatronesVigentes">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.patronesvigentes.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="patronesActivos" type="{http://ws.patronesvigentes.imss.gob.mx/}PatronVigenteVO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaPatronesVigentes", propOrder = {
    "patronesActivos"
})
public class RespuestaPatronesVigentes
    extends RespuestaWS
{

    @XmlElement(nillable = true)
    protected List<PatronVigenteVO> patronesActivos;

    /**
     * Gets the value of the patronesActivos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the patronesActivos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPatronesActivos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PatronVigenteVO }
     * 
     * 
     */
    public List<PatronVigenteVO> getPatronesActivos() {
        if (patronesActivos == null) {
            patronesActivos = new ArrayList<PatronVigenteVO>();
        }
        return this.patronesActivos;
    }

}
