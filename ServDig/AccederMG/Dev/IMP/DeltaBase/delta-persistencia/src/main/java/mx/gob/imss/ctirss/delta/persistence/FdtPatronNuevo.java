package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_PATRON_NUEVO database table.
 * 
 */
@Entity
@Table(name="FDT_PATRON_NUEVO")
public class FdtPatronNuevo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtPatronNuevoPK id;

	@Column(name="CVE_MODAL_ANT", precision=2)
	private BigDecimal cveModalAnt;

	@Column(name="REG_PATRON_ANT", length=8)
	private String regPatronAnt;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO", nullable=false, insertable=false, updatable=false)
	private FdtAviso fdtAviso;

	//bi-directional many-to-one association to FdtPatron
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtPatron fdtPatron;

    public FdtPatronNuevo() {
    }

	public FdtPatronNuevoPK getId() {
		return this.id;
	}

	public void setId(FdtPatronNuevoPK id) {
		this.id = id;
	}
	
	public BigDecimal getCveModalAnt() {
		return this.cveModalAnt;
	}

	public void setCveModalAnt(BigDecimal cveModalAnt) {
		this.cveModalAnt = cveModalAnt;
	}

	public String getRegPatronAnt() {
		return this.regPatronAnt;
	}

	public void setRegPatronAnt(String regPatronAnt) {
		this.regPatronAnt = regPatronAnt;
	}

	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
	public FdtPatron getFdtPatron() {
		return this.fdtPatron;
	}

	public void setFdtPatron(FdtPatron fdtPatron) {
		this.fdtPatron = fdtPatron;
	}
	
}