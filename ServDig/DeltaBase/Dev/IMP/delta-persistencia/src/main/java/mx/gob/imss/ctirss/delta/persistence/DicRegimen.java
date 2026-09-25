package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_REGIMEN database table.
 * 
 */
@Entity
@Table(name="DIC_REGIMEN")
public class DicRegimen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_REGIMEN", nullable=false, precision=22)
	private long cveIdRegimen;

	@Column(name="DES_REGIMEN", length=50)
	private String desRegimen;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicModalidad
	@OneToMany(mappedBy="dicRegimen")
	private List<DicModalidad> dicModalidads;

    public DicRegimen() {
    }

	public long getCveIdRegimen() {
		return this.cveIdRegimen;
	}

	public void setCveIdRegimen(long cveIdRegimen) {
		this.cveIdRegimen = cveIdRegimen;
	}

	public String getDesRegimen() {
		return this.desRegimen;
	}

	public void setDesRegimen(String desRegimen) {
		this.desRegimen = desRegimen;
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

	public List<DicModalidad> getDicModalidads() {
		return this.dicModalidads;
	}

	public void setDicModalidads(List<DicModalidad> dicModalidads) {
		this.dicModalidads = dicModalidads;
	}
	
}