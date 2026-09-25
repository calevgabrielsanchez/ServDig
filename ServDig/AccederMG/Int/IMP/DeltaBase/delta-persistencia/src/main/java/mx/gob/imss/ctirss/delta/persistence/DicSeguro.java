package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_SEGURO database table.
 * 
 */
@Entity
@Table(name="DIC_SEGURO")
@NamedQuery(name="DicSeguro.findAll", query="SELECT d FROM DicSeguro d")
public class DicSeguro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_SEGURO")
	private long cveIdSeguro;

	@Column(name="DES_SEGURO")
	private String desSeguro;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicRama
	@OneToMany(mappedBy="dicSeguro")
	private List<DicRama> dicRamas;

	public DicSeguro() {
	}

	public long getCveIdSeguro() {
		return this.cveIdSeguro;
	}

	public void setCveIdSeguro(long cveIdSeguro) {
		this.cveIdSeguro = cveIdSeguro;
	}

	public String getDesSeguro() {
		return this.desSeguro;
	}

	public void setDesSeguro(String desSeguro) {
		this.desSeguro = desSeguro;
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

	public List<DicRama> getDicRamas() {
		return this.dicRamas;
	}

	public void setDicRamas(List<DicRama> dicRamas) {
		this.dicRamas = dicRamas;
	}

	public DicRama addDicRama(DicRama dicRama) {
		getDicRamas().add(dicRama);
		dicRama.setDicSeguro(this);

		return dicRama;
	}

	public DicRama removeDicRama(DicRama dicRama) {
		getDicRamas().remove(dicRama);
		dicRama.setDicSeguro(null);

		return dicRama;
	}

}