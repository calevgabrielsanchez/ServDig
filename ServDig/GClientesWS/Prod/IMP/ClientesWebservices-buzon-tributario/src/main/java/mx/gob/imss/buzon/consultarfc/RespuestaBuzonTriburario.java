
package mx.gob.imss.buzon.consultarfc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaBuzonTriburario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaBuzonTriburario">
 *   &lt;complexContent>
 *     &lt;extension base="{http://consultarfc.buzon.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="usuario" type="{http://consultarfc.buzon.imss.gob.mx/}UsuarioBuzonVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaBuzonTriburario", propOrder = {
    "usuario"
})
public class RespuestaBuzonTriburario
    extends RespuestaWS
{

    protected UsuarioBuzonVO usuario;

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link UsuarioBuzonVO }
     *     
     */
    public UsuarioBuzonVO getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsuarioBuzonVO }
     *     
     */
    public void setUsuario(UsuarioBuzonVO value) {
        this.usuario = value;
    }

}
