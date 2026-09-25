package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_SALARIOS_MINIMOS database table.
 * 
 */
@Entity
@Table(name="FDT_SALARIOS_MINIMOS")
public class FdtSalariosMinimo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_SALARIO", nullable=false, precision=22)
	private long idSalario;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_INI_VIGENCIA", nullable=false)
	private Date fhIniVigencia;

	@Column(name="IM_DIA_SALARIO", nullable=false, precision=12, scale=2)
	private BigDecimal imDiaSalario;

    public FdtSalariosMinimo() {
    }

	public long getIdSalario() {
		return this.idSalario;
	}

	public void setIdSalario(long idSalario) {
		this.idSalario = idSalario;
	}

	public Date getFhIniVigencia() {
		return this.fhIniVigencia;
	}

	public void setFhIniVigencia(Date fhIniVigencia) {
		this.fhIniVigencia = fhIniVigencia;
	}

	public BigDecimal getImDiaSalario() {
		return this.imDiaSalario;
	}

	public void setImDiaSalario(BigDecimal imDiaSalario) {
		this.imDiaSalario = imDiaSalario;
	}

}