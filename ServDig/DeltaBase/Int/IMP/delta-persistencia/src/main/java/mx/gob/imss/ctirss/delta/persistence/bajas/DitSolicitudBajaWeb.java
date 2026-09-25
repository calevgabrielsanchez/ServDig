package mx.gob.imss.ctirss.delta.persistence.bajas;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity para DIT_SOLICITUD_BAJA_WEB (REQ 14-20)
 *
 * Almacena solicitudes de baja expresa con token de confirmación en 2 pasos:
 * - Token UUID con validez de 72 horas
 * - Estado: PENDIENTE → CONFIRMADA | EXPIRADA | CANCELADA
 * - Auditoría completa (fechas solicitud/confirmación, IPs)
 *
 *
 * @author Sistema Bajas IMSS
 * @version 1.0
 */
@Entity
@Table(name = "DIT_SOLICITUD_BAJA_WEB")
public class DitSolicitudBajaWeb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name = "SEQ_SOLICITUD_BAJA_WEB",
        sequenceName = "SEQ_SOLICITUD_BAJA_WEB",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "SEQ_SOLICITUD_BAJA_WEB"
    )
    @Column(name = "CVE_ID_SOLICITUD", nullable = false, precision = 22)
    private Long cveIdSolicitud;

    /**
     * FK a DIT_SEGURO_IVRO - Seguro sobre el que se solicita la baja
     */
    @Column(name = "CVE_ID_SEGURO_IVRO", nullable = false, precision = 22)
    private Long cveIdSeguroIvro;

    /**
     * Token UUID único enviado al correo (REQ-17)
     * Validez: 72 horas
     */
    @Column(name = "TXT_TOKEN", nullable = false, length = 64, unique = true)
    private String txtToken;

    /**
     * Fecha de expiración del token (SYSDATE + 3 días)
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_EXPIRACION", nullable = false)
    private Date fecExpiracion;

    /**
     * Estado del token:
     * - PENDIENTE: Correo enviado, esperando confirmación
     * - CONFIRMADA: Usuario confirmó y baja ejecutada
     * - EXPIRADA: Token venció (>72h)
     * - CANCELADA: Solicitud cancelada
     */
    @Column(name = "TP_ESTADO", nullable = false, length = 20)
    private String tpEstado;

    /**
     * Fecha/hora de solicitud de baja (REQ-19)
     */
    @Column(name = "FEC_SOLICITUD", nullable = false)
    private Timestamp fecSolicitud;

    /**
     * Fecha/hora de confirmación de baja (REQ-20)
     * NULL hasta que se confirme
     */
    @Column(name = "FEC_CONFIRMACION")
    private Timestamp fecConfirmacion;

    /**
     * IP del cliente que solicitó la baja
     */
    @Column(name = "TXT_IP_SOLICITUD", length = 40)
    private String txtIpSolicitud;

    /**
     * IP del cliente que confirmó la baja
     */
    @Column(name = "TXT_IP_CONFIRMACION", length = 40)
    private String txtIpConfirmacion;

    /**
     * Motivo de baja capturado del usuario
     */
    @Column(name = "TXT_MOTIVO", length = 500)
    private String txtMotivo;

    /**
     * Usuario autenticado al solicitar
     */
    @Column(name = "CVE_USUARIO_SOLICITUD", length = 50)
    private String cveUsuarioSolicitud;

    /**
     * Timestamp de creación
     */
    @Column(name = "STP_CREACION", nullable = false)
    private Timestamp stpCreacion;

    /**
     * Timestamp de actualización
     */
    @Column(name = "STP_ACTUALIZACION", nullable = false)
    private Timestamp stpActualizacion;

    // ========================================================================
    // CONSTRUCTORES
    // ========================================================================

    public DitSolicitudBajaWeb() {
    }

    // ========================================================================
    // GETTERS Y SETTERS
    // ========================================================================

    public Long getCveIdSolicitud() {
        return cveIdSolicitud;
    }

    public void setCveIdSolicitud(Long cveIdSolicitud) {
        this.cveIdSolicitud = cveIdSolicitud;
    }

    public Long getCveIdSeguroIvro() {
        return cveIdSeguroIvro;
    }

    public void setCveIdSeguroIvro(Long cveIdSeguroIvro) {
        this.cveIdSeguroIvro = cveIdSeguroIvro;
    }

    public String getTxtToken() {
        return txtToken;
    }

    public void setTxtToken(String txtToken) {
        this.txtToken = txtToken;
    }

    public Date getFecExpiracion() {
        return fecExpiracion;
    }

    public void setFecExpiracion(Date fecExpiracion) {
        this.fecExpiracion = fecExpiracion;
    }

    public String getTpEstado() {
        return tpEstado;
    }

    public void setTpEstado(String tpEstado) {
        this.tpEstado = tpEstado;
    }

    public Timestamp getFecSolicitud() {
        return fecSolicitud;
    }

    public void setFecSolicitud(Timestamp fecSolicitud) {
        this.fecSolicitud = fecSolicitud;
    }

    public Timestamp getFecConfirmacion() {
        return fecConfirmacion;
    }

    public void setFecConfirmacion(Timestamp fecConfirmacion) {
        this.fecConfirmacion = fecConfirmacion;
    }

    public String getTxtIpSolicitud() {
        return txtIpSolicitud;
    }

    public void setTxtIpSolicitud(String txtIpSolicitud) {
        this.txtIpSolicitud = txtIpSolicitud;
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

    public String getCveUsuarioSolicitud() {
        return cveUsuarioSolicitud;
    }

    public void setCveUsuarioSolicitud(String cveUsuarioSolicitud) {
        this.cveUsuarioSolicitud = cveUsuarioSolicitud;
    }

    public Timestamp getStpCreacion() {
        return stpCreacion;
    }

    public void setStpCreacion(Timestamp stpCreacion) {
        this.stpCreacion = stpCreacion;
    }

    public Timestamp getStpActualizacion() {
        return stpActualizacion;
    }

    public void setStpActualizacion(Timestamp stpActualizacion) {
        this.stpActualizacion = stpActualizacion;
    }

    @Override
    public String toString() {
        return "DitSolicitudBajaWeb{" +
                "cveIdSolicitud=" + cveIdSolicitud +
                ", cveIdSeguroIvro=" + cveIdSeguroIvro +
                ", txtToken='" + txtToken + '\'' +
                ", tpEstado='" + tpEstado + '\'' +
                ", fecSolicitud=" + fecSolicitud +
                ", fecConfirmacion=" + fecConfirmacion +
                '}';
    }
}
