
package mx.gob.imss.cit.ws.cuentaindividualhistorico.cliente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaHistoricoCuentaIndividual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaHistoricoCuentaIndividual">
 *   &lt;complexContent>
 *     &lt;extension base="{http://cuentaIndividual.sisec.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="histcuentaIndividual" type="{http://cuentaIndividual.sisec.imss.gob.mx/}HistoricoCuentaIndividualVo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaHistoricoCuentaIndividual", propOrder = {
    "histcuentaIndividual"
})
public class RespuestaHistoricoCuentaIndividual
    extends RespuestaWS
{

    @XmlElement(nillable = true)
    protected List<HistoricoCuentaIndividualVo> histcuentaIndividual;

    /**
     * Gets the value of the histcuentaIndividual property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the histcuentaIndividual property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistcuentaIndividual().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HistoricoCuentaIndividualVo }
     * 
     * 
     */
    public List<HistoricoCuentaIndividualVo> getHistcuentaIndividual() {
        if (histcuentaIndividual == null) {
            histcuentaIndividual = new ArrayList<HistoricoCuentaIndividualVo>();
        }
        return this.histcuentaIndividual;
    }

}
