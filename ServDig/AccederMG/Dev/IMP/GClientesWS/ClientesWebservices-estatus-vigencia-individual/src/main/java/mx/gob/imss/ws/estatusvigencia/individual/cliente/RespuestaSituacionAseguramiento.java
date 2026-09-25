
package mx.gob.imss.ws.estatusvigencia.individual.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaSituacionAseguramiento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaSituacionAseguramiento">
 *   &lt;complexContent>
 *     &lt;extension base="{http://situacionAseguramiento.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="resultado" type="{http://situacionAseguramiento.imss.gob.mx/}SituacionAseguramientoVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaSituacionAseguramiento", propOrder = {
    "resultado"
})
public class RespuestaSituacionAseguramiento
    extends RespuestaWS
{

    protected SituacionAseguramientoVO resultado;

    /**
     * Gets the value of the resultado property.
     * 
     * @return
     *     possible object is
     *     {@link SituacionAseguramientoVO }
     *     
     */
    public SituacionAseguramientoVO getResultado() {
        return resultado;
    }

    /**
     * Sets the value of the resultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link SituacionAseguramientoVO }
     *     
     */
    public void setResultado(SituacionAseguramientoVO value) {
        this.resultado = value;
    }

}
