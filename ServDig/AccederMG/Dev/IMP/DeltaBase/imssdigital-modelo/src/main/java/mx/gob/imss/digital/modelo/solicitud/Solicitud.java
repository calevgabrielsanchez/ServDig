/**
 * 
 */
package mx.gob.imss.digital.modelo.solicitud;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.interfaces.MensajeError;
import mx.gob.imss.digital.modelo.tramite.Tramite;

/**
 * Solicitud de una operacion en el portal
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitud", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
@XmlRootElement(name = "solicitud", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
public class Solicitud implements Serializable, MensajeError {
    
    /**
     * serial version UID
     */
    private final static long serialVersionUID = 1234321L;
    /**
     * Lista de tramites asociados a una solicitud
     */
    protected Tramite[] tramite;
    /**
     * identificador de la solicitud
     */
    protected Long idSolicitud;
    /**
     * Numero de la solicitud
     */
    protected String numSolicitud;
    /**
     * Usuario de la solicitud
     */
    protected String usuario;
    /**
     * fecha de registro
     */
    protected Date fechaRegistro;
    /**
     * Fecha registro formateada
     */
    protected String fechaRegistroFormateada;
    /**
     * identificador del estado de la solicitud
     */
    protected EstadoSolicitud estadoSolicitud;
    /**
     * Firma electronica asociada a la solicitud
     */
    protected FirmaElectronica firmaElectronica; 
    /**
     * Tipo de solicitud a procesar
     */
    protected TipoSolicitud tipoSolicitud;
    /**
     * Origen de la solicitud
     */
    protected OrigenSolicitud origenSolicitud;
    /**
     * Usuario responsable
     */
    private String usuarioResponsable;

    /**
     * Atributos necesarios para guardar lo relacionado a la firma digital
     */
    private boolean firmadaDigitalmente;
    /**
     * Cadena original
     */
    private String cadenaOriginal;
    /**
     * Sello digital
     */
    private String selloDigital;
    /**
     * Secuencia de notaria
     */
    private String secuenciaDeNotaria;
    /**
     * Numero de serie de certificado
     */
    private String numeroSerieCertificado;
    /**
     * Mensaje de error
     */
    private String errorFormGeneral;

    /**
     * Gets the value of the tramite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tramite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTramite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tramite }
     * 
     * 
     */
    public Tramite[] getTramite() {
        if (tramite == null) {
            tramite = new Tramite[]{};
        }
        return this.tramite;
    }

    /**
     * Gets the value of the idSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Sets the value of the idSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdSolicitud(Long value) {
        this.idSolicitud = value;
    }

    /**
     * Gets the value of the numSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumSolicitud() {
        return numSolicitud;
    }

    /**
     * Sets the value of the numSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumSolicitud(String value) {
        this.numSolicitud = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the fechaRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Sets the value of the fechaRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaRegistro(Date value) {
        this.fechaRegistro = value;
    }

    /**
     * Gets the value of the fechaRegistroFormateada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaRegistroFormateada() {
        return fechaRegistroFormateada;
    }

    /**
     * Sets the value of the fechaRegistroFormateada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaRegistroFormateada(String value) {
        this.fechaRegistroFormateada = value;
    }

    

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the firmaElectronica
     */
    public FirmaElectronica getFirmaElectronica() {
        return firmaElectronica;
    }

    /**
     * @param firmaElectronica the firmaElectronica to set
     */
    public void setFirmaElectronica(FirmaElectronica firmaElectronica) {
        this.firmaElectronica = firmaElectronica;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(Tramite[] tramite) {
        this.tramite = tramite != null ? tramite.clone() : null;
    }

    /**
     * @return the tipoSolicitud
     */
    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * @param tipoSolicitud the tipoSolicitud to set
     */
    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * @return the origenSolicitud
     */
    public OrigenSolicitud getOrigenSolicitud() {
        return origenSolicitud;
    }

    /**
     * @param origenSolicitud the origenSolicitud to set
     */
    public void setOrigenSolicitud(OrigenSolicitud origenSolicitud) {
        this.origenSolicitud = origenSolicitud;
    }

    /**
     * @return the usuarioResponsable
     */
    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    /**
     * @param usuarioResponsable the usuarioResponsable to set
     */
    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    /**
     * @return the firmadaDigitalmente
     */
    public boolean isFirmadaDigitalmente() {
        return firmadaDigitalmente;
    }

    /**
     * @param firmadaDigitalmente the firmadaDigitalmente to set
     */
    public void setFirmadaDigitalmente(boolean firmadaDigitalmente) {
        this.firmadaDigitalmente = firmadaDigitalmente;
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
     * @return the secuenciaDeNotaria
     */
    public String getSecuenciaDeNotaria() {
        return secuenciaDeNotaria;
    }

    /**
     * @param secuenciaDeNotaria the secuenciaDeNotaria to set
     */
    public void setSecuenciaDeNotaria(String secuenciaDeNotaria) {
        this.secuenciaDeNotaria = secuenciaDeNotaria;
    }

    /**
     * @return the numeroSerieCertificado
     */
    public String getNumeroSerieCertificado() {
        return numeroSerieCertificado;
    }

    /**
     * @param numeroSerieCertificado the numeroSerieCertificado to set
     */
    public void setNumeroSerieCertificado(String numeroSerieCertificado) {
        this.numeroSerieCertificado = numeroSerieCertificado;
    }

    /**
     * @return the errorFormGeneral
     */
    public String getErrorFormGeneral() {
        return errorFormGeneral;
    }

    /**
     * @param errorFormGeneral the errorFormGeneral to set
     */
    public void setErrorFormGeneral(String errorFormGeneral) {
        this.errorFormGeneral = errorFormGeneral;
    }

    

}
