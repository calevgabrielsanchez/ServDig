package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_AREA_GEOGRAFICA database table.
 * 
 */
@Entity
@Table(name="DIC_AREA_GEOGRAFICA")
public class DicAreaGeografica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_AREA_GEOGRAFICA", nullable=false, precision=22)
	private long cveIdAreaGeografica;

	@Column(name="DES_AREA_GEOGRAFICA", length=255)
	private String desAreaGeografica;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicMunicipioImss
	@OneToMany(mappedBy="dicAreaGeografica")
	private List<DicMunicipioImss> dicMunicipioImsses;

	//bi-directional many-to-one association to DitSalarioGeneral
	@OneToMany(mappedBy="dicAreaGeografica")
	private List<DitSalarioGeneral> ditSalarioGenerals;

    public DicAreaGeografica() {
    }

	public long getCveIdAreaGeografica() {
		return this.cveIdAreaGeografica;
	}

	public void setCveIdAreaGeografica(long cveIdAreaGeografica) {
		this.cveIdAreaGeografica = cveIdAreaGeografica;
	}

	public String getDesAreaGeografica() {
		return this.desAreaGeografica;
	}

	public void setDesAreaGeografica(String desAreaGeografica) {
		this.desAreaGeografica = desAreaGeografica;
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

	public List<DicMunicipioImss> getDicMunicipioImsses() {
		return this.dicMunicipioImsses;
	}

	public void setDicMunicipioImsses(List<DicMunicipioImss> dicMunicipioImsses) {
		this.dicMunicipioImsses = dicMunicipioImsses;
	}
	
	public List<DitSalarioGeneral> getDitSalarioGenerals() {
		return this.ditSalarioGenerals;
	}

	public void setDitSalarioGenerals(List<DitSalarioGeneral> ditSalarioGenerals) {
		this.ditSalarioGenerals = ditSalarioGenerals;
	}
	
}