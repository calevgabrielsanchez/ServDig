package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement(name="SignedInfo" )
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignedInfo", propOrder = {
    "canonicalizationMethod",
    "signatureMethod",
    "reference"
})
public class SignedInfoType {

    @XmlElement(name = "CanonicalizationMethod", required = true)
    protected CanonicalizationMethodType canonicalizationMethod;
    @XmlElement(name = "SignatureMethod", required = true)
    protected SignatureMethodType signatureMethod;
    @XmlElement(name = "Reference", required = true)
    protected ReferenceType reference;
    
    @XmlAttribute(name = "xmlns:xsd")
    protected String nameSpaceXsd;
    
    @XmlAttribute(name = "xmlns:xsi" )
    protected String aNameXmlnsXsi;
    
    
    @XmlAttribute(name = "xmlns" )
    protected String aNameXmlns;

    /**
     * Gets the value of the canonicalizationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link CanonicalizationMethodType }
     *     
     */
    public CanonicalizationMethodType getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    /**
     * Sets the value of the canonicalizationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CanonicalizationMethodType }
     *     
     */
    public void setCanonicalizationMethod(CanonicalizationMethodType value) {
        this.canonicalizationMethod = value;
    }

    /**
     * Gets the value of the signatureMethod property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureMethodType }
     *     
     */
    public SignatureMethodType getSignatureMethod() {
        return signatureMethod;
    }

    /**
     * Sets the value of the signatureMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureMethodType }
     *     
     */
    public void setSignatureMethod(SignatureMethodType value) {
        this.signatureMethod = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setReference(ReferenceType value) {
        this.reference = value;
    }

	/**
	 * @return the nameSpaceXsd
	 */
	public String getNameSpaceXsd() {
		return nameSpaceXsd;
	}

	/**
	 * @param nameSpaceXsd the nameSpaceXsd to set
	 */
	public void setNameSpaceXsd(String nameSpaceXsd) {
		this.nameSpaceXsd = nameSpaceXsd;
	}

	/**
	 * @return the aNameXmlnsXsi
	 */
	public String getaNameXmlnsXsi() {
		return aNameXmlnsXsi;
	}

	/**
	 * @param aNameXmlnsXsi the aNameXmlnsXsi to set
	 */
	public void setaNameXmlnsXsi(String aNameXmlnsXsi) {
		this.aNameXmlnsXsi = aNameXmlnsXsi;
	}

	/**
	 * @return the aNameXmlns
	 */
	public String getaNameXmlns() {
		return aNameXmlns;
	}

	/**
	 * @param aNameXmlns the aNameXmlns to set
	 */
	public void setaNameXmlns(String aNameXmlns) {
		this.aNameXmlns = aNameXmlns;
	}

}
