package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_CONTRIBUCION database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_CONTRIBUCION")
public class DicTipoContribucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_CONTRIBUCION", nullable=false, precision=22)
	private long cveIdTipoContribucion;

	@Column(name="DES_TIPO_CONTRIBUCION", length=255)
	private String desTipoContribucion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitTipoContribModalidad
	@OneToMany(mappedBy="dicTipoContribucion")
	private List<DitTipoContribModalidad> ditTipoContribModalidads;

    public DicTipoContribucion() {
    }

	public long getCveIdTipoContribucion() {
		return this.cveIdTipoContribucion;
	}

	public void setCveIdTipoContribucion(long cveIdTipoContribucion) {
		this.cveIdTipoContribucion = cveIdTipoContribucion;
	}

	public String getDesTipoContribucion() {
		return this.desTipoContribucion;
	}

	public void setDesTipoContribucion(String desTipoContribucion) {
		this.desTipoContribucion = desTipoContribucion;
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

	public List<DitTipoContribModalidad> getDitTipoContribModalidads() {
		return this.ditTipoContribModalidads;
	}

	public void setDitTipoContribModalidads(List<DitTipoContribModalidad> ditTipoContribModalidads) {
		this.ditTipoContribModalidads = ditTipoContribModalidads;
	}
	
}