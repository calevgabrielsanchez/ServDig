package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FDI_DICTAMEN_EXISTENTE database table.
 * 
 */
@Entity
@Table(name="FDI_DICTAMEN_EXISTENTE")
public class FdiDictamenExistente implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdiDictamenExistentePK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_A", nullable=false)
	private Date fhPeriodoA;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_DE", nullable=false)
	private Date fhPeriodoDe;

	@Column(name="NU_REG_CP", nullable=false, length=16)
	private String nuRegCp;

	@Column(name="REG_PATRON", nullable=false, length=10)
	private String regPatron;

	//bi-directional many-to-one association to FdtSubdeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_SDELEG_ORIG", referencedColumnName="SDELEG_ORIG", nullable=false, insertable=false, updatable=false)
		})
	private FdtSubdeleg fdtSubdeleg;

    public FdiDictamenExistente() {
    }

	public FdiDictamenExistentePK getId() {
		return this.id;
	}

	public void setId(FdiDictamenExistentePK id) {
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

	public String getNuRegCp() {
		return this.nuRegCp;
	}

	public void setNuRegCp(String nuRegCp) {
		this.nuRegCp = nuRegCp;
	}

	public String getRegPatron() {
		return this.regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public FdtSubdeleg getFdtSubdeleg() {
		return this.fdtSubdeleg;
	}

	public void setFdtSubdeleg(FdtSubdeleg fdtSubdeleg) {
		this.fdtSubdeleg = fdtSubdeleg;
	}
	
}