package mx.gob.imss.ctirss.delta.persistence.bajas;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "DIT_BAJA_EXPRESA_DETALLE")
public class DitBajaExpresaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name = "SEQ_BAJA_EXPRESA_DETALLE",
        sequenceName = "SEQ_BAJA_EXPRESA_DETALLE",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "SEQ_BAJA_EXPRESA_DETALLE"
    )
    @Column(name = "CVE_ID_DETALLE", nullable = false, precision = 22)
    private Long cveIdDetalle;

    /**
     * FK a DIT_BAJA_SEGURO (relación 1:1 - UNIQUE constraint en DDL)
     * CORREGIDO: Ahora usa @OneToOne como DitBajaReingresoDetalle
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_BAJA", nullable = false, unique = true)
    private DitBajaSeguro ditBajaSeguro;

    /**
     * Fecha/hora de solicitud de baja (REQ-19)
     */
    @Column(name = "FEC_SOLICITUD_BAJA", nullable = false)
    private Timestamp fecSolicitudBaja;

    /**
     * IP origen de la solicitud
     */
    @Column(name = "TXT_IP_SOLICITUD", length = 40)
    private String txtIpSolicitud;

    /**
     * Usuario que solicitó la baja
     */
    @Column(name = "CVE_USUARIO_SOLICITUD", length = 50)
    private String cveUsuarioSolicitud;

    /**
     * Fecha/hora de confirmación de baja (REQ-20)
     */
    @Column(name = "FEC_CONFIRMACION_BAJA", nullable = false)
    private Timestamp fecConfirmacionBaja;

    /**
     * IP origen de la confirmación
     */
    @Column(name = "TXT_IP_CONFIRMACION", length = 40)
    private String txtIpConfirmacion;

    /**
     * Motivo de baja capturado del usuario
     */
    @Column(name = "TXT_MOTIVO", length = 500)
    private String txtMotivo;

    /**
     * FK a DIT_SOLICITUD_BAJA_WEB (referencia a la solicitud)
     */
    @Column(name = "CVE_ID_SOLICITUD", nullable = false, precision = 22)
    private Long cveIdSolicitud;

    /**
     * Token UUID que se usó para confirmar (para auditoría)
     */
    @Column(name = "TXT_TOKEN_USADO", length = 64)
    private String txtTokenUsado;

    /**
     * Timestamp de creación
     */
    @Column(name = "STP_CREACION", nullable = false)
    private Timestamp stpCreacion;

    // ========================================================================
    // CONSTRUCTORES
    // ========================================================================

    public DitBajaExpresaDetalle() {
    }

    // ========================================================================
    // GETTERS Y SETTERS
    // ========================================================================

    public Long getCveIdDetalle() {
        return cveIdDetalle;
    }

    public void setCveIdDetalle(Long cveIdDetalle) {
        this.cveIdDetalle = cveIdDetalle;
    }

    public DitBajaSeguro getDitBajaSeguro() {
        return ditBajaSeguro;
    }

    public void setDitBajaSeguro(DitBajaSeguro ditBajaSeguro) {
        this.ditBajaSeguro = ditBajaSeguro;
    }

    /**
     * Método helper para obtener CVE_ID_BAJA cuando se necesite
     * (mantiene compatibilidad con código existente)
     */
    public Long getCveIdBaja() {
        return ditBajaSeguro != null ? ditBajaSeguro.getCveIdBaja() : null;
    }

    public Timestamp getFecSolicitudBaja() {
        return fecSolicitudBaja;
    }

    public void setFecSolicitudBaja(Timestamp fecSolicitudBaja) {
        this.fecSolicitudBaja = fecSolicitudBaja;
    }

    public String getTxtIpSolicitud() {
        return txtIpSolicitud;
    }

    public void setTxtIpSolicitud(String txtIpSolicitud) {
        this.txtIpSolicitud = txtIpSolicitud;
    }

    public String getCveUsuarioSolicitud() {
        return cveUsuarioSolicitud;
    }

    public void setCveUsuarioSolicitud(String cveUsuarioSolicitud) {
        this.cveUsuarioSolicitud = cveUsuarioSolicitud;
    }

    public Timestamp getFecConfirmacionBaja() {
        return fecConfirmacionBaja;
    }

    public void setFecConfirmacionBaja(Timestamp fecConfirmacionBaja) {
        this.fecConfirmacionBaja = fecConfirmacionBaja;
    }

    public String getTxtIpConfirmacion() {
        return txtIpConfirmacion;
    }

    public void setTxtIpConfirmacion(String txtIpConfirmacion) {
        this.txtIpConfirmacion = txtIpConfirmacion;
    }

    public String getTxtMotivo() {
        return txtMotivo;
    }

    public void setTxtMotivo(String txtMotivo) {
        this.txtMotivo = txtMotivo;
    }

    public Long getCveIdSolicitud() {
        return cveIdSolicitud;
    }

    public void setCveIdSolicitud(Long cveIdSolicitud) {
        this.cveIdSolicitud = cveIdSolicitud;
    }

    public String getTxtTokenUsado() {
        return txtTokenUsado;
    }

    public void setTxtTokenUsado(String txtTokenUsado) {
        this.txtTokenUsado = txtTokenUsado;
    }

    public Timestamp getStpCreacion() {
        return stpCreacion;
    }

    public void setStpCreacion(Timestamp stpCreacion) {
        this.stpCreacion = stpCreacion;
    }

    @Override
    public String toString() {
        return "DitBajaExpresaDetalle{" +
                "cveIdDetalle=" + cveIdDetalle +
                ", cveIdBaja=" + (ditBajaSeguro != null ? ditBajaSeguro.getCveIdBaja() : null) +
                ", fecSolicitudBaja=" + fecSolicitudBaja +
                ", fecConfirmacionBaja=" + fecConfirmacionBaja +
                ", cveUsuarioSolicitud='" + cveUsuarioSolicitud + '\'' +
                ", txtMotivo='" + txtMotivo + '\'' +
                '}';
    }
}
