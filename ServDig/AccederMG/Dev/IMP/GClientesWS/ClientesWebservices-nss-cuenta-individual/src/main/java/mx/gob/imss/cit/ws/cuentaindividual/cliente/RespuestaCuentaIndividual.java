
package mx.gob.imss.cit.ws.cuentaindividual.cliente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaCuentaIndividual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaCuentaIndividual">
 *   &lt;complexContent>
 *     &lt;extension base="{http://cuentaIndividual.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="cuentaIndividual" type="{http://cuentaIndividual.imss.gob.mx/}CuentaIndividualVo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaCuentaIndividual", propOrder = {
    "cuentaIndividual"
})
public class RespuestaCuentaIndividual
    extends RespuestaWS
{

    @XmlElement(nillable = true)
    protected List<CuentaIndividualVo> cuentaIndividual;

    /**
     * Gets the value of the cuentaIndividual property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cuentaIndividual property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCuentaIndividual().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CuentaIndividualVo }
     * 
     * 
     */
    public List<CuentaIndividualVo> getCuentaIndividual() {
        if (cuentaIndividual == null) {
            cuentaIndividual = new ArrayList<CuentaIndividualVo>();
        }
        return this.cuentaIndividual;
    }

}
