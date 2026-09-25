package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDT_PATRON_CPA database table.
 * 
 */
@Entity
@Table(name="FDT_PATRON_CPA")
public class FdtPatronCpa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_RELACION", nullable=false, precision=22)
	private long idRelacion;

	@Column(name="ID_SUSTITUCION", precision=22)
	private BigDecimal idSustitucion;

	@Column(name="TX_STATUS_CPA", length=1)
	private String txStatusCpa;

	//bi-directional many-to-one association to FdiSancion
	@OneToMany(mappedBy="fdtPatronCpa")
	private List<FdiSancion> fdiSancions;

	//bi-directional many-to-one association to FdtPatron
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false)
		})
	private FdtPatron fdtPatron;

	//bi-directional many-to-one association to FdiCpa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CV_CURP", nullable=false)
	private FdiCpa fdiCpa;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO")
	private FdtAviso fdtAviso;

    public FdtPatronCpa() {
    }

	public long getIdRelacion() {
		return this.idRelacion;
	}

	public void setIdRelacion(long idRelacion) {
		this.idRelacion = idRelacion;
	}

	public BigDecimal getIdSustitucion() {
		return this.idSustitucion;
	}

	public void setIdSustitucion(BigDecimal idSustitucion) {
		this.idSustitucion = idSustitucion;
	}

	public String getTxStatusCpa() {
		return this.txStatusCpa;
	}

	public void setTxStatusCpa(String txStatusCpa) {
		this.txStatusCpa = txStatusCpa;
	}

	public List<FdiSancion> getFdiSancions() {
		return this.fdiSancions;
	}

	public void setFdiSancions(List<FdiSancion> fdiSancions) {
		this.fdiSancions = fdiSancions;
	}
	
	public FdtPatron getFdtPatron() {
		return this.fdtPatron;
	}

	public void setFdtPatron(FdtPatron fdtPatron) {
		this.fdtPatron = fdtPatron;
	}
	
	public FdiCpa getFdiCpa() {
		return this.fdiCpa;
	}

	public void setFdiCpa(FdiCpa fdiCpa) {
		this.fdiCpa = fdiCpa;
	}
	
	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
}