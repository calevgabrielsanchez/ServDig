package mx.gob.imss.ctirss.delta.persistence.bajas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity para tabla DIT_BAJA_REINGRESO_DETALLE.
 * Campos ESPECÍFICOS solo para bajas tipo REINGRESO_RO (REQ 1-6)
 * extraídos de archivo SINDO posicional 174 caracteres.
 *
 * @author Sistema Bajas por Reingreso RO
 * @version 1.0
 */
@Entity
@Table(name = "DIT_BAJA_REINGRESO_DETALLE")
@NamedQuery(name = "DitBajaReingresoDetalle.findAll", query = "SELECT d FROM DitBajaReingresoDetalle d")
public class DitBajaReingresoDetalle implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del detalle (PK)
     */
    @Id
    @SequenceGenerator(name = "DETALLE_REINGRESO_GENERATOR", sequenceName = "SEQ_BAJA_REINGRESO_DETALLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DETALLE_REINGRESO_GENERATOR")
    @Column(name = "CVE_ID_DETALLE", nullable = false, precision = 22)
    private long cveIdDetalle;

    /**
 	 *Registro Patronal origen sub (sucursal/subdelegación) (2) 12-13
     */
    @Column(name = "CVE_ID_SUBDELEGACION_PATORIG", nullable = false, length = 11)
    private String rpOrigSub;
    
    
    /**
     * FK a DIT_BAJA_SEGURO (relaciÃ³n 1:1)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_BAJA", nullable = false, unique = true)
    private DitBajaSeguro ditBajaSeguro;

    // ========================================================================
    // DATOS PATRÓN ORIGEN (Modalidad 40 - Baja)
    // ========================================================================

    /**
     * Registro patronal origen (Modalidad 40)
     * Archivo SINDO pos 24-34: 11 caracteres
     */
    @Column(name = "CVE_PATRON_ORIGEN", nullable = false, length = 11)
    private String cvePatronOrigen;

    /**
     * Nombre patrón Modalidad 40
     * REQ 2: Lookup via SujetoObligadoServiceBusinessRemote
     */
    @Column(name = "TXT_NOMBRE_PATRON_ORIGEN", length = 100)
    private String txtNombrePatronOrigen;

    /**
     * Salario diario origen (Modalidad 40) 85-92
     */
    @Column(name = "NUM_SALARIO_DIARIO_ORIGEN", precision = 10, scale = 2)
    private BigDecimal numSalarioDiarioOrigen;

    /**
     * Registro Patronal destino sub (sucursal/subdelegacion) (2) 93-94
     */
    @Column(name = "CVE_ID_SUBDELEGACION_PATDEST", length = 2)
    private String rpDestSub;
    
    /**
     * Modalidad origen
     */
    @Column(name = "CVE_MODALIDAD_ORIGEN", length = 2)
    private String cveModalidadOrigen;

    /**
     * Tipo de movimiento origen
     */
    @Column(name = "TP_MOVIMIENTO_ORIGEN", length = 2)
    private String tpMovimientoOrigen;

    // ========================================================================
    // DATOS PATRÓN DESTINO (Régimen Obligatorio - Alta/Reingreso)
    // ========================================================================

    /**
     * Registro patronal destino (Régimen Obligatorio)
     * Archivo SINDO pos 105-115: 11 caracteres
     */
    @Column(name = "CVE_PATRON_DESTINO", nullable = false, length = 11)
    private String cvePatronDestino;

    /**
     * Nombre patrón RO que causó la baja
     * REQ 6: Lookup via SujetoObligadoServiceBusinessRemote
     */
    @Column(name = "TXT_NOMBRE_PATRON_DESTINO", length = 100)
    private String txtNombrePatronDestino;

    /**
     * Salario diario destino (Régimen Obligatorio)
     */
    @Column(name = "NUM_SALARIO_DIARIO_DESTINO", precision = 10, scale = 2)
    private BigDecimal numSalarioDiarioDestino;

    /**
     * Modalidad destino (siempre 40 = RO)
     */
    @Column(name = "CVE_MODALIDAD_DESTINO", length = 2)
    private String cveModalidadDestino;

    /**
     * Tipo de movimiento destino
     */
    @Column(name = "TP_MOVIMIENTO_DESTINO", length = 2)
    private String tpMovimientoDestino;

    // ========================================================================
    // FECHAS CRÍTICAS
    // ========================================================================

    /**
     * REQ 1: Fecha reingreso a RO
     * Archivo SINDO pos 95-102 (DDMMAAAA)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_MOVIMIENTO_PATRON", nullable = false)
    private Date fecMovimientoPatron;

    /**
     * REQ 2: Fecha baja Modalidad 40
     * Archivo SINDO pos 14-21 (DDMMAAAA)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_BAJA_MODALIDAD_40", nullable = false)
    private Date fecBajaModalidad40;

    // ========================================================================
    // OTROS DATOS DEL ARCHIVO SINDO
    // ========================================================================

    /**
     * CIZ (Clasificación Internacional de Zona)
     */
    @Column(name = "CVE_CIZ", length = 1)
    private String cveCiz;

    /**
     * Timestamp de creación
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STP_CREACION", nullable = false)
    private Date stpCreacion;

    /**
     * Constructor sin argumentos (requerido por JPA)
     */
    public DitBajaReingresoDetalle() {
    }

    // ========================================================================
    // GETTERS Y SETTERS
    // ========================================================================

    public long getCveIdDetalle() {
        return cveIdDetalle;
    }

    public String getRpOrigSub() {
		return rpOrigSub;
	}

	public void setRpOrigSub(String rpOrigSub) {
		this.rpOrigSub = rpOrigSub;
	}

	public String getRpDestSub() {
		return rpDestSub;
	}

	public void setRpDestSub(String rpDestSub) {
		this.rpDestSub = rpDestSub;
	}

	public void setCveIdDetalle(long cveIdDetalle) {
        this.cveIdDetalle = cveIdDetalle;
    }

    public DitBajaSeguro getDitBajaSeguro() {
        return ditBajaSeguro;
    }

    public void setDitBajaSeguro(DitBajaSeguro ditBajaSeguro) {
        this.ditBajaSeguro = ditBajaSeguro;
    }

    public String getCvePatronOrigen() {
        return cvePatronOrigen;
    }

    public void setCvePatronOrigen(String cvePatronOrigen) {
        this.cvePatronOrigen = cvePatronOrigen;
    }

    public String getTxtNombrePatronOrigen() {
        return txtNombrePatronOrigen;
    }

    public void setTxtNombrePatronOrigen(String txtNombrePatronOrigen) {
        this.txtNombrePatronOrigen = txtNombrePatronOrigen;
    }

    public BigDecimal getNumSalarioDiarioOrigen() {
        return numSalarioDiarioOrigen;
    }

    public void setNumSalarioDiarioOrigen(BigDecimal numSalarioDiarioOrigen) {
        this.numSalarioDiarioOrigen = numSalarioDiarioOrigen;
    }

    public String getCveModalidadOrigen() {
        return cveModalidadOrigen;
    }

    public void setCveModalidadOrigen(String cveModalidadOrigen) {
        this.cveModalidadOrigen = cveModalidadOrigen;
    }

    public String getTpMovimientoOrigen() {
        return tpMovimientoOrigen;
    }

    public void setTpMovimientoOrigen(String tpMovimientoOrigen) {
        this.tpMovimientoOrigen = tpMovimientoOrigen;
    }

    public String getCvePatronDestino() {
        return cvePatronDestino;
    }

    public void setCvePatronDestino(String cvePatronDestino) {
        this.cvePatronDestino = cvePatronDestino;
    }

    public String getTxtNombrePatronDestino() {
        return txtNombrePatronDestino;
    }

    public void setTxtNombrePatronDestino(String txtNombrePatronDestino) {
        this.txtNombrePatronDestino = txtNombrePatronDestino;
    }

    public BigDecimal getNumSalarioDiarioDestino() {
        return numSalarioDiarioDestino;
    }

    public void setNumSalarioDiarioDestino(BigDecimal numSalarioDiarioDestino) {
        this.numSalarioDiarioDestino = numSalarioDiarioDestino;
    }

    public String getCveModalidadDestino() {
        return cveModalidadDestino;
    }

    public void setCveModalidadDestino(String cveModalidadDestino) {
        this.cveModalidadDestino = cveModalidadDestino;
    }

    public String getTpMovimientoDestino() {
        return tpMovimientoDestino;
    }

    public void setTpMovimientoDestino(String tpMovimientoDestino) {
        this.tpMovimientoDestino = tpMovimientoDestino;
    }

    public Date getFecMovimientoPatron() {
        return fecMovimientoPatron;
    }

    public void setFecMovimientoPatron(Date fecMovimientoPatron) {
        this.fecMovimientoPatron = fecMovimientoPatron;
    }

    public Date getFecBajaModalidad40() {
        return fecBajaModalidad40;
    }

    public void setFecBajaModalidad40(Date fecBajaModalidad40) {
        this.fecBajaModalidad40 = fecBajaModalidad40;
    }

    public String getCveCiz() {
        return cveCiz;
    }

    public void setCveCiz(String cveCiz) {
        this.cveCiz = cveCiz;
    }

    public Date getStpCreacion() {
        return stpCreacion;
    }

    public void setStpCreacion(Date stpCreacion) {
        this.stpCreacion = stpCreacion;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DitBajaReingresoDetalle {");
        sb.append("cveIdDetalle=").append(cveIdDetalle);
        sb.append(", rpOrigSub='").append(rpOrigSub).append('\'');
        sb.append(", ditBajaSeguroId=").append(ditBajaSeguro != null ? ditBajaSeguro.getCveIdBaja() : "null");
        sb.append(", cvePatronOrigen='").append(cvePatronOrigen).append('\'');
        sb.append(", txtNombrePatronOrigen='").append(txtNombrePatronOrigen).append('\'');
        sb.append(", numSalarioDiarioOrigen=").append(numSalarioDiarioOrigen);
        sb.append(", rpDestSub='").append(rpDestSub).append('\'');
        sb.append(", cveModalidadOrigen='").append(cveModalidadOrigen).append('\'');
        sb.append(", tpMovimientoOrigen='").append(tpMovimientoOrigen).append('\'');
        sb.append(", cvePatronDestino='").append(cvePatronDestino).append('\'');
        sb.append(", txtNombrePatronDestino='").append(txtNombrePatronDestino).append('\'');
        sb.append(", numSalarioDiarioDestino=").append(numSalarioDiarioDestino);
        sb.append(", cveModalidadDestino='").append(cveModalidadDestino).append('\'');
        sb.append(", tpMovimientoDestino='").append(tpMovimientoDestino).append('\'');
        sb.append(", fecMovimientoPatron=").append(fecMovimientoPatron);
        sb.append(", fecBajaModalidad40=").append(fecBajaModalidad40);
        sb.append(", cveCiz='").append(cveCiz).append('\'');
        sb.append(", stpCreacion=").append(stpCreacion);
        sb.append('}');
        return sb.toString();
    }
}
