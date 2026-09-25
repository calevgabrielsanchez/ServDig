package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_TRABAJADOR database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_TRABAJADOR")
public class DicTipoTrabajador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_TRABAJADOR", nullable=false, precision=22)
	private long cveIdTipoTrabajador;

	@Column(name="DES_TIPO_TRABAJADOR", length=100)
	private String desTipoTrabajador;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitTipoTrabModalidad
	@OneToMany(mappedBy="dicTipoTrabajador")
	private List<DitTipoTrabModalidad> ditTipoTrabModalidads;

    public DicTipoTrabajador() {
    }

	public long getCveIdTipoTrabajador() {
		return this.cveIdTipoTrabajador;
	}

	public void setCveIdTipoTrabajador(long cveIdTipoTrabajador) {
		this.cveIdTipoTrabajador = cveIdTipoTrabajador;
	}

	public String getDesTipoTrabajador() {
		return this.desTipoTrabajador;
	}

	public void setDesTipoTrabajador(String desTipoTrabajador) {
		this.desTipoTrabajador = desTipoTrabajador;
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

	public List<DitTipoTrabModalidad> getDitTipoTrabModalidads() {
		return this.ditTipoTrabModalidads;
	}

	public void setDitTipoTrabModalidads(List<DitTipoTrabModalidad> ditTipoTrabModalidads) {
		this.ditTipoTrabModalidads = ditTipoTrabModalidads;
	}
	
}