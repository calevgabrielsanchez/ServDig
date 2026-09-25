package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FDT_DATOS_AFIL_15 database table.
 * 
 */
@Entity
@Table(name="FDT_DATOS_AFIL_15")
public class FdtDatosAfil15 implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtDatosAfil15PK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_A", nullable=false)
	private Date fhPeriodoA;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_DE", nullable=false)
	private Date fhPeriodoDe;

	@Column(name="TX_INCIDENCIA", nullable=false, length=50)
	private String txIncidencia;

	//bi-directional many-to-one association to FdtPatron
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtPatron fdtPatron;

    public FdtDatosAfil15() {
    }

	public FdtDatosAfil15PK getId() {
		return this.id;
	}

	public void setId(FdtDatosAfil15PK id) {
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

	public String getTxIncidencia() {
		return this.txIncidencia;
	}

	public void setTxIncidencia(String txIncidencia) {
		this.txIncidencia = txIncidencia;
	}

	public FdtPatron getFdtPatron() {
		return this.fdtPatron;
	}

	public void setFdtPatron(FdtPatron fdtPatron) {
		this.fdtPatron = fdtPatron;
	}
	
}