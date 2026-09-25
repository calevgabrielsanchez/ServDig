package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SSO_NOTIFICACION database table.
 * 
 */
@Entity
@Table(name="SSO_NOTIFICACION")
public class SsoNotificacion implements Serializable {
	private static final long serialVersionUID = 1L;
	private long cveIdSsoNotificacion;
	private SsoSolicitud ssoSolicitud;
	private SsoCatestatus cveTipoSsoNotificacion;
	private Date fecFechaNotificacion;

    public SsoNotificacion() {
    }


	@Id
	@Column(name="CVE_ID_SSO_NOTIFICACION")
	public long getCveIdSsoNotificacion() {
		return this.cveIdSsoNotificacion;
	}

	public void setCveIdSsoNotificacion(long cveIdSsoNotificacion) {
		this.cveIdSsoNotificacion = cveIdSsoNotificacion;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHA_NOTIFICACION")
	public Date getFecFechaNotificacion() {
		return this.fecFechaNotificacion;
	}

	public void setFecFechaNotificacion(Date fecFechaNotificacion) {
		this.fecFechaNotificacion = fecFechaNotificacion;
	}

	//bi-directional many-to-one association to SsoSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOSOLICITUD")
	public SsoSolicitud getSsoSolicitud() {
		return this.ssoSolicitud;
	}

	public void setSsoSolicitud(SsoSolicitud ssoSolicitud) {
		this.ssoSolicitud = ssoSolicitud;
	}
		
	//bi-directional many-to-one association to SsoCatestatus
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_TIPO_SSO_NOTIFICACION")
	public SsoCatestatus getSsoCatestatus() {
		return this.cveTipoSsoNotificacion;
	}

	public void setSsoCatestatus(SsoCatestatus cveTipoSsoNotificacion) {
		this.cveTipoSsoNotificacion = cveTipoSsoNotificacion;
	}
}