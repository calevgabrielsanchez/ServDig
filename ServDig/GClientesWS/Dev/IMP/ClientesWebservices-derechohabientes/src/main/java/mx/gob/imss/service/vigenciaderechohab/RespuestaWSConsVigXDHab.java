
package mx.gob.imss.service.vigenciaderechohab;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para respuestaWSConsVigXDHab complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="respuestaWSConsVigXDHab">
 *   &lt;complexContent>
 *     &lt;extension base="{http://vigenciaderechohab.service.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="infoConsVigXDHab" type="{http://vigenciaderechohab.service.imss.gob.mx/}InfoConsVigXDHab" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaWSConsVigXDHab", propOrder = {
    "infoConsVigXDHab"
})
public class RespuestaWSConsVigXDHab
    extends RespuestaWS
{

    protected InfoConsVigXDHab infoConsVigXDHab;

    /**
     * Obtiene el valor de la propiedad infoConsVigXDHab.
     * 
     * @return
     *     possible object is
     *     {@link InfoConsVigXDHab }
     *     
     */
    public InfoConsVigXDHab getInfoConsVigXDHab() {
        return infoConsVigXDHab;
    }

    /**
     * Define el valor de la propiedad infoConsVigXDHab.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoConsVigXDHab }
     *     
     */
    public void setInfoConsVigXDHab(InfoConsVigXDHab value) {
        this.infoConsVigXDHab = value;
    }

}
