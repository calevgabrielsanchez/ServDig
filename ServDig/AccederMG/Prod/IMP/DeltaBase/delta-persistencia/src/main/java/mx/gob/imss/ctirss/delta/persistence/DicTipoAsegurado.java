package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_ASEGURADO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_ASEGURADO")
public class DicTipoAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_ASEGURADO", nullable=false, precision=22)
	private long cveIdTipoAsegurado;

	@Column(name="DES_TIPO_ASEGURADO", length=255)
	private String desTipoAsegurado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitCuotasCotizante
	@OneToMany(mappedBy="dicTipoAsegurado")
	private List<DitCuotasCotizante> ditCuotasCotizantes;

	//bi-directional many-to-one association to DitTipoAsegModalidad
	@OneToMany(mappedBy="dicTipoAsegurado")
	private List<DitTipoAsegModalidad> ditTipoAsegModalidads;

    public DicTipoAsegurado() {
    }

	public long getCveIdTipoAsegurado() {
		return this.cveIdTipoAsegurado;
	}

	public void setCveIdTipoAsegurado(long cveIdTipoAsegurado) {
		this.cveIdTipoAsegurado = cveIdTipoAsegurado;
	}

	public String getDesTipoAsegurado() {
		return this.desTipoAsegurado;
	}

	public void setDesTipoAsegurado(String desTipoAsegurado) {
		this.desTipoAsegurado = desTipoAsegurado;
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

	public List<DitCuotasCotizante> getDitCuotasCotizantes() {
		return this.ditCuotasCotizantes;
	}

	public void setDitCuotasCotizantes(List<DitCuotasCotizante> ditCuotasCotizantes) {
		this.ditCuotasCotizantes = ditCuotasCotizantes;
	}
	
	public List<DitTipoAsegModalidad> getDitTipoAsegModalidads() {
		return this.ditTipoAsegModalidads;
	}

	public void setDitTipoAsegModalidads(List<DitTipoAsegModalidad> ditTipoAsegModalidads) {
		this.ditTipoAsegModalidads = ditTipoAsegModalidads;
	}
	
}