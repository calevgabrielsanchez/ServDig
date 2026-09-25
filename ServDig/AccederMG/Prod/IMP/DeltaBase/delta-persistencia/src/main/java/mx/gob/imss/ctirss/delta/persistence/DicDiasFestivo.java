package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;



/**
 * The persistent class for the DIC_DIAS_FESTIVOS database table.
 * 
 */
@Entity
@Table(name="DIC_DIAS_FESTIVOS")
public class DicDiasFestivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_DIA_FESTIVO", nullable=false)
	private Date fecDiaFestivo;

	@Column(name="NUM_DIA_SEMANA", precision=1)
	private BigDecimal numDiaSemana;

    public DicDiasFestivo() {
    }

	public Date getFecDiaFestivo() {
		return this.fecDiaFestivo;
	}

	public void setFecDiaFestivo(Date fecDiaFestivo) {
		this.fecDiaFestivo = fecDiaFestivo;
	}

	public BigDecimal getNumDiaSemana() {
		return this.numDiaSemana;
	}

	public void setNumDiaSemana(BigDecimal numDiaSemana) {
		this.numDiaSemana = numDiaSemana;
	}

}