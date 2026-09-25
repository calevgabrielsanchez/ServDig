package mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat;

import java.io.Serializable;


public class Roles implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1341761260848411715L;
	protected String cRol;
    protected String dRol;
    protected String dTipo;
    protected String fAltaRol;
    protected String fBajaRol;

    /**
     * Gets the value of the cRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRol() {
        return cRol;
    }

    /**
     * Sets the value of the cRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRol(String value) {
        this.cRol = value;
    }

    /**
     * Gets the value of the dRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDRol() {
        return dRol;
    }

    /**
     * Sets the value of the dRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDRol(String value) {
        this.dRol = value;
    }

    /**
     * Gets the value of the dTipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDTipo() {
        return dTipo;
    }

    /**
     * Sets the value of the dTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDTipo(String value) {
        this.dTipo = value;
    }

    /**
     * Gets the value of the fAltaRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAltaRol() {
        return fAltaRol;
    }

    /**
     * Sets the value of the fAltaRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAltaRol(String value) {
        this.fAltaRol = value;
    }

    /**
     * Gets the value of the fBajaRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFBajaRol() {
        return fBajaRol;
    }

    /**
     * Sets the value of the fBajaRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFBajaRol(String value) {
        this.fBajaRol = value;
    }

}
