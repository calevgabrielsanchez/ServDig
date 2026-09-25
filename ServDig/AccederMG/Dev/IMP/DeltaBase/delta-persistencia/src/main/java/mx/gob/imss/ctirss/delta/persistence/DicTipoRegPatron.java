package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_REG_PATRON database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_REG_PATRON")
public class DicTipoRegPatron implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_REG_PATRON", nullable=false, precision=22)
	private long cveIdTipoRegPatron;

	@Column(name="DES_TIPO_REG_PATRON", length=20)
	private String desTipoRegPatron;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitPatronGeneral
	@OneToMany(mappedBy="dicTipoRegPatron")
	private List<DitPatronGeneral> ditPatronGenerals;

    public DicTipoRegPatron() {
    }

	public long getCveIdTipoRegPatron() {
		return this.cveIdTipoRegPatron;
	}

	public void setCveIdTipoRegPatron(long cveIdTipoRegPatron) {
		this.cveIdTipoRegPatron = cveIdTipoRegPatron;
	}

	public String getDesTipoRegPatron() {
		return this.desTipoRegPatron;
	}

	public void setDesTipoRegPatron(String desTipoRegPatron) {
		this.desTipoRegPatron = desTipoRegPatron;
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

	public List<DitPatronGeneral> getDitPatronGenerals() {
		return this.ditPatronGenerals;
	}

	public void setDitPatronGenerals(List<DitPatronGeneral> ditPatronGenerals) {
		this.ditPatronGenerals = ditPatronGenerals;
	}
	
}