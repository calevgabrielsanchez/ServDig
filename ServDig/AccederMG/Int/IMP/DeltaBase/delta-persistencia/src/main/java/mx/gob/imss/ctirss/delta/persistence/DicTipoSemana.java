package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_SEMANA database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_SEMANA")
public class DicTipoSemana implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_SEMANA", nullable=false, precision=22)
	private long cveIdTipoSemana;

	@Column(name="DES_TIPO_SEMANA", length=50)
	private String desTipoSemana;

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
	@OneToMany(mappedBy="dicTipoSemana")
	private List<DitCuotasCotizante> ditCuotasCotizantes;

	//bi-directional many-to-one association to DitTipoSemanaModalidad
	@OneToMany(mappedBy="dicTipoSemana")
	private List<DitTipoSemanaModalidad> ditTipoSemanaModalidads;

    public DicTipoSemana() {
    }

	public long getCveIdTipoSemana() {
		return this.cveIdTipoSemana;
	}

	public void setCveIdTipoSemana(long cveIdTipoSemana) {
		this.cveIdTipoSemana = cveIdTipoSemana;
	}

	public String getDesTipoSemana() {
		return this.desTipoSemana;
	}

	public void setDesTipoSemana(String desTipoSemana) {
		this.desTipoSemana = desTipoSemana;
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
	
	public List<DitTipoSemanaModalidad> getDitTipoSemanaModalidads() {
		return this.ditTipoSemanaModalidads;
	}

	public void setDitTipoSemanaModalidads(List<DitTipoSemanaModalidad> ditTipoSemanaModalidads) {
		this.ditTipoSemanaModalidads = ditTipoSemanaModalidads;
	}
	
}