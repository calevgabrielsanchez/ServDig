package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_LEY_SEGURO database table.
 * 
 */
@Entity
@Table(name="DIT_LEY_SEGURO")
public class DitLeySeguro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_LEY_SEGURO", nullable=false, precision=22)
	private long cveIdLeySeguro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicTipoSeguro
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_SEGURO")
	private DicTipoSeguro dicTipoSeguro;

	//bi-directional many-to-one association to DicLey
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_LEY")
	private DicLey dicLey;

	//bi-directional many-to-one association to DitModalidadLeySeguro
	@OneToMany(mappedBy="ditLeySeguro")
	private List<DitModalidadLeySeguro> ditModalidadLeySeguros;

    public DitLeySeguro() {
    }

	public long getCveIdLeySeguro() {
		return this.cveIdLeySeguro;
	}

	public void setCveIdLeySeguro(long cveIdLeySeguro) {
		this.cveIdLeySeguro = cveIdLeySeguro;
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

	public DicTipoSeguro getDicTipoSeguro() {
		return this.dicTipoSeguro;
	}

	public void setDicTipoSeguro(DicTipoSeguro dicTipoSeguro) {
		this.dicTipoSeguro = dicTipoSeguro;
	}
	
	public DicLey getDicLey() {
		return this.dicLey;
	}

	public void setDicLey(DicLey dicLey) {
		this.dicLey = dicLey;
	}
	
	public List<DitModalidadLeySeguro> getDitModalidadLeySeguros() {
		return this.ditModalidadLeySeguros;
	}

	public void setDitModalidadLeySeguros(List<DitModalidadLeySeguro> ditModalidadLeySeguros) {
		this.ditModalidadLeySeguros = ditModalidadLeySeguros;
	}
	
}