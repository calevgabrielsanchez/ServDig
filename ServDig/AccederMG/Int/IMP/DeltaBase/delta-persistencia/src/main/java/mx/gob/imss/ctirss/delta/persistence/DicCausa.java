package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CAUSA database table.
 * 
 */
@Entity
@Table(name="DIC_CAUSA")
public class DicCausa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CAUSA", nullable=false, precision=22)
	private long cveIdCausa;

	@Column(name="DES_CAUSA", length=255)
	private String desCausa;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitMovtoPatSujOblig
	@OneToMany(mappedBy="dicCausa")
	private List<DitMovtoPatSujOblig> ditMovtoPatSujObligs;

    public DicCausa() {
    }

	public long getCveIdCausa() {
		return this.cveIdCausa;
	}

	public void setCveIdCausa(long cveIdCausa) {
		this.cveIdCausa = cveIdCausa;
	}

	public String getDesCausa() {
		return this.desCausa;
	}

	public void setDesCausa(String desCausa) {
		this.desCausa = desCausa;
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

	public List<DitMovtoPatSujOblig> getDitMovtoPatSujObligs() {
		return this.ditMovtoPatSujObligs;
	}

	public void setDitMovtoPatSujObligs(List<DitMovtoPatSujOblig> ditMovtoPatSujObligs) {
		this.ditMovtoPatSujObligs = ditMovtoPatSujObligs;
	}
	
}