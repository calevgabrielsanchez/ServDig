package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CAUSA_BAJA_ASEG database table.
 * 
 */
@Entity
@Table(name="DIC_CAUSA_BAJA_ASEG")
public class DicCausaBajaAseg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CAUSA_BAJA_ASEG", nullable=false, precision=22)
	private long cveIdCausaBajaAseg;

	@Column(name="DES_CAUSA_BAJA", length=100)
	private String desCausaBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitMovasegAjusteCierre
	@OneToMany(mappedBy="dicCausaBajaAseg")
	private List<DitMovasegAjusteCierre> ditMovasegAjusteCierres;

	//bi-directional many-to-one association to DitMovtoAsegCierre
	@OneToMany(mappedBy="dicCausaBajaAseg")
	private List<DitMovtoAsegCierre> ditMovtoAsegCierres;

    public DicCausaBajaAseg() {
    }

	public long getCveIdCausaBajaAseg() {
		return this.cveIdCausaBajaAseg;
	}

	public void setCveIdCausaBajaAseg(long cveIdCausaBajaAseg) {
		this.cveIdCausaBajaAseg = cveIdCausaBajaAseg;
	}

	public String getDesCausaBaja() {
		return this.desCausaBaja;
	}

	public void setDesCausaBaja(String desCausaBaja) {
		this.desCausaBaja = desCausaBaja;
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

	public List<DitMovasegAjusteCierre> getDitMovasegAjusteCierres() {
		return this.ditMovasegAjusteCierres;
	}

	public void setDitMovasegAjusteCierres(List<DitMovasegAjusteCierre> ditMovasegAjusteCierres) {
		this.ditMovasegAjusteCierres = ditMovasegAjusteCierres;
	}
	
	public List<DitMovtoAsegCierre> getDitMovtoAsegCierres() {
		return this.ditMovtoAsegCierres;
	}

	public void setDitMovtoAsegCierres(List<DitMovtoAsegCierre> ditMovtoAsegCierres) {
		this.ditMovtoAsegCierres = ditMovtoAsegCierres;
	}
	
}