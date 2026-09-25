package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_JORNADA database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_JORNADA")
public class DicTipoJornada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_JORNADA", nullable=false, precision=22)
	private long cveIdTipoJornada;

	@Column(name="DES_TIPO_JORNADA", length=255)
	private String desTipoJornada;

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
	@OneToMany(mappedBy="dicTipoJornada")
	private List<DitCuotasCotizante> ditCuotasCotizantes;

	//bi-directional many-to-one association to DitTipoJornadaModalidad
	@OneToMany(mappedBy="dicTipoJornada")
	private List<DitTipoJornadaModalidad> ditTipoJornadaModalidads;

    public DicTipoJornada() {
    }

	public long getCveIdTipoJornada() {
		return this.cveIdTipoJornada;
	}

	public void setCveIdTipoJornada(long cveIdTipoJornada) {
		this.cveIdTipoJornada = cveIdTipoJornada;
	}

	public String getDesTipoJornada() {
		return this.desTipoJornada;
	}

	public void setDesTipoJornada(String desTipoJornada) {
		this.desTipoJornada = desTipoJornada;
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
	
	public List<DitTipoJornadaModalidad> getDitTipoJornadaModalidads() {
		return this.ditTipoJornadaModalidads;
	}

	public void setDitTipoJornadaModalidads(List<DitTipoJornadaModalidad> ditTipoJornadaModalidads) {
		this.ditTipoJornadaModalidads = ditTipoJornadaModalidads;
	}
	
}