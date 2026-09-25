package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the FDI_CPA_HISTORICO database table.
 * 
 */
@Entity
@Table(name="FDI_CPA_HISTORICO")
public class FdiCpaHistorico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NU_CONSECUTIVO", nullable=false, precision=22)
	private long nuConsecutivo;

	@Column(name="NU_REG_CP", precision=9)
	private BigDecimal nuRegCp;

	//bi-directional many-to-one association to FdiCpa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CV_CURP", nullable=false)
	private FdiCpa fdiCpa;

    public FdiCpaHistorico() {
    }

	public long getNuConsecutivo() {
		return this.nuConsecutivo;
	}

	public void setNuConsecutivo(long nuConsecutivo) {
		this.nuConsecutivo = nuConsecutivo;
	}

	public BigDecimal getNuRegCp() {
		return this.nuRegCp;
	}

	public void setNuRegCp(BigDecimal nuRegCp) {
		this.nuRegCp = nuRegCp;
	}

	public FdiCpa getFdiCpa() {
		return this.fdiCpa;
	}

	public void setFdiCpa(FdiCpa fdiCpa) {
		this.fdiCpa = fdiCpa;
	}
	
}