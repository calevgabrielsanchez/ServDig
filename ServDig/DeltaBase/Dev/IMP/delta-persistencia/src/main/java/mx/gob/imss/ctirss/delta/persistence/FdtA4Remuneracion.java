package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A4_REMUNERACION database table.
 * 
 */
@Entity
@Table(name="FDT_A4_REMUNERACION")
public class FdtA4Remuneracion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA4RemuneracionPK id;

	@Column(name="IM_REMUNERACION", nullable=false, precision=12, scale=2)
	private BigDecimal imRemuneracion;

	@Column(name="IN_INTEGRA_SALARIO", length=1)
	private String inIntegraSalario;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

	//bi-directional many-to-one association to FdcRemuneracione
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_REMUNERACION", nullable=false, insertable=false, updatable=false)
	private FdcRemuneracione fdcRemuneracione;

    public FdtA4Remuneracion() {
    }

	public FdtA4RemuneracionPK getId() {
		return this.id;
	}

	public void setId(FdtA4RemuneracionPK id) {
		this.id = id;
	}
	
	public BigDecimal getImRemuneracion() {
		return this.imRemuneracion;
	}

	public void setImRemuneracion(BigDecimal imRemuneracion) {
		this.imRemuneracion = imRemuneracion;
	}

	public String getInIntegraSalario() {
		return this.inIntegraSalario;
	}

	public void setInIntegraSalario(String inIntegraSalario) {
		this.inIntegraSalario = inIntegraSalario;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
	public FdcRemuneracione getFdcRemuneracione() {
		return this.fdcRemuneracione;
	}

	public void setFdcRemuneracione(FdcRemuneracione fdcRemuneracione) {
		this.fdcRemuneracione = fdcRemuneracione;
	}
	
}