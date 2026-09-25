package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "X509DataType")
public class X509DataType {
    
    @XmlElement(name = "X509IssuerSerial", required = true)
    protected X509IssuerSerialType x509IssuerSerial;
    @XmlElement(name = "X509Certificate", required = true)
    protected String x509Certificate;
    
    public X509IssuerSerialType getX509IssuerSerial() {
        return x509IssuerSerial;
    }

    public void setX509IssuerSerial(X509IssuerSerialType x509IssuerSerial) {
        this.x509IssuerSerial = x509IssuerSerial;
    }

    public String getX509Certificate() {
        return x509Certificate;
    }

    public void setX509Certificate(String x509Certificate) {
        this.x509Certificate = x509Certificate;
    }
       
}
