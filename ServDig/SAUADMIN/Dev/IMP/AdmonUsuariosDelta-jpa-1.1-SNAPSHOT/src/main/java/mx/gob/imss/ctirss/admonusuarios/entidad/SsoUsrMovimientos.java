package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;

@Entity
@Table(name="SSO_USRMOVIMIENTOS")
public class SsoUsrMovimientos extends AbstractModel{

	private static final long serialVersionUID = 1L;
	private Long cveSsoUsrMovto;
	private SsoSolicitud ssoSolicitud;
	private SsoCatestatus ssoCatEstatus;
	private String desDatosMovimientos;
	private SsoAprobador ssoAprobador;
	private Date fechaRegistro;
	
	public SsoUsrMovimientos(){
		
	}

	@Id
	@Column(name="CVE_SSOUSRMOVTO", unique=true)
	public Long getCveSsoUsrMovto() {
		return cveSsoUsrMovto;
	}

	public void setCveSsoUsrMovto(Long cveSsoUsrMovto) {
		this.cveSsoUsrMovto = cveSsoUsrMovto;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOSOLICITUD")
	public SsoSolicitud getSsoSolicitud() {
		return ssoSolicitud;
	}

	public void setSsoSolicitud(SsoSolicitud ssoSolicitud) {
		this.ssoSolicitud = ssoSolicitud;
	}

	@Column(name="DES_DATOSMOVIENTO", nullable=false, length=500)
	public String getDesDatosMovimientos() {
		return desDatosMovimientos;
	}

	public void setDesDatosMovimientos(String desDatosMovimientos) {
		this.desDatosMovimientos = desDatosMovimientos;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_APROBADOR")
	public SsoAprobador getSsoAprobador() {
		return ssoAprobador;
	}

	public void setSsoAprobador(SsoAprobador ssoAprobador) {
		this.ssoAprobador = ssoAprobador;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREGISTRO")
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOESTATUS")
	public SsoCatestatus getSsoCatEstatus() {
		return ssoCatEstatus;
	}

	public void setSsoCatEstatus(SsoCatestatus ssoCatEstatus) {
		this.ssoCatEstatus = ssoCatEstatus;
	}

}