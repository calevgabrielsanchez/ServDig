
package mx.gob.imss.cit.ws.cuentaindividualhistorico.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HistoricoCuentaIndividualVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HistoricoCuentaIndividualVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registroPatronal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claveModalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaInicioMovimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroConsecutivoPeriodos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="curp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaFinalMovimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="origenMovimientoInicial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="origenMovimientoFinal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoMovimientoIniintcial" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tipoMovimientoFinal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fechaRecepcionMovimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="salarioBase" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tipoSalario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="huelga" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claveCiz" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HistoricoCuentaIndividualVo", propOrder = {
    "nss",
    "registroPatronal",
    "claveModalidad",
    "fechaInicioMovimiento",
    "numeroConsecutivoPeriodos",
    "curp",
    "fechaFinalMovimiento",
    "origenMovimientoInicial",
    "origenMovimientoFinal",
    "tipoMovimientoIniintcial",
    "tipoMovimientoFinal",
    "fechaRecepcionMovimiento",
    "salarioBase",
    "tipoSalario",
    "huelga",
    "claveCiz"
})
public class HistoricoCuentaIndividualVo {

    protected String nss;
    protected String registroPatronal;
    protected String claveModalidad;
    protected String fechaInicioMovimiento;
    protected int numeroConsecutivoPeriodos;
    protected String curp;
    protected String fechaFinalMovimiento;
    protected String origenMovimientoInicial;
    protected String origenMovimientoFinal;
    protected int tipoMovimientoIniintcial;
    protected int tipoMovimientoFinal;
    protected String fechaRecepcionMovimiento;
    protected Double salarioBase;
    protected String tipoSalario;
    protected String huelga;
    protected int claveCiz;

    /**
     * Gets the value of the nss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNss() {
        return nss;
    }

    /**
     * Sets the value of the nss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNss(String value) {
        this.nss = value;
    }

    /**
     * Gets the value of the registroPatronal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistroPatronal() {
        return registroPatronal;
    }

    /**
     * Sets the value of the registroPatronal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistroPatronal(String value) {
        this.registroPatronal = value;
    }

    /**
     * Gets the value of the claveModalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveModalidad() {
        return claveModalidad;
    }

    /**
     * Sets the value of the claveModalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveModalidad(String value) {
        this.claveModalidad = value;
    }

    /**
     * Gets the value of the fechaInicioMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaInicioMovimiento() {
        return fechaInicioMovimiento;
    }

    /**
     * Sets the value of the fechaInicioMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaInicioMovimiento(String value) {
        this.fechaInicioMovimiento = value;
    }

    /**
     * Gets the value of the numeroConsecutivoPeriodos property.
     * 
     */
    public int getNumeroConsecutivoPeriodos() {
        return numeroConsecutivoPeriodos;
    }

    /**
     * Sets the value of the numeroConsecutivoPeriodos property.
     * 
     */
    public void setNumeroConsecutivoPeriodos(int value) {
        this.numeroConsecutivoPeriodos = value;
    }

    /**
     * Gets the value of the curp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurp() {
        return curp;
    }

    /**
     * Sets the value of the curp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurp(String value) {
        this.curp = value;
    }

    /**
     * Gets the value of the fechaFinalMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFinalMovimiento() {
        return fechaFinalMovimiento;
    }

    /**
     * Sets the value of the fechaFinalMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFinalMovimiento(String value) {
        this.fechaFinalMovimiento = value;
    }

    /**
     * Gets the value of the origenMovimientoInicial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigenMovimientoInicial() {
        return origenMovimientoInicial;
    }

    /**
     * Sets the value of the origenMovimientoInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigenMovimientoInicial(String value) {
        this.origenMovimientoInicial = value;
    }

    /**
     * Gets the value of the origenMovimientoFinal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigenMovimientoFinal() {
        return origenMovimientoFinal;
    }

    /**
     * Sets the value of the origenMovimientoFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigenMovimientoFinal(String value) {
        this.origenMovimientoFinal = value;
    }

    /**
     * Gets the value of the tipoMovimientoIniintcial property.
     * 
     */
    public int getTipoMovimientoIniintcial() {
        return tipoMovimientoIniintcial;
    }

    /**
     * Sets the value of the tipoMovimientoIniintcial property.
     * 
     */
    public void setTipoMovimientoIniintcial(int value) {
        this.tipoMovimientoIniintcial = value;
    }

    /**
     * Gets the value of the tipoMovimientoFinal property.
     * 
     */
    public int getTipoMovimientoFinal() {
        return tipoMovimientoFinal;
    }

    /**
     * Sets the value of the tipoMovimientoFinal property.
     * 
     */
    public void setTipoMovimientoFinal(int value) {
        this.tipoMovimientoFinal = value;
    }

    /**
     * Gets the value of the fechaRecepcionMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaRecepcionMovimiento() {
        return fechaRecepcionMovimiento;
    }

    /**
     * Sets the value of the fechaRecepcionMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaRecepcionMovimiento(String value) {
        this.fechaRecepcionMovimiento = value;
    }

    /**
     * Gets the value of the salarioBase property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSalarioBase() {
        return salarioBase;
    }

    /**
     * Sets the value of the salarioBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSalarioBase(Double value) {
        this.salarioBase = value;
    }

    /**
     * Gets the value of the tipoSalario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoSalario() {
        return tipoSalario;
    }

    /**
     * Sets the value of the tipoSalario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoSalario(String value) {
        this.tipoSalario = value;
    }

    /**
     * Gets the value of the huelga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHuelga() {
        return huelga;
    }

    /**
     * Sets the value of the huelga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHuelga(String value) {
        this.huelga = value;
    }

    /**
     * Gets the value of the claveCiz property.
     * 
     */
    public int getClaveCiz() {
        return claveCiz;
    }

    /**
     * Sets the value of the claveCiz property.
     * 
     */
    public void setClaveCiz(int value) {
        this.claveCiz = value;
    }

}
