package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_ASEGURADO_HUELGA database table.
 * 
 */
@Entity
@Table(name="DIT_ASEGURADO_HUELGA")
public class DitAseguradoHuelga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ASEGURADO_HUELGA", nullable=false, precision=22)
	private long cveIdAseguradoHuelga;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_HUELGA")
	private Date fecFinHuelga;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_HUELGA")
	private Date fecInicioHuelga;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO")
	private DitAsegurado ditAsegurado;

    public DitAseguradoHuelga() {
    }

	public long getCveIdAseguradoHuelga() {
		return this.cveIdAseguradoHuelga;
	}

	public void setCveIdAseguradoHuelga(long cveIdAseguradoHuelga) {
		this.cveIdAseguradoHuelga = cveIdAseguradoHuelga;
	}

	public Date getFecFinHuelga() {
		return this.fecFinHuelga;
	}

	public void setFecFinHuelga(Date fecFinHuelga) {
		this.fecFinHuelga = fecFinHuelga;
	}

	public Date getFecInicioHuelga() {
		return this.fecInicioHuelga;
	}

	public void setFecInicioHuelga(Date fecInicioHuelga) {
		this.fecInicioHuelga = fecInicioHuelga;
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

	public DitAsegurado getDitAsegurado() {
		return this.ditAsegurado;
	}

	public void setDitAsegurado(DitAsegurado ditAsegurado) {
		this.ditAsegurado = ditAsegurado;
	}
	
}