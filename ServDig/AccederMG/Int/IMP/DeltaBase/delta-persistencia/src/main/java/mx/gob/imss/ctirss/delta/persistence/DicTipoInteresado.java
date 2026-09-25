package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_INTERESADO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_INTERESADO")
public class DicTipoInteresado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_INTERESADO", nullable=false, precision=22)
	private long cveIdTipoInteresado;

	@Column(name="DES_TIPO_INTERESADO", length=20)
	private String desTipoInteresado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@OneToMany(mappedBy="dicTipoInteresado")
	private List<DitPatronSujetoObligado> ditPatronSujetoObligados;

    public DicTipoInteresado() {
    }

	public long getCveIdTipoInteresado() {
		return this.cveIdTipoInteresado;
	}

	public void setCveIdTipoInteresado(long cveIdTipoInteresado) {
		this.cveIdTipoInteresado = cveIdTipoInteresado;
	}

	public String getDesTipoInteresado() {
		return this.desTipoInteresado;
	}

	public void setDesTipoInteresado(String desTipoInteresado) {
		this.desTipoInteresado = desTipoInteresado;
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

	public List<DitPatronSujetoObligado> getDitPatronSujetoObligados() {
		return this.ditPatronSujetoObligados;
	}

	public void setDitPatronSujetoObligados(List<DitPatronSujetoObligado> ditPatronSujetoObligados) {
		this.ditPatronSujetoObligados = ditPatronSujetoObligados;
	}
	
}