package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_RAMA database table.
 * 
 */
@Entity
@Table(name="DIC_RAMA")
@NamedQuery(name="DicRama.findAll", query="SELECT d FROM DicRama d")
public class DicRama implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_RAMA")
	private long cveIdRama;

	@Column(name="DES_RAMA")
	private String desRama;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicFactorModalidadRama
	@OneToMany(mappedBy="dicRama")
	private List<DicFactorModalidadRama> dicFactorModalidadRamas;

	//bi-directional many-to-one association to DicSeguro
	@ManyToOne
	@JoinColumn(name="CVE_ID_SEGURO")
	private DicSeguro dicSeguro;

	public DicRama() {
	}

	public long getCveIdRama() {
		return this.cveIdRama;
	}

	public void setCveIdRama(long cveIdRama) {
		this.cveIdRama = cveIdRama;
	}

	public String getDesRama() {
		return this.desRama;
	}

	public void setDesRama(String desRama) {
		this.desRama = desRama;
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

	public List<DicFactorModalidadRama> getDicFactorModalidadRamas() {
		return this.dicFactorModalidadRamas;
	}

	public void setDicFactorModalidadRamas(List<DicFactorModalidadRama> dicFactorModalidadRamas) {
		this.dicFactorModalidadRamas = dicFactorModalidadRamas;
	}

	public DicFactorModalidadRama addDicFactorModalidadRama(DicFactorModalidadRama dicFactorModalidadRama) {
		getDicFactorModalidadRamas().add(dicFactorModalidadRama);
		dicFactorModalidadRama.setDicRama(this);

		return dicFactorModalidadRama;
	}

	public DicFactorModalidadRama removeDicFactorModalidadRama(DicFactorModalidadRama dicFactorModalidadRama) {
		getDicFactorModalidadRamas().remove(dicFactorModalidadRama);
		dicFactorModalidadRama.setDicRama(null);

		return dicFactorModalidadRama;
	}

	public DicSeguro getDicSeguro() {
		return this.dicSeguro;
	}

	public void setDicSeguro(DicSeguro dicSeguro) {
		this.dicSeguro = dicSeguro;
	}

}