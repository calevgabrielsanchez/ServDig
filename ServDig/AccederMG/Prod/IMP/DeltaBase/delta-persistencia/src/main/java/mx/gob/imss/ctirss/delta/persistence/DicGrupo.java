package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_GRUPO database table.
 * 
 */
@Entity
@Table(name="DIC_GRUPO")
public class DicGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_GRUPO", nullable=false, precision=22)
	private long cveIdGrupo;

	@Column(name="DES_GRUPO", length=255)
	private String desGrupo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_GRUPO", length=50)
	private String numGrupo;

	//bi-directional many-to-one association to DicFraccion
	@OneToMany(mappedBy="dicGrupo")
	private List<DicFraccion> dicFraccions;

	//bi-directional many-to-one association to DicDivision
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DIVISION")
	private DicDivision dicDivision;

    public DicGrupo() {
    }

	public long getCveIdGrupo() {
		return this.cveIdGrupo;
	}

	public void setCveIdGrupo(long cveIdGrupo) {
		this.cveIdGrupo = cveIdGrupo;
	}

	public String getDesGrupo() {
		return this.desGrupo;
	}

	public void setDesGrupo(String desGrupo) {
		this.desGrupo = desGrupo;
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

	public String getNumGrupo() {
		return this.numGrupo;
	}

	public void setNumGrupo(String numGrupo) {
		this.numGrupo = numGrupo;
	}

	public List<DicFraccion> getDicFraccions() {
		return this.dicFraccions;
	}

	public void setDicFraccions(List<DicFraccion> dicFraccions) {
		this.dicFraccions = dicFraccions;
	}
	
	public DicDivision getDicDivision() {
		return this.dicDivision;
	}

	public void setDicDivision(DicDivision dicDivision) {
		this.dicDivision = dicDivision;
	}
	
}