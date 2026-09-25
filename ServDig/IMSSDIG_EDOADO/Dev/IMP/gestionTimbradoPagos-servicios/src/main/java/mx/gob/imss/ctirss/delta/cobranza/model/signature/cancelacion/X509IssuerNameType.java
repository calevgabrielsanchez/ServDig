package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author NOVUTECK1
 */
@XmlType(name = "X509IssuerNameType")
public class X509IssuerNameType {
    
    @XmlElement(name = "X509IssuerName", required = true)
    protected String x509IssuerName;

    public String getX509IssuerName() {
        return x509IssuerName;
    }

    public void setX509IssuerName(String x509IssuerName) {
        this.x509IssuerName = x509IssuerName;
    }
    
    
}
