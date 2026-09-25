package mx.gob.imss.ctirss.delta.persistence.bajas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity para tabla STG_REINGRESO_RO.
 * Staging EXCLUSIVO para archivo posicional SINDO (174-233 caracteres).
 * Conserva línea raw original para auditoría y trazabilidad.
 *
 * @author Sistema Bajas por Reingreso RO
 * @version 1.0
 */
@Entity
@Table(name = "STG_REINGRESO_RO")
@NamedQuery(name = "StgReingresoRO.findAll", query = "SELECT s FROM StgReingresoRO s")
public class StgReingresoRO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del registro staging (PK)
     */
    @Id
    @SequenceGenerator(name = "STG_REINGRESO_GENERATOR", sequenceName = "SEQ_STG_REINGRESO_RO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_REINGRESO_GENERATOR")
    @Column(name = "CVE_ID_STAGING", nullable = false, precision = 22)
    private long cveIdStaging;

    /**
     * FK a LOTE_PROCESAMIENTO_BAJA
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_LOTE", nullable = false)
    private LoteProcesamientoBaja loteProcesamientoBaja;

    /**
     * FK manual a DIT_BAJA_SEGURO (sin constraint para evitar ciclo)
     * NULL hasta que se procese exitosamente
     */
    @Column(name = "CVE_ID_BAJA", precision = 22)
    private Long cveIdBaja;

    /**
     * Línea completa (174-233 caracteres) del archivo SINDO
     * Inmutable para auditoría crítica
     */
    @Column(name = "TXT_LINEA_ORIGINAL", nullable = false, length = 233)
    private String txtLineaOriginal;

    /**
     * Número de línea en archivo original (para trazabilidad)
     */
    @Column(name = "NUM_LINEA_ARCHIVO", nullable = false, precision = 10)
    private int numLineaArchivo;

    /**
     * Estado: PENDIENTE, PROCESADO, ERROR
     */
    @Column(name = "TP_ESTADO", nullable = false, length = 30)
    private String tpEstado;

    /**
     * Timestamp de carga en staging
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STP_CARGA", nullable = false)
    private Date stpCarga;

    /**
     * Timestamp de procesamiento (NULL si pendiente)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STP_PROCESAMIENTO")
    private Date stpProcesamiento;

    /**
     * Mensaje de error parsing (NULL si exitoso)
     */
    @Column(name = "TXT_ERROR", length = 500)
    private String txtError;

    /**
     * Contador de reintentos de procesamiento (máximo 5)
     */
    @Column(name = "NUM_INTENTOS", nullable = false, precision = 2)
    private int numIntentos;

    /**
     * Constructor sin argumentos (requerido por JPA)
     */
    public StgReingresoRO() {
        this.tpEstado = "PENDIENTE";
        this.numIntentos = 0;
    }

    // ========================================================================
    // GETTERS Y SETTERS
    // ========================================================================

    public long getCveIdStaging() {
        return cveIdStaging;
    }

    public void setCveIdStaging(long cveIdStaging) {
        this.cveIdStaging = cveIdStaging;
    }

    public LoteProcesamientoBaja getLoteProcesamientoBaja() {
        return loteProcesamientoBaja;
    }

    public void setLoteProcesamientoBaja(LoteProcesamientoBaja loteProcesamientoBaja) {
        this.loteProcesamientoBaja = loteProcesamientoBaja;
    }

    public Long getCveIdBaja() {
        return cveIdBaja;
    }

    public void setCveIdBaja(Long cveIdBaja) {
        this.cveIdBaja = cveIdBaja;
    }

    public String getTxtLineaOriginal() {
        return txtLineaOriginal;
    }

    public void setTxtLineaOriginal(String txtLineaOriginal) {
        this.txtLineaOriginal = txtLineaOriginal;
    }

    public int getNumLineaArchivo() {
        return numLineaArchivo;
    }

    public void setNumLineaArchivo(int numLineaArchivo) {
        this.numLineaArchivo = numLineaArchivo;
    }

    public String getTpEstado() {
        return tpEstado;
    }

    public void setTpEstado(String tpEstado) {
        this.tpEstado = tpEstado;
    }

    public Date getStpCarga() {
        return stpCarga;
    }

    public void setStpCarga(Date stpCarga) {
        this.stpCarga = stpCarga;
    }

    public Date getStpProcesamiento() {
        return stpProcesamiento;
    }

    public void setStpProcesamiento(Date stpProcesamiento) {
        this.stpProcesamiento = stpProcesamiento;
    }

    public String getTxtError() {
        return txtError;
    }

    public void setTxtError(String txtError) {
        this.txtError = txtError;
    }

    public int getNumIntentos() {
        return numIntentos;
    }

    public void setNumIntentos(int numIntentos) {
        this.numIntentos = numIntentos;
    }
}
