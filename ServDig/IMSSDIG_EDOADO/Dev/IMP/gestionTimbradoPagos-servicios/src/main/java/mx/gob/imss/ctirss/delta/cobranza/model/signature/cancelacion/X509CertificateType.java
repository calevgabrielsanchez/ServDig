package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "X509CertificateType")
public class X509CertificateType {
    
    @XmlElement(name = "X509Certificate", required = true)
    protected String x509CertificateValue;

    public String getX509CertificateValue() {
        return x509CertificateValue;
    }

    public void setX509CertificateValue(String x509CertificateValue) {
        this.x509CertificateValue = x509CertificateValue;
    }
    
    
}
