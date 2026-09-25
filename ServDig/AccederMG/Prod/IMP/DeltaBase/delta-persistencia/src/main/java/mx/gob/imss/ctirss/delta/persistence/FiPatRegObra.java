package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FI_PAT_REG_OBRA database table.
 * 
 */
@Entity
@Table(name="FI_PAT_REG_OBRA")
public class FiPatRegObra implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FiPatRegObraPK id;

	@Column(name="NU_TRABAJADORES", nullable=false, precision=4)
	private BigDecimal nuTrabajadores;

	//bi-directional many-to-one association to FdtPatron
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtPatron fdtPatron;

    public FiPatRegObra() {
    }

	public FiPatRegObraPK getId() {
		return this.id;
	}

	public void setId(FiPatRegObraPK id) {
		this.id = id;
	}
	
	public BigDecimal getNuTrabajadores() {
		return this.nuTrabajadores;
	}

	public void setNuTrabajadores(BigDecimal nuTrabajadores) {
		this.nuTrabajadores = nuTrabajadores;
	}

	public FdtPatron getFdtPatron() {
		return this.fdtPatron;
	}

	public void setFdtPatron(FdtPatron fdtPatron) {
		this.fdtPatron = fdtPatron;
	}
	
}