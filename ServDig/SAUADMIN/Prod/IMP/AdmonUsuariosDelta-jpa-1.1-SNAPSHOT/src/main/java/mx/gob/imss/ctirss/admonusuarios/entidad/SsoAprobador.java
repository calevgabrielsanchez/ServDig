package mx.gob.imss.ctirss.admonusuarios.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;


/**
 * The persistent class for the SSO_APROBADOR database table.
 * 
 */
@Entity
@Table(name="SSO_APROBADOR")
public class SsoAprobador  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Long cveIdAprobador = null;
	private String cveMatricula;
	private SsoSolicitud ssoSolicitud;
	private SsoCatestatus ssoCatestatus;
	private Long admingral = null;
	
    public SsoAprobador() {
    }

	@Id
	@Column(name="CVE_ID_APROBADOR", unique=true, nullable=false)
	public Long getCveIdAprobador() {
		return this.cveIdAprobador;
	}

	public void setCveIdAprobador(Long cveIdAprobador) {
		this.cveIdAprobador = cveIdAprobador;
	}


	@Column(name="CVE_MATRICULA", nullable=false, length=20)
	public String getCveMatricula() {
		return this.cveMatricula;
	}

	public void setCveMatricula(String cveMatricula) {
		this.cveMatricula = cveMatricula;
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
	@JoinColumn(name="CVE_SSOESTATUS")
	public SsoCatestatus getSsoCatestatus() {
		return this.ssoCatestatus;
	}

	public void setSsoCatestatus(SsoCatestatus ssoCatestatus) {
		this.ssoCatestatus = ssoCatestatus;
	}

	@Column(name="IND_ADMINGRAL", nullable=true)
	public Long getAdmingral() {
		return admingral;
	}

	public void setAdmingral(Long admingral) {
		this.admingral = admingral;
	}
	
	

}