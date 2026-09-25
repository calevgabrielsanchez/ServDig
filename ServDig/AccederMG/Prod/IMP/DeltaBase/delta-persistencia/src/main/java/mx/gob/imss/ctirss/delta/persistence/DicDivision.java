package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_DIVISION database table.
 * 
 */
@Entity
@Table(name="DIC_DIVISION")
public class DicDivision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_DIVISION", nullable=false, precision=22)
	private long cveIdDivision;

	@Column(name="DES_DIVISION", length=255)
	private String desDivision;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_DIVISION", length=50)
	private String numDivision;

	//bi-directional many-to-one association to DicGrupo
	@OneToMany(mappedBy="dicDivision")
	private List<DicGrupo> dicGrupos;

    public DicDivision() {
    }

	public long getCveIdDivision() {
		return this.cveIdDivision;
	}

	public void setCveIdDivision(long cveIdDivision) {
		this.cveIdDivision = cveIdDivision;
	}

	public String getDesDivision() {
		return this.desDivision;
	}

	public void setDesDivision(String desDivision) {
		this.desDivision = desDivision;
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

	public String getNumDivision() {
		return this.numDivision;
	}

	public void setNumDivision(String numDivision) {
		this.numDivision = numDivision;
	}

	public List<DicGrupo> getDicGrupos() {
		return this.dicGrupos;
	}

	public void setDicGrupos(List<DicGrupo> dicGrupos) {
		this.dicGrupos = dicGrupos;
	}
	
}