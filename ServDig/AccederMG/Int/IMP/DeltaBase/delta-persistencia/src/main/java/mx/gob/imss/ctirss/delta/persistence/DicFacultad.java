package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_FACULTAD database table.
 * 
 */
@Entity
@Table(name="DIC_FACULTAD")
public class DicFacultad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_FACULTAD", nullable=false, precision=22)
	private long cveIdFacultad;

	@Column(name="DES_FACULTAD", length=255)
	private String desFacultad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicTipoFacultad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_FACULTAD")
	private DicTipoFacultad dicTipoFacultad;

	//bi-directional many-to-one association to DitRlFacultad
	@OneToMany(mappedBy="dicFacultad")
	private List<DitRlFacultad> ditRlFacultads;

    public DicFacultad() {
    }

	public long getCveIdFacultad() {
		return this.cveIdFacultad;
	}

	public void setCveIdFacultad(long cveIdFacultad) {
		this.cveIdFacultad = cveIdFacultad;
	}

	public String getDesFacultad() {
		return this.desFacultad;
	}

	public void setDesFacultad(String desFacultad) {
		this.desFacultad = desFacultad;
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

	public DicTipoFacultad getDicTipoFacultad() {
		return this.dicTipoFacultad;
	}

	public void setDicTipoFacultad(DicTipoFacultad dicTipoFacultad) {
		this.dicTipoFacultad = dicTipoFacultad;
	}
	
	public List<DitRlFacultad> getDitRlFacultads() {
		return this.ditRlFacultads;
	}

	public void setDitRlFacultads(List<DitRlFacultad> ditRlFacultads) {
		this.ditRlFacultads = ditRlFacultads;
	}
	
}