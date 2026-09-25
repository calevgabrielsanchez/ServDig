package mx.gob.imss.ctirss.delta.persistence.bajas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity para tabla LOTE_PROCESAMIENTO_BAJA.
 * Control de lotes de procesamiento de bajas (ARCHIVO_SINDO, JOB_MORA, POST_WEB).
 *
 * @author Sistema Bajas por Reingreso RO
 * @version 1.0
 */
@Entity
@Table(name = "LOTE_PROCESAMIENTO_BAJA")
@NamedQuery(name = "LoteProcesamientoBaja.findAll", query = "SELECT l FROM LoteProcesamientoBaja l")
public class LoteProcesamientoBaja implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del lote (PK)
     */
    @Id
    @SequenceGenerator(name = "LOTE_BAJA_GENERATOR", sequenceName = "SEQ_LOTE_PROCESAMIENTO_BAJA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOTE_BAJA_GENERATOR")
    @Column(name = "CVE_ID_LOTE", nullable = false, precision = 22)
    private long cveIdLote;

    /**
     * Tipo de origen: ARCHIVO_SINDO, JOB_MORA, POST_WEB
     */
    @Column(name = "TP_ORIGEN", nullable = false, length = 30)
    private String tpOrigen;

    /**
     * Nombre del archivo original (solo para ARCHIVO_SINDO)
     */
    @Column(name = "TXT_NOMBRE_ARCHIVO", length = 200)
    private String txtNombreArchivo;

    /**
     * Fecha inicio del procesamiento
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_INICIO", nullable = false)
    private Date fecInicio;

    /**
     * Fecha fin del procesamiento (NULL si aún en proceso)
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_FIN")
    private Date fecFin;

    /**
     * Estado del lote: PROCESANDO, COMPLETADO, ERROR
     */
    @Column(name = "TP_ESTADO", nullable = false, length = 30)
    private String tpEstado;

    /**
     * Total de registros en el lote
     */
    @Column(name = "NUM_REGISTROS_TOTAL", nullable = false, precision = 10)
    private int numRegistrosTotal;

    /**
     * Registros procesados exitosamente
     */
    @Column(name = "NUM_EXITOSOS", nullable = false, precision = 10)
    private int numExitosos;

    /**
     * Registros con error
     */
    @Column(name = "NUM_ERRORES", nullable = false, precision = 10)
    private int numErrores;

    /**
     * Usuario que creó el lote
     */
    @Column(name = "CVE_USUARIO_CREACION", nullable = false, length = 50)
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
     * Usuario que modificó el lote
     */
    @Column(name = "CVE_USUARIO_MODIFICACION", length = 50)
    private String cveUsuarioModificacion;

    /**
     * Relación OneToMany con STG_REINGRESO_RO
     */
    @OneToMany(mappedBy = "loteProcesamientoBaja")
    private List<StgReingresoRO> stagingList;

    /**
     * Relación OneToMany con DIT_BAJA_SEGURO
     */
    @OneToMany(mappedBy = "loteProcesamientoBaja")
    private List<DitBajaSeguro> bajasList;

    /**
     * Constructor sin argumentos (requerido por JPA)
     */
    public LoteProcesamientoBaja() {
        // Inicializar contadores en 0
        this.numRegistrosTotal = 0;
        this.numExitosos = 0;
        this.numErrores = 0;
    }

    // ========================================================================
    // GETTERS Y SETTERS
    // ========================================================================

    public long getCveIdLote() {
        return cveIdLote;
    }

    public void setCveIdLote(long cveIdLote) {
        this.cveIdLote = cveIdLote;
    }

    public String getTpOrigen() {
        return tpOrigen;
    }

    public void setTpOrigen(String tpOrigen) {
        this.tpOrigen = tpOrigen;
    }

    public String getTxtNombreArchivo() {
        return txtNombreArchivo;
    }

    public void setTxtNombreArchivo(String txtNombreArchivo) {
        this.txtNombreArchivo = txtNombreArchivo;
    }

    public Date getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(Date fecInicio) {
        this.fecInicio = fecInicio;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public String getTpEstado() {
        return tpEstado;
    }

    public void setTpEstado(String tpEstado) {
        this.tpEstado = tpEstado;
    }

    public int getNumRegistrosTotal() {
        return numRegistrosTotal;
    }

    public void setNumRegistrosTotal(int numRegistrosTotal) {
        this.numRegistrosTotal = numRegistrosTotal;
    }

    public int getNumExitosos() {
        return numExitosos;
    }

    public void setNumExitosos(int numExitosos) {
        this.numExitosos = numExitosos;
    }

    public int getNumErrores() {
        return numErrores;
    }

    public void setNumErrores(int numErrores) {
        this.numErrores = numErrores;
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

    public List<StgReingresoRO> getStagingList() {
        return stagingList;
    }

    public void setStagingList(List<StgReingresoRO> stagingList) {
        this.stagingList = stagingList;
    }

    public List<DitBajaSeguro> getBajasList() {
        return bajasList;
    }

    public void setBajasList(List<DitBajaSeguro> bajasList) {
        this.bajasList = bajasList;
    }
}
