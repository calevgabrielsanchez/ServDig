package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_CONVENIO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_CONVENIO")
public class DicTipoConvenio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_CONVENIO", nullable=false, precision=22)
	private long cveIdTipoConvenio;

	@Column(name="DES_TIPO_CONVENIO", length=255)
	private String desTipoConvenio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicConvenio
	@OneToMany(mappedBy="dicTipoConvenio")
	private List<DicConvenio> dicConvenios;

    public DicTipoConvenio() {
    }

	public long getCveIdTipoConvenio() {
		return this.cveIdTipoConvenio;
	}

	public void setCveIdTipoConvenio(long cveIdTipoConvenio) {
		this.cveIdTipoConvenio = cveIdTipoConvenio;
	}

	public String getDesTipoConvenio() {
		return this.desTipoConvenio;
	}

	public void setDesTipoConvenio(String desTipoConvenio) {
		this.desTipoConvenio = desTipoConvenio;
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

	public List<DicConvenio> getDicConvenios() {
		return this.dicConvenios;
	}

	public void setDicConvenios(List<DicConvenio> dicConvenios) {
		this.dicConvenios = dicConvenios;
	}
	
}