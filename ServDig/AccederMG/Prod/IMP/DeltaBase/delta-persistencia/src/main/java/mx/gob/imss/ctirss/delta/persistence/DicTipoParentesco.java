package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_PARENTESCO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_PARENTESCO")
public class DicTipoParentesco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_PARENTESCO", nullable=false, precision=22)
	private long cveIdTipoParentesco;

	@Column(name="DES_TIPO_PARENTESCO", length=20)
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

	//bi-directional many-to-one association to DicParentesco
	@OneToMany(mappedBy="dicTipoParentesco")
	private List<DicParentesco> dicParentescos;

    public DicTipoParentesco() {
    }

	public long getCveIdTipoParentesco() {
		return this.cveIdTipoParentesco;
	}

	public void setCveIdTipoParentesco(long cveIdTipoParentesco) {
		this.cveIdTipoParentesco = cveIdTipoParentesco;
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

	public List<DicParentesco> getDicParentescos() {
		return this.dicParentescos;
	}

	public void setDicParentescos(List<DicParentesco> dicParentescos) {
		this.dicParentescos = dicParentescos;
	}
	
}