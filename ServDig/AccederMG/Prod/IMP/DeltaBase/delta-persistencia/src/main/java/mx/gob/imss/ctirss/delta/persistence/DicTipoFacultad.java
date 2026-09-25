package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_FACULTAD database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_FACULTAD")
public class DicTipoFacultad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_FACULTAD", nullable=false, precision=22)
	private long cveIdTipoFacultad;

	@Column(name="DES_TIPO_FACULTAD", length=255)
	private String desTipoFacultad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicFacultad
	@OneToMany(mappedBy="dicTipoFacultad")
	private List<DicFacultad> dicFacultads;

    public DicTipoFacultad() {
    }

	public long getCveIdTipoFacultad() {
		return this.cveIdTipoFacultad;
	}

	public void setCveIdTipoFacultad(long cveIdTipoFacultad) {
		this.cveIdTipoFacultad = cveIdTipoFacultad;
	}

	public String getDesTipoFacultad() {
		return this.desTipoFacultad;
	}

	public void setDesTipoFacultad(String desTipoFacultad) {
		this.desTipoFacultad = desTipoFacultad;
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

	public List<DicFacultad> getDicFacultads() {
		return this.dicFacultads;
	}

	public void setDicFacultads(List<DicFacultad> dicFacultads) {
		this.dicFacultads = dicFacultads;
	}
	
}