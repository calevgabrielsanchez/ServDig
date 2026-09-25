
package mx.gob.imss.ctirss.ws.asignacion.externo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para getInfoByNssResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="getInfoByNssResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responseInfo" type="{http://soap.resource.ado.imss.gob.mx/}responseNssPasoacambioResource" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInfoByNssResponse", propOrder = {
    "responseInfo"
})
public class GetInfoByNssResponse {

    protected ResponseNssPasoacambioResource responseInfo;

    /**
     * Obtiene el valor de la propiedad responseInfo.
     * 
     * @return
     *     possible object is
     *     {@link ResponseNssPasoacambioResource }
     *     
     */
    public ResponseNssPasoacambioResource getResponseInfo() {
        return responseInfo;
    }

    /**
     * Define el valor de la propiedad responseInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseNssPasoacambioResource }
     *     
     */
    public void setResponseInfo(ResponseNssPasoacambioResource value) {
        this.responseInfo = value;
    }

}
