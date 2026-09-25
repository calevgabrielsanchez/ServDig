package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_MODALIDAD_LEY_SEGURO database table.
 * 
 */
@Entity
@Table(name="DIT_MODALIDAD_LEY_SEGURO")
public class DitModalidadLeySeguro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MODALIDAD_SEGURO", nullable=false, precision=22)
	private long cveIdModalidadSeguro;

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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	//bi-directional many-to-one association to DitLeySeguro
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_LEY_SEGURO")
	private DitLeySeguro ditLeySeguro;

	//bi-directional many-to-one association to DitModalidadSeguroPrest
	@OneToMany(mappedBy="ditModalidadLeySeguro")
	private List<DitModalidadSeguroPrest> ditModalidadSeguroPrests;

    public DitModalidadLeySeguro() {
    }

	public long getCveIdModalidadSeguro() {
		return this.cveIdModalidadSeguro;
	}

	public void setCveIdModalidadSeguro(long cveIdModalidadSeguro) {
		this.cveIdModalidadSeguro = cveIdModalidadSeguro;
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

	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}
	
	public DitLeySeguro getDitLeySeguro() {
		return this.ditLeySeguro;
	}

	public void setDitLeySeguro(DitLeySeguro ditLeySeguro) {
		this.ditLeySeguro = ditLeySeguro;
	}
	
	public List<DitModalidadSeguroPrest> getDitModalidadSeguroPrests() {
		return this.ditModalidadSeguroPrests;
	}

	public void setDitModalidadSeguroPrests(List<DitModalidadSeguroPrest> ditModalidadSeguroPrests) {
		this.ditModalidadSeguroPrests = ditModalidadSeguroPrests;
	}
	
}