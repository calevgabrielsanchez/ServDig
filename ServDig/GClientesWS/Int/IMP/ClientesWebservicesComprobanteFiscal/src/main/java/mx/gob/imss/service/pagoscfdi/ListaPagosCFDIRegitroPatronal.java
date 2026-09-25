package mx.gob.imss.service.pagoscfdi;

import java.util.ArrayList;
import java.util.List;

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
 * &lt;complexType name="listaInfoPagosCFDIRegPatronVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infoPagosCFDIRegPatronVO" type="{http://pagoscfdi.service.imss.gob.mx/}PagosCFDIRegitroPatronal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listaInfoPagosCFDIRegPatronVO", propOrder = {"infoPagosCFDIRegPatronVO"})
public class ListaPagosCFDIRegitroPatronal {

	@XmlElement(name = "infoPagosCFDIRegPatronVO")
    protected List<PagosCFDIRegitroPatronal> infoPagosCFDIRegPatronVO;
    
    public List<PagosCFDIRegitroPatronal> getInfoPagosCFDIRegPatronVO() {
        if (infoPagosCFDIRegPatronVO == null) {
        	infoPagosCFDIRegPatronVO = new ArrayList<PagosCFDIRegitroPatronal>();
        }
        return this.infoPagosCFDIRegPatronVO;
    }
	
    public void setInfoPagosCFDIRegPatronVO(List<PagosCFDIRegitroPatronal> values) {
        this.infoPagosCFDIRegPatronVO = values;
    }
    
}
