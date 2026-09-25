package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_A1_CLASIFICACION database table.
 * 
 */
@Entity
@Table(name="FDT_A1_CLASIFICACION")
public class FdtA1Clasificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA1ClasificacionPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_FINAL")
	private Date fhFinal;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_INIACTIVIDAD")
	private Date fhIniactividad;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_INICIAL")
	private Date fhInicial;

	@Column(name="NU_PRIMA", precision=7, scale=5)
	private BigDecimal nuPrima;

	@Column(name="TX_CLASE", length=3)
	private String txClase;

	@Column(name="TX_FRACCION", length=4)
	private String txFraccion;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA1Clasificacion() {
    }

	public FdtA1ClasificacionPK getId() {
		return this.id;
	}

	public void setId(FdtA1ClasificacionPK id) {
		this.id = id;
	}
	
	public Date getFhFinal() {
		return this.fhFinal;
	}

	public void setFhFinal(Date fhFinal) {
		this.fhFinal = fhFinal;
	}

	public Date getFhIniactividad() {
		return this.fhIniactividad;
	}

	public void setFhIniactividad(Date fhIniactividad) {
		this.fhIniactividad = fhIniactividad;
	}

	public Date getFhInicial() {
		return this.fhInicial;
	}

	public void setFhInicial(Date fhInicial) {
		this.fhInicial = fhInicial;
	}

	public BigDecimal getNuPrima() {
		return this.nuPrima;
	}

	public void setNuPrima(BigDecimal nuPrima) {
		this.nuPrima = nuPrima;
	}

	public String getTxClase() {
		return this.txClase;
	}

	public void setTxClase(String txClase) {
		this.txClase = txClase;
	}

	public String getTxFraccion() {
		return this.txFraccion;
	}

	public void setTxFraccion(String txFraccion) {
		this.txFraccion = txFraccion;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}