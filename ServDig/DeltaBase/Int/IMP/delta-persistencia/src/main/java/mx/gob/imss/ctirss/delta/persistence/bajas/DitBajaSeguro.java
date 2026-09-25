package mx.gob.imss.ctirss.delta.persistence.bajas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;

/**
 * Entity para tabla DIT_BAJA_SEGURO.
 * Tabla CENTRAL unificada con campos COMUNES a todos los tipos de baja
 * (REINGRESO_RO, MORA, EXPRESA).
 *
 * Incluye FK cross-schema a MGPBDTU9X.DIT_SEGURO_IVRO (DDL v3.0).
 *
 * @author Sistema Bajas por Reingreso RO
 * @version 3.0
 */
@Entity
@Table(name = "DIT_BAJA_SEGURO")
@NamedQuery(name = "DitBajaSeguro.findAll", query = "SELECT d FROM DitBajaSeguro d")
public class DitBajaSeguro implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la baja (PK)
     */
    @Id
    @SequenceGenerator(name = "BAJA_SEGURO_GENERATOR", sequenceName = "SEQ_BAJA_SEGURO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BAJA_SEGURO_GENERATOR")
    @Column(name = "CVE_ID_BAJA", nullable = false, precision = 22)
    private long cveIdBaja;

    /**
     * Discriminador tipo de baja: REINGRESO_RO, MORA, EXPRESA
     */
    @Column(name = "TP_BAJA", nullable = false, length = 30)
    private String tpBaja;

    /**
     * FK manual a STG_REINGRESO_RO (sin constraint)
     * SOLO para REINGRESO_RO (NULL para MORA/EXPRESA)
     */
    @Column(name = "CVE_ID_STAGING", precision = 22)
    private Long cveIdStaging;

    /**
     * FK a LOTE_PROCESAMIENTO_BAJA
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_LOTE", nullable = false)
    private LoteProcesamientoBaja loteProcesamientoBaja;

    /**
     * FK cross-schema a MGPBDTU9X.DIT_SEGURO_IVRO
     * NUEVO v3.0: Relación directa con el seguro que se está dando de baja
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_SEGURO_IVRO", nullable = false)
    private DitSeguroIvro ditSeguroIvro;

    /**
     * NSS del asegurado (11 dígitos numéricos)
     */
    @Column(name = "CVE_NSS", nullable = false, length = 11)
    private String cveNss;

    /**
     * Nombre completo del asegurado
     * Obtenido via lookup PersonaFisicaServiceBusinessRemote
     */
    @Column(name = "TXT_NOMBRE_COMPLETO", length = 150)
    private String txtNombreCompleto;

    /**
     * CURP del asegurado (18 caracteres)
     * Obtenido via lookup PersonaFisicaServiceBusinessRemote
     */
    @Column(name = "TXT_CURP", length = 18)
    private String txtCurp;

    /**
     * Fecha de solicitud de la baja
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_SOLICITUD", nullable = false)
    private Date fecSolicitud;

    /**
     * Fecha efectiva de la baja
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_EFECTIVA_BAJA", nullable = false)
    private Date fecEfectivaBaja;

    /**
     * Estado de la máquina de estados:
     * PENDIENTE_VALIDACION, VALIDADO, RECHAZADO, EN_PROCESO,
     * PROCESADO, NOTIFICADO, ERROR, CANCELADO
     */
    @Column(name = "TP_ESTADO", nullable = false, length = 30)
    private String tpEstado;

    /**
     * Usuario que creó el registro
     */
    @Column(name = "CVE_USUARIO_CREACION", length = 50)
    private String cveUsuarioCreacion;

    /**
     * Timestamp de creación
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STP_CREACION", nullable = false)
    private Date stpCreacion;

    /**
     * Timestamp de última actualización
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STP_ACTUALIZACION", nullable = false)
    private Date stpActualizacion;

    /**
     * Usuario que modificó el registro
     */
    @Column(name = "CVE_USUARIO_MODIFICACION", length = 50)
    private String cveUsuarioModificacion;

    /**
     * Observaciones adicionales
     */
    @Column(name = "TXT_OBSERVACIONES", length = 500)
    private String txtObservaciones;

    /**
     * Relación OneToOne con DIT_BAJA_REINGRESO_DETALLE
     */
    @OneToOne(mappedBy = "ditBajaSeguro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DitBajaReingresoDetalle detalle;

    /**
     * Constructor sin argumentos (requerido por JPA)
     */
    public DitBajaSeguro() {
        this.tpEstado = "PENDIENTE_VALIDACION";
    }
    /**
     * Correo(s) electrónico(s) del asegurado
     */
    @Column(name = "TXT_CORREOS", length = 300)
    private String txtCorreos;



    public String getTxtCorreos() {
        return txtCorreos;
    }

    public void setTxtCorreos(String txtCorreos) {
        this.txtCorreos = txtCorreos;
    }

    // ========================================================================
    // GETTERS Y SETTERS
    // ========================================================================

    public long getCveIdBaja() {
        return cveIdBaja;
    }

    public void setCveIdBaja(long cveIdBaja) {
        this.cveIdBaja = cveIdBaja;
    }

    public String getTpBaja() {
        return tpBaja;
    }

    public void setTpBaja(String tpBaja) {
        this.tpBaja = tpBaja;
    }

    public Long getCveIdStaging() {
        return cveIdStaging;
    }

    public void setCveIdStaging(Long cveIdStaging) {
        this.cveIdStaging = cveIdStaging;
    }

    public LoteProcesamientoBaja getLoteProcesamientoBaja() {
        return loteProcesamientoBaja;
    }

    public void setLoteProcesamientoBaja(LoteProcesamientoBaja loteProcesamientoBaja) {
        this.loteProcesamientoBaja = loteProcesamientoBaja;
    }

    /**
     * Obtiene el seguro IVRO relacionado (FK cross-schema)
     *
     * @return DitSeguroIvro entity
     */
    public DitSeguroIvro getDitSeguroIvro() {
        return ditSeguroIvro;
    }

    /**
     * Establece el seguro IVRO relacionado (FK cross-schema)
     *
     * @param ditSeguroIvro Entity del seguro a dar de baja
     */
    public void setDitSeguroIvro(DitSeguroIvro ditSeguroIvro) {
        this.ditSeguroIvro = ditSeguroIvro;
    }

    public String getCveNss() {
        return cveNss;
    }

    public void setCveNss(String cveNss) {
        this.cveNss = cveNss;
    }

    public String getTxtNombreCompleto() {
        return txtNombreCompleto;
    }

    public void setTxtNombreCompleto(String txtNombreCompleto) {
        this.txtNombreCompleto = txtNombreCompleto;
    }

    public String getTxtCurp() {
        return txtCurp;
    }

    public void setTxtCurp(String txtCurp) {
        this.txtCurp = txtCurp;
    }

    public Date getFecSolicitud() {
        return fecSolicitud;
    }

    public void setFecSolicitud(Date fecSolicitud) {
        this.fecSolicitud = fecSolicitud;
    }

    public Date getFecEfectivaBaja() {
        return fecEfectivaBaja;
    }

    public void setFecEfectivaBaja(Date fecEfectivaBaja) {
        this.fecEfectivaBaja = fecEfectivaBaja;
    }

    public String getTpEstado() {
        return tpEstado;
    }

    public void setTpEstado(String tpEstado) {
        this.tpEstado = tpEstado;
    }

    public String getCveUsuarioCreacion() {
        return cveUsuarioCreacion;
    }

    public void setCveUsuarioCreacion(String cveUsuarioCreacion) {
        this.cveUsuarioCreacion = cveUsuarioCreacion;
    }

    public Date getStpCreacion() {
        return stpCreacion;
    }

    public void setStpCreacion(Date stpCreacion) {
        this.stpCreacion = stpCreacion;
    }

    public Date getStpActualizacion() {
        return stpActualizacion;
    }

    public void setStpActualizacion(Date stpActualizacion) {
        this.stpActualizacion = stpActualizacion;
    }

    public String getCveUsuarioModificacion() {
        return cveUsuarioModificacion;
    }

    public void setCveUsuarioModificacion(String cveUsuarioModificacion) {
        this.cveUsuarioModificacion = cveUsuarioModificacion;
    }

    public String getTxtObservaciones() {
        return txtObservaciones;
    }

    public void setTxtObservaciones(String txtObservaciones) {
        this.txtObservaciones = txtObservaciones;
    }

    public DitBajaReingresoDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(DitBajaReingresoDetalle detalle) {
        this.detalle = detalle;
    }
}
