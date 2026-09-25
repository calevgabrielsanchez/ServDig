package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CICLO database table.
 * 
 */
@Entity
@Table(name="DIC_CICLO")
public class DicCiclo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CICLO", nullable=false, precision=22)
	private long cveIdCiclo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_CICLO", precision=22)
	private BigDecimal numCiclo;

	//bi-directional many-to-one association to DicRangoCuotaSsf
	@OneToMany(mappedBy="dicCiclo")
	private List<DicRangoCuotaSsf> dicRangoCuotaSsfs;

	//bi-directional many-to-one association to DitSalarioGeneral
	@OneToMany(mappedBy="dicCiclo")
	private List<DitSalarioGeneral> ditSalarioGenerals;

    public DicCiclo() {
    }

	public long getCveIdCiclo() {
		return this.cveIdCiclo;
	}

	public void setCveIdCiclo(long cveIdCiclo) {
		this.cveIdCiclo = cveIdCiclo;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public BigDecimal getNumCiclo() {
		return this.numCiclo;
	}

	public void setNumCiclo(BigDecimal numCiclo) {
		this.numCiclo = numCiclo;
	}

	public List<DicRangoCuotaSsf> getDicRangoCuotaSsfs() {
		return this.dicRangoCuotaSsfs;
	}

	public void setDicRangoCuotaSsfs(List<DicRangoCuotaSsf> dicRangoCuotaSsfs) {
		this.dicRangoCuotaSsfs = dicRangoCuotaSsfs;
	}
	
	public List<DitSalarioGeneral> getDitSalarioGenerals() {
		return this.ditSalarioGenerals;
	}

	public void setDitSalarioGenerals(List<DitSalarioGeneral> ditSalarioGenerals) {
		this.ditSalarioGenerals = ditSalarioGenerals;
	}
	
}