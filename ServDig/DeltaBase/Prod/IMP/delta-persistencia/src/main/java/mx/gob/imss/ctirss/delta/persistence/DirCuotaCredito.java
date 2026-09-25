package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIR_CUOTA_CREDITO database table.
 * 
 */
@Entity
@Table(name="DIR_CUOTA_CREDITO")
public class DirCuotaCredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CUOTA_CREDITO", nullable=false, precision=22)
	private long cveIdCuotaCredito;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitCuotacredIncidencia
	@OneToMany(mappedBy="dirCuotaCredito")
	private List<DitCuotacredIncidencia> ditCuotacredIncidencias;

    public DirCuotaCredito() {
    }

	public long getCveIdCuotaCredito() {
		return this.cveIdCuotaCredito;
	}

	public void setCveIdCuotaCredito(long cveIdCuotaCredito) {
		this.cveIdCuotaCredito = cveIdCuotaCredito;
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

	public List<DitCuotacredIncidencia> getDitCuotacredIncidencias() {
		return this.ditCuotacredIncidencias;
	}

	public void setDitCuotacredIncidencias(List<DitCuotacredIncidencia> ditCuotacredIncidencias) {
		this.ditCuotacredIncidencias = ditCuotacredIncidencias;
	}
	
}