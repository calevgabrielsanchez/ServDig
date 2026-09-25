
package mx.gob.imss.service.pagoscfdi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="response">
 *   &lt;complexContent>
 *     &lt;extension base="{http://pagoscfdi.service.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="listaInfoPagosCFDIRegPatronVO" type="{http://pagoscfdi.service.imss.gob.mx/}ListaPagosCFDIRegitroPatronal" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response", propOrder = {"listaInfoPagosCFDIRegPatronVO"})
public class Response
    extends RespuestaWS{

	@XmlElement(name = "listaInfoPagosCFDIRegPatronVO")
    protected ListaPagosCFDIRegitroPatronal listaInfoPagosCFDIRegPatronVO;


    public ListaPagosCFDIRegitroPatronal getListaInfoPagosCFDIRegPatronVO() {
        return listaInfoPagosCFDIRegPatronVO;
    }


    public void setListaInfoPagosCFDIRegPatronVO(ListaPagosCFDIRegitroPatronal value) {
        this.listaInfoPagosCFDIRegPatronVO = value;
    }

}
