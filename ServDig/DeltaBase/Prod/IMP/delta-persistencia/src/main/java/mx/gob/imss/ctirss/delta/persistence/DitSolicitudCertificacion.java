package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the DIT_SOLICITUD_CERTIFICACION database table.
 * 
 */
@Entity
@Table(name = "DIT_SOLICITUD_CERTIFICACION")
public class DitSolicitudCertificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_SOLICITUD_CERTIFICACION_CVEIDSOLICITUDCERTIFICACION_GENERATOR", sequenceName = "SEQ_DITSOLICITUDCERTIFICACION")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_SOLICITUD_CERTIFICACION_CVEIDSOLICITUDCERTIFICACION_GENERATOR")
	@Column(name = "CVE_ID_SOLICITUD_CERTIFICACION")
	private Long cveIdSolicitudCertificacion;

	// bi-directional many-to-one association to DitSolicitud
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_SOLICITUD")
	private DitSolicitud ditSolicitud;

	@Column(name = "NUM_FOLIO_CERTIFICACION")
	private String numFolioCertificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public DitSolicitudCertificacion() {
	}

	public Long getCveIdSolicitudCertificacion() {
		return cveIdSolicitudCertificacion;
	}

	public void setCveIdSolicitudCertificacion(Long cveIdSolicitudCertificacion) {
		this.cveIdSolicitudCertificacion = cveIdSolicitudCertificacion;
	}

	public DitSolicitud getDitSolicitud() {
		return ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}

	public String getNumFolioCertificacion() {
		return numFolioCertificacion;
	}

	public void setNumFolioCertificacion(String numFolioCertificacion) {
		this.numFolioCertificacion = numFolioCertificacion;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

}