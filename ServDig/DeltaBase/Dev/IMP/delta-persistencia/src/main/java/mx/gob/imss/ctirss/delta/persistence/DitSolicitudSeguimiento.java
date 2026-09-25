package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_SOLICITUD_SEGUIMIENTO database table.
 * 
 */
@Entity
@Table(name="DIT_SOLICITUD_SEGUIMIENTO")
public class DitSolicitudSeguimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_SOLICITUD_SEGUIMIENTO", nullable=false, precision=22)
	private long cveIdSolicitudSeguimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ESTATUS")
	private Date fecEstatus;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="REF_OBSERVACIONES", length=255)
	private String refObservaciones;

	//bi-directional many-to-one association to DitSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SOLICITUD")
	private DitSolicitud ditSolicitud;

	//bi-directional many-to-one association to DicEstadoSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ESTADO_SOLICITUD")
	private DicEstadoSolicitud dicEstadoSolicitud;
	
	
	
    public DitSolicitudSeguimiento() {
    }

	public long getCveIdSolicitudSeguimiento() {
		return this.cveIdSolicitudSeguimiento;
	}

	public void setCveIdSolicitudSeguimiento(long cveIdSolicitudSeguimiento) {
		this.cveIdSolicitudSeguimiento = cveIdSolicitudSeguimiento;
	}

	public Date getFecEstatus() {
		return this.fecEstatus;
	}

	public void setFecEstatus(Date fecEstatus) {
		this.fecEstatus = fecEstatus;
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

	public String getRefObservaciones() {
		return this.refObservaciones;
	}

	public void setRefObservaciones(String refObservaciones) {
		this.refObservaciones = refObservaciones;
	}

	public DitSolicitud getDitSolicitud() {
		return this.ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}
	
	public DicEstadoSolicitud getDicEstadoSolicitud() {
		return this.dicEstadoSolicitud;
	}

	public void setDicEstadoSolicitud(DicEstadoSolicitud dicEstadoSolicitud) {
		this.dicEstadoSolicitud = dicEstadoSolicitud;
	}

	
}