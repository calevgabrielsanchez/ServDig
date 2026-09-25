package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="X509IssuerSerialType", propOrder = {
    "x509IssuerName",
    "x509SerialNumber"
})

public class X509IssuerSerialType {
    
    @XmlElement(name = "X509IssuerName", required = true)
    protected String x509IssuerName;
    @XmlElement(name = "X509SerialNumber", required = false)
    protected String x509SerialNumber;

    public String getX509IssuerName() {
        return x509IssuerName;
    }

    public void setX509IssuerName(String x509IssuerName) {
        this.x509IssuerName = x509IssuerName;
    }

    public String getX509SerialNumber() {
        return x509SerialNumber;
    }

    public void setX509SerialNumber(String x509SerialNumber) {
        this.x509SerialNumber = x509SerialNumber;
    }    
}
