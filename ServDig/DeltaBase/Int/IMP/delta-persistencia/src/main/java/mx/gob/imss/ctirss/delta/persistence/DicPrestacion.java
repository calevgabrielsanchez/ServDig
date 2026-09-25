package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_PRESTACION database table.
 * 
 */
@Entity
@Table(name="DIC_PRESTACION")
public class DicPrestacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PRESTACION", nullable=false, precision=22)
	private long cveIdPrestacion;

	@Column(name="DES_PRESTACION", length=50)
	private String desPrestacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitModalidadSeguroPrest
	@OneToMany(mappedBy="dicPrestacion")
	private List<DitModalidadSeguroPrest> ditModalidadSeguroPrests;

    public DicPrestacion() {
    }

	public long getCveIdPrestacion() {
		return this.cveIdPrestacion;
	}

	public void setCveIdPrestacion(long cveIdPrestacion) {
		this.cveIdPrestacion = cveIdPrestacion;
	}

	public String getDesPrestacion() {
		return this.desPrestacion;
	}

	public void setDesPrestacion(String desPrestacion) {
		this.desPrestacion = desPrestacion;
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

	public List<DitModalidadSeguroPrest> getDitModalidadSeguroPrests() {
		return this.ditModalidadSeguroPrests;
	}

	public void setDitModalidadSeguroPrests(List<DitModalidadSeguroPrest> ditModalidadSeguroPrests) {
		this.ditModalidadSeguroPrests = ditModalidadSeguroPrests;
	}
	
}