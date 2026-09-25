package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_ANEXO_5 database table.
 * 
 */
@Entity
@Table(name="FDT_ANEXO_5")
public class FdtAnexo5 implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtAnexo5PK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_A", nullable=false)
	private Date fhPeriodoA;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_DE", nullable=false)
	private Date fhPeriodoDe;

	@Column(name="TX_A5_ACTIVIDAD", length=250)
	private String txA5Actividad;

	@Column(name="TX_CLASE", length=5)
	private String txClase;

	@Column(name="TX_DIVISION", length=50)
	private String txDivision;

	@Column(name="TX_FRACCION", precision=22)
	private BigDecimal txFraccion;

	@Column(name="TX_GIRO", length=100)
	private String txGiro;

	@Column(name="TX_GRUPO", precision=22)
	private BigDecimal txGrupo;

	@Column(name="TX_PRIMA", precision=11, scale=5)
	private BigDecimal txPrima;

    @Lob()
	@Column(name="TX_PROCESOS_TRAB_ACTUAL")
	private byte[] txProcesosTrabActual;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

	//bi-directional many-to-one association to CfcActeconomica
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ACTECONOMICA", nullable=false, insertable=false, updatable=false)
	private CfcActeconomica cfcActeconomica;

    public FdtAnexo5() {
    }

	public FdtAnexo5PK getId() {
		return this.id;
	}

	public void setId(FdtAnexo5PK id) {
		this.id = id;
	}
	
	public Date getFhPeriodoA() {
		return this.fhPeriodoA;
	}

	public void setFhPeriodoA(Date fhPeriodoA) {
		this.fhPeriodoA = fhPeriodoA;
	}

	public Date getFhPeriodoDe() {
		return this.fhPeriodoDe;
	}

	public void setFhPeriodoDe(Date fhPeriodoDe) {
		this.fhPeriodoDe = fhPeriodoDe;
	}

	public String getTxA5Actividad() {
		return this.txA5Actividad;
	}

	public void setTxA5Actividad(String txA5Actividad) {
		this.txA5Actividad = txA5Actividad;
	}

	public String getTxClase() {
		return this.txClase;
	}

	public void setTxClase(String txClase) {
		this.txClase = txClase;
	}

	public String getTxDivision() {
		return this.txDivision;
	}

	public void setTxDivision(String txDivision) {
		this.txDivision = txDivision;
	}

	public BigDecimal getTxFraccion() {
		return this.txFraccion;
	}

	public void setTxFraccion(BigDecimal txFraccion) {
		this.txFraccion = txFraccion;
	}

	public String getTxGiro() {
		return this.txGiro;
	}

	public void setTxGiro(String txGiro) {
		this.txGiro = txGiro;
	}

	public BigDecimal getTxGrupo() {
		return this.txGrupo;
	}

	public void setTxGrupo(BigDecimal txGrupo) {
		this.txGrupo = txGrupo;
	}

	public BigDecimal getTxPrima() {
		return this.txPrima;
	}

	public void setTxPrima(BigDecimal txPrima) {
		this.txPrima = txPrima;
	}

	public byte[] getTxProcesosTrabActual() {
		return this.txProcesosTrabActual;
	}

	public void setTxProcesosTrabActual(byte[] txProcesosTrabActual) {
		this.txProcesosTrabActual = txProcesosTrabActual != null ? txProcesosTrabActual.clone() : null;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
	public CfcActeconomica getCfcActeconomica() {
		return this.cfcActeconomica;
	}

	public void setCfcActeconomica(CfcActeconomica cfcActeconomica) {
		this.cfcActeconomica = cfcActeconomica;
	}
	
}