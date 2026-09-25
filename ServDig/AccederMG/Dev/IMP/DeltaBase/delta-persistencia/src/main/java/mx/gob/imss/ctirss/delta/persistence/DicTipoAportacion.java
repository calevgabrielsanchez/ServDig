package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_APORTACION database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_APORTACION")
@NamedQuery(name="DicTipoAportacion.findAll", query="SELECT d FROM DicTipoAportacion d")
public class DicTipoAportacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_TIPO_APORTACION")
	private long cveIdTipoAportacion;

	@Column(name="DES_TIPO_APORTACION")
	private String desTipoAportacion;

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
	@OneToMany(mappedBy="dicTipoAportacion")
	private List<DicFactorModalidadRama> dicFactorModalidadRamas;

	public DicTipoAportacion() {
	}

	public long getCveIdTipoAportacion() {
		return this.cveIdTipoAportacion;
	}

	public void setCveIdTipoAportacion(long cveIdTipoAportacion) {
		this.cveIdTipoAportacion = cveIdTipoAportacion;
	}

	public String getDesTipoAportacion() {
		return this.desTipoAportacion;
	}

	public void setDesTipoAportacion(String desTipoAportacion) {
		this.desTipoAportacion = desTipoAportacion;
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
		dicFactorModalidadRama.setDicTipoAportacion(this);

		return dicFactorModalidadRama;
	}

	public DicFactorModalidadRama removeDicFactorModalidadRama(DicFactorModalidadRama dicFactorModalidadRama) {
		getDicFactorModalidadRamas().remove(dicFactorModalidadRama);
		dicFactorModalidadRama.setDicTipoAportacion(null);

		return dicFactorModalidadRama;
	}

}