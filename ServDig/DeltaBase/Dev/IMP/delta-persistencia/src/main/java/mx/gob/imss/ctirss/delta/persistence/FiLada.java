package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FI_LADAS database table.
 * 
 */
@Entity
@Table(name="FI_LADAS")
public class FiLada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CV_LADA", nullable=false, precision=22)
	private long cvLada;

	@Column(name="ENT_FED", nullable=false, precision=2)
	private BigDecimal entFed;

	//bi-directional many-to-one association to FdiCpa
	@OneToMany(mappedBy="fiLada")
	private List<FdiCpa> fdiCpas;

    public FiLada() {
    }

	public long getCvLada() {
		return this.cvLada;
	}

	public void setCvLada(long cvLada) {
		this.cvLada = cvLada;
	}

	public BigDecimal getEntFed() {
		return this.entFed;
	}

	public void setEntFed(BigDecimal entFed) {
		this.entFed = entFed;
	}

	public List<FdiCpa> getFdiCpas() {
		return this.fdiCpas;
	}

	public void setFdiCpas(List<FdiCpa> fdiCpas) {
		this.fdiCpas = fdiCpas;
	}
	
}