package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_PARENTESCO database table.
 * 
 */
@Entity
@Table(name="DIC_PARENTESCO")
public class DicParentesco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PARENTESCO", nullable=false, precision=22)
	private long cveIdParentesco;

	@Column(name="DES_TIPO_PARENTESCO", length=50)
	private String desTipoParentesco;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicGradoParentesco
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_GRADO_PARENTESCO")
	private DicGradoParentesco dicGradoParentesco;

	//bi-directional many-to-one association to DicTipoParentesco
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_PARENTESCO")
	private DicTipoParentesco dicTipoParentesco;

	//bi-directional many-to-one association to DitAsigNssParentesco
	@OneToMany(mappedBy="dicParentesco")
	private List<DitAsigNssParentesco> ditAsigNssParentescos;

    public DicParentesco() {
    }

	public long getCveIdParentesco() {
		return this.cveIdParentesco;
	}

	public void setCveIdParentesco(long cveIdParentesco) {
		this.cveIdParentesco = cveIdParentesco;
	}

	public String getDesTipoParentesco() {
		return this.desTipoParentesco;
	}

	public void setDesTipoParentesco(String desTipoParentesco) {
		this.desTipoParentesco = desTipoParentesco;
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

	public DicGradoParentesco getDicGradoParentesco() {
		return this.dicGradoParentesco;
	}

	public void setDicGradoParentesco(DicGradoParentesco dicGradoParentesco) {
		this.dicGradoParentesco = dicGradoParentesco;
	}
	
	public DicTipoParentesco getDicTipoParentesco() {
		return this.dicTipoParentesco;
	}

	public void setDicTipoParentesco(DicTipoParentesco dicTipoParentesco) {
		this.dicTipoParentesco = dicTipoParentesco;
	}
	
	public List<DitAsigNssParentesco> getDitAsigNssParentescos() {
		return this.ditAsigNssParentescos;
	}

	public void setDitAsigNssParentescos(List<DitAsigNssParentesco> ditAsigNssParentescos) {
		this.ditAsigNssParentescos = ditAsigNssParentescos;
	}
	
}