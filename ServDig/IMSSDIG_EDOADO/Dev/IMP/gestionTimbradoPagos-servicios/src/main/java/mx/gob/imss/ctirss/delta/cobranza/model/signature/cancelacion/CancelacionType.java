package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED) 
@XmlType(name = "CancelacionType", propOrder = {    
    "folios",
    "signature"
})

@XmlRootElement(name = "Cancelacion")
public class CancelacionType {
    
    @XmlElement(name = "Folios", required = true)
    protected List<FoliosType> folios;
    
    @XmlElement(name = "Signature", required = true)
    protected SignatureType signature;

    @XmlAttribute(name ="xmlns")
    protected String nameSpacexmlns;       
    
    @XmlAttribute(name = "RfcEmisor")
    protected String rfcEmisor;
        
    @XmlAttribute(name = "Fecha", required = true)
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected Date fecha;
    
    @XmlAttribute(name = "xmlns:xsd")
    protected String nameSpaceXsd;
    
    @XmlAttribute(name = "xmlns:xsi" )
    protected String aNameXmlnsXsi;

    
    public String getNameSpaceXsd() {
        return nameSpaceXsd;
    }

    public void setNameSpaceXsd(String nameSpaceXsd) {
        this.nameSpaceXsd = nameSpaceXsd;
    }

    public List<FoliosType> getFolios() {
        if (folios == null) {
            folios = new ArrayList<FoliosType>();
        }
        return this.folios;
    }

    public void setFolios(List<FoliosType> folios) {
        this.folios = folios;
    }
    
    public SignatureType getSignature() {
        return signature;
    }

    public void setSignature(SignatureType value) {
        this.signature = value;
    }

    public String getRfcEmisor() {
        return rfcEmisor;
    }

    public void setRfcEmisor(String value) {
        this.rfcEmisor = value;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    public String getNameSpacexmlns() {
        return nameSpacexmlns;
    }

    public void setNameSpacexmlns(String nameSpacexmlns) {
        this.nameSpacexmlns = nameSpacexmlns;
    }

    public String getaNameXmlnsXsi() {
        return aNameXmlnsXsi;
    }

    public void setaNameXmlnsXsi(String aNameXmlnsXsi) {
        this.aNameXmlnsXsi = aNameXmlnsXsi;
    }
}
