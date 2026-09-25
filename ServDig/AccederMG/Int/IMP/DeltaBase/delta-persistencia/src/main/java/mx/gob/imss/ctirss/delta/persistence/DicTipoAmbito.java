package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_AMBITO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_AMBITO")
public class DicTipoAmbito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_AMBITO", nullable=false, precision=22)
	private long cveIdTipoAmbito;

	@Column(name="DES_TIPO_AMBITO", length=255)
	private String desTipoAmbito;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicMunicipioImss
	@OneToMany(mappedBy="dicTipoAmbito")
	private List<DicMunicipioImss> dicMunicipioImsses;

    public DicTipoAmbito() {
    }

	public long getCveIdTipoAmbito() {
		return this.cveIdTipoAmbito;
	}

	public void setCveIdTipoAmbito(long cveIdTipoAmbito) {
		this.cveIdTipoAmbito = cveIdTipoAmbito;
	}

	public String getDesTipoAmbito() {
		return this.desTipoAmbito;
	}

	public void setDesTipoAmbito(String desTipoAmbito) {
		this.desTipoAmbito = desTipoAmbito;
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

	public List<DicMunicipioImss> getDicMunicipioImsses() {
		return this.dicMunicipioImsses;
	}

	public void setDicMunicipioImsses(List<DicMunicipioImss> dicMunicipioImsses) {
		this.dicMunicipioImsses = dicMunicipioImsses;
	}
	
}