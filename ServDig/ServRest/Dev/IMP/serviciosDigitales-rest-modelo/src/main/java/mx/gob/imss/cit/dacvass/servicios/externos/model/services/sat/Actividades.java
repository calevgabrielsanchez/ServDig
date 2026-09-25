package mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Actividades implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1151639269580375405L;
	protected String orden;
    protected String porcentaje;
    protected String cActividad;
    protected String dActividad;
    protected String fAltaAct;
    protected String fBajaAct;

    /**
     * Gets the value of the orden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrden() {
        return orden;
    }

    /**
     * Sets the value of the orden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrden(String value) {
        this.orden = value;
    }

    /**
     * Gets the value of the porcentaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPorcentaje() {
        return porcentaje;
    }

    /**
     * Sets the value of the porcentaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPorcentaje(String value) {
        this.porcentaje = value;
    }

    /**
     * Gets the value of the cActividad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCActividad() {
        return cActividad;
    }

    /**
     * Sets the value of the cActividad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCActividad(String value) {
        this.cActividad = value;
    }

    /**
     * Gets the value of the dActividad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDActividad() {
        return dActividad;
    }

    /**
     * Sets the value of the dActividad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDActividad(String value) {
        this.dActividad = value;
    }

    /**
     * Gets the value of the fAltaAct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAltaAct() {
        return fAltaAct;
    }

    /**
     * Sets the value of the fAltaAct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAltaAct(String value) {
        this.fAltaAct = value;
    }

    /**
     * Gets the value of the fBajaAct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFBajaAct() {
        return fBajaAct;
    }

    /**
     * Sets the value of the fBajaAct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFBajaAct(String value) {
        this.fBajaAct = value;
    }

}
