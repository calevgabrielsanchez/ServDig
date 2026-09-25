package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_MANDATO database table.
 * 
 */
@Entity
@Table(name="DIC_MANDATO")
public class DicMandato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MANDATO", nullable=false, precision=22)
	private long cveIdMandato;

	@Column(name="DES_MANDATO", length=255)
	private String desMandato;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitRepresentanteLegal
	@OneToMany(mappedBy="dicMandato")
	private List<DitRepresentanteLegal> ditRepresentanteLegals;

    public DicMandato() {
    }

	public long getCveIdMandato() {
		return this.cveIdMandato;
	}

	public void setCveIdMandato(long cveIdMandato) {
		this.cveIdMandato = cveIdMandato;
	}

	public String getDesMandato() {
		return this.desMandato;
	}

	public void setDesMandato(String desMandato) {
		this.desMandato = desMandato;
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

	public List<DitRepresentanteLegal> getDitRepresentanteLegals() {
		return this.ditRepresentanteLegals;
	}

	public void setDitRepresentanteLegals(List<DitRepresentanteLegal> ditRepresentanteLegals) {
		this.ditRepresentanteLegals = ditRepresentanteLegals;
	}
	
}