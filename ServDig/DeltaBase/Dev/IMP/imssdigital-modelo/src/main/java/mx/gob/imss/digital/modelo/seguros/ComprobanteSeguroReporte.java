/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comprobanteSeguroReporte", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "comprobanteSeguroReporte", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class ComprobanteSeguroReporte implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String nss;
    private String curp;
    private String fecNacimiento;
    private Integer edad;
    private String sexo;
    private String domicilio;
    private String modalidad;
    private String nrp;
    private String delegacion;
    private String subdelegacion;
    private String tipoTramite;
    private String periodo;
    private BigDecimal cuota;
    private String formaPago;
    private Boolean cuestionario;
    private Integer origen;
    private String usuario;
    private String subdelegacionUsuario;
    private String rfcFirma;
    private String nombreFirma;
    private String curpFirma;
    private String aseguramiento;
    
    private String cadenaOriginal;
    private String selloDigital;
    private String secuenciaNotarial;
    private String numeroSerie;

    private Boolean beneficio;
    private Boolean recargo;
    
    private BigDecimal salarioDiario;
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * @return the nss
     */
    public String getNss() {
        return nss;
    }
    /**
     * @param nss the nss to set
     */
    public void setNss(String nss) {
        this.nss = nss;
    }
    /**
     * @return the curp
     */
    public String getCurp() {
        return curp;
    }
    /**
     * @param curp the curp to set
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }
    /**
     * @return the fecNacimiento
     */
    public String getFecNacimiento() {
        return fecNacimiento;
    }
    /**
     * @param fecNacimiento the fecNacimiento to set
     */
    public void setFecNacimiento(String fecNacimiento) {
        this.fecNacimiento = fecNacimiento;
    }
    /**
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }
    /**
     * @param edad the edad to set
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }
    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    /**
     * @return the domicilio
     */
    public String getDomicilio() {
        return domicilio;
    }
    /**
     * @param domicilio the domicilio to set
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }
    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    /**
     * @return the nrp
     */
    public String getNrp() {
        return nrp;
    }
    /**
     * @param nrp the nrp to set
     */
    public void setNrp(String nrp) {
        this.nrp = nrp;
    }
    /**
     * @return the delegacion
     */
    public String getDelegacion() {
        return delegacion;
    }
    /**
     * @param delegacion the delegacion to set
     */
    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }
    /**
     * @return the subdelegacion
     */
    public String getSubdelegacion() {
        return subdelegacion;
    }
    /**
     * @param subdelegacion the subdelegacion to set
     */
    public void setSubdelegacion(String subdelegacion) {
        this.subdelegacion = subdelegacion;
    }
    /**
     * @return the tipoTramite
     */
    public String getTipoTramite() {
        return tipoTramite;
    }
    /**
     * @param tipoTramite the tipoTramite to set
     */
    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }
    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }
    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    /**
     * @return the cuota
     */
    public BigDecimal getCuota() {
        return cuota;
    }
    /**
     * @param cuota the cuota to set
     */
    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }
    /**
     * @return the formaPago
     */
    public String getFormaPago() {
        return formaPago;
    }
    /**
     * @param formaPago the formaPago to set
     */
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    /**
     * @return the cuestionario
     */
    public Boolean getCuestionario() {
        return cuestionario;
    }
    /**
     * @param cuestionario the cuestionario to set
     */
    public void setCuestionario(Boolean cuestionario) {
        this.cuestionario = cuestionario;
    }
    /**
     * @return the origen
     */
    public Integer getOrigen() {
        return origen;
    }
    /**
     * @param origen the origen to set
     */
    public void setOrigen(Integer origen) {
        this.origen = origen;
    }
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     * @return the subdelegacionUsuario
     */
    public String getSubdelegacionUsuario() {
        return subdelegacionUsuario;
    }
    /**
     * @param subdelegacionUsuario the subdelegacionUsuario to set
     */
    public void setSubdelegacionUsuario(String subdelegacionUsuario) {
        this.subdelegacionUsuario = subdelegacionUsuario;
    }
    /**
     * @return the rfcFirma
     */
    public String getRfcFirma() {
        return rfcFirma;
    }
    /**
     * @param rfcFirma the rfcFirma to set
     */
    public void setRfcFirma(String rfcFirma) {
        this.rfcFirma = rfcFirma;
    }
    /**
     * @return the nombreFirma
     */
    public String getNombreFirma() {
        return nombreFirma;
    }
    /**
     * @param nombreFirma the nombreFirma to set
     */
    public void setNombreFirma(String nombreFirma) {
        this.nombreFirma = nombreFirma;
    }
    /**
     * @return the curpFirma
     */
    public String getCurpFirma() {
        return curpFirma;
    }
    /**
     * @param curpFirma the curpFirma to set
     */
    public void setCurpFirma(String curpFirma) {
        this.curpFirma = curpFirma;
    }
    /**
     * @return the cadenaOriginal
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }
    /**
     * @param cadenaOriginal the cadenaOriginal to set
     */
    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }
    /**
     * @return the selloDigital
     */
    public String getSelloDigital() {
        return selloDigital;
    }
    /**
     * @param selloDigital the selloDigital to set
     */
    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }
    /**
     * @return the secuenciaNotarial
     */
    public String getSecuenciaNotarial() {
        return secuenciaNotarial;
    }
    /**
     * @param secuenciaNotarial the secuenciaNotarial to set
     */
    public void setSecuenciaNotarial(String secuenciaNotarial) {
        this.secuenciaNotarial = secuenciaNotarial;
    }
    /**
     * @return the numeroSerie
     */
    public String getNumeroSerie() {
        return numeroSerie;
    }
    /**
     * @param numeroSerie the numeroSerie to set
     */
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
    /**
     * @return the aseguramiento
     */
    public String getAseguramiento() {
        return aseguramiento;
    }
    /**
     * @param aseguramiento the aseguramiento to set
     */
    public void setAseguramiento(String aseguramiento) {
        this.aseguramiento = aseguramiento;
    }
    /**
     * @return the beneficio
     */
    public Boolean getBeneficio() {
        return beneficio;
    }
    /**
     * @param beneficio the beneficio to set
     */
    public void setBeneficio(Boolean beneficio) {
        this.beneficio = beneficio;
    }
    /**
     * @return the recargo
     */
    public Boolean getRecargo() {
        return recargo;
    }
    /**
     * @param recargo the recargo to set
     */
    public void setRecargo(Boolean recargo) {
        this.recargo = recargo;
    }
    /**
     * @return the salarioDiario
     */
    public BigDecimal getSalarioDiario() {
        return salarioDiario;
    }
    /**
     * @param salarioDiario the salarioDiario to set
     */
    public void setSalarioDiario(BigDecimal salarioDiario) {
        this.salarioDiario = salarioDiario;
    }
       
}
